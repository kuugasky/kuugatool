package io.github.kuugasky.kuugatool.core.concurrent.forkjoin;

import com.google.common.collect.Lists;
import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.enums.TaskSplitType;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * 使用一种分治算法，递归地将任务分割成更小的子任务，其中阈值可配置，然后把子任务分配给不同的线程并发执行，最后再把结果组合起来。
 * <p>
 * 该用法常见于数组与集合的运算。
 * <p>
 * 由于提交的任务不一定能够递归地分割成ForkJoinTask，且ForkJoinTask执行时间不等长，所以ForkJoinPool使用一种工作窃取的算法，允许空闲的线程“窃取”分配给另一个线程的工作。
 * <p>
 * 由于工作无法平均分配并执行。所以工作窃取算法能更高效地利用硬件资源。
 *
 * @author kuuga
 */
public final class ForkJoinPool {

    private final java.util.concurrent.ForkJoinPool forkJoinPool;

    private final List<ForkJoinTask<?>> tasks;

    /**
     * 创建ForkJoinPool线程池
     * 初始化tasks集合
     */
    private ForkJoinPool() {
        forkJoinPool = new java.util.concurrent.ForkJoinPool();
        tasks = Lists.newArrayList();
    }

    /**
     * 提供静态创建方法
     *
     * @return ForkJoinPool
     */
    private static ForkJoinPool getInstance() {
        return new ForkJoinPool();
    }

    /**
     * 处理任务
     *
     * @param value 入参值
     * @param task  消费任务
     * @return RecursiveTask
     */
    private <T, U> RecursiveTask<T> processTask(T value, final ForkJoinCallBack<T, U> task) {
        return new RecursiveTask<>() {
            @Override
            protected T compute() {
                return ObjectUtil.cast(task.execute(value));
            }
        };
    }

    /**
     * 将某个值放入线程中处理，并支持通过getResult()获取结果集
     *
     * @param value 入参值
     * @param task  消费任务
     */
    private <T, U> void submit(T value, ForkJoinCallBack<T, U> task) {
        tasks.add(forkJoinPool.submit(processTask(value, task)));
    }

    /**
     * 获取结果
     *
     * @return List<T>
     */
    public <U> List<U> getResult() {
        List<U> result = Lists.newArrayList();
        for (ForkJoinTask<?> task : tasks) {
            try {
                U value = ObjectUtil.cast(task.get());
                if (value != null) {
                    result.add(value);
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 提交任务
     *
     * @param sourceList 源数据
     * @param task       任务实现
     * @return instance
     */
    public static <T, U> ForkJoinPool submit(List<T> sourceList, ForkJoinCallBack<T, U> task) {
        ForkJoinPool instance = getInstance();
        ListUtil.optimize(sourceList).forEach(element -> instance.submit(element, task));
        return instance;
    }

    /**
     * 提交任务
     *
     * @param sourceList 源数据
     * @param type       集合拆分类型
     * @param splitCount 拆分数
     * @param task       任务实现
     * @return instance
     */
    public static <T, U> ForkJoinPool submit(List<T> sourceList, TaskSplitType type, int splitCount, ForkJoinCallBack<T, U> task) {
        ForkJoinPool instance = getInstance();
        if (ListUtil.isEmpty(sourceList)) {
            return instance;
        }
        List<List<T>> lists = TaskSplitType.getSplits(sourceList, type, splitCount);

        ListUtil.optimize(lists).forEach(list -> ListUtil.optimize(list).forEach(element -> instance.submit(element, task)));
        return instance;
    }

    /**
     * 提交任务并获取结果集
     *
     * @param sourceList 源数据
     * @param task       任务实现
     * @return instance
     */
    public static <T, U> List<U> submitAndGet(List<T> sourceList, ForkJoinCallBack<T, U> task) {
        ForkJoinPool forkJoinPool = submit(sourceList, task);
        return forkJoinPool.getResult();
    }

    /**
     * 提交任务并获取结果集
     *
     * @param sourceList 源数据
     * @param type       集合拆分类型
     * @param splitCount 拆分数
     * @param task       任务实现
     * @return instance
     */
    public static <T, U> List<U> submitAndGet(List<T> sourceList, TaskSplitType type, int splitCount, ForkJoinCallBack<T, U> task) {
        ForkJoinPool forkJoinPool = submit(sourceList, type, splitCount, task);
        return forkJoinPool.getResult();
    }

}
