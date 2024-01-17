package io.github.kuugasky.kuugatool.core.date;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.date.interval.BetweenFormatter;
import io.github.kuugasky.kuugatool.core.lang.Console;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DateUtilTest {

    @Test
    void of() {
        Date of = DateUtil.of(2021, 1, 6);
        System.out.println(DateUtil.format(of));
    }

    @Test
    void testOf() {
        Date of = DateUtil.of(2021, 1, 6, 12, 1, 1);
        System.out.println(DateUtil.format(of));
    }

    @Test
    void ofTime() {
        Date of = DateUtil.ofTime(12, 1, 6);
        System.out.println(DateUtil.format(of));
    }

    @Test
    void testOfTime() {
        Date of = DateUtil.ofTime(12, 1, 6, 1000);
        System.out.println(DateUtil.format(DateFormat.yyyy_MM_dd_HH_mm_ss_SSS, of));
    }

    @Test
    void testOf1() {
        Date of = DateUtil.of(2021, Month.MAY, 6);
        System.out.println(DateUtil.format(of));
    }

    @Test
    void now() {
        System.out.println(DateUtil.now());
    }

    @Test
    void today() {
        System.out.println(DateUtil.today());
    }

    @Test
    void testToday() {
        System.out.println(DateUtil.today(DateFormat.yyyy_MM_dd_HH_mm_ss));
    }

    @Test
    void todayTime() {
        System.out.println(DateUtil.todayTime());
    }

    @Test
    void checkMonthFormat() {
        System.out.println(DateUtil.checkMonthFormat("2021-01"));
    }

    @Test
    void checkDateFormat() {
        System.out.println(DateUtil.checkDateFormat("2021-01-11"));
    }

    @Test
    void toDate() {
        LocalDate localDate = LocalDate.of(2021, 1, 12);
        System.out.println(DateUtil.toDate(localDate));
    }

    @Test
    void testToDate() {
        LocalTime localTime = LocalTime.of(11, 1, 12);
        System.out.println(DateUtil.toDate(localTime));
    }

    @Test
    void testToDate1() {
        LocalDateTime localDateTime = LocalDateTime.of(2021, 1, 7, 11, 1, 12);
        System.out.println(DateUtil.toDate(localDateTime));
    }

    @Test
    void toLocalDate() {
        LocalDate localDate = DateUtil.toLocalDate(DateUtil.now());
        System.out.println(localDate);
    }

    @Test
    void toLocalTime() {
        LocalTime localTime = DateUtil.toLocalTime(DateUtil.now());
        System.out.println(localTime);
    }

    @Test
    void toLocalDateTime() {
        LocalDateTime localDateTime = DateUtil.toLocalDateTime(DateUtil.now());
        System.out.println(localDateTime);
    }

    @Test
    void moreOrLessSeconds() {
        // 10s后
        Date dateMore = DateUtil.moreOrLessSeconds(DateUtil.now(), 10);
        System.out.println(dateMore);
        // 10s前
        Date dateLess = DateUtil.moreOrLessSeconds(DateUtil.now(), -10);
        System.out.println(dateLess);
    }

    @Test
    void moreOrLessMinutes() {
        // 10m后
        Date dateMore = DateUtil.moreOrLessMinutes(DateUtil.now(), 10);
        System.out.println(dateMore);
        // 10m前
        Date dateLess = DateUtil.moreOrLessMinutes(DateUtil.now(), -10);
        System.out.println(dateLess);
    }

    @Test
    void moreOrLessHours() {
        // 10h后
        Date dateMore = DateUtil.moreOrLessHours(DateUtil.now(), 10);
        System.out.println(dateMore);
        // 10h前
        Date dateLess = DateUtil.moreOrLessHours(DateUtil.now(), -10);
        System.out.println(dateLess);
    }

    @Test
    void moreOrLessDays() {
        // 10d后
        Date dateMore = DateUtil.moreOrLessDays(DateUtil.now(), 10);
        System.out.println(dateMore);
        // 10d前
        Date dateLess = DateUtil.moreOrLessDays(DateUtil.now(), -10);
        System.out.println(dateLess);
    }

    @Test
    void moreOrLessMonths() {
        // 10个月后
        Date dateMore = DateUtil.moreOrLessMonths(DateUtil.now(), 10);
        System.out.println(dateMore);
        // 10个月前
        Date dateLess = DateUtil.moreOrLessMonths(DateUtil.now(), -10);
        System.out.println(dateLess);
    }

    @Test
    void moreOrLessYears() {
        // 10年后
        Date dateMore = DateUtil.moreOrLessYears(DateUtil.now(), 10);
        System.out.println(dateMore);
        // 10年前
        Date dateLess = DateUtil.moreOrLessYears(DateUtil.now(), -10);
        System.out.println(dateLess);
    }

    @Test
    void moreOrLessWeeks() {
        // 10周后
        Date dateMore = DateUtil.moreOrLessWeeks(DateUtil.now(), 10);
        System.out.println(dateMore);
        // 10周前
        Date dateLess = DateUtil.moreOrLessWeeks(DateUtil.now(), -10);
        System.out.println(dateLess);
    }

    @Test
    void moreOrLess() {
        // 10天后
        Date dateMore = DateUtil.moreOrLess(DateUtil.now(), 10, ChronoUnit.DAYS);
        System.out.println(dateMore);
        // 10天前
        Date dateLess = DateUtil.moreOrLessWeeks(DateUtil.now(), -10);
        System.out.println(dateLess);
    }

    @Test
    void ofYearDay() {
        LocalDate localDate = DateUtil.ofYearDay(2021, 100);
        System.out.println(localDate);
    }

    @Test
    void ofSecondOfDay() {
        LocalTime localTime = DateUtil.ofSecondOfDay(9999L);
        System.out.println(localTime);
    }

    @Test
    void ofNanoOfDay() {
        LocalTime localTime = DateUtil.ofNanoOfDay(9999999999L);
        System.out.println(localTime);
    }

    @Test
    void firstDayOfMonth() {
        Date date = DateUtil.firstDayOfMonth(DateUtil.now());
        System.out.println(DateUtil.format(date));
    }

    @Test
    void firstDayOfNextMonth() {
        // 下个月第一天
        Date date = DateUtil.firstDayOfNextMonth(DateUtil.now());
        System.out.println(DateUtil.format(date));
    }

    @Test
    void firstDayOfYear() {
        // 所在年第一天
        Date date = DateUtil.firstDayOfYear(DateUtil.now());
        System.out.println(DateUtil.format(date));
    }

    @Test
    void firstDayOfNextYear() {
        // 下一年第一天
        Date date = DateUtil.firstDayOfNextYear(DateUtil.now());
        System.out.println(DateUtil.format(date));
    }

    @Test
    void firstDayOfWeek() {
        // 所在周第一天
        Date date = DateUtil.firstDayOfWeek(DateUtil.now());
        System.out.println(DateUtil.format(date));
    }

    @Test
    void firstDayOfNextWeek() {
        // 下一周第一天
        Date date = DateUtil.firstDayOfNextWeek(DateUtil.now());
        System.out.println(DateUtil.format(date));
    }

    @Test
    void lastDayOfMonth() {
        // 所在月最后一天
        Date date = DateUtil.lastDayOfMonth(DateUtil.now());
        System.out.println(DateUtil.format(date));
    }

    @Test
    void lastDayOfNextMonth() {
        // 下个月最后一天
        Date date = DateUtil.lastDayOfNextMonth(DateUtil.now());
        System.out.println(DateUtil.format(date));
    }

    @Test
    void lastDayOfYear() {
        // 所在年最后一天
        Date date = DateUtil.lastDayOfYear(DateUtil.now());
        System.out.println(DateUtil.format(date));
    }

    @Test
    void lastDayOfNextYear() {
        // 下一年最后一天
        Date date = DateUtil.lastDayOfNextYear(DateUtil.now());
        System.out.println(DateUtil.format(date));
    }

    @Test
    void lastDayOfWeek() {
        // 所在周最后一天
        Date date = DateUtil.lastDayOfWeek(DateUtil.now());
        System.out.println(DateUtil.format(date));
    }

    @Test
    void lastDayOfNextWeek() {
        // 下一周最后一天
        Date date = DateUtil.lastDayOfNextWeek(DateUtil.now());
        System.out.println(DateUtil.format(date));
    }

    @Test
    void format() {
        System.out.println(DateUtil.format(DateUtil.now()));
    }

    @Test
    void testFormat() {
        System.out.println(DateUtil.format(DateFormat.yyyy_MM_dd_HH_mm_ss, DateUtil.now()));
    }

    @Test
    void formatDate() {
        System.out.println(DateUtil.formatDate(DateUtil.now()));
    }

    @Test
    void formatTime() {
        System.out.println(DateUtil.formatTime(DateUtil.now()));
    }

    @Test
    void formatDateTime() {
        System.out.println(DateUtil.formatDateTime(DateUtil.now()));
    }

    @Test
    void parse() {
        // System.out.println(DateUtil.parse(DateFormat.yyyy_MM_dd, "2021-01-07"));
        // System.out.println(DateUtil.parse(DateFormat.yyyy_MM_dd2, "2021/01/07"));
        System.out.println(DateUtil.parse(DateFormat.yyyy_MM_dd3, "2021年01月07日"));
    }

    @Test
    void parseTimestamp() {
        System.out.println(DateUtil.parseTimestamp(DateUtil.currentTimeSeconds()));
        System.out.println(DateUtil.parseTimestamp(DateUtil.currentTimeMillis()));
        System.out.println(DateUtil.parseTimeMillis(DateUtil.currentTimeMillis()));
    }

    @Test
    void parseLocalDate() {
        System.out.println(DateUtil.parseLocalDate("2021-01-07"));
    }

    @Test
    void parseLocalDateTime() {
        // System.out.println(DateUtil.toLocalDateTime(DateUtil.now()));
        System.out.println(DateUtil.parseLocalDateTime("2021-01-07T20:15:15.357"));
    }

    @Test
    void parseLocalTime() {
        System.out.println(DateUtil.parseLocalTime("12:10:12"));
    }

    @Test
    void parseTimeSeconds() {
        System.out.println(DateUtil.parseTimeSeconds(DateUtil.currentTimeSeconds()));
    }

    @Test
    void testParseTimestamp() {
        System.out.println(DateUtil.parseTimestamp(DateUtil.currentTimeMillisByClock(), TimestampType.MILLISECOND));
    }

    @Test
    void currentMillSeconds() {
        System.out.println(DateUtil.currentTimeMillisByClock());
    }

    @Test
    void milliSecondsLeftToday() {
        System.out.println(DateUtil.milliSecondsLeftToday());
    }

    @Test
    void secondsLeftToday() {
        System.out.println(DateUtil.secondsLeftToday());
    }

    @Test
    void milliSecondsLeftTodayForJDK() {
        System.out.println(DateUtil.milliSecondsLeftTodayForJDK());
    }

    @Test
    void secondsLeftTodayForJDK() {
        System.out.println(DateUtil.secondsLeftTodayForJDK());
    }

    @Test
    void initFirstAndLastTime() {
        Date before = DateUtil.moreOrLessDays(DateUtil.now(), -3);
        Date after = DateUtil.moreOrLessDays(DateUtil.now(), 3);
        System.out.println(DateUtil.initFirstAndLastTime(before, after));
    }

    @Test
    void yearDifference() {
        // 年份差值
        Date before = DateUtil.moreOrLessYears(DateUtil.now(), -3);
        Date after = DateUtil.moreOrLessYears(DateUtil.now(), 3);
        System.out.println(DateUtil.yearDifference(before, after));
    }

    @Test
    void monthDifference() {
        // 月份差值
        Date before = DateUtil.moreOrLessMonths(DateUtil.now(), -3);
        Date after = DateUtil.moreOrLessMonths(DateUtil.now(), 3);
        System.out.println(DateUtil.monthDifference(before, after));
    }

    @Test
    void dayDifference() {
        // 天数差值
        Date before = DateUtil.moreOrLessDays(DateUtil.now(), -3);
        Date after = DateUtil.moreOrLessDays(DateUtil.now(), 3);
        System.out.println(DateUtil.dayDifference(before, after));
    }

    @Test
    void dayDifferenceZeroTime() {
        // 天数差值
        Date before = DateUtil.moreOrLessDays(DateUtil.now(), -3);
        Date after = DateUtil.moreOrLessDays(DateUtil.now(), 3);
        System.out.println(DateUtil.dayDifferenceZeroTime(before, after));
    }

    @Test
    void hourDifference() {
        // 时数差值
        Date before = DateUtil.moreOrLessHours(DateUtil.now(), -3);
        Date after = DateUtil.moreOrLessHours(DateUtil.now(), 3);
        System.out.println(DateUtil.hourDifference(before, after));
    }

    @Test
    void minuteDifference() {
        // 分数差值
        Date before = DateUtil.moreOrLessMinutes(DateUtil.now(), -3);
        Date after = DateUtil.moreOrLessMinutes(DateUtil.now(), 3);
        System.out.println(DateUtil.minuteDifference(before, after));
    }

    @Test
    void secondDifference() {
        // 秒数差值
        Date before = DateUtil.moreOrLessSeconds(DateUtil.now(), -3);
        Date after = DateUtil.moreOrLessSeconds(DateUtil.now(), 3);
        System.out.println(DateUtil.secondDifference(before, after));
    }

    @Test
    void timeDifferenceToString() {
        Date before = DateUtil.moreOrLessSeconds(DateUtil.now(), -3000);
        System.out.println(DateUtil.timeDifferenceToString(before));
    }

    @Test
    void dayOfWeekForFirstMonth() {
        // date所在年的第一个月的第一个周几
        Date dayOfWeekForFirstMonth = DateUtil.dayOfFirstMWeekForFirstMonth(DateUtil.now(), DayOfWeek.FRIDAY);
        System.out.println(DateUtil.format(dayOfWeekForFirstMonth));
    }

    @Test
    void printWeekendOfCurrentMonth() {
        // 打印本月的周末
        DateUtil.printWeekendOfCurrentMonth();
    }

    @Test
    void printWeekend() {
        DateUtil.printWeekend(DateUtil.moreOrLessMonths(DateUtil.now(), 1));
    }

    @Test
    void between() {
        // 开区间
        // 不包含起止时间点
        Date before = DateUtil.moreOrLessDays(DateUtil.now(), -3);
        Date after = DateUtil.moreOrLessDays(DateUtil.now(), 3);
        boolean between = DateUtil.between(before, after, DateUtil.now());
        System.out.println(between);
        boolean between1 = DateUtil.between(DateUtil.now(), after, DateUtil.now());
        System.out.println(between1);
    }

    @Test
    void include() {
        // 闭区间
        // 包含起止时间点
        Date before = DateUtil.moreOrLessDays(DateUtil.now(), -3);
        Date after = DateUtil.moreOrLessDays(DateUtil.now(), 3);
        boolean between = DateUtil.include(before, after, DateUtil.now());
        System.out.println(between);
        boolean between1 = DateUtil.include(DateUtil.now(), after, DateUtil.now());
        System.out.println(between1);
    }

    @Test
    void before() {
        Date before = DateUtil.moreOrLessDays(DateUtil.now(), -3);
        System.out.println(DateUtil.isBefore(before, DateUtil.now()));
        System.out.println(DateUtil.isBefore(DateUtil.now(), DateUtil.now()));
        System.out.println(DateUtil.isBefore(DateUtil.moreOrLessSeconds(DateUtil.now(), -1), DateUtil.now()));
    }

    @Test
    void after() {
        Date after = DateUtil.moreOrLessDays(DateUtil.now(), 3);
        System.out.println(DateUtil.isAfter(after, DateUtil.now()));
        System.out.println(DateUtil.isAfter(DateUtil.now(), DateUtil.now()));
        System.out.println(DateUtil.isAfter(DateUtil.moreOrLessSeconds(DateUtil.now(), 1), DateUtil.now()));
    }

    @Test
    void dateIntervalList() {
        // 获取两个日期间所有dateList
        Date before = DateUtil.moreOrLessDays(DateUtil.now(), -5);
        Date after = DateUtil.moreOrLessDays(DateUtil.now(), 3);
        List<Date> dates = DateUtil.dateIntervalList(before, after);
        ListUtil.optimize(dates).forEach(date -> System.out.println(DateUtil.format(date)));
    }

    @Test
    void weeks() {
        // 获取date对应周的周一和周日
        List<Date> weeks = DateUtil.weeks(DateUtil.now());
        ListUtil.optimize(weeks).forEach(week -> System.out.println(DateUtil.format(week)));
    }

    @Test
    void weeksFull() {
        // 获取date对应周的周一至周日
        List<Date> weeks = DateUtil.weeksFull(DateUtil.now());
        ListUtil.optimize(weeks).forEach(week -> System.out.println(DateUtil.format(week)));
    }

    @Test
    void getDayOfWeek() {
        System.out.println("今天周几（中文）:" + DateUtil.getDayOfWeek().getWeek());
        System.out.println("今天周几（英文）:" + DateUtil.getDayOfWeek().getDayOfWeek());
        System.out.println("今天星期几（中文）:" + DateUtil.getDayOfWeek().getXq());
        System.out.println("今天是中国一周内的第几天:" + DateUtil.getDayOfWeek().getIndexOfChina());
        System.out.println("今天是国外一周内的第几天:" + DateUtil.getDayOfWeek().getIndexOfForeign());

        System.out.println(StringUtil.repeatNormal());
        // 明天是周几
        DayOfWeekEnum dayOfWeekEnum = DateUtil.getDayOfWeek(DateUtil.moreOrLessDays(DateUtil.now(), 1));
        System.out.println(dayOfWeekEnum.getWeek());
        System.out.println(dayOfWeekEnum.getXq());
        System.out.println(dayOfWeekEnum.getIndexOfChina());
        System.out.println(dayOfWeekEnum.getIndexOfForeign());
        System.out.println(dayOfWeekEnum.getDayOfWeek());
    }

    @Test
    void dayOfTheYear() {
        // 当天是在一个月内的第几天
        System.out.println(DateUtil.dayOfTheYear());
    }

    @Test
    void testDayOfTheYear() {
        // date是在一年内的第几天
        System.out.println(DateUtil.dayOfTheYear(DateUtil.moreOrLessDays(DateUtil.now(), -10)));
    }

    @Test
    void dayOfTheMonth() {
        // 当天是在一个月内的第几天
        System.out.println(DateUtil.dayOfTheMonth());
    }

    @Test
    void testDayOfTheMonth() {
        // date是在一个月内的第几天
        System.out.println(DateUtil.dayOfTheMonth(DateUtil.moreOrLessDays(DateUtil.now(), -10)));
    }

    @Test
    void dayOfTheWeek() {
        // 当天是在一周的第几天
        System.out.println(DateUtil.dayOfTheWeek());
    }

    @Test
    void testDayOfTheWeek() {
        // date是在一周的第几天
        System.out.println(DateUtil.dayOfTheWeek(DateUtil.moreOrLessDays(DateUtil.now(), -10)));
    }

    @Test
    void isSameDay() {
        Date before = DateUtil.moreOrLessDays(DateUtil.now(), -3);
        Date after = DateUtil.moreOrLessDays(DateUtil.now(), 3);
        // 是否同一天
        System.out.println(DateUtil.isSameDay(before, after));
        System.out.println(DateUtil.isSameDay(DateUtil.now(), DateUtil.now()));
    }

    @Test
    void yearMonthToday() {
        // 获取年月文本：2019-07
        System.out.println(DateUtil.yearMonthToday());
        System.out.println(DateUtil.format(DateFormat.yyyyMM2, DateUtil.now()));
    }

    @Test
    void yearMonth() {
        // 获取年月文本：2019-07
        System.out.println(DateUtil.yearMonth(DateUtil.now()));
    }

    @Test
    void monthDayToday() {
        // 获取月日文本：07-09
        System.out.println(DateUtil.monthDayToday());
    }

    @Test
    void monthDay() {
        // 获取月日文本：07-09
        System.out.println(DateUtil.monthDay(DateUtil.now()));
    }

    @Test
    void isBirthdayToday() {
        System.out.println(DateUtil.isBirthdayToday(DateUtil.now()));
    }

    @Test
    void isLeapYear() {
        // 判断当年是否闰年
        System.out.println(DateUtil.isLeapYear());
    }

    @Test
    void testIsLeapYear() {
        // 判断当年是否闰年
        System.out.println(DateUtil.isLeapYear(DateUtil.moreOrLessYears(DateUtil.now(), -1)));
    }

    @Test
    void testIsLeapYear1() {
        // 判断当年是否闰年
        System.out.println(DateUtil.isLeapYear(2022));
    }

    @Test
    void getTimestampOfSecond() {
        // 获取日期的秒级时间戳
        System.out.println(DateUtil.getTimeSeconds(DateUtil.now()));
    }

    @Test
    void getTimestamp() {
        // 获取日期的毫秒级时间戳
        System.out.println(DateUtil.getTimeMillis(DateUtil.now()));
    }

    @Test
    void zeroOfDay() {
        // 获取指定日期零点 00:00:00
        Date date = DateUtil.zeroOfDay(DateUtil.now());
        System.out.println(DateUtil.format(date));
    }

    @Test
    void lastMomentOfDay() {
        Date date = DateUtil.lastMomentOfDay(DateUtil.now());
        System.out.println(DateUtil.format(date));
    }

    @Test
    void dayDifferenceToday() {
        // 判断目标日期与当天相差天数是否不超过days
        // 目标日期无论是早于当前时间还是晚于当前时间，最终取绝对值与days对比
        Date before = DateUtil.moreOrLessDays(DateUtil.now(), 3);
        System.out.println(DateUtil.dayDifferenceToday(before, 1));
        Date before1 = DateUtil.moreOrLessDays(DateUtil.now(), -1);
        System.out.println(DateUtil.dayDifferenceToday(before1, 1));
    }

    @Test
    void testDayDifferenceToday() {
        // 判断目标日期与当天相差天数是否不超过days
        // 目标日期无论是早于当前时间还是晚于当前时间，最终取绝对值与days对比
        // absolute：true 超过一秒都算2天
        // absolute：false 范围值，超过一天没超过2天的都算一天
        Date before = DateUtil.moreOrLessDays(DateUtil.now(), 3);
        System.out.println(DateUtil.dayDifferenceToday(before, 1, true));
        Date before1 = DateUtil.moreOrLessDays(DateUtil.now(), -1);
        System.out.println(DateUtil.dayDifferenceToday(before1, 1, true));
    }

    @Test
    void dayDifferenceForAbsolute() {
        // 计算两个日期相差天数（绝对值，超过一秒都算是超过一天）
        Date before = DateUtil.moreOrLessDays(DateUtil.now(), -3);
        int i = DateUtil.dayDifferenceForAbsolute(before, DateUtil.now());
        System.out.println(i);
    }

    @Test
    void within24Hours() {
        System.out.println(DateUtil.within24Hours(DateUtil.now()));
        System.out.println(DateUtil.within24Hours(DateUtil.moreOrLessHours(DateUtil.now(), -23)));
    }

    @Test
    void toYearMonth() {
        System.out.println(DateUtil.toYearMonth(DateUtil.now()));
    }

    @Test
    void toMonthDay() {
        System.out.println(DateUtil.toMonthDay(DateUtil.now()));
    }

    @Test
    void equalsDay() {
        Date date1 = DateUtil.moreOrLessDays(DateUtil.now(), -1);
        Date date2 = DateUtil.moreOrLessDays(DateUtil.now(), -1);
        System.out.println(DateUtil.equalsDay(date1, date2));
    }

    @Test
    void equalsMonthDay() {
        Date date1 = DateUtil.moreOrLessDays(DateUtil.now(), -1);
        Date date2 = DateUtil.moreOrLessDays(DateUtil.now(), -1);
        System.out.println(DateUtil.equalsMonthDay(date1, date2));
    }

    @Test
    void equalsYearMonth() {
        Date date1 = DateUtil.moreOrLessDays(DateUtil.now(), -1);
        Date date2 = DateUtil.moreOrLessDays(DateUtil.now(), -1);
        System.out.println(DateUtil.equalsYearMonth(date1, date2));
    }

    @Test
    public void testEqualsYear_sameYear_returnTrue() {
        Date date1 = new Date();
        Date date2 = new Date();

        boolean result = DateUtil.equalsYear(date1, date2);

        assertTrue(result);
    }

    @Test
    public void testEqualsYear_differentYear_returnFalse() {
        Date date1 = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 1);
        Date date2 = new Date(calendar.getTime().getTime());

        boolean result = DateUtil.equalsYear(date1, date2);

        assertFalse(result);
    }

    @Test
    void getLengthOfMonth() {
        System.out.println(DateUtil.lengthOfMonth());
    }

    @Test
    void testGetLengthOfMonth() {
        Date date = DateUtil.moreOrLessDays(DateUtil.now(), -1);
        System.out.println(DateUtil.lengthOfMonth(date));
    }

    @Test
    void thisYear() {
        System.out.println(DateUtil.thisYear());
    }

    @Test
    void getYear() {
        System.out.println(DateUtil.getYear(DateUtil.now()));
    }

    @Test
    void thisMonth() {
        System.out.println(DateUtil.thisMonth());
    }

    @Test
    void getMonth() {
        System.out.println(DateUtil.getMonth(DateUtil.now()));
    }

    @Test
    void getDay() {
        System.out.println(DateUtil.getDay(DateUtil.now()));
    }

    @Test
    void thisDay() {
        System.out.println(DateUtil.thisDay());
    }

    @Test
    void thisHour() {
        System.out.println(DateUtil.thisHour());
    }

    @Test
    void getHour() {
        System.out.println(DateUtil.getHour(DateUtil.now()));
    }

    @Test
    void thisMinute() {
        System.out.println(DateUtil.thisMinute());
    }

    @Test
    void getMinute() {
        System.out.println(DateUtil.getMinute(DateUtil.now()));
    }

    @Test
    void thisSecond() {
        System.out.println(DateUtil.thisSecond());
    }

    @Test
    void getSecond() {
        System.out.println(DateUtil.getSecond(DateUtil.now()));
    }

    public static void main(String[] args) {
        System.out.println(DateFormat.yyyyMMdd.name() + "=" + DateUtil.format(DateFormat.yyyy_MM_dd_HH_mm_ss, DateUtil.parse(DateFormat.yyyyMMdd, "19900513")));
        System.out.println(DateFormat.yyyyMM.name() + "=" + DateUtil.format(DateFormat.yyyy_MM_dd_HH_mm_ss, DateUtil.parse(DateFormat.yyyyMM, "199005")));
        System.out.println(DateFormat.yyyyMM2.name() + "=" + DateUtil.format(DateFormat.yyyy_MM_dd_HH_mm_ss, DateUtil.parse(DateFormat.yyyyMM2, "1999-05")));
        System.out.println(DateFormat.yyyyMM3.name() + "=" + DateUtil.format(DateFormat.yyyy_MM_dd_HH_mm_ss, DateUtil.parse(DateFormat.yyyyMM3, "1999/05")));
        System.out.println(DateFormat.yyMMdd.name() + "=" + DateUtil.format(DateFormat.yyyy_MM_dd_HH_mm_ss, DateUtil.parse(DateFormat.yyMMdd, "900513")));
        System.out.println(DateFormat.MMdd.name() + "=" + DateUtil.format(DateFormat.yyyy_MM_dd_HH_mm_ss, DateUtil.parse(DateFormat.MMdd, "0513")));
        System.out.println(DateFormat.MMdd2.name() + "=" + DateUtil.format(DateFormat.yyyy_MM_dd_HH_mm_ss, DateUtil.parse(DateFormat.MMdd2, "05-13")));
        System.out.println(DateFormat.MMdd3.name() + "=" + DateUtil.format(DateFormat.yyyy_MM_dd_HH_mm_ss, DateUtil.parse(DateFormat.MMdd3, "05/13")));
        System.out.println(DateFormat.yyyy_MM_dd.name() + "=" + DateUtil.format(DateFormat.yyyy_MM_dd_HH_mm_ss, DateUtil.parse(DateFormat.yyyy_MM_dd, "1990-05-13")));
        System.out.println(DateFormat.yyyy_MM_dd2.name() + "=" + DateUtil.format(DateFormat.yyyy_MM_dd_HH_mm_ss, DateUtil.parse(DateFormat.yyyy_MM_dd2, "1990/05/13")));
        System.out.println(DateFormat.HH_mm_ss.name() + "=" + DateUtil.format(DateFormat.yyyy_MM_dd_HH_mm_ss, DateUtil.parse(DateFormat.HH_mm_ss, "10:11:12")));
        System.out.println(DateFormat.yyyyMMddHHmmss.name() + "=" + DateUtil.format(DateFormat.yyyy_MM_dd_HH_mm_ss, DateUtil.parse(DateFormat.yyyyMMddHHmmss, "19900513101112")));
        System.out.println(DateFormat.yyyy_MM_dd_HH_mm.name() + "=" + DateUtil.format(DateFormat.yyyy_MM_dd_HH_mm_ss, DateUtil.parse(DateFormat.yyyy_MM_dd_HH_mm, "1990-05-13 10:11")));
        System.out.println(DateFormat.yyyy_MM_dd_HH_mm_ss.name() + "=" + DateUtil.format(DateFormat.yyyy_MM_dd_HH_mm_ss, DateUtil.parse(DateFormat.yyyy_MM_dd_HH_mm_ss, "1990-05-13 10:11:12")));
        System.out.println(DateFormat.yyyy_MM_dd_HH_mm_ss2.name() + "=" + DateUtil.format(DateFormat.yyyy_MM_dd_HH_mm_ss, DateUtil.parse(DateFormat.yyyy_MM_dd_HH_mm_ss2, "1990/05/13 10:11:12")));
        System.out.println(DateFormat.yyyy_MM_dd_HH_mm_ss_SSS.name() + "=" + DateUtil.format(DateFormat.yyyy_MM_dd_HH_mm_ss, DateUtil.parse(DateFormat.yyyy_MM_dd_HH_mm_ss_SSS, "1990-05-13 10:11:12.999")));

        Console.redLog(StringUtil.repeat("=", 200));

        Date now = DateUtil.now();
        System.out.println(DateFormat.yyyyMMdd.name() + "=" + DateUtil.format(DateFormat.yyyyMMdd, now));
        System.out.println(DateFormat.yyyyMM.name() + "=" + DateUtil.format(DateFormat.yyyyMM, now));
        System.out.println(DateFormat.yyyyMM2.name() + "=" + DateUtil.format(DateFormat.yyyyMM2, now));
        System.out.println(DateFormat.yyyyMM3.name() + "=" + DateUtil.format(DateFormat.yyyyMM3, now));
        System.out.println(DateFormat.yyMMdd.name() + "=" + DateUtil.format(DateFormat.yyMMdd, now));
        System.out.println(DateFormat.MMdd.name() + "=" + DateUtil.format(DateFormat.MMdd, now));
        System.out.println(DateFormat.MMdd2.name() + "=" + DateUtil.format(DateFormat.MMdd2, now));
        System.out.println(DateFormat.MMdd3.name() + "=" + DateUtil.format(DateFormat.MMdd3, now));
        System.out.println(DateFormat.yyyy_MM_dd.name() + "=" + DateUtil.format(DateFormat.yyyy_MM_dd, now));
        System.out.println(DateFormat.yyyy_MM_dd2.name() + "=" + DateUtil.format(DateFormat.yyyy_MM_dd2, now));
        System.out.println(DateFormat.HH_mm_ss.name() + "=" + DateUtil.format(DateFormat.HH_mm_ss, now));
        System.out.println(DateFormat.yyyyMMddHHmmss.name() + "=" + DateUtil.format(DateFormat.yyyyMMddHHmmss, now));
        System.out.println(DateFormat.yyyy_MM_dd_HH_mm.name() + "=" + DateUtil.format(DateFormat.yyyy_MM_dd_HH_mm, now));
        System.out.println(DateFormat.yyyy_MM_dd_HH_mm_ss.name() + "=" + DateUtil.format(DateFormat.yyyy_MM_dd_HH_mm_ss, now));
        System.out.println(DateFormat.yyyy_MM_dd_HH_mm_ss2.name() + "=" + DateUtil.format(DateFormat.yyyy_MM_dd_HH_mm_ss2, now));
        System.out.println(DateFormat.yyyy_MM_dd_HH_mm_ss_SSS.name() + "=" + DateUtil.format(DateFormat.yyyy_MM_dd_HH_mm_ss_SSS, now));
    }

    @Test
    void parseSenior() {
        System.out.println(DateUtil.formatDateTime(DateUtil.parseSenior("yyyy=MM=dd HH$mm$ss", "1990=05=13 12$13$14")));
        System.out.println(DateUtil.formatDateTime(DateUtil.parseSenior("yyyy=MM=dd HH时mm分ss秒", "1990=05=13 12时13分14秒")));
    }

    @Test
    void formatSenior() {
        System.out.println(DateUtil.formatSenior("yyyy 年MM月dd 日 hh:mm a", DateUtil.now()));
        System.out.println(DateUtil.formatSenior("yyyy年mm月dd日 hh:mm a", DateUtil.now()));
        System.out.println(DateUtil.formatSenior("yyyy年MM月", DateUtil.now()));
        System.out.println(DateUtil.formatSenior("MMdd", DateUtil.now()));
        System.out.println(DateUtil.formatSenior("yyyyMM", DateUtil.now()));
        System.out.println(DateUtil.formatSenior("HHmmss", DateUtil.now()));
        System.out.println(DateUtil.formatSenior("HHmmss a", DateUtil.now()));
        System.out.println(DateUtil.formatSenior("yyyy/MM/dd HH:mm:ss a", DateUtil.now()));
        System.out.println(DateUtil.formatSenior("yyyy/MM/dd HH:mm:ss E a", DateUtil.now()));
    }

    @Test
    void intervalTimeDifferenceShort() {
        Date form = DateUtil.parse(DateFormat.yyyy_MM_dd_HH_mm_ss, "2021-11-01 00:00:00");
        Date to = DateUtil.parse(DateFormat.yyyy_MM_dd_HH_mm_ss, "2021-11-03 10:20:30");

        String s = DateUtil.intervalTimeDifferenceShort(form, to);
        System.out.println(s);

        long millisDifference = DateUtil.millisDifference(form, to);
        System.out.println(millisDifference);
        BetweenFormatter betweenFormatter = new BetweenFormatter(millisDifference, BetweenFormatter.Level.SECOND);
        System.out.println(betweenFormatter.format());
    }

    @Test
    void intervalTimeDifferenceByCN() {
        Date form = DateUtil.parse(DateFormat.yyyy_MM_dd_HH_mm_ss, "2021-11-01 00:00:00");
        Date to = DateUtil.parse(DateFormat.yyyy_MM_dd_HH_mm_ss, "2021-11-03 10:20:30");

        // 正常的
        System.out.println(DateUtil.intervalTimeDifferenceByCN(form, to));
        System.out.println(DateUtil.intervalTimeDifferenceByCN(form, to));
        // 搞反的
        System.out.println(DateUtil.intervalTimeDifferenceByCN(to, form));
        System.out.println(DateUtil.intervalTimeDifferenceByCN(to, form));

        // 小时 分钟 秒 from大于to
        form = DateUtil.parse(DateFormat.yyyy_MM_dd_HH_mm_ss, "2021-11-01 10:20:30");
        to = DateUtil.parse(DateFormat.yyyy_MM_dd_HH_mm_ss, "2021-11-03 05:10:15");

        // 正常的
        System.out.println(DateUtil.intervalTimeDifferenceByCN(form, to));
        System.out.println(DateUtil.intervalTimeDifferenceByCN(form, to));
        // 搞反的
        System.out.println(DateUtil.intervalTimeDifferenceByCN(to, form));
        System.out.println(DateUtil.intervalTimeDifferenceByCN(to, form));
    }

    @Test
    void intervalTimeDifferenceByEN() {
        Date form = DateUtil.parse(DateFormat.yyyy_MM_dd_HH_mm_ss, "2021-11-01 00:00:00");
        Date to = DateUtil.parse(DateFormat.yyyy_MM_dd_HH_mm_ss, "2021-11-03 10:20:30");

        // 正常的
        System.out.println(DateUtil.intervalTimeDifferenceByEN(form, to));
        System.out.println(DateUtil.intervalTimeDifferenceByEN(form, to));
        // 搞反的
        System.out.println(DateUtil.intervalTimeDifferenceByEN(to, form));
        System.out.println(DateUtil.intervalTimeDifferenceByEN(to, form));

        // 小时 分钟 秒 from大于to
        form = DateUtil.parse(DateFormat.yyyy_MM_dd_HH_mm_ss, "2021-11-01 10:20:30");
        to = DateUtil.parse(DateFormat.yyyy_MM_dd_HH_mm_ss, "2021-11-03 05:10:15");

        // 正常的
        System.out.println(DateUtil.intervalTimeDifferenceByEN(form, to));
        System.out.println(DateUtil.intervalTimeDifferenceByEN(form, to));
        // 搞反的
        System.out.println(DateUtil.intervalTimeDifferenceByEN(to, form));
        System.out.println(DateUtil.intervalTimeDifferenceByEN(to, form));
    }

    @Test
    void isNextNaturalMonthByNow() {
        System.out.println(DateUtil.isNextNaturalMonthByNow(DateUtil.now()));
    }

    @Test
    void isXNaturalMonthLaterByNow() {
        LocalDateTime of = LocalDateTime.of(2022, 1, 1, 23, 20, 10);
        System.out.println(DateUtil.isXNaturalMonthLaterByNow(DateUtil.toDate(of), 2));
    }

    @Test
    void difference() throws InterruptedException {
        LocalDateTime startDateTime = LocalDateTime.of(2021, 1, 1, 1, 1, 1, 1);
        LocalDateTime endDateTime = LocalDateTime.of(2022, 2, 2, 2, 2, 2, 2);
        Date fromDate = DateUtil.toDate(startDateTime);
        Thread.sleep(1000);
        Date toDate = DateUtil.toDate(endDateTime);
        DateUtil.DateInfo difference = DateUtil.difference(fromDate, toDate);
        System.out.println(difference);
    }

    @Test
    void difference2() throws InterruptedException {
        LocalDateTime startDateTime = LocalDateTime.of(2021, 1, 1, 1, 1, 1, 1);
        Thread.sleep(1000);
        LocalDateTime endDateTime = LocalDateTime.of(2022, 2, 2, 2, 2, 2, 2);
        Thread.sleep(1000);
        DateUtil.DateInfo difference = DateUtil.difference(startDateTime, endDateTime);
        System.out.println(difference);
        System.out.println(difference.toStringCN());
    }

    @Test
    void zoneDateTime() {
        ZonedDateTime now = ZonedDateTime.now();
        System.out.println(now);
        ZoneId zone = ZoneId.of("Asia/Shanghai");
        System.out.println(zone);
        ZonedDateTime.now(zone);

        System.out.println(ZoneId.SHORT_IDS);
    }

}