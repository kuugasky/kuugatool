package io.github.kuugasky.kuugatool.http.function;

/**
 * KuugaConsumer
 *
 * @author kuuga
 */
@FunctionalInterface
public interface KuugaConsumer<T, U> {

    /**
     * Performs this operation on the given argument.
     *
     * @param t the input argument
     * @return U
     */
    U accept(T t);

}
