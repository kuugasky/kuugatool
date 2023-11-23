package io.github.kuugasky.kuugatool.core.polling;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 轮询工具类
 *
 * @author kuuga
 */
public final class PollingUtil<T> {

    /**
     * 定义私有构造函数来屏蔽这个隐式公有构造函数
     */
    private PollingUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private List<T> elements;
    private int size = 0;

    private PollingUtil(List<T> list) {
        this.elements = list;
        this.size = list.size();
    }

    private static final ReentrantLock REENTRANT_LOCK = new ReentrantLock();

    private final static AtomicInteger CURRENT_PLACE = new AtomicInteger(1);

    /**
     * 初始化轮询器
     *
     * @param list 集合
     * @param <T>  泛型
     * @return 轮询器
     */
    public static <T> PollingUtil<T> init(List<T> list) {
        if (ListUtil.isEmpty(list)) {
            return new PollingUtil<>();
        }
        return new PollingUtil<>(list);
    }

    /**
     * 从轮询器中获取元素
     *
     * @return 元素
     */
    public T get() {
        REENTRANT_LOCK.lock();
        try {
            if (size <= 0) {
                return null;
            }
            T element;
            int i = CURRENT_PLACE.get();
            if (i <= elements.size()) {
                element = elements.get(i - 1);
                int nextIndex = i + 1;
                CURRENT_PLACE.set(nextIndex > size ? 1 : nextIndex);
                return element;
            } else {
                CURRENT_PLACE.set(0);
                return elements.get(0);
            }
        } finally {
            REENTRANT_LOCK.unlock();
        }
    }

}
