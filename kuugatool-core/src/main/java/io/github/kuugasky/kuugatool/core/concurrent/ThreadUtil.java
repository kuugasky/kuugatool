package io.github.kuugasky.kuugatool.core.concurrent;

import io.github.kuugasky.kuugatool.core.concurrent.core.NamedThreadFactory;
import io.github.kuugasky.kuugatool.core.concurrent.core.ThreadFactoryBuilder;
import io.github.kuugasky.kuugatool.core.concurrent.daemon.DaemonThread;
import io.github.kuugasky.kuugatool.core.concurrent.high.GlobalThreadPool;
import io.github.kuugasky.kuugatool.core.concurrent.high.HighConcurrentResult;
import io.github.kuugasky.kuugatool.core.concurrent.high.HighConcurrentTester;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.*;

/**
 * 线程池工具
 *
 * @author kuuga
 */
public class ThreadUtil {

    /**
     * 定义私有构造函数来屏蔽这个隐式公有构造函数
     */
    private ThreadUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    // ================================================================================================================
    // CountDownLatch
    // ================================================================================================================

    /**
     * 新建一个CountDownLatch，一个同步辅助类，在完成一组正在其他线程中执行的操作之前，它允许一个或多个线程一直等待。
     * 必须执行对应count次数的 this.countDown()，待 this.getCount() == 0，才会继续执行主线程
     * {@link CountDownLatchUse#build(int)}
     *
     * @param count 闭锁数量
     * @return CountDownLatch
     */
    public static CountDownLatch newCountDownLatch(int count) {
        return new CountDownLatch(count);
    }

    // ================================================================================================================
    // 线程池执行任务
    // ================================================================================================================

    /**
     * 直接在公共线程池中执行线程
     *
     * @param runnable 可运行对象
     */
    public static void execute(Runnable runnable) {
        GlobalThreadPool.execute(runnable);
    }

    /**
     * 执行异步方法
     *
     * @param runnable 需要执行的方法体
     * @param isDaemon 是否守护线程。守护线程会在主线程结束后自动结束
     * @return 执行的方法体
     */
    @SuppressWarnings("all")
    public static Runnable execAsync(Runnable runnable, boolean isDaemon) {
        Thread thread = new Thread(runnable);
        thread.setDaemon(isDaemon);
        thread.start();
        return runnable;
    }

    /**
     * 执行有返回值的异步方法<br>
     * Future代表一个异步执行的操作，通过get()方法可以获得操作的结果，如果异步操作还没有完成，则，get()会使当前线程阻塞
     *
     * @param <T>  回调对象类型
     * @param task {@link Callable}
     * @return Future
     */
    public static <T> Future<T> execAsync(Callable<T> task) {
        return GlobalThreadPool.submit(task);
    }

    /**
     * 执行有返回值的异步方法<br>
     * Future代表一个异步执行的操作，通过get()方法可以获得操作的结果，如果异步操作还没有完成，则，get()会使当前线程阻塞
     *
     * @param runnable 可运行对象
     * @return {@link Future}
     * @since 3.0.5
     */
    public static Future<?> execAsync(Runnable runnable) {
        return GlobalThreadPool.submit(runnable);
    }

    // ================================================================================================================
    // 创建线程
    // ================================================================================================================

    /**
     * 创建新线程，非守护线程，正常优先级，线程组与当前线程的线程组一致
     *
     * @param runnable {@link Runnable}
     * @param name     线程名
     * @return {@link Thread}
     * @since 3.1.2
     */
    public static Thread newThread(Runnable runnable, String name) {
        final Thread thread = newThread(runnable, name, false);
        if (thread.getPriority() != Thread.NORM_PRIORITY) {
            thread.setPriority(Thread.NORM_PRIORITY);
        }
        return thread;
    }

    /**
     * 创建新线程
     *
     * @param runnable {@link Runnable}
     * @param name     线程名
     * @param isDaemon 是否守护线程,非守护线程时，main方法主线程结束才会执行，单元测试无效
     * @return {@link Thread}
     * @since 4.1.2
     */
    @SuppressWarnings("all")
    public static Thread newThread(Runnable runnable, String name, boolean isDaemon) {
        final Thread t = new Thread(null, runnable, name);
        t.setDaemon(isDaemon);
        return t;
    }

    // ================================================================================================================
    // 线程sleep挂起
    // ================================================================================================================

    /**
     * 挂起当前线程
     *
     * @param timeout  挂起的时长
     * @param timeUnit 时长单位
     * @return 被中断返回false，否则true
     */
    public static boolean sleep(Number timeout, TimeUnit timeUnit) {
        try {
            timeUnit.sleep(timeout.longValue());
        } catch (InterruptedException e) {
            return false;
        }
        return true;
    }

