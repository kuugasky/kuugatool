package io.github.kuugasky.kuugatool.core.concurrent;

import io.github.kuugasky.kuugatool.core.concurrent.daemon.DaemonThread;
import io.github.kuugasky.kuugatool.core.concurrent.pool.ThreadScheduledPool;
import io.github.kuugasky.kuugatool.core.lang.Console;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ThreadScheduledPoolTest {

    private static int i = 0;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // 延迟3s，周5s一周期
        ScheduledFuture<?> scheduledFuture = ThreadScheduledPool.scheduleWithFixedDelay(() -> {
            System.out.println("......start");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                if (i == 10) {
                    throw new RuntimeException("报错了");
                }
            } catch (Exception e) {
                Console.redLog("error message:", e.getMessage());
            }
            i++;
            System.out.println("......end");
        }, 1, 2, TimeUnit.SECONDS);

        Object r = scheduledFuture.get();
        System.out.println(StringUtil.formatString(r));
        // ScheduledFuture<?> scheduledFuture = ThreadScheduledPool.scheduleWithFixedDelay(() -> System.out.println("~~~~~~"), 1, 2, TimeUnit.SECONDS);
        // Thread.sleep(5000);
        // scheduledFuture.cancel(true);
        //
        // ThreadScheduledPool.schedule(() -> System.out.println("......"), 5, TimeUnit.SECONDS);
    }

    @Test
    public void schedule() {
        ThreadScheduledPool.schedule(() -> System.out.println("kuuga"), 2, TimeUnit.SECONDS);
        DaemonThread.await(3, TimeUnit.SECONDS);
    }

    @Test
    public void testSchedule() throws ExecutionException, InterruptedException, TimeoutException {
        ScheduledFuture<String> schedule = ThreadScheduledPool.schedule(() -> "kuuga", 2, TimeUnit.SECONDS);
        String s = schedule.get(3, TimeUnit.SECONDS);
        System.out.println(s);
        DaemonThread.await(3, TimeUnit.SECONDS);
    }

    @Test
    void scheduleAtFixedRate() {
        ThreadScheduledPool.scheduleAtFixedRate(() -> System.out.println("kuuga"), 2, 1, TimeUnit.SECONDS);
        DaemonThread.await(5, TimeUnit.SECONDS);
    }

    @Test
    public void shutdown() {
        ThreadScheduledPool.shutdown();
    }

    @Test
    public void shutdownNow() {
        ThreadScheduledPool.shutdownNow();
    }

}