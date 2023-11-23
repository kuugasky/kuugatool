package io.github.kuugasky.kuugatool.cache.local;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * ExpireWork
 * <p>
 * 本地缓存到期清除线程
 *
 * @author kuuga
 * @since 2023-05-29
 */
public class KuugaExpireWork implements Runnable {

    /**
     * logger
     */
    private final static Logger logger = LoggerFactory.getLogger(KuugaExpireWork.class);
    /**
     * 本地缓存存储值的数据结构
     */
    private final KuugaLocalCacheValue cacheValue;
    /**
     * 缓存key
     */
    private final String key;
    /**
     * keyMap
     */
    private final ConcurrentHashMap<String, KuugaLocalCacheValue> keyMap;

    /**
     * ExpireWork
     *
     * @param cacheValue LocalCacheValue
     * @param key        String
     * @param keyMap     ConcurrentHashMap
     */
    public KuugaExpireWork(KuugaLocalCacheValue cacheValue, String key, ConcurrentHashMap<String, KuugaLocalCacheValue> keyMap) {
        this.cacheValue = cacheValue;
        this.key = key;
        this.keyMap = keyMap;
    }

    @Override
    public void run() {
        try {
            boolean isOverride = cacheValue.isOverride();
            boolean noOverride = !isOverride;
            // 判断cacheValue是否被覆盖，未被覆盖时从keyMap中移除key对应的value
            if (noOverride) {
                keyMap.remove(key);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

}
