package io.github.kuugasky.kuugatool.core.enums;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;

import java.util.List;

/**
 * 任务拆分方式
 *
 * @author kuuga
 */
public enum TaskSplitType {
    /**
     * 按每个任务数据量拆分
     */
    DATA_SIZE,
    /**
     * 按任务数拆分
     */
    TASK_AMOUNT;

    /**
     * 根据集合拆分类型进行拆分
     *
     * @param sourceList 源数据集合
     * @param type       任务拆分类型
     * @param splitCount 拆分数
     * @param <T>        T
     * @return List
     */
    public static <T> List<List<T>> getSplits(List<T> sourceList, TaskSplitType type, int splitCount) {
        return switch (type) {
            case DATA_SIZE -> ListUtil.splitAvg(sourceList, splitCount);
            case TASK_AMOUNT -> ListUtil.splitAvgRatio(sourceList, splitCount);
        };
    }

}
