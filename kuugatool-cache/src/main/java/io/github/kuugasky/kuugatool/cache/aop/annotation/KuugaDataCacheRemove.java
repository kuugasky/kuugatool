package io.github.kuugasky.kuugatool.cache.aop.annotation;

import io.github.kuugasky.kuugatool.cache.aop.aspect.KuugaDataCacheAop;

import java.lang.annotation.*;

/**
 * data-cache组件主动缓存清除注解
 *
 * @author kuuga
 * @see KuugaDataCache
 * @see KuugaDataCacheAop
 * @since 2023-06-15
 */
@Inherited
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface KuugaDataCacheRemove {

    /**
     * 缓存名
     *
     * @return String
     */
    String value();

    /**
     * <P>缓存键</P>
     * 使用下标表示参数索引位
     * {@link KuugaDataCache#key}
     *
     * @return String[]
     */
    String[] key() default {};

}
