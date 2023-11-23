package io.github.kuugasky.kuugatool.core.queue;

import java.util.LinkedList;
import java.util.Queue;

/**
 * LifoStack
 * <p>
 * 双队列实现栈
 * <p>
 * 时间复杂度为O(n)，空间复杂度为O(n)，其中n为栈中元素的个数。
 * <li>队列：先进先出</li>
 * <li>栈：后进先出</li>
 *
 * @author kuuga
 * @since 2023/9/26-09-26 11:42
 */
public class LifoStack<T> {

    /**
     * 存储栈中的元素
     */
    private Queue<T> queue;
    /**
     * 在pop操作时暂存元素
     */
    private Queue<T> tempQueue;

    public LifoStack() {
        this.queue = new LinkedList<>();
        this.tempQueue = new LinkedList<>();
    }

    /**
     * 入栈
     *
     * @param element 元素
     */
    public void push(T element) {
        // 队列：先进先出（放到队列列头）
        queue.offer(element);
    }

    /**
     * 出栈
     *
     * @return 元素
     */
    public T pop() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }
        while (queue.size() > 1) {
            // 队列先进先出，所以先把前面的元素转移到tempQueue，留下最后一个元素
            // 先将queue队列中的元素倒入tempQueue队列中，直到queue队列中只有一个元素，将其弹出
            tempQueue.offer(queue.poll());
        }
        // 把queue仅留的最后一个元素拿出来
        T element = queue.poll();
        // queue弹出最后一个元素后，变成空队列
        Queue<T> temp = queue;
        // tempQueue的元素给回到queue
        queue = tempQueue;
        // 空队列给tempQueue
        tempQueue = temp;
        return element;
    }

    /**
     * 获取栈顶元素，与pop方法类似，但是不会从queue中移除栈顶元素，也就是实际元素不会出栈
     *
     * @return 元素
     */
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }
        while (queue.size() > 1) {
            tempQueue.offer(queue.poll());
        }
        T element = queue.poll();
        // 在弹出元素之前需要先将其加入tempQueue队列中。
        tempQueue.offer(element);
        Queue<T> temp = queue;
        queue = tempQueue;
        tempQueue = temp;
        return element;
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public boolean hasItem() {
        return !isEmpty();
    }

    public static void main(String[] args) {
        // LifoLinkedStack<Integer> stack = new LifoLinkedStack<>();
        LifoStack<Integer> stack = new LifoStack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        while (stack.hasItem()) {
            System.out.println("pop-->" + stack.pop());
        }
        stack.push(4);
        System.out.println("peek-->" + stack.peek());

        while (stack.hasItem()) {
            System.out.println("pop-->" + stack.pop());
        }
    }

}
