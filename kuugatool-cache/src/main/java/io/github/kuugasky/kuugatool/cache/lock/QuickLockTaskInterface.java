package io.github.kuugasky.kuugatool.cache.lock;

/**
 * QuickLockTaskInterface
 *
 * @author kuuga
 * @since 2020-11-12 下午2:59
 */
@FunctionalInterface
public interface QuickLockTaskInterface<T> {

    /**
     * redisLock
     *
     * @return T
     */
    T redisLock();

}
