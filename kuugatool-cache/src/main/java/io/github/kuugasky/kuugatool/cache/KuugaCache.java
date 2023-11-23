package io.github.kuugasky.kuugatool.cache;

import io.github.kuugasky.kuugatool.cache.basic.cachekey.CacheKeyType;
import io.github.kuugasky.kuugatool.cache.basic.enums.CacheOperationType;
import io.github.kuugasky.kuugatool.cache.config.KuugaRedisConfig;
import io.github.kuugasky.kuugatool.cache.redis.KuugaCacheAction;
import io.github.kuugasky.kuugatool.cache.redis.subscribe.AbstractRedisMessageListenerContainer;
import lombok.Data;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 缓存模块支持启用Redis缓存或本地缓存
 * <p>
 * 本地缓存为{@link ConcurrentHashMap}实现<br>
 * 事实上该接口可以用任何缓存中间件的API实现。<br>
 * 注意如果自定义缓存组件，则需要去除对{@link KuugaRedisConfig}的引用，并参考之实现自定义的配置类
 *
 * @author kuuga
 */
public interface KuugaCache {

    /**
     * 获取指定key的数据
     *
     * @param cacheTypeEnum 缓存key类型
     * @param key           缓存key
     * @return 缓存中存储的结果
     */
    Object get(CacheKeyType cacheTypeEnum, String key);

    /**
     * 获取模糊匹配键类型对应的数据集，在负载代理redis集群下不生效
     * <p>不建议使用，可能会因为返回数据过多导致内存溢出</p>
     *
     * @param cacheTypeEnum 缓存key类型
     * @return 缓存中存储的结果
     */
    List<Object> getAll(CacheKeyType cacheTypeEnum);

    /**
     * 批量获取指定key的数据集
     *
     * @param cacheTypeEnum 缓存key类型
     * @param keys          缓存key集合
     * @return 缓存中存储的结果
     */
    List<Object> multiGet(CacheKeyType cacheTypeEnum, List<String> keys);

    /**
     * 从hash缓存数据中或的数据
     *
     * @param cacheTypeEnum 缓存key类型
     * @param key           缓存key
     * @param hashKey       hashKey
     * @return 缓存中存储的结果
     */
    Object getHash(CacheKeyType cacheTypeEnum, String key, String hashKey);

    /**
     * 获取hash数据
     *
     * @param cacheTypeEnum 缓存key类型
     * @param key           缓存key
     * @return 缓存中存储的结果
     */
    Map<String, Object> getHashAll(CacheKeyType cacheTypeEnum, String key);

    /**
     * 获取hash数据的key集合
     *
     * @param cacheTypeEnum 缓存key类型
     * @param key           缓存key
     * @return 缓存中存储的结果
     */
    Set<String> getHashAllKeys(CacheKeyType cacheTypeEnum, String key);

    /**
     * 获取hash数据的key总数
     *
     * @param cacheTypeEnum 缓存key类型
     * @param key           缓存key
     * @return 缓存中存储的结果
     */
    long getHashKeyCount(CacheKeyType cacheTypeEnum, String key);

    /**
     * 向指定key中存入数据，默认为当天有效
     *
     * @param cacheTypeEnum 缓存key类型
     * @param key           缓存key
     * @param value         指定值
     */
    void put(CacheKeyType cacheTypeEnum, String key, Object value);

    // /**
    //  * 向指定key中存入数据，不指定过期时间
    //  *
    //  * @param cacheTypeEnum 缓存key类型
    //  * @param key 缓存key
    //  * @param value 指定值
    //  */
    /*void putWithoutExpiredTime(CacheKeyType cacheTypeEnum, String key, Object value);*/

    /**
     * 向指定key中存入数据，当天有效
     *
     * @param cacheTypeEnum 缓存key类型
     * @param key           缓存key
     * @param value         指定值
     */
    void putToday(CacheKeyType cacheTypeEnum, String key, Object value);

    /**
     * 向指定key中存入数据，当天有效
     *
     * @param cacheTypeEnum 缓存key类型
     * @param key           缓存key
     * @param value         指定值
     * @param second        有效时间(s)
     */
    void put(CacheKeyType cacheTypeEnum, String key, Object value, int second);

    /**
     * 向指定key中存入Hash数据，当天有效
     *
     * @param cacheTypeEnum 缓存key类型
     * @param key           缓存key
     * @param hashKey       hashKey
     * @param value         指定值
     */
    void putHashToday(CacheKeyType cacheTypeEnum, String key, String hashKey, Object value);

    /**
     * 向指定key中存入Hash数据，默认为当天有效
     *
     * @param cacheTypeEnum 缓存key类型
     * @param key           缓存key
     * @param hashKey       hashKey
     * @param value         指定值
     */
    void putHash(CacheKeyType cacheTypeEnum, String key, String hashKey, Object value);

