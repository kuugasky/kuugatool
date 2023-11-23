package io.github.kuugasky.kuugatool.cache.config;

import io.github.kuugasky.kuugatool.cache.KuugaCache;
import io.github.kuugasky.kuugatool.cache.basic.constants.CacheConstants;
import io.github.kuugasky.kuugatool.cache.basic.enums.CacheOperationType;
import io.github.kuugasky.kuugatool.cache.config.factory.RedisConfigFactory;
import io.github.kuugasky.kuugatool.cache.config.properties.KuugaCacheProperties;
import io.github.kuugasky.kuugatool.cache.redis.RedisCacheImpl;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * KuugaRedisConfig
 * <p>
 * {@code KuugaCacheProperties#REDIS_ENABLE = true}时注入本地缓存
 *
 * @author kuuga
 * @since 2023-06-14
 */
@ConditionalOnProperty(value = KuugaCacheProperties.REDIS_ENABLE, havingValue = "true", matchIfMissing = true)
@ConditionalOnClass(redis.clients.jedis.Jedis.class)
public class KuugaRedisConfig {

    private static final Logger logger = LoggerFactory.getLogger(KuugaRedisConfig.class);

    /**
     * 注入{@code KuugaCache}
     *
     * @param environment          Environment
     * @param kuugaCacheProperties KuugaCacheProperties
     * @param redisTemplate        RedisTemplate
     * @param redisTemplateQuery   RedisTemplate
     * @return KuugaCache
     */
    @Bean(name = CacheConstants.KUUGA_CACHE_BEAN_NAME)
    public KuugaCache redisCache(Environment environment, KuugaCacheProperties kuugaCacheProperties, RedisTemplate<?, ?> redisTemplate, RedisTemplate<?, ?> redisTemplateQuery) {
        Map<String, RedisTemplate<String, Object>> redisMap = new HashMap<>(2);
        redisMap.put(CacheOperationType.SAVE.name(), ObjectUtil.cast(redisTemplate));
        redisMap.put(CacheOperationType.QUERY.name(), ObjectUtil.cast(redisTemplateQuery));

        String appName = environment.getProperty(CacheConstants.SPRING_APPLICATION_NAME);

        if (StringUtil.isEmpty(appName)) {
            throw new RuntimeException("注意:应用名为空");
        }

        RedisCacheImpl redisCache = new RedisCacheImpl(redisMap);

        if (!StringUtil.isEmpty(appName)) {
            redisCache.setSystemUnique(appName.toUpperCase());
        }
        redisCache.setUseRedisSessionBase(kuugaCacheProperties.isUseRedisTokenBase());
        return redisCache;
    }

    /**
     * 注入RedisSave模板
     *
     * @param jedisConnectionFactory JedisConnectionFactory
     * @return RedisTemplate
     */
    @Bean(name = "redisTemplate")
    public RedisTemplate<?, ?> redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        return RedisConfigFactory.makeRedisTemplate(jedisConnectionFactory);
    }

    /**
     * 注入RedisQuery模板
     *
     * @param jedisConnectionFactoryQuery JedisConnectionFactory
     * @return RedisTemplate
     */
    @Bean(name = "redisTemplateQuery")
    public RedisTemplate<?, ?> redisTemplateQuery(JedisConnectionFactory jedisConnectionFactoryQuery) {
        return RedisConfigFactory.makeRedisTemplate(jedisConnectionFactoryQuery);
    }

    /**
     * 注入字符串Redis模板
     *
     * @param jedisConnectionFactory JedisConnectionFactory
     * @return StringRedisTemplate
     */
    @Bean(name = "stringRedisTemplate")
    public StringRedisTemplate stringRedisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        StringRedisTemplate redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    /**
     * 注入jedis连接工厂Save
     *
     * @param kuugaCacheProperties KuugaCacheProperties
     * @return JedisConnectionFactory
     */
    @Bean(name = "jedisConnectionFactory")
    public JedisConnectionFactory jedisConnectionFactory(KuugaCacheProperties kuugaCacheProperties) {
        KuugaCacheProperties.Redis saveRedis = kuugaCacheProperties.getSaveRedis();
        return makeJedisConnectionFactory(saveRedis, CacheOperationType.SAVE);
    }

    /**
     * 注入jedis连接工厂Query
     *
     * @param kuugaCacheProperties KuugaCacheProperties
     * @return JedisConnectionFactory
     */
    @Bean(name = "jedisConnectionFactoryQuery")
    public JedisConnectionFactory jedisConnectionFactoryQuery(KuugaCacheProperties kuugaCacheProperties) {
        boolean queryUseSaveProperties = kuugaCacheProperties.isQueryUseSaveProperties();
        KuugaCacheProperties.Redis saveRedis = kuugaCacheProperties.getSaveRedis();
        KuugaCacheProperties.Redis queryRedis = kuugaCacheProperties.getQueryRedis();
        return makeJedisConnectionFactory(queryUseSaveProperties ? saveRedis : queryRedis, CacheOperationType.QUERY);
    }

    /**
     * Redis可自定义集群加载模式
     *
     * @param redis KuugaCacheProperties.Redis
     * @return JedisConnectionFactory
     */
    public static JedisConnectionFactory makeJedisConnectionFactory(KuugaCacheProperties.Redis redis, CacheOperationType cacheOperationType) {
        JedisConnectionFactory factory;
        // 创建Jedis池配置
        JedisPoolConfig poolConfig = makeJedisPoolConfig(redis);
        // 集群
        if (redis.isClusterEnable()) {
            factory = makeJedisConnectionFactoryOfCluster(redis, poolConfig);
        }
        // 单机
        else {
            factory = makeJedisConnectionFactoryOfStandalone(redis, poolConfig);
        }
        loggerRedisInfo(redis, cacheOperationType);
        return factory;
    }

    /**
     * 创建Jedis集群连接工厂
     * <p>
     * 集群模式不用设置database
     *
     * @param redis      KuugaCacheProperties.Redis
     * @param poolConfig JedisPoolConfig
     * @return JedisConnectionFactory
     */
    private static JedisConnectionFactory makeJedisConnectionFactoryOfCluster(KuugaCacheProperties.Redis redis, JedisPoolConfig poolConfig) {
        JedisConnectionFactory factory;
        // 创建Jedis客户端配置生成器
        JedisClientConfiguration.JedisClientConfigurationBuilder builder = JedisClientConfiguration.builder();

        int timeout = redis.getTimeout();
        if (timeout > 0) {
            // 设置连接超时时间
            builder.connectTimeout(Duration.ofMillis(timeout));
            // 设置读取超时时间
            builder.readTimeout(Duration.ofMillis(timeout));
        }
        // 配置并启用连接池
        builder.usePooling().poolConfig(poolConfig);
        // 构建Jedis客户端配置
        JedisClientConfiguration jedisClientConfiguration = builder.build();

        // 获取redis集群配置
        RedisClusterConfiguration clusterConfig = getRedisClusterConfig(redis.getUrl());
        // 为redis集群配置设置密码
        RedisConfigFactory.setPasswordOfClusterConfiguration(redis.getPassword(), clusterConfig);
        // 创建Jedis连接工厂
        factory = new JedisConnectionFactory(clusterConfig, jedisClientConfiguration);
        return factory;
    }

    /**
     * 创建Jedis单机连接工厂
     *
     * @param redis      KuugaCacheProperties.Redis
     * @param poolConfig JedisPoolConfig
     * @return JedisConnectionFactory
     */
    private static JedisConnectionFactory makeJedisConnectionFactoryOfStandalone(KuugaCacheProperties.Redis redis, JedisPoolConfig poolConfig) {
        JedisConnectionFactory factory;
        // // 创建Jedis客户端配置生成器
        JedisClientConfiguration.JedisClientConfigurationBuilder builder = JedisClientConfiguration.builder();

        int timeout = redis.getTimeout();
        if (timeout > 0) {
            // 设置连接超时时间
            builder.connectTimeout(Duration.ofMillis(timeout));
            // 设置读取超时时间
            builder.readTimeout(Duration.ofMillis(timeout));
        }
        // 配置并启用连接池
        builder.usePooling().poolConfig(poolConfig);
        // 构建Jedis客户端配置
        JedisClientConfiguration jedisClientConfiguration = builder.build();

        // 创建redis单机配置
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        // 设置redis url
        configuration.setHostName(redis.getUrl());
        // 设置redis port
        configuration.setPort(redis.getPort());
        // 设置database index
        configuration.setDatabase(redis.getDbIndex());
        // 设置redis密码
        RedisConfigFactory.setPasswordOfStandaloneConfiguration(redis.getPassword(), configuration);
        // 创建Jedis连接工厂
        factory = new JedisConnectionFactory(configuration, jedisClientConfiguration);
        return factory;
    }

    /**
     * 创建Jedis池配置
     *
     * @param redis KuugaCacheProperties.Redis
     * @return JedisPoolConfig
     */
    private static JedisPoolConfig makeJedisPoolConfig(KuugaCacheProperties.Redis redis) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(redis.getPoolMaxTotal());
        config.setMaxIdle(redis.getPoolMaxIdle());
        config.setMaxWaitMillis(redis.getPoolMaxWaitMillis());
        return config;
    }

    /**
     * 获取集群配置
     * <p>
     * 拆分集群配置地址，解析成
     * <p>
     * eg：kfang.infra.agent.save-redis.url=172.24.16.150:7001,172.24.16.151:7001,172.24.16.152:7001,172.24.16.152:7002,172.24.16.151:7002,172.24.16.150:7002
     *
     * @param envUrl 集群redis配置
     * @return RedisClusterConfiguration
     */
    private static RedisClusterConfiguration getRedisClusterConfig(String envUrl) {
        RedisClusterConfiguration clusterConfig = new RedisClusterConfiguration();
        String[] ips = envUrl.split(",");
        List<RedisNode> list = new ArrayList<>(ips.length * 2);
        for (String ipAndPort : ips) {
            String[] nodes = ipAndPort.trim().split(":");
            String ip = nodes[0].trim();
            int port = Integer.parseInt(nodes[1].trim());
            RedisNode node = new RedisNode(ip, port);
            list.add(node);
        }
        clusterConfig.setClusterNodes(list);
        // 设置要遵循的最大重定向数
        clusterConfig.setMaxRedirects(ips.length);
        return clusterConfig;
    }

    private static void loggerRedisInfo(KuugaCacheProperties.Redis redis, CacheOperationType cacheOperationType) {
        final String cacheType = cacheOperationType.name();
        logger.info("==============> Redis." + cacheType + " Successful initialization. " + redis.toString());
    }

}