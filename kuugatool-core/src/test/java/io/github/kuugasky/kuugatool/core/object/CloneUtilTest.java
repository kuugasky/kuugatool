package io.github.kuugasky.kuugatool.core.object;

import io.github.kuugasky.kuugatool.core.entity.KuugaModel;
import lombok.Data;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public class CloneUtilTest {

    @Data
    static class DeepClone implements Serializable {
        private DeepItemClone deepItemClone;
        private String name;
    }

    @Data
    public static class DeepClone2 implements Cloneable {
        private DeepItemClone deepItemClone;
        private String name;

        @Override
        public Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }

    @Data
    static class DeepItemClone implements Serializable {
        private String address;
    }

    static DeepClone deepClone = new DeepClone();

    @BeforeAll
    static void before() {
        deepClone.setName("kuuga");
        DeepItemClone deepItemClone = new DeepItemClone();
        deepItemClone.setAddress("address");
        deepClone.setDeepItemClone(deepItemClone);
    }

    @Test
    void shallowCopy() throws IOException, ClassNotFoundException, CloneNotSupportedException {
        DeepClone2 deepClone2 = new DeepClone2();
        deepClone2.setName("kuuga");
        Object clone = deepClone2.clone();

        System.out.println(clone == deepClone2);
        deepClone2.setName("kuugaCopy");
        System.out.println("clone name value:" + ((DeepClone2) clone).getName());
    }

    @Test
    void cloneByStream() throws IOException, ClassNotFoundException {
        DeepClone deepCloneCopy = CloneUtil.cloneByStream(deepClone);
        deepClone.getDeepItemClone().setAddress("address1");
        System.out.println("stream流深拷贝：" + deepCloneCopy.getDeepItemClone().getAddress());
    }

    @Test
    void copyProperties() throws InvocationTargetException, IllegalAccessException {
        DeepClone deepCloneCopy = new DeepClone();
        BeanUtils.copyProperties(deepCloneCopy, deepClone);
        deepClone.getDeepItemClone().setAddress("address1");
        System.out.println("copyProperties拷贝：" + deepCloneCopy.getDeepItemClone().getAddress());
    }

    @Test
    public void cloneByApache() {
        DeepClone deepCloneCopy = CloneUtil.cloneByApache(deepClone);
        deepClone.getDeepItemClone().setAddress("address1");
        System.out.println("apache流深拷贝：" + deepCloneCopy.getDeepItemClone().getAddress());
    }

    @Test
    public void copy1() {
        KuugaModel Kuuga = KuugaModel.builder().name("kuuga").sex(1).build();
        KuugaModel kuugaModel = MapperUtil.copy(Kuuga, KuugaModel.class);
        Kuuga.setSex(2);
        System.out.println(kuugaModel.getSex());
    }

    @Test
    public void copy2() {
        KuugaModel Kuuga = null;
        KuugaModel kuugaModel = MapperUtil.copy(Kuuga, KuugaModel.class);
        System.out.println(kuugaModel.getSex());
    }

    @Test
    void cloneBySerializable() throws Exception {
        KuugaModel Kuuga = KuugaModel.builder().name("kuuga").sex(1).build();
        KuugaModel kuugaModel = CloneUtil.cloneByStream(Kuuga);
        System.out.println(Kuuga == kuugaModel);
        System.out.println(Kuuga.getSex() == kuugaModel.getSex());
        System.out.println(Objects.equals(Kuuga.getName(), kuugaModel.getName()));
    }

    @Test
    void cloneByApache1() {
        KuugaModel Kuuga = KuugaModel.builder().name("kuuga").sex(1).build();
        Object kuugaModel = CloneUtil.cloneByApache(Kuuga);
        System.out.println(Kuuga == kuugaModel);
        System.out.println(Kuuga.getSex() == ((KuugaModel) kuugaModel).getSex());
        System.out.println(Objects.equals(Kuuga.getName(), ((KuugaModel) kuugaModel).getName()));
    }

}