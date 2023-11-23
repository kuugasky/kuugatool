package io.github.kuugasky.kuugatool.thirdparty.request.context.exception;

/**
 * 第三方请求异常类
 *
 * @author pengqinglong
 * @since 2022/3/15
 */
public class ThirdpartyRequestException extends RuntimeException {

    public ThirdpartyRequestException(String message) {
        super(message);
    }

}