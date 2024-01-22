package io.github.kuugasky.kuugatool.cache.aop.aspect;

import com.alibaba.fastjson2.JSON;
import io.github.kuugasky.kuugatool.cache.KuugaCache;
import io.github.kuugasky.kuugatool.cache.aop.annotation.KuugaDataCache;
import io.github.kuugasky.kuugatool.cache.aop.annotation.KuugaDataCacheRemove;
import io.github.kuugasky.kuugatool.cache.aop.annotation.KuugaDataCacheTimeUnit;
import io.github.kuugasky.kuugatool.cache.basic.cachekey.KuugaBaseCacheKey;
import io.github.kuugasky.kuugatool.cache.basic.constants.CacheConstants;
import io.github.kuugasky.kuugatool.core.md5.MD5Util;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import io.github.kuugasky.kuugatool.extra.spring.KuugaSpringBeanPicker;
import jakarta.annotation.PostConstruct;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DependsOn;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * data-cache缓存切面，具体操作标记了注解的方法
 * <p>
 * 拦截标记Cache注解的方法
 *
 * @author kuuga
 * @see KuugaDataCache
 * @see KuugaDataCacheRemove
 * @see KuugaDataCacheTimeUnit
 * @since 2023-06-15
 */
@Aspect
@Component
@DependsOn(KuugaSpringBeanPicker.BEAN_NAME)
public class KuugaDataCacheAop {
    /**
     * log
     */
    private static final Logger logger = LoggerFactory.getLogger(KuugaDataCacheAop.class);
    /**
     * 井号符
     */
    public static final String POUND_SIGN = "#";

    /**
     * 下标匹配器
     */
    Pattern indexPattern = Pattern.compile("#\\d+\\.");
    private final static int THRESHOLD_SECOND = 60;

    private KuugaCache kuugaCache;

    @PostConstruct
    private void init() {
        String redisBeanName = CacheConstants.KUUGA_CACHE_BEAN_NAME;
        if (StringUtil.hasText(redisBeanName)) {
            kuugaCache = KuugaSpringBeanPicker.getBean(redisBeanName, KuugaCache.class);
        }
        if (kuugaCache == null) {
            throw new IllegalStateException("======> no DataCacheRedis found,please check your config <======");
        }
    }

