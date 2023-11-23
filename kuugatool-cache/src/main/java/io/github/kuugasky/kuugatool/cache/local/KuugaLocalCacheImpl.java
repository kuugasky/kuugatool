package io.github.kuugasky.kuugatool.cache.local;

import io.github.kuugasky.kuugatool.cache.KuugaCache;
import io.github.kuugasky.kuugatool.cache.basic.cachekey.CacheKeyType;
import io.github.kuugasky.kuugatool.cache.basic.enums.CacheOperationType;
import io.github.kuugasky.kuugatool.cache.config.KuugaLocalCacheConfig;
import io.github.kuugasky.kuugatool.cache.redis.KuugaCacheAction;
import io.github.kuugasky.kuugatool.core.date.DateUtil;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 本地实现的缓存组件
 * <p>架构默认使用RedisCache，当应用无需使用Redis中间件时（如仅仅只是本地应用）
 * 则可以配置本地缓存实现，以简化系统实现结构
 *
 * @author kuuga
 * @see KuugaCache
 * @see KuugaLocalCacheConfig
 */
public class KuugaLocalCacheImpl implements KuugaCache {

    /**
     * 本地缓存桶
     */
    private final ConcurrentHashMap<String, ConcurrentHashMap<String, KuugaLocalCacheValue>> localCache = new ConcurrentHashMap<>();

    /**
     * 系统命名空间
     */
    private String systemUnique = "CLOUD_ANONYMOUS";

    /**
     * 过期处理线程池
     */
    private static final ScheduledExecutorService EXPIRE_POOL = Executors.newScheduledThreadPool(5);

    @Override
    public void setSystemUnique(String systemUnique) {
        this.systemUnique = systemUnique;
    }

    @Override
    public void publish(String channel, Object message) {
        throw new RuntimeException("non-supported operation");
    }

    @Override
    public Boolean setIfAbsent(CacheKeyType cacheTypeEnum, String key, Object value) {
        throw new RuntimeException("non-supported operation");
    }

    @Override
    public Boolean setIfAbsent(CacheKeyType cacheTypeEnum, String key, Object value, long timeout, TimeUnit unit) {
        throw new RuntimeException("non-supported operation");
    }

    @Override
    public Boolean setBit(CacheKeyType cacheTypeEnum, String key, long offset, boolean value) {
        throw new RuntimeException("non-supported operation");
    }

    @Override
    public Boolean getBit(CacheKeyType cacheTypeEnum, String key, long offset) {
        throw new RuntimeException("non-supported operation");
    }

    @Override
    public Long bitCount(CacheKeyType cacheTypeEnum, String key) {
        throw new RuntimeException("non-supported operation");
    }

    @Override
    public BitmapPos bitPosHitFirstOffset(CacheKeyType cacheTypeEnum, String key, boolean bit) {
        throw new RuntimeException("non-supported operation");
    }

    @Override
    public BitmapPos bitPosHitFirstOffset(CacheKeyType cacheTypeEnum, String key, boolean bit, long startOffset, long endOffset) {
        throw new RuntimeException("non-supported operation");
    }

    @Override
    public BitmapPos bitPosHitLastOffset(CacheKeyType cacheTypeEnum, String key, boolean bit, long startOffset, long endOffset) {
        throw new RuntimeException("non-supported operation");
    }

    @Override
    public String getSystemUnique() {
        return systemUnique;
    }

    /**
     * 返回缓存的对象
     *
     * @param cacheTypeEnum 缓存key类型枚举项
     * @param key           缓存key
     * @return 缓存值
     */
    @Override
    public Object get(CacheKeyType cacheTypeEnum, String key) {
        ConcurrentHashMap<String, KuugaLocalCacheValue> keyMap = pickCacheKeyMap(cacheTypeEnum);
        if (keyMap == null) {
            return null;
        }
        return returnUnModifyObject(keyMap.get(key));
    }

    /**
     * 根据缓存key类型枚举项获取所有缓存对象
     *
     * @param cacheTypeEnum 缓存key类型枚举项
     * @return 符合条件的缓存对象集合
     */
    @Override
    public List<Object> getAll(CacheKeyType cacheTypeEnum) {
        ConcurrentHashMap<String, KuugaLocalCacheValue> keyMap = pickCacheKeyMap(cacheTypeEnum);
        if (keyMap == null || keyMap.values().size() == 0) {
            return null;
        }
        List<Object> result = new ArrayList<>(keyMap.values().size() * 2);
        for (KuugaLocalCacheValue cacheValue : keyMap.values()) {
            Object rel = returnUnModifyObject(cacheValue);
            if (rel != null) {
                result.add(rel);
            }
        }
        return result;
    }

