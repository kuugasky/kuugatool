package io.github.kuugasky.kuugatool.core.idcard;

import org.junit.jupiter.api.Test;

public class IDCardTest {

    @Test
    public void test() {
        IDCard idCard = new IDCard("150102880730303");
        System.out.println("状态=" + idCard.isValid());
        System.out.println("年龄=" + idCard.getAge());
        System.out.println("生日=" + idCard.getBirthday());
        System.out.println("生日Str=" + idCard.getBirthdayStr());
        System.out.println("城市Code=" + idCard.getCityCode());
        System.out.println("性别=" + idCard.getGender());
        System.out.println("省份=" + idCard.getProvince());
        System.out.println("省份Code=" + idCard.getProvinceCode());
    }

}