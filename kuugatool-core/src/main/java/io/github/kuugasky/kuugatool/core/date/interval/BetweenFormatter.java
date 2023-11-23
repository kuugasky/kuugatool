package io.github.kuugasky.kuugatool.core.date.interval;

import io.github.kuugasky.kuugatool.core.constants.KuugaConstants;
import io.github.kuugasky.kuugatool.core.date.DateUnit;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.lang.NonNull;

import java.io.Serial;
import java.io.Serializable;
import java.security.InvalidParameterException;

/**
 * 时长格式化器，用于格式化输出两个日期相差的时长<br>
 * 根据{@link Level}不同，调用{@link #format()}方法后返回类似于：
 * <ul>
 *    <li>XX小时XX分XX秒</li>
 *    <li>XX天XX小时</li>
 *    <li>XX月XX天XX小时</li>
 * </ul>
 *
 * @author Looly
 */
public final class BetweenFormatter implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 时长毫秒数
     */
    private long betweenMs;
    /**
     * 格式化级别
     */
    private Level level;
    /**
     * 格式化级别的最大个数
     */
    private final int levelMaxCount;

    /**
     * 构造
     *
     * @param betweenMs 日期间隔
     * @param level     级别，按照天、小时、分、秒、毫秒分为5个等级，根据传入等级，格式化到相应级别
     */
    public BetweenFormatter(long betweenMs, @NonNull Level level) {
        this(betweenMs, level, level.level);
    }

    /**
     * 构造
     *
     * @param betweenMs     日期间隔
     * @param level         级别，按照天、小时、分、秒、毫秒分为5个等级，根据传入等级，格式化到相应级别
     * @param levelMaxCount 格式化级别的最大个数，假如级别个数为1，但是级别到秒，那只显示一个级别
     */
    public BetweenFormatter(long betweenMs, Level level, int levelMaxCount) {
        this.betweenMs = betweenMs;
        this.level = level;
        if (levelMaxCount <= KuugaConstants.ZERO) {
            throw new InvalidParameterException("levelMaxCount can't be less than or equal to 0");
        }
        if (levelMaxCount > KuugaConstants.FIVE) {
            throw new InvalidParameterException("levelMaxCount can't be more than 5");
        }
        this.levelMaxCount = levelMaxCount;
    }

    /**
     * 格式化日期间隔输出<br>
     *
     * @return 格式化后的字符串
     */
    public String format() {
        return format(Pattern.CHINESE);
    }

    public String format(Pattern pattern) {
        final StringBuilder sb = new StringBuilder();
        if (betweenMs > 0) {
            long day = betweenMs / DateUnit.DAY.getMillis();
            long hour = betweenMs / DateUnit.HOUR.getMillis() - day * 24;
            long minute = betweenMs / DateUnit.MINUTE.getMillis() - day * 24 * 60 - hour * 60;

            final long betweenOfSecond = ((day * 24 + hour) * 60 + minute) * 60;
            long second = betweenMs / DateUnit.SECOND.getMillis() - betweenOfSecond;
            long millisecond = betweenMs - (betweenOfSecond + second) * 1000;

            final int level = this.level.ordinal();
            int levelCount = 0;

            if (isLevelCountValid(levelCount) && 0 != day) {
                sb.append(day).append(Level.DAY.sign(pattern));
                levelCount++;
            }
            if (isLevelCountValid(levelCount) && 0 != hour && level >= Level.HOUR.ordinal()) {
                sb.append(hour).append(Level.HOUR.sign(pattern));
                levelCount++;
            }
            if (isLevelCountValid(levelCount) && 0 != minute && level >= Level.MINUTE.ordinal()) {
                sb.append(minute).append(Level.MINUTE.sign(pattern));
                levelCount++;
            }
            if (isLevelCountValid(levelCount) && 0 != second && level >= Level.SECOND.ordinal()) {
                sb.append(second).append(Level.SECOND.sign(pattern));
                levelCount++;
            }
            if (isLevelCountValid(levelCount) && 0 != millisecond && level >= Level.MILLISECOND.ordinal()) {
                sb.append(millisecond).append(Level.MILLISECOND.sign(pattern));
                // levelCount++;
            }
        }

        if (StringUtil.isEmpty(sb)) {
            sb.append(0).append(this.level.sign(pattern));
        }

        return sb.toString();
    }

    /**
     * 获得 时长毫秒数
     *
     * @return 时长毫秒数
     */
    public long getBetweenMs() {
        return betweenMs;
    }

    /**
     * 设置 时长毫秒数
     *
     * @param betweenMs 时长毫秒数
     */
    public void setBetweenMs(long betweenMs) {
        this.betweenMs = betweenMs;
    }

    /**
     * 获得 格式化级别
     *
     * @return 格式化级别
     */
    public Level getLevel() {
        return level;
    }

    /**
     * 设置格式化级别
     *
     * @param level 格式化级别
     */
    public void setLevel(Level level) {
        this.level = level;
    }

    public enum Pattern {
        /**
         *
         */
        CHINESE,
        ENGLISH_UPPER,
        ENGLISH_LOWER,
    }

    /**
     * 格式化等级枚举
     *
     * @author Looly
     */
    @Getter
    @AllArgsConstructor
    public enum Level {

        /**
         * 天
         */
        DAY(1, "天", "D", "d"),
        /**
         * 小时
         */
        HOUR(2, "小时", "H", "h"),
        /**
         * 分钟
         */
        MINUTE(3, "分", "M", "m"),
        /**
         * 秒
         */
        SECOND(4, "秒", "S", "s"),
        /**
         * 毫秒
         */
        MILLISECOND(5, "毫秒", "MS", "ms");

        /**
         * 级别
         */
        private final int level;
        /**
         * 级别名称
         */
        private final String name;
        /**
         * 级别英文大写
         */
        private final String englishUpper;
        /**
         * 级别英文小写
         */
        private final String englishLower;

        public String sign(Pattern pattern) {
            return switch (pattern) {
                case CHINESE -> this.name;
                case ENGLISH_UPPER -> this.englishUpper;
                case ENGLISH_LOWER -> this.englishLower;
            };
        }

    }

    @Override
    public String toString() {
        return format();
    }

    /**
     * 等级数量是否有效<br>
     * 有效的定义是：levelMaxCount大于0（被设置），当前等级数量没有超过这个最大值
     *
     * @param levelCount 登记数量
     * @return 是否有效
     */
    private boolean isLevelCountValid(int levelCount) {
        return this.levelMaxCount <= 0 || levelCount < this.levelMaxCount;
    }
}
