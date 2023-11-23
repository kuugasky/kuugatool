package io.github.kuugasky.kuugatool.json;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;

/**
 * json验证工具类
 *
 * @author kuuga
 */
public final class JsonValidateUtil {

    static {
        JSON.config(JSONWriter.Feature.WriteEnumUsingToString);
        JSON.config(JSONReader.Feature.SupportSmartMatch);
    }

    public static boolean isValid(String text) {
        return JSON.isValid(text);
    }

    public static boolean isValid(byte[] bytes) {
        return JSON.isValid(bytes);
    }

    public static boolean isValidArray(String text) {
        return JSON.isValidArray(text);
    }

    public static boolean isValidArray(byte[] bytes) {
        return JSON.isValidArray(bytes);
    }

    public static boolean isValidObject(String text) {
        return JSON.isValidObject(text);
    }

    public static boolean isValidObject(byte[] bytes) {
        return JSON.isValidObject(bytes);
    }

}
