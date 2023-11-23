package io.github.kuugasky.kuugatool.core.encoder;

import io.github.kuugasky.kuugatool.core.constants.KuugaCharsets;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class CodeUtilTest {

    public static void main(String[] args) throws UnsupportedEncodingException {
        String sourceStr = "kuugatool工具类库";
        System.out.println(UrlCodeUtil.encode(sourceStr));
        System.out.println(UrlCodeUtil.encode(sourceStr, KuugaCharsets.UTF_8));
        System.out.println(UrlCodeUtil.encode(sourceStr, StandardCharsets.UTF_8));
        String encodeUtf8Str = UrlCodeUtil.encode(sourceStr);
        System.out.println(UrlCodeUtil.decode(encodeUtf8Str));
        String encodeGbkStr = UrlCodeUtil.encode(sourceStr, KuugaCharsets.GBK);
        System.out.println(UrlCodeUtil.decode(encodeGbkStr, KuugaCharsets.GBK));
        System.out.println(UrlCodeUtil.decode(encodeGbkStr, Charset.forName("GBK")));
    }

}