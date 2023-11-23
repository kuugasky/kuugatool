package io.github.kuugasky.kuugatool.operationlog.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 占用/可用状态枚举
 *
 * @author kuuga
 * @since 2021/3/8
 */
@AllArgsConstructor
public enum HouseOccupancyStatusEnum {

    /**
     *
     */
    OCCUPIED("占用"),
    USABLE("可用"),
    ;

    @Getter
    private final String desc;

}
