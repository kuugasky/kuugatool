package io.github.kuugasky.kuugatool.core.concurrent.core;

import io.github.kuugasky.kuugatool.core.object.ObjectUtil;

import java.io.Serial;
import java.util.Objects;
import java.util.concurrent.*;

/**
 * 建造者模式-线程池构造器
 * <p>
 * {@link ThreadPoolExecutor} 建造者
 *
 * <pre>
 *     1. 如果队列任务数 < corePoolSize -> 任务放入立即执行
 *     2. 如果队列任务数 > corePoolSize -> 队列未满则继续插入队列；队列满了后任务加入maxPoolSize执行，超出部分触发拒绝策略
 * </pre>
 *
 * @author looly
 * @since 4.1.9
 */
public class ExecutorBuilder implements Builder<ThreadPoolExecutor> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 默认的等待队列容量
     */
    public static final int DEFAULT_QUEUE_CAPACITY = 1024;

    /**
     * 初始池大小 - 核心线程数
     */
    private int corePoolSize;
    /**
     * 最大池大小（允许同时执行的最大线程数）- 核心线程数
     */
    private int maxPoolSize = Integer.MAX_VALUE;
    /**
     * 线程存活时间，60s转纳秒
     * <p>
     * 大于corePoolSize且小于maxPoolSize部分的空闲线程保留时长
     */
    private long keepAliveTime = TimeUnit.SECONDS.toNanos(60);
    /**
     * 工作队列，用于存放未执行的线程
     */
    private BlockingQueue<Runnable> workQueue;
    /**
     * 线程工厂，用于自定义线程创建
     */
    private ThreadFactory threadFactory;
    /**
     * 当线程阻塞（block）时的异常处理器，所谓线程阻塞即线程池和等待队列已满，无法处理线程时采取的策略
     */
    private RejectedExecutionHandler handler;
    /**
     * 线程执行超时后是否回收线程
     */
    private Boolean allowCoreThreadTimeOut;

    /**
     * 设置初始池大小，默认0
     *
     * @param corePoolSize 核心线程数
     * @return this
     */
    public ExecutorBuilder setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
        return this;
    }

    /**
     * 设置最大池大小（允许同时执行的最大线程数）
     *
     * @param maxPoolSize 最大池大小（允许同时执行的最大线程数）
     * @return this
     */
    public ExecutorBuilder setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
        return this;
    }

    /**
     * 设置线程存活时间，即大于corePoolSize且小于maxPoolSize部分的空闲线程保留时长
     *
     * @param keepAliveTime 空闲线程存活时间
     * @param unit          单位
     * @return this
     */
    public ExecutorBuilder setKeepAliveTime(long keepAliveTime, TimeUnit unit) {
        return setKeepAliveTime(unit.toNanos(keepAliveTime));
    }

    /**
     * 设置线程存活时间，即大于corePoolSize且小于maxPoolSize部分的空闲线程保留时长，单位纳秒
     *
     * @param keepAliveTime 空闲线程存活时间，单位纳秒
     * @return this
     */
    public ExecutorBuilder setKeepAliveTime(long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
        return this;
    }

    /**
     * 设置队列，用于存在未执行的线程<br>
     * 可选队列有：
     *
     * <pre>
     * 1. {@link SynchronousQueue} 同步队列：它将任务直接提交给线程而不保持它们。当运行线程小于maxPoolSize时会创建新线程，否则触发异常策略
     * 2. {@link LinkedBlockingQueue} 链式阻塞队列（容量为{@link Integer#MAX_VALUE}）：当运行线程大于corePoolSize时始终放入此队列，此时maxPoolSize无效。
     *      当构造LinkedBlockingQueue对象时传入参数(小于Integer#MAX_VALUE时)，变为有界队列，corePoolSize队列满时，
     *      运行线程小于maxPoolSize时会创建新线程，直到maxPoolSize满了，maxPoolSize和任务队列满了后新任务加入会触发异常策略
     * 3. {@link ArrayBlockingQueue} 数组阻塞队列（有界队列）：相对无界队列有利于控制队列大小，队列满时，运行线程大于corePoolSize且小于maxPoolSize时会创建新线程，否则触发异常策略
     * </pre>
     *
     * @param workQueue 队列
     * @return this
     */
    public ExecutorBuilder setWorkQueue(BlockingQueue<Runnable> workQueue) {
        this.workQueue = workQueue;
        return this;
    }

    /**
     * 使用{@link ArrayBlockingQueue} 做为等待队列<br>
     * 有界队列，相对无界队列有利于控制队列大小，队列满时，运行线程小于maxPoolSize时会创建新线程，否则触发异常策略
     * <p>
     * 注意：有界队列时，当corePoolSize满了，队列也满了，继续入任务，导致maxPoolSize也满了后，将会触发异常策略
     * <p>
     * 可以采用{@link KuugaRejectedExecutionHandler}，将益处部分任务放入无界队列，有界队列处理完任务后继续从无界队列提取任务放入线程执行。
     *
     * @param capacity 队列容量
     * @return this
     * @since 5.1.4
     */
    public ExecutorBuilder useArrayBlockingQueue(int capacity) {
        return setWorkQueue(new ArrayBlockingQueue<>(capacity));
    }

    /**
     * 使用{@link SynchronousQueue} 同步队列做为等待队列（非公平策略）<br>
     * 它将任务直接提交给线程而不保持它们。当运行线程小于maxPoolSize时会创建新线程，否则触发异常策略
     *
     * @return this
     * @since 4.1.11
     */
    public ExecutorBuilder useSynchronousQueue() {
        return useSynchronousQueue(false);
    }

    /**
     * 使用{@link SynchronousQueue} 同步队列做为等待队列<br>
     * 它将任务直接提交给线程而不保持它们。当运行线程小于maxPoolSize时会创建新线程，否则触发异常策略
     *
     * @param fair 是否使用公平访问策略
     * @return this
     * @since 4.5.0
     */
    public ExecutorBuilder useSynchronousQueue(boolean fair) {
        return setWorkQueue(new SynchronousQueue<>(fair));
    }

    /**
     * 设置线程工厂，用于自定义线程创建
     *
     * @param threadFactory 线程工厂
     * @return this
     * @see ThreadFactoryBuilder
     */
    public ExecutorBuilder setThreadFactory(ThreadFactory threadFactory) {
        this.threadFactory = threadFactory;
        return this;
    }

    /**
     * 设置当线程阻塞（block）时的异常处理器，所谓线程阻塞即线程池和等待队列已满，无法处理线程时采取的策略
     * <p>
     * 此处可以使用JDK预定义的几种策略，见{@link RejectPolicy}枚举
     *
     * @param handler {@link RejectedExecutionHandler}
     * @return this
     * @see RejectPolicy
     */
    public ExecutorBuilder setHandler(RejectedExecutionHandler handler) {
        this.handler = handler;
        return this;
    }

    /**
     * 设置线程执行超时后是否回收线程
     *
     * @param allowCoreThreadTimeOut 线程执行超时后是否回收线程
     * @return this
     */
    public ExecutorBuilder setAllowCoreThreadTimeOut(boolean allowCoreThreadTimeOut) {
        this.allowCoreThreadTimeOut = allowCoreThreadTimeOut;
        return this;
    }

    /**
     * 创建ExecutorBuilder，开始构建
     *
     * @return {@link ExecutorBuilder}
     */
    public static ExecutorBuilder create() {
        return new ExecutorBuilder();
    }

    /**
     * 构建ThreadPoolExecutor
     */
    @Override
    public ThreadPoolExecutor build() {
        return build(this);
    }

    /**
     * 创建有回收关闭功能的ExecutorService
     *
     * @return 创建有回收关闭功能的ExecutorService
     * @since 5.1.4
     */
    public ExecutorService buildFinalizable() {
        return new FinalizableDelegatedExecutorService(build());
    }

    /**
     * 构建ThreadPoolExecutor
     *
     * @param builder {@link ExecutorBuilder}
     * @return {@link ThreadPoolExecutor}
     */
    private static ThreadPoolExecutor build(ExecutorBuilder builder) {
        final int corePoolSize = builder.corePoolSize;
        final int maxPoolSize = builder.maxPoolSize;
        final long keepAliveTime = builder.keepAliveTime;
        final BlockingQueue<Runnable> workQueue;

        workQueue = Objects.requireNonNullElseGet(
                builder.workQueue,
                // corePoolSize为0则要使用SynchronousQueue避免无限阻塞
                () -> (corePoolSize <= 0) ? new SynchronousQueue<>() : new LinkedBlockingQueue<>(DEFAULT_QUEUE_CAPACITY));

        final ThreadFactory threadFactory = (null != builder.threadFactory) ? builder.threadFactory : Executors.defaultThreadFactory();
        // 被拒绝的任务的处理程序，它抛出RejectedExecutionException异常。这是ThreadPoolExecutor和ScheduledThreadPoolExecutor的默认处理程序。
        ThreadPoolExecutor.AbortPolicy abortPolicy = new ThreadPoolExecutor.AbortPolicy();
        RejectedExecutionHandler handler = ObjectUtil.getOrElse(builder.handler, abortPolicy);

        final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maxPoolSize, keepAliveTime, TimeUnit.NANOSECONDS, workQueue, threadFactory, handler);

        if (null != builder.allowCoreThreadTimeOut) {
            threadPoolExecutor.allowCoreThreadTimeOut(builder.allowCoreThreadTimeOut);
        }
        return threadPoolExecutor;
    }

}
