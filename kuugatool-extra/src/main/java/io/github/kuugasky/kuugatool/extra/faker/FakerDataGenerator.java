package io.github.kuugasky.kuugatool.extra.faker;

import com.github.javafaker.Number;
import com.github.javafaker.*;
import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.collection.MapUtil;
import io.github.kuugasky.kuugatool.core.instance.ReflectionUtil;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import io.github.kuugasky.kuugatool.core.random.RandomUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Function;

/**
 * FakerDataGenerator
 *
 * @author kuuga
 * @since 2024/12/17
 */
@Slf4j
public class FakerDataGenerator {

    private static final String ADDRESS = "address";
    private static final String PHONE = "phone";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email";
    private static final String NAME = "name";
    // private static final String REMARK = "remark";

    private FakerType fakerType;
    private Faker faker;
    private int listLoopCount = 3;

    /**
     * 列表循环次数
     *
     * @param listLoopCount 列表循环次数
     * @return this
     */
    public FakerDataGenerator listLoopCount(int listLoopCount) {
        this.listLoopCount = listLoopCount;
        return this;
    }

    /**
     * 构建英语Faker对象生成器
     *
     * @return FakeDataGenerator
     */
    public static FakerDataGenerator buildEnglish() {
        FakerDataGenerator fakeDataGenerator = new FakerDataGenerator();
        fakeDataGenerator.fakerType = FakerType.ENGLISH;
        fakeDataGenerator.faker = new Faker(Locale.ENGLISH);
        fakeDataGenerator.initFieldFakerList();
        fakeDataGenerator.initFieldTypeToSupplierMap();
        return fakeDataGenerator;
    }

    /**
     * 构建中文Faker对象生成器
     *
     * @return FakeDataGenerator
     */
    public static FakerDataGenerator buildChina() {
        FakerDataGenerator fakeDataGenerator = new FakerDataGenerator();
        fakeDataGenerator.fakerType = FakerType.CHINA;
        fakeDataGenerator.faker = new Faker(Locale.CHINA);
        fakeDataGenerator.initFieldFakerList();
        fakeDataGenerator.initFieldTypeToSupplierMap();
        return fakeDataGenerator;
    }

    /**
     * 构建默认中文Faker对象生成器
     *
     * @return FakeDataGenerator
     */
    public static FakerDataGenerator build() {
        return buildChina();
    }

    /**
     * 根据{@link FakerType}构建Faker对象生成器
     *
     * @return FakeDataGenerator
     */
    public static FakerDataGenerator build(FakerType fakerType) {
        switch (fakerType) {
            case CHINA -> {
                return buildChina();
            }
            case ENGLISH -> {
                return buildEnglish();
            }
        }
        return buildChina();
    }

    /**
     * 字段类型与对应的faker对象映射
     *
     * @param <T>
     */
    @Builder
    static class FieldFakerItem<T> {
        /**
         * 字段名
         */
        private String fieldName;
        /**
         * faker内置属性对象
         */
        private T fakerInternalPropertyObject;
        /**
         * faker对象字段填充方法
         */
        private Function<T, Object> fieldFaker;
    }

    /**
     * 填充对象字段与对应的faker对象映射集合
     */
    private List<FieldFakerItem<?>> fieldTypeToSupplierList;
    /**
     * 字段类型与对应的faker对象映射集合
     */
    private Map<Class<?>, Function<Faker, Object>> fieldTypeToSupplierMap;

    /**
     * 可自行补充映射关系
     */
    private void initFieldFakerList() {
        fieldTypeToSupplierList = ListUtil.newArrayList();
        // name
        FieldFakerItem<Name> nameFakerMapper = FieldFakerItem.<Name>builder().fieldName(NAME).fakerInternalPropertyObject(this.faker.name()).fieldFaker(Name::fullName).build();
        fieldTypeToSupplierList.add(nameFakerMapper);
        // address
        FieldFakerItem<Address> addressFakerMapper = FieldFakerItem.<Address>builder().fieldName(ADDRESS).fakerInternalPropertyObject(this.faker.address()).fieldFaker(Address::fullAddress).build();
        fieldTypeToSupplierList.add(addressFakerMapper);
        // email
        FieldFakerItem<Internet> emailFakerMapper;
        if (fakerType == FakerType.CHINA) {
            emailFakerMapper = FieldFakerItem.<Internet>builder().fieldName(EMAIL).fakerInternalPropertyObject(new Faker(Locale.ENGLISH).internet()).fieldFaker(Internet::emailAddress).build();
        } else {
            emailFakerMapper = FieldFakerItem.<Internet>builder().fieldName(EMAIL).fakerInternalPropertyObject(this.faker.internet()).fieldFaker(Internet::emailAddress).build();
        }
        fieldTypeToSupplierList.add(emailFakerMapper);
        // phone
        FieldFakerItem<PhoneNumber> phoneFakerMapper = FieldFakerItem.<PhoneNumber>builder().fieldName(PHONE).fakerInternalPropertyObject(this.faker.phoneNumber()).fieldFaker(PhoneNumber::phoneNumber).build();
        fieldTypeToSupplierList.add(phoneFakerMapper);
        // password
        Function<Number, Object> randomNumber = item -> item.randomNumber() + "";
        FieldFakerItem<Number> passwordFakerMapper = FieldFakerItem.<Number>builder().fieldName(PASSWORD).fakerInternalPropertyObject(this.faker.number()).fieldFaker(randomNumber).build();
        fieldTypeToSupplierList.add(passwordFakerMapper);
    }

