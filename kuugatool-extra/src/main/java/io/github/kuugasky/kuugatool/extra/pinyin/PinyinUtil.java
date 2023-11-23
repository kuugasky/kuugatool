package io.github.kuugasky.kuugatool.extra.pinyin;

import com.github.stuxuhai.jpinyin.ChineseHelper;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import io.github.kuugasky.kuugatool.core.collection.ArrayUtil;
import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.constants.KuugaConstants;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.text.Collator;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * 拼音工具类
 *
 * @author kuuga
 */
@Slf4j
public final class PinyinUtil {

    /**
     * 检查汉字是否为多音字
     *
     * @param pinYinStr 需转换的汉字
     * @return true 多音字，false 不是多音字
     */
    public static boolean isMultiToneWord(char pinYinStr) {
        boolean check = false;
        try {
            check = PinyinHelper.hasMultiPinyin(pinYinStr);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("检查汉字是否为多音字异常：{}", e.getMessage(), e);
        }
        return check;
    }

    /**
     * 转换为不带音调的短拼音字符串
     *
     * @param pinYinStr 输入字符串
     * @return 简拼
     */
    public static String toShortPinYin(String pinYinStr) {
        return toShortPinYin(pinYinStr, false);
    }

    /**
     * 转换为不带音调的短拼音字符串，并转小写
     *
     * @param pinYinStr 输入字符串
     * @return 简拼
     */
    public static String toShortPinYinToLowerCase(String pinYinStr) {
        String shortPinYin = toShortPinYin(pinYinStr, false);
        return StringUtil.hasText(shortPinYin) ? shortPinYin.toLowerCase() : shortPinYin;
    }

    /**
     * 转换为不带音调的短拼音字符串，并转大写
     *
     * @param pinYinStr 输入字符串
     * @return 简拼
     */
    public static String toShortPinYinToUpperCase(String pinYinStr) {
        String shortPinYin = toShortPinYin(pinYinStr, false);
        return StringUtil.hasText(shortPinYin) ? shortPinYin.toUpperCase() : shortPinYin;
    }

    /**
     * 获取大写字母第一个拼音
     *
     * @param pinYinStr 输入字符串
     * @return 大写首字母拼音
     */
    public static String getFirstPinyinOfUpperCase(String pinYinStr) {
        String pinyin = PinyinUtil.toShortPinYinToUpperCase(pinYinStr, true);
        if (StringUtil.isEmpty(pinyin)) {
            return StringUtil.EMPTY;
        }
        return String.valueOf(pinyin.charAt(0));
    }

    /**
     * 转换为不带音调的短拼音字符串
     *
     * @param pinYinStr   输入字符串
     * @param deleteBlank 删除空格
     * @return 简拼
     */
    public static String toShortPinYin(String pinYinStr, boolean deleteBlank) {
        if (StringUtil.isEmpty(pinYinStr)) {
            return pinYinStr;
        }
        String tempStr;
        try {
            tempStr = PinyinHelper.getShortPinyin(pinYinStr);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("转换文本短拼音异常：{}", e.getMessage(), e);
            return pinYinStr;
        }
        if (deleteBlank) {
            tempStr = tempStr.replaceAll("\\s*", KuugaConstants.EMPTY);
        }
        return tempStr;
    }

    /**
     * 转换为不带音调的短拼音字符串，并转小写
     *
     * @param pinYinStr   输入字符串
     * @param deleteBlank 删除空格
     * @return 简拼
     */
    public static String toShortPinYinToLowerCase(String pinYinStr, boolean deleteBlank) {
        String shortPinYin = toShortPinYin(pinYinStr, deleteBlank);
        return StringUtil.hasText(shortPinYin) ? shortPinYin.toLowerCase() : shortPinYin;
    }

    /**
     * 转换为不带音调的短拼音字符串，并转大写
     *
     * @param pinYinStr   输入字符串
     * @param deleteBlank 删除空格
     * @return 简拼
     */
    public static String toShortPinYinToUpperCase(String pinYinStr, boolean deleteBlank) {
        String shortPinYin = toShortPinYin(pinYinStr, deleteBlank);
        return StringUtil.hasText(shortPinYin) ? shortPinYin.toUpperCase() : shortPinYin;
    }

