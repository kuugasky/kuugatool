package io.github.kuugasky.kuugatool.crypto;

import io.github.kuugasky.kuugatool.core.string.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * 不使用自动生成的rsa.从类目录下的pem中获取pub,private key. 提高效率.
 * 当accessToken出现大范围泄露时， 直接修改类目录下的key文件可以让之前颁发的token失效.
 *
 * @author kuuga
 * @since 2019-12-20 17:48
 */
@Slf4j
public class RSAPemUtil {

    private static final String RSA_2048_PEM = "rsa_2048.pem";
    private static final String RSA_2048_PUB_PEM = "rsa_2048_pub.pem";

    private final static char UNDER_LINE = '-';
    private final static String ENTER = "\r";

    /**
     * 获取预生成好的公钥
     * fixme 这里需要把获取到的PublicKey字符串缓存起来，避免每次都进行io读取
     *
     * @return RSAPublicKey
     */
    public static RSAPublicKey defaultPublicKry() {
        try {
            return getPublicKeyFromString(getPublicKey(new ClassPathResource(RSA_2048_PUB_PEM).getInputStream()));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("rsa public key create error.");
        }
    }

    /**
     * 获取预生成好的私钥
     * fixme 这里需要把获取到的PrivateKey字符串缓存起来，避免每次都进行io读取
     *
     * @return RSAPrivateKey
     */
    public static RSAPrivateKey defaultPrivateKey() {
        try {
            return getPrivateKeyFromString(getPrivateKey(new ClassPathResource(RSA_2048_PEM).getInputStream()));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException("rsa private key create error.");
        }
    }

    private static RSAPublicKey getPublicKeyFromString(String key) throws GeneralSecurityException {
        String publicKeyPem = key;
        publicKeyPem = publicKeyPem.replace("-----BEGIN PUBLIC KEY-----n", StringUtil.EMPTY);
        publicKeyPem = publicKeyPem.replace("-----END PUBLIC KEY-----", StringUtil.EMPTY);
        byte[] encoded = Base64.getMimeDecoder().decode(publicKeyPem);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return (RSAPublicKey) kf.generatePublic(new X509EncodedKeySpec(encoded));
    }

    private static String getPrivateKey(InputStream is) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder str = new StringBuilder();
        br.readLine();
        String s = br.readLine();
        while (s.charAt(0) != UNDER_LINE) {
            str.append(s).append(ENTER);
            s = br.readLine();
        }
        return str.toString();
    }

    private static RSAPrivateKey getPrivateKeyFromString(String key) throws GeneralSecurityException {
        String privateKeyPem = key;
        privateKeyPem = privateKeyPem.replace("-----BEGIN PRIVATE KEY-----n", StringUtil.EMPTY);
        privateKeyPem = privateKeyPem.replace("-----END PRIVATE KEY-----", StringUtil.EMPTY);
        byte[] encoded = Base64.getMimeDecoder().decode(privateKeyPem);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        return (RSAPrivateKey) kf.generatePrivate(keySpec);
    }

    private static String getPublicKey(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String s = br.readLine();
        StringBuilder publicKey = new StringBuilder();
        while (s != null) {
            if (s.charAt(0) != UNDER_LINE) {
                publicKey.append(s).append(ENTER);
            }
            s = br.readLine();
        }
        return publicKey.toString();
    }

}
