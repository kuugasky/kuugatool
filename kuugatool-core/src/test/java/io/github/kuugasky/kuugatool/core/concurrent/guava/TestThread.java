package io.github.kuugasky.kuugatool.core.concurrent.guava;

import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.*;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * TestThread
 *
 * @author kuuga
 * @since 2022/8/10 10:40
 */
public class TestThread {
    public static void main(String[] args) throws InterruptedException {
        List<String> result = new ArrayList<>();
        List<String> list = new ArrayList<>();

        // 模拟原始数据
        for (int i = 0; i < 1211; i++) {
            list.add(i + "-");
            System.out.println("添加原始数据:" + i);
        }

        int size = 50;// 切分粒度，每size条数据，切分一块，交由一条线程处理
        int countNum = 0;// 当前处理到的位置
        int count = list.size() / size;// 切分块数
        int threadNum = 0;// 使用线程数
        if (count * size != list.size()) {
            count++;
        }

        final CountDownLatch countDownLatch = new CountDownLatch(count);

        // 使用Guava的ListeningExecutorService装饰线程池
        ListeningExecutorService executorService = MoreExecutors.listeningDecorator(GuavaThreadPoolWrapperTest.threadPoolExecutor);

        while (countNum < count * size) {
            // 切割不同的数据块，分段处理
            threadNum++;
            countNum += size;
            MyCallable myCallable = new MyCallable();
            myCallable.setList(ImmutableList.copyOf(list.subList(countNum - size, Math.min(list.size(), countNum))));

            ListenableFuture<List<String>> listenableFuture = executorService.submit(myCallable);

            // 回调函数
            Futures.addCallback(listenableFuture, new FutureCallback<>() {
                // 任务处理成功时执行
                @Override
                public void onSuccess(List<String> list) {
                    countDownLatch.countDown();
                    System.out.println("第h次处理完成");
                    result.addAll(list);
                }

                // 任务处理失败时执行
                @Override
                public void onFailure(@NonNull Throwable throwable) {
                    countDownLatch.countDown();
                    System.out.println("处理失败：" + throwable);
                }
            }, GuavaThreadPoolWrapperTest.threadPoolExecutor);

        }

        // 设置时间，超时了直接向下执行，不再阻塞
        countDownLatch.await(100, TimeUnit.SECONDS);

        result.forEach(System.out::println);
        System.out.println("------------结果处理完毕，返回完毕,使用线程数量：" + threadNum);

        System.out.println("处理完了");
    }
}

class MyCallable implements Callable<List<String>> {

    private List<String> list;

    @Override
    public List<String> call() {
        List<String> listReturn = new ArrayList<>();
        // 模拟对数据处理，然后返回
        for (String s : list) {
            listReturn.add(s + "：处理时间：" + System.currentTimeMillis() + "---:处理线程：" + Thread.currentThread());
        }

        return listReturn;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}