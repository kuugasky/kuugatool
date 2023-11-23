package io.github.kuugasky.kuugatool.cache.redis.subscribe;

import org.springframework.stereotype.Component;

/**
 * RedisMessageListenerDemo
 *
 * @author kuuga
 * @since 2023/6/16-06-16 15:20
 */
@Component
public class RedisMessageListenerDemo extends AbstractRedisMessageListenerContainer {

    private static final String REDIS_SUBSCRIBE_CHANNEL = "redis_channel";

    public RedisMessageListenerDemo() {
        super(REDIS_SUBSCRIBE_CHANNEL);
    }

    @Override
    public void onMessage(String channel, String message) {
        System.out.println("channel4=========>" + channel);
        System.out.println("message4=========>" + message);
    }

}