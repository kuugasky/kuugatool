package io.github.kuugasky.kuugatool.json.entity;

import com.alibaba.fastjson2.JSON;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import io.github.kuugasky.kuugatool.json.JsonUtil;
import lombok.Data;
import org.junit.jupiter.api.Test;

/**
 * JsonErrorDemoTest
 *
 * @author kuuga
 * @since 2022/6/20 15:49
 */
public class JsonErrorDemoTest {

    @Test
    void println() {
        KuugaDemo kuugaDemo = new KuugaDemo();
        kuugaDemo.setName("kuuga");
        kuugaDemo.setAge(11);
        KuugaDemo.KuugaField kuugaField = new KuugaDemo.KuugaField();
        kuugaField.setFieldName("fieldName");
        kuugaField.setFieldAge(22);
        kuugaDemo.setKuugaField(kuugaField);
        System.out.println(JSON.toJSONString(kuugaDemo));
    }

    @Test
    void trueFormat() {
        String message = "{\"age\":11,\"kuugaField\":{\"fieldAge\":22,\"fieldName\":\"fieldName\"},\"name\":\"Kuuga\"}";
        KuugaDemo kuugaDemo = JSON.parseObject(message, KuugaDemo.class);
        System.out.println(StringUtil.formatString(kuugaDemo));
    }

    @Test
    void errorFormat() {
        String message = "{\"age\":11,\"kuugaField\":\"{\"fieldAge\":22,\"fieldName\":\"fieldName\"}\",\"name\":\"Kuuga\"}";
        KuugaDemo kuugaDemo = JSON.parseObject(message, KuugaDemo.class);
        // illegal character f 不能解析成员变量对象用String传输的格式
        System.out.println(StringUtil.formatString(kuugaDemo));
    }

    @Test
    void errorFormat1() {
        String message = "{\"age\":11,\"kuugaField\":\"{\"fieldAge\":22,\"fieldName\":\"fieldName\"}\",\"name\":\"Kuuga\"}";
        KuugaDemo kuugaDemo = JSON.to(KuugaDemo.class, message);
        // illegal character f 不能解析成员变量对象用String传输的格式
        System.out.println(StringUtil.formatString(kuugaDemo));
    }

    @Test
    void specialHandling() {
        // String message = "{\"age\":11,\"kuugaField\":\"{\"fieldAge\":22,\"fieldName\":\"fieldName\"}\",\"name\":\"Kuuga\"}";
        String message = "{\"age\":11,\"kuugaField\":{\"fieldAge\":22,\"fieldName\":\"fieldName\"},\"name\":\"Kuuga\"}";
        KuugaDemo kuugaDemo = JsonUtil.toJavaObject(message, KuugaDemo.class);
        // 正常 特殊处理 移除成员变量对象json字符串前后双引号&去除转义字符
        System.out.println(StringUtil.formatString(kuugaDemo));
    }

    @Test
    void test() {
        String url = "{\"kuugaUrlDemo\":{\"url\":\"http://www.baidu.com.jpg-f{size}\"}}";
        KuugaUrl kuugaUrl = JSON.parseObject(url, KuugaUrl.class);
        System.out.println(StringUtil.formatString(kuugaUrl));
    }

}

@Data
class KuugaUrl {
    private KuugaUrlDemo kuugaUrlDemo;

    @Data
    static class KuugaUrlDemo {
        private String url;
    }
}

@Data
class KuugaDemo {
    private String name;
    private int age;
    private KuugaField kuugaField;

    @Data
    static class KuugaField {
        private String fieldName;
        private int fieldAge;
    }

}
