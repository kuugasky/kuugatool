package io.github.kuugasky.kuugatool.core.lang;

import io.github.kuugasky.kuugatool.core.creditcode.CreditCodeUtil;
import io.github.kuugasky.kuugatool.core.entity.KuugaDTO;
import io.github.kuugasky.kuugatool.core.exception.ValidateException;
import io.github.kuugasky.kuugatool.core.hex.HexUtil;
import io.github.kuugasky.kuugatool.core.regex.PatternPool;
import io.github.kuugasky.kuugatool.core.regex.RegexConstants;
import io.github.kuugasky.kuugatool.core.string.IdUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;


/**
 * 验证器单元测试
 *
 * @author Looly
 */
public class ValidatorTest {

    @Test
    public void isTrue() {
        org.junit.Assert.assertTrue(Validator.isTrue(Boolean.TRUE));
    }

    @Test
    public void isFalse() {
        org.junit.Assert.assertTrue(Validator.isFalse(Boolean.FALSE));
    }

    @Test
    public void validateTrue() {
        Validator.validateTrue(Boolean.FALSE, "{}-{}是false", "kuuga", "cool");
    }

    @Test
    public void validateFalse() {
        Validator.validateFalse(Boolean.TRUE, "{}-{}是false", "kuuga", "cool");
    }

    @Test
    public void isNull() {
        org.junit.Assert.assertTrue(Validator.isNull(null));
    }

    @Test
    public void isNotNull() {
        org.junit.Assert.assertFalse(Validator.isNotNull(null));
    }

    @Test
    public void validateNull() {
        Object t = Validator.validateNull(null, "{}-{}是false", "kuuga", "cool");
        System.out.println(t);
    }

    @Test
    public void validateNotNull() {
        Object t = Validator.validateNotNull(null, "{}-{}是false", "kuuga", "cool");
        System.out.println(t);
    }

    @Test
    public void isEmpty() {
        org.junit.Assert.assertTrue(Validator.isEmpty(StringUtil.EMPTY));
    }

    @Test
    public void isNotEmpty() {
        org.junit.Assert.assertFalse(Validator.isNotEmpty(StringUtil.EMPTY));
    }

    @Test
    public void validateEmpty() {
        Validator.validateEmpty(new KuugaDTO(), "必须空值");
    }

    @Test
    public void validateNotEmpty() {
        Validator.validateNotEmpty(null, "不允许空值");
    }

    @Test
    public void equal() {
        System.out.println(Validator.equal(null, null));
    }

    @Test
    public void validateEqual() {
        System.out.println(Validator.validateEqual(null, StringUtil.EMPTY, "值不一样"));
    }

    @Test
    public void validateNotEqual() {
        Validator.validateNotEqual(null, null, "值不能一样");
    }

    @Test
    public void validateNotEmptyAndEqual() {
        // * 验证是否非空且与指定值相等<br>
        // * 当数据为空时抛出验证异常<br>
        // * 当两值不等时抛出异常
        Validator.validateNotEmptyAndEqual(1, 1, "t1不能为空且值不能与t2不相等");
    }

    @Test
    public void validateNotEmptyAndNotEqual() {
        // * 验证是否非空且与指定值相等<br>
        //  * 当数据为空时抛出验证异常<br>
        //  * 当两值相等时抛出异常
        Validator.validateNotEmptyAndNotEqual(1, 2, "t1不能为空且值不能与t2相等");
    }

    @Test
    public void validateMatchRegex() {
        Validator.validateMatchRegex(RegexConstants.REGEX_IP, "127.0.0.1x", "非正确数据");
    }

    @Test
    public void isMatchRegex() {
        System.out.println(Validator.isMatchRegex(PatternPool.GENERAL, "abc_"));
    }

    @Test
    public void isMatchRegex2() {
        System.out.println(Validator.isMatchRegex(RegexConstants.REGEX_TENCENT_NUM, "270000000"));
    }

    @Test
    public void isGeneral() {
        System.out.println(Validator.isGeneral("kuuga0410_样"));
    }

    @Test
    public void validateGeneral() {
        System.out.println(Validator.validateGeneral("kuuga0410_样", "只能是英文和下划线"));
    }

    @Test
    public void isGeneral2() {
        String str = StringUtil.EMPTY;
        boolean general = Validator.isGeneral(str, -1, 5);
        Assert.assertFalse(general);

        str = "123_abc_ccc";
        general = Validator.isGeneral(str, -1, 100);
        Assert.assertTrue(general);

        // 不允许中文
        str = "123_abc_ccc中文";
        general = Validator.isGeneral(str, -1, 100);
        Assert.assertFalse(general);
    }

