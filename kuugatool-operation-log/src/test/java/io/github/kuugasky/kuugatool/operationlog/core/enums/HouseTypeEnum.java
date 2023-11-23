package io.github.kuugasky.kuugatool.operationlog.core.enums;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * 房源类型枚举
 *
 * @author kuuga
 * @since 2019-04-15
 */
@AllArgsConstructor
public enum HouseTypeEnum {

    /**
     *
     */
    RENT("租"),
    SELL("售"),
    RENT_SELL("租售"),
    DATA("资料"),
    COMPANY_RENT_OUT("司租"),
    COMPANY_SOLD("司售"),
    ;

    @Getter
    private final String desc;

    /**
     * 返回有效房源状态集合
     */
    public static List<HouseTypeEnum> validHouseType() {
        return ListUtil.newArrayList(HouseTypeEnum.RENT, HouseTypeEnum.SELL, HouseTypeEnum.RENT_SELL);
    }

    /**
     * 返回必看房房源状态集合
     */
    public static List<HouseTypeEnum> mustSeeHouseType() {
        return ListUtil.newArrayList(HouseTypeEnum.SELL, HouseTypeEnum.RENT_SELL);
    }

    /**
     * 返回有效房源状态str集合
     */
    public static List<String> validHouseTypeStr() {
        return ListUtil.newArrayList(HouseTypeEnum.RENT.name(), HouseTypeEnum.SELL.name(), HouseTypeEnum.RENT_SELL.name());
    }

    /**
     * 返回无效房源状态集合
     */
    public static List<HouseTypeEnum> invalidHouseType() {
        return ListUtil.newArrayList(HouseTypeEnum.DATA, HouseTypeEnum.COMPANY_RENT_OUT, HouseTypeEnum.COMPANY_SOLD);
    }

    /**
     * 返回无效房源状态str集合
     */
    public static List<String> invalidHouseTypeStr() {
        return ListUtil.newArrayList(HouseTypeEnum.DATA.name(), HouseTypeEnum.COMPANY_RENT_OUT.name(), HouseTypeEnum.COMPANY_SOLD.name());
    }

    /**
     * 判断是否是租状态
     */
    public static boolean isRent(HouseTypeEnum houseType) {
        return RENT == houseType;
    }

    /**
     * 判断是否包含租状态
     */
    public static boolean isContainsRent(HouseTypeEnum houseType) {
        return RENT == houseType || RENT_SELL == houseType;
    }

    /**
     * 判断是否是售状态
     */
    public static boolean isSell(HouseTypeEnum houseType) {
        return SELL == houseType;
    }

    /**
     * 判断是否包含售状态
     */
    public static boolean isContainsSell(HouseTypeEnum houseType) {
        return SELL == houseType || RENT_SELL == houseType;
    }

    /**
     * 判断是否是租售类型
     */
    public static boolean isRentSell(HouseTypeEnum houseType) {
        return RENT_SELL == houseType;
    }

    /**
     * 判断是否是资料类型
     */
    public static boolean isData(HouseTypeEnum houseType) {
        return DATA == houseType;
    }

    /**
     * 判断是否是司租类型
     */
    public static boolean isCompanyRent(HouseTypeEnum houseType) {
        return COMPANY_RENT_OUT == houseType;
    }

    /**
     * 判断是否是司售类型
     */
    public static boolean isCompanySell(HouseTypeEnum houseType) {
        return COMPANY_SOLD == houseType;
    }

    /**
     * 判断是否是已成交类型
     */
    public static boolean isTrade(HouseTypeEnum houseType) {
        return COMPANY_SOLD == houseType || COMPANY_RENT_OUT == houseType;
    }

    /**
     * 判断是否是有效房源类型
     */
    public static boolean isValidType(HouseTypeEnum houseType) {
        return isSell(houseType) || isRent(houseType) || isRentSell(houseType);
    }

    /**
     * 判断是否是无效房源类型
     */
    public static boolean isInvalidType(HouseTypeEnum houseType) {
        return isData(houseType) || isCompanyRent(houseType) || isCompanySell(houseType);
    }

    /**
     * 判断是否是租状态
     */
    public static boolean isRent(String houseType) {
        return RENT.name().equals(houseType);
    }

    /**
     * 判断是否包含租状态
     */
    public static boolean isContainsRent(String houseType) {
        return RENT.name().equals(houseType) || RENT_SELL.name().equals(houseType);
    }

    /**
     * 判断是否是售状态
     */
    public static boolean isSell(String houseType) {
        return SELL.name().equals(houseType);
    }

    /**
     * 判断是否包含售状态
     */
    public static boolean isContainsSell(String houseType) {
        return SELL.name().equals(houseType) || RENT_SELL.name().equals(houseType);
    }

    /**
     * 判断是否是租售类型
     */
    public static boolean isRentSell(String houseType) {
        return RENT_SELL.name().equals(houseType);
    }

    /**
     * 判断是否是资料类型
     */
    public static boolean isData(String houseType) {
        return DATA.name().equals(houseType);
    }

    /**
     * 判断是否是司租类型
     */
    public static boolean isCompanyRent(String houseType) {
        return COMPANY_RENT_OUT.name().equals(houseType);
    }

    /**
     * 判断是否是司售类型
     */
    public static boolean isCompanySell(String houseType) {
        return COMPANY_SOLD.name().equals(houseType);
    }

    /**
     * 判断是否是已成交类型
     */
    public static boolean isTrade(String houseType) {
        return COMPANY_SOLD.name().equals(houseType) || COMPANY_RENT_OUT.name().equals(houseType);
    }

    /**
     * 判断是否是有效房源类型
     */
    public static boolean isValidType(String houseType) {
        return isSell(houseType) || isRent(houseType) || isRentSell(houseType);
    }

    /**
     * 判断是否是无效房源类型
     */
    public static boolean isInvalidType(String houseType) {
        return isData(houseType) || isCompanyRent(houseType) || isCompanySell(houseType);
    }

    /**
     * 通过租/售类型生成真正的房源状态
     */
    public static HouseTypeEnum build(HouseTypeEnum rent, HouseTypeEnum sell) {

        // 租售直接走
        if (isRent(rent) && isSell(sell)) {
            return RENT_SELL;
        }

        // 司售先走
        if (isCompanySell(sell)) {
            return COMPANY_SOLD;
        }

        // 租盘为司租或为空 并且售盘为售 售走
        boolean conditions = (rent == null || isCompanyRent(rent)) && isSell(sell);
        if (conditions) {
            return SELL;
        }

        // 司租再走 本来不用校验 确保严谨性 额外校验一个售为空
        if (isCompanyRent(rent) && sell == null) {
            return COMPANY_RENT_OUT;
        }

        // 再走租 本来不用校验 确保严谨性 额外校验一个售为空
        if (isRent(rent) && sell == null) {
            return RENT;
        }

        // 都么得 走资料 本来不用校验 确保严谨性 额外校验租/售为空
        if (rent == null && sell == null) {
            return DATA;
        }

        throw new RuntimeException("房源状态异常");
    }
}
