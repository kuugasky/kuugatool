package io.github.kuugasky.kuugatool.extra.pinyin;

import com.github.stuxuhai.jpinyin.ChineseHelper;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import io.github.kuugasky.kuugatool.core.constants.KuugaConstants;

/**
 * ChineseConvertPinyinUtil
 * <p>
 * 汉语繁体拼音转换工具类
 * <p>
 * 拼音格式：<br>
 * - WITH_TONE_NUMBER--数字代表声调<br>
 * - WITHOUT_TONE--不带声调<br>
 * - WITH_TONE_MARK--带声调
 *
 * @author kuuga
 */
public final class ChineseConvertPinyinUtil {

    /**
     * 默认拼音分隔符
     */
    private static final String SEPARATOR = KuugaConstants.EMPTY;

    private ChineseConvertPinyinUtil() {
    }

    /**
     * 检查汉字是否为多音字
     *
     * @param pinYinStr 需转换的汉字
     * @return true 多音字，false 不是多音字
     */
    public static boolean checkPinYin(char pinYinStr) {
        try {
            return PinyinHelper.hasMultiPinyin(pinYinStr);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 转换为每个汉字对应拼音首字母字符串
     *
     * @param content     内容
     * @param deleteBlank 转换后去掉空格
     * @return 拼音字符串
     */
    public static String convertToGetShortPinYin(String content, boolean deleteBlank) {
        String tempStr;
        try {
            tempStr = PinyinHelper.getShortPinyin(content);
        } catch (Exception e) {
            tempStr = content;
            e.printStackTrace();
        }
        if (deleteBlank) {
            tempStr = tempStr.replaceAll("\\s*", SEPARATOR);
        }
        return tempStr;
    }

    /**
     * 转换为有声调的拼音字符串
     *
     * @param content     内容
     * @param deleteBlank 转换后去掉空格
     * @param separator   拼音分隔符
     * @return 有声调的拼音字符串
     */
    public static String convertToMarkPinYin(String content, boolean deleteBlank, String separator) {
        String tempStr;
        try {
            if (separator == null) {
                separator = ChineseConvertPinyinUtil.SEPARATOR;
            }
            tempStr = PinyinHelper.convertToPinyinString(content, separator, PinyinFormat.WITH_TONE_MARK);

        } catch (Exception e) {
            tempStr = content;
            e.printStackTrace();
        }
        if (deleteBlank) {
            tempStr = tempStr.replaceAll("\\s*", ChineseConvertPinyinUtil.SEPARATOR);
        }
        return tempStr;
    }

    /**
     * 转换为数字声调字符串
     *
     * @param content     内容
     * @param deleteBlank 转换后去掉空格
     * @param separator   拼音分隔符
     * @return 转换完成的拼音字符串
     */
    public static String convertToNumberPinYin(String content, boolean deleteBlank, String separator) {
        String tempStr;
        try {
            if (separator == null) {
                separator = ChineseConvertPinyinUtil.SEPARATOR;
            }
            tempStr = PinyinHelper.convertToPinyinString(content, separator, PinyinFormat.WITH_TONE_NUMBER);
        } catch (Exception e) {
            tempStr = content;
            e.printStackTrace();
        }
        if (deleteBlank) {
            tempStr = tempStr.replaceAll("\\s*", ChineseConvertPinyinUtil.SEPARATOR);
        }
        return tempStr;
    }

    /**
     * 繁体转换为简体
     *
     * @param content     内容
     * @param deleteBlank 转换后去掉空格
     * @return String
     */
    public static String convertToSimplified(String content, boolean deleteBlank) {
        String tempStr;
        try {
            tempStr = ChineseHelper.convertToSimplifiedChinese(content);
        } catch (Exception e) {
            tempStr = content;
            e.printStackTrace();
        }
        if (deleteBlank) {
            tempStr = tempStr.replaceAll("\\s*", SEPARATOR);
        }
        return tempStr;
    }

    /**
     * 转换为不带音调的拼音字符串
     *
     * @param content     内容
     * @param deleteBlank 转换后去掉空格字符
     * @param separator   拼音分隔符
     * @return 拼音字符串
     */
    public static String convertToTonePinYin(String content, boolean deleteBlank, String separator) {
        String tempStr;
        try {
            if (separator == null) {
                separator = ChineseConvertPinyinUtil.SEPARATOR;
            }
            tempStr = PinyinHelper.convertToPinyinString(content, separator, PinyinFormat.WITHOUT_TONE);
        } catch (Exception e) {
            tempStr = content;
            e.printStackTrace();
        }
        if (deleteBlank) {
            tempStr = tempStr.replaceAll("\\s*", ChineseConvertPinyinUtil.SEPARATOR);
        }
        return tempStr;
    }

    /**
     * 简体转换为繁体
     *
     * @param content     内容
     * @param deleteBlank 转换后去掉非打印字符
     * @return String
     */
    public static String convertToTraditional(String content, boolean deleteBlank) {
        String tempStr;
        try {
            tempStr = ChineseHelper.convertToTraditionalChinese(content);
        } catch (Exception e) {
            tempStr = content;
            e.printStackTrace();
        }
        if (deleteBlank) {
            tempStr = tempStr.replaceAll("\\s*", SEPARATOR);
        }
        return tempStr;
    }

}
