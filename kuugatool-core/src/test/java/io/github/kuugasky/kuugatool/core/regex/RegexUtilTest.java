package io.github.kuugasky.kuugatool.core.regex;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class RegexUtilTest {

    @Test
    public void isNumber() {
        // 验证文本是否纯数字
        System.out.println(RegexUtil.isNumber("kuuga111"));
        System.out.println(RegexUtil.isNumber("123456x"));
        System.out.println(RegexUtil.isNumber("123456"));
    }

    @Test
    public void isMobileSimple() {
        // 验证手机号（简单）
        System.out.println(RegexUtil.isMobileSimple("xxxxx"));
        System.out.println(RegexUtil.isMobileSimple("159199011111"));
        System.out.println(RegexUtil.isMobileSimple("15919901111"));
    }

    @Test
    public void isMobileExact() {
        // 验证手机号（精确）
        System.out.println(RegexUtil.isMobileExact("15919901111"));
        System.out.println(RegexUtil.isMobileExact("1591990111111"));
        System.out.println(RegexUtil.isMobileExact("10019901111"));
    }

    @Test
    public void isTel() {
        // 验证电话号码
        System.out.println(RegexUtil.isTel("0912619xxx"));
        System.out.println(RegexUtil.isTel("09126191111"));
        System.out.println(RegexUtil.isTel("0912-6191111"));
    }

    @Test
    public void isIDCard15() {
        // 验证身份证号码15位
        System.out.println(RegexUtil.isIDCard15("310112850409522"));
    }

    @Test
    public void isIDCard18() {
        // 验证身份证号码18位
        System.out.println(RegexUtil.isIDCard18("440582199901136377"));
    }

    @Test
    public void isEmail() {
        // 验证邮箱
        System.out.println(RegexUtil.isEmail("kuuga0410@163.com"));
    }

    @Test
    public void isURL() {
        // 验证URL
        System.out.println(RegexUtil.isURL("https://www.baidu.com/search"));
    }

    @Test
    public void isZh() {
        // 验证汉字
        System.out.println(RegexUtil.isZh("xx"));
        System.out.println(RegexUtil.isZh("秦枫"));
    }

    @Test
    public void isUsername() {
        // 验证用户名
        System.out.println(RegexUtil.isUsername("kuugasky"));
        System.out.println(RegexUtil.isUsername("kuuga_0410"));
        System.out.println(RegexUtil.isUsername("Kuuga@0410"));
        System.out.println(RegexUtil.isUsername("123456"));
        System.out.println(RegexUtil.isUsername("秦枫"));
    }

    @Test
    public void isDate() {
        // 验证yyyy-MM-dd格式的日期校验，已考虑平闰年
        System.out.println(RegexUtil.isDate("2021-03-15"));
        System.out.println(RegexUtil.isDate("2021-03-15 00:00:00"));
        System.out.println(RegexUtil.isDate("20210315"));

        System.out.println(RegexUtil.isDateTime("2021-03-15"));
        System.out.println(RegexUtil.isDateTime("2021-03-15 00:00:00"));
        System.out.println(RegexUtil.isDateTime("20210315"));
    }

    @Test
    public void isIP() {
        // 验证IP地址
        System.out.println(RegexUtil.isIP("127.0.0.1"));
        System.out.println(RegexUtil.isIP("127.0.0.0.1"));
    }

    @Test
    public void isMatch() {
        // 判断是否匹配正则
        System.out.println(RegexUtil.isMatch(RegexConstants.REGEX_IP, "127.0.0.1"));
    }

    @Test
    public void getMatches() {
        // 获取正则匹配的部分
        List<String> matches = RegexUtil.getMatches("\\d", "127.0.0.1");
        ListUtil.optimize(matches).forEach(System.out::println);

        List<String> matches2 = RegexUtil.getMatches("\\d+.?\\d+", "88.00㎡xx10.3.3");
        ListUtil.optimize(matches2).forEach(System.out::println);
    }

    @Test
    public void getSplits() {
        // 获取正则匹配分组
        String[] splits = RegexUtil.getSplits("127.0.0.1", "\\.");
        System.out.println(Arrays.toString(Arrays.stream(splits).toArray()));
    }

    @Test
    public void getReplaceFirst() {
        // 获取正则匹配分组
        String[] splits = RegexUtil.getSplits("127.0.0.1", "\\.");
        System.out.println(Arrays.toString(Arrays.stream(splits).toArray()));
    }

    @Test
    public void getReplaceAll() {
        // 获取正则匹配分组
        String splits = RegexUtil.getReplaceAll("127.0.0.1", "\\.", "-");
        System.out.println(splits);
    }

}