package io.github.kuugasky.kuugatool.core.function;

/**
 * TrinocularExpressionFunc
 *
 * @author kuuga
 * @since 2021/6/8
 */
@FunctionalInterface
public interface TrinocularExpressionFunc {

    /**
     * 表达式判断
     *
     * @return 布尔值
     */
    boolean judge();

}
