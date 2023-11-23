package io.github.kuugasky.kuugatool.cache.lock;

import io.github.kuugasky.kuugatool.cache.KuugaCache;
import io.github.kuugasky.kuugatool.cache.basic.cachekey.KuugaBaseCacheKey;
import io.github.kuugasky.kuugatool.cache.basic.enums.CacheOperationType;
import io.github.kuugasky.kuugatool.cache.redis.RedisCacheImpl;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 分布式锁实现
 * <p>
 * 基于redis的单线程递增机制，若其他缓存组件可能不适用
 * <p>
 * 分布式锁简化方法{@link LockQuickAction}，{@link LockAcquireFail}
 *
 * @author kuuga
 * @see <a href="https://redis.io/commands/set">Redis版本要求2.6.12+</a>
 * @since 2023-06-14
 */
@Slf4j
public final class KuugaLock {

    private final KuugaCache kuugaCache;
    private final AtomicBoolean localHasLock;
    private final String key;
    /**
     * 默认加锁时长
     **/
    private final static int DEFAULT_CACHE_TIME = 2 * 60;
    /**
     * 默认分布式锁间隔尝试时间
     */
    private final static int DEFAULT_INTERVAL_TRY_TIME = 1000;

    /**
     * 分布式锁构造器
     *
     * @param kuugaCache 缓存实现bean
     * @param key        缓存键，1Lock对应1Key
     */
    public KuugaLock(KuugaCache kuugaCache, String key) {
        this.kuugaCache = kuugaCache;
        if (StringUtil.isEmpty(key)) {
            throw new RuntimeException("RedisLock的缓存key必填");
        }
        this.key = key;
        localHasLock = new AtomicBoolean(false);
    }

    /**
     * 判断是否已经加锁
     *
     * @return boolean
     */
    public boolean isLock() {
        boolean lock = localHasLock.get();
        if (!lock) {
            lock = kuugaCache.ttl(KuugaBaseCacheKey.REDIS_LOCK, key) > 0;
            localHasLock.compareAndSet(false, lock);
        }
        return lock;
    }

    /**
     * 分布式解锁
     *
     * @param force 强制解锁
     */
    public void unLock(boolean force) {
        if (force) {
            unKuugaLock();
        } else {
            unLock();
        }
    }

    /**
     * 分布式解锁
     **/
    public void unLock() {
        if (isLock()) {
            unKuugaLock();
        }
    }

    /**
     * 分布式加锁
     *
     * @param second 加锁时间，默认为{@link #DEFAULT_CACHE_TIME}
     */
    private boolean kuugaLock(Integer second) {
        // 加搜时间如果为空，则默认为2分钟（这里有个问题，假设两分钟任务都没执行完，则锁失效会被其他线程获取）
        if (second == null) {
            second = DEFAULT_CACHE_TIME;
        }
        final int lockSecond = second;

        // redis的另一种实现方式
        if (kuugaCache instanceof RedisCacheImpl) {
            // 封装key
            String lockKey = kuugaCache.wrapKey(KuugaBaseCacheKey.REDIS_LOCK, key);
            // 使用redis的setIfAbsent进行key赋值
            return (Boolean) kuugaCache.doCustomAction(CacheOperationType.SAVE,
                    redisTemplate -> redisTemplate.opsForValue()
                            .setIfAbsent(lockKey, 1, lockSecond, TimeUnit.SECONDS));
        } else {
            // 通用实现方式，非原子性
            long lockNum = kuugaCache.incr(KuugaBaseCacheKey.REDIS_LOCK, key);
            // 如果存储的锁资源大于1，即已经被其他渠道加了锁
            if (lockNum > 1) {
                return false;
            } else {
                kuugaCache.expire(KuugaBaseCacheKey.REDIS_LOCK, key, lockSecond);
                return true;
            }
        }
    }

    /**
     * 分布式解锁
     **/
    private void unKuugaLock() {
        kuugaCache.remove(KuugaBaseCacheKey.REDIS_LOCK, key);
        localHasLock.set(false);
    }

    /**
     * 分布式加锁
     * <p>
     * 注意：Redis的分布式锁和SETNX是不同的概念。<br>
     * 分布式锁是一种用于实现控制共享资源访问的机制，这里采用的是juc的cas和redis的setIfAbsent来实现分布式锁。<br>
     * 而SETNX是一种键值存储方式，可以用于实现简单的分布式锁，但在高并发环境下可能存在一些问题。
     *
     * @param second 加锁时间，可为空，默认为{@link #DEFAULT_CACHE_TIME}
     * @return 判断是否获得了锁资源
     */
    public boolean lock(Integer second) {
        if (!localHasLock.get()) {
            // 使用redis的setIfAbsent进行锁定
            boolean redisTrayLock = kuugaLock(second);
            // 处理单点并发，采用juc包下的AtomicBoolean进行cas获取锁
            localHasLock.compareAndSet(false, redisTrayLock);
        }
        return localHasLock.get();
    }

    /**
     * 分布式加锁，默认取得锁时间为{@link #DEFAULT_CACHE_TIME}
     *
     * @param tryTime 尝试次数
     * @return 判断是否获得了锁资源
     */
    public boolean tryLock(int tryTime) {
        return tryLock(tryTime, null);
    }

    /**
     * 分布式加锁，指定尝试次数及加锁时长
     *
     * @param tryTime    尝试次数
     * @param lockSecond 加锁时间，可为空，默认为{@link #DEFAULT_CACHE_TIME}
     * @return 判断是否获得了锁资源
     */
    public boolean tryLock(int tryTime, Integer lockSecond) {
        // 根据重试次数尝试获取分布式锁，默认为3次
        while (tryTime > 0) {
            boolean hasLock = lock(lockSecond);
            // 尝试次数-1
            tryTime--;
            if (!hasLock) {
                try {
                    // 获取不到分布式锁，就休眠1s
                    Thread.sleep(DEFAULT_INTERVAL_TRY_TIME);
                } catch (InterruptedException e) {
                    log.error("分布式加锁异常，稍后重试！errorMessage:" + e.getMessage());
                }
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 分布式加锁，默认取得锁时间为{@link #DEFAULT_CACHE_TIME}
     *
     * @return 判断是否获得了锁资源
     */
    public boolean lock() {
        return lock(null);
    }

    /**
     * 快速锁操作
     *
     * @param action 操作接口
     * @param fail   失败回调接口
     */
    public void quickAction(LockQuickAction action, LockAcquireFail fail) {
        tryQuickAction(action, fail, 1, null);
    }

    /**
     * 快速锁操作，带重试
     *
     * @param action  获得锁后的操作
     * @param fail    没有获得锁的操作
     * @param tryTime 重试次数
     */
    public void tryQuickAction(LockQuickAction action, LockAcquireFail fail, int tryTime) {
        tryQuickAction(action, fail, tryTime, null);
    }

    /**
     * 快速锁操作，带重试及加锁时长
     *
     * @param action     获得锁后的操作
     * @param fail       没有获得锁的操作
     * @param tryTime    重试次数
     * @param lockSecond 加锁时间，可为空，默认为{@link #DEFAULT_CACHE_TIME}
     */
    public void tryQuickAction(LockQuickAction action, LockAcquireFail fail,
                               int tryTime, Integer lockSecond) {
        while (tryTime > 0) {
            boolean hasLock = lock(lockSecond);
            tryTime--;
            if (hasLock) {
                try {
                    action.doAction();
                    return;
                } finally {
                    unLock();
                }
            }
        }
        fail.acquireFail();
    }

}
