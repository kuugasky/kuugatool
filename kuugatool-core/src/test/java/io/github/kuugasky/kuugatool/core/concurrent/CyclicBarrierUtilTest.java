package io.github.kuugasky.kuugatool.core.concurrent;


import io.github.kuugasky.kuugatool.core.concurrent.daemon.DaemonThread;
import io.github.kuugasky.kuugatool.core.concurrent.pool.ThreadPool;
import org.junit.jupiter.api.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.TimeUnit;

class CyclicBarrierUtilTest {

    @Test
    void testBuild() throws BrokenBarrierException, InterruptedException {
        CyclicBarrierUse cyclicBarrier = CyclicBarrierUse.build(2, () -> {
            System.out.println("开饭啦！");
        });
        System.out.println("1.开始第一个屏障");
        cyclicBarrier.await();
        // 错误示例，一个线程内连续进行等候
        System.out.println("2.开始第二个屏障");
        cyclicBarrier.await();

        System.out.println("收拾碗筷");
    }

    @Test
    void testBuild2() {
        CyclicBarrierUse cyclicBarrier = CyclicBarrierUse.build(2, () -> {
            System.out.println("开饭啦！");
        });

        ThreadPool.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println("开始第一个屏障");
                cyclicBarrier.await();
            } catch (BrokenBarrierException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        ThreadPool.execute(() -> {
            System.out.println("开始第二个屏障");
            try {
                cyclicBarrier.await();
            } catch (BrokenBarrierException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        DaemonThread.await();
    }

}