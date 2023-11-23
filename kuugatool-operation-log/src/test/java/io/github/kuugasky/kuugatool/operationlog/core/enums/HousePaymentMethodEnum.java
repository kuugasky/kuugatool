package io.github.kuugasky.kuugatool.operationlog.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 付款方式枚举
 *
 * @author kuuga
 * @since 2021/3/8
 */
@AllArgsConstructor
public enum HousePaymentMethodEnum {

    /**
     *
     */
    BET_ONE_PAY_ONE("押一付一"),
    BET_ONE_PAY_TWO("押一付二"),
    BET_ONE_PAY_THREE("押一付三"),
    BET_TWO_PAY_ONE("押二付一"),
    BET_TWO_PAY_TWO("押二付二"),
    BET_TWO_PAY_THREE("押二付三"),
    PAY_HALF_YEAR("半年付"),
    PAY_YEAR("年付"),
    INTERVIEW("面议"),
    ;

    @Getter
    private final String desc;

}