    @Test
    public void validateGeneral2() {
        String str = "123_abc_ccc中文";
        System.out.println(Validator.validateGeneral(str, 0, 120, "不允许超过10个字符"));
    }

    @Test
    public void isGeneral3() {
        String str = "123_abc_ccc";
        System.out.println(Validator.isGeneral(str, 1));
    }

    @Test
    public void validateGeneral3() {
        String str = "123_abc_ccc";
        System.out.println(Validator.validateGeneral(str, 100, "长度最少要是100"));
    }

    @Test
    public void isLetter() {
        String str = "kuuga";
        System.out.println(Validator.isLetter(str));
        String str1 = "kuuga0";
        System.out.println(Validator.isLetter(str1));
    }

    @Test
    public void validateLetter() {
        String str1 = "kuuga0";
        System.out.println(Validator.validateLetter(str1, "必须全部由字母组成"));
    }

    @Test
    public void isUpperCase() {
        String str1 = "kuugasky";
        // 必须纯字母
        System.out.println(Validator.isUpperCase(str1));
    }

    @Test
    public void validateUpperCase() {
        String str1 = "kuugasky";
        // 必须纯字母
        System.out.println(Validator.validateUpperCase(str1, "必须纯字母比对"));
    }

    @Test
    public void isLowerCase() {
        String str1 = "kuugasky";
        // 必须纯字母
        System.out.println(Validator.isLowerCase(str1));
    }

    @Test
    public void validateLowerCase() {
        String str1 = "kuugasky";
        // 必须纯字母
        System.out.println(Validator.validateLowerCase(str1, "必须纯字母比对"));
    }

    @Test
    public void isNumber() {
        String str1 = "123";
        System.out.println(Validator.isNumber(str1));
    }

    @Test
    public void hasNumber() {
        String str1 = "123AAA";
        System.out.println(Validator.hasNumber(str1));
    }

    @Test
    public void validateNumber() {
        String str1 = "123AAA";
        System.out.println(Validator.validateNumber(str1, "必须为纯数字"));
    }

    @Test
    public void isWord() {
        String str1 = "123AAA";
        System.out.println(Validator.isWord(str1));
    }

    @Test
    public void validateWord() {
        String str1 = "123AAA";
        System.out.println(Validator.validateWord(str1, "必须为纯字母"));
    }

    @Test
    public void isMoney() {
        String str1 = "1.000";
        System.out.println(Validator.isMoney(str1));
    }

    @Test
    public void validateMoney() {
        String str1 = "1.0001x";
        System.out.println(Validator.validateMoney(str1, "非货币文本"));
    }

    @Test
    public void isZipCode() {
        // 港
        boolean zipCode = Validator.isZipCode("999077");
        Assert.assertTrue(zipCode);
        // 澳
        zipCode = Validator.isZipCode("999078");
        Assert.assertTrue(zipCode);
        // 台（2020年3月起改用6位邮编，3+3）
        zipCode = Validator.isZipCode("822001");
        Assert.assertTrue(zipCode);

        // 内蒙
        zipCode = Validator.isZipCode("016063");
        Assert.assertTrue(zipCode);
        // 山西
        zipCode = Validator.isZipCode("045246");
        Assert.assertTrue(zipCode);
        // 河北
        zipCode = Validator.isZipCode("066502");
        Assert.assertTrue(zipCode);
        // 北京
        zipCode = Validator.isZipCode("102629");
        Assert.assertTrue(zipCode);
    }

    @Test
    public void validateZipCode() {
        System.out.println(Validator.validateZipCode("xxx", "非邮政编码"));
    }

    @Test
    public void isEmail() {
        System.out.println(Validator.isEmail("kuuga0410111.com"));
    }

    @Test
    public void validateEmail() {
        System.out.println(Validator.validateEmail("kuuga0410111.com", "非正常邮箱地址"));
    }

    @Test
    public void isMobile() {
        System.out.println(Validator.isMobile("15911112222"));
    }

    @Test
    public void validateMobile() {
        System.out.println(Validator.validateMobile("1591111222", "错误手机号"));
    }

    @Test
    public void isCitizenId() {
        // 18为身份证号码验证
        boolean b = Validator.isCitizenId("110101199003074477");
        Assert.assertTrue(b);

        // 15位身份证号码验证
        // boolean b1 = Validator.isCitizenId("410001910101123");
        // Assert.assertTrue(b1);

        // 10位身份证号码验证
        // boolean b2 = Validator.isCitizenId("U193683453");
        // Assert.assertTrue(b2);
    }

