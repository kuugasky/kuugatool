package io.github.kuugasky.kuugatool.json;

import com.alibaba.fastjson2.*;
import com.alibaba.fastjson2.util.TypeUtils;
import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.constants.KuugaConstants;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Json解析器
 *
 * @author kuuga
 */
public final class KuugaJsonParser {

    public static final String STRING = "{}";

    static {
        JSON.config(JSONWriter.Feature.WriteEnumUsingToString);
        JSON.config(JSONReader.Feature.SupportSmartMatch);
    }

    private String jsonStr;

    private boolean abnormalInhibition = false;

    private Object defaultObj;

    /**
     * 如果解析不到值，则返回defaultObj[仅对对象json toJava/toList有效]
     *
     * @param defaultObj 默认值
     * @return KuugaJsonParser
     */
    public KuugaJsonParser defaultObject(Object defaultObj) {
        this.defaultObj = defaultObj;
        this.abnormalInhibition = true;
        return this;
    }

    public KuugaJsonParser defaultClass(Class<?> clazz) {
        try {
            this.defaultObj = clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            e.printStackTrace();
            this.defaultObj = null;
        }
        this.abnormalInhibition = true;
        return this;
    }

    /**
     * json字符串转KuugaJsonParser
     *
     * @param jsonStr json字符串
     * @return KuugaJsonParser
     */
    public static KuugaJsonParser of(String jsonStr) {
        return new KuugaJsonParser(jsonStr);
    }

    /**
     * 私有构造器
     *
     * @param jsonStr json字符串
     */
    private KuugaJsonParser(String jsonStr) {
        if (!JsonValidateUtil.isValid(jsonStr)) {
            throw new JSONException("KuugaJsonParser error,异常json串:" + jsonStr);
        }
        this.jsonStr = jsonStr;
    }

    /**
     * 解析jsonStr中某个key的值
     *
     * @param key key
     * @return key对应值封装的KuugaJsonParser
     */
    public KuugaJsonParser parsingObject(String key) {
        try {
            if (JsonValidateUtil.isValid(jsonStr)) {
                jsonStr = JsonUtil.parseObject(jsonStr).getString(key);
            } else {
                throw new RuntimeException("json解析异常，请检查json路径");
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (abnormalInhibition) {
                jsonStr = KuugaConstants.EMPTY_JSON;
            } else {
                throw e;
            }
        }
        if (StringUtil.isEmpty(jsonStr)) {
            jsonStr = KuugaConstants.EMPTY_JSON;
        }
        return this;
    }

    /**
     * 解析jsonStr中某个key的数组值
     *
     * @param key key
     * @return key对应数组值封装的KuugaJsonParser
     */
    public KuugaJsonParser parsingArray(String key) {
        try {
            if (JsonValidateUtil.isValid(jsonStr)) {
                jsonStr = JsonUtil.parseObject(jsonStr).getString(key);
            } else {
                throw new RuntimeException("json解析异常，请检查json路径");
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (abnormalInhibition) {
                jsonStr = KuugaConstants.EMPTY_ARRAY;
            } else {
                throw e;
            }
        }
        if (StringUtil.isEmpty(jsonStr)) {
            jsonStr = KuugaConstants.EMPTY_ARRAY;
        }
        return this;
    }

    /**
     * jsonStr转javaObject
     *
     * @param clazz class
     * @param <T>   类型
     * @return 对象
     */
    public <T> T toJavaObject(Class<T> clazz) {
        try {
            return JsonUtil.toJavaObject(jsonStr, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            if (abnormalInhibition) {
                return ObjectUtil.cast(defaultObj);
            } else {
                throw e;
            }
        }
    }

    /**
     * jsonStr转List
     *
     * @param clazz class
     * @param <T>   类型
     * @return 对象
     */
    public <T> List<T> toList(Class<T> clazz) {
        try {
            return JsonUtil.toList(jsonStr, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            if (abnormalInhibition) {
                return ObjectUtil.cast(defaultObj);
            } else {
                throw e;
            }
        }
    }

    /**
     * jsonStr转JsonObjectList
     *
     * @return jsonArray
     */
    public List<JSONObject> toJsonObjects() {
        List<JSONObject> result = ListUtil.newArrayList();
        try {
            JSONArray jsonArray;
            if (STRING.equals(jsonStr)) {
                jsonArray = new JSONArray();
            } else {
                jsonArray = JsonUtil.parseArray(jsonStr);
            }
            for (Object obj : jsonArray) {
                JSONObject jsonObject = JsonUtil.parseObject(JsonUtil.toJsonString(obj));
                result.add(jsonObject);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            if (abnormalInhibition) {
                return ObjectUtil.cast(defaultObj);
            } else {
                throw e;
            }
        }
    }

    /**
     * 取值
     *
     * @return jsonStr
     */
    public String getString() {
        return ObjectUtil.equals(KuugaConstants.EMPTY_JSON, this.jsonStr) ? StringUtil.EMPTY : this.jsonStr;
    }

    /**
     * 取值
     *
     * @return jsonStr布尔值
     */
    public Boolean getBoolean() {
        return ObjectUtil.equals(KuugaConstants.EMPTY_JSON, this.jsonStr) ? null : TypeUtils.cast(jsonStr, Boolean.class);
    }

    /**
     * 取值
     *
     * @return jsonStr int值
     */
    public Integer getInt() {
        return ObjectUtil.equals(KuugaConstants.EMPTY_JSON, this.jsonStr) ? null : TypeUtils.cast(jsonStr, Integer.class);
    }

    /**
     * 取值
     *
     * @return jsonStr double值
     */
    public Double getDouble() {
        return ObjectUtil.equals(KuugaConstants.EMPTY_JSON, this.jsonStr) ? null : TypeUtils.cast(jsonStr, Double.class);
    }

    /**
     * 取值
     *
     * @return jsonStr long值
     */
    public Long getLong() {
        return ObjectUtil.equals(KuugaConstants.EMPTY_JSON, this.jsonStr) ? null : TypeUtils.cast(jsonStr, Long.class);
    }

    /**
     * 取值
     *
     * @return jsonStr float值
     */
    public Float getFloat() {
        return ObjectUtil.equals(KuugaConstants.EMPTY_JSON, this.jsonStr) ? null : TypeUtils.cast(jsonStr, Float.class);
    }

}