package io.github.kuugasky.kuugatool.cache.config.properties;

import io.github.kuugasky.kuugatool.cache.basic.cachekey.KuugaBaseCacheKey;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import redis.clients.jedis.Protocol;

/**
 * KuugaCacheProperties
 *
 * @author kuuga
 * @since 2023-06-15
 */
@Data
@ConfigurationProperties(prefix = KuugaCacheProperties.PREFIX)
public class KuugaCacheProperties {

    public static final String PREFIX = "kuugatool.cache";

    public static final String REDIS_ENABLE = PREFIX + ".redis-enable";

    /**
     * 是否将token_base标志的存储key{@link KuugaBaseCacheKey#TOKEN_BASE}，放置在公共命名空间区域
     */
    private boolean useRedisTokenBase = true;

    /**
     * redis是否启用
     */
    private boolean redisEnable = true;

    /**
     * 读配置使用与写配置一致的参数
     */
    private boolean queryUseSaveProperties = false;

    /**
     * 写redis相关配置
     */
    private Redis saveRedis = new Redis();

    /**
     * 读redis相关配置
     */
    private Redis queryRedis = new Redis();

    @Data
    public static class Redis {
        /**
         * redis连接池最大连接数量
         */
        private int poolMaxTotal = 100;

        /**
         * redis连接池最大闲置数量
         */
        private int poolMaxIdle = 5;

        /**
         * redis连接池最大闲置时间
         */
        private int poolMaxWaitMillis = 20000;

        /**
         * redis的url配置
         */
        private String url;

        /**
         * redis的端口配置，如果是集群设置，则不需要填写这一项
         */
        private int port = Protocol.DEFAULT_PORT;

        /**
         * redis的密码
         */
        private String password;

        /**
         * 使用redis数据库Index
         */
        private int dbIndex = Protocol.DEFAULT_DATABASE;

        private int timeout = Protocol.DEFAULT_TIMEOUT;

        /**
         * 是否使用集群设置
         */
        private boolean clusterEnable = false;

        @Override
        public String toString() {
            return "Redis{" +
                    "poolMaxTotal=" + poolMaxTotal +
                    ", poolMaxIdle=" + poolMaxIdle +
                    ", poolMaxWaitMillis=" + poolMaxWaitMillis +
                    ", url='" + url + '\'' +
                    ", port=" + port +
                    ", password='" + password + '\'' +
                    ", dbIndex=" + dbIndex +
                    ", timeout=" + timeout +
                    ", clusterEnable=" + clusterEnable +
                    '}';
        }
    }

}