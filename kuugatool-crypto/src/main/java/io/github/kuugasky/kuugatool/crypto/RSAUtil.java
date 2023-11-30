package io.github.kuugasky.kuugatool.crypto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.ArrayUtils;

import javax.crypto.Cipher;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
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
     * MAX_DECRYPT_BLOCK应等于密钥长度/8（1byte=8bit），所以当密钥位数为2048时，最大解密长度应为256.
     * 128 对应 1024，256对应2048
     */
    private static final int KEY_SIZE = 2048;
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

    /**
     * 默认种子
     */
    public static final String DEFAULT_SEED = "$%^*%^(KUUGA@0410)(ED47d784sde78";

    // - - - - - - - - - - - - - - - - - - - - RSA 生成秘钥对 - - - - - - - - - - - - - - - - - - - - //

    /**
     * 生成默认密钥
     *
     * @return 密钥对象
     */
    public static RSAKeyPair initKey() throws Exception {
        return initKey(DEFAULT_SEED);
    }

    /**
     * 生成密钥对：若seed为null，那么结果是随机的；若seed不为null且固定，那么结果也是固定的；
     *
     * @param seed 种子
     * @return 密钥对象
     */
    public static RSAKeyPair initKey(String seed) throws Exception {
        KeyPairGenerator keygen = KeyPairGenerator.getInstance(KEY_ALGORITHM);

        // 如果指定seed，那么secureRandom结果是一样的，所以生成的公私钥也永远不会变
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.setSeed(seed.getBytes());
        // Modulus size must range from 512 to 1024 and be a multiple of 64
        keygen.initialize(KEY_SIZE, secureRandom);

        // 生成一个密钥对，保存在keyPair中
        KeyPair keys = keygen.genKeyPair();
        PublicKey publicKey = keys.getPublic();
        PrivateKey privateKey = keys.getPrivate();

        // 将公钥和私钥保存到Map
        return new RSAKeyPair(publicKey, privateKey);
    }

    // - - - - - - - - - - - - - - - - - - - - RSA 密钥串转对象 - - - - - - - - - - - - - - - - - - - - //

    /**
     * 获取公钥 PublicKey 信息
     *
     * @param publicKeyStr 公钥文本 new String(encodeBase64(publicKey.getEncoded()))
     * @return 公钥对象
     */
    public static PublicKey getPublicKey(String publicKeyStr) throws Exception {
        byte[] publicKeys = decodeBase64(publicKeyStr);
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeys);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        return keyFactory.generatePublic(publicKeySpec);
    }

    /**
     * 获取私钥 PrivateKey 信息
     *
     * @param privateKeyStr 公钥文本 new String(encodeBase64(privateKey.getEncoded()))
     * @return 私钥对象
     */
    public static PrivateKey getPrivateKey(String privateKeyStr) throws Exception {
        byte[] privateKeys = decodeBase64(privateKeyStr);
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeys);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        return keyFactory.generatePrivate(privateKeySpec);
    }

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
        byte[] inputByte = decodeBase64(ciphertext.getBytes(charset));
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
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] plaintextBytes = plaintext.getBytes(charset);

        byte[] ciphertextBytes = null;
        /* 执行加密操作 */
        for (int i = 0; i < plaintextBytes.length; i += MAX_ENCRYPT_BLOCK) {
            byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(plaintextBytes, i, i + MAX_ENCRYPT_BLOCK));
            ciphertextBytes = ArrayUtils.addAll(ciphertextBytes, doFinal);
        }
        return encodeBase64Str(ciphertextBytes);
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
        byte[] inputByte = decodeBase64(ciphertext.getBytes(charset));
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        /* 执行解密操作 */
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < inputByte.length; i += MAX_DECRYPT_BLOCK) {
            byte[] doFinal = cipher.doFinal(ArrayUtils.subarray(inputByte, i, i + MAX_DECRYPT_BLOCK));
            sb.append(new String(doFinal, StandardCharsets.UTF_8));
        }
        return sb.toString();
    }

    // - - - - - - - - - - - - - - - - - - - - org.apache.commons.codec.binary.Base64 - - - - - - - - - - - - - - - - - - - - //

    /**
     * BASE64Encoder 加密
     *
     * @param binaryData 要加密的数据字节数组
     * @return 加密后的字节
     */
    @SuppressWarnings("unused")
    private static byte[] encodeBase64(byte[] binaryData) {
        return Base64.getEncoder().encode(binaryData);
    }

    private static String encodeBase64Str(byte[] binaryData) {
        return new String(Base64.getEncoder().encode(binaryData));
    }

    /**
     * BASE64Encoder 解密
     *
     * @param data 要解密的数据
     * @return 加密后的字符串
     */
    public static byte[] decodeBase64(String data) {
        return Base64.getDecoder().decode(data);
    }

    public static byte[] decodeBase64(byte[] dataByteArray) {
        return Base64.getDecoder().decode(dataByteArray);
    }

    @SuppressWarnings("unused")
    public static String decodeBase64Str(byte[] dataByteArray) {
        return new String(Base64.getDecoder().decode(dataByteArray));
    }

    // - - - - - - - - - - - - - - - - - - - - SIGN 签名，验签 - - - - - - - - - - - - - - - - - - - - //

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

        byte[] signatureBytes = decodeBase64(signature.getBytes());

        return publicSignature.verify(signatureBytes);
    }

    // - - - - - - - - - - - - - - - - - - - - SIGN 内部类 - - - - - - - - - - - - - - - - - - - - //

    @Data
    @AllArgsConstructor
    public static class RSAKeyPair {

        private PublicKey publicKey;
        private PrivateKey privateKey;

        private String publicKeyBase64Str;
        private String privateKeyBase64Str;

        public RSAKeyPair(PublicKey publicKey, PrivateKey privateKey) {
            this.publicKey = publicKey;
            this.privateKey = privateKey;
            if (null != publicKey) {
                this.publicKeyBase64Str = Base64.getEncoder().encodeToString(publicKey.getEncoded());
            }
            if (null != privateKey) {
                this.privateKeyBase64Str = Base64.getEncoder().encodeToString(privateKey.getEncoded());
            }
        }
    }

}
