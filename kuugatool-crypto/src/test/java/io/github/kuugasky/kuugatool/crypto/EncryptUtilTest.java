package io.github.kuugasky.kuugatool.crypto;

import org.junit.jupiter.api.Test;

public class EncryptUtilTest {

    @Test
    public void encodeMD5String() {
        // 用MD5算法进行加密
        System.out.println(EncryptUtil.encodeMD5String("kuugasky", "kuuga"));
    }

    @Test
    public void encodeSHAString() {
        // 用SHA算法进行加密
        System.out.println(EncryptUtil.encodeSHAString("kuugasky"));
    }

    @Test
    public void encodeBase64String() {
        // 用base64算法进行加密
        System.out.println(EncryptUtil.encodeBase64String("kuugasky"));
    }

    @Test
    public void decodeBase64String() {
        // 用base64算法进行解密
        System.out.println(EncryptUtil.decodeBase64String("aHl1Z2EwNDEw"));
    }
}