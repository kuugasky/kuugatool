/*
 * Copyright 2016-2019 yoara
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.kuugasky.kuugatool.crypto;

import io.github.kuugasky.kuugatool.crypto.exception.DecryptionException;
import io.github.kuugasky.kuugatool.crypto.exception.EncryptionException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SystemUtils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * AES对称加密和解密【对称加密】
 * 高级加密标准：
 * 是美国联邦政府采用的一种对称加密标准,这个标准用来替代原先的DES算法,已经广为全世界所使用,已然成为对称加密算法中最流行的算法之一。
 * AES算法作为新一代的数据加密标准汇聚了强安全性、高性能、高效率、易用和灵活等优点,设计有三个密钥长度:128,192,256位,比DES算法的加密强度更高,更为安全。
 * <p>
 * 可自定义加密规则：encodeRules
 * <p>
 * 在对称加密算法中,数据发送方将明文(原始数据)和加密密钥一起经过特殊加密算法处理后,生成复杂的加密密文进行发送,数据接收方收到密文后,若想读取原文,
 * 则需要使用加密使用的密钥及相同算法的逆算法对加密的密文进行解密,才能使其恢复成可读明文。
 *
 * @author kuuga
 */
@Slf4j
public final class AESUtil {

    /**
     * 密钥算法aes
     */
    private static final String KEY_ALGORITHM_AES = "AES";
    /**
     * 字符编码
     */
    private static final String CHARACTER = "UTF-8";
    /**
     * 算法
     */
    private static final String ALGORITHM = "SHA1PRNG";

    /**
     * AES加密秘钥位数
     */
    @Getter
    public enum AESSize {
        /**
         * 支持三个密钥长度:128,192,256位<br>
         * 当我们把密钥定为大于128时（即192或256）时，就会出现这个错误：Illegal key size or default parameters 这是因为Java默认不能处理这么长的key。<br>
         * 因为美国的出口限制，Sun通过权限文件（local_policy.jar、US_export_policy.jar）做了相应限制。<br>
         * <a href="https://blog.csdn.net/qq1940879801/article/details/51505631">...</a>
         */
        SIZE_128(128), SIZE_192(192), SIZE_256(256);

        AESSize(int size) {
            this.size = size;
        }

        private final int size;

    }

    /**
     * 获得AES秘钥字节数组
     */
    public static byte[] makeKey() {
        return makeKey(AESSize.SIZE_128, null);
    }

    /**
     * 获得AES秘钥字节数组，随机种子并不确定随机序列
     *
     * @param size 秘钥块尺寸
     * @param seed 随机种子
     */
    @Deprecated
    public static byte[] makeKey(AESSize size, String seed) {
        KeyGenerator keyGen = null;
        try {
            keyGen = KeyGenerator.getInstance(KEY_ALGORITHM_AES);
            int switchSize = size.getSize();
            if (seed == null) {
                keyGen.init(switchSize);
            } else {
                keyGen.init(switchSize, new SecureRandom(seed.getBytes(CHARACTER)));
            }
        } catch (Exception ignored) {
        }
        if (null == keyGen) {
            return null;
        }
        SecretKey secretKey = keyGen.generateKey();
        return secretKey.getEncoded();
    }

    /**
     * 用字符串生成的私钥
     *
     * @param secretKeyStr 私钥字符串
     * @param isHex        是否十六进制
     * @return SecretKeySpec
     */
    public static SecretKeySpec getAesSecretKey(String secretKeyStr, boolean isHex) throws Exception {
        byte[] keyByte = isHex ? HexUtil.hexStringToBytes(secretKeyStr) : secretKeyStr.getBytes(CHARACTER);
        return new SecretKeySpec(keyByte, KEY_ALGORITHM_AES);
    }

    /**
     * 用字符串生成的私钥
     *
     * @param keyByte keyByte
     * @return SecretKeySpec
     */
    public static SecretKeySpec getAesSecretKey(byte[] keyByte) {
        return new SecretKeySpec(keyByte, KEY_ALGORITHM_AES);
    }

    /**
     * 对数据加密，默认UTF-8 加密
     *
     * @param message 数据
     * @param key     密钥规范
     * @return 密文字节数组
     */
    public static byte[] encrypt(String message, SecretKeySpec key) throws Exception {
        Cipher enCipher = Cipher.getInstance(KEY_ALGORITHM_AES);
        enCipher.init(Cipher.ENCRYPT_MODE, key);
        return enCipher.doFinal(message.getBytes(CHARACTER));
    }

