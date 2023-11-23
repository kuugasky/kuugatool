package io.github.kuugasky.kuugatool.core.collection;

import io.github.kuugasky.kuugatool.core.queue.FifoLinkedQueue;
import org.junit.jupiter.api.Test;

import java.util.List;

public class FifoQueueTest {

    @Test
    public void test() {
        FifoLinkedQueue<String> fifoQueue = new FifoLinkedQueue<>();
        fifoQueue.put("aa");
        fifoQueue.put("bb");
        System.out.println(fifoQueue.get());
        fifoQueue.put("cc");
        System.out.println(fifoQueue.get());
        System.out.println(fifoQueue.get());
    }

    @Test
    public void isEmpty() {
        List<String> list = ListUtil.newArrayList("aa", "bb", "cc");
        FifoLinkedQueue<String> fifoQueue = new FifoLinkedQueue<>();
        fifoQueue.putList(list);
        System.out.println(fifoQueue.isEmpty());
    }

    @Test
    public void hasItem() {
        List<String> list = ListUtil.newArrayList("aa", "bb", "cc");
        FifoLinkedQueue<String> fifoQueue = new FifoLinkedQueue<>();
        fifoQueue.putList(list);
        System.out.println(fifoQueue.hasItem());
    }

    @Test
    public void fifoQueueList() {
        List<String> list = ListUtil.newArrayList("aa", "bb", "cc");
        FifoLinkedQueue<String> fifoQueue = new FifoLinkedQueue<>();
        fifoQueue.putList(list);
        while (fifoQueue.hasItem()) {
            System.out.println(fifoQueue.getSize());
            System.out.println(fifoQueue.get() + "===" + fifoQueue.getSize());
        }
    }

    @Test
    public void put() {
        FifoLinkedQueue<String> fifoQueue = new FifoLinkedQueue<>();
        fifoQueue.put("aa");
        System.out.println(fifoQueue);
    }

    @Test
    public void get() {
        FifoLinkedQueue<String> fifoQueue = new FifoLinkedQueue<>();
        fifoQueue.put("aa");
        System.out.println(fifoQueue.get());
    }

    @Test
    public void getSize() {
        FifoLinkedQueue<String> fifoQueue = new FifoLinkedQueue<>();
        fifoQueue.put("aa");
        System.out.println(fifoQueue.getSize());
    }

}