package io.github.kuugasky.kuugatool.core.charset;

import io.github.kuugasky.kuugatool.core.lang.Assert;
import org.junit.jupiter.api.Test;

class CharUtilTest {

    @Test
    void isAscii() {
        Assert.isTrue(CharUtil.isAscii('A'));
    }

    @Test
    void isAsciiPrintable() {
        Assert.isTrue(CharUtil.isAsciiPrintable('A'));
    }

    @Test
    void isAsciiControl() {
        Assert.isTrue(CharUtil.isAsciiControl('\n'));
    }

    @Test
    void isLetter() {
        Assert.isTrue(CharUtil.isLetter('a'));
        Assert.isTrue(CharUtil.isLetter('A'));
        Assert.isFalse(CharUtil.isLetter('3'));
        Assert.isFalse(CharUtil.isLetter('-'));
        Assert.isFalse(CharUtil.isLetter('\n'));
    }

    @Test
    void isLetterUpper() {
        Assert.isFalse(CharUtil.isLetterUpper('a'));
        Assert.isTrue(CharUtil.isLetterUpper('A'));
    }

    @Test
    void isLetterLower() {
        Assert.isTrue(CharUtil.isLetterLower('a'));
        Assert.isFalse(CharUtil.isLetterLower('A'));
    }

    @Test
    void isNumber() {
        Assert.isFalse(CharUtil.isNumber('A'));
        Assert.isTrue(CharUtil.isNumber('1'));
    }

    @Test
    void isHexChar() {
        Assert.isTrue(CharUtil.isHexChar('1'));
    }

    @Test
    void isLetterOrNumber() {
        Assert.isTrue(CharUtil.isLetterOrNumber('1'));
        Assert.isTrue(CharUtil.isLetterOrNumber('A'));
        Assert.isTrue(CharUtil.isLetterOrNumber('a'));
    }

    @Test
    void testToString() {
        System.out.println(CharUtil.toString('a'));
    }

    @Test
    void isCharClass() {
        Assert.isTrue(CharUtil.isCharClass(Character.class));
        Assert.isTrue(CharUtil.isCharClass(char.class));
        Assert.isFalse(CharUtil.isCharClass(String.class));
    }

    @Test
    void isChar() {
        Assert.isTrue(CharUtil.isChar('1'));
        Assert.isFalse(CharUtil.isChar("1"));
    }

    @Test
    void isBlankChar() {
        Assert.isTrue(CharUtil.isBlankChar(' '));
    }

    @Test
    void testIsBlankChar() {
        Assert.isTrue(CharUtil.isBlankChar('\ufeff'));
        Assert.isTrue(CharUtil.isBlankChar('\u202a'));
    }

    @Test
    void isEmoji() {
        char[] chars = "🐮".toCharArray();
        for (char aChar : chars) {
            System.out.println(aChar);
            Assert.isTrue(CharUtil.isEmoji(aChar));
        }
    }

    @Test
    void isFileSeparator() {
        Assert.isTrue(CharUtil.isFileSeparator('/'));
        Assert.isTrue(CharUtil.isFileSeparator('\\'));
    }

    @Test
    void testEquals() {
        Assert.isTrue(CharUtil.equals('a', 'A', true));
        Assert.isFalse(CharUtil.equals('a', 'A', false));
    }

    @Test
    void getType() {
        System.out.println(CharUtil.getType('a'));
    }

    @Test
    void digit16() {
        System.out.println(CharUtil.digit16('a'));
    }

    @Test
    void print() {
        System.out.println("CharUtil.TAB ==> " + CharUtil.TAB);
        System.out.println("CharUtil.DOT ==> " + CharUtil.DOT);
        System.out.println("CharUtil.CR ==> " + CharUtil.CR);
        System.out.println("CharUtil.LF ==> " + CharUtil.LF);
        System.out.println("CharUtil.DASHED ==> " + CharUtil.DASHED);
        System.out.println("CharUtil.UNDERLINE ==> " + CharUtil.UNDERLINE);
        System.out.println("CharUtil.COMMA ==> " + CharUtil.COMMA);
        System.out.println("CharUtil.DELIM_START ==> " + CharUtil.DELIM_START);
        System.out.println("CharUtil.DELIM_END ==> " + CharUtil.DELIM_END);
        System.out.println("CharUtil.BRACKET_START ==> " + CharUtil.BRACKET_START);
        System.out.println("CharUtil.BRACKET_END ==> " + CharUtil.BRACKET_END);
        System.out.println("CharUtil.DOUBLE_QUOTES ==> " + CharUtil.DOUBLE_QUOTES);
        System.out.println("CharUtil.SINGLE_QUOTE ==> " + CharUtil.SINGLE_QUOTE);
        System.out.println("CharUtil.AMP ==> " + CharUtil.AMP);
        System.out.println("CharUtil.COLON ==> " + CharUtil.COLON);
        System.out.println("CharUtil.AT ==> " + CharUtil.AT);
    }

}