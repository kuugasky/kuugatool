package io.github.kuugasky.kuugatool.core.concurrent.timer;

/**
 * 多线程任务错误处理对象
 *
 * @param <T> 任务类,Runnable子类
 * @author kuuga, 2013-12-24
 */
public interface ErrorHandler<T extends Runnable> {

    /**
     * 错误处理方法
     *
     * @param name  任务名称
     * @param task  任务实例
     * @param cause 异常对象
     */
    void caught(String name, T task, Throwable cause);
}
