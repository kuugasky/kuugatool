package io.github.kuugasky.kuugatool.json;

import com.alibaba.fastjson2.*;
import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;

import java.util.List;

/**
 * JsonByteUtil
 *
 * @author kuuga
 * @since 2022-05-20 17:45:41
 */
public class JsonByteUtil {

    static {
        JSON.config(JSONWriter.Feature.WriteEnumUsingToString);
        JSON.config(JSONReader.Feature.SupportSmartMatch);
    }

    /**
     * 将Java对象输出成jsonb格式的byte[]
     *
     * @param object object
     * @return bytes
     */
    public static byte[] toBytes(Object object) {
        return JSONB.toBytes(object);
    }

    /**
     * 将Java对象输出成jsonb格式的byte[]
     *
     * @param object  object
     * @param feature feature
     * @return bytes
     */
    public static byte[] toBytes(Object object, JSONWriter.Feature feature) {
        return JSONB.toBytes(object, feature);
    }

    /**
     * 将jsonb格式的byte[]解析成Java对象
     *
     * @param jsonbBytes  jsonbBytes
     * @param objectClass objectClass
     * @param <T>         T
     * @return T
     */
    public static <T> T parseObject(byte[] jsonbBytes, Class<T> objectClass) {
        return JSONB.parseObject(jsonbBytes, objectClass, JSONReader.Feature.SupportClassForName);
    }

    /**
     * 将jsonb格式的byte[]解析成Java对象
     *
     * @param jsonbBytes jsonbBytes
     * @return T
     */
    public static JSONArray parseArray(byte[] jsonbBytes) {
        return JSONB.parseArray(jsonbBytes);
    }

    /**
     * 将jsonb格式的byte[]解析成Java对象
     *
     * @param jsonbBytes jsonbBytes
     * @return T
     */
    public static <T> List<T> parseArray(byte[] jsonbBytes, Class<T> objectClass) {
        JSONArray objects = JSONB.parseArray(jsonbBytes);
        if (ObjectUtil.isNull(objectClass)) {
            return ListUtil.emptyList();
        }
        List<T> result = ListUtil.newArrayList(objects.size());
        objects.forEach(object -> result.add(JsonUtil.toJavaObject((JSONObject) object, objectClass)));
        return result;
    }

}
