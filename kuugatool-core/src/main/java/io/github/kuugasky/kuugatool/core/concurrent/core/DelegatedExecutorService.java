package io.github.kuugasky.kuugatool.core.concurrent.core;

import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

/**
 * ExecutorService代理
 *
 * @author loolly
 */
public class DelegatedExecutorService extends AbstractExecutorService {

    /**
     * 一个 Executor，它提供了管理终止的方法和可以生成 Future 以跟踪一个或多个异步任务的进度的方法。
     */
    private final ExecutorService executorService;

    /**
     * 构造
     *
     * @param executor {@link ExecutorService}
     */
    DelegatedExecutorService(ExecutorService executor) {
        Assert.notNull(executor, "executor must be not null !");
        executorService = executor;
    }

    @Override
    public void execute(@NonNull Runnable command) {
        executorService.execute(command);
    }

    @Override
    public void shutdown() {
        executorService.shutdown();
    }

    @Override
    @NonNull
    public List<Runnable> shutdownNow() {
        return executorService.shutdownNow();
    }

    @Override
    public boolean isShutdown() {
        return executorService.isShutdown();
    }

    @Override
    public boolean isTerminated() {
        return executorService.isTerminated();
    }

    @Override
    public boolean awaitTermination(long timeout, @NonNull TimeUnit unit) throws InterruptedException {
        return executorService.awaitTermination(timeout, unit);
    }

    @Override
    public Future<?> submit(@NonNull Runnable task) {
        return executorService.submit(task);
    }

    @Override
    public <T> Future<T> submit(@NonNull Callable<T> task) {
        return executorService.submit(task);
    }

    @Override
    public <T> Future<T> submit(@NonNull Runnable task, T result) {
        return executorService.submit(task, result);
    }

    @Override
    public <T> List<Future<T>> invokeAll(@NonNull Collection<? extends Callable<T>> tasks) throws InterruptedException {
        return executorService.invokeAll(tasks);
    }

    @Override
    public <T> List<Future<T>> invokeAll(@NonNull Collection<? extends Callable<T>> tasks, long timeout, @NonNull TimeUnit unit)
            throws InterruptedException {
        return executorService.invokeAll(tasks, timeout, unit);
    }

    @Override
    public <T> T invokeAny(@NonNull Collection<? extends Callable<T>> tasks)
            throws InterruptedException, ExecutionException {
        return executorService.invokeAny(tasks);
    }

    @Override
    public <T> T invokeAny(@NonNull Collection<? extends Callable<T>> tasks, long timeout, @NonNull TimeUnit unit)
            throws InterruptedException, ExecutionException, TimeoutException {
        return executorService.invokeAny(tasks, timeout, unit);
    }

}
