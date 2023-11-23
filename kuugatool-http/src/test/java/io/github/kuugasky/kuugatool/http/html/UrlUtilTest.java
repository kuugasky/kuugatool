package io.github.kuugasky.kuugatool.http.html;

import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class UrlUtilTest {

    @Test
    void of() throws MalformedURLException {
        String url = "https://www.google.com/org/test/Kuuga.jpg";
        URL of = UrlUtil.of(url);
        System.out.println(StringUtil.formatString(of));
    }

    @Test
    void getProtocol() throws MalformedURLException {
        String url = "https://www.google.com/org/test/Kuuga.jpg";
        System.out.println(UrlUtil.getProtocol(url));
    }

    @Test
    void getHost() throws MalformedURLException {
        String url = "https://www.google.com/org/test/Kuuga.jpg";
        System.out.println(UrlUtil.getHost(url));
    }

    @Test
    void getPort() throws MalformedURLException {
        String url = "https://www.google.com/org/test/Kuuga.jpg";
        System.out.println(UrlUtil.getPort(url));
    }

    @Test
    void getDefaultPort() throws MalformedURLException {
        String url = "https://www.google.com/org/test/Kuuga.jpg";
        System.out.println(UrlUtil.getDefaultPort(url));
    }

    @Test
    void getPath() throws MalformedURLException {
        String url = "https://www.google.com/org/test/Kuuga.jpg";
        Assertions.assertEquals(UrlUtil.getPath(url), "/org/test/Kuuga.jpg");

        String url1 = "https://www.google.com?keyword=java";
        System.out.println(UrlUtil.getPath(url1));
    }

    @Test
    void getFile() throws IOException {
        String url = "https://www.google.com/org/test/Kuuga.jpg";
        Assertions.assertEquals(UrlUtil.getFile(url), "/org/test/Kuuga.jpg");
    }

    @Test
    public void isUrl() {
        String url = "http://www.baidu.com?name=kuuga&sex=1&charset=utf-8";
        System.out.println(UrlUtil.isUrl(url));
    }

    @Test
    public void encodeIllegalCharacterInUrl() {
        String url = "http://baidu.com?name=kuuga&sex=1%name=枫¥%……&charset=utf-8 {}%2$";
        System.out.println(UrlUtil.encodeIllegalCharacterInUrl(url));
    }

    @Test
    public void removeProtocol() {
        System.out.println(UrlUtil.removeProtocol("http://www.baidu.com?name=kuuga&sex=1&charset=utf-8"));
        System.out.println(UrlUtil.removeProtocol("https://www.baidu.com?name=kuuga&sex=1&charset=utf-8"));
    }

    @Test
    public void removePort() throws MalformedURLException {
        String url = "https://www.google.com:8088/org/test/Kuuga.jpg";
        System.out.println(UrlUtil.removePort(url));
        String url1 = "https://www.baidu.com?name=kuuga&sex=1&charset=utf-8";
        System.out.println(UrlUtil.removePort(url1));
    }

    @Test
    public void getCharset() {
        String url = "http://baidu.com?name=kuuga&sex=1&charset=utf-8";
        System.out.println(UrlUtil.getCharset(url));
    }

    @Test
    public void toMap() {
        String url = "http://baidu.com?name=kuuga&sex=1";
        Map<String, String> stringObjectMap = UrlUtil.toMap(url);
        System.out.println(stringObjectMap);
    }

    @Test
    public void testToMap() {
        String url = "http://baidu.com?name=kuuga#sex=1";
        Map<String, String> stringObjectMap = UrlUtil.toMap(url, "#");
        System.out.println(stringObjectMap);
    }

}