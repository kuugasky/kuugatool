package io.github.kuugasky.kuugatool.operationlog.entity;

import lombok.Data;

/**
 * OperationLogBase
 *
 * @author pengqinglong
 * @since 2021/3/24
 */
@Data
public class OperationLogBase {

    /**
     * 主键id，数据对比时以此ID做连接
     */
    private String id;

}