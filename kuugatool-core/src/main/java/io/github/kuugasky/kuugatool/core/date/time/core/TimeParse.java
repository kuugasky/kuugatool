package io.github.kuugasky.kuugatool.core.date.time.core;

import io.github.kuugasky.kuugatool.core.date.DateUtil;
import io.github.kuugasky.kuugatool.core.date.time.ConvertorParse;
import io.github.kuugasky.kuugatool.core.date.time.entity.TimeParseEntity;
import io.github.kuugasky.kuugatool.core.date.time.enums.LimitTime;
import io.github.kuugasky.kuugatool.core.string.StringUtil;

import java.util.Date;

/**
 * 时间格式解析器
 *
 * @author pengqinglong
 * @since 2022/2/15
 */
public abstract class TimeParse implements ConvertorParse<String, Long> {

    /**
     * 初始化parse的规则
     *
     * @return 时间解析实体
     */
    protected abstract TimeParseEntity initParseUnit();

    /**
     * 解析时间表达式，获取对应秒差值
     *
     * @param timeExpression 时间表达式
     * @return 秒差值
     */
    @Override
    public Long parse(String timeExpression) {

        TimeParseEntity entity = this.initParseUnit();

        // 提取时间表达式进行赋值
        this.handle(timeExpression, entity);

        // 解析数据
        return this.calculate(entity);
    }

    /**
     * 格式是否符合要求
     */
    protected void handle(String time, TimeParseEntity entity) {
        // TO_DAY 计算当前时间到第二天0点相差秒数
        if (StringUtil.equals(LimitTime.TODAY.name(), time)) {
            entity.setToday(true);
            return;
        }
        // TODAY_NOW 计算当前时间到第二天满24小时相差秒数
        if (StringUtil.equals(LimitTime.TODAY_NOW.name(), time)) {
            entity.setDay(1);
            return;
        }
        // 快照增加一个占位符 防止越界
        String snapShoot = time + "#";

        snapShoot = this.formatYear(snapShoot, entity);

        snapShoot = this.formatMonth(snapShoot, entity);

        snapShoot = this.formatDay(snapShoot, entity);

        snapShoot = this.formatHour(snapShoot, entity);

        snapShoot = this.formatMinute(snapShoot, entity);

        this.formatSecond(snapShoot, entity);
    }

    /**
     * 计算结果
     */
    protected long calculate(TimeParseEntity entity) {
        if (entity.isToday()) {
            // 获取当天到第二日0点相差秒数
            return DateUtil.secondsLeftToday();
        }
        // 当前时间
        Date now = DateUtil.now();
        // 时间表达式目标时间
        Date targetDate = new Date();

        targetDate = DateUtil.moreOrLessYears(targetDate, entity.getYear());
        targetDate = DateUtil.moreOrLessMonths(targetDate, entity.getMonth());
        targetDate = DateUtil.moreOrLessDays(targetDate, entity.getDay());
        targetDate = DateUtil.moreOrLessHours(targetDate, entity.getHour());
        targetDate = DateUtil.moreOrLessMinutes(targetDate, entity.getMinute());
        targetDate = DateUtil.moreOrLessSeconds(targetDate, entity.getSecond());
        // 计算两个日期时间相差秒数
        return DateUtil.secondDifference(now, targetDate);
    }

    /**
     * 切分时间表达式，提取秒值
     *
     * @param timeExpression 时间表达式
     * @param entity         时间解析实体
     */
    private void formatSecond(String timeExpression, TimeParseEntity entity) {
        int i = timeExpression.indexOf(entity.getSecondUnit());
        if (i == -1) {
            // 填空补0
            entity.setSecond(0);
            return;
        }
        String[] split = timeExpression.split(entity.getSecondUnit());
        entity.setSecond(Integer.parseInt(split[0]));
    }

    /**
     * 切分时间表达式，提取分值
     *
     * @param timeExpression 时间表达式
     * @param entity         时间解析实体
     * @return 分值
     */
    private String formatMinute(String timeExpression, TimeParseEntity entity) {
        int i = timeExpression.indexOf(entity.getMinuteUnit());
        if (i == -1) {
            // 填空补0
            entity.setMinute(0);
            return timeExpression;
        }
        String[] split = timeExpression.split(entity.getMinuteUnit());
        entity.setMinute(Integer.parseInt(split[0]));
        return split[1];
    }

    /**
     * 切分时间表达式，提取时值
     *
     * @param timeExpression 时间表达式
     * @param entity         时间解析实体
     * @return 时值
     */
    private String formatHour(String timeExpression, TimeParseEntity entity) {
        int i = timeExpression.indexOf(entity.getHourUnit());
        if (i == -1) {
            // 填空补0
            entity.setHour(0);
            return timeExpression;
        }
        String[] split = timeExpression.split(entity.getHourUnit());
        entity.setHour(Integer.parseInt(split[0]));
        return split[1];
    }

    /**
     * 切分时间表达式，提取日期值
     *
     * @param timeExpression 时间表达式
     * @param entity         时间解析实体
     * @return 日期值
     */
    private String formatDay(String timeExpression, TimeParseEntity entity) {
        int i = timeExpression.indexOf(entity.getDayUnit());
        if (i == -1) {
            // 填空补0
            entity.setDay(0);
            return timeExpression;
        }
        String[] split = timeExpression.split(entity.getDayUnit());
        entity.setDay(Integer.parseInt(split[0]));
        return split[1];
    }

    /**
     * 切分时间表达式，提取月份值
     *
     * @param timeExpression 时间表达式
     * @param entity         时间解析实体
     * @return 月份值
     */
    private String formatMonth(String timeExpression, TimeParseEntity entity) {
        int i = timeExpression.indexOf(entity.getMonthUnit());
        if (i == -1) {
            // 填空补0
            entity.setMonth(0);
            return timeExpression;
        }
        String[] split = timeExpression.split(entity.getMonthUnit());
        entity.setMonth(Integer.parseInt(split[0]));
        return split[1];
    }

    /**
     * 切分时间表达式，提取年份值
     *
     * @param timeExpression 时间表达式
     * @param entity         时间解析实体
     * @return 年份值
     */
    private String formatYear(String timeExpression, TimeParseEntity entity) {
        int i = timeExpression.indexOf(entity.getYearUnit());
        if (i == -1) {
            // 填空补0
            entity.setYear(0);
            return timeExpression;
        }
        String[] split = timeExpression.split(entity.getYearUnit());
        entity.setYear(Integer.parseInt(split[0]));
        return split[1];
    }

}