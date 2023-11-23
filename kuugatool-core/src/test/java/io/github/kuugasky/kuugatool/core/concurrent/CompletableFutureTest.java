package io.github.kuugasky.kuugatool.core.concurrent;

import io.github.kuugasky.kuugatool.core.string.StringUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

/**
 * CompletableFutureTest
 *
 * @author kuuga
 * @since 2022/10/13-10-13 16:31
 */
@Slf4j
public class CompletableFutureTest {

    private final ExecutorService scanTestExecutor = new ThreadPoolExecutor(6, 12,
            1, TimeUnit.SECONDS, new LinkedBlockingDeque<>(10000), Executors.defaultThreadFactory(),
            // 当触发拒绝策略，只要线程池没有关闭的话，丢弃阻塞队列 workQueue 中最老的一个任务，并将新任务加入
            new ThreadPoolExecutor.DiscardOldestPolicy());

    @Test
    void test() throws ExecutionException, InterruptedException {
        Kuuga Kuuga = new Kuuga();
        // 并行请求
        CompletableFuture<String> nameFuture = CompletableFuture.supplyAsync(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return "kuuga";

                }, scanTestExecutor)
                .exceptionally(e -> {
                    log.error("aliScan method error:" + e.getMessage(), e);
                    return null;
                });
        CompletableFuture<Integer> ageFuture = CompletableFuture.supplyAsync(() -> 1, scanTestExecutor)
                .exceptionally(e -> {
                    log.error("kfangScan method error:" + e.getMessage(), e);
                    return null;
                });

        nameFuture.thenAccept(Kuuga::setName);
        ageFuture.thenAccept(Kuuga::setAge);

        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(nameFuture, ageFuture);
        voidCompletableFuture.get();
        System.out.println(StringUtil.formatString(Kuuga));
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxx");
    }

    @Test
    void xx() {
        // 实例化一个CompletableFuture,返回值是Integer
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            // 返回null
            return null;
        });

        CompletableFuture<String> exceptionally = future.thenApply(result -> {
            // 制造一个空指针异常NPE
            return result;
        }).thenApply(result -> {
            // 这里不会执行，因为上面出现了异常
            return "现在是" + result + "点钟";
        }).exceptionally(error -> {
            // 我们选择在这里打印出异常
            error.printStackTrace();
            // 并且当异常发生的时候，我们返回一个默认的文字
            return "出错啊~";
        });

        exceptionally.thenAccept(System.out::println);

    }

    @Data
    static class Kuuga {
        private String name;
        private int age;
    }

}