    /**
     * 根据缓存key类型枚举项和多个缓存key集合，获取多个缓存值对象集合
     *
     * @param cacheTypeEnum 缓存key类型枚举项
     * @param keys          缓存key集合
     * @return 符合条件的缓存值对象集合
     */
    @Override
    public List<Object> multiGet(CacheKeyType cacheTypeEnum, List<String> keys) {
        ConcurrentHashMap<String, KuugaLocalCacheValue> keyMap = pickCacheKeyMap(cacheTypeEnum);
        if (keyMap == null || keyMap.values().size() == 0) {
            return null;
        }
        List<Object> result = new ArrayList<>(keys.size() * 2);
        for (String key : keys) {
            Object rel = returnUnModifyObject(keyMap.get(key));
            if (rel != null) {
                result.add(rel);
            }
        }
        return result;
    }

    @Override
    public Object getHash(CacheKeyType cacheTypeEnum, String key, String hashKey) {
        Map<String, Object> hashMap = getHashAll(cacheTypeEnum, key);
        if (hashMap != null) {
            return hashMap.get(hashKey);
        }
        return null;
    }

    /**
     * 返回缓存的hashMap对象
     *
     * @param cacheTypeEnum 缓存key类型枚举项
     * @param key           缓存key
     * @return 所有符合条件的hash缓存map
     */
    @Override
    public Map<String, Object> getHashAll(CacheKeyType cacheTypeEnum, String key) {
        Object result = get(cacheTypeEnum, key);
        if (result != null && !(result instanceof Map)) {
            throw new RuntimeException("not hash type");
        }
        return ObjectUtil.cast(result);
    }

    @Override
    public Set<String> getHashAllKeys(CacheKeyType cacheTypeEnum, String key) {
        Map<String, Object> hashMap = getHashAll(cacheTypeEnum, key);
        if (hashMap != null) {
            return hashMap.keySet();
        }
        return null;
    }

    @Override
    public long getHashKeyCount(CacheKeyType cacheTypeEnum, String key) {
        Map<String, Object> hashMap = getHashAll(cacheTypeEnum, key);
        if (hashMap != null) {
            return hashMap.keySet().size();
        }
        return 0;
    }

    @Override
    public void put(CacheKeyType cacheTypeEnum, String key, Object value) {
        putToday(cacheTypeEnum, key, value);
    }

    // @Override
    // public void putWithoutExpiredTime(CacheKeyType cacheTypeEnum, String key, Object value) {
    //     throw new UnsupportedOperationException("Not supported");
    // }

    @Override
    public void putToday(CacheKeyType cacheTypeEnum, String key, Object value) {
        long expire = DateUtil.zeroOfDay(DateUtil.moreOrLessDays(new Date(), 1)).getTime();
        putValueToCache(cacheTypeEnum, key, null, value, false, expire);
    }

    @Override
    public void put(CacheKeyType cacheTypeEnum, String key, Object value, int second) {
        long expire = second * 1000L;
        putValueToCache(cacheTypeEnum, key, null, value, false, expire);
    }

    @Override
    public void putHashToday(CacheKeyType cacheTypeEnum, String key, String hashKey, Object value) {
        long expire = DateUtil.zeroOfDay(DateUtil.moreOrLessDays(new Date(), 1)).getTime();
        putValueToCache(cacheTypeEnum, key, hashKey, value, false, expire);
    }

    @Override
    public void putHash(CacheKeyType cacheTypeEnum, String key, String hashKey, Object value) {
        putHashToday(cacheTypeEnum, key, hashKey, value);
    }

    @Override
    public void putHash(CacheKeyType cacheTypeEnum, String key, String hashKey, Object value, int second) {
        long expire = second * 1000L;
        putValueToCache(cacheTypeEnum, key, hashKey, value, false, expire);
    }

    @Override
    public void removeHash(CacheKeyType cacheTypeEnum, String key, String hashKey) {
        ConcurrentHashMap<String, KuugaLocalCacheValue> keyMap = pickCacheKeyMap(cacheTypeEnum);
        if (keyMap == null) {
            return;
        }
        KuugaLocalCacheValue hashValue = keyMap.get(key);
        if (hashValue instanceof Map<?, ?> map) {
            map.remove(hashKey);
        }
    }

    @Override
    public void remove(CacheKeyType cacheTypeEnum, String key) {
        ConcurrentHashMap<String, KuugaLocalCacheValue> keyMap = pickCacheKeyMap(cacheTypeEnum);
        if (keyMap != null) {
            keyMap.remove(key);
        }
    }

    @Override
    public void unlink(CacheKeyType cacheTypeEnum, String key) {
        remove(cacheTypeEnum, key);
    }

    @Override
    public void removeAll(CacheKeyType cacheTypeEnum) {
        String cacheTypeKey = wrapCacheKey(cacheTypeEnum);
        localCache.remove(cacheTypeKey);
    }

