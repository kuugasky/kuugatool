package io.github.kuugasky.kuugatool.sql.exception;

import io.github.kuugasky.kuugatool.core.exception.ExceptionUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;

/**
 * SqlParseException
 *
 * @author kuuga
 * @since 2022-06-07
 */
public class SqlParseException extends RuntimeException {

    public SqlParseException(Throwable e) {
        super(ExceptionUtil.getMessage(e), e);
    }

    public SqlParseException(String message) {
        super(message);
    }

    public SqlParseException(String messageTemplate, Object... params) {
        super(StringUtil.format(messageTemplate, params));
    }

    public SqlParseException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public SqlParseException(Throwable throwable, String messageTemplate, Object... params) {
        super(StringUtil.format(messageTemplate, params), throwable);
    }

}
