package io.github.kuugasky.kuugatool.operationlog.core.house;

import io.github.kuugasky.kuugatool.operationlog.annotations.OperationLog;
import io.github.kuugasky.kuugatool.operationlog.annotations.OperationLogField;
import io.github.kuugasky.kuugatool.operationlog.core.enums.CustomerPhoneDistrictEnum;
import io.github.kuugasky.kuugatool.operationlog.core.enums.HouseOwnerRelationshipEnum;
import io.github.kuugasky.kuugatool.operationlog.core.enums.YesOrNoEnum;
import io.github.kuugasky.kuugatool.operationlog.entity.OperationLogBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 房源业主保存Form
 *
 * @author pql
 * @since 2021-03-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@OperationLog(moduleName = "\r\n【业主信息】",
        stringFormats = {"${now}", "由空更新为\"${now}\"", "由\"${history}\"更新为\"${now}\"", "由\"${history}\"更新为\"空\""})
public class HouseOwnerOperationLog extends OperationLogBase {

    @OperationLogField(isJoinModuleName = true, moduleName = "\r\n【业主信息】", fieldName = "业主姓名", order = 40, leader = true)
    private String name;

    @OperationLogField(fieldName = "号码区", skip = true, isMerge = true, mergeName = "业主联系方式", mergePrefix = "(", mergeSuffix = ")", mergeSum = 2, mergeIndex = 1, order = 41)
    private CustomerPhoneDistrictEnum phoneDistrict;

    @OperationLogField(fieldName = "电话号码", isSensitive = true, isMerge = true, mergeName = "业主联系方式", mergeSum = 2, mergeIndex = 2, order = 42)
    private String phone;

    @OperationLogField(fieldName = "业主关系", order = 43)
    private HouseOwnerRelationshipEnum relationship;

    @OperationLogField(fieldName = "是否主要联系人", isSensitive = true, order = 44, isJoinOtherFiled = true, otherFiledName = "phone")
    private YesOrNoEnum mainPhone;

}
