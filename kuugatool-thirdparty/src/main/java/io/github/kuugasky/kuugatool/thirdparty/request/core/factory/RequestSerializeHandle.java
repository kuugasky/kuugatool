package io.github.kuugasky.kuugatool.thirdparty.request.core.factory;

import io.github.kuugasky.kuugatool.core.collection.MapUtil;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import io.github.kuugasky.kuugatool.extra.spring.KuugaSpringBeanPicker;
import io.github.kuugasky.kuugatool.thirdparty.basic.enums.RequestSerializeMode;
import io.github.kuugasky.kuugatool.thirdparty.request.context.annotations.RequestEntity;
import io.github.kuugasky.kuugatool.thirdparty.request.context.authentication.RequestForm;
import io.github.kuugasky.kuugatool.thirdparty.request.core.serialize.RequestDefaultJsonSerialize;
import io.github.kuugasky.kuugatool.thirdparty.request.core.serialize.RequestDefaultParamSerialize;
import io.github.kuugasky.kuugatool.thirdparty.request.core.serialize.RequestSerialize;
import org.springframework.context.support.AbstractApplicationContext;

import java.util.Map;

/**
 * 第三方请求序列化处理器工厂类
 *
 * @author pengqinglong
 * @since 2022/3/24
 */
public class RequestSerializeHandle {

    private static final Map<Class<? extends RequestForm>, RequestSerialize<RequestForm, ?>> HANDLE_MAPPING;

    static {
        HANDLE_MAPPING = MapUtil.newHashMap();

        AbstractApplicationContext applicationContext = KuugaSpringBeanPicker.getApplicationContext();
        String[] beanNames = applicationContext.getBeanNamesForType(RequestSerialize.class);

        for (String beanName : beanNames) {
            RequestSerialize<RequestForm, Object> bean = ObjectUtil.cast(applicationContext.getBean(beanName));
            Class<? extends RequestForm> modelClass = bean.getModelClass();
            HANDLE_MAPPING.put(modelClass, bean);
        }
    }

    /**
     * 解析返回结果
     */
    public static <T> T serialize(RequestForm form) {
        // 优先使用form的自定义的处理器进行处理
        RequestSerialize<RequestForm, ?> handle = HANDLE_MAPPING.get(form.getClass());

        // form没有自定义处理器再寻找模块的自定义处理器进行处理
        if (handle == null) {
            handle = HANDLE_MAPPING.get(form.getClass().getSuperclass());
        }

        // 模块也没有自定义处理器 则通过序列化模式寻找默认处理器
        if (handle == null) {
            RequestEntity annotation = form.getClass().getAnnotation(RequestEntity.class);
            RequestSerializeMode serialize = annotation.serialize();
            if (RequestSerializeMode.JSON == serialize) {
                handle = new RequestDefaultJsonSerialize<>();
            } else {
                handle = new RequestDefaultParamSerialize<>();
            }
        }

        // 处理返回结果
        Object serialize = handle.serialize(form);
        return ObjectUtil.cast(serialize);
    }

}