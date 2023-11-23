package io.github.kuugasky.kuugatool.core.kuuga;

import org.junit.jupiter.api.Test;

/**
 * KuugaTest
 *
 * @author kuuga
 * @since 2022/11/17-11-17 20:39
 */
public class KuugaTest {

    @Test
    void isTure() {
        Kuuga.isTure(true).throwMessage("异常了.");
    }

    @Test
    void isFalse() {
        Kuuga.isFalse(false).throwMessage("异常了.");
    }

    @Test
    void isTureOrFalse() {
        Kuuga.isTureOrFalse(true)
                .trueOrFalseHandle(
                        () -> System.out.println("true,俺要开始秀了"),
                        () -> System.out.println("false,秀不动了,快跑"));
    }

    @Test
    void isBLankOrNoBLank() {
        Kuuga.isBlankOrNoBlank("helLo")
                .presentOrElseHandle(
                        System.out::println,
                        () -> System.out.println("空字符串"));
    }

}
