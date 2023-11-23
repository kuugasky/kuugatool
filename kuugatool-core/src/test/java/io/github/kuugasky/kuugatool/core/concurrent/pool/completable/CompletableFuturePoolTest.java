package io.github.kuugasky.kuugatool.core.concurrent.pool.completable;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.collection.MapUtil;
import io.github.kuugasky.kuugatool.core.concurrent.pool.CompletableFuturePool;
import io.github.kuugasky.kuugatool.core.concurrent.pool.ThreadPool;
import io.github.kuugasky.kuugatool.core.date.interval.TimeInterval;
import io.github.kuugasky.kuugatool.core.entity.KuugaDTO;
import io.github.kuugasky.kuugatool.core.entity.KuugaModel;
import io.github.kuugasky.kuugatool.core.entity.User;
import io.github.kuugasky.kuugatool.core.enums.TaskSplitType;
import io.github.kuugasky.kuugatool.core.function.ThreadTaskFunc;
import io.github.kuugasky.kuugatool.core.object.MapperUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

class CompletableFuturePoolTest {

    /**
     * 多个任务不同返回类型demo
     *
     * @throws ExecutionException   执行异常
     * @throws InterruptedException 中断异常
     */
    @Test
    void testGet() throws ExecutionException, InterruptedException {
        TimeInterval timeInterval = new TimeInterval();
        timeInterval.start();
        List<Object> result = CompletableFuturePool.build()
                .supplyAsync(() -> {
                    int sum = 0;
                    for (int i = 0; i < 100; i++) {
                        sum += i;
                    }
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return sum;
                }).supplyAsync(() -> {
                    long sum = 0L;
                    for (int i = 0; i < 100; i++) {
                        sum += i;
                    }
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return sum;
                }).supplyAsync(() -> {
                    double sum = 0D;
                    for (int i = 0; i < 100; i++) {
                        sum += i;
                    }
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return sum;
                }).getResult();

        // 所有任务执行完成后回归主线程
        System.out.println(timeInterval.intervalPretty());
        ListUtil.optimize(result).forEach(System.out::println);
    }

    /**
     * 多个任务相同同返回类型demo
     *
     * @throws ExecutionException   执行异常
     * @throws InterruptedException 中断异常
     */
    @Test
    void testGet2() throws ExecutionException, InterruptedException {
        TimeInterval timeInterval = new TimeInterval();
        timeInterval.start();
        int iCount = 100;
        Supplier<Integer> integerSupplier = () -> {
            int sum = 0;
            for (int i = 0; i < iCount; i++) {
                sum += i;
            }
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return sum;
        };
        List<Integer> result = CompletableFuturePool.<Integer>build()
                .supplyAsync(integerSupplier).supplyAsync(() -> {
                    int sum = 0;
                    for (int i = 0; i < 1000; i++) {
                        sum += i;
                    }
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return sum;
                }).supplyAsync(() -> {
                    int sum = 0;
                    for (int i = 0; i < 10000; i++) {
                        sum += i;
                    }
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return sum;
                }).getResult();

        System.out.println("this is main thread:" + Thread.currentThread().getName());

        // 所有任务执行完成后回归主线程
        System.out.println(timeInterval.intervalPretty());
        ListUtil.optimize(result).forEach(System.out::println);
    }

