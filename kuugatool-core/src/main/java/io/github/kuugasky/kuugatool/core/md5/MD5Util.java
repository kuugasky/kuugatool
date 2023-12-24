package io.github.kuugasky.kuugatool.core.md5;

import io.github.kuugasky.kuugatool.core.constants.KuugaConstants;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.security.MessageDigest;

/**
 * MD5的算法（信息摘要算法5）- 数字摘要
 * <p>
 * 数字摘要也称为消息摘要,它是一个唯一对应一个消息或文本的固定长度的值,它由一个单向Hash函数对消息进行计算而产生。
 * 如果消息在传递的途中改变了,接收者通过对收到消息采用相同的Hash重新计算,新产生的摘要与原摘要进行比较,就可知道消息是否被篡改了,因此消息摘要能够验证消息的完整性。
 * 消息摘要采用单向Hash函数将需要计算的内容"摘要"成固定长度的串,这个串亦称为数字指纹。这个串有固定的长度,且不同的明文摘要成密文,其结果总是不同的(相对的),而同样的明文其摘要必定一致。
 * 这样这串摘要便可成为验证明文是否是"真身"的"指纹"了。
 * <p>
 * MD5即Message Digest Algorithm 5(信息摘要算法5),是数字摘要算法一种实现,用于确保信息传输完整性和一致性,摘要长度为128位。
 * MD5由MD4、MD3、MD2改进而来,主要增强算法复杂度和不可逆性,该算法因其普遍、稳定、快速的特点,在产业界得到了极为广泛的使用,目前主流的编程语言普遍都已有MD5算法实现。
 * <p>
 * 传入参数：一个字节数组 传出参数：字节数组的 MD5 结果字符串
 * <p>
 * org.apache.commons.codec.digest.DigestUtils
 * md5Hex：MD5加密，返回32位字符串
 * sha1Hex：SHA-1加密
 * sha256Hex：SHA-256加密
 * sha512Hex：SHA-512加密
 * md5：MD5加密，返回16位字符串
 *
 * @author kuuga
 */
public final class MD5Util {

    private final static Logger logger = LoggerFactory.getLogger(MD5Util.class);

    /**
     * 用来将字节转换成 16 进制表示的字符
     */
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String getMd5ToUpperCase(String source) {
        String md5 = getMd5(source);
        return StringUtil.hasText(md5) ? md5.toUpperCase() : md5;
    }

    public static String getMd5ToUpperCase(byte[] source) {
        String md5 = getMd5(source);
        return StringUtil.hasText(md5) ? md5.toUpperCase() : md5;
    }

    public static String getMd5(String source) {
        return getMd5(source.getBytes(Charset.defaultCharset()));
    }

    public static String getMd5(byte[] source) {
        if (source == null || source.length == 0) {
            return StringUtil.EMPTY;
        }
        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(source);
            // MD5 的计算结果是一个 128 位的长整数
            byte[] tmp = md.digest();
            // 用字节表示就是 16 个字节
            // 每个字节用 16 进制表示的话，使用两个字符
            // 所以表示成 16 进制需要 32 个字符
            char[] str = new char[16 * 2];
            // 表示转换结果中对应的字符位置
            int k = 0;
            // 从第一个字节开始，对 MD5 的每一个字节
            for (int i = 0; i < KuugaConstants.SIXTEEN; i++) {
                // 转换成 16 进制字符的转换
                // 取第 i 个字节
                byte byte0 = tmp[i];
                // 取字节中高 4 位的数字转换
                str[k++] = HEX_DIGITS[byte0 >>> 4 & 0xf];
                // >>>
                // 为逻辑右移，将符号位一起右移
                // 取字节中低 4 位的数字转换
                str[k++] = HEX_DIGITS[byte0 & 0xf];
            }
            // 换后的结果转换为字符串
            result = new String(str);
        } catch (Exception e) {
            logger.error("MD5加密异常:{}", e.getMessage());
        }
        return result;
    }

}