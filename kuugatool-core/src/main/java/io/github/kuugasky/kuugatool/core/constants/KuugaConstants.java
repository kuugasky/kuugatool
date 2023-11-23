package io.github.kuugasky.kuugatool.core.constants;

import io.github.kuugasky.kuugatool.core.string.StringUtil;

/**
 * 工具类常量
 *
 * @author kuuga
 * @since 2023-11-22
 */
public interface KuugaConstants {

    //==================================== 系统环境 ====================================

    String PRO = "pro";
    String DEV = "dev";
    String PRE = "pre";
    String RC = "rc";

    //==================================== 系统常量 ====================================

    String IMEI_CODE = "imeicode";

    //==================================== 常规常量 ====================================

    String PERSON_DEFAULT_PSW = "123456";

    String REFERER = "Referer";

    //==================================== 符号 ====================================

    String POINT = ".";

    String COMMA = "，";

    String SEMICOLON = "；";

    String EN_SEMICOLON = ";";

    String EQUAL_SIGN = "=";

    String WELL = "#";

    String SINGLE_WITH = "&";

    String DOUBLE_WITH = "&&";

    String SINGLE_OR = "|";

    String DOUBLE_OR = "||";

    String BACKQUOTE = "`";

    /**
     * 破折号【中文】
     */
    String DASH = "——";

    /**
     * 连接号【英文】
     */
    String HYPHEN = "-";

    /**
     * 下划线【英文】
     */
    String UNDERLINE = "_";

    String SLASH = "/";

    String BACKSLASH = "\\";

    /**
     * 字符串常量：空格符 {@code " "}
     */
    String SPACE = " ";

    String QUESTION_MARK = "?";

    String LINE_SEPARATOR = "\r\n";

    /**
     * 字符串常量：空字符串 {@link  StringUtil#EMPTY}
     */
    String EMPTY = StringUtil.EMPTY;

    /**
     * 字符串常量：{@code "null"} <br>
     * 注意：{@code "null" != null}
     */
    String NULL = "null";
    String UNDEFINED = "undefined";
    String UNCHECKED = "unchecked";
    String UNKNOWN = "unknown";
    String ALL = "all";

    /**
     * 英文逗号
     */
    String COMMAENG = ",";

    /**
     * 冒号
     */
    String COLON = ":";

    /**
     * 感叹号
     */
    String EXCLAMATION = "!";

    String EMPTY_JSON = "{}";

    String EMPTY_ARRAY = "[]";

    String RW = "rw";

    //==================================== 时间常量 ====================================

    int ONE_MINUTE = 60;
    int TWO_MINUTE = 2 * 60;
    int FIVE_MINUTE = 5 * 60;
    int FIFTEEN_MINUTE = 15 * 60;
    int THIRTY_MINUTE = 30 * 60;
    int ONE_HOUR = 60 * 60;
    int ONE_DAY_SECONDS = 24 * 60 * 60;
    int FIVE_DAY_SECONDS = 5 * ONE_DAY_SECONDS;

    //==================================== 常用数值,替代魔术数 ====================================

    int ZERO = 0;
    int ONE = 1;
    int TWO = 2;
    int THREE = 3;
    int FOUR = 4;
    int FIVE = 5;
    int SIX = 6;
    int SEVEN = 7;
    int EIGHT = 8;
    int NINE = 9;
    int TEN = 10;
    int ELEVEN = 11;
    int TWELVE = 12;
    int THIRTEEN = 13;
    int FOURTEEN = 14;
    int FIFTEEN = 15;
    int SIXTEEN = 16;
    int SEVENTEEN = 17;
    int EIGHTEEN = 18;
    int NINETEEN = 19;
    int TWENTY = 20;
    int THIRTY = 30;
    int FIFTY = 50;
    int SIXTY = 60;
    int NINETY_NINE = 99;
    int ONE_HUNDRED = 100;
    int TWO_HUNDRED = 200;
    int TWO_THOUSAND = 2_000;

}
