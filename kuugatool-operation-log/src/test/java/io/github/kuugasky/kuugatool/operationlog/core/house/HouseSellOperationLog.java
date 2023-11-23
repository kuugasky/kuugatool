package io.github.kuugasky.kuugatool.operationlog.core.house;

import io.github.kuugasky.kuugatool.operationlog.annotations.OperationLog;
import io.github.kuugasky.kuugatool.operationlog.annotations.OperationLogField;
import io.github.kuugasky.kuugatool.operationlog.core.enums.HousePropertyEnum;
import io.github.kuugasky.kuugatool.operationlog.core.enums.HouseSellTypeEnum;
import io.github.kuugasky.kuugatool.operationlog.entity.OperationLogBase;
import io.github.kuugasky.kuugatool.operationlog.enums.OtherFiledLocationEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 房源出售信息
 *
 * @author pengqinglong
 * @since 2021/3/16
 */

@EqualsAndHashCode(callSuper = false)
@Data
@OperationLog(moduleName = "\r\n【出售信息】",
        stringFormats = {"${now}", "由空更新为\"${now}\"", "由\"${history}\"更新为\"${now}\"", "由${history}更新为空"})
public class HouseSellOperationLog extends OperationLogBase {

    @OperationLogField(fieldName = "角色保护额外字段 用于拓房角色拼接", skip = true)
    private String roleProtect;

    @OperationLogField(moduleName = "\r\n【拓房角色】", isJoinModuleName = true, fieldName = "售拓房类型", order = 35, leader = true, isJoinOtherFiled = true, otherFiledLocation = OtherFiledLocationEnum.FIELD, otherFiledName = "roleProtect")
    private HousePropertyEnum extendType;

    @OperationLogField(fieldName = "售拓房人", order = 36, suffix = "；")
    private String extendPersonName;

    @OperationLogField(moduleName = "\r\n【价格信息】", isJoinModuleName = true, fieldName = "售价类型", order = 1, leader = true)
    private HouseSellTypeEnum sellType;

    @OperationLogField(fieldName = "售价", suffix = "万，", order = 2)
    private BigDecimal sellPrice;

}