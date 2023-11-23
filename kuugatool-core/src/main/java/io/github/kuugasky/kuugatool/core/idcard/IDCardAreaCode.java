package io.github.kuugasky.kuugatool.core.idcard;

import java.util.Hashtable;
import java.util.Map;

/**
 * 身份证地区码
 *
 * @author kuuga
 */
public final class IDCardAreaCode {

    private IDCardAreaCode() {
    }

    static final Map<String, String> AREA_CODE = new Hashtable<>(35);

    static {

        AREA_CODE.put("11", "北京");

        AREA_CODE.put("12", "天津");

        AREA_CODE.put("13", "河北");

        AREA_CODE.put("14", "山西");

        AREA_CODE.put("15", "内蒙古");

        AREA_CODE.put("21", "辽宁");

        AREA_CODE.put("22", "吉林");

        AREA_CODE.put("23", "黑龙江");

        AREA_CODE.put("31", "上海");

        AREA_CODE.put("32", "江苏");

        AREA_CODE.put("33", "浙江");

        AREA_CODE.put("34", "安徽");

        AREA_CODE.put("35", "福建");

        AREA_CODE.put("36", "江西");

        AREA_CODE.put("37", "山东");

        AREA_CODE.put("41", "河南");

        AREA_CODE.put("42", "湖北");

        AREA_CODE.put("43", "湖南");

        AREA_CODE.put("44", "广东");

        AREA_CODE.put("45", "广西");

        AREA_CODE.put("46", "海南");

        AREA_CODE.put("50", "重庆");

        AREA_CODE.put("51", "四川");

        AREA_CODE.put("52", "贵州");

        AREA_CODE.put("53", "云南");

        AREA_CODE.put("54", "西藏");

        AREA_CODE.put("61", "陕西");

        AREA_CODE.put("62", "甘肃");

        AREA_CODE.put("63", "青海");

        AREA_CODE.put("64", "宁夏");

        AREA_CODE.put("65", "新疆");

        AREA_CODE.put("71", "台湾");

        AREA_CODE.put("81", "香港");

        AREA_CODE.put("82", "澳门");

        AREA_CODE.put("91", "国外");

    }

}