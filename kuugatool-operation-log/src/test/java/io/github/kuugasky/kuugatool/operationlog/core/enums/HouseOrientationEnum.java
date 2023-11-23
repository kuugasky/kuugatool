package io.github.kuugasky.kuugatool.operationlog.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 房间朝向枚举
 *
 * @author kuuga
 * @since 2021/2/22
 */
@AllArgsConstructor
public enum HouseOrientationEnum {

    /**
     *
     */
    EAST("东"),
    SOUTH("南"),
    WEST("西"),
    NORTH("北"),
    NORTH_EAST("东北"),
    SOUTH_EAST("东南"),
    NORTH_WEST("西北"),
    SOUTH_WEST("西南"),
    WEST_EAST("东西"),
    NORTH_SOUTH("南北"),
    ;

    @Getter
    private final String desc;

}
