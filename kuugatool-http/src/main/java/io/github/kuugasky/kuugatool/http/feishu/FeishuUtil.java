package io.github.kuugasky.kuugatool.http.feishu;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import io.github.kuugasky.kuugatool.core.collection.MapUtil;
import io.github.kuugasky.kuugatool.core.date.DateUtil;
import io.github.kuugasky.kuugatool.core.encoder.Base64Util;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import io.github.kuugasky.kuugatool.http.KuugaHttp;
import io.github.kuugasky.kuugatool.http.KuugaHttpCustom;
import io.github.kuugasky.kuugatool.http.KuugaHttpSingleton;
import io.github.kuugasky.kuugatool.json.JsonUtil;
import org.apache.http.HttpHeaders;
import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * FeishuUtil
 * <br>
 * <a href="https://open.feishu.cn/document/ukTMukTMukTM/ucTM5YjL3ETO24yNxkjN">飞书发送消息</a>
 *
 * @author kuuga
 * @since 2022/12/8-12-08 10:54
 */
public class FeishuUtil {

    private static final Logger logger = LoggerFactory.getLogger(FeishuUtil.class);

    private static final KuugaHttpCustom HTTP_CLIENT = KuugaHttpSingleton.init();

    /**
     * webhook url
     */
    private static final String URL = "https://open.feishu.cn/open-apis/bot/v2/hook/";
    private static final String SUCCESS = "success";
    public static final String STATUS_MESSAGE = "StatusMessage";

    static {
        Map<String, Object> reader = MapUtil.newHashMap();
        reader.put(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
        HTTP_CLIENT.requestHeader(reader);
    }

    /**
     * 发送消息(可能会抛出异常)
     */
    public static void sendText(String accessToken, String secret, String text) {
        try {
            String url = URL + accessToken;
            String requestJson = getRequestJson(text, secret);
            logger.info("发送飞书请求:{}", requestJson);
            KuugaHttp.HttpResult post = HTTP_CLIENT.post(url, requestJson);
            String responseJson = post.getContent();
            logger.info("发送飞书响应:{}", responseJson);
            JSONObject jsonObject = JSON.parseObject(responseJson);
            String status = jsonObject.getString(STATUS_MESSAGE);
            if (StringUtil.isEmpty(status) || !SUCCESS.equals(status)) {
                logger.error("发送飞书接口异常，响应结果：{}", responseJson);
            }
        } catch (Exception e) {
            logger.error("发送飞书接口异常, {}", e.getMessage(), e);
        }
    }

    /**
     * 请求
     */
    private static String getRequestJson(String text, String secret) throws Exception {
        Map<String, String> requestMap = MapUtil.newHashMap();
        long timestamp = DateUtil.currentTimeSeconds();
        String content = JsonUtil.toJsonString(MapUtil.newHashMap("text", text));
        requestMap.put("msg_type", "text");
        requestMap.put("content", content);
        requestMap.put("timestamp", timestamp + "");
        requestMap.put("sign", sign(secret, timestamp));
        return JSON.toJSONString(requestMap);
    }

    /**
     * 参数签名
     *
     * @param secret    密钥
     * @param timestamp 时间戳
     * @return 签名
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     * @throws InvalidKeyException      InvalidKeyException
     */
    private static String sign(String secret, long timestamp) throws NoSuchAlgorithmException, InvalidKeyException {
        // 把timestamp+"\n"+密钥当做签名字符串
        String stringToSign = timestamp + "\n" + secret;
        // 使用HmacSHA256算法计算签名
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(stringToSign.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
        byte[] signData = mac.doFinal(new byte[]{});
        return Base64Util.encode(signData);
    }

}

