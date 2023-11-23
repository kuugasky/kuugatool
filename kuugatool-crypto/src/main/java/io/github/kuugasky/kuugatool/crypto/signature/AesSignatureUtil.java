package io.github.kuugasky.kuugatool.crypto.signature;

import io.github.kuugasky.kuugatool.core.encoder.Base64Util;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import io.github.kuugasky.kuugatool.crypto.AESUtil;
import io.github.kuugasky.kuugatool.json.JsonUtil;

/**
 * AES签名工具类
 *
 * @author kuuga
 * @since 2021/3/27
 */
public class AesSignatureUtil {

    public static String encryption(Object treeMap, String encodeRules) throws Exception {
        if (ObjectUtil.isNull(treeMap)) {
            return StringUtil.EMPTY;
        }
        String plaintext = JsonUtil.toJsonString(treeMap);
        System.out.println("明文：" + plaintext);
        String ciphertext = AESUtil.encrypt(encodeRules, plaintext);
        return Base64Util.encode(ciphertext);
    }

    public static String decryption(String ciphertext, String encodeRules) throws Exception {
        if (StringUtil.isEmpty(ciphertext)) {
            return StringUtil.EMPTY;
        }
        String decode = Base64Util.decode(ciphertext);
        return AESUtil.decrypt(encodeRules, decode);
    }

}