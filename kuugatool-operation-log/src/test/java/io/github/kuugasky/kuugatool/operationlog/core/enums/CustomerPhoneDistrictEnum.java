package io.github.kuugasky.kuugatool.operationlog.core.enums;

import io.github.kuugasky.kuugatool.core.enums.EnumSign;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 客户号码所属地区枚举
 *
 * @author kuuga
 * @since 2021/2/22
 */
@AllArgsConstructor
@EnumSign("PhoneDistrictEnum")
public enum CustomerPhoneDistrictEnum {

    /**
     *
     */
    MAINLAND("大陆", "+86"),

    HONGKONG("香港", "+852"),

    MACAU("澳门", "+853"),

    TAIWAN("台湾", "+886"),

    OTHER("其他", null),
    ;

    @Getter
    private final String desc;

    @Getter
    private final String districtCode;

    /**
     * 港澳台
     */
    private static final List<CustomerPhoneDistrictEnum> LIST = new ArrayList<>() {
        {
            add(HONGKONG);
            add(MACAU);
            add(TAIWAN);
        }
    };

    public static boolean containsHongKongOrMacauOrTaiWan(CustomerPhoneDistrictEnum phoneDistrictEnum) {
        return LIST.contains(phoneDistrictEnum);
    }

}
