package io.github.kuugasky.design.apply.demo2;

/**
 * CompositeModeTest
 *
 * @author kuuga
 * @since 2023/10/12 18:57
 */
public class CompositeModeTest {

    public static void main(String[] args) {
        // 正常价
        System.out.println(new CashContext(1).getResult(100, 2));
        // 先打8折，再满300返100
        System.out.println(new CashContext(2).getResult(375, 1));
        // 先满200返50，再打7折
        System.out.println(new CashContext(3).getResult(200, 1));

    }

}
