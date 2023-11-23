package io.github.kuugasky.kuugatool.thirdparty.request.core.factory;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.collection.MapUtil;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import io.github.kuugasky.kuugatool.extra.spring.KuugaSpringBeanPicker;
import io.github.kuugasky.kuugatool.thirdparty.request.context.authentication.RequestAuthentication;
import io.github.kuugasky.kuugatool.thirdparty.request.context.authentication.RequestForm;
import io.github.kuugasky.kuugatool.thirdparty.request.context.config.RequestConfig;
import org.springframework.context.support.AbstractApplicationContext;

import java.util.Map;

/**
 * 第三方请求鉴权器工厂类
 *
 * @author pengqinglong
 * @since 2022/3/15
 */
public class RequestAuthenticationFactory {

    /**
     * 配置映射
     * key : Class<? extends ThirdpartyForm>
     * value : ThirdpartyAuthentication<ThirdpartyConfig, ThirdpartyForm>
     */
    private static final Map<Class<? extends RequestForm>, RequestAuthentication<RequestConfig, RequestForm>> CONFIG_MAPPING;

    static {
        /*
         * 初始化配置映射
         */
        CONFIG_MAPPING = MapUtil.newHashMap();
        // 获取应用上下文
        AbstractApplicationContext applicationContext = KuugaSpringBeanPicker.getApplicationContext();
        // 获取ThirdpartyAuthentication.class类型的Bean名称数组
        String[] beanNamesForType = applicationContext.getBeanNamesForType(RequestAuthentication.class);
        for (String beanName : ListUtil.optimize(beanNamesForType)) {
            Object bean = applicationContext.getBean(beanName);
            RequestAuthentication<RequestConfig, RequestForm> authentication = ObjectUtil.cast(bean);
            Class<? extends RequestForm> authenticationClass = authentication.getAuthenticationClass();

            CONFIG_MAPPING.put(authenticationClass, authentication);
        }
    }

    /**
     * 通过鉴权对象获取第三方配置信息
     */
    public static RequestAuthentication<RequestConfig, RequestForm> getAuthentication(RequestForm form) {
        RequestAuthentication<RequestConfig, RequestForm> authentication = CONFIG_MAPPING.get(form.getClass());
        if (authentication == null) {
            Class<?> superclass = form.getClass().getSuperclass();
            authentication = CONFIG_MAPPING.get(superclass);
        }
        return authentication;
    }

}