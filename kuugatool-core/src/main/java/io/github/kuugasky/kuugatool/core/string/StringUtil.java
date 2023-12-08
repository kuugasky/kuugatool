package io.github.kuugasky.kuugatool.core.string;

import io.github.kuugasky.kuugatool.core.collection.*;
import io.github.kuugasky.kuugatool.core.constants.KuugaConstants;
import io.github.kuugasky.kuugatool.core.function.Matcher;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 字符串处理工具类
 *
 * @author kuuga
 */
public final class StringUtil {

    /**
     * 定义私有构造函数来屏蔽这个隐式公有构造函数
     */
    private StringUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    // static final ========================================================================================================================

    private static final int INDEX_NOT_FOUND = -1;
    /**
     * The constant ZERO_NINE_REGEX.
     */
    public static final String ZERO_NINE_REGEX = "[^0-9]";
    /**
     * The constant EMPTY.
     */
    public static final String EMPTY = "";

    // empty or null ========================================================================================================================

    /**
     * 空值处理
     *
     * @param value        value
     * @param defaultValue defaultValue
     * @return String string
     */
    public static String ifNull(Object value, String defaultValue) {
        boolean isNull = value == null
                || KuugaConstants.EMPTY.equals(toString(value))
                || KuugaConstants.NULL.equalsIgnoreCase(toString(value).trim())
                || KuugaConstants.UNDEFINED.equals(value.toString())
                || toString(value).trim().isEmpty();
        if (isNull) {
            return defaultValue;
        } else {
            return toString(value);
        }
    }

    /**
     * 判断对象是否为空
     *
     * @param obj 待判断对象
     * @return boolean true-空(null/空字符串/纯空白字符),false-非空
     */
    public static boolean isEmpty(Object obj) {
        return obj == null || obj.toString().trim().replaceAll("\\s", EMPTY).isEmpty();
    }

    /**
     * 判断对象数组是否都为空
     *
     * @param objs 待判断对象数组
     * @return boolean true-空(null/空字符串/纯空白字符),false-非空
     */
    public static boolean isEmpty(Object... objs) {
        if (ArrayUtil.isEmpty(objs)) {
            return true;
        }
        return Arrays.stream(objs).allMatch(StringUtil::isEmpty);
    }

