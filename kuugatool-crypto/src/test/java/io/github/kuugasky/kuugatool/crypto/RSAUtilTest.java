package io.github.kuugasky.kuugatool.crypto;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import io.github.kuugasky.kuugatool.core.encoder.UrlCodeUtil;
import io.github.kuugasky.kuugatool.core.lang.Console;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class RSAUtilTest {

    @Test
    void testInitKey() throws Exception {
        RSAUtil.RSAKeyPair rsaKeyPair = RSAUtil.initKey();
        System.out.println(StringUtil.formatString(rsaKeyPair));
    }

    @Test
    void getKey() throws Exception {
        Key Key = RSAUtil.getPublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgaKXrlTeMoIPLzt/zkYmqQkxnIBydJJHJjI/Rj5qKggr+UyJIq/ucywtMVGM5/agscr35lZCH3UmJ/UeQ3WaVZkI5dkN9lAIV10Yl2cRPzrunITrHcuy5RzpLyZajgA9930pQgdLaDNTNAtF9UBtfu8a2iI9fEw8fM6GCjc8712rNEHHfEKXVymw/lpzhrmG6s3RbDeluFlF0QZph8I7pqZnnn3HUAEEzqRc8751rYgN37QmkG2Q9PCo7U7spyozxnFqV0TSljc3JEbOplpoMh8eyt/+UnkyBFUR3rIXaZi4Ajm8ycxwTXNuvs24AsjSw30DiN9q/e0Dh+jVvmdOfwIDAQAB");
        System.out.println(Base64.getEncoder().encodeToString(Key.getEncoded()));
    }

    @Test
    void getPrivateKey() throws Exception {
        PrivateKey Key = RSAUtil.getPrivateKey("MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCDX/tx9xyMrkA0RicEkxtb8CFs3ibeL3MgLpi39xtUaGHDIWIFDVUjvZjZxwt/N3rjRDLAXhphMTs6S4JWFo55yceE9fN9vKI31c98VezqGMpCSeh1vtiPQKt5D8SrI5ANRzk/rZ6egy3hYgCULxlRqnCffZyMObDJtTY15hnKpx6W5MBxsrifQEJpjgQnV+9DOHs3l0AeC1Pvu6Gj98pNhOjfutsjKtbOUJtQGXuf1Jo49mWTQ9qQmzAt0UdSeAjI8fSMNUVat3UjrjF/yAYILgJJoVYHD7SzRYeloxNZZPbaKC7iBtf7DJnTuzJaeJp245BI+kUZuGWlKRI+p/jpAgMBAAECggEAaNL5CqIlwYp36D3hfCD0v5MG2JsZYEd0EMWdTYx53gu7lbrZPza4LGBnybysS8G06AnOhPeRDOP/YjBFuvMck8iQzmKA+l69ANg1Sfw1oUROfj4CoZC5svPi3iHaxsMFedLOtySNX+VDgoZz98js5uCcJGZRtwGBMjDJJJCJG8zAB+73eXcJN4wbgqTNgmeXRvfpcyKHRr86YW81KlKF2JQlcvF0x7x8AkFpVvkM4EL4LGYl+WFvwaFC6W4Srd3oTrUkOCQVEJin4XrZ60YSg7Ba6iVNVSS2jUfdL5uGiebqQJYNr/CMpiaGl9T4Fv4QAv206sERfCiyV1rttfJUAQKBgQC/jVwz1rZcO4FgRwJeAkoaM1GGjT/dCmAv5+8xHNVQDOdUE5A4um13YHZW0aOIM6gGHAV3YGqUgqYTGLpUhXdEgnHDNQuMbpxTB2RNPYOIukTY906hTzOTjCa/1Utz6OlVBBFmRpkbdHCNkYeaczY9sDTrLyRI2EvHVDUdNSPgSQKBgQCvk3nxluZ+bkE/ZGY++CByZMus65wC5tUPn1atngcoy1O+8XTpXqYNK3soeELSdYQC+MffztMykxzI539DV+DMNAcKwcmRL7ckuV1mCk0d1O/EetZ/ZzCwBA5Wv3p4CGBGXp2Y0QO7+99oUsAx6y9LSK5VAcJr43Uf322J6t6ToQKBgHgOrZ+AD8Q3N5uUZhNt8+wABjUDCugLvMXJ5M0veTM3IzY7HMWxczLDLuizdH/FgKZzoal0sNhsGpMViJO5I0u5RnxHHvnhyfDwTRlsWKi1trkWKB5KedUcnpzVnvkDHlu+tPkZfpUKohDZCdjwJSr86e4OGfveNYC1cqTfzaChAoGANP/YlKPPiiSwD3m4H5P4/28LyHQK0RlrAV9A5AHK+XZ/Hil2+Cc5CsqwT6QVz7/njJoag6XEPQUJcF/pG6QAWMPvyEB9HxV/PFe0KOQ9KR8pDW9jJmw41zkyNt9wEaG9piBThFi7vtgmNTCdkbQDv7792O1CCh1MMsXYJATBEWECgYEAoTuD5ThjHZYhsjeuposNSbjgn0t6fMbw1YvGpBUMLoytBBuk1ixABkzvLxV2jyj+Ihrzvvw7JA4Ry32fiZTyS79Fsg7UvERVutGUhezrlbA71jJXdlYbnxRafx9HKQU9DaiEdXyDeyvj9RzedzBWeIEJQoJ1AYlClvxBrQRzifs=");
        System.out.println(Base64.getEncoder().encodeToString(Key.getEncoded()));
    }

    @Test
    void encrypt() throws Exception {
        RSAUtil.RSAKeyPair rsaKeyPair = RSAUtil.initKey("kuugasky");
        System.out.println("rsa密钥对:" + JSON.toJSONString(rsaKeyPair));

        PublicKey key = rsaKeyPair.getPublicKey();
        String encrypt = RSAUtil.encrypt("测试加密", key);
        // post请求应该不涉及转义，所以base64编码后即可返回
        System.out.println("加密串:" + encrypt);
        // get请求涉及参数编码，最好是再一层编码防止转义
        String encryptEncode = UrlCodeUtil.encode(encrypt);
        System.out.println(encryptEncode);
    }

    @Test
    void encrypt2() throws Exception {
        RSAUtil.RSAKeyPair rsaKeyPair = RSAUtil.initKey("kuugasky");
        System.out.println("rsa密钥对:" + JSON.toJSONString(rsaKeyPair));

        PublicKey publicKey = rsaKeyPair.getPublicKey();

        String encrypt = RSAUtil.encrypt("测试加密", publicKey, StandardCharsets.UTF_8);
        System.out.println("加密串:" + encrypt);
    }

    @Test
    void testEncrypt() throws Exception {
        RSAUtil.RSAKeyPair rsaKeyPair = RSAUtil.initKey("kuugasky");
        System.out.println(JSON.toJSONString(rsaKeyPair));

        PublicKey publicKey = rsaKeyPair.getPublicKey();
        String encrypt = RSAUtil.encrypt("测试加密", publicKey, StandardCharsets.UTF_8);
        System.out.println("加密串:" + encrypt);
    }

    @Test
    void decrypt() throws Exception {
        RSAUtil.RSAKeyPair rsaKeyPair = RSAUtil.initKey("kuugasky");
        System.out.println("rsa密钥对:" + JSON.toJSONString(rsaKeyPair));

        PublicKey publicKey = rsaKeyPair.getPublicKey();
        PrivateKey privateKey = rsaKeyPair.getPrivateKey();

        String ciphertext = RSAUtil.encrypt("万里长城，锦绣中华", publicKey);
        Console.blueLog("ciphertext = " + ciphertext);
        // url编码
        String ciphertextEncode = UrlCodeUtil.encode(ciphertext);
        Console.redLog("plaintext = " + RSAUtil.decrypt(ciphertext, privateKey));
        // url解码
        String ciphertextDecode = UrlCodeUtil.decode(ciphertextEncode);
        Console.redLog("plaintext = " + RSAUtil.decrypt(ciphertextDecode, privateKey));
    }

    public static void main(String[] args) throws Exception {
        RSAUtil.RSAKeyPair rsaKeyPair = RSAUtil.initKey("kuugasky");
        System.out.println("rsa密钥对:" + JSON.toJSONString(rsaKeyPair));

        PublicKey publicKey = rsaKeyPair.getPublicKey();
        PrivateKey privateKey = rsaKeyPair.getPrivateKey();
        // 私钥签名
        String plainText = "foobar";
        System.out.println("明文：" + plainText);
        String signature = RSAUtil.sign(plainText, privateKey);
        System.out.println("明文加密后的签名：" + signature);
        // 公钥验证签名（明文+签名+公钥->验签）
        boolean isCorrect = RSAUtil.verify(plainText, signature, publicKey);
        System.out.println("签名结果: " + isCorrect);
    }

    @Test
    void decryptByKey() throws Exception {
        RSAUtil.RSAKeyPair rsaKeyPair = RSAUtil.initKey("kuugasky");
        System.out.println(JSON.toJSONString(rsaKeyPair));

        String publicKeyBase64Str = rsaKeyPair.getPublicKeyBase64Str();
        String privateKeyStr = rsaKeyPair.getPrivateKeyBase64Str();


        System.out.println("================密钥对构造完毕,甲方将公钥公布给乙方，开始进行加密数据的传输=============");
        String str = "aattaggcctegthththfef/aat.mp4";
        System.out.println("===========甲方向乙方发送加密数据==============");
        System.out.println("原文:" + str);
        // 甲方进行数据的加密
        PublicKey publicKey = RSAUtil.getPublicKey(publicKeyBase64Str);
        PrivateKey privateKey = RSAUtil.getPrivateKey(privateKeyStr);
        String code1 = RSAUtil.encrypt(str, publicKey);
        System.out.println("甲方 使用乙方公钥加密后的数据：" + code1);
        System.out.println("===========乙方使用甲方提供的公钥对数据进行解密==============");
        // 乙方进行数据的解密
        String decode1 = RSAUtil.decrypt(code1, privateKey);
        System.out.println("乙方解密后的数据：" + decode1 + StringUtil.EMPTY);

        System.out.println("===========反向进行操作，乙方向甲方发送数据==============");

        str = "乙方向甲方发送数据RSA算法";

        System.out.println("原文:" + str);

        // 乙方使用公钥对数据进行加密
        String code2 = RSAUtil.encrypt(str, publicKey);
        System.out.println("===========乙方使用公钥对数据进行加密==============");
        System.out.println("加密后的数据：" + code2);

        System.out.println("=============乙方将数据传送给甲方======================");
        System.out.println("===========甲方使用私钥对数据进行解密==============");

        // 甲方使用私钥对数据进行解密
        String decode2 = RSAUtil.decrypt(code2, privateKey);

        System.out.println("甲方解密后的数据：" + decode2);
    }

    @Test
    void encryptSegmented() throws Exception {
        RSAUtil.RSAKeyPair rsaKeyPair = RSAUtil.initKey();
        String encryptSegmented = RSAUtil.encryptSegmented("万里长城，锦绣中华", rsaKeyPair.getPublicKey());
        System.out.println(encryptSegmented);

        String decryptSegmented = RSAUtil.decryptSegmented(encryptSegmented, rsaKeyPair.getPrivateKey());
        System.out.println(decryptSegmented);
    }

    @Test
    void sbw() throws Exception {
        RSAUtil.RSAKeyPair rsaKeyPair = RSAUtil.initKey("kfang&sbw");
        // 私钥
        PrivateKey privateKey = rsaKeyPair.getPrivateKey();
        System.out.println(privateKey);
        // 公钥
        PublicKey publicKey = rsaKeyPair.getPublicKey();
        System.out.println(publicKey);

        System.out.println("publicKey:" + rsaKeyPair.getPublicKeyBase64Str());
        System.out.println("privateKey:" + rsaKeyPair.getPrivateKeyBase64Str());

        // String publicKeyBase64Str = rsaKeyPair.getPublicKeyBase64Str();
        // System.out.println("公钥信息:" + publicKeyBase64Str);
        // PublicKey publicKey1 = RSAUtil.getPublicKey(publicKeyBase64Str);
        // System.out.println("公钥是否相等:" + ObjectUtil.equals(publicKey, publicKey1));

        // 加密
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("key1", "sbw");
        jsonObject.put("key2", "kfang");

        String plaintext = jsonObject.toString();
        System.out.println("明文:" + plaintext);

        String encrypt = RSAUtil.encrypt(plaintext, publicKey);
        System.out.println("密文:" + encrypt);

        String sign = RSAUtil.sign(plaintext, privateKey);
        System.out.println("签名:" + sign);

        boolean verify = RSAUtil.verify(plaintext, sign, publicKey);
        System.out.println("验签:" + verify);
    }

}