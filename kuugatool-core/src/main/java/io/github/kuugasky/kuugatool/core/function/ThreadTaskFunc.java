package io.github.kuugasky.kuugatool.core.function;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * 线程任务函数
 *
 * @author kuuga
 */
@FunctionalInterface
public interface ThreadTaskFunc<T, V> {

    /**
     * 线程任务处理函数
     *
     * @param list 数据源
     * @return 结果集
     */
    Callable<List<V>> process(List<T> list);

}
