package io.github.kuugasky.kuugatool.core.string;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * StringEscapeUtil
 * <p>
 * 字符串转义实用工具
 *
 * @author kuuga
 * @since 2022/6/20 19:18
 */
public final class StringEscapeUtil {

    /**
     * sql转义防注入
     */
    public static String escapeSql(String sql) {
        return StringEscapeUtils.escapeSql(sql);
    }

    /**
     * html转义
     */
    public static String escapeHtml(String html) {
        return StringEscapeUtils.escapeHtml(html);
    }

    /**
     * 移除html转义
     */
    public static String unescapeHtml(String html) {
        return StringEscapeUtils.unescapeHtml(html);
    }

    /**
     * java转义
     */
    public static String escapeJava(String java) {
        return StringEscapeUtils.escapeJava(java);
    }

    /**
     * 移除java转义
     */
    public static String unescapeJava(String java) {
        return StringEscapeUtils.unescapeJava(java);
    }

    /**
     * javaScript转义
     */
    public static String escapeJavaScript(String javaScript) {
        return StringEscapeUtils.escapeJavaScript(javaScript);
    }

    /**
     * 移除javaScript转义
     */
    public static String unescapeJavaScript(String javaScript) {
        return StringEscapeUtils.unescapeJavaScript(javaScript);
    }

    /**
     * xml转义
     */
    public static String escapeXml(String xml) {
        return StringEscapeUtils.escapeXml(xml);
    }

    /**
     * 移除xml转义
     */
    public static String unescapeXml(String xml) {
        return StringEscapeUtils.unescapeXml(xml);
    }

    /**
     * csv转义
     */
    public static String escapeCsv(String csv) {
        return StringEscapeUtils.escapeCsv(csv);
    }

    /**
     * 移除csv转义
     */
    public static String unescapeCsv(String csv) {
        return StringEscapeUtils.unescapeCsv(csv);
    }

}
