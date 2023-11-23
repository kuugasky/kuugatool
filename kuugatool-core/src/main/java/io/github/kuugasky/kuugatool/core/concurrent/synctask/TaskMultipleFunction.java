package io.github.kuugasky.kuugatool.core.concurrent.synctask;

import java.util.List;

/**
 * 多个source任务函数接口
 *
 * @author pengqinglong
 */
@FunctionalInterface
public interface TaskMultipleFunction<T> {

    /**
     * 多任务调用
     *
     * @param sources 源数据集合
     * @return 结果集
     */
    List<? extends Result> call(List<T> sources);

}