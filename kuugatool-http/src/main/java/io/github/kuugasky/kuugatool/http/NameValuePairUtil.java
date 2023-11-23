package io.github.kuugasky.kuugatool.http;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.collection.MapUtil;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.List;
import java.util.Map;

/**
 * NameValuePairUtil
 *
 * @author kuuga
 * @since 2022-05-24 15:25:12
 */
public class NameValuePairUtil {

    /**
     * 通过name和value构建NameValuePair
     *
     * @param name  name
     * @param value value
     * @return NameValuePair
     */
    public static NameValuePair of(String name, String value) {
        return new BasicNameValuePair(name, value);
    }

    /**
     * 将Map转为NameValuePair集合,将过滤空键或空值
     *
     * @param map Map对象
     * @return NameValuePair集合
     */
    public static List<NameValuePair> toList(Map<String, Object> map) {
        if (MapUtil.isEmpty(map)) {
            return ListUtil.emptyList();
        }
        List<NameValuePair> nameValuePairs = ListUtil.newArrayList(map.size());

        map.entrySet().stream().filter(entry -> StringUtil.hasText(entry.getKey(), entry.getValue()))
                .forEach(entry -> {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    BasicNameValuePair nameValuePair = new BasicNameValuePair(key, StringUtil.toString(value));
                    nameValuePairs.add(nameValuePair);
                });

        return nameValuePairs;
    }

    /**
     * 将Map转为NameValuePair集合,将过滤空键或空值
     *
     * @param object object对象
     * @return NameValuePair集合
     */
    public static List<NameValuePair> toList(Object object) {
        return ObjectUtil.isNull(object) ?
                ListUtil.emptyList() :
                toList(ObjectUtil.toMap(object));
    }

}
