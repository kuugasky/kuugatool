package io.github.kuugasky.kuugatool.core.concurrent.pool;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.collection.MapUtil;
import io.github.kuugasky.kuugatool.core.collection.SetUtil;
import io.github.kuugasky.kuugatool.core.concurrent.core.KuugaRejectedExecutionHandler;
import io.github.kuugasky.kuugatool.core.concurrent.core.KuugaThreadPoolExecutor;
import io.github.kuugasky.kuugatool.core.concurrent.timer.TimerTask;
import io.github.kuugasky.kuugatool.core.enums.TaskSplitType;
import io.github.kuugasky.kuugatool.core.function.ThreadTaskFunc;
import io.github.kuugasky.kuugatool.core.function.ThreadTaskNoReturnValueExceptionHandleFunc;
import io.github.kuugasky.kuugatool.core.function.ThreadTaskNoReturnValueFunc;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 线程池【使用静态内部类实现线程池单例】
 * 链表结构有界阻塞线程池
 *
 * @author kuuga
 */
@Slf4j
public final class ThreadPool {

    /**
     * 私有构造函数
     */
    private ThreadPool() {
    }

    /**
     * 可变尺寸的线程池
     */
    private static ExecutorService executorService = null;

    public static ListeningExecutorService toListeningExecutorService() {
        return MoreExecutors.listeningDecorator(getPool());
    }

    /**
     * 枚举实现单例
     */
    private enum ThreadPoolSingleton {
        /**
         * 线程池单例枚举
         */
        INSTANCE;

        private final ExecutorService threadPoolService;

        public static final String THREAD_FACTORY_NAME = "KUUGA-THREAD-POOL-%d";

        ThreadPoolSingleton() {
            // 定义线程工厂名称
            final ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat(THREAD_FACTORY_NAME).build();
            // 定义工作队列：有边界阻塞队列，边界值为【20】
            final LinkedBlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(20);
            // 拒绝政策
            KuugaRejectedExecutionHandler abortPolicy = new KuugaRejectedExecutionHandler();

            threadPoolService = new KuugaThreadPoolExecutor(10, 20, 60L, TimeUnit.SECONDS, workQueue, threadFactory, abortPolicy);
        }

        public ExecutorService getSingleton() {
            return threadPoolService;
        }
    }

    /**
     * 单例
     */
    private static ExecutorService getPool() {
        if (null == executorService || executorService.isShutdown() || executorService.isTerminated()) {
            synchronized (ThreadPool.class) {
                if (executorService == null || executorService.isShutdown() || executorService.isTerminated()) {
                    executorService = ThreadPoolSingleton.INSTANCE.getSingleton();
                }
            }
        }
        return executorService;
    }

    /**
     * 提交一个 Callable 任务用于执行，并返回一个表示该任务的 Future。
     *
     * @param task 要提交的任务
     * @return 表示任务等待完成的 Future
     * <p>
     * // 获取任务结果
     * V get() throws InterruptedException, ExecutionException;
     * <p>
     * // 支持超时时间的获取任务结果
     * V get(long timeout, TimeUnit unit)
     * throws InterruptedException, ExecutionException, TimeoutException;
     * <p>
     * // 判断任务是否已完成
     * boolean isDone();
     * <p>
     * // 判断任务是否已取消
     * boolean isCancelled();
     * <p>
     * // 取消任务
     * 如果任务未执行，取消不报错；如果任务已执行，取消可能会报：java.lang.InterruptedException: sleep interrupted
     * boolean cancel(boolean mayInterruptIfRunning);
     */
    public static <V> Future<V> call(Callable<V> task) {
        return getPool().submit(task);
    }

    /**
     * 在未来某个时间执行给定的命令。该命令可能在新的线程、已入池的线程或者正调用的线程中执行，这由 Executor 实现决定。
     *
     * @param task 可运行的任务
     */
    public static void execute(Runnable task) {
        getPool().execute(task);
    }

    /**
     * 提交无需返回值的任务，Runnable 接口 run() 方法无返回值
     *
     * @param task 提交的任务
     * @return 表示任务等待完成的 Future
     */
    public static Future<?> submit(Runnable task) {
        return getPool().submit(task);
    }

    /**
     * 提交需要返回值的任务，Callable 接口 call() 方法有返回值
     *
     * @param task 提交的任务
     * @param <T>  结果的类型
     * @return T
     */
    public static <T> Future<T> submit(Callable<T> task) {
        return getPool().submit(task);
    }

