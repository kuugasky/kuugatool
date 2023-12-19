package io.github.kuugasky.kuugatool.crypto;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * 常用加密工具类
 *
 * @author kuuga
 * @since 2021-04-24
 */
@Slf4j
public class EncryptUtil {

    private static final String SHA = "SHA";
    private static final String MD5 = "MD5";

    /**
     * 私有构造方法,将该工具类设为单例模式.
     */
    private EncryptUtil() {
    }

    /**
     * 用MD5算法进行加密
     *
     * @param str 需要加密的字符串
     * @return MD5加密后的结果
     */
    public static String encodeMd5(String str, String salt) {
        return encode(str + salt, MD5);
    }

    /**
     * 用SHA算法进行加密
     *
     * @param str 需要加密的字符串
     * @return SHA加密后的结果
     */
    public static String encodeSha(String str) {
        return encode(str, SHA);
    }

    /**
     * 用base64算法进行加密
     *
     * @param str 需要加密的字符串
     * @return base64加密后的结果
     */
    public static String encodeBase64(String str) {
        return Base64.getEncoder().encodeToString(str.getBytes());
    }

    /**
     * 用base64算法进行解密
     *
     * @param str 需要解密的字符串
     * @return base64解密后的结果
     */
    public static String decodeBase64(String str) {
        return new String(Base64.getDecoder().decode(str));
    }

    private static String encode(String str, String method) {
        MessageDigest mdInst;
        // 把密文转换成十六进制的字符串形式
        // 单线程用StringBuilder，速度快 多线程用StringBuffer，安全
        StringBuilder stringBuilder = new StringBuilder();
        try {
            // 获得MD5摘要算法的 MessageDigest对象
            mdInst = MessageDigest.getInstance(method);
            // 使用指定的字节更新摘要
            mdInst.update(str.getBytes());
            // 获得密文
            byte[] md = mdInst.digest();
            for (int b : md) {
                int tmp = b;
                if (tmp < 0) {
                    tmp += 256;
                }
                if (tmp < 16) {
                    stringBuilder.append("0");
                }
                stringBuilder.append(Integer.toHexString(tmp));
            }
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
        }
        return stringBuilder.toString();
    }

}
