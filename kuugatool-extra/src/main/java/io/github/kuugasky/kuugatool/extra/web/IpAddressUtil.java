package io.github.kuugasky.kuugatool.extra.web;

import io.github.kuugasky.kuugatool.core.constants.KuugaConstants;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * IP地址工具类
 *
 * @author kuuga
 * @since 2020-12-25 上午11:33
 */
@Slf4j
public final class IpAddressUtil {

    private static final String X_FORWARDED_FOR = "x-forwarded-for";
    private static final String PROXY_CLIENT_IP = "Proxy-Client-IP";
    private static final String WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";

    /**
     * 获取IP地址数组
     *
     * @param request request
     * @return String[]
     */
    public static String[] getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader(X_FORWARDED_FOR);
        if (StringUtil.hasText(ip)) {
            ip = ip.replaceAll(KuugaConstants.SPACE, StringUtil.EMPTY);
        }
        if (isInvalidIp(ip)) {
            ip = request.getHeader(PROXY_CLIENT_IP);
        }
        if (isInvalidIp(ip)) {
            ip = request.getHeader(WL_PROXY_CLIENT_IP);
        }
        if (isInvalidIp(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip.split(KuugaConstants.COMMAENG);
    }

    private static boolean isInvalidIp(String ip) {
        return StringUtil.isEmpty(ip) || KuugaConstants.UNKNOWN.equalsIgnoreCase(ip);
    }

}
