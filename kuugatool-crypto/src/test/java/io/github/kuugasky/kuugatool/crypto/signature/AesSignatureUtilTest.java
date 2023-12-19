package io.github.kuugasky.kuugatool.crypto.signature;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.TreeMap;

class AesSignatureUtilTest {

    @Test
    void defaultTest() {
        TreeMap<String, String> sortMap = new TreeMap<>();
        sortMap.put("userName", "kuuga");
        sortMap.put("password", "123456");
        sortMap.put("address", "测试地址");

        String encryption = AesSignatureUtil.encryption(sortMap);
        Assertions.assertEquals("ZHhmZ1FkV2tpMzlZWWpIYUt4dkNFSmdXVUJMQ0pzRDVoeFJtN2pWMkIyY2hIUUYzRGIrWlJwMVd2N3g3ZXpZS1pSM1l0QUpvMHNnaW9rcEZhSWNFVWxSS084ZEtNb3h4VWVmclI2dWtWL3M9", encryption);
        String plaintext = AesSignatureUtil.decryption(encryption);
        Assertions.assertEquals("{\"address\":\"测试地址\",\"password\":\"123456\",\"userName\":\"kuuga\"}", plaintext);
    }

    record Demo(String name, Integer age) {
    }

    @Test
    void encryption() {
        final String encodeRules = "kuugasky@gmail.com";
        Demo kuuga = new Demo("kuuga", 30);
        String encryption = AesSignatureUtil.encryption(kuuga, encodeRules);
        System.out.println(encryption);
        Assertions.assertEquals("aU9NTk1MY1AvM0M0QVRqelBGeGFoNmxpTkwxc2tPdG1RZFdGSlFUUVM4dz0=", encryption);
    }

    @Test
    void decryption() {
        final String encodeRules = "kuugasky@gmail.com";
        Demo kuuga = new Demo("kuuga", 30);
        String encryption = AesSignatureUtil.encryption(kuuga, encodeRules);
        String decryption = AesSignatureUtil.decryption(encryption, encodeRules);
        Assertions.assertEquals("{\"age\":30,\"name\":\"kuuga\"}", decryption);
    }

}