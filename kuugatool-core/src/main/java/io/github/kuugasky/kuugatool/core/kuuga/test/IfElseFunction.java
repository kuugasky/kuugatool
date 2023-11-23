package io.github.kuugasky.kuugatool.core.kuuga.test;

/**
 * IfelseFunction
 *
 * @author kuuga
 * @since 2022/11/21-11-21 09:27
 */
public interface IfElseFunction {

    TrueAndThenFunction trueHandle();

    FalseAndThenFunction falseHandle();

}
