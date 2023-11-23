package io.github.kuugasky.kuugatool.core.idcard;

import io.github.kuugasky.kuugatool.core.charset.CharUtil;
import io.github.kuugasky.kuugatool.core.constants.KuugaConstants;
import io.github.kuugasky.kuugatool.core.date.DateFormat;
import io.github.kuugasky.kuugatool.core.date.DateUtil;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import io.github.kuugasky.kuugatool.core.regex.PatternPool;
import io.github.kuugasky.kuugatool.core.regex.RegexUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

/**
 * IDCard
 * 旧的最早期的是15位的，为了解决千年虫的问题和增加防伪，新身份证号码就是18位的
 * <p>
 * [421022][19881006][45][2][1]
 * [421022]是地区代码
 * [19881006]是出生年月日
 * [45]是户籍序列编号
 * [2]是区分男女，1是男、2是女
 * [1]是前面所有数字经过特定计算后得出的数，有点防伪的意思
 * 对应15位为：
 * 去掉出生年月日前的19，和去掉最后一位1，就是原先最早期的15位证拉，（421022881006452）
 *
 * @author kuuga
 */
public final class IDCard {

    /**
     * 身份证号
     */
    @Getter
    private final String idCardNo;
    /**
     * 有效状态
     */
    private final boolean valid;
    /**
     * 无效状态
     */
    private final boolean isInvalid;

    public IDCard(final String idCardNo) {
        if (StringUtil.isEmpty(idCardNo)) {
            throw new RuntimeException("ID Card must not empty");
        }
        if (idCardNo.length() != IDCardVerify.CHINA_ID_MIN_LENGTH && idCardNo.length() != IDCardVerify.CHINA_ID_MAX_LENGTH) {
            throw new RuntimeException("ID Card length must be 15 or 18");
        }

        if (idCardNo.length() == IDCardVerify.CHINA_ID_MIN_LENGTH) {
            this.idCardNo = convert15To18(idCardNo);
        } else {
            this.idCardNo = idCardNo;
        }

        this.valid = isValidIDCard();
        this.isInvalid = !valid;
    }

    public boolean isValid() {
        return this.valid;
    }

    /**
     * 获取生日信息
     *
     * @return birthday
     */
    public LocalDate getBirthday() {
        if (isInvalid) {
            return null;
        }
        final String birthdayStr = idCardNo.substring(IDCardVerify.SIX, IDCardVerify.FOURTEEN);
        Date parse = DateUtil.parse(DateFormat.yyyyMMdd, birthdayStr);
        if (ObjectUtil.nonNull(parse)) {
            return DateUtil.toLocalDate(parse);
        }
        return null;
    }

    /**
     * 获取生日信息
     *
     * @return birthdayStr
     */
    public String getBirthdayStr() {
        if (isInvalid) {
            return StringUtil.EMPTY;
        }
        LocalDate birthday = getBirthday();
        if (ObjectUtil.nonNull(birthday)) {
            return birthday.toString();
        }
        return StringUtil.EMPTY;
    }

    /**
     * 获取年龄
     *
     * @return age
     */
    public int getAge() {
        if (isInvalid) {
            return 0;
        }

        int idCardLength = idCardNo.length();

        String birthdayStr;

        if (idCardLength == IDCardVerify.CHINA_ID_MAX_LENGTH) {
            birthdayStr = idCardNo.substring(IDCardVerify.SIX, IDCardVerify.TEN);
            return LocalDate.now().getYear() - Integer.parseInt(birthdayStr);
        } else if (idCardLength == IDCardVerify.CHINA_ID_MIN_LENGTH) {
            birthdayStr = idCardNo.substring(IDCardVerify.SIX, IDCardVerify.EIGHT);
            return Integer.parseInt(birthdayStr);
        }
        return 0;
    }

