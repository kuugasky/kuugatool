package io.github.kuugasky.kuugatool.extra.concurrent;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.ListenableFuture;
import io.github.kuugasky.kuugatool.core.collection.ArrayUtil;
import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.concurrent.daemon.DaemonThread;
import io.github.kuugasky.kuugatool.core.concurrent.guava.GuavaThreadPoolWrapper;
import io.github.kuugasky.kuugatool.core.concurrent.pool.ThreadPool;
import io.github.kuugasky.kuugatool.core.concurrent.timer.ExecuteResult;
import io.github.kuugasky.kuugatool.core.concurrent.timer.TimerTask;
import io.github.kuugasky.kuugatool.core.date.DateUtil;
import io.github.kuugasky.kuugatool.core.enums.TaskSplitType;
import io.github.kuugasky.kuugatool.core.function.ThreadTaskFunc;
import io.github.kuugasky.kuugatool.core.lang.Console;
import io.github.kuugasky.kuugatool.core.object.MapperUtil;
import io.github.kuugasky.kuugatool.core.string.StringJoinerUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import io.github.kuugasky.kuugatool.extra.concurrent.config.SleuthThreadPoolConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.annotation.Nullable;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@SpringJUnitConfig(classes = SleuthThreadPoolConfig.class)
public class SleuthThreadPoolTest {

    @Test
    void toListeningExecutorService() {
        Thread hook = new Thread(SleuthThreadPool::shutdown);
        Runtime.getRuntime().addShutdownHook(hook);

        GuavaThreadPoolWrapper listeningExecutorService = SleuthThreadPool.toListeningExecutorService();

        // 提交任务
        ListenableFuture<Integer> future = listeningExecutorService.submit(() -> {
                    System.out.println(Thread.currentThread().getName() + " 开始耗时计算:" + DateUtil.todayTime());
                    Thread.sleep(5000);
                    System.out.println(Thread.currentThread().getName() + " 结束耗时计算:" + DateUtil.todayTime());
                    return 100;
                },
                // 注入监听
                () -> System.out.println(Thread.currentThread().getName() + " 任务监听")
        );

        ListenableFuture<Integer> future2 = listeningExecutorService.submit(() -> {
                    System.out.println(Thread.currentThread().getName() + " 开始耗时计算:" + DateUtil.todayTime());
                    Thread.sleep(5000);
                    System.out.println(Thread.currentThread().getName() + " 结束耗时计算:" + DateUtil.todayTime());
                    return 100;
                },
                // 注入监听
                () -> System.out.println(Thread.currentThread().getName() + " 任务监听")
        );

        // 异步任务回调
        FutureCallback<Integer> callback = new FutureCallback<>() {
            @Override
            public void onSuccess(@Nullable Integer result) {
                System.out.println(Thread.currentThread().getName() + " 成功，计算结果:" + result);
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println(Thread.currentThread().getName() + " 失败:" + t.getMessage());
            }
        };

        // 绑定future和callback回调
        System.out.println("future绑定回调-只是绑定，不是立即执行");
        listeningExecutorService.addCallback(future, callback);
        System.out.println("future2绑定回调-只是绑定，不是立即执行");
        listeningExecutorService.addCallback(future2, callback);

        // allAsList
        // List<ListenableFuture<Integer>> list = ListUtil.newArrayList(future, future2);
        // ListenableFuture<List<Integer>> listListenableFuture = listeningExecutorService.allAsList(list);
        // System.out.println("future&future2执行");
        // List<Integer> integers = listListenableFuture.get();
        // System.out.println(StringJoinerUtil.join(integers));

        System.out.println("main thread.....");

        DaemonThread.await(8, TimeUnit.SECONDS);
    }

    @Test
    public void invokeAllTaskNoReturnValue() {
        List<String> rangeString = ListUtil.createRangeString(1, 100);
        SleuthThreadPool.invokeAllTaskNoReturnValue(rangeString, TaskSplitType.DATA_SIZE, 1, list -> {
            String s = list.get(0);
            if (Integer.parseInt(s) % 2 != 0) {
                throw new RuntimeException(String.format("%s不能被2整除.", s));
            }
        });
    }

