package io.github.kuugasky.kuugatool.core.concurrent.pool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.concurrent.core.KuugaRejectedExecutionHandler;
import io.github.kuugasky.kuugatool.core.concurrent.core.KuugaThreadPoolExecutor;
import io.github.kuugasky.kuugatool.core.enums.TaskSplitType;
import io.github.kuugasky.kuugatool.core.function.ThreadTaskNoReturnValueExceptionHandleFunc;
import io.github.kuugasky.kuugatool.core.function.ThreadTaskNoReturnValueFunc;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * CompletableFuturePool
 *
 * @author kuuga
 * @since 2023/5/9-05-09 20:49
 */
@Slf4j
public class CompletableFuturePool {

    // 自定义线程池 ==============================================================================================

    private static final ExecutorService EXECUTOR_SERVICE;

    static {
        final String threadFactoryName = "KUUGA-COMPLETABLE-FUTURE-THREAD-POOL-%d";
        // 定义线程工厂名称
        final ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat(threadFactoryName).build();
        // 定义工作队列：有边界阻塞队列，边界值为【20】
        final LinkedBlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(20);
        // 拒绝政策
        KuugaRejectedExecutionHandler abortPolicy = new KuugaRejectedExecutionHandler();

        EXECUTOR_SERVICE = new KuugaThreadPoolExecutor(10, 20, 60L, TimeUnit.SECONDS, workQueue, threadFactory, abortPolicy);
    }

    // 一阶

    // runAsync 异步执行无结果回调 ==============================================================================================

    /**
     * 返回一个新的CompletableFuture，该Future由在给定执行器中运行的任务在运行给定操作后异步完成。
     * <p>
     * 一阶无入参，无返回
     *
     * @param runnable 在完成返回的CompletableFuture之前要运行的操作
     */
    public static CompletableFuture<Void> runAsync(Runnable runnable) {
        return CompletableFuture.runAsync(runnable, EXECUTOR_SERVICE);
    }

    public static CompletableFuture<Void> runAsync(Runnable runnable, Executor executor) {
        return CompletableFuture.runAsync(runnable, executor);
    }

    // supplyAsync 异步执行有结果回调 ==============================================================================================