    /**
     * 校验身份证
     *
     * @return boolean
     */
    @SuppressWarnings("all")
    private boolean isValidIDCard() {
        final int idCardLength = this.idCardNo.length();
        if (idCardLength != IDCardVerify.CHINA_ID_MIN_LENGTH && idCardLength != IDCardVerify.CHINA_ID_MAX_LENGTH) {
            return false;
        }
        String idCardStr = idCardLength == IDCardVerify.CHINA_ID_MAX_LENGTH ? idCardNo : convert15To18(idCardNo);

        final String idCardNoYear = idCardStr.substring(IDCardVerify.SIX, IDCardVerify.TEN);
        final String idCardNoMonth = idCardStr.substring(IDCardVerify.TEN, IDCardVerify.TWELVE);
        final String idCardNoDay = idCardStr.substring(IDCardVerify.TWELVE, IDCardVerify.FOURTEEN);

        String strDate = String.format(IDCardVerify.YYYY_MM_DD, idCardNoYear, idCardNoMonth, idCardNoDay);
        if (!IDCardVerify.isNumeric(idCardStr)) {
            return false;
        }
        if (!IDCardVerify.isDate(strDate)) {
            return false;
        }
        if (!IDCardVerify.isValidYearOrMonthOrDay(idCardNoYear, idCardNoMonth, idCardNoDay)) {
            return false;
        }
        if (!IDCardVerify.isValidAreaCode(idCardStr)) {
            return false;
        }

        //======判断最后一位值
        int totalMultiplicationAiCoefficient = IDCardVerify.ZERO;
        for (int i = IDCardVerify.ZERO; i < IDCardVerify.SEVENTEEN; i++) {
            int number = Integer.parseInt(String.valueOf(idCardStr.charAt(i)));
            int coefficient = Integer.parseInt(IDCardVerify.COEFFICIENT[i]);
            totalMultiplicationAiCoefficient = totalMultiplicationAiCoefficient + number * coefficient;
        }

        int modValue = totalMultiplicationAiCoefficient % 11;

        String strVerifyCode = IDCardVerify.VAL_CODE_ARR[modValue];

        return strVerifyCode.equals(idCardStr.substring(IDCardVerify.SEVENTEEN));
    }

    /**
     * 获取省份
     */
    public String getProvince() {
        if (isInvalid) {
            return StringUtil.EMPTY;
        }
        return IDCardAreaCode.AREA_CODE.get(this.idCardNo.substring(IDCardVerify.ZERO, IDCardVerify.TWO));
    }

    /**
     * 获取省份Code
     */
    public String getProvinceCode() {
        return this.idCardNo.substring(0, 2);
    }

    /**
     * 获取性别
     */
    public String getGender() {
        if (isInvalid) {
            return StringUtil.EMPTY;
        }
        char sCardChar = Objects.requireNonNull(idCardNo).charAt(16);
        return (sCardChar % 2 != 0) ? "男" : "女";
    }

    /**
     * 获取城市Code
     */
    public String getCityCode() {
        if (isInvalid) {
            return StringUtil.EMPTY;
        }
        return idCardNo.substring(0, 6);
    }

    /**
     * 将15位身份证号码转换为18位
     *
     * @param idCardNo 15位身份编码
     * @return 18位身份编码
     */
    private String convert15To18(final String idCardNo) {
        if (StringUtil.isEmpty(idCardNo) || idCardNo.length() == IDCardVerify.CHINA_ID_MAX_LENGTH) {
            return idCardNo;
        }
        if (idCardNo.length() != IDCardVerify.CHINA_ID_MIN_LENGTH) {
            return idCardNo;
        }
        StringBuilder idCard18;

        if (!RegexUtil.isMatch(PatternPool.NUMBERS.pattern(), idCardNo)) {
            return null;
        }
        // 获取出生年月日
        String birthday = idCardNo.substring(6, 12);
        Date birthDate = DateUtil.parse(DateFormat.yyMMdd, birthday);
        // 获取出生年(完全表现形式,如：2010)
        int sYear = DateUtil.getYear(birthDate);
        if (sYear > KuugaConstants.TWO_THOUSAND) {
            // 2000年之后不存在15位身份证号，此处用于修复此问题的判断
            sYear -= 100;
        }
        idCard18 = StringUtil.builder().append(idCardNo, 0, 6).append(sYear).append(idCardNo.substring(8));
        // 获取校验位
        char sVal = getCheckCode18(idCard18.toString());
        idCard18.append(sVal);
        return idCard18.toString();
    }

    /**
     * 获得18位身份证校验码
     *
     * @param code17 18位身份证号中的前17位
     * @return 第18位
     */
    private char getCheckCode18(String code17) {
        int sum = getPowerSum(code17.toCharArray());
        return getCheckCode18(sum);
    }

    /**
     * 将身份证的每位和对应位的加权因子相乘之后，再得到和值
     *
     * @param iArr 身份证号码的数组
     * @return 身份证编码
     */
    private int getPowerSum(char[] iArr) {
        int iSum = 0;
        if (IDCardVerify.POWER.length == iArr.length) {
            for (int i = 0; i < iArr.length; i++) {
                iSum += Integer.parseInt(String.valueOf(iArr[i])) * IDCardVerify.POWER[i];
            }
        }
        return iSum;
    }

    /**
     * 将power和值与11取模获得余数进行校验码判断
     *
     * @param iSum 加权和
     * @return 校验位
     */
    private char getCheckCode18(int iSum) {
        return switch (iSum % 11) {
            case 10 -> '2';
            case 9 -> '3';
            case 8 -> '4';
            case 7 -> '5';
            case 6 -> '6';
            case 5 -> '7';
            case 4 -> '8';
            case 3 -> '9';
            case 2 -> 'X';
            case 1 -> '0';
            case 0 -> '1';
            default -> CharUtil.SPACE;
        };
    }

}