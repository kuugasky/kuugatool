package io.github.kuugasky.kuugatool.core.string;

import io.github.kuugasky.kuugatool.core.constants.KuugaConstants;
import io.github.kuugasky.kuugatool.core.regex.RegexUtil;

/**
 * SensitiveUtil
 * <p>
 * 敏感词脱敏工具类
 *
 * @author pengqinglong
 * @since 2021/10/15
 */
public final class SensitiveUtil {

    private static final int SEVEN = 7;
    private static final int EIGHT = 8;

    /**
     * 支持的脱敏类型枚举
     *
     * @author dazer and neusoft and qiaomu
     */
    public enum DesensitizedType {
        // 中文名
        CHINESE_NAME,
        // 身份证号
        ID_CARD,
        // 座机号
        FIXED_PHONE,
        // 手机号
        MOBILE_PHONE,
        // 地址
        ADDRESS,
        // 电子邮件
        EMAIL,
        // 密码
        PASSWORD,
        // 中国大陆车牌，包含普通车辆、新能源车辆
        CAR_LICENSE,
        // 银行卡
        BANK_CARD
    }

    /**
     * 根据枚举类型进行脱敏
     *
     * @param str              文本
     * @param desensitizedType 脱敏类型
     * @return 脱敏后文本
     */
    public static String desensitized(CharSequence str, SensitiveUtil.DesensitizedType desensitizedType) {
        String value = StringUtil.trim(StringUtil.str(str));
        return switch (desensitizedType) {
            case CHINESE_NAME -> username(value);
            case ID_CARD -> idCardNum(value, 1, 2);
            case FIXED_PHONE -> fixedPhone(value);
            case MOBILE_PHONE -> mobilePhone(value);
            case ADDRESS -> address(value, SEVEN);
            case EMAIL -> email(value);
            case PASSWORD -> password(value);
            case CAR_LICENSE -> carLicense(value);
            case BANK_CARD -> bankCard(value);
        };
    }

    /**
     * 密码：全部转换*号
     *
     * @param password 明文密码
     * @return 脱敏后的密码
     */
    public static String password(String password) {
        if (StringUtil.isEmpty(password)) {
            return StringUtil.EMPTY;
        }
        return StringUtil.repeatAndJoin("*", password.length(), StringUtil.EMPTY);
    }

    /**
     * 固定电话：前四后二
     *
     * @param phone 明文固话
     * @return 脱敏后的固话
     */
    public static String fixedPhone(String phone) {
        if (StringUtil.isEmpty(phone)) {
            return StringUtil.EMPTY;
        }
        return encodeText(phone, 4, 2);
    }

    /**
     * 手机号：前三后四
     *
     * @param phone 明文手机号
     * @return 脱敏后的手机号
     */
    public static String mobilePhone(String phone) {
        if (StringUtil.isEmpty(phone)) {
            return StringUtil.EMPTY;
        }
        return encodeText(phone, 3, 4);
    }

    /**
     * 邮箱：前缀@展示1位，其他字符转换*号
     *
     * @param email 明文邮箱账号
     * @return 脱敏后的邮箱账号
     */
    public static String email(String email) {
        if (StringUtil.isEmpty(email)) {
            return StringUtil.EMPTY;
        }
        if (!RegexUtil.isEmail(email)) {
            throw new RuntimeException("邮箱账号格式错误");
        }
        String[] split = email.split("@");
        return encodeText(split[0], 1, 0) + "@" + split[1];
    }

    /**
     * 用户名：保留第一位，其余转换*号
     *
     * @param username 用户名
     * @return 脱敏后的用户名
     */
    public static String username(String username) {
        if (StringUtil.isEmpty(username)) {
            return StringUtil.EMPTY;
        }
        return username.charAt(0) + StringUtil.repeatAndJoin("*", username.length() - 1, StringUtil.EMPTY);
    }

    /**
     * 【地址】只显示到地区，不显示详细地址，比如：北京市海淀区****
     *
     * @param address       家庭住址
     * @param sensitiveSize 敏感信息长度
     * @return 脱敏后的家庭地址
     */
    public static String address(String address, int sensitiveSize) {
        if (StringUtil.isEmpty(address)) {
            return StringUtil.EMPTY;
        }
        return encodeText(address, sensitiveSize, 0);
    }

