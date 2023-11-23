package io.github.kuugasky.kuugatool.operationlog.core.enums;

import io.github.kuugasky.kuugatool.core.enums.EnumSign;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 客户号码所属地区枚举
 *
 * @author kuuga
 * @since 2021/2/22
 */
@AllArgsConstructor
@EnumSign("YesOrNoEnum")
public enum YesOrNoEnum {

    /**
     *
     */
    YES("是"),

    NO("否"),

    ;

    @Getter
    private final String desc;

}