    /**
     * 对数据解密
     *
     * @param encryptStrByteArray 密文字节数组
     * @param key                 密钥规范
     * @return String
     */
    public static String decrypt(byte[] encryptStrByteArray, SecretKeySpec key) throws Exception {
        Cipher deCipher = Cipher.getInstance(KEY_ALGORITHM_AES);
        deCipher.init(Cipher.DECRYPT_MODE, key);
        return new String(deCipher.doFinal(encryptStrByteArray), CHARACTER);
    }

    /**
     * 对数据解密，默认UTF-8 加密
     *
     * @param encryptStr 密文
     * @param key        密钥规范
     * @param isHex      是否16进制
     * @return String
     */
    public static String decrypt(String encryptStr, SecretKeySpec key, boolean isHex) throws Exception {
        byte[] signCode = isHex ? HexUtil.hexStringToBytes(encryptStr) : encryptStr.getBytes(CHARACTER);
        return decrypt(signCode, key);
    }

    // ===============================================================================================================================
    // 根据自定义字符串使用RSA进行加密解密  ==============================================================================================
    // ===============================================================================================================================

    private static KeyGenerator keygen;

    static {
        try {
            // 初始化密钥生成器
            keygen = KeyGenerator.getInstance(KEY_ALGORITHM_AES);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * 构造密钥生成器，指定为AES算法,不区分大小写
     *
     * @param encodeRules 加密规则
     * @throws NoSuchAlgorithmException 没有这样的算法异常
     */
    private static void initKeyGenerator(String encodeRules) throws NoSuchAlgorithmException {
        if (SystemUtils.IS_OS_WINDOWS) {
            keygen.init(AESSize.SIZE_128.size, new SecureRandom(encodeRules.getBytes(Charset.defaultCharset())));
        } else {
            SecureRandom random = SecureRandom.getInstance(ALGORITHM);
            random.setSeed(encodeRules.getBytes(Charset.defaultCharset()));
            keygen.init(AESSize.SIZE_128.size, random);
        }
    }

    /**
     * 加密
     *
     * @param encodeRules 加密规则
     * @param message     数据
     * @return 密文
     */
    public static String encrypt(String encodeRules, String message) throws EncryptionException {
        try {
            // 根据encodeRules规则初始化密钥生成器
            initKeyGenerator(encodeRules);
            // 产生原始对称密钥
            SecretKey originalKey = keygen.generateKey();
            byte[] raw = originalKey.getEncoded();
            // 根据字节数组生成AES密钥
            SecretKey key = new SecretKeySpec(raw, KEY_ALGORITHM_AES);
            // 根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM_AES);
            // 初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // 获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
            byte[] byteEncode = message.getBytes(StandardCharsets.UTF_8);
            // 根据密码器的初始化方式--加密：将数据加密
            byte[] byteAes = cipher.doFinal(byteEncode);
            return Base64.getEncoder().encodeToString(byteAes);
        } catch (NoSuchAlgorithmException | // 无此算法
                 NoSuchPaddingException | // 没有这样的填充异常
                 InvalidKeyException | // 无效密钥异常
                 IllegalBlockSizeException | // 非法块大小异常
                 BadPaddingException e) { // 不良填充异常
            throw new EncryptionException(e);
        }
    }

    /**
     * 解密
     *
     * @param encodeRules 加密规则
     * @param encryptStr  加密串
     * @return String
     */
    public static String decrypt(String encodeRules, String encryptStr) throws DecryptionException {
        try {
            // 根据encodeRules规则初始化密钥生成器
            initKeyGenerator(encodeRules);
            // 产生原始对称密钥
            SecretKey originalKey = keygen.generateKey();
            byte[] raw = originalKey.getEncoded();
            // 根据字节数组生成AES密钥
            SecretKey key = new SecretKeySpec(raw, KEY_ALGORITHM_AES);
            // 根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM_AES);
            // 初始化密码器，第一个参数为加密(Encrypt_mode)或者解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.DECRYPT_MODE, key);
            // 将加密并编码后的内容解码成字节数组
            byte[] byteContent = Base64.getDecoder().decode(encryptStr);
            byte[] byteDecode = cipher.doFinal(byteContent);
            return new String(byteDecode, Charset.defaultCharset());
        } catch (NoSuchAlgorithmException | // 无此算法异常
                 NoSuchPaddingException | // 没有这样的填充异常
                 InvalidKeyException | // 无效密钥异常
                 IllegalBlockSizeException | // 非法块大小异常
                 BadPaddingException e) { // 不良填充异常
            throw new RuntimeException(e);
        }
    }

}
