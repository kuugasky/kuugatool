package io.github.kuugasky.kuugatool.cache.local;

import lombok.Getter;
import lombok.Setter;

/**
 * 本地缓存存储值的数据结构
 * <ul>
 *     <li>expireTime过期时间</li>
 *     <li>value存储的数据</li>
 *     <li>override是否被覆盖</li>
 *     <li>cacheTime存储时间</li>
 * </ul>
 *
 * @author kuuga
 * @see KuugaLocalCacheImpl
 */
public class KuugaLocalCacheValue {

    /**
     * 过期时间
     */
    @Getter
    private final long expireTime;
    /**
     * 存储的数据
     */
    @Getter
    private final Object value;
    /**
     * 是否被覆盖
     */
    @Setter
    @Getter
    private boolean override;
    /**
     * 存储时间
     */
    @Getter
    private final long cacheTime;

    public KuugaLocalCacheValue(Object value, long expireTime) {
        this.expireTime = expireTime;
        this.value = value;
        this.cacheTime = System.currentTimeMillis();
    }

}
