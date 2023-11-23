package io.github.kuugasky.kuugatool.core.date;

import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.util.List;

class SimpleCalendarTest {

    @Test
    void getCalendarDetail() throws ParseException {
        // 获取日历详细信息
        SimpleCalendar.Element calendarDetail = SimpleCalendar.getCalendarDetail(DateUtil.now());
        System.out.println("阳历年生肖：" + calendarDetail.getAnimalYear());
        System.out.println("阳历年：" + calendarDetail.sYear);
        System.out.println("阳历月：" + calendarDetail.sMonth);
        System.out.println("阳历日：" + calendarDetail.sDay);
        System.out.println("周几：" + calendarDetail.week);
        System.out.println("农历年：" + calendarDetail.lYear);
        System.out.println("农历月：" + calendarDetail.lMonth);
        System.out.println("农历日：" + calendarDetail.lDay);
        System.out.println("农历月大写：" + calendarDetail.lMonthChinese);
        System.out.println("农历日大写：" + calendarDetail.lDayChinese);
        System.out.println("是否闰年：" + calendarDetail.isLeap);
        System.out.println("几月是闰月：" + calendarDetail.leapMonth);
        System.out.println("农历干支纪年：" + calendarDetail.cYear);
        System.out.println("农历干支纪月：" + calendarDetail.cMonth);
        System.out.println("农历干支纪日：" + calendarDetail.cDay);
        System.out.println("颜色：" + calendarDetail.color);
        System.out.println("是否今天：" + calendarDetail.isToday);
        System.out.println("农历节日：" + calendarDetail.lunarFestival);
        System.out.println("阳历节日：" + calendarDetail.solarFestival);
        System.out.println("节气：" + calendarDetail.solarTerms);
        System.out.println("宜忌事项：" + calendarDetail.sgz5);
        System.out.println("未知：" + calendarDetail.sgz3);
    }

    @Test
    void getElements() throws ParseException {
        SimpleCalendar simpleCalendar = new SimpleCalendar();
        simpleCalendar.calendar(2022, 9);
        List<SimpleCalendar.Element> elements = simpleCalendar.getElements();
        elements.forEach(x -> System.out.println(x.sMonth));
    }

    @Test
    void solarDays() {
        SimpleCalendar simpleCalendar = new SimpleCalendar();
        // 返回公历 y年某m+1月的天数
        long l = simpleCalendar.solarDays(1990, 5);
        System.out.println(l);
    }

    @Test
    void cyclical() {
        SimpleCalendar simpleCalendar = new SimpleCalendar();
        String s = simpleCalendar.cyclical(1);
        System.out.println(s);
    }

    @Test
    void cDay() {
        SimpleCalendar simpleCalendar = new SimpleCalendar();
        // 数字日期转中文日期
        String s = simpleCalendar.cDay(13);
        System.out.println(s);
    }

    @Test
    void sTerm() throws ParseException {
        SimpleCalendar simpleCalendar = new SimpleCalendar();
        // 某年的第n个节气为几日(从0小寒起算)
        int i = simpleCalendar.sTerm(1990, 5);
        System.out.println(i);
    }

    @Test
    void leapMonth() {
        SimpleCalendar simpleCalendar = new SimpleCalendar();
        // 返回农历y年闰哪个月1-12，没闰返回0
        long leapMonth = simpleCalendar.leapMonth(1990);
        System.out.println(leapMonth);
    }

    @Test
    void lYearDays() {
        SimpleCalendar simpleCalendar = new SimpleCalendar();
        // 返回农历year年的总天数(农历维度一年的总天数)
        int leapDays = simpleCalendar.lYearDays(1990);
        System.out.println(leapDays);
    }

    @Test
    void leapDays() {
        SimpleCalendar simpleCalendar = new SimpleCalendar();
        // 获取闰年闰月内的总天数，如果year不是闰年则返回0
        int leapDays = simpleCalendar.leapDays(1990);
        System.out.println(leapDays);
    }

    @Test
    void main() {
        SimpleCalendar simpleCalendar = new SimpleCalendar();
        System.out.println(simpleCalendar);
    }
}