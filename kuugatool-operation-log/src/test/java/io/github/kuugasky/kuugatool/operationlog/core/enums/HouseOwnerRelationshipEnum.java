package io.github.kuugasky.kuugatool.operationlog.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 房源业主关系枚举
 *
 * @author kuuga
 * @since 2021/3/9
 */
@AllArgsConstructor
public enum HouseOwnerRelationshipEnum {
    /**
     *
     */
    PROPERTY_OWNER("产权人"),
    KINSHIP("亲属"),
    FRIEND("朋友"),
    TENANT("租客"),
    DRIVER("司机"),
    PLATFORM_BROKER("平台经纪人"),
    CHARTER_TENANT("包租客"),
    ;

    @Getter
    private final String desc;

}