    /**
     * 两条线程各玩各的
     *
     * @throws ExecutionException   ExecutionException
     * @throws InterruptedException InterruptedException
     */
    @Test
    void testGet3() throws ExecutionException, InterruptedException {
        CompletableFuture<Object> submit = CompletableFuture.supplyAsync(() -> {
            System.out.println("异步线程开启");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("异步线程结束");
            return null;
        });

        for (int i = 0; i < 10; i++) {
            System.out.println("主线程执行:" + i);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        Object result = submit.get();
        System.out.println(result);
        System.out.println("任务结束");
    }

    @Test
    void testGet4() throws ExecutionException, InterruptedException {
        Map<String, String> result = CompletableFuturePool.<Map<String, String>>build()
                .supplyAsync(() -> {
                    Map<String, String> nameMap = MapUtil.newHashMap();
                    nameMap.put("1", "kuuga");
                    return nameMap;
                }).getResult().get(0);

        CompletableFuture<Map<String, String>> mapCompletableFuture = CompletableFuture.supplyAsync(() -> {
            Map<String, String> nameMap = MapUtil.newHashMap();
            nameMap.put("1", "kuuga");
            nameMap.put("2", "kuuga");
            return nameMap;
        });
        Map<String, String> result1 = mapCompletableFuture.get();

        System.out.println(result);
        System.out.println(StringUtil.repeatNormal());
        System.out.println(result1);
        System.out.println(StringUtil.repeatNormal());

        CompletableFuture<String> stringCompletableFuture =
                mapCompletableFuture.thenComposeAsync(map -> CompletableFuture.supplyAsync(() -> String.join(",", map.keySet())));
        System.out.println(stringCompletableFuture.get());
    }

    @Test
    void invokeAllTask() {
        List<CompletableFuture<List<KuugaModel>>> completableFutures = ListUtil.newArrayList();

        // init data
        List<KuugaModel> sourceList = ListUtil.newArrayList();
        for (int i = 0; i < 10; i++) {
            sourceList.add(KuugaModel.builder().name("kuuga" + i).sex(i).build());
        }

        List<List<KuugaModel>> lists = ListUtil.splitList(sourceList, 3);
        ListUtil.optimize(lists).forEach(list -> {
            List<KuugaModel> result = ListUtil.newArrayList();
            CompletableFuture<List<KuugaModel>> listCompletableFuture = CompletableFuture.supplyAsync(() -> {
                list.forEach(kuugaModel -> {
                    kuugaModel.setName("new" + kuugaModel.getName());
                    result.add(kuugaModel);
                });
                return result;
            });
            completableFutures.add(listCompletableFuture);
        });

        List<List<KuugaModel>> lists1 = CompletableFuturePool.invokeAllTask(completableFutures);
        List<KuugaModel> sum = ListUtil.summary(lists1);
        System.out.println(sum.size());
    }

    @Test
    void invokeAllTaskOfCompletableFuture() {
        // init data
        List<KuugaModel> sourceList = ListUtil.newArrayList();
        for (int i = 0; i < 10; i++) {
            sourceList.add(KuugaModel.builder().name("kuuga" + i).sex(i).build());
        }

        TimeInterval timeInterval = new TimeInterval();

        timeInterval.start("1");
        Function<List<KuugaModel>, List<KuugaDTO>> function = list -> MapperUtil.copy(list, KuugaDTO.class);
        List<KuugaDTO> dtos = CompletableFuturePool.invokeAllTask(sourceList, TaskSplitType.TASK_AMOUNT, 3, function);
        System.out.println(dtos.size());
        System.out.println("completableFuture--->" + timeInterval.intervalPretty("1"));
    }

    @Test
    public void invokeAllTaskNoReturnValue() {
        List<String> rangeString = ListUtil.createRangeString(1, 100);
        CompletableFuturePool.invokeAllTaskNoReturnValue(rangeString, TaskSplitType.DATA_SIZE, 1, list -> {
            String s = list.get(0);
            if (Integer.parseInt(s) % 2 != 0) {
                // throw new RuntimeException(String.format("%s不能被2整除.", s));
                System.out.printf("%s不能被2整除.%n", s);
            }
        });
    }


    @Test
    void invokeAllTaskOfThreadPool() {

        // init data
        List<KuugaModel> sourceList = ListUtil.newArrayList();
        for (int i = 0; i < 10; i++) {
            sourceList.add(KuugaModel.builder().name("kuuga" + i).sex(i).build());
        }

        TimeInterval timeInterval = new TimeInterval();

        timeInterval.start("2");
        ThreadTaskFunc<KuugaModel, KuugaDTO> threadTaskFunc = list -> () -> MapperUtil.copy(list, KuugaDTO.class);
        List<KuugaDTO> kuugaDTOS = ThreadPool.invokeAllTask(sourceList, TaskSplitType.TASK_AMOUNT, 3, threadTaskFunc);
        System.out.println(kuugaDTOS.size());
        System.out.println("future--->" + timeInterval.intervalPretty("2"));
    }

    @Test
    void runAsync() throws ExecutionException, InterruptedException {
        CompletableFuturePool.runAsync(() -> System.out.println("runAsync")).get();
    }

    @Test
    void runAsync1() throws ExecutionException, InterruptedException {
        Executor executor = Executors.newFixedThreadPool(1);
        CompletableFuturePool.runAsync(() -> System.out.println("runAsync"), executor).get();
    }

    @Test
    void supplyAsync() throws ExecutionException, InterruptedException {
        CompletableFuture<String> stringCompletableFuture = CompletableFuturePool.supplyAsync(() -> {
            System.out.println("supplyAsync");
            return "kuuga";
        });
        System.out.println(stringCompletableFuture);
        // 不调用.get()，则只输出supplyAsync
        System.out.println(stringCompletableFuture.get());
    }

    @Test
    void supplyAsync1() throws ExecutionException, InterruptedException {
        Executor executor = Executors.newFixedThreadPool(1);
        CompletableFuture<String> stringCompletableFuture = CompletableFuturePool.supplyAsync(() -> {
            System.out.println("supplyAsync");
            return "kuuga";
        }, executor);
        System.out.println(stringCompletableFuture);
        // 不调用.get()，则只输出supplyAsync
        System.out.println(stringCompletableFuture.get());
    }

    @Test
    void thenApply() throws ExecutionException, InterruptedException {
        // 创建一个CompletableFuture
        CompletableFuture<String> whatsYourNameFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Jack";
        }).orTimeout(1, TimeUnit.SECONDS);

