package io.github.kuugasky.kuugatool.crypto.exception;

/**
 * DecryptionException
 *
 * @author kuuga
 * @since 2023/12/19 20:59
 */
public class DecryptionException extends RuntimeException {

    public DecryptionException() {
        super();
    }

    public DecryptionException(String message) {
        super(message);
    }

    public DecryptionException(String message, Throwable cause) {
        super(message, cause);
    }

    public DecryptionException(Throwable cause) {
        super(cause);
    }

    protected DecryptionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
