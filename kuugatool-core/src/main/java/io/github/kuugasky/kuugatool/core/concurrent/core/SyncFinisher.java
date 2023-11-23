package io.github.kuugasky.kuugatool.core.concurrent.core;

import com.google.common.collect.Multiset;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.ListenableFuture;
import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.collection.MapUtil;
import io.github.kuugasky.kuugatool.core.collection.SetUtil;
import io.github.kuugasky.kuugatool.core.concurrent.ThreadPoolUtil;
import io.github.kuugasky.kuugatool.core.concurrent.guava.GuavaThreadPoolWrapper;
import io.github.kuugasky.kuugatool.core.date.interval.TimeInterval;
import io.github.kuugasky.kuugatool.core.exception.UtilException;
import io.github.kuugasky.kuugatool.core.md5.MD5Util;
import io.github.kuugasky.kuugatool.core.string.snow.SnowflakeIdUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 线程同步结束器<br>
 * 在完成一组正在其他线程中执行的操作之前，它允许一个或多个线程一直等待。
 *
 * @author Looly
 * @since 4.1.15
 */
@Slf4j
public class SyncFinisher {

    private final long syncFinisherId = SnowflakeIdUtil.getSnowflakeId();

    /**
     * 工作任务set集合
     */
    private final Set<Worker> workers;
    /**
     * 线程数
     */
    private final int threadSize;
    /**
     * 将ExecutorService封装为Guava的ListeningExecutorService
     */
    private final GuavaThreadPoolWrapper threadPoolWrapper;
    /**
     * 所有线程任务是否同时开始
     */
    private boolean syncStart;
    /**
     * 启动同步器，用于保证所有worker线程同时开始
     */
    private final CountDownLatch beginLatch;

    /**
     * 结束同步器，用于等待所有worker线程同时结束
     */
    private CountDownLatch endLatch;

    @Getter
    private final AtomicLong workerSuccessCount = new AtomicLong(0);
    @Getter
    private final AtomicLong workerErrorCount = new AtomicLong(0);
    private final Multiset<String> errorCountOfMd5Set = SetUtil.newMultiset();
    private final Map<String, String> workerErrorMessageMap = MapUtil.newHashMap();

    /**
     * 结果集
     */
    @Builder
    @Getter
    public static class SyncException {
        private String errorMd5;

        private String errorMessage;

        private long errorCount;
    }

    public List<SyncException> getWorkerErrorMessages() {
        List<SyncException> workerErrorMessages = ListUtil.newArrayList();
        errorCountOfMd5Set.elementSet().forEach(errorCountOfMd5 -> {
            SyncException syncException = SyncException.builder()
                    .errorMd5(errorCountOfMd5)
                    .errorCount(errorCountOfMd5Set.count(errorCountOfMd5))
                    .errorMessage(workerErrorMessageMap.get(errorCountOfMd5))
                    .build();
            workerErrorMessages.add(syncException);
        });
        return workerErrorMessages;
    }

    /**
     * 构造
     *
     * @param threadSize 线程数
     */
    public SyncFinisher(int threadSize) {
        // 初始化线程同步开始锁
        this.beginLatch = new CountDownLatch(1);
        // 初始化线程数
        this.threadSize = threadSize;
        // 初始化线程服务#最大核心数为{@link Integer#MAX_VALUE}#队列{@link LinkedBlockingQueue}
        final ExecutorService executorService = ThreadPoolUtil.newExecutor(threadSize);
        this.threadPoolWrapper = GuavaThreadPoolWrapper.build(executorService);
        // 初始化工作任务集合#链式HaseSet
        this.workers = new LinkedHashSet<>();
    }

    /**
     * 设置是否所有worker线程同时开始
     *
     * @param syncStart 是否所有worker线程同时开始
     * @return this
     */
    public SyncFinisher syncStart(boolean syncStart) {
        this.syncStart = syncStart;
        return this;
    }

    /**
     * 增加定义的线程数同等数量的worker
     *
     * @param runnable 工作线程
     * @return this
     */
    public SyncFinisher addRepeatWorker(final Runnable runnable) {
        for (int i = 0; i < this.threadSize; i++) {
            addWorker(runnable);
        }
        return this;
    }

