package io.github.kuugasky.kuugatool.core.date;

import io.github.kuugasky.kuugatool.core.constants.KuugaConstants;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * SimpleCalendar
 * <p>
 * 简单日历
 *
 * @author kuuga
 * @since 2021/7/12
 */
@Slf4j
public class SimpleCalendar {

    /**
     * 农历信息
     */
    static final long[] LUNAR_INFO = new long[]{
            0x4bd8, 0x4ae0, 0xa570, 0x54d5, 0xd260, 0xd950, 0x5554, 0x56af, 0x9ad0, 0x55d2,
            0x4ae0, 0xa5b6, 0xa4d0, 0xd250, 0xd255, 0xb54f, 0xd6a0, 0xada2, 0x95b0, 0x4977,
            0x497f, 0xa4b0, 0xb4b5, 0x6a50, 0x6d40, 0xab54, 0x2b6f, 0x9570, 0x52f2, 0x4970,
            0x6566, 0xd4a0, 0xea50, 0x6a95, 0x5adf, 0x2b60, 0x86e3, 0x92ef, 0xc8d7, 0xc95f,
            0xd4a0, 0xd8a6, 0xb55f, 0x56a0, 0xa5b4, 0x25df, 0x92d0, 0xd2b2, 0xa950, 0xb557,
            0x6ca0, 0xb550, 0x5355, 0x4daf, 0xa5b0, 0x4573, 0x52bf, 0xa9a8, 0xe950, 0x6aa0,
            0xaea6, 0xab50, 0x4b60, 0xaae4, 0xa570, 0x5260, 0xf263, 0xd950, 0x5b57, 0x56a0,
            0x96d0, 0x4dd5, 0x4ad0, 0xa4d0, 0xd4d4, 0xd250, 0xd558, 0xb540, 0xb6a0, 0x95a6,
            0x95bf, 0x49b0, 0xa974, 0xa4b0, 0xb27a, 0x6a50, 0x6d40, 0xaf46, 0xab60, 0x9570,
            0x4af5, 0x4970, 0x64b0, 0x74a3, 0xea50, 0x6b58, 0x5ac0, 0xab60, 0x96d5, 0x92e0,
            0xc960, 0xd954, 0xd4a0, 0xda50, 0x7552, 0x56a0, 0xabb7, 0x25d0, 0x92d0, 0xcab5,
            0xa950, 0xb4a0, 0xbaa4, 0xad50, 0x55d9, 0x4ba0, 0xa5b0, 0x5176, 0x52bf, 0xa930,
            0x7954, 0x6aa0, 0xad50, 0x5b52, 0x4b60, 0xa6e6, 0xa4e0, 0xd260, 0xea65, 0xd530,
            0x5aa0, 0x76a3, 0x96d0, 0x4afb, 0x4ad0, 0xa4d0, 0xd0b6, 0xd25f, 0xd520, 0xdd45,
            0xb5a0, 0x56d0, 0x55b2, 0x49b0, 0xa577, 0xa4b0, 0xaa50, 0xb255, 0x6d2f, 0xada0,
            0x4b63, 0x937f, 0x49f8, 0x4970, 0x64b0, 0x68a6, 0xea5f, 0x6b20, 0xa6c4, 0xaaef,
            0x92e0, 0xd2e3, 0xc960, 0xd557, 0xd4a0, 0xda50, 0x5d55, 0x56a0, 0xa6d0, 0x55d4,
            0x52d0, 0xa9b8, 0xa950, 0xb4a0, 0xb6a6, 0xad50, 0x55a0, 0xaba4, 0xa5b0, 0x52b0,
            0xb273, 0x6930, 0x7337, 0x6aa0, 0xad50, 0x4b55, 0x4b6f, 0xa570, 0x54e4, 0xd260,
            0xe968, 0xd520, 0xdaa0, 0x6aa6, 0x56df, 0x4ae0, 0xa9d4, 0xa4d0, 0xd150, 0xf252,
            0xd520};
    public static final Pattern COMPILE = Pattern.compile("^(\\d{2})(\\d{2})([\\s*])(.+)$");
    public static final Pattern COMPILE1 = Pattern.compile("^(\\d{2})(.{2})([\\s*])(.+)$");

