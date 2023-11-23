package io.github.kuugasky.kuugatool.cache.config.factory;

import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Optional;

/**
 * Redis配置工厂
 *
 * @author kuuga
 * @since 2023-05-29
 */
public class RedisConfigFactory {

    // /**
    //  * logger
    //  */
    // private static final Logger logger = LoggerFactory.getLogger(RedisConfigFactory.class);

    /**
     * redis key serializer
     */
    public static RedisSerializer<String> keySerializer = new StringRedisSerializer();
    /**
     * redis hash key serializer
     */
    public static RedisSerializer<String> hashKeySerializer = new StringRedisSerializer();

    // /**
    //  * 设置自定义key序列化器
    //  *
    //  * @param keySerializer key序列化
    //  */
    // public static void setCustomKeySerializer(RedisSerializer<String> keySerializer) {
    //     RedisConfigFactory.keySerializer = keySerializer;
    // }
    //
    // /**
    //  * 设置自定义hashKey序列化器
    //  *
    //  * @param hashKeySerializer hashKey序列化
    //  */
    // public static void setCustomHashKeySerializer(RedisSerializer<String> hashKeySerializer) {
    //     RedisConfigFactory.hashKeySerializer = hashKeySerializer;
    // }

    /**
     * 组装redis模版
     *
     * @param connectionFactory redis连接工厂
     * @return RedisTemplate
     */
    public static <K, V> RedisTemplate<K, V> makeRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<K, V> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(keySerializer);
        redisTemplate.setHashKeySerializer(hashKeySerializer);
        return redisTemplate;
    }

    // /**
    //  * 组装redis连接池配置
    //  *
    //  * @param redis redis
    //  * @return JedisPoolConfig
    //  */
    // private static JedisPoolConfig makeJedisPoolConfig(KuugaCacheProperties.Redis redis) {
    //     JedisPoolConfig config = new JedisPoolConfig();
    //     config.setMaxTotal(redis.getPoolMaxTotal());
    //     config.setMaxIdle(redis.getPoolMaxIdle());
    //     config.setMaxWaitMillis(redis.getPoolMaxWaitMillis());
    //     return config;
    // }

    // /**
    //  * Redis可自定义集群或单机加载模式（只支持集群和单机，暂不支持哨兵模式）
    //  * <p>
    //  * api建议单机模式和哨兵模式配置dbIndex，集群模式不支持
    //  *
    //  * @param redis redis配置
    //  * @return JedisConnectionFactory
    //  */
    // public static JedisConnectionFactory makeJedisConnectionFactory(KuugaCacheProperties.Redis redis) {
    //     JedisConnectionFactory factory;
    //     JedisPoolConfig poolConfig = makeJedisPoolConfig(redis);
    //     if (redis.isClusterEnable()) {
    //         RedisClusterConfiguration clusterConfig = getClusterConfig(redis.getUrl());
    //         clusterConfig.setPassword(redis.getPassword());
    //         if (StringUtil.hasText(redis.getPassword())) {
    //             clusterConfig.setPassword(redis.getPassword());
    //         }
    //         JedisClientConfiguration jedisClientConfiguration = JedisClientConfiguration.builder()
    //                 .connectTimeout(Duration.ofMillis(redis.getTimeout()))
    //                 .usePooling().poolConfig(poolConfig)
    //                 .build();
    //         factory = new JedisConnectionFactory(clusterConfig, jedisClientConfiguration);
    //     } else {
    //         RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
    //         configuration.setHostName(redis.getUrl());
    //         configuration.setPort(redis.getPort());
    //         if (StringUtil.hasText(redis.getPassword())) {
    //             configuration.setPassword(redis.getPassword());
    //         }
    //         configuration.setDatabase(redis.getDbIndex());
    //
    //         if (redis.getTimeout() > 0) {
    //             JedisClientConfiguration jedisClientConfiguration = JedisClientConfiguration.builder()
    //                     .connectTimeout(Duration.ofMillis(redis.getTimeout()))
    //                     .usePooling().poolConfig(poolConfig)
    //                     .build();
    //             factory = new JedisConnectionFactory(configuration, jedisClientConfiguration);
    //         } else {
    //             factory = new JedisConnectionFactory(configuration);
    //         }
    //     }
    //     loggerRedisInfo(redis);
    //     return factory;
    // }

    // /**
    //  * Lettuce自定义集群加载模式
    //  *
    //  * @return LettuceConnectionFactory
    //  */
    // public static LettuceConnectionFactory makeLettuceConnectionFactory(KuugaCacheProperties.Redis redis) {
    //     LettuceConnectionFactory factory;
    //     String password = redis.getPassword();
    //     if (redis.isClusterEnable()) {
    //         RedisClusterConfiguration clusterConfig = getClusterConfig(redis.getUrl());
    //         setPasswordOfClusterConfiguration(password, clusterConfig);
    //         if (redis.getTimeout() > 0) {
    //             // 连接超时配置
    //             LettuceClientConfiguration lettuceClientConfiguration = LettuceClientConfiguration.builder()
    //                     .commandTimeout(Duration.ofMillis(redis.getTimeout()))
    //                     .build();
    //             factory = new LettuceConnectionFactory(clusterConfig, lettuceClientConfiguration);
    //         } else {
    //             factory = new LettuceConnectionFactory(clusterConfig);
    //         }
    //     } else {
    //         RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
    //         configuration.setHostName(redis.getUrl());
    //         configuration.setPort(redis.getPort());
    //         setPasswordOfStandaloneConfiguration(password, configuration);
    //         if (redis.getTimeout() > 0) {
    //             // 连接超时配置
    //             LettuceClientConfiguration lettuceClientConfiguration = LettuceClientConfiguration.builder()
    //                     .commandTimeout(Duration.ofMillis(redis.getTimeout()))
    //                     .build();
    //             factory = new LettuceConnectionFactory(configuration, lettuceClientConfiguration);
    //         } else {
    //             factory = new LettuceConnectionFactory(configuration);
    //         }
    //     }
    //     factory.setDatabase(redis.getDbIndex());
    //     loggerRedisInfo(redis);
    //     return factory;
    // }

    /**
     * 单机配置密码
     *
     * @param password      密码
     * @param configuration redis单机配置
     */
    public static void setPasswordOfStandaloneConfiguration(String password, RedisStandaloneConfiguration configuration) {
        Optional.ofNullable(password).filter(StringUtil::hasText).ifPresent(configuration::setPassword);
    }

    /**
     * 集群配置密码
     *
     * @param password      密码
     * @param clusterConfig redis集群配置
     */
    public static void setPasswordOfClusterConfiguration(String password, RedisClusterConfiguration clusterConfig) {
        Optional.ofNullable(password).filter(StringUtil::hasText).ifPresent(clusterConfig::setPassword);
    }

    // /**
    //  * 获取redis集群配置
    //  *
    //  * @param envUrl 集群配置url
    //  * @return redis集群配置
    //  */
    // private static RedisClusterConfiguration getClusterConfig(String envUrl) {
    //     final RedisClusterConfiguration clusterConfig = new RedisClusterConfiguration();
    //     final String[] ips = envUrl.split(",");
    //     final List<RedisNode> list = new ArrayList<>(ips.length * 2);
    //     ListUtil.optimize(ips).forEach(ip -> {
    //         String[] nodes = ip.trim().split(":");
    //         RedisNode node = new RedisNode(nodes[0].trim(), Integer.parseInt(nodes[1].trim()));
    //         list.add(node);
    //     });
    //
    //     clusterConfig.setClusterNodes(list);
    //     clusterConfig.setMaxRedirects(ips.length);
    //     return clusterConfig;
    // }

    // /**
    //  * 打印redis启动信息
    //  *
    //  * @param redis redis配置
    //  */
    // private static void loggerRedisInfo(KuugaCacheProperties.Redis redis) {
    //     logger.info("==============> Redis Successful initialization. " + redis.toString());
    // }

}
