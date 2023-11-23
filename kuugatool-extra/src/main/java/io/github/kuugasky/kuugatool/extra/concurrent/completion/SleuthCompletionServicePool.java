package io.github.kuugasky.kuugatool.extra.concurrent.completion;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import io.github.kuugasky.kuugatool.extra.concurrent.config.SleuthThreadPoolConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.cloud.sleuth.instrument.async.TraceableScheduledExecutorService;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * SleuthCompletionServicePool
 *
 * @author kuuga
 * @since 2021/5/18
 */
@Slf4j
public final class SleuthCompletionServicePool {

    public static final String SLEUTH_COMPLETION_EXECUTOR_SERVICE = "sleuthCompletionExecutorService";

    private final static int DEFAULT_THREAD_COUNT = 5;

    private final ExecutorService traceableScheduledExecutorService;
    private final ExecutorCompletionService<Object> executorCompletionService;
    private boolean needReturnResult;
    private final ExecutorService executorService;

    private final AtomicInteger atomicInteger = new AtomicInteger();

    private final List<Callable<?>> callableList = ListUtil.newArrayList();
    private final List<Runnable> runnableList = ListUtil.newArrayList();

    public static SleuthCompletionServicePool build(BeanFactory beanFactory) {
        return new SleuthCompletionServicePool(beanFactory, DEFAULT_THREAD_COUNT, false);
    }

    public static SleuthCompletionServicePool build(BeanFactory beanFactory, int threadCount) {
        return new SleuthCompletionServicePool(beanFactory, threadCount, false);
    }

    public static SleuthCompletionServicePool build(BeanFactory beanFactory, boolean needReturnResult) {
        return new SleuthCompletionServicePool(beanFactory, DEFAULT_THREAD_COUNT, needReturnResult);
    }

    public static SleuthCompletionServicePool build(BeanFactory beanFactory, int threadCount, boolean needReturnResult) {
        return new SleuthCompletionServicePool(beanFactory, threadCount, needReturnResult);
    }

    /**
     * 初始化ExecutorCompletionService对象
     *
     * @param needReturnResult 是否需要返回结果
     */
    public SleuthCompletionServicePool(BeanFactory beanFactory, int threadCount, boolean needReturnResult) {
        this.needReturnResult = needReturnResult;
        this.executorService = Executors.newFixedThreadPool(threadCount, getThreadFactory());
        this.traceableScheduledExecutorService = new TraceableScheduledExecutorService(beanFactory, executorService);
        this.executorCompletionService = new ExecutorCompletionService<>(traceableScheduledExecutorService);
        this.needReturnResult = needReturnResult;
    }

    /**
     * 定义线程名称
     */
    private ThreadFactory getThreadFactory() {
        return new ThreadFactoryBuilder().setNameFormat(SleuthThreadPoolConfig.THREAD_COMPLETION_FACTORY_NAME).build();
    }

    public SleuthCompletionServicePool submit(Callable<Object> future) {
        executorCompletionService.submit(future);
        callableList.add(future);
        atomicInteger.incrementAndGet();
        return this;
    }

    public SleuthCompletionServicePool submit(Runnable runnable) {
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
            if (null != traceableScheduledExecutorService && !traceableScheduledExecutorService.isShutdown()) {
                traceableScheduledExecutorService.shutdown();
                // log.warn("executor shutdown");
            }
            if (null != executorService && !executorService.isShutdown()) {
                executorService.shutdown();
                // log.warn("executorService shutdown");
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
            if (null != traceableScheduledExecutorService && !traceableScheduledExecutorService.isShutdown()) {
                traceableScheduledExecutorService.shutdown();
                // log.warn("executor shutdown");
            }
            if (null != executorService && !executorService.isShutdown()) {
                executorService.shutdown();
                // log.warn("executorService shutdown");
            }
        }
    }

}
