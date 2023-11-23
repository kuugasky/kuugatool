package io.github.kuugasky.kuugatool.core.io.bytes;

import io.github.kuugasky.kuugatool.core.string.StringUtil;

import java.nio.charset.Charset;

/**
 * byte size util
 *
 * @author kuuga
 * @date 2017-09-14
 */
public final class ByteUtil {

    /**
     * 定义私有构造函数来屏蔽这个隐式公有构造函数
     */
    private ByteUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判断输入的字节数组是否为空
     *
     * @return boolean 空则返回true,非空则false
     */
    public static boolean isEmpty(byte[] bytes) {
        return null == bytes || 0 == bytes.length;
    }

    /**
     * 判断输入的字节数组是否为非空
     *
     * @return boolean 非空则返回true,空则false
     */
    public static boolean hasItem(byte[] bytes) {
        return !isEmpty(bytes);
    }

    /**
     * 字符串转为字节数组
     * 若想自己指定字符集,可以使用<code>getBytes(String str, String charset)</code>方法
     */
    public static byte[] getBytes(String data) {
        return getBytes(data, Charset.defaultCharset());
    }

    /**
     * 字符串转为字节数组
     * 如果系统不支持所传入的<code>charset</code>字符集,则按照系统默认字符集进行转换
     */
    public static byte[] getBytes(String data, Charset charset) {
        if (StringUtil.isEmpty(data)) {
            return new byte[]{};
        }
        return data.getBytes(charset);
    }

    /**
     * 字符串转为字节数组
     * 如果系统不支持所传入的<code>charset</code>字符集,则按照系统默认字符集进行转换
     */
    public static byte[] getBytes(String data, String charsetStr) {
        if (StringUtil.isEmpty(data)) {
            return new byte[]{};
        }
        if (StringUtil.hasText(charsetStr)) {
            return data.getBytes(Charset.forName(charsetStr));
        } else {
            return data.getBytes(Charset.defaultCharset());
        }
    }

}