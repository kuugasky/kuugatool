package io.github.kuugasky.kuugatool.operationlog.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 租赁方式
 *
 * @author kuuga
 * @since 2021/2/23
 */
@AllArgsConstructor
public enum HouseLeaseWayEnum {

    /**
     *
     */
    WHOLE_RENT("整租"),
    JOINT_RENT("合租"),
    ;

    @Getter
    private final String desc;

}
