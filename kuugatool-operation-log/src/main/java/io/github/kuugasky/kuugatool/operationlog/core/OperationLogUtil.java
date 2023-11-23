package io.github.kuugasky.kuugatool.operationlog.core;

import io.github.kuugasky.kuugatool.core.constants.KuugaConstants;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import io.github.kuugasky.kuugatool.operationlog.annotations.OperationLog;

import java.util.Objects;

/**
 * 操作记录工具类
 *
 * @author pengqinglong
 * @since 2021/3/26
 */
public class OperationLogUtil {

    private static final String REGEX = "，\r\n";

    /**
     * 获取操作记录content
     *
     * @param nowEntity 新对象
     * @param history   旧对象（新增的时候可以为null）
     * @param clazz     模版class（字段要求与新旧对象保持一致）
     * @return 拼接好的字符串
     * @throws Exception Exception
     */
    public static String getContent(Object nowEntity, Object history, Class<?> clazz) throws Exception {
        OperationLog operationLog = clazz.getAnnotation(OperationLog.class);
        if (Objects.isNull(operationLog)) {
            throw new RuntimeException("class没有@OperationLog注解");
        }

        OperationHandle operationHandle = operationLog.handle().getDeclaredConstructor().newInstance();
        return operationHandle.handle(nowEntity, history, clazz);
    }

    /**
     * 处理content后插入记录
     */
    public static String getContentPretty(String content) {
        String regex = "\r\n";
        if (content.startsWith(regex)) {
            content = content.substring(regex.length());
        }
        content = StringUtil.removeEnd(content.replaceAll(REGEX, regex), KuugaConstants.COMMA);
        return content;
    }

}