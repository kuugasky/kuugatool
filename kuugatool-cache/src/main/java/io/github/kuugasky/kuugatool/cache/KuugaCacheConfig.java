package io.github.kuugasky.kuugatool.cache;

import io.github.kuugasky.kuugatool.cache.aop.aspect.KuugaDataCacheAop;
import io.github.kuugasky.kuugatool.cache.config.KuugaLocalCacheConfig;
import io.github.kuugasky.kuugatool.cache.config.KuugaRedisConfig;
import io.github.kuugasky.kuugatool.cache.config.properties.KuugaCacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * KuugaCacheConfig
 *
 * @author kuuga
 * @see KuugaDataCacheAop ComponentScan注入
 * @since 2023-06-14
 */
@ComponentScan("io.github.kuugasky.kuugatool.cache.aop.aspect")
@Import({KuugaRedisConfig.class, KuugaLocalCacheConfig.class})
@EnableConfigurationProperties(KuugaCacheProperties.class)
public class KuugaCacheConfig {

}
