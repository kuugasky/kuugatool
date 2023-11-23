package io.github.kuugasky.kuugatool.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 房源户型枚举
 *
 * @author kuuga
 * @since 2021/2/22
 */
@AllArgsConstructor
public enum HouseDoorModelEnum {

    /**
     *
     */
    BACHELOR_APARTMENT(1, "单身公寓"),
    ONE_ROOM(2, "一室"),
    TWO_ROOM(3, "两室"),
    THREE_ROOM(4, "三室"),
    FOUR_ROOM(5, "四室"),
    MORE_THAN_FOUR_ROOM(6, "四室以上"),
    ;

    @Getter
    private final int index;
    @Getter
    private final String desc;

}
