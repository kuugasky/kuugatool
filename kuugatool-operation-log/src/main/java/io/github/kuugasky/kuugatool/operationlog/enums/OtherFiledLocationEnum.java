package io.github.kuugasky.kuugatool.operationlog.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 其他字段拼接的位子
 *
 * @author pengqinglong
 * @since 2021/8/17
 */
@Getter
@AllArgsConstructor
public enum OtherFiledLocationEnum {

    /**
     *
     */
    MODEL("模块前"),
    FIELD("字段前"),
    VALUE_BEFORE("字段值前"),
    VALUE_AFTER("字段值后"),
    ;

    private final String desc;

}