    /**
     * 【中国车牌】车牌中间用*代替
     * eg1：null       -》 ""
     * eg1：""         -》 ""
     * eg3：苏D40000   -》 苏D4***0
     * eg4：陕A12345D  -》 陕A1****D
     * eg5：京A123     -》 京A123     如果是错误的车牌，不处理
     *
     * @param carLicense 完整的车牌号
     * @return 脱敏后的车牌
     */
    public static String carLicense(String carLicense) {
        if (StringUtil.isEmpty(carLicense)) {
            return StringUtil.EMPTY;
        }
        // 普通车牌
        if (carLicense.length() == SEVEN) {
            carLicense = encodeText(carLicense, 3, 1);
        } else if (carLicense.length() == EIGHT) {
            // 新能源车牌
            carLicense = encodeText(carLicense, 3, 1);
        }
        return carLicense;
    }

    /**
     * 银行卡号脱敏
     * eg: 1101 **** **** **** 3256
     *
     * @param bankCardNo 银行卡号
     * @return 脱敏之后的银行卡号
     * @since 5.6.3
     */
    public static String bankCard(String bankCardNo) {
        if (StringUtil.isEmpty(bankCardNo)) {
            return StringUtil.EMPTY;
        }
        bankCardNo = StringUtil.trim(bankCardNo);
        if (bankCardNo.length() < KuugaConstants.NINE) {
            return bankCardNo;
        }

        final int length = bankCardNo.length();
        final int midLength = length - KuugaConstants.EIGHT;
        final StringBuilder buf = new StringBuilder();

        buf.append(bankCardNo, 0, 4);
        for (int i = 0; i < midLength; ++i) {
            if (i % 4 == 0) {
                buf.append(KuugaConstants.SPACE);
            }
            buf.append('*');
        }
        buf.append(KuugaConstants.SPACE).append(bankCardNo, length - 4, length);
        return buf.toString();
    }

    /**
     * 【身份证号】前1位 和后2位
     *
     * @param idCardNum 身份证
     * @param front     保留：前面的front位数；从1开始
     * @param end       保留：后面的end位数；从1开始
     * @return 脱敏后的身份证
     */
    public static String idCardNum(String idCardNum, int front, int end) {
        // 身份证不能为空
        if (StringUtil.isEmpty(idCardNum)) {
            return StringUtil.EMPTY;
        }
        // 需要截取的长度不能大于身份证号长度
        if ((front + end) > idCardNum.length()) {
            return StringUtil.EMPTY;
        }
        // 需要截取的不能小于0
        if (front < 0 || end < 0) {
            return StringUtil.EMPTY;
        }
        return encodeText(idCardNum, front, end);
    }

    /**
     * 处理敏感数据 默认前后保留两位
     * 规则 #{[明文]}#&{[秘文]}&
     * 例子：#{[13312345678]}#&{[13xxxxxxx78]}&
     *
     * @param plaintext 明文
     * @return java.lang.String
     * @author 彭清龙
     * @since 4:07 下午 2021/3/20
     **/
    public static String handleSensitive(String plaintext) {
        return handleSensitive(plaintext, 2, 2);
    }

    /**
     * 处理敏感数据
     * 规则 #{[明文]}#&{[秘文]}&
     *
     * @param plaintext 明文
     * @param head      头保留多少位
     * @param tail      尾保留多少位
     * @return java.lang.String
     * @author 彭清龙
     * @since 4:20 下午 2021/3/20
     **/
    public static String handleSensitive(String plaintext, int head, int tail) {
        return handleSensitive(plaintext, head, tail, '*');
    }

    /**
     * 处理敏感数据
     * <p>
     * 规则 #{[明文]}#&{[秘文]}&
     *
     * @param plaintext 明文
     * @param head      头保留多少位
     * @param tail      尾保留多少位
     * @param mask      掩码
     * @return java.lang.String
     * @author 彭清龙
     * @since 4:20 下午 2021/3/20
     **/
    public static String handleSensitive(String plaintext, int head, int tail, char mask) {
        // 非空校验
        if (StringUtil.isEmpty(plaintext)) {
            return plaintext;
        }

        // length需要大于前后保留的位数
        int length = plaintext.length();
        if (length < (head + tail)) {
            return plaintext;
        }

        StringBuilder builder = new StringBuilder();
        // 明文处理
        builder.append("#{[");
        builder.append(plaintext);
        builder.append("]}#");

        // 密文处理
        builder.append("&{[");
        builder.append(plaintext, 0, head);

        int count = head;

        int end = length - tail;
        for (; count < end; count++) {
            builder.append(mask);
        }
        builder.append(plaintext, count, length);
        builder.append("]}&");

        return builder.toString();
    }

    /**
     * 明文直接加密为秘文 不拼接规则 默认头部保留3位尾部保留4位使用*作为掩码
     *
     * @param plaintext 明文
     * @return 秘文
     */
    public static String encodeText(String plaintext) {
        return encodeText(plaintext, 3, 4);
    }