    @Test
    void testToListeningExecutorService() throws ExecutionException, InterruptedException {
        GuavaThreadPoolWrapper guavaThreadPoolWrapper = SleuthThreadPool.toListeningExecutorService();
        ListenableFuture<String> future = guavaThreadPoolWrapper.submit(
                () -> "kuuga",
                () -> System.out.println("task isDone."));

        FutureCallback<String> callback = new FutureCallback<>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("success");
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("error:" + t.getMessage());
            }
        };

        guavaThreadPoolWrapper.addCallback(future, callback);

        List<String> objects = guavaThreadPoolWrapper.allAsList(future).get();
        System.out.println(StringJoinerUtil.join(objects));
    }

    @Test
    void execute() {
        SleuthThreadPool.execute(() -> System.out.println("kuuga"));
    }

    @Test
    void submit() {
        SleuthThreadPool.submit(() -> System.out.println("kuuga"));
    }

    @Test
    void testSubmit() throws ExecutionException, InterruptedException {
        Future<String> submit = SleuthThreadPool.submit(() -> "kuuga");
        System.out.println(submit.get());
    }

    @Test
    void testSubmit3() throws ExecutionException, InterruptedException {
        Future<String> submit = SleuthThreadPool.submit(() -> {
            // int i = 1 / 0;
            return "kuuga";
        }, null, error -> System.out.println("任务异常：" + error.getMessage()));
        System.out.println(submit.get());
    }

    @Test
    void testSubmit3_1() throws ExecutionException, InterruptedException {
        Future<String> submit = SleuthThreadPool.submit(() -> {
            // int i = 1 / 0;
            return "kuuga";
        }, error -> System.out.println("任务异常：" + error.getMessage()));
        System.out.println(submit.get());
    }

    @Test
    void testSubmit4() throws ExecutionException, InterruptedException {
        Runnable runnable = () -> {
            // int i = 1 / 0;
            System.out.println("xxx");
        };
        Future<?> submit = SleuthThreadPool.submit(runnable, () -> System.out.println("任务正常完成"), error -> System.out.println("任务异常：" + error.getMessage()));
        System.out.println(submit.get());
    }

    @Test
    void testSubmit1() throws ExecutionException, InterruptedException, TimeoutException {
        Future<String> submit = SleuthThreadPool.submit(() -> System.out.println("kuuga"), "success");
        System.out.println(StringUtil.repeatNormal());
        String s = submit.get(1, TimeUnit.SECONDS);
        System.out.println(s);
    }

    @Test
    void invokeAllTask() {
        // init data
        List<KuugaModel> sourceList = ListUtil.newArrayList();
        for (int i = 0; i < 10; i++) {
            sourceList.add(KuugaModel.builder().name("kuuga" + i).sex(i).build());
        }

        ThreadTaskFunc<KuugaModel, KuugaDTO> function = list -> () -> {
            if (list.size() == 2) {
                throw new RuntimeException("报错了");
            }
            return MapperUtil.copy(list, KuugaDTO.class);
        };

        List<KuugaDTO> kuugaDTOS = SleuthThreadPool.invokeAllTask(sourceList, TaskSplitType.TASK_AMOUNT, 3, function);
        ListUtil.optimize(kuugaDTOS).forEach(dto -> System.out.println(StringUtil.formatString(dto)));
    }

    @Test
    void testInvokeAllTask1() {
        List<Callable<List<KuugaModel>>> tasks = ListUtil.newArrayList();

        // init data
        List<KuugaModel> sourceList = ListUtil.newArrayList();
        for (int i = 0; i < 10; i++) {
            sourceList.add(KuugaModel.builder().name("kuuga" + i).sex(i).build());
        }

        List<List<KuugaModel>> lists = ListUtil.splitList(sourceList, 3);
        ListUtil.optimize(lists).forEach(list -> {
            List<KuugaModel> result = ListUtil.newArrayList();
            Callable<List<KuugaModel>> callable = () -> {
                list.forEach(kuugaModel -> {
                    kuugaModel.setName("new" + kuugaModel.getName());
                    result.add(kuugaModel);
                });
                return result;
            };
            tasks.add(callable);
            ThreadPool.submit(callable);
        });

        List<List<KuugaModel>> lists1 = SleuthThreadPool.invokeAllTask(tasks);
        List<KuugaModel> sum = ListUtil.summary(lists1);
        ListUtil.optimize(sum).forEach(kuugaModel -> System.out.println(StringUtil.formatString(kuugaModel)));
    }

    @Test
    void testInvokeAllTaskNoReturnValue() {
        List<Integer> list = ListUtil.newArrayList();
        for (int i = 0; i < 10000; i++) {
            list.add(i);
        }
        AtomicLong size = new AtomicLong();
        SleuthThreadPool.invokeAllTaskNoReturnValue(list, TaskSplitType.DATA_SIZE, 100, items -> {
            synchronized (SleuthThreadPoolTest.class) {
                Console.redLog(size.getAndIncrement());
                System.out.println(ArrayUtil.toString(items));
            }
        });
    }

    @Test
    void testInvokeAllTaskNoReturnValue1() {
        List<String> rangeString = ListUtil.createRangeString(1, 100);
        SleuthThreadPool.invokeAllTaskNoReturnValue(rangeString, TaskSplitType.DATA_SIZE, 1, list -> {
            String s = list.get(0);
            if (Integer.parseInt(s) % 2 != 0) {
                throw new RuntimeException(String.format("%s不能被2整除.", s));
            }
        });
    }

    @Test
    void testSubmit2() {
        // 定义任务
        Runnable task = () -> {
            log.info("I am very good.");
            try {
                TimeUnit.SECONDS.sleep(4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // throw new RuntimeException("throw new RuntimeException.");
        };
        TimerTask<Runnable> timerTask = TimerTask.create(task).timeout(3);
        timerTask.name("Kuuga thread");
        timerTask.errorHandler((name, task1, cause) -> log.error("定时任务执行异常", cause));
        timerTask.callback((taskName, task1, result) -> {
            if (result == ExecuteResult.OK) {
                log.info(taskName + " ===任务执行成功==> " + result);
            } else if (result == ExecuteResult.TIMEOUT) {
                log.info(taskName + " ===任务执行超时==> " + result);
            } else if (result == ExecuteResult.ERROR) {
                log.info(taskName + " ===任务执行异常==> " + result);
            }
        });
        ThreadPool.submit(timerTask);
        ThreadPool.shutdown();
    }

    @Test
    void shutdown() {
        SleuthThreadPool.shutdown();
    }

    @Test
    void shutdownNow() {
        SleuthThreadPool.shutdownNow();
    }

    @Test
    void Kuuga() throws ExecutionException, InterruptedException {
        GuavaThreadPoolWrapper guavaThreadPoolWrapper = SleuthThreadPool.toListeningExecutorService();

        List<ListenableFuture<Integer>> futures = ListUtil.newArrayList();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            ListenableFuture<Integer> future = guavaThreadPoolWrapper.submit(() -> finalI, () -> System.out.println(Thread.currentThread().getName() + " 任务监听完成"));
            FutureCallback<Integer> callback = new FutureCallback<>() {
                @Override
                public void onSuccess(Integer result) {
                    System.out.println(Thread.currentThread().getName() + " success,result=" + result);
                }

                @Override
                public void onFailure(Throwable t) {
                    System.out.println(Thread.currentThread().getName() + " error,message=" + t.getMessage());
                }
            };
            guavaThreadPoolWrapper.addCallback(future, callback);
            futures.add(future);
        }
        List<Integer> integers = guavaThreadPoolWrapper.successfulAsList(futures).get();
        System.out.println(StringJoinerUtil.join(integers));
    }

    @Test
    void submit2() {
        // 定义任务
        Runnable task = () -> {
            System.out.println("I am very good.");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            throw new RuntimeException("throw new RuntimeException.");
        };

        TimerTask<Runnable> timerTask = TimerTask.create(task).timeout(3);
        timerTask.name("【Kuuga thread】");
        timerTask.errorHandler((name, task1, cause) -> log.error("定时任务执行异常", cause));
        timerTask.callback((taskName, task1, result) -> {
            if (result == ExecuteResult.OK) {
                System.out.println(taskName + " ===任务执行成功==> " + result);
            } else if (result == ExecuteResult.TIMEOUT) {
                System.out.println(taskName + " ===任务执行超时==> " + result);
            } else if (result == ExecuteResult.ERROR) {
                System.out.println(taskName + " ===任务执行异常==> " + result);
            }
        });
        SleuthThreadPool.submit(timerTask);
        DaemonThread.await();
    }

}