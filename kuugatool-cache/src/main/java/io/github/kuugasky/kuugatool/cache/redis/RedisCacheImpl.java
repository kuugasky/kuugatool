package io.github.kuugasky.kuugatool.cache.redis;

import io.github.kuugasky.kuugatool.cache.KuugaCache;
import io.github.kuugasky.kuugatool.cache.basic.cachekey.CacheKeyType;
import io.github.kuugasky.kuugatool.cache.basic.enums.CacheOperationType;
import io.github.kuugasky.kuugatool.cache.config.KuugaRedisConfig;
import io.github.kuugasky.kuugatool.core.collection.BitmapOffsetUtil;
import io.github.kuugasky.kuugatool.core.date.DateUtil;
import io.github.kuugasky.kuugatool.core.lang.BooleanUtil;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import org.springframework.data.domain.Range;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 基于Redis接口实现的依赖于Redis缓存中间件的缓存组件
 * <p>
 * 架构默认的顶层命名空间是{@link RedisCacheImpl#systemUnique}，请务必定义系统本身的顶层命名空间
 *
 * @author kuuga
 * @see KuugaCache
 * @see KuugaRedisConfig
 * @since 2023-06-15
 */
public class RedisCacheImpl implements KuugaCache {

    /**
     * 缓存模板MAP，包含SAVE和QUERY
     */
    private final Map<String, RedisTemplate<String, Object>> redis;
    /**
     * 默认系统命名空间
     */
    private String systemUnique = "CLOUD_ANONYMOUS";
    /**
     * 是否使用RedisSessionBase
     */
    private boolean useRedisSessionBase;

    public RedisCacheImpl(Map<String, RedisTemplate<String, Object>> redis) {
        this.redis = redis;
    }

    @Override
    public void setSystemUnique(String systemUnique) {
        this.systemUnique = systemUnique;
    }

    @Override
    public void publish(String channel, Object message) {
        // doCustomAction方式
        // this.doCustomAction(CacheOperationType.SAVE, actionExecutor -> {
        //     ((RedisTemplate<?, ?>) actionExecutor).convertAndSend(channel, message);
        //     return null;
        // });
        getCacheTemplate(CacheOperationType.SAVE).convertAndSend(channel, message);
    }

    @Override
    public Boolean setIfAbsent(CacheKeyType cacheTypeEnum, String key, Object value) {
        RedisTemplate<String, Object> redisTemplate = getCacheTemplate(CacheOperationType.SAVE);
        String wrapKey = wrapKey(cacheTypeEnum, key);
        return redisTemplate.opsForValue().setIfAbsent(wrapKey, value);
    }

    @Override
    public Boolean setIfAbsent(CacheKeyType cacheTypeEnum, String key, Object value, long timeout, TimeUnit unit) {
        RedisTemplate<String, Object> redisTemplate = getCacheTemplate(CacheOperationType.SAVE);
        String wrapKey = wrapKey(cacheTypeEnum, key);
        return redisTemplate.opsForValue().setIfAbsent(wrapKey, value, timeout, unit);
    }

    @Override
    public Boolean setBit(CacheKeyType cacheTypeEnum, String key, long offset, boolean value) {
        RedisTemplate<String, Object> redisTemplate = getCacheTemplate(CacheOperationType.SAVE);
        String wrapKey = wrapKey(cacheTypeEnum, key);
        return redisTemplate.opsForValue().setBit(wrapKey, offset, value);
    }

    @Override
    public Boolean getBit(CacheKeyType cacheTypeEnum, String key, long offset) {
        RedisTemplate<String, Object> redisTemplate = getCacheTemplate(CacheOperationType.SAVE);
        String wrapKey = wrapKey(cacheTypeEnum, key);
        return redisTemplate.opsForValue().getBit(wrapKey, offset);
    }

    @Override
    public Long bitCount(CacheKeyType cacheTypeEnum, String key) {
        RedisTemplate<String, Object> redisTemplate = getCacheTemplate(CacheOperationType.SAVE);
        String wrapKey = wrapKey(cacheTypeEnum, key);
        RedisCallback<Long> callback = connection -> connection.bitCount(wrapKey.getBytes(StandardCharsets.UTF_8));
        return redisTemplate.execute(callback);
    }

    @Override
    public BitmapPos bitPosHitFirstOffset(CacheKeyType cacheTypeEnum, String key, boolean bit) {
        RedisTemplate<String, Object> redisTemplate = getCacheTemplate(CacheOperationType.SAVE);
        String wrapKey = wrapKey(cacheTypeEnum, key);
        RedisCallback<Long> callback = connection -> connection.bitPos(wrapKey.getBytes(StandardCharsets.UTF_8), bit);
        Long offset = redisTemplate.execute(callback);
        if (ObjectUtil.isNull(offset)) {
            return null;
        }
        BitmapPos bitmapPos = new BitmapPos();
        bitmapPos.setHitOffset(offset);
        bitmapPos.setOffsetValue(getBit(cacheTypeEnum, key, offset));
        long[] offsetRange = BitmapOffsetUtil.bitOffsetRange(offset);
        bitmapPos.setStartOfRange(offsetRange[0]);
        bitmapPos.setEndOfRange(offsetRange[1]);
        return bitmapPos;
    }

    @Override
    public BitmapPos bitPosHitFirstOffset(CacheKeyType cacheTypeEnum, String key, boolean bit, long startOffset, long endOffset) {
        RedisTemplate<String, Object> redisTemplate = getCacheTemplate(CacheOperationType.SAVE);
        String wrapKey = wrapKey(cacheTypeEnum, key);
        // 返回字符串中第一个位设置为给定位的位置。范围开始和结束可以包含负值，以便从字符串末尾开始索引字节，其中-1是最后一个字节，-2是倒数第二个字节。
        long from = BitmapOffsetUtil.byteRange(startOffset)[0];
        long to = BitmapOffsetUtil.byteRange(endOffset)[0];
        RedisCallback<Long> callback = connection -> connection.bitPos(wrapKey.getBytes(StandardCharsets.UTF_8), bit, Range.leftOpen(from, to));
        Long offset = redisTemplate.execute(callback);
        if (ObjectUtil.isNull(offset)) {
            return null;
        }
        BitmapPos bitmapPos = new BitmapPos();
        bitmapPos.setHitOffset(offset);
        bitmapPos.setOffsetValue(getBit(cacheTypeEnum, key, offset));
        bitmapPos.setStartOfRange(BitmapOffsetUtil.bitOffsetRange(startOffset)[0]);
        bitmapPos.setEndOfRange(BitmapOffsetUtil.bitOffsetRange(endOffset)[1]);
        return bitmapPos;
    }

    @Override
    public BitmapPos bitPosHitLastOffset(CacheKeyType cacheTypeEnum, String key, boolean bit, long startOffset, long endOffset) {
        RedisTemplate<String, Object> redisTemplate = getCacheTemplate(CacheOperationType.QUERY);

        String wrapKey = wrapKey(cacheTypeEnum, key);

        Long lastHitOffset;

        RedisCallback<Long> lastHitOffsetCallback;
        // 查找最后一字节(8bit)的区间中，第一个符合条件的偏移量
        if (startOffset >= 0 || endOffset >= 0) {
            long from = BitmapOffsetUtil.byteRange(startOffset)[0];
            long to = BitmapOffsetUtil.byteRange(endOffset)[0];
            if (from == to) {
                lastHitOffsetCallback = connection -> connection.bitPos(wrapKey.getBytes(StandardCharsets.UTF_8), bit, Range.leftOpen(from, to));
                lastHitOffset = redisTemplate.execute(lastHitOffsetCallback);
            } else if (to > from) {
                long startRetrieveIndex = to;
                do {
                    long finalStartRetrieveIndex = startRetrieveIndex;
                    lastHitOffsetCallback = connection -> connection.bitPos(wrapKey.getBytes(StandardCharsets.UTF_8), bit, Range.leftOpen(finalStartRetrieveIndex, finalStartRetrieveIndex));
                    lastHitOffset = redisTemplate.execute(lastHitOffsetCallback);
                    startRetrieveIndex--;
                } while ((ObjectUtil.isNull(lastHitOffset) || lastHitOffset == -1) && startRetrieveIndex >= from);
            } else {
                lastHitOffset = null;
            }
        } else {
            lastHitOffsetCallback = connection -> connection.bitPos(wrapKey.getBytes(StandardCharsets.UTF_8), bit, Range.leftOpen(-1L, -1L));
            lastHitOffset = redisTemplate.execute(lastHitOffsetCallback);
        }

        if (ObjectUtil.isNull(lastHitOffset) || lastHitOffset == -1) {
            return null;
        }

        long[] offsetRange = BitmapOffsetUtil.bitOffsetRange(lastHitOffset);

        BitmapPos bitmapPos = new BitmapPos();
        Long finalLastHitOffset = lastHitOffset;
        RedisCallback<Long> callback = connection -> {
            // 拿到最后区间里面第一个为true的偏移量
            long bitIndex = finalLastHitOffset;

            Boolean nextBit;

            long lastTrueOffset = finalLastHitOffset;

            while (bitIndex <= offsetRange[1]) {
                ++bitIndex;
                nextBit = connection.getBit(wrapKey.getBytes(StandardCharsets.UTF_8), bitIndex);
                if (BooleanUtil.isTrue(nextBit)) {
                    lastTrueOffset = bitIndex;
                }
            }
            return lastTrueOffset;
        };
        Long lastTrueOffset = redisTemplate.execute(callback);
        if (ObjectUtil.isNull(lastTrueOffset)) {
            return null;
        }
        bitmapPos.setHitOffset(lastTrueOffset);
        bitmapPos.setOffsetValue(getBit(cacheTypeEnum, key, lastTrueOffset));
        long[] offsetRangeOfLastNode = BitmapOffsetUtil.bitOffsetRange(lastTrueOffset);
        bitmapPos.setStartOfRange(offsetRangeOfLastNode[0]);
        bitmapPos.setEndOfRange(offsetRangeOfLastNode[1]);
        return bitmapPos;
    }

    @Override
    public String getSystemUnique() {
        return systemUnique;
    }

    /**
     * 是否使用RedisSessionBase
     *
     * @param useRedisSessionBase boolean
     */
    public void setUseRedisSessionBase(boolean useRedisSessionBase) {
        this.useRedisSessionBase = useRedisSessionBase;
    }

    /**
     * 获取指定key的数据
     *
     * @param cacheTypeEnum 缓存key类型枚举项
     * @param key           缓存key
     * @return 缓存值
     */
    @Override
    public Object get(CacheKeyType cacheTypeEnum, String key) {
        return getCacheTemplate(CacheOperationType.QUERY).opsForValue().get(wrapKey(cacheTypeEnum, key));
    }

    /**
     * 获取模糊匹配键类型对应的数据集，在负载代理redis集群下不生效
     *
     * @param cacheTypeEnum 缓存key类型枚举项
     * @return 数据集
     */
    @Override
    public List<Object> getAll(CacheKeyType cacheTypeEnum) {
        String keyPattern = "*" + wrapKey(cacheTypeEnum, "") + "*";
        Set<String> keySet = getCacheTemplate(CacheOperationType.QUERY).keys(keyPattern);
        if (keySet == null) {
            return null;
        }

        Iterator<String> it = keySet.iterator();
        List<Object> resultList = new ArrayList<>();
        while (it.hasNext()) {
            resultList.add(getCacheTemplate(CacheOperationType.QUERY).opsForValue().get(it.next()));
        }
        return resultList;
    }

    /**
     * 批量获取指定key的数据集
     *
     * @param cacheTypeEnum 缓存key类型枚举项
     * @param keys          缓存keys
     * @return keys对应的缓存数据集
     */
    @Override
    public List<Object> multiGet(CacheKeyType cacheTypeEnum, List<String> keys) {
        if (keys == null) {
            return null;
        }
        List<String> wrapKeys = new ArrayList<>(keys.size() * 2);
        for (String key : keys) {
            wrapKeys.add(wrapKey(cacheTypeEnum, key));
        }
        return getCacheTemplate(CacheOperationType.QUERY).opsForValue().multiGet(wrapKeys);
    }

    /**
     * 从hash缓存数据中获取数据
     *
     * @param cacheTypeEnum 缓存key类型枚举项
     * @param key           缓存key
     * @param hashKey       hashKey
     * @return 缓存值
     */
    @Override
    public Object getHash(CacheKeyType cacheTypeEnum, String key, String hashKey) {
        return getCacheTemplate(CacheOperationType.QUERY).<String, Object>opsForHash().get(wrapKey(cacheTypeEnum, key), hashKey);
    }

    /**
     * 获取hash数据
     *
     * @param cacheTypeEnum 缓存key类型枚举项
     * @param key           缓存key
     * @return hashAllMap
     */
    @Override
    public Map<String, Object> getHashAll(CacheKeyType cacheTypeEnum, String key) {
        return getCacheTemplate(CacheOperationType.QUERY).<String, Object>opsForHash().entries(wrapKey(cacheTypeEnum, key));
    }

    /**
     * 获取hash数据的key集合
     *
     * @param cacheTypeEnum 缓存key类型枚举项
     * @param key           缓存key
     * @return key缓存集合
     */
    @Override
    public Set<String> getHashAllKeys(CacheKeyType cacheTypeEnum, String key) {
        return getCacheTemplate(CacheOperationType.QUERY).<String, Object>opsForHash().keys(wrapKey(cacheTypeEnum, key));
    }

    /**
     * 获取hash数据的key集合
     *
     * @param cacheTypeEnum 缓存key类型枚举项
     * @param key           缓存key
     * @return key计数
     */
    @Override
    public long getHashKeyCount(CacheKeyType cacheTypeEnum, String key) {
        return getCacheTemplate(CacheOperationType.QUERY).<String, Object>opsForHash().size(wrapKey(cacheTypeEnum, key));
    }

    /**
     * 向指定key中存入数据，默认为当天有效
     *
     * @param cacheTypeEnum 缓存key类型枚举项
     * @param key           缓存key
     * @param value         缓存值
     */
    @Override
    public void put(CacheKeyType cacheTypeEnum, String key, Object value) {
        putToday(cacheTypeEnum, key, value);
    }

    // /**
    //  * 向指定key中存入数据，不指定过期时间
    //  */
    // @Override
    // public void putWithoutExpiredTime(CacheKeyType cacheTypeEnum, String key, Object value) {
    //     //getCacheTemplate(CacheOpeType.SAVE).opsForValue().set(wrapKey(cacheTypeEnum, key), value);
    // }

    /**
     * 向指定key中存入数据，当天有效
     *
     * @param cacheTypeEnum 缓存key类型枚举项
     * @param key           缓存key
     * @param value         缓存值
     */
    @Override
    public void putToday(CacheKeyType cacheTypeEnum, String key, Object value) {
        put(cacheTypeEnum, key, value, Integer.parseInt(String.valueOf(DateUtil.secondsLeftToday())));
    }

    /**
     * 向指定key中存入数据，当天有效
     *
     * @param cacheTypeEnum 缓存key类型枚举项
     * @param key           缓存key
     * @param value         缓存值
     * @param second        有效时间(s)
     */
    @Override
    public void put(CacheKeyType cacheTypeEnum, String key, Object value, int second) {
        getCacheTemplate(CacheOperationType.SAVE).opsForValue()
                .set(wrapKey(cacheTypeEnum, key), value, second, TimeUnit.SECONDS);
    }

    /**
     * 向指定key中存入Hash数据，当天有效
     *
     * @param cacheTypeEnum 缓存key类型枚举项
     * @param key           缓存key
     * @param hashKey       hashKey
     * @param value         缓存值
     */
    @Override
    public void putHashToday(CacheKeyType cacheTypeEnum, String key,
                             String hashKey, Object value) {
        putHash(cacheTypeEnum, key, hashKey, value, Integer.parseInt(String.valueOf(DateUtil.secondsLeftToday())));
    }

    /**
     * 向指定key中存入Hash数据，默认为当天有效
     *
     * @param cacheTypeEnum 缓存key类型枚举项
     * @param key           缓存key
     * @param hashKey       hashKey
     * @param value         缓存值
     */
    @Override
    public void putHash(CacheKeyType cacheTypeEnum, String key,
                        String hashKey, Object value) {
        putHashToday(cacheTypeEnum, key, hashKey, value);
    }

    /**
     * 向指定key中存入Hash数据
     *
     * @param cacheTypeEnum 缓存key类型枚举项
     * @param key           缓存key
     * @param hashKey       hashKey
     * @param value         缓存值
     * @param second        缓存有效时间(s)
     */
    @Override
    public void putHash(CacheKeyType cacheTypeEnum, String key,
                        String hashKey, Object value, int second) {
        getCacheTemplate(CacheOperationType.SAVE).<String, Object>opsForHash().put(wrapKey(cacheTypeEnum, key), hashKey, value);
        expire(cacheTypeEnum, key, second);
    }

    /**
     * 删除指定key的hash数据
     *
     * @param cacheTypeEnum 缓存key类型枚举项
     * @param key           缓存key
     * @param hashKey       haskKey
     */
    @Override
    public void removeHash(CacheKeyType cacheTypeEnum, String key, String hashKey) {
        getCacheTemplate(CacheOperationType.SAVE).<String, Object>opsForHash().delete(wrapKey(cacheTypeEnum, key), hashKey);
    }

    /**
     * 删除指定key的数据
     *
     * @param cacheTypeEnum 缓存key类型枚举项
     * @param key           缓存key
     */
    @Override
    public void remove(CacheKeyType cacheTypeEnum, String key) {
        getCacheTemplate(CacheOperationType.SAVE).delete(wrapKey(cacheTypeEnum, key));
    }

    /**
     * 指定key的数据，lazy free，大键无阻塞式删除
     *
     * @param cacheTypeEnum 缓存key类型枚举项
     * @param key           缓存key
     */
    @Override
    public void unlink(CacheKeyType cacheTypeEnum, String key) {
        getCacheTemplate(CacheOperationType.SAVE).unlink(wrapKey(cacheTypeEnum, key));
    }

    /**
     * 删除指定类型hash数据，代理集群下无效
     *
     * @param cacheTypeEnum 缓存key类型枚举项
     */
    @Override
    public void removeAll(CacheKeyType cacheTypeEnum) {
        removeAll(cacheTypeEnum, "");
    }

    /**
     * 删除指定类型，模糊匹配关键字的hash数据，代理集群下无效
     *
     * @param cacheTypeEnum 缓存key类型枚举项
     * @param key           缓存key
     */
    @Override
    public void removeAll(CacheKeyType cacheTypeEnum, String key) {
        String cacheKey = wrapKey(cacheTypeEnum, key);
        Set<String> keySet = getCacheTemplate(CacheOperationType.QUERY).keys("*" + cacheKey + "*");
        if (keySet == null) {
            return;
        }
        for (String s : keySet) {
            getCacheTemplate(CacheOperationType.SAVE).delete(s);
        }
    }

    /**
     * 指定缓存数据有效期时间
     *
     * @param cacheTypeEnum 缓存key类型
     * @param key           缓存key
     * @param timeout       有效期时间
     */
    @Override
    public void expire(CacheKeyType cacheTypeEnum, String key, int timeout) {
        getCacheTemplate(CacheOperationType.SAVE).expire(wrapKey(cacheTypeEnum, key), timeout, TimeUnit.SECONDS);
    }

    /**
     * 给定key数据自增操作
     *
     * @param cacheTypeEnum 缓存key类型
     * @param key           缓存key
     * @return 自增后的值
     */
    @Override
    public long incr(CacheKeyType cacheTypeEnum, String key) {
        return incr(cacheTypeEnum, key, 1L);
    }

    /**
     * 给定key数据自增操作，并设置超时时间
     *
     * @param cacheTypeEnum 缓存key类型
     * @param key           缓存key
     * @param second        缓存时间s
     * @return 自增后的值
     */
    @Override
    public long incr(CacheKeyType cacheTypeEnum, String key, Integer second) {
        return incr(cacheTypeEnum, key, 1L, second);
    }

    /**
     * 给定key数据自增指定步长操作
     *
     * @param cacheTypeEnum 缓存key类型
     * @param key           缓存key
     * @param incrValue     步长
     * @return 自增后的值
     */
    @Override
    public long incr(CacheKeyType cacheTypeEnum, String key, long incrValue) {
        return incr(cacheTypeEnum, key, incrValue, null);
    }

    /**
     * 给定key数据自增指定步长操作，并设置超时时间
     *
     * @param cacheTypeEnum 缓存key类型
     * @param key           缓存key
     * @param incrValue     步长
     * @param second        缓存时间s
     * @return 自增后的值
     */
    @Override
    public long incr(CacheKeyType cacheTypeEnum, String key, long incrValue, Integer second) {
        Long count = getCacheTemplate(CacheOperationType.SAVE).opsForValue().increment(wrapKey(cacheTypeEnum, key), incrValue);
        int expire;
        if (second != null && second > 0) {
            expire = second;
        } else {
            expire = Integer.parseInt(String.valueOf(DateUtil.secondsLeftToday()));
        }
        expire(cacheTypeEnum, key, expire);
        return count == null ? 0 : count;
    }

    /**
     * 获取自增数据的值
     *
     * @param cacheTypeEnum 缓存key类型
     * @param key           缓存key
     * @return 自减后的值
     */
    @Override
    public long getIncr(CacheKeyType cacheTypeEnum, String key) {
        RedisConnectionFactory connectionFactory = getCacheTemplate(CacheOperationType.QUERY).getConnectionFactory();
        if (ObjectUtil.isNull(connectionFactory)) {
            return 0;
        }
        RedisAtomicLong entityIdCounter = new RedisAtomicLong(wrapKey(cacheTypeEnum, key), connectionFactory);
        Long expire = entityIdCounter.getExpire();
        if (null != expire && expire == -1) {
            entityIdCounter.expire(Integer.parseInt(String.valueOf(DateUtil.secondsLeftToday())), TimeUnit.SECONDS);
        }
        return entityIdCounter.longValue();
    }

    /**
     * 给定key数据自减操作
     *
     * @param cacheTypeEnum 缓存key类型
     * @param key           缓存key
     * @return 自减后的值
     */
    @Override
    public long decr(CacheKeyType cacheTypeEnum, String key) {
        return decr(cacheTypeEnum, key, 1L);
    }

    /**
     * 给定key数据自减操作，并设置超时时间
     *
     * @param cacheTypeEnum 缓存key类型
     * @param key           缓存key
     * @param second        缓存时间s
     * @return 自减后的值
     */
    @Override
    public long decr(CacheKeyType cacheTypeEnum, String key, Integer second) {
        return decr(cacheTypeEnum, key, 1L, second);
    }

    /**
     * 给定key数据自减指定步长操作
     *
     * @param cacheTypeEnum 缓存key类型
     * @param key           缓存key
     * @param decrValue     步长
     * @return 自减后的值
     */
    @Override
    public long decr(CacheKeyType cacheTypeEnum, String key, long decrValue) {
        return decr(cacheTypeEnum, key, decrValue, null);
    }

    /**
     * 给定key数据自减指定步长操作，并设置超时时间
     *
     * @param cacheTypeEnum 缓存key类型枚举项
     * @param key           缓存key
     * @param decrValue     步长
     * @param second        缓存时间s
     * @return 自减后的值
     */
    @Override
    public long decr(CacheKeyType cacheTypeEnum, String key, long decrValue, Integer second) {
        Long count = getCacheTemplate(CacheOperationType.SAVE).opsForValue().decrement(wrapKey(cacheTypeEnum, key), decrValue);
        int expire;
        if (second != null && second > 0) {
            expire = second;
        } else {
            expire = Integer.parseInt(String.valueOf(DateUtil.secondsLeftToday()));
        }
        expire(cacheTypeEnum, key, expire);
        return count == null ? 0 : count;
    }

    /**
     * 获取自减数据的值
     *
     * @param cacheTypeEnum 缓存key类型
     * @param key           缓存key
     * @return 自减后的值
     */
    @Override
    public long getDecr(CacheKeyType cacheTypeEnum, String key) {
        RedisConnectionFactory connectionFactory = getCacheTemplate(CacheOperationType.QUERY).getConnectionFactory();
        assert connectionFactory != null;
        RedisAtomicLong entityIdCounter = new RedisAtomicLong(wrapKey(cacheTypeEnum, key), connectionFactory);
        Long expire = entityIdCounter.getExpire();
        if (null != expire && expire == -1) {
            entityIdCounter.expire(Integer.parseInt(String.valueOf(DateUtil.secondsLeftToday())), TimeUnit.SECONDS);
        }
        return entityIdCounter.longValue();
    }

    /**
     * 移除键的有效时间，使之变为永久有效，慎用
     *
     * @param cacheTypeEnum 缓存key类型
     * @param key           缓存key
     * @return 操作是否成功
     */
    @Deprecated
    @Override
    public boolean persist(CacheKeyType cacheTypeEnum, String key) {
        Boolean persist = getCacheTemplate(CacheOperationType.SAVE).persist(wrapKey(cacheTypeEnum, key));
        return BooleanUtil.isTrue(persist);
    }

    /**
     * 获取键数据剩余有效时间，单位(s)
     *
     * @param cacheTypeEnum 缓存key类型
     * @param key           缓存key
     * @return 缓存剩余时间s
     */
    @Override
    public long ttl(CacheKeyType cacheTypeEnum, String key) {
        Long expire = getCacheTemplate(CacheOperationType.QUERY).getExpire(wrapKey(cacheTypeEnum, key));
        return expire == null ? 0 : expire;
    }

    /**
     * 存储新值并返回旧值
     *
     * @param cacheTypeEnum 缓存key类型
     * @param key           缓存key
     * @param value         缓存值
     * @return 插入value，并返回key之前的值
     */
    @Override
    public Object getAndSet(CacheKeyType cacheTypeEnum, String key, Object value) {
        return getCacheTemplate(CacheOperationType.SAVE).opsForValue().getAndSet(wrapKey(cacheTypeEnum, key), value);
    }

    /**
     * 执行自定义redis操作
     *
     * @param type   缓存操作类型
     * @param action 缓存动作
     * @return 执行结果
     */
    @Override
    public Object doCustomAction(CacheOperationType type, KuugaCacheAction<RedisTemplate<String, Object>> action) {
        RedisTemplate<String, Object> cacheTemplate = getCacheTemplate(type);
        return action.doAction(cacheTemplate);
    }

    /**
     * 包装key的方法
     *
     * @param cacheTypeEnum 缓存key类型
     * @param key           缓存key
     * @return 组装好的缓存key
     */
    @Override
    public String wrapKey(CacheKeyType cacheTypeEnum, String key) {
        StringBuilder keyBuilder = new StringBuilder();
        if (useRedisSessionBase) {
            if (!CacheKeyType.TOKEN_BASE.equals(cacheTypeEnum.getBaseType())) {
                keyBuilder.append(getSystemUnique()).append(":");
            }
        } else {
            keyBuilder.append(getSystemUnique()).append(":");
        }
        keyBuilder.append(cacheTypeEnum.getBaseType()).append(":")
                .append(cacheTypeEnum.getCacheKey()).append(":")
                .append(key);
        return keyBuilder.toString();
    }

    /**
     * 根据类型返回redis模板对象
     *
     * @param type 缓存操作类型
     * @return 对应缓存操作类型的Redis模板
     */
    private RedisTemplate<String, Object> getCacheTemplate(CacheOperationType type) {
        return redis.get(type.toString());
    }

}
