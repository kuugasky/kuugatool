package io.github.kuugasky.kuugatool.core.concurrent.daemon;

import io.github.kuugasky.kuugatool.core.concurrent.pool.ThreadScheduledPool;
import io.github.kuugasky.kuugatool.core.lang.Console;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 守护线程
 *
 * @author kuuga
 * @since 2021/7/14
 */
@Slf4j
public final class DaemonThread {

    /**
     * 守护线程等待，不退出
     */
    @SneakyThrows
    public static void await() {
        new CountDownLatch(1).await();
    }

    /**
     * 守护线程等待，到指定时间退出
     */
    public static void await(long timeoutMillis, TimeUnit unit) {
        String threadName = Thread.currentThread().getName();
        CountDownLatch countDownLatch = new CountDownLatch(1);

        log.info("[{}]线程挂起", threadName);
        Console.redLog(StringUtil.format("[{}]线程挂起", threadName));

        ThreadScheduledPool.schedule(() -> {
            if (countDownLatch.getCount() == 0) {
                return;
            }
            countDownLatch.countDown();

            log.info("[{}]线程退出", threadName);
            Console.greenLog(StringUtil.format("[{}]线程退出", threadName));

        }, timeoutMillis, unit);
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            log.error("DaemonThread.await error: {}", e.getMessage(), e);
        }
    }

}
