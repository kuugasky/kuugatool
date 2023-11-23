package io.github.kuugasky.kuugatool.extra.pinyin;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class PinyinUtilTest {

    @Test
    void testIsMultiToneWord() {
        // 检查汉字是否为多音字
        System.out.println(PinyinUtil.isMultiToneWord('长'));
    }

    @Test
    void testToShortPinYin() {
        // 转换为不带音调的短拼音字符串
        System.out.println(PinyinUtil.toShortPinYin("长安"));
        System.out.println(PinyinUtil.toShortPinYin("深圳"));
    }

    @Test
    void testTestToShortPinYin() {
        // 转换为不带音调的短拼音字符串
        System.out.println(PinyinUtil.toShortPinYin("长 安", true));
    }

    @Test
    void testToFullPinYin() {
        // 转换为不带音调的全拼音字符串
        System.out.println(PinyinUtil.toFullPinYin("长安"));
    }

    @Test
    void testTestToFullPinYin() {
        // 转换为不带音调的全拼音字符串
        System.out.println(PinyinUtil.toFullPinYin("长 安", true));
    }

    @Test
    void testToMarkPinYin() {
        // 转换为有声调的拼音字符串
        System.out.println(PinyinUtil.toMarkPinYin("长安"));
    }

    @Test
    void testTestToMarkPinYin() {
        // 转换为有声调的拼音字符串
        System.out.println(PinyinUtil.toMarkPinYin("长 安", true));
    }

    @Test
    void testToNumberPinYin() {
        // 转换为数字声调字符串
        System.out.println(PinyinUtil.toNumberPinYin("长安"));
    }

    @Test
    void testTestToNumberPinYin() {
        // 转换为数字声调字符串
        System.out.println(PinyinUtil.toNumberPinYin("长 安", true));
    }

    @Test
    void testSort() {
        // 根据字符串首字母排序
        System.out.println(PinyinUtil.sort(ListUtil.newArrayList("测试", "中国", "阿里巴巴")));
    }

    @Test
    void testTestSort() {
        // 根据字符串首字母排序
        System.out.println(Arrays.toString(PinyinUtil.sort(new String[]{"测试", "中国", "阿里巴巴"})));
    }

    @Test
    void testToTraditional() {
        // 简体转换为繁体
        System.out.println(PinyinUtil.toTraditional("长安"));
    }

    @Test
    void testTestToTraditional() {
        // 简体转换为繁体
        System.out.println(PinyinUtil.toTraditional("长 安", true));
    }

    @Test
    void testToSimplified() {
        // 繁体转换为简体
        System.out.println(PinyinUtil.toSimplified("商辦租賃"));
    }

    @Test
    void testTestToSimplified() {
        // 繁体转换为简体
        System.out.println(PinyinUtil.toSimplified("商辦 租賃", true));
    }

    @Test
    void toShortPinYinToLowerCase() {
        // 转换为不带音调的短拼音字符串，并转小写
        System.out.println(PinyinUtil.toShortPinYinToLowerCase("看房 网"));
        System.out.println(PinyinUtil.toShortPinYinToLowerCase("看房 网", true));
    }

    @Test
    void toShortPinYinToUpperCase() {
        // 转换为不带音调的短拼音字符串，并转大写
        System.out.println(PinyinUtil.toShortPinYinToUpperCase("看房 网"));
        System.out.println(PinyinUtil.toShortPinYinToUpperCase("看房 网", true));
    }

    @Test
    void toFullPinYinToLowerCase() {
        // 转换为不带音调的全拼音字符串，并转小写
        System.out.println(PinyinUtil.toFullPinYinToLowerCase("看房 网"));
        System.out.println(PinyinUtil.toFullPinYinToLowerCase("看房 网", true));
    }

    @Test
    void toFullPinYinToUpperCase() {
        // 转换为不带音调的全拼音字符串，并转大写
        System.out.println(PinyinUtil.toFullPinYinToUpperCase("看房 网"));
        System.out.println(PinyinUtil.toFullPinYinToUpperCase("看房 网", true));
    }

    @Test
    void getFirstPinyinOfUpperCase() {
        System.out.println(PinyinUtil.getFirstPinyinOfUpperCase("  看房 网"));
        System.out.println(PinyinUtil.toShortPinYinToUpperCase("  看房 网", true));
    }

}