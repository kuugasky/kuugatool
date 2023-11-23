package io.github.kuugasky.kuugatool.core.concurrent;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.github.kuugasky.kuugatool.core.concurrent.core.ExecutorBuilder;
import io.github.kuugasky.kuugatool.core.concurrent.core.KuugaRejectedExecutionHandler;
import io.github.kuugasky.kuugatool.core.concurrent.core.KuugaThreadPoolExecutor;
import io.github.kuugasky.kuugatool.core.concurrent.high.GlobalThreadPool;
import io.github.kuugasky.kuugatool.core.string.StringUtil;

import java.util.Locale;
import java.util.concurrent.*;

/**
 * ExecutorServiceUtil
 *
 * @author kuuga
 * @since 2022/8/9 15:47
 */
public class ThreadPoolUtil {

    private static final int CORE_POOL_SIZE = 10;
    private static final int MAXIMUM_POOL_SIZE = 20;
    private static final long KEEP_ALIVE_TIME = 60L;
    private static final TimeUnit UNIT = TimeUnit.SECONDS;

    public static final String THREAD_FACTORY_NAME = "KUUGA-THREAD-POOL-CUSTOM-%d";

    /**
     * 自定义线程池
     */
    public static ExecutorService build() {
        return build(StringUtil.EMPTY);
    }

    /**
     * 自定义线程池
     *
     * @param threadName 线程池名称
     */
    public static ExecutorService build(String threadName) {
        return build(threadName, CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, UNIT);
    }

    /**
     * 自定义线程池
     *
     * @param threadName      线程池名称
     * @param corePoolSize    核心线程数-在池中保留的线程数，如果它们是空闲的，除非设置了{@code allowCoreThreadTimeOut}
     * @param maximumPoolSize 最大线程数
     * @param keepAliveTime   线程持续时间-当线程数大于核心数，小于最大线程数，超过核心数部分的线程为多余空闲线程，多余线程在持续时间内没接到新的任务将进行回收。
     * @param unit            空闲持续时间单位-参数{@code keepAliveTime}的时间单位
     */
    public static ExecutorService build(String threadName, int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit) {
        // 定义线程工厂名称
        final ThreadFactory threadFactory;
        if (StringUtil.hasText(threadName)) {
            threadFactory = new ThreadFactoryBuilder().setNameFormat(threadName + "-%d").build();
        } else {
            threadFactory = new ThreadFactoryBuilder().setNameFormat(THREAD_FACTORY_NAME).build();
        }
        // 定义工作队列：有边界阻塞队列，边界值为【20】
        final LinkedBlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(20);
        // 拒绝政策
        KuugaRejectedExecutionHandler abortPolicy = new KuugaRejectedExecutionHandler();
        return build(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, abortPolicy);
    }

    /**
     * 自定义线程池
     *
     * @param corePoolSize    核心线程数-在池中保留的线程数，如果它们是空闲的，除非设置了{@code allowCoreThreadTimeOut}
     * @param maximumPoolSize 最大线程数
     * @param keepAliveTime   线程持续时间-当线程数大于核心数，小于最大线程数，超过核心数部分的线程为多余空闲线程，多余线程在持续时间内没接到新的任务将进行回收。
     * @param unit            空闲持续时间单位-参数{@code keepAliveTime}的时间单位
     * @param workQueue       工作队列-在任务执行之前用于保存任务的执行队列。这个队列将只保存{@code Runnable}，由{@code execute}方法提交的任务。
     * @param threadFactory   线程工厂-该工厂使用时的执行者，创建一个新线程
     * @param handler         处理程序-执行被阻塞时使用的处理程序，因为线程边界和队列容量已经达到
     * @return ExecutorService
     * IllegalArgumentException 如果以下情况之一成立:<br>
     * {@code corePoolSize < 0}<br>
     * {@code keepAliveTime < 0}<br>
     * {@code maximumPoolSize <= 0}<br>
     * {@code maximumPoolSize < corePoolSize}
     */
    public static ExecutorService build(
            int corePoolSize, int maximumPoolSize, long keepAliveTime,
            TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory,
            RejectedExecutionHandler handler
    ) {
        return new KuugaThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
    }

