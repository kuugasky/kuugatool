package io.github.kuugasky.kuugatool.extra.spring;

import com.alibaba.fastjson2.TypeReference;
import io.github.kuugasky.kuugatool.core.collection.ArrayUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Map;

/**
 * KuugaSpringBeanPicker
 * Spring(Spring boot)工具封装
 * 赋值并读取上下文IOC容器中的bean对象等
 *
 * @author kuuga
 * @since 2021/6/16
 */
@Component(KuugaSpringBeanPicker.BEAN_NAME)
public class KuugaSpringBeanPicker implements ApplicationContextAware {

    public final static String BEAN_NAME = "kuugaSpringBeanPicker";

    private static AbstractApplicationContext applicationContext;

    /**
     * 设置applicationContext
     *
     * @param applicationContext ApplicationContext
     */
    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        if (applicationContext instanceof AbstractApplicationContext) {
            KuugaSpringBeanPicker.applicationContext = (AbstractApplicationContext) applicationContext;
        } else {
            throw new RuntimeException("applicationContext is not instance of AbstractApplicationContext!");
        }
    }

    /**
     * 获取applicationContext
     *
     * @return ApplicationContext
     */
    public static AbstractApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 通过class获取Bean
     *
     * @param clazz Bean类
     * @return Bean对象
     */
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    /**
     * 通过name获取 Bean
     *
     * @param name Bean名称
     * @return Bean
     */
    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     *
     * @param <T>   bean类型
     * @param name  Bean名称
     * @param clazz bean类型
     * @return Bean对象
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return applicationContext.getBean(name, clazz);
    }

    /**
     * 通过类型参考返回带泛型参数的Bean
     *
     * @param reference 类型参考，用于持有转换后的泛型类型
     * @param <T>       Bean类型
     * @return 带泛型参数的Bean
     * @since 5.4.0
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(TypeReference<T> reference) {
        final ParameterizedType parameterizedType = (ParameterizedType) reference.getType();
        final Class<T> rawType = (Class<T>) parameterizedType.getRawType();
        final Class<?>[] genericTypes = Arrays.stream(parameterizedType.getActualTypeArguments()).map(type -> (Class<?>) type).toArray(Class[]::new);
        final String[] beanNames = applicationContext.getBeanNamesForType(ResolvableType.forClassWithGenerics(rawType, genericTypes));
        return getBean(beanNames[0], rawType);
    }

    /**
     * 获取指定类型对应的所有Bean，包括子类
     *
     * @param <T>  Bean类型
     * @param type 类、接口，null表示获取所有bean
     * @return 类型对应的bean，key是bean注册的name，value是Bean
     * @since 5.3.3
     */
    public static <T> Map<String, T> getBeansOfType(Class<T> type) {
        return applicationContext.getBeansOfType(type);
    }

    /**
     * 获取指定类型对应的Bean名称，包括子类
     *
     * @param type 类、接口，null表示获取所有bean名称
     * @return bean名称
     * @since 5.3.3
     */
    public static String[] getBeanNamesForType(Class<?> type) {
        return applicationContext.getBeanNamesForType(type);
    }

    /**
     * 获取配置文件配置项的值
     *
     * @param key 配置项key
     * @return 属性值
     * @since 5.3.3
     */
    public static String getProperty(String key) {
        return applicationContext.getEnvironment().getProperty(key);
    }

    /**
     * 获取当前的环境配置，无配置返回null
     *
     * @return 当前的环境配置
     * @since 5.3.3
     */
    public static String[] getActiveProfiles() {
        return applicationContext.getEnvironment().getActiveProfiles();
    }

    /**
     * 获取当前的环境配置，当有多个环境配置时，只获取第一个
     *
     * @return 当前的环境配置
     * @since 5.3.3
     */
    public static String getActiveProfile() {
        final String[] activeProfiles = getActiveProfiles();
        return ArrayUtil.hasItem(activeProfiles) ? activeProfiles[0] : null;
    }

    /**
     * 动态向Spring注册Bean
     * <p>
     * 由{@link org.springframework.beans.factory.BeanFactory} 实现，通过工具开放API
     *
     * @param <T>      Bean类型
     * @param beanName 名称
     * @param bean     bean
     * @author shadow
     * @since 5.4.2
     */
    public static <T> void registerBean(String beanName, T bean) {
        ConfigurableApplicationContext context = applicationContext;
        if (bean instanceof InitializingBean) {
            try {
                // 注意：如果调用registerBean前，bean已经显式调用了afterPropertiesSet()，则此处会报错
                ((InitializingBean) bean).afterPropertiesSet();
            } catch (Exception e) {
                throw new BeanInitializationException("BeanInitializationException, " +
                        "registered bean implements the InitializingBean interface, " +
                        "and calls the bean.afterPropertiesSet() method exception during initialization.");
            }
        }
        context.getBeanFactory().registerSingleton(beanName, bean);
    }

}
