package io.github.kuugasky.kuugatool.operationlog.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 房源设施枚举
 *
 * @author kuuga
 * @since 2021/3/8
 */
@AllArgsConstructor
public enum HouseFacilityEnum {

    /**
     *
     */
    VACANT_ROOM("空房"),
    BED("床"),
    WARDROBE("衣柜"),
    SOFA("沙发"),
    DINING_TABLE_CHAIR("餐桌椅"),
    AIR_CONDITIONING("空调"),
    TV("电视"),
    WATER_HEATER("热水器"),
    WASHING_MACHINE("洗衣机"),
    REFRIGERATOR("冰箱"),
    BROADBAND("宽带"),
    RANGE_HOOD("油烟机"),
    MICROWAVE_OVEN("微波炉"),
    NATURAL_GAS("天然气"),
    DISINFECTION_CABINET("消毒柜"),
    ;

    @Getter
    private final String desc;

}
