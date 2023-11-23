package io.github.kuugasky.design.behavior.strategy.demo2;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Test
 *
 * @author kuuga
 * @since 2023/9/4-09-04 09:08
 */
public class Test {
    public static void main(String[] args) {
        List<Stock> list = new ArrayList<>();
        list.add(Stock.builder().code("A1").price(1D).rise(1D).build());
        list.add(Stock.builder().code("A3").price(3D).rise(3D).build());
        list.add(Stock.builder().code("A2").price(2D).rise(5D).build());

        System.out.println("HighPrice:" + getRank("HighPrice", list).stream().map(Stock::getCode).collect(Collectors.joining(",")));
        System.out.println("LowPrice:" + getRank("LowPrice", list).stream().map(Stock::getCode).collect(Collectors.joining(",")));
        System.out.println("HighRise:" + getRank("HighRise", list).stream().map(Stock::getCode).collect(Collectors.joining(",")));
    }

    private static List<Stock> getRank(String rankType, List<Stock> list) {
        // 创建上下文
        Context context = new Context();
        // 这里选择策略
        switch (rankType) {
            case "HighPrice" -> context.setStrategy(new HighPriceRank());
            case "LowPrice" -> context.setStrategy(new LowPriceRank());
            case "HighRise" -> context.setStrategy(new HighRiseRank());
            default -> throw new IllegalArgumentException("rankType not found");
        }
        // 然后执行策略
        return context.getRank(list);
    }

}
