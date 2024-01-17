package io.github.kuugasky.kuugatool.core.date;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.comparator.CompareUtil;
import io.github.kuugasky.kuugatool.core.constants.KuugaConstants;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Date操作工具类
 *
 * @author kuuga
 */
@Slf4j
public final class DateUtil {

    private static final String DEFAULT_OFFSET_ID = "+8";

    /**
     * 定义私有构造函数来屏蔽这个隐式公有构造函数
     */
    private DateUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 一天秒数
     */
    private static final int ONE_DAY_SECONDS = 60 * 60 * 24;
    /**
     * 一天毫秒数
     */
    private static final int ONE_DAT_TIMESTAMP = 1000 * 3600 * 24;
    /**
     * 系统默认区域ID
     */
    private static final ZoneId ZONE_ID = ZoneId.systemDefault();

    private static final String INTERVAL_TIME_SHORTHAND = "%dd%dh%dm%ds";
    private static final String INTERVAL_TIME_STR = "%d天%d时%d分%d秒";

    /**
     * 是否有效解析格式
     *
     * @param pattern pattern
     * @return boolean
     */
    private static boolean isValidParseFormat(final DateFormat pattern) {
        return DateFormat.VALID_PARSE_FORMATS.contains(pattern);
    }

    // 创建日期===========================================================================================================

    /**
     * 日期创建
     *
     * @param year  年
     * @param month 月
     * @param day   日
     * @return date
     */
    public static Date of(final int year, final int month, final int day) {
        LocalDate localDate = LocalDate.of(year, month, day);
        return toDate(localDate);
    }

    /**
     * 日期创建
     *
     * @param year  年
     * @param month 月
     * @param day   日
     * @return date
     */
    public static Date of(final int year, final Month month, final int day) {
        return toDate(LocalDate.of(year, month, day));
    }

    /**
     * 日期创建
     *
     * @param year   年
     * @param month  月
     * @param day    日
     * @param hour   时
     * @param minute 分
     * @param second 秒
     * @return date
     */
    public static Date of(final int year, final int month, final int day, final int hour, final int minute, final int second) {
        LocalDateTime localDateTime = LocalDateTime.of(year, month, day, hour, minute, second);
        return toDate(localDateTime);
    }

