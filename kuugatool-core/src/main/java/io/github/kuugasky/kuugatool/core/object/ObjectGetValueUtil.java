package io.github.kuugasky.kuugatool.core.object;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Function;

/**
 * @author kuuga
 * @since 2022-02-11 18:06:10
 */
@Slf4j
public class ObjectGetValueUtil<T> {

    private final T source;

    private Function<T, String> function;

    public ObjectGetValueUtil(T source) {
        this.source = source;
    }

    public static <T> ObjectGetValueUtil<T> source(T source) {
        return new ObjectGetValueUtil<>(source);
    }

    public ObjectGetValueUtil<T> expectation(Function<T, String> function) {
        this.function = function;
        return this;
    }

    public String get() {
        return function.apply(source);
    }

    public String orElse(String target) {
        try {
            return get();
        } catch (Exception e) {
            log.error("ObjectGetValueUtil.getValue.error:{}", e.getMessage(), e);
            return target;
        }
    }

}
