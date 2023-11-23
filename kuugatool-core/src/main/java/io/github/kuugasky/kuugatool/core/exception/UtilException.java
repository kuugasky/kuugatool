package io.github.kuugasky.kuugatool.core.exception;

import io.github.kuugasky.kuugatool.core.string.StringUtil;

import java.io.Serial;

/**
 * 工具类异常
 *
 * @author xiaoleilu
 */
public class UtilException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 8247610319171014183L;

    public UtilException(Throwable e) {
        super(ExceptionUtil.getMessage(e), e);
    }

    public UtilException(String message) {
        super(message);
    }

    public UtilException(String messageTemplate, Object... params) {
        super(StringUtil.format(messageTemplate, params));
    }

    public UtilException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public UtilException(Throwable throwable, String messageTemplate, Object... params) {
        super(StringUtil.format(messageTemplate, params), throwable);
    }

}
