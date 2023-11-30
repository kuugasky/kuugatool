package io.github.kuugasky.kuugatool.extra.web;

import com.alibaba.fastjson2.JSON;
import io.github.kuugasky.kuugatool.core.collection.MapUtil;
import io.github.kuugasky.kuugatool.core.constants.KuugaCharsets;
import io.github.kuugasky.kuugatool.core.constants.KuugaConstants;
import io.github.kuugasky.kuugatool.core.date.DateFormat;
import io.github.kuugasky.kuugatool.core.date.DateUtil;
import io.github.kuugasky.kuugatool.core.function.ObjectToMapFunc;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import io.github.kuugasky.kuugatool.core.string.StringJoinerUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.Objects;

/**
 * CommonWebUtil
 *
 * @author kuuga
 * @since 2020-12-25 下午12:32
 */
public final class CommonWebUtil {

    private static final Logger logger = LoggerFactory.getLogger(CommonWebUtil.class);

    private static final String CONTENT_TYPE_APPLICATION_JSON_UTF8 = "application/json; charset=utf-8";

    private static final String CONTENT_TYPE_APPLICATION_JSON = "application/json";

    /**
     * 判断HttpServletRequest是否ajax请求
     *
     * @param request HttpServletRequest实例
     * @return boolean
     */
    public static boolean isXmlHttpRequest(HttpServletRequest request) {
        return "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"));
    }

    /**
     * 获取请求中的xml参数
     */
    public static String getXmlParams(HttpServletRequest request) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
            String buffer;
            // 存放请求内容
            StringBuilder xml = new StringBuilder();

            while ((buffer = br.readLine()) != null) {
                // 在页面中显示读取到的请求参数
                xml.append(buffer);
            }
            return xml.toString();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            return StringUtil.EMPTY;
        }
    }

    /**
     * 读取post请求的json数据
     */
    public static String getJsonParams(HttpServletRequest request) {
        StringBuilder buffer = new StringBuilder();
        String line;
        String json = StringUtil.EMPTY;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            reader.close();
            json = buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
        }
        return json;
    }

    /**
     * 获取request请求中的参数，转换为int值
     *
     * @param name         参数名
     * @param defaultValue 默认值
     * @return int
     */
    public static int getInt(String name, int defaultValue) {
        String resultStr = getRequestParameter(name);
        if (resultStr != null) {
            try {
                return Integer.parseInt(resultStr);
            } catch (Exception e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    /**
     * 获取request请求中的参数，转换为bigDecimal值
     *
     * @param name         参数名
     * @param defaultValue 默认值
     * @return bigDecimal
     */
    public static BigDecimal getBigDecimal(String name, BigDecimal defaultValue) {
        String resultStr = getRequestParameter(name);
        if (resultStr != null) {
            try {
                return BigDecimal.valueOf(Double.parseDouble(resultStr));
            } catch (Exception e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    /**
     * 获取request请求中的参数，转换为String值
     *
     * @param name         参数名
     * @param defaultValue 默认值
     * @return String
     */
    public static String getString(String name, String defaultValue) {
        String resultStr = getRequestParameter(name);
        if (resultStr == null || StringUtil.EMPTY.equals(resultStr) || KuugaConstants.NULL.equals(resultStr) || KuugaConstants.UNDEFINED.equals(resultStr)) {
            return defaultValue;
        } else {
            return resultStr;
        }
    }

    /**
     * 获取请求中的参数，转换为Boolean
     *
     * @param name         参数名
     * @param defaultValue 默认值
     * @return Boolean
     */
    public static Boolean getBoolean(String name, boolean defaultValue) {
        String resultStr = getRequestParameter(name);
        if (resultStr == null || StringUtil.EMPTY.equals(resultStr.trim()) || KuugaConstants.NULL.equals(resultStr.toLowerCase().trim()) || KuugaConstants.UNDEFINED.equals(resultStr.toLowerCase().trim())) {
            return defaultValue;
        } else {
            return "1".equals(resultStr.trim()) || "true".equalsIgnoreCase(resultStr.trim());
        }
    }

    /**
     * 获取请求中的参数，转换为float
     *
     * @param name         参数名
     * @param defaultValue 默认值
     * @return double
     */
    public static float getFloat(String name, float defaultValue) {
        String resultStr = getRequestParameter(name);
        if (resultStr != null) {
            try {
                return Float.parseFloat(resultStr);
            } catch (Exception e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    /**
     * 获取请求中的参数，转换为double
     *
     * @param name         参数名
     * @param defaultValue 默认值
     * @return double
     */
    public static double getDouble(String name, double defaultValue) {
        String resultStr = getRequestParameter(name);
        if (resultStr != null) {
            try {
                return Double.parseDouble(resultStr);
            } catch (Exception e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }

    /**
     * 获取请求中的参数，转换为Date
     *
     * @param date       参数名
     * @param dateFormat 日期格式
     * @return Date
     */
    public static Date getDate(String date, DateFormat dateFormat) {
        String dateStr = getString(date, null);
        if (StringUtil.isEmpty(dateStr)) {
            return null;
        }
        return DateUtil.parse(dateFormat, dateStr);
    }

    /**
     * 获取请求中的cookie
     *
     * @param cookieName cookieName
     * @return Cookie
     */
    public static Cookie getCookie(String cookieName) {
        return CookieUtil.getCookie(getRequest(), cookieName);
    }

    /**
     * 输入流读取行到字符串【只读流读取】
     *
     * @param is InputStream
     * @return string
     * @throws IOException IO异常
     */
    public static String inputStreamReadLineToString(InputStream is) throws IOException {
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        StringBuilder sb = new StringBuilder();
        String s;
        while ((s = br.readLine()) != null) {
            sb.append(s);
        }
        return sb.toString();
    }

    /**
     * 输入流到字符串【字节流读取】
     *
     * @param is InputStream
     * @return string
     * @throws IOException IO异常
     */
    public static String inputStreamToString(InputStream is) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i;
        while ((i = is.read()) != -1) {
            byteArrayOutputStream.write(i);
        }
        return byteArrayOutputStream.toString();
    }

    /**
     * 获取请求中的HttpServletRequest对象
     *
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    }

    /**
     * 获取请求中的HttpServletResponse对象
     *
     * @return HttpServletResponse
     */
    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
    }

    /**
     * 获取请求头参数
     *
     * @param name header name
     * @return a containing the value of the requested header, or null if the request does not have a header of that name
     */
    public static String getHeader(String name) {
        return getRequest().getHeader(name);
    }

    /**
     * 获取请求中的ServletContext对象
     *
     * @return ServletContext
     */
    public static ServletContext getServletContext() {
        WebApplicationContext currentWebApplicationContext = ContextLoader.getCurrentWebApplicationContext();
        if (ObjectUtil.nonNull(currentWebApplicationContext)) {
            return currentWebApplicationContext.getServletContext();
        }
        return null;
    }

    /**
     * 获取请求参数集MAP
     *
     * @return Map<String, String>
     */
    public static Map<String, String> getParameterMap() {
        Map<String, String> map = MapUtil.newHashMap();
        Enumeration<String> paramNames = getRequest().getParameterNames();
        while (paramNames.hasMoreElements()) {
            String paramName = paramNames.nextElement();
            String[] paramValues = getRequest().getParameterValues(paramName);
            map.put(paramName, StringJoinerUtil.join(paramValues));
            // StringUtils.join(paramValues, ",")
        }
        return map;
    }

    /**
     * 获取request中的参数
     *
     * @param name 参数名
     * @return 参数值
     */
    private static String getRequestParameter(String name) {
        HttpServletRequest request = getRequest();
        return request.getParameter(name);
    }

    /**
     * 获得主机环境
     *
     * @return String
     */
    public static String getHostContext() {
        StringBuffer url = getRequest().getRequestURL();
        String uri = getRequest().getRequestURI();

        return url.delete(url.length() - uri.length(), url.length())
                .append(getRequest().getContextPath())
                .append("/").toString();
    }

    /**
     * 输出提示json
     *
     * @param response       response
     * @param responseStatus 响应状态码
     * @param function       对象转map函数
     */
    public static void printJsonMsg(HttpServletResponse response, int responseStatus, ObjectToMapFunc function) {
        try {
            response.setContentType(CONTENT_TYPE_APPLICATION_JSON);
            // http状态码
            response.setStatus(responseStatus);
            response.setCharacterEncoding(KuugaCharsets.UTF_8);
            Map<String, Object> result = MapUtil.newHashMap();
            function.process(result);
            String resultMsg = JSON.toJSONString(result);
            response.getWriter().write(resultMsg);
        } catch (IOException e) {
            // log.error(e);
        }
    }

    /**
     * 函数功能说明 ：将实体转换为Json格式输出 <br/>
     *
     * @param response response
     * @param object   object
     */
    public static void responseJson(HttpServletResponse response, Object object) {
        String jsonStr = JSON.toJSONString(object, DateFormat.yyyy_MM_dd_HH_mm_ss.format());
        response.setCharacterEncoding(KuugaCharsets.UTF_8);
        response.setContentType(CONTENT_TYPE_APPLICATION_JSON_UTF8);

        try (PrintWriter p = response.getWriter()) {
            p.write(jsonStr);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

}
