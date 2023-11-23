package io.github.kuugasky.design.behavior.strategy.demo2;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * HighPriceRank
 * <p>
 * 高价榜
 *
 * @author kuuga
 * @since 2023/9/4-09-04 09:05
 */
public class HighPriceRank implements Strategy {

    @Override
    public List<Stock> sort(List<Stock> source) {
        return source.stream()
                .sorted(Comparator.comparing(Stock::getPrice).reversed())
                .collect(Collectors.toList());
    }

}
