package io.github.kuugasky.kuugatool.pdf.exception;

/**
 * BaseException
 *
 * @author kuuga
 * @since 2023-06-12
 */
public class BaseException extends RuntimeException {

    /**
     * 错误码
     */
    public int errorCode;
    /**
     * 错误消息
     */
    public String errorMsg;

    /**
     * 创建异常
     */
    public BaseException() {
        super("运行时异常");
    }

    /**
     * 创建异常
     *
     * @param errorCode 自定义异常码
     * @param errorMsg  自定义异常内容
     */
    public BaseException(int errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    /**
     * 创建异常
     *
     * @param errorMsg 自定义异常内容
     */
    public BaseException(String errorMsg) {
        super(errorMsg);
        this.errorCode = 500;
        this.errorMsg = errorMsg;
    }

    /**
     * 创建异常
     *
     * @param errorMsg 自定义异常内容
     * @param e        异常
     */
    public BaseException(String errorMsg, Exception e) {
        super(errorMsg, e);
        this.errorCode = 500;
        this.errorMsg = errorMsg;
    }

}