    /**
     * 日期创建
     *
     * @param hour   时
     * @param minute 分
     * @param second 秒
     * @return date
     */
    public static Date ofTime(final int hour, final int minute, final int second) {
        LocalDate now = LocalDate.now();
        LocalDateTime localDateTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), hour, minute, second);
        return toDate(localDateTime);
    }

    /**
     * 日期创建
     *
     * @param hour         时
     * @param minute       分
     * @param second       秒
     * @param nanoOfSecond 毫秒
     * @return date
     */
    public static Date ofTime(final int hour, final int minute, final int second, final int nanoOfSecond) {
        LocalDate now = LocalDate.now();
        LocalDateTime localDateTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), hour, minute, second, nanoOfSecond);
        return toDate(localDateTime);
    }

    // 获取年月日时分秒===========================================================================================================

    /**
     * 获取当前年份
     *
     * @return 当前年份
     */
    public static int thisYear() {
        return LocalDate.now().getYear();
    }

    /**
     * 获取指定日期的年份
     *
     * @param date 指定的日期
     * @return 年份
     */
    public static int getYear(Date date) {
        return toLocalDate(date).getYear();
    }

    /**
     * 获取当前月份
     *
     * @return 当前月份
     */
    public static int thisMonth() {
        return LocalDate.now().getMonthValue();
    }

    /**
     * 获取给定日期的月份
     *
     * @param date 传入的日期
     * @return 传入日期的月份值
     */
    public static int getMonth(Date date) {
        return toLocalDate(date).getMonthValue();
    }

    /**
     * 获取当前日期的天数。
     *
     * @return 当前日期的天数
     */
    public static int thisDay() {
        return LocalDate.now().getDayOfMonth();
    }

    /**
     * 获取给定日期是该月的第几天
     *
     * @param date 日期对象
     * @return 该月的第几天，从1开始
     */
    public static int getDay(Date date) {
        return toLocalDate(date).getDayOfMonth();
    }

    /**
     * 获取当前小时数
     *
     * @return 当前小时数
     */
    public static int thisHour() {
        return LocalTime.now().getHour();
    }

    /**
     * 获取指定日期的小时数
     *
     * @param date 日期对象
     * @return 指定日期的小时数
     */
    public static int getHour(Date date) {
        return toLocalTime(date).getHour();
    }

    /**
     * 获取当前分钟数
     *
     * @return 当前分钟数
     */
    public static int thisMinute() {
        return LocalTime.now().getMinute();
    }

    /**
     * 获取给定日期的分钟数
     *
     * @param date 一个给定的日期
     * @return 给定日期的分钟数
     */
    public static int getMinute(Date date) {
        return toLocalTime(date).getMinute();
    }

    /**
     * 获取当前秒数
     *
     * @return 当前秒数
     */
    public static int thisSecond() {
        return LocalTime.now().getSecond();
    }

    /**
     * 获取指定日期的秒数
     *
     * @param date 日期对象
     * @return 秒数
     */
    public static int getSecond(Date date) {
        return toLocalTime(date).getSecond();
    }

    // 当天日期相关===========================================================================================================

    /**
     * 获取当前时间
     *
     * @return 返回当前时间的Date对象
     */
    public static Date now() {
        return new Date();
    }

    /**
     * 返回当前日期的字符串表示
     *
     * @return 当前日期的字符串表示
     */
    public static String today() {
        return LocalDate.now().toString();
    }

    /**
     * 返回当前日期的字符串表示，格式为yyyy-MM-dd。<br>
     * 如果传入的日期格式与yyyy-MM-dd相同，则调用today()方法返回当天的日期字符串；<br>
     * 否则，调用format方法将当前日期转换为指定的日期格式。
     *
     * @param dateFormat 日期格式
     * @return 当前日期的字符串表示
     */
    public static String today(final DateFormat dateFormat) {
        if (dateFormat == DateFormat.yyyy_MM_dd) {
            return today();
        }
        return format(dateFormat, new Date());
    }

    /**
     * 获取当前时间的字符串表示，格式为"yyyy-MM-dd HH:mm:ss"。
     *
     * @return 当前时间的字符串表示。
     */
    public static String todayTime() {
        return today(DateFormat.yyyy_MM_dd_HH_mm_ss);
    }

    // 时间格式校验===========================================================================================================

    /**
     * 检查月份字符串的格式是否符合指定的格式
     *
     * @param monthStr 月份字符串
     * @return 如果月份字符串格式符合指定格式，则返回true；否则返回false
     */
    public static boolean checkMonthFormat(final String monthStr) {
        // 定义月份格式的正则表达式
        final String monthFormat = "\\d{4}-\\d{2}";
        // 编译正则表达式
        Pattern pattern = Pattern.compile(monthFormat);
        // 创建匹配器
        Matcher match = pattern.matcher(monthStr);
        // 判断匹配结果是否匹配成功
        return match.matches();
    }

    /**
     * 检查日期格式是否符合指定格式
     *
     * @param dateStr 日期字符串
     * @return 如果日期格式符合指定格式，则返回true；否则返回false
     */
    public static boolean checkDateFormat(final String dateStr) {
        final String dateFormat = "\\d{4}-\\d{2}-\\d{2}";
        Pattern pattern = Pattern.compile(dateFormat);
        Matcher match = pattern.matcher(dateStr);
        return match.matches();
    }

    // JDK8日期和Date互转===========================================================================================================

    /**
     * 将给定的LocalDate对象转换为Date对象
     *
     * @param localDate 一个LocalDate对象
     * @return 转换后的Date对象
     */
    public static Date toDate(final LocalDate localDate) {
        ZonedDateTime zdt = localDate.atStartOfDay(ZONE_ID);
        return Date.from(zdt.toInstant());
    }

    /**
     * 将给定的LocalTime对象转换为Date对象
     *
     * @param localTime 一个LocalTime对象
     * @return 转换后的Date对象
     */
    public static Date toDate(final LocalTime localTime) {
        LocalDate localDate = LocalDate.now(); // 获取当前日期
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime); // 使用给定的时间生成当前日期和时间的对象
        Instant instant = localDateTime.atZone(ZONE_ID).toInstant(); // 将日期时间转换为指定时区的Instant对象
        return Date.from(instant); // 将Instant对象转换为Date对象
    }

    /**
     * 将给定的LocalDateTime对象转换为Date对象
     *
     * @param localDateTime 一个LocalDateTime对象
     * @return 转换后的Date对象
     */
    public static Date toDate(final LocalDateTime localDateTime) {
        ZonedDateTime zdt = localDateTime.atZone(ZONE_ID);
        return Date.from(zdt.toInstant());
    }

    /**
     * 将 Date 对象转换为 LocalDate 对象
     *
     * @param date 需要转换的 Date 对象
     * @return 转换后的 LocalDate 对象
     */
    public static LocalDate toLocalDate(final Date date) {
        Instant instant = date.toInstant();
        return instant.atZone(ZONE_ID).toLocalDate();
    }

    /**
     * 将 Date 对象转换为 LocalTime 类型的对象
     *
     * @param date 待转换的 Date 对象
     * @return 转换后的 LocalTime 类型的对象
     */
    public static LocalTime toLocalTime(final Date date) {
        return toLocalDateTime(date).toLocalTime();
    }

    /**
     * 将 Java 的 Date 对象转换为 LocalDateTime 对象
     *
     * @param date 需要转换的 Date 对象
     * @return 转换后的 LocalDateTime 对象
     */
    public static LocalDateTime toLocalDateTime(final Date date) {
        Instant instant = date.toInstant();
        return instant.atZone(ZONE_ID).toLocalDateTime();
    }

    /**
     * 将给定的日期转换为年份和月份。
     *
     * @param date 日期对象
     * @return 年份和月份的组合对象
     */
    public static YearMonth toYearMonth(final Date date) {
        LocalDate localDate = toLocalDate(date);
        return YearMonth.of(localDate.getYear(), localDate.getMonthValue());
    }

    /**
     * 将给定的日期转换为月日组合对象。
     *
     * @param date 日期对象
     * @return 月日组合对象
     */
    public static MonthDay toMonthDay(final Date date) {
        LocalDate localDate = toLocalDate(date);
        return MonthDay.of(localDate.getMonthValue(), localDate.getDayOfMonth());
    }

    // 增减年月日周复合功能函数===========================================================================================================

    /**
     * 对指定的日期进行加减秒操作
     *
     * @param date         待操作的日期
     * @param secondsCount 秒数的变化量，正数表示加秒，负数表示减秒
     * @return 操作后的日期
     */
    public static Date moreOrLessSeconds(final Date date, final int secondsCount) {
        if (secondsCount == 0) {
            return date;
        }
        if (secondsCount > 0) {
            return plusSeconds(date, secondsCount);
        }
        return minusSeconds(date, Math.abs(secondsCount));
    }

    /**
     * 对指定的日期进行加减分钟操作
     *
     * @param date         待操作的日期
     * @param minutesCount 分钟的变化量，正数表示加分钟，负数表示减分钟
     * @return 操作后的日期
     */
    public static Date moreOrLessMinutes(final Date date, final int minutesCount) {
        if (minutesCount == 0) {
            return date;
        }
        if (minutesCount > 0) {
            return plusMinutes(date, minutesCount);
        }
        return minusMinutes(date, Math.abs(minutesCount));
    }

    /**
     * 对指定的日期进行加减小时操作
     *
     * @param date       待操作的日期
     * @param hoursCount 小时的变化量，正数表示加小时，负数表示减小时
     * @return 操作后的日期
     */
    public static Date moreOrLessHours(final Date date, final int hoursCount) {
        if (hoursCount == 0) {
            return date;
        }
        if (hoursCount > 0) {
            return plusHours(date, hoursCount);
        }
        return minusHours(date, Math.abs(hoursCount));
    }

    /**
     * 对指定的日期进行加减天数操作
     *
     * @param date      待操作的日期
     * @param daysCount 天数的变化量，正数表示加天数，负数表示减天数
     * @return 操作后的日期
     */
    public static Date moreOrLessDays(final Date date, final int daysCount) {
        if (daysCount == 0) {
            return date;
        }
        if (daysCount > 0) {
            return plusDays(date, daysCount);
        }
        return minusDays(date, Math.abs(daysCount));
    }

    /**
     * 对指定的日期进行加减月数操作
     *
     * @param date        待操作的日期
     * @param monthsCount 月数的变化量，正数表示加月数，负数表示减月数
     * @return 操作后的日期
     */
    public static Date moreOrLessMonths(final Date date, final int monthsCount) {
        if (monthsCount == 0) {
            return date;
        }
        if (monthsCount > 0) {
            return plusMonths(date, monthsCount);
        }
        return minusMonths(date, Math.abs(monthsCount));
    }

    /**
     * 对指定的日期进行加减年数操作
     *
     * @param date       待操作的日期
     * @param yearsCount 年数的变化量，正数表示加年数，负数表示减年数
     * @return 操作后的日期
     */
    public static Date moreOrLessYears(final Date date, final int yearsCount) {
        if (yearsCount == 0) {
            return date;
        }
        if (yearsCount > 0) {
            return plusYears(date, yearsCount);
        }
        return minusYears(date, Math.abs(yearsCount));
    }

    /**
     * 对指定的日期进行加减周数操作
     *
     * @param date       待操作的日期
     * @param weeksCount 周数的变化量，正数表示加周数，负数表示减周数
     * @return 操作后的日期
     */
    public static Date moreOrLessWeeks(final Date date, final int weeksCount) {
        if (weeksCount == 0) {
            return date;
        }
        if (weeksCount > 0) {
            return plusWeeks(date, weeksCount);
        }
        return minusWeeks(date, Math.abs(weeksCount));
    }

    /**
     * 对指定的日期按照给定的时间单位进行加减操作
     *
     * @param date  要进行操作的日期
     * @param count 加减的次数
     * @param unit  时间单位
     * @return 操作后的日期
     */
    public static Date moreOrLess(final Date date, final int count, final ChronoUnit unit) {
        if (count == 0) {
            return date;
        }
        LocalDateTime localDateTime = toLocalDateTime(date);
        if (count > 0) {
            return toDate(localDateTime.plus(count, unit));
        }
        return toDate(localDateTime.minus(Math.abs(count), unit));
    }

    // 增减年月日周单功能函数===========================================================================================================

    /**
     * 向给定的日期添加指定的秒数。
     *
     * @param date         要添加秒数的日期
     * @param secondsCount 要添加的秒数
     * @return 添加秒数后的日期
     */
    private static Date plusSeconds(Date date, int secondsCount) {
        return toDate(toLocalDateTime(date).plusSeconds(secondsCount));
    }

    /**
     * 从给定的日期中减去指定的秒数。
     *
     * @param date         要减去秒数的日期
     * @param secondsCount 要减去的秒数
     * @return 减去秒数后的日期
     */
    private static Date minusSeconds(Date date, int secondsCount) {
        return toDate(toLocalDateTime(date).minusSeconds(secondsCount));
    }

    /**
     * 从给定的日期中加上指定的分钟数。
     *
     * @param date         要添加分钟数的日期
     * @param minutesCount 要添加的分钟数
     * @return 添加分钟数后的日期
     */
    private static Date plusMinutes(final Date date, final int minutesCount) {
        return toDate(toLocalDateTime(date).plusMinutes(minutesCount));
    }

    private static Date minusMinutes(final Date date, final int hourCount) {
        return toDate(toLocalDateTime(date).minusMinutes(hourCount));
    }

    private static Date plusHours(final Date date, final int hourCount) {
        return toDate(toLocalDateTime(date).plusHours(hourCount));
    }

    private static Date minusHours(final Date date, int hourCount) {
        return toDate(toLocalDateTime(date).minusHours(hourCount));
    }

    private static Date plusDays(final Date date, final int dayCount) {
        return toDate(toLocalDateTime(date).plusDays(dayCount));
    }

    private static Date minusDays(final Date date, final int dayCount) {
        return toDate(toLocalDateTime(date).minusDays(dayCount));
    }

    private static Date plusMonths(final Date date, final int monthCount) {
        return toDate(toLocalDateTime(date).plusMonths(monthCount));
    }

    private static Date minusMonths(final Date date, final int monthCount) {
        return toDate(toLocalDateTime(date).minusMonths(monthCount));
    }

    private static Date plusYears(final Date date, final int yearCount) {
        return toDate(toLocalDateTime(date).plusYears(yearCount));
    }

    private static Date minusYears(final Date date, final int yearCount) {
        return toDate(toLocalDateTime(date).minusYears(yearCount));
    }

    private static Date plusWeeks(final Date date, final int weekCount) {
        return toDate(toLocalDateTime(date).plusWeeks(weekCount));
    }

    private static Date minusWeeks(final Date date, final int weekCount) {
        return toDate(toLocalDateTime(date).minusWeeks(weekCount));
    }

    // 获取某年第N天的LocalDate===========================================================================================================

    public static LocalDate ofYearDay(final int year, final int dayCount) {
        return LocalDate.ofYearDay(year, dayCount);
    }

    // 秒和纳秒转LocalTime===========================================================================================================

    /**
     * 秒转LocalTime
     *
     * @param secondOfDay secondOfDay
     * @return localTime
     */
    public static LocalTime ofSecondOfDay(final long secondOfDay) {
        return LocalTime.ofSecondOfDay(secondOfDay);
    }

    /**
     * 纳秒转LocalTime
     *
     * @param nanoOfDay nanoOfDay
     * @return localTime
     */
    public static LocalTime ofNanoOfDay(final long nanoOfDay) {
        return LocalTime.ofNanoOfDay(nanoOfDay);
    }

    // 第一天===========================================================================================================

    public static Date firstDayOfMonth(final Date date) {
        LocalDate localDate = toLocalDate(date).with(TemporalAdjusters.firstDayOfMonth());
        return toDate(localDate);
    }

    /**
     * 返回指定日期所在月份下一个月的第一个日期。
     *
     * @param date 日期
     * @return 下一个月的第一个日期
     */
    public static Date firstDayOfNextMonth(final Date date) {
        LocalDate localDate = toLocalDate(date).with(TemporalAdjusters.firstDayOfNextMonth());
        return toDate(localDate);
    }

    /**
     * 返回指定日期所在年份的第一天日期。
     *
     * @param date 日期
     * @return 年份的第一天日期
     */
    public static Date firstDayOfYear(final Date date) {
        LocalDate localDate = toLocalDate(date).with(TemporalAdjusters.firstDayOfYear());
        return toDate(localDate);
    }

    /**
     * 返回指定日期所在年份的下一年的第一天日期。
     *
     * @param date 日期
     * @return 下一年的第一天日期
     */
    public static Date firstDayOfNextYear(final Date date) {
        LocalDate localDate = toLocalDate(date).with(TemporalAdjusters.firstDayOfNextYear());
        return toDate(localDate);
    }

    /**
     * 返回指定日期所在周的周一。
     *
     * @param date 指定的日期
     * @return 返回指定日期所在周的周一的日期
     */
    public static Date firstDayOfWeek(final Date date) {
        // 如果指定日期就是周一，则直接返回该日期
        LocalDate saturdayWeekDate = toLocalDate(date).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        return toDate(saturdayWeekDate);
    }

    /**
     * 返回指定日期所在下一个星期的第一天。
     *
     * @param date 一个Date对象，表示需要计算的日期
     * @return 返回指定日期所在下一个星期的第一天的Date对象
     */
    public static Date firstDayOfNextWeek(final Date date) {
        LocalDate saturdayWeekDate = toLocalDate(date).with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        return toDate(saturdayWeekDate);
    }


    // 最后一天===========================================================================================================

    /**
     * 返回指定日期所在月份的最后一天。
     *
     * @param date 一个Date对象，表示需要计算的日期
     * @return 返回指定日期所在月份的最后一天的Date对象
     */
    public static Date lastDayOfMonth(final Date date) {
        LocalDate localDate = toLocalDate(date).with(TemporalAdjusters.lastDayOfMonth());
        return toDate(localDate);
    }


    /**
     * 求下个月的最后一天
     *
     * @param date 日期
     * @return 下个月的最后一天
     */
    public static Date lastDayOfNextMonth(final Date date) {
        Date nextMonthDate = plusMonths(date, 1);
        return lastDayOfMonth(nextMonthDate);
    }


    /**
     * 返回给定日期所在年的最后一天。
     *
     * @param date 给定的日期
     * @return 给定日期所在年的最后一天
     */
    public static Date lastDayOfYear(final Date date) {
        LocalDate localDate = toLocalDate(date).with(TemporalAdjusters.lastDayOfYear());
        return toDate(localDate);
    }


    /**
     * 返回下一年的最后一天。
     *
     * @param date 一个Date对象，表示当前日期
     * @return 返回下一年的最后一天的Date对象
     */
    public static Date lastDayOfNextYear(final Date date) {
        Date nextMonthDate = plusYears(date, 1);
        return lastDayOfYear(nextMonthDate);
    }

    /**
     * 返回给定日期所在周的最后一天。
     *
     * @param date 一个Date对象，表示要计算的日期
     * @return 返回给定日期所在周的最后一天的Date对象
     */
    public static Date lastDayOfWeek(final Date date) {
        LocalDate sundayWeekDate = toLocalDate(date).with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        return toDate(sundayWeekDate);
    }

    /**
     * 返回下周的最后一天
     *
     * @param date 一个日期对象
     * @return 返回下周的最后一天
     */
    public static Date lastDayOfNextWeek(final Date date) {
        Date nextWeekDate = plusWeeks(date, 1);
        return lastDayOfWeek(nextWeekDate);
    }


    // date format ===========================================================================================================

    /**
     * 时间格式化为{@link  DateFormat#yyyy_MM_dd_HH_mm_ss}
     *
     * @param date date
     * @return dateStr
     */
    public static String format(final Date date) {
        return format(DateFormat.yyyy_MM_dd_HH_mm_ss, date);
    }

    /**
     * 时间格式化为{@link  DateFormat}
     *
     * @param dateFormat dateFormat
     * @param date       date
     * @return dateStr
     */
    public static String format(final DateFormat dateFormat, final Date date) {
        if (null == date) {
            return StringUtil.EMPTY;
        }
        LocalDateTime localDateTime = toLocalDateTime(date);
        return localDateTime.format(DateTimeFormatter.ofPattern(dateFormat.format()));
        // return DateFormatUtils.format(date, dateFormat.format());
    }

    /**
     * 高级时间格式化
     * 通过 {@link DateTimeFormatter} 将date格式化为pattern指定格式
     * <p>
     *
     * @param pattern pattern
     * @param date    date
     * @return dateStr
     */
    public static String formatSenior(final String pattern, final Date date) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return toLocalDateTime(date).format(dateTimeFormatter);
    }

    /**
     * 时间格式化为{@link  DateFormat#yyyy_MM_dd}
     *
     * @param date date
     * @return dateStr
     */
    public static String formatDate(final Date date) {
        return format(DateFormat.yyyy_MM_dd, date);
        // return DateFormatUtils.format(date, DateFormat.yyyy_MM_dd.format());
    }

    /**
     * 时间格式化为{@link  DateFormat#HH_mm_ss}
     *
     * @param date date
     * @return dateStr
     */
    public static String formatTime(final Date date) {
        return format(DateFormat.HH_mm_ss, date);
        // return DateFormatUtils.format(date, DateFormat.HH_mm_ss.format());
    }

    /**
     * 时间格式化为{@link  DateFormat#yyyy_MM_dd_HH_mm_ss}
     *
     * @param date date
     * @return dateStr
     */
    public static String formatDateTime(final Date date) {
        return format(DateFormat.yyyy_MM_dd_HH_mm_ss, date);
        // return DateFormatUtils.format(date, DateFormat.yyyy_MM_dd_HH_mm_ss.format());
    }

    // date parse ===========================================================================================================

    /**
     * 日期时间文本解析
     *
     * @param dateFormat  日期时间格式
     * @param dateTimeStr 日期时间文本
     * @return 日期时间
     */
    public static Date parse(final DateFormat dateFormat, final String dateTimeStr) {
        if (!isValidParseFormat(dateFormat)) {
            return null;
        }

        if (DateFormat.SPECIAL_FORMATS.contains(dateFormat)) {
            // 此处不能使用DateTimeFormatter，因为DateTimeFormatter不支持这几类特殊格式
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat.format());
            try {
                return simpleDateFormat.parse(dateTimeStr);
            } catch (ParseException e) {
                log.error("日期时间解析失败，日期时间格式：{}，日期时间文本：{}", dateFormat, dateTimeStr);
                return null;
            }
        }

        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat.format());
        if (DateFormat.DATE_FORMATS.contains(dateFormat)) {
            return toDate(LocalDate.parse(dateTimeStr, formatter));
        }
        if (DateFormat.TIME_FORMATS.contains(dateFormat)) {
            return toDate(LocalTime.parse(dateTimeStr, formatter));
        }
        if (DateFormat.DATE_TIME_FORMATS.contains(dateFormat)) {
            return toDate(LocalDateTime.parse(dateTimeStr, formatter));
        }
        return null;
    }

    /**
     * 日期时间文本高级解析（随意格式）
     *
     * @param pattern     格式
     * @param dateTimeStr 日期时间文本
     * @return 日期四件
     */
    public static Date parseSenior(final String pattern, final String dateTimeStr) {
        if (StringUtil.isEmpty(pattern)) {
            return null;
        }
        if (pattern.contains(KuugaConstants.WELL)) {
            throw new IllegalArgumentException("Pattern includes reserved character: '#'");
        }
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return toDate(LocalDateTime.parse(dateTimeStr, formatter));
    }

    /**
     * 解析文本格式为{@link LocalDate}的日期时间文本，转换为{@link Date}
     *
     * @param dateTimeStr dateTimeStr
     * @return date
     */
    public static Date parseLocalDate(final String dateTimeStr) {
        LocalDate localDate = LocalDate.parse(dateTimeStr);
        return toDate(localDate);
    }

    /**
     * 解析文本格式为{@link LocalDateTime}的日期时间文本，转换为{@link Date}
     *
     * @param dateTimeStr dateTimeStr
     * @return date
     */
    public static Date parseLocalDateTime(final String dateTimeStr) {
        LocalDateTime localDateTime = LocalDateTime.parse(dateTimeStr);
        return toDate(localDateTime);
    }

    /**
     * 解析文本格式为{@link LocalTime}的日期时间文本，转换为{@link Date}
     *
     * @param dateTimeStr dateTimeStr
     * @return date
     */
    public static Date parseLocalTime(final String dateTimeStr) {
        LocalTime localTime = LocalTime.parse(dateTimeStr);
        return toDate(localTime);
    }

    /**
     * 解析时间戳为{@link Date}
     * <p>
     * 11
     *
     * @param timestamp timestamp
     * @return date
     */
    public static Date parseTimestamp(final long timestamp) {
        if (timestamp <= 0) {
            return null;
        }
        String timestampStr = String.valueOf(timestamp);
        int timestampStrLength = timestampStr.length();
        if (timestampStrLength == KuugaConstants.TEN) {
            return parseTimestamp(timestamp, TimestampType.SECOND);
        } else if (timestampStrLength == KuugaConstants.THIRTEEN) {
            return parseTimestamp(timestamp, TimestampType.MILLISECOND);
        }
        return null;
    }

    /**
     * 解析秒级时间戳为{@link Date}
     *
     * @param timestamp timestamp
     * @return date
     */
    public static Date parseTimeSeconds(final long timestamp) {
        return parseTimestamp(timestamp, TimestampType.SECOND);
    }

    /**
     * 解析毫秒级时间戳为{@link Date}
     *
     * @param timestamp timestamp
     * @return date
     */
    public static Date parseTimeMillis(final long timestamp) {
        return parseTimestamp(timestamp, TimestampType.MILLISECOND);
    }

    public static Date parseTimestamp(final long timestamp, final TimestampType timestampType) {
        Instant instant;
        if (timestampType == TimestampType.SECOND) {
            instant = Instant.ofEpochSecond(timestamp);
        } else {
            instant = Instant.ofEpochMilli(timestamp);
        }
        ZonedDateTime zonedDateTime = instant.atZone(ZoneOffset.of(DEFAULT_OFFSET_ID));
        return toDate(zonedDateTime.toLocalDateTime());
    }

    // 当天剩余秒数、毫秒数===========================================================================================================

    /**
     * 当天剩余秒数
     *
     * @return long
     */
    public static long secondsLeftToday() {
        // return ONE_DAY_SECONDS - DateUtils.getFragmentInSeconds(Calendar.getInstance(), Calendar.DATE);
        return ONE_DAY_SECONDS - secondDifference(zeroOfDay(now()), now());
    }

    /**
     * 当天剩余毫秒数
     *
     * @return long
     */
    public static long milliSecondsLeftToday() {
        // return ONE_DAY_SECONDS * 1000 - DateUtils.getFragmentInMilliseconds(Calendar.getInstance(), Calendar.DATE);
        Date now = now();
        return millisDifference(now, zeroOfDay(moreOrLessDays(now, 1)));
    }

    /**
     * 当天剩余秒数
     *
     * @return long
     */
    public static long secondsLeftTodayForJDK() {
        LocalDateTime midnight = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        return ChronoUnit.SECONDS.between(LocalDateTime.now(), midnight);
    }

    /**
     * 当天剩余毫秒数
     *
     * @return long
     */
    public static long milliSecondsLeftTodayForJDK() {
        LocalDateTime midnight = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        return ChronoUnit.MILLIS.between(LocalDateTime.now(), midnight);
    }

    // 差值===========================================================================================================

    @Data
    public static class DateInfo {
        private int years; // 年
        private int months; // 月
        private int days; // 日
        private int hours; // 时
        private long minutes; // 分
        private int seconds; // 秒
        // private int millis; // 毫秒
        private int nanos; // 纳秒

        @Override
        public String toString() {
            String toString = "%dY%dM%dD %dH%dm%ds.%dns";
            return String.format(toString, years, months, days, hours, minutes, seconds, nanos);
        }

        public String toStringCN() {
            String toString = "%d年%d月%d日 %d时%d分%d秒.%d纳秒";
            return String.format(toString, years, months, days, hours, minutes, seconds, nanos);
        }

    }

    /**
     * 计算两个日期的差值，包含年月日时分秒纳秒(丢失纳秒)
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 各维度差值
     */
    public static DateInfo difference(final Date startDate, final Date endDate) {
        LocalDateTime startDateTime = toLocalDateTime(startDate);
        LocalDateTime endDateTime = toLocalDateTime(endDate);
        return difference(startDateTime, endDateTime);
    }

    /**
     * 计算两个日期的差值，包含年月日时分秒纳秒
     *
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     * @return 各维度差值
     */
    public static DateInfo difference(final LocalDateTime startDateTime, final LocalDateTime endDateTime) {
        LocalDate startDate = LocalDate.of(startDateTime.getYear(), startDateTime.getMonthValue(), startDateTime.getDayOfMonth());
        LocalDate endDate = LocalDate.of(endDateTime.getYear(), endDateTime.getMonthValue(), endDateTime.getDayOfMonth());
        Period period = DatePeriodUtil.between(startDate, endDate);
        Duration duration = DateDurationUtil.between(startDateTime, endDateTime);
        DateInfo dateInfo = new DateInfo();
        dateInfo.setYears(period.getYears());
        dateInfo.setMonths(period.getMonths());
        dateInfo.setDays(period.getDays());
        dateInfo.setHours(duration.toHoursPart());
        dateInfo.setMinutes(duration.toMinutesPart());
        dateInfo.setSeconds(duration.toSecondsPart());
        dateInfo.setNanos(duration.toNanosPart());

        return dateInfo;
    }

    /**
     * 计算两个日期年份的差值
     *
     * @param fromDate 起始日期
     * @param toDate   截止日期
     * @return 年份差值
     */

    public static long yearDifference(final Date fromDate, final Date toDate) {
        LocalDateTime localDateTimeFrom = toLocalDateTime(fromDate);
        LocalDateTime localDateTimeTo = toLocalDateTime(toDate);
        return localDateTimeFrom.until(localDateTimeTo, ChronoUnit.YEARS);
    }

    /**
     * 计算两个日期月份的差值
     *
     * @param fromDate 起始日期
     * @param toDate   截止日期
     * @return 月份差值
     */
    public static long monthDifference(final Date fromDate, final Date toDate) {
        LocalDateTime localDateTimeFrom = toLocalDateTime(fromDate);
        LocalDateTime localDateTimeTo = toLocalDateTime(toDate);
        return localDateTimeFrom.until(localDateTimeTo, ChronoUnit.MONTHS);
    }

    /**
     * 计算两个日期的天数差值
     * 移除时分秒，2021-03-14 23:59:59和2021-03-15 00:00:00都算超过一天
     *
     * @param fromDate 起始日期
     * @param toDate   截止日期
     * @return 相差天数
     */
    public static long dayDifferenceZeroTime(Date fromDate, Date toDate) {
        LocalDate fromDateOfLocalDate = DateUtil.toLocalDate(fromDate);
        LocalDate toDateOfLocalDate = DateUtil.toLocalDate(toDate);
        return dayDifference(toDate(fromDateOfLocalDate), toDate(toDateOfLocalDate));
    }

    /**
     * 计算两个日期的天数差值（必须完整24小时才算一天）
     *
     * @param fromDate 起始日期
     * @param toDate   截止日期
     * @return 相差天数
     */
    public static long dayDifference(final Date fromDate, final Date toDate) {
        LocalDateTime localDateTimeFrom = toLocalDateTime(fromDate);
        LocalDateTime localDateTimeTo = toLocalDateTime(toDate);
        return localDateTimeFrom.until(localDateTimeTo, ChronoUnit.DAYS);
        // return toLocalDate(toDate).toEpochDay() - toLocalDate(fromDate).toEpochDay();
    }

    /**
     * 计算两个日期的小时差值
     *
     * @param fromDate 起始日期
     * @param toDate   截止日期
     * @return 相差天数
     */
    public static long hourDifference(final Date fromDate, final Date toDate) {
        LocalDateTime localDateTimeFrom = toLocalDateTime(fromDate);
        LocalDateTime localDateTimeTo = toLocalDateTime(toDate);
        return localDateTimeFrom.until(localDateTimeTo, ChronoUnit.HOURS);
        // return toLocalDate(toDate).toEpochDay() - toLocalDate(fromDate).toEpochDay();
    }

    /**
     * 判断两个时间相差多少分钟
     *
     * @param fromDate fromDate
     * @param toDate   toDate
     * @return long
     */
    public static long minuteDifference(final Date fromDate, final Date toDate) {
        LocalDateTime localDateTimeFrom = toLocalDateTime(fromDate);
        LocalDateTime localDateTimeTo = toLocalDateTime(toDate);
        return localDateTimeFrom.until(localDateTimeTo, ChronoUnit.MINUTES);
    }

    /**
     * 判断两个时间相差多少分秒
     *
     * @param fromDate fromDate
     * @param toDate   toDate
     * @return long
     */
    public static long secondDifference(final Date fromDate, final Date toDate) {
        LocalDateTime localDateTimeFrom = toLocalDateTime(fromDate);
        LocalDateTime localDateTimeTo = toLocalDateTime(toDate);
        return localDateTimeFrom.until(localDateTimeTo, ChronoUnit.SECONDS);
    }

    /**
     * 判断两个时间相差多少毫秒
     *
     * @param fromDate fromDate
     * @param toDate   toDate
     * @return long
     */
    public static long millisDifference(final Date fromDate, final Date toDate) {
        LocalDateTime localDateTimeFrom = toLocalDateTime(fromDate);
        LocalDateTime localDateTimeTo = toLocalDateTime(toDate);
        return localDateTimeFrom.until(localDateTimeTo, ChronoUnit.MILLIS);
    }

    /**
     * 计算指定时间与当前时间的差
     *
     * @param date date
     * @return timeDifferenceToString
     */
    public static String timeDifferenceToString(final Date date) {
        if (date == null) {
            return null;
        }
        long time = System.currentTimeMillis() - date.getTime();
        long min = time / 1000 / 60;
        if (min < KuugaConstants.FIVE) {
            return "刚刚";
        } else if (min < 60) {
            return min + "分钟之前";
        } else if (min < 1440) {
            return min / 60 + "小时之前";
        } else if (min < 10080) {
            return min / 60 / 24 + "天之前";
        } else if (min < 40320) {
            return min / 60 / 24 / 7 + "周之前";
        } else if (min < 525600) {
            return min / 60 / 24 / 7 / 4 + "月之前";
        } else {
            return min / 60 / 24 / 365 + "年之前";
        }
    }

    // 获取周几===========================================================================================================

    /**
     * date所在年的第一个月的第一个周几
     *
     * @param date      date
     * @param dayOfWeek 周几
     * @return date
     */
    public static Date dayOfFirstMWeekForFirstMonth(final Date date, final DayOfWeek dayOfWeek) {
        LocalDate localDate = toLocalDate(date).with(TemporalAdjusters.firstInMonth(dayOfWeek));
        return toDate(localDate);
    }

    /**
     * 打印本月的所有周末
     */
    public static void printWeekendOfCurrentMonth() {
        printWeekend(new Date());
    }

    /**
     * 打印date所在月份的所有周末
     *
     * @param date 日期
     */
    public static void printWeekend(final Date date) {
        // 本月的第一天
        LocalDate firstDay = toLocalDate(firstDayOfMonth(date));
        // 本月的最后一天
        LocalDate lastDay = toLocalDate(lastDayOfMonth(date));
        // 最后一天的那天是本周的第几周x
        int weeks = lastDay.get(ChronoField.ALIGNED_WEEK_OF_MONTH);
        int i = 1;
        // 第一周的星期天
        LocalDate saturdayWeekDate = firstDay.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
        LocalDate sundayWeekDate = firstDay.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        while (i < weeks) {
            System.out.printf("本月第%s周的周末是：%s ~ %s%n", i, saturdayWeekDate, sundayWeekDate);
            saturdayWeekDate = sundayWeekDate.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
            sundayWeekDate = sundayWeekDate.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
            i++;
        }
    }

    // 日期是否包含以及区间相关===========================================================================================================

    /**
     * 开区间
     * 不包含起止时间点
     *
     * @param beginDate 开始时间
     * @param endDate   结束时间
     * @param target    目标时间
     * @return 是否包含，不含起止时间点
     */
    public static boolean between(final Date beginDate, final Date endDate, final Date target) {
        return beginDate.before(target) && endDate.after(target);
    }

    /**
     * 闭区间
     * 包含起止时间点
     *
     * @param beginDate 开始时间
     * @param endDate   结束时间
     * @param target    目标时间
     * @return 是否包含
     */
    public static boolean include(final Date beginDate, final Date endDate, final Date target) {
        boolean between = between(beginDate, endDate, target);
        return between || beginDate.equals(target) || endDate.equals(target);
    }

    /**
     * 判断两个日期是否满足 date1 在 date2 之前的关系。
     *
     * @param date1 第一个日期对象
     * @param date2 第二个日期对象
     * @return 如果 date1 在 date2 之前返回 true，否则返回 false
     */
    public static boolean isBefore(final Date date1, final Date date2) {
        return toLocalDateTime(date1).isBefore(toLocalDateTime(date2));
    }

    /**
     * 判断两个日期是否满足 date1 在 date2 之后的关系。
     *
     * @param date1 第一个日期对象
     * @param date2 第二个日期对象
     * @return 如果 date1 在 date2 之后返回 true，否则返回 false
     */
    public static boolean isAfter(final Date date1, final Date date2) {
        return toLocalDateTime(date1).isAfter(toLocalDateTime(date2));
    }

    /**
     * 获取两个日期间所有dateList，包含起止日期
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return dateList
     */
    public static List<Date> dateIntervalList(final Date startDate, final Date endDate) {
        if (ObjectUtil.containsNull(startDate, endDate)) {
            return ListUtil.emptyList();
        }
        List<Date> result = ListUtil.newArrayList();
        Date startDateTmp = startDate;
        while (startDateTmp.before(endDate)) {
            result.add(zeroOfDay(startDateTmp));
            startDateTmp = plusDays(startDateTmp, 1);
        }
        return result;
    }

    /**
     * 获取date对应周的周一和周日
     *
     * @param date 日期
     * @return 返回同一周的周一和周日
     */
    public static List<Date> weeks(final Date date) {
        Date firstDayOfWeek = firstDayOfWeek(date);
        Date lastDayOfWeek = lastDayOfWeek(date);
        return ListUtil.newArrayList(firstDayOfWeek, lastDayOfWeek);
    }

    /**
     * 获取date对应周的周一至周日
     *
     * @param date 日期
     * @return 对应周的周一至周日
     */
    public static List<Date> weeksFull(final Date date) {
        Date firstDayOfWeek = firstDayOfWeek(date);
        Date lastDayOfWeek = lastDayOfWeek(date);
        List<Date> result = ListUtil.newArrayList();
        Date temp = firstDayOfWeek;
        while (temp.before(lastDayOfWeek)) {
            result.add(temp);
            temp = plusDays(temp, 1);
        }
        result.add(lastDayOfWeek);
        return result;
    }

    /**
     * 得到今天是周几
     *
     * @return DayOfWeekEnum
     */
    public static DayOfWeekEnum getDayOfWeek() {
        DayOfWeekEnum[] weeks = DayOfWeekEnum.values();
        DayOfWeek dayOfWeek = toLocalDate(DateUtil.now()).getDayOfWeek();
        int dayOfWeekValue = dayOfWeek.getValue();
        return weeks[dayOfWeekValue - 1];
    }

    /**
     * 得到某个时间是周几
     *
     * @param date date
     * @return DayOfWeekEnum
     */
    public static DayOfWeekEnum getDayOfWeek(final Date date) {
        DayOfWeekEnum[] weeks = DayOfWeekEnum.values();
        DayOfWeek dayOfWeek = toLocalDate(date).getDayOfWeek();
        int dayOfWeekValue = dayOfWeek.getValue();
        return weeks[dayOfWeekValue - 1];
    }

    // 所在年月周的第几天===========================================================================================================

    /**
     * 当天是在一个月内的第几天
     *
     * @return long
     */
    public static long dayOfTheYear() {
        return LocalDate.now().getDayOfYear();
    }

    /**
     * date是在一年内的第几天
     *
     * @param date 日期
     * @return long
     */
    public static long dayOfTheYear(final Date date) {
        return toLocalDate(date).getDayOfYear();
    }

    /**
     * 当天是在一个月内的第几天
     *
     * @return long
     */
    public static long dayOfTheMonth() {
        return LocalDate.now().getDayOfMonth();
    }

    /**
     * date是在一个月内的第几天
     *
     * @param date 日期
     * @return long
     */
    public static long dayOfTheMonth(final Date date) {
        return toLocalDate(date).getDayOfMonth();
    }

    /**
     * 当天是在一周的第几天
     *
     * @return long
     */
    public static long dayOfTheWeek() {
        return LocalDate.now().getDayOfWeek().getValue();
    }

    /**
     * date是在一周的第几天
     *
     * @param date 日期
     * @return long
     */
    public static long dayOfTheWeek(final Date date) {
        return toLocalDate(date).getDayOfWeek().getValue();
    }

    // 是否同一天===========================================================================================================

    /**
     * 是否同一天
     *
     * @param date1 date1
     * @param date2 date2
     * @return boolean
     */
    public static boolean isSameDay(final Date date1, final Date date2) {
        LocalDate localDateFrom = toLocalDate(date1);
        LocalDate localDateTo = toLocalDate(date2);
        return localDateFrom.equals(localDateTo);
    }

    // 年份月份相关===========================================================================================================

    /**
     * 获取年月文本：2019-07
     *
     * @return str
     */
    public static String yearMonthToday() {
        return yearMonth(new Date());
    }

    /**
     * 获取年月文本：2019-07
     *
     * @param date 日期
     * @return str
     */
    public static String yearMonth(final Date date) {
        LocalDate localDate = toLocalDate(date);
        YearMonth yearMonth = YearMonth.from(localDate);
        return yearMonth.toString();
    }

    // 月份日期相关===========================================================================================================

    /**
     * 获取月日文本：07-09
     *
     * @return 当日MM-dd
     */
    public static String monthDayToday() {
        return monthDay(new Date());
    }

    /**
     * 获取月日文本：07-09
     *
     * @param date 日期
     * @return 获取MM-dd
     */
    public static String monthDay(final Date date) {
        String formatStr = "%s-%s";
        LocalDate now = toLocalDate(date);
        MonthDay monthDay = MonthDay.from(now);
        int monthValue = monthDay.getMonthValue();
        int dayOfMonth = monthDay.getDayOfMonth();
        Object monthZeroFill = monthValue < 10 ? "0" + monthValue : monthValue;
        Object dayZeroFill = dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth;
        return String.format(formatStr, monthZeroFill, dayZeroFill);
    }

    /**
     * 判断今天是否生日
     *
     * @param birthday 生日日期
     * @return 是否
     */
    public static boolean isBirthdayToday(final Date birthday) {
        return isCycleDate(birthday);
    }

    /**
     * 判断今天是否某个周期性日期
     *
     * @param cycleDate 周期性日期
     * @return True is a periodic date and false is a non-periodic date.
     */
    public static boolean isCycleDate(final Date cycleDate) {
        MonthDay cycleMonthDay = MonthDay.from(toLocalDate(cycleDate));

        LocalDate now = toLocalDate(new Date());
        MonthDay monthDay = MonthDay.from(now);

        return cycleMonthDay.equals(monthDay);
    }

    // 判断是否闰年===========================================================================================================

    /**
     * 判断当年是否闰年
     *
     * @return boolean
     */
    public static boolean isLeapYear() {
        LocalDate nowDate = LocalDate.now();
        // 判断闰年
        return nowDate.isLeapYear();
    }

    /**
     * 判断date所在年份是否闰年
     *
     * @param date 日期
     * @return boolean
     */
    public static boolean isLeapYear(final Date date) {
        LocalDate nowDate = toLocalDate(date);
        // 判断闰年
        return nowDate.isLeapYear();
    }

    public static boolean isLeapYear(final int year) {
        return isLeapYear(toDate(LocalDate.of(year, 1, 1)));
    }

    // 获取时间戳===========================================================================================================

    /**
     * 获取当前时间的秒级时间戳
     *
     * @return 时间戳
     */
    public static long currentTimeSeconds() {
        // 将当前时间转换为LocalDateTime对象
        LocalDateTime localDateTime = toLocalDateTime(now());
        // 将LocalDateTime对象转换为从1970年1月1日00:00:00 UTC到当前时间的秒数，并使用默认时区
        return localDateTime.toEpochSecond(ZoneOffset.of(DEFAULT_OFFSET_ID));

    }

    /**
     * 获取当前时间的毫秒级时间戳
     *
     * @return 时间戳
     */
    public static long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当前时间的毫秒级时间戳
     *
     * @return long
     */
    public static long currentTimeMillisByClock() {
        // java1.8
        return Clock.systemDefaultZone().millis();
        // 使用localDateTime获取当前毫秒值
        // return LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 获取date的秒级时间戳
     *
     * @param date 时间
     * @return 时间戳
     */
    public static long getTimeSeconds(final Date date) {
        LocalDateTime localDateTime = toLocalDateTime(date);
        return localDateTime.toEpochSecond(ZoneOffset.of(DEFAULT_OFFSET_ID));
    }

    /**
     * 获取date的毫秒级时间戳
     *
     * @param date 时间
     * @return 时间戳
     */
    public static long getTimeMillis(final Date date) {
        LocalDateTime localDateTime = toLocalDateTime(date);
        return localDateTime.toInstant(ZoneOffset.of(DEFAULT_OFFSET_ID)).toEpochMilli();
    }

    // 补零补位===========================================================================================================

    /**
     * 获取指定日期零点 00:00:00
     *
     * @param date 日期
     * @return Date
     */
    public static Date zeroOfDay(final Date date) {
        if (date == null) {
            return null;
        }
        LocalDate localDate = toLocalDate(date);
        return of(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth(), 0, 0, 0);
    }

    /**
     * 获取指定日期最后时刻 23:59:59
     *
     * @param date 日期
     * @return Date
     */
    public static Date lastMomentOfDay(final Date date) {
        if (date == null) {
            return null;
        }
        LocalDate localDate = toLocalDate(date);
        return of(localDate.getYear(), localDate.getMonthValue(), localDate.getDayOfMonth(), 23, 59, 59);
    }

    /**
     * 起止时间补零
     *
     * @param firstTime 补00:00:00
     * @param lastTime  补23:59:59
     * @return list
     */
    public static List<Date> initFirstAndLastTime(final Date firstTime, final Date lastTime) {
        return ListUtil.newArrayList(zeroOfDay(firstTime), lastMomentOfDay(lastTime));
    }

    // 计算日期差值===========================================================================================================

    /**
     * 判断目标日期与当天相差天数是否不超过days
     *
     * @param targetDate 目标日期
     * @param days       距离当前时间的天数
     * @return boolean 是否不超过
     */
    public static boolean dayDifferenceToday(final Date targetDate, final int days) {
        return dayDifferenceToday(targetDate, days, true);
    }

    /**
     * 判断目标日期与当天相差天数是否不超过days
     * 目标日期无论是早于当前时间还是晚于当前时间，最终取绝对值与days对比
     *
     * @param targetDate 目标天数
     * @param days       距离当前时间的天数
     * @param absolute   是否按绝对值计算
     *                   absolute：true 超过一秒都算2天
     *                   absolute：false 范围值，超过一天没超过2天的都算一天
     * @return boolean 是否不超过
     */
    public static boolean dayDifferenceToday(final Date targetDate, final int days, final boolean absolute) {
        long leftDays;
        final Date dateTo = new Date();
        if (absolute) {
            leftDays = dayDifferenceForAbsolute(targetDate, dateTo);
        } else {
            leftDays = dayDifference(targetDate, dateTo);
        }
        return Math.abs(leftDays) <= days;
    }

    /**
     * 计算两个日期相差天数（绝对值，超过一秒都算是超过一天）
     *
     * @param dateFrom 开始日期
     * @param dateTo   结束日期
     * @return 两个日期天数差值
     */
    public static int dayDifferenceForAbsolute(final Date dateFrom, final Date dateTo) {
        long time1 = getTimeMillis(dateFrom);
        long time2 = getTimeMillis(dateTo);

        long betweenDays = (time2 - time1) / ONE_DAT_TIMESTAMP;
        long betweenDaysOfRemainder = (time2 - time1) % ONE_DAT_TIMESTAMP;

        if (betweenDaysOfRemainder > 0) {
            ++betweenDays;
        }

        return Integer.parseInt(String.valueOf(betweenDays));
    }

    /**
     * 是否24小时内
     *
     * @param date date
     * @return boolean
     */
    public static boolean within24Hours(final Date date) {
        Date end = new Date();
        long cha = end.getTime() - date.getTime();
        if (cha < 0) {
            return false;
        }
        double result = cha * 1.0 / (1000 * 60 * 60);
        return result <= 24;
    }

    //  equals ====================================================================================

    /**
     * 对比日期
     *
     * @param date1 date1
     * @param date2 date2
     * @return boolean
     */
    public static boolean equalsDay(final Date date1, final Date date2) {
        LocalDate localDate1 = toLocalDate(date1);
        LocalDate localDate2 = toLocalDate(date2);
        return localDate1.equals(localDate2);
    }

    /**
     * 对比月日
     *
     * @param date1 date1
     * @param date2 date2
     * @return boolean
     */
    public static boolean equalsMonthDay(final Date date1, final Date date2) {
        MonthDay localDate1 = toMonthDay(date1);
        MonthDay localDate2 = toMonthDay(date2);
        return localDate1.equals(localDate2);
    }

    /**
     * 对比年月
     *
     * @param date1 date1
     * @param date2 date2
     * @return boolean
     */
    public static boolean equalsYearMonth(final Date date1, final Date date2) {
        YearMonth localDate1 = toYearMonth(date1);
        YearMonth localDate2 = toYearMonth(date2);
        return localDate1.equals(localDate2);
    }

    /**
     * 对比年
     *
     * @param date1 date1
     * @param date2 date2
     * @return Ture is the same year and false is not the same year.
     */
    public static boolean equalsYear(final Date date1, final Date date2) {
        YearMonth localDate1 = toYearMonth(date1);
        YearMonth localDate2 = toYearMonth(date2);
        return localDate1.getYear() == localDate2.getYear();
    }

    /**
     * 获取本月共有多少天
     *
     * @return 总共天数
     */
    public static int lengthOfMonth() {
        YearMonth currentYearMonth = YearMonth.now();
        return currentYearMonth.lengthOfMonth();
    }

    /**
     * 获取指定日期所在月份共有多少天
     *
     * @return 总共天数
     */
    public static int lengthOfMonth(final Date date) {
        YearMonth currentYearMonth = toYearMonth(date);
        return currentYearMonth.lengthOfMonth();
    }

    //  间隔时间 ====================================================================================

    /**
     * 间隔时间对比 返回 ?d?h?m?s
     *
     * @param fromDate from小 to大 返回为正数
     * @param toDate   from大 to小 返回为负数
     * @return ?天?时?分?秒
     */
    public static String intervalTimeDifferenceShort(Date fromDate, Date toDate) {
        return intervalTimeDifference(fromDate, toDate, true);
    }

    /**
     * 间隔时间对比 返回两个时间间隔 ?天?时?分?秒
     *
     * @param fromDate from小 to大 返回为正数
     * @param toDate   from大 to小 返回为负数
     * @return returnMode ?天?时?分?秒
     */
    public static String intervalTimeDifferenceByCN(Date fromDate, Date toDate) {
        return intervalTimeDifference(fromDate, toDate, false);
    }

    /**
     * 间隔时间对比 返回 ?d?h?m?s
     *
     * @param fromDate from小 to大 返回为正数
     * @param toDate   from大 to小 返回为负数
     * @return returnMode 返回模式 ?d?h?m?s
     */
    public static String intervalTimeDifferenceByEN(Date fromDate, Date toDate) {
        return intervalTimeDifference(fromDate, toDate, true);
    }

    /**
     * 间隔时间对比
     *
     * @param fromDate   from小 to大 返回为正数
     * @param toDate     from大 to小 返回为负数
     * @param returnMode 返回模式 true:?d?h?m?s/false:?天?时?分?秒
     */
    public static String intervalTimeDifference(Date fromDate, Date toDate, boolean returnMode) {
        LocalDateTime localDateTimeFrom = toLocalDateTime(fromDate);
        LocalDateTime localDateTimeTo = toLocalDateTime(toDate);

        //  天 - 根据指定的单元计算到另一个日期时间的时间量。
        long day = localDateTimeFrom.until(localDateTimeTo, ChronoUnit.DAYS);
        //  小时
        localDateTimeFrom = localDateTimeFrom.plusDays(day);
        long hour = localDateTimeFrom.until(localDateTimeTo, ChronoUnit.HOURS);
        //  分钟
        localDateTimeFrom = localDateTimeFrom.plusHours(hour);
        long minute = localDateTimeFrom.until(localDateTimeTo, ChronoUnit.MINUTES);
        //  秒
        localDateTimeFrom = localDateTimeFrom.plusMinutes(minute);
        long second = localDateTimeFrom.until(localDateTimeTo, ChronoUnit.SECONDS);

        return String.format(returnMode ? INTERVAL_TIME_SHORTHAND : INTERVAL_TIME_STR, day, hour, minute, second);
    }

    /**
     * 现在是否dateTime对应的下一个自然月
     *
     * @param dateTime dateTime
     * @return boolean
     */
    public static boolean isNextNaturalMonthByNow(Date dateTime) {
        return isXNaturalMonthLaterByNow(dateTime, 1);
    }

    /**
     * 现在是否dateTime对应的x个自然月
     *
     * @param dateTime dateTime
     * @return boolean
     */
    public static boolean isXNaturalMonthLaterByNow(Date dateTime, int x) {
        if (ObjectUtil.isNull(dateTime)) {
            throw new RuntimeException("param cannot be null");
        }
        if (x <= 0) {
            throw new RuntimeException("x cannot be less than 0");
        }
        Date nextMonthDateOfExpected = moreOrLessMonths(dateTime, x);
        LocalDate localDateOfNow = toLocalDate(DateUtil.now());
        LocalDate localDateOfExpected = toLocalDate(nextMonthDateOfExpected);
        String yearMonthOfNow = localDateOfNow.toString().substring(0, 7);
        String yearMonthOfExpected = localDateOfExpected.toString().substring(0, 7);
        return StringUtil.equals(yearMonthOfNow, yearMonthOfExpected);
    }

    //  compare ====================================================================================

    /**
     * 指定格式对比两个日期
     *
     * @param date1  date1
     * @param date2  date2
     * @param format format
     * @return int
     */
    public static int compare(Date date1, Date date2, DateFormat format) {
        if (format != null) {
            if (date1 != null) {
                date1 = parse(format, format(format, date1));
            }
            if (date2 != null) {
                date2 = parse(format, format(format, date2));
            }
        }
        return CompareUtil.compare(date1, date2);
    }

}