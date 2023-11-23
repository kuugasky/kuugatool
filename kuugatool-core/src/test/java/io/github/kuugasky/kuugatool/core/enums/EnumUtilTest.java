package io.github.kuugasky.kuugatool.core.enums;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.collection.MapUtil;
import io.github.kuugasky.kuugatool.core.collection.SetUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class EnumUtilTest {

    static void main(String[] args) {
        List<Class<?>> classes = new ArrayList<>() {{
            // common-agent
            add(ByteType.class);
        }};

        // 指定枚举类只返回包含枚举项
        Map<String, List<Enum<?>>> enumContainsMap = MapUtil.newHashMap();
        // enumContainsMap.put("HouseDecorationEnum", ListUtil.newArrayList(HouseDecorationEnum.DELICATE));

        // 指定枚举类过滤掉对应枚举项
        Map<String, List<Enum<?>>> enumFilterMap = MapUtil.newHashMap();
        // enumFilterMap.put("CustomerSourceEnum", ListUtil.newArrayList(CustomerSourceEnum.APPOINTMENT));

        Map<String, List<Map<String, Object>>> houseDoorModelEnum = EnumUtil.scanEnum(ListUtil.newArrayList("HouseDoorModelEnum"), classes, enumContainsMap, enumFilterMap);
        System.out.println(StringUtil.formatString(houseDoorModelEnum));

    }

    @Test
    void getEnumNames() {
        // 获取某个枚举集合的name作为新的集合返回
        List<String> enumNames = EnumUtil.getEnumNames(ListUtil.newArrayList(HouseDoorModelEnum.BACHELOR_APARTMENT));
        System.out.println(enumNames);
    }

    @Test
    void scanAllEnum() {
        Map<String, List<Map<String, Object>>> stringListMap = EnumUtil.scanAllEnum(ListUtil.newArrayList(HouseDoorModelEnum.class));
        System.out.println(StringUtil.formatString(stringListMap));
    }

    @Test
    void scanEnum() {
        Map<String, List<Map<String, Object>>> houseDoorModelEnum = EnumUtil.scanEnum(ListUtil.newArrayList("HouseDoorModelEnum"), ListUtil.newArrayList());
        System.out.println(StringUtil.formatString(houseDoorModelEnum));
    }

    @Test
    void testScanEnum() {
    }

    @Test
    void getClassSetFromPackage() {
    }

    @Test
    void findClassInPackageByJar() {
    }

    @Test
    void findClassInPackageByFile() {
        EnumUtil.findClassInPackageByFile("io.github.kuugasky.kuugatool.core.enums",
                "/Users/kuuga/IdeaProjects/kfang/kuugatool/kuugatool-core/target/test-classes/cn/kuugatool/core/enums",
                false, SetUtil.newHashSet(HouseDoorModelEnum.class));
    }

    @Test
    void isEnum() {
        System.out.println(EnumUtil.isEnum(HouseDoorModelEnum.ONE_ROOM));
    }

    @Test
    void testIsEnum() {
        System.out.println(EnumUtil.isEnum(HouseDoorModelEnum.class));
    }

    @Test
    void testToString() {
        String s = EnumUtil.toString(HouseDoorModelEnum.ONE_ROOM);
        System.out.println(s);
    }

    @Test
    void getNames() {
        // 枚举类中所有枚举对象的name列表
        List<String> bachelor_apartment = EnumUtil.getNames(HouseDoorModelEnum.class);
        ListUtil.optimize(bachelor_apartment).forEach(System.out::println);
    }

    @Test
    void getValue() {
        // 将name字符串解析成枚举项
        HouseDoorModelEnum bachelor_apartment = EnumUtil.parseEnumOfName(HouseDoorModelEnum.class, "BACHELOR_APARTMENT");
        System.out.println(bachelor_apartment);
    }

    @Test
    void getFieldValues() {
        // 获得枚举类中各枚举对象下指定字段的值
        List<Object> index = EnumUtil.getFieldValues(HouseDoorModelEnum.class, "index");
        ListUtil.optimize(index).forEach(System.out::println);
    }

    @Test
    void getFieldNames() {
        // 获得枚举类中所有的字段名
        List<String> fieldNames = EnumUtil.getFieldNames(HouseDoorModelEnum.class);
        ListUtil.optimize(fieldNames).forEach(System.out::println);
    }

    @Test
    void getEnumMap() {
        // 获取枚举字符串值和枚举对象的Map对应，使用LinkedHashMap保证有序
        LinkedHashMap<String, HouseDoorModelEnum> enumMap = EnumUtil.getEnumMap(HouseDoorModelEnum.class);
        System.out.println(StringUtil.formatString(enumMap));
    }

    @Test
    void getNameFieldMap() {
        // 获得枚举名对应指定字段值的Map
        Map<String, Object> name = EnumUtil.getNameFieldMap(HouseDoorModelEnum.class, "desc");
        System.out.println(StringUtil.formatString(name));
    }

    @Test
    void contains() {
        boolean bachelor_apartment = EnumUtil.contains(HouseDoorModelEnum.class, "BACHELOR_APARTMENT");
        System.out.println(bachelor_apartment);
    }

    @Test
    void testContains() {
        boolean bachelor_apartment = EnumUtil.contains(
                HouseDoorModelEnum.TWO_ROOM,
                new HouseDoorModelEnum[]{HouseDoorModelEnum.ONE_ROOM, HouseDoorModelEnum.THREE_ROOM, HouseDoorModelEnum.TWO_ROOM});
        System.out.println(bachelor_apartment);
    }

    @Test
    void notContains() {
        boolean bachelor_apartment = EnumUtil.notContains(HouseDoorModelEnum.class, "BACHELOR_APARTMENT");
        System.out.println(bachelor_apartment);
    }

    @Test
    void equalsIgnoreCase() {
        boolean bachelor_apartment = EnumUtil.equalsIgnoreCase(HouseDoorModelEnum.BACHELOR_APARTMENT, "bachelor_apartment");
        System.out.println(bachelor_apartment);
    }

    @Test
    void testEquals() {
        boolean bachelor_apartment = EnumUtil.equals(HouseDoorModelEnum.BACHELOR_APARTMENT, "BACHELOR_APARTMENT");
        System.out.println(bachelor_apartment);
    }

    @Test
    void toList() {
        List<HouseDoorModelEnum> bachelor_apartment = EnumUtil.toList(ListUtil.newArrayList("BACHELOR_APARTMENT", "ONE_ROOM"), HouseDoorModelEnum.class);
        System.out.println(StringUtil.formatString(bachelor_apartment));
    }

    @Test
    void getAttributeValue() {
        System.out.println(EnumUtil.getAttributeValue(HouseDoorModelEnum.BACHELOR_APARTMENT, "desc"));
    }

    @Test
    void getEnumItemIfContain() {
        Enum<HouseDoorModelEnum> index = EnumUtil.getEnumItemIfContain(HouseDoorModelEnum.class, "index", 11);
        System.out.println(index);
    }

    @Test
    void getEnumItemIfContain1() {
        Enum<HouseDoorModelEnum> index = EnumUtil.getEnumItemIfContain(HouseDoorModelEnum.class, "index", 11, HouseDoorModelEnum.THREE_ROOM);
        System.out.println(index);
    }

    @Test
    void getPackClassMap() {
        List<Class<?>> CLASSES = new ArrayList<>();
        CLASSES.add(ByteType.class);
        Class<?>[] classes = CLASSES.toArray(Class[]::new);
        Map<String, Object> packClassMap = EnumUtil.getPackClassMap(classes);
        System.out.println(MapUtil.toString(packClassMap, true));
    }

    @Test
    void getPackClassMap1() {
        Class<?>[] classes = new Class[]{ByteType.class};
        Map<String, Object> packClassMap = EnumUtil.getPackClassMap(classes);
        System.out.println(MapUtil.toString(packClassMap, true));
    }

    @Test
    void test() {
        List<Class<?>> CLASSES = new ArrayList<>();

        CLASSES.add(ByteType.class);

        // 指定枚举类只返回包含枚举项
        Map<String, List<Enum<?>>> enumContainsMap = MapUtil.newHashMap();
        // enumContainsMap.put("HouseDecorationEnum", ListUtil.newArrayList(HouseDecorationEnum.DELICATE));

        // 指定枚举类过滤掉对应枚举项
        Map<String, List<Enum<?>>> enumFilterMap = MapUtil.newHashMap();
        enumFilterMap.put("ByteType", ListUtil.newArrayList(ByteType.B));

        Map<String, List<Map<String, Object>>> enumList = EnumUtil.scanEnum(ListUtil.newArrayList("ByteType"), CLASSES, enumContainsMap, enumFilterMap);
        System.out.println(MapUtil.toString(enumList, true));
    }

}