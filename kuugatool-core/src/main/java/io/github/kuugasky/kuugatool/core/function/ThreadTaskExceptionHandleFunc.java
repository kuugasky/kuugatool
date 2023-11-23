package io.github.kuugasky.kuugatool.core.function;

import java.util.List;

/**
 * 线程任务异常处理函数
 *
 * @author kuuga
 */
@FunctionalInterface
public interface ThreadTaskExceptionHandleFunc<T, V> {

    /**
     * 线程任务异常处理
     *
     * @param list      任务数据源
     * @param callable  任务函数
     * @param throwable 异常
     */
    void handle(List<T> list, ThreadTaskFunc<T, V> callable, Throwable throwable);

}
