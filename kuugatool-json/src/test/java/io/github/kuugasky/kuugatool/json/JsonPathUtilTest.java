package io.github.kuugasky.kuugatool.json;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.date.interval.TimeInterval;
import io.github.kuugasky.kuugatool.core.string.snow.SnowflakeIdUtil;
import io.github.kuugasky.kuugatool.json.entity.KuugaModel;
import org.junit.jupiter.api.Test;

import java.util.List;

class JsonPathUtilTest {

    @Test
    void path() {
        KuugaModel Kuuga = KuugaModel.builder().name("kuuga").sex(1).enabled(true).build();
        String jsonString = JsonUtil.toJsonString(Kuuga);
        Object path = JsonPathUtil.extract(jsonString, "$.enabled");
        System.out.println(path);
    }

    @Test
    void path2() {
        KuugaModel Kuuga = KuugaModel.builder().name("kuuga").sex(1).enabled(true).build();
        String jsonString = JsonUtil.toJsonString(Kuuga);

        TimeInterval timeInterval = new TimeInterval();
        String json = SnowflakeIdUtil.getSnowflakeIdStr();
        String jsonPath = SnowflakeIdUtil.getSnowflakeIdStr();

        timeInterval.start(json);
        for (int i = 0; i < 10000000; i++) {
            // System.out.println(JsonUtil.get(jsonString, "enabled"));
            JsonUtil.get(jsonString, "enabled");
        }
        System.out.println("json----> " + timeInterval.intervalPretty(json));

        timeInterval.start(jsonPath);
        for (int i = 0; i < 10000000; i++) {
            // System.out.println(JsonPathUtil.path(jsonString, "$.enabled"));
            JsonPathUtil.extract(jsonString, "$.enabled");
        }
        System.out.println("path----> " + timeInterval.intervalPretty(jsonPath));
    }

    @Test
    void test3() {
        KuugaModel Kuuga = KuugaModel.builder().name("kuuga").sex(1).enabled(true).build();
        KuugaModel kuuga1 = KuugaModel.builder().name("kuuga1").sex(0).enabled(false).build();
        List<KuugaModel> lists = ListUtil.newArrayList(Kuuga, kuuga1);

        String jsonString = JsonUtil.toJsonString(lists);

        TimeInterval timeInterval = new TimeInterval();
        String json = SnowflakeIdUtil.getSnowflakeIdStr();
        String jsonPath = SnowflakeIdUtil.getSnowflakeIdStr();

        timeInterval.start(json);
        for (int i = 0; i < 1; i++) {
            // System.out.println(JsonUtil.get(jsonString, "enabled"));
            JSONArray array = JsonUtil.parseArray(jsonString);
            JSONObject jsonObject = (JSONObject) array.get(0);
            jsonObject.get("enabled");
        }
        System.out.println("json----> " + timeInterval.intervalPretty(json));

        timeInterval.start(jsonPath);
        for (int i = 0; i < 1; i++) {
            // System.out.println(JsonPathUtil.path(jsonString, "$.enabled"));
            // 只能$.field读取字段
            System.out.println(JsonPathUtil.extract(jsonString, "$0.enabled"));
        }
        System.out.println("path----> " + timeInterval.intervalPretty(jsonPath));


    }

}