package io.github.kuugasky.kuugatool.cache.lock;

/**
 * 分布式锁方法的的操作实现接口
 *
 * @author kuuga
 * @see KuugaLock#quickAction(LockQuickAction, LockAcquireFail)
 * @since 2023-06-15
 */
public interface LockQuickAction {
    /**
     * 锁获取成功后的执行任务
     */
    void doAction();
}
