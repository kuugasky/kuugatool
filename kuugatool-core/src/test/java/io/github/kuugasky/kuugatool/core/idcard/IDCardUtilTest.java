package io.github.kuugasky.kuugatool.core.idcard;

import org.junit.jupiter.api.Test;

public class IDCardUtilTest {

    @Test
    public void test() {
        // String idCardNo = "421022881006452";
        // String idCardNo = "321083197812162119";
        String idCardNo = "150102880730303";
        // String idCardNo = "150102198807303035";
        // String idCardNo = "232332198706232427";
        System.out.println(IDCardUtil.isValid(idCardNo));
        System.out.println(IDCardUtil.getAge(idCardNo));
        System.out.println(IDCardUtil.getGender(idCardNo));
        System.out.println(IDCardUtil.getProvinceCode(idCardNo));
        System.out.println(IDCardUtil.getProvince(idCardNo));
        System.out.println(IDCardUtil.getCityCode(idCardNo));
        System.out.println(IDCardUtil.getBirthday(idCardNo));
        System.out.println(IDCardUtil.getBirthdayStr(idCardNo));
        System.out.println(IDCardUtil.convert15To18(idCardNo));
    }

}