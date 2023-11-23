package io.github.kuugasky.kuugatool.thirdparty.request.context.authentication;

import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import io.github.kuugasky.kuugatool.thirdparty.request.context.config.RequestConfig;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 第三方请求鉴权器接口
 * <p>
 * 实现该接口可以进行自定义鉴权
 *
 * @author pengqinglong
 * @since 2022/3/14
 */
public interface RequestAuthentication<T extends RequestConfig, K extends RequestForm> {

    /**
     * 获取config对象
     *
     * @return T
     */
    T getConfig();

    /**
     * 对请求form进行封装
     * <p>
     * 例如：获取token，组装鉴权信息
     *
     * @param k the key
     * @return K
     */
    K initForm(K k);

    /**
     * 获取鉴权class
     *
     * @return class对象
     */
    default Class<K> getAuthenticationClass() {
        // 获取当前类泛型接口集合
        Type[] genericInterfaces = this.getClass().getGenericInterfaces();
        // 获取参数化类型
        ParameterizedType genericInterface = (ParameterizedType) genericInterfaces[0];
        // 获取实际类型参数
        Type actualTypeArgument = genericInterface.getActualTypeArguments()[1];
        try {
            return ObjectUtil.cast(Class.forName(actualTypeArgument.getTypeName()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}