package io.github.kuugasky.kuugatool.cache.redis;

import io.github.kuugasky.kuugatool.cache.basic.enums.CacheOperationType;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 自定义redis行为接口
 *
 * @author kuuga
 * @see RedisCacheImpl#doCustomAction(CacheOperationType, KuugaCacheAction)
 * @since 2023-06-15
 */
public interface RedisAction extends KuugaCacheAction<RedisTemplate<String, Object>> {

}