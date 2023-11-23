package io.github.kuugasky.kuugatool.core.concurrent.synctask;

/**
 * 比较函数 用于比较两个对象是否是同一个
 *
 * @author pengqinglong
 * @since 2022/4/20
 */
@FunctionalInterface
public interface Comparable<T> {

    /**
     * 主键对比
     *
     * @param source 源数据
     * @param result 结果
     * @return boolean
     */
    boolean comparable(T source, Result result);

}