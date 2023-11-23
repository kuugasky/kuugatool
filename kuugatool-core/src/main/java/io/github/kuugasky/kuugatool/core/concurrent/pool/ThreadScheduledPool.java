package io.github.kuugasky.kuugatool.core.concurrent.pool;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * 周期性线程池
 *
 * @author kuuga
 */
public final class ThreadScheduledPool {

    /**
     * 可变尺寸的线程池
     */
    private static ScheduledExecutorService scheduledExecutorService = null;

    /**
     * 静态内部类【利用了classloader的机制来保证初始化instance时只有一个线程：懒加载变种】
     * private static final ExecutorService INSTANCE = Executors.newScheduledThreadPool();
     */
    private static class ThreadPoolHolder {
        /**
         * 定义线程名称
         */
        private static final ThreadFactory NAMED_THREAD_FACTORY = new ThreadFactoryBuilder().setNameFormat("KUUGA-THREAD-SCHEDULED-POOL-%d").build();

        private static final ScheduledExecutorService INSTANCE = Executors.newScheduledThreadPool(5, NAMED_THREAD_FACTORY);
    }

    /**
     * 单例
     */
    private static ScheduledExecutorService getPool() {
        if (null == scheduledExecutorService || scheduledExecutorService.isShutdown() || scheduledExecutorService.isTerminated()) {
            synchronized (ThreadScheduledPool.class) {
                if (scheduledExecutorService == null || scheduledExecutorService.isShutdown() || scheduledExecutorService.isTerminated()) {
                    scheduledExecutorService = ThreadPoolHolder.INSTANCE;
                }
            }
        }
        return scheduledExecutorService;
    }

    /**
     * 延迟initialDelay后，以period为周期的定时任务
     * 注：
     * 以上一个任务开始的时间计时，period时间过去后，检测上一个任务是否执行完毕，如果上一个任务执行完毕，则当前任务立即执行，
     * 如果上一个任务没有执行完毕，则需要等上一个任务执行完毕后立即执行。
     *
     * @param command      任务
     * @param initialDelay 初始延迟时间
     * @param period       周期
     * @param unit         时间单位
     */
    public static void scheduleAtFixedRate(Runnable command,
                                           long initialDelay,
                                           long period,
                                           TimeUnit unit) {
        getPool().scheduleAtFixedRate(command, initialDelay, period, unit);
    }

    /**
     * 延时initialDelay后，每隔delay执行一次的周期任务
     * 注：
     * 以上一个任务结束时开始计时，delay时间过去后，立即执行。
     * <p>
     * 都不用get，就已经按周期运行了
     *
     * @param command      任务
     * @param initialDelay 初始延迟时间
     * @param delay        任务延迟间隔
     * @param unit         时间单位
     * @return ScheduledFuture
     */
    public static ScheduledFuture<?> scheduleWithFixedDelay(Runnable command,
                                                            long initialDelay,
                                                            long delay,
                                                            TimeUnit unit) {
        return getPool().scheduleWithFixedDelay(command, initialDelay, delay, unit);
    }

    /**
     * 延迟定时任务
     *
     * @param command 任务
     * @param delay   初始延迟时间
     * @param unit    时间单位
     */
    public static void schedule(Runnable command, long delay, TimeUnit unit) {
        getPool().schedule(command, delay, unit);
    }

    /**
     * 延迟定时任务
     * 注：都不用get，就已经按周期运行了
     *
     * @param callable 可调用的任务
     * @param delay    初始延迟时间
     * @param unit     时间单位
     * @return ScheduledFuture
     */
    public static <V> ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit) {
        return getPool().schedule(callable, delay, unit);
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