package io.github.kuugasky.design.behavior.strategy.demo2;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * HighRiseRank
 * <p>
 * public class HighRiseRank implements Strategy {
 *
 * @author kuuga
 * @Override public List<Stock> sort(List<Stock> source) {
 * return source.stream()
 * .sorted(Comparator.comparing(Stock::getRise).reversed())
 * .collect(Collectors.toList());
 * }
 * }
 * @since 2023/9/4-09-04 09:06
 */
public class HighRiseRank implements Strategy {

    @Override
    public List<Stock> sort(List<Stock> source) {
        return source.stream()
                .sorted(Comparator.comparing(Stock::getRise).reversed())
                .collect(Collectors.toList());
    }

}