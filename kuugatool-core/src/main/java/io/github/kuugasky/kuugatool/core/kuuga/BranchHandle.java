package io.github.kuugasky.kuugatool.core.kuuga;

/**
 * BranchHandle
 * <p>
 * 分支处理接口
 *
 * @author kuuga
 * @since 2022/11/17-11-17 20:42
 */
@FunctionalInterface
public interface BranchHandle {

    /**
     * 分支操作
     *
     * @param trueHandle  为true时要进行的操作
     * @param falseHandle 为false时要进行的操作
     **/
    void trueOrFalseHandle(Runnable trueHandle, Runnable falseHandle);

}

