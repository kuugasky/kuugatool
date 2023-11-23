package io.github.kuugasky.kuugatool.nacos.parse;

import org.junit.jupiter.api.Test;

public class NacosConfigReadConvertorTest {

    @Test
    void test() {
        NacosConfigReadConvertor nacosConfigReadConvertor = new NacosConfigReadConvertor();
        String value = nacosConfigReadConvertor.getValue(MetaSourceEnum.JD.getPinyin(), "刑案资产");
        System.out.println(value);
    }

}