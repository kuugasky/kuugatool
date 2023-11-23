package io.github.kuugasky.kuugatool.http.html;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.collection.MapUtil;
import io.github.kuugasky.kuugatool.core.constants.KuugaConstants;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * url and html utils.
 *
 * @author code4crafter@gmail.com <br>
 * @since 0.1.0
 */
@Slf4j
public final class UrlUtil {

    private static final Pattern PATTERN_FOR_CHARSET = Pattern.compile("charset\\s*=\\s*['\"]*([^\\s;'\"]*)");

    /**
     * url地址转URL对象
     *
     * @param url url
     * @return URL
     * @throws MalformedURLException 格式错误的URL异常
     */
    public static URL of(String url) throws MalformedURLException {
        return new URL(url);
    }

    /**
     * 获取url协议
     *
     * @param url url
     * @return 协议
     * @throws MalformedURLException 格式错误的URL异常
     */
    public static String getProtocol(String url) throws MalformedURLException {
        return of(url).getProtocol();
    }

    /**
     * 获取url host
     *
     * @param url url
     * @return 协议
     * @throws MalformedURLException 格式错误的URL异常
     */
    public static String getHost(String url) throws MalformedURLException {
        return of(url).getHost();
    }

    /**
     * 获取url默认端口
     *
     * @param url url
     * @return 协议
     * @throws MalformedURLException 格式错误的URL异常
     */
    public static int getDefaultPort(String url) throws MalformedURLException {
        return of(url).getDefaultPort();
    }

    /**
     * 获取url端口
     *
     * @param url url
     * @return 协议
     * @throws MalformedURLException 格式错误的URL异常
     */
    public static int getPort(String url) throws MalformedURLException {
        return of(url).getPort();
    }

    /**
     * 获取url path
     *
     * @param url url
     * @return 协议
     * @throws MalformedURLException 格式错误的URL异常
     */
    public static String getPath(String url) throws MalformedURLException {
        return of(url).getPath();
    }

    /**
     * 获取url file
     *
     * @param url url
     * @return 协议
     * @throws MalformedURLException 格式错误的URL异常
     */
    public static String getFile(String url) throws MalformedURLException {
        return of(url).getFile();
    }

    /**
     * 判断字符串是否为URL
     *
     * @param url url address
     * @return true:是URL、false:不是URL
     */
    public static boolean isUrl(String url) {
        try {
            of(url);
            return true;
        } catch (MalformedURLException e) {
            log.error("非有效url地址:{}", url);
            return false;
        }
    }

    /**
     * 规范化的Url
     * Borrowed from Jsoup.
     *
     * @param url   url
     * @param refer refer
     * @return canonicalizeUrl
     */
    public static String canonicalizeUrl(String url, String refer) {
        URL base;
        try {
            try {
                base = new URL(refer);
            } catch (MalformedURLException e) {
                // the base is unsuitable, but the attribute may be abs on its own, so try that
                URL abs = new URL(refer);
                return abs.toExternalForm();
            }
            // workaround: java resolves '//path/file + ?foo' to '//path/?foo', not '//path/file?foo' as desired
            if (url.startsWith(KuugaConstants.QUESTION_MARK)) {
                url = base.getPath() + url;
            }
            URL abs = new URL(base, url);
            return encodeIllegalCharacterInUrl(abs.toExternalForm());
        } catch (MalformedURLException e) {
            return StringUtil.EMPTY;
        }
    }

    /**
     * 在Url中编码非法字符
     *
     * @param url url
     * @return new url
     */
    public static String encodeIllegalCharacterInUrl(String url) {
        return url.replace(" ", "%20");
    }

    private static final Pattern PATTERN_FOR_PROTOCOL = Pattern.compile("[\\w]+://");

    /**
     * 去除http和https协议
     *
     * @param url eg: <a href="https://www.google.com">...</a>
     * @return eg: www.google.com
     */
    public static String removeProtocol(String url) {
        return PATTERN_FOR_PROTOCOL.matcher(url).replaceAll(StringUtil.EMPTY);
    }

    /**
     * 去除端口
     *
     * @param urlAddress urlAddress
     * @return String
     */
    public static String removePort(String urlAddress) throws MalformedURLException {
        URL of = of(urlAddress);
        return String.format("%s://%s%s", of.getProtocol(), of.getHost(), of.getFile());
    }

    /**
     * 允许空白空间引用
     */
    public static final Pattern PATTERN_FOR_HREF_WITH_QUOTE = Pattern.compile("(<a[^<>]*href=)[\"']([^\"'<>]*)[\"']", Pattern.CASE_INSENSITIVE);

    /**
     * 不允许空白空格
     */
    private static final Pattern PATTERN_FOR_HREF_WITHOUT_QUOTE = Pattern.compile("(<a[^<>]*href=)([^\"'<>\\s]+)", Pattern.CASE_INSENSITIVE);

    /**
     * 将所有的连接进行补充完整的连接
     *
     * @param html html
     * @param url  url
     * @return String
     */
    public static String fixAllRelativeHref(String html, String url) {
        html = replaceByPattern(html, url, PATTERN_FOR_HREF_WITH_QUOTE);
        html = replaceByPattern(html, url, PATTERN_FOR_HREF_WITHOUT_QUOTE);
        return html;
    }

    /**
     * 根据正则，提取所有的有效连接
     *
     * @param html    html
     * @param url     url
     * @param pattern pattern
     * @return String
     */
    public static String replaceByPattern(String html, String url, Pattern pattern) {
        StringBuilder stringBuilder = new StringBuilder();
        Matcher matcher = pattern.matcher(html);
        int lastEnd = 0;
        boolean modified = false;
        while (matcher.find()) {
            modified = true;
            stringBuilder.append(StringUtil.sub(html, lastEnd, matcher.start()));
            stringBuilder.append(matcher.group(1));
            stringBuilder.append("\"").append(canonicalizeUrl(matcher.group(2), url)).append("\"");
            lastEnd = matcher.end();
        }
        if (!modified) {
            return html;
        }
        stringBuilder.append(StringUtil.sub(html, lastEnd, 0));
        return stringBuilder.toString();
    }

    /**
     * 如果url中有charset编码参数，则提取
     *
     * @param url url
     * @return charsetStr
     */
    public static String getCharset(String url) {
        Matcher matcher = PATTERN_FOR_CHARSET.matcher(url);
        if (matcher.find()) {
            String charset = matcher.group(1);
            if (Charset.isSupported(charset)) {
                return charset;
            }
        }
        return null;
    }

    /**
     * url转换成map对象
     *
     * @param url url
     * @return map
     */
    public static Map<String, String> toMap(String url) {
        return toMap(url, "&");
    }

    /**
     * url转换成map对象
     *
     * @param url       url
     * @param separator 分隔符
     * @return map
     */
    public static Map<String, String> toMap(String url, String separator) {
        Map<String, String> map = MapUtil.newHashMap();
        if (StringUtil.hasText(url)) {
            String newUrl = url;
            if (url.contains(KuugaConstants.QUESTION_MARK)) {
                newUrl = url.substring(url.indexOf(KuugaConstants.QUESTION_MARK) + 1);
            }
            String[] params = newUrl.split(separator);

            ListUtil.optimize(Arrays.asList(params)).forEach(param -> {
                String[] keyValue = param.split(KuugaConstants.EQUAL_SIGN);
                if (keyValue.length == KuugaConstants.TWO) {
                    map.put(keyValue[0], keyValue[1]);
                } else {
                    map.put(keyValue[0], StringUtil.EMPTY);
                }
            });
        }
        return map;
    }

}
