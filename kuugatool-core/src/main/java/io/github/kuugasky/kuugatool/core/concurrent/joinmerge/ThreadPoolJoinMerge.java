package io.github.kuugasky.kuugatool.core.concurrent.joinmerge;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.github.kuugasky.kuugatool.core.concurrent.core.KuugaThreadPoolExecutor;

import java.util.List;
import java.util.concurrent.*;

/**
 * 线程池【使用静态内部类实现线程池单例】
 * 链表结构有界阻塞线程池
 *
 * @author kuuga
 */
public final class ThreadPoolJoinMerge {

    /**
     * 私有构造函数
     */
    private ThreadPoolJoinMerge() {
    }

    /**
     * 可变尺寸的线程池
     */
    private static ExecutorService executorService = null;

    /**
     * 枚举实现单例
     */
    private enum ThreadPoolSingleton {
        /**
         * 线程池单例枚举
         */
        INSTANCE;

        private final ExecutorService threadPoolService;

        public static final String THREAD_FACTORY_NAME = "KUUGA-THREAD-POOL-JOIN-MERGE-%d";

        ThreadPoolSingleton() {
            // 定义线程工厂名称
            final ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat(THREAD_FACTORY_NAME).build();
            // 定义工作队列：有边界阻塞队列，边界值为【Integer.MAX_VALUE】
            final LinkedBlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
            // 中止政策
            ThreadPoolExecutor.AbortPolicy abortPolicy = new ThreadPoolExecutor.AbortPolicy();

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
            synchronized (ThreadPoolJoinMerge.class) {
                if (executorService == null || executorService.isShutdown() || executorService.isTerminated()) {
                    executorService = ThreadPoolSingleton.INSTANCE.getSingleton();
                }
            }
        }
        return executorService;
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
     * 调用所有任务
     * 注：循环创建任务过程中，需要将任务add到tasks，同时需要submit到线程池
     *
     * @param tasks 提交的任务集合
     * @param <T>   入参及出参的数据类型
     */
    public static <T> void invokeAllTask(List<Callable<T>> tasks) {
        try {
            getPool().invokeAll(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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