package io.github.kuugasky.kuugatool.core.concurrent.synctask;

import lombok.Data;

/**
 * ErrorTask
 *
 * @author pengqinglong
 * @since 2022/4/28
 */
@Data
public class ErrorTask<T> {

    /**
     * 事件
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

    /**
     * 异常
     */
    private Exception exception;

    public ErrorTask(TaskEvent event, T source, Exception exception) {
        this.event = event;
        this.source = source;
        this.exception = exception;
    }

    public ErrorTask(TaskEvent event, T source, Result result, Exception exception) {
        this.event = event;
        this.source = source;
        this.result = result;
        this.exception = exception;
    }

}