    /**
     * 向指定key中存入Hash数据
     *
     * @param cacheTypeEnum 缓存key类型
     * @param key           缓存key
     * @param hashKey       hashKey
     * @param value         指定值
     * @param second        有效时间(s)
     */
    void putHash(CacheKeyType cacheTypeEnum, String key, String hashKey, Object value, int second);

    /**
     * 删除指定key的hash数据
     *
     * @param cacheTypeEnum 缓存key类型
     * @param key           缓存key
     * @param hashKey       hashKey
     */
    void removeHash(CacheKeyType cacheTypeEnum, String key, String hashKey);

    /**
     * 删除指定key的数据
     *
     * @param cacheTypeEnum 缓存key类型
     * @param key           缓存key
     */
    void remove(CacheKeyType cacheTypeEnum, String key);

    /**
     * 指定key的数据，lazy free，大键无阻塞式删除
     *
     * @param cacheTypeEnum 缓存key类型
     * @param key           缓存key
     */
    void unlink(CacheKeyType cacheTypeEnum, String key);

    /**
     * 删除指定类型hash数据，代理集群下无效
     *
     * @param cacheTypeEnum 缓存key类型
     */
    void removeAll(CacheKeyType cacheTypeEnum);

    /**
     * 删除指定类型，模糊匹配关键字的hash数据，代理集群下无效
     *
     * @param cacheTypeEnum 缓存key类型
     * @param key           缓存key
     */
    void removeAll(CacheKeyType cacheTypeEnum, String key);

    /**
     * 指定缓存数据有效期时间
     *
     * @param cacheTypeEnum 缓存key类型
     * @param key           缓存key
     * @param timeout       有效期时间
     */
    void expire(CacheKeyType cacheTypeEnum, String key, int timeout);

    /**
     * 给定key数据自增操作
     *
     * @param cacheTypeEnum 缓存key类型
     * @param key           缓存key
     * @return 自增值
     */
    long incr(CacheKeyType cacheTypeEnum, String key);

    /**
     * 给定key数据自增操作，并设置超时时间
     *
     * @param cacheTypeEnum 缓存key类型
     * @param key           缓存key
     * @param second        超时时间
     * @return 自增值
     */
    long incr(CacheKeyType cacheTypeEnum, String key, Integer second);

    /**
     * 给定key数据自增指定步长操作
     *
     * @param cacheTypeEnum 缓存key类型
     * @param key           缓存key
     * @param incrValue     步长
     * @return 自增值
     */
    long incr(CacheKeyType cacheTypeEnum, String key, long incrValue);

    /**
     * 给定key数据自增指定步长操作，并设置超时时间
     *
     * @param cacheTypeEnum 缓存key类型
     * @param key           缓存key
     * @param incrValue     步长
     * @param second        超时时间
     * @return 自增值
     */
    long incr(CacheKeyType cacheTypeEnum, String key, long incrValue, Integer second);

    /**
     * 获取自增数据的值
     *
     * @param cacheTypeEnum 缓存key类型
     * @param key           缓存key
     * @return 自增值
     */
    long getIncr(CacheKeyType cacheTypeEnum, String key);

    // decr

    /**
     * 给定key数据自减操作
     *
     * @param cacheTypeEnum 缓存key类型
     * @param key           缓存key
     * @return 自减值
     */
    long decr(CacheKeyType cacheTypeEnum, String key);

    /**
     * 给定key数据自减操作，并设置超时时间
     *
     * @param cacheTypeEnum 缓存key类型
     * @param key           缓存key
     * @param second        超时时间
     * @return 自减值
     */
    long decr(CacheKeyType cacheTypeEnum, String key, Integer second);

    /**
     * 给定key自减操作指定步长操作
     *
     * @param cacheTypeEnum 缓存key类型
     * @param key           缓存key
     * @param incrValue     步长
     * @return 自减值
     */
    long decr(CacheKeyType cacheTypeEnum, String key, long incrValue);

    /**
     * 给定key自减操作指定步长操作，并设置超时时间
     *
     * @param cacheTypeEnum 缓存key类型
     * @param key           缓存key
     * @param incrValue     步长
     * @param second        超时时间
     * @return 自减值
     */
    long decr(CacheKeyType cacheTypeEnum, String key, long incrValue, Integer second);

    /**
     * 获取自减数据的值
     *
     * @param cacheTypeEnum 缓存key类型
     * @param key           缓存key
     * @return 自减值
     */
    long getDecr(CacheKeyType cacheTypeEnum, String key);

    /**
     * 移除键的有效时间，使之变为永久有效，慎用
     *
     * @param cacheTypeEnum 缓存key类型
     * @param key           缓存key
     * @return boolean
     */
    @Deprecated
    boolean persist(CacheKeyType cacheTypeEnum, String key);

    /**
     * 获取键数据剩余有效时间，单位(s)
     *
     * @param cacheTypeEnum 缓存key类型
     * @param key           缓存key
     * @return 键数据剩余有效时间
     */
    long ttl(CacheKeyType cacheTypeEnum, String key);

