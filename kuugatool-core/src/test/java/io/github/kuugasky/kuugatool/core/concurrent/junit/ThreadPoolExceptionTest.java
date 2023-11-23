package io.github.kuugasky.kuugatool.core.concurrent.junit;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.github.kuugasky.kuugatool.core.concurrent.core.KuugaRejectedExecutionHandler;
import io.github.kuugasky.kuugatool.core.concurrent.core.KuugaThreadPoolExecutor;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

/**
 * ThreadPoolException
 *
 * @author kuuga
 * @since 2023/2/10-02-10 13:33
 */
public class ThreadPoolExceptionTest {

    /**
     * submit不会抛出异常
     */
    @Test
    void test() {
        // 创建一个线程池
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        // 当线程池抛出异常后 submit无提示，其他线程继续执行;可以采用submit.get()来抛出异常
        executorService.submit(new task());
        // 当线程池抛出异常后 execute抛出异常，其他线程继续执行新任务
        executorService.execute(new task());
    }

    // 任务类
    static class task implements Runnable {
        @Override
        public void run() {
            System.out.println("进入了task方法！！！");
            int i = 1 / 0;
        }
    }

    /**
     * 方案一：使用 try -catch
     */
    @Test
    void test1() throws ExecutionException, InterruptedException {
        // 创建一个线程池
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        /*submit()想要获取异常信息就必须使用get()方法！！*/
        // 当线程池抛出异常后 submit无提示，其他线程继续执行
        Future<?> submit = executorService.submit(new task1());
        submit.get();

        // 当线程池抛出异常后 execute抛出异常，其他线程继续执行新任务
        executorService.execute(new task1());
    }

    // 任务类
    static class task1 implements Runnable {
        @Override
        public void run() {
            try {
                System.out.println("进入了task方法！！！");
                int i = 1 / 0;
            } catch (Exception e) {
                System.out.println("异常信息:" + e.getMessage());
            }
        }
    }

    /**
     * 方案二：使用Thread.setDefaultUncaughtExceptionHandler方法捕获异常
     */
    @Test
    void test3() throws InterruptedException {
        // 1.实现一个自己的线程池工厂
        ThreadFactory factory = (Runnable r) -> {
            // 创建一个线程
            Thread t = new Thread(r);
            // 给创建的线程设置UncaughtExceptionHandler对象 里面实现异常的默认逻辑
            Thread.setDefaultUncaughtExceptionHandler((Thread thread1, Throwable e)
                    -> System.out.println("线程工厂设置的exceptionHandler" + e.getMessage()));
            return t;
        };
        // 2.创建一个自己定义的线程池，使用自己定义的线程工厂
        ExecutorService executorService = new ThreadPoolExecutor(
                1,
                1,
                0,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(10),
                factory);

        // submit无提示
        executorService.submit(new task());

        Thread.sleep(1000);
        System.out.println("==================为检验打印结果，1秒后执行execute方法");

        // execute 方法被线程工厂factory 的UncaughtExceptionHandler捕捉到异常
        executorService.execute(new task());
    }
}

// 任务类
class task implements Runnable {

    @Override
    public void run() {
        // 方案一：使用 try -catch
        // 方案二：使用Thread.setDefaultUncaughtExceptionHandler方法捕获异常
        System.out.println("进入了task方法！！！");
        int i = 1 / 0;
    }

    @Test
    void test() throws ExecutionException, InterruptedException {
        final ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("kuuga").build();
        // 定义工作队列：有边界阻塞队列，边界值为【20】
        final LinkedBlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(20);
        // 拒绝政策
        KuugaRejectedExecutionHandler abortPolicy = new KuugaRejectedExecutionHandler();

        ExecutorService threadPoolService = new KuugaThreadPoolExecutor(10, 20, 60L, TimeUnit.SECONDS, workQueue, threadFactory, abortPolicy);
        Future<?> Kuuga = threadPoolService.submit(() -> System.out.println("kuuga"));
        Kuuga.get();
    }

}



