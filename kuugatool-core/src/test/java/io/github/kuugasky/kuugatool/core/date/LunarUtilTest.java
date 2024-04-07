package io.github.kuugasky.kuugatool.core.date;

import org.junit.jupiter.api.Test;

import java.util.Date;

class LunarUtilTest {

    @Test
    void animalsYear() {
        Date today = DateUtil.now();
        LunarUtil lunar = new LunarUtil(today);
        // 获取农历年的生肖
        String animalsYear = lunar.animalsYear();
        System.out.println(animalsYear);
    }

    @Test
    void cyclical() {
        Date today = DateUtil.now();
        LunarUtil lunar = new LunarUtil(today);
        // 获取天干地支，如:壬寅
        String cyclical = lunar.cyclical();
        System.out.println(cyclical);
    }

    @Test
    void getChinaDayString() {
        // 数字day转农历中文day
        for (int i = 1; i < 32; i++) {
            String chinaDayString = LunarUtil.getChinaDayString(i);
            System.out.println(i + " : " + chinaDayString);
        }
    }

    @Test
    void testToString() {
        Date today = DateUtil.now();
        LunarUtil lunar = new LunarUtil(today);
        // 2024年二月廿九
        System.out.println(lunar);
    }

    @Test
    void print() {
        Date today = DateUtil.now();
        LunarUtil lunar = new LunarUtil(today);
        System.out.println("北京时间：" + DateUtil.formatDate(today) + "　农历：" + lunar);
    }

}