package io.github.kuugasky.design.behavior.strategy.demo2;

import java.util.List;

/**
 * Strategy
 *
 * @author kuuga
 * @since 2023/9/4-09-04 09:05
 */
public interface Strategy {
    /**
     * 将股票列表排序
     *
     * @param source 源数据
     * @return 排序后的榜单
     */
    List<Stock> sort(List<Stock> source);
}
