package io.github.kuugasky.design.behavior.iterator.core;

/**
 * Iterator
 * <p>
 * 作用为遍历集合中元素，相当于循环语句中的循环变量`（for(int i =0 ;i<arr.length;i++）`,具体实现一个顺序遍历的迭代器。
 *
 * @author kuuga
 * @since 2023/6/19-06-19 14:39
 */
public interface Iterator {

    /**
     * 是否由下一个元素
     *
     * @return boolean
     */
    boolean hasNext();

    /**
     * 获取下一个元素
     *
     * @return Object
     */
    Object next();

}
