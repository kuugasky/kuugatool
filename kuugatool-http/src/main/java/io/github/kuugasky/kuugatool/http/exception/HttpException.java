package io.github.kuugasky.kuugatool.http.exception;

/**
 * HttpException
 *
 * @author kuuga
 */
public class HttpException extends Exception {

    private final String message;

    public HttpException(final String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

}