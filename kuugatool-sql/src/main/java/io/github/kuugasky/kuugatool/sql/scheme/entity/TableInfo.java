package io.github.kuugasky.kuugatool.sql.scheme.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 数据库SchemeInfo
 *
 * @author kuuga
 */
@Data
public class TableInfo {

    /**
     * 表名
     */
    private String tableName;

    /**
     * 表描述
     */
    private String tableDesc;

    /**
     * 引擎
     */
    private String engine;

    /**
     * 表记录行数
     */
    private long tableRows;

    /**
     * 表数据容量
     */
    private long dataLength;
    private String dataCapacity;

    /**
     * 索引容量
     */
    private long indexLength;
    private String indexCapacity;

    /**
     * 表数据长度
     */
    private Date createTime;

    /**
     * 表数据长度
     */
    private Date updateTime;

    /**
     * SQL模型信息集合
     */
    private List<FieldModel> fieldModels;

}
