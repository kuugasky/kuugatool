package io.github.kuugasky.kuugatool.cache.basic.cachekey;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * KuugaBaseCacheKey
 *
 * @author kuuga
 * @since 2023/6/14
 */
@Getter
@AllArgsConstructor
public enum KuugaBaseCacheKey implements CacheKeyType {

    /**
     *
     */
    REDIS_LOCK(REDIS),
    REDIS_CACHED_DATA(REDIS),
    REDIS_CACHED_DATA_THRESHOLD(REDIS),
    ;

    private final String cacheType;

    @Override
    public String getBaseType() {
        return this.getCacheKey();
    }

    @Override
    public String getCacheKey() {
        return this.name();
    }

}
