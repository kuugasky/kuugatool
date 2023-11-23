package io.github.kuugasky.kuugatool.core.collection;

import java.io.Serial;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 通过{@link ConcurrentHashMap}实现的线程安全HashSet
 *
 * @param <E> 元素类型
 * @author Looly
 * @since 3.1.0
 */
public class ConcurrentHashSet<E> extends AbstractSet<E> implements java.io.Serializable {

    @Serial
    private static final long serialVersionUID = 7997886765361607470L;

    /**
     * 持有对象。如果值为此对象表示有数据，否则无数据
     */
    private static final Boolean PRESENT = true;

    private final ConcurrentHashMap<E, Boolean> concurrentHashMap;

    // Constructor start ===============================================================================================

    /**
     * 构造<br>
     * 触发因子为默认的0.75
     */
    public ConcurrentHashSet() {
        concurrentHashMap = new ConcurrentHashMap<>();
    }

    /**
     * 构造<br>
     * 触发因子为默认的0.75
     *
     * @param initialCapacity 初始大小
     */
    public ConcurrentHashSet(int initialCapacity) {
        concurrentHashMap = new ConcurrentHashMap<>(initialCapacity);
    }

    /**
     * 构造
     *
     * @param initialCapacity 初始大小
     * @param loadFactor      加载因子。此参数决定数据增长时触发的百分比
     */
    public ConcurrentHashSet(int initialCapacity, float loadFactor) {
        concurrentHashMap = new ConcurrentHashMap<>(initialCapacity, loadFactor);
    }

    /**
     * 构造
     *
     * @param initialCapacity  初始大小
     * @param loadFactor       触发因子。此参数决定数据增长时触发的百分比
     * @param concurrencyLevel 线程并发度
     */
    public ConcurrentHashSet(int initialCapacity, float loadFactor, int concurrencyLevel) {
        concurrentHashMap = new ConcurrentHashMap<>(initialCapacity, loadFactor, concurrencyLevel);
    }

    /**
     * 从已有集合中构造
     *
     * @param iter {@link Iterable}
     */
    public ConcurrentHashSet(Iterable<E> iter) {
        if (iter instanceof Collection<E> collection) {
            concurrentHashMap = new ConcurrentHashMap<>((int) (collection.size() / 0.75f));
            this.addAll(collection);
        } else {
            concurrentHashMap = new ConcurrentHashMap<>();
            for (E e : iter) {
                this.add(e);
            }
        }
    }
    // Constructor end ===============================================================================================

    @Override
    public Iterator<E> iterator() {
        return concurrentHashMap.keySet().iterator();
    }

    @Override
    public int size() {
        return concurrentHashMap.size();
    }

    @Override
    public boolean isEmpty() {
        return concurrentHashMap.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return concurrentHashMap.containsKey(o);
    }

    @Override
    public boolean add(E e) {
        return concurrentHashMap.put(e, PRESENT) == null;
    }

    /**
     * 如果存在元素则移除，且返回true，否则返回false
     *
     * @param o object to be removed from this set, if present
     * @return boolean
     */
    @Override
    public boolean remove(Object o) {
        return PRESENT.equals(concurrentHashMap.remove(o));
    }

    @Override
    public void clear() {
        concurrentHashMap.clear();
    }

}