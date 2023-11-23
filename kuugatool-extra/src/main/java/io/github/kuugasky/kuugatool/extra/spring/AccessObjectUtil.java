package io.github.kuugasky.kuugatool.extra.spring;

import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;

/**
 * AccessObjectUtil
 * 访问对象工具类
 *
 * @author kuuga
 */
public final class AccessObjectUtil {

    /**
     * 打印{@link @RequestBody}请求参数
     * 使用前需要先将request进行封装
     * ContentCachingRequestWrapper wrapperRequest = new ContentCachingRequestWrapper(request);
     * ContentCachingResponseWrapper wrapperResponse = new ContentCachingResponseWrapper(response);
     * filterChain.doFilter(wrapperRequest, wrapperResponse);
     *
     * @param request request
     */
    public static String getRequestBody(ContentCachingRequestWrapper request) {
        ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        if (wrapper == null) {
            return StringUtil.EMPTY;
        }
        byte[] buf = wrapper.getContentAsByteArray();
        if (buf.length > 0) {
            String payload;
            try {
                payload = new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
            } catch (UnsupportedEncodingException e) {
                payload = "[unknown]";
            }
            return payload.replaceAll("\\n", StringUtil.EMPTY);
        }
        return null;
    }

    /**
     * 打印{@link @RequestBody}响应参数
     * 使用前需要先将response进行封装
     * ContentCachingRequestWrapper wrapperRequest = new ContentCachingRequestWrapper(request);
     * ContentCachingResponseWrapper wrapperResponse = new ContentCachingResponseWrapper(response);
     * filterChain.doFilter(wrapperRequest, wrapperResponse);
     *
     * @param response response
     */
    public static String getResponseBody(ContentCachingResponseWrapper response) {
        ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        if (ObjectUtil.isNull(wrapper)) {
            return null;
        }
        byte[] buf = wrapper.getContentAsByteArray();
        if (buf.length > 0) {
            String payload;
            try {
                payload = new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
            } catch (UnsupportedEncodingException e) {
                payload = "[unknown]";
            }
            return payload;
        }
        return null;
    }

    /**
     * 获取请求地址上的参数
     *
     * @param request request
     * @return str
     */
    public static String getRequestParams(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        Enumeration<String> enu = request.getParameterNames();
        // 获取请求参数
        while (enu.hasMoreElements()) {
            String name = enu.nextElement();
            sb.append(name).append("=").append(request.getParameter(name));
            if (enu.hasMoreElements()) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

}
