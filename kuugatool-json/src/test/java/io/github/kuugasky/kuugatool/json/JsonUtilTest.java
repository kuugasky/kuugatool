package io.github.kuugasky.kuugatool.json;

import com.alibaba.fastjson2.*;
import com.alibaba.fastjson2.filter.Filter;
import com.alibaba.fastjson2.filter.SimplePropertyPreFilter;
import com.alibaba.fastjson2.util.TypeUtils;
import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.collection.MapUtil;
import io.github.kuugasky.kuugatool.core.date.DateFormat;
import io.github.kuugasky.kuugatool.core.date.DateUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import io.github.kuugasky.kuugatool.json.entity.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.*;

class JsonUtilTest {

    @Test
    void xx1() {
        String x = JSON.toJSONString(null);
        System.out.println(x);
    }

    @Test
    void toJsonString2() {
        SimplePropertyPreFilter excludeProperties = new SimplePropertyPreFilter();
        excludeProperties.getExcludes().addAll(List.of(new String[]{"name"}));
        SimplePropertyPreFilter includeProperties = new SimplePropertyPreFilter();
        excludeProperties.getExcludes().addAll(List.of(new String[]{"id", "username", "mobile"}));

        KuugaModel Kuuga = KuugaModel.builder().name("kuuga").sex(1).build();
        String jsonString = JsonUtil.toJsonString(Kuuga, excludeProperties);
        KuugaModel kuugaModel = JsonUtil.parseObject(jsonString, KuugaModel.class);
        System.out.println(jsonString);
        System.out.println(StringUtil.formatString(kuugaModel));

        String s = JsonUtil.toJsonString(Kuuga, includeProperties, JSONWriter.Feature.PrettyFormat, JSONWriter.Feature.WriteMapNullValue);
        System.out.println(s);
    }

    @Test
    void toJsonString1() {
        Demo2 demo = new Demo2("kuuga", DateUtil.now());
        // 格式美化
        JSONWriter.Feature[] features = {JSONWriter.Feature.PrettyFormat};
        String toJSONString = JsonUtil.toJsonString(demo, features);
        System.out.println(toJSONString);
        String toJSONString1 = JsonUtil.toJsonString(demo, DateFormat.yyyy_MM_dd_HH_mm_ss_SSS.format(), features);
        System.out.println(toJSONString1);
        String toJSONString2 = JsonUtil.toJsonString(demo, DateFormat.yyyy_MM_dd_HH_mm_ss_SSS.format());
        System.out.println(toJSONString2);

        System.out.println(JSON.toJSONString(demo, DateFormat.yyyy_MM_dd_HH_mm_ss_SSS.format()));
    }

    @Data
    @AllArgsConstructor
    static class Demo2 {
        private String name;
        private Date time;
    }

    @Test
    void toJsonString3() {
        Demo2 demo = new Demo2("kuuga", DateUtil.now());

        SimplePropertyPreFilter excludeFilter = new SimplePropertyPreFilter();
        Set<String> includes = excludeFilter.getIncludes();
        includes.add("name");
        includes.add("time");

        Filter[] filters = {excludeFilter};

        String toJSONString = JsonUtil.toJsonString(demo, "yyyy-MM-dd HH:mm:ss.SSS", filters);
        System.out.println(toJSONString);

        String toJSONString1 = JsonUtil.toJsonString(demo, "yyyy-MM-dd HH:mm:ss", filters);
        System.out.println(toJSONString1);
    }

    @Test
    void toJsonString() {
        KuugaModel Kuuga = KuugaModel.builder().name("kuuga").sex(1).build();
        String jsonString = JsonUtil.toJsonString(Kuuga);
        System.out.println(jsonString);

        String toJSONString = JSON.toJSONString(Kuuga, JSONWriter.Feature.WriteMapNullValue);
        System.out.println(toJSONString);
    }

