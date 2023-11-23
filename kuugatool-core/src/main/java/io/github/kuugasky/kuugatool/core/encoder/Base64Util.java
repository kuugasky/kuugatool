package io.github.kuugasky.kuugatool.core.encoder;

import io.github.kuugasky.kuugatool.core.constants.KuugaCharsets;
import io.github.kuugasky.kuugatool.core.string.StringUtil;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Base64;

/**
 * Java Base64 编码解码工具类
 *
 * @author kuuga
 */
public final class Base64Util {

    /**
     * 定义私有构造函数来屏蔽这个隐式公有构造函数
     */
    private Base64Util() {
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
        return encode(str.getBytes(Charset.forName(DEFAULT_CHARSET)));
    }

    public static String encode(byte[] strBytes) {
        return Base64.getEncoder().encodeToString(strBytes);
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
        return Base64.getEncoder().encodeToString(str.getBytes(charset));
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
        return new String(Base64.getEncoder().encode(str.getBytes(charset)), charset);
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
        return new String(Base64.getDecoder().decode(str.getBytes(Charset.forName(DEFAULT_CHARSET))));
    }

    /**
     * 解码【默认utf-8】
     *
     * @param str 字符串
     * @return 解码后的字符串
     */
    public static byte[] decodeToBytes(String str) {
        if (StringUtil.isEmpty(str)) {
            return null;
        }
        return Base64.getDecoder().decode(str.getBytes(Charset.forName(DEFAULT_CHARSET)));
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
        return new String(Base64.getDecoder().decode(str.getBytes(charset)), charset);
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
        return new String(Base64.getDecoder().decode(str.getBytes(charset)), charset);
    }

}
