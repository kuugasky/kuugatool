package io.github.kuugasky.kuugatool.operationlog.core.house;

import io.github.kuugasky.kuugatool.operationlog.annotations.OperationLog;
import io.github.kuugasky.kuugatool.operationlog.annotations.OperationLogField;
import io.github.kuugasky.kuugatool.operationlog.entity.OperationLogBase;
import io.github.kuugasky.kuugatool.operationlog.enums.FieldTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * HouseOperationLog
 *
 * @author pengqinglong
 * @since 2021/3/25
 */
@EqualsAndHashCode(callSuper = true)
@Data
@OperationLog(moduleName = "【房源信息】",
        stringFormats = {"${now}", "由\"空\"更新为\"${now}\"", "由\"${history}\"更新为\"${now}\"", "由\"${history}\"更新为\"空\""})
public class HouseOperationLog extends OperationLogBase {

    @OperationLogField(fieldName = "\r\n【基本信息】", fieldType = FieldTypeEnum.OBJECT)
    private HouseBasicOperationLog house;

    @OperationLogField(fieldName = "\r\n【出售信息】", fieldType = FieldTypeEnum.OBJECT)
    private HouseSellOperationLog sell;

    @OperationLogField(fieldName = "\r\n【出租信息】", fieldType = FieldTypeEnum.OBJECT)
    private HouseRentOperationLog rent;

    @OperationLogField(fieldName = "\r\n【维护信息】", fieldType = FieldTypeEnum.OBJECT)
    private HouseMaintainOperationLog maintain;

    @OperationLogField(fieldName = "", order = 39, fieldType = FieldTypeEnum.OBJECT_LIST)
    private List<HouseOwnerOperationLog> ownerList;

}