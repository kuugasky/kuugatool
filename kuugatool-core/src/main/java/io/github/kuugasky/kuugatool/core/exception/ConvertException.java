package io.github.kuugasky.kuugatool.core.exception;

import io.github.kuugasky.kuugatool.core.string.StringUtil;

import java.io.Serial;

/**
 * 转换异常
 *
 * @author xiaoleilu
 */
public class ConvertException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 4730597402855274362L;

    public ConvertException(Throwable e) {
        super(ExceptionUtil.getMessage(e), e);
    }

    public ConvertException(String message) {
        super(message);
    }

    public ConvertException(String messageTemplate, Object... params) {
        super(StringUtil.format(messageTemplate, params));
    }

    public ConvertException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public ConvertException(Throwable throwable, String messageTemplate, Object... params) {
        super(StringUtil.format(messageTemplate, params), throwable);
    }

}
