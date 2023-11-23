package io.github.kuugasky.kuugatool.core.concurrent;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import io.github.kuugasky.kuugatool.core.collection.ArrayUtil;
import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.concurrent.daemon.DaemonThread;
import io.github.kuugasky.kuugatool.core.concurrent.pool.ThreadPool;
import io.github.kuugasky.kuugatool.core.date.DateUtil;
import io.github.kuugasky.kuugatool.core.entity.KuugaDTO;
import io.github.kuugasky.kuugatool.core.entity.KuugaModel;
import io.github.kuugasky.kuugatool.core.enums.TaskSplitType;
import io.github.kuugasky.kuugatool.core.function.ThreadTaskFunc;
import io.github.kuugasky.kuugatool.core.lang.Console;
import io.github.kuugasky.kuugatool.core.object.MapperUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

class ThreadPoolTest {

    private static List<String> list;

    @BeforeAll
    public static void before() {
        list = ListUtil.newArrayList(10);
        for (int i = 0; i < 10; i++) {
            list.add(i + StringUtil.EMPTY);
        }
    }

    @Test
    void test() {
        // 创建存储任务的容器
        List<Callable<List<String>>> tasks = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            int finalI = i;
            Callable<List<String>> areaUnlimitedTask = () -> {
                List<String> callableResultList = ListUtil.newArrayList();
                callableResultList.add(list.get(finalI) + "_kuuga");

                System.out.println("------->" + ThreadPool.poolTaskCount());
                System.out.println("=======>" + ThreadPool.poolInfo());

                return callableResultList;
            };
            // 先把任务提交到线程池
            ThreadPool.submit(areaUnlimitedTask);
            tasks.add(areaUnlimitedTask);
        }
        // 调用所有任务，得到所有结果集，回归主线程
        List<List<String>> lists = ThreadPool.invokeAllTask(tasks);
        System.out.println(lists);