    /**
     * 存储新值并返回旧值
     *
     * @param cacheTypeEnum 缓存key类型
     * @param key           缓存key
     * @param value         缓存值
     * @return 原来存在的旧值，若无则为null
     */
    Object getAndSet(CacheKeyType cacheTypeEnum, String key, Object value);

    /**
     * 自定义redis行为
     *
     * @param type   缓存模板类型
     * @param action 缓存动作
     * @return 缓存值
     */
    Object doCustomAction(CacheOperationType type, KuugaCacheAction<RedisTemplate<String, Object>> action);

    /**
     * 包装key的方法
     *
     * @param cacheTypeEnum 缓存key类型
     * @param key           缓存key
     * @return 拼接好的缓存key
     */
    String wrapKey(CacheKeyType cacheTypeEnum, String key);

    /**
     * 获取系统命名空间
     *
     * @return 系统命名空间
     */
    String getSystemUnique();

    /**
     * 设置系统命名空间
     *
     * @param systemUnique 系统命名空间
     */
    void setSystemUnique(String systemUnique);

    // 订阅发布

    /**
     * 发布消息
     * <p>
     * 消息订阅参照:{@link AbstractRedisMessageListenerContainer}
     *
     * @param channel 渠道
     * @param message 消息
     */
    void publish(String channel, Object message);

    // setIfAbsent

    /**
     * 如果缓存为空，则赋值
     *
     * @param cacheTypeEnum 缓存key类型
     * @param key           缓存key
     * @param value         缓存值
     * @return 是否赋值成功
     */
    Boolean setIfAbsent(CacheKeyType cacheTypeEnum, String key, Object value);

    /**
     * 如果缓存为空，则赋值
     *
     * @param cacheTypeEnum 缓存key类型
     * @param key           缓存key
     * @param value         缓存值
     * @param timeout       失效时间
     * @param unit          时间单位
     * @return 是否赋值成功
     */
    Boolean setIfAbsent(CacheKeyType cacheTypeEnum, String key, Object value, long timeout, TimeUnit unit);

    // bitmap

    /**
     * 位图-设置位值
     * <p>
     * 设置存储在key处的值的偏移位。
     *
     * @param cacheTypeEnum 缓存key类型
     * @param key           缓存key
     * @param offset        偏移量
     * @param value         值
     * @return 返回该位置原来的值
     */
    Boolean setBit(CacheKeyType cacheTypeEnum, String key, long offset, boolean value);

    /**
     * 位图-获取位值
     *
     * @param cacheTypeEnum 缓存key类型
     * @param key           缓存key
     * @param offset        偏移量
     * @return 位值
     */
    Boolean getBit(CacheKeyType cacheTypeEnum, String key, long offset);

    /**
     * 位图-统计位值为1的量
     *
     * @param cacheTypeEnum 缓存key类型
     * @param key           缓存key
     * @return 符合条件数量
     */
    Long bitCount(CacheKeyType cacheTypeEnum, String key);

    /**
     * 命中第一个符合条件的偏移量
     *
     * @param cacheTypeEnum 缓存key类型
     * @param key           缓存key
     * @param bit           位值
     * @return 命中偏移量信息
     */
    BitmapPos bitPosHitFirstOffset(CacheKeyType cacheTypeEnum, String key, boolean bit);

    /**
     * 命中区间第一个符合条件的偏移量
     * <p>
     * 注意：<br>
     * startOffset和endOffset可能是跨了byte范围的，所以实际查询会取startOffset所在范围开始节点和endOffset所在范围结束节点去检索第一个bit值符合的offset。<br>
     * 所以命中offset范围并不是startOffset-endOffset，而是在startOffset[Range.start]和endOffset[Range.end]中。
     *
     * @param cacheTypeEnum 缓存key类型
     * @param key           缓存key
     * @param bit           位值
     * @param startOffset   查询区间偏移量开始
     * @param endOffset     查询区间偏移量结束
     * @return 命中偏移量信息
     */
    BitmapPos bitPosHitFirstOffset(CacheKeyType cacheTypeEnum, String key, boolean bit, long startOffset, long endOffset);

    /**
     * 命中区间中最后一个符合条件的偏移量
     * <p>
     * 注意：<br>
     * startOffset和endOffset可能是跨了byte范围的，所以实际查询会取startOffset所在范围开始节点和endOffset所在范围结束节点去检索第一个bit值符合的offset。<br>
     * 所以命中offset范围并不是startOffset-endOffset，而是在startOffset[Range.start]和endOffset[Range.end]中。
     *
     * @param cacheTypeEnum 缓存key类型
     * @param key           缓存key
     * @param bit           位值
     * @param startOffset   查询区间偏移量开始
     * @param endOffset     查询区间偏移量结束
     * @return 命中偏移量信息
     */
    BitmapPos bitPosHitLastOffset(CacheKeyType cacheTypeEnum, String key, boolean bit, long startOffset, long endOffset);


    @Data
    class BitmapPos {
        private long startOfRange;
        private long endOfRange;

        private Long hitOffset;
        private boolean offsetValue;
    }

}
