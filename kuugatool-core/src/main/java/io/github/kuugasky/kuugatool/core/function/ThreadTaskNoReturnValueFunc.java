package io.github.kuugasky.kuugatool.core.function;

import java.util.List;

/**
 * 线程任务函数
 *
 * @author kuuga
 */
@FunctionalInterface
public interface ThreadTaskNoReturnValueFunc<T> {

    /**
     * 线程任务处理函数
     *
     * @param list 数据源
     */
    void process(List<T> list);

}
