package io.github.kuugasky.kuugatool.json;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import lombok.Data;

/**
 * FastJsonTest
 *
 * @author kuuga
 * @since 2022/6/6 16:32
 */
@Data
public class FastJsonTest {

    private FastJsonEnum fastJsonEnum = FastJsonEnum.FAST_JSON_ENUM;

    public static void main(String[] args) {
        // fastjson2默认关闭了很多feature
        // JSON.config(JSONWriter.Feature.WriteEnumUsingToString);JSON.config(JSONReader.Feature.SupportSmartMatch);

        FastJsonTest fastJsonTest = new FastJsonTest();
        System.out.println(JSON.toJSONString(fastJsonTest));
        System.out.println(com.alibaba.fastjson2.JSON.toJSONString(fastJsonTest, JSONWriter.Feature.WriteEnumsUsingName));
        System.out.println(com.alibaba.fastjson2.JSON.toJSONString(fastJsonTest, JSONWriter.Feature.WriteEnumUsingToString));

        System.out.println(JSON.toJSONString(fastJsonTest));
        // System.out.println(JSON.toJSONString(fastJsonTest, SerializerFeature.WriteEnumUsingName));
        // System.out.println(JSON.toJSONString(fastJsonTest, SerializerFeature.WriteEnumUsingToString));

        System.out.println(JsonUtil.toJsonString(fastJsonTest));
    }

}

enum FastJsonEnum {
    FAST_JSON_ENUM;
}
