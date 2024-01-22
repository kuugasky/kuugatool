package io.github.kuugasky.kuugatool.cache.redis.subscribe;

import io.github.kuugasky.kuugatool.core.string.StringUtil;
import io.github.kuugasky.kuugatool.extra.spring.KuugaSpringBeanPicker;
import jakarta.annotation.Resource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.lang.NonNull;

/**
 * AbstractRedisListenerContainer
 * <p>
 * 抽象Redis消息监听器容器
 *
 * @author kuuga
 * @since 2023/6/16-06-16 11:30
 */
@DependsOn(KuugaSpringBeanPicker.BEAN_NAME)
public abstract class AbstractRedisMessageListenerContainer implements InitializingBean, ApplicationContextAware {

    private static final String DOLLAR_SIGN = "$";
    @Resource
    private RedisConnectionFactory jedisConnectionFactory;

    private AbstractApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = (AbstractApplicationContext) applicationContext;
    }

    private final String channel;

    public AbstractRedisMessageListenerContainer(String channel) {
        this.channel = channel;
    }

    @Override
    public void afterPropertiesSet() {
        if (StringUtil.isEmpty(channel)) {
            throw new RuntimeException("Redis channel can not be null.");
        }

        String classTypeName = this.getClass().getTypeName();
        String subscribeClassName;
        if (classTypeName.contains(DOLLAR_SIGN)) {
            subscribeClassName = classTypeName.split("\\$")[1] + "RedisMessageListenerContainer";
        } else {
            String[] split = classTypeName.split("\\.");
            subscribeClassName = split[split.length - 1] + "RedisMessageListenerContainer";
        }
        subscribeClassName = StringUtil.firstCharToLowerCase(subscribeClassName);

        MessageListenerAdapter listenerAdapter = new MessageListenerAdapter(this, "onMessage");
        listenerAdapter.setSerializer(new JdkSerializationRedisSerializer(this.getClass().getClassLoader()));
        listenerAdapter.afterPropertiesSet();

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic(channel));
        container.afterPropertiesSet();
        applicationContext.getBeanFactory().registerSingleton(subscribeClassName, container);
    }

    /**
     * 接收消息
     *
     * @param channel 渠道
     * @param message 消息
     */
    @SuppressWarnings("unused")
    public abstract void onMessage(String channel, String message);

}


