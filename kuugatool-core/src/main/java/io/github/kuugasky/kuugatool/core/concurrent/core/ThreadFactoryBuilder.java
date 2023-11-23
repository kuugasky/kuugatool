package io.github.kuugasky.kuugatool.core.concurrent.core;

import io.github.kuugasky.kuugatool.core.string.StringUtil;

import java.io.Serial;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 建造者模式-ThreadFactory线程工厂创建器<br>
 * 参考：Guava的ThreadFactoryBuilder
 *
 * @author looly
 * @since 4.1.9
 */
public class ThreadFactoryBuilder implements Builder<ThreadFactory> {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 用于线程创建的线程工厂类
     */
    private ThreadFactory threadFactory;
    /**
     * 线程名的前缀
     */
    private String namePrefix;
    /**
     * 是否守护线程，默认false
     */
    private Boolean daemon;
    /**
     * 线程优先级
     */
    private Integer priority;
    /**
     * 未捕获异常处理器
     */
    private UncaughtExceptionHandler uncaughtExceptionHandler;

    /**
     * 创建{@link ThreadFactoryBuilder}
     *
     * @return {@link ThreadFactoryBuilder}
     */
    public static ThreadFactoryBuilder create() {
        return new ThreadFactoryBuilder();
    }

    /**
     * 设置用于创建基础线程的线程工厂
     *
     * @param threadFactory 用于创建基础线程的线程工厂
     * @return this
     */
    public ThreadFactoryBuilder setThreadFactory(ThreadFactory threadFactory) {
        this.threadFactory = threadFactory;
        return this;
    }

    /**
     * 设置线程名前缀，例如设置前缀为kuugatool-thread-，则线程名为kuugatool-thread-1之类。
     *
     * @param namePrefix 线程名前缀
     * @return this
     */
    public ThreadFactoryBuilder setNamePrefix(String namePrefix) {
        this.namePrefix = namePrefix;
        return this;
    }

    /**
     * 设置是否守护线程
     *
     * @param daemon 是否守护线程
     * @return this
     */
    public ThreadFactoryBuilder setDaemon(boolean daemon) {
        this.daemon = daemon;
        return this;
    }

    /**
     * 设置线程优先级
     *
     * @param priority 优先级
     * @return this
     * @see Thread#MIN_PRIORITY 线程最小优先级
     * @see Thread#NORM_PRIORITY 线程标准优先级
     * @see Thread#MAX_PRIORITY 线程最大优先级
     */
    public ThreadFactoryBuilder setPriority(int priority) {
        if (priority < Thread.MIN_PRIORITY) {
            throw new IllegalArgumentException(StringUtil.format("Thread priority ({}) must be >= {}", priority, Thread.MIN_PRIORITY));
        }
        if (priority > Thread.MAX_PRIORITY) {
            throw new IllegalArgumentException(StringUtil.format("Thread priority ({}) must be <= {}", priority, Thread.MAX_PRIORITY));
        }
        this.priority = priority;
        return this;
    }

    /**
     * 设置未捕获异常的处理方式
     *
     * @param uncaughtExceptionHandler {@link UncaughtExceptionHandler}
     * @return this
     */
    public ThreadFactoryBuilder setUncaughtExceptionHandler(UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.uncaughtExceptionHandler = uncaughtExceptionHandler;
        return this;
    }

    /**
     * 构建{@link ThreadFactory}
     *
     * @return {@link ThreadFactory}
     */
    @Override
    public ThreadFactory build() {
        return build(this);
    }

    /**
     * 构建线程工厂
     *
     * @param builder {@link ThreadFactoryBuilder} 线程工厂建造者
     * @return {@link ThreadFactory} 线程工厂
     */
    private static ThreadFactory build(ThreadFactoryBuilder builder) {
        ThreadFactory defaultThreadFactory = Executors.defaultThreadFactory();

        final ThreadFactory backingThreadFactory = (null != builder.threadFactory) ? builder.threadFactory : defaultThreadFactory;
        final String namePrefix = builder.namePrefix;
        final Boolean daemon = builder.daemon;
        final Integer priority = builder.priority;
        final UncaughtExceptionHandler handler = builder.uncaughtExceptionHandler;
        final AtomicLong count = (null == namePrefix) ? null : new AtomicLong();

        // 返回自定义构建的线程工厂
        return runnable -> {
            final Thread thread = backingThreadFactory.newThread(runnable);
            if (null != namePrefix) {
                // 线程名
                thread.setName(namePrefix + "-" + count.getAndIncrement());
            }
            if (null != daemon) {
                // 是否守护线程
                thread.setDaemon(daemon);
            }
            if (null != priority) {
                // 线程优先级
                thread.setPriority(priority);
            }
            if (null != handler) {
                // 未捕获异常处理程序
                thread.setUncaughtExceptionHandler(handler);
            }
            return thread;
        };
    }

}
