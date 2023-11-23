package io.github.kuugasky.kuugatool.core.concurrent.synctask;

/**
 * 异常处理
 *
 * @author pengqinglong
 * @since 2022/4/28
 */
@FunctionalInterface
public interface ErrorHandle {

    /**
     * 异常处理
     *
     * @param e      异常
     * @param event  事件
     * @param source 源数据
     */
    <T> void handle(Exception e, TaskEvent event, T source);

}