package io.github.kuugasky.kuugatool.http.baidu;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import io.github.kuugasky.kuugatool.core.collection.MapUtil;
import io.github.kuugasky.kuugatool.core.number.NumberUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import io.github.kuugasky.kuugatool.http.KuugaHttpCustom;
import io.github.kuugasky.kuugatool.http.KuugaHttpSingleton;
import io.github.kuugasky.kuugatool.json.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 百度地图工具类
 *
 * @author kuuga
 */
public final class BaiduMapUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaiduMapUtil.class);

    private static final String BAIDU_MAP_REQUEST_URL = "http://api.map.baidu.com/place/v2/search";

    private static final String ZERO = "0";

    /**
     * 用于获取线路站点id：http://map.baidu.com/?qt=bsi&c=340
     */
    private final static Map<String, String> LINE_MAP = new HashMap<>() {{
        put("f19e82a86ef8036b33f71d6c", "1号线(罗宝线)(罗湖-机场东)");
        put("428a8a5f6a82e35a9603196c", "2号线(蛇口线)(赤湾-新秀)");
        put("e4dd65fa9be7ff62c07da229", "3号线(龙岗线)(益田-双龙)");
        put("be8206228cf08f16d5851f6c", "4号线(龙华线)(福田口岸-清湖)");
        put("1588b5155551fea63686f4ec", "5号线(环中线)(赤湾-黄贝岭)");
        put("c0fee59aa30e38fb441a32fa", "6号线(光明线)(松岗-科学馆)");
        put("7387535997702bf9b11e3604", "7号线(太安-西丽湖)");
        put("cedf801f22082c2c4657261f", "9号线(前湾-文锦)");
        put("64261537bfa45c6fbda240eb", "10号线(坂田线)(福田口岸-双拥街)");
        put("51a8d3da63047064fa42b2d8", "11号线(福田-碧头)");
    }};

    public static final String RESULTS = "results";
    public static final String STATUS = "status";

    public static void main(String[] args) {
        // System.out.println(getDistanceJson("CblQXfSABCZSEpgk2j4e0o83KoDSvSaX", "地铁站", "114.0639770000", "22.5484560000", 800));
        //
        // String dom = "深圳市高新园";
        // String[] coordinate = getCoordinate("CblQXfSABCZSEpgk2j4e0o83KoDSvSaX", dom);
        // System.out.println("'" + dom + "'的经纬度为：" + Arrays.toString(coordinate));
        // System.err.println("######同步坐标已达到日配额6000限制，请明天再试！#####");

        // System.out.println(getAllCityOfOpenSubway());

        // System.out.println(getMetroLine("340", lineMap));

        // Map<String, String> allCityOfOpenSubway = getAllCityOfOpenSubway();
        // System.out.println(MapUtil.toString(allCityOfOpenSubway, true));
        // 深圳市code：340
        Map<String, Object> metroLine = getMetroLine("340", LINE_MAP);
        System.out.println(MapUtil.toString(metroLine, true));
    }

    /**
     * 获取城市下的所有线路
     *
     * @param cityCode 指定城市CODE
     * @param lineMap  指定线路方向（百度会返回两个方向的同一条线路）
     * @return 地铁线路
     */
    public static Map<String, Object> getMetroLine(String cityCode, Map<String, String> lineMap) {
        String citySubwayRequest = String.format("http://map.baidu.com/?qt=bsi&c=%s&t=123457788", cityCode);
        KuugaHttpCustom.HttpResult httpResult = KuugaHttpSingleton.init().post(citySubwayRequest);
        String citySubwayText = httpResult.getContent();
        JSONObject citySubwayJson = JsonUtil.parseObject(citySubwayText);

        JSONArray subwayArray = JSON.parseArray(citySubwayJson.get("content").toString());
        // 线路map
        Comparator<String> stringComparator = Comparator.comparingInt(obj -> Integer.parseInt(Objects.requireNonNull(NumberUtil.getNumber(obj)).toString()));
        Map<String, Object> lineSortMap = new TreeMap<>(stringComparator);

        subwayArray.forEach(subway -> {
            JSONObject subwayJson = JsonUtil.parseObject(subway.toString());
            String lineUid = subwayJson.get("line_uid").toString();
            String lineSpellName = subwayJson.get("line_name").toString();
            String lineName = lineSpellName.split("\\(")[0];

            if (!lineMap.containsKey(lineUid)) {
                return;
            }
            subwayJson.put("line_syzl_uid", UUID.randomUUID().toString());
            lineSortMap.put(lineName, subwayJson);
        });
        return lineSortMap;
    }

    /**
     * 获取所有已开通地铁的城市编号信息
     *
     * @return info
     */
    private static Map<String, String> getAllCityOfOpenSubway() {
        String allCitySubwayRequest = "http://map.baidu.com/?qt=subwayscity&t=123457788";
        KuugaHttpCustom.HttpResult httpResult = KuugaHttpSingleton.init().post(allCitySubwayRequest);
        String allCitySubwayJsonText = httpResult.getContent();
        JSONObject allCitySubwayJsonObject = JsonUtil.parseObject(allCitySubwayJsonText);
        JSONObject resultJson = JsonUtil.parseObject(allCitySubwayJsonObject.get("result").toString());
        Object errorStatus = resultJson.get("error");
        if (!"0".equalsIgnoreCase(errorStatus.toString())) {
            LOGGER.error("百度地图所有全国城市地铁接口调用失败：" + allCitySubwayJsonText);
        }
        // 百度地图地铁线路版本subwayVersion
        JSONObject subwaysCityJson = JsonUtil.parseObject(allCitySubwayJsonObject.get("subways_city").toString());
        JSONArray allCities = JSON.parseArray(subwaysCityJson.get("cities").toString());

        Map<String, String> cities = MapUtil.newHashMap();
        allCities.forEach(city -> {
            JSONObject cityJson = JsonUtil.parseObject(city.toString());
            cities.put(cityJson.get("cn_name").toString(), cityJson.get("code").toString());
        });
        return cities;
    }

    /**
     * 调用百度地图API获取多少公里内最近的【地铁】信息
     *
     * @param ak          百度地图密钥
     * @param trafficType 交通类型
     * @param longitude   经度
     * @param latitude    纬度
     * @param radius      距离
     * @return 距离json
     */
    public static String getDistanceJson(String ak, String trafficType, String longitude, String latitude, Integer radius) {
        Map<String, Object> param = MapUtil.newHashMap();
        param.put("query", trafficType);
        param.put("radius", radius);
        param.put("output", "io/github/kuugasky/kuugatool/json");
        param.put("scope", 2);
        param.put("filter", "sort_name:distance|sort_rule:1");
        param.put("ak", ak);
        param.put("location", String.format("%s,%s", latitude, longitude));
        KuugaHttpCustom.HttpResult httpResult = KuugaHttpSingleton.init().get(BAIDU_MAP_REQUEST_URL, param);
        String resultJsonStr = httpResult.getContent();

        JSONObject jsonObject = JSON.parseObject(resultJsonStr);
        String status = jsonObject.get(STATUS).toString();
        if (ZERO.equals(status)) {
            Object results = JSON.parseObject(resultJsonStr).get(RESULTS);
            if (results instanceof JSONArray) {
                JSONArray array = (JSONArray) JSON.parseObject(resultJsonStr).get(RESULTS);
                int size = array.size();
                if (size > 0) {
                    Object o = array.get(0);
                    return JsonUtil.toJsonString(o);
                }
            }
        } else {
            LOGGER.error("百度地图接口调用失败：{}", resultJsonStr);
            System.out.println("百度地图接口调用失败：" + resultJsonStr);
            return "-1";
        }
        return "{}";
    }

    /**
     * 调用百度地图API根据地址，获取坐标
     *
     * @param ak      百度地图密钥
     * @param address 地址
     * @return 经纬
     */
    public static String[] getCoordinate(String ak, String address) {
        if (StringUtil.isEmpty(ak)) {
            throw new RuntimeException("百度AK不能为空！");
        }
        if (address != null && !StringUtil.EMPTY.equals(address)) {
            address = address.replaceAll("\\s*", StringUtil.EMPTY).replace("#", "栋");
            String url = "http://api.map.baidu.com/geocoder/v2/?address=" + address + "&output=json&ak=" + ak;
            String json = loadJSON(url);

            if (StringUtil.EMPTY.equalsIgnoreCase(json)) {
                return null;
            }
            JSONObject obj = JSON.parseObject(json);
            if (ZERO.equals(obj.getString("status"))) {
                // 经度
                double lng = obj.getJSONObject("result").getJSONObject("location").getDouble("lng");
                // 纬度
                double lat = obj.getJSONObject("result").getJSONObject("location").getDouble("lat");
                DecimalFormat df = new DecimalFormat("#.######");
                return new String[]{df.format(lng), df.format(lat)};
            }
        }
        return null;
    }

    private static String loadJSON(String url) {
        StringBuilder json = new StringBuilder();
        try {
            URL oracle = new URL(url);
            URLConnection yc = oracle.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), StandardCharsets.UTF_8));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();
        } catch (IOException ignored) {
        }
        return json.toString();
    }

}