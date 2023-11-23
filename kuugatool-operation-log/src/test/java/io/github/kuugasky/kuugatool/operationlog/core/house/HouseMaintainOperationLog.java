package io.github.kuugasky.kuugatool.operationlog.core.house;

import io.github.kuugasky.kuugatool.operationlog.annotations.OperationLog;
import io.github.kuugasky.kuugatool.operationlog.annotations.OperationLogField;
import io.github.kuugasky.kuugatool.operationlog.core.enums.*;
import io.github.kuugasky.kuugatool.operationlog.entity.OperationLogBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 维护信息
 *
 * @author pengqinglong
 * @since 2021/3/16
 */
@EqualsAndHashCode(callSuper = false)
@Data
@OperationLog(moduleName = "\r\n【维护信息】",
        stringFormats = {"${now}", "由空更新为\"${now}\"", "由\"${history}\"更新为\"${now}\"", "由${history}更新为空"})
public class HouseMaintainOperationLog extends OperationLogBase {

    @OperationLogField(isJoinModuleName = true, fieldName = "产权状态", order = 19, leader = true)
    private HousePropertyStatusEnum propertyStatus;

    @OperationLogField(fieldName = "年限", order = 20)
    private HouseYearLimitEnum yearLimit;

    @OperationLogField(fieldName = "是否唯一", order = 21)
    private YesOrNoEnum uniqueState;

    @OperationLogField(fieldName = "按揭欠款", order = 22)
    private BigDecimal mortgageArrears;

    @OperationLogField(fieldName = "按揭银行", order = 23)
    private String mortgageBank;

    @OperationLogField(fieldName = "房屋居住状态", order = 24)
    private HouseLivingStatusEnum livingStatus;

    @OperationLogField(fieldName = "租赁截止时间", order = 24)
    private Date leaseTermTime;

    @OperationLogField(fieldName = "共有产权", order = 25)
    private YesOrNoEnum commonProperty;

    @OperationLogField(fieldName = "小学学位", order = 26)
    private HouseOccupancyStatusEnum primaryDegree;

    @OperationLogField(fieldName = "中学学位", order = 27)
    private HouseOccupancyStatusEnum middleDegree;

}