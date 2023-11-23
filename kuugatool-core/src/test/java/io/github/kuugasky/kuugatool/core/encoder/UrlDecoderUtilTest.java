package io.github.kuugasky.kuugatool.core.encoder;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

public class UrlDecoderUtilTest {

    @Test
    public void encode() {
        System.out.println(UrlCodeUtil.encode("测试编码：Kuuga!"));
    }

    @Test
    public void testEncode() {
        System.out.println(UrlCodeUtil.encode("测试编码：Kuuga!", Charset.defaultCharset()));
    }

    @Test
    public void testEncode1() throws UnsupportedEncodingException {
        System.out.println(UrlCodeUtil.encode("测试编码：Kuuga!", "utf-8"));
    }

    @Test
    public void decode() {
        System.out.println(UrlCodeUtil.decode("%E6%B5%8B%E8%AF%95%E7%BC%96%E7%A0%81%EF%BC%9Akuuga%21"));
    }

    @Test
    public void testDecode() {
        System.out.println(UrlCodeUtil.decode("%E6%B5%8B%E8%AF%95%E7%BC%96%E7%A0%81%EF%BC%9Akuuga%21", Charset.defaultCharset()));
    }

    @Test
    public void testDecode1() throws UnsupportedEncodingException {
        System.out.println(UrlCodeUtil.decode("%E6%B5%8B%E8%AF%95%E7%BC%96%E7%A0%81%EF%BC%9Akuuga%21", "utf-8"));
    }
}