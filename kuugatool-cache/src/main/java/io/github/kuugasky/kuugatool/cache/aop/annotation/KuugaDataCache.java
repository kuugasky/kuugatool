package io.github.kuugasky.kuugatool.cache.aop.annotation;

import io.github.kuugasky.kuugatool.cache.aop.aspect.KuugaDataCacheAop;

import java.lang.annotation.*;

/**
 * 数据缓存实现
 * <p>默认配置为：
 *  <ul>
 *  <li>1.1分钟</li>
 *  <li>2.请求阈值1</li>
 *  <li>3.默认方法签名做为缓存value</li>
 *  <li>4.不判断条件</li>
 *  <li>5.以所有参数项做为缓存依据key</li>
 *  </ul>
 *
 * <p>配合{@link KuugaDataCacheRemove}实现主动数据清除操作
 *
 * @author kuuga
 * @see KuugaDataCacheTimeUnit
 * @see KuugaDataCacheAop
 */
@Inherited
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface KuugaDataCache {
    /**
     * 缓存名，不设置则为方法签名
     *
     * @return String
     */
    String value() default "";

    /**
     * <P>缓存权键，若为空则以所有参数作为key值索引缓存</P>
     * <P>如果是单参数，且该参数时ValidateForm的子类时，可以不写下标键</P>
     * 使用下标表示参数索引位：<br/>
     * <ul>
     *  <li>list:[0]</li>
     *  <li>map:['xxx']</li>
     *  <li>object:xxx.xxx</li>
     *  <li>String:#this</li>
     * </ul>
     * <h3>"#1.list[0]"</h3>
     * <h3>"#1.map['xxx']"</h3>
     * <h3>"#1.xxx.xxx"</h3>
     * <h3>"#1.#this"</h3>
     *
     * @return String[]
     */
    String[] key() default {};

    /**
     * 缓存条件，遵循spring 表达式写法
     * 使用下标表示参数索引位：<br/>
     *
     * @return String[]
     * @see #key()
     */
    String[] condition() default {};

    /**
     * 缓存时间单位数
     *
     * @return int
     */
    int time() default 1;

    /**
     * 缓存阈值次数，当1分钟内请求的次数>=该设置次数时，才会开始使用缓存数据。
     *
     * @return int
     */
    int accessThreshold() default 1;

    /**
     * 缓存时间单位
     *
     * @return DataCacheTimeUnit
     */
    KuugaDataCacheTimeUnit timeUnit() default KuugaDataCacheTimeUnit.MINUTE;

}
