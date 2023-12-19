package io.github.kuugasky.kuugatool.crypto;

import org.apache.commons.lang3.ArrayUtils;

import javax.crypto.Cipher;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;

/**
 * 提供RSA加密组件服务【非对称加密】【密钥交换算法】
 * <p>RSA加密数据最多不能超过 117 bytes
 * 功能：SHA256withRSA 工具类
 * 说明：
 *
 * @author Mr.tjm
 * @since 2020-5-20 11:25
 */
public class RSAUtil {

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 501;
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 512;

    /**
     * 不仅可以使用DSA算法，同样也可以使用RSA算法做数字签名
     */
    private static final String KEY_ALGORITHM = "RSA";
    /**
     * 签名算法
     */
    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";

    // - - - - - - - - - - - - - - - - - - - - RSA 加密 - - - - - - - - - - - - - - - - - - - - //

    /**
     * 公钥加密，指定 RSA 方式的 PublicKey 对象
     *
     * @param plaintext 明文
     * @param publicKey 公钥
     * @return ciphertext 密文
     */
    public static String encrypt(String plaintext, PublicKey publicKey) throws Exception {
        return encrypt(plaintext, publicKey, StandardCharsets.UTF_8);
    }

    /**
     * 公钥加密，指定 RSA 方式的 PublicKey 对象
     *
     * @param plaintext 明文
     * @param publicKey 公钥
     * @param charset   编码格式
     * @return ciphertext 密文
     */
    public static String encrypt(String plaintext, PublicKey publicKey, Charset charset) throws Exception {
        // RSA加密
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return Base64.getEncoder().encodeToString(cipher.doFinal(plaintext.getBytes(charset)));
    }

    // - - - - - - - - - - - - - - - - - - - - RSA 加密、解密 - - - - - - - - - - - - - - - - - - - - //

    /**
     * 私钥解密，指定 RSA 方式的 PrivateKey 对象
     *
     * @param ciphertext 密文
     * @param privateKey 私钥
     * @return plaintext 明文
     */
    public static String decrypt(String ciphertext, PrivateKey privateKey) throws Exception {
        return decrypt(ciphertext, privateKey, StandardCharsets.UTF_8);
    }