    /**
     * 判断对象数组是否为空
     *
     * @param objects 待判断对象
     * @return boolean true-空(null/空字符串/纯空白字符),false-非空
     */
    public static boolean containsEmpty(Object... objects) {
        if (null == objects) {
            return false;
        }
        for (Object object : objects) {
            if (isEmpty(object)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符串是否包含可见字符(与{@link #isEmpty(Object)}方法相反)
     *
     * @param obj 待判断对象
     * @return boolean true-有内容,false-空(null/空字符串/纯空白字符)
     */
    public static boolean hasText(Object obj) {
        return !isEmpty(obj);
    }

    /**
     * 判断入参是否都包含可见字符，只要有一个服务isEmpty都返回false
     * 如果需要判断多个入参是否含一个或多个空值，可使用containsEmpty进行判断
     *
     * @param objects 可变数量对象入参
     * @return boolean
     */
    public static boolean hasText(Object... objects) {
        if (null == objects || objects.length == 0) {
            return false;
        }
        for (Object object : objects) {
            if (isEmpty(object)) {
                return false;
            }
        }
        return true;
    }

    // toString ========================================================================================================================

    /**
     * 转换为字符串
     *
     * @param obj 待转换对象
     * @return String null-空字符串;
     */
    public static String toString(Object obj) {
        return null == obj ? null : obj.toString();
    }

    // format ========================================================================================================================

    /**
     * 格式化对象输出
     *
     * @param obj 待格式化对象
     * @return String null-空字符串;
     */
    public static String formatString(Object obj) {
        StringBuilder stringBuilder = new StringBuilder();
        if (isEmpty(obj)) {
            return EMPTY;
        }
        boolean isStringType = obj instanceof String;
        boolean isEnumType = obj instanceof Enum;
        if (isStringType || isEnumType) {
            return obj.toString();
        }
        if (obj instanceof Collection) {
            List<Object> objList;
            if (obj instanceof Set) {
                objList = new ArrayList<>(ObjectUtil.cast(obj));
            } else {
                objList = ObjectUtil.cast(obj);
            }
            ListUtil.optimize(objList)
                    .stream()
                    .filter(Objects::nonNull)
                    .forEach(o -> stringBuilder.append(formatString(o)));
            return stringBuilder.toString();
        }
        if (obj instanceof Iterable) {
            Iterable<Object> iterable = ObjectUtil.cast(obj);
            iterable.forEach(o -> stringBuilder.append(formatString(o)));
            return stringBuilder.toString();
        }
        if (obj instanceof Iterator<?>) {
            Iterator<Object> iterator = ObjectUtil.cast(obj);
            while (iterator.hasNext()) {
                stringBuilder.append(formatString(iterator.next()));
            }
            return stringBuilder.toString();
        }
        if (obj instanceof Map) {
            Map<Object, Object> objMap = ObjectUtil.cast(obj);
            return MapUtil.toString(objMap, true);
        }
        try {
            return ToStringBuilder.reflectionToString(obj, ToStringStyle.MULTI_LINE_STYLE);
        } catch (Exception e) {
            return obj.toString();
        }
    }

    /**
     * 格式化字符串
     *
     * @param tpl    模板,参数占位符为{}, 如 "My name is {}, i'm {} years old."
     * @param params 参数
     * @return 格式化后的字符串 string
     */
    public static String format(String tpl, Object... params) {
        return isEmpty(tpl) ? tpl : String.format(tpl.replaceAll("\\{}", "%s"), params);
    }

    /**
     * 格式化%s字符串
     *
     * @param tpl    模板,参数占位符为%s, 如 "My name is %s, I'm %s years old."
     * @param params 参数
     * @return 格式化后的字符串 string
     */
    public static String formatPercentSign(String tpl, Object... params) {
        return isEmpty(tpl) ? tpl : String.format(tpl, params);
    }

    /**
     * 格式化占位符
     *
     * @param context 文本
     * @param param   占位符映射map
     * @return 格式化后的文本
     */
    public static String formatPlaceHolder(String context, Map<String, String> param) {
        if (param != null && !param.isEmpty()) {
            for (Map.Entry<String, String> entry : param.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                String regex = "\\{%s\\}".formatted(key);
                context = context.replaceAll(regex, value);
            }
        }
        return context;
    }

    // trim strip stripLeading ========================================================================================================================

    /**
     * 返回一个对象的字符串
     * 去掉\t\r\n和半角空格，但是不能去掉全角空格和中间空格
     *
     * @param obj 对象
     * @return 字符串 string
     */
    public static String trim(Object obj) {
        return null == obj ? EMPTY : String.valueOf(obj).trim();
    }

    /**
     * 给定字符串数组全部做去首尾空格
     *
     * @param strArray 字符串数组
     */
    public static void trim(String[] strArray) {
        if (null == strArray) {
            return;
        }
        String str;
        for (int i = 0; i < strArray.length; i++) {
            str = strArray[i];
            if (null != str) {
                strArray[i] = trim(str);
            }
        }
    }

    /**
     * 返回一个对象的字符串
     * 去掉\t\r\n和半角空格和全角空格，但是不能去掉中间空格
     *
     * @param obj 对象
     * @return 字符串 string
     */
    public static String strip(Object obj) {
        return null == obj ? EMPTY : String.valueOf(obj).strip();
    }

    /**
     * 返回一个对象的字符串
     * 去掉前面空格，包括\t\r\n和全角空格和半角空格
     *
     * @param obj 对象
     * @return 字符串 string
     */
    public static String stripLeading(Object obj) {
        return obj == null ? EMPTY : String.valueOf(obj).stripLeading();
    }

    /**
     * 返回一个对象的字符串
     * 去掉后面空格，包括\t\r\n和全角空格和半角空格
     *
     * @param obj 对象
     * @return 字符串 string
     */
    public static String stripTrailing(Object obj) {
        return obj == null ? EMPTY : String.valueOf(obj).stripTrailing();
    }

    /**
     * 除去字符串头尾部的空白，如果字符串是{@code null}或者""，返回{@code null}。
     *
     * <pre>
     * StrUtil.trimToNull(null)          = null
     * StrUtil.trimToNull("")            = null
     * StrUtil.trimToNull("     ")       = null
     * StrUtil.trimToNull("abc")         = "abc"
     * StrUtil.trimToEmpty("    abc    ") = "abc"
     * </pre>
     *
     * @param str 字符串
     * @return 去除两边空白符后的字符串, 如果为空返回null
     * @since 3.2.1
     */
    public static String trimToNull(CharSequence str) {
        final String trimStr = trim(str);
        return EMPTY.equals(trimStr) ? null : trimStr;
    }

    /**
     * 简化空格
     * 移除换行符、换位符、首尾空格，以及多空格转单空格
     *
     * @param source 字符串
     * @return String string
     */
    public static String simplifySpace(String source) {
        if (isEmpty(source)) {
            return source;
        }
        return source.trim().replaceAll("\n", EMPTY).replaceAll("\t", EMPTY).replaceAll(" +", KuugaConstants.SPACE);
    }

    // 移除空格 ============================================================================================================================================

    /**
     * 移除所有空格
     *
     * @param str 文本
     * @return str string
     */
    public static String removeAllSpace(String str) {
        return removeAllSpace(str, false);
    }

    /**
     * 移除所有空格
     *
     * @param str                     文本
     * @param containsSpacesInBetween 包含中间的空格
     * @return str string
     */
    public static String removeAllSpace(String str, boolean containsSpacesInBetween) {
        if (isEmpty(str(str))) {
            return str;
        }
        str = strip(str).replaceAll("\r", EMPTY).replaceAll("\n", EMPTY).replaceAll("\t", EMPTY);
        if (containsSpacesInBetween) {
            /*
             * str.replace(" ", ""); 去掉所有空格，包括首尾、中间
             * str.replaceAll(" +",""); 去掉所有空格
             * str.replaceAll("\\s*", "");  可以替换大部分空白字符， 不限于空格 \s 可以匹配空格、制表符、换页符等空白字符的其中任意一个
             */
            str = isEmpty(str) ? str : str.replaceAll("\\s*", EMPTY);
        }
        return str;
    }

    /**
     * 移除字符串中所有给定字符串<br>
     * 例：removeAll("aa-bb-cc-dd", "-") -> aabbccdd
     *
     * @param str         字符串
     * @param strToRemove 被移除的字符串
     * @return 移除后的字符串 string
     */
    public static String removeAll(CharSequence str, CharSequence strToRemove) {
        // strToRemove如果为空， 也不用继续后面的逻辑
        if (isEmpty(str) || isEmpty(strToRemove)) {
            return toString(str);
        }
        return str.toString().replace(strToRemove, EMPTY);
    }

    /**
     * 去除字符串中指定的多个字符，如有多个则全部去除
     *
     * @param str   字符串
     * @param chars 字符列表
     * @return 去除后的字符 string
     * @since 4.2.2
     */
    public static String removeAll(CharSequence str, char... chars) {
        if (null == str || ArrayUtil.isEmpty(chars)) {
            return toString(str);
        }
        final int len = str.length();
        if (0 == len) {
            return toString(str);
        }
        final StringBuilder builder = new StringBuilder(len);
        char c;
        for (int i = 0; i < len; i++) {
            c = str.charAt(i);
            if (!ArrayUtil.contains(chars, c)) {
                builder.append(c);
            }
        }
        return builder.toString();
    }

    // to collect ========================================================================================================================

    /**
     * 转为String集合
     *
     * @param obj       字符串
     * @param separator 分隔符
     * @return String集合 list
     */
    public static List<String> toList(String obj, String separator) {
        if (isEmpty(obj)) {
            return ListUtil.emptyList();
        }

        String[] strSplit = trim(obj).split(separator);

        return Arrays.stream(strSplit)
                .filter(StringUtil::hasText)
                .collect(Collectors.toList());
    }

    /**
     * 转为String集合(分隔符为",")
     *
     * @param obj 字符串
     * @return String集合 list
     */
    public static List<String> toList(String obj) {
        return toList(obj, KuugaConstants.COMMAENG);
    }

    /**
     * 转为String数组
     *
     * @param str       字符串
     * @param separator 分隔符
     * @return String数组 string []
     */
    public static String[] toArray(String str, String separator) {
        if (isEmpty(str)) {
            return new String[0];
        }
        return str.split(separator);
    }

    /**
     * 转为String数组(分隔符为",")
     *
     * @param obj 字符串
     * @return String数组 string [ ]
     */
    public static String[] toArray(String obj) {
        return toArray(obj, KuugaConstants.COMMAENG);
    }

    // case ========================================================================================================================

    /**
     * 转为小写字符串
     *
     * @param obj 字符串
     * @return 小写字符串 string
     */
    public static String toLowerCase(Object obj) {
        return isEmpty(obj) ? EMPTY : obj.toString().toLowerCase();
    }

    /**
     * 转为大写字符串
     *
     * @param obj 字符串
     * @return 大写字符串 string
     */
    public static String toUpperCase(Object obj) {
        return isEmpty(obj) ? EMPTY : obj.toString().toUpperCase();
    }

    /**
     * 字符串转大写方法
     *
     * @param str                  待转化字符串
     * @param camelCaseToUnderLine 是否将驼峰转化成下划线
     * @return 大写字符串 string
     */
    public static String toUpperCase(String str, boolean camelCaseToUnderLine) {
        if (camelCaseToUnderLine) {
            boolean firstChar = true;
            StringBuilder sb = new StringBuilder();
            char[] chars = str.toCharArray();
            for (char c : chars) {
                if (Character.isUpperCase(c) && !firstChar) {
                    sb.append("_");
                }
                sb.append(c);
                firstChar = false;
            }
            return sb.toString().toUpperCase();
        } else {
            return str.toUpperCase();
        }
    }

    /**
     * 把字符串的首字母小写
     *
     * @param str str
     * @return str string
     */
    public static String firstCharToLowerCase(CharSequence str) {
        String strResult = str(str);
        if (isEmpty(str)) {
            return strResult;
        }
        char firstChar = str.charAt(0);
        if (Character.isLowerCase(firstChar)) {
            return strResult;
        }

        char[] charArray = strResult.toCharArray();
        charArray[0] += 32;
        return String.valueOf(charArray);
    }

    /**
     * 把字符串的首字母大写
     *
     * @param str str
     * @return str string
     */
    public static String firstCharToUpperCase(CharSequence str) {
        if (isEmpty(str)) {
            return str(str);
        }
        char firstChar = str.charAt(0);
        if (Character.isUpperCase(firstChar)) {
            return str(str);
        }
        return Character.toUpperCase(firstChar) + str(str).substring(1);
    }

    // equal ========================================================================================================================

    /**
     * 对比两个对象是否相同
     * <p>
     * 当obj1==null或obj2==null时,返回false;
     * 否则返回 obj1.equals(obj2)
     *
     * @param obj1 对象1
     * @param obj2 对象2
     * @return boolean
     */
    public static boolean equals(Object obj1, Object obj2) {
        return equals(obj1, obj2, false);
    }

    /**
     * 对比两个对象是否不相同
     *
     * @param obj1 对象1
     * @param obj2 对象2
     * @return boolean
     */
    public static boolean equalsNo(Object obj1, Object obj2) {
        return !equals(obj1, obj2, false);
    }

    /**
     * 对比两个字符串是否相同(忽略大小写)
     * <p>
     * 当obj1==null或obj2==null时,返回false;
     * 否则返回 obj1.toString().equalsIgnoreCase(obj2.toString())
     *
     * @param obj1 对象1
     * @param obj2 对象2
     * @return boolean
     */
    public static boolean equalsIgnoreCase(Object obj1, Object obj2) {
        return equals(obj1, obj2, true);
    }

    /**
     * Equals boolean.
     *
     * @param obj1             the obj 1
     * @param obj2             the obj 2
     * @param equalsIgnoreCase the equals ignore case
     * @return the boolean
     */
    public static boolean equals(Object obj1, Object obj2, boolean equalsIgnoreCase) {
        if (null == obj1 || null == obj2) {
            return Objects.equals(obj1, obj2);
        }

        boolean equals;
        if (equalsIgnoreCase) {
            equals = obj1.toString().equalsIgnoreCase(obj2.toString());
        } else {
            equals = obj1.toString().equals(obj2.toString());
        }
        return equals;
    }

    /**
     * 截取两个字符串的不同部分（长度一致），判断截取的子串是否相同<br>
     * 任意一个字符串为null返回false
     *
     * @param str        -- 字符串
     * @param ignoreCase -- 如果为 true，则比较字符时忽略大小写。
     * @param toffset    -- 此字符串中子区域的起始偏移量。
     * @param other      -- 字符串参数。
     * @param ooffset    -- 字符串参数中子区域的起始偏移量。
     * @param len        -- 要比较的字符数。
     * @return 子串是否相同 boolean
     * @since 3.2.1
     */
    public static boolean regionMatches(CharSequence str, int toffset, CharSequence other, int ooffset, int len, boolean ignoreCase) {
        if (null == str || null == other) {
            return false;
        }
        // String 区域匹配 截取str字符串第toffset位截取len长度子串，对比other字符串第ooffset位截取len长度子串，进行对比
        return str.toString().regionMatches(ignoreCase, toffset, other.toString(), ooffset, len);
    }

    // getString ========================================================================================================================

    /**
     * 字节数组转为字符串
     * 该方法默认以ISO-8859-1转码
     * 若想自己指定字符集,可以使用<code>getString(byte[] data, String charset)</code>方法
     *
     * @param data the data
     * @return the string
     */
    public static String getString(byte[] data) {
        return getString(data, "ISO-8859-1");
    }

    /**
     * 字节数组转为字符串
     * 如果系统不支持所传入的<code>charset</code>字符集,则按照系统默认字符集进行转换
     *
     * @param bytes   the bytes
     * @param charset the charset
     * @return the string
     */
    public static String getString(byte[] bytes, Charset charset) {
        return getString(bytes, charset.name());
    }

    /**
     * 字节数组转为字符串
     * 如果系统不支持所传入的<code>charset</code>字符集,则按照系统默认字符集进行转换
     *
     * @param bytes   the bytes
     * @param charset the charset
     * @return the string
     */
    public static String getString(byte[] bytes, String charset) {
        if (isEmpty(bytes)) {
            return EMPTY;
        }
        if (isEmpty(charset)) {
            return new String(bytes, Charset.defaultCharset());
        }
        try {
            return new String(bytes, charset);
        } catch (UnsupportedEncodingException e) {
            return new String(bytes);
        }
    }

    // repeat ========================================================================================================================

    public static String repeatNormal() {
        return repeat(KuugaConstants.EQUAL_SIGN, 100);
    }

    /**
     * 返回一个字符串，其值是此字符串重复 {@code count} 次的串联。
     * 如果此字符串为空或计数为零，则返回空字符串。
     *
     * @param str   str
     * @param count 重复次数
     * @return the String
     */
    public static String repeat(String str, int count) {
        return str.repeat(count);
    }

    public static String repeat(char strChar, int count) {
        return String.valueOf(strChar).repeat(count);
    }

    /**
     * 重复某个字符串并通过分界符连接
     *
     * <pre>
     * StrUtil.repeatAndJoin("?", 5, ",")   = "?,?,?,?,?"
     * StrUtil.repeatAndJoin("?", 0, ",")   = ""
     * StrUtil.repeatAndJoin("?", 5, null) = "?????"
     * </pre>
     *
     * @param str         被重复的字符串
     * @param count       数量
     * @param conjunction 分界符
     * @return 连接后的字符串 string
     * @since 4.0.1
     */
    public static String repeatAndJoin(char str, int count, CharSequence conjunction) {
        return repeatAndJoin(String.valueOf(str), count, conjunction);
    }

    public static String repeatAndJoin(char str, int count, char conjunctionChar) {
        return repeatAndJoin(String.valueOf(str), count, String.valueOf(conjunctionChar));
    }

    /**
     * Repeat and join string.
     *
     * @param str         the str
     * @param count       the count
     * @param conjunction the conjunction
     * @return the string
     */
    public static String repeatAndJoin(CharSequence str, int count, CharSequence conjunction) {
        if (count <= 0) {
            return str(str);
        }
        final KuugaStringBuilder builder = KuugaStringBuilder.builder();
        boolean isFirst = true;
        while (count-- > 0) {
            if (isFirst) {
                isFirst = false;
            } else if (hasText(conjunction)) {
                builder.append(conjunction);
            }
            builder.append(str);
        }
        return builder.toString();
    }

    // contains ========================================================================================================================

    /**
     * Contains boolean.
     *
     * @param source the source
     * @param target the target
     * @return the boolean
     */
    public static boolean contains(String source, String target) {
        return contains(source, new String[]{target});
    }

    /**
     * 指定字符是否在字符串中出现过
     *
     * @param str        字符串
     * @param searchChar 被查找的字符
     * @return 是否包含 boolean
     * @since 3.1.2
     */
    public static boolean contains(CharSequence str, char searchChar) {
        return indexOf(str, searchChar) > -1;
    }

    /**
     * 指定字符串是否在字符串中出现过
     *
     * @param str       字符串
     * @param searchStr 被查找的字符串
     * @return 是否包含 boolean
     * @since 5.1.1
     */
    public static boolean contains(CharSequence str, CharSequence searchStr) {
        if (null == str || null == searchStr) {
            return false;
        }
        return str.toString().contains(searchStr);
    }

    /**
     * 判断字符串source是否包含target文本
     *
     * @param sourceStr      the sourceStr
     * @param targetStrArray the targetStrArray
     * @return boolean
     */
    public static boolean contains(String sourceStr, String[] targetStrArray) {
        return contains(sourceStr, targetStrArray, false);
    }

    /**
     * Contains ignore case boolean.
     *
     * @param sourceStr the sourceStr
     * @param targetStr the targetStr
     * @return the boolean
     */
    public static boolean containsIgnoreCase(String sourceStr, String targetStr) {
        return contains(sourceStr, new String[]{targetStr}, true);
    }

    /**
     * 判断字符串source是否包含target文本，忽略大小写
     *
     * @param sourceStr      the sourceStr
     * @param targetStrArray the targetStrArray
     * @return boolean
     */
    public static boolean containsIgnoreCase(String sourceStr, String[] targetStrArray) {
        return contains(sourceStr, targetStrArray, true);
    }

    /**
     * 判断字符串source是否包含target文本
     *
     * @param sourceStr      sourceStr
     * @param targetStrArray targetStrArray
     * @param ignoreCase     是否忽略大小写
     * @return boolean
     */
    public static boolean contains(String sourceStr, String[] targetStrArray, boolean ignoreCase) {
        boolean paramsIsEmpty = isEmpty(sourceStr) || ArrayUtil.isEmpty(targetStrArray);
        if (paramsIsEmpty) {
            return false;
        }
        long containsCount;
        if (ignoreCase) {
            containsCount = Arrays.stream(targetStrArray).filter(str -> sourceStr.toUpperCase().contains(str.toUpperCase())).count();
        } else {
            containsCount = Arrays.stream(targetStrArray).filter(sourceStr::contains).count();
        }
        return containsCount > 0;
    }

    // length ========================================================================================================================

    /**
     * 获取map集合中所有元素的toString最长元素
     *
     * @param <K> the type parameter
     * @param <V> the type parameter
     * @param map map
     * @return T int
     */
    public static <K, V> int maxLength(Map<K, V> map) {
        if (MapUtil.isEmpty(map)) {
            return 0;
        }
        int kMaxLength = SetUtil.optimize(map.keySet()).stream()
                .filter(Objects::nonNull)
                .map(Objects::toString)
                .mapToInt(String::length)
                .max().orElse(0);
        int vMaxLength = map.values().stream()
                .filter(Objects::nonNull)
                .map(Objects::toString)
                .mapToInt(String::length)
                .max().orElse(0);

        return Integer.max(kMaxLength, vMaxLength);
    }

    /**
     * 获取map集合中所有key元素的toString最长元素
     *
     * @param <K> the type parameter
     * @param <V> the type parameter
     * @param map map
     * @return T int
     */
    public static <K, V> int maxKeyLength(Map<K, V> map) {
        if (MapUtil.isEmpty(map)) {
            return 0;
        }
        return SetUtil.optimize(map.keySet()).stream()
                .filter(Objects::nonNull)
                .map(Objects::toString)
                .mapToInt(String::length)
                .max().orElse(0);
    }

    /**
     * 获取map集合中所有value元素的toString最长元素
     *
     * @param <K> the type parameter
     * @param <V> the type parameter
     * @param map map
     * @return T int
     */
    public static <K, V> int maxValueLength(Map<K, V> map) {
        if (MapUtil.isEmpty(map)) {
            return 0;
        }
        return map.values().stream()
                .filter(Objects::nonNull)
                .map(Objects::toString)
                .mapToInt(String::length)
                .max().orElse(0);
    }

    /**
     * 获取list集合中所有元素的toString最长元素
     *
     * @param <T>  T
     * @param list list
     * @return T int
     */
    public static <T> int maxLength(final List<T> list) {
        return ListUtil.optimize(list).stream()
                .filter(Objects::nonNull)
                .map(Objects::toString)
                .mapToInt(String::length).max().orElse(0);
    }

    // startWith ========================================================================================================================

    /**
     * 判断text的前缀是否prefix，不区分大小写
     *
     * @param text   text
     * @param prefix prefix
     * @return boolean
     */
    public static boolean startsWith(final String text, final String prefix) {
        if (containsEmpty(text, prefix)) {
            return false;
        }
        return text.startsWith(prefix);
    }

    /**
     * 判断text的前缀是否prefix，不区分大小写
     *
     * @param text   text
     * @param prefix prefix
     * @return boolean
     */
    public static boolean startsWithIgnoreCase(final String text, final String prefix) {
        if (containsEmpty(text, prefix)) {
            return false;
        }
        return text.toUpperCase().startsWith(prefix.toUpperCase());
    }

    // endWith ================================================================================================================

    /**
     * 字符串是否以给定字符结尾
     *
     * @param str 字符串
     * @param c   字符
     * @return 是否结尾 boolean
     */
    public static boolean endsWith(CharSequence str, char c) {
        if (isEmpty(str)) {
            return false;
        }
        return c == str.charAt(str.length() - 1);
    }

    /**
     * 是否以指定字符串结尾<br>
     * 如果给定的字符串和开头字符串都为null则返回true，否则任意一个值为null返回false
     *
     * @param str          被监测字符串
     * @param suffix       结尾字符串
     * @param isIgnoreCase 是否忽略大小写
     * @return 是否以指定字符串结尾 boolean
     */
    public static boolean endsWith(CharSequence str, CharSequence suffix, boolean isIgnoreCase) {
        if (null == str || null == suffix) {
            return null == str && null == suffix;
        }

        if (isIgnoreCase) {
            return str.toString().toLowerCase().endsWith(suffix.toString().toLowerCase());
        } else {
            return str.toString().endsWith(suffix.toString());
        }
    }

    /**
     * 是否以指定字符串结尾
     *
     * @param str    被监测字符串
     * @param suffix 结尾字符串
     * @return 是否以指定字符串结尾 boolean
     */
    public static boolean endsWith(CharSequence str, CharSequence suffix) {
        return endsWith(str, suffix, false);
    }

    /**
     * 是否以指定字符串结尾，忽略大小写
     *
     * @param str    被监测字符串
     * @param suffix 结尾字符串
     * @return 是否以指定字符串结尾 boolean
     */
    public static boolean endsWithIgnoreCase(CharSequence str, CharSequence suffix) {
        return endsWith(str, suffix, true);
    }

    /**
     * 给定字符串是否以任何一个字符串结尾<br>
     * 给定字符串和数组为空都返回false
     *
     * @param str      给定字符串
     * @param suffixes 需要检测的结尾字符串
     * @return 给定字符串是否以任何一个字符串结尾 boolean
     * @since 3.0.6
     */
    public static boolean endsWithAny(CharSequence str, CharSequence... suffixes) {
        if (isEmpty(str) || ArrayUtil.isEmpty(suffixes)) {
            return false;
        }

        for (CharSequence suffix : suffixes) {
            if (endsWith(str, suffix, false)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 给定字符串是否以任何一个字符串结尾（忽略大小写）<br>
     * 给定字符串和数组为空都返回false
     *
     * @param str      给定字符串
     * @param suffixes 需要检测的结尾字符串
     * @return 给定字符串是否以任何一个字符串结尾 boolean
     * @since 5.5.9
     */
    public static boolean endsWithAnyIgnoreCase(CharSequence str, CharSequence... suffixes) {
        if (isEmpty(str) || ArrayUtil.isEmpty(suffixes)) {
            return false;
        }

        for (CharSequence suffix : suffixes) {
            if (endsWith(str, suffix, true)) {
                return true;
            }
        }
        return false;
    }

    // remove ===============================================================================================================================================

    /**
     * 移除字符串起始的指定字符
     *
     * @param str       字符串
     * @param removeStr 待移除字符串
     * @return String string
     */
    public static String removeStart(final String str, final String removeStr) {
        return removeStart(str, removeStr, false);
    }

    /**
     * 移除字符串起始的指定字符 忽略大小写
     *
     * @param str       字符串
     * @param removeStr 待移除字符串
     * @return String string
     */
    public static String removeStartIgnoreCase(final String str, final String removeStr) {
        return removeStart(str, removeStr, true);
    }

    /**
     * 移除字符串起始的指定字符
     *
     * @param str        字符串
     * @param removeStr  待移除字符串
     * @param ignoreCase 是否忽略大小写
     * @return String string
     */
    public static String removeStart(final String str, final String removeStr, boolean ignoreCase) {
        if (isEmpty(str) || isEmpty(removeStr)) {
            return str;
        }

        if (ignoreCase) {
            if (str.toUpperCase().startsWith(removeStr.toUpperCase())) {
                return str.substring(removeStr.length());
            }
        } else {
            if (str.startsWith(removeStr)) {
                return str.substring(removeStr.length());
            }
        }
        return str;
    }

    /**
     * 移除字符串末尾的指定字符
     *
     * @param str       字符串
     * @param removeStr 待移除字符串
     * @return String string
     */
    public static String removeEnd(final String str, final String removeStr) {
        return removeEnd(str, removeStr, false);
    }

    /**
     * 移除字符串末尾的指定字符 忽略大小写
     *
     * @param str       字符串
     * @param removeStr 待移除字符串
     * @return String string
     */
    public static String removeEndIgnoreCase(final String str, final String removeStr) {
        return removeEnd(str, removeStr, true);
    }

    /**
     * 移除字符串末尾的指定字符
     *
     * @param str        字符串
     * @param removeStr  待移除字符串
     * @param ignoreCase 是否忽略大小写
     * @return String string
     */
    public static String removeEnd(final String str, final String removeStr, boolean ignoreCase) {
        if (isEmpty(str)) {
            return str;
        }

        boolean isMatching = str.endsWith(removeStr);

        boolean ignoreCaseIsMatching = ignoreCase && str.toUpperCase().endsWith(removeStr.toUpperCase());

        if (isMatching || ignoreCaseIsMatching) {
            return str.substring(0, str.length() - removeStr.length());
        }
        return str;
    }

    // firstText or lastText ===============================================================================================================================================

    /**
     * 获取分隔符中第一个出现的字符串
     *
     * @param str 字符串
     * @return String string
     */
    public static String firstText(String str) {
        return firstText(str, KuugaConstants.COMMAENG);
    }

    /**
     * 获取分隔符中第一个出现的字符串
     *
     * @param str       字符串
     * @param separator 分隔符
     * @return String string
     */
    public static String firstText(String str, String separator) {
        if (isEmpty(str) || isEmpty(separator)) {
            return str;
        }
        String[] split = str.split(separator);
        str = Arrays.stream(split).filter(StringUtil::hasText).findFirst().orElse(EMPTY);
        return str;
    }

    /**
     * 获取分隔符数组中第一个出现的字符串
     *
     * @param str        字符串
     * @param separators 分隔符数组
     * @return String string
     */
    public static String firstText(String str, String[] separators) {
        if (isEmpty(str) || ArrayUtil.isEmpty(separators)) {
            return str;
        }

        long containsCount = Arrays.stream(separators).filter(str::contains).count();
        if (containsCount == 0) {
            return str;
        }

        Map<Integer, String> separatorIndexMap = new TreeMap<>();
        for (String separator : separators) {
            int separatorIndex = str.indexOf(separator);
            if (separatorIndex == -1) {
                continue;
            }
            separatorIndexMap.put(separatorIndex, separator);
        }
        ArrayList<Integer> integers = new ArrayList<>(separatorIndexMap.keySet());
        integers.sort(Comparator.comparingInt(index -> index));
        return str.split(separatorIndexMap.get(integers.get(0)))[0].trim();
    }

    /**
     * 获取分隔符中最后一个出现的字符串
     *
     * @param str 字符串
     * @return String string
     */
    public static String lastText(String str) {
        return lastText(str, KuugaConstants.COMMAENG);
    }

    /**
     * 获取分隔符中最后一个出现的字符串
     *
     * @param str       字符串
     * @param separator 分隔符
     * @return String string
     */
    public static String lastText(String str, String separator) {
        if (isEmpty(str) || isEmpty(separator)) {
            return str;
        }
        String[] split = str.split(separator);
        List<String> list = Arrays.stream(split).filter(StringUtil::hasText).collect(Collectors.toList());
        return ListSortUtil.reverseNew(list).stream().findFirst().orElse(StringUtil.EMPTY);
    }

    /**
     * 获取分隔符数组中最后一个出现的字符串
     *
     * @param str        字符串
     * @param separators 分隔符数组
     * @return String string
     */
    public static String lastText(String str, String[] separators) {
        boolean isEmpty = isEmpty(str) || ArrayUtil.isEmpty(separators);

        if (isEmpty) {
            return str;
        }

        long containsCount = Arrays.stream(separators).filter(str::contains).count();
        if (containsCount == 0) {
            return str;
        }

        Map<Integer, String> separatorIndexMap = new TreeMap<>();
        for (String separator : separators) {
            int separatorIndex = str.lastIndexOf(separator);
            if (separatorIndex == -1) {
                continue;
            }
            separatorIndexMap.put(separatorIndex, separator);
        }
        ArrayList<Integer> integers = new ArrayList<>(separatorIndexMap.keySet());
        integers.sort((index1, index2) -> index2 - index1);
        String regex = separatorIndexMap.get(integers.get(0));
        String[] split = str.split(regex);
        return split[split.length - 1].trim();
    }

    // join ========================================================================================================================

    /**
     * 字符串拼接
     *
     * @param elements 元素数组
     * @return String string
     */
    public static String join(CharSequence... elements) {
        return String.join(StringUtil.EMPTY, elements);
    }

    /**
     * 字符串拼接
     *
     * @param delimiter 分隔符
     * @param elements  元素数组
     * @return String string
     */
    public static String joinWithDelimiter(CharSequence delimiter, CharSequence... elements) {
        return String.join(delimiter, elements);
    }

    public static String joinWithDelimiter(CharSequence delimiter, Iterable<? extends CharSequence> elements) {
        return String.join(delimiter, elements);
    }

    /**
     * 文本拼接
     *
     * @param delimiter 分隔符
     * @param prefix    前缀
     * @param suffix    后缀
     * @param elements  集合
     * @return 拼接好的文本
     */
    public static String join(CharSequence delimiter, CharSequence prefix, CharSequence suffix, Iterable<? extends CharSequence> elements) {
        List<? extends CharSequence> charSequences = IterUtil.toList(elements);
        return charSequences.stream().collect(Collectors.joining(delimiter, prefix, suffix));
    }


    // 特殊字符转义 ========================================================================================================================

    /**
     * 将文本中的特殊字符进行转义
     *
     * @param str the str
     * @return the string
     */
    public static String escapeQueryChars(String str) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < str.length(); ++i) {
            char c = str.charAt(i);
            if (c == '\\' || c == '+' || c == '-' || c == '!' || c == '(' || c == ')' || c == ':' || c == '^'
                    || c == '[' || c == ']' || c == '"' || c == '{' || c == '}' || c == '~' || c == '*' || c == '?'
                    || c == '|' || c == '&' || c == ';' || c == '/' || Character.isWhitespace(c)) {
                sb.append('\\');
            }
            sb.append(c);
        }
        return sb.toString();
    }

    // 头尾追加 =========================================================================================================

    /**
     * 如果给定字符串不是以prefix开头的，在开头补充 prefix
     *
     * @param str    字符串
     * @param prefix 前缀
     * @return 补充后的字符串 string
     */
    public static String addPrefixIfNot(CharSequence str, CharSequence prefix) {
        if (isEmpty(str) || isEmpty(prefix)) {
            return str(str);
        }

        final String str2 = str.toString();
        final String prefix2 = prefix.toString();
        if (!str2.startsWith(prefix2)) {
            return prefix2.concat(str2);
        }
        return str2;
    }

    /**
     * 结果尾部不是suffix结束，则追加suffix
     *
     * @param str    str
     * @param suffix suffix
     * @return str string
     */
    public static String addSuffixIfNot(CharSequence str, CharSequence suffix) {
        if (isEmpty(str) || isEmpty(suffix)) {
            return toString(str);
        }

        final String str2 = str.toString();
        final String suffix2 = suffix.toString();
        if (!str2.endsWith(suffix2)) {
            return str2.concat(suffix2);
        }
        return str2;
    }

    // 填充字符串 =========================================================================================================

    /**
     * 补充字符串以满足最小长度
     *
     * <pre>
     * StrUtil.padAfter(null, *, *);//null
     * StrUtil.padAfter("1", 3, '0');//"100"
     * StrUtil.padAfter("123", 2, '0');//"23"
     * </pre>
     *
     * @param str       字符串，如果为{@code null}，直接返回null
     * @param minLength 最小长度
     * @param padChar   补充的字符
     * @return 补充后的字符串 string
     */
    public static String padAfter(CharSequence str, int minLength, CharSequence padChar) {
        if (null == str) {
            return null;
        }
        final int strLen = str.length();
        if (strLen == minLength) {
            return str.toString();
        } else if (strLen > minLength) {
            return str.toString().substring(0, minLength);
        }

        return str.toString().concat(repeatAndJoin(padChar, minLength - strLen, EMPTY));
    }

    // builder or buffer =========================================================================================================

    /**
     * Builder string builder.
     *
     * @return the string builder
     */
    public static StringBuilder builder() {
        return new StringBuilder();
    }

    /**
     * Builder string builder.
     *
     * @param capacity the capacity
     * @return the string builder
     */
    public static StringBuilder builder(int capacity) {
        return new StringBuilder(capacity);
    }

    /**
     * Builder string builder.
     *
     * @param text the text
     * @return the string builder
     */
    public static StringBuilder builder(final String text) {
        return builder().append(text);
    }

    /**
     * Buffer string buffer.
     *
     * @return the string buffer
     */
    public static StringBuffer buffer() {
        return new StringBuffer();
    }

    /**
     * Buffer string buffer.
     *
     * @param text the text
     * @return the string buffer
     */
    public static StringBuffer buffer(final String text) {
        return buffer().append(text);
    }

    // bytes =========================================================================================================

    /**
     * 编码字符串，编码为UTF-8
     *
     * @param str 字符串
     * @return 编码后的字节码 byte[]
     */
    public static byte[] utf8Bytes(CharSequence str) {
        return bytes(str, StandardCharsets.UTF_8);
    }

    /**
     * 编码字符串<br>
     * 使用系统默认编码
     *
     * @param str 字符串
     * @return 编码后的字节码 byte[]
     */
    public static byte[] bytes(CharSequence str) {
        return bytes(str, Charset.defaultCharset());
    }

    /**
     * 编码字符串
     *
     * @param str     字符串
     * @param charset 字符集，如果此字段为空，则解码的结果取决于平台
     * @return 编码后的字节码 byte[]
     */
    public static byte[] bytes(CharSequence str, String charset) {
        return bytes(str, isEmpty(charset) ? Charset.defaultCharset() : Charset.forName(charset));
    }

    /**
     * 编码字符串
     *
     * @param str     字符串
     * @param charset 字符集，如果此字段为空，则解码的结果取决于平台
     * @return 编码后的字节码 byte[]
     */
    public static byte[] bytes(CharSequence str, Charset charset) {
        if (str == null) {
            return null;
        }

        if (null == charset) {
            return str.toString().getBytes();
        }
        return str.toString().getBytes(charset);
    }

    /**
     * 字符串转换为byteBuffer
     *
     * @param str     字符串
     * @param charset 编码
     * @return byteBuffer byte buffer
     */
    public static ByteBuffer byteBuffer(CharSequence str, String charset) {
        return ByteBuffer.wrap(bytes(str, charset));
    }

    /**
     * 将对象转为字符串<br>
     *
     * <pre>
     * 1、Byte数组和ByteBuffer会被转换为对应字符串的数组
     * 2、对象数组会调用Arrays.toString方法
     * </pre>
     *
     * @param obj 对象
     * @return 字符串 string
     */
    public static String utf8Str(Object obj) {
        return str(obj, StandardCharsets.UTF_8);
    }

    // str ==============================================================================================================================

    /**
     * {@link CharSequence} 转为字符串，null安全
     *
     * @param cs {@link CharSequence}
     * @return 字符串 string
     */
    public static String str(CharSequence cs) {
        return null == cs ? null : cs.toString();
    }

    /**
     * 将对象转为字符串
     *
     * <pre>
     * 1、Byte数组和ByteBuffer会被转换为对应字符串的数组
     * 2、对象数组会调用Arrays.toString方法
     * </pre>
     *
     * @param obj         对象
     * @param charsetName 字符集
     * @return 字符串 string
     */
    public static String str(Object obj, String charsetName) {
        return str(obj, Charset.forName(charsetName));
    }

    /**
     * 将对象转为字符串
     * <pre>
     * 	 1、Byte数组和ByteBuffer会被转换为对应字符串的数组
     * 	 2、对象数组会调用Arrays.toString方法
     * </pre>
     *
     * @param obj     对象
     * @param charset 字符集
     * @return 字符串 string
     */
    public static String str(Object obj, Charset charset) {
        if (null == obj) {
            return null;
        }

        if (obj instanceof String) {
            return (String) obj;
        } else if (obj instanceof byte[]) {
            return str((byte[]) obj, charset);
        } else if (obj instanceof Byte[]) {
            return str((Byte[]) obj, charset);
        } else if (obj instanceof ByteBuffer) {
            return str((ByteBuffer) obj, charset);
        } else if (ArrayUtil.isArray(obj)) {
            return ArrayUtil.toString(obj);
        }

        return obj.toString();
    }

    /**
     * 将byte数组转为字符串
     *
     * @param bytes   byte数组
     * @param charset 字符集
     * @return 字符串 string
     */
    public static String str(byte[] bytes, String charset) {
        return str(bytes, isEmpty(charset) ? Charset.defaultCharset() : Charset.forName(charset));
    }

    /**
     * 解码字节码
     *
     * @param data    字符串
     * @param charset 字符集，如果此字段为空，则解码的结果取决于平台
     * @return 解码后的字符串 string
     */
    public static String str(byte[] data, Charset charset) {
        if (data == null) {
            return null;
        }

        if (null == charset) {
            return new String(data);
        }
        return new String(data, charset);
    }

    /**
     * 将Byte数组转为字符串
     *
     * @param bytes   byte数组
     * @param charset 字符集
     * @return 字符串 string
     */
    public static String str(Byte[] bytes, String charset) {
        return str(bytes, isEmpty(charset) ? Charset.defaultCharset() : Charset.forName(charset));
    }

    /**
     * 解码字节码
     *
     * @param data    字符串
     * @param charset 字符集，如果此字段为空，则解码的结果取决于平台
     * @return 解码后的字符串 string
     */
    public static String str(Byte[] data, Charset charset) {
        if (data == null) {
            return null;
        }

        byte[] bytes = new byte[data.length];
        Byte dataByte;
        for (int i = 0; i < data.length; i++) {
            dataByte = data[i];
            bytes[i] = (null == dataByte) ? -1 : dataByte;
        }

        return str(bytes, charset);
    }

    /**
     * 将编码的byteBuffer数据转换为字符串
     *
     * @param data    数据
     * @param charset 字符集，如果为空使用当前系统字符集
     * @return 字符串 string
     */
    public static String str(ByteBuffer data, String charset) {
        if (data == null) {
            return null;
        }

        return str(data, Charset.forName(charset));
    }

    /**
     * 将编码的byteBuffer数据转换为字符串
     *
     * @param data    数据
     * @param charset 字符集，如果为空使用当前系统字符集
     * @return 字符串 string
     */
    public static String str(ByteBuffer data, Charset charset) {
        if (null == charset) {
            charset = Charset.defaultCharset();
        }
        return charset.decode(data).toString();
    }

    // sub ========================================================================================================================================

    /**
     * 改进JDK subString
     * index从0开始计算，最后一个字符为-1
     * 如果from和to位置一样，返回 ""
     * 如果from或to为负数，则按照length从后向前数位置，如果绝对值大于字符串长度，则from归到0，to归到length(是否有必要支持这种？)
     * 如果经过修正的index中from大于to，则互换from和to example:
     * abcdefgh 2 3 -> c
     * abcdefgh 2 -3 -> cde
     *
     * @param str              String
     * @param fromIndexInclude 开始的index（包括）
     * @param toIndexExclude   结束的index（不包括）
     * @return 字串 string
     */
    public static String sub(CharSequence str, int fromIndexInclude, int toIndexExclude) {
        if (isEmpty(str)) {
            return str(str);
        }
        int len = str.length();

        if (fromIndexInclude < 0) {
            fromIndexInclude = len + fromIndexInclude;
            if (fromIndexInclude < 0) {
                fromIndexInclude = 0;
            }
        } else if (fromIndexInclude > len) {
            fromIndexInclude = len;
        }

        if (toIndexExclude < 0) {
            toIndexExclude = len + toIndexExclude;
            if (toIndexExclude < 0) {
                toIndexExclude = len;
            }
        } else if (toIndexExclude > len) {
            toIndexExclude = len;
        }

        if (toIndexExclude < fromIndexInclude) {
            int tmp = fromIndexInclude;
            fromIndexInclude = toIndexExclude;
            toIndexExclude = tmp;
        }

        if (fromIndexInclude == toIndexExclude) {
            return EMPTY;
        }

        return str.toString().substring(fromIndexInclude, toIndexExclude);
    }

    // indexOf ========================================================================================================================================

    /**
     * 指定范围内查找指定字符
     *
     * @param str        字符串
     * @param searchChar 被查找的字符
     * @return 位置 int
     */
    public static int indexOf(final CharSequence str, char searchChar) {
        return indexOf(str, searchChar, 0);
    }

    /**
     * 指定范围内查找指定字符
     *
     * @param str        字符串
     * @param searchChar 被查找的字符
     * @param start      起始位置，如果小于0，从0开始查找
     * @return 位置 int
     */
    public static int indexOf(CharSequence str, char searchChar, int start) {
        if (str instanceof String) {
            return ((String) str).indexOf(searchChar, start);
        } else {
            return indexOf(str, searchChar, start, -1);
        }
    }

    /**
     * 指定范围内查找指定字符
     *
     * @param str        字符串
     * @param searchChar 被查找的字符
     * @param start      起始位置，如果小于0，从0开始查找
     * @param end        终止位置，如果超过str.length()则默认查找到字符串末尾
     * @return 位置 int
     */
    public static int indexOf(final CharSequence str, char searchChar, int start, int end) {
        if (isEmpty(str)) {
            return INDEX_NOT_FOUND;
        }
        final int len = str.length();
        if (start < 0 || start > len) {
            start = 0;
        }
        if (end > len || end < 0) {
            end = len;
        }
        for (int i = start; i < end; i++) {
            if (str.charAt(i) == searchChar) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * 返回(searchChar)此字符串中最后一次出现指定字符的索引
     * 不存在则返回：-1
     *
     * @param str        字符串
     * @param searchChar 被查找的字符
     * @return 下标
     */
    public static int lastIndexOf(final String str, char searchChar) {
        return str.lastIndexOf(searchChar);
    }

    // code type change ========================================================================================================================================

    /**
     * 编码类型转换
     *
     * @param sourceString  源字符串
     * @param targetCharset 目标编码类型
     * @return str string
     */
    public static String codeTypeChange(String sourceString, Charset targetCharset) {
        byte[] sourceStringBytes = sourceString.getBytes();
        return new String(sourceStringBytes, targetCharset);
    }

    /**
     * 编码类型转换
     *
     * @param sourceString  源字符串
     * @param sourceCharset 源编码类型
     * @param targetCharset 目标编码类型
     * @return str string
     */
    public static String codeTypeChange(String sourceString, Charset sourceCharset, Charset targetCharset) {
        byte[] sourceStringBytes = sourceString.getBytes(sourceCharset);
        return new String(sourceStringBytes, targetCharset);
    }

    // match ========================================================================================================================================

    /**
     * 字符串的每一个字符是否都与定义的匹配器匹配
     *
     * @param value   字符串
     * @param matcher 匹配器
     * @return 是否全部匹配 boolean
     * @since 3.2.3
     */
    public static boolean isAllCharMatch(CharSequence value, Matcher<Character> matcher) {
        if (isEmpty(value)) {
            return false;
        }
        for (int i = value.length(); --i >= 0; ) {
            if (!matcher.match(value.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 返回从此字符串中提取的行流，由行终止符分隔。
     * aaa\nbbb\nccc
     * 跟进\n进行分割
     *
     * @param str str
     * @return the stream
     */
    public static Stream<String> lines(String str) {
        return str.lines();
    }

    // whitespace ====================================================================================================

    /**
     * 是否包含空白符
     *
     * @param str 字符串
     * @return boolean
     */
    public static boolean containsWhitespace(String str) {
        return StringUtils.containsWhitespace(str);
    }

    /**
     * 是否包含空白符
     *
     * @param str 字符序列
     * @return boolean
     */
    public static boolean containsWhitespace(CharSequence str) {
        return StringUtils.containsWhitespace(str);
    }

    // count ====================================================================================================

    /**
     * 计算一个字符串中指定子串的出现次数
     *
     * @param str 字符串
     * @param sub 指定字串
     * @return 出现次数
     */
    public static int countOccurrencesOf(String str, String sub) {
        return StringUtils.countOccurrencesOf(str, sub);
    }

}