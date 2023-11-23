package io.github.kuugasky.kuugatool.operationlog.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 房源居住状态枚举
 *
 * @author kuuga
 * @since 2021/3/8
 */
@AllArgsConstructor
public enum HouseLivingStatusEnum {

    /**
     *
     */
    SELF("自住"),
    RENT("出租"),
    EMPTY("空置"),
    ;

    @Getter
    private final String desc;

    /**
     * 判断是否是出租状态
     */
    public static boolean isRent(HouseLivingStatusEnum livingStatusEnum) {
        return RENT == livingStatusEnum;
    }

}
