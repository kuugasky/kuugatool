package io.github.kuugasky.kuugatool.core.date.time;

import io.github.kuugasky.kuugatool.core.date.time.core.ChineseTimeParse;
import io.github.kuugasky.kuugatool.core.date.time.core.EnglishTimeParse;
import io.github.kuugasky.kuugatool.core.date.time.core.TimeParse;
import io.github.kuugasky.kuugatool.core.date.time.enums.LimitTime;
import io.github.kuugasky.kuugatool.core.date.time.exception.ParseException;
import io.github.kuugasky.kuugatool.core.enums.EnumUtil;
import io.github.kuugasky.kuugatool.core.regex.RegexUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;

import java.util.Arrays;

/**
 * 时间解析器工厂
 *
 * @author pengqinglong
 * @since 2022/2/15
 */
public class TimeParseFactory {

    /**
     * 中文表达式组
     */
    private static final String[] CHINESE = {"年", "月", "日", "时", "分", "秒"};

    /**
     * 英文表达式组
     */
    private static final String[] ENGLISH = {"y", "M", "d", "h", "m", "s"};

    /**
     * 解析时间表达式
     * <p>
     * - 0y0M0d0h0m0s:ChineseTimeParse
     * - 0年0月0日0时0分0秒:EnglishTimeParse
     *
     * @param timeExpression 带时间信息带表达式
     * @return TimeParse
     */
    public static TimeParse create(String timeExpression) {
        if (StringUtil.isEmpty(timeExpression)) {
            throw new ParseException("时间表达式不能为空");
        }

        // 是否枚举类型
        boolean isEnumType = EnumUtil.contains(LimitTime.class, timeExpression);
        if (isEnumType) {
            return new ChineseTimeParse();
        }

        // 是否中文时间表达式
        boolean containChinese = Arrays.stream(CHINESE).anyMatch(timeExpression::contains);
        if (containChinese) {
            checkChinaFormatter(timeExpression);
            return new ChineseTimeParse();
        }

        // 是否英文时间表达式
        boolean containEnglish = Arrays.stream(ENGLISH).anyMatch(timeExpression::contains);
        if (containEnglish) {
            checkEnglishFormatter(timeExpression);
            return new EnglishTimeParse();
        }

        throw new ParseException("[%s] 无法找到匹配的parse");
    }

    private static void checkEnglishFormatter(String timeExpression) {
        boolean englishMatch = RegexUtil.isMatch("^\\d+y\\d+M\\d+d\\d+h\\d+m\\d+s$", timeExpression);
        if (!englishMatch) {
            throw new RuntimeException("时间表达式错误，请按照以下格式处理：0y0M0d0h0m0s");
        }
    }

    private static void checkChinaFormatter(String timeExpression) {
        boolean chinaMatch = RegexUtil.isMatch("^\\d+年\\d+月\\d+日\\d+时\\d+分\\d+秒$", timeExpression);
        if (!chinaMatch) {
            throw new ParseException("时间表达式错误，请按照以下格式处理：0年0月0日0时0分0秒");
        }
    }

}