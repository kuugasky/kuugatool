package io.github.kuugasky.kuugatool.thirdparty.requested.authentication;

import io.github.kuugasky.kuugatool.thirdparty.basic.enums.RequestedAuthenticationWay;

/**
 * 第三方对接顶层鉴权接口
 *
 * @author pengqinglong
 * @since 2021/9/7
 */
public interface RequestedAuthentication {

    /**
     * 自我校验鉴权是否通过
     *
     * @return true:通过 false:不通过
     */
    boolean checkout();

    /**
     * 鉴权方式
     * 请求头中
     * 请求body中/url中
     * json中
     *
     * @return 鉴权方式枚举
     */
    RequestedAuthenticationWay getAuthenticationWay();

}