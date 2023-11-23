package io.github.kuugasky.kuugatool.operationlog.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 操作日志字段类型
 *
 * @author pengqinglong
 * @since 2021/3/25
 */
@AllArgsConstructor
public enum FieldTypeEnum {

    /**
     *
     */
    NORMAL("普通字段"),
    OBJECT("对象字段"),
    NORMAL_LIST("普通list"),
    OBJECT_LIST("对象list"),
    ;

    @Getter
    private final String desc;

}