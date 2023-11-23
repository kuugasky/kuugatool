package io.github.kuugasky.kuugatool.core.io.watch;

import io.github.kuugasky.kuugatool.core.exception.ExceptionUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;

import java.io.Serial;

/**
 * 监听异常
 *
 * @author Looly
 */
public class WatchException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 8068509879445395353L;

    public WatchException(Throwable e) {
        super(ExceptionUtil.getMessage(e), e);
    }

    public WatchException(String message) {
        super(message);
    }

    public WatchException(String messageTemplate, Object... params) {
        super(StringUtil.format(messageTemplate, params));
    }

    public WatchException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public WatchException(Throwable throwable, String messageTemplate, Object... params) {
        super(StringUtil.format(messageTemplate, params), throwable);
    }
}