        // 使用thenApply()将回调附加到Future
        CompletableFuture<String> stringCompletableFuture = CompletableFuturePool.thenApply(whatsYourNameFuture, name -> "Hello " + name);
        // 使用thenApply()将回调附加到Future
        CompletableFuture<String> stringCompletableFuture1 = CompletableFuturePool.thenApplyAsync(stringCompletableFuture, name -> "Kuuga：" + name);
        CompletableFuture<String> stringCompletableFuture2 = CompletableFuturePool.thenApplyAsync(stringCompletableFuture1, name -> "good " + name, null);

        // 阻塞并获取Future的结果
        System.out.println(stringCompletableFuture2.get()); // Hello Jack
    }

    @SuppressWarnings("all")
    CompletableFuture<User> getUsersDetail(String name) {
        return CompletableFuture.supplyAsync(() -> new User(name, "男", null));
    }

    CompletableFuture<String> getCreditRating(User user) {
        return CompletableFuture.supplyAsync(user::getName);
    }

    @Test
    void thenCompose() throws ExecutionException, InterruptedException {
        // thenApply受限场景
        Function<User, CompletableFuture<String>> getCreditRating = this::getCreditRating;
        // 注意：与之前的thenApply入参不同的是：之前thenApply入参是上一个future的结果值，而此处入参却是整个future对象，所以结果类型是嵌套的CompletableFuture
        CompletableFuture<CompletableFuture<String>> Kuuga = getUsersDetail("kuuga").thenApply(getCreditRating);
        System.out.println(Kuuga.get());

        // 可以使用thenCompose进行处理，将最终返回结果类型转换为终阶类型
        CompletableFuture<String> result = getUsersDetail("kuuga").thenCompose(this::getCreditRating);
        System.out.println(result.get());
        result.thenRun(() -> System.out.println("任务结束"));

        CompletableFuture<String> stringCompletableFuture = CompletableFuturePool.thenCompose(getUsersDetail("kuugasky"), this::getCreditRating);
        CompletableFuturePool.thenAccept(stringCompletableFuture, name -> System.out.println("最终结果为:" + name));

        CompletableFuture<String> stringCompletableFuture2 = CompletableFuturePool.thenComposeAsync(getUsersDetail("kuugasky"), this::getCreditRating);
        CompletableFuturePool.thenAccept(stringCompletableFuture2, name -> System.out.println("最终结果为:" + name));

        CompletableFuture<String> stringCompletableFuture3 = CompletableFuturePool.thenComposeAsync(getUsersDetail("kuugasky"), this::getCreditRating, null);
        CompletableFuturePool.thenAccept(stringCompletableFuture3, name -> System.out.println("最终结果为:" + name));
    }

    @Test
    void thenCombine() throws ExecutionException, InterruptedException {
        System.out.println("获取体重.");
        CompletableFuture<Double> weightInKgFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            System.out.println("------------>体重计算完成");
            return 65.0;
        });

        System.out.println("获取身高.");
        // 下一步还要结算体重，且最终两个future都执行完后才计算bmi，此处如果.get()则阻塞主线程去提交身高异步任务
        // System.out.println("获取身高." + weightInKgFuture.get());
        CompletableFuture<Double> heightInCmFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            System.out.println("------------>身高计算完成");
            return 177.8;
        });

        CompletableFuture<Double> doubleCompletableFuture = CompletableFuture.supplyAsync(() -> 100D);

        CompletableFuture<Double> combinedFuture = weightInKgFuture
                .thenCombine(heightInCmFuture, (weightInKg, heightInCm) -> {
                    System.out.println("------------>开始计算BMI");
                    Double heightInMeter = heightInCm / 100;
                    return weightInKg / (heightInMeter * heightInMeter);
                }).thenCombine(doubleCompletableFuture, (bmi, basicValue) -> bmi * basicValue);

        System.out.println("你的体重指数为：" + combinedFuture.get());
    }

    @Test
    void thenCombine1() throws ExecutionException, InterruptedException {
        System.out.println("获取体重.");
        CompletableFuture<Double> weightInKgFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            System.out.println("------------>体重计算完成");
            return 65.0;
        });

        System.out.println("获取身高.");
        // 下一步还要结算体重，且最终两个future都执行完后才计算bmi，此处如果.get()则阻塞主线程去提交身高异步任务
        // System.out.println("获取身高." + weightInKgFuture.get());
        CompletableFuture<Double> heightInCmFuture = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            System.out.println("------------>身高计算完成");
            return 177.8;
        });

        CompletableFuture<Double> doubleCompletableFuture = CompletableFuturePool.thenCombine(heightInCmFuture, weightInKgFuture, (height, weight) -> {
            System.out.println("------------>开始计算BMI");
            Double heightInMeter = height / 100;
            return weight / (heightInMeter * heightInMeter);
        });

        System.out.println("你的体重指数为：" + doubleCompletableFuture.get());
    }

    @Test
    void thenAccept() throws ExecutionException, InterruptedException {
        // thenAccept()例子
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> "我是一阶段");
        CompletableFuture<Void> voidCompletableFuture = CompletableFuturePool.thenAccept(stringCompletableFuture, product -> System.out.println("终阶结束，上一阶段是：" + product));
        voidCompletableFuture.get();
    }

    @Test
    void thenAcceptAsync() throws ExecutionException, InterruptedException {
        // thenAccept()例子
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> "我是一阶段");
        CompletableFuture<Void> voidCompletableFuture = CompletableFuturePool.thenAcceptAsync(stringCompletableFuture, product -> System.out.println("终阶结束，上一阶段是：" + product));
        voidCompletableFuture.get();
    }

    @Test
    void thenAcceptAsync1() throws ExecutionException, InterruptedException {
        Executor executor = Executors.newFixedThreadPool(1);
        // thenAccept()例子
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> "我是一阶段");
        CompletableFuture<Void> voidCompletableFuture =
                CompletableFuturePool
                        .thenAcceptAsync(stringCompletableFuture, product -> System.out.println("终阶结束，上一阶段是：" + product), executor);
        voidCompletableFuture.get();
    }

    @Test
    void thenRun() throws ExecutionException, InterruptedException {
        // thenRun()例子
        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> "我是一阶段");
        // 最后使用thenRun的时候，上一个future如果是supplyAsync则没什么意义，因为其返回值在thenRun中并不会被使用
        CompletableFuture<Void> voidCompletableFuture = CompletableFuturePool.thenRun(stringCompletableFuture, () -> System.out.println("任务结束了，我不能获取上一个CompletableFuture的值"));
        voidCompletableFuture.get();
    }

    CompletableFuture<String> downloadWebPage(String pageLink) {
        return CompletableFuture.supplyAsync(() -> {
            // 下载并返回网页内容的代码
            if (Integer.parseInt(pageLink) % 2 == 0) {
                System.out.println(pageLink);
                return pageLink;
            }
            if (pageLink.equals("3")) {
                throw new RuntimeException("异常了:" + pageLink);
            }
            return null;
        });
    }

    @Test
    void allOf() throws ExecutionException, InterruptedException {
        // 100个网页的链接地址列表
        List<String> webPageLinks = ListUtil.createRangeString(1, 100);

        // 异步下载所有网页的内容
        List<CompletableFuture<String>> pageContentFutures = webPageLinks.stream()
                .map(this::downloadWebPage)
                .toList();


        // 使用allOf()方法创建一个合并的Future
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(
                // 任务无序执行，中间报错了后续任务继续执行
                pageContentFutures.toArray(new CompletableFuture[0])
        );

        allFutures.get();
    }

    @Test
    void allOf1() {
        // 100个网页的链接地址列表
        List<String> webPageLinks = ListUtil.createRangeString(1, 100);

        // 异步下载所有网页的内容
        List<String> strings = webPageLinks.stream()
                // .join()，任务有序执行，中间报错后续任务就不执行了
                // 在所有的Future都完成后才调用future.join()，类似于执行了.get()方法，因此我们不会在任何地方阻塞.
                .map(pageLink -> downloadWebPage(pageLink).join())
                .toList();

        System.out.println(strings);
    }

    @Test
    void allOf2() throws ExecutionException, InterruptedException {
        // 100个网页的链接地址列表
        List<String> webPageLinks = ListUtil.createRangeString(1, 10);

        // 异步下载所有网页的内容
        List<CompletableFuture<String>> list = webPageLinks.stream()
                .map(this::downloadWebPage)
                .toList();

        CompletableFuture<Void> voidCompletableFuture = CompletableFuturePool.allOf(list);
        // 当voidCompletableFuture中有异常时，如果不执行.get()则异常不会被抛出
        System.out.println(voidCompletableFuture.get());

        CompletableFuture<Void> voidCompletableFuture2 = CompletableFuturePool.allOf(list.toArray(new CompletableFuture[0]));
        System.out.println(voidCompletableFuture2.get());
    }

    @Test
    void anyOf() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Future1的结果";
        });

        CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            System.out.println("Future2的结果:" + 11);
            return 11;
        });

        CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return "Future3的结果";
        });

        CompletableFuture<Object> anyOfFuture = CompletableFuturePool.anyOf(future1, future2, future3);

        System.out.println(anyOfFuture.get()); // 输出Future2的结果

        CompletableFuture<Object> anyOfFuture1 = CompletableFuturePool.anyOf(ListUtil.newArrayList(future1, future2, future3));

        System.out.println(anyOfFuture1.get()); // 输出Future2的结果
    }

    @Test
    void exceptionally() throws ExecutionException, InterruptedException {
        int age = -1;

        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
            if (age < 0) {
                throw new IllegalArgumentException("年龄不可能为负数");
            }
            if (age > 18) {
                return "成年人";
            } else {
                return "未成年人";
            }
        });

        Function<Throwable, String> throwableStringFunction = ex -> {
            System.out.println("我们得到异常：" + ex.getMessage());
            return "Unknown!";
        };
        // CompletableFuture<String> exceptionally = stringCompletableFuture.exceptionally(throwableStringFunction);
        CompletableFuture<String> exceptionally = CompletableFuturePool.exceptionally(stringCompletableFuture, throwableStringFunction);
        System.out.println(exceptionally.get());
        CompletableFuture<String> exceptionallyAsync = CompletableFuturePool.exceptionallyAsync(stringCompletableFuture, throwableStringFunction);
        System.out.println(exceptionallyAsync.get());
        CompletableFuture<String> exceptionallyAsync2 = CompletableFuturePool.exceptionallyAsync(stringCompletableFuture, throwableStringFunction, null);
        System.out.println(exceptionallyAsync2.get());
    }

    @Test
    void handle() throws ExecutionException, InterruptedException {
        int age = 21;

        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
            if (age < 0) {
                throw new IllegalArgumentException("年龄不可能为负数");
            }
            if (age > 18) {
                return "成年人";
            } else {
                return "未成年人";
            }
        });

        BiFunction<String, Throwable, String> exceptionFunction = (res, ex) -> {
            if (ex != null) {
                System.out.println("我们得到异常：" + ex.getMessage());
                return "Unknown!";
            }
            return res;
        };
        CompletableFuture<String> handle = CompletableFuturePool.handle(stringCompletableFuture, exceptionFunction);
        System.out.println(handle.get());
        CompletableFuture<String> handle1 = CompletableFuturePool.handleAsync(stringCompletableFuture, exceptionFunction);
        System.out.println(handle1.get());
        CompletableFuture<String> handle2 = CompletableFuturePool.handleAsync(stringCompletableFuture, exceptionFunction, null);
        System.out.println(handle2.get());
    }

    @Test
    void exceptionallyCompose() throws ExecutionException, InterruptedException {
        int age = -1;

        CompletableFuture<String> stringCompletableFuture = CompletableFuture.supplyAsync(() -> {
            if (age < 0) {
                throw new IllegalArgumentException("年龄不可能为负数");
            }
            if (age > 18) {
                return "成年人";
            } else {
                return "未成年人";
            }
        });
        Function<Throwable, CompletionStage<String>> throwableCompletionStageFunction = e -> {
            System.out.println("exceptionallyCompose!!!");
            return CompletableFuture.completedFuture(e.getMessage());
        };
        CompletableFuture<String> stringCompletableFuture1 = CompletableFuturePool.exceptionallyCompose(stringCompletableFuture, throwableCompletionStageFunction);
        System.out.println(stringCompletableFuture1.get());
        CompletableFuture<String> stringCompletableFuture2 = CompletableFuturePool.exceptionallyComposeAsync(stringCompletableFuture, throwableCompletionStageFunction);
        System.out.println(stringCompletableFuture2.get());
        CompletableFuture<String> stringCompletableFuture3 = CompletableFuturePool.exceptionallyComposeAsync(stringCompletableFuture, throwableCompletionStageFunction, null);
        System.out.println(stringCompletableFuture3.get());
    }

}