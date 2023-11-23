package io.github.kuugasky.kuugatool.core.kuuga;

import java.util.function.Consumer;

/**
 * PresentOrElseHandler
 * <p>
 * 空值与非空值分支处理
 *
 * @author kuuga
 * @since 2022/11/17-11-17 20:44
 */
public interface PresentOrElseHandler<T extends Object> {

    /**
     * 值不为空时执行消费操作
     * 值为空时执行其他的操作
     *
     * @param action      值不为空时，执行的消费操作
     * @param emptyAction 值为空时，执行的操作
     **/
    void presentOrElseHandle(Consumer<? super T> action, Runnable emptyAction);

}