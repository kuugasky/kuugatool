package io.github.kuugasky.kuugatool.cache.lock;

/**
 * 分布式锁简化方法的失败回调接口
 *
 * @author kuuga
 * @see KuugaLock#quickAction(LockQuickAction, LockAcquireFail)
 * @since 2023-06-14
 */
public interface LockAcquireFail {
    /**
     * 锁获取失败时的回调方法
     */
    void acquireFail();
}
