package io.github.kuugasky.kuugatool.core.kuuga.test;

/**
 * TureAndThenFunction
 *
 * @author kuuga
 * @since 2022/11/21-11-21 09:27
 */
public interface TrueAndThenFunction {

    void andThen(Runnable trueHandle);

}
