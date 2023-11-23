package io.github.kuugasky.kuugatool.core.object;

import io.github.kuugasky.kuugatool.core.collection.ArrayUtil;
import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.collection.MapUtil;
import io.github.kuugasky.kuugatool.core.collection.SetUtil;
import io.github.kuugasky.kuugatool.core.entity.KuugaDTO;
import io.github.kuugasky.kuugatool.core.entity.KuugaModel;
import io.github.kuugasky.kuugatool.core.optional.KuugaOptional;
import io.github.kuugasky.kuugatool.core.string.JoinerUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Comparator;
import java.util.Set;

public class ObjectUtilTest {

    @Test
    void nonNull() {
        KuugaModel kuugaModel = new KuugaModel();
        System.out.println(ObjectUtil.nonNull(kuugaModel));
    }

    @Test
    void isNull() {
        KuugaModel kuugaModel = null;
        boolean aNull = ObjectUtil.isNull(kuugaModel);
        System.out.println(aNull);
    }

    @Test
    void isNull2() {
        KuugaModel kuugaModel = null;
        boolean aNull = ObjectUtil.isNull(kuugaModel, "1");
        System.out.println(aNull);
    }

    @Test
    void testIsNull() {
        KuugaModel kuugaModel = null;
        KuugaModel kuugaModel1 = null;
        System.out.println(ObjectUtil.containsNull(kuugaModel, kuugaModel1));
    }

    @Test
    void toMap() {
        KuugaModel Kuuga = KuugaModel.builder().name("kuuga").sex(1).build();
        System.out.println(ObjectUtil.toMap(Kuuga));
    }

    @Test
    void testToMap() {
        KuugaModel Kuuga = KuugaModel.builder().name("kuuga").sex(1).build();
        System.out.println(ObjectUtil.toMap(Kuuga, true));
    }

    @Test
    void testToMap1() {
        KuugaModel Kuuga = KuugaModel.builder().name("kuuga").sex(1).build();

        System.out.println(ObjectUtil.toMap(Kuuga, map -> {
            map.put("name1", Kuuga.getName());
            map.put("sex1", Kuuga.getSex());
        }));
    }

    @Test
    void testToMap2() {
        KuugaModel Kuuga = KuugaModel.builder().name("kuuga").sex(1).build();

        System.out.println(ObjectUtil.toMap(Kuuga, true, (map) -> {
            map.put("name1", Kuuga.getName());
            map.put("sex1", Kuuga.getSex());
        }));
    }

    @Test
    void beanToMap() {
        KuugaModel Kuuga = KuugaModel.builder().name("kuuga").sex(1).build();
        System.out.println(ObjectUtil.toMap(Kuuga, KuugaModel.class, true));
    }

    @Test
    void beanToMap2() {
        KuugaModel Kuuga = KuugaModel.builder().name("kuuga").sex(1).build();
        System.out.println(ObjectUtil.toMap(Kuuga, KuugaModel.class, true, (map) -> {
            map.put("name1", Kuuga.getName());
            map.put("sex1", Kuuga.getSex());
        }));
    }

    @Test
    void toUrlParams() {
        KuugaModel Kuuga = KuugaModel.builder().name(null).sex(1).build();
        System.out.println(ObjectUtil.toUrlParams(Kuuga));

        System.out.println(JoinerUtil.on("&").withKeyValueSeparator("=").join(ObjectUtil.toMap(Kuuga)));
    }

    @Test
    void toUrlParamsOfRemoveNullValue() {
        KuugaModel Kuuga = KuugaModel.builder().name(null).sex(1).build();
        System.out.println(ObjectUtil.toUrlParamsOfRemoveNullValue(Kuuga));
    }

    @Test
    void testToUrlParamsOfRemoveNullValue() {
        KuugaModel Kuuga = KuugaModel.builder().name(null).sex(1).build();
        System.out.println(ObjectUtil.toUrlParamsOfRemoveNullValue(Kuuga, true));
    }

    @Test
    void testToUrlParams() {
        KuugaModel Kuuga = KuugaModel.builder().name(null).sex(1).build();
        System.out.println(ObjectUtil.toUrlParamsOfRemoveNullValue(Kuuga, true));
    }

    @Test
    void testToUrlParams1() {
        KuugaModel Kuuga = KuugaModel.builder().name("kuuga").sex(1).build();
        System.out.println(ObjectUtil.toUrlParams(Kuuga, true));
    }

    @Test
    void testToUrlParams2() {
        KuugaModel Kuuga = KuugaModel.builder().name("kuuga").sex(1).build();
        System.out.println(ObjectUtil.toUrlParams(Kuuga, true));
    }

    @Test
    void cast() {
        KuugaModel Kuuga = KuugaModel.builder().name("kuuga").sex(1).build();
        Object cast = ObjectUtil.cast(Kuuga);
        System.out.println(cast);
    }

