package io.github.kuugasky.kuugatool.core.concurrent;

import io.github.kuugasky.kuugatool.core.concurrent.daemon.DaemonThread;
import org.junit.jupiter.api.Test;

class SemaphoreUtilTest {

    @Test
    void test() {
        // 定义semaphore实例，设置许可数为3，即停车位为3个
        SemaphoreUse semaphore = SemaphoreUse.build(3, false);
        // 创建五个线程，即有5辆汽车准备进入停车场停车
        for (int i = 1; i <= 5; i++) {
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + "尝试进入停车场...");
                    // 尝试获取许可
                    semaphore.acquire();
                    // 模拟停车
                    long time = (long) (Math.random() * 10 + 1);
                    System.out.println(Thread.currentThread().getName() + "获得许可，进入了停车场，停车" + time + "秒...");
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(Thread.currentThread().getName() + "开始驶离停车场...");
                    // 释放许可
                    semaphore.release();
                    System.out.println(Thread.currentThread().getName() + "释放许可，离开了停车场！");
                }
            }, i + "号汽车").start();
        }
        DaemonThread.await();
    }
}