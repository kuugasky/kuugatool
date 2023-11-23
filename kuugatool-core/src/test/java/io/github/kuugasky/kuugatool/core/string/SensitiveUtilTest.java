package io.github.kuugasky.kuugatool.core.string;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SensitiveUtilTest {

    @Test
    public void test() {
        System.out.println("password----" + SensitiveUtil.password("123456"));
        // 加密敏感信息 默认前后各保留两位
        String s1 = SensitiveUtil.handleSensitive("13312345678");
        System.out.println("加密敏感信息 默认前后各保留两位---" + s1);

        // 加密敏感信息 前面保留三位 后面保留四位
        String s2 = SensitiveUtil.handleSensitive("13312345678", 3, 4);
        System.out.println("加密敏感信息 前面保留三位 后面保留四位" + s2);

        // 获取明文密文
        String s3 = SensitiveUtil.acquirePlaintext(s1);
        System.out.println("acquirePlaintext获取明文---" + s3);
        String s4 = SensitiveUtil.acquireCiphertext(s1);
        System.out.println("acquireCiphertext获取密文---" + s4);

        // 获取明文
        String s5 = SensitiveUtil.acquireSensitive(s1, true);
        System.out.println("acquireSensitive获取明文---" + s5);
        // 获取秘文
        String s6 = SensitiveUtil.acquireSensitive(s1, false);
        System.out.println("acquireSensitive获取密文---" + s6);

        // 单加密
        System.out.println("单加密---" + SensitiveUtil.encodeText("13265116714"));
        // 单加密 前面保留三位 后面保留4位
        System.out.println("单加密 前面保留三位 后面保留4位---" + SensitiveUtil.encodeText("13265116714", 3, 4));

        // 使用#加密
        System.out.println("使用#加密---" + SensitiveUtil.encodeText("13265116714", 3, 4, '#'));
        System.out.println("使用¥加密---" + SensitiveUtil.handleSensitive("13265116714", 3, 4, '¥'));

        System.out.println("全部加密----" + SensitiveUtil.allEncodeText("1234567"));

        System.out.println("通过掩码数量进行加密 原数据 123 加密结果" + SensitiveUtil.encodeText("123", 3, '#', 5));
        System.out.println("通过掩码数量进行加密 原数据 123456 加密结果" + SensitiveUtil.encodeText("123456", 3, '#', 5));
        System.out.println("通过掩码数量进行加密 原数据 1234567890 加密结果" + SensitiveUtil.encodeText("1234567890", 3, '#', 5));
        System.out.println("通过掩码数量进行加密 原数据 1234567890abcdefg 加密结果" + SensitiveUtil.encodeText("1234567890abcdefg", 3, '#', 5));
    }


    @Test
    public void desensitized() {
        Assertions.assertEquals("段**", SensitiveUtil.desensitized("段正淳", SensitiveUtil.DesensitizedType.CHINESE_NAME));
        Assertions.assertEquals("5***************1X", SensitiveUtil.desensitized("51343620000320711X", SensitiveUtil.DesensitizedType.ID_CARD));
        Assertions.assertEquals("0915*****79", SensitiveUtil.desensitized("09157518479", SensitiveUtil.DesensitizedType.FIXED_PHONE));
        Assertions.assertEquals("180****1999", SensitiveUtil.desensitized("18049531999", SensitiveUtil.DesensitizedType.MOBILE_PHONE));
        Assertions.assertEquals("北京市海淀区马********", SensitiveUtil.desensitized("北京市海淀区马连洼街道289号", SensitiveUtil.DesensitizedType.ADDRESS));
        Assertions.assertEquals("d*************@gmail.com.cn", SensitiveUtil.desensitized("duandazhi-jack@gmail.com.cn", SensitiveUtil.DesensitizedType.EMAIL));
        Assertions.assertEquals("**********", SensitiveUtil.desensitized("1234567890", SensitiveUtil.DesensitizedType.PASSWORD));
        Assertions.assertEquals("1101 **** **** **** 3256", SensitiveUtil.desensitized("11011111222233333256", SensitiveUtil.DesensitizedType.BANK_CARD));
        Assertions.assertEquals("6227 **** **** *** 5123", SensitiveUtil.desensitized("6227880100100105123", SensitiveUtil.DesensitizedType.BANK_CARD));
    }

    @Test
    public void password() {
        System.out.println(SensitiveUtil.password("1234567890"));
    }

    @Test
    public void fixedPhone() {
        System.out.println(SensitiveUtil.fixedPhone("09157518479"));
    }

    @Test
    public void mobilePhone() {
        System.out.println(SensitiveUtil.mobilePhone("18049531999"));
    }

    @Test
    public void email() {
        System.out.println(SensitiveUtil.email("duandazhi-jack@gmail.com.cn"));
    }

    @Test
    public void username() {
        System.out.println(SensitiveUtil.username("段正淳"));
    }

    @Test
    public void address() {
        System.out.println(SensitiveUtil.address("北京市海淀区马连洼街道289号", 7));
    }

    @Test
    public void carLicense() {
        System.out.println(SensitiveUtil.carLicense("苏D40000"));
        System.out.println(SensitiveUtil.carLicense("陕A12345D"));
    }

    @Test
    public void bankCard() {
        System.out.println(SensitiveUtil.bankCard("6227880100100105123"));
    }

    @Test
    public void idCardNum() {
        System.out.println(SensitiveUtil.bankCard("51343620000320711X"));
    }

    @Test
    public void handleSensitive() {
        // #{[15911112222]}#&{[15*******22]}&
        System.out.println(SensitiveUtil.handleSensitive("15911112222"));
    }

    @Test
    public void testHandleSensitive() {
        // #{[15911112222]}#&{[1*********2]}&
        System.out.println(SensitiveUtil.handleSensitive("15911112222", 1, 1));
    }

    @Test
    public void testHandleSensitive1() {
        // #{[15911112222]}#&{[1$$$$$$$$$2]}&
        System.out.println(SensitiveUtil.handleSensitive("15911112222", 1, 1, '$'));
    }

    @Test
    public void encodeText() {
        // 默认前三后四：159****2222
        System.out.println(SensitiveUtil.encodeText("15911112222"));
        System.out.println(SensitiveUtil.encodeText("15911112222", 3, 4));
        System.out.println(SensitiveUtil.encodeText("11", 3, 4));
    }

    @Test
    public void allEncodeText() {
        // 默认前三后四：159****2222
        System.out.println(SensitiveUtil.allEncodeText("15911112222"));
    }

    @Test
    public void testEncodeText() {
        // 1*********2
        System.out.println(SensitiveUtil.encodeText("15911112222", 1, 1));
    }

    @Test
    public void testEncodeText1() {
        // 1%%%%%%%%%2
        System.out.println(SensitiveUtil.encodeText("15911112222", 1, 1, '%'));
    }

    @Test
    public void testEncodeText2() {
        // 1%%11112222
        System.out.println(SensitiveUtil.encodeText("15911112222", 1, 1, '%', 2));
    }

    @Test
    public void testEncodeText3() {
        // 1%%11112222
        System.out.println(SensitiveUtil.encodeText("15911112222", 1, '%', 2));
    }

    @Test
    public void acquireCiphertext() {
        // 获取密文
        System.out.println(SensitiveUtil.acquireCiphertext("#{[15911112222]}#&{[1*********2]}&"));
    }

    @Test
    public void acquirePlaintext() {
        // 获取明文
        System.out.println(SensitiveUtil.acquirePlaintext("#{[15911112222]}#&{[1*********2]}&"));
    }

    @Test
    public void acquireSensitive() {
        // 获取明文
        System.out.println(SensitiveUtil.acquireSensitive("#{[15911112222]}#&{[1*********2]}&", true));
        System.out.println(SensitiveUtil.acquireSensitive("#{[15911112222]}#&{[1*********2]}&", false));
    }

}