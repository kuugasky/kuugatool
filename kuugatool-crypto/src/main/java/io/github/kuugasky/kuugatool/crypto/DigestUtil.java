package io.github.kuugasky.kuugatool.crypto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * DigestUtil
 * 哈希算法（Hash）又称摘要算法（Digest），它的作用是：对任意一组输入数据进行计算，得到一个固定长度的输出摘要。
 * <a href="https://www.liaoxuefeng.com/wiki/1252599548343744/1304227729113121">...</a>
 * <p>
 * 哈希算法可用于验证数据完整性，具有防篡改检测的功能；
 * 常用的哈希算法有MD5、SHA-1等；
 * 用哈希存储口令时要考虑彩虹表攻击，可以采用明文前后加上自定义文本。
 *
 * @author kuuga
 * @since 2021/6/7
 */
public class DigestUtil {

    /**
     * Java标准库提供了常用的哈希算法，并且有一套统一的接口。如MD5
     *
     * @param plaintext 明文
     * @return 密文
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     */
    public static String md5(String plaintext) throws NoSuchAlgorithmException {
        // 创建一个MessageDigest实例:
        MessageDigest md = MessageDigest.getInstance("MD5");
        // 反复调用update输入数据:
        md.update(plaintext.getBytes(StandardCharsets.UTF_8));
        // 16 bytes: 68e109f0f40ca72a15e05cc22786f8e6
        byte[] result = md.digest();
        return new BigInteger(1, result).toString(16);
    }

    /**
     * SHA-1也是一种哈希算法，它的输出是160 bits，即20字节。
     * SHA-1是由美国国家安全局开发的，SHA算法实际上是一个系列，包括SHA-0（已废弃）、SHA-1、SHA-256、SHA-512等。
     * <p>
     * 在Java中使用SHA-1，和MD5完全一样，只需要把算法名称改为"SHA-1"：
     *
     * @param plaintext 明文
     * @return 密文
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     */
    public static String sha1(String plaintext) throws NoSuchAlgorithmException {
        // 创建一个MessageDigest实例:
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        // 反复调用update输入数据:
        md.update(plaintext.getBytes(StandardCharsets.UTF_8));
        byte[] result = md.digest();
        return new BigInteger(1, result).toString(16);
    }

    public static String sha1(String plaintext, ShaAlgorithmEnum shaAlgorithmEnum) throws NoSuchAlgorithmException {
        // 创建一个MessageDigest实例:
        MessageDigest md = MessageDigest.getInstance(shaAlgorithmEnum.algorithm);
        // 反复调用update输入数据:
        md.update(plaintext.getBytes(StandardCharsets.UTF_8));
        byte[] result = md.digest();
        return new BigInteger(1, result).toString(16);
    }

    @AllArgsConstructor
    enum ShaAlgorithmEnum {
        /**
         *
         */
        SHA_1("SHA-1"),
        SHA_256("SHA-256"),
        SHA_512("SHA-512");
        @Getter
        private final String algorithm;
    }

}