    @Getter
    List<Element> elements = new ArrayList<>();
    public static Map<String, SimpleCalendar> cache = new HashMap<>();
    long[] solarMonth = new long[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    String[] gan = new String[]{"甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸"};
    String[] zhi = new String[]{"子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥"};

    String[] animals = new String[]{"鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪"};

    String[] solarTerm = new String[]{"小寒", "大寒", "立春", "雨水", "惊蛰", "春分", "清明", "谷雨", "立夏", "小满", "芒种", "夏至", "小暑", "大暑", "立秋", "处暑", "白露", "秋分", "寒露", "霜降", "立冬", "小雪", "大雪", "冬至"};
    int[] sTermInfo = new int[]{0, 21208, 42467, 63836, 85337, 107014, 128867, 150921, 173149, 195551, 218072, 240693, 263343, 285989, 308563, 331033, 353350, 375494, 397447, 419210, 440795, 462224, 483532, 504758};
    char[] nStr1 = new char[]{'日', '一', '二', '三', '四', '五', '六', '七', '八', '九', '十'};
    String[] nStr2 = new String[]{"初", "十", "廿", "卅", " "};

    static String[] monthChinese = new String[]{"一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"};
    static String[] dayChinese = new String[]{"一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二", "十三", "十四", "十五", "十六", "十七", "十八", "十九", "二十", "二十一", "二十二", "二十三", "二十四", "二十五", "二十六", "二十七", "二十八", "二十九", "三十", "三十一"};
    char[] jcName0 = new char[]{'建', '除', '满', '平', '定', '执', '破', '危', '成', '收', '开', '闭'};
    char[] jcName1 = new char[]{'闭', '建', '除', '满', '平', '定', '执', '破', '危', '成', '收', '开'};
    char[] jcName2 = new char[]{'开', '闭', '建', '除', '满', '平', '定', '执', '破', '危', '成', '收'};
    char[] jcName3 = new char[]{'收', '开', '闭', '建', '除', '满', '平', '定', '执', '破', '危', '成'};
    char[] jcName4 = new char[]{'成', '收', '开', '闭', '建', '除', '满', '平', '定', '执', '破', '危'};
    char[] jcName5 = new char[]{'危', '成', '收', '开', '闭', '建', '除', '满', '平', '定', '执', '破'};
    char[] jcName6 = new char[]{'破', '危', '成', '收', '开', '闭', '建', '除', '满', '平', '定', '执'};
    char[] jcName7 = new char[]{'执', '破', '危', '成', '收', '开', '闭', '建', '除', '满', '平', '定'};
    char[] jcName8 = new char[]{'定', '执', '破', '危', '成', '收', '开', '闭', '建', '除', '满', '平'};
    char[] jcName9 = new char[]{'平', '定', '执', '破', '危', '成', '收', '开', '闭', '建', '除', '满'};
    char[] jcName10 = new char[]{'满', '平', '定', '执', '破', '危', '成', '收', '开', '闭', '建', '除'};
    char[] jcName11 = new char[]{'除', '满', '平', '定', '执', '破', '危', '成', '收', '开', '闭', '建'};

    /**
     * 国历节日  *表示放假日
     */
    String[] sFtv = new String[]{
            "0101*元旦",
            "0106  中国13亿人口日",
            "0110  中国110宣传日",

            "0202  世界湿地日",
            "0204  世界抗癌症日",
            "0210  世界气象日",
            "0214  情人节",
            "0221  国际母语日",
            "0207  国际声援南非日",

            "0303  全国爱耳日",
            "0308  妇女节",
            "0312  植树节 孙中山逝世纪念日",
            "0315  消费者权益保护日",
            "0321  世界森林日",
            "0322  世界水日",
            "0323  世界气象日",
            "0324  世界防治结核病日",

            "0401  愚人节",
            "0407  世界卫生日",
            "0422  世界地球日",

            "0501*国际劳动节",
            "0504  中国青年节",
            "0505  全国碘缺乏病日",
            "0508  世界红十字日",
            "0512  国际护士节",
            "0515  国际家庭日",
            "0517  世界电信日",
            "0518  国际博物馆日",
            "0519  中国汶川地震哀悼日 全国助残日",
            "0520  全国学生营养日",
            "0522  国际生物多样性日",
            "0523  国际牛奶日",
            "0531  世界无烟日",

            "0601  国际儿童节",
            "0605  世界环境日",
            "0606  全国爱眼日",
            "0617  防治荒漠化和干旱日",
            "0623  国际奥林匹克日",
            "0625  全国土地日",
            "0626  国际反毒品日",

            "0701  建党节 香港回归纪念日",
            "0707  抗日战争纪念日",
            "0711  世界人口日",

            "0801  八一建军节",
            "0815  日本正式宣布无条件投降日",

            "0908  国际扫盲日",
            "0909  **逝世纪念日",
            "0910  教师节",
            "0916  国际臭氧层保护日",
            "0917  国际和平日",
            "0918  九·一八事变纪念日",
            "0920  国际爱牙日",
            "0927  世界旅游日",
            "0928  孔子诞辰",

            "1001*国庆节 国际音乐节 国际老人节",
            "1002  国际减轻自然灾害日",
            "1004  世界动物日",
            "1007  国际住房日",
            "1008  世界视觉日 全国高血压日",
            "1009  世界邮政日",
            "1010  辛亥革命纪念日 世界精神卫生日",
            "1015  国际盲人节",
            "1016  世界粮食节",
            "1017  世界消除贫困日",
            "1022  世界传统医药日",
            "1024  联合国日",
            "1025  人类天花绝迹日",
            "1026  足球诞生日",
            "1031  万圣节",

            "1107  十月社会主义革命纪念日",
            "1108  中国记者日",
            "1109  消防宣传日",
            "1110  世界青年节",
            "1112  孙中山诞辰",
            "1114  世界糖尿病日",
            "1117  国际大学生节",

            "1201  世界艾滋病日",
            "1203  世界残疾人日",
            "1209  世界足球日",
            "1210  世界人权日",
            "1212  西安事变纪念日",
            "1213  南京大屠杀",
            "1220  澳门回归纪念日",
            "1221  国际篮球日",
            "1224  平安夜",
            "1225  圣诞节 世界强化免疫日",
            "1226  **诞辰"};

    /**
     * 农历节日  *表示放假日
     */
    String[] lFtv = new String[]{
            "0101*春节",
            "0102*大年初二",
            "0103*大年初三",
            "0104*大年初四",
            "0105*大年初五",
            "0106*大年初六",
            "0107*大年初七",
            "0105  路神生日",
            "0115  元宵节",
            "0202  龙抬头",
            "0219  观世音圣诞",
            "0404  寒食节",
            "0408  佛诞节 ",
            "0505*端午节",
            "0606  天贶节 姑姑节",
            "0624  彝族火把节",
            "0707  七夕情人节",
            "0714  鬼节(南方)",
            "0715  盂兰节",
            "0730  地藏节",
            "0815*中秋节",
            "0909  重阳节",
            "1001  祭祖节",
            "1117  阿弥陀佛圣诞",
            "1208  腊八节 释迦如来成道日",
            "1223  过小年",
            "1229*腊月二十九",
            "0100*除夕"};

    /**
     * 获取日历详细信息
     *
     * @param date date
     * @return Element
     * @throws ParseException 解析异常
     */
    public static Element getCalendarDetail(Date date) throws ParseException {
        return getCalendarDetail(date, false);
    }

    public static Element getCalendarDetail(Date date, boolean useCache) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        String cacheKey = (year + "-" + month);
        SimpleCalendar lunarCalendarUtil;
        if (useCache) {
            lunarCalendarUtil = cache.get(cacheKey);
        } else {
            lunarCalendarUtil = new SimpleCalendar();
            lunarCalendarUtil.calendar(year, month);
            cache.put(cacheKey, lunarCalendarUtil);
        }

        return lunarCalendarUtil.getElements().get(cal.get(Calendar.DATE) - 1);
    }