    @Test
    public void validateCitizenIdNumber() {
        Validator.validateCitizenIdNumber("110101199003074477X", "错误身份证号");
    }

    @Test
    public void isBirthday() {
        System.out.println(Validator.isBirthday(2021, 14, 12));
    }

    @Test
    public void isBirthday2() {
        System.out.println(Validator.isBirthday("2021-12-12"));
        System.out.println(Validator.isBirthday("20211212"));
    }

    @Test
    public void validateBirthday() {
        System.out.println(Validator.validateBirthday("2021-13-12", "错误格式"));
    }

    @Test
    public void isIpv4() {
        System.out.println(Validator.isIpv4("127.0.0.1"));
    }

    @Test
    public void validateIpv4() {
        System.out.println(Validator.validateIpv4("127.0.0.1.1", "非有效ip"));
    }

    @Test
    public void isIpv6() {
        System.out.println(Validator.isIpv6("2031:0000:1F1F:0000:0000:0100:11A0:ADDF"));
    }

    @Test
    public void validateIpv6() {
        System.out.println(Validator.validateIpv6("2031:0000:1F1F:0000:0000:0100:11A0:ADDF:111", "非有效ip6"));
    }

    @Test
    public void isMac() {
        System.out.println(Validator.isMac("24:4b:fe:56:5d:37"));
    }

    @Test
    public void validateMac() {
        System.out.println(Validator.validateMac("24:4b:fe:56:5d:37:1", "错误mac地址"));
    }

    @Test
    public void isPlateNumber() {
        System.out.println(Validator.isPlateNumber("粤BA03205"));
        System.out.println(Validator.isPlateNumber("闽20401领"));
    }

    @Test
    public void validatePlateNumber() {
        System.out.println(Validator.validatePlateNumber("粤BA03205X", "错误车牌号"));
    }

    @Test
    public void isUrl() {
        System.out.println(Validator.isUrl("http://www.baidu.com"));
    }

    @Test
    public void validateUrl() {
        System.out.println(Validator.validateUrl("xhttp://www.baidu.cx", "错误url"));
    }

    @Test
    public void isChinese() {
        System.out.println(Validator.isChinese("错误url"));
    }

    @Test
    public void hasChinese() {
        System.out.println(Validator.hasChinese("错误url"));
    }

    @Test
    public void validateChinese() {
        System.out.println(Validator.validateChinese("错误url", "非纯中文"));
    }

    @Test
    public void isGeneralWithChinese() {
        System.out.println(Validator.isGeneralWithChinese("kuuga_0410"));
    }

    @Test
    public void validateGeneralWithChinese() {
        System.out.println(Validator.validateGeneralWithChinese("#", "错误格式"));
    }

    @Test
    public void isUUID() {
        System.out.println(Validator.isUUID(IdUtil.randomUUID()));
        System.out.println(Validator.isUUID(IdUtil.simpleUUID()));
        System.out.println(Validator.isUUID(IdUtil.fastSimpleUUID()));
    }

    @Test
    public void validateUUID() {
        System.out.println(Validator.validateUUID(IdUtil.randomUUID(), "错误格式"));
        System.out.println(Validator.validateUUID("xxx", "错误格式"));
    }

    @Test
    public void isHex() {
        String s = HexUtil.encodeHexStr("kuugasky".getBytes());
        System.out.println(s);
        System.out.println(Validator.isHex(s));
    }

    @Test
    public void validateHex() {
        String s = IdUtil.randomUUID();
        System.out.println(s);
        System.out.println(Validator.validateHex(s, "错误16进制"));
    }

    @Test
    public void isBetween() {
        System.out.println(Validator.isBetween(10, 2, 20));
        System.out.println(Validator.isBetween(10, 2D, 20D));
        System.out.println(Validator.isBetween(10, 2L, 20D));
    }

    @Test
    public void validateBetween() {
        Validator.validateBetween(30, 2L, 20D, "不在范围内");
    }

    @Test
    public void isCreditCode() {
        String s = CreditCodeUtil.randomCreditCode();
        System.out.println(s);
        System.out.println(Validator.isCreditCode(s));
    }

    @Test
    public void isCarVin() {
        System.out.println(Validator.isCarVin("LSJA24U62JG269225"));
        System.out.println(Validator.isCarVin("LDC613P23A1305189"));
    }

    @Test
    public void validateCarVin() {
        Validator.validateCarVin("LDC613P23A1305189X", "错误车架号");
    }

    @Test
    public void isCarDrivingLicence() {
        System.out.println(Validator.isCarDrivingLicence("430101758218"));
    }

    @Test
    public void validateCarDrivingLicence() {
        System.out.println(Validator.validateCarDrivingLicence("4301017582181", "错误驾驶证"));
    }

