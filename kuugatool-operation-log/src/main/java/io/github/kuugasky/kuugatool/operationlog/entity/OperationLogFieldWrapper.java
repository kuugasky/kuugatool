package io.github.kuugasky.kuugatool.operationlog.entity;

import lombok.Data;

import java.lang.reflect.Field;

/**
 * OperationLogFieldWrapper
 *
 * @author pengqinglong
 * @since 2021/3/24
 */
@Data
public class OperationLogFieldWrapper {

    /**
     * 父字段的order
     */
    private int parentOrder;

    /**
     * 父字段的name
     */
    private Field parent;

    /**
     * 字段对象
     */
    private Field field;

    public OperationLogFieldWrapper(Field field) {
        this.field = field;
    }
}