    /**
     * 返回一个异步完成的新CompletableFuture，通过在给定执行器中运行的任务获得的值通过调用给定的供应商。
     * <p>
     * 一阶无入参，有返回
     *
     * @param supplier 返回要使用的值的函数来完成返回的CompletableFuture
     */
    public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier) {
        return CompletableFuture.supplyAsync(supplier, EXECUTOR_SERVICE);
    }

    public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier, Executor executor) {
        return CompletableFuture.supplyAsync(supplier, executor);
    }

    // 二阶

    /**
     * thenApply()方法可以处理和转换上一个CompletableFuture的结果。该方法可以在二阶中无限追加。
     * <p>
     * 不需要指定线程池，该方法直接使用上一个future所用线程池执行。
     * <p>
     * 二阶有入参，有返回
     *
     * @param previousOne 上一个CompletableFuture
     * @param fn          fn入参为上一个CompletableFuture的结果
     *                    注意：当fn是一个CompletableFuture时，则fn入参时完整的CompletableFuture对象不再是future的结果值，
     *                    此时该方法返回结果类型是CompletableFuture<CompletableFuture<U>>。
     *                    此种情况应使用{@link CompletableFuturePool#thenCompose(CompletableFuture, Function)}
     * @return 返回一个包装转换后的结果值的CompletableFuture
     */
    public static <T, U> CompletableFuture<U> thenApply(CompletableFuture<T> previousOne, Function<? super T, ? extends U> fn) {
        return previousOne.thenApply(fn);
    }

    public static <T, U> CompletableFuture<U> thenApplyAsync(CompletableFuture<T> previousOne, Function<? super T, ? extends U> fn) {
        return previousOne.thenApplyAsync(fn, EXECUTOR_SERVICE);
    }

    public static <T, U> CompletableFuture<U> thenApplyAsync(CompletableFuture<T> previousOne, Function<? super T, ? extends U> fn, Executor executor) {
        if (null == executor) {
            return previousOne.thenApplyAsync(fn);
        } else {
            return previousOne.thenApplyAsync(fn, executor);
        }
    }

    /**
     * thenCompose()方法可以合并多个Future。同样是承接上一个CompletableFuture，但thenCompost支持fn为一个完整的CompletableFuture对象
     * - 与thenApply相同的是，都可以承接上一个CompletableFuture
     * - 与thenApply不同的是，thenApply的fn常用于承接的是上一个future的结果值，但fn是一整个CompletableFuture时，
     * 则返回结果类型是嵌套的CompletableFuture<CompletableFuture<U>>，而thenCompose.fn则用于承接完整的CompletableFuture
     * <p>
     * 二阶有入参，有返回
     *
     * @param previousOne 上一个CompletableFuture
     * @param fn          fn入参为一个完整的CompletableFuture
     * @return CompletableFuture<U>
     */
    public static <T, U> CompletableFuture<U> thenCompose(CompletableFuture<T> previousOne, Function<? super T, ? extends CompletionStage<U>> fn) {
        return previousOne.thenCompose(fn);
    }

    public static <T, U> CompletableFuture<U> thenComposeAsync(CompletableFuture<T> previousOne, Function<? super T, ? extends CompletionStage<U>> fn) {
        return previousOne.thenComposeAsync(fn, EXECUTOR_SERVICE);
    }

    public static <T, U> CompletableFuture<U> thenComposeAsync(CompletableFuture<T> previousOne, Function<? super T, ? extends CompletionStage<U>> fn, Executor executor) {
        if (null == executor) {
            return previousOne.thenComposeAsync(fn);
        }
        return previousOne.thenComposeAsync(fn, executor);
    }

    /**
     * 合并两个独立的Future，该方法会等两个future都执行完成后才开始执行。[结合两个以上Future时，使用allOf或anyOf]
     * <p>
     * 二阶有入参，有返回
     *
     * @param previousOne 第一个CompletableFuture
     * @param other       第二个CompletableFuture
     * @param fn          两个CompletableFuture的结果BiFunction
     * @return CompletableFuture<V>
     */
    public static <T, U, V> CompletableFuture<V> thenCombine(CompletableFuture<T> previousOne, CompletionStage<? extends U> other,
                                                             BiFunction<? super T, ? super U, ? extends V> fn) {
        return previousOne.thenCombine(other, fn);
    }

    // 终阶

    /**
     * Future最后阶段，返回CompletableFuture<Void>，无实际结果值，只是在future完成后执行一些代码。也可以使用thenRun().
     * <p>
     * 终阶有入参，无返回
     *
     * @param previousOne 上一个CompletableFuture
     * @param action      future完成后执行的消费者函数，action入参为上一个CompletableFuture的结果
     * @return CompletableFuture<Void>
     */
    public static <T> CompletableFuture<Void> thenAccept(CompletableFuture<T> previousOne, Consumer<? super T> action) {
        return previousOne.thenAccept(action);
    }

    /**
     * 同{@link CompletableFuturePool#thenAccept(CompletableFuture, Consumer)}，区别在于该方法执行用{@code EXECUTOR_SERVICE}开启新异步线程执行
     * <p>
     * 终阶有入参，无返回
     *
     * @param previousOne 上一个CompletableFuture
     * @param action      future完成后执行的消费者函数，action入参为上一个CompletableFuture的结果
     * @return CompletableFuture<Void>
     */
    public static <T> CompletableFuture<Void> thenAcceptAsync(CompletableFuture<T> previousOne, Consumer<? super T> action) {
        return previousOne.thenAcceptAsync(action, EXECUTOR_SERVICE);
    }

    /**
     * 同{@link CompletableFuturePool#thenAccept(CompletableFuture, Consumer)}，区别在于该方法执行用{@code EXECUTOR_SERVICE}或{@code ForkJoinPool}开启新异步线程执行
     * <p>
     * 终阶有入参，无返回
     *
     * @param previousOne 上一个CompletableFuture
     * @param action      future完成后执行的消费者函数，action入参为上一个CompletableFuture的结果
     * @return CompletableFuture<Void>
     */
    public static <T> CompletableFuture<Void> thenAcceptAsync(CompletableFuture<T> previousOne, Consumer<? super T> action, Executor executor) {
        if (null == executor) {
            // ForkJoinPool
            return previousOne.thenAcceptAsync(action);
        }
        return previousOne.thenAcceptAsync(action, executor);
    }

    /**
     * Future最后阶段，返回CompletableFuture<Void>，无实际结果值，只是在future完成后执行一些代码。
     * <p>
     * 终阶无入参，无返回
     *
     * @param previousOne 上一个CompletableFuture
     * @param action      future完成后执行的Runnable函数，与thenAccept.consumer不同的是不能获取上一个CompletableFuture的执行结果做为入参
     * @return CompletableFuture<Void>
     */
    public static <T> CompletableFuture<Void> thenRun(CompletableFuture<T> previousOne, Runnable action) {
        return previousOne.thenRun(action);
    }

    /**
     * 合并多个同返回类型的CompletableFuture，无序执行多任务。
     * <p>
     * 终阶无返回，需要执行.get()，不然cfs中异常不会抛出
     *
     * @param cfs CompletableFuture数组
     * @return CompletableFuture<Void>
     */
    public static CompletableFuture<Void> allOf(CompletableFuture<?>... cfs) {
        return CompletableFuture.allOf(cfs);
    }

    /**
     * 合并多个同返回类型的CompletableFuture，无序执行多任务。
     * <p>
     * 终阶无返回，需要执行.get()，不然cfs中异常不会抛出
     *
     * @param cfs CompletableFuture集合
     * @return CompletableFuture<Void>
     */
    public static <T> CompletableFuture<Void> allOf(List<CompletableFuture<T>> cfs) {
        return CompletableFuture.allOf(cfs.toArray(new CompletableFuture[0]));
    }

    /**
     * 多个相同或不相同返回类型的CompletableFuture并行无序执行，且cfs任何一个完成时，则返回该已完成任务结果，其他任务取消。
     * <p>
     * 终阶无返回，需要执行.get()，不然cfs中异常不会抛出
     *
     * @param cfs CompletableFuture数组
     * @return CompletableFuture<Void>
     */
    public static CompletableFuture<Object> anyOf(CompletableFuture<?>... cfs) {
        return CompletableFuture.anyOf(cfs);
    }

    /**
     * 多个相同或不相同返回类型的CompletableFuture并行无序执行，且cfs任何一个完成时，则返回该已完成任务结果，其他任务取消。
     * <p>
     * 终阶无返回，需要执行.get()，不然cfs中异常不会抛出
     *
     * @param cfs CompletableFuture集合
     * @return CompletableFuture<Void>
     */
    public static CompletableFuture<Object> anyOf(List<CompletableFuture<?>> cfs) {
        return CompletableFuture.anyOf(cfs.toArray(new CompletableFuture[0]));
    }

    // 异常处理 ==============================================================================================

    /**
     * CompletableFuture异常处理，fn入参为{@link  Exception}
     *
     * @param future CompletableFuture
     * @param fn     异常处理函数，Function<Throwable, ? extends T> fn
     * @return CompletableFuture<T>
     */
    public static <T> CompletableFuture<T> exceptionally(CompletableFuture<T> future, Function<Throwable, ? extends T> fn) {
        return future.exceptionally(fn);
    }

    public static <T> CompletableFuture<T> exceptionallyAsync(CompletableFuture<T> future, Function<Throwable, ? extends T> fn) {
        return future.exceptionallyAsync(fn, EXECUTOR_SERVICE);
    }

    public static <T> CompletableFuture<T> exceptionallyAsync(CompletableFuture<T> future, Function<Throwable, ? extends T> fn, Executor executor) {
        if (null == executor) {
            return future.exceptionallyAsync(fn);
        }
        return future.exceptionallyAsync(fn, executor);
    }

    /**
     * CompletableFuture异常处理，fn入参为{@link  Exception}
     *
     * @param future CompletableFuture
     * @param fn     异常处理函数，Function<Throwable, ? extends CompletionStage<T>>
     * @return CompletableFuture<T>
     */
    public static <T> CompletableFuture<T> exceptionallyCompose(CompletableFuture<T> future, Function<Throwable, ? extends CompletionStage<T>> fn) {
        return future.exceptionallyCompose(fn);
    }

    public static <T> CompletableFuture<T> exceptionallyComposeAsync(CompletableFuture<T> future, Function<Throwable, ? extends CompletionStage<T>> fn) {
        return future.exceptionallyComposeAsync(fn, EXECUTOR_SERVICE);
    }

    public static <T> CompletableFuture<T> exceptionallyComposeAsync(CompletableFuture<T> future, Function<Throwable, ? extends CompletionStage<T>> fn, Executor executor) {
        if (null == executor) {
            return future.exceptionallyComposeAsync(fn);
        }
        return future.exceptionallyComposeAsync(fn, executor);
    }

    /**
     * CompletableFuture异常处理，fn入参为future结果值和{@link  Exception}
     *
     * @param future CompletableFuture
     * @param fn     异常处理函数，入参为future计算结果和{@link  Exception}
     * @return CompletableFuture<T>
     */
    public static <T, U> CompletableFuture<U> handle(CompletableFuture<T> future, BiFunction<? super T, Throwable, ? extends U> fn) {
        return future.handle(fn);
    }

    public static <T, U> CompletableFuture<U> handleAsync(CompletableFuture<T> future, BiFunction<? super T, Throwable, ? extends U> fn) {
        return future.handleAsync(fn, EXECUTOR_SERVICE);
    }

    public static <T, U> CompletableFuture<U> handleAsync(CompletableFuture<T> future, BiFunction<? super T, Throwable, ? extends U> fn, Executor executor) {
        if (null == executor) {
            return future.handleAsync(fn);
        }
        return future.handleAsync(fn, executor);
    }

    // CompletableFuturePool构建 ==============================================================================================

    public static <T> KuugaCompletableFuture<T> build() {
        return new KuugaCompletableFuture<>(EXECUTOR_SERVICE);
    }

    // KuugaCompletableFuture封装 ==============================================================================================

    public static class KuugaCompletableFuture<T> {

        private final List<CompletableFuture<T>> completableFutures = ListUtil.newArrayList();

        private final ExecutorService executorService;

        public KuugaCompletableFuture(ExecutorService executorService) {
            this.executorService = executorService;
        }

        public KuugaCompletableFuture<T> supplyAsync(Supplier<T> supplier) {
            CompletableFuture<T> completableFuture = CompletableFuture.supplyAsync(supplier, executorService);
            completableFutures.add(completableFuture);
            return this;
        }

        public void get() throws ExecutionException, InterruptedException {
            if (ListUtil.hasItem(completableFutures)) {
                CompletableFuture.allOf((CompletableFuture<?>) completableFutures).join();
            }
            for (CompletableFuture<T> tCompletableFuture : ListUtil.optimize(completableFutures)) {
                tCompletableFuture.get();
            }
        }

        public List<T> getResult() throws ExecutionException, InterruptedException {
            if (ListUtil.hasItem(completableFutures)) {
                CompletableFuture<?>[] array = completableFutures.toArray(new CompletableFuture[0]);
                CompletableFuture.allOf(array).join();
            }
            List<T> result = ListUtil.newArrayList(completableFutures.size());
            for (CompletableFuture<T> tCompletableFuture : ListUtil.optimize(completableFutures)) {
                T u = tCompletableFuture.get();
                result.add(u);
            }
            return result;
        }
    }

    // invoke相关 ==============================================================================================

    /**
     * 调用所有任务
     * 注：循环创建任务过程中，需要将任务add到tasks，同时需要submit到线程池
     *
     * @param completableFutures 提交的任务集合
     * @param <T>                入参及出参的数据类型
     * @return T
     */
    public static <T> List<T> invokeAllTask(List<CompletableFuture<T>> completableFutures) {
        List<T> result = ListUtil.newArrayList();
        // .join():完成时返回结果值，如果异常完成则抛出(未检查的)异常。
        // 为了更好地符合通用函数形式的使用，如果在CompletableFuture的完成过程中涉及的计算抛出了异常，则该方法抛出一个(未检查的)CompletionException，并将底层异常作为其原因。
        CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0])).join();
        for (CompletableFuture<T> completableFuture : completableFutures) {
            try {
                T t = completableFuture.get();
                result.add(t);
            } catch (InterruptedException | ExecutionException e) {
                log.error("CompletableFuturePool#invokeAllTask error:{}", e.getMessage(), e);
            }
        }
        return result;
    }

    /**
     * 调用所有任务
     * 注：循环创建任务过程中，需要将任务add到tasks，同时需要submit到线程池
     *
     * @param sourceList 源数据
     * @param splitCount 拆分成几份子集
     * @param callable   可调用任务
     * @return T
     */
    public static <T, R> List<R> invokeAllTask(List<T> sourceList, TaskSplitType type, int splitCount, Function<List<T>, List<R>> callable) {
        if (ListUtil.isEmpty(sourceList)) {
            return ListUtil.emptyList();
        }
        List<List<T>> lists = TaskSplitType.getSplits(sourceList, type, splitCount);

        List<CompletableFuture<Boolean>> completableFutures = ListUtil.newArrayList();

        List<List<R>> result = ListUtil.newArrayList();

        lists.forEach(list -> {
            CompletableFuture<Boolean> booleanCompletableFuture =
                    CompletableFuture
                            .supplyAsync(() -> list, EXECUTOR_SERVICE)
                            .thenApply(callable).thenApply(result::add);

            completableFutures.add(booleanCompletableFuture);
        });

        CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0])).join();

        for (CompletableFuture<Boolean> completableFuture : completableFutures) {
            try {
                completableFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                log.error("CompletableFuturePool#invokeAllTask error:{}", e.getMessage(), e);
            }
        }

        return ListUtil.summary(result);
    }

    /**
     * 调用所有任务
     * 注：循环创建任务过程中，需要将任务add到tasks，同时需要submit到线程池
     *
     * @param sourceList 源数据
     * @param splitCount 拆分成几份子集
     * @param callable   可调用任务
     */
    public static <T> void invokeAllTaskNoReturnValue(List<T> sourceList, TaskSplitType type, int splitCount, ThreadTaskNoReturnValueFunc<T> callable) {
        invokeAllTaskNoReturnValue(sourceList, type, splitCount, callable, null);
    }

    /**
     * 调用所有任务
     * 注：循环创建任务过程中，需要将任务add到tasks，同时需要submit到线程池
     *
     * @param sourceList          源数据
     * @param splitCount          拆分成几份子集
     * @param callable            可调用任务
     * @param exceptionHandleFunc 异常处理函数（为null时任何单一任务报错则任务终止，非null时单一任务报错进行捕获）
     */
    public static <T> void invokeAllTaskNoReturnValue(List<T> sourceList, TaskSplitType type, int splitCount,
                                                      ThreadTaskNoReturnValueFunc<T> callable,
                                                      ThreadTaskNoReturnValueExceptionHandleFunc<T> exceptionHandleFunc) {
        if (ListUtil.isEmpty(sourceList)) {
            return;
        }
        List<List<T>> lists = TaskSplitType.getSplits(sourceList, type, splitCount);

        List<CompletableFuture<Void>> completableFutures = ListUtil.newArrayList();

        lists.forEach(list -> {
            CompletableFuture<Void> voidCompletableFuture =
                    CompletableFuture
                            .supplyAsync(() -> list, EXECUTOR_SERVICE)
                            .thenAccept(callable::process)
                            .exceptionally(ex -> {
                                log.error("CompletableFuturePool.invokeAllTaskNoReturnValue error:{}", ex.getMessage(), ex);
                                exceptionHandleFunc.handle(list, callable, ex);
                                return null;
                            });

            completableFutures.add(voidCompletableFuture);
        });

        CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0])).join();

        try {
            for (CompletableFuture<Void> completableFuture : completableFutures) {
                completableFuture.get();
            }
        } catch (InterruptedException | ExecutionException e) {
            log.error("CompletableFuturePool#invokeAllTaskNoReturnValue error:{}", e.getMessage(), e);
        }
    }

}