    @Test
    public void isNumberTest() {
        org.junit.Assert.assertTrue(Validator.isNumber("45345365465"));
        org.junit.Assert.assertTrue(Validator.isNumber("0004545435"));
        org.junit.Assert.assertTrue(Validator.isNumber("5.222"));
        org.junit.Assert.assertTrue(Validator.isNumber("0.33323"));
    }

    @Test
    public void hasNumberTest() {
        String var1 = StringUtil.EMPTY;
        String var2 = "str";
        String var3 = "180";
        String var4 = "身高180体重180";
        org.junit.Assert.assertFalse(Validator.hasNumber(var1));
        org.junit.Assert.assertFalse(Validator.hasNumber(var2));
        org.junit.Assert.assertTrue(Validator.hasNumber(var3));
        org.junit.Assert.assertTrue(Validator.hasNumber(var4));
    }

    @Test
    public void isLetterTest() {
        org.junit.Assert.assertTrue(Validator.isLetter("asfdsdsfds"));
        org.junit.Assert.assertTrue(Validator.isLetter("asfdsdfdsfVCDFDFGdsfds"));
        org.junit.Assert.assertTrue(Validator.isLetter("asfdsdf你好dsfVCDFDFGdsfds"));
    }

    @Test
    public void isUperCaseTest() {
        org.junit.Assert.assertTrue(Validator.isUpperCase("VCDFDFG"));
        org.junit.Assert.assertTrue(Validator.isUpperCase("ASSFD"));

        org.junit.Assert.assertFalse(Validator.isUpperCase("asfdsdsfds"));
        org.junit.Assert.assertFalse(Validator.isUpperCase("ASSFD你好"));
    }

    @Test
    public void isLowerCaseTest() {
        org.junit.Assert.assertTrue(Validator.isLowerCase("asfdsdsfds"));

        org.junit.Assert.assertFalse(Validator.isLowerCase("aaaa你好"));
        org.junit.Assert.assertFalse(Validator.isLowerCase("VCDFDFG"));
        org.junit.Assert.assertFalse(Validator.isLowerCase("ASSFD"));
        org.junit.Assert.assertFalse(Validator.isLowerCase("ASSFD你好"));
    }

    @Test
    public void isBirthdayTest() {
        boolean b = Validator.isBirthday("20150101");
        org.junit.Assert.assertTrue(b);
        boolean b2 = Validator.isBirthday("2015-01-01");
        org.junit.Assert.assertTrue(b2);
        boolean b3 = Validator.isBirthday("2015.01.01");
        org.junit.Assert.assertTrue(b3);
        boolean b4 = Validator.isBirthday("2015年01月01日");
        org.junit.Assert.assertTrue(b4);
        boolean b5 = Validator.isBirthday("2015.01.01");
        org.junit.Assert.assertTrue(b5);
        boolean b6 = Validator.isBirthday("2018-08-15");
        org.junit.Assert.assertTrue(b6);

        // 验证年非法
        org.junit.Assert.assertFalse(Validator.isBirthday("2095.05.01"));
        // 验证月非法
        org.junit.Assert.assertFalse(Validator.isBirthday("2015.13.01"));
        // 验证日非法
        org.junit.Assert.assertFalse(Validator.isBirthday("2015.02.29"));
    }

    @Test
    public void isCitizenIdTest() {
        // 18为身份证号码验证
        boolean b = Validator.isCitizenId("110101199003074477");
        org.junit.Assert.assertTrue(b);

        // 15位身份证号码验证
        boolean b1 = Validator.isCitizenId("410001910101123");
        org.junit.Assert.assertTrue(b1);

        // 10位身份证号码验证
        boolean b2 = Validator.isCitizenId("U193683453");
        org.junit.Assert.assertTrue(b2);
    }

    // @Test(expected = ValidateException.class)
    @Test
    public void validateTest() throws ValidateException {
        ValidateException businessException = Assertions.assertThrows(ValidateException.class,
                () -> Validator.validateChinese("我是一段zhongwen", "内容中包含非中文"));
        assertEquals(businessException.getMessage(), "内容中包含非中文");
    }

    @Test
    public void isEmailTest() {
        boolean email = Validator.isEmail("abc_cde@163.com");
        org.junit.Assert.assertTrue(email);
        boolean email1 = Validator.isEmail("abc_%cde@163.com");
        org.junit.Assert.assertTrue(email1);
        boolean email2 = Validator.isEmail("abc_%cde@aaa.c");
        org.junit.Assert.assertTrue(email2);
        boolean email3 = Validator.isEmail("xiaolei.lu@aaa.b");
        org.junit.Assert.assertTrue(email3);
        boolean email4 = Validator.isEmail("xiaolei.Lu@aaa.b");
        org.junit.Assert.assertTrue(email4);
    }

