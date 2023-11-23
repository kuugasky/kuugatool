package io.github.kuugasky.design.behavior.iterator.core;

/**
 * Aggregate
 * <p>
 * 集合：所要遍历的集合的接口。实现了该接口的类将成为一个可以保存多个元素的集合，类似数组。
 * <p>
 * 集合接口中套迭代器接口方法。
 *
 * @author kuuga
 * @since 2023/6/19-06-19 14:38
 */
public interface Aggregate {

    /**
     * 定义迭代器接口
     *
     * @return 该迭代方法接口需要由其子类实现，包含hasNext和next方法。
     */
    Iterator iterator();

}
