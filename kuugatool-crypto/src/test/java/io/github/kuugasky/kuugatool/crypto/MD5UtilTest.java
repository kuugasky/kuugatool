package io.github.kuugasky.kuugatool.crypto;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

public class MD5UtilTest {

    @Test
    public void getMD5ToUpperCase() {
        System.out.println(MD5Util.getMD5ToUpperCase("测试MD5加密"));
    }

    @Test
    public void getMD5ToUpperCase1() {
        System.out.println(MD5Util.getMD5ToUpperCase("测试MD5加密".getBytes(StandardCharsets.UTF_8)));
    }

    @Test
    public void getMD5() {
        System.out.println(MD5Util.getMD5("测试MD5加密".getBytes(StandardCharsets.UTF_8)));
    }

    @Test
    public void testGetMD5() {
        System.out.println(MD5Util.getMD5("测试MD5加密"));
    }
}