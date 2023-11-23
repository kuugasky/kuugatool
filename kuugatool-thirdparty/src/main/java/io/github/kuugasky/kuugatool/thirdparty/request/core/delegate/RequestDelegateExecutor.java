package io.github.kuugasky.kuugatool.thirdparty.request.core.delegate;

import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import io.github.kuugasky.kuugatool.thirdparty.basic.enums.RequestMode;
import io.github.kuugasky.kuugatool.thirdparty.basic.enums.RequestSerializeMode;
import io.github.kuugasky.kuugatool.thirdparty.request.context.annotations.RequestEntity;
import io.github.kuugasky.kuugatool.thirdparty.request.context.authentication.RequestForm;

/**
 * 请求委托执行器
 *
 * @author pengqinglong
 * @since 2022/3/15
 */
public class RequestDelegateExecutor {

    /**
     * url请求执行
     */
    public static String execute(String url, RequestForm form) {
        // 后期优化进本地缓存
        Class<? extends RequestForm> aClass = form.getClass();
        RequestEntity annotation = aClass.getAnnotation(RequestEntity.class);

        RequestSerializeMode serialize = annotation.serialize();
        RequestDelegate delegate = createDelegate(serialize);

        if (ObjectUtil.isNull(delegate)) {
            return null;
        }

        RequestMode mode = annotation.mode();
        if (RequestMode.POST == mode) {
            return delegate.post(url, form);
        }

        if (RequestMode.GET == mode) {
            return delegate.get(url, form);
        }
        return null;
    }

    /**
     * 根据序列化模式创建请求委托类
     *
     * @param serializeMode 序列化模式
     * @return RequestDelegate
     */
    private static RequestDelegate createDelegate(RequestSerializeMode serializeMode) {

        if (RequestSerializeMode.JSON == serializeMode) {
            return new RequestJsonDelegate();
        }

        if (RequestSerializeMode.PARAM == serializeMode) {
            return new RequestParamDelegate();
        }

        return null;
    }

}