    @Test
    void toJsonStringByEnum() {
        KuugaModel Kuuga = KuugaModel.builder().name("kuuga").sex(1).kuugaEnum(KuugaEnum.GOOD).build();
        System.out.println(JSON.toJSONString(Kuuga));
        System.out.println(JSON.toJSONString(Kuuga, JSONWriter.Feature.WriteEnumsUsingName));
        System.out.println(JSON.toJSONString(KuugaEnum.GOOD, JSONWriter.Feature.WriteEnumsUsingName));
        // System.out.println(JSON.toJSONString(Kuuga, JSONWriter.Feature.WriteEnumUsingToString));
    }

    @Data
    static
    class Identitykey {
        private IdentityTypeEnum type;
        private String value;
    }

    enum IdentityTypeEnum {
        CHINA_PKT
    }

    @Test
    void xx() {
        Identitykey identitykeyBrief = new Identitykey();

        identitykeyBrief.setType(IdentityTypeEnum.CHINA_PKT);

        identitykeyBrief.setValue("1");
        // JSONWriter.Feature.WriteEnumsUsingName
        System.out.println(JSON.toJSONString(identitykeyBrief, JSONWriter.Feature.WriteEnumsUsingName));
    }

    @Test
    void testToJsonString() {
        List<KuugaModel> list = ListUtil.newArrayList();
        KuugaModel kuuga1 = KuugaModel.builder().name("kuuga1").sex(1).build();
        KuugaModel kuuga2 = KuugaModel.builder().name("kuuga2").sex(2).build();
        KuugaModel kuuga3 = KuugaModel.builder().name("kuuga3").sex(3).build();
        list.add(kuuga1);
        list.add(kuuga2);
        list.add(kuuga3);
        String jsonString = JsonUtil.toJsonString(list);
        System.out.println(jsonString);
    }

    @Test
    void testToJsonString1() {
        KuugaModel kuuga1 = KuugaModel.builder().name("kuuga1").sex(1).build();
        KuugaModel kuuga2 = KuugaModel.builder().name("kuuga2").sex(2).build();
        KuugaModel kuuga3 = KuugaModel.builder().name("kuuga3").sex(3).build();
        String jsonString = JsonUtil.toJsonString(new KuugaModel[]{kuuga1, kuuga2, kuuga3});
        System.out.println(jsonString);
    }

    @Data
    static class KuugaModelT<T> {

        private T object;

        private String name;
        private int sex;
        private Boolean enabled;

        private KuugaEnum kuugaEnum = KuugaEnum.GOOD;

    }

    @Test
    void toJavaObject1() {
        KuugaModelT<String> kuugaModel = new KuugaModelT<>();
        kuugaModel.setKuugaEnum(KuugaEnum.BAD);
        kuugaModel.setName("kuuga");
        kuugaModel.setSex(1);
        kuugaModel.setEnabled(true);
        kuugaModel.setObject("xxx");

        String jsonString = JsonUtil.toJsonString(kuugaModel);
        System.out.println(jsonString);

        TypeReference<KuugaModelT<String>> typeReference = new TypeReference<>() {
        };
        // KuugaModelT<String> airDto = JSONObject.parseObject(jsonString, typeReference);
        // System.out.println(StringUtil.formatString(airDto));
        // System.out.println(JsonUtil.toJavaObject(jsonString, KuugaModelT.class));

        System.out.println(StringUtil.formatString(JSON.to(KuugaModelT.class, jsonString)));
        System.out.println(JsonUtil.toJavaObject(jsonString, typeReference));
    }

    @Test
    void toJavaObject() {
        KuugaModel kuugaModel = JsonUtil.toJavaObject("{\"name\":\"kuuga1\",\"sex\":1}", KuugaModel.class);
        System.out.println(kuugaModel);
    }

    @Test
    void toJavaObject2() {
        KuugaModel kuugaModel = JsonUtil.toJavaObject("{\"name\":\"kuuga1\",\"sex\":1}".getBytes(StandardCharsets.UTF_8), KuugaModel.class);
        System.out.println(kuugaModel);
    }

