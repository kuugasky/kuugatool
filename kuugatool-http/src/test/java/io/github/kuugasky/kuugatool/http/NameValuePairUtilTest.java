package io.github.kuugasky.kuugatool.http;

import io.github.kuugasky.kuugatool.core.collection.MapUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import lombok.Data;
import org.apache.http.NameValuePair;
import org.junit.jupiter.api.Test;

import java.util.List;

class NameValuePairUtilTest {

    @Test
    void of() {
        NameValuePair of = NameValuePairUtil.of("kuuga", "good");
        System.out.println(StringUtil.formatString(of));
    }

    @Test
    void ofMap() {
        List<NameValuePair> of = NameValuePairUtil.toList(MapUtil.newHashMap("kuuga", "good"));
        System.out.println(StringUtil.formatString(of));
    }

    @Test
    void ofObject() {
        KuugaDto kuugaDto = new KuugaDto();
        kuugaDto.setName("kuuga");
        kuugaDto.setValue(100);
        List<NameValuePair> of = NameValuePairUtil.toList(kuugaDto);
        System.out.println(StringUtil.formatString(of));
    }

    @Data
    public static class KuugaDto {
        private String name;
        private int value;
    }

}