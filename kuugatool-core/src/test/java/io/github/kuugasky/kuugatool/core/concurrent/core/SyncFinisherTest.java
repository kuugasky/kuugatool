package io.github.kuugasky.kuugatool.core.concurrent.core;

import io.github.kuugasky.kuugatool.core.concurrent.CountDownLatchUse;
import io.github.kuugasky.kuugatool.core.concurrent.ThreadUtil;
import io.github.kuugasky.kuugatool.core.concurrent.daemon.DaemonThread;
import io.github.kuugasky.kuugatool.core.date.DateFormat;
import io.github.kuugasky.kuugatool.core.date.DateUtil;
import io.github.kuugasky.kuugatool.core.date.interval.TimeInterval;
import io.github.kuugasky.kuugatool.core.lang.Console;
import io.github.kuugasky.kuugatool.core.random.RandomUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

class SyncFinisherTest {

    @Test
    void setSyncStart() {
        // 创建线程同步结束器，线程数1，setSyncStart设置所有线程同时开始
        SyncFinisher syncFinisher = new SyncFinisher(1).syncStart(true);
        System.out.println(StringUtil.formatString(syncFinisher));
    }

    @Test
    void addRepeatWorker() {
        // 创建线程同步结束器，线程数2，setSyncStart设置所有线程同时开始
        SyncFinisher syncFinisher = new SyncFinisher(2);
        // 下面两个worker是否需要同时开始
        syncFinisher.syncStart(true);
        // 增加定义的线程数同等数量的worker，以下两个任务各执行2次
        syncFinisher.addRepeatWorker(
                () -> {
                    System.out.println(Thread.currentThread().getName() + "-->kuuga1<--" + DateUtil.format(DateFormat.yyyy_MM_dd_HH_mm_ss_SSS, DateUtil.now()));
                    try {
                        TimeUnit.SECONDS.sleep(RandomUtil.randomInt(1, 2));
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
        syncFinisher.addRepeatWorker(
                () -> {
                    System.out.println(Thread.currentThread().getName() + "-->kuuga2<--" + DateUtil.format(DateFormat.yyyy_MM_dd_HH_mm_ss_SSS, DateUtil.now()));
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
        syncFinisher.start();

        System.out.println(syncFinisher.getWorkerSuccessCount());
        System.out.println(syncFinisher.getWorkerErrorCount());
        List<SyncFinisher.SyncException> workerErrorMessages = syncFinisher.getWorkerErrorMessages();
        for (SyncFinisher.SyncException workerErrorMessage : workerErrorMessages) {
            System.out.println(StringUtil.formatString(workerErrorMessage));
        }

        System.out.println(syncFinisher.interval());
        System.out.println(syncFinisher.intervalPretty());

    }

    @Test
    void addWorker() {
        // 创建线程同步结束器，线程数2，setSyncStart设置所有线程同时开始
        SyncFinisher syncFinisher = new SyncFinisher(2).syncStart(true);
        // 增加工作线程，以下三个任务各执行1次
        syncFinisher.addWorker(() -> System.out.println(Thread.currentThread().getName() + "-->kuuga1<--" + DateUtil.format(DateFormat.yyyy_MM_dd_HH_mm_ss_SSS, DateUtil.now())));
        syncFinisher.addWorker(() -> System.out.println(Thread.currentThread().getName() + "==kuuga " + new TimeInterval().intervalPretty()));
        syncFinisher.addWorker(() -> System.out.println(Thread.currentThread().getName() + "==kuuga " + new TimeInterval().intervalPretty()));
        syncFinisher.addWorker(() -> System.out.println(Thread.currentThread().getName() + "==kuuga " + new TimeInterval().intervalPretty()));
        // false时异步线程任务未执行完，主线程就结束了
        syncFinisher.start(false);
        // true时要等所有异步线程执行完才切回主线程结束
        // syncFinisher.start(true);
    }

    @Test
    void await() {
        CountDownLatchUse downLatchUtil = CountDownLatchUse.build(5);

        // 创建线程同步结束器，线程数5，setSyncStart设置所有线程同时开始
        SyncFinisher syncFinisher = new SyncFinisher(5).syncStart(true);
        // 增加工作线程，以下任务各执行1次
        SyncFinisher finalSyncFinisher = syncFinisher;
        syncFinisher = syncFinisher.addRepeatWorker(() -> {
            TimeInterval timeInterval = new TimeInterval();
            timeInterval.start();
            System.out.println(Thread.currentThread().getName() + "==kuuga start");
            try {
                TimeUnit.SECONDS.sleep(RandomUtil.randomInt(1, 5));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(Thread.currentThread().getName() + "==kuuga [" + finalSyncFinisher.count() + "]" + timeInterval.intervalPretty());
            downLatchUtil.countDown();
        });
        // 开始工作，并阻塞等待所有任务完成
        syncFinisher.start(true);
        // syncFinisher.await();

        System.out.println("main thread.");

        // while (downLatchUtil.getCount() != 0) {
        //     TimeUnit.SECONDS.sleep(1);
        //     System.out.println("---->" + syncFinisher.count());
        //     if (downLatchUtil.getCount() == 0) {
        //         System.exit(0);
        //     }
        // }
        DaemonThread.await();
    }

    @Test
    void clearWorker() {
        SyncFinisher syncFinisher = new SyncFinisher(100).syncStart(true);
        syncFinisher.addRepeatWorker(() -> System.out.println(Thread.currentThread().getName() + "==kuuga " + new TimeInterval().intervalPretty()));
        syncFinisher.clearWorker();
        System.out.println(syncFinisher.count());
        syncFinisher.start();
    }

    @Test
    void count() {
        SyncFinisher syncFinisher = new SyncFinisher(2).syncStart(true);
        syncFinisher.addRepeatWorker(() -> {
            ThreadUtil.sleep(10000);
            System.out.println(Thread.currentThread().getName() + "==kuuga " + new TimeInterval().intervalPretty());
        });
        // true阻塞执行，必须所有work执行完才走主线程
        // false非阻塞执行，直接往下走主线程
        syncFinisher.start(true);
        long count = syncFinisher.count();
        System.out.println(count);
    }

    @Test
    void test() {
        // 模拟1000个线程并发
        SyncFinisher sf = new SyncFinisher(1000);
        sf.addRepeatWorker(() -> {
            // 需要并发测试的业务代码
            ThreadUtil.sleep(1000);
            System.out.println(Thread.currentThread().getName() + " --->Kuuga " + new TimeInterval().intervalPretty());
        });
        sf.start(true);
        System.out.println("main thread.");
    }

    @Test
    void Kuuga() {
        SyncFinisher syncFinisher = new SyncFinisher(2);
        syncFinisher.addRepeatWorker(() -> {
            long delay = RandomUtil.randomLong(100, 1000);
            if (delay % 2 != 0) {
                throw new RuntimeException("delay % 2 != 0");
            } else {
                System.out.println(delay);
            }
            ThreadUtil.sleep(delay);
            Console.blueLog("{} test finished, delay: {}", Thread.currentThread().getName(), delay);
            System.out.println();
        }).addRepeatWorker(() -> {
            long delay = RandomUtil.randomLong(100, 1000);
            if (delay % 2 != 0) {
                throw new RuntimeException("delay % 2 != 0");
            } else {
                System.out.println(delay);
            }
            ThreadUtil.sleep(delay);
            Console.blueLog("{} test finished, delay: {}", Thread.currentThread().getName(), delay);
            System.out.println();
        });

        syncFinisher.syncStart(true);
        syncFinisher.start(true);
        System.out.println("WorkerSuccessCount-->" + syncFinisher.getWorkerSuccessCount());
        System.out.println("WorkerErrorCount-->" + syncFinisher.getWorkerErrorCount());
        List<SyncFinisher.SyncException> workerErrorMessages = syncFinisher.getWorkerErrorMessages();
        workerErrorMessages.forEach(x -> System.out.println(StringUtil.formatString(x)));
    }

}