    @Test
    void testToJavaObject() {
        KuugaModel kuugaModel = JsonUtil.toJavaObject("{\"name\":\"kuuga1\",\"sex\":1}", KuugaModel.class);
        System.out.println(kuugaModel);
    }

    @Test
    void testToJavaObject1() {
        Demo2 Kuuga = new Demo2("kuuga", DateUtil.now());
        String x = JsonUtil.toJsonString(Kuuga);
        System.out.println(x);
        String x1 = JsonUtil.toJsonString(Kuuga, "yyyy-MM-dd HH:mm:ss");
        System.out.println(x1);
        String x2 = JsonUtil.toJsonString(Kuuga, "yyyy-MM-dd HH:mm:ss.SSS");
        System.out.println(x2);

        System.out.println(JsonUtil.toJavaObject(x, Demo2.class));
        System.out.println(JsonUtil.toJavaObject(x1, Demo2.class));
        System.out.println(JsonUtil.toJavaObject(x2, Demo2.class));

        System.out.println(DateUtil.format(DateFormat.yyyy_MM_dd_HH_mm_ss_SSS, Objects.requireNonNull(JsonUtil.toJavaObject(x, Demo2.class)).getTime()));
        System.out.println(DateUtil.format(DateFormat.yyyy_MM_dd_HH_mm_ss_SSS, Objects.requireNonNull(JsonUtil.toJavaObject(x1, Demo2.class)).getTime()));
        System.out.println(DateUtil.format(DateFormat.yyyy_MM_dd_HH_mm_ss_SSS, Objects.requireNonNull(JsonUtil.toJavaObject(x2, Demo2.class)).getTime()));
    }

    @Test
    void toParseObject() {
        KuugaModel kuugaModel = JsonUtil.toJavaObject("{\"name\":\"kuuga1\",\"sex\":1}".getBytes(StandardCharsets.UTF_8), KuugaModel.class);
        System.out.println(kuugaModel);
    }

    @Test
    void toParseObject2() {
        KuugaModel kuugaModel = JsonUtil.toJavaObject("{\"name\":\"kuuga1\",\"sex\":1}", KuugaModel.class);
        System.out.println(kuugaModel);
    }

    @Test
    void test1() {
        String message = "{\"type\": \"TEXT\", \"msgId\": 1000173551, \"source\": \"APP\", \"toName\": \"冰淇淋\", \"content\": \"方便电话沟通吗13714138139\", \"houseId\": \"\", \"gardenId\": \"\", \"jumpLink\": \"\", \"toUserId\": \"K-B-845\", \"apartment\": \"\", \"fromPhone\": \"13714138139\", \"houseType\": \"\", \"fromUserId\": \"S-A-7fbf4c80-d274-4a42-a66a-6cb05f38fabe\", \"gardenName\": \"\", \"jpushParam\": {\"fromName\": \"苏彬\", \"fromPhone\": \"13714138139\", \"fromPhoto\": \"https://file4.kfangcdn.com/prod/005227.jpg-f{size}\", \"fromUserId\": \"S-A-7fbf4c80-d274-4a42-a66a-6cb05f38fabe\"}, \"builtupArea\": [], \"houseSource\": \"\", \"propertyUse\": \"\", \"typeVersion\": \"V1_0_0\", \"businessType\": \"OTHER\", \"houseShortId\": \"\", \"fromTimeStamp\": 1655609966002, \"houseExpandId\": \"\", \"startEntrance\": \"MESSAGE_LIST\", \"toUserIsOnline\": true, \"toUserOnlineStatus\": \"PUSH_ONLINE\"}";
        JSONObject jsonObject = JsonUtil.parseObject(message);
        System.out.println(jsonObject.toString());
    }

