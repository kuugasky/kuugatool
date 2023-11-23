package io.github.kuugasky.kuugatool.cache.jedis;

import io.github.kuugasky.kuugatool.core.date.DateUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * JedisTool
 * <p>
 * Redis连接小工具，轻量使用
 *
 * @author kuuga
 * @since 2023/5/6-05-06 10:53
 */
public enum JedisTool {

    /**
     *
     */
    INSTANCE;

    private JedisPool jedisPool;

    static JedisBuilder builder() {
        return new JedisBuilder();
    }

    private static class JedisBuilder {
        private String redisUrl;
        private int redisPort;
        private String redisUser;
        private String redisPassword;

        public JedisBuilder url(String redisUrl) {
            this.redisUrl = redisUrl;
            return this;
        }

        public JedisBuilder port(int redisPort) {
            this.redisPort = redisPort;
            return this;
        }

        public JedisBuilder user(String redisUser) {
            this.redisUser = redisUser;
            return this;
        }

        public JedisBuilder password(String redisPassword) {
            this.redisPassword = redisPassword;
            return this;
        }

        public void build() {
            if (null != JedisTool.INSTANCE.jedisPool) {
                return;
            }
            synchronized (JedisPool.class) {
                if (null != JedisTool.INSTANCE.jedisPool) {
                    throw new RuntimeException("redis实例已存在，请勿重复创建");
                }
                JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
                // 最大连接数为 10
                jedisPoolConfig.setMaxTotal(1000);
                jedisPoolConfig.setMinIdle(500);
                jedisPoolConfig.setMaxWaitMillis(10000);
                JedisTool.INSTANCE.jedisPool = new JedisPool(jedisPoolConfig, redisUrl, redisPort, 2000, redisUser, redisPassword);
            }
        }
    }

    /**
     * 插入缓存，默认当天零点失效
     *
     * @param key   key
     * @param value value
     */
    public static void set(String key, String value) {
        try (Jedis jedis = JedisTool.INSTANCE.jedisPool.getResource()) {
            jedis.set(key, value);
            jedis.expire(key, DateUtil.secondsLeftToday());
        }
    }

    /**
     * 插入缓存
     *
     * @param key          key
     * @param value        value
     * @param expireSecond 缓存时间s
     */
    public static void set(String key, String value, long expireSecond) {
        try (Jedis jedis = JedisTool.INSTANCE.jedisPool.getResource()) {
            jedis.set(key, value);
            jedis.expire(key, expireSecond);
        }
    }

    /**
     * 设置缓存时间s
     *
     * @param key          key
     * @param expireSecond 重置缓存时间s
     */
    public static void expire(String key, long expireSecond) {
        try (Jedis jedis = JedisTool.INSTANCE.jedisPool.getResource()) {
            jedis.expire(key, expireSecond);
        }
    }

    /**
     * 根据key获取缓存
     *
     * @param key key
     * @return 缓存值
     */
    public static String get(String key) {
        try (Jedis jedis = JedisTool.INSTANCE.jedisPool.getResource()) {
            return jedis.get(key);
        }
    }

    /**
     * 根据key删除缓存
     *
     * @param key key
     */
    public static void del(String key) {
        try (Jedis jedis = JedisTool.INSTANCE.jedisPool.getResource()) {
            jedis.del(key);
        }
    }

    /**
     * 获取缓存key剩余时间s
     *
     * @param key key
     * @return 缓存剩余时间
     */
    public static Long ttl(String key) {
        try (Jedis jedis = JedisTool.INSTANCE.jedisPool.getResource()) {
            return jedis.ttl(key);
        }
    }

    /**
     * 缓存key自增
     *
     * @param key key
     * @return 自增返回值
     */
    public static Long incr(String key) {
        try (Jedis jedis = JedisTool.INSTANCE.jedisPool.getResource()) {
            return jedis.incr(key);
        }
    }

    /**
     * 缓存key自减
     *
     * @param key key
     * @return 自减返回值
     */
    public static Long decr(String key) {
        try (Jedis jedis = JedisTool.INSTANCE.jedisPool.getResource()) {
            return jedis.decr(key);
        }
    }

    public static void main(String[] args) {
        JedisTool.builder()
                .url("127.0.0.1")
                .port(6379)
                .user(null)
                .password(null).build();
        // JedisTool.builder()
        //         .url("10.210.10.154")
        //         .port(7001)
        //         .user(null)
        //         .password("kfang.com").build();

        String key = "FEIGN-ISOLATION:service-kuuga:10210";
        JedisTool.set(key, "1");
        System.out.println(JedisTool.get(key));
        JedisTool.del(key);

        JedisTool.set("kuuga", "kuugaValue", 10);
        System.out.println(JedisTool.get("kuuga"));
        System.out.println(JedisTool.ttl("kuuga"));
        JedisTool.expire("kuuga", 1);
        System.out.println(JedisTool.ttl("kuuga"));

        System.out.println(StringUtil.repeatNormal());


        JedisTool.del("incr");

        System.out.println(JedisTool.incr("incr"));
        System.out.println(JedisTool.incr("incr"));
        System.out.println(JedisTool.incr("incr"));
        System.out.println(JedisTool.decr("incr"));
        System.out.println(JedisTool.decr("incr"));
    }

}

