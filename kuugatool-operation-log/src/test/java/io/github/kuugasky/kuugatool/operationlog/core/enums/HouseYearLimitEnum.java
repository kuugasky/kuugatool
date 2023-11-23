package io.github.kuugasky.kuugatool.operationlog.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 房源年限枚举
 *
 * @author kuuga
 * @since 2021/3/8
 */
@AllArgsConstructor
public enum HouseYearLimitEnum {

    /**
     *
     */
    LESS_THAN_2_YEARS("未满2年"),
    FULL_2_YEARS("满2年"),
    LESS_THAN_5_YEARS("未满5年"),
    FULL_5_YEARS("满5年"),
    ;

    @Getter
    private final String desc;

}