    /**
     * 转换为不带音调的全拼音字符串
     *
     * @param pinYinStr 需转换的汉字
     * @return 全拼
     */
    public static String toFullPinYin(String pinYinStr) {
        return toFullPinYin(pinYinStr, false);
    }

    /**
     * 转换为不带音调的全拼音字符串，并转小写
     *
     * @param pinYinStr 需转换的汉字
     * @return 全拼
     */
    public static String toFullPinYinToLowerCase(String pinYinStr) {
        String fullPinYin = toFullPinYin(pinYinStr, false);
        return StringUtil.hasText(fullPinYin) ? fullPinYin.toLowerCase() : fullPinYin;
    }

    /**
     * 转换为不带音调的全拼音字符串，并转大写
     *
     * @param pinYinStr 需转换的汉字
     * @return 全拼
     */
    public static String toFullPinYinToUpperCase(String pinYinStr) {
        String fullPinYin = toFullPinYin(pinYinStr, false);
        return StringUtil.hasText(fullPinYin) ? fullPinYin.toUpperCase() : fullPinYin;
    }

    /**
     * 转换为不带音调的全拼音字符串
     *
     * @param pinYinStr   需转换的汉字
     * @param deleteBlank 删除空格
     * @return 全拼
     */
    public static String toFullPinYin(String pinYinStr, boolean deleteBlank) {
        if (StringUtil.isEmpty(pinYinStr)) {
            return pinYinStr;
        }
        String tempStr;
        try {
            tempStr = PinyinHelper.convertToPinyinString(pinYinStr, KuugaConstants.EMPTY, PinyinFormat.WITHOUT_TONE);
        } catch (Exception e) {
            tempStr = pinYinStr;
            e.printStackTrace();
        }
        if (deleteBlank) {
            tempStr = tempStr.replaceAll("\\s*", KuugaConstants.EMPTY);
        }
        return tempStr;
    }

    /**
     * 转换为不带音调的全拼音字符串，并转小写
     *
     * @param pinYinStr   需转换的汉字
     * @param deleteBlank 删除空格
     * @return 全拼
     */
    public static String toFullPinYinToLowerCase(String pinYinStr, boolean deleteBlank) {
        String fullPinYin = toFullPinYin(pinYinStr, deleteBlank);
        return StringUtil.hasText(fullPinYin) ? fullPinYin.toLowerCase() : fullPinYin;
    }

    /**
     * 转换为不带音调的全拼音字符串，并转大写
     *
     * @param pinYinStr   需转换的汉字
     * @param deleteBlank 删除空格
     * @return 全拼
     */
    public static String toFullPinYinToUpperCase(String pinYinStr, boolean deleteBlank) {
        String fullPinYin = toFullPinYin(pinYinStr, deleteBlank);
        return StringUtil.hasText(fullPinYin) ? fullPinYin.toUpperCase() : fullPinYin;
    }

    /**
     * 转换为有声调的拼音字符串
     *
     * @param pinYinStr 汉字
     * @return 有声调的拼音字符串
     */
    public static String toMarkPinYin(String pinYinStr) {
        return toMarkPinYin(pinYinStr, false);
    }

    /**
     * 转换为有声调的拼音字符串
     *
     * @param pinYinStr   汉字
     * @param deleteBlank 转换后去掉非打印字符
     * @return 有声调的拼音字符串
     */
    public static String toMarkPinYin(String pinYinStr, boolean deleteBlank) {
        if (StringUtil.isEmpty(pinYinStr)) {
            return pinYinStr;
        }
        String tempStr;
        try {
            tempStr = PinyinHelper.convertToPinyinString(pinYinStr, KuugaConstants.EMPTY, PinyinFormat.WITH_TONE_MARK);
        } catch (Exception e) {
            tempStr = pinYinStr;
            e.printStackTrace();
        }
        if (deleteBlank) {
            tempStr = tempStr.replaceAll("\\s*", KuugaConstants.EMPTY);
        }
        return tempStr;
    }

