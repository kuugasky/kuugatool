package io.github.kuugasky.kuugatool.extra.web;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class CookieUtilTest {

    @Test
    public void getCookie() {
        HttpServletRequest request = CommonWebUtil.getRequest();
        System.out.println(CookieUtil.getCookie(request, "test"));
    }

    @Test
    public void getCookies() {
        HttpServletRequest request = CommonWebUtil.getRequest();
        System.out.println(Arrays.toString(CookieUtil.getCookies(request)));
    }

    @Test
    public void getCookieValue() {
        HttpServletRequest request = CommonWebUtil.getRequest();
        System.out.println(CookieUtil.getCookieValue(request, "test"));
    }

    @Test
    public void addCookie() {
        HttpServletResponse response = CommonWebUtil.getResponse();
        Cookie cookie = CookieUtil.addCookie(response, "test", "value", -1);
        System.out.println(cookie);
    }

    @Test
    public void testAddCookie() {
        HttpServletResponse response = CommonWebUtil.getResponse();
        Cookie cookie = CookieUtil.addCookie(response, "test", "value", "www.baidu.com", -1);
        System.out.println(cookie);
    }

    @Test
    public void testAddCookie1() {
        HttpServletResponse response = CommonWebUtil.getResponse();
        CookieUtil.addCookie(response, "test", "value", "www.baidu.com", "path", -1);
    }

    @Test
    public void deleteCookie() {
        HttpServletRequest request = CommonWebUtil.getRequest();
        HttpServletResponse response = CommonWebUtil.getResponse();
        CookieUtil.deleteCookie(request, response, "test");
    }

    @Test
    public void testDeleteCookie() {
        HttpServletRequest request = CommonWebUtil.getRequest();
        HttpServletResponse response = CommonWebUtil.getResponse();
        CookieUtil.deleteCookie(request, response, "test", "domain", "path", true);
    }
}