package io.github.kuugasky.kuugatool.core.idcard;

import io.github.kuugasky.kuugatool.core.date.DateUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * IDCardVerify
 * 身份证号校验
 *
 * @author kuuga
 */
public final class IDCardVerify {

    /**
     * 中国公民身份证号码最小长度。
     */
    static final int CHINA_ID_MIN_LENGTH = 15;
    /**
     * 中国公民身份证号码最大长度。
     */
    static final int CHINA_ID_MAX_LENGTH = 18;
    /**
     * 最后一位身份证的号码
     */
    static final String[] VAL_CODE_ARR = {"1", "0", "x", "9", "8", "7", "6", "5", "4", "3", "2"};
    /**
     * 每位加权因子
     */
    static final int[] POWER = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
    /**
     * 17位系数
     */
    static final String[] COEFFICIENT = {"7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2"};

    static final String YYYY_MM_DD = "%s-%s-%s";
    static final int ZERO = 0;
    static final int TWO = 2;
    static final int EIGHT = 8;
    static final int SIX = 6;
    static final int TEN = 10;
    static final int TWELVE = 12;
    static final int FOURTEEN = 14;
    static final int SEVENTEEN = 17;
    private static final int THIRTY_ONE = 31;
    private static final int ONE_HUNDRED_AND_FIFTY = 150;

    private static final Pattern IS_NUMBER_PATTERN = Pattern.compile("[0-9]*");

    @SuppressWarnings("all")
    private static final Pattern IS_DATE_PATTERN = Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\- \\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");

    /**
     * 判断字符串是否为数字
     *
     * @param strNumber strNumber
     * @return boolean
     */
    static boolean isNumeric(String strNumber) {
        Matcher isNum = IS_NUMBER_PATTERN.matcher(strNumber);
        return isNum.matches();
    }

    /**
     * 判断字符串是否为日期格式
     *
     * @return boolean
     */
    static boolean isDate(String strDate) {
        Matcher matcher = IS_DATE_PATTERN.matcher(strDate);
        return matcher.matches();
    }

    /**
     * 年份、月份、日期是否都有效
     *
     * @param idCardNoYear  idCardNoYear
     * @param idCardNoMonth idCardNoMonth
     * @param idCardNoDay   idCardNoDay
     * @return boolean
     */
    static boolean isValidYearOrMonthOrDay(String idCardNoYear, String idCardNoMonth, String idCardNoDay) {
        boolean validYear = isValidYear(idCardNoYear, idCardNoMonth, idCardNoDay);
        boolean validMonth = isValidMonth(idCardNoMonth);
        boolean validDay = isValidDay(idCardNoDay);
        return validYear && validMonth && validDay;
    }

    /**
     * 是否有效地区码
     *
     * @param idCardNo idCardNo
     * @return boolean
     */
    static boolean isValidAreaCode(String idCardNo) {
        return IDCardAreaCode.AREA_CODE.get(idCardNo.substring(ZERO, TWO)) != null;
    }


    /**
     * 是否有效日期
     *
     * @param idCardNoDay idCardNoDay
     * @return boolean
     */
    private static boolean isValidDay(String idCardNoDay) {
        // 日期范围1-31
        int idCardDay = Integer.parseInt(idCardNoDay);
        return idCardDay <= THIRTY_ONE && idCardDay > ZERO;
    }

    /**
     * 是否有效月份
     *
     * @param idCardNoMonth idCardNoMonth
     * @return boolean
     */
    private static boolean isValidMonth(String idCardNoMonth) {
        // 月份范围1-12
        int idCardMonth = Integer.parseInt(idCardNoMonth);
        return idCardMonth <= TWELVE && idCardMonth > ZERO;
    }

    /**
     * 是否有效年份
     *
     * @param idCardNoYear  idCardNoYear
     * @param idCardNoMonth idCardNoMonth
     * @param idCardNoDay   idCardNoDay
     * @return boolean
     */
    private static boolean isValidYear(String idCardNoYear, String idCardNoMonth, String idCardNoDay) {
        try {
            GregorianCalendar gc = new GregorianCalendar();
            int diffYear = gc.get(Calendar.YEAR) - Integer.parseInt(idCardNoYear);
            Date idCardBirthday = DateUtil.parseLocalDate(idCardNoYear.concat("-").concat(idCardNoMonth).concat("-").concat(idCardNoDay));
            long diffTime = gc.getTime().getTime() - idCardBirthday.getTime();
            if (diffYear <= ONE_HUNDRED_AND_FIFTY && diffTime > ZERO) {
                return true;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return false;
    }

}