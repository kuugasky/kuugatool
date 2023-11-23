package io.github.kuugasky.kuugatool.operationlog.core.house;

import io.github.kuugasky.kuugatool.operationlog.annotations.OperationLog;
import io.github.kuugasky.kuugatool.operationlog.annotations.OperationLogField;
import io.github.kuugasky.kuugatool.operationlog.core.enums.*;
import io.github.kuugasky.kuugatool.operationlog.entity.OperationLogBase;
import io.github.kuugasky.kuugatool.operationlog.enums.OtherFiledLocationEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

/**
 * 房源出租信息
 *
 * @author pengqinglong
 * @since 2021/3/16
 */
@EqualsAndHashCode(callSuper = false)
@Data
@OperationLog(moduleName = "\r\n【出租信息】",
        stringFormats = {"${now}", "由空更新为\"${now}\"", "由\"${history}\"更新为\"${now}\"", "由${history}更新为空"})
public class HouseRentOperationLog extends OperationLogBase {

    @OperationLogField(fieldName = "角色保护额外字段 用于拓房角色拼接", skip = true)
    private String roleProtect;

    @OperationLogField(fieldName = "租拓房类型", order = 37, isJoinOtherFiled = true, otherFiledLocation = OtherFiledLocationEnum.FIELD, otherFiledName = "roleProtect")
    private HousePropertyEnum extendType;

    @OperationLogField(fieldName = "租拓房人", order = 38)
    private String extendPersonName;

    @OperationLogField(fieldName = "租价类型", order = 3)
    private HouseRentTypeEnum rentType;

    @OperationLogField(fieldName = "租价", suffix = "元，", order = 4)
    private BigDecimal rentPrice;

    @OperationLogField(moduleName = "\r\n【出租信息】", isJoinModuleName = true, fieldName = "押金", suffix = "元，", order = 28, leader = true)
    private BigDecimal deposit;

    @OperationLogField(fieldName = "转让费", suffix = "元，", order = 29)
    private BigDecimal transferfee;

    @OperationLogField(fieldName = "租赁方式", order = 30)
    private HouseLeaseWayEnum rentMethod;

    @OperationLogField(fieldName = "付款方式", order = 31)
    private HousePaymentMethodEnum paymentMethod;

    @OperationLogField(fieldName = "房屋设施", separator = "/", order = 32)
    private List<HouseFacilityEnum> houseFacilitiesList;
}