    @Override
    public void removeAll(CacheKeyType cacheTypeEnum, String key) {
        throw new RuntimeException("non-supported operation");
    }

    @Override
    public void expire(CacheKeyType cacheTypeEnum, String key, int timeout) {
        ConcurrentHashMap<String, KuugaLocalCacheValue> keyMap = pickCacheKeyMap(cacheTypeEnum);
        if (keyMap == null) {
            return;
        }
        KuugaLocalCacheValue oldValue = keyMap.get(key);
        if (oldValue == null) {
            return;
        }
        long expire = timeout * 1000L;
        KuugaLocalCacheValue newValue = new KuugaLocalCacheValue(oldValue.getValue(), expire);
        putValueAndInitExpireWork(key, keyMap, newValue);
        oldValue.setOverride(true);
    }

    @Override
    public synchronized long incr(CacheKeyType cacheTypeEnum, String key) {
        return incr(cacheTypeEnum, key, 1L);
    }

    @Override
    public synchronized long incr(CacheKeyType cacheTypeEnum, String key, Integer second) {
        return incr(cacheTypeEnum, key, 1L, second);
    }

    @Override
    public synchronized long incr(CacheKeyType cacheTypeEnum, String key, long incrValue) {
        return incr(cacheTypeEnum, key, 1L, null);
    }

    @Override
    public synchronized long incr(CacheKeyType cacheTypeEnum, String key, long incrValue, Integer second) {
        long expire;
        if (second != null && second > 0) {
            expire = second * 1000;
        } else {
            expire = DateUtil.zeroOfDay(DateUtil.moreOrLessDays(new Date(), 1)).getTime();
        }
        Object incrObj = putValueToCache(cacheTypeEnum, key, null, incrValue, true, expire);
        return Long.parseLong(String.valueOf(incrObj));
    }

    @Override
    public long getIncr(CacheKeyType cacheTypeEnum, String key) {
        Object result = get(cacheTypeEnum, key);
        if (result != null) {
            return (Long) result;
        }
        return 0;
    }

    @Override
    public synchronized long decr(CacheKeyType cacheTypeEnum, String key) {
        return decr(cacheTypeEnum, key, 1L);
    }

    @Override
    public synchronized long decr(CacheKeyType cacheTypeEnum, String key, Integer second) {
        return decr(cacheTypeEnum, key, 1L, second);
    }

    @Override
    public synchronized long decr(CacheKeyType cacheTypeEnum, String key, long incrValue) {
        return decr(cacheTypeEnum, key, 1L, null);
    }

    @Override
    public synchronized long decr(CacheKeyType cacheTypeEnum, String key, long decrValue, Integer second) {
        long expire;
        if (second != null && second > 0) {
            expire = second * 1000;
        } else {
            expire = DateUtil.zeroOfDay(DateUtil.moreOrLessDays(new Date(), 1)).getTime();
        }
        return (Long) putValueToCacheForDecr(cacheTypeEnum, key, decrValue, expire);
    }

    @Override
    public long getDecr(CacheKeyType cacheTypeEnum, String key) {
        Object result = get(cacheTypeEnum, key);
        if (result != null) {
            return (Long) result;
        }
        return 0;
    }

    @Override
    public boolean persist(CacheKeyType cacheTypeEnum, String key) {
        throw new RuntimeException("non-supported operation");
    }

    @Override
    public long ttl(CacheKeyType cacheTypeEnum, String key) {
        ConcurrentHashMap<String, KuugaLocalCacheValue> keyMap = pickCacheKeyMap(cacheTypeEnum);
        if (keyMap != null) {
            KuugaLocalCacheValue value = keyMap.get(key);
            if (value != null) {
                long left = (value.getExpireTime() + value.getCacheTime() - System.currentTimeMillis()) / 1000;
                return left < 0 ? 0 : left;
            }
        }
        return 0;
    }

    @Override
    public synchronized Object getAndSet(CacheKeyType cacheTypeEnum, String key, Object value) {
        ConcurrentHashMap<String, KuugaLocalCacheValue> keyMap = pickCacheKeyMap(cacheTypeEnum);
        Object old = null;
        if (keyMap != null) {
            KuugaLocalCacheValue oldValue = keyMap.get(key);
            old = returnUnModifyObject(oldValue);
            long expireTime;
            if (oldValue != null) {
                expireTime = oldValue.getExpireTime();
                oldValue.setOverride(true);
            } else {
                expireTime = Objects.requireNonNull(DateUtil.zeroOfDay(DateUtil.moreOrLessDays(new Date(), 1))).getTime();
            }
            KuugaLocalCacheValue newValue = new KuugaLocalCacheValue(value, expireTime);
            putValueAndInitExpireWork(key, keyMap, newValue);
        }
        return old;
    }

