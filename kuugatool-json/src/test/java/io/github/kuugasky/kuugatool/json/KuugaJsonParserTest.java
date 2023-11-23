package io.github.kuugasky.kuugatool.json;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import io.github.kuugasky.kuugatool.json.entity.KuugaEnum;
import io.github.kuugasky.kuugatool.json.entity.KuugaModel;
import org.junit.jupiter.api.Test;

import java.util.List;

class KuugaJsonParserTest {

    @Test
    void defaultObject() {
        KuugaModel Kuuga = KuugaModel.builder().name("kuuga").sex(1).enabled(true).build();
        // KuugaModel kuuga = null;
        String jsonString = JsonUtil.toJsonString(Kuuga);
        // String jsonString = "{}";
        Boolean test = KuugaJsonParser.of(jsonString).defaultObject(false).parsingObject("enabled1").getBoolean();
        System.out.println(test);
    }

    @Test
    void defaultClass1() {
        KuugaModel kuuga1 = KuugaModel.builder().name("kuuga1").sex(0).enabled(true).build();
        KuugaModel javaObject = KuugaJsonParser.of(JsonUtil.toJsonString(kuuga1))
                .defaultClass(KuugaModel.class)
                .toJavaObject(KuugaModel.class);

        System.out.println(StringUtil.formatString(javaObject));
    }

    @Test
    void defaultClass2() {
        KuugaModel kuugaModel = KuugaJsonParser.of("{}")
                .defaultClass(KuugaModel.class)
                .toJavaObject(KuugaModel.class);

        System.out.println(StringUtil.formatString(kuugaModel));
    }

    @Test
    void defaultClass3() {
        List<KuugaModel> list = ListUtil.newArrayList();
        KuugaModel kuuga1 = KuugaModel.builder().name("kuuga1").sex(0).enabled(true).build();
        KuugaModel kuuga2 = KuugaModel.builder().name("kuuga2").sex(1).enabled(false).build();
        list.add(kuuga1);
        list.add(kuuga2);

        // List<KuugaModel> kuugaModels = KuugaJsonParser.of(JsonUtil.toJsonString(list))
        List<KuugaModel> kuugaModels = KuugaJsonParser.of("[{}]")
                .defaultClass(KuugaModel.class)
                .toList(KuugaModel.class);

        System.out.println(StringUtil.formatString(kuugaModels));
    }

    @Test
    void of() {
    }

    @Test
    void parsingObject() {
    }

    @Test
    void parsingArray() {
        KuugaModel item1 = new KuugaModel("kuuga", 1, true, KuugaEnum.GOOD);
        KuugaModel item2 = new KuugaModel("kuugaX", 0, false, KuugaEnum.BAD);
        JSONArray of = JSONArray.of(item1, item2);
        JSONObject jsonArray = JSONObject.of("jsonArray", of);
        KuugaJsonParser jsonArray1 = KuugaJsonParser.of(jsonArray.toJSONString()).parsingArray("jsonArray");
        List<KuugaModel> kuugaModels = jsonArray1.toList(KuugaModel.class);
        System.out.println(kuugaModels);
    }

    @Test
    void toJavaObject() {
        KuugaModel item1 = new KuugaModel("kuuga", 1, true, KuugaEnum.GOOD);
        JSONObject jsonArray = JSONObject.of("key", JsonUtil.toJsonString(item1));
        KuugaModel kuugaModel = KuugaJsonParser.of(jsonArray.toJSONString()).parsingObject("key").toJavaObject(KuugaModel.class);
        System.out.println(StringUtil.formatString(kuugaModel));
    }

    @Test
    void toList() {
        KuugaModel item1 = new KuugaModel("kuuga", 1, true, KuugaEnum.GOOD);
        KuugaModel item2 = new KuugaModel("kuugaX", 0, false, KuugaEnum.BAD);
        JSONArray of = JSONArray.of(item1, item2);
        JSONObject jsonArray = JSONObject.of("jsonArray", of);
        List<KuugaModel> jsonArray1 = KuugaJsonParser.of(jsonArray.toJSONString()).parsingObject("jsonArray").toList(KuugaModel.class);
        jsonArray1.forEach(x -> System.out.println(StringUtil.formatString(x)));
    }

    @Test
    void getString() {
        JSONObject jsonObject = JSONObject.of("key", "kuuga");
        String key = KuugaJsonParser.of(jsonObject.toJSONString()).parsingObject("key").getString();
        System.out.println(key);
    }

    @Test
    void getBoolean() {
        JSONObject jsonObject = JSONObject.of("key", true);
        Boolean key = KuugaJsonParser.of(jsonObject.toJSONString()).parsingObject("key").getBoolean();
        System.out.println(key);
    }

    @Test
    void getInt() {
        JSONObject jsonObject = JSONObject.of("key", 1);
        Integer key = KuugaJsonParser.of(jsonObject.toJSONString()).parsingObject("key").getInt();
        System.out.println(key);
    }

    @Test
    void getDouble() {
        JSONObject jsonObject = JSONObject.of("key", 1D);
        Double key = KuugaJsonParser.of(jsonObject.toJSONString()).parsingObject("key").getDouble();
        System.out.println(key);
    }

    @Test
    void getLong() {
        JSONObject jsonObject = JSONObject.of("key", 1L);
        Long key = KuugaJsonParser.of(jsonObject.toJSONString()).parsingObject("key").getLong();
        System.out.println(key);
    }

    @Test
    void getFloat() {
        JSONObject jsonObject = JSONObject.of("key", 1f);
        Float key = KuugaJsonParser.of(jsonObject.toJSONString()).parsingObject("key").getFloat();
        System.out.println(key);
    }
}