    /**
     * 挂起当前线程
     *
     * @param millis 挂起的毫秒数
     * @return 被中断返回false，否则true
     */
    public static boolean sleep(Number millis) {
        if (millis == null) {
            return true;
        }
        return sleep(millis.longValue());
    }

    /**
     * 挂起当前线程
     *
     * @param millis 挂起的毫秒数
     * @return 被中断返回false，否则true
     * @since 5.3.2
     */
    public static boolean sleep(long millis) {
        if (millis > 0) {
            try {
                Thread.sleep(millis);
            } catch (InterruptedException e) {
                return false;
            }
        }
        return true;
    }

    /**
     * 考虑{@link Thread#sleep(long)}方法有可能时间不足给定毫秒数，此方法保证sleep时间不小于给定的毫秒数
     *
     * @param millis 给定的sleep时间
     * @return 被中断返回false，否则true
     * @see ThreadUtil#sleep(Number)
     */
    public static boolean safeSleep(Number millis) {
        if (millis == null) {
            return true;
        }

        return safeSleep(millis.longValue());
    }

    /**
     * 考虑{@link Thread#sleep(long)}方法有可能时间不足给定毫秒数，此方法保证sleep时间不小于给定的毫秒数
     *
     * @param millis 给定的sleep时间
     * @return 被中断返回false，否则true
     * @see ThreadUtil#sleep(Number)
     * @since 5.3.2
     */
    public static boolean safeSleep(long millis) {
        long done = 0;
        long before;
        long spendTime;
        while (done >= 0 && done < millis) {
            before = System.currentTimeMillis();
            if (!sleep(millis - done)) {
                return false;
            }
            spendTime = System.currentTimeMillis() - before;
            if (spendTime <= 0) {
                // Sleep花费时间为0或者负数，说明系统时间被拨动
                break;
            }
            done += spendTime;
        }
        return true;
    }

    // ================================================================================================================
    // 线程堆栈
    // ================================================================================================================

    /**
     * @return 获得堆栈列表
     */
    public static StackTraceElement[] getStackTrace() {
        return Thread.currentThread().getStackTrace();
    }

    /**
     * 获得堆栈项
     *
     * @param i 第几个堆栈项
     * @return 堆栈项
     */
    public static StackTraceElement getStackTraceElement(int i) {
        StackTraceElement[] stackTrace = getStackTrace();
        if (i < 0) {
            i += stackTrace.length;
        }
        return stackTrace[i];
    }

    // ================================================================================================================
    // 创建本地线程对象ThreadLocal
    // ================================================================================================================

    /**
     * 创建本地线程对象
     *
     * @param <T>           持有对象类型
     * @param isInheritable 是否为子线程提供从父线程那里继承的值
     * @return 本地线程
     */
    public static <T> ThreadLocal<T> createThreadLocal(boolean isInheritable) {
        if (isInheritable) {
            return new InheritableThreadLocal<>();
        } else {
            return new ThreadLocal<>();
        }
    }

    // ================================================================================================================
    // 创建ThreadFactoryBuilder
    // ================================================================================================================

    /**
     * 创建ThreadFactoryBuilder
     *
     * @return ThreadFactoryBuilder
     * @see ThreadFactoryBuilder#build()
     * @since 4.1.13
     */
    public static ThreadFactoryBuilder createThreadFactoryBuilder() {
        return ThreadFactoryBuilder.create();
    }

    // ================================================================================================================
    // 结束线程
    // ================================================================================================================

    /**
     * 结束线程，调用此方法后，线程将抛出 {@link InterruptedException}异常
     *
     * @param thread 线程
     * @param isJoin 是否等待结束
     */
    public static void interrupt(Thread thread, boolean isJoin) {
        if (null != thread && !thread.isInterrupted()) {
            thread.interrupt();
            if (isJoin) {
                waitForDie(thread);
            }
        }
    }

    // ================================================================================================================
    // 等待线程结束
    // ================================================================================================================

    /**
     * 等待当前线程结束. 调用 {@link Thread#join()} 并忽略 {@link InterruptedException}
     */
    public static void waitForDie() {
        waitForDie(Thread.currentThread());
    }

    /**
     * 等待线程结束. 调用 {@link Thread#join()} 并忽略 {@link InterruptedException}
     *
     * @param thread 线程
     */
    public static void waitForDie(Thread thread) {
        if (null == thread) {
            return;
        }

        boolean dead = false;
        do {
            try {
                thread.join();
                dead = true;
            } catch (InterruptedException e) {
                // ignore
            }
        } while (!dead);
    }