    @Override
    public Object doCustomAction(CacheOperationType type, KuugaCacheAction<RedisTemplate<String, Object>> action) {
        throw new RuntimeException("non-supported operation");
    }

    @Override
    public String wrapKey(CacheKeyType cacheTypeEnum, String key) {
        return wrapCacheKey(cacheTypeEnum) + ":" + key;
    }

    /**
     * 将数据存进缓存，并增加过期任务
     *
     * @param key      缓存key
     * @param keyMap   缓存keyMap
     * @param newValue 缓存值对象
     * @return 缓存值对象
     */
    private KuugaLocalCacheValue putValueAndInitExpireWork(String key, ConcurrentHashMap<String, KuugaLocalCacheValue> keyMap, KuugaLocalCacheValue newValue) {
        KuugaLocalCacheValue old = keyMap.put(key, newValue);
        EXPIRE_POOL.schedule(new KuugaExpireWork(newValue, key, keyMap), newValue.getExpireTime(), TimeUnit.MILLISECONDS);
        return old;
    }

    /**
     * 包装key的方法
     **/
    private String wrapCacheKey(CacheKeyType cacheTypeEnum) {
        return getSystemUnique() + ":" + cacheTypeEnum.getBaseType() + ":" + cacheTypeEnum.getCacheKey() + ":";
    }

    /**
     * 根据缓存key枚举获取缓存ConcurrentHashMap对象
     *
     * @param cacheTypeEnum 缓存key枚举
     * @return ConcurrentHashMap<String, LocalCacheValue>
     */
    private ConcurrentHashMap<String, KuugaLocalCacheValue> pickCacheKeyMap(CacheKeyType cacheTypeEnum) {
        String cacheTypeKey = wrapCacheKey(cacheTypeEnum);
        return localCache.get(cacheTypeKey);
    }

    private ConcurrentHashMap<String, KuugaLocalCacheValue> pickCacheKeyMapWithoutAbsent(CacheKeyType cacheTypeEnum) {
        ConcurrentHashMap<String, KuugaLocalCacheValue> keyMap = pickCacheKeyMap(cacheTypeEnum);
        if (keyMap == null) {
            keyMap = new ConcurrentHashMap<>(1);
            localCache.put(wrapCacheKey(cacheTypeEnum), keyMap);
        }
        return keyMap;
    }

    /**
     * 返回未修改对象
     *
     * @param value 缓存值对象
     * @return 缓存值
     */
    private Object returnUnModifyObject(KuugaLocalCacheValue value) {
        if (value != null && value.getValue() != null) {
            return value.getValue();
        }
        return null;
    }

    private Object putValueToCache(CacheKeyType cacheTypeEnum, String key, String hashKey,
                                   Object value, boolean isIncr, long expireTime) {
        ConcurrentHashMap<String, KuugaLocalCacheValue> keyMap = pickCacheKeyMapWithoutAbsent(cacheTypeEnum);
        KuugaLocalCacheValue cacheValue;
        if (isIncr) {
            Long old;
            KuugaLocalCacheValue oldCacheValue = keyMap.get(key);
            if (oldCacheValue != null) {
                old = (Long) oldCacheValue.getValue();
                oldCacheValue.setOverride(true);
            } else {
                old = 0L;
            }
            cacheValue = new KuugaLocalCacheValue(old + (Long) value, expireTime);
            putValueAndInitExpireWork(key, keyMap, cacheValue);
            return cacheValue.getValue();
        } else {
            if (hashKey != null) {
                ConcurrentHashMap<String, Object> hashValue = new ConcurrentHashMap<>(1);
                hashValue.put(hashKey, value);
                cacheValue = new KuugaLocalCacheValue(hashValue, expireTime);
            } else {
                cacheValue = new KuugaLocalCacheValue(value, expireTime);
            }
            KuugaLocalCacheValue old = putValueAndInitExpireWork(key, keyMap, cacheValue);
            if (old == null) {
                return null;
            } else {
                return old.getValue();
            }
        }
    }

    private Object putValueToCacheForDecr(CacheKeyType cacheTypeEnum, String key, Object value, long expireTime) {
        ConcurrentHashMap<String, KuugaLocalCacheValue> keyMap = pickCacheKeyMapWithoutAbsent(cacheTypeEnum);
        KuugaLocalCacheValue cacheValue;
        Long old;
        KuugaLocalCacheValue oldCacheValue = keyMap.get(key);
        if (oldCacheValue != null) {
            old = (Long) oldCacheValue.getValue();
            oldCacheValue.setOverride(true);
        } else {
            old = 0L;
        }
        cacheValue = new KuugaLocalCacheValue(old - (Long) value, expireTime);
        putValueAndInitExpireWork(key, keyMap, cacheValue);
        return cacheValue.getValue();
    }

}
