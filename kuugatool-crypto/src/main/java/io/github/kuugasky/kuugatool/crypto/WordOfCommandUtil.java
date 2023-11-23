package io.github.kuugasky.kuugatool.crypto;

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
 * 口令加密算法
 * https://www.liaoxuefeng.com/wiki/1252599548343744/1304227859136546
 *
 * @author kuuga
 * @since 2021/6/4
 */
public class WordOfCommandUtil {

    private static final String ALGORITHM = "PBEwithSHA1and128bitAES-CBC-BC";
    private static final int ITERATION_COUNT = 1000;

    public static String generateSalt() throws NoSuchAlgorithmException {
        // 把BouncyCastle作为Provider添加到java.security:
        Security.addProvider(new BouncyCastleProvider());
        byte[] bytes = SecureRandom.getInstanceStrong().generateSeed(16);
        return Base64.getEncoder().encodeToString(bytes);
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
        PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey key = keyFactory.generateSecret(keySpec);
        PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(getSaltBytes(saltStr), ITERATION_COUNT);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key, pbeParameterSpec);
        byte[] bytes = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(bytes);
    }

    private static byte[] getSaltBytes(String saltStr) {
        return Base64.getDecoder().decode(saltStr);
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

}
