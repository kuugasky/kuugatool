package io.github.kuugasky.kuugatool.pdf.exception;

/**
 * BaseException
 *
 * @author kuuga
 * @date 2023-06-12
 */
public class BaseException extends RuntimeException {

    public int errorCode;
    public String errorMsg;

    public BaseException() {
        super("运行时异常");
    }

    public BaseException(int errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public BaseException(String errorMsg) {
        super(errorMsg);
        this.errorCode = 500;
        this.errorMsg = errorMsg;
    }

    public BaseException(String errorMsg, Exception e) {
        super(errorMsg, e);
        this.errorCode = 500;
        this.errorMsg = errorMsg;
    }

}
