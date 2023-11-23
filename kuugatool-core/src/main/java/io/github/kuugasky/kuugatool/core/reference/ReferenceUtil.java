package io.github.kuugasky.kuugatool.core.reference;

import java.lang.ref.*;

/**
 * 引用工具类，主要针对{@link Reference} 工具化封装<br>
 * 主要封装包括：
 * <pre>
 * 1. {@link SoftReference} 软引用，在GC报告内存不足时会被GC回收
 * 2. {@link WeakReference} 弱引用，在GC时发现弱引用会回收其对象
 * 3. {@link PhantomReference} 虚引用，在GC时发现虚引用对象，会将{@link PhantomReference}插入{@link ReferenceQueue}。 此时对象未被真正回收，要等到{@link ReferenceQueue}被真正处理后才会被回收。
 * </pre>
 *
 * @author looly
 * @since 3.1.2
 */
public class ReferenceUtil {

    /**
     * 定义私有构造函数来屏蔽这个隐式公有构造函数
     */
    private ReferenceUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 获得引用
     *
     * @param <T>      被引用对象类型
     * @param type     引用类型枚举
     * @param referent 被引用对象
     * @return {@link Reference}
     */
    public static <T> Reference<T> create(ReferenceType type, T referent) {
        return create(type, referent, null);
    }

    /**
     * 获得引用
     *
     * @param <T>      被引用对象类型
     * @param type     引用类型枚举
     * @param referent 被引用对象
     * @param queue    引用队列
     * @return {@link Reference}
     */
    public static <T> Reference<T> create(ReferenceType type, T referent, ReferenceQueue<T> queue) {
        return switch (type) {
            case SOFT -> new SoftReference<>(referent);
            case WEAK -> new WeakReference<>(referent);
            case PHANTOM -> new PhantomReference<>(referent, queue);
        };
    }

    /**
     * 引用类型
     *
     * @author looly
     */
    public enum ReferenceType {
        /**
         * 软引用，在GC报告内存不足时会被GC回收
         */
        SOFT,
        /**
         * 弱引用，在GC时发现弱引用会回收其对象
         */
        WEAK,
        /**
         * 虚引用，在GC时发现虚引用对象，会将{@link PhantomReference}插入{@link ReferenceQueue}。 <br>
         * 此时对象未被真正回收，要等到{@link ReferenceQueue}被真正处理后才会被回收。
         */
        PHANTOM
    }

}