    // ================================================================================================================
    // 获取线程
    // ================================================================================================================

    /**
     * 获取JVM中与当前线程同组的所有线程<br>
     *
     * @return 线程对象数组
     */
    public static Thread[] getThreads() {
        // 当前线程
        Thread thread = Thread.currentThread();
        // 当前线程组
        ThreadGroup threadGroup = thread.getThreadGroup();
        // 当前线程父级线程组
        ThreadGroup parent = threadGroup.getParent();
        return getThreads(parent);
    }

    /**
     * 获取JVM中与当前线程同组的所有线程<br>
     * 使用数组二次拷贝方式，防止在线程列表获取过程中线程终止<br>
     * from Voovan
     *
     * @param group 线程组
     * @return 线程对象数组
     */
    public static Thread[] getThreads(ThreadGroup group) {
        final Thread[] slackList = new Thread[group.activeCount() * 2];
        final int actualSize = group.enumerate(slackList);
        final Thread[] result = new Thread[actualSize];
        System.arraycopy(slackList, 0, result, 0, actualSize);
        return result;
    }

    /**
     * 获取进程的主线程<br>
     * from Voovan
     *
     * @return 进程的主线程
     */
    public static Thread getMainThread() {
        for (Thread thread : getThreads()) {
            if (thread.getId() == 1) {
                return thread;
            }
        }
        return null;
    }

    // /**
    //  * 获取当前线程的线程组
    //  *
    //  * @return 线程组
    //  * @since 3.1.2
    //  */
    // @Deprecated(since = "17", forRemoval = true)
    // public static ThreadGroup currentThreadGroup() {
    //     final SecurityManager s = System.getSecurityManager();
    //     return (null != s) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
    // }

    // ================================================================================================================
    // 创建线程工厂
    // ================================================================================================================

    /**
     * 创建线程工厂
     *
     * @param prefix   线程名前缀
     * @param isDaemon 是否守护线程
     * @return {@link ThreadFactory}
     * @since 4.0.0
     */
    public static ThreadFactory newNamedThreadFactory(String prefix, boolean isDaemon) {
        return new NamedThreadFactory(prefix, isDaemon);
    }

    /**
     * 创建线程工厂
     *
     * @param prefix      线程名前缀
     * @param threadGroup 线程组，可以为null
     * @param isDaemon    是否守护线程
     * @return {@link ThreadFactory}
     * @since 4.0.0
     */
    public static ThreadFactory newNamedThreadFactory(String prefix, ThreadGroup threadGroup, boolean isDaemon) {
        return new NamedThreadFactory(prefix, threadGroup, isDaemon);
    }

    /**
     * 创建线程工厂
     *
     * @param prefix      线程名前缀
     * @param threadGroup 线程组，可以为null
     * @param isDaemon    是否守护线程
     * @param handler     未捕获异常处理
     * @return {@link ThreadFactory}
     * @since 4.0.0
     */
    public static ThreadFactory newNamedThreadFactory(String prefix, ThreadGroup threadGroup, boolean isDaemon, UncaughtExceptionHandler handler) {
        return new NamedThreadFactory(prefix, threadGroup, isDaemon, handler);
    }

    // ================================================================================================================
    // 线程阻塞
    // ================================================================================================================

    /**
     * 阻塞当前线程，保证在main方法中执行不被退出
     * <p>
     * 推荐使用: {@link DaemonThread#await(long, TimeUnit)}
     *
     * @param obj 对象所在线程
     * @since 4.5.6
     */
    @SuppressWarnings("SynchronizationOnLocalVariableOrMethodParameter")
    public static void sync(Object obj) {
        synchronized (obj) {
            try {
                obj.wait();
            } catch (InterruptedException e) {
                // ignore
            }
        }
    }

    // ================================================================================================================
    // 并发
    // ================================================================================================================

    /**
     * 并发测试<br>
     * 此方法用于测试多线程下执行某些逻辑的并发性能<br>
     * 调用此方法会导致当前线程阻塞。
     *
     * @param threadSize 并发线程数
     * @param runnable   执行的逻辑实现
     * @return {@link HighConcurrentResult}
     * @since 4.5.8
     */
    public static HighConcurrentResult concurrencyTest(int threadSize, Runnable runnable) {
        HighConcurrentTester highConcurrentTester = new HighConcurrentTester(threadSize);
        return highConcurrentTester.test(runnable);
    }

}
