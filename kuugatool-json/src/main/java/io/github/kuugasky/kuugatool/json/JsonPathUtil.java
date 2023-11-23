package io.github.kuugasky.kuugatool.json;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONPath;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;

/**
 * @author kuuga
 * @since 2022-05-20 18:00:52
 */
public class JsonPathUtil {

    static {
        JSON.config(JSONWriter.Feature.WriteEnumUsingToString);
        JSON.config(JSONReader.Feature.SupportSmartMatch);
    }

    public static Object extract(String json, String path) {
        return JSONPath.extract(json, path);
    }

}
