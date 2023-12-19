package io.github.kuugasky.kuugatool.crypto;

import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Base64;

/**
 * WordOfCommandUtil
 * <p>javax.crypto</p>
 * <a href="https://www.liaoxuefeng.com/wiki/1252599548343744/1304227859136546">口令加密算法</a>
 *
 * @author kuuga
 * @since 2021/6/4
 */
public class WordOfCommandUtil {

    /**
     * 算法
     */
    private static final String ALGORITHM = "PBEwithSHA1and128bitAES-CBC-BC";
    private static final String DEFAULT_SALT = "hot6AdvSb73Ll807U+PZmw==";
    /**
     * 迭代计数
     */
    private static final int ITERATION_COUNT = 1000;

    /**
     * 生成盐
     *
     * @return Salt
     * @throws NoSuchAlgorithmException 无此算法异常
     */
    public static String generateSalt() throws NoSuchAlgorithmException {
        BouncyCastleProvider bouncyCastleProvider = new BouncyCastleProvider();
        // 把BouncyCastle作为Provider添加到java.security:
        Security.addProvider(bouncyCastleProvider);
        byte[] bytes = SecureRandom.getInstanceStrong().generateSeed(16);
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * 加密
     *
     * @param password  口令
     * @param plaintext 明文
     * @return 密文
     * @throws GeneralSecurityException GeneralSecurityException
     */
    public static String encrypt(String password, String plaintext) throws GeneralSecurityException {
        WordOfCommandUtil.generateSalt();
        return encrypt(password, DEFAULT_SALT, plaintext);
    }

    /**
     * 加密
     *
     * @param password  口令
     * @param saltStr   加密盐
     * @param plaintext 明文
     * @return 密文
     * @throws GeneralSecurityException GeneralSecurityException
     */
    public static String encrypt(String password, String saltStr, String plaintext) throws GeneralSecurityException {
        checkRequireOfEncrypt(password, saltStr, plaintext);
        PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey key = keyFactory.generateSecret(keySpec);
        PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(getSaltBytes(saltStr), ITERATION_COUNT);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key, pbeParameterSpec);
        byte[] bytes = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * Required validation
     *
     * @param password  口令
     * @param saltStr   加密盐
     * @param plaintext 明文
     */
    private static void checkRequireOfEncrypt(String password, String saltStr, String plaintext) {
        if (StringUtil.isEmpty(password)) {
            throw new RuntimeException("The password cannot be empty.");
        }
        if (StringUtil.isEmpty(saltStr)) {
            throw new RuntimeException("The salt cannot be empty.");
        }
        if (StringUtil.isEmpty(plaintext)) {
            throw new RuntimeException("The plaintext cannot be empty.");
        }
    }

    /**
     * get salt bytes
     *
     * @param saltStr 加密盐字符串
     * @return bytes
     */
    private static byte[] getSaltBytes(String saltStr) {
        return Base64.getDecoder().decode(saltStr);
    }

    /**
     * 解密
     *
     * @param password   口令
     * @param ciphertext 密文
     * @return 明文
     * @throws GeneralSecurityException GeneralSecurityException
     */
    public static String decrypt(String password, String ciphertext) throws GeneralSecurityException {
        return decrypt(password, DEFAULT_SALT, ciphertext);
    }

    /**
     * 解密
     *
     * @param password   口令
     * @param saltStr    加密盐
     * @param ciphertext 密文
     * @return 明文
     * @throws GeneralSecurityException GeneralSecurityException
     */
    public static String decrypt(String password, String saltStr, String ciphertext) throws GeneralSecurityException {
        checkRequireOfDecrypt(password, saltStr, ciphertext);
        byte[] ciphertextByte = Base64.getDecoder().decode(ciphertext);
        PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey key = keyFactory.generateSecret(keySpec);
        PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(getSaltBytes(saltStr), ITERATION_COUNT);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key, pbeParameterSpec);
        byte[] bytes = cipher.doFinal(ciphertextByte);
        return new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * Required validation
     *
     * @param password   口令
     * @param saltStr    加密盐
     * @param ciphertext 密文
     */
    private static void checkRequireOfDecrypt(String password, String saltStr, String ciphertext) {
        if (StringUtil.isEmpty(password)) {
            throw new RuntimeException("The password cannot be empty.");
        }
        if (StringUtil.isEmpty(saltStr)) {
            throw new RuntimeException("The salt cannot be empty.");
        }
        if (StringUtil.isEmpty(ciphertext)) {
            throw new RuntimeException("The plaintext cannot be empty.");
        }
    }

}
