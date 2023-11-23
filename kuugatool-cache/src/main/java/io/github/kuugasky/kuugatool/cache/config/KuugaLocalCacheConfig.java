package io.github.kuugasky.kuugatool.cache.config;

import io.github.kuugasky.kuugatool.cache.KuugaCache;
import io.github.kuugasky.kuugatool.cache.basic.constants.CacheConstants;
import io.github.kuugasky.kuugatool.cache.config.properties.KuugaCacheProperties;
import io.github.kuugasky.kuugatool.cache.local.KuugaLocalCacheImpl;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

/**
 * 架构默认的缓存配置类
 * <p>
 * {@code KuugaCacheProperties#REDIS_ENABLE = false}时注入本地缓存
 *
 * @author kuuga
 * @since 2023-05-29
 */
@ConditionalOnProperty(value = KuugaCacheProperties.REDIS_ENABLE, havingValue = "false")
public class KuugaLocalCacheConfig {

    /**
     * 注入本地缓存
     *
     * @param environment Environment
     * @return KuugaCache
     */
    @Bean(name = CacheConstants.KUUGA_CACHE_BEAN_NAME)
    public KuugaCache localCache(Environment environment) {
        // 创建本地缓存实现类
        KuugaCache localCache = new KuugaLocalCacheImpl();
        // 获取应用名
        String appName = environment.getProperty(CacheConstants.SPRING_APPLICATION_NAME);
        if (StringUtil.hasText(appName)) {
            // 以应用名设置为系统命名空间，默认值CLOUD_ANONYMOUS
            localCache.setSystemUnique(appName.toUpperCase());
        }
        return localCache;
    }

}
