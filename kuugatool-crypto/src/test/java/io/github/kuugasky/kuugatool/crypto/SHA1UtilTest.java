package io.github.kuugasky.kuugatool.crypto;

import org.junit.jupiter.api.Test;

class SHA1UtilTest {

    @Test
    void encode() {
        // sha1生成固定40位长度密文
        System.out.println(SHA1Util.encode("SHA1加密测试!"));
        // md5生成32位长度密文
        System.out.println(MD5Util.getMd5("SHA1加密测试!"));
    }

}