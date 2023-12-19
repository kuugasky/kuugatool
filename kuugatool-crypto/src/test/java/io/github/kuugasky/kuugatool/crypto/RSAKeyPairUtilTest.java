package io.github.kuugasky.kuugatool.crypto;

import io.github.kuugasky.kuugatool.core.encoder.UrlCodeUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.junit.jupiter.api.Test;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

/**
 * rsa非对称密钥对工具类Test
 */
class RSAKeyPairUtilTest {

    @Test
    void defaultPublicKry() {
        // 获取预生成好的公钥：从resources/rsa_2048_pub.pem读取
        RSAPublicKey rsaPublicKey = RSAKeyPairUtil.defaultPublicKry();
        System.out.println(rsaPublicKey);
        System.out.println(StringUtil.repeatNormal());
        System.out.println("参数:" + rsaPublicKey.getParams());
        System.out.println("系数:" + rsaPublicKey.getModulus());
        System.out.println("公有指数:" + rsaPublicKey.getPublicExponent());
    }

    @Test
    void defaultPrivateKey() {
        // 获取预生成好的私钥：从resources/rsa_2048.pem读取
        RSAPrivateKey rsaPrivateKey = RSAKeyPairUtil.defaultPrivateKey();
        System.out.println(rsaPrivateKey);
        System.out.println(StringUtil.repeatNormal());
        System.out.println("参数:" + rsaPrivateKey.getParams());
        System.out.println("系数:" + rsaPrivateKey.getModulus());
        System.out.println("私有指数:" + rsaPrivateKey.getPrivateExponent());
    }

    @Test
    void encode() throws Exception {
        // 获取公钥
        RSAPublicKey rsaPublicKey = RSAKeyPairUtil.defaultPublicKry();
        // 获取私钥
        RSAPrivateKey rsaPrivateKey = RSAKeyPairUtil.defaultPrivateKey();
        // 明文
        String plaintext = "Kuuga is a men";
        // 公钥加密
        String encrypted = RSAUtil.encrypt(plaintext, rsaPublicKey);
        System.out.println("加密串Base64：" + encrypted);
        System.out.println("加密串字符串URLEncoder：" + UrlCodeUtil.encode(encrypted));
        // 私钥解密
        String decrypt = RSAUtil.decrypt(encrypted, rsaPrivateKey);
        System.out.println("解密串：" + decrypt);
    }

    @Test
    void rsaSignature() throws Exception {
        RSAPublicKey rsaPublicKey = RSAKeyPairUtil.defaultPublicKry();
        RSAPrivateKey rsaPrivateKey = RSAKeyPairUtil.defaultPrivateKey();
        // 明文
        String plainText = "1";
        // rsa非对称私钥加密签名
        String signature = RSAUtil.sign(plainText, rsaPrivateKey);
        System.out.println("私钥加密生成签名：" + signature);
        // rsa公钥验签
        boolean verifyStatus = RSAUtil.verify(plainText, signature, rsaPublicKey);
        System.out.println("公钥验证签名是否正确：" + verifyStatus);
    }

    @Test
    void rsaSignature1() throws Exception {
        // 根据默认种子生成密钥对
        RSAKeyPairUtil.RSAKeyPair rsaKeyPair = RSAKeyPairUtil.initRsaKeyPair();
        // 公钥
        RSAPublicKey rsaPublicKey = rsaKeyPair.getRsaPublicKey();
        System.out.println("公钥:" + rsaPublicKey.toString());
        // 私钥
        RSAPrivateKey rsaPrivateKey = rsaKeyPair.getRsaPrivateKey();
        // 明文
        String plainText = "kuuga";
        // 私钥签名
        String signature = RSAUtil.sign(plainText, rsaPrivateKey);
        System.out.println("私钥加密生成签名：" + signature);
        // 公钥验签
        boolean b = RSAUtil.verify(plainText, signature, rsaPublicKey);
        System.out.println("公钥验证签名是否正确：" + b);
    }

    @Test
    void createPem() throws Exception {
        RSAKeyPairUtil.RSAKeyPair rsaKeyPair = RSAKeyPairUtil.initRsaKeyPair();
        RSAPublicKey rsaPublicKey = rsaKeyPair.getRsaPublicKey();
        byte[] encoded = rsaPublicKey.getEncoded();
        RSAKeyPairUtil.saveKeyToFile("/Users/hyuga/Downloads/rsa.pem", encoded);
    }

}