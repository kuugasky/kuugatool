package io.github.kuugasky.kuugatool.core.charset;

import io.github.kuugasky.kuugatool.core.string.StringUtil;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

/**
 * 字符集工具类
 *
 * @author kuuga
 * @since 2023-11-22
 */
public final class CharsetUtil {

    public static final Charset CHARSET_UTF_8 = StandardCharsets.UTF_8;

    /**
     * 定义私有构造函数来屏蔽这个隐式公有构造函数
     */
    private CharsetUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static final Set<Charset> CHARSETS = new HashSet<>();

    private static final String GBK = "GBK";

    static {
        CHARSETS.add(Charset.defaultCharset());
        CHARSETS.add(Charset.forName(GBK));
        CHARSETS.add(StandardCharsets.UTF_8);
        CHARSETS.add(StandardCharsets.UTF_16BE);
        CHARSETS.add(StandardCharsets.UTF_16LE);
        CHARSETS.add(StandardCharsets.UTF_16);
        CHARSETS.add(StandardCharsets.ISO_8859_1);
        CHARSETS.add(StandardCharsets.US_ASCII);
    }

    /**
     * 校验文本编码格式
     *
     * @param content 文本
     * @param charset 编码
     * @return boolean
     */
    public static boolean verifyCharsetEncode(String content, Charset charset) {
        return content.equals(new String(content.getBytes(charset)));
    }

    /**
     * 获取文本编码
     *
     * @param content 文本
     * @return 编码格式
     */
    public static String getHtmlEncode(String content) {
        for (Charset charset : CHARSETS) {
            if (verifyCharsetEncode(content, charset)) {
                return charset.name().toLowerCase();
            }
        }
        return null;
    }

    public static Charset forName(String charsetName) {
        return Charset.forName(charsetName);
    }

    /**
     * 转换字符串的字符集编码
     *
     * @param source      字符串
     * @param srcCharset  源字符集，默认ISO-8859-1
     * @param destCharset 目标字符集，默认UTF-8
     * @return 转换后的字符集
     */
    public static String convert(String source, String srcCharset, String destCharset) {
        return convert(source, Charset.forName(srcCharset), Charset.forName(destCharset));
    }

    /**
     * 转换字符串的字符集编码<br>
     * 当以错误的编码读取为字符串时，打印字符串将出现乱码。<br>
     * 此方法用于纠正因读取使用编码错误导致的乱码问题。<br>
     * 例如，在Servlet请求中客户端用GBK编码了请求参数，我们使用UTF-8读取到的是乱码，此时，使用此方法即可还原原编码的内容
     * <pre>
     * 客户端 -》 GBK编码 -》 Servlet容器 -》 UTF-8解码 -》 乱码
     * 乱码 -》 UTF-8编码 -》 GBK解码 -》 正确内容
     * </pre>
     *
     * @param source      字符串
     * @param srcCharset  源字符集，默认ISO-8859-1
     * @param destCharset 目标字符集，默认UTF-8
     * @return 转换后的字符集
     */
    public static String convert(String source, Charset srcCharset, Charset destCharset) {
        if (null == srcCharset) {
            srcCharset = StandardCharsets.ISO_8859_1;
        }

        if (null == destCharset) {
            destCharset = StandardCharsets.UTF_8;
        }

        if (StringUtil.isEmpty(source) || srcCharset.equals(destCharset)) {
            return source;
        }
        return new String(source.getBytes(srcCharset), destCharset);
    }

    /**
     * 系统字符集编码，如果是Windows，则默认为GBK编码，否则取 {@link CharsetUtil#defaultCharsetName()}
     *
     * @return 系统字符集编码
     * @see CharsetUtil#defaultCharsetName()
     * @since 3.1.2
     */
    public static String systemCharsetName() {
        return systemCharset().name();
    }

    /**
     * 系统字符集编码，如果是Windows，则默认为GBK编码，否则取 {@link CharsetUtil#defaultCharsetName()}
     *
     * @return 系统字符集编码
     * @see CharsetUtil#defaultCharsetName()
     * @since 3.1.2
     */
    public static Charset systemCharset() {
        return defaultCharset();
    }

    /**
     * 系统默认字符集编码
     *
     * @return 系统字符集编码
     */
    public static String defaultCharsetName() {
        return defaultCharset().name();
    }

    /**
     * 系统默认字符集编码
     *
     * @return 系统字符集编码
     */
    public static Charset defaultCharset() {
        return Charset.defaultCharset();
    }

}
