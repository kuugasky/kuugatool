package io.github.kuugasky.kuugatool.crypto.signature;

import io.github.kuugasky.kuugatool.core.collection.MapUtil;
import io.github.kuugasky.kuugatool.core.encoder.Base64Util;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import io.github.kuugasky.kuugatool.crypto.AESUtil;
import io.github.kuugasky.kuugatool.crypto.exception.DecryptionException;
import io.github.kuugasky.kuugatool.crypto.exception.EncryptionException;
import io.github.kuugasky.kuugatool.json.JsonUtil;

import java.util.Map;

/**
 * AES签名工具类
 * <p>
 * aes对称加密
 *
 * @author kuuga
 * @since 2021/3/27
 */
public class AesSignatureUtil {

    /**
     * 编码规则
     */
    private static final String DEFAULT_ENCODE_RULES = "kuugasky@gmail.com";

    /**
     * 加密
     *
     * @param params 待加密对象
     * @return 密文
     * @throws EncryptionException 加密异常
     */
    public static String encryption(Object params) throws EncryptionException {
        return encryption(params, DEFAULT_ENCODE_RULES);
    }

    /**
     * 加密
     *
     * @param params      待加密对象
     * @param encodeRules 编码规则
     * @return 密文
     * @throws EncryptionException 加密异常
     */
    public static String encryption(Object params, String encodeRules) throws EncryptionException {
        if (ObjectUtil.isNull(params)) {
            return StringUtil.EMPTY;
        }
        if (params instanceof Map<?, ?> map) {
            if (MapUtil.isEmpty(map)) {
                return StringUtil.EMPTY;
            }
        }
        // 明文
        String plaintext = JsonUtil.toJsonString(params);
        // 密文
        String ciphertext = AESUtil.encrypt(encodeRules, plaintext);
        // 密文base64编码
        return Base64Util.encode(ciphertext);
    }

    /**
     * 解密
     *
     * @param ciphertext 密文
     * @return 明文
     * @throws DecryptionException 解密异常
     */
    public static String decryption(String ciphertext) throws DecryptionException {
        return decryption(ciphertext, DEFAULT_ENCODE_RULES);
    }

    /**
     * 解密
     *
     * @param ciphertext  密文
     * @param encodeRules 编码规则
     * @return 明文
     * @throws DecryptionException 解密异常
     */
    public static String decryption(String ciphertext, String encodeRules) throws DecryptionException {
        if (StringUtil.isEmpty(ciphertext)) {
            return StringUtil.EMPTY;
        }
        // 密文base64编码
        String decode = Base64Util.decode(ciphertext);
        // aes解密
        return AESUtil.decrypt(encodeRules, decode);
    }

}