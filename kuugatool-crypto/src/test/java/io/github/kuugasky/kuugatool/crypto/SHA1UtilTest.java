package io.github.kuugasky.kuugatool.crypto;

import org.junit.jupiter.api.Test;

public class SHA1UtilTest {

    @Test
    public void encode() {
        System.out.println(SHA1Util.encode("SHA1加密测试!"));
    }
}