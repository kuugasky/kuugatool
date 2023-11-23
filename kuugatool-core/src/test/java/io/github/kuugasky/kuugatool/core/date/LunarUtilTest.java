package io.github.kuugasky.kuugatool.core.date;

import org.junit.jupiter.api.Test;

import java.util.Date;

class LunarUtilTest {

    @Test
    void animalsYear() {
        Date today = DateUtil.now();
        LunarUtil lunar = new LunarUtil(today);
        String animalsYear = lunar.animalsYear();
        System.out.println(animalsYear);
    }

    @Test
    void cyclical() {
        Date today = DateUtil.now();
        LunarUtil lunar = new LunarUtil(today);
        String cyclical = lunar.cyclical();
        System.out.println(cyclical);
    }

    @Test
    void getChinaDayString() {
        String chinaDayString = LunarUtil.getChinaDayString(15);
        System.out.println(chinaDayString);
    }

    @Test
    void testToString() {
        Date today = DateUtil.now();
        LunarUtil lunar = new LunarUtil(today);
        System.out.println(lunar);
    }

    @Test
    void main() {
        Date today = DateUtil.now();
        LunarUtil lunar = new LunarUtil(today);
        System.out.println("北京时间：" + DateUtil.formatDate(today) + "　农历：" + lunar);
    }

}