    /**
     * 中文替代faker的sentences
     */
    String[] chineseSentences = {
            "君不见，黄河之水天上来，奔流到海不复回。",
            "君不见，高堂明镜悲白发，朝如青丝暮成雪。",
            "人生得意须尽欢，莫使金樽空对月。",
            "天生我材必有用，千金散尽还复来。",
            "烹羊宰牛且为乐，会须一饮三百杯。",
            "岑夫子，丹丘生，将进酒，杯莫停。",
            "与君歌一曲，请君为我倾耳听。",
            "钟鼓馔玉不足贵，但愿长醉不复醒。",
            "古来圣贤皆寂寞，惟有饮者留其名。",
            "陈王昔时宴平乐，斗酒十千恣欢谑。",
            "主人何为言少钱，径须沽取对君酌。",
            "五花马，千金裘，呼儿将出换美酒，与尔同销万古愁。"
    };

    /**
     * 初始化字段类型与对应的faker对象映射集合
     */
    private void initFieldTypeToSupplierMap() {
        fieldTypeToSupplierMap = MapUtil.newHashMap();
        fieldTypeToSupplierMap.put(Integer.class, f -> f.number().numberBetween(1, 100));
        fieldTypeToSupplierMap.put(int.class, f -> f.number().numberBetween(1, 100));
        fieldTypeToSupplierMap.put(Double.class, f -> f.number().randomDouble(2, 1, 100));
        fieldTypeToSupplierMap.put(double.class, f -> f.number().randomDouble(2, 1, 100));
        fieldTypeToSupplierMap.put(Boolean.class, f -> f.bool().bool());
        fieldTypeToSupplierMap.put(boolean.class, f -> f.bool().bool());
        fieldTypeToSupplierMap.put(Long.class, f -> f.number().randomNumber(5, true));
        fieldTypeToSupplierMap.put(long.class, f -> f.number().randomNumber(5, true));
        fieldTypeToSupplierMap.put(Date.class, f -> f.date().birthday(1, 60));
    }

    /**
     * 生成指定对象实例，并赋予虚拟值
     *
     * @param objClass 目标对象类型
     * @return T
     */
    public <T> T generateFakeData(Class<T> objClass) {
        T obj = ReflectionUtil.newInstance(objClass);
        return generateFakeData(obj);
    }

    /**
     * 生成指定对象实例集合，并赋予虚拟值
     *
     * @param clazz 目标对象类型
     * @return List
     */
    public List<?> generateFakeListData(Class<?> clazz) {
        List<Object> list = ListUtil.newArrayList();
        for (int i = 0; i < listLoopCount; i++) {
            Object t = generateFakeData(clazz);
            list.add(t);
        }
        return list;
    }

    /**
     * 给目标对象赋予虚拟值
     *
     * @param obj 目标对象
     * @return T
     */
    public <T> T generateFakeData(T obj) {
        Class<?> clazz = obj.getClass();
        // 获取填充对象所有字段
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if ("java.util.List".equals(field.getType().getName())) {
                List<Object> items = ListUtil.newArrayList(listLoopCount);
                for (int i = 0; i < listLoopCount; i++) {
                    String fieldRealClass = StringUtil.removeEnd(StringUtil.removeStart(field.getGenericType().toString(), "java.util.List<"), ">");
                    Object fieldObject;
                    try {
                        fieldObject = generateFakeData(Class.forName(fieldRealClass));
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    items.add(fieldObject);
                }
                ReflectionUtil.setFieldValue(obj, field, items);
            }
            field.setAccessible(true); // Allow access to private fields

            String fieldName = field.getName().toLowerCase();

            Optional<FieldFakerItem<?>> first = fieldTypeToSupplierList.stream().filter(item -> fieldName.contains(item.fieldName)).findFirst();
            if (first.isPresent()) {
                FieldFakerItem<?> item = first.get();
                field.setAccessible(true); // Allow access to private fields
                try {
                    Function<T, String> fieldFaker = ObjectUtil.cast(item.fieldFaker);
                    T object = ObjectUtil.cast(item.fakerInternalPropertyObject);
                    field.set(obj, fieldFaker.apply(object));
                } catch (IllegalAccessException e) {
                    log.error("FakeDataGenerator.generateFakeData error for field {} in class {}: {}", field.getName(), obj.getClass().getName(), e.getMessage());
                }
            } else {
                try {
                    // Check the field type and assign appropriate fake data
                    Function<Faker, Object> supplier = fieldTypeToSupplierMap.get(field.getType());
                    if (supplier != null) {
                        Object apply = supplier.apply(faker);

                        String targetTypeName = apply.getClass().getTypeName();
                        String fieldTypeName = field.getType().getTypeName();

                        if (targetTypeName.equals(fieldTypeName)) {
                            field.set(obj, apply);
                        } else {
                            switch (fieldTypeName) {
                                case "java.time.LocalDateTime" -> field.set(obj, LocalDateTime.now());
                                case "java.time.LocalDate" -> field.set(obj, LocalDate.now());
                                case "java.time.LocalTime" -> field.set(obj, LocalTime.now());
                            }
                        }
                    } else if ("java.lang.String".equals(field.getType().getName())) {
                        if (this.fakerType == FakerType.ENGLISH) {
                            field.set(obj, this.faker.lorem().sentence(chineseSentences.length));
                        } else {
                            field.set(obj, chineseSentences[RandomUtil.randomInt(chineseSentences.length)]);
                        }
                    }
                } catch (IllegalAccessException e) {
                    log.error("FakeDataGenerator.generateFakeData error for field {} in class {}: {}", field.getName(), obj.getClass().getName(), e.getMessage());
                }
            }
        }
        return obj;
    }

}