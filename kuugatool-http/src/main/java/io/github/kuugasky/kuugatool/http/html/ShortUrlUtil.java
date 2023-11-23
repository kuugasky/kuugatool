package io.github.kuugasky.kuugatool.http.html;

import io.github.kuugasky.kuugatool.core.date.DateUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import io.github.kuugasky.kuugatool.http.KuugaHttpCustom;
import io.github.kuugasky.kuugatool.http.KuugaHttpSingleton;
import io.github.kuugasky.kuugatool.json.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

/**
 * 短网址工具类
 * author kuuga 2016-11-25
 * version 1.0
 *
 * @author kuuga
 */
public final class ShortUrlUtil {

    private static final Logger logger = LoggerFactory.getLogger(ShortUrlUtil.class);

    private static final String SUO_IM_URL = "http://home.suo.im/ucenter/api.htm";
    private static final String SUO_IM_KEY = "5d3a65b0b1a9c7012dfcce4d@287a18a9bcb79c91e912f4c0eb958f4c";

    private static final String SUO_IM_TXT_URI = "http://suo.im/api.htm?url=%s&key=" + SUO_IM_KEY + "&expireDate=%s";
    private static final String SUO_IM_JSON_URI = "http://suo.im/api.htm?format=json&url=%s&key=" + SUO_IM_KEY + "&expireDate=%s";
    private static final String SUO_IM_JSONP_URI = "http://suo.im/api.htm?format=jsonp&url=%s&callback=%s&key=" + SUO_IM_KEY + "&expireDate=%s";

    public static void main(String[] args) {
        System.out.println(ShortUrlUtil.getJsonShortUrl("https://bbp.sbwl.com/mobile/h5/evaluateStart.html?code=kuuga"));
        System.out.println(ShortUrlUtil.getTxtShortUrl("https://bbp.sbwl.com/mobile/h5/evaluateStart.html?code=kuuga"));
        System.out.println(ShortUrlUtil.getJsonpShortUrl("https://bbp.sbwl.com/mobile/h5/evaluateStart.html?code=kuuga"));
    }

    public static String getJsonpShortUrl(String longUrl) {
        Date expireDate = DateUtil.moreOrLessMonths(DateUtil.now(), 1);
        return getJsonpShortUrl(longUrl, "result", expireDate);
    }

    public static String getJsonpShortUrl(String longUrl, String callbackName) {
        Date expireDate = DateUtil.moreOrLessMonths(DateUtil.now(), 1);
        return getJsonpShortUrl(longUrl, callbackName, expireDate);
    }

    public static String getJsonpShortUrl(String longUrl, String callbackName, Date expireDate) {
        return getSuoImShortUrl(longUrl, expireDate, SuoImType.JSONP, callbackName);
    }

    public static String getTxtShortUrl(String longUrl) {
        Date expireDate = DateUtil.moreOrLessMonths(DateUtil.now(), 1);
        return getTxtShortUrl(longUrl, expireDate);
    }

    public static String getTxtShortUrl(String longUrl, Date expireDate) {
        return getSuoImShortUrl(longUrl, expireDate, SuoImType.TXT, null);
    }

    /**
     * 商办这边不指定失效日期，则默认一个月
     *
     * @param longUrl 长连接
     * @return 短链接
     */
    public static String getJsonShortUrl(String longUrl) {
        Date expireDate = DateUtil.moreOrLessMonths(DateUtil.now(), 1);
        return getJsonShortUrl(longUrl, expireDate);
    }

    /**
     * 第三方接口默认生成短链接有效期三个月
     *
     * @param longUrl 长连接
     * @return 短链接
     */
    public static String getJsonShortUrl(String longUrl, Date expireDate) {
        return getSuoImShortUrl(longUrl, expireDate, SuoImType.JSON, null);
    }

    private static String getSuoImShortUrl(String longUrl, Date expireDate, SuoImType suoImType, String callbackName) {
        if (StringUtil.isEmpty(longUrl)) {
            return null;
        }
        String expireDateStr;
        // 如果失效时间有效，则按失效时间生成短链接，否则默认失效时间是一个月
        if (expireDate != null && DateUtil.before(DateUtil.now(), expireDate)) {
            expireDateStr = DateUtil.formatDate(expireDate);
        } else {
            expireDateStr = DateUtil.formatDate(DateUtil.moreOrLessMonths(DateUtil.now(), 1));
        }
        String encoderLongUrl = URLEncoder.encode(longUrl, StandardCharsets.UTF_8);
        try {
            String suoImRequest = getSuoImRequestUrl(suoImType, encoderLongUrl, expireDateStr, callbackName);
            logger.info("调用suo.im短链接网址：{}", suoImRequest);
            KuugaHttpCustom.HttpResult httpResult = KuugaHttpSingleton.init().get(suoImRequest);
            String responseJson = httpResult.getContent();
            return getResponseUrl(responseJson, suoImType);
        } catch (Exception e) {
            logger.error("短链接接口调用异常，e:{}", e.getMessage(), e);
            return null;
        }
    }

    private static String getResponseUrl(String responseJson, SuoImType suoImType) {
        if (StringUtil.isEmpty(responseJson)) {
            logger.error("短链接接口返回空数据！");
            return null;
        }
        return switch (suoImType) {
            case TXT, JSONP -> responseJson;
            case JSON -> {
                Map<String, Object> responseObjectMap = JsonUtil.toMap(responseJson);
                if (StringUtil.hasText(responseObjectMap.get("err"))) {
                    logger.error("短链接接口调用失败，错误信息:{}", responseObjectMap.get("err"));
                    yield null;
                }
                yield responseObjectMap.get("url").toString();
            }
        };
    }

    private static String getSuoImRequestUrl(SuoImType suoImType, String encoderLongUrl, String expireDateStr, String callbackName) {
        return switch (suoImType) {
            case TXT -> String.format(SUO_IM_TXT_URI, encoderLongUrl, expireDateStr);
            case JSONP -> {
                callbackName = StringUtil.isEmpty(callbackName) ? "result" : callbackName;
                yield String.format(SUO_IM_JSONP_URI, encoderLongUrl, callbackName, expireDateStr);
            }
            case JSON -> String.format(SUO_IM_JSON_URI, encoderLongUrl, expireDateStr);
        };
    }

}

enum SuoImType {
    /**
     *
     */
    TXT,
    JSON,
    JSONP
}

