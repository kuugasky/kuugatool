package io.github.kuugasky.kuugatool.core.concurrent.timer;

/**
 * 多线程任务执行结果
 *
 * @author kuuga, 2013-12-24
 */
public enum ExecuteResult {
    /**
     * 正常
     **/
    OK,
    /**
     * 错误
     **/
    ERROR,
    /**
     * 超时
     **/
    TIMEOUT
}
