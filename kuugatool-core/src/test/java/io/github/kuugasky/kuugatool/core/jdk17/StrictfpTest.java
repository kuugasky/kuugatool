package io.github.kuugasky.kuugatool.core.jdk17;

/**
 * StrictfpTest
 *
 * @author kuuga
 * @since 2022/8/8 12:27
 */
public class StrictfpTest {

    public static void main(String[] args) {
        System.out.println("sum: " + testStrictfp());

    }

    /**
     * JEP 306： 恢复始终严格的浮点语义
     * 既然是恢复严格的浮点语义，那么说明在某个时间点之前，是始终严格的浮点语义的。其实在 Java SE 1.2 之前，所有的浮点计算都是严格的，但是以当初的情况来看，过于严格的浮点计算在当初流行的 x86 架构和 x87 浮点协议处理器上运行，需要大量的额外的指令开销，所以在 Java SE 1.2 开始，需要手动使用关键字 strictfp（strict float point） 才能启用严格的浮点计算。
     * <p>
     * 但是在 2021 年的今天，硬件早已发生巨变，当初的问题已经不存在了，所以从 Java 17 开始，恢复了始终严格的浮点语义这一特性。
     * <p>
     * 扩展：strictfp 是 Java 中的一个关键字，大多数人可能没有注意过它，它可以用在类、接口或者方法上，被 strictfp 修饰的部分中的 float 和 double 表达式会进行严格浮点计算。
     *
     * @return double
     */
    public strictfp static double testStrictfp() {
        float aFloat = 0.6666666666666666666f;
        double aDouble = 0.88888888888888888d;
        return aFloat + aDouble;
    }

}
