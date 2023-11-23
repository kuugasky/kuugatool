package io.github.kuugasky.kuugatool.core.idcard;

import java.time.LocalDate;

/**
 * 身份证号验证工具
 *
 * @author kuuga
 */
public final class IDCardUtil {

    /**
     * 定义私有构造函数来屏蔽这个隐式公有构造函数
     */
    private IDCardUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 校验是否有效身份证号
     *
     * @param idCardNo idCardNo
     * @return boolean
     */
    public static boolean isValid(final String idCardNo) {
        return new IDCard(idCardNo).isValid();
    }

    /**
     * 根据身份编号获取性别，只支持15或18位身份证号码
     *
     * @param idCardNo 身份编号
     * @return 性别(1 : 男 ， 0 : 女)
     */
    public static String getGender(String idCardNo) {
        return new IDCard(idCardNo).getGender();
    }

    /**
     * 根据身份证号码计算年龄
     *
     * @param idCardNo idCardNo
     * @return age
     */
    public static int getAge(final String idCardNo) {
        return new IDCard(idCardNo).getAge();
    }

    /**
     * 根据身份证号码计算出生日期
     *
     * @param idCardNo idCardNo
     * @return birthday
     */
    public static LocalDate getBirthday(final String idCardNo) {
        return new IDCard(idCardNo).getBirthday();
    }

    /**
     * 根据身份证号码计算出生日期
     *
     * @param idCardNo idCardNo
     * @return birthdayStr
     */
    public static String getBirthdayStr(final String idCardNo) {
        return new IDCard(idCardNo).getBirthdayStr();
    }


    /**
     * 获取省份
     *
     * @param idCardNo idCardNo
     * @return 地区码
     */
    public static String getProvince(final String idCardNo) {
        return new IDCard(idCardNo).getProvince();
    }

    /**
     * 获取省份地区码
     *
     * @param idCardNo idCardNo
     * @return 地区码
     */
    public static String getProvinceCode(final String idCardNo) {
        return idCardNo.substring(0, 2);
    }

    /**
     * 根据身份编号获取市级编码，只支持15或18位身份证号码
     *
     * @param idCardNo 身份编码
     * @return 市级编码。
     */
    public static String getCityCode(final String idCardNo) {
        return new IDCard(idCardNo).getCityCode();
    }

    /**
     * 15位身份证号转换为诶18位
     *
     * @param idCardNo 身份编码
     * @return 18位身份证号
     */
    public static String convert15To18(final String idCardNo) {
        return new IDCard(idCardNo).getIdCardNo();
    }

}