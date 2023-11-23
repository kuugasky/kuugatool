package io.github.kuugasky.kuugatool.thirdparty.basic.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 第三方调用我方API鉴权方式枚举
 *
 * @author pengqinglong
 * @since 2021/9/16
 */
@AllArgsConstructor
@Getter
public enum RequestedAuthenticationWay {

    /**
     *
     */
    HEADER("请求头中"),
    BODY("请求body/url中"),
    JSON("json中"),
    ;

    private final String desc;
}