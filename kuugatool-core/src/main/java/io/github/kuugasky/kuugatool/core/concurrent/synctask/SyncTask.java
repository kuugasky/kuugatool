package io.github.kuugasky.kuugatool.core.concurrent.synctask;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.concurrent.core.KuugaRejectedExecutionHandler;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * SyncTask
 * <p>
 * 适用于报表统计多表多字段sql执行，获取结果集并入库
 * <p>
 * - 放入查询数据集 {@link SyncTask#data(List)}
 * - 添加单任务 {@link SyncTask#addTask(TaskEvent, TaskSingleFunction, ErrorHandle)}
 * - 或添加多任务 {@link SyncTask#addTask(TaskEvent, TaskMultipleFunction, java.lang.Comparable, ErrorHandle)}
 * - 执行查询并按自定义函数方法归档结果集 {@link SyncTask#exec(TaskConsumer)}
 *
 * @author pengqinglong
 * @since 2022/4/20
 */
@Slf4j
public class SyncTask<T> {

    /**
     * 异步任务内部线程池
     */
    private static final ThreadPoolExecutor SYNC_TASK_THREAD_POOL;

    /**
     * 完成任务总数
     */
    private static final AtomicLong COMPLETED_TASK_NUMBER;

    /**
     * 当前正在并发的任务数
     */
    private static final AtomicInteger CURRENT_CONCURRENT_TASK_NUMBER;

    /**
     * 峰值并发
     */
    private static final AtomicInteger MAX_CONCURRENT_PEAK_NUMBER;

    static {
        KuugaRejectedExecutionHandler handler = new KuugaRejectedExecutionHandler();
        LinkedBlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(10);
        ThreadFactory factory = new ThreadFactoryBuilder().setNameFormat("syncTask-thread-%d").build();
        SYNC_TASK_THREAD_POOL = new ThreadPoolExecutor(10, 20, 60, TimeUnit.SECONDS, workQueue, factory, handler);

        COMPLETED_TASK_NUMBER = new AtomicLong();
        CURRENT_CONCURRENT_TASK_NUMBER = new AtomicInteger();
        MAX_CONCURRENT_PEAK_NUMBER = new AtomicInteger();
    }

    /**
     * 获取当前任务执行的情况
     */
    public static ConcurrentTaskInfo getTaskInfo() {
        long completedTaskNumber = COMPLETED_TASK_NUMBER.get();
        int currentConcurrentTaskNumber = CURRENT_CONCURRENT_TASK_NUMBER.get();
        int maxPeakNumber = MAX_CONCURRENT_PEAK_NUMBER.get();
        return new ConcurrentTaskInfo(completedTaskNumber, currentConcurrentTaskNumber, maxPeakNumber);
    }

    /**
     * 服务停止时关闭线程池
     */
    public static void shutdown() {
        SYNC_TASK_THREAD_POOL.shutdown();
    }

    /**
     * 对异步任务对象进行包装 防止外部获取到进行改动
     */
    private final Task<T> task;

    /**
     * 私有构造方法
     */
    private SyncTask(List<T> sources) {
        this.task = new Task<>(sources);
    }

    /**
     * 初始化数据源
     *
     * @return 对Task内部类进行包装 禁止外部访问
     */
    public static <T> SyncTask<T> data(List<T> sources) {
        return new SyncTask<>(sources);
    }

    /**
     * 设置整个任务链默认的异常处理 可对任务进行单独的异常处理
     * 优先级为 任务单独的 -> 任务链默认的
     */
    public SyncTask<T> addErrorHandle(ErrorHandle errorHandle) {
        this.task.setErrorHandle(errorHandle);
        return this;
    }

    /**
     * 添加单任务
     *
     * @param event 事件
     * @param task  任务
     */
    public SyncTask<T> addTask(TaskEvent event, TaskSingleFunction<T> task) {
        this.task.addTask(event, task, null);
        return this;
    }

    /**
     * 添加单任务
     *
     * @param event 事件
     * @param task  任务
     */
    public SyncTask<T> addTask(TaskEvent event, TaskSingleFunction<T> task, ErrorHandle errorHandle) {
        this.task.addTask(event, task, errorHandle);
        return this;
    }

    /**
     * 添加多任务
     *
     * @param event      事件
     * @param task       任务
     * @param comparable 对比器 用于对比任务获取到的result与source
     */
    public SyncTask<T> addTask(TaskEvent event, TaskMultipleFunction<T> task, Comparable<T> comparable) {
        this.task.addTask(event, task, comparable, null);
        return this;
    }

    /**
     * 添加多任务
     *
     * @param event      事件
     * @param task       任务
     * @param comparable 对比器 用于对比任务获取到的result与source
     */
    public SyncTask<T> addTask(TaskEvent event, TaskMultipleFunction<T> task, Comparable<T> comparable, ErrorHandle errorHandle) {
        this.task.addTask(event, task, comparable, errorHandle);
        return this;
    }

    /**
     * 带结果回调处理
     */
    public List<ErrorTask<T>> exec(TaskConsumer<T> consumer) throws Exception {
        return this.task.exec(consumer);
    }

    /**
     * 无结果回调处理 将结果处理直接写在任务中的方式
     * （不建议使用 更建议所有处理结果汇总 方便他人接手进行问题排查）
     */
    public List<ErrorTask<T>> exec() throws Exception {
        return this.task.exec(null);
    }

    /**
     * 内部类 整个任务链的封装
     *
     * @param <T>
     */
    private static class Task<T> {

        /**
         * 任务结果队列
         */
        private final BlockingQueue<QueueResult<T>> queue;

        /**
         * 源数据list
         */
        private final List<T> sources;

        /**
         * 默认的异常处理
         */
        private ErrorHandle errorHandle;

        /**
         * 异常的source
         */
        private final List<ErrorTask<T>> errorTasks;

        /**
         * 任务栈
         */
        private Node stack;

        /**
         * 任务栈的数量
         */
        private final AtomicInteger size;

        /**
         * 最终执行的主线程
         */
        private Thread main;

        public Task(List<T> list) {
            this.sources = list;
            this.queue = new LinkedBlockingQueue<>();
            this.size = new AtomicInteger(0);
            this.errorTasks = ListUtil.newArrayList(sources.size());
        }

        public ErrorHandle getErrorHandle() {
            return this.errorHandle;
        }

        public void setErrorHandle(ErrorHandle errorHandle) {
            this.errorHandle = errorHandle;
        }

        public void addErrorSource(ErrorTask<T> task) {
            errorTasks.add(task);
        }

        /**
         * 添加all任务
         */
        public void addTask(TaskEvent event, TaskMultipleFunction<T> task, Comparable<T> comparable, ErrorHandle errorHandle) {
            this.pushTask(this.createTaskMultiple(event, task, comparable, errorHandle));
        }

        /**
         * 添加single任务 自定义异常处理
         */
        public void addTask(TaskEvent event, TaskSingleFunction<T> task, ErrorHandle errorHandle) {
            this.pushTask(this.createTaskSingle(event, task, errorHandle));
        }

        /**
         * 压栈
         */
        private void pushTask(Runnable runnable) {
            stack = new Node(runnable, stack);
            this.size.incrementAndGet();
        }

        /**
         * 创建多任务Runnable
         */
        public Runnable createTaskMultiple(TaskEvent event, TaskMultipleFunction<T> task, Comparable<T> comparable, ErrorHandle errorHandle) {
            taskBefore();
            return new TaskMultiple(this, event, task, comparable, errorHandle);
        }

        /**
         * 创建单个任务
         */
        private Runnable createTaskSingle(TaskEvent event, TaskSingleFunction<T> task, ErrorHandle errorHandle) {
            taskBefore();
            return new TaskSingle(this, event, task, errorHandle);
        }

        /**
         * 任务前置处理
         */
        @SuppressWarnings("all")
        private void taskBefore() {
            int current = CURRENT_CONCURRENT_TASK_NUMBER.incrementAndGet();
            // cas更新最大并发数
            for (int max = MAX_CONCURRENT_PEAK_NUMBER.get(); max < current && !MAX_CONCURRENT_PEAK_NUMBER.compareAndSet(max, current); ) {
                max = MAX_CONCURRENT_PEAK_NUMBER.get();
            }
        }

        /**
         * 任务完成后的线程回调
         */
        private void taskComplete() {
            // 完成任务+1
            COMPLETED_TASK_NUMBER.incrementAndGet();

            // 当前任务数-1
            CURRENT_CONCURRENT_TASK_NUMBER.decrementAndGet();

            // 当前任务链表size - 1
            this.size.decrementAndGet();
        }

        /**
         * 任务结果处理
         *
         * @param consumer 结果消费处理
         */
        public List<ErrorTask<T>> exec(TaskConsumer<T> consumer) throws Exception {
            // 最终调用exec的线程作为main线程
            this.main = Thread.currentThread();

            // 执行加入的任务
            List<Future<?>> futureList = this.execTask();

            // 处理任务结果
            if (consumer != null) {
                this.handleResult(consumer);
            }
            // 处理完成 或者无需处理时 对队列进行清空 帮助gc
            queue.clear();

            // 任务执行完成后判断是否有异常
            this.checkFutureList(futureList);

            return errorTasks;
        }

        /**
         * 任务执行完成后判断是否有异常
         */
        private void checkFutureList(List<Future<?>> futureList) throws InterruptedException, ExecutionException {
            while (ListUtil.hasItem(futureList)) {
                Future<?> future = futureList.get(0);
                if (future.isDone()) {
                    future.get();
                    futureList.remove(future);
                }
            }
        }

        /**
         * 执行任务
         */
        private List<Future<?>> execTask() {

            // 保存任务future
            List<Future<?>> futureList = ListUtil.newArrayList(size.get());

            // 栈顶任务由主线程执行
            Node mainNode = stack;

            // 提交任务 弹栈帮助gc
            while ((stack = stack.getNext()) != null) {
                // 任务提交线程池
                futureList.add(SYNC_TASK_THREAD_POOL.submit(stack.getTask()));
            }

            // 运行mainNode任务 （run中不处理异常 当main任务异常时直接抛出）
            this.runMainNode(mainNode, futureList);

            return futureList;
        }

        /**
         * 执行mainNode任务
         * 主栈任务异常 则取消所有子栈任务
         */
        private void runMainNode(Node mainNode, List<Future<?>> futureList) {
            try {
                mainNode.getTask().run();
            } catch (Exception e) {
                // 异常 任务全部中断掉
                futureList.forEach(future -> future.cancel(true));
            }
        }

        /**
         * 结果处理
         * 主线程阻塞等待当前批次线程池内任务消费处理完成
         * 任务执行完成后判断是否有异常
         */
        private void handleResult(TaskConsumer<T> consumer) {
            // 阻塞等待任务结果
            while (true) {
                QueueResult<T> queueResult = null;
                try {
                    queueResult = queue.poll(100, TimeUnit.MILLISECONDS);

                    // 消费
                    if (queueResult != null) {
                        consumer.consumer(queueResult.event, queueResult.source, queueResult.result);
                    }
                } catch (InterruptedException e) {
                    // 吞掉中断
                } catch (Exception e) {
                    if (queueResult != null) {
                        this.addErrorSource(new ErrorTask<>(queueResult.event, queueResult.source, queueResult.result, e));
                    }
                }

                // 单线程计算等待任务执行完成
                int i = this.size.get();
                if (i == 0) {
                    int size = queue.size();
                    for (int j = 0; j < size; j++) {
                        queueResult = queue.poll();
                        if (queueResult != null) {
                            consumer.consumer(queueResult.event, queueResult.source, queueResult.result);
                        }
                    }
                    return;
                }
            }
        }

        /**
         * 线程任务
         */
        @Data
        private abstract static class TaskRunnable<T> implements Runnable {

            /**
             * 任务异常处理
             */
            protected ErrorHandle errorHandle;

            /**
             * 任务事件
             */
            protected TaskEvent event;

            /**
             * 当前任务链对象
             */
            protected Task<T> task;

            protected void errorHandle(Exception e, T source) {

                // 自定义处理
                if (errorHandle != null) {
                    errorHandle.handle(e, event, source);
                    task.addErrorSource(new ErrorTask<>(event, source, e));
                    return;
                }

                // 任务链通用异常处理
                if (task.errorHandle != null) {
                    task.errorHandle.handle(e, event, source);
                    task.addErrorSource(new ErrorTask<>(event, source, e));
                    return;
                }

                // 默认处理
                task.addErrorSource(new ErrorTask<>(event, source, e));
            }
        }

        /**
         * 单体任务
         */
        private class TaskSingle extends TaskRunnable<T> {

            /**
             * 任务执行函数
             */
            private final TaskSingleFunction<T> function;

            public TaskSingle(Task<T> task, TaskEvent event, TaskSingleFunction<T> function) {
                this.task = task;
                this.event = event;
                this.function = function;
            }

            public TaskSingle(Task<T> task, TaskEvent event, TaskSingleFunction<T> function, ErrorHandle errorHandle) {
                this.task = task;
                this.event = event;
                this.function = function;
                this.errorHandle = errorHandle;
            }


            @Override
            public void run() {
                try {
                    for (T source : ListUtil.optimize(sources)) {
                        try {
                            Result result = function.call(ObjectUtil.cast(source));
                            boolean offer = queue.offer(new QueueResult<>(event, source, result));
                            log.debug("queue offer status:{}", offer);
                        } catch (Exception e) {
                            super.errorHandle(e, source);
                        }
                    }
                } finally {
                    // 任何情况下 必须执行任务结束
                    task.taskComplete();
                }
            }
        }

        /**
         * 集体任务
         */
        private class TaskMultiple extends TaskRunnable<T> {

            /**
             * 任务执行函数
             */
            private final TaskMultipleFunction<T> function;

            /**
             * 元素比较器
             */
            private final Comparable<T> comparable;

            public TaskMultiple(Task<T> task, TaskEvent event, TaskMultipleFunction<T> function, Comparable<T> comparable) {
                this.task = task;
                this.event = event;
                this.function = function;
                this.comparable = comparable;
            }

            public TaskMultiple(Task<T> task, TaskEvent event, TaskMultipleFunction<T> function, Comparable<T> comparable, ErrorHandle errorHandle) {
                this.task = task;
                this.event = event;
                this.function = function;
                this.errorHandle = errorHandle;
                this.comparable = comparable;
            }

            @Override
            public void run() {
                try {
                    List<? extends Result> results = function.call(ObjectUtil.cast(sources));
                    for (Result result : ListUtil.optimize(results)) {
                        for (T source : sources) {
                            if (comparable.comparable(source, result)) {
                                boolean offer = queue.offer(new QueueResult<>(event, source, result));
                                log.debug("queue offer status:{}", offer);
                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                    errorHandleMultiple(e, sources);
                } finally {
                    // 任何情况下 必须执行任务结束
                    task.taskComplete();
                }
            }

            /**
             * 异常处理
             */
            protected void errorHandleMultiple(Exception e, List<T> sources) {

                if (errorHandle != null) {

                    // 自定义处理
                    errorHandle.handle(e, event, sources);

                } else if (task.errorHandle != null) {

                    // 任务链通用异常处理
                    task.errorHandle.handle(e, event, sources);
                }

                for (T source : ListUtil.optimize(sources)) {
                    // 默认处理
                    task.addErrorSource(new ErrorTask<>(event, source, e));
                }
            }
        }

        /**
         * 栈节点
         */
        @AllArgsConstructor
        @Data
        private static class Node {
            /**
             * 持有任务
             */
            private Runnable task;
            /**
             * 下一位指针
             */
            private Node next;
        }

        /**
         * 队列结果
         *
         * @param <T>
         */
        @Data
        @AllArgsConstructor
        private static class QueueResult<T> {
            /**
             * 任务事件
             */
            private TaskEvent event;
            /**
             * 源数据
             */
            private T source;
            /**
             * 结果
             */
            private Result result;
        }
    }

    @Data
    @AllArgsConstructor
    public static class ConcurrentTaskInfo {
        /**
         * 已完成任务数
         */
        private long completed;
        /**
         * 当前在执行的任务数
         */
        private int current;
        /**
         * 最大峰值的并发任务数
         */
        private int maxPeak;
    }

}






