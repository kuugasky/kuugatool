package io.github.kuugasky.kuugatool.json;

import com.alibaba.fastjson2.*;
import com.alibaba.fastjson2.filter.Filter;
import com.alibaba.fastjson2.filter.SimplePropertyPreFilter;
import io.github.kuugasky.kuugatool.core.collection.ArrayUtil;
import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.collection.MapUtil;
import io.github.kuugasky.kuugatool.core.constants.KuugaConstants;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 简介：Fastjson是一个Java语言编写的高性能功能完善的JSON库。
 * 高性能：fastjson采用独创的算法，将parse的速度提升到极致，超过所有json库，包括曾经号称最快的jackson。并且还超越了google的二进制协议protocol buf。
 * 支持标准：Fastjson完全支持http://json.org的标准，也是官方网站收录的参考实现之一。
 * 功能强大：支持各种JDK类型。包括基本类型、JavaBean、Collection、Map、Enum、泛型等。 支持循环引用。
 * 无依赖：不需要例外额外的jar，能够直接跑在JDK上。
 * 支持范围广：支持JDK 5、JDK 6、Android、阿里云手机等环境。
 * 开源：Apache License 2.0 代码托管在github.org上，项目地址是 <a href="https://github.com/AlibabaTech/fastjson">fastjson</a>`
 * 测试充分：fastjson有超过1500个testcase，每次构建都会跑一遍，丰富的测试场景保证了功能稳定。
 *
 * @author kuuga
 */
public final class JsonUtil {

    private static final String ERROR_PREFIX_FORMAT = "\"{\"";
    private static final String ERROR_SUFFIX_FORMAT = "\"}\"";

    static {
        JSON.config(JSONWriter.Feature.WriteEnumUsingToString);
        JSON.config(JSONReader.Feature.SupportSmartMatch);
    }

    // toJsonString ==================================================================================================================================

    /**
     * 将object转json字符串
     *
     * @param object object
     * @return JSON字符串 {key1:v1,...}
     */
    public static String toJsonString(Object object) {
        if (object == null) {
            return null;
        } else if (object instanceof Number || object instanceof String) {
            return object.toString();
        }
        return JSON.toJSONString(object);
    }

    /**
     * 将bean转json字符串
     *
     * @param object   对象
     * @param features JSONWriter.Feature array
     * @return jsonString
     */
    static String toJsonString(Object object, JSONWriter.Feature... features) {
        return JSON.toJSONString(object, features);
    }

    /**
     * 将object转json字符串，移除指定属性，支持自定义序列化格式
     *
     * @param object                  对象
     * @param simplePropertyPreFilter 自定义过滤，可包含or不包含
     * @param features                features
     * @return jsonString
     */
    public static String toJsonString(Object object, SimplePropertyPreFilter simplePropertyPreFilter, JSONWriter.Feature... features) {
        return JSON.toJSONString(object, simplePropertyPreFilter, features);
    }

    /**
     * 将bean转json字符串
     *
     * @param object   对象
     * @param format   日期时间格式化
     * @param features JSONWriter.Feature array
     * @return jsonString
     */
    static String toJsonString(Object object, String format, JSONWriter.Feature... features) {
        return JSON.toJSONString(object, format, features);
    }

    /**
     * 将bean转json字符串
     *
     * @param object   对象
     * @param format   日期时间格式化
     * @param filters  过滤器
     * @param features JSONWriter.Feature array
     * @return jsonString
     */
    static String toJsonString(Object object, String format, Filter[] filters, JSONWriter.Feature... features) {
        return JSON.toJSONString(object, format, filters, features);
    }

    // toJsonString扩展 ==================================================================================================================================

    /**
     * 将object转json字符串，移除指定属性
     *
     * @param object            对象
     * @param includesFieldName 自定义包含的字段名数组
     * @return jsonString
     */
    public static String toJsonStringWithIncludes(Object object, String... includesFieldName) {
        if (ArrayUtil.hasItem(includesFieldName)) {
            SimplePropertyPreFilter excludeFilter = new SimplePropertyPreFilter();
            excludeFilter.getIncludes().addAll(List.of(includesFieldName));
            return JSON.toJSONString(object, excludeFilter);
        } else {
            return JSON.toJSONString(object);
        }
    }

    /**
     * 将object转json字符串，移除指定属性
     *
     * @param object             对象
     * @param excludesFieldNames 自定义排除掉的字段名数组
     * @return jsonString
     */
    public static String toJsonStringWithExcludes(Object object, String... excludesFieldNames) {
        if (ArrayUtil.hasItem(excludesFieldNames)) {
            SimplePropertyPreFilter excludeFilter = new SimplePropertyPreFilter();
            excludeFilter.getExcludes().addAll(List.of(excludesFieldNames));
            return JSON.toJSONString(object, excludeFilter);
        } else {
            return JSON.toJSONString(object);
        }
    }

    // toJavaObject ==================================================================================================================================

    /**
     * 将JSON字符串转为Bean
     *
     * @param jsonStr       jsonStr
     * @param typeReference 引用类型
     * @return Bean
     */
    public static <T> T toJavaObject(String jsonStr, TypeReference<T> typeReference) {
        return JSONObject.parseObject(jsonStr, typeReference);
    }

    /**
     * 将JSONObject转为Bean
     *
     * @param jsonObj jsonObj
     * @param clazz   clazz
     * @return Bean
     */
    public static <T> T toJavaObject(JSONObject jsonObj, Class<T> clazz) {
        return JSON.to(clazz, jsonObj);
    }

    /**
     * 将JSON字符串转为Bean
     *
     * @param jsonStr jsonStr
     * @param tClass  tClass
     * @return Bean
     */
    public static <T> T toJavaObject(String jsonStr, Class<T> tClass) {
        if (StringUtil.isEmpty(jsonStr)) {
            return null;
        }

        if (!JsonValidateUtil.isValid(jsonStr)) {
            // json对象内某个属性是对象，但是以字符串形式存在，存在多个转义字符\
            jsonStr = jsonStr.replaceAll("\\\\", StringUtil.EMPTY);
        }

        if (!JsonValidateUtil.isValid(jsonStr)) {
            return null;
        }

        if (!jsonStr.contains(KuugaConstants.BACKSLASH)) {
            return JSON.to(tClass, jsonStr);
        }

        JSONObject jsonObject = JSON.parseObject(jsonStr);

        jsonObject.forEach((k, v) -> {
            String keyOf = String.valueOf(k);
            String valueOf = String.valueOf(v);
            valueOf = valueOf.replaceAll("\\\\", StringUtil.EMPTY);
            // json对象内某个属性是对象，但是以字符串形式存在，且字符串前后{}带上了双引号"{}"
            if (StringUtil.startsWith(valueOf, ERROR_PREFIX_FORMAT)) {
                valueOf = "{\"" + StringUtil.removeStart(valueOf, ERROR_PREFIX_FORMAT);
                if (StringUtil.endsWith(valueOf, ERROR_SUFFIX_FORMAT)) {
                    valueOf = StringUtil.removeStart(valueOf, ERROR_SUFFIX_FORMAT) + "\"}";
                }
                jsonObject.put(keyOf, valueOf);
            }
        });

        return toJavaObject(jsonObject, tClass);
    }

    /**
     * 将JSON字符串bytes转为Bean
     *
     * @param utf8Bytes utf8Bytes
     * @param clazz     clazz
     * @return Bean
     */
    public static <T> T toJavaObject(byte[] utf8Bytes, Class<T> clazz) {
        return JSON.to(clazz, utf8Bytes);
    }

    // parseObject ==================================================================================================================================

    /**
     * 将JSON字符串转为Bean
     *
     * @param text  jsonStr
     * @param clazz clazz
     * @return Bean
     */
    @Deprecated
    @SuppressWarnings("all")
    public static <T> T parseObject(String text, Class<T> clazz) {
        return JSON.parseObject(text, clazz);
    }

    /**
     * 将JSON字符串bytes转为Bean
     *
     * @param utf8Bytes utf8Bytes
     * @param clazz     clazz
     * @return Bean
     */
    @Deprecated
    public static <T> T parseObject(byte[] utf8Bytes, Class<T> clazz) {
        return JSON.parseObject(utf8Bytes, clazz);
    }

    /**
     * 解析json字符串为json对象
     *
     * @param text json字符串
     * @return json对象
     */
    public static JSONObject parseObject(String text) {
        return JSON.parseObject(text);
    }

    /**
     * 解析json字符串为json对象
     *
     * @param bytes json字符串bytes
     * @return json对象
     */
    public static JSONObject parseObject(byte[] bytes) {
        return JSON.parseObject(bytes);
    }


    // toJSONBytes ==================================================================================================================================

    /**
     * 将Java对象输出成UTF8编码的byte[]
     *
     * @param object object
     * @return bytes
     */
    @SuppressWarnings("all")
    public static byte[] toJSONBytes(Object object) {
        return JSON.toJSONBytes(object);
    }

    // toMap ==================================================================================================================================

    /**
     * 将JSON字符串转为Map
     *
     * @param jsonStr jsonStr
     * @return Map
     */
    public static Map<String, Object> toMap(String jsonStr) {
        Map<String, Object> map = MapUtil.newHashMap();
        JSONObject jsonObj = JSON.parseObject(jsonStr);
        Set<String> keySet = jsonObj.keySet();

        for (String key : keySet) {
            map.put(key, jsonObj.get(key));
        }
        return map;
    }

    /**
     * 将JSON字符串转为Map集合
     *
     * @param jsonStr jsonStr
     * @return Map集合
     */
    @SuppressWarnings("unchecked")
    public static <V> List<Map<String, V>> toMapList(String jsonStr) {
        List<Map<String, V>> list = ListUtil.newArrayList();

        JSONObject jsonObj;
        Set<String> keySet;

        JSONArray jsonArr = JSON.parseArray(jsonStr);
        for (Object obj : jsonArr) {
            Map<String, V> map = MapUtil.newHashMap();

            jsonObj = JSON.parseObject(JSON.toJSONString(obj));
            keySet = jsonObj.keySet();

            for (String key : keySet) {
                map.put(key, (V) jsonObj.get(key));
            }
            list.add(map);
        }
        return list;
    }

    // toList ==================================================================================================================================

    /**
     * 将JSON字符串转为Bean集合
     *
     * @param jsonStr       jsonStr
     * @param componentType 组件类型
     * @return Bean集合
     */
    public static <E> List<E> toList(String jsonStr, Class<E> componentType) {
        return JSON.parseArray(jsonStr, componentType);
    }

    /**
     * 将JSON字符串转为Bean集合
     *
     * @param bytes         jsonStrBytes
     * @param componentType 组件类型
     * @return Bean集合
     */
    public static <E> List<E> toList(byte[] bytes, Class<E> componentType) {
        return JSON.parseArray(bytes, componentType);
    }

    // toArray ==================================================================================================================================

    /**
     * 将集合转为数组
     *
     * @param <E>           数组元素类型
     * @param collection    集合
     * @param componentType 组件类型
     * @return 数组
     */
    public static <E> E[] toArray(List<E> collection, Class<E> componentType) {
        return collection.toArray(ArrayUtil.newArray(componentType, 0));
    }

    // parseArray ==================================================================================================================================

    /**
     * 将JSON字符串转为Bean数组
     *
     * @param jsonStr       jsonStr
     * @param componentType componentType
     * @return Bean数组
     */
    public static <E> E[] parseArray(String jsonStr, Class<E> componentType) {
        return toArray(toList(jsonStr, componentType), componentType);
    }

    /**
     * 解析json字符串为json数组
     *
     * @param text json字符串
     * @return json数组
     */
    public static JSONArray parseArray(String text) {
        return JSON.parseArray(text);
    }

    // path ====================================================================================================================================================

    // public static Object path(String text, String path) {
    //     // 缓存起来重复使用能提升性能
    //     JSONPath jsonPath = JSONPath.of(path);
    //     JSONReader parser = JSONReader.of(text);
    //     return jsonPath.extract(parser);
    // }

    // jsonReader ====================================================================================================================================================

    /**
     * 构造基于String输入的JSONReader
     *
     * @param str str
     * @return JSONReader
     */
    public static JSONReader of(String str) {
        return JSONReader.of(str);
    }

    /**
     * 构造基于utf8编码byte数组输入的JSONReader
     *
     * @param utf8Bytes utf8Bytes
     * @return JSONReader
     */
    public static JSONReader of(byte[] utf8Bytes) {
        return JSONReader.of(utf8Bytes);
    }

    /**
     * 构造基于char[]输入的JSONReader
     *
     * @param chars chars
     * @return JSONReader
     */
    public static JSONReader of(char[] chars) {
        return JSONReader.of(chars);
    }

    /**
     * 构造基于json格式byte数组输入的JSONReader
     *
     * @param jsonbBytes jsonbBytes
     * @return JSONReader
     */
    @SuppressWarnings("all")
    public static JSONReader ofJSONB(byte[] jsonbBytes) {
        return JSONReader.ofJSONB(jsonbBytes);
    }

    // getValue ====================================================================================================================================================

    public static String getString(String text, String key) {
        return parseObject(text).getString(key);
    }

    public static Integer getInteger(String text, String key) {
        return parseObject(text).getInteger(key);
    }

    public static int getIntValue(String text, String key) {
        return parseObject(text).getIntValue(key);
    }

    public static Double getDouble(String text, String key) {
        return parseObject(text).getDouble(key);
    }

    public static double getDoubleValue(String text, String key) {
        return parseObject(text).getDoubleValue(key);
    }

    public static Long getLong(String text, String key) {
        return parseObject(text).getLong(key);
    }

    public static long getLongValue(String text, String key) {
        return parseObject(text).getLongValue(key);
    }

    public static Float getFloat(String text, String key) {
        return parseObject(text).getFloat(key);
    }

    public static float getFloatValue(String text, String key) {
        return parseObject(text).getFloatValue(key);
    }

    public static BigDecimal getBigDecimal(String text, String key) {
        return parseObject(text).getBigDecimal(key);
    }

    public static BigInteger getBigInteger(String text, String key) {
        return parseObject(text).getBigInteger(key);
    }

    public static Date getDate(String text, String key) {
        return parseObject(text).getDate(key);
    }

    public static Boolean getBoolean(String text, String key) {
        return parseObject(text).getBoolean(key);
    }

    public static boolean getBooleanValue(String text, String key) {
        return parseObject(text).getBooleanValue(key);
    }

    public static Object get(String text, Object key) {
        JSONObject jsonObject = parseObject(text);
        if (ObjectUtil.nonNull(jsonObject)) {
            return jsonObject.get(String.valueOf(key));
        } else {
            return null;
        }
    }

}
