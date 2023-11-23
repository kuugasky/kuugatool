package io.github.kuugasky.kuugatool.core.concurrent.high;

import io.github.kuugasky.kuugatool.core.concurrent.ThreadPoolUtil;
import io.github.kuugasky.kuugatool.core.concurrent.ThreadUtil;
import io.github.kuugasky.kuugatool.core.concurrent.core.ThreadFactoryBuilder;
import io.github.kuugasky.kuugatool.core.concurrent.pool.ThreadPool;
import io.github.kuugasky.kuugatool.core.entity.KuugaModel;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

public class ThreadUtilTest {

    @Test
    public void newExecutorByBlockingCoefficient() {
        /*
         * 获得一个新的线程池<br>
         * 传入阻塞系数，线程池的大小计算公式为：CPU可用核心数 / (1 - 阻塞因子)<br>
         * Blocking Coefficient(阻塞系数) = 阻塞时间／（阻塞时间+使用CPU的时间）<br>
         * 计算密集型任务的阻塞系数为0，而IO密集型任务的阻塞系数则接近于1。
         * <p>
         * see: http://blog.csdn.net/partner4java/article/details/9417663
         *
         * @param blockingCoefficient 阻塞系数，阻塞因子介于0~1之间的数，阻塞因子越大，线程池中的线程数越多。
         */
        ThreadPoolExecutor threadPoolExecutor = ThreadPoolUtil.newExecutorByBlockingCoefficient(0.5f);
        System.out.println(StringUtil.formatString(threadPoolExecutor));
    }

    @Test
    public void execute() {
        /*
         * 直接在公共线程池中执行线程
         *
         * @param runnable 可运行对象
         */
        ThreadUtil.execute(() -> System.out.println("kuuga"));
    }

    @Test
    public void execAsync() {
        /*
         * 执行异步方法
         *
         * @param runnable 需要执行的方法体
         * @param isDaemon 是否守护线程。守护线程会在主线程结束后自动结束
         */
        ThreadUtil.execAsync(() -> System.out.println("kuuga"), true);
    }

    @Test
    public void testExecAsync() throws ExecutionException, InterruptedException {
        Future<KuugaModel> kuugaModelFuture = ThreadUtil.execAsync(() -> {
            System.out.println("Kuuga...");
            return KuugaModel.builder().name("kuuga").sex(1).build();
        });
        KuugaModel kuugaModel = kuugaModelFuture.get();
        System.out.println(StringUtil.formatString(kuugaModel));
    }

    @Test
    public void testExecAsync1() throws ExecutionException, InterruptedException {
        Callable<KuugaModel> task = () -> {
            System.out.println(".....");
            return KuugaModel.builder().name("kuuga1").sex(2).build();
        };
        Future<KuugaModel> kuugaModelFuture = ThreadUtil.execAsync(task);
        KuugaModel kuugaModel = kuugaModelFuture.get();
        System.out.println(StringUtil.formatString(kuugaModel));
    }

    @Test
    void execAsync2() throws ExecutionException, InterruptedException {
        Future<?> execAsync = ThreadUtil.execAsync(() -> System.out.println("execAsync"));
        execAsync.get();
    }

    @Test
    public void newCompletionService() {
        /*
         * 新建一个CompletionService，调用其submit方法可以异步执行多个任务，最后调用take方法按照完成的顺序获得其结果。<br>
         * 若未完成，则会阻塞
         *
         * @param <T> 回调对象类型
         */
        CompletionService<Object> objectCompletionService = ThreadPoolUtil.newCompletionService();
        System.out.println(StringUtil.formatString(objectCompletionService));
    }

