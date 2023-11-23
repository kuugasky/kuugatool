package io.github.kuugasky.kuugatool.core.concurrent.guava;

import com.google.common.annotations.Beta;
import com.google.common.base.Function;
import com.google.common.util.concurrent.*;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;

/**
 * GuavaListeningExecutorService
 * <p>
 * An {@link ExecutorService} that returns {@link ListenableFuture} instances. To create an instance
 * from an existing {@link ExecutorService}, call {@link
 * MoreExecutors#listeningDecorator(ExecutorService)}.
 *
 * @author kuuga
 * @since 2022/8/9 11:37
 */
public final class GuavaThreadPoolWrapper {

    private final ListeningExecutorService listeningExecutorService;

    private GuavaThreadPoolWrapper(ListeningExecutorService listeningExecutorService) {
        this.listeningExecutorService = listeningExecutorService;
    }

    /**
     * jdk future to listenableFuture
     *
     * @param future jdk future
     * @param <V>    V
     * @return listenableFuture
     */
    @Beta
    @SuppressWarnings("all")
    public <V extends @Nullable Object> ListenableFuture<V> listenInPoolThread(Future<V> future) {
        return JdkFutureAdapters.listenInPoolThread(future);
    }

    /**
     * 构建一个由主线程执行的ListeningExecutorService服务
     *
     * @return ListeningExecutorService
     */
    public static GuavaThreadPoolWrapper build() {
        /*
         * 返回一个ListeningExecutorService实例，具体是DirectExecutorService类型，DirectExecutorService是MoreExecutors的内部类，继承了AbstractListeningExecutorService。
         * 和DirectExecutor实例类似，调用DirectExecutorService实例的submit()方法时，会在当前线程执行任务，而不会另起一个线程。
         */
        ListeningExecutorService listeningExecutorService = MoreExecutors.newDirectExecutorService();
        return new GuavaThreadPoolWrapper(listeningExecutorService);
    }

    /**
     * 构建一个由{@link ExecutorService}指定的ListeningExecutorService服务
     *
     * @param executorService executorService
     * @return ListeningExecutorService
     */
    public static GuavaThreadPoolWrapper build(ExecutorService executorService) {
        ListeningExecutorService listeningExecutorService = MoreExecutors.listeningDecorator(executorService);
        return new GuavaThreadPoolWrapper(listeningExecutorService);
    }

    /**
     * 提交{@link Callable}任务
     *
     * @param task 任务
     * @return ListenableFuture
     */
    public <T extends @Nullable Object> ListenableFuture<T> submit(Callable<T> task) {
        return listeningExecutorService.submit(task);
    }

    /**
     * 提交{@link Callable}任务
     *
     * @param task     任务
     * @param listener 监听Runnable
     * @return ListenableFuture
     */
    public <T extends @Nullable Object> ListenableFuture<T> submit(Callable<T> task, Runnable listener) {
        ListenableFuture<T> listenableFuture = listeningExecutorService.submit(task);
        addListener(listenableFuture, listener);
        return listenableFuture;
    }

    /**
     * 提交{@link Runnable}任务
     *
     * @param task 任务
     * @return ListenableFuture
     */
    public ListenableFuture<?> submit(Runnable task) {
        return listeningExecutorService.submit(task);
    }

    /**
     * 提交{@link Runnable}任务
     *
     * @param task     任务
     * @param listener 监听Runnable
     * @return ListenableFuture
     */
    public ListenableFuture<?> submit(Runnable task, Runnable listener) {
        ListenableFuture<?> listenableFuture = listeningExecutorService.submit(task);
        addListener(listenableFuture, listener);
        return listenableFuture;
    }

    /**
     * 提交{@link Runnable}任务
     *
     * @param task   任务
     * @param result 返回对象
     * @return ListenableFuture
     */
    public <T extends @Nullable Object> ListenableFuture<T> submit(Runnable task, T result) {
        return listeningExecutorService.submit(task, result);
    }

    /**
     * 提交{@link Runnable}任务
     *
     * @param task     任务
     * @param result   返回对象
     * @param listener 监听Runnable
     * @return ListenableFuture
     */
    public <T extends @Nullable Object> ListenableFuture<T> submit(Runnable task, T result, Runnable listener) {
        ListenableFuture<T> listenableFuture = listeningExecutorService.submit(task, result);
        addListener(listenableFuture, listener);
        return listenableFuture;
    }

    /**
     * 在多线程运算完的时候，指定的Runnable{@code listener}参数传入的对象会被指定的Executor执行。
     *
     * @param listenableFuture listenableFuture
     * @param listener         listener
     */
    private <T extends @Nullable Object> void addListener(ListenableFuture<T> listenableFuture, Runnable listener) {
        if (ObjectUtil.nonNull(listener)) {
            listenableFuture.addListener(listener, this.listeningExecutorService);
        }
    }