    @Test
    void cast1() {
        KuugaModel Kuuga = KuugaModel.builder().name("kuuga").sex(1).build();
        KuugaDTO cast = ObjectUtil.cast(Kuuga, KuugaDTO.class);
        System.out.println(cast);
    }

    @Test
    void getValue() {
        KuugaModel Kuuga = KuugaModel.builder().name("kuuga").sex(1).build();
        Kuuga = null;
        System.out.println(ObjectUtil.getOrElse(Kuuga, true));
    }

    @Test
    void testEquals() {
        KuugaModel Kuuga = KuugaModel.builder().name("kuuga").sex(1).build();
        KuugaModel kuuga1 = KuugaModel.builder().name("kuuga").sex(0).build();
        boolean equals = ObjectUtil.equals(Kuuga, kuuga1);
        System.out.println(equals);
    }

    @Test
    void testEqualsNo() {
        KuugaModel Kuuga = KuugaModel.builder().name("kuuga").sex(1).build();
        KuugaModel kuuga1 = KuugaModel.builder().name("kuuga").sex(0).build();
        boolean equals = ObjectUtil.equalsNo(Kuuga, kuuga1);
        System.out.println(equals);
    }

    @Test
    void deepEquals() {
        KuugaModel Kuuga = KuugaModel.builder().name("kuuga").sex(1).build();
        KuugaModel kuuga1 = KuugaModel.builder().name("kuuga").sex(1).build();
        boolean equals = ObjectUtil.deepEquals(Kuuga, kuuga1);
        System.out.println(equals);
    }

    @Test
    void deepEqualsNo() {
        KuugaModel Kuuga = KuugaModel.builder().name("kuuga").sex(1).build();
        KuugaModel kuuga1 = KuugaModel.builder().name("kuuga").sex(1).build();
        boolean equals = ObjectUtil.deepEqualsNo(Kuuga, kuuga1);
        System.out.println(equals);
    }

    @Test
    void testToString() {
        KuugaModel Kuuga = KuugaModel.builder().name("kuuga").sex(1).build();
        String toString = ObjectUtil.toString(Kuuga);
        System.out.println(toString);
    }

    @Test
    void testToString1() {
        KuugaModel Kuuga = null;
        String toString = ObjectUtil.toString(Kuuga, "kuuga");
        System.out.println(toString);
    }

    @Test
    void requireNonNull() {
        Object requireNonNull = ObjectUtil.requireNonNull(null);
        System.out.println(requireNonNull);
    }

    @Test
    void testRequireNonNull() {
        Object requireNonNull = ObjectUtil.requireNonNull(null, "参数不能为空");
        System.out.println(requireNonNull);
    }

    @Test
    void testRequireNonNull2() {
        Object requireNonNull = ObjectUtil.requireNonNull(null, new RuntimeException("业务参数不能为空"));
        System.out.println(requireNonNull);
    }

    @Test
    void requireNonNullElse() {
        Object requireNonNull = ObjectUtil.requireNonNullElse(null, "kuugasky");
        System.out.println(requireNonNull);
    }

    @Test
    void requireNonNullElseGet() {
        Object requireNonNullElseGet = ObjectUtil.requireNonNullElseGet(null,
                () -> KuugaModel.builder().name("kuuga").sex(1).build());
        System.out.println(requireNonNullElseGet);
    }

    @Test
    void testRequireNonNull1() {
        Object requireNonNull = ObjectUtil.requireNonNull(null, () -> {
            System.out.println("请求参数不能为空");
            return "请求参数不能为空";
        });
        System.out.println(requireNonNull);
    }

    @Test
    void checkElementIndex() {
        ObjectUtil.checkElementIndex(ListUtil.newArrayList(1, 2, 3), 3);
    }

    @Test
    void compare() {
        KuugaModel Kuuga = KuugaModel.builder().name("kuuga").sex(1).build();
        KuugaModel kuuga1 = KuugaModel.builder().name("kuuga1").sex(0).build();
        KuugaModel kuuga2 = KuugaModel.builder().name("kuuga").sex(0).build();
        KuugaModel kuuga3 = KuugaModel.builder().name("kuuga").sex(1).build();
        int compare = ObjectUtil.compare(Kuuga, kuuga1, Comparator.comparingInt(KuugaModel::getSex));
        System.out.println(compare);
        int compare1 = ObjectUtil.compare(Kuuga, kuuga2, Comparator.comparingInt(KuugaModel::getSex));
        System.out.println(compare1);
        int compare2 = ObjectUtil.compare(Kuuga, kuuga3, Comparator.comparingInt(KuugaModel::getSex));
        System.out.println(compare2);
    }

    @Test
    void testNonNull() {
        System.out.println(ObjectUtil.nonNull(null));
    }

