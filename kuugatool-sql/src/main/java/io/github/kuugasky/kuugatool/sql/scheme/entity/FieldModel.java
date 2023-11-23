package io.github.kuugasky.kuugatool.sql.scheme.entity;

import lombok.Data;

/**
 * SQL模型信息-字段
 *
 * @author kuuga
 */
@Data
public class FieldModel {

    /**
     * 字段数据类型
     */
    private String dataType;
    /**
     * 字段名
     */
    private String columnName;
    /**
     * 字段是否为主键
     */
    private boolean primaryKey;
    /**
     * 注释
     */
    private String columnComment;
    /**
     * 是否为索引键
     */
    private boolean indexKey;
    /**
     * 字段扩展表示
     */
    private String columnType;
    /**
     * 是否可以为空
     */
    private boolean canNull;

}