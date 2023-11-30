package io.github.kuugasky.kuugatool.crypto;

import java.nio.charset.Charset;
import java.security.MessageDigest;

/**
 * 安全哈希算法【安全散列算法】- 数字摘要（Secure Hash Algorithm）主要适用于数字签名标准
 * <p>
 * 原理：
 * SHA-1是一种数据加密算法，该算法的思想是接收一段明文，然后以一种不可逆的方式将它转换成一段（通常更小）密文，也可以简单的理解为取一串输入码（称为预映射或信息），
 * 并把它们转化为长度较短、位数固定的输出序列即散列值（也称为信息摘要或信息认证代码）的过程。类似MD5加密
 * <p>
 * SHA-1是基于MD4算法的,现在已成为公认的最安全的散列算法之一,并被广泛使用。 SHA-1算法生成的摘要信息的长度为160位,
 * 由于生成的摘要信息更长,运算的过程更加复杂, 在相同的硬件上,SHA-1的运行速度比MD5更慢,但是也更为安全。
 *
 * @author kuuga
 * @since 2018-03-30
 */
public final class SHA1Util {

    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * Takes the raw bytes from the digest and formats them correct.
     *
     * @param bytes the raw bytes from the digest.
     * @return the formatted bytes.
     */
    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        // 把密文转换成十六进制的字符串形式
        for (byte aByte : bytes) {
            buf.append(HEX_DIGITS[(aByte >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[aByte & 0x0f]);
        }
        return buf.toString();
    }

    public static String encode(String str) {
        if (str == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.update(str.getBytes(Charset.defaultCharset()));
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}