        Future<?> test = ThreadPool.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("test");
        });
        try {
            System.out.println(test.isDone());
            // test.get(1, TimeUnit.SECONDS);
            test.cancel(true);
            System.out.println(test.isCancelled());
            if (!test.isCancelled()) {
                test.get();
                System.out.println(test.isDone());
            }
        } catch (InterruptedException | ExecutionException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Test
    void call() throws ExecutionException, InterruptedException {
        Callable<String> task = () -> "kuugasky";
        Future<String> Kuuga = ThreadPool.call(task);
        String s = Kuuga.get();
        System.out.println(s);
    }

    @Test
    void execute() {
        ThreadPool.execute(() -> System.out.println("kuuga"));
    }

    @Test
    void submit() {
        ThreadPool.submit(() -> System.out.println("kuuga"));
    }

    @Test
    void testSubmit() throws ExecutionException, InterruptedException {
        Callable<KuugaModel> task = () -> KuugaModel.builder().name("kuuga").sex(1).build();
        KuugaModel kuugaModel = ThreadPool.submit(task).get();
        System.out.println(StringUtil.formatString(kuugaModel));
    }

    @Test
    void testSubmit1() throws ExecutionException, InterruptedException {
        List<String> list = ListUtil.newArrayList();
        Future<List<String>> submit = ThreadPool.submit(() -> {
            for (int i = 0; i < 10; i++) {
                list.add(i + ".Kuuga");
            }
        }, list);
        List<String> strings = submit.get();
        System.out.println(Arrays.toString(strings.toArray()));
    }


    @Test
    void testSubmit2() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

            }
        };
        ThreadPool.submit(timerTask);
    }

    @Test
    void invokeAllTask() {
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

        List<List<KuugaModel>> lists1 = ThreadPool.invokeAllTask(tasks);
        List<KuugaModel> sum = ListUtil.summary(lists1);
        ListUtil.optimize(sum).forEach(kuugaModel -> System.out.println(StringUtil.formatString(kuugaModel)));
    }

    @Test
    void invokeAllTask1() {
        // init data
        List<KuugaModel> sourceList = ListUtil.newArrayList();
        for (int i = 0; i < 10; i++) {
            sourceList.add(KuugaModel.builder().name("kuuga" + i).sex(i).build());
        }

        ThreadTaskFunc<KuugaModel, KuugaDTO> function = list -> () -> MapperUtil.copy(list, KuugaDTO.class);

        List<KuugaDTO> kuugaDTOS = ThreadPool.invokeAllTask(sourceList, TaskSplitType.TASK_AMOUNT, 3, function);
        ListUtil.optimize(kuugaDTOS).forEach(dto -> System.out.println(StringUtil.formatString(dto)));
    }

    @Test
    void invokeAllTask2() {
        // init data
        List<KuugaModel> sourceList = ListUtil.newArrayList();
        for (int i = 0; i < 10; i++) {
            sourceList.add(KuugaModel.builder().name("kuuga" + i).sex(i).build());
        }

        ThreadTaskFunc<KuugaModel, String> function = list -> () -> {
            List<String> result = ListUtil.newArrayList();
            list.forEach(kuugaModel -> result.add(kuugaModel.getName()));
            return result;
        };

        List<String> kuugaDTOS = ThreadPool.invokeAllTask(sourceList, TaskSplitType.TASK_AMOUNT, 3, function);
        ListUtil.optimize(kuugaDTOS).forEach(dto -> System.out.println(StringUtil.formatString(dto)));
    }


    @Test
    void shutdown() {
        ThreadPool.shutdown();
    }

    @Test
    void shutdownNow() {
        ThreadPool.shutdownNow();
    }

    @Test
    void invokeAllTaskNoReturnValue() {
        List<Integer> list = ListUtil.newArrayList();
        for (int i = 0; i < 10000; i++) {
            list.add(i);
        }
        AtomicLong size = new AtomicLong();
        ThreadPool.invokeAllTaskNoReturnValue(list, TaskSplitType.DATA_SIZE, 100, items -> {
            synchronized (ThreadPoolTest.class) {
                Console.redLog(size.getAndIncrement());
                System.out.println(ArrayUtil.toString(items));
            }
        });
    }

    @Test
    void invokeAllTaskNoReturnValue2() {
        List<String> rangeString = ListUtil.createRangeString(1, 100);
        ThreadPool.invokeAllTaskNoReturnValue(rangeString, TaskSplitType.DATA_SIZE, 1, list -> {
            String s = list.get(0);
            if (Integer.parseInt(s) % 2 != 0) {
                throw new RuntimeException(String.format("%s不能被2整除.", s));
            }
        }, (list, callable, throwable) -> System.out.println(throwable.getMessage()));
    }

    @Test
    void toListeningExecutorService() {
        ListeningExecutorService listeningExecutorService = ThreadPool.toListeningExecutorService();

        ListenableFuture<Integer> future = listeningExecutorService.submit(() -> {
            System.out.println("开始耗时计算:" + DateUtil.todayTime());
            Thread.sleep(5000);
            System.out.println("结束耗时计算:" + DateUtil.todayTime());
            return 100;
        });

        // 异步任务回调
        // future.addListener(() -> System.out.println("调用成功"), listeningExecutorService);

        FutureCallback<Integer> callback = new FutureCallback<>() {
            @Override
            public void onSuccess(@Nullable Integer result) {
                System.out.println("成功，计算结果:" + result);
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println("失败");
            }
        };

        Futures.addCallback(future, callback, listeningExecutorService);
        System.out.println("main thread.....");

        DaemonThread.await();
    }


    @Test
    void newExecutor() {
        /*
         * 获得一个新的线程池，默认的策略如下：
         * 1. 初始线程数为 0
         * 2. 最大线程数为Integer.MAX_VALUE
         * 3. 使用SynchronousQueue
         * 4. 任务直接提交给线程而不保持它们
         */
        ExecutorService executorService = ThreadPoolUtil.newExecutor();
        System.out.println(StringUtil.formatString(executorService));
    }

    @Test
    void testNewExecutor() {
        /*
         * 新建一个线程池，默认的策略如下：
         * 1. 初始线程数为corePoolSize指定的大小
         * 2. 最大线程数限制Integer.MAX_VALUE
         * 3. 默认使用LinkedBlockingQueue，默认队列大小为1024
         * 4. 当运行线程大于corePoolSize放入队列，队列满后抛出异常
         */
        ExecutorService executorService = ThreadPoolUtil.newExecutor(-1);
        System.out.println(StringUtil.formatString(executorService));
        executorService.submit(() -> System.out.println("task start."));
    }

    @Test
    void testNewExecutor1() {
        /*
         * 获得一个新的线程池<br>
         * 如果maximumPoolSize &gt;= corePoolSize，在没有新任务加入的情况下，多出的线程将最多保留60s
         *
         * @param corePoolSize    初始线程池大小
         * @param maximumPoolSize 最大线程池大小
         */
        ThreadPoolExecutor threadPoolExecutor = ThreadPoolUtil.newExecutor(10, 20);
        System.out.println(StringUtil.formatString(threadPoolExecutor));
    }

    @Test
    void testNewExecutor2() {
        /*
         * 获得一个新的线程池，并指定最大任务队列大小<br>
         * 如果maximumPoolSize &gt;= corePoolSize，在没有新任务加入的情况下，多出的线程将最多保留60s
         *
         * @param corePoolSize     初始线程池大小
         * @param maximumPoolSize  最大线程池大小
         * @param maximumQueueSize 最大任务队列大小
         */
        ExecutorService executorService = ThreadPoolUtil.newExecutor(10, 20, 50);
        System.out.println(StringUtil.formatString(executorService));
    }

    @Test
    void newSingleExecutor() {
        /*
         * 获得一个新的线程池，只有单个线程，策略如下：
         * 1. 初始线程数为 1
         * 2. 最大线程数为 1
         * 3. 默认使用LinkedBlockingQueue，默认队列大小为1024
         * 4. 同时只允许一个线程工作，剩余放入队列等待，等待数超过1024报错
         */
        ExecutorService executorService = ThreadPoolUtil.newSingleExecutor();
        System.out.println(StringUtil.formatString(executorService));
    }

}