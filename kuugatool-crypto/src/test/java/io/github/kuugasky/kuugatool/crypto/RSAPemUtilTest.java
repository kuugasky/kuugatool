package io.github.kuugasky.kuugatool.crypto;

import io.github.kuugasky.kuugatool.core.encoder.UrlCodeUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.junit.jupiter.api.Test;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class RSAPemUtilTest {

    @Test
    public void defaultPublicKry() {
        System.out.println(RSAPemUtil.defaultPublicKry());
    }

    @Test
    public void defaultPrivateKey() {
        System.out.println(RSAPemUtil.defaultPrivateKey());
    }

    @Test
    public void encode() throws Exception {
        RSAPublicKey rsaPublicKey = RSAPemUtil.defaultPublicKry();
        RSAPrivateKey rsaPrivateKey = RSAPemUtil.defaultPrivateKey();
        // 公钥加密
        String kuuga_is_a_men = RSAUtil.encrypt("Kuuga is a men", rsaPublicKey);
        System.out.println("加密串Base64：" + kuuga_is_a_men);
        System.out.println("加密串字符串URLEncoder：" + UrlCodeUtil.encode(kuuga_is_a_men));
        // 私钥解密
        String decrypt = RSAUtil.decrypt(kuuga_is_a_men, rsaPrivateKey);
        System.out.println("解密串：" + decrypt);
    }

    @Test
    public void rsaSignature() throws Exception {
        RSAPublicKey rsaPublicKey = RSAPemUtil.defaultPublicKry();
        RSAPrivateKey rsaPrivateKey = RSAPemUtil.defaultPrivateKey();
        String signature = RSAUtil.sign("1", rsaPrivateKey);
        System.out.println("私钥加密生成签名：" + signature);
        boolean b = RSAUtil.verify(StringUtil.EMPTY, signature, rsaPublicKey);
        System.out.println("公钥验证签名是否正确：" + b);
    }

    @Test
    public void rsaSignature1() throws Exception {
        RSAUtil.RSAKeyPair rsaKeyPair = RSAUtil.initKey();
        // 公钥
        PublicKey rsaPublicKey = rsaKeyPair.getPublicKey();
        // 私钥
        PrivateKey rsaPrivateKey = rsaKeyPair.getPrivateKey();
        String signature = RSAUtil.sign(StringUtil.EMPTY, rsaPrivateKey);
        System.out.println("私钥加密生成签名：" + signature);
        boolean b = RSAUtil.verify("1", signature, rsaPublicKey);
        System.out.println("公钥验证签名是否正确：" + b);
    }

}