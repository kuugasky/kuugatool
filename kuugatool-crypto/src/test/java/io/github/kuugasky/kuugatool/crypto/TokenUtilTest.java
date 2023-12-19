package io.github.kuugasky.kuugatool.crypto;

import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import io.github.kuugasky.kuugatool.core.collection.MapUtil;
import io.github.kuugasky.kuugatool.core.concurrent.daemon.DaemonThread;
import io.github.kuugasky.kuugatool.core.concurrent.pool.ThreadScheduledPool;
import io.github.kuugasky.kuugatool.core.date.DateUtil;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

class TokenUtilTest {

    /**
     * 生成不失效的token
     */
    @Test
    void test4() {
        String sign = TokenUtil.sign(0, MapUtil.newHashMap("1", "2"));
        // 获取声明值
        String value = TokenUtil.getClaimValue(sign, "1").asString();
        // 设置永不失效
        String sign1 = TokenUtil.sign(0, MapUtil.newHashMap("1", "2"));

        System.out.println("sign:" + sign);
        System.out.println("claim value:" + value);

        Assertions.assertEquals(sign, sign1);
        Assertions.assertTrue(TokenUtil.verify(sign1));
    }

    @Test
    void test3() {
        LoginDto loginDto = new LoginDto();
        loginDto.setId(UUID.randomUUID().toString());
        loginDto.setPhone("13800138000");
        loginDto.setName("kuuga");
        loginDto.setCreateTime(DateUtil.now());
        loginDto.setVersion(System.currentTimeMillis());
        String sign = TokenUtil.sign(ObjectUtil.toMap(loginDto));
        System.out.println(sign);
    }

    @Test
    void test1() {
        String sign = TokenUtil.sign(MapUtil.newHashMap("1", "2"));
        System.out.println(TokenUtil.getClaimValue(sign, "1").asString());
    }

    @Test
    void test2() {
        try {
            // 设置过期时间
            Date date = new Date(System.currentTimeMillis() + 100 * 1000);
            // 私钥和加密算法
            Algorithm algorithm = Algorithm.HMAC256("kuugasky");
            // 设置头部信息
            Map<String, Object> header = new HashMap<>(2);
            header.put("typ", "jwt");
            // 返回token字符串
            JWTCreator.Builder builder = com.auth0.jwt.JWT.create()
                    .withHeader(header)
                    // 发证时间
                    .withIssuedAt(new Date())
                    // 过期时间
                    .withExpiresAt(date);
            builder.withClaim("kuuga", "2");
            // 密钥
            String sign = builder.sign(algorithm);
            System.out.println(sign);
            System.out.println(TokenUtil.getClaims(sign));
        } catch (Exception ignored) {
        }
    }

    @Test
    void test() {
        int tenSeconds = 5;
        String sign = TokenUtil.sign(tenSeconds, MapUtil.newHashMap("userName", "kuuga", "password", "e10adc3949ba59abbe56e057f20f883e"));
        System.out.println(sign);
        ThreadScheduledPool.scheduleWithFixedDelay(
                () -> {
                    try {
                        // System.out.println(TokenUtil.verify(sign) + "------>" + TokenUtil.getClaims(sign).get("userName").toString());
                        // System.out.println(TokenUtil.getClaims(sign).get("userName").toString());
                        System.out.println(TokenUtil.getClaimValue(sign, "userName"));
                    } catch (Exception e) {
                        System.err.println(e.getMessage());
                    }
                },
                0, 1, TimeUnit.SECONDS);
        DaemonThread.await();
    }

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>(16);
        map.put("userId", "123456");
        map.put("rose", "admin");
        map.put("integer", 1111);
        map.put("double", 112.222);
        map.put("Long", 112L);
        map.put("bool", true);
        map.put("date", new Date());
        // 生成token
        String token = TokenUtil.sign(map);
        System.out.println("token信息：" + token);
        System.out.println("验证token是否正确：" + TokenUtil.verify(token));
        // 使用方法
        Map<String, Claim> claims = TokenUtil.getClaims(token);
        String userId = claims.get("userId").asString();
        System.out.println("获取token声明中的userId：" + userId);
        System.out.println("获取签发token时间：" + TokenUtil.getIssuedAt(token));
        System.out.println("获取过期时间：" + TokenUtil.getExpiresAt(token));
        System.out.println("检查是否已过期：" + TokenUtil.isExpired(token));
        System.out.println("获取头" + TokenUtil.getHeaderByBase64(token));
        System.out.println("获取负荷" + TokenUtil.getPayloadByBase64(token));

        System.out.println(StringUtil.repeatNormal());

        System.out.println(MapUtil.toString(claims, true));
    }

    @Test
    void test5() {
        String secret = "kuuga";
        String sign = TokenUtil.sign(secret, MapUtil.newHashMap("name", secret));
        String nameValue = TokenUtil.getClaimValue(secret, sign, "name").toString();
        System.out.println(nameValue);
        System.out.println(TokenUtil.verify(secret, sign));
    }


}