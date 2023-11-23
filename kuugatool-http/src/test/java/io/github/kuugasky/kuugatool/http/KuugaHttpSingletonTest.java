package io.github.kuugasky.kuugatool.http;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.collection.MapUtil;
import io.github.kuugasky.kuugatool.core.optional.KuugaOptional;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import io.github.kuugasky.kuugatool.http.function.KuugaConsumer;
import org.apache.http.NameValuePair;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Map;
import java.util.function.BiConsumer;

class KuugaHttpSingletonTest {

    static KuugaHttpCustom kuugaHttpSingleton;

    @BeforeAll
    static void beforeAll() {
        kuugaHttpSingleton = KuugaHttpSingleton.init();
    }

    @Test
    void test() {
        KuugaHttp abstractHttpClient = kuugaHttpSingleton
                .requestHeader(MapUtil.newHashMap())
                .needSSL(false)
                .abnormalShielding(true)
                .timeout(100000)
                .useProxy(false)
                .compress(true)
                .fixAllRelativeHref(true)
                .encodingDetect(true);
        KuugaHttp.HttpResult httpResult = abstractHttpClient.get("http://10.210.10.54:13000/web-agent-pc/security/dict/garden/queryPage");
        System.out.println(httpResult.getContent());
        System.out.println("status = " + httpResult.getStatus());
        System.out.println("contentType = " + httpResult.getContentType());
        System.out.println("costTime = " + httpResult.getCostTime());
        System.out.println("costTimeDesc = " + httpResult.getCostTimeDesc());
        KuugaOptional.ofNullable(httpResult.getException()).ifPresent(x -> System.out.println("exception = " + x.getMessage()));
        System.out.println("headers = " + Arrays.toString(httpResult.getHeaders()));
        System.out.println("protocolVersion = " + httpResult.getProtocolVersion());
        System.out.println("request = " + httpResult.getRequest());
        httpResult.isFail(() -> System.out.println("isFail = " + "is puJie."));
        httpResult.isRequestTimeout(() -> System.out.println("isTimeOut = " + "is time out."));
        httpResult.isOk(() -> System.out.println("isOk = " + "is ok."));
        httpResult.hasContext(s -> System.out.println("hasContext = " + s.length()));
        httpResult.exceptionHandler(s -> System.out.println("exceptionHandler = " + s.getException().getMessage()));
        httpResult.hasNoContext(() -> System.out.println("noContext = " + "no context"));

        System.out.println("isOkAndHasContext.getString = " + httpResult.isOkAndHasContext().getString());
        System.out.println("isOkAndHasContext.KuugaConsumer = " + httpResult.isOkAndHasContext((KuugaConsumer<String, String>) s -> String.valueOf(s.length())));

        httpResult.isOkAndHasContext(s -> {
            System.out.print("isOkAndHasContext.Consumer = " + s.length());
        });

        BiConsumer<String, Map<String, String>> action = (s, o) -> o.put("length", String.valueOf(s.length()));
        Map<String, String> result = MapUtil.newHashMap();
        httpResult.isOkAndHasContext(action, result);
        result.forEach((k, v) -> System.out.printf("isOkAndHasContext.BiConsumer = k:%s, v:%s%n", k, v));
    }

    @Test
    void init() {
        System.out.println(StringUtil.formatString(kuugaHttpSingleton));
    }

    @Test
    void get() {
        KuugaHttp.HttpResult httpResult = kuugaHttpSingleton.get("https://www.baidu.com");
        System.out.println(StringUtil.formatString(httpResult));
    }

    @Test
    void testGet() {
        NameValuePair nameValuePair = NameValuePairUtil.of("kuuga", "test");
        KuugaHttp.HttpResult httpResult = kuugaHttpSingleton.get("https://www.baidu.com", ListUtil.newArrayList(nameValuePair));
        System.out.println(StringUtil.formatString(httpResult));
    }

    @Test
    void testGet1() {
        NameValuePair nameValuePair = NameValuePairUtil.of("kuuga", "test");
        KuugaHttp.HttpResult httpResult = kuugaHttpSingleton.get("https://www.baidu.com", nameValuePair);
        System.out.println(StringUtil.formatString(httpResult));
    }

    @Test
    void testGet2() {
        KuugaHttp.HttpResult httpResult = kuugaHttpSingleton.get("https://www.baidu.com", MapUtil.newHashMap("kuuga", "test"));
        System.out.println(StringUtil.formatString(httpResult));
    }

    @Test
    void post() {
        KuugaHttp.HttpResult httpResult = kuugaHttpSingleton.post("https://www.baidu.com");
        System.out.println(StringUtil.formatString(httpResult));
    }

    @Test
    void testPost() {
        NameValuePair nameValuePair = NameValuePairUtil.of("kuuga", "test");
        KuugaHttp.HttpResult httpResult = kuugaHttpSingleton.post("https://www.baidu.com", nameValuePair);
        System.out.println(StringUtil.formatString(httpResult));
    }

    @Test
    void testPost1() {
        NameValuePair nameValuePair = NameValuePairUtil.of("kuuga", "test");
        KuugaHttp.HttpResult httpResult = kuugaHttpSingleton.post("https://www.baidu.com", ListUtil.newArrayList(nameValuePair));
        System.out.println(StringUtil.formatString(httpResult));
    }

    @Test
    void testPost2() {
        KuugaHttp.HttpResult httpResult = kuugaHttpSingleton.post("https://www.baidu.com", MapUtil.newHashMap("kuuga", "test"));
        System.out.println(StringUtil.formatString(httpResult));
    }

    @Test
    void testPost3() {
        KuugaHttp.HttpResult httpResult = kuugaHttpSingleton.post("https://www.baidu.com", "{\"name\":\"Kuuga\"}");
        System.out.println(StringUtil.formatString(httpResult));
    }

}