package io.github.kuugasky.kuugatool.core.concurrent;

import io.github.kuugasky.kuugatool.core.concurrent.pool.ThreadPool;
import io.github.kuugasky.kuugatool.core.date.interval.TimeInterval;
import io.github.kuugasky.kuugatool.core.lang.Console;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class CountDownLatchUtilTest {

    @Test
    void build() {
        CountDownLatchUse countDownLatch = CountDownLatchUse.build(1);
        System.out.println(StringUtil.formatString(countDownLatch));
    }

    @Test
    void await() throws InterruptedException {
        // 调用await()方法的线程会被挂起，它会等待直到count值为0才继续执行
        CountDownLatchUse countDownLatch = CountDownLatchUse.build(2);
        ThreadPool.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                countDownLatch.countDown();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        countDownLatch.countDown();
        System.out.println("-------->" + countDownLatch.getCount());
        countDownLatch.await();
        System.out.println("good");
    }

    @Test
    void await2() {
        // 调用await()方法的线程会被挂起，它会等待直到count值为0才继续执行
        CountDownLatchUse countDownLatch = CountDownLatchUse.build(2);
        countDownLatch.countDown();
        countDownLatch.countDown();
        System.out.println("good");
    }

    @Test
    void testAwait() throws InterruptedException {
        // 调用await()方法的线程会被挂起，它会等待直到count值为0才继续执行
        CountDownLatchUse countDownLatch = CountDownLatchUse.build(2);

        ThreadPool.execute(() -> {
            for (int i = 0; i < 3; i++) {
                countDownLatch.countDown();
                Console.greenLog("count --");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        boolean await = countDownLatch.await(3, TimeUnit.SECONDS);
        if (await) {
            Console.blueLog("任务结束");
        } else {
            Console.blueLog("3s后count不为0，主线程继续执行:" + false);
        }
    }

    @Test
    void countDown() {
        CountDownLatchUse countDownLatch = CountDownLatchUse.build(1);
        countDownLatch.countDown();
        System.out.println(countDownLatch.getCount());
    }

    @Test
    void getCount() {
        CountDownLatchUse countDownLatch = CountDownLatchUse.build(1);
        countDownLatch.countDown();
        System.out.println(countDownLatch.getCount());
    }

    @Test
    void test() {
        TimeInterval timeInterval = new TimeInterval();
        String mainName = Thread.currentThread().getName();
        timeInterval.start(mainName);
        final CountDownLatch latch = new CountDownLatch(2);
        System.out.println("主线程开始执行……");
        // 第一个子线程执行
        ExecutorService es1 = Executors.newSingleThreadExecutor();
        es1.execute(() -> {
            String name = Thread.currentThread().getName();
            timeInterval.start(name);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("子线程：" + name + "执行" + "#耗时:" + timeInterval.intervalPretty(name));
            latch.countDown();
        });
        es1.shutdown();

        // 第二个子线程执行
        ExecutorService es2 = Executors.newSingleThreadExecutor();
        es2.execute(() -> {
            String name = Thread.currentThread().getName();
            timeInterval.start(name);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("子线程：" + Thread.currentThread().getName() + "执行" + "#耗时:" + timeInterval.intervalPretty(name));
            latch.countDown();
        });
        es2.shutdown();
        System.out.println("等待两个线程执行完毕……");
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("两个子线程都执行完毕，继续执行主线程" + "#耗时:" + timeInterval.intervalPretty(mainName));
    }

}