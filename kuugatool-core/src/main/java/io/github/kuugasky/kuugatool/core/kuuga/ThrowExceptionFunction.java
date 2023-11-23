package io.github.kuugasky.kuugatool.core.kuuga;

/**
 * ThrowExceptionFunction
 * <p>
 * 抛异常接口
 *
 * @author kuuga
 * @since 2022/11/17-11-17 20:36
 */
@FunctionalInterface
public interface ThrowExceptionFunction {

    /**
     * 抛出异常信息
     *
     * @param message 异常信息
     **/
    void throwMessage(String message);

}
