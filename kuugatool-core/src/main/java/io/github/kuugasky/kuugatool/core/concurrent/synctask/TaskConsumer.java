package io.github.kuugasky.kuugatool.core.concurrent.synctask;

/**
 * 任务结果消费
 *
 * @author pengqinglong
 * @since 2022/4/20
 */
@FunctionalInterface
public interface TaskConsumer<T> {
    /**
     * 任务消费
     *
     * @param event  任务事件
     * @param source 源数据
     * @param result 结果
     */
    void consumer(TaskEvent event, T source, Result result);
}