    @Test
    public void testNewCompletionService() throws ExecutionException, InterruptedException {
        ExecutorService executorService = ThreadPoolUtil.newExecutor();
        CompletionService<KuugaModel> objectCompletionService1 = ThreadPoolUtil.newCompletionService(executorService);
        objectCompletionService1.submit(() -> {
            ThreadUtil.sleep(3000);
            return KuugaModel.builder().name("kuuga1").build();
        });
        objectCompletionService1.submit(() -> KuugaModel.builder().name("kuuga4").build());
        objectCompletionService1.submit(() -> KuugaModel.builder().name("kuuga3").build());
        objectCompletionService1.submit(() -> KuugaModel.builder().name("kuuga2").build());
        // 往CompletionService添加任务，然后take().get()按执行完的顺序获取结果值，如果溢出get则一直等待InterruptedException
        System.out.println(StringUtil.formatString(objectCompletionService1.take().get().getName()));
        System.out.println(StringUtil.formatString(objectCompletionService1.take().get().getName()));
        System.out.println(StringUtil.formatString(objectCompletionService1.take().get().getName()));
        System.out.println(StringUtil.formatString(objectCompletionService1.take().get().getName()));
        System.out.println(StringUtil.formatString(objectCompletionService1.take().get().getName()));
    }

    @Test
    public void newCountDownLatch() throws InterruptedException {
        /*
         * 新建一个CountDownLatch，一个同步辅助类，在完成一组正在其他线程中执行的操作之前，它允许一个或多个线程一直等待。
         *
         * @param count 闭锁数量
         */
        CountDownLatch mainLatch = ThreadUtil.newCountDownLatch(2);

        // CountDownLatch mainLatch = new CountDownLatch(2);
        // 任务锁闭锁5次，等5个线程任务都调用childLatch.countDown();后才能继续执行Bingo
        CountDownLatch childLatch = new CountDownLatch(5);

        System.out.println("mainLatch.getCount()......" + mainLatch.getCount());
        System.out.println("childLatch.getCount()......" + childLatch.getCount());

        // 依次创建并启动处于等待状态的5个MyRunnable线程
        for (int i = 0; i < 5; ++i) {
            new Thread(new MyRunnable(mainLatch, childLatch)).start();
        }
        Thread.sleep(100); // 先让子线程抢资源
        mainLatch.countDown(); // 主线程执行
        System.out.println("主线程完成一个 开始等待");
        // Thread.sleep(2000);
        System.out.println("计数一次再次执行, 此时countDown 的count 被减到0 其他await的线程可以执行");
        mainLatch.countDown();
        childLatch.await(); // 等待childLatch 被countDown5次
        System.out.println("Bingo!");
    }

    static class MyRunnable implements Runnable {
        private final CountDownLatch mainLatch;
        private final CountDownLatch childLatch;

        public MyRunnable(CountDownLatch mainLatch, CountDownLatch childLatch) {
            this.mainLatch = mainLatch;
            this.childLatch = childLatch;
        }

