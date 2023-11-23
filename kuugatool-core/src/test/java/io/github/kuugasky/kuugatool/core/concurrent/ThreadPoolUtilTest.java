package io.github.kuugasky.kuugatool.core.concurrent;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.github.kuugasky.kuugatool.core.concurrent.core.KuugaRejectedExecutionHandler;
import io.github.kuugasky.kuugatool.core.concurrent.daemon.DaemonThread;
import io.github.kuugasky.kuugatool.core.random.RandomUtil;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

class ThreadPoolUtilTest {

    @Test
    void build() throws ExecutionException, InterruptedException {
        ExecutorService executorService = ThreadPoolUtil.build();
        Future<?> Kuuga = executorService.submit(() -> System.out.println(Thread.currentThread().getName()));
        Kuuga.get();

        ExecutorService executorService1 = ThreadPoolUtil.newExecutor(1);
        Future<?> kuuga1 = executorService1.submit(() -> System.out.println(Thread.currentThread().getName()));
        kuuga1.get();
        Future<?> kuuga2 = executorService1.submit(() -> System.out.println(Thread.currentThread().getName()));
        kuuga2.get();

        ThreadFactory threadFactory = ThreadUtil.newNamedThreadFactory("kuuga-THREAD-POOL-CUSTOM", true);
        Thread thread = threadFactory.newThread(() -> System.out.println(Thread.currentThread().getName()));
        thread.start();


    }

    @Test
    void testBuild() throws ExecutionException, InterruptedException {
        ExecutorService executorService = ThreadPoolUtil.build("Kuuga-util");
        System.out.println(executorService);
        Future<?> xxx = executorService.submit(() -> System.out.println(Thread.currentThread().getName() + "--->xxx"));
        xxx.get();
    }

    @Test
    void testBuild1() {
        ExecutorService executorService = ThreadPoolUtil.build("Kuuga-util", 10, 10, 10L, TimeUnit.SECONDS);
        System.out.println(executorService);
    }

    @Test
    void testBuild2() {
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat(ThreadPoolUtil.THREAD_FACTORY_NAME).build();
        // 定义工作队列：有边界阻塞队列，边界值为【20】
        final LinkedBlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(20);

        ExecutorService executorService = ThreadPoolUtil.build(10, 10, 10L, TimeUnit.SECONDS, workQueue, threadFactory, new KuugaRejectedExecutionHandler());
        System.out.println(executorService);
    }

    @Test
    void testBuild3() throws ExecutionException, InterruptedException {
        ExecutorService executorService = ThreadPoolUtil.build(10, 20, 10L, TimeUnit.SECONDS, "Kuuga-house-detail-thread-pool");
        CompletableFuture<Integer> integerCompletableFuture = CompletableFuture.supplyAsync(() -> {
            int sum = 0;
            for (int i = 0; i < 5; i++) {
                sum += i;
                try {
                    System.out.println("sum=" + sum);
                    TimeUnit.SECONDS.sleep(RandomUtil.randomInt(1, 2));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            return sum;
        }, executorService);
        integerCompletableFuture.thenRun(() -> System.out.println("线程任务执行完成。"));
        Integer integer = integerCompletableFuture.get();
        System.out.println(integer);
        DaemonThread.await();
    }

}