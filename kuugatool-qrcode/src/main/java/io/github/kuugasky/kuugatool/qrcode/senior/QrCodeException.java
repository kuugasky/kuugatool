package io.github.kuugasky.kuugatool.qrcode.senior;

import io.github.kuugasky.kuugatool.core.exception.ExceptionUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;

import java.io.Serial;

/**
 * Qrcode异常
 *
 * @author xiaoleilu
 */
public class QrCodeException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 8247610319171014183L;

    public QrCodeException(Throwable e) {
        super(ExceptionUtil.getMessage(e), e);
    }

    public QrCodeException(String message) {
        super(message);
    }

    public QrCodeException(String messageTemplate, Object... params) {
        super(StringUtil.format(messageTemplate, params));
    }

    public QrCodeException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public QrCodeException(Throwable throwable, String messageTemplate, Object... params) {
        super(StringUtil.format(messageTemplate, params), throwable);
    }
}
