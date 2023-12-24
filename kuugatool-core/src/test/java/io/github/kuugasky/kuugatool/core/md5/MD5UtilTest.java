package io.github.kuugasky.kuugatool.core.md5;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

class MD5UtilTest {

    @Test
    void getMD5ToUpperCase() {
        System.out.println(MD5Util.getMd5ToUpperCase("测试MD5加密"));
    }

    @Test
    void getMD5ToUpperCase1() {
        System.out.println(MD5Util.getMd5ToUpperCase("测试MD5加密".getBytes(StandardCharsets.UTF_8)));
    }

    @Test
    void getMD5() {
        System.out.println(MD5Util.getMd5("测试MD5加密".getBytes(StandardCharsets.UTF_8)));
    }

    @Test
    void testGetMD5() {
        System.out.println(MD5Util.getMd5("测试MD5加密"));
    }

}