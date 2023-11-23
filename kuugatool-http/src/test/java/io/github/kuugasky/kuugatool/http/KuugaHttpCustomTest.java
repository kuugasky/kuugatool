package io.github.kuugasky.kuugatool.http;

import io.github.kuugasky.kuugatool.core.collection.MapUtil;
import io.github.kuugasky.kuugatool.core.optional.KuugaOptional;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class KuugaHttpCustomTest {

    static KuugaHttp kuugaHttp;

    @BeforeAll
    static void before() {
        kuugaHttp = KuugaHttpCustom.init();
    }

    @Test
    void execute() {
        kuugaHttp
                .requestHeader(MapUtil.newHashMap())
                .needSSL(false)
                .abnormalShielding(true)
                .timeout(100000)
                .useProxy(false)
                .compress(true)
                .fixAllRelativeHref(true)
                .encodingDetect(true);
        KuugaHttp.HttpResult httpResult = kuugaHttp.get("http://10.210.10.54:13000/web-agent-pc/security/dict/garden/queryPage");
        System.out.println(httpResult.getCostTimeDesc());
        System.out.println(httpResult.getCostTime());
        System.out.println(httpResult.getContent());
        System.out.println(httpResult.getContentType());
        System.out.println(httpResult.getProtocolVersion());
        System.out.println(httpResult.getStatus());
        System.out.println(Arrays.toString(httpResult.getHeaders()));

        System.out.println(StringUtil.formatString(httpResult));

        httpResult.isOk(() -> httpResult.hasContext(System.out::println));

        KuugaOptional.ofNullable(httpResult).filter(o -> o.getStatus() != 200).filter(o -> StringUtil.hasText(o.getContent())).ifPresent(o -> {
            if (StringUtil.hasText(o.getContent())) {
                System.out.println(o.getContent());
            } else {
                System.out.println("xxx");
            }
        });
    }

}