    public <T extends @Nullable Object> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks)
            throws InterruptedException {
        return listeningExecutorService.invokeAll(tasks);
    }

    public <T extends @Nullable Object> List<Future<T>> invokeAll(
            Collection<? extends Callable<T>> tasks, long timeout, TimeUnit unit)
            throws InterruptedException {
        return listeningExecutorService.invokeAll(tasks, timeout, unit);
    }

    public <V extends @Nullable Object> void addCallback(
            final ListenableFuture<V> future,
            final FutureCallback<? super V> callback,
            Executor executor) {
        Futures.addCallback(future, callback, executor);
    }

    public <V extends @Nullable Object> void addCallback(
            final ListenableFuture<V> future,
            final FutureCallback<? super V> callback) {
        Futures.addCallback(future, callback, this.listeningExecutorService);
    }

    /**
     * 返回一个ListenableFuture
     * <p>
     * 该ListenableFuture返回的result是一个List，List中的值是每个ListenableFuture的返回值
     * <p>
     * 假如传入的其中之一fails或者cancel，这个Future fails 或者canceled
     *
     * @param futures futures
     * @return ListenableFuture
     */
    @Beta
    @SuppressWarnings("all")
    public <V extends @Nullable Object> ListenableFuture<List<V>> allAsList(
            Iterable<? extends ListenableFuture<? extends V>> futures) {
        return Futures.allAsList(futures);
    }

    @Beta
    @SafeVarargs
    @SuppressWarnings("all")
    public final <V extends @Nullable Object> ListenableFuture<List<V>> allAsList(ListenableFuture<? extends V>... futures) {
        return Futures.allAsList(futures);
    }

    /**
     * 返回一个ListenableFuture
     * <p>
     * 该Future的结果包含所有成功的Future，按照原来的顺序，当其中之一Failed或者cancel，则用null替代
     *
     * @param futures futures
     * @return ListenableFuture
     */
    @SuppressWarnings("all")
    public <V extends @Nullable Object> ListenableFuture<List<@Nullable V>> successfulAsList(
            ListenableFuture<? extends V>... futures) {
        return Futures.successfulAsList(futures);
    }

    @Beta
    @SuppressWarnings("all")
    public <V extends @Nullable Object> ListenableFuture<List<@Nullable V>> successfulAsList(
            Iterable<? extends ListenableFuture<? extends V>> futures) {
        return Futures.successfulAsList(futures);
    }

    /**
     * 返回一个新的ListenableFuture
     * <p>
     * 该ListenableFuture返回的result是由传入的Function参数指派到传入的ListenableFuture中.
     *
     * @param input    input
     * @param function function
     * @return ListenableFuture
     */
    @Beta
    @SuppressWarnings("all")
    public <I extends @Nullable Object, O extends @Nullable Object> ListenableFuture<O> transform(
            ListenableFuture<I> input, Function<? super I, ? extends O> function
    ) {
        return Futures.transform(input, function, this.listeningExecutorService);
    }

    /**
     * 返回一个新的{@code Future}，其结果异步派生自给定的{@code Future}。
     * <p>
     * 如果给定的{@code Future}失败，返回的{@code Future}将失败，返回相同的异常（并且不会调用该函数）。
     *
     * @param input    input
     * @param function function
     * @return ListenableFuture
     */
    @Beta
    @SuppressWarnings("all")
    public <I extends @Nullable Object, O extends @Nullable Object> ListenableFuture<O> transformAsync(
            ListenableFuture<I> input, AsyncFunction<? super I, ? extends O> function
    ) {
        return Futures.transformAsync(input, function, this.listeningExecutorService);
    }

    /**
     * 返回一个新的{@code Future}，其结果异步派生自给定的{@code Future}。
     * <p>
     * 如果给定的{@code Future}失败，返回的{@code Future}将失败，返回相同的异常（并且不会调用该函数）。
     *
     * @param input    input
     * @param function function(同步转异步{@link AsyncFunction})
     * @return ListenableFuture
     */
    @Beta
    @SuppressWarnings("all")
    public <I extends @Nullable Object, O extends @Nullable Object> ListenableFuture<O> transformAsyncBySync(
            ListenableFuture<I> input, Function<? super I, ? extends O> function
    ) {
        AsyncFunction<I, O> asyncFunction = new AsyncFunction<>() {
            @Override
            public ListenableFuture<O> apply(I input) throws Exception {
                ListenableFuture<O> listenableFuture = listeningExecutorService.submit(() -> function.apply(input));
                return listenableFuture;
            }
        };
        return Futures.transformAsync(input, asyncFunction, this.listeningExecutorService);
    }

}
