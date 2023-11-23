package io.github.kuugasky.kuugatool.crypto;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.JWTVerifier;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * TokenUtil
 *
 * @author kuuga
 * @since 2023/3/14-03-14 10:29
 */
@Slf4j
public class TokenUtil {

    /**
     * 默认过期时间 15分钟
     */
    private static final long EXPIRE_TIME_OF_SECONDS = 15 * 60;

    /**
     * 默认私钥
     */
    private static final String DEFAULT_TOKEN_SECRET = "Kuuga@1990";
    private static final String TYPE_STR = "Type";
    private static final String JWT_STR = "Jwt";
    private static final String ALG_STR = "alg";
    private static final String HS_256_STR = "HS256";

    /**
     * 签名
     *
     * @param map 声明参数
     * @return token
     */
    public static String sign(Map<String, Object> map) {
        return sign(DEFAULT_TOKEN_SECRET, EXPIRE_TIME_OF_SECONDS, map);
    }

    /**
     * 签名
     *
     * @param tokenSecret token密钥
     * @param map         声明参数
     * @return token
     */
    public static String sign(String tokenSecret, Map<String, Object> map) {
        return sign(tokenSecret, EXPIRE_TIME_OF_SECONDS, map);
    }

    /**
     * 签名
     *
     * @param expireTimeOfSeconds token失效时间(单位：秒)
     * @param map                 声明参数
     * @return token
     */
    public static String sign(long expireTimeOfSeconds, Map<String, Object> map) {
        return sign(DEFAULT_TOKEN_SECRET, expireTimeOfSeconds, map);
    }

    /**
     * 生成签名，15分钟过期
     * 根据内部改造，支持6中类型，Integer,Long,Boolean,Double,String,Date
     *
     * @param tokenSecret         token密钥
     * @param expireTimeOfSeconds token失效时间(单位：秒)
     * @param map                 声明参数
     * @return token
     */
    public static String sign(String tokenSecret, long expireTimeOfSeconds, Map<String, Object> map) {
        try {
            // 私钥和加密算法
            Algorithm algorithm = Algorithm.HMAC256(tokenSecret);
            // 设置头部信息
            Map<String, Object> header = new HashMap<>(2);
            header.put(TYPE_STR, JWT_STR);
            header.put(ALG_STR, HS_256_STR);
            // 返回token字符串
            JWTCreator.Builder builder = JWT.create()
                    .withHeader(header)
                    // 发证时间
                    .withIssuedAt(new Date());

            if (expireTimeOfSeconds > 0) {
                // 设置过期时间
                Date date = new Date(System.currentTimeMillis() + expireTimeOfSeconds * 1000);
                // 过期时间
                builder.withExpiresAt(date);
            }

            map.forEach((key, value) -> {
                if (value instanceof Integer) {
                    builder.withClaim(key, (Integer) value);
                } else if (value instanceof Long) {
                    builder.withClaim(key, (Long) value);
                } else if (value instanceof Boolean) {
                    builder.withClaim(key, (Boolean) value);
                } else if (value instanceof String) {
                    builder.withClaim(key, String.valueOf(value));
                } else if (value instanceof Double) {
                    builder.withClaim(key, (Double) value);
                } else if (value instanceof Date) {
                    builder.withClaim(key, (Date) value);
                }
            });
            // 密钥
            return builder.sign(algorithm);
        } catch (Exception e) {
            log.error("token签名生成异常:{}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * 检验token是否正确
     *
     * @param token **token**
     * @return boolean
     */
    public static boolean verify(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(DEFAULT_TOKEN_SECRET);
            JWTVerifier verifier = com.auth0.jwt.JWT.require(algorithm).build();
            verifier.verify(token);
            return true;
        } catch (Exception e) {
            log.error("token签名验证异常:{}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * 获取用户自定义Claim集合
     *
     * @param token token
     * @return map
     */
    public static Map<String, Claim> getClaims(String token) {
        Algorithm algorithm = Algorithm.HMAC256(DEFAULT_TOKEN_SECRET);
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token).getClaims();
    }

    /**
     * 获取声明值
     *
     * @param token token
     * @param key   claimKey
     * @return claimValue
     */
    public static Claim getClaimValue(String token, String key) {
        return getClaims(token).get(key);
    }

    /**
     * 获取过期时间
     *
     * @param token token
     * @return expiresAt
     */
    public static Date getExpiresAt(String token) {
        Algorithm algorithm = Algorithm.HMAC256(DEFAULT_TOKEN_SECRET);
        return JWT.require(algorithm).build().verify(token).getExpiresAt();
    }

    /**
     * 获取jwt发布时间
     */
    public static Date getIssuedAt(String token) {
        Algorithm algorithm = Algorithm.HMAC256(DEFAULT_TOKEN_SECRET);
        return JWT.require(algorithm).build().verify(token).getIssuedAt();
    }

    /**
     * 验证token是否失效
     *
     * @param token token
     * @return true:过期   false:没过期
     */
    public static boolean isExpired(String token) {
        try {
            final Date expiration = getExpiresAt(token);
            return expiration.before(new Date());
        } catch (TokenExpiredException e) {
            return true;
        }
    }

    /**
     * 直接Base64解密获取header内容
     *
     * @param token token
     * @return header info
     */
    public static String getHeaderByBase64(String token) {
        if (StringUtil.isEmpty(token)) {
            return null;
        } else {
            byte[] headerByte = Base64.getDecoder().decode(token.split("\\.")[0]);
            return new String(headerByte);
        }
    }

    /**
     * 直接Base64解密获取payload内容
     *
     * @param token token
     * @return payload
     */
    public static String getPayloadByBase64(String token) {
        if (StringUtil.isEmpty(token)) {
            return null;
        } else {
            byte[] payloadByte = Base64.getDecoder().decode(token.split("\\.")[1]);
            return new String(payloadByte);
        }
    }

}
