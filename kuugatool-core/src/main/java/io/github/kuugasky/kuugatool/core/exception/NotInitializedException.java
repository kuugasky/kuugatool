package io.github.kuugasky.kuugatool.core.exception;

import io.github.kuugasky.kuugatool.core.string.StringUtil;

import java.io.Serial;

/**
 * 未初始化异常
 *
 * @author xiaoleilu
 */
public class NotInitializedException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 8247610319171014183L;

    public NotInitializedException(Throwable e) {
        super(e);
    }

    public NotInitializedException(String message) {
        super(message);
    }

    public NotInitializedException(String messageTemplate, Object... params) {
        super(StringUtil.format(messageTemplate, new Object[]{params}));
    }

    public NotInitializedException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public NotInitializedException(Throwable throwable, String messageTemplate, Object... params) {
        super(StringUtil.format(messageTemplate, new Object[]{params}), throwable);
    }

}
