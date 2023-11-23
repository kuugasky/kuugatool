package io.github.kuugasky.kuugatool.operationlog.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 房源产权状态枚举
 *
 * @author kuuga
 * @since 2021/3/8
 */
@AllArgsConstructor
public enum HousePropertyStatusEnum {

    /**
     *
     */
    MORTGAGE("抵押"),
    NO_MORTGAGE("无抵押"),
    SEALED("查封"),
    NO_CERTIFICATE("未出证"),
    ;

    @Getter
    private final String desc;

}
