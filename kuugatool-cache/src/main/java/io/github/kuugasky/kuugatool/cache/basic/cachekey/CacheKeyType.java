package io.github.kuugasky.kuugatool.cache.basic.cachekey;

/**
 * 缓存类型接口
 * <p>架构采用定义式的缓存接口，用于管理缓存键
 *
 * @author kuuga
 */
public interface CacheKeyType {

    /**
     * 使用该baseType时，将使用通用的命名空间，与项目命名域无关
     **/
    String TOKEN_BASE = "TOKEN_BASE";

    String REDIS = "REDIS";

    /**
     * 返回cachekey关联的baseType
     *
     * @return baseType定义父级空间，是自有文本形式
     */
    String getBaseType();

    /**
     * 返回cachekey的key
     *
     * @return 返回Enum.name()即可
     */
    String getCacheKey();

}