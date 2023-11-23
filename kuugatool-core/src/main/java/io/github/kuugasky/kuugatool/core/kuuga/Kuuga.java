package io.github.kuugasky.kuugatool.core.kuuga;

/**
 * Kuuga
 *
 * @author kuuga
 * @since 2022/11/17-11-17 20:36
 */
public class Kuuga {

    /**
     * 如果参数为true抛出异常
     *
     * @param status status
     * @return com.example.demo.func.ThrowExceptionFunction
     **/
    public static ThrowExceptionFunction isTure(boolean status) {
        return (errorMessage) -> {
            if (status) {
                throw new RuntimeException(errorMessage);
            }
        };
    }

    /**
     * 如果参数为true抛出异常
     *
     * @param status status
     * @return com.example.demo.func.ThrowExceptionFunction
     **/
    public static ThrowExceptionFunction isFalse(boolean status) {
        return (errorMessage) -> {
            if (status) {
                throw new RuntimeException(errorMessage);
            }
        };
    }

    /**
     * 参数为true或false时，分别进行不同的操作
     *
     * @param b b
     * @return com.example.demo.func.BranchHandle
     **/
    public static BranchHandle isTureOrFalse(boolean b) {
        return (trueHandle, falseHandle) -> {
            if (b) {
                trueHandle.run();
            } else {
                falseHandle.run();
            }
        };
    }

    /**
     * 参数为true或false时，分别进行不同的操作
     *
     * @param str str
     * @return com.example.demo.func.BranchHandle
     **/
    public static PresentOrElseHandler<?> isBlankOrNoBlank(String str) {
        return (consumer, runnable) -> {
            if (str == null || str.isEmpty()) {
                runnable.run();
            } else {
                consumer.accept(str);
            }
        };
    }

}