    @Test
    void test2() {
        String message = "{\"type\": \"TEXT\", \"msgId\": 1000173551, \"source\": \"APP\", \"toName\": \"冰淇淋\", \"content\": \"方便电话沟通吗13714138139\", \"houseId\": \"\", \"gardenId\": \"\", \"jumpLink\": \"\", \"toUserId\": \"K-B-845\", \"apartment\": \"\", \"fromPhone\": \"13714138139\", \"houseType\": \"\", \"fromUserId\": \"S-A-7fbf4c80-d274-4a42-a66a-6cb05f38fabe\", \"gardenName\": \"\", \"jpushParam\": {\"fromName\": \"苏彬\", \"fromPhone\": \"13714138139\", \"fromPhoto\": \"https://file4.kfangcdn.com/prod/005227.jpg-f{size}\", \"fromUserId\": \"S-A-7fbf4c80-d274-4a42-a66a-6cb05f38fabe\"}, \"builtupArea\": [], \"houseSource\": \"\", \"propertyUse\": \"\", \"typeVersion\": \"V1_0_0\", \"businessType\": \"OTHER\", \"houseShortId\": \"\", \"fromTimeStamp\": 1655609966002, \"houseExpandId\": \"\", \"startEntrance\": \"MESSAGE_LIST\", \"toUserIsOnline\": true, \"toUserOnlineStatus\": \"PUSH_ONLINE\"}";
        // message = message.replaceAll("-f\\{size}", StringUtil.EMPTY);
        ImNotifyMessage jsonObject = JsonUtil.toJavaObject(message, ImNotifyMessage.class);
        System.out.println(StringUtil.formatString(jsonObject));
    }

    @Test
    void test3() {
        // 不行
        String message = "{\"id\": \"4746f4f0946c4c34a891af8952e2f7b8\", \"date\": 1655913662, \"orgId\": \"000240\", \"isMain\": \"YES\", \"messageId\": 1655913662, \"changeDiff\": \"{\\\"currentCompanyOrgId\\\":\\\"000060\\\",\\\"currentLevelId\\\":\\\"001\\\",\\\"currentOrgId\\\":\\\"000240\\\",\\\"currentPositionId\\\":\\\"0265\\\",\\\"oldCompanyOrgId\\\":\\\"000060\\\",\\\"oldLevelId\\\":\\\"001\\\",\\\"oldOrgId\\\":\\\"000169\\\",\\\"oldPositionId\\\":\\\"0265\\\"}\", \"positionId\": \"0265\", \"operateType\": \"UPDATE\"}";
        PersonPositionMessage entity = JsonUtil.toJavaObject(message, PersonPositionMessage.class);
        System.out.println(StringUtil.formatString(entity));
    }

    @Test
    void test4() {
        String message = "{\"id\": \"4746f4f0946c4c34a891af8952e2f7b8\", \"date\": 1655913662, \"orgId\": \"000240\", \"isMain\": \"YES\", \"messageId\": 1655913662, \"changeDiff\": {\\\"currentCompanyOrgId\\\":\\\"000060\\\",\\\"currentLevelId\\\":\\\"001\\\",\\\"currentOrgId\\\":\\\"000240\\\",\\\"currentPositionId\\\":\\\"0265\\\",\\\"oldCompanyOrgId\\\":\\\"000060\\\",\\\"oldLevelId\\\":\\\"001\\\",\\\"oldOrgId\\\":\\\"000169\\\",\\\"oldPositionId\\\":\\\"0265\\\"}, \"positionId\": \"0265\", \"operateType\": \"UPDATE\"}";
        PersonPositionMessage entity = JsonUtil.toJavaObject(message, PersonPositionMessage.class);
        System.out.println(StringUtil.formatString(entity));
    }

    @Test
    void test41() {
        String message = "{\"id\": \"000216\", \"date\": 1655967600000, \"orgId\": \"001667\", \"messageId\": 1655967600923, \"operateType\": \"UPDATE\"}";
        HousePreMergeBaseMessage entity = JsonUtil.toJavaObject(message, HousePreMergeBaseMessage.class);
        // HousePreMergeBaseMessage entity = TypeUtils.cast(message, HousePreMergeBaseMessage.class);
        // HousePreMergeBaseMessage entity = JSON.to(HousePreMergeBaseMessage.class, message);
        System.out.println(StringUtil.formatString(entity));
    }