    /**
     * 私钥解密，指定 RSA 方式的 PrivateKey 对象
     *
     * @param ciphertext 密文
     * @param privateKey 私钥
     * @param charset    编码
     * @return plaintext 明文
     */
    public static String decrypt(String ciphertext, PrivateKey privateKey, Charset charset) throws Exception {
        // 64位解码加密后的字符串
        byte[] inputByte = Base64.getDecoder().decode((ciphertext.getBytes(charset)));
        // RSA解密
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(inputByte));
    }

    // - - - - - - - - - - - - - - - - - - - - 分段式加解密,避免因为需要加密的原字符串过长而出现错误 - - - - - - - - - - - - - - - - - - - - //

    /**
     * 分段式加密
     *
     * @param plaintext 明文
     * @param publicKey 公钥
     * @return ciphertext  密文
     * @throws Exception 分段式加密异常
     */
    public static String encryptSegmented(String plaintext, PublicKey publicKey) throws Exception {
        return encryptSegmented(plaintext, publicKey, StandardCharsets.UTF_8);
    }

    /**
     * 分段式加密
     *
     * @param plaintext 明文
     * @param publicKey 公钥
     * @param charset   编码
     * @return ciphertext  密文
     * @throws Exception 分段式加密异常
     */
    public static String encryptSegmented(String plaintext, PublicKey publicKey, Charset charset) throws Exception {
        /* 得到Cipher对象来实现对源数据的RSA加密 */
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] plaintextBytes = plaintext.getBytes(charset);

        byte[] ciphertextBytes = null;
        /* 执行加密操作 */
        for (int i = 0; i < plaintextBytes.length; i += MAX_ENCRYPT_BLOCK) {
            byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(plaintextBytes, i, i + MAX_ENCRYPT_BLOCK));
            ciphertextBytes = ArrayUtils.addAll(ciphertextBytes, doFinal);
        }
        return new String(Base64.getEncoder().encode(ciphertextBytes));
    }

    /**
     * 分段式解密
     *
     * @param ciphertext 密文
     * @param privateKey 私钥
     * @return plaintext 明文
     * @throws Exception 分段式解码异常
     */
    public static String decryptSegmented(String ciphertext, PrivateKey privateKey) throws Exception {
        return decryptSegmented(ciphertext, privateKey, StandardCharsets.UTF_8);
    }

    /**
     * 分段式解密
     *
     * @param ciphertext 密文
     * @param privateKey 私钥
     * @param charset    编码
     * @return plaintext 明文
     * @throws Exception 分段式解码异常
     */
    public static String decryptSegmented(String ciphertext, PrivateKey privateKey, Charset charset) throws Exception {
        // 64位解码加密后的字符串
        byte[] inputByte = Base64.getDecoder().decode(ciphertext.getBytes(charset));
        Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        /* 执行解密操作 */
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < inputByte.length; i += MAX_DECRYPT_BLOCK) {
            byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(inputByte, i, i + MAX_DECRYPT_BLOCK));
            sb.append(new String(doFinal, StandardCharsets.UTF_8));
        }
        return sb.toString();
    }

    // - - - - - - - - - - - - - - - - - - - - SIGN 签名 - - - - - - - - - - - - - - - - - - - - //

    /**
     * 服务端下发数据客户端，进行私钥签名，返回明文数据和签名
     *
     * @param plainText  明文
     * @param privateKey 私钥
     * @return 数据签名
     * @throws Exception 签名异常
     */
    public static String sign(String plainText, PrivateKey privateKey) throws Exception {
        return sign(plainText, privateKey, StandardCharsets.UTF_8);
    }

    /**
     * 服务端下发数据客户端，进行私钥签名，返回明文数据和签名
     *
     * @param plainText  明文
     * @param privateKey 私钥
     * @param charset    编码
     * @return 数据签名
     * @throws Exception 签名异常
     */
    public static String sign(String plainText, PrivateKey privateKey, Charset charset) throws Exception {
        Signature privateSignature = Signature.getInstance(SIGNATURE_ALGORITHM);
        privateSignature.initSign(privateKey);
        privateSignature.update(plainText.getBytes(charset));

        byte[] signature = privateSignature.sign();

        return Base64.getEncoder().encodeToString(signature);
    }

    // - - - - - - - - - - - - - - - - - - - - SIGN 验签 - - - - - - - - - - - - - - - - - - - - //

    /**
     * 客户端接收到服务端下发的明文数据和签名，进行签名验证，防止数据篡改
     *
     * @param plainText 明文
     * @param signature 数据签名
     * @param publicKey 公钥
     * @return 签名是否有效
     * @throws Exception 验签异常
     */
    public static boolean verify(String plainText, String signature, PublicKey publicKey) throws Exception {
        return verify(plainText, signature, publicKey, StandardCharsets.UTF_8);
    }

    /**
     * 客户端接收到服务端下发的明文数据和签名，进行签名验证，防止数据篡改
     *
     * @param plainText 明文
     * @param signature 数据签名
     * @param publicKey 公钥
     * @param charset   编码
     * @return 签名是否有效
     * @throws Exception 验签异常
     */
    public static boolean verify(String plainText, String signature, PublicKey publicKey, Charset charset) throws Exception {
        Signature publicSignature = Signature.getInstance(SIGNATURE_ALGORITHM);
        publicSignature.initVerify(publicKey);
        publicSignature.update(plainText.getBytes(charset));

        byte[] signatureBytes = Base64.getDecoder().decode(signature.getBytes());

        return publicSignature.verify(signatureBytes);
    }

}
