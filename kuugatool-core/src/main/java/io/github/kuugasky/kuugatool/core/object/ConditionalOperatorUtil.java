package io.github.kuugasky.kuugatool.core.object;

import io.github.kuugasky.kuugatool.core.function.ConditionalOperatorFunc;

/**
 * ConditionalOperatorUtil
 * <p>
 * 三目表达式工具类
 *
 * @author kuuga
 * @since 2021/6/8
 */
public final class ConditionalOperatorUtil {

    /**
     * 定义私有构造函数来屏蔽这个隐式公有构造函数
     */
    private ConditionalOperatorUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static <T> T judge(ConditionalOperatorFunc expressionFunc, T value, T otherValue) {
        if (expressionFunc.judge()) {
            return value;
        } else {
            return otherValue;
        }
    }

}