    @Test
    void test42() {
        String json = "{\"time\":1655967600000}";
        System.out.println(JSON.parseObject(json).toString());

        System.out.println(JSON.to(Demo.class, json).getTime());

        System.out.println(TypeUtils.cast(json, Demo.class).getTime());
    }

    @Test
    void testToJsonString2() {
        String message = "{\"id\": \"000216\", \"date\": 1655967600000, \"orgId\": \"001667\", \"messageId\": 1655967600923, \"operateType\": \"UPDATE\"}";
        HousePreMergeBaseMessage entity = JsonUtil.toJavaObject(message, HousePreMergeBaseMessage.class);
        String json = JsonUtil.toJsonString(entity, "id");
        System.out.println(json);
    }

    @Test
    void testToJsonString3() {
        String message = "{\"id\": \"000216\", \"date\": 1655967600000, \"orgId\": \"001667\", \"messageId\": 1655967600923, \"operateType\": \"UPDATE\"}";
        HousePreMergeBaseMessage entity = JsonUtil.toJavaObject(message, HousePreMergeBaseMessage.class);
        String json = JsonUtil.toJsonStringWithIncludes(entity, "id", "messageId");
        System.out.println(json);
        String json1 = JsonUtil.toJsonStringWithExcludes(entity, "id", "messageId");
        System.out.println(json1);
    }

    @Test
    void testToJsonString4() {
        String message = "{\"id\": \"000216\", \"date\": 1655967600000, \"orgId\": \"001667\", \"messageId\": 1655967600923, \"operateType\": \"UPDATE\"}";
        HousePreMergeBaseMessage entity = JsonUtil.toJavaObject(message, HousePreMergeBaseMessage.class);
        SimplePropertyPreFilter excludeFilter = new SimplePropertyPreFilter();
        excludeFilter.getExcludes().add("id");
        excludeFilter.getExcludes().add("messageId");
        excludeFilter.getExcludes().add("orgId");
        String json = JsonUtil.toJsonString(entity, excludeFilter);
        System.out.println(json);
    }

    @Data
    static class Demo {
        private Date time;
    }