    @SuppressWarnings("all")
    public void calendar(int y, int m) throws ParseException {
        Date sdObj;
        Lunar ldObj;
        Boolean lL = null;
        long lD2;
        int lD = 1;
        int lX = 0;
        int tmp1;
        int tmp2;
        int lM2;
        Integer lY = null, lM = null, lY2 = null;
        String cY, cM, cD; // 年柱,月柱,日柱
        Integer[] ldpos = new Integer[3];
        int n = 0;
        int firstLm = 0;
        String dateString = y + "-" + (m + 1) + "-" + 1;
        sdObj = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);

        // 公历当月天数
        long length = solarDays(y, m);
        // 公历当月1日星期几
        int firstWeek = DateUtil.toLocalDate(sdObj).getDayOfMonth();

        ////////年柱 1900年立春后为庚子年(60进制36)
        if (m >= KuugaConstants.TWO) {
            cY = cyclical(y - 1900 + 36);
        } else {
            cY = cyclical(y - 1900 + 36 - 1);
        }
        // 立春日期
        int term2 = sTerm(y, 2);

        ////////月柱 1900年1月小寒以前为 丙子月(60进制12)

        // 返回当月「节」为几日开始
        int firstNode = sTerm(y, m * 2);
        cM = cyclical((y - 1900) * 12L + m + 12);

