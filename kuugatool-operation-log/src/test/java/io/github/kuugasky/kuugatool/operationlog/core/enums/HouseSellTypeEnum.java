package io.github.kuugasky.kuugatool.operationlog.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 售价类型枚举
 *
 * @author kuuga
 * @since 2021/3/8
 */
@AllArgsConstructor
public enum HouseSellTypeEnum {

    /**
     *
     */
    PAID_IN("实收"),
    GO_DUTCH("各付"),
    ;

    @Getter
    private final String desc;

}
