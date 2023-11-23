package io.github.kuugasky.kuugatool.extra.concurrent;

import io.github.kuugasky.kuugatool.core.concurrent.daemon.DaemonThread;
import io.github.kuugasky.kuugatool.core.date.DateUtil;
import io.github.kuugasky.kuugatool.extra.concurrent.config.SleuthThreadPoolConfig;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@SpringJUnitConfig(classes = SleuthThreadPoolConfig.class)
class SleuthThreadScheduledPoolTest {

    /**
     * 如果上一个任务没有执行完毕，则需要等上一个任务执行完毕后立即执行。
     */
    @Test
    void scheduleAtFixedRate() {
        SleuthThreadScheduledPool.scheduleAtFixedRate(
                () -> {
                    System.out.println("Kuuga<----->" + DateUtil.formatDateTime(DateUtil.now()));
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                },
                0, 1, TimeUnit.SECONDS);
        DaemonThread.await(5, TimeUnit.SECONDS);
    }

    /**
     * 以上一个任务结束时开始计时，delay时间过去后，立即执行。
     */
    @Test
    void scheduleWithFixedDelay() {
        SleuthThreadScheduledPool.scheduleWithFixedDelay(
                () -> {
                    System.out.println("Kuuga<----->" + DateUtil.formatDateTime(DateUtil.now()));
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                },
                0, 1, TimeUnit.SECONDS);
        DaemonThread.await(5, TimeUnit.SECONDS);
    }

    @Test
    void schedule() {
        SleuthThreadScheduledPool.schedule(() -> System.out.println("kuuga"), 1, TimeUnit.SECONDS);
        DaemonThread.await(2, TimeUnit.SECONDS);
    }

    @Test
    void testSchedule() throws ExecutionException, InterruptedException, TimeoutException {
        ScheduledFuture<String> schedule = SleuthThreadScheduledPool.schedule(() -> "kuuga", 1, TimeUnit.SECONDS);
        System.out.println(schedule.get(2, TimeUnit.SECONDS));
        DaemonThread.await(3, TimeUnit.SECONDS);
    }

    @Test
    void shutdown() {
        SleuthThreadScheduledPool.shutdown();
    }

    @Test
    void shutdownNow() {
        SleuthThreadScheduledPool.shutdownNow();
    }

}