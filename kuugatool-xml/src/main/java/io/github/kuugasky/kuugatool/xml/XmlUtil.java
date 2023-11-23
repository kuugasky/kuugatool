package io.github.kuugasky.kuugatool.xml;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.dom4j.*;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * xml工具类
 *
 * @author kuuga
 * @since 2017-09-26
 */
public final class XmlUtil {

    /**
     * 读取xml文件内容
     *
     * @param path filePath
     * @return exception
     * @throws Exception Exception
     */
    public static String readXmlFile(String path) throws Exception {
        File file = new File(path);
        try (FileInputStream fis = new FileInputStream(file); FileChannel fc = fis.getChannel()) {

            ByteBuffer bb = ByteBuffer.allocate(Long.valueOf(file.length()).intValue());
            // fc向buffer中读入数据
            fc.read(bb);
            bb.flip();
            return new String(bb.array(), StandardCharsets.UTF_8);
        }
    }

    /**
     * xml转json
     *
     * @param xmlStr xmlStr
     * @return JSONObject
     */
    public static JSONObject toJson(String xmlStr) throws DocumentException {
        Document doc = DocumentHelper.parseText(xmlStr);
        JSONObject json = new JSONObject();
        dom4j2Json(doc.getRootElement(), json);
        return json;
    }

    /**
     * xml转json
     *
     * @param element element
     * @param json    json
     */
    private static void dom4j2Json(Element element, JSONObject json) {
        // 如果是属性
        for (Attribute attribute : element.attributes()) {
            if (!isEmpty(attribute.getValue())) {
                json.put("@" + attribute.getName(), attribute.getValue());
            }
        }
        List<Element> chdEl = ObjectUtil.cast(element.elements());
        // 如果没有子元素,只有一个值
        if (chdEl.isEmpty() && !isEmpty(element.getText())) {
            json.put(element.getName(), element.getText());
        }

        // 有子元素
        for (Element e : chdEl) {
            // 子元素也有子元素
            if (!e.elements().isEmpty()) {
                JSONObject jsonObject = new JSONObject();
                dom4j2Json(e, jsonObject);
                Object o = json.get(e.getName());
                if (o != null) {
                    JSONArray jsonArray = null;
                    // 如果此元素已存在,则转为jsonArray
                    if (o instanceof JSONObject childJsonObject) {
                        json.remove(e.getName());
                        jsonArray = new JSONArray();
                        jsonArray.add(childJsonObject);
                        jsonArray.add(childJsonObject);
                    }
                    if (o instanceof JSONArray) {
                        jsonArray = (JSONArray) o;
                        jsonArray.add(jsonObject);
                    }
                    json.put(e.getName(), jsonArray);
                } else {
                    if (!jsonObject.isEmpty()) {
                        json.put(e.getName(), jsonObject);
                    }
                }
            }
            // 子元素没有子元素
            else {
                for (Attribute attribute : element.attributes()) {
                    if (!isEmpty(attribute.getValue())) {
                        json.put("@" + attribute.getName(), attribute.getValue());
                    }
                }
                if (!e.getText().isEmpty()) {
                    json.put(e.getName(), e.getText());
                }
            }
        }
    }

    public static boolean isEmpty(String str) {
        return StringUtil.isEmpty(str) || "null".equals(str);
    }

}
