package io.github.kuugasky.kuugatool.thirdparty.request.core.factory;

import io.github.kuugasky.kuugatool.core.collection.MapUtil;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import io.github.kuugasky.kuugatool.extra.spring.KuugaSpringBeanPicker;
import io.github.kuugasky.kuugatool.thirdparty.request.context.authentication.RequestForm;
import io.github.kuugasky.kuugatool.thirdparty.request.core.result.RequestDefaultJsonResultHandle;
import io.github.kuugasky.kuugatool.thirdparty.request.core.result.RequestResultHandle;
import org.springframework.context.support.AbstractApplicationContext;

import java.util.Map;

/**
 * 第三方请求结果分析工厂类
 *
 * @author pengqinglong
 * @since 2022/3/15
 */
public class RequestResultHandleParse {

    private static final Map<Class<? extends RequestForm>, RequestResultHandle<RequestForm>> HANDLE_MAPPING;

    static {
        HANDLE_MAPPING = MapUtil.newHashMap();

        AbstractApplicationContext applicationContext = KuugaSpringBeanPicker.getApplicationContext();
        String[] beanNames = applicationContext.getBeanNamesForType(RequestResultHandle.class);

        for (String beanName : beanNames) {
            RequestResultHandle<RequestForm> bean = ObjectUtil.cast(applicationContext.getBean(beanName));
            Class<? extends RequestForm> modelClass = bean.getModelClass();
            HANDLE_MAPPING.put(modelClass, bean);
        }
    }

    /**
     * 解析返回结果
     */
    public static <T> T parse(String result, RequestForm form, Class<T> clazz) {
        // 优先使用form的自定义的处理器进行处理
        RequestResultHandle<RequestForm> handle = HANDLE_MAPPING.get(form.getClass());

        // form没有自定义处理器再寻找模块的自定义处理器进行处理
        if (handle == null) {
            handle = HANDLE_MAPPING.get(form.getClass().getSuperclass());
        }

        // 模块也没有自定义处理器 则使用默认的json处理器
        if (handle == null) {
            handle = new RequestDefaultJsonResultHandle();
        }

        // 处理返回结果
        return handle.handle(result, clazz);
    }

}