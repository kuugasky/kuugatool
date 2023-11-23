package io.github.kuugasky.kuugatool.core.uri;

import io.github.kuugasky.kuugatool.core.file.FileUtil;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.util.Arrays;

class URLUtilTest {

    @Test
    void url() {
        // 通过一个字符串形式的URL地址创建URL对象
        URL url = URLUtil.url("http://www.baidu.com");
        System.out.println(url);
    }

    @Test
    void testUrl() {
        // 通过一个字符串形式的URL地址创建URL对象
        URL url = URLUtil.url("http://www.baidu.com", new URLStreamHandler() {
            @Override
            protected URLConnection openConnection(URL u) throws IOException {
                return new URLConnection(new URL("http://www.baidu.com")) {
                    @Override
                    public void connect() throws IOException {
                        System.out.println("connect");
                    }
                };
            }
        });
        System.out.println(url);
    }

    @Test
    void getStringURI() {
        // 获取string协议的URL，类似于string:///xxxxx
        URI stringURI = URLUtil.getStringURI("https://www.baidu.com");
        System.out.println(stringURI);
    }

    @Test
    void toUrlForHttp() {
        // 将URL字符串转换为URL对象，并做必要验证
        URL url = URLUtil.toUrlForHttp("https://baidu.com");
        System.out.println(url);
    }

    @Test
    void testToUrlForHttp() {
        // 将URL字符串转换为URL对象，并做必要验证
        URL url = URLUtil.toUrlForHttp("https://baidu.com", new URLStreamHandler() {
            @Override
            protected URLConnection openConnection(URL u) throws IOException {
                return new HttpURLConnection(new URL("https://baidu.com")) {
                    @Override
                    public void disconnect() {
                        System.out.println("disconnect");
                    }

                    @Override
                    public boolean usingProxy() {
                        return false;
                    }

                    @Override
                    public void connect() {

                    }
                };
            }
        });
        System.out.println(url);
    }

    @Test
    void encodeBlank() {
        // 单独编码URL中的空白符，空白符编码为%20
        String s = URLUtil.encodeBlank("https://baidu.com/ Kuuga");
        System.out.println(s);
    }

    @Test
    void getURL() {
        File file = FileUtil.file("/Users/kuuga/Downloads/getUrl.txt");
        FileUtil.createFile(file);
        URL url = URLUtil.getURL(file);
        System.out.println(url);
    }

    @Test
    void getURLs() {
        File file = FileUtil.file("/Users/kuuga/Downloads/getUrl.txt");
        FileUtil.createFile(file);
        URL[] url = URLUtil.getURLs(file, file);
        System.out.println(Arrays.toString(url));
    }

    @Test
    void getHost() throws MalformedURLException {
        URI host = URLUtil.getHost(new URL("https://baidu.com"));
        System.out.println(host);
    }

    @Test
    void getPath() throws MalformedURLException, URISyntaxException {
        String path = URLUtil.getPath(new URL("https://baidu.com").toURI().toString());
        System.out.println(path);
    }

    @Test
    void getDecodedPath() throws MalformedURLException {
        String decodedPath = URLUtil.getDecodedPath(new URL("https://baidu.com"));
        System.out.println(decodedPath);
    }

    @Test
    void toURI() throws MalformedURLException, URISyntaxException {
        URI uri = new URL("https://baidu.com").toURI();
        System.out.println(uri);
    }

    @Test
    void testToURI() {
    }

    @Test
    void testToURI1() {
    }

    @Test
    void testToURI2() {
    }

    @Test
    void isFileURL() {
    }

    @Test
    void isJarURL() {
    }

    @Test
    void getDataUriBase64() {
    }

    @Test
    void getDataUri() {
    }

    @Test
    void testGetDataUri() {
    }
}