package io.github.kuugasky.kuugatool.operationlog.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * HouseStructureEnum
 *
 * @author kuuga
 * @since 2021/3/8
 */
@AllArgsConstructor
public enum HouseStructureEnum {

    /**
     *
     */
    FLAT("平层"),
    STAGGERED("错层"),
    COMPOUND("复式"),
    LEAPING("跃式");

    @Getter
    private final String desc;

}
