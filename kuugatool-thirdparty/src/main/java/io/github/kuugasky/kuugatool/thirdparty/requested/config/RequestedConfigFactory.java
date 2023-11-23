package io.github.kuugasky.kuugatool.thirdparty.requested.config;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.collection.MapUtil;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import io.github.kuugasky.kuugatool.extra.spring.KuugaSpringBeanPicker;
import io.github.kuugasky.kuugatool.thirdparty.requested.authentication.RequestedAuthentication;
import org.springframework.context.support.AbstractApplicationContext;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * 第三方配置工厂类
 *
 * @author pengqinglong
 * @since 2021/9/8
 */
public class RequestedConfigFactory {

    private static final Map<Class<? extends RequestedAuthentication>, RequestedConfiguration<RequestedAuthentication>> CONFIG_MAPPING;

    static {
        CONFIG_MAPPING = MapUtil.newHashMap();
        AbstractApplicationContext applicationContext = KuugaSpringBeanPicker.getApplicationContext();
        String[] beanNamesForType = applicationContext.getBeanNamesForType(RequestedConfiguration.class);
        for (String beanName : ListUtil.optimize(beanNamesForType)) {
            Object bean = applicationContext.getBean(beanName);
            if (!(bean instanceof RequestedConfiguration)) {
                continue;
            }
            RequestedConfiguration<RequestedAuthentication> config = ObjectUtil.cast(bean);
            Class<? extends RequestedAuthentication> authenticationClass = config.getAuthenticationClass();

            CONFIG_MAPPING.put(authenticationClass, config);
        }
    }

    /**
     * 通过鉴权对象获取第三方配置信息
     */
    public static RequestedConfiguration<RequestedAuthentication> getConfig(RequestedAuthentication authentication) {
        RequestedConfiguration<RequestedAuthentication> config = CONFIG_MAPPING.get(authentication.getClass());
        if (config == null) {
            Type genericSuperclass = authentication.getClass().getGenericSuperclass();
            try {
                config = CONFIG_MAPPING.get(Class.forName(genericSuperclass.getTypeName()));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return config;
    }

}