    /**
     * 转换为数字声调字符串
     *
     * @param pinYinStr 需转换的汉字
     * @return 转换完成的拼音字符串
     */
    public static String toNumberPinYin(String pinYinStr) {
        return toNumberPinYin(pinYinStr, false);
    }

    /**
     * 转换为数字声调字符串
     *
     * @param pinYinStr   需转换的汉字
     * @param deleteBlank 转换后去掉非打印字符
     * @return 转换完成的拼音字符串
     */
    public static String toNumberPinYin(String pinYinStr, boolean deleteBlank) {
        if (StringUtil.isEmpty(pinYinStr)) {
            return pinYinStr;
        }
        String tempStr;
        try {
            tempStr = PinyinHelper.convertToPinyinString(pinYinStr, KuugaConstants.EMPTY, PinyinFormat.WITH_TONE_NUMBER);
        } catch (Exception e) {
            tempStr = pinYinStr;
            e.printStackTrace();
        }
        if (deleteBlank) {
            tempStr = tempStr.replaceAll("\\s*", KuugaConstants.EMPTY);
        }
        return tempStr;
    }

    /**
     * 根据字符串首字母排序
     *
     * @param sources sources
     * @return arrays
     */
    public static String[] sort(String[] sources) {
        if (ArrayUtil.isEmpty(sources)) {
            return sources;
        }
        // 定义一个中文排序器
        Comparator<Object> comparator = Collator.getInstance(Locale.CHINA);
        // 升序排列
        Arrays.sort(sources, comparator);
        return sources;
    }

    /**
     * 根据字符串首字母排序
     *
     * @param sources sources
     * @return list
     */
    public static List<String> sort(List<String> sources) {
        if (ListUtil.isEmpty(sources)) {
            return sources;
        }
        String[] strings = ListUtil.toArray(sources, new String[]{});
        String[] sort = sort(strings);
        return ListUtil.newArrayList(sort);
    }

    /**
     * 简体转换为繁体
     *
     * @param pinYinStr pinYinStr
     * @return str
     */
    public static String toTraditional(String pinYinStr) {
        return toTraditional(pinYinStr, false);
    }

    /**
     * 简体转换为繁体
     *
     * @param pinYinStr   pinYinStr
     * @param deleteBlank 删除空格
     * @return str
     */
    public static String toTraditional(String pinYinStr, boolean deleteBlank) {
        if (StringUtil.isEmpty(pinYinStr)) {
            return pinYinStr;
        }
        String tempStr;
        try {
            tempStr = ChineseHelper.convertToTraditionalChinese(pinYinStr);
        } catch (Exception e) {
            tempStr = pinYinStr;
            e.printStackTrace();
        }
        if (deleteBlank) {
            tempStr = tempStr.replaceAll("\\s*", KuugaConstants.EMPTY);
        }
        return tempStr;
    }

    /**
     * 繁体转换为简体
     *
     * @param pinYinStr pinYinStr
     * @return str
     */
    public static String toSimplified(String pinYinStr) {
        return toSimplified(pinYinStr, false);
    }

    /**
     * 繁体转换为简体
     *
     * @param pinYinStr   pinYinStr
     * @param deleteBlank 删除空格
     * @return str
     */
    public static String toSimplified(String pinYinStr, boolean deleteBlank) {
        if (StringUtil.isEmpty(pinYinStr)) {
            return pinYinStr;
        }
        String tempStr;
        try {
            tempStr = ChineseHelper.convertToSimplifiedChinese(pinYinStr);
        } catch (Exception e) {
            tempStr = pinYinStr;
            e.printStackTrace();
        }
        if (deleteBlank) {
            tempStr = tempStr.replaceAll("\\s*", KuugaConstants.EMPTY);
        }
        return tempStr;
    }

}
