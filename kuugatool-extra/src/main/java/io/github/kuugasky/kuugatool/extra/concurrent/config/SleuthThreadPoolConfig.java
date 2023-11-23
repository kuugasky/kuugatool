package io.github.kuugasky.kuugatool.extra.concurrent.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.github.kuugasky.kuugatool.core.concurrent.core.KuugaRejectedExecutionHandler;
import io.github.kuugasky.kuugatool.core.concurrent.core.KuugaThreadPoolExecutor;
import io.github.kuugasky.kuugatool.extra.concurrent.SleuthCompletableFuturePool;
import io.github.kuugasky.kuugatool.extra.concurrent.SleuthThreadPool;
import io.github.kuugasky.kuugatool.extra.concurrent.SleuthThreadScheduledPool;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.cloud.sleuth.instrument.async.TraceableExecutorService;
import org.springframework.cloud.sleuth.instrument.async.TraceableScheduledExecutorService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.*;

/**
 * SleuthThreadPoolConfig
 *
 * @author kuuga
 * @since 2021/6/16
 */
//@ComponentScan(basePackageClasses = KuugaSpringBeanPicker.class)、
@Component
public class SleuthThreadPoolConfig {

    @Resource
    private BeanFactory beanFactory;

    public static final String THREAD_FACTORY_NAME = "KUUGA-SLEUTH-THREAD-POOL-%d";
    public static final String THREAD_SCHEDULED_FACTORY_NAME = "KUUGA-SLEUTH-SCHEDULED-THREAD-POOL-%d";
    public static final String THREAD_COMPLETION_FACTORY_NAME = "KUUGA-SLEUTH-COMPLETION-THREAD-POOL-%d";
    public static final String THREAD_COMPLETABLE_FUTURE_FACTORY_NAME = "KUUGA-SLEUTH-COMPLETABLE-FUTURE-THREAD-POOL-%d";

    @Bean(name = SleuthThreadPool.SLEUTH_EXECUTOR_SERVICE)
    public ExecutorService sleuthExecutorService() {
        // 定义线程工厂名称
        final ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat(THREAD_FACTORY_NAME).build();
        // 定义工作队列：有边界阻塞队列，边界值为【Integer.MAX_VALUE】
        // final LinkedBlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
        final LinkedBlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(20);
        // 拒绝策略
        // ThreadPoolExecutor.AbortPolicy abortPolicy = new ThreadPoolExecutor.AbortPolicy();
        KuugaRejectedExecutionHandler kuugaRejectedExecutionHandler = new KuugaRejectedExecutionHandler();

        KuugaThreadPoolExecutor kuugaThreadPoolExecutor = new KuugaThreadPoolExecutor(10, 20, 60L, TimeUnit.SECONDS, workQueue, threadFactory, kuugaRejectedExecutionHandler);

        return new TraceableExecutorService(this.beanFactory, kuugaThreadPoolExecutor);
    }

    @Bean(name = SleuthThreadScheduledPool.SLEUTH_SCHEDULED_EXECUTOR_SERVICE)
    public ScheduledExecutorService sleuthScheduledExecutorService() {
        // 定义线程名称
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat(THREAD_SCHEDULED_FACTORY_NAME).build();
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10, threadFactory);

        return new TraceableScheduledExecutorService(this.beanFactory, scheduledExecutorService);
    }

    // @Bean(name = SleuthCompletionServicePool.SLEUTH_COMPLETION_EXECUTOR_SERVICE)
    // public ExecutorService sleuthCompletionExecutorService() {
    //     // 定义线程名称
    //     ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat(THREAD_COMPLETION_FACTORY_NAME).build();
    //     ExecutorService completionExecutorService = Executors.newFixedThreadPool(5, threadFactory);
    //
    //     return new TraceableScheduledExecutorService(this.beanFactory, completionExecutorService);
    // }

    @Bean(name = SleuthCompletableFuturePool.SLEUTH_COMPLETABLE_FUTURE_EXECUTOR_SERVICE)
    public ExecutorService sleuthCompletableFutureExecutorService() {
        // 定义线程工厂名称
        final ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat(THREAD_COMPLETABLE_FUTURE_FACTORY_NAME).build();
        // 定义工作队列：有边界阻塞队列，边界值为【20】
        final LinkedBlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(20);
        // 拒绝政策
        KuugaRejectedExecutionHandler abortPolicy = new KuugaRejectedExecutionHandler();
        ExecutorService executorService = new KuugaThreadPoolExecutor(10, 20, 60L, TimeUnit.SECONDS, workQueue, threadFactory, abortPolicy);

        return new TraceableScheduledExecutorService(this.beanFactory, executorService);
    }

}
