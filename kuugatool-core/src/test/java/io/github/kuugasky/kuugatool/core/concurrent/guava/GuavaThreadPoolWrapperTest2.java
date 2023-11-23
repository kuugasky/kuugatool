package io.github.kuugasky.kuugatool.core.concurrent.guava;

import com.google.common.util.concurrent.*;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * GuavaListeningExecutorServiceTest2
 *
 * @author kuuga
 * @since 2022/8/9 15:07
 */
public class GuavaThreadPoolWrapperTest2 {

    // 线程池中线程个数
    private static final int POOL_SIZE = 50;
    // 带有回调机制的线程池
    private static final ListeningExecutorService service =
            MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(POOL_SIZE));

    @Test
    public void testListenableFuture() {
        final List<String> value = Collections.synchronizedList(new ArrayList<>());
        try {
            List<ListenableFuture<String>> futures = new ArrayList<>();
            // 将实现了callable的任务放入到线程池中，得到一个带有回调机制的ListenableFuture实例，
            // 通过Futures.addCallback方法对得到的ListenableFuture实例进行监听，一旦得到结果就进入到onSuccess方法中，
            // 在onSuccess方法中将查询的结果存入到集合中
            for (int i = 0; i < 1; i++) {
                final int index = i;
                if (i == 9) {
                    Thread.sleep(500 * i);
                }
                ListenableFuture<String> listenableFuture = service
                        .submit(() -> {
                            long time = System.currentTimeMillis();
                            TimeUnit.SECONDS.sleep(3);
                            System.out.println(StringUtil.format("Finishing sleeping task{}: {}", index, time));
                            return String.valueOf(time);
                        });

                listenableFuture.addListener(() -> System.out.println(StringUtil.format("Listener be triggered for task{}.", index)), service);

                // FutureCallback
                FutureCallback<String> futureCallback = new FutureCallback<>() {
                    public void onSuccess(String result) {
                        System.out.println(StringUtil.format("Add result value into value list {}.", result));
                        value.add(result);
                    }

                    public void onFailure(Throwable t) {
                        System.out.println(StringUtil.format("Add result value into value list error.", t));
                        throw new RuntimeException(t);
                    }
                };

                Futures.addCallback(listenableFuture, futureCallback, service
                );
                // 将每一次查询得到的ListenableFuture放入到集合中
                futures.add(listenableFuture);
            }

            // 这里将集合中的若干ListenableFuture形成一个新的ListenableFuture
            // 目的是为了异步阻塞，直到所有的ListenableFuture都得到结果才继续当前线程
            // 这里的时间取的是所有任务中用时最长的一个
            ListenableFuture<List<String>> allAsList = Futures.allAsList(futures);
            allAsList.get();
            System.out.println(StringUtil.format("All sub-task are finished."));
        } catch (Exception ignored) {
        }
    }

    @Test
    public void testCompletableFuture() throws Exception {
        // ...
    }

}
