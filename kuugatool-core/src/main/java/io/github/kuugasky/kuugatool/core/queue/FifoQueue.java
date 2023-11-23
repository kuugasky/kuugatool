package io.github.kuugasky.kuugatool.core.queue;

import java.util.Stack;
import java.util.concurrent.atomic.AtomicLong;

/**
 * FifoQueue
 *
 * @author kuuga
 * @since 2023/9/26-09-26 12:16
 */
public final class FifoQueue<T> {

    private final AtomicLong size;

    private final Stack<T> stackIn;
    private final Stack<T> stackOut;

    public FifoQueue() {
        stackIn = new Stack<>();
        stackOut = new Stack<>();
        size = new AtomicLong(0);
    }

    /**
     * 入队
     *
     * @param element 元素
     */
    public synchronized void enqueue(T element) {
        stackIn.push(element);
        size.incrementAndGet();
    }

    /**
     * 出队
     *
     * @return 元素
     */
    public synchronized T dequeue() {
        // stackOut为空，开始倒栈
        if (stackOut.isEmpty()) {
            while (!stackIn.isEmpty()) {
                // 把stackIn的所有元素倒叙插入到stackOut，这样再从stackOut中出栈的就是目标元素
                // stackIn：1、2、3，转移到stackOut：3、2、1，这时从stackOut出栈的就是1，符合队列先进先出
                stackOut.push(stackIn.pop());
            }
        }
        T pop = stackOut.pop();
        size.decrementAndGet();
        return pop;
    }

    public boolean isEmpty() {
        return stackIn.isEmpty() && stackOut.isEmpty();
    }

    public boolean hasItem() {
        return !isEmpty();
    }

    public static void main(String[] args) {
        FifoQueue<Integer> queue = new FifoQueue<>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);

        while (queue.size.get() > 0) {
            System.out.println(queue.dequeue());
        }
    }

}