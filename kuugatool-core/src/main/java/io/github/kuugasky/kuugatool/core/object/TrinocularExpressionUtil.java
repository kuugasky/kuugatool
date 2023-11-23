package io.github.kuugasky.kuugatool.core.object;

import io.github.kuugasky.kuugatool.core.function.TrinocularExpressionFunc;

/**
 * TrinocularExpressionUtil
 * 三目表达式工具类
 *
 * @author kuuga
 * @since 2021/6/8
 */
public final class TrinocularExpressionUtil {

    /**
     * 定义私有构造函数来屏蔽这个隐式公有构造函数
     */
    private TrinocularExpressionUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static <T> T judge(TrinocularExpressionFunc expressionFunc, T value, T otherValue) {
        if (expressionFunc.judge()) {
            return value;
        } else {
            return otherValue;
        }
    }

}
