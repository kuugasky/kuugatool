package io.github.kuugasky.kuugatool.core.function.namemapper;

import java.util.List;

/**
 * 查询目标数据集合函数
 *
 * @author kuuga
 * @since 2021/2/8
 */
@FunctionalInterface
public interface QueryTargetListFunc<R> {

    /**
     * 执行器
     *
     * @param sourceIds 源数据IDS
     * @return 目标数据集合
     */
    List<R> execute(List<String> sourceIds);

}
