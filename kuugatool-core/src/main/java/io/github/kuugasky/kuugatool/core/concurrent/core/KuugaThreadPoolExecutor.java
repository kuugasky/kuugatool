package io.github.kuugasky.kuugatool.core.concurrent.core;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 扩展线程池执行器
 * ThreadPoolExecutor，它使用可能的几个池线程中的一个执行每个提交的任务，通常使用Executors工厂方法配置。
 *
 * @author kuuga
 */
@Slf4j
public final class KuugaThreadPoolExecutor extends ThreadPoolExecutor {

    private final ThreadLocal<Long> startTime = new ThreadLocal<>();
    private final AtomicLong numTasks = new AtomicLong();
    private final AtomicLong totalTime = new AtomicLong();

    public KuugaThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public KuugaThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    public KuugaThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
    }

    public KuugaThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        // System.out.println("-------------------线程池线程任务执行开始-------------------------->" + t.getName());
        super.beforeExecute(t, r);

        log.info(String.format("Thread %s: start %s", t, r));
        startTime.set(System.currentTimeMillis());
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        // System.out.println("-------------------线程池线程任务执行结束--------------------------" + Thread.currentThread().getName());
        try {
            long endTime = System.currentTimeMillis();
            long taskTime = endTime - startTime.get();
            numTasks.incrementAndGet();
            log.info(String.format("Thread %s: end %s, time=%dms", Thread.currentThread(), r, taskTime));
        } finally {
            super.afterExecute(r, t);
        }
    }

    @Override
    protected void terminated() {
        // System.out.println("---------------------线程池Executor终止---------------------------");
        try {
            log.info(String.format("Terminated: avg time=%dms", totalTime.get() / numTasks.get()));
        } finally {
            super.terminated();
        }
    }

}
