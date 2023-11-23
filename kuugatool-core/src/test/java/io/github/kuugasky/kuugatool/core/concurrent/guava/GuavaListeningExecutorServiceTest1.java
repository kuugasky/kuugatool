package io.github.kuugasky.kuugatool.core.concurrent.guava;

import com.google.common.util.concurrent.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * GuavaListeningExecutorServiceTest1
 *
 * @author kuuga
 * @since 2022/8/9 14:53
 */
public class GuavaListeningExecutorServiceTest1 {

    public static void main(String[] args) {
        ExecutorService delegate = Executors.newCachedThreadPool();
        ListeningExecutorService executorService = MoreExecutors.listeningDecorator(delegate);
        // 执行任务
        final ListenableFuture<Integer> listenableFuture = executorService.submit(() -> {
            System.out.println("新任务。。。");
            TimeUnit.SECONDS.sleep(1);
            return 7;
        });
        // 任务完成回掉函数
        final FutureCallback<Integer> futureCallback = new FutureCallback<>() {
            @Override
            public void onSuccess(Integer result) {
                System.out.println("任务执行成功，对任务进行操作。");
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("任务执行失败。");
            }
        };

        // 绑定任务以及回调函数
        Futures.addCallback(listenableFuture, futureCallback, delegate);
    }

}