        public void run() {
            try {
                System.out.println("子线程执行 但是碰到主线程[await]开始等待====" + mainLatch.getCount());
                mainLatch.await();// 等待主线程执行两次 countDown() 再往下执行
                System.out.println("子线程：每个线程开始处理自己的工作");
                childLatch.countDown();// 完成预期工作，countDown() 一下
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void newThread() {
        // 创建新线程，非守护线程，正常优先级，线程组与当前线程的线程组一致
        ThreadUtil.newThread(() -> System.out.println(Thread.currentThread().getName() + "....."), "kuuga-THREAD-POOL").start();
    }

    @Test
    public void testNewThread() {
        // public static void main(String[] args) {
        Thread thread = ThreadUtil.newThread(() -> {
            ThreadUtil.sleep(3000);
            System.out.println(Thread.currentThread().getName() + ".....");
        }, "kuuga-THREAD-POOL", false);
        thread.start();
    }

    @Test
    public void sleep() {
        ThreadUtil.sleep(1000);
    }

    @Test
    public void testSleep() {
        ThreadUtil.sleep(1000D);
    }

    @Test
    public void testSleep1() {
        // public static void main(String[] args) {
        ThreadUtil.sleep(3, TimeUnit.SECONDS);
    }

    @Test
    public void safeSleep() {
        ThreadUtil.safeSleep(1000);
    }

    @Test
    public void testSafeSleep() {
        boolean b = ThreadUtil.safeSleep(10000F);
        System.out.println(b);
    }

    @Test
    public void getStackTrace() {
        // 获得堆栈列表
        StackTraceElement[] stackTrace = ThreadUtil.getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
            System.out.println(StringUtil.formatString(stackTraceElement));
        }

    }

    @Test
    public void getStackTraceElement() {
        StackTraceElement stackTraceElement = ThreadUtil.getStackTraceElement(1);
        System.out.println(StringUtil.formatString(stackTraceElement));
    }

    @Test
    public void createThreadLocal() {
        // public static void main(String[] args) {
        // 得用main试
        ThreadLocal<String> threadLocal = ThreadUtil.createThreadLocal(false);
        threadLocal.set("kuuga");
        ThreadUtil.execute(() -> System.out.println(threadLocal.get()));
        // 是否为子线程提供从父线程那里继承的值
        ThreadLocal<String> threadLocal1 = ThreadUtil.createThreadLocal(true);
        threadLocal1.set("kuuga1");
        ThreadUtil.execute(() -> System.out.println(threadLocal1.get()));
    }

    @Test
    public void createThreadFactoryBuilder() {
        // public static void main(String[] args) {
        // 创建一个非守护线程，线程名前缀为kuuga-
        ThreadFactoryBuilder threadFactoryBuilder = ThreadUtil.createThreadFactoryBuilder();
        ThreadFactory build = threadFactoryBuilder.setDaemon(false).setNamePrefix("Kuuga-").build();
        build.newThread(() -> System.out.println(Thread.currentThread().getName() + ".....")).start();
    }

    @Test
    public void interrupt() {
        // public static void main(String[] args) {
        Thread thread = ThreadUtil.newThread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + ".............");
            ThreadUtil.sleep(10000);
            System.out.println("====");
        }, "Kuuga-");
        thread.start();
        thread.interrupt();
        // ThreadUtil.interrupt(thread, false);
    }

    @Test
    void interrupt2() {
        Thread thread = ThreadUtil.newThread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + ".............");
            ThreadUtil.sleep(10000);
            System.out.println("====");
        }, "Kuuga-");
        thread.start();
        ThreadUtil.interrupt(thread, true);
    }

    @Test
    public void waitForDie() {
        // public static void main(String[] args) {
        ThreadPool.submit(() -> {
            // ThreadUtil.sleep(1000);
            ThreadUtil.waitForDie();
            System.out.println("线程执行完成");
        });
    }

    @Test
    public void testWaitForDie() {
        ThreadPool.submit(() -> {
            // ThreadUtil.sleep(1000);
            ThreadUtil.waitForDie(new Thread());
            System.out.println("线程执行完成");
        });
    }

    @Test
    public void getThreads() {
        Thread[] threads = ThreadUtil.getThreads();
        for (Thread thread : threads) {
            System.out.println(thread.getName());
        }
    }

    @Test
    public void testGetThreads() {
        Thread[] threads = ThreadUtil.getThreads(new ThreadGroup("group"));
        for (Thread thread : threads) {
            System.out.println(thread.getName());
        }
    }

    @Test
    public void getMainThread() {
        Thread mainThread = ThreadUtil.getMainThread();
        assert mainThread != null;
        System.out.println(mainThread.getName());
    }

    // @Test
    // public void currentThreadGroup() {
    //     ThreadGroup threadGroup = ThreadUtil.currentThreadGroup();
    //     System.out.println(threadGroup.getName());
    // }

    @Test
    public void newNamedThreadFactory() {
        ThreadFactory threadFactory = ThreadUtil.newNamedThreadFactory("Kuuga-", true);
        System.out.println(StringUtil.formatString(threadFactory));
    }

    @Test
    public void testNewNamedThreadFactory() {
        ThreadFactory threadFactory = ThreadUtil.newNamedThreadFactory("Kuuga-", new ThreadGroup("group"), true);
        System.out.println(StringUtil.formatString(threadFactory));
    }

    @Test
    public void testNewNamedThreadFactory1() {
        ThreadFactory threadFactory = ThreadUtil.newNamedThreadFactory("Kuuga-", new ThreadGroup("group"), true,
                (thread, throwable) -> System.out.println(thread.getName() + "=" + throwable.getMessage()));

        System.out.println(StringUtil.formatString(threadFactory));
    }

    @Test
    public void sync() {
        ThreadUtil.sync(this);
    }

    @Test
    public void concurrencyTest() {
        ThreadUtil.concurrencyTest(10, () -> System.out.println(Thread.currentThread().getName() + "=====kuuga"));
    }
}
