package io.github.kuugasky.kuugatool.core.collection;

import io.github.kuugasky.kuugatool.core.entity.KuugaModel;
import io.github.kuugasky.kuugatool.core.queue.LifoLinkedStack;
import org.junit.Before;
import org.junit.jupiter.api.Test;

public class LifoLinkedStackTest {

    private LifoLinkedStack<String> lifoLinkedStack;
    private LifoLinkedStack<KuugaModel> kuugaModelLifoLinkedStack;

    @Before
    public void before() {
        lifoLinkedStack = new LifoLinkedStack<>();
        kuugaModelLifoLinkedStack = new LifoLinkedStack<>();
        for (int i = 0; i < 5; i++) {
            KuugaModel build = KuugaModel.builder().name(i + ".Kuuga").sex(i).build();
            kuugaModelLifoLinkedStack.push(build);
        }
    }

    @Test
    public void push() {
        for (int i = 0; i < 10; i++) {
            lifoLinkedStack.push(i + ".Kuuga");
        }
        System.out.println(lifoLinkedStack.getSize());
    }

    @Test
    public void pop() {
        KuugaModel kuugaModel;
        while ((kuugaModel = kuugaModelLifoLinkedStack.pop()) != null) {
            System.out.println(kuugaModel.toString() + "--" + kuugaModelLifoLinkedStack.getSize());
        }
    }

    @Test
    public void isEmpty() {
        while (!kuugaModelLifoLinkedStack.isEmpty()) {
            System.out.println(kuugaModelLifoLinkedStack.pop() + "---" + kuugaModelLifoLinkedStack.getSize());
        }
    }

    @Test
    public void hasItem() {
        while (kuugaModelLifoLinkedStack.hasItem()) {
            System.out.println(kuugaModelLifoLinkedStack.pop() + "---" + kuugaModelLifoLinkedStack.getSize());
        }
    }

    @Test
    public void popOrElse() {
        for (int i = 0; i < 10; i++) {
            lifoLinkedStack.push(i + ".Kuuga");
        }
        String ss;
        while ((ss = lifoLinkedStack.pop()) != null) {
            System.out.println(ss + "---" + lifoLinkedStack.getSize());
        }
    }

}