package io.github.kuugasky.kuugatool.core.string;

import org.junit.jupiter.api.Test;

public class RadixUtilTest {

    @Test
    public void encode() {
        // 将10转换为5进制文本
        System.out.println(RadixUtil.encode("kuuga", 10));
    }

    @Test
    public void testEncode() {
        System.out.println(RadixUtil.encode("kuuga", 10L));
    }

    @Test
    public void decodeToInt() {
        System.out.println(RadixUtil.decodeToInt("kuuga", "UH"));
    }

    @Test
    public void decode() {
        System.out.println(RadixUtil.decodeToLong("kuuga", "UH"));
    }
}