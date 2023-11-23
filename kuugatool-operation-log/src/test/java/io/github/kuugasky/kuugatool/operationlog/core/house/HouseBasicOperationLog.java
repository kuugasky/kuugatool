package io.github.kuugasky.kuugatool.operationlog.core.house;

import io.github.kuugasky.kuugatool.operationlog.annotations.OperationLog;
import io.github.kuugasky.kuugatool.operationlog.annotations.OperationLogField;
import io.github.kuugasky.kuugatool.operationlog.core.enums.HouseDecorationEnum;
import io.github.kuugasky.kuugatool.operationlog.core.enums.HouseOrientationEnum;
import io.github.kuugasky.kuugatool.operationlog.core.enums.HouseStructureEnum;
import io.github.kuugasky.kuugatool.operationlog.core.enums.HouseTypeEnum;
import io.github.kuugasky.kuugatool.operationlog.entity.OperationLogBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * HouseBasicOperationLog
 *
 * @author pengqinglong
 * @since 2021/3/25
 */
@EqualsAndHashCode(callSuper = true)
@Data
@OperationLog(moduleName = "\r\n[基本信息]",
        stringFormats = {"${now}", "由空更新为\"${now}\"", "由\"${history}\"更新为\"${now}\"", "由${history}更新为空"})
public class HouseBasicOperationLog extends OperationLogBase {

    @OperationLogField(moduleName = "【房源状态】", isJoinModuleName = true, fieldName = "盘源状态", leader = true)
    private HouseTypeEnum houseType;

    @OperationLogField(isJoinModuleName = true, moduleName = "\r\n【基本信息】", fieldName = "建筑面积", suffix = "㎡，", order = 5, leader = true)
    private BigDecimal buildArea;

    @OperationLogField(fieldName = "房间/卧室", isMerge = true, mergeName = "户型", mergeSuffix = "房", mergeSum = 4, mergeIndex = 1, order = 6)
    private Integer bedroom;

    @OperationLogField(fieldName = "客厅", isMerge = true, mergeName = "户型", mergeSuffix = "厅", mergeSum = 4, mergeIndex = 2, order = 7)
    private Integer hall;

    @OperationLogField(fieldName = "厨房", isMerge = true, mergeName = "户型", mergeSuffix = "厨", mergeSum = 4, mergeIndex = 3, order = 8)
    private Integer kitchen;

    @OperationLogField(fieldName = "卫生间/浴室", isMerge = true, mergeName = "户型", mergeSuffix = "卫", mergeSum = 4, mergeIndex = 4, order = 9)
    private Integer bathroom;

    @OperationLogField(fieldName = "朝向", order = 10)
    private HouseOrientationEnum orientation;

    @OperationLogField(fieldName = "装修情况", order = 11)
    private HouseDecorationEnum decoration;

    @OperationLogField(fieldName = "座落", order = 12, isSensitive = true, headRetain = 0, tailRetain = 0)
    private String locationName;

    @OperationLogField(fieldName = "楼层", order = 13, isSensitive = true, headRetain = 0, tailRetain = 0)
    private String floorName;

    @OperationLogField(fieldName = "房号", order = 14, isSensitive = true, headRetain = 0, tailRetain = 0)
    private String roomNo;

    @OperationLogField(fieldName = "结构", order = 15)
    private HouseStructureEnum structure;

    @OperationLogField(fieldName = "套内面积", suffix = "㎡，", order = 16)
    private BigDecimal insideArea;
}