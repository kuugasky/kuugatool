package io.github.kuugasky.kuugatool.operationlog.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 租价类型枚举
 *
 * @author kuuga
 * @since 2021/3/8
 */
@AllArgsConstructor
public enum HouseRentTypeEnum {

    /**
     *
     */
    MONTHLY_RENT("月租金"),
    QUARTER_RENT("季租金"),
    YEARLY_RENT("年租金"),
    ;

    @Getter
    private final String desc;

    public static boolean isQuarter(HouseRentTypeEnum houseRentTypeEnum) {
        return QUARTER_RENT == houseRentTypeEnum;
    }

    public static boolean isYearly(HouseRentTypeEnum houseRentTypeEnum) {
        return YEARLY_RENT == houseRentTypeEnum;
    }

}