    @Around(value = "@annotation(kuugaDataCacheAble)")
    public Object caching(ProceedingJoinPoint joinPoint, KuugaDataCache kuugaDataCacheAble) throws Throwable {
        Object cacheObject = null;
        boolean processFlag = false;
        try {
            // 判断是否有条件
            // 未设置缓存时间或缓存时间小于等于0
            boolean notSetCacheTime = checkCacheTime(kuugaDataCacheAble);
            if (notSetCacheTime || !checkCondition(joinPoint, kuugaDataCacheAble.condition())) {
                processFlag = true;
                cacheObject = joinPoint.proceed();
            } else {
                String sign = StringUtil.isEmpty(kuugaDataCacheAble.value())
                        ? joinPoint.getSignature().toShortString() : kuugaDataCacheAble.value();
                String key = sign + ":" + MD5Util.getMd5(makeKey(joinPoint, kuugaDataCacheAble.key()));
                cacheObject = kuugaCache.get(KuugaBaseCacheKey.REDIS_CACHED_DATA, key);
                if (cacheObject == null) {
                    processFlag = true;
                    cacheObject = joinPoint.proceed();
                    if (cacheObject != null) {
                        long threshold = kuugaCache.incr(KuugaBaseCacheKey.REDIS_CACHED_DATA_THRESHOLD, key);
                        if (threshold == 1) {
                            kuugaCache.expire(KuugaBaseCacheKey.REDIS_CACHED_DATA_THRESHOLD, key, THRESHOLD_SECOND);
                        }
                        if (threshold > kuugaDataCacheAble.accessThreshold() - 1) {
                            int lifecycle = calculateLifecycle(kuugaDataCacheAble);
                            kuugaCache.put(KuugaBaseCacheKey.REDIS_CACHED_DATA, key, cacheObject, lifecycle);
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            if (!processFlag) {
                return joinPoint.proceed();
            }
        }
        return cacheObject;
    }

    private boolean checkCacheTime(KuugaDataCache kuugaDataCacheAble) {
        boolean notSet = kuugaDataCacheAble.time() <= 0;
        if (notSet) {
            logger.error("缓存注解time参数设置错误：必须大于0");
        }
        return notSet;
    }

    @Around(value = "@annotation(kuugaDataCacheRemove)")
    public Object removing(ProceedingJoinPoint joinPoint, KuugaDataCacheRemove kuugaDataCacheRemove) throws Throwable {
        String sign = StringUtil.isEmpty(kuugaDataCacheRemove.value())
                ? joinPoint.getSignature().toShortString() : kuugaDataCacheRemove.value();
        String key = sign + ":" +
                MD5Util.getMd5(makeKey(joinPoint, kuugaDataCacheRemove.key()));
        kuugaCache.remove(KuugaBaseCacheKey.REDIS_CACHED_DATA, key);
        return joinPoint.proceed();
    }

    /**
     * 校验条件匹配
     *
     * @param joinPoint  切面切入点
     * @param conditions 缓存条件数组
     * @return boolean
     */
    private boolean checkCondition(ProceedingJoinPoint joinPoint, String[] conditions) {
        // 获取切面接口参数数组
        Object[] args = joinPoint.getArgs();
        // 接口参数数组为空，返回
        if (args == null || args.length == 0) {
            return true;
        }
        // 自定义匹配条件为空，返回
        if (conditions == null || conditions.length == 0) {
            return true;
        }

        boolean needCheckIndex = needCheckIndex(args, conditions);
        // 循环判断自定义缓存条件
        for (String condition : conditions) {
            if (StringUtil.isEmpty(condition)) {
                continue;
            }
            // 是否需要判断下标
            if (needCheckIndex) {
                int[] matchers = matcherIndex(condition, "condition");
                if (matchers != null) {
                    Expression exp = getExpression(condition, matchers[0]);
                    Boolean value = exp.getValue(args[matchers[1]], Boolean.class);
                    value = value != null && value;
                    if (!value) {
                        return false;
                    }
                }
            } else {
                Expression exp = getExpression(condition, -1);
                Boolean value = exp.getValue(args[0], Boolean.class);
                value = value != null && value;
                if (!value) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 判断是否需要检查自定义匹配规则下标
     *
     * @param args       切面接口参数数组
     * @param conditions 缓存条件数组
     * @return boolean
     */
    private boolean needCheckIndex(Object[] args, String[] conditions) {
        boolean needCheckIndex = true;
        // if (args != null && args.length == 1 && args[0] instanceof ValidationForm) {
        if (args != null && args.length == 1) {
            if (conditions != null && conditions.length > 0 && !conditions[0].startsWith(POUND_SIGN)) {
                needCheckIndex = false;
            }
        }
        return needCheckIndex;
    }

    private Expression getExpression(String matcher, int matcherEnd) {
        // 计算参数
        String expression = matcherEnd > 0 ? matcher.substring(matcherEnd) : matcher;
        // 解析表达式
        ExpressionParser parser = new SpelExpressionParser();
        return parser.parseExpression(expression);
    }

    /**
     * 生成用于缓存的key
     */
    private byte[] makeKey(ProceedingJoinPoint joinPoint, String[] keys) {
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            return joinPoint.getSignature().toShortString().getBytes();
        }
        if (keys != null && keys.length > 0) {
            boolean needCheckIndex = needCheckIndex(args, keys);

            Object[] keyArgs = new Object[keys.length];
            for (int i = 0; i < keys.length; i++) {
                String key = keys[i];
                if (StringUtil.isEmpty(key)) {
                    continue;
                }
                if (needCheckIndex) {
                    int[] matchers = matcherIndex(key, "key");
                    if (matchers != null) {
                        keyArgs[i] = getExpression(key, matchers[0]).getValue(args[matchers[1]]);
                    }
                } else {
                    keyArgs[i] = getExpression(key, -1).getValue(args[0]);
                }
            }
            return argsToString(keyArgs).getBytes();
        }
        return argsToString(args).getBytes();
    }

    private int[] matcherIndex(String matcherStr, String type) {
        Matcher matcher = indexPattern.matcher(matcherStr);
        if (!matcher.find() || matcher.start() != 0) {
            logger.error("缓存注解" + type + "参数设置错误：['" + matcherStr + "']");
            return null;
        } else {
            int matcherEnd = matcher.end();
            int index = Integer.parseInt(matcherStr.substring(1, matcherEnd - 1));
            return new int[]{matcherEnd, index};
        }
    }

    /**
     * 将参数转换成string
     */
    private String argsToString(Object[] args) {
        if (null == args || args.length == 0) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        builder.append(toJsonString(args[0]));
        for (int i = 1; i < args.length; i++) {
            builder.append("#").append(toJsonString(args[i]));
        }
        return builder.toString();
    }

    private String toJsonString(Object obj) {
        if (obj == null) {
            return null;
        } else if (obj instanceof Number || obj instanceof String) {
            return obj.toString();
        } else {
            return JSON.toJSONString(obj);
        }
    }

    /**
     * 换算秒
     *
     * @return 缓存生命周期
     */
    private int calculateLifecycle(KuugaDataCache kuugaDataCacheAble) {
        int lifecycle = 0;
        if (kuugaDataCacheAble.time() > 0) {
            switch (kuugaDataCacheAble.timeUnit()) {
                case SECOND -> lifecycle = kuugaDataCacheAble.time();
                case MINUTE -> lifecycle = kuugaDataCacheAble.time() * 60;
                case HOUR -> lifecycle = kuugaDataCacheAble.time() * 60 * 60;
                default -> {
                }
            }
        }
        return Math.min(lifecycle, 24 * 60 * 60);
    }

}
