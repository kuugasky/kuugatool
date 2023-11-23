package io.github.kuugasky.kuugatool.core.concurrent.guava;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.ListenableFuture;
import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.concurrent.ThreadPoolUtil;
import io.github.kuugasky.kuugatool.core.concurrent.daemon.DaemonThread;
import io.github.kuugasky.kuugatool.core.concurrent.pool.ThreadPool;
import io.github.kuugasky.kuugatool.core.entity.KuugaModel;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import io.github.kuugasky.kuugatool.core.string.StringJoinerUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.concurrent.*;

class GuavaThreadPoolWrapperTest {

    /**
     * 线程池
     */
    static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 10, 60,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(200),
            new ThreadPoolExecutor.CallerRunsPolicy()
    );

    @Test
    void build() {
        GuavaThreadPoolWrapper build = GuavaThreadPoolWrapper.build(threadPoolExecutor);
        System.out.println(StringUtil.formatString(build));
    }

    @Test
    void submit() {
        GuavaThreadPoolWrapper build = GuavaThreadPoolWrapper.build(threadPoolExecutor);
        Runnable runnable = () -> System.out.println("kuuga");
        Runnable listener = () -> System.out.println("任务isDone.");
        build.submit(runnable, listener);
    }

    @Test
    void testSubmit() throws ExecutionException, InterruptedException {
        GuavaThreadPoolWrapper build = GuavaThreadPoolWrapper.build(threadPoolExecutor);
        Callable<Object> callable = () -> "kuuga";
        Runnable runnable = () -> System.out.println("任务isDone.");
        ListenableFuture<Object> submit = build.submit(callable, runnable);
        Object o = submit.get();
        System.out.println(o);

    }

    @Test
    void testSubmit1() {
        KuugaModel kuugaModel = new KuugaModel();
        GuavaThreadPoolWrapper build = GuavaThreadPoolWrapper.build(threadPoolExecutor);
        Runnable runnable = () -> System.out.println("kuuga");
        Runnable listener = () -> System.out.println("任务isDone.");
        build.submit(runnable, kuugaModel, listener);
    }

    @Test
    void invokeAll() throws InterruptedException {
        Collection<Callable<String>> tasks = ListUtil.newArrayList();
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            tasks.add(() -> finalI + "-guava");
        }
        GuavaThreadPoolWrapper build = GuavaThreadPoolWrapper.build(threadPoolExecutor);
        List<Future<String>> futures = build.invokeAll(tasks);

        futures.forEach(future -> {
            try {
                String s = future.get();
                System.out.println(s);
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void testInvokeAll() throws InterruptedException {
        Collection<Callable<String>> tasks = ListUtil.newArrayList();
        for (int i = 0; i < 5; i++) {
            int finalI = i;
            tasks.add(() -> {
                TimeUnit.SECONDS.sleep(1);
                return finalI + StringUtil.EMPTY;
            });
        }
        GuavaThreadPoolWrapper build = GuavaThreadPoolWrapper.build(threadPoolExecutor);
        List<Future<String>> futures = build.invokeAll(tasks, 2, TimeUnit.SECONDS);

        futures.forEach(future -> {
            try {
                String s = future.get();
                System.out.println(s);
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Test
    void addCallback() {
        GuavaThreadPoolWrapper build = GuavaThreadPoolWrapper.build(threadPoolExecutor);

        ListenableFuture<?> future = build.submit(() -> {
            int i = 1 / 0;
            System.out.println(i);
            System.out.println("kuuga");
        });

        FutureCallback<Object> futureCallback = new FutureCallback<>() {
            @Override
            public void onSuccess(Object result) {
                System.out.println("success");
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("failure:" + t.getMessage());
            }
        };
        // first execute
        build.addCallback(future, futureCallback);
        // second execute
        build.addCallback(future, futureCallback, threadPoolExecutor);
    }

    @Test
    void testAddCallback() {
    }

    @Test
    void allAsList() throws ExecutionException, InterruptedException {
        // ExecutorService executorService = Executors.newCachedThreadPool();
        ExecutorService executorService = ThreadPoolUtil.build();
        GuavaThreadPoolWrapper guavaThreadPoolWrapper = GuavaThreadPoolWrapper.build();

        final List<Long> value = new ArrayList<>();

        List<ListenableFuture<Long>> futures = new ArrayList<>();
        for (long i = 1; i <= 3; i++) {
            // 处理线程逻辑
            final ListenableFuture<Long> listenableFuture = guavaThreadPoolWrapper.submit(new AddCallable(1000000 * (i - 1) + 1, i * 1000000));
            // 回调方法
            FutureCallback<Long> callback = new FutureCallback<>() {
                @Override
                public void onSuccess(Long result) {
                    value.add(result);
                }

                @Override
                public void onFailure(Throwable t) {
                    t.printStackTrace();
                }
            };
            listenableFuture.addListener(() -> System.out.println("task is done."), executorService);
            guavaThreadPoolWrapper.addCallback(listenableFuture, callback);
            futures.add(listenableFuture);
        }
        // 阻塞三个线程执行完成
        ListenableFuture<List<Long>> listListenableFuture = guavaThreadPoolWrapper.allAsList(futures);
        List<Long> longs = listListenableFuture.get();

        String successResultJoin = StringJoinerUtil.join(value);
        String guavaResultJoin = StringJoinerUtil.join(longs);
        System.out.println("successResultJoin:" + successResultJoin);
        System.out.println("guavaResultJoin:" + guavaResultJoin);
        executorService.shutdownNow();
    }

    @Test
    void successfulAsList() throws ExecutionException, InterruptedException {
        GuavaThreadPoolWrapper build = GuavaThreadPoolWrapper.build();

        List<ListenableFuture<Integer>> list = ListUtil.newArrayList();
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            ListenableFuture<Integer> future = build.submit(() -> {
                if (finalI == 50) {
                    int x = 1 / 0;
                }
                return finalI;
            });
            list.add(future);
        }

        // ListenableFuture<List<@Nullable Integer>> listListenableFuture = build.allAsList(list);
        // // .get()报错[/ by zero]
        // List<@Nullable Integer> integers = listListenableFuture.get();
        // System.out.println(integers.size());
        // IntSummaryStatistics intSummaryStatistics = integers.stream().mapToInt(x -> x).summaryStatistics();
        // System.out.println(intSummaryStatistics.getSum());
        // System.out.println(integers.size());
        //
        // System.out.println(StringUtil.repeatNormal());

        ListenableFuture<List<@Nullable Integer>> listListenableFutureX = build.successfulAsList(list);
        List<@Nullable Integer> integersX = listListenableFutureX.get();
        System.out.println(integersX.size());
        System.out.println("null count:" + integersX.stream().filter(ObjectUtil::isNull).count());
        IntSummaryStatistics intSummaryStatisticsX = integersX.stream().mapToInt(x -> x).summaryStatistics();
        // 4950
        System.out.println(intSummaryStatisticsX.getSum());
        System.out.println(integersX.size());
    }

    @Test
    void transformAsync() throws ExecutionException, InterruptedException {
        GuavaThreadPoolWrapper build = GuavaThreadPoolWrapper.build(threadPoolExecutor);
        ListenableFuture<Integer> future = build.submit(() -> 1);

        ListenableFuture<String> transform = build.transformAsync(future, input -> build.submit(() -> input + " is good."));
        ListenableFuture<String> transform2 = build.transformAsyncBySync(future, input -> String.format("value = %s", input));

        String s = transform.get();
        System.out.println(s);
        String s2 = transform2.get();
        System.out.println(s2);
    }

    @Test
    void listenInPoolThread() throws ExecutionException, InterruptedException {
        ThreadPoolExecutor threadPoolExecutor1 = threadPoolExecutor;
        GuavaThreadPoolWrapper build = GuavaThreadPoolWrapper.build(threadPoolExecutor1);

        Future<?> future = ThreadPool.submit(() -> "kuuga");

        ListenableFuture<?> listenableFuture = build.listenInPoolThread(future);

        listenableFuture.addListener(() -> System.out.println("task start."), threadPoolExecutor1);

        System.out.println(future.getClass());
        System.out.println(future.get());
        // System.out.println(listenableFuture.get());
        DaemonThread.await(5, TimeUnit.SECONDS);
    }

    @Test
    void transform() throws ExecutionException, InterruptedException {
        GuavaThreadPoolWrapper build = GuavaThreadPoolWrapper.build(threadPoolExecutor);
        ListenableFuture<Integer> future = build.submit(() -> 1);

        ListenableFuture<String> transform = build.transform(future, input -> String.format("value = %s", input));

        String s = transform.get();
        System.out.println(s);
    }
}

/**
 * 累加线程
 */
class AddCallable implements Callable<Long> {
    private final long begin;
    private final long end;

    public AddCallable(long begin, long end) {
        this.begin = begin;
        this.end = end;
    }

    @Override
    public Long call() {
        long result = 0;
        for (long i = begin; i <= end; i++) {
            result += i;
        }
        System.out.println(Thread.currentThread().getName());
        return result;
    }
}