package io.github.kuugasky.kuugatool.core.instance;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.collection.MapUtil;
import io.github.kuugasky.kuugatool.core.entity.KuugaModel;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class InstanceCreatorTest {

    @Test
    public void testClone() throws Exception {
        KuugaModel Kuuga = KuugaModel.builder().name("kuuga").sex(10).build();
        KuugaModel clone = InstanceCreator.clone(Kuuga);
        System.out.println(clone);
    }

    @Test
    public void testClone1() throws Exception {
        KuugaModel Kuuga = KuugaModel.builder().name("kuuga").sex(10).build();
        KuugaModel kuuga1 = KuugaModel.builder().name("kuuga1").sex(11).build();
        List<KuugaModel> list = ListUtil.newArrayList();
        list.add(Kuuga);
        list.add(kuuga1);
        List<KuugaModel> clone = InstanceCreator.clone(list);
        System.out.println(clone);
    }

    @Test
    public void testClone2() throws Exception {
        KuugaModel Kuuga = KuugaModel.builder().name("kuuga").sex(10).build();
        KuugaModel kuuga1 = KuugaModel.builder().name("kuuga1").sex(11).build();
        Map<Integer, KuugaModel> map = MapUtil.newHashMap();
        map.put(1, Kuuga);
        map.put(2, kuuga1);
        Map<Integer, KuugaModel> clone = InstanceCreator.clone(map);
        System.out.println(clone);
    }

    @Test
    public void create() throws Exception {
        KuugaModel kuugaModel = InstanceCreator.create(KuugaModel.class);
        System.out.println(kuugaModel);
    }

    @Test
    public void testCreate() {
        KuugaModel kuugaModel = InstanceCreator.create(KuugaModel.class, new Class[]{}, new Object[]{});
        System.out.println(kuugaModel);
    }

    @Test
    public void testCreate1() {
        KuugaModel kuugaModel = InstanceCreator.create("io.github.kuugasky.kuugatool.core.entity.kuugaModel");
        System.out.println(kuugaModel);
    }

    @Test
    public void testCreate2() {
        KuugaModel kuugaModel = InstanceCreator.create(KuugaModel.class, String.class, "kuugasky");
        System.out.println(kuugaModel);
    }

    @Test
    public void testCreate3() {
        KuugaModel kuugaModel = InstanceCreator.create("io.github.kuugasky.kuugatool.core.entity.kuugaModel", new Class[]{String.class}, new Object[]{"kuuga111"});
        System.out.println(kuugaModel);
    }

    @Test
    public void testCreate4() {
        KuugaModel kuugaModel = InstanceCreator.create("io.github.kuugasky.kuugatool.core.entity.kuugaModel", String.class, "kuuga111");
        System.out.println(kuugaModel);
    }

}