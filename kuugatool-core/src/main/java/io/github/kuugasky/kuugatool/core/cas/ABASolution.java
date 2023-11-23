package io.github.kuugasky.kuugatool.core.cas;

import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * ABASolution
 * <p>
 * ABA解决方案
 *
 * @author kuuga
 * @since 2023/9/9-09-09 16:19
 */
public class ABASolution {

    public static void main(String[] args) {
        stampedType();
        markable();
    }

    private static void markable() {
        // 创建一个AtomicMarkableReference，初始值为初始对象（"Initial"）和标记（false）
        AtomicMarkableReference<String> atomicRef = new AtomicMarkableReference<>("Initial", false);

        // 线程1：尝试将值从"Initial"更新为"NewValue"，并标记为true
        boolean success1 = atomicRef.compareAndSet("Initial", "NewValue", false, true);

        // 线程2：将值从"NewValue"更新回"Initial"，并标记为true
        boolean success2 = atomicRef.compareAndSet("NewValue", "Initial", false, true);

        // 线程3：尝试将值从"Initial"更新为"AnotherValue"，并标记为false
        boolean success3 = atomicRef.compareAndSet("Initial", "AnotherValue", false, false);

        // 获取当前值和标记
        String currentValue = atomicRef.getReference();
        boolean currentMark = atomicRef.isMarked();

        System.out.println("Success1: " + success1); // 输出true，成功更新
        System.out.println("Success2: " + success2); // 输出true，成功更新
        System.out.println("Success3: " + success3); // 输出false，标记不匹配

        System.out.println("Current Value: " + currentValue); // 输出"Initial"
        System.out.println("Current Mark: " + currentMark); // 输出true
    }


    private static void stampedType() {
        AtomicStampedReference<Integer> atomicRef = new AtomicStampedReference<>(0, 0);

        int stamp = atomicRef.getStamp(); // 获取初始的版本号

        // 线程1：尝试将值从0更新为1
        boolean success1 = atomicRef.compareAndSet(0, 1, stamp, stamp + 1);

        // 线程2：将值从0更新为2，然后又更新回0
        atomicRef.compareAndSet(0, 2, stamp, stamp + 1);
        atomicRef.compareAndSet(2, 0, stamp + 1, stamp + 2);

        // 线程1再次尝试将值从0更新为1，此时检测到ABA问题，操作失败
        boolean success2 = atomicRef.compareAndSet(0, 1, stamp, stamp + 1);

        System.out.println("Success1: " + success1); // 输出true
        System.out.println("Success2: " + success2); // 输出false，检测到ABA问题
    }

}