        lM2 = (y - 1900) * 12 + m + 12;
        // 当月一日与 1900/1/1 相差天数
        // 1900/1/1与 1970/1/1 相差25567日, 1900/1/1 日柱为甲戌日(60进制10)
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        df2.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = df2.parse(StringUtil.EMPTY + y + "-" + (m + 1) + "-" + 1 + " 00:00:00");

        long dayCyclical = date.getTime() / 86400000 + 25567 + 10;
        //// long dayCyclical =date.getTime() / 86400000 + 25567 + 10;
        SimpleDateFormat df3 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        for (int i = 0; i < length; i++) {
            if (i == 18) {
                int b = 5;
            }
            if (lD > lX) {
                // 当月一日日期
                sdObj = df3.parse(StringUtil.EMPTY + y + "-" + (m + 1) + "-" + (i + 1) + " 00:00:00");
                // 农历
                ldObj = new Lunar(sdObj);
                // 农历年
                lY = ldObj.year;
                // 农历月
                lM = ldObj.month;
                // 农历日
                lD = ldObj.day;
                // 农历是否闰月
                lL = ldObj.isLeap;
                // 农历当月最后一天
                lX = lL ? leapDays(lY) : monthDays(lY, lM);

                if (n != 0) {
                    System.out.print(StringUtil.EMPTY);
                } else {
                    firstLm = lM;
                }

                ldpos[n++] = i - lD + 1;
            }

            // 依节气调整二月分的年柱, 以立春为界
            if (m == 1 && (i + 1) == term2) {
                cY = cyclical(y - 1900 + 36);
                lY2 = (y - 1900 + 36);
            }
            // 依节气月柱, 以「节」为界
            if ((i + 1) == firstNode) {
                cM = cyclical((y - 1900) * 12L + m + 13);
                lM2 = (y - 1900) * 12 + m + 13;
            }
            // 日柱
            cD = cyclical(dayCyclical + i);
            lD2 = (dayCyclical + i);
            Element element = new Element(y, m + 1, i + 1, (nStr1[(i + firstWeek) % 7]),
                    lY, lM, lD++, lL,
                    cY, cM, cD);
            element.setCDay(cDay(element.getLDay()));
            int paramterLy2 = lY2 == null ? -1 : (lY2 % 12);
            int paramterLm2 = lM2 % 12;
            long paramterLd2 = lD2 % 12;
            int paramterLy2b = lY2 == null ? -1 : lY2 % 10;
            int paramterLy2c = (int) (lD2 % 10);
            int paramterLld = lD - 1;
            element.setSgz5(calConv2(paramterLy2, paramterLm2, (int) paramterLd2, paramterLy2b, paramterLy2c, lM, paramterLld));
            element.setSgz3(cyclical6(lM2 % 12, (int) ((lD2) % 12)));
            elements.add(element);
        }

