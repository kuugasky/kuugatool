package io.github.kuugasky.kuugatool.extra.web;

import io.github.kuugasky.kuugatool.core.collection.ArrayUtil;
import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cookie工具类
 *
 * @author kuuga
 */
public final class CookieUtil {

    /**
     * 获取请求中的cookie
     *
     * @param request    request
     * @param cookieName cookieName
     * @return Cookie
     */
    public static Cookie getCookie(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = getCookies(request);
        if (ArrayUtil.isEmpty(cookies)) {
            return null;
        }

        return ListUtil.optimize(cookies)
                .stream()
                .filter(cookie -> cookieName.equals(cookie.getName()))
                .findFirst()
                .orElse(null);
    }

    /**
     * 获取request中的cookie
     *
     * @param request request
     * @return Cookie[]
     */
    public static Cookie[] getCookies(HttpServletRequest request) {
        return request.getCookies();
    }

    /**
     * 得到Cookie的值
     *
     * @param request    request
     * @param cookieName cookieName
     * @return String
     */
    public static String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie cookie = getCookie(request, cookieName);
        if (ObjectUtil.isNull(cookie)) {
            return null;
        }
        return cookie.getValue();
    }

    /**
     * 添加cookie
     *
     * @param resp        response
     * @param cookieName  cookieName
     * @param cookieValue cookieValue
     * @param second      失效时间，单位秒[-1表示永久保存]
     * @return cookie
     */
    public static Cookie addCookie(HttpServletResponse resp, String cookieName, String cookieValue, int second) {
        return addCookie(resp, cookieName, cookieValue, null, null, second);
    }

    /**
     * 添加cookie
     *
     * @param resp        response
     * @param cookieName  cookieName
     * @param cookieValue cookieValue
     * @param domain      domain
     * @param second      失效时间，单位秒[-1表示永久保存]
     * @return cookie
     */
    public static Cookie addCookie(HttpServletResponse resp, String cookieName, String cookieValue, String domain, int second) {
        return addCookie(resp, cookieName, cookieValue, domain, null, second);
    }

    /**
     * 添加cookie
     *
     * @param resp        response
     * @param cookieName  cookieName
     * @param cookieValue cookieValue
     * @param domain      domain
     * @param path        path
     * @param second      失效时间，单位秒[-1表示永久保存]
     * @return cookie
     */
    public static Cookie addCookie(HttpServletResponse resp, String cookieName, String cookieValue, String domain, String path, int second) {
        Cookie ck = new Cookie(cookieName, cookieValue);
        ck.setMaxAge(second);
        if (StringUtil.hasText(domain)) {
            ck.setDomain(domain);
        }
        if (StringUtil.hasText(path)) {
            ck.setPath(path);
        }
        resp.addCookie(ck);
        return ck;
    }

    /**
     * 删除Cookie
     *
     * @param request    request
     * @param response   response
     * @param cookieName cookieName
     */
    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
        deleteCookie(request, response, cookieName, null, null, false);
    }

    /**
     * 删除Cookie
     *
     * @param request      request
     * @param response     response
     * @param cookieName   cookieName
     * @param cookieDomain cookieDomain
     * @param cookiePath   cookiePath
     * @param readOnly     readOnly
     */
    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, String cookieDomain, String cookiePath, boolean readOnly) {
        Cookie cookie = getCookie(request, cookieName);
        if (null == cookie) {
            return;
        }
        cookie.setValue(null);
        cookie.setMaxAge(-1);
        if (StringUtil.hasText(cookieDomain)) {
            cookie.setDomain(cookieDomain);
        }
        if (StringUtil.hasText(cookiePath)) {
            cookie.setPath(cookiePath);
        }
        if (readOnly) {
            cookie.setHttpOnly(true);
        }
        response.addCookie(cookie);
    }

}
