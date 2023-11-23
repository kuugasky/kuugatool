package io.github.kuugasky.kuugatool.extra.web;

import io.github.kuugasky.kuugatool.core.date.DateFormat;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

class CommonWebUtilTest {

    @Test
    void getInt() {
        System.out.println(CommonWebUtil.getInt("name", 1));
    }

    @Test
    void getBigDecimal() {
        System.out.println(CommonWebUtil.getBigDecimal("name", new BigDecimal(1)));
    }

    @Test
    void getString() {
        System.out.println(CommonWebUtil.getString("name", "1"));
    }

    @Test
    void getCookie() {
        System.out.println(CommonWebUtil.getCookie("name"));
    }

    @Test
    void getBoolean() {
        System.out.println(CommonWebUtil.getBoolean("name", true));
    }

    @Test
    void getDouble() {
        System.out.println(CommonWebUtil.getDouble("name", 1D));
    }

    @Test
    void getFloat() {
        System.out.println(CommonWebUtil.getFloat("name", 1f));
    }

    @Test
    void getDate() {
        System.out.println(CommonWebUtil.getDate("name", DateFormat.yyyy_MM_dd));
    }

    @Test
    void inputStreamReadLineToString() throws IOException {
        System.out.println(CommonWebUtil.inputStreamReadLineToString(new InputStream() {
            @Override
            public int read() {
                return 0;
            }
        }));
    }

    @Test
    void inputStreamToString() throws IOException {
        System.out.println(CommonWebUtil.inputStreamToString(new InputStream() {
            @Override
            public int read() {
                return 0;
            }
        }));
    }

    @Test
    void getRequest() {
        System.out.println(CommonWebUtil.getRequest());
    }

    @Test
    void getHeader() {
        System.out.println(CommonWebUtil.getHeader("x-sid"));
    }

    @Test
    void getResponse() {
        System.out.println(CommonWebUtil.getResponse());
    }

    @Test
    void getServletContext() {
        System.out.println(CommonWebUtil.getServletContext());
    }

    @Test
    void getParameterMap() {
        System.out.println(CommonWebUtil.getParameterMap());
    }

    @Test
    void getHostContext() {
        System.out.println(CommonWebUtil.getHostContext());
    }

    @Test
    void printJsonMsg() {
        String code = "E0000";
        String message = "操作成功";
        CommonWebUtil.printJsonMsg(CommonWebUtil.getResponse(), 200, map -> {
            map.put("status", code);
            map.put("message", message);
        });
    }

    @Test
    void responseJson() {
        CommonWebUtil.responseJson(CommonWebUtil.getResponse(), null);
    }

    @Test
    void isXmlHttpRequest() {
        HttpServletRequest request = CommonWebUtil.getRequest();
        System.out.println(CommonWebUtil.isXmlHttpRequest(request));
    }

    @Test
    void getXmlParams() {
        HttpServletRequest request = CommonWebUtil.getRequest();
        System.out.println(CommonWebUtil.getXmlParams(request));
    }

    @Test
    void getJsonParams() {
        HttpServletRequest request = CommonWebUtil.getRequest();
        System.out.println(CommonWebUtil.getJsonParams(request));
    }

}