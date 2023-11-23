package io.github.kuugasky.kuugatool.thirdparty.request.core.result;

import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import io.github.kuugasky.kuugatool.thirdparty.request.context.authentication.RequestForm;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 结果处理器
 *
 * @author pengqinglong
 * @since 2022/3/15
 */
public interface RequestResultHandle<T extends RequestForm> {

    /**
     * request请求结果result转换为resultClass类型对象
     *
     * @param result      request请求结果字符串
     * @param resultClass 返回对象Class
     * @param <K>         K
     * @return K
     */
    <K> K handle(String result, Class<K> resultClass);

    /**
     * 获取处理模块的class
     *
     * @return class对象
     */
    default Class<T> getModelClass() {
        Type actualTypeArgument = ((ParameterizedType) this.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
        try {
            return ObjectUtil.cast(Class.forName(actualTypeArgument.getTypeName()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}


