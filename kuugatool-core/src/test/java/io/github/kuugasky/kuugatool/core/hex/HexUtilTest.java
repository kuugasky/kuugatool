package io.github.kuugasky.kuugatool.core.hex;

import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;

class HexUtilTest {

    @Test
    void isHexNumber() {
        // 判断给定字符串是否为16进制数
        System.out.println(HexUtil.isHexNumber("0x1"));
    }

    @Test
    void encodeHex() {
        // 将字节数组转换为十六进制字符数组
        System.out.println(HexUtil.encodeHex("kuuga中华".getBytes()));
    }

    @Test
    void testEncodeHex() {
        // 将字节数组转换为十六进制字符数组
        System.out.println(HexUtil.encodeHex("kuuga中华", StandardCharsets.UTF_8));
    }

    @Test
    void testEncodeHex1() {
        // 将字节数组转换为十六进制字符数组
        System.out.println(HexUtil.encodeHex("kuuga中华".getBytes(), false));
    }

    @Test
    void encodeHexStr() {
        // 将字符串转换为十六进制字符串，结果为小写，默认编码是UTF-8
        System.out.println(HexUtil.encodeHexStr("kuuga中华"));
    }

    @Test
    void testEncodeHexStr() {
        // 将字节数组转换为十六进制字符串
        System.out.println(HexUtil.encodeHexStr("kuuga中华".getBytes()));
    }

    @Test
    void testEncodeHexStr1() {
        // 将字节数组转换为十六进制字符串
        System.out.println(HexUtil.encodeHexStr("kuuga中华".getBytes(), false));
    }

    @Test
    void testEncodeHexStr2() {
        // 将字符串转换为十六进制字符串，结果为小写
        System.out.println(HexUtil.encodeHexStr("kuuga中华", StandardCharsets.UTF_8));
    }

    @Test
    void decodeHexStr() {
        // 将字符串转换为十六进制字符串，结果为小写，默认编码是UTF-8
        String encodeHexStr = HexUtil.encodeHexStr("kuuga中华");
        System.out.println(HexUtil.decodeHexStr(encodeHexStr));
    }

    @Test
    void testDecodeHexStr() {
        // 将字符串转换为十六进制字符串，结果为小写，默认编码是UTF-8
        String encodeHexStr = HexUtil.encodeHexStr("kuuga中华");
        System.out.println(HexUtil.decodeHexStr(encodeHexStr, StandardCharsets.UTF_8));
    }

    @Test
    void testDecodeHexStr1() {
        // 将字符串转换为十六进制字符串，结果为小写，默认编码是UTF-8
        String encodeHexStr = HexUtil.encodeHexStr("kuuga中华");
        System.out.println(HexUtil.decodeHexStr(encodeHexStr.toCharArray(), StandardCharsets.UTF_8));
    }

    @Test
    void decodeHex() {
        // 将十六进制字符数组转换为字节数组
        String encodeHexStr = HexUtil.encodeHexStr("kuuga中华");
        System.out.println(Arrays.toString(HexUtil.decodeHex(encodeHexStr)));
        System.out.println(new String(Objects.requireNonNull(HexUtil.decodeHex(encodeHexStr))));
    }

    @Test
    void testDecodeHex() {
        // 将十六进制字符数组转换为字节数组
        String encodeHexStr = HexUtil.encodeHexStr("kuuga中华");
        System.out.println(Arrays.toString(HexUtil.decodeHex(encodeHexStr.toCharArray())));
        System.out.println(new String(Objects.requireNonNull(HexUtil.decodeHex(encodeHexStr))));
    }

    @Test
    void encodeColor() {
        // 将Color编码为Hex形式
        System.out.println(HexUtil.encodeColor(Color.BLACK));
        System.out.println(HexUtil.encodeColor(Color.WHITE));
        System.out.println(HexUtil.encodeColor(Color.BLUE));
        System.out.println(HexUtil.encodeColor(Color.RED));
    }

    @Test
    void testEncodeColor() {
        // 将Color编码为Hex形式
        System.out.println(HexUtil.encodeColor(Color.BLACK, "#"));
        System.out.println(HexUtil.encodeColor(Color.WHITE, "#"));
        System.out.println(HexUtil.encodeColor(Color.BLUE, "#"));
        System.out.println(HexUtil.encodeColor(Color.RED, "#"));
    }

    @Test
    void decodeColor() {
        String encodeColor = HexUtil.encodeColor(Color.BLACK, "#");
        // 将hex转换为color
        System.out.println(HexUtil.decodeColor(encodeColor));
    }

    @Test
    void toUnicodeHex() {
        // 将指定int值转换为Unicode字符串形式，常用于特殊字符（例如汉字）转Unicode形式 转换的字符串如果u后不足4位，则前面用0填充
        System.out.println(HexUtil.toUnicodeHex(12345));
    }

    @Test
    void testToUnicodeHex() {
        // 将指定int值转换为Unicode字符串形式，常用于特殊字符（例如汉字）转Unicode形式 转换的字符串如果u后不足4位，则前面用0填充
        System.out.println(HexUtil.toUnicodeHex('x'));
    }

    @Test
    void toHex() {
        // 转为16进制字符串
        System.out.println(HexUtil.toHex('x'));
    }

    @Test
    void testToHex() {
        // 转为16进制字符串
        System.out.println(HexUtil.toHex(123L));
    }

    @Test
    void appendHex() {
        // 将byte值转为16进制并添加到StringBuilder中
        StringBuilder builder = StringUtil.builder();
        byte aByte = "kuuga".getBytes()[0];
        HexUtil.appendHex(builder, aByte, true);
        System.out.println(builder);
    }

    @Test
    void toBigInteger() {
        // Hex（16进制）字符串转为BigInteger
        String encodeHexStr = HexUtil.encodeHexStr("kuuga");
        BigInteger Kuuga = HexUtil.toBigInteger(encodeHexStr);
        System.out.println(Kuuga);
    }

    @Test
    void format() {
        // 格式化Hex字符串，结果为每2位加一个空格
        String encodeHexStr = HexUtil.encodeHexStr("kuuga");
        System.out.println(HexUtil.format(encodeHexStr));
    }

}