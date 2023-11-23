package io.github.kuugasky.kuugatool.core.constants;

import org.junit.jupiter.api.Test;

class KuugaCharConstantsTest {

    @Test
    void isAscii() {
        // 是否为ASCII字符，ASCII字符位于0~127之间
        System.out.println(KuugaCharConstants.isAscii('a'));
        System.out.println(KuugaCharConstants.isAscii('A'));
        System.out.println(KuugaCharConstants.isAscii('3'));
        System.out.println(KuugaCharConstants.isAscii('-'));
        System.out.println(KuugaCharConstants.isAscii('\n'));
        System.out.println(KuugaCharConstants.isAscii('&'));
    }

    @Test
    void isAsciiPrintable() {
        // 是否为可见ASCII字符，可见字符位于32~126之间
        System.out.println(KuugaCharConstants.isAsciiPrintable('a'));
        System.out.println(KuugaCharConstants.isAsciiPrintable('A'));
        System.out.println(KuugaCharConstants.isAsciiPrintable('3'));
        System.out.println(KuugaCharConstants.isAsciiPrintable('-'));
        System.out.println(KuugaCharConstants.isAsciiPrintable('\n'));
        System.out.println(KuugaCharConstants.isAsciiPrintable('&'));
    }

    @Test
    void isAsciiControl() {
        // 是否为ASCII控制符（不可见字符），控制符位于0~31和127
        System.out.println(KuugaCharConstants.isAsciiControl('a'));
        System.out.println(KuugaCharConstants.isAsciiControl('A'));
        System.out.println(KuugaCharConstants.isAsciiControl('3'));
        System.out.println(KuugaCharConstants.isAsciiControl('-'));
        System.out.println(KuugaCharConstants.isAsciiControl('\n'));
        System.out.println(KuugaCharConstants.isAsciiControl('&'));
    }

    @Test
    void isLetter() {
        // 判断是否为字母（包括大写字母和小写字母） 字母包括A~Z和a~z
        System.out.println(KuugaCharConstants.isLetter('a'));
        System.out.println(KuugaCharConstants.isLetter('A'));
        System.out.println(KuugaCharConstants.isLetter('3'));
        System.out.println(KuugaCharConstants.isLetter('-'));
        System.out.println(KuugaCharConstants.isLetter('\n'));
        System.out.println(KuugaCharConstants.isLetter('&'));
    }

    @Test
    void isLetterUpper() {
        // 判断是否为大写字母，大写字母包括A~Z
        System.out.println(KuugaCharConstants.isLetterUpper('a'));
        System.out.println(KuugaCharConstants.isLetterUpper('A'));
        System.out.println(KuugaCharConstants.isLetterUpper('3'));
        System.out.println(KuugaCharConstants.isLetterUpper('-'));
        System.out.println(KuugaCharConstants.isLetterUpper('\n'));
        System.out.println(KuugaCharConstants.isLetterUpper('&'));
    }

    @Test
    void isLetterLower() {
        // 判断是否为小写字母，大写字母包括a~z
        System.out.println(KuugaCharConstants.isLetterLower('a'));
        System.out.println(KuugaCharConstants.isLetterLower('A'));
        System.out.println(KuugaCharConstants.isLetterLower('3'));
        System.out.println(KuugaCharConstants.isLetterLower('-'));
        System.out.println(KuugaCharConstants.isLetterLower('\n'));
        System.out.println(KuugaCharConstants.isLetterLower('&'));
    }

    @Test
    void isNumber() {
        // 检查是否为数字字符，数字字符指0~9
        System.out.println(KuugaCharConstants.isNumber('a'));
        System.out.println(KuugaCharConstants.isNumber('A'));
        System.out.println(KuugaCharConstants.isNumber('3'));
        System.out.println(KuugaCharConstants.isNumber('-'));
        System.out.println(KuugaCharConstants.isNumber('\n'));
        System.out.println(KuugaCharConstants.isNumber('&'));
    }

    @Test
    void isHexChar() {
        // 是否为16进制规范的字符，判断是否为如下字符
        System.out.println(KuugaCharConstants.isHexChar('a'));
        System.out.println(KuugaCharConstants.isHexChar('A'));
        System.out.println(KuugaCharConstants.isHexChar('3'));
        System.out.println(KuugaCharConstants.isHexChar('-'));
        System.out.println(KuugaCharConstants.isHexChar('\n'));
        System.out.println(KuugaCharConstants.isHexChar('&'));
    }

    @Test
    void isLetterOrNumber() {
        // 是否为字母或数字，包括A~Z、a~z、0~9
        System.out.println(KuugaCharConstants.isLetterOrNumber('a'));
        System.out.println(KuugaCharConstants.isLetterOrNumber('A'));
        System.out.println(KuugaCharConstants.isLetterOrNumber('3'));
        System.out.println(KuugaCharConstants.isLetterOrNumber('-'));
        System.out.println(KuugaCharConstants.isLetterOrNumber('\n'));
        System.out.println(KuugaCharConstants.isLetterOrNumber('&'));
    }

    @Test
    void isCharClass() {
        // 给定类名是否为字符类
        System.out.println(KuugaCharConstants.isCharClass(Character.class));
        System.out.println(KuugaCharConstants.isCharClass(char.class));
        System.out.println(KuugaCharConstants.isCharClass(String.class));
    }

    @Test
    void isChar() {
        // 给定对象对应的类是否为字符类
        System.out.println(KuugaCharConstants.isChar(Character.class));
        System.out.println(KuugaCharConstants.isChar(char.class));
        System.out.println(KuugaCharConstants.isChar(String.class));
        System.out.println(KuugaCharConstants.isChar('c'));
        System.out.println(KuugaCharConstants.isChar("c"));
        System.out.println(KuugaCharConstants.isChar("C"));

    }

    @Test
    void isBlankChar() {
        // 是否空白符 空白符包括空格、制表符、全角空格和不间断空格
        System.out.println(KuugaCharConstants.isBlankChar(1));
        System.out.println(KuugaCharConstants.isBlankChar(2));
        System.out.println(KuugaCharConstants.isBlankChar(3));
    }

    @Test
    void testIsBlankChar() {
        // 是否空白符 空白符包括空格、制表符、全角空格和不间断空格
        System.out.println(KuugaCharConstants.isBlankChar('\n'));
        System.out.println(KuugaCharConstants.isBlankChar('\r'));
        System.out.println(KuugaCharConstants.isBlankChar('\t'));
    }

    @Test
    void isEmoji() {
        System.out.println(KuugaCharConstants.isEmoji("0x0".toCharArray()[0]));
    }

    @Test
    void isFileSeparator() {
        System.out.println(KuugaCharConstants.isFileSeparator('-'));
        System.out.println(KuugaCharConstants.isFileSeparator('/'));
        System.out.println(KuugaCharConstants.isFileSeparator('\\'));
    }

    @Test
    void testEquals() {
        System.out.println(KuugaCharConstants.equals('a', 'A', true));
    }

    @Test
    void getType() {
        // 获取字符类型
        System.out.println(KuugaCharConstants.getType(10));
    }

    @Test
    void digit16() {
        // 获取给定字符的16进制数值
        System.out.println(KuugaCharConstants.digit16(10));
    }

}