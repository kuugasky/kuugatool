package io.github.kuugasky.kuugatool.core.queue;

import lombok.Getter;

/**
 * LinkedStack【链式栈-后进先出】
 * <p>
 * Last-In,First-Out(LIFO)
 * <p>
 * 类型参数不能用基本类型。
 * <p>
 * 每次放新数据都成为新的top，把原来的top往下压一级，通过指针建立链接。
 * <p>
 * 末端哨兵既是默认构造器创建出的符合end()返回true的节点。
 *
 * @author kuuga
 * @since 2020-08-22 13:15
 */
public final class LifoLinkedStack<T> {

    @Getter
    private int size;

    private static class Node<T> {
        T item;
        Node<T> next;

        Node() {
            item = null;
            next = null;
        }

        Node(T item, Node<T> next) {
            this.item = item;
            this.next = next;
        }

        boolean end() {
            return item == null && next == null;
        }
    }

    /**
     * End sentinel
     */
    private Node<T> top = new Node<>();

    public void push(T item) {
        top = new Node<>(item, top);
        size++;
    }

    public T pop() {
        T result = top.item;
        if (!top.end()) {
            top = top.next;
        }
        size--;
        return result;
    }

    public boolean isEmpty() {
        return (top.next == null);
    }

    public boolean hasItem() {
        return !isEmpty();
    }

}