    @Test
    void test5() {
        String message = "{\"type\": \"TEXT\", \"msgId\": 1000173551, \"source\": \"APP\", \"toName\": \"冰淇淋\", \"content\": \"方便电话沟通吗13714138139\", \"houseId\": \"\", \"gardenId\": \"\", \"jumpLink\": \"\", \"toUserId\": \"K-B-845\", \"apartment\": \"\", \"fromPhone\": \"13714138139\", \"houseType\": \"\", \"fromUserId\": \"S-A-7fbf4c80-d274-4a42-a66a-6cb05f38fabe\", \"gardenName\": \"\", \"jpushParam\": {\"fromName\": \"苏彬\", \"fromPhone\": \"13714138139\", \"fromPhoto\": \"https://file4.kfangcdn.com/prod/005227.jpg-f{size}\", \"fromUserId\": \"S-A-7fbf4c80-d274-4a42-a66a-6cb05f38fabe\"}, \"builtupArea\": [], \"houseSource\": \"\", \"propertyUse\": \"\", \"typeVersion\": \"V1_0_0\", \"businessType\": \"OTHER\", \"houseShortId\": \"\", \"fromTimeStamp\": 1655609966002, \"houseExpandId\": \"\", \"startEntrance\": \"MESSAGE_LIST\", \"toUserIsOnline\": true, \"toUserOnlineStatus\": \"PUSH_ONLINE\"}";
        JSONObject jsonObject = JSON.parseObject(message);
        System.out.println("message++++++++++\n" + jsonObject.toString());
        String message1 = "{\"type\": \"TEXT\", \"msgId\": 1000173551, \"source\": \"APP\", \"toName\": \"冰淇淋\", \"content\": \"方便电话沟通吗13714138139\", \"houseId\": \"\", \"gardenId\": \"\", \"jumpLink\": \"\", \"toUserId\": \"K-B-845\", \"apartment\": \"\", \"fromPhone\": \"13714138139\", \"houseType\": \"\", \"fromUserId\": \"S-A-7fbf4c80-d274-4a42-a66a-6cb05f38fabe\", \"gardenName\": \"\", \"jpushParam\": {\"fromName\": \"苏彬\", \"fromPhone\": \"13714138139\", \"fromPhoto\": \"https://file4.kfangcdn.com/prod/005227.jpg-f{size}\", \"fromUserId\": \"S-A-7fbf4c80-d274-4a42-a66a-6cb05f38fabe\"}, \"builtupArea\": [], \"houseSource\": \"\", \"propertyUse\": \"\", \"typeVersion\": \"V1_0_0\", \"businessType\": \"OTHER\", \"houseShortId\": \"\", \"fromTimeStamp\": 1655609966002, \"houseExpandId\": \"\", \"startEntrance\": \"MESSAGE_LIST\", \"toUserIsOnline\": true, \"toUserOnlineStatus\": \"PUSH_ONLINE\"}";
        // message = message.replaceAll("-f\\{size}", StringUtil.EMPTY);
        ImNotifyMessage jsonObject1 = JsonUtil.toJavaObject(message1, ImNotifyMessage.class);
        System.out.println("message1++++++++++\n" + StringUtil.formatString(jsonObject1));
        String message2 = "{\"id\": \"4746f4f0946c4c34a891af8952e2f7b8\", \"date\": 1655913662, \"orgId\": \"000240\", \"isMain\": \"YES\", \"messageId\": 1655913662, \"changeDiff\": {\"currentCompanyOrgId\":\"000060\",\"currentLevelId\":\"001\",\"currentOrgId\":\"000240\",\"currentPositionId\":\"0265\",\"oldCompanyOrgId\":\"000060\",\"oldLevelId\":\"001\",\"oldOrgId\":\"000169\",\"oldPositionId\":\"0265\"}, \"positionId\": \"0265\", \"operateType\": \"UPDATE\"}";
        PersonPositionMessage entity2 = JsonUtil.toJavaObject(message2, PersonPositionMessage.class);
        System.out.println("message2++++++++++\n" + StringUtil.formatString(entity2));
        String message3 = "{\"id\": \"4746f4f0946c4c34a891af8952e2f7b8\", \"date\": 1655913662, \"orgId\": \"000240\", \"isMain\": \"YES\", \"messageId\": 1655913662, \"changeDiff\": \"{\\\"currentCompanyOrgId\\\":\\\"000060\\\",\\\"currentLevelId\\\":\\\"001\\\",\\\"currentOrgId\\\":\\\"000240\\\",\\\"currentPositionId\\\":\\\"0265\\\",\\\"oldCompanyOrgId\\\":\\\"000060\\\",\\\"oldLevelId\\\":\\\"001\\\",\\\"oldOrgId\\\":\\\"000169\\\",\\\"oldPositionId\\\":\\\"0265\\\"}\", \"positionId\": \"0265\", \"operateType\": \"UPDATE\"}";
        PersonPositionMessage entity3 = JsonUtil.toJavaObject(message3, PersonPositionMessage.class);
        System.out.println("message3++++++++++\n" + StringUtil.formatString(entity3));
        String message4 = "{\"id\": \"000216\", \"date\": 1655967600000, \"orgId\": \"001667\", \"messageId\": 1655967600923, \"operateType\": \"UPDATE\"}";
        HousePreMergeBaseMessage entity4 = JsonUtil.toJavaObject(message4, HousePreMergeBaseMessage.class);
        System.out.println("message4++++++++++\n" + StringUtil.formatString(entity4));

        String message5 = "{\"remark\": \"南康日豪花园\\n\",\"clueCreateTime\":1656542148707}";
        TimeDemo timeDemo = JsonUtil.toJavaObject(message5, TimeDemo.class);
        System.out.println("message5++++++++++\n" + StringUtil.formatString(timeDemo));
    }

    @Data
    static class TimeDemo {
        private Date clueCreateTime;
        private String remark;
    }

