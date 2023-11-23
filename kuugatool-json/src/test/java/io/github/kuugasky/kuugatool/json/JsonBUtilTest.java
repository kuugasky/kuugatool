package io.github.kuugasky.kuugatool.json;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONWriter;
import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import io.github.kuugasky.kuugatool.json.entity.KuugaModel;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class JsonBUtilTest {

    @Test
    void toBytes() {
        KuugaModel Kuuga = KuugaModel.builder().name("kuuga").sex(1).enabled(true).build();
        byte[] bytes = JsonByteUtil.toBytes(Kuuga);
        System.out.println(Arrays.toString(bytes));
    }

    @Test
    void toBytes1() {
        KuugaModel Kuuga = KuugaModel.builder().name("kuuga").sex(1).build();
        byte[] bytes = JsonByteUtil.toBytes(Kuuga, JSONWriter.Feature.WriteNulls);
        System.out.println(Arrays.toString(bytes));
        System.out.println(JsonByteUtil.parseObject(bytes, KuugaModel.class));
    }

    @Test
    void parseObject() {
        KuugaModel Kuuga = KuugaModel.builder().name("kuuga").sex(1).enabled(true).build();
        KuugaModel kuugaModel = JsonByteUtil.parseObject(JsonByteUtil.toBytes(Kuuga), KuugaModel.class);
        System.out.println(kuugaModel);
    }

    @Test
    void parseArray() {
        KuugaModel Kuuga = KuugaModel.builder().name("kuuga").sex(1).build();
        KuugaModel kuuga2 = KuugaModel.builder().name("kuuga0").sex(0).build();

        List<KuugaModel> kuugaModels = ListUtil.newArrayList(Kuuga, kuuga2);
        // feature对list无效
        byte[] bytes2 = JsonByteUtil.toBytes(kuugaModels, JSONWriter.Feature.WriteNulls);

        JSONArray jsonArray = JsonByteUtil.parseArray(bytes2);
        jsonArray.forEach(System.out::println);
    }

    @Test
    void parseArray1() {
        KuugaModel Kuuga = KuugaModel.builder().name("kuuga").sex(1).enabled(true).build();
        KuugaModel kuuga2 = KuugaModel.builder().name("kuuga0").sex(0).enabled(false).build();

        List<KuugaModel> kuugaModels = ListUtil.newArrayList(Kuuga, kuuga2);
        byte[] bytes2 = JsonByteUtil.toBytes(kuugaModels);
        System.out.println(Arrays.toString(bytes2));

        List<KuugaModel> kuugaModels1 = JsonByteUtil.parseArray(bytes2, KuugaModel.class);
        kuugaModels1.forEach(kuugaModel -> System.out.println(StringUtil.formatString(Kuuga)));
    }

}