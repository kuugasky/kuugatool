package io.github.kuugasky.kuugatool.core.concurrent.high;

import io.github.kuugasky.kuugatool.core.concurrent.ThreadUtil;
import io.github.kuugasky.kuugatool.core.lang.Console;
import io.github.kuugasky.kuugatool.core.random.RandomUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HighConcurrentTesterTest {

    /**
     * 单任务*N线程数并发
     */
    @Test
    public void concurrencyTesterTest() {
        // 100条线程，并发执行任务
        HighConcurrentResult tester = ThreadUtil.concurrencyTest(100, () -> {
            long delay = RandomUtil.randomLong(100, 1000);
            if (delay % 2 != 0) {
                throw new RuntimeException("delay % 2 != 0");
            }
            ThreadUtil.sleep(delay);
            Console.blueLog("{} test finished, delay: {}", Thread.currentThread().getName(), delay);
        });
        Console.blueLog("总耗时：" + tester.getInterval());
        System.out.println(StringUtil.formatString(tester));
        System.out.println(tester.getTimeInterval().intervalPretty());
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool = Executors.newCachedThreadPool();
        // 100锁
        CountDownLatch cdl = new CountDownLatch(100);
        for (int i = 0; i < 100; i++) {
            // 将锁放入CountRunnable
            CountRunnable runnable = new CountRunnable(cdl);
            // 线程池执行runnable
            pool.execute(runnable);
        }
        // 等待子线程执行完，count为0后，主线程继续执行，比输出"concurrency counts"快
        cdl.await();
        System.out.println("线程任务完成.");
    }
}

/**
 * Runnable实现类
 */
class CountRunnable implements Runnable {

    /**
     * 倒计时闩锁
     */
    private final CountDownLatch countDownLatch;

    public CountRunnable(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            synchronized (countDownLatch) {
                // 每次减少一个容量
                countDownLatch.countDown();
                System.out.println("thread counts = " + (countDownLatch.getCount()));
            }
            // 每条线程任务都在等countDownLatch释放锁
            countDownLatch.await();
            // 所有线程任务释放锁后，count计数为0，打印出来都是100-0
            System.out.println("concurrency counts = " + (100 - countDownLatch.getCount()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}