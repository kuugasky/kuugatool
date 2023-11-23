package io.github.kuugasky.kuugatool.core.concurrent.timer;

/**
 * 多线程任务回调对象
 *
 * @param <T> 任务类,Runnable子类
 * @author kuuga, 2013-12-24
 */
public interface Callback<T extends Runnable> {

    /**
     * 回调方法
     *
     * @param name   任务名称
     * @param task   任务实例
     * @param result 任务结果
     * @see ExecuteResult
     */
    void callback(String name, T task, ExecuteResult result);
}
