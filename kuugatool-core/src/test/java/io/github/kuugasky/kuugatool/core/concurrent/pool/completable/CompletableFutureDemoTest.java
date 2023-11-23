package io.github.kuugasky.kuugatool.core.concurrent.pool.completable;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.concurrent.pool.CompletableFuturePool;
import io.github.kuugasky.kuugatool.core.enums.TaskSplitType;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * CompletableFutureDemo
 * 用法演示
 *
 * @author kuuga
 * @since 2023/5/10-05-10 16:49
 */
class CompletableFutureDemoTest {

    /**
     * 提交异步任务
     */
    @Test
    void supplyAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            // 模拟长时间任务
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Long time task finished";
        });
        System.out.println(future.get());
    }

    /**
     * 提交异步任务，任务超时未完成抛出异常
     * <p>
     * 任务超时机制
     * <p>
     * 有时候我们需要对任务加上一个超时机制，以防止任务因为某些原因无法正常结束，导致程序长时间阻塞。<br>
     * CompletableFuture 类提供了 completeOnTimeout 方法，可以在任务等待超过指定时长后返回一个默认值作为结果。<br>
     * 示例如下：
     */
    @Test
    void completeOnTimeout() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            // 模拟长时间任务
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Long time task finished";
        }).completeOnTimeout("Task is timeout", 3000L, TimeUnit.MILLISECONDS);

        System.out.println(future.get());  // 输出 Time out
    }

    /**
     * 异常处理机制
     * <p>
     * 在 CompletableFuture 的异步执行过程中，可能会出现异常，这时候需要对异常进行特殊处理。<br>
     * CompletableFuture 类提供了一系列方法，如 exceptionally、handle、whenComplete等，可以对异常进行处理。
     * <p>
     * 其中，
     * exceptionally 方法可以在任务抛出异常后返回一个默认值作为结果。<br>
     * handle 方法类似于 exceptionally 方法，但可以将异常和正常结果分别处理，可以通过 isCompletedExceptionally 方法来判断任务是否出现异常。<br>
     * whenComplete 方法可以在任务完成后进行一些操作，无论任务是否出现异常。
     * 示例如下：
     */
    @Test
    void exceptionHandler() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> 10 / 0);
        // 任务异常时回调
        future.exceptionally(ex -> {
            System.out.println(ex.getMessage());  // 输出 / by zero
            return -1;
        });

        Integer integer = future.get();
        System.out.println(integer);

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> 10 / 0);
        future2.handle((res, ex) -> {
            // 任务完成时，异常情况下返回
            if (ex != null) {
                System.out.println(ex.getMessage());  // 输出 / by zero
                return -1;
            }
            // 任务完成时，正常情况下返回
            return res;
        });

        CompletableFuture<Integer> future3 = CompletableFuture.supplyAsync(() -> 10 / 2);
        // 方法可以在任务完成后进行一些操作，无论任务是否出现异常。
        future3.whenComplete((res, ex) -> {
            if (ex != null) {
                System.out.println(ex.getMessage());  // 不会输出
            } else {
                System.out.println("Result: " + res);  // 输出 Result: 5
            }
        });
    }

    @Test
    void invokeAllTaskNoReturnValue2() {
        List<String> rangeString = ListUtil.createRangeString(1, 100);
        CompletableFuturePool.invokeAllTaskNoReturnValue(rangeString, TaskSplitType.DATA_SIZE, 1, list -> {
            String s = list.get(0);
            if (Integer.parseInt(s) % 2 != 0) {
                throw new RuntimeException(String.format("%s不能被2整除.", s));
            }
        }, (list, callable, throwable) -> System.out.println(throwable.getMessage()));
    }

}
