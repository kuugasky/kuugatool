package io.github.kuugasky.kuugatool.cache.redis;

/**
 * 自定义redis行为接口
 *
 * @author kuuga
 * @since 2023-06-15
 */
public interface KuugaCacheAction<T> {

    /**
     * redis自定义操作
     *
     * @param actionExecutor 行动执行器
     * @return 操作结果
     */
    Object doAction(T actionExecutor);

}