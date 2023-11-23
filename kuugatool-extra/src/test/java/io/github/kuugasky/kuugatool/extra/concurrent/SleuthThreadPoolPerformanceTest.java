package io.github.kuugasky.kuugatool.extra.concurrent;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.concurrent.core.KuugaRejectedExecutionHandler;
import io.github.kuugasky.kuugatool.core.concurrent.core.KuugaThreadPoolExecutor;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.*;

/**
 * SleuthThreadPoolPerformanceTest
 *
 * @author kuuga
 * @since 2022/3/10
 */
public class SleuthThreadPoolPerformanceTest {

    private static final KuugaThreadPoolExecutor oldPool;

    private static final KuugaThreadPoolExecutor newPool;

    static {
        // 原始线程池初始化
        ThreadFactory threadFactory = (new ThreadFactoryBuilder()).setNameFormat("old-pool-%d").build();
        LinkedBlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
        ThreadPoolExecutor.AbortPolicy abortPolicy = new ThreadPoolExecutor.AbortPolicy();
        oldPool = new KuugaThreadPoolExecutor(10, 20, 60L, TimeUnit.SECONDS, workQueue, threadFactory, abortPolicy);


        // 修改后线程池初始化
        threadFactory = (new ThreadFactoryBuilder()).setNameFormat("new-pool-%d").build();
        KuugaRejectedExecutionHandler kuugaRejectedExecutionHandler = new KuugaRejectedExecutionHandler();
        workQueue = new LinkedBlockingQueue<>(20);
        newPool = new KuugaThreadPoolExecutor(10, 20, 60L, TimeUnit.SECONDS, workQueue, threadFactory, kuugaRejectedExecutionHandler);
    }

    @Test
    public void threadPerformanceTesting() throws InterruptedException {
        ExecutorService pool = Executors.newCachedThreadPool();
        pool.shutdown();
        System.out.println("第一次运行");
        List<TestCallable> list = ListUtil.newArrayList();
        list.add(() -> newPoolTest(100));
        list.add(() -> oldPoolTest(100));
        pool.invokeAll(list);

        System.out.println("第二次运行");
        list = ListUtil.newArrayList();
        list.add(() -> newPoolTest(50));
        list.add(() -> oldPoolTest(50));
        pool.invokeAll(list);

        System.out.println("第三次运行");
        list = ListUtil.newArrayList();
        list.add(() -> newPoolTest(30));
        list.add(() -> oldPoolTest(30));
        pool.invokeAll(list);

        System.out.println("第四次运行");
        list = ListUtil.newArrayList();
        list.add(() -> newPoolTest(500));
        list.add(() -> oldPoolTest(500));
        pool.invokeAll(list);
    }

    public static String oldPoolTest(int count) throws InterruptedException {
        long l = System.currentTimeMillis();
        List<TestCallable> list = ListUtil.newArrayList();
        for (int i = 0; i < count; i++) {
            int iFinal = i;
            list.add(() -> handle("oldPool", iFinal));
        }
        oldPool.invokeAll(list);
        System.out.printf("oldPool执行%d条任务共花费：%d毫秒%n", count, (System.currentTimeMillis() - l));
        return null;
    }

    public static String newPoolTest(int count) throws InterruptedException {
        long l = System.currentTimeMillis();
        List<TestCallable> list = ListUtil.newArrayList();
        for (int i = 0; i < count; i++) {
            int iFinal = i;
            list.add(() -> handle("newPool", iFinal));
        }
        newPool.invokeAll(list);
        System.out.printf("newPool执行%d条任务共花费：%d毫秒%n", count, (System.currentTimeMillis() - l));
        return null;
    }

    private static String handle(String str, int iFinal) throws InterruptedException {
        // System.out.println(str + " 第" + iFinal +"次运行");
        TimeUnit.SECONDS.sleep(2);
        return null;
    }

    @FunctionalInterface
    private interface TestCallable extends Callable<String> {

    }

}