    /**
     * 自定义线程池
     * <p>
     * 慎用：非必要尽量使用公共线程池
     * </p>
     *
     * <p>
     * - workQueue工作队列采用有边界阻塞队列{@link LinkedBlockingQueue} 边界值20
     * - abortPolicy拒绝策略采用自定义KuugaRejectedExecutionHandler进行僚机线程动态填充workQueue队列，以使线程池同时执行任务数达到maximumPoolSize
     *
     * @param corePoolSize    核心线程数-在池中保留的线程数，如果它们是空闲的，除非设置了{@code allowCoreThreadTimeOut}
     * @param maximumPoolSize 最大线程数
     * @param keepAliveTime   线程持续时间-当线程数大于核心数，小于最大线程数，超过核心数部分的线程为多余空闲线程，多余线程在持续时间内没接到新的任务将进行回收。
     * @param unit            空闲持续时间单位-参数{@code keepAliveTime}的时间单位
     * @param threadPoolName  线程池名称不允许为空
     * @return ExecutorService
     */
    public static ExecutorService build(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, String threadPoolName) {
        if (StringUtil.isEmpty(threadPoolName)) {
            throw new RuntimeException("自定义线程池名称不允许为空");
        }
        String threadFactoryName = threadPoolName.toUpperCase(Locale.ROOT) + "-%d";
        // 定义线程工厂名称
        final ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat(threadFactoryName).build();
        // 定义工作队列：有边界阻塞队列，边界值为【20】
        final LinkedBlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(20);
        // 拒绝政策
        KuugaRejectedExecutionHandler abortPolicy = new KuugaRejectedExecutionHandler();
        return new KuugaThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, abortPolicy);
    }

    // ================================================================================================================
    // 创建线程池
    // ================================================================================================================

    /**
     * 获得一个新的线程池，默认的策略如下：
     * <pre>
     *    1. 初始线程数为 0
     *    2. 最大线程数为Integer.MAX_VALUE
     *    3. 使用SynchronousQueue
     *    4. 任务直接提交给线程而不保持它们
     * </pre>
     *
     * @return ExecutorService
     */
    public static ExecutorService newExecutor() {
        return ExecutorBuilder.create().useSynchronousQueue().build();
    }

    /**
     * 新建一个线程池，默认的策略如下：
     * <pre>
     *    1. 初始线程数为corePoolSize指定的大小
     *    2. 最大线程数限制-{@link Integer#MAX_VALUE}
     *    3. 默认使用LinkedBlockingQueue，默认队列大小为1024
     *    4. 当运行线程大于corePoolSize放入队列，队列满后抛出异常
     * </pre>
     *
     * @param corePoolSize 同时执行的线程数大小
     * @return ExecutorService
     */
    public static ExecutorService newExecutor(int corePoolSize) {
        ExecutorBuilder builder = ExecutorBuilder.create();
        if (corePoolSize > 0) {
            builder.setCorePoolSize(corePoolSize);
            // maxPoolSize用Integer.MAX不稳妥
            builder.setMaxPoolSize(corePoolSize + 10);
        }
        return builder.build();
    }

    /**
     * 获得一个新的线程池<br>
     * 如果maximumPoolSize &gt;= corePoolSize，在没有新任务加入的情况下，多出的线程将最多保留60s
     *
     * @param corePoolSize    初始线程池大小
     * @param maximumPoolSize 最大线程池大小
     * @return {@link ThreadPoolExecutor}
     */
    public static ThreadPoolExecutor newExecutor(int corePoolSize, int maximumPoolSize) {
        return ExecutorBuilder.create()
                .setCorePoolSize(corePoolSize)
                .setMaxPoolSize(maximumPoolSize)
                .build();
    }

    /**
     * 获得一个新的线程池，并指定最大任务队列大小<br>
     * 如果maximumPoolSize &gt;= corePoolSize，在没有新任务加入的情况下，多出的线程将最多保留60s
     *
     * @param corePoolSize     初始线程池大小
     * @param maximumPoolSize  最大线程池大小
     * @param maximumQueueSize 最大任务队列大小
     * @return {@link ThreadPoolExecutor}
     * @since 5.4.1
     */
    public static ExecutorService newExecutor(int corePoolSize, int maximumPoolSize, int maximumQueueSize) {
        return ExecutorBuilder.create()
                .setCorePoolSize(corePoolSize)
                .setMaxPoolSize(maximumPoolSize)
                .setWorkQueue(new LinkedBlockingQueue<>(maximumQueueSize))
                .build();
    }

    /**
     * 获得一个新的线程池，只有单个线程，策略如下：
     * <pre>
     *    1. 初始线程数为 1
     *    2. 最大线程数为 1
     *    3. 默认使用LinkedBlockingQueue，默认队列大小为1024
     *    4. 同时只允许一个线程工作，剩余放入队列等待，等待数超过1024报错
     * </pre>
     *
     * @return ExecutorService
     */
    public static ExecutorService newSingleExecutor() {
        return ExecutorBuilder.create()
                .setCorePoolSize(1)
                .setMaxPoolSize(1)
                .setKeepAliveTime(0)
                .buildFinalizable();
    }

    /**
     * 获得一个新的线程池<br>
     * 传入阻塞系数，线程池的大小计算公式为：CPU可用核心数 / (1 - 阻塞因子)<br>
     * Blocking Coefficient(阻塞系数) = 阻塞时间／（阻塞时间+使用CPU的时间）<br>
     * 计算密集型任务的阻塞系数为0，而IO密集型任务的阻塞系数则接近于1。
     * <p>
     * see: <a href="http://blog.csdn.net/partner4java/article/details/9417663">...</a>
     *
     * @param blockingCoefficient 阻塞系数，阻塞因子介于0~1之间的数，阻塞因子越大，线程池中的线程数越多。
     * @return {@link ThreadPoolExecutor}
     * @since 3.0.6
     */
    public static ThreadPoolExecutor newExecutorByBlockingCoefficient(float blockingCoefficient) {
        if (blockingCoefficient >= 1 || blockingCoefficient < 0) {
            throw new IllegalArgumentException("[blockingCoefficient] must between 0 and 1, or equals 0.");
        }

        // 最佳的线程数 = CPU可用核心数 / (1 - 阻塞系数)
        int poolSize = (int) (Runtime.getRuntime().availableProcessors() / (1 - blockingCoefficient));
        return ExecutorBuilder.create().setCorePoolSize(poolSize).setMaxPoolSize(poolSize).setKeepAliveTime(0L).build();
    }

    /**
     * 新建一个CompletionService，调用其submit方法可以异步执行多个任务，最后调用take方法按照完成的顺序获得其结果。<br>
     * 若未完成，则会阻塞
     *
     * @param <T> 回调对象类型
     * @return CompletionService
     */
    public static <T> CompletionService<T> newCompletionService() {
        return new ExecutorCompletionService<>(GlobalThreadPool.getExecutor());
    }

    /**
     * 新建一个CompletionService，调用其submit方法可以异步执行多个任务，最后调用take方法按照完成的顺序获得其结果。<br>
     * 若未完成，则会阻塞
     *
     * @param <T>      回调对象类型
     * @param executor 执行器 {@link ExecutorService}
     * @return CompletionService
     */
    public static <T> CompletionService<T> newCompletionService(ExecutorService executor) {
        return new ExecutorCompletionService<>(executor);
    }

}
