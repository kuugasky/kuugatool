package io.github.kuugasky.kuugatool.operationlog.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 房源属性枚举
 *
 * @author kuuga
 * @since 2019-07-23
 */
@Getter
@AllArgsConstructor
public enum HousePropertyEnum {

    /**
     *
     */
    PERSONAL("个人拓盘"),
    COMPANY("平台公盘"),

    ;
    private final String desc;

}
