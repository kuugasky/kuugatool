package io.github.kuugasky;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * LRUCache
 * <p>
 * 一个基于LinkedHashMap的LRU实现
 * <p>
 * LinkedHashMap内部维护了一个双向链表，用于存储元素的顺序信息。
 *
 * @author kuuga
 * @since 2023/9/26-09-26 14:55
 */
public class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private final int capacity;

    /**
     * 当accessOrder(访问顺序)参数为true时，LinkedHashMap会按照访问顺序来维护元素，即最近访问的元素会被移到链表尾部，而最久未使用的元素会被移到链表头部。
     * <p>
     * 当accessOrder参数为false时，LinkedHashMap会按照插入顺序来维护元素。
     *
     * @param capacity 初始容量
     */
    public LRUCache(int capacity) {
        // 调用LinkedHashMap构造函数，设置初始容量和负载因子
        super(capacity, 0.75f, true);
        this.capacity = capacity;
    }

    /**
     * 删除最年长条目
     *
     * @param eldest The least recently inserted entry in the map, or if
     *               this is an access-ordered map, the least recently accessed
     *               entry.  This is the entry that will be removed it this
     *               method returns {@code true}.  If the map was empty prior
     *               to the {@code put} or {@code putAll} invocation resulting
     *               in this invocation, this will be the entry that was just
     *               inserted; in other words, if the map contains a single
     *               entry, the eldest entry is also the newest.
     * @return boolean
     */
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        // 重写LinkedHashMap的removeEldestEntry方法，实现LRU缓存淘汰策略
        // 当缓存容量超出设定值时，自动移除最久未使用的元素
        return size() > capacity;
    }
}