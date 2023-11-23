package io.github.kuugasky.kuugatool.core.encoder;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

class Base64UtilTest {

    @Test
    void encode() {
        String encodeStr = Base64Util.encode("华夏文明");
        System.out.println(encodeStr);
    }

    @Test
    void testEncode() {
        String encodeStr = Base64Util.encode("华夏文明".getBytes());
        System.out.println(encodeStr);
    }

    @Test
    void testEncode1() throws UnsupportedEncodingException {
        String encodeStr = Base64Util.encode("华夏文明", "GBK");
        System.out.println(encodeStr);
    }

    @Test
    void testEncode2() {
        String encodeStr = Base64Util.encode("华夏文明", Charset.forName("GBK"));
        System.out.println(encodeStr);
    }

    @Test
    void decode() {
        String encodeStr = Base64Util.encode("华夏文明");
        System.out.println(Base64Util.decode(encodeStr));
    }

    @Test
    void decodeToBytes() {
        String encodeStr = Base64Util.encode("华夏文明");
        System.out.println(Arrays.toString(Base64Util.decodeToBytes(encodeStr)));
    }

    @Test
    void testDecode() throws UnsupportedEncodingException {
        String encodeStr = Base64Util.encode("华夏文明");
        System.out.println(Base64Util.decode(encodeStr, "utf-8"));
    }

    @Test
    void testDecode1() {
        String encodeStr = Base64Util.encode("华夏文明");
        System.out.println(Base64Util.decode(encodeStr, StandardCharsets.UTF_8));
    }
}