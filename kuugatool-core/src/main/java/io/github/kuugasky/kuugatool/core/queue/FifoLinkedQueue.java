package io.github.kuugasky.kuugatool.core.queue;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import lombok.Getter;

import java.util.List;

/**
 * FifoQueue【先进先出】
 * <p>
 * First-In,First-Out(FIFO)
 * <p>
 * 特殊线性表，只允许在队头取出元素、在队尾插入元素
 *
 * @author kuuga
 * @since 2021/5/27
 */
public class FifoLinkedQueue<T> {

    public FifoLinkedQueue() {
        head = null;
        tail = null;
    }

    @Getter
    private int size = 0;

    private static class Node<T> {
        T item;
        Node<T> next;

        Node(T item) {
            this.item = item;
            this.next = null;
        }
    }

    private Node<T> head;
    private Node<T> tail;

    public boolean isEmpty() {
        return (head == null);
    }

    public boolean hasItem() {
        return !isEmpty();
    }

    public void putList(List<T> list) {
        ListUtil.optimize(list).forEach(this::put);
    }

    public void put(T item) {
        Node<T> t = tail;
        tail = new Node<>(item);
        if (isEmpty()) {
            head = tail;
        } else {
            t.next = tail;
        }
        size++;
    }

    public T get() {
        T v = head.item;
        head = head.next;
        size--;
        return v;
    }

}
