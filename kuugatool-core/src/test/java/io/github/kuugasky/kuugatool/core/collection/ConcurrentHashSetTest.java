package io.github.kuugasky.kuugatool.core.collection;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

class ConcurrentHashSetTest {

    @Test
    void iterator() {
        ConcurrentHashSet<Object> objects = new ConcurrentHashSet<>();
        objects.add(1);
        Iterator<Object> iterator = objects.iterator();
        System.out.println(iterator);
    }

    @Test
    void size() {
        ConcurrentHashSet<Object> objects = new ConcurrentHashSet<>();
        objects.add(1);
        System.out.println(objects.size());
    }

    @Test
    void isEmpty() {
        System.out.println(new ConcurrentHashSet<>().isEmpty());
    }

    @Test
    void contains() {
        ConcurrentHashSet<Object> objects = new ConcurrentHashSet<>();
        objects.add(1);
        System.out.println(objects.contains(1));
    }

    @Test
    void add() {
        ConcurrentHashSet<Object> objects = new ConcurrentHashSet<>(1);
        objects.add(1);
        System.out.println(objects.contains(1));
    }

    @Test
    void remove() {
        ConcurrentHashSet<Object> objects = new ConcurrentHashSet<>(1);
        objects.add(2);
        boolean remove = objects.remove(1);
        System.out.println(remove);
        System.out.println(objects);
    }

    @Test
    void clear() {
        ConcurrentHashSet<Object> objects = new ConcurrentHashSet<>(1);
        objects.add(1);
        objects.add(2);
        objects.clear();
        System.out.println(objects.size());
    }

    @Test
    void init() {
        ConcurrentHashSet<Integer> set = new ConcurrentHashSet<>(5, 3);
        for (int i = 0; i < 1; i++) {
            set.add(i);
            System.out.println(i + "---> " + set.size());
        }
        ConcurrentHashSet<Integer> set1 = new ConcurrentHashSet<>(5, 3, 2);
        for (int i = 0; i < 1; i++) {
            set1.add(i);
            System.out.println(i + "---> " + set1.size());
        }
        List<Integer> integers = ListUtil.newArrayList(1, 2, 3);
        ConcurrentHashSet<Integer> set2 = new ConcurrentHashSet<>(integers);
        for (int i = 0; i < 1; i++) {
            set2.add(i);
            System.out.println(i + "---> " + set2.size());
        }
    }

}