    @Test
    void toJSONBytes() {
        byte[] kuugas = JsonUtil.toJSONBytes(KuugaModel.builder().name("kuuga").sex(1).build());
        System.out.println(Arrays.toString(kuugas));
    }

    @Test
    void toMap() {
        Map<String, Object> map = JsonUtil.toMap("{\"name\":\"kuuga1\",\"sex\":1}");
        System.out.println(map);
    }

    @Test
    void toMapList() {
        List<Map<String, Object>> list = JsonUtil.toMapList("[{\"name\":\"kuuga1\",\"sex\":1},{\"name\":\"kuuga2\",\"sex\":2},{\"name\":\"kuuga3\",\"sex\":3}]");
        System.out.println(list);
    }

    @Test
    void toList() {
        List<KuugaModel> list = JsonUtil.toList("[{\"name\":\"kuuga1\",\"sex\":1},{\"name\":\"kuuga2\",\"sex\":2},{\"name\":\"kuuga3\",\"sex\":3}]", KuugaModel.class);
        System.out.println(list);
    }

    @Test
    void toList1() {
        List<KuugaModel> list = JsonUtil.toList("[{\"name\":\"kuuga1\",\"sex\":1},{\"name\":\"kuuga2\",\"sex\":2},{\"name\":\"kuuga3\",\"sex\":3}]".getBytes(), KuugaModel.class);
        System.out.println(list);
    }

    @Test
    void parseArray() {
        KuugaModel[] kuugaModels = JsonUtil.parseArray("[{\"name\":\"kuuga1\",\"sex\":1},{\"name\":\"kuuga2\",\"sex\":2},{\"name\":\"kuuga3\",\"sex\":3}]", KuugaModel.class);
        Arrays.stream(kuugaModels).forEach(kuugaModel -> System.out.println(kuugaModel.toString()));

        System.out.println(StringUtil.repeat('-', 50));

        String str = "[\"id\", 123]";
        JSONArray jsonArray = JSON.parseArray(str);
        String name = jsonArray.getString(0);
        int id = jsonArray.getIntValue(1);

        System.out.println(name);
        System.out.println(id);
    }

    @Test
    void parseArray1() {
        JSONArray jsonArray = JsonUtil.parseArray("[{\"name\":\"kuuga1\",\"sex\":1},{\"name\":\"kuuga2\",\"sex\":2},{\"name\":\"kuuga3\",\"sex\":3}]");
        jsonArray.stream().iterator().forEachRemaining(json -> System.out.println(json.toString()));
    }

    @Test
    void parseObject() {
        JSONObject jsonObject = JsonUtil.parseObject("{\"name\":\"kuuga1\"}");
        System.out.println(jsonObject.toString());

        int id = JSON.parseObject("{\"id\":123}").getIntValue("id");
        System.out.println(id);
    }

    @Test
    void testParseObject() {
        JSONObject jsonObject = JsonUtil.parseObject("{\"name\":\"kuuga1\"}".getBytes(StandardCharsets.UTF_8));
        System.out.println(jsonObject.toString());
    }

    @Test
    void getString() {
        System.out.println(JsonUtil.getString("{\"name\":\"kuuga1\"}", "name"));
    }

    @Test
    void getInteger() {
        System.out.println(JsonUtil.getInteger("{\"name\":\"\"}", "name"));
    }

    @Test
    void getIntValue() {
        System.out.println(JsonUtil.getIntValue("{\"name\":\"1\"}", "name"));
    }

    @Test
    void getDouble() {
        System.out.println(JsonUtil.getDouble("{\"name\":\"\"}", "name"));
    }

    @Test
    void getDoubleValue() {
        System.out.println(JsonUtil.getDoubleValue("{\"name\":\"1D\"}", "name"));
    }

    @Test
    void getLong() {
        System.out.println(JsonUtil.getLong("{\"name\":\"\"}", "name"));
    }

    @Test
    void getLongValue() {
        System.out.println(JsonUtil.getLongValue("{\"name\":\"1\"}", "name"));
    }

