package io.github.kuugasky.kuugatool.thirdparty.request.context.annotations;

import io.github.kuugasky.kuugatool.thirdparty.basic.enums.RequestAuthenticationMode;
import io.github.kuugasky.kuugatool.thirdparty.basic.enums.RequestMode;
import io.github.kuugasky.kuugatool.thirdparty.basic.enums.RequestSerializeMode;
import io.github.kuugasky.kuugatool.thirdparty.request.context.authentication.RequestForm;
import io.github.kuugasky.kuugatool.thirdparty.request.context.config.RequestConfig;

import java.lang.annotation.*;

/**
 * 第三方请求实体注解
 * <p>
 * 该注解直接打在{@link RequestForm}的子类下的某个请求form实现类上
 *
 * @author pengqinglong
 * @since 2022/3/14
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestEntity {

    /**
     * 接口路径 拼接上前缀 {@link RequestConfig#getUrl()}后形成完整的请求路径
     */
    String path();

    /**
     * 请求超时时间 单位:毫秒 默认10s
     *
     * @return timeout
     */
    int timeout() default 10000;

    /**
     * 请求方式 目前暂时支持 post和get 默认post
     */
    RequestMode mode() default RequestMode.POST;

    /**
     * 鉴权方式 目前暂时支持 header和body 默认header
     */
    RequestAuthenticationMode authMode() default RequestAuthenticationMode.HEADER;

    /**
     * 参数序列化方式 目前暂时支持 json和param 默认json
     */
    RequestSerializeMode serialize() default RequestSerializeMode.JSON;

}