    /**
     * 明文直接全部加密为秘文 不拼接规则
     *
     * @param plaintext 明文
     * @return 密文
     */
    public static String allEncodeText(String plaintext) {
        return encodeText(plaintext, 0, 0);
    }

    /**
     * 明文直接加密为秘文 不拼接规则 默认使用*作为掩码
     *
     * @param plaintext 明文
     * @param head      头部保留
     * @param tail      尾部保留
     * @return 秘文
     */
    public static String encodeText(String plaintext, int head, int tail) {
        return encodeText(plaintext, head, tail, '*');
    }

    /**
     * 明文直接加密为秘文 不拼接规则
     *
     * @param plaintext 明文
     * @param head      头部保留
     * @param tail      尾部保留
     * @param mask      掩码
     * @return 秘文
     */
    public static String encodeText(String plaintext, int head, int tail, char mask) {
        StringBuilder builder = new StringBuilder();

        // 非空校验
        if (StringUtil.isEmpty(plaintext) || plaintext.length() <= head) {
            return plaintext;
        }

        // length需要大于前后保留的位数
        int length = plaintext.length();
        if (length < (head + tail)) {
            return plaintext;
        }

        int count = head;

        int end = length - tail;
        builder.append(plaintext, 0, head);
        for (; count < end; count++) {
            builder.append(mask);
        }
        builder.append(plaintext, count, length);

        return builder.toString();
    }

    /**
     * 明文直接加密为秘文 不拼接规则
     *
     * @param plaintext 明文
     * @param head      头部保留
     * @param tail      尾部保留
     * @param mask      掩码
     * @param maskSize  掩码数量 此字段大于0时 生效 此时tail字段失效
     * @return 秘文
     */
    public static String encodeText(String plaintext, int head, int tail, char mask, int maskSize) {
        if (maskSize > 0) {
            return encodeText(plaintext, head, mask, maskSize);
        } else {
            return encodeText(plaintext, head, tail, mask);
        }
    }

    /**
     * 明文直接加密为秘文 不拼接规则
     *
     * @param plaintext 明文
     * @param head      头部保留
     * @param mask      掩码
     * @param maskSize  掩码数量
     * @return 秘文
     */
    public static String encodeText(String plaintext, int head, char mask, int maskSize) {
        StringBuilder builder = new StringBuilder();

        // 非空校验
        if (StringUtil.isEmpty(plaintext) || plaintext.length() <= head) {
            return plaintext;
        }

        int count = head;

        int end = maskSize + head;
        builder.append(plaintext, 0, head);
        for (; count < end && count < plaintext.length(); count++) {
            builder.append(mask);
        }
        builder.append(plaintext, count, plaintext.length());

        return builder.toString();
    }

    /**
     * 获取密文
     *
     * @param ciphertext 密文
     * @return java.lang.String
     * @author 彭清龙
     * @since 5:17 下午 2021/3/20
     **/
    public static String acquireCiphertext(String ciphertext) {
        return acquireSensitive(ciphertext, false);
    }

    /**
     * 获取明文
     *
     * @param plaintext 密文
     * @return java.lang.String
     * @author 彭清龙
     * @since 5:18 下午 2021/3/20
     **/
    public static String acquirePlaintext(String plaintext) {
        return acquireSensitive(plaintext, true);
    }

    /**
     * @param ciphertext 密文
     * @return String
     * plaintextOrCiphertext 取明文还是取密文 true：取明文 false：取密文]
     * @author 彭清龙
     * @since 4:25 下午 2021/3/20
     */
    public static String acquireSensitive(String ciphertext, boolean plaintextOrCiphertext) {
        if (StringUtil.isEmpty(ciphertext)) {
            return ciphertext;
        }
        // 默认取密文
        String needStartMark = "&{[";
        String needEndMark = "]}&";

        String replaceStartMark = "#{[";
        String replaceEndMark = "]}#";

        // 取明文
        if (plaintextOrCiphertext) {
            needStartMark = "#{[";
            needEndMark = "]}#";

            replaceStartMark = "&{[";
            replaceEndMark = "]}&";
        }

        // 处理数据
        while (ciphertext.contains(replaceStartMark) && ciphertext.contains(replaceEndMark)) {
            int replaceStartIdx = ciphertext.indexOf(replaceStartMark);
            int replaceEndIdx = ciphertext.indexOf(replaceEndMark);

            // 获取需要的文本
            String replaceText = ciphertext.substring(replaceStartIdx, replaceEndIdx + 3);
            ciphertext = ciphertext.replace(needStartMark, StringUtil.EMPTY)
                    .replace(needEndMark, StringUtil.EMPTY)
                    .replace(replaceText, StringUtil.EMPTY);
        }
        return ciphertext;
    }

}