    @Test
    void getFloat() {
        System.out.println(JsonUtil.getFloat("{\"name\":\"\"}", "name"));
    }

    @Test
    void getFloatValue() {
        System.out.println(JsonUtil.getFloatValue("{\"name\":\"1\"}", "name"));
    }

    @Test
    void getBigDecimal() {
        System.out.println(JsonUtil.getBigDecimal("{\"name\":\"1\"}", "name"));
    }

    @Test
    void getBigInteger() {
        System.out.println(JsonUtil.getBigInteger("{\"name\":\"1\"}", "name"));
    }

    @Test
    void getDate() {
        System.out.println(JsonUtil.getDate("{\"name\":\"2022-05-20\"}", "name"));
    }

    @Test
    void getBoolean() {
        System.out.println(JsonUtil.getBoolean("{\"name\":\"\"}", "name"));
        System.out.println(JsonUtil.getBoolean("{\"name\":\"true\"}", "name"));
        System.out.println(JsonUtil.getBoolean("{\"name\":\"1\"}", "name"));
        System.out.println(JsonUtil.getBoolean("{\"name\":\"0\"}", "name"));
    }

    @Test
    void getBooleanValue() {
        System.out.println(JsonUtil.getBooleanValue("{\"name\":\"true\"}", "name"));
        System.out.println(JsonUtil.getBooleanValue("{\"name\":\"1\"}", "name"));
        System.out.println(JsonUtil.getBooleanValue("{\"name\":\"0\"}", "name"));
    }

    @Test
    void testGet() {
        Map<Integer, String> map = MapUtil.newHashMap();
        map.put(1, "kuuga1");
        map.put(2, "kuuga2");
        JSONObject jsonObject = new JSONObject(map);
        System.out.println(jsonObject.get(2));
    }

    @Test
    void of() {
        JSONReader of = JsonUtil.of("{\"name\":\"kuuga1\",\"sex\":1}");
        System.out.println(of);
    }

    @Test
    void of1() {
        byte[] bytes = "{\"name\":\"kuuga1\",\"sex\":1}".getBytes(StandardCharsets.UTF_8);
        JSONReader of = JsonUtil.of(bytes);
        KuugaModel kuugaModel = of.read(KuugaModel.class);
        System.out.println(StringUtil.formatString(kuugaModel));
    }

    @Test
    void of2() {
        char[] charArray = "{\"name\":\"kuuga1\",\"sex\":1}".toCharArray();
        JSONReader of = JsonUtil.of(charArray);
        KuugaModel kuugaModel = of.read(KuugaModel.class);
        System.out.println(StringUtil.formatString(kuugaModel));
    }

    @Test
    void ofJSONB() {
        KuugaModel Kuuga = KuugaModel.builder().name("kuuga").sex(1).build();
        byte[] bytes = JsonByteUtil.toBytes(Kuuga);
        JSONReader of = JsonUtil.ofJSONB(bytes);
        KuugaModel kuugaModel = of.read(KuugaModel.class);
        System.out.println(StringUtil.formatString(kuugaModel));
    }

    @Test
    void test11() {
        String s = "{\"content\": [{\"id\": \"c805dcdcf2b24f01a38df84c344af3e8\", \"ext\": \"257044\", \"phone\": \"18504233203\", \"content\": \"416341\", \"createId\": \"admin\", \"modifyId\": \"admin\", \"createTime\": 1655434828294, \"orgManager\": false, \"returnTime\": 1655434824000, \"operatorOrgManager\": false, \"orgTreeAllianceView\": false, \"returnAdditionalInfo\": true, \"orgTreeAllianceCodePrefix\": \"000000-000001-000002\", \"operatorDataPermissionCheck\": false}], \"messageId\": 108490576907484597, \"orgManager\": false, \"operatorOrgManager\": false, \"orgTreeAllianceView\": false, \"orgTreeAllianceCodePrefix\": \"000000-000001-000002\", \"operatorDataPermissionCheck\": false}";
        System.out.println(s);
    }

}