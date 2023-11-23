package io.github.kuugasky.kuugatool.core.concurrent.high;

import io.github.kuugasky.kuugatool.core.concurrent.core.SyncFinisher;
import io.github.kuugasky.kuugatool.core.date.interval.TimeInterval;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 高并发测试仪
 *
 * @author kuuga
 * @since 2021-01-12 下午6:00
 */
public class HighConcurrentTester {

    /**
     * 同步整理器
     */
    private final SyncFinisher syncFinisher;
    /**
     * 计时器
     */
    private final TimeInterval timeInterval;

    /**
     * 构造
     *
     * @param threadSize 线程数
     */
    public HighConcurrentTester(int threadSize) {
        // 初始化线程同步结束器
        this.syncFinisher = new SyncFinisher(threadSize);
        this.timeInterval = new TimeInterval();
    }

    /**
     * 执行测试
     *
     * @param runnable 要执行的任务
     * @return this
     */
    public HighConcurrentResult test(Runnable runnable) {
        // 计时器开始
        timeInterval.start();

        // 插入任务：runnable * threadSize
        this.syncFinisher.addRepeatWorker(runnable);
        // 设置多线程任务是否需要同时开始
        this.syncFinisher.syncStart(true);
        // 多线程任务开始&是否需要等待所有任务完成
        this.syncFinisher.start(true);

        // 多线程任务耗时
        long interval = timeInterval.interval();

        List<SyncFinisher.SyncException> workerErrorMessages = syncFinisher.getWorkerErrorMessages();

        return HighConcurrentResult.builder()
                .timeInterval(this.timeInterval)
                .interval(interval)
                .taskCount(syncFinisher.workersCount())
                .taskSuccessCount(syncFinisher.getWorkerSuccessCount().get())
                .taskErrorCount(syncFinisher.getWorkerErrorCount().get())
                .errorMessages(workerErrorMessages.stream().map(SyncFinisher.SyncException::getErrorMessage).collect(Collectors.toList()))
                .build();
    }

}
