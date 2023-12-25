package io.github.kuugasky.kuugatool.nacos.parse;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import io.github.kuugasky.kuugatool.core.collection.MapUtil;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import io.github.kuugasky.kuugatool.json.JsonUtil;
import io.github.kuugasky.kuugatool.nacos.parse.enums.NacosConfigParser;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * 枚举转换门面类
 * (外观模式)
 *
 * @author pengqinglong
 * @since 2022/2/16
 */
@Slf4j
public class NacosConfigReadConvertor {

    /**
     * 单例模式-容器单例
     */
    private static final Map<String, Map<String, String>> PARSES = new HashMap<>();

    /**
     * nacos枚举映射对应配置dataId
     */
    private static final String DATA_ID = "metaMapping.json";

    /**
     * nacos对应的配置group
     */
    private static final String GROUP_ID = "kuuga";

    static {
        /*
         * 类第一次被加载进jvm时触发
         * 将解析器全部初始化
         */
        for (MetaSourceEnum source : MetaSourceEnum.values()) {
            try {
                final BiConsumer<String, Map<String, Map<String, String>>> biConsumer = new BiConsumer<>() {
                    @Override
                    public void accept(String nacosConfig, Map<String, Map<String, String>> result) {
                        JSONArray objects = JsonUtil.parseArray(nacosConfig);

                        String sourcePinyin = source.getPinyin();
                        result.put(sourcePinyin, getKeyValues(sourcePinyin, objects));
                    }

                    private Map<String, String> getKeyValues(String sourcePinyin, JSONArray objects) {
                        Map<String, String> keyValues = MapUtil.newHashMap();
                        JSONObject jsonObject = (JSONObject) objects.stream().filter(object -> ((JSONObject) object).containsKey(sourcePinyin)).findFirst().orElse(null);
                        if (ObjectUtil.isNull(jsonObject)) {
                            return MapUtil.emptyMap();
                        }
                        JSONObject keyValueJsons = (JSONObject) jsonObject.get(sourcePinyin);
                        keyValueJsons.keySet().forEach(key -> {
                            try {
                                Object values = keyValueJsons.get(key);
                                if (values instanceof String value) {
                                    keyValues.put(value, key);
                                } else if (values instanceof JSONArray valueArrays) {
                                    valueArrays.forEach(value -> keyValues.put((String) value, key));
                                }
                            } catch (Exception e) {
                                log.error(e.getMessage(), e);
                            }
                        });
                        return keyValues;
                    }
                };

                NacosConfigParser.start(DATA_ID, GROUP_ID, biConsumer, PARSES);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("HouseMetaEnumConvertor.static error:{}", e.getMessage(), e);
            }
        }
    }

    public String getValue(String source, String key) {
        return PARSES.get(source).get(key);
    }

}