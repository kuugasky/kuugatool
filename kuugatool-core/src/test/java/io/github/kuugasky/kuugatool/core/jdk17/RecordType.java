package io.github.kuugasky.kuugatool.core.jdk17;

/**
 * Record
 *
 * @author kuuga
 * @since 2022/8/8 09:58
 */
public class RecordType {

    /**
     * 使用record增强 Java 编程语言，record是充当不可变数据的透明载体的类。record可以被认为是名义元组。
     *
     * @param x x
     * @param y y
     */
    public record Point(int x, int y) {

    }

    record Range(int lo, int hi) {
        /**
         * 规范构造函数，用于验证其隐式形式参数
         *
         * @param lo lo
         * @param hi hi
         */
        Range {
            if (lo > hi)  // referring here to the implicit constructor parameters
                throw new IllegalArgumentException(String.format("(%d,%d)", lo, hi));
        }
    }

    record Rational(int num, int denom) {
        // /**
        //  * 规范化其形式参数的紧凑规范构造函数
        //  *
        //  * @param num   num
        //  * @param denom denom
        //  */
        // Rational {
        //     int gcd = gcd(num, denom);
        //     num /= gcd;
        //     denom /= gcd;
        // }

        /**
         * 此声明等效于传统的构造函数形式
         *
         * @param num   num
         * @param denom denom
         */
        Rational(int num, int denom) {
            // Normalization
            int gcd = gcd(num, denom);
            num /= gcd;
            denom /= gcd;
            // Initialization
            this.num = num;
            this.denom = denom;
        }

        int gcd(int num, int denom) {
            return 1;
        }
    }

    public static void main(String[] args) {
        Point point = new Point(1, 3);
        System.out.println(point.x());
        System.out.println(point.y());

        Range range = new Range(2, 1);
    }

    // 反编译
    // public final class Point extends java.lang.Record {
    //     private final int x;
    //     private final int y;
    //
    //     public Point(int x, int y) { /* compiled code */ }
    //
    //     public java.lang.String toString() { /* compiled code */ }
    //
    //     public final int hashCode() { /* compiled code */ }
    //
    //     public final boolean equals(java.lang.Object o) { /* compiled code */ }
    //
    //     public int x() { /* compiled code */ }
    //
    //     public int y() { /* compiled code */ }
    // }


}
