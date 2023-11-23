package io.github.kuugasky.kuugatool.extra.pinyin;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ChineseConvertPinyinUtilTest {

    private String str;

    @BeforeEach
    void before() {
        str = "重慶 most input \r\n a b c#        d";
    }

    @Test
    void checkPinYin() {
        // 检查汉字是否为多音字
        System.out.println(ChineseConvertPinyinUtil.checkPinYin('重'));
    }

    @Test
    void convertToGetShortPinYin() {
        // 转换为每个汉字对应拼音首字母字符串
        System.out.println(ChineseConvertPinyinUtil.convertToGetShortPinYin(str, true));
    }

    @Test
    void convertToMarkPinYin() {
        // 转换为有声调的拼音字符串
        System.out.println(ChineseConvertPinyinUtil.convertToMarkPinYin(str, true, null));
    }

    @Test
    void convertToNumberPinYin() {
        // 转换为数字声调字符串
        System.out.println(ChineseConvertPinyinUtil.convertToNumberPinYin(str, true, null));
    }

    @Test
    void convertToSimplified() {
        // 繁体转换为简体
        System.out.println(ChineseConvertPinyinUtil.convertToSimplified(str, true));
    }

    @Test
    void convertToTonePinYin() {
        // 转换为不带音调的拼音字符串
        // System.out.println(ChineseConvertPinyinUtil.convertToTonePinYin(str, true, null));
        // System.out.println(ChineseConvertPinyinUtil.convertToTonePinYin(str, false, "¥"));
        System.out.println(ChineseConvertPinyinUtil.convertToTonePinYin("黄泽源 秦枫", false, "  "));
    }

    @Test
    void convertToTraditional() {
        // 简体转换为繁体
        System.out.println(ChineseConvertPinyinUtil.convertToTraditional(str, true));
        System.out.println(ChineseConvertPinyinUtil.convertToTraditional("黄泽源", true));
        System.out.println(ChineseConvertPinyinUtil.convertToTraditional("秦枫", true));
    }
}