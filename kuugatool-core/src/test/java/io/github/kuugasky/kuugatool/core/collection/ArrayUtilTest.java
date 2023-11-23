package io.github.kuugasky.kuugatool.core.collection;

import io.github.kuugasky.kuugatool.core.entity.KuugaModel;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class ArrayUtilTest {

    @Test
    public void newArray() {
        System.out.println(Arrays.toString(ArrayUtil.newArray()));
    }

    @Test
    public void newArray1() {
        System.out.println(Arrays.toString(ArrayUtil.newArray(10)));
    }

    @Test
    public void testNewArray() {
        System.out.println(Arrays.toString(new KuugaModel[]{new KuugaModel()}));
    }

    @Test
    public void testNewArray1() {
        System.out.println(Arrays.toString(ArrayUtil.newArray(KuugaModel.class, 2)));
    }

    @Test
    public void isEmpty() {
        System.out.println(ArrayUtil.isEmpty(ArrayUtil.newArray()));
    }

    @Test
    public void testIsEmpty() {
        System.out.println(ArrayUtil.isEmpty(new KuugaModel()));
    }

    @Test
    public void hasItem() {
        System.out.println(ArrayUtil.hasItem(ArrayUtil.newArray(KuugaModel.class, 2)));
    }

    @Test
    public void length() {
        System.out.println(ArrayUtil.length(ArrayUtil.newArray(KuugaModel.class, 2)));
    }

    @Test
    public void asList() {
        System.out.println(ArrayUtil.asList(ArrayUtil.newArray(KuugaModel.class, 2)));
    }

    @Test
    public void toList() {
        System.out.println(ArrayUtil.toList(ArrayUtil.newArray(KuugaModel.class, 2)));
    }

    @Test
    public void contains() {
        KuugaModel kuugaModel1 = KuugaModel.builder().name("kuuga1").sex(1).build();
        KuugaModel kuugaModel2 = KuugaModel.builder().name("kuuga2").sex(1).build();
        KuugaModel[] kuugaModels = {kuugaModel1, kuugaModel2};
        boolean contains = ArrayUtil.contains(kuugaModels, kuugaModel1);
        System.out.println(contains);
    }

    @Test
    public void testContains() {
        KuugaModel kuugaModel1 = KuugaModel.builder().name("kuuga1").sex(1).build();
        KuugaModel kuugaModel2 = KuugaModel.builder().name("kuuga2").sex(1).build();
        KuugaModel kuugaModel3 = KuugaModel.builder().name("kuuga3").sex(1).build();
        KuugaModel[] kuugaModels = {kuugaModel1, kuugaModel2};
        boolean contains = ArrayUtil.contains(kuugaModels, kuugaModel1);
        System.out.println(contains);
        boolean contains1 = ArrayUtil.contains(kuugaModels, new KuugaModel[]{kuugaModel2, kuugaModel3});
        System.out.println(contains1);
    }

    @Test
    public void newIntArrayContainsInitValue() {
        System.out.println(Arrays.toString(ArrayUtil.newIntArrayContainsInitValue(10)));
    }

    @Test
    public void newStringArrayContainsInitValue() {
        System.out.println(Arrays.toString(ArrayUtil.newStringArrayContainsInitValue(10)));
    }

    @Test
    public void insert() {
        KuugaModel kuugaModel1 = KuugaModel.builder().name("kuuga1").sex(1).build();
        KuugaModel kuugaModel2 = KuugaModel.builder().name("kuuga2").sex(1).build();
        KuugaModel kuugaModel3 = KuugaModel.builder().name("kuuga3").sex(1).build();
        KuugaModel[] kuugaModels = {kuugaModel1, kuugaModel2};
        KuugaModel[] insert = ArrayUtil.insert(kuugaModels, 0, kuugaModel3);
        System.out.println(Arrays.toString(insert));
    }

    @Test
    public void sub() {
        int[] sub = ArrayUtil.sub(new int[]{1, 2, 3, 4}, 1, 3);
        System.out.println(Arrays.toString(sub));
    }

    @Test
    public void add() {
        KuugaModel kuugaModel1 = KuugaModel.builder().name("kuuga1").sex(1).build();
        KuugaModel kuugaModel2 = KuugaModel.builder().name("kuuga2").sex(1).build();
        KuugaModel kuugaModel3 = KuugaModel.builder().name("kuuga3").sex(1).build();
        KuugaModel[] kuugaModels = {kuugaModel1, kuugaModel2};
        KuugaModel[] add = ArrayUtil.add(kuugaModels, kuugaModel3);
        System.out.println(Arrays.toString(add));
    }

    @Test
    public void addAll() {
        KuugaModel kuugaModel1 = KuugaModel.builder().name("kuuga1").sex(1).build();
        KuugaModel kuugaModel2 = KuugaModel.builder().name("kuuga2").sex(1).build();
        KuugaModel kuugaModel3 = KuugaModel.builder().name("kuuga3").sex(1).build();
        KuugaModel[] kuugaModels = {kuugaModel1, kuugaModel2};
        KuugaModel[] kuugaModels2 = {kuugaModel3};
        KuugaModel[] addAll = ArrayUtil.addAll(kuugaModels, kuugaModels2);
        System.out.println(Arrays.toString(addAll));
    }

    @Test
    void resize() {
        byte[] bytes = "kuuga".getBytes();
        byte[] resize = ArrayUtil.resize(bytes, 10);
        System.out.println(Arrays.toString(resize));
    }

}