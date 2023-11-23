package io.github.kuugasky.kuugatool.crypto;

import io.github.kuugasky.kuugatool.core.encoder.Base64Util;
import io.github.kuugasky.kuugatool.core.encoder.UrlCodeUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.junit.jupiter.api.Test;

import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.UUID;

public class AESUtilTest {

    @Test
    public void makeKey() {
        // 获得AES秘钥字节数组
        byte[] bytes = AESUtil.makeKey();
        System.out.println(Arrays.toString(bytes));
    }

    @Test
    public void testMakeKey() {
        // 获得AES秘钥字节数组，随机种子并不确定随机序列
        byte[] bytes = AESUtil.makeKey(AESUtil.AESSize.SIZE_128, "kuuga");
        System.out.println(Arrays.toString(bytes));
    }

    @Test
    public void getAESSecretKey() {
        // 用字符串生成的私钥
        byte[] bytes = AESUtil.makeKey();
        SecretKeySpec secretKeySpec = AESUtil.getAESSecretKey(bytes);
        System.out.println(StringUtil.formatString(secretKeySpec));
    }

    @Test
    public void testGetAESSecretKey() throws Exception {
        // 用字符串生成的私钥
        SecretKeySpec secretKeySpec = AESUtil.getAESSecretKey("kuuga", false);
        System.out.println(StringUtil.formatString(secretKeySpec));
    }

    @Test
    public void encrypt() throws Exception {
        // 对数据加密，默认UTF-8 加密
        byte[] bytes = AESUtil.makeKey(AESUtil.AESSize.SIZE_128, "kuugasky");
        SecretKeySpec secretKeySpec = AESUtil.getAESSecretKey(bytes);
        byte[] kuuga_is_a_good_boys = AESUtil.encrypt("Kuuga is a good boy", secretKeySpec);
        System.out.println(Arrays.toString(kuuga_is_a_good_boys));
        System.out.println(new String(kuuga_is_a_good_boys));
    }

    @Test
    public void decrypt() throws Exception {
        byte[] bytes = AESUtil.makeKey(AESUtil.AESSize.SIZE_128, "kuugasky");
        SecretKeySpec secretKeySpec = AESUtil.getAESSecretKey(bytes);
        byte[] kuuga_is_a_good_boys = AESUtil.encrypt("Kuuga is a good boy", secretKeySpec);
        String encode = new String(Base64.getEncoder().encode(kuuga_is_a_good_boys));
        System.out.println("加密Base64编码后：" + encode);
        byte[] decode = Base64.getDecoder().decode(encode);

        byte[] bytes1 = AESUtil.makeKey(AESUtil.AESSize.SIZE_128, "kuuga1");
        SecretKeySpec secretKeySpec1 = AESUtil.getAESSecretKey(bytes1);

        String kuuga_is_a_good_boys1 = AESUtil.decrypt(decode, secretKeySpec);
        System.out.println("解密后：" + kuuga_is_a_good_boys1);
    }

    @Test
    public void encrypt1() throws Exception {
        String sid = UUID.randomUUID().toString();
        System.out.println("原文：" + sid);
        String encrypt = AESUtil.encrypt("kuuga@mac", sid);
        System.out.println("加密后：" + encrypt);
        System.out.println("加密后Base64编码：" + Base64Util.encode(encrypt));
        System.out.println("加密后URLDecoder编码：" + UrlCodeUtil.encode(encrypt));
        String s = AESUtil.decrypt("kuuga@mac", encrypt);
        System.out.println("解密后：" + s);
    }

}