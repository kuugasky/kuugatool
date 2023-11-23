package io.github.kuugasky.kuugatool.core.concurrent.high;

import io.github.kuugasky.kuugatool.core.entity.KuugaModel;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class GlobalThreadPoolTest {

    @Test
    public void init() {
        // 初始化全局线程池
        GlobalThreadPool.init();
        System.out.println(GlobalThreadPool.getExecutor());
        GlobalThreadPool.init();
        System.out.println(GlobalThreadPool.getExecutor());
    }

    @Test
    public void shutdown() {
        // 立即关闭全局线程池
        GlobalThreadPool.shutdown(true);
    }

    @Test
    public void getExecutor() {
        // GlobalThreadPool.init();
        ExecutorService executor = GlobalThreadPool.getExecutor();
        System.out.println(executor);
    }

    @Test
    public void execute() {
        GlobalThreadPool.execute(() -> System.out.println("kuuga"));
    }

    @Test
    public void submit() throws ExecutionException, InterruptedException {
        Callable<KuugaModel> task = () -> KuugaModel.builder().build();
        Future<KuugaModel> submit = GlobalThreadPool.submit(task);
        System.out.println(StringUtil.formatString(submit.get()));
    }

    @Test
    public void testSubmit() {
        GlobalThreadPool.submit(() -> System.out.println("kuuga"));
    }
}