    @Test
    public void isMobileTest() {
        boolean m1 = Validator.isMobile("13900221432");
        org.junit.Assert.assertTrue(m1);
        boolean m2 = Validator.isMobile("015100221432");
        org.junit.Assert.assertTrue(m2);
        boolean m3 = Validator.isMobile("+8618600221432");
        org.junit.Assert.assertTrue(m3);
    }

    @Test
    public void isMatchTest() {
        String url = "http://aaa-bbb.somthing.com/a.php?a=b&c=2";
        org.junit.Assert.assertTrue(Validator.isMatchRegex(PatternPool.URL_HTTP, url));

        url = "https://aaa-bbb.somthing.com/a.php?a=b&c=2";
        org.junit.Assert.assertTrue(Validator.isMatchRegex(PatternPool.URL_HTTP, url));

        url = "https://aaa-bbb.somthing.com:8080/a.php?a=b&c=2";
        org.junit.Assert.assertTrue(Validator.isMatchRegex(PatternPool.URL_HTTP, url));
    }

    @Test
    public void isGeneralTest() {
        String str = StringUtil.EMPTY;
        boolean general = Validator.isGeneral(str, -1, 5);
        org.junit.Assert.assertTrue(general);

        str = "123_abc_ccc";
        general = Validator.isGeneral(str, -1, 100);
        org.junit.Assert.assertTrue(general);

        // 不允许中文
        str = "123_abc_ccc中文";
        general = Validator.isGeneral(str, -1, 100);
        org.junit.Assert.assertFalse(general);
    }

    @Test
    public void isPlateNumberTest() {
        org.junit.Assert.assertTrue(Validator.isPlateNumber("粤BA03205"));
        org.junit.Assert.assertTrue(Validator.isPlateNumber("闽20401领"));
    }

    @Test
    public void isChineseTest() {
        org.junit.Assert.assertTrue(Validator.isChinese("全都是中文"));
        org.junit.Assert.assertFalse(Validator.isChinese("not全都是中文"));
    }

    @Test
    public void isUUIDTest() {
        System.out.println(IdUtil.fastSimpleUUID());
        org.junit.Assert.assertTrue(Validator.isUUID(IdUtil.randomUUID()));
        org.junit.Assert.assertTrue(Validator.isUUID(IdUtil.fastSimpleUUID()));

        org.junit.Assert.assertTrue(Validator.isUUID(IdUtil.randomUUID().toUpperCase()));
        org.junit.Assert.assertTrue(Validator.isUUID(IdUtil.fastSimpleUUID().toUpperCase()));
    }

    @Test
    public void isZipCodeTest() {
        // 港
        boolean zipCode = Validator.isZipCode("999077");
        org.junit.Assert.assertTrue(zipCode);
        // 澳
        zipCode = Validator.isZipCode("999078");
        org.junit.Assert.assertTrue(zipCode);
        // 台（2020年3月起改用6位邮编，3+3）
        zipCode = Validator.isZipCode("822001");
        org.junit.Assert.assertTrue(zipCode);

        // 内蒙
        zipCode = Validator.isZipCode("016063");
        org.junit.Assert.assertTrue(zipCode);
        // 山西
        zipCode = Validator.isZipCode("045246");
        org.junit.Assert.assertTrue(zipCode);
        // 河北
        zipCode = Validator.isZipCode("066502");
        org.junit.Assert.assertTrue(zipCode);
        // 北京
        zipCode = Validator.isZipCode("102629");
        org.junit.Assert.assertTrue(zipCode);
    }

    @Test
    public void isBetweenTest() {
        org.junit.Assert.assertTrue(Validator.isBetween(0, 0, 1));
        org.junit.Assert.assertTrue(Validator.isBetween(1L, 0L, 1L));
        org.junit.Assert.assertTrue(Validator.isBetween(0.19f, 0.1f, 0.2f));
        org.junit.Assert.assertTrue(Validator.isBetween(0.19, 0.1, 0.2));
    }

    @Test
    public void isCarVinTest() {
        org.junit.Assert.assertTrue(Validator.isCarVin("LSJA24U62JG269225"));
        org.junit.Assert.assertTrue(Validator.isCarVin("LDC613P23A1305189"));
    }

    @Test
    public void isCarDrivingLicenceTest() {
        Assert.assertTrue(Validator.isCarDrivingLicence("430101758218"));
    }
}
