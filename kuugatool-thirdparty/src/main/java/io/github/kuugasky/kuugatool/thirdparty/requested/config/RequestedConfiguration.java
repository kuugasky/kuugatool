package io.github.kuugasky.kuugatool.thirdparty.requested.config;

import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import io.github.kuugasky.kuugatool.thirdparty.requested.authentication.RequestedAuthentication;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 第三方配置定高层接口
 *
 * @author pengqinglong
 * @since 2021/9/8
 */
public interface RequestedConfiguration<T extends RequestedAuthentication> {

    /**
     * 获取第三方鉴权放在请求中的字段key
     *
     * @return 鉴权字段key
     */
    String[] getRequestParamCheckKeys();

    /**
     * 使用config对入参进行初始化 用于后续的鉴权
     *
     * @param t 鉴权类
     */
    void initForm(T t);

    /**
     * 获取鉴权class
     *
     * @return class对象
     */
    default Class<T> getAuthenticationClass() {
        Type actualTypeArgument = ((ParameterizedType) this.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
        try {
            return ObjectUtil.cast(Class.forName(actualTypeArgument.getTypeName()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}