    /**
     * 提交需要返回值的任务，任务结果是第二个参数 result 对象
     *
     * @param task   提交的任务
     * @param result 传入一个对应用于接收返回值
     * @param <T>    结果的类型
     * @return 代表任务即将完成的未来
     */
    public static <T> Future<T> submit(Runnable task, T result) {
        return getPool().submit(task, result);
    }

    /**
     * 调用所有任务
     * 注：循环创建任务过程中，需要将任务add到tasks，同时需要submit到线程池
     *
     * @param tasks 提交的任务集合
     * @param <T>   入参及出参的数据类型
     * @return T
     */
    public static <T> List<T> invokeAllTask(List<Callable<T>> tasks) {
        List<T> results = new ArrayList<>(tasks.size());
        try {
            List<Future<T>> futureList = getPool().invokeAll(tasks);

            for (Future<T> future : futureList) {
                results.add(future.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            log.error("ThreadPool#invokeAllTask error:{}", e.getMessage(), e);
        }
        return results;
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
    public static <T, V> List<V> invokeAllTask(List<T> sourceList, TaskSplitType type, int splitCount, ThreadTaskFunc<T, V> callable) {
        if (ListUtil.isEmpty(sourceList)) {
            return ListUtil.emptyList();
        }
        List<List<T>> lists = TaskSplitType.getSplits(sourceList, type, splitCount);

        List<Future<List<V>>> futureList;
        List<List<V>> results = new ArrayList<>(splitCount);

        List<Callable<List<V>>> tasks = ListUtil.newArrayList();

        ListUtil.optimize(lists).forEach(list -> {
            Callable<List<V>> process = callable.process(list);
            tasks.add(process);
            ThreadPool.submit(process);
        });

        try {
            futureList = getPool().invokeAll(tasks);

            for (Future<List<V>> future : futureList) {
                results.add(future.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            log.error("ThreadPool#invokeAllTask error:{}", e.getMessage(), e);
        }
        return ListUtil.summary(results);
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

        Map<Future<?>, List<T>> tasks = MapUtil.newHashMap();

        ListUtil.optimize(lists).forEach(list -> {
            Runnable process = () -> callable.process(list);
            Future<?> future = ThreadPool.submit(process);
            tasks.put(future, list);
        });

        try {
            for (Future<?> future : SetUtil.optimize(tasks.keySet())) {
                if (ObjectUtil.isNull(exceptionHandleFunc)) {
                    future.get();
                } else {
                    try {
                        future.get();
                    } catch (Exception e) {
                        log.error("ThreadPool.invokeAllTaskNoReturnValue error:{}", e.getMessage(), e);
                        exceptionHandleFunc.handle(tasks.get(future), callable, e);
                    }
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            log.error("ThreadPool#invokeAllTaskNoReturnValue error:{}", e.getMessage(), e);
        }
    }

    /**
     * 提交任务
     *
     * @param timerTask 要提交的任务
     */
    public static <R extends Runnable> void submit(TimerTask<R> timerTask) {
        Future<?> future = getPool().submit(timerTask.task());
        timerTask.future(future);
        getPool().execute(timerTask);
    }

    /**
     * 获取线程池信息
     */
    public static String poolInfo() {
        ExecutorService pool = getPool();
        return pool.toString();
    }

    /**
     * 获取线程池待完成的工作数量
     */
    public static int poolTaskCount() {
        ExecutorService pool = getPool();
        ThreadPoolExecutor tp = ((ThreadPoolExecutor) pool);
        // 工作队列数量
        int queueSize = tp.getQueue().size();
        // 正在积极执行任务的线程的大致数目
        int activeCount = tp.getActiveCount();
        return queueSize + activeCount;
    }

    /**
     * 关闭线程池(等待所有任务执行完成)
     * 不会立即终止线程池，而是要等所有任务缓存队列中的任务都执行完后才终止，但再也不会接受新的任务
     */
    public static void shutdown() {
        getPool().shutdown();
    }

    /**
     * 即刻关闭线程池
     * 立即终止线程池，并尝试打断正在执行的任务，并且清空任务缓存队列，返回尚未执行的任务
     */
    @Deprecated
    public static void shutdownNow() {
        getPool().shutdownNow();
    }

}