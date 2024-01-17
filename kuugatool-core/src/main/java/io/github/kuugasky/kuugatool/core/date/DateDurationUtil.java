package io.github.kuugasky.kuugatool.core.date;

import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * DateDurationUtil
 * <p>
 * Duration 表示一个时间段，Duration 包含两部分：seconds 表示秒，nanos 表示纳秒，它们的组合表达了时间长度。
 *
 * @author kuuga
 * @since 2022/7/11 10:01
 */
public final class DateDurationUtil {

    /**
     * 获取表示两个时间对象之间持续时间的Duration。<br>
     * 它计算两个临时对象之间的持续时间。<br>
     * 如果对象类型不同，则根据第一个对象的类型计算持续时间。<br>
     * 例如，如果第一个参数是LocalTime，那么第二个参数将转换为LocalTime。<br>
     * 指定的时态对象必须支持SECONDS单元。<br>
     * 为了完全准确，应该支持NANOS单元或NANO_OF_SECOND字段。<br>
     * 如果结束在开始之前，这个方法的结果可以是一个负数周期。<br>
     * 为了保证获得正的持续时间，对结果调用abs()。
     *
     * @param startInclusive the start instant, inclusive, not null
     * @param endExclusive   the end instant, exclusive, not null
     * @return a {@code Duration}, not null
     * @throws DateTimeException   if the seconds between the temporals cannot be obtained
     * @throws ArithmeticException if the calculation exceeds the capacity of {@code Duration}
     */
    public static Duration between(LocalDateTime startInclusive, LocalDateTime endExclusive) {
        return Duration.between(startInclusive, endExclusive);
    }

    public static Duration between(LocalTime startInclusive, LocalTime endExclusive) {
        return Duration.between(startInclusive, endExclusive);
    }

    /**
     * 获取此持续期间的天数。(多一纳秒都不行)
     * 这将通过将秒数除以86400返回持续时间内的总天数。
     * 这是基于一天为24小时的标准定义。
     * 此实例是不可变的，且不受此方法调用的影响。
     *
     * @param startDateTime 开始日期时间
     * @param endDateTime   结束日期时间
     * @return 持续时间中的天数，可以是负数
     */
    public static long toDays(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Duration duration = Duration.between(startDateTime, endDateTime);
        return duration.toDays();
    }

    /**
     * 提取持续时间中的天数部分。完全等同于{@link DateDurationUtil#toDays(LocalDateTime, LocalDateTime)}
     *
     * @param startDateTime 开始日期时间
     * @param endDateTime   结束日期时间
     * @return 持续时间中的天数，可以是负数
     */
    public static long toDaysPart(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Duration duration = Duration.between(startDateTime, endDateTime);
        return duration.toDaysPart();
    }

    /**
     * 获取此持续时间中的小时数。(多一纳秒都不行)
     * 这将通过将秒数除以3600返回持续时间中的总小时数。
     * 此实例是不可变的，且不受此方法调用的影响。
     *
     * @param startDateTime 开始日期时间
     * @param endDateTime   结束日期时间
     * @return 获取描述差值
     */
    public static long toHours(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Duration duration = Duration.between(startDateTime, endDateTime);
        return duration.toHours();
    }

    /**
     * 提取持续时间中的小时数部分。(多一纳秒都不行)
     * 返回用{@link #toHours}除以一天中的小时数时剩余的小时数。
     * (int) (toHours() % 24);
     * 这是基于一天为24小时的标准定义。
     * 该实例是不可变的，不受此方法调用的影响。
     *
     * @param startDateTime 开始日期时间
     * @param endDateTime   结束日期时间
     * @return 获取描述差值
     */
    public static long toHoursPart(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Duration duration = Duration.between(startDateTime, endDateTime);
        return duration.toHoursPart();
    }

    /**
     * 获取此持续时间中的分钟数。(多一纳秒都不行)
     * 这将通过将秒数除以60返回持续时间内的总分钟数。
     * 此实例是不可变的，且不受此方法调用的影响。
     *
     * @param startDateTime 开始日期时间
     * @param endDateTime   结束日期时间
     * @return 获取描述差值
     */
    public static long toMinutes(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Duration duration = Duration.between(startDateTime, endDateTime);
        return duration.toMinutes();
    }

