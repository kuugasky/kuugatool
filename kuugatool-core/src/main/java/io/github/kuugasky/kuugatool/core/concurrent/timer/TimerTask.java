package io.github.kuugasky.kuugatool.core.concurrent.timer;

import io.github.kuugasky.kuugatool.core.concurrent.pool.ThreadPool;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 可回调任务,与{@linkplain ThreadPool}配合使用
 *
 * @param <R> 主要业务,Runnable
 * @author kuuga
 */
@Slf4j
public class TimerTask<R extends Runnable> implements Runnable {

    /**
     * 主要业务
     */
    private final R task;
    /**
     * 超时时间,单位(秒)
     */
    private int timeout;
    /**
     * 任务名称
     */
    private String name;
    /**
     * 主要业务执行完成回调
     */
    private Callback<R> callback;
    /**
     * 错误处理
     */
    private ErrorHandler<R> errorHandler;
    /**
     * 业务执行结果
     */
    private ExecuteResult result = ExecuteResult.OK;
    private Future<?> future;

    /**
     * 私有构造器
     *
     * @param task 主要业务
     */
    private TimerTask(R task) {
        this.task = task;
    }

    /**
     * 创建一个任务
     *
     * @param task 主要业务
     * @return TimerTask<T>
     */
    public static <T extends Runnable> TimerTask<T> create(T task) {
        return new TimerTask<>(task);
    }

    /**
     * 设置超时时间(秒)
     *
     * @param timeout 超时时间(秒)
     * @return 当前任务
     */
    public TimerTask<R> timeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    /**
     * 设置任务名称
     *
     * @param name 任务名称
     * @return 当前任务
     */
    public TimerTask<R> name(String name) {
        this.name = name;
        return this;
    }

    /**
     * 设置回调对象
     *
     * @param callback 回调对象
     * @return 当前任务
     */
    public TimerTask<R> callback(Callback<R> callback) {
        this.callback = callback;
        return this;
    }

    /**
     * 设置错误处理对象
     *
     * @param errorHandler 错误处理对象
     */
    public void errorHandler(ErrorHandler<R> errorHandler) {
        this.errorHandler = errorHandler;
    }

    /**
     * 设置主要业务对应的Future实例
     *
     * @param future 主要业务对应的Future实例
     */
    public void future(Future<?> future) {
        this.future = future;
    }

    /**
     * 当然任务对应的主要业务
     *
     * @return 主要业务对象
     */
    public R task() {
        return task;
    }

    /**
     * 任务实际执行逻辑,由线程自动调用
     */
    @Override
    public void run() {
        // 如未设置任务名称,则默认设为当前线程名
        name = StringUtil.isEmpty(name) ? Thread.currentThread().getName() : name;
        try {
            // 如果已设置超时时间,则最长等待所设时间后返回,否则等任务执行完成
            if (timeout > 0) {
                future.get(timeout, TimeUnit.SECONDS);
            } else {
                future.get();
            }
        } catch (Exception e) {
            // 异常&取消任务
            future.cancel(true);
            // 超时异常打印日志
            if (e instanceof TimeoutException) {
                result = ExecuteResult.TIMEOUT;
                log.warn(MessageFormat.format("Timeout: TaskName:{0}, Timeout:{1} Sec", name, timeout), e);
            } else { // 异常
                result = ExecuteResult.ERROR;
                // 任务异常处理打印日志
                if (errorHandler != null) {
                    // 任务异常触发回调任务
                    errorHandler.caught(name, task, e);
                } else {
                    log.error(MessageFormat.format("Error: TaskName:{0}, Timeout:{1} Sec", name, timeout), e);
                }
            }
        } finally {
            // 任务执行完后的回调处理逻辑
            if (callback != null) {
                callback.callback(name, task, result);
            }
        }
    }

}
