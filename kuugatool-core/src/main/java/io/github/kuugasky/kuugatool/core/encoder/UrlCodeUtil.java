package io.github.kuugasky.kuugatool.core.encoder;

import io.github.kuugasky.kuugatool.core.constants.KuugaCharsets;
import io.github.kuugasky.kuugatool.core.string.StringUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;

/**
 * Java URL 编码解码工具类
 * <a href="https://www.liaoxuefeng.com/wiki/1252599548343744/1304227703947297">URL编码解码</a>
 *
 * @author kuuga
 */
public final class UrlCodeUtil {

    /**
     * 定义私有构造函数来屏蔽这个隐式公有构造函数
     */
    private UrlCodeUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private final static String DEFAULT_CHARSET = KuugaCharsets.UTF_8;

    /**
     * 编码【默认utf-8】
     *
     * @param str 字符串
     * @return 编码后的字符串
     */
    public static String encode(String str) {
        if (StringUtil.isEmpty(str)) {
            return str;
        }
        return URLEncoder.encode(str, Charset.forName(DEFAULT_CHARSET));
    }

    /**
     * 编码
     *
     * @param str     字符串
     * @param charset 编码
     * @return 编码后的字符串
     * @throws UnsupportedEncodingException 不支持的编码异常
     */
    public static String encode(String str, String charset) throws UnsupportedEncodingException {
        if (StringUtil.isEmpty(str)) {
            return str;
        }
        return URLEncoder.encode(str, charset);
    }

    /**
     * 编码
     *
     * @param str     字符串
     * @param charset 编码
     * @return 编码后的字符串
     */
    public static String encode(String str, Charset charset) {
        if (StringUtil.isEmpty(str)) {
            return str;
        }
        return URLEncoder.encode(str, charset);
    }

    /**
     * 解码【默认utf-8】
     *
     * @param str 字符串
     * @return 解码后的字符串
     */
    public static String decode(String str) {
        if (StringUtil.isEmpty(str)) {
            return str;
        }
        return URLDecoder.decode(str, Charset.forName(DEFAULT_CHARSET));
    }

    /**
     * 解码
     *
     * @param str     字符串
     * @param charset 编码
     * @return 解码后的字符串
     * @throws UnsupportedEncodingException 不支持的编码异常
     */
    public static String decode(String str, String charset) throws UnsupportedEncodingException {
        if (StringUtil.isEmpty(str)) {
            return str;
        }
        return URLDecoder.decode(str, charset);
    }

    /**
     * 解码
     *
     * @param str     字符串
     * @param charset 编码
     * @return 解码后的字符串
     */
    public static String decode(String str, Charset charset) {
        if (StringUtil.isEmpty(str)) {
            return str;
        }
        return URLDecoder.decode(str, charset);
    }

}
