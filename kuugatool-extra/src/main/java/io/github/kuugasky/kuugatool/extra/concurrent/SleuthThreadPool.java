package io.github.kuugasky.kuugatool.extra.concurrent;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.ListenableFuture;
import io.github.kuugasky.kuugatool.core.collection.CollectionUtil;
import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.collection.MapUtil;
import io.github.kuugasky.kuugatool.core.collection.SetUtil;
import io.github.kuugasky.kuugatool.core.concurrent.guava.GuavaThreadPoolWrapper;
import io.github.kuugasky.kuugatool.core.concurrent.timer.TimerTask;
import io.github.kuugasky.kuugatool.core.date.interval.TimeInterval;
import io.github.kuugasky.kuugatool.core.enums.TaskSplitType;
import io.github.kuugasky.kuugatool.core.function.ThreadTaskFunc;
import io.github.kuugasky.kuugatool.core.function.ThreadTaskNoReturnValueFunc;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import io.github.kuugasky.kuugatool.core.optional.KuugaOptional;
import io.github.kuugasky.kuugatool.extra.spring.KuugaSpringBeanPicker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.sleuth.instrument.async.TraceableExecutorService;
import org.springframework.context.support.AbstractApplicationContext;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Consumer;

/**
 * 线程池【使用静态内部类实现线程池单例】
 * 链表结构有界阻塞线程池
 *
 * @author kuuga
 */
@Slf4j
public final class SleuthThreadPool {

    public static final String SLEUTH_EXECUTOR_SERVICE = "sleuthExecutorService";

    /**
     * 私有构造函数
     */
    private SleuthThreadPool() {
    }

    /**
     * 可变尺寸的线程池
     */
    private static ExecutorService executorService = null;

    /**
     * 转换为GuavaListeningExecutorService
     *
     * @return GuavaListeningExecutorService
     */
    public static GuavaThreadPoolWrapper toListeningExecutorService() {
        return GuavaThreadPoolWrapper.build(getPool());
    }

