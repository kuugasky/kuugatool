package io.github.kuugasky.kuugatool.operationlog.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 房屋装修类型枚举
 *
 * @author kuuga
 * @since 2021/02/23
 */
@AllArgsConstructor
public enum HouseDecorationEnum {

    /**
     *
     */
    DELICATE("精装修"),
    ORDINARY("普通装修"),
    ROUGH("毛坯"),
    ;

    @Getter
    private final String desc;

}
