package io.github.kuugasky.kuugatool.core.string;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class StringEscapeUtilTest {

    @Test
    void escapeSql() {
        String sql = "1' or '1'='1";
        System.out.println("防SQL注入:" + StringEscapeUtil.escapeSql(sql)); // 防SQL注入
    }

    @Test
    void escapeHtml() {
        System.out.println("转义HTML:" + StringEscapeUtil.escapeHtml("<font>chen磊  xing</font>"));    // 转义HTML,注意汉字
    }

    @Test
    void unescapeHtml() {
        System.out.println("反转义HTML:" + StringEscapeUtil.unescapeHtml("&lt;font&gt;chen&#30922;  xing&lt;/font&gt;"));    // 转义HTML,注意汉字
    }

    @Test
    void escapeJava() {
        System.out.println("转成Unicode编码：" + StringEscapeUtil.escapeJava("陈磊兴"));     // 转义成Unicode编码
    }

    @Test
    void unescapeJava() {
        System.out.println("反转成：" + StringEscapeUtil.unescapeJava("\\u9648\\u78CA\\u5174"));     // 转义成Unicode编码
    }

    @Test
    void escapeJavaScript() {
        System.out.println("转义JavaScript：" + StringEscapeUtil.escapeJavaScript("<script src=''></script>"));
    }

    @Test
    void unescapeJavaScript() {
        System.out.println("转义JavaScript：" + StringEscapeUtil.unescapeJavaScript("<script src=\\'\\'><\\/script>"));
    }

    @Test
    void escapeXml() {
        System.out.println("转义XML：" + StringEscapeUtil.escapeXml("<name>陈磊兴</name>"));   // 转义xml
    }

    @Test
    void unescapeXml() {
        System.out.println("反转义XML：" + StringEscapeUtil.unescapeXml("&lt;name&gt;&#38472;&#30922;&#20852;&lt;/name&gt;"));   // 转义xml
    }

    @Test
    void escapeCsv() {
        assertEquals("foo.bar", StringEscapeUtil.escapeCsv("foo.bar"));
        assertEquals("\"foo,bar\"", StringEscapeUtil.escapeCsv("foo,bar"));
        assertEquals("\"foo\nbar\"", StringEscapeUtil.escapeCsv("foo\nbar"));
        assertEquals("\"foo\rbar\"", StringEscapeUtil.escapeCsv("foo\rbar"));
        assertEquals("\"foo\"\"bar\"", StringEscapeUtil.escapeCsv("foo\"bar"));
        assertEquals("foo\uD84C\uDFB4bar", StringEscapeUtil.escapeCsv("foo\uD84C\uDFB4bar"));
        assertEquals(StringUtil.EMPTY, StringEscapeUtil.escapeCsv(StringUtil.EMPTY));
        assertNull(StringEscapeUtil.escapeCsv(null));
    }

    @Test
    void unescapeCsv() {
    }
}