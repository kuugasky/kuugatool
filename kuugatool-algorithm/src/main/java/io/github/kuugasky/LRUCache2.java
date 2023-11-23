package io.github.kuugasky;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * LRUCache2
 *
 * @author kuuga
 * @since 2023/9/26-09-26 15:01
 */
public class LRUCache2<K, V> {

    private final int capacity;            // 缓存容量
    private final Map<K, V> cache;         // 缓存
    private final LinkedList<K> keyList;   // 缓存key列表，用于记录key的访问顺序

    public LRUCache2(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>(capacity);
        this.keyList = new LinkedList<>();
    }

    // put操作：向缓存中存入一个key-value
    public synchronized void put(K key, V value) {
        // 如果缓存中已经存在该key，则需要将其从缓存中移除，因为它将被更新
        if (cache.containsKey(key)) {
            keyList.remove(key);
        }
        // 如果缓存已满，则需要删除最久未使用的key-value，即keyList的第一个元素
        while (cache.size() >= capacity) {
            K oldestKey = keyList.removeFirst();
            cache.remove(oldestKey);
        }
        // 将新的key-value存入缓存中，并将该key添加到keyList的末尾，表示最近被访问
        cache.put(key, value);
        keyList.addLast(key);
    }

    // get操作：根据key获取对应的value
    public synchronized V get(K key) {
        // 如果缓存中存在该key，则将其从keyList中移除，并添加到末尾表示最近被访问
        if (cache.containsKey(key)) {
            keyList.remove(key);
            keyList.addLast(key);
            return cache.get(key);
        }
        // 如果缓存中不存在该key，则返回null
        return null;
    }
}
