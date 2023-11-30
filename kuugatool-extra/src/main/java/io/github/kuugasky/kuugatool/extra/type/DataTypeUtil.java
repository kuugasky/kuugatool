package io.github.kuugasky.kuugatool.extra.type;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONException;
import io.github.kuugasky.kuugatool.core.number.NumberUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数据类型判断工具类
 *
 * @author kuuga
 * @since 2021-01-21 上午11:13
 */
public class DataTypeUtil {

    private static final Pattern IS_NUMERIC_PATTERN = Pattern.compile("[-+]?[0-9]+(\\.[0-9]+)?");
    private static final String TRUE = "true";
    private static final String FALSE = "false";

    /**
     * 判断是否是数字类型的
     *
     * @param cs CharSequence
     * @return boolean
     */
    public static boolean isNumeric(final CharSequence cs) {
        if (StringUtil.isEmpty(cs)) {
            return true;
        }

        Matcher isNum = IS_NUMERIC_PATTERN.matcher(cs);
        return isNum.matches();
    }

    /**
     * 判断是否是布尔值型的
     *
     * @param str str
     * @return boolean
     */
    public static boolean isBoolean(final CharSequence str) {
        if (StringUtil.isEmpty(str)) {
            return false;
        }
        return TRUE.contentEquals(str) || FALSE.contentEquals(str);
    }

    /**
     * 判断字符串是否是指定的时间格式
     *
     * @param str    str
     * @param format 格式
     * @return boolean
     */
    public static boolean isDateTime(final String str, final String format) {
        if (StringUtil.isEmpty(str)) {
            return false;
        }
        if (StringUtil.isEmpty(format)) {
            throw new NullPointerException("format不能为空");
        }
        synchronized (DataTypeUtil.class) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            try {
                simpleDateFormat.setLenient(false);
                simpleDateFormat.parse(str);
            } catch (ParseException e) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串是否是Array<Number>
     *
     * @param str str
     * @return boolean
     */
    public static boolean isArrayNumber(final String str) {
        if (StringUtil.isEmpty(str)) {
            return false;
        }
        try {
            JSONArray jsonArray = JSON.parseArray(str);
            Iterator<Object> iterator = jsonArray.iterator();
            boolean result = false;
            while (iterator.hasNext()) {
                result = isNumeric(iterator.next().toString());
                if (!result) {
                    return false;
                }
            }
            return result;
        } catch (JSONException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是Array<String>
     *
     * @param str str
     * @return boolean
     */
    public static boolean isArray(final String str) {
        if (StringUtil.isEmpty(str)) {
            return false;
        }
        try {
            JSON.parseArray(str);
            return true;
        } catch (JSONException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是Array<Boolean>
     *
     * @param str str
     * @return boolean
     */
    public static boolean isArrayBoolean(final String str) {
        if (StringUtil.isEmpty(str)) {
            return false;
        }
        try {
            JSONArray jsonArray = JSON.parseArray(str);
            Iterator<Object> iterator = jsonArray.iterator();
            boolean result = false;
            while (iterator.hasNext()) {
                result = isBoolean(iterator.next().toString());
                if (!result) {
                    return false;
                }
            }
            return result;
        } catch (JSONException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是Array<DateTime>
     *
     * @param str    str
     * @param format format
     * @return boolean
     */
    public static boolean isArrayDateTime(final String str, final String format) {
        if (StringUtil.isEmpty(str)) {
            return false;
        }
        if (StringUtil.isEmpty(format)) {
            throw new NullPointerException("format不能为空");
        }
        try {
            JSONArray jsonArray = JSON.parseArray(str);
            Iterator<Object> iterator = jsonArray.iterator();
            boolean result = false;
            while (iterator.hasNext()) {
                result = isDateTime(iterator.next().toString(), format);
                if (!result) {
                    return false;
                }
            }
            return result;
        } catch (JSONException e) {
            return false;
        }
    }

    /**
     * 判断字符串是否是时间戳（秒）
     *
     * @param str str
     * @return boolean
     */
    public static boolean isTimestampOfSecond(final CharSequence str) {
        if (StringUtil.isEmpty(str)) {
            return false;
        }
        // 秒 长度是十位
        if (NumberUtil.isNumber(str)) {
            return str.length() == 10;
        }
        return false;
    }

    /**
     * 判断字符串是否是时间戳（毫秒）
     *
     * @param str str
     * @return boolean
     */
    @SuppressWarnings("all")
    public static boolean isTimestampOfSecondMS(final CharSequence str) {
        if (StringUtil.isEmpty(str)) {
            return false;
        }
        // 毫秒 长度是13位
        if (NumberUtil.isNumber(str)) {
            return str.length() == 13;
        }
        return false;
    }

}
