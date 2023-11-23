package io.github.kuugasky.kuugatool.core.concurrent;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * CompletionServiceUtil
 * 使用提供的 {@link Executor} 执行任务的 {@link CompletionService}。
 * 此类安排提交的任务在完成后放置在使用 {@code take} 可访问的队列中。
 * 该类足够轻量级，适合在处理任务组时临时使用。
 *
 * @author kuuga
 * @since 2021/5/18
 */
public final class CompletionServiceUtil {

    /**
     * 默认线程数
     */
    private final static int DEFAULT_THREAD_COUNT = 5;
    /**
     * 是否需要返回结果
     */
    private boolean needReturnResult;
    /**
     * 可调用的列表
     */
    private final List<Callable<?>> callableList = ListUtil.newArrayList();
    /**
     * 可运行列表
     */
    private final List<Runnable> runnableList = ListUtil.newArrayList();

    /**
     * 使用提供的 {@link Executor} 执行任务的 {@link CompletionService}。
     * 此类安排提交的任务在完成后放置在使用 {@code take} 可访问的队列中。
     * 该类足够轻量级，适合在处理任务组时临时使用。
     */
    private final ExecutorCompletionService<Object> executorCompletionService;

    private final ExecutorService executorService;

    private final AtomicInteger atomicInteger = new AtomicInteger();

    public static CompletionServiceUtil build() {
        return new CompletionServiceUtil(DEFAULT_THREAD_COUNT, false);
    }

    public static CompletionServiceUtil build(int threadCount) {
        return new CompletionServiceUtil(threadCount, false);
    }

    public static CompletionServiceUtil build(boolean needReturnResult) {
        return new CompletionServiceUtil(DEFAULT_THREAD_COUNT, needReturnResult);
    }

    public static CompletionServiceUtil build(int threadCount, boolean needReturnResult) {
        return new CompletionServiceUtil(threadCount, needReturnResult);
    }

    /**
     * 初始化ExecutorCompletionService对象
     *
     * @param needReturnResult 是否需要返回结果
     */
    public CompletionServiceUtil(int threadCount, boolean needReturnResult) {
        this.needReturnResult = needReturnResult;
        this.executorService = Executors.newFixedThreadPool(threadCount, getThreadFactory());
        this.executorCompletionService = new ExecutorCompletionService<>(executorService);
        this.needReturnResult = needReturnResult;
    }

    /**
     * 定义线程名称
     */
    private ThreadFactory getThreadFactory() {
        return new ThreadFactoryBuilder().setNameFormat("KUUGA-SLEUTH-COMPLETION-THREAD-POOL-%d").build();
    }

    public CompletionServiceUtil submit(Callable<Object> future) {
        executorCompletionService.submit(future);
        callableList.add(future);
        atomicInteger.incrementAndGet();
        return this;
    }

    public CompletionServiceUtil submit(Runnable runnable) {
        executorCompletionService.submit(runnable, null);
        runnableList.add(runnable);
        atomicInteger.incrementAndGet();
        return this;
    }

    private void checkTaskTypeCount() {
        int taskCount = atomicInteger.get();
        if (needReturnResult) {
            if (taskCount != callableList.size()) {
                throw new RuntimeException("不允许一个任务内处理多种类型任务");
            }
        } else {
            if (taskCount != runnableList.size()) {
                throw new RuntimeException("不允许一个任务内处理多种类型任务");
            }
        }
    }

    public void takeGet() throws InterruptedException, ExecutionException {
        checkTaskTypeCount();
        if (ListUtil.isEmpty(ListUtil.nullFilter(runnableList))) {
            return;
        }
        try {
            for (int i = 0; i < runnableList.size(); i++) {
                executorCompletionService.take().get();
            }
        } finally {
            if (null != executorService) {
                executorService.shutdown();
            }
        }
    }

    public <V> List<V> takeGetReturn() throws InterruptedException, ExecutionException {
        checkTaskTypeCount();
        if (ListUtil.isEmpty(ListUtil.nullFilter(callableList))) {
            return ListUtil.newArrayList();
        }

        try {
            List<V> result = ListUtil.newArrayList();
            for (int i = 0; i < callableList.size(); i++) {
                Object object = executorCompletionService.take().get();
                result.add(ObjectUtil.cast(object));
            }
            return result;
        } finally {
            if (null != executorService) {
                executorService.shutdown();
            }
        }
    }

}
