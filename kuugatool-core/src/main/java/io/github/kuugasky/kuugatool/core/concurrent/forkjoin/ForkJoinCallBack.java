package io.github.kuugasky.kuugatool.core.concurrent.forkjoin;


/**
 * ForkJoin回调函数式接口
 *
 * @author kuuga
 */
@FunctionalInterface
public interface ForkJoinCallBack<T, U> {

    /**
     * 执行内容
     *
     * @param value value
     * @return T
     */
    U execute(T value);

}