    /**
     * 单例
     */
    private static ExecutorService getPool() {
        if (null == executorService || executorService.isShutdown() || executorService.isTerminated()) {
            synchronized (SleuthThreadPool.class) {
                if (executorService == null || executorService.isShutdown() || executorService.isTerminated()) {
                    AbstractApplicationContext applicationContext = KuugaSpringBeanPicker.getApplicationContext();
                    ObjectUtil.requireNonNull(applicationContext, SLEUTH_EXECUTOR_SERVICE + " import error.");
                    executorService = (ExecutorService) applicationContext.getBean(SLEUTH_EXECUTOR_SERVICE);
                }
            }
        }
        return executorService;
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
    public static <V> Future<V> submit(Callable<V> task) {
        return getPool().submit(task);
    }

    /**
     * 提交一个 Callable 任务用于执行，并返回一个表示该任务的 Future。
     *
     * @param task          要提交的任务
     * @param errorConsumer 失败后执行函数
     * @return 表示任务等待完成的 Future
     */
    public static <V> Future<V> submit(Callable<V> task, Consumer<Throwable> errorConsumer) {
        return submit(task, null, errorConsumer);
    }

    /**
     * 提交一个 Callable 任务用于执行，并返回一个表示该任务的 Future。
     *
     * @param task            要提交的任务
     * @param successConsumer 成功后执行函数
     * @param errorConsumer   失败后执行函数
     * @return 表示任务等待完成的 Future
     */
    public static <V> Future<V> submit(Callable<V> task, Consumer<V> successConsumer, Consumer<Throwable> errorConsumer) {
        GuavaThreadPoolWrapper threadPoolWrapper = toListeningExecutorService();
        ListenableFuture<V> future = threadPoolWrapper.submit(task);

        // 异步任务回调，feature未get依旧可以执行
        FutureCallback<V> callback = new FutureCallback<>() {
            @Override
            public void onSuccess(@Nullable V result) {
                if (KuugaOptional.ofNullable(successConsumer).isPresent()) {
                    successConsumer.accept(result);
                }
            }

            @Override
            public void onFailure(@Nullable Throwable t) {
                if (KuugaOptional.ofNullable(errorConsumer).isPresent()) {
                    errorConsumer.accept(t);
                }
            }
        };

        threadPoolWrapper.addCallback(future, callback);
        return future;
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
     * 提交无需返回值的任务，Runnable 接口 run() 方法无返回值
     *
     * @param task            要提交的任务
     * @param successRunnable 成功后执行函数
     * @param errorConsumer   失败后执行函数
     * @return 表示任务等待完成的 Future
     */
    public static Future<?> submit(Runnable task, Runnable successRunnable, Consumer<Throwable> errorConsumer) {
        GuavaThreadPoolWrapper threadPoolWrapper = toListeningExecutorService();
        ListenableFuture<?> future = threadPoolWrapper.submit(task);

        // 异步任务回调，feature未get依旧可以执行
        FutureCallback<Object> callback = new FutureCallback<>() {
            @Override
            public void onSuccess(@Nullable Object result) {
                if (KuugaOptional.ofNullable(successRunnable).isPresent()) {
                    successRunnable.run();
                }
            }

            @Override
            public void onFailure(@Nullable Throwable t) {
                if (KuugaOptional.ofNullable(errorConsumer).isPresent()) {
                    errorConsumer.accept(t);
                }
            }
        };

        threadPoolWrapper.addCallback(future, callback);
        return future;
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
     * 调用所有任务
     * <p>
     * 注：循环创建任务过程中，需要将任务add到tasks，同时需要submit到线程池
     * <p>
     * ps:用法麻烦，建议使用{@link SleuthThreadPool#invokeAllTask(List, TaskSplitType, int, ThreadTaskFunc)}
     *
     * @param tasks 提交的任务集合
     * @param <T>   入参及出参的数据类型
     * @return T
     */
    public static <T> List<T> invokeAllTask(List<Callable<T>> tasks) {
        List<Future<T>> futureList;
        List<T> results = new ArrayList<>(tasks.size());
        try {
            futureList = getPool().invokeAll(tasks);

            for (Future<T> future : futureList) {
                results.add(future.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
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
        TimeInterval timeInterval = new TimeInterval();
        timeInterval.start();

        log.info("SleuthThreadPool#invokeAllTask 数据集合量({})", sourceList.size());

        if (ListUtil.isEmpty(sourceList)) {
            return ListUtil.emptyList();
        }

        List<List<T>> lists = TaskSplitType.getSplits(sourceList, type, splitCount);
        switch (type) {
            case DATA_SIZE -> log.info("SleuthThreadPool#invokeAllTask 按每个任务包含({})个元素拆分", lists.size());
            case TASK_AMOUNT -> log.info("SleuthThreadPool#invokeAllTask 按总任务量({})个拆分", lists.size());
            default -> {
            }
        }

        List<Future<List<V>>> futureList = null;
        List<List<V>> results = new ArrayList<>(splitCount);

        List<Callable<List<V>>> taskCallables = ListUtil.newArrayList();

        CollectionUtil.forEach(ListUtil.optimize(lists), (index, list) -> {
            log.info("SleuthThreadPool#invokeAllTask 第({})批数据提交处理,元素量({})", (index + 1), list.size());
            Callable<List<V>> process = callable.process(list);
            taskCallables.add(process);
        });

        try {
            futureList = getPool().invokeAll(taskCallables);
        } catch (InterruptedException e) {
            log.error("SleuthThreadPool#invokeAllTask#invokeAll 调用所有任务异常:{}", e.getMessage(), e);
        }
        CollectionUtil.forEach(futureList, (index, future) -> {
            try {
                results.add(future.get());
            } catch (InterruptedException | ExecutionException e) {
                log.error("SleuthThreadPool#invokeAllTask 第({})批数据处理异常:{}", (index + 1), e.getMessage(), e);
            }
        });
        log.info("SleuthThreadPool#invokeAllTask 调用所有任务完成,耗时:{}", timeInterval.intervalPretty());
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
    public static <T> void invokeAllTaskNoReturnValue(List<T> sourceList, TaskSplitType type, int splitCount,
                                                      ThreadTaskNoReturnValueFunc<T> callable) {
        TimeInterval timeInterval = new TimeInterval();
        timeInterval.start();

        log.info("SleuthThreadPool#invokeAllTaskNoReturnValue 数据集合量({})", sourceList.size());

        if (ListUtil.isEmpty(sourceList)) {
            return;
        }
        List<List<T>> lists = TaskSplitType.getSplits(sourceList, type, splitCount);

        switch (type) {
            case DATA_SIZE ->
                    log.info("SleuthThreadPool#invokeAllTaskNoReturnValue 按每个任务包含({})个元素拆分", lists.size());
            case TASK_AMOUNT ->
                    log.info("SleuthThreadPool#invokeAllTaskNoReturnValue 按总任务量({})个拆分", lists.size());
            default -> {
            }
        }

        Map<Future<?>, List<T>> tasks = MapUtil.newHashMap();

        CollectionUtil.forEach(ListUtil.optimize(lists), (index, list) -> {
            log.info("SleuthThreadPool#invokeAllTaskNoReturnValue 第({})批数据提交处理,元素量({})", (index + 1), list.size());
            Runnable process = () -> callable.process(list);
            Future<?> future = SleuthThreadPool.submit(process);
            tasks.put(future, list);
        });

        CollectionUtil.forEach(SetUtil.optimize(tasks.keySet()), (index, future) -> {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                log.error("SleuthThreadPool#invokeAllTaskNoReturnValue 第({})批数据处理异常:{}", (index + 1), e.getMessage(), e);
            }
        });
        lists.clear();
        tasks.clear();
        log.info("SleuthThreadPool#invokeAllTaskNoReturnValue 调用所有任务完成,耗时:{}", timeInterval.intervalPretty());
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
     * 关闭线程池(等待所有任务执行完成)<br>
     * 不会立即终止线程池，而是要等所有任务缓存队列中的任务都执行完后才终止，但再也不会接受新的任务<br>
     * PS:通过SpringIOC注入，JVM关闭时会自动调用{@link TraceableExecutorService#shutdown()}
     */
    @Deprecated
    public static void shutdown() {
        // getPool().shutdown();
    }

    /**
     * 即刻关闭线程池<br>
     * 立即终止线程池，并尝试打断正在执行的任务，并且清空任务缓存队列，返回尚未执行的任务<br>
     * PS:通过SpringIOC注入，JVM关闭时会自动调用{@link TraceableExecutorService#shutdown()}
     */
    @Deprecated
    public static void shutdownNow() {
        // getPool().shutdownNow();
    }

}