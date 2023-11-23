package io.github.kuugasky.kuugatool.core.exception;

import io.github.kuugasky.kuugatool.core.string.StringUtil;

import java.io.Serial;

/**
 * 加密异常
 *
 * @author Looly
 */
public class CryptoException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 8068509879445395353L;

    public CryptoException(Throwable e) {
        super(ExceptionUtil.getMessage(e), e);
    }

    public CryptoException(String message) {
        super(message);
    }

    public CryptoException(String messageTemplate, Object... params) {
        super(StringUtil.format(messageTemplate, params));
    }

    public CryptoException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public CryptoException(Throwable throwable, String messageTemplate, Object... params) {
        super(StringUtil.format(messageTemplate, params), throwable);
    }

}
