package io.github.kuugasky.kuugatool.crypto.exception;

/**
 * EncryptionException
 *
 * @author kuuga
 * @since 2023/12/19 20:55
 */
public class EncryptionException extends RuntimeException {

    public EncryptionException() {
        super();
    }

    public EncryptionException(String message) {
        super(message);
    }

    public EncryptionException(String message, Throwable cause) {
        super(message, cause);
    }

    public EncryptionException(Throwable cause) {
        super(cause);
    }

    protected EncryptionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