    /**
     * 获取此持续时间中的分钟数部分。
     * (int) (toMinutes() % MINUTES_PER_HOUR);
     *
     * @param startDateTime 开始日期时间
     * @param endDateTime   结束日期时间
     * @return 获取描述差值
     */
    public static long toMinutesPart(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Duration duration = Duration.between(startDateTime, endDateTime);
        return duration.toMinutesPart();
    }

    /**
     * 获取此持续时间内的秒数。
     * 返回在持续时间内的整个秒数。
     * 该实例是不可变的，不受此方法调用的影响。
     *
     * @param startDateTime 开始日期时间
     * @param endDateTime   结束日期时间
     * @return 获取描述差值
     */
    public static long toSeconds(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Duration duration = Duration.between(startDateTime, endDateTime);
        return duration.toSeconds();
    }

    /**
     * 获取此持续时间内的秒数部分。
     *
     * @param startDateTime 开始日期时间
     * @param endDateTime   结束日期时间
     * @return 获取描述差值
     */
    public static long toSecondsPart(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Duration duration = Duration.between(startDateTime, endDateTime);
        return duration.toSecondsPart();
    }

    /**
     * 将此持续时间转换为以毫秒为单位的总长度。
     * 如果持续时间太长，无法在很长的毫秒内容纳，则会引发异常。
     * 如果这个持续时间的精度大于毫秒，那么转换将删除任何多余的精度信息，就像纳秒数量被整数除以一百万一样。
     * PS:年-月-日-时-分-秒-毫秒-微妙-纳秒
     * 1 秒 = 1_000 毫秒
     * 1 秒 = 1_000_000 微秒 = 1_1000 * 1_1000 毫秒
     * 1 秒 = 1_000_000_000 纳秒 = 1_1000 * 1_1000 * 1_1000 毫秒
     *
     * @param startDateTime 开始日期时间
     * @param endDateTime   结束日期时间
     * @return 获取毫秒数差值
     */
    public static long toMillis(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Duration duration = Duration.between(startDateTime, endDateTime);
        return duration.toMillis();
    }

    /**
     * 将此持续时间转换为以毫秒为单位的总长度部分。
     *
     * @param startDateTime 开始日期时间
     * @param endDateTime   结束日期时间
     * @return 获取纳秒数差值
     */
    public static long toMillisPart(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Duration duration = Duration.between(startDateTime, endDateTime);
        return duration.toMillisPart();
    }

    /**
     * 将此持续时间转换为以纳秒为单位的总长度，以{@code long}表示。
     * 注意：{@link Duration#toNanos()}是完整的纳秒差值，{@link Duration#getNano()}和{@link Duration#toNanosPart()} ()}只是纳秒部分的差值
     *
     * @param startDateTime 开始日期时间
     * @param endDateTime   结束日期时间
     * @return 获取纳秒数差值
     */
    public static long toNanos(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Duration duration = Duration.between(startDateTime, endDateTime);
        return duration.toNanos();
    }

    /**
     * 得到持续时间秒内的纳秒部分。
     *
     * @param startDateTime 开始日期时间
     * @param endDateTime   结束日期时间
     * @return 获取纳秒数差值
     */
    public static long toNanosPart(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        Duration duration = Duration.between(startDateTime, endDateTime);
        return duration.toNanosPart();
    }

    /**
     * 如果endDateTime日期时间早于startDateTime日期时间，则返回false，否则为true
     * 同日期返回false，只有当endDateTime日期时间早于startDateTime日期时间（含纳秒）才返回true
     *
     * @param startDateTime 开始日期时间
     * @param endDateTime   结束日期时间
     * @return boolean
     */
    public static boolean isNegative(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return Duration.between(startDateTime, endDateTime).isNegative();
    }

    public static boolean isNegative(LocalTime startTime, LocalTime endTime) {
        return Duration.between(startTime, endTime).isNegative();
    }

}
