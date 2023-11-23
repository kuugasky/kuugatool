package io.github.kuugasky.kuugatool.core.date.time;

/**
 * 转换解析器
 *
 * @author pengqinglong
 * @since 2022/2/15
 */
public interface ConvertorParse<T, V> {

    /**
     * 将对象解析后返回
     *
     * @param t t
     * @return v
     */
    V parse(T t);

}