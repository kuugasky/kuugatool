package io.github.kuugasky.kuugatool.crypto;

import io.github.kuugasky.kuugatool.core.string.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * RSA(非对称)密钥对工具类
 * <p>
 * 不使用自动生成的rsa，从类目录下的pem中获取public、private key，提高效率。<br>
 * 当accessToken出现大范围泄露时，直接修改类目录下的key文件可以让之前颁发的token失效。<br>
 *
 * <ul>签名
 *  <li>私钥签名</li>
 *  <li>公钥验签</li>
 * </ul>
 * <ul>加解密
 *  <li>公钥加密</li>
 *  <li>私钥解密</li>
 * </ul>
 *
 * @author kuuga
 * @since 2019-12-20 17:48
 */
@Slf4j
public class RSAKeyPairUtil {

    private static final String KEY_ALGORITHM = "RSA";

    /**
     * 默认种子
     */
    public static final String DEFAULT_SEED = "$%^*%^(KUUGA@0410)(ED47d784sde78";

    /**
     * MAX_DECRYPT_BLOCK应等于密钥长度/8（1byte=8bit），所以当密钥位数为2048时，最大解密长度应为256.
     * 128 对应 1024，256对应2048
     */
    private static final int KEY_SIZE = 2048;

    private static final String RSA_2048_PEM = "rsa_2048.pem";
    private static final String RSA_2048_PUB_PEM = "rsa_2048_pub.pem";

    private final static char UNDER_LINE = '-';
    private final static String ENTER = "\r";

    /**
     * 获取预生成好的公钥
     * <p>
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
     * <p>
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

    /**
     * 从输入流中获取公钥对象
     *
     * @param inputStream 输入流
     * @return 公钥对象
     * @throws IOException IO异常
     */
    private static String getPublicKey(InputStream inputStream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
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

    /**
     * 从字符串中获取公钥对象
     *
     * @param key 公钥字符串
     * @return 公钥对象
     * @throws GeneralSecurityException 一般安全例外
     */
    public static RSAPublicKey getPublicKeyFromString(String key) throws GeneralSecurityException {
        String publicKeyPem = key;
        publicKeyPem = publicKeyPem.replace("-----BEGIN PUBLIC KEY-----n", StringUtil.EMPTY);
        publicKeyPem = publicKeyPem.replace("-----END PUBLIC KEY-----", StringUtil.EMPTY);
        byte[] encoded = Base64.getMimeDecoder().decode(publicKeyPem);
        KeyFactory kf = KeyFactory.getInstance(KEY_ALGORITHM);
        return (RSAPublicKey) kf.generatePublic(new X509EncodedKeySpec(encoded));
    }

    /**
     * 从输入流中获取私钥对象
     *
     * @param inputStream 输入流
     * @return 私钥对象
     * @throws IOException IO异常
     */
    private static String getPrivateKey(InputStream inputStream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder str = new StringBuilder();
        br.readLine();
        String s = br.readLine();
        while (s.charAt(0) != UNDER_LINE) {
            str.append(s).append(ENTER);
            s = br.readLine();
        }
        return str.toString();
    }

    /**
     * 从字符串中获取私钥对象
     *
     * @param key 私钥字符串
     * @return 私钥对象
     * @throws GeneralSecurityException 一般安全例外
     */
    public static RSAPrivateKey getPrivateKeyFromString(String key) throws GeneralSecurityException {
        String privateKeyPem = key;
        privateKeyPem = privateKeyPem.replace("-----BEGIN PRIVATE KEY-----n", StringUtil.EMPTY);
        privateKeyPem = privateKeyPem.replace("-----END PRIVATE KEY-----", StringUtil.EMPTY);
        byte[] encoded = Base64.getMimeDecoder().decode(privateKeyPem);
        KeyFactory kf = KeyFactory.getInstance(KEY_ALGORITHM);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        return (RSAPrivateKey) kf.generatePrivate(keySpec);
    }

    // - - - - - - - - - - - - - - - - - - - - RSA 生成秘钥对 - - - - - - - - - - - - - - - - - - - - //

    /**
     * 生成默认密钥对
     *
     * @return 密钥对
     */
    public static RSAKeyPair initRsaKeyPair() throws Exception {
        return initRsaKeyPair(DEFAULT_SEED);
    }

    /**
     * 生成RSA密钥对：若seed为null，那么结果是随机的；若seed不为null且固定，那么结果也是固定的；
     *
     * @param seed 种子
     * @return 密钥对象
     */
    public static RSAKeyPair initRsaKeyPair(String seed) throws Exception {
        KeyPairGenerator keygen = KeyPairGenerator.getInstance(KEY_ALGORITHM);

        // 如果指定seed，那么secureRandom结果是一样的，所以生成的公私钥也永远不会变
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.setSeed(seed.getBytes());
        // Modulus size must range from 512 to 1024 and be a multiple of 64
        keygen.initialize(KEY_SIZE, secureRandom);

        // 生成一个密钥对，保存在keyPair中
        KeyPair keys = keygen.genKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keys.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keys.getPrivate();

        // 将公钥和私钥保存到Map
        return new RSAKeyPair(publicKey, privateKey);
    }

    // - - - - - - - - - - - - - - - - - - - - SIGN 内部类 - - - - - - - - - - - - - - - - - - - - //

    @Data
    @AllArgsConstructor
    public static class RSAKeyPair {
        /**
         * rsa公钥
         */
        private RSAPublicKey rsaPublicKey;
        /**
         * rsa私钥
         */
        private RSAPrivateKey rsaPrivateKey;
        /**
         * rsa公钥base64字符串
         */
        private String rsaPublicKeyBase64Str;
        /**
         * rsa私钥base64字符串
         */
        private String rsaPrivateKeyBase64Str;

        public RSAKeyPair(RSAPublicKey rsaPublicKey, RSAPrivateKey rsaPrivateKey) {
            this.rsaPublicKey = rsaPublicKey;
            this.rsaPrivateKey = rsaPrivateKey;
            if (null != rsaPublicKey) {
                this.rsaPublicKeyBase64Str = Base64.getEncoder().encodeToString(rsaPublicKey.getEncoded());
            }
            if (null != rsaPrivateKey) {
                this.rsaPrivateKeyBase64Str = Base64.getEncoder().encodeToString(rsaPrivateKey.getEncoded());
            }
        }
    }

    /**
     * 保存密钥为 .pem 文件
     */
    public static void saveKeyToFile(String fileName, byte[] keyBytes) throws Exception {
        if (StringUtil.isEmpty(fileName)) {
            throw new FileNotFoundException("file name can not be empty");
        }
        if (!StringUtil.endsWith(fileName, ".pem")) {
            throw new FileNotFoundException("file type must be pem");
        }

        String pemHeader = "-----BEGIN RSA KEY-----\n";
        String pemFooter = "\n-----END RSA KEY-----\n";

        // 转换为 Base64 编码
        String base64Key = Base64.getEncoder().encodeToString(keyBytes);

        // 写入到文件
        try (FileWriter fw = new FileWriter(fileName)) {
            fw.write(pemHeader);
            fw.write(base64Key);
            fw.write(pemFooter);
        }
    }

}
