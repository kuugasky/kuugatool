package io.github.kuugasky.kuugatool.extra.entity;

import org.apache.http.HttpStatus;

/**
 * ResponseBody
 *
 * @author kuuga
 * @since 2020-12-25 下午12:35
 */
public class ResponseBody {

    private int status = HttpStatus.SC_UNAUTHORIZED;
    private String code;
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