        // 节气
        tmp1 = sTerm(y, m * 2) - 1;
        tmp2 = sTerm(y, m * 2 + 1) - 1;
        elements.get(tmp1).solarTerms = solarTerm[m * 2];
        elements.get(tmp2).solarTerms = solarTerm[m * 2 + 1];
        if (m != 3) {
            System.out.print(StringUtil.EMPTY);
        } else {
            elements.get(tmp1).color = "red"; // 清明颜色
        }

        Pattern p = COMPILE;
        // 国历节日
        for (String i : sFtv) {
            Matcher matcher = p.matcher(i);
            if (matcher.matches()) {
                if ("1212  西安事变纪念日".equals(i)) {
                    int j = 2;
                }
                if (Integer.parseInt(matcher.group(1)) == (m + 1)) {
                    elements.get(Integer.parseInt(matcher.group(2)) - 1).solarFestival += matcher.group(4) + StringUtil.EMPTY;
                }
            }
        }

        p = COMPILE1;
        // 农历节日
        for (String i : lFtv) {
            Matcher matcher = p.matcher(i);
            if (matcher.matches()) {
                tmp1 = Integer.parseInt(matcher.group(1)) - firstLm;
                if (tmp1 != -11) {
                    System.out.print(StringUtil.EMPTY);
                } else {
                    tmp1 = 1;
                }
                if (tmp1 >= 0 && tmp1 < n) {
                    tmp2 = ldpos[tmp1] + Integer.parseInt(matcher.group(2)) - 1;
                    if (tmp2 >= 0 && tmp2 < length) {
                        elements.get(tmp2).lunarFestival += matcher.group(4);
                        if (!"*".equals(matcher.group(3))) {
                            continue;
                        }
                        elements.get(tmp2).color = "red";
                    }
                }
            }
        }

        // 复活节只出现在3或4月
        if (m == KuugaConstants.TWO || m == KuugaConstants.THREE) {
            Easter estDay = new Easter(y);
            if (m == estDay.m) {
                elements.get(estDay.d - 1).solarFestival = elements.get(estDay.d - 1).solarFestival + " 复活节(Easter Sunday)";
            }
        }


        // 黑色星期五
        if ((firstWeek + KuugaConstants.TWELVE) % KuugaConstants.SEVEN == KuugaConstants.FIVE) {
            elements.get(12).solarFestival += "黑色星期五";
        }

