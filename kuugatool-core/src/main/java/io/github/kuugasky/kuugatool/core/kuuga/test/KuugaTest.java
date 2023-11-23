package io.github.kuugasky.kuugatool.core.kuuga.test;

/**
 * KuugaTest
 *
 * @author kuuga
 * @since 2022/11/21-11-21 09:29
 */
public class KuugaTest {

    public static TrueAndThenFunction isTrue(boolean status) {
        return runnable -> {
            if (status) {
                runnable.run();
            }
        };
    }

    public static FalseAndThenFunction isFalse(boolean status) {
        return runnable -> {
            if (!status) {
                runnable.run();
            }
        };
    }

    public static IfElseFunction trueOrFalse(boolean status) {
        return new IfElseFunction() {
            @Override
            public TrueAndThenFunction trueHandle() {
                return Runnable::run;
            }

            @Override
            public FalseAndThenFunction falseHandle() {
                return Runnable::run;
            }
        };
    }

    public static void main(String[] args) {
        KuugaTest.isTrue(true).andThen(() -> System.out.println("yes"));
        KuugaTest.isFalse(false).andThen(() -> System.out.println("no"));
    }

}