    /**
     * 增加工作线程
     *
     * @param runnable 工作线程
     * @return this
     */
    @SuppressWarnings("all")
    public SyncFinisher addWorker(@NonNull final Runnable runnable) {
        workers.add(new Worker() {
            @Override
            public void work(long workerId) {
                if (syncStart) {
                    try {
                        beginLatch.await();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                log.info(String.format("[SYNC FINISHER][%s][%s] %s", syncFinisherId, workerId, "worker任务开始执行"));
                // 异步线程任务执行
                runnable.run();
            }
        });
        return this;
    }

    /**
     * 定义单个worker任务监听回调
     */
    public FutureCallback<Object> futureCallback = new FutureCallback<>() {
        @Override
        public void onSuccess(Object result) {
            try {
                // 任务成功，成功量+1
                workerSuccessCount.incrementAndGet();
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                // 任务完成后，work闭锁减1
                endLatch.countDown();
            }
        }

        @Override
        public void onFailure(@NonNull Throwable t) {
            try {
                String message = t.getMessage();
                String md5 = MD5Util.getMD5(message);
                // 任务失败，失败量+1
                workerErrorCount.incrementAndGet();
                // 异常信息存储
                if (errorCountOfMd5Set.contains(md5)) {
                    errorCountOfMd5Set.add(md5);
                } else {
                    errorCountOfMd5Set.add(md5);
                    workerErrorMessageMap.put(md5, message);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                // 任务完成后，work闭锁减1
                endLatch.countDown();
            }
        }
    };

    /**
     * 开始工作
     */
    public void start() {
        start(true);
    }

    /**
     * 开始工作
     *
     * @param syncEnd 是否阻塞等待所有任务完成<br>
     *                - true，主线程挂起等待所有异步线程执行完才切回主线程<br>
     *                - false，任务异步执行，主线程不挂起(false时影响结果回调统计)
     * @since 4.5.8
     */
    public void start(boolean syncEnd) {
        TimeInterval timeInterval = new TimeInterval();
        timeInterval.start();
        log.info(String.format("[SYNC FINISHER][%s] 共添加worker任务数量[%s]", syncFinisherId, workersCount()));

        // 任务先提交到线程队列
        workers.forEach(worker -> {
            // runnable task提交，并转换为ListenableFuture
            ListenableFuture<?> listenableFuture = threadPoolWrapper.submit(worker);
            // listenableFuture添加监听回调
            threadPoolWrapper.addCallback(listenableFuture, futureCallback);
            log.info(String.format("[SYNC FINISHER][%s][%s] %s", syncFinisherId, worker.getWorkerId(), "worker就绪，提交至线程池"));
        });

        // 设置同等任务数量的结束锁
        endLatch = new CountDownLatch(workersCount());

        // 保证所有worker同时开始，worker内部beginLatch.await()
        log.info(String.format("[SYNC FINISHER][%s] %s", syncFinisherId, "多线程任务同步器 #开始闭锁# 释放成功"));
        this.beginLatch.countDown();

        log.info(String.format("[SYNC FINISHER][%s] %s", syncFinisherId, "多线程任务同步器开始执行，是否需要同步结束：" + syncEnd + " (非同步结束将影响任务回调统计)"));

        if (syncEnd) {
            // 等待所有线程任务完成，一致结束
            try {
                // 等待所有任务完成，等worker完成后执行endLatch.countDown()
                log.info(String.format("[SYNC FINISHER][%s] %s", syncFinisherId, "等待多线程任务同步器的 #结束闭锁# 释放"));
                this.endLatch.await();
                log.info(String.format("[SYNC FINISHER][%s] %s", syncFinisherId, "多线程任务同步器所有任务已执行完成"));
            } catch (InterruptedException e) {
                throw new UtilException(e);
            }
        }
        this.interval = timeInterval.interval();
        this.intervalPretty = timeInterval.intervalPretty();
    }

    /**
     * 清空工作线程对象
     */
    public void clearWorker() {
        workers.clear();
    }

    private long interval;
    private String intervalPretty;

    public long interval() {
        return this.interval;
    }

    public String intervalPretty() {
        return this.intervalPretty;
    }

    /**
     * 剩余任务数
     * (不能作为精准判断,5个任务,其中3个同时开始,这时剩余数都是5,但是第4个开始时拿到的剩余就是2了)
     *
     * @return 剩余任务数
     */
    public long count() {
        if (null == endLatch) {
            return 0;
        }
        return endLatch.getCount();
    }

    public int workersCount() {
        return this.workers.size();
    }

    /**
     * Worker任务下标ID
     */
    private final AtomicLong atomicLong = new AtomicLong(0);

    /**
     * 工作任务
     *
     * @author xiaoleilu
     */
    public abstract class Worker implements Runnable {

        @Getter
        private long workerId;

        @Override
        public void run() {
            this.workerId = atomicLong.incrementAndGet();
            work(workerId);
        }

        /**
         * work
         *
         * @param workerId workerId
         */
        public abstract void work(long workerId);

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return super.equals(obj);
        }
    }

}
