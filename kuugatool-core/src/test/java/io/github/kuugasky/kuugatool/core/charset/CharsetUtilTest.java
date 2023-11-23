package io.github.kuugasky.kuugatool.core.charset;

import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * CharsetUtilTest
 *
 * @author kuuga
 */
public class CharsetUtilTest {

    public static void main(String[] args) {
        System.out.println(CharsetUtil.verifyCharsetEncode("你好", StandardCharsets.UTF_8));
        System.out.println(CharsetUtil.verifyCharsetEncode("你好", Charset.forName("GBK")));
        System.out.println(CharsetUtil.getHtmlEncode("你好"));
    }

    @Test
    void verifyCharsetEncode() {
        boolean isUtf8 = CharsetUtil.verifyCharsetEncode("你好 world", CharsetUtil.CHARSET_UTF_8);
        System.out.println(isUtf8);
    }

    @Test
    void getHtmlEncode() {
        String encode = CharsetUtil.getHtmlEncode("你好 world");
        System.out.println(encode);
    }

    @Test
    void forName() {
        Charset charset = CharsetUtil.forName("utf8");
        System.out.println(charset);
        Charset charset1 = CharsetUtil.forName("utf-8");
        System.out.println(charset1);
    }

    @Test
    void convert() {
        String convert = CharsetUtil.convert("你好 world", "utf8", "gbk");
        System.out.println(convert);
    }

    @Test
    void testConvert() {
        String convert = CharsetUtil.convert("你好 world", Charset.defaultCharset(), Charset.forName("gbk"));
        System.out.println(convert);
    }

    @Test
    void systemCharsetName() {
        String systemCharsetName = CharsetUtil.systemCharsetName();
        System.out.println(systemCharsetName);
    }

    @Test
    void systemCharset() {
        Charset systemCharset = CharsetUtil.systemCharset();
        System.out.println(systemCharset);
    }

    @Test
    void defaultCharsetName() {
        String defaultCharsetName = CharsetUtil.defaultCharsetName();
        System.out.println(defaultCharsetName);
    }

    @Test
    void defaultCharset() {
        Charset defaultCharset = CharsetUtil.defaultCharset();
        System.out.println(defaultCharset);
    }
}