        // 今日
        // if (y == tY && m == tM) this[tD - 1].isToday = true;
    }

    /**
     * 返回公历 y年某m+1月的天数
     *
     * @param y year
     * @param m month
     * @return 天数
     */
    public long solarDays(int y, int m) {
        if (m == 1) {
            boolean b = (y % 4 == 0) && (y % 100 != 0) || (y % 400 == 0);
            return b ? 29 : 28;
        } else {
            return (solarMonth[m]);
        }
    }

    /**
     * 返回阴历 (y年,m+1月)
     *
     * @param num  num
     * @param num2 num2
     * @return char
     */
    @SuppressWarnings("all")
    private char cyclical6(int num, int num2) {
        if (num == 0) {
            return (jcName0[num2]);
        }
        if (num == 1) {
            return (jcName1[num2]);
        }
        if (num == 2) {
            return (jcName2[num2]);
        }
        if (num == 3) {
            return (jcName3[num2]);
        }
        if (num == 4) {
            return (jcName4[num2]);
        }
        if (num == 5) {
            return (jcName5[num2]);
        }
        if (num == 6) {
            return (jcName6[num2]);
        }
        if (num == 7) {
            return (jcName7[num2]);
        }
        if (num == 8) {
            return (jcName8[num2]);
        }
        if (num == 9) {
            return (jcName9[num2]);
        }
        if (num == 10) {
            return (jcName10[num2]);
        }
        if (num == 11) {
            return (jcName11[num2]);
        }
        return '0';
    }

    @SuppressWarnings("all")
    private String calConv2(int yy, int mm, int dd, int y, int d, int m, int dt) {
        int dy = d + dd;
        if ((yy == 0 && dd == 6) || (yy == 6 && dd == 0) || (yy == 1 && dd == 7) || (yy == 7 && dd == 1) || (yy == 2 && dd == 8) || (yy == 8 && dd == 2) || (yy == 3 && dd == 9) || (yy == 9 && dd == 3) || (yy == 4 && dd == 10) || (yy == 10 && dd == 4) || (yy == 5 && dd == 11) || (yy == 11 && dd == 5)) {
            return "<FONT color=#0000A0>日值岁破 大事不宜</font>";
        } else if ((mm == 0 && dd == 6) || (mm == 6 && dd == 0) || (mm == 1 && dd == 7) || (mm == 7 && dd == 1) || (mm == 2 && dd == 8) || (mm == 8 && dd == 2) || (mm == 3 && dd == 9) || (mm == 9 && dd == 3) || (mm == 4 && dd == 10) || (mm == 10 && dd == 4) || (mm == 5 && dd == 11) || (mm == 11 && dd == 5)) {
            return "<FONT color=#0000A0>日值月破 大事不宜</font>";
        } else if ((y == 0 && dy == 911) || (y == 1 && dy == 55) || (y == 2 && dy == 111) || (y == 3 && dy == 75) || (y == 4 && dy == 311) || (y == 5 && dy == 9) || (y == 6 && dy == 511) || (y == 7 && dy == 15) || (y == 8 && dy == 711) || (y == 9 && dy == 35)) {
            return "<FONT color=#0000A0>日值上朔 大事不宜</font>";
        } else if ((m == 1 && dt == 13) || (m == 2 && dt == 11) || (m == 3 && dt == 9) || (m == 4 && dt == 7) || (m == 5 && dt == 5) || (m == 6 && dt == 3) || (m == 7 && dt == 1) || (m == 7 && dt == 29) || (m == 8 && dt == 27) || (m == 9 && dt == 25) || (m == 10 && dt == 23) || (m == 11 && dt == 21) || (m == 12 && dt == 19)) {
            return "<FONT color=#0000A0>日值杨公十三忌 大事不宜</font>";
        } else {
            return "0";
        }
    }

    /**
     * 传入 offset new Date 返回干支, 0=甲子
     *
     * @param num num
     * @return string
     */
    public String cyclical(long num) {
        return (gan[(int) (num % 10)] + zhi[(int) (num % 12)]);
    }

    /**
     * 数字日期转中文日期
     *
     * @param day 数字日期
     * @return 中文日期
     */
    public String cDay(int day) {
        String s;

        return switch (day) {
            case 10 -> "初十";
            case 20 -> "二十";
            case 30 -> "三十";
            default -> {
                s = nStr2[Double.valueOf(Math.floor(Double.parseDouble((day / 10) + StringUtil.EMPTY))).intValue()];
                s += nStr1[day % 10];
                yield s;
            }
        };
    }

    /**
     * 返回农历y年闰哪个月1-12，没闰返回0
     *
     * @param year 年
     * @return 闰月
     */
    public int leapMonth(int year) {
        long lm = LUNAR_INFO[year - 1900] & 0xf;
        return Integer.parseInt((lm == 0xf ? 0 : lm) + StringUtil.EMPTY);
    }

    /**
     * 某年的第n个节气为几日(从0小寒起算)
     *
     * @param year   年
     * @param number 第几个节气
     * @return 第几日
     * @throws ParseException ParseException
     */
    public int sTerm(int year, int number) throws ParseException {
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df2.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = df2.parse("1900-01-06 02:05:00");
        long utcTime2 = date.getTime();
        BigDecimal time2 = new BigDecimal("31556925974.7").multiply(new BigDecimal(year - 1900)).add(new BigDecimal(sTermInfo[number]).multiply(BigDecimal.valueOf(60000L)));
        BigDecimal time = time2.add(BigDecimal.valueOf(utcTime2));
        Date offDate = new Date(time.longValue());
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
        cal.setTime(offDate);
        // 日期从0算起
        return cal.get(Calendar.DATE);
    }

    /**
     * 返回农历year年的总天数(农历维度一年的总天数)
     *
     * @param year 年
     * @return lYearDays
     */
    public int lYearDays(int year) {
        long i, sum = 348;
        int i1 = 0x8000;
        int i2 = 0x8;
        for (i = i1; i > i2; i >>= 1) {
            sum += (LUNAR_INFO[year - 1900] & i) != 0 ? 1 : 0;
        }
        return Integer.parseInt((sum + leapDays(year)) + StringUtil.EMPTY);
    }

    /**
     * 获取闰年闰月内的总天数，如果year不是闰年则返回0
     *
     * @param year 年
     * @return leapDays
     */
    public int leapDays(int year) {
        if (leapMonth(year) != 0) {
            return ((LUNAR_INFO[year - 1899] & 0xf) == 0xf ? 30 : 29);
        } else {
            return 0;
        }
    }

    /**
     * 返回农历year年month月的总天数
     *
     * @param year  年
     * @param month 月
     * @return monthDays
     */
    private int monthDays(int year, int month) {
        return ((LUNAR_INFO[year - 1900] & (0x10000 >> month)) != 0 ? 30 : 29);
    }

    /**
     * 农历
     */
    public class Lunar {
        private final int year;
        private boolean isLeap;
        private final int month;
        @Getter
        public int leapMonth;
        private final int day;

        public Lunar(Date objDate) throws ParseException {
            int i, leap, temp = 0;
            SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            df2.setTimeZone(TimeZone.getTimeZone("UTC"));
            SimpleDateFormat dtFmt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date date = df2.parse(dtFmt.format(objDate));
            SimpleDateFormat df3 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            df3.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date3 = df3.parse(StringUtil.EMPTY + 1900 + "-" + 1 + "-" + 31 + " 00:00:00");
            long time1 = date.getTime();
            long time2 = date3.getTime();
            int offset = (int) ((time1 - time2) / 86400000);
            int i1 = 1900;
            int i2 = 2100;
            for (i = i1; i < i2 && offset > 0; i++) {
                temp = lYearDays(i);
                offset -= temp;
            }

            if (offset < 0) {
                offset += temp;
                i--;
            }

            this.year = i;
            leap = leapMonth(i); // 闰哪个月
            leapMonth = leap;
            this.isLeap = DateUtil.isLeapYear(year);

            int i3 = 13;
            for (i = 1; i < i3 && offset > 0; i++) {
                // 闰月
                if (leap > 0 && i == (leap + 1) && !this.isLeap) {
                    --i;
                    this.isLeap = true;
                    temp = leapDays(this.year);
                } else {
                    temp = monthDays(this.year, i);
                }

                // 解除闰月
                if (!this.isLeap || i != (leap + 1)) {
                    System.out.print(StringUtil.EMPTY);
                } else {
                    this.isLeap = false;
                }

                offset -= temp;
            }

            if (offset != 0 || leap <= 0 || i != leap + 1) {
                System.out.print(StringUtil.EMPTY);
            } else {
                if (this.isLeap) {
                    this.isLeap = false;
                } else {
                    this.isLeap = true;
                    --i;
                }
            }

            if (offset < 0) {
                offset += temp;
                --i;
            }
            this.month = i;
            this.day = offset + 1;
        }
    }

    public class Easter {

        public int m;
        public int d;

        public Easter(int y) throws ParseException {
            int term2 = sTerm(y, 5); // 取得春分日期
            SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            df2.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date dayTerm2 = df2.parse(StringUtil.EMPTY + y + "-" + 3 + "-" + term2 + " 00:00:00");// 取得春分的公历日期控件(春分一定出现在3月)
            Lunar lDayTerm2 = new Lunar(dayTerm2); // 取得取得春分农历
            int lmlen;
            int i = 15;
            if (lDayTerm2.day >= i) // 取得下个月圆的相差天数
            {
                lmlen = (lDayTerm2.isLeap ? leapDays(y) : monthDays(y, lDayTerm2.month)) - lDayTerm2.day + 15;
            } else {
                lmlen = 15 - lDayTerm2.day;
            }

            // 一天等于 1000*60*60*24 = 86400000 毫秒
            Date l15 = new Date(dayTerm2.getTime() + 86400000L * lmlen); // 求出第一次月圆为公历几日
            Date dayEaster = new Date(l15.getTime() + 86400000 * (7 - DateUtil.toLocalDate(l15).getDayOfMonth())); // 求出下个周日

            LocalDate dayEasterByLocalDate = DateUtil.toLocalDate(dayEaster);
            this.m = dayEasterByLocalDate.getMonthValue();
            this.d = dayEasterByLocalDate.getDayOfMonth();
        }
    }

    @Data
    @Getter
    @Setter
    public class Element {
        // 阳历年生肖
        public String animalYear;
        // 阳历年
        public int sYear;
        // 阳历月
        public int sMonth;
        // 阳历日
        public int sDay;
        // 周几
        public char week;
        // 农历年
        public int lYear;
        // 农历月
        public int lMonth;
        // 农历日
        public int lDay;
        // 农历月大写
        public String lMonthChinese;
        // 农历日大写
        public String lDayChinese;
        // 是否闰年
        public boolean isLeap;
        // 几月是闰月
        public int leapMonth;
        /*
         * 农历干支纪年
         * （“辛丑年就是农历一甲子中的一个，比如1901、1961、2021（60年一周期）。所谓农历的干支纪年，就字面意义来说，就相当于树干和枝叶。
         * 我国古代以天为主，以地为从，天和干相连叫天干，地和支相连叫地支，合起来叫天干地支，简称干支。”）
         */
        public String cYear;
        // 农历干支纪月
        public String cMonth;
        // 农历干支纪日
        public String cDay;
        // 颜色
        public String color;
        // 是否今天
        public boolean isToday;
        // 农历节日
        public String lunarFestival;
        // 阳历节日
        public String solarFestival;
        // 节气
        public String solarTerms;
        // 宜忌事项
        public String sgz5;
        public char sgz3;

        /**
         * 传回农历 y年的生肖
         *
         * @return 生肖
         */
        final public String animalsYear() {
            return animals[(sYear - 4) % 12];
        }

        public Element(int sYear, int sMonth, int sDay, char week, int lYear, int lMonth, int lDay, boolean isLeap, String cYear, String cMonth, String cDay) {
            this.isToday = DateUtil.equalsDay(DateUtil.of(sYear, sMonth, sDay), DateUtil.now());
            // 瓣句
            this.sYear = sYear;   // 公元年4位数字
            this.animalYear = animalsYear();
            this.sMonth = sMonth;  // 公元月数字
            this.sDay = sDay;    // 公元日数字
            this.week = week;    // 星期, 1个中文
            // 农历
            this.lYear = lYear;   // 公元年4位数字
            this.lMonth = lMonth;  // 农历月数字
            this.lDay = lDay;    // 农历日数字
            this.isLeap = isLeap;  // 是否为农历闰月?
            try {
                this.leapMonth = new Lunar(DateUtil.of(sYear, sMonth, sDay)).getLeapMonth();
            } catch (ParseException e) {
                log.error("SimpleCalendar init parse error:{}", e.getMessage(), e);
            }
            // 中文
            this.lMonthChinese = monthChinese[lMonth - 1];
            this.lDayChinese = dayChinese[lDay - 1];
            // 八字
            this.cYear = cYear;   // 年柱, 2个中文
            this.cMonth = cMonth;  // 月柱, 2个中文
            this.cDay = cDay;    // 日柱, 2个中文

            this.color = StringUtil.EMPTY;

            this.lunarFestival = StringUtil.EMPTY; // 农历节日
            this.solarFestival = StringUtil.EMPTY; // 公历节日
            this.solarTerms = StringUtil.EMPTY; // 节气
        }

    }

}
