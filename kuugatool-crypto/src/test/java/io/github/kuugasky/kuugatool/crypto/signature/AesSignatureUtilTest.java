package io.github.kuugasky.kuugatool.crypto.signature;

import org.junit.jupiter.api.Test;

import java.util.TreeMap;

public class AesSignatureUtilTest {

    public static void main(String[] args) throws Exception {
        TreeMap<String, String> sortMap = new TreeMap<>();
        sortMap.put("userName", "kuuga");
        sortMap.put("password", "123456");
        sortMap.put("address", "测试地址");

        final String encodeRules = "kuugasky@gmail.com";
        String encryption = AesSignatureUtil.encryption(sortMap, encodeRules);
        System.out.println("密文：" + encryption);
        String plaintext = AesSignatureUtil.decryption(encryption, encodeRules);
        System.out.println("明文：" + plaintext);
    }

    @Test
    public void encryption() {
    }

    @Test
    public void decryption() {
    }
}