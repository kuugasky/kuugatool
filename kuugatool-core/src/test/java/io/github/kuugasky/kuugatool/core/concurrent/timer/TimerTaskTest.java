package io.github.kuugasky.kuugatool.core.concurrent.timer;

import io.github.kuugasky.kuugatool.core.concurrent.daemon.DaemonThread;
import io.github.kuugasky.kuugatool.core.concurrent.pool.ThreadPool;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

@Slf4j
class TimerTaskTest {

    @Test
    void task() {
        // 定义任务
        Runnable task = () -> {
            System.out.println("I am very good.");
            try {
                int i = 1 / 0;
                TimeUnit.SECONDS.sleep(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // throw new RuntimeException("throw new RuntimeException.");
        };
        TimerTask<Runnable> timerTask = TimerTask.create(task).timeout(3);
        timerTask.name("Kuuga thread");
        timerTask.errorHandler((name, task1, cause) -> {
            System.out.println("定时任务执行异常 " + String.format("name:%s, task1:%s", name, task1.toString()));
            System.out.println("定时任务执行异常" + cause);
        });
        timerTask = timerTask.callback((taskName, task1, result) -> {
            if (result == ExecuteResult.OK) {
                System.out.println(taskName + " ===任务执行成功==> " + result);
            } else if (result == ExecuteResult.TIMEOUT) {
                System.out.println(taskName + " ===任务执行超时==> " + result);
            } else if (result == ExecuteResult.ERROR) {
                System.out.println(taskName + " ===任务执行异常==> " + result);
            }
        });
        ThreadPool.submit(timerTask);
        DaemonThread.await(5, TimeUnit.SECONDS);

        ThreadPool.shutdown();
    }
}