    @Test
    void testNonNull1() {
        System.out.println(ObjectUtil.nonNull(null, null));
    }

    @Test
    void testIsNull1() {
        System.out.println(ObjectUtil.isNull(null));
    }

    @Test
    void containsNull() {
        System.out.println(ObjectUtil.containsNull(null, null));
    }

    @Test
    void containsNotNull() {
        System.out.println(ObjectUtil.containsNotNull(null, 1));
    }

    @Test
    void defaultIfNull() {
        System.out.println(ObjectUtil.defaultIfNull(null, 1));
    }

    @Test
    void classTypeIsSerializable() {
        System.out.println(ObjectUtil.classTypeIsSerializable(new KuugaDTO()));
    }

    @Test
    void classTypeIsMap() {
        System.out.println(ObjectUtil.classTypeIsMap(MapUtil.newHashMap()));
    }

    @Test
    void classTypeIsList() {
        System.out.println(ObjectUtil.classTypeIsList(ListUtil.newArrayList()));
    }

    @Test
    void classTypeIsSet() {
        System.out.println(ObjectUtil.classTypeIsSet(SetUtil.newHashSet()));
    }

    @Test
    void classTypeIsArray() {
        System.out.println(ObjectUtil.classTypeIsArray(ArrayUtil.newArray()));
    }

    @Test
    void anewSetValueToStr() {
        Demo demo = new Demo();
        demo.setBigDecimal(BigDecimal.ONE);
        ObjectUtil.anewSetValueToStr(demo, Demo::setTest, demo.getBigDecimal(), "%s万");
        System.out.println(demo.getTest());
        ObjectUtil.anewSetValueToStr(demo, Demo::setTest, KuugaOptional.ofNullable(demo).getBean(Demo::getBigDecimal), "%s万");
        System.out.println(demo.getTest());
    }

    @Test
    void testRequireNonNullElseGet() {
        String str = ObjectUtil.requireNonNullElseGet(null, () -> "kuuga");
        System.out.println(str);
    }

    @Test
    void requireHasText() {
        System.out.println(ObjectUtil.requireHasText(StringUtil.EMPTY));
    }

    @Test
    void testRequireHasText() {
        System.out.println(ObjectUtil.requireHasText(StringUtil.EMPTY, new RuntimeException("str can't empty.")));
    }

    @Test
    void testRequireHasText1() {
        System.out.println(ObjectUtil.requireHasText(StringUtil.EMPTY, "str can't empty."));
    }

    @Test
    void requireHasTextElse() {
        String Kuuga = ObjectUtil.requireHasTextElse(StringUtil.EMPTY, "kuuga");
        System.out.println(Kuuga);
    }

    @Test
    void requireHasTextElseGet() {
        String Kuuga = ObjectUtil.requireHasTextElseGet(StringUtil.EMPTY, () -> "defaultValue.");
        System.out.println(Kuuga);
    }

    @Test
    void testRequireHasText2() {
        System.out.println(ObjectUtil.requireHasText(StringUtil.EMPTY, () -> "xxx"));
    }

    @Test
    void requireHasItem() {
        Collection<Object> objects = ObjectUtil.requireHasItem(ListUtil.newArrayList());
        System.out.println(objects);
    }

    @Test
    void testRequireHasItem() {
        Set<Object> obj = SetUtil.newHashSet();
        obj.add(null);
        Collection<Object> objects = ObjectUtil.requireHasItem(obj, false);
        System.out.println(objects);
    }

    @Test
    void testRequireHasItem1() {
        System.out.println(ObjectUtil.requireHasItem(ListUtil.emptyList(), new RuntimeException("list can't be empty.")));
    }

    @Test
    void testRequireHasItem2() {
        System.out.println(ObjectUtil.requireHasItem(ListUtil.emptyList(), true, new RuntimeException("list can't be empty.")));
    }

    @Test
    void testRequireNonNull3() {
        System.out.println(ObjectUtil.requireHasItem(ListUtil.emptyList(), "list can't be empty."));
    }

    @Test
    void testRequireNonNull4() {
        System.out.println(ObjectUtil.requireHasItem(ListUtil.emptyList(), true, "list can't be empty."));
    }

    @Test
    void testRequireNonNullElse() {
        System.out.println(ObjectUtil.requireHasItemElse(ListUtil.emptyList(), ListUtil.emptyList()));
    }

    @Test
    void testRequireNonNullElseGet1() {
        System.out.println(ObjectUtil.requireHasItemElseGet(ListUtil.emptyList(), ListUtil::emptyList));
    }

    @Test
    void testRequireNonNull5() {
        System.out.println(ObjectUtil.requireHasItem(ListUtil.emptyList(), () -> "list can't be empty."));
    }

    @Data
    static class Demo {
        private BigDecimal bigDecimal;
        private String test;
    }

}