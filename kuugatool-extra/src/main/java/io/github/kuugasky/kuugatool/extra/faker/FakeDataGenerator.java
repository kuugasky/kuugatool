package io.github.kuugasky.kuugatool.extra.faker;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Locale;

/**
 * FakeDataGenerator
 *
 * @author kuuga
 * @since 2024/12/17
 */
@Slf4j
public class FakeDataGenerator {

    private static final String ADDRESS = "address";
    private static final String PHONE = "phone";
    private static final String EMAIL = "email";
    private static final String NAME = "name";

    private FakeType fakeType;
    private Faker faker;

    public static FakeDataGenerator buildEnglish() {
        FakeDataGenerator fakeDataGenerator = new FakeDataGenerator();
        fakeDataGenerator.fakeType = FakeType.ENGLISH;
        fakeDataGenerator.faker = new Faker(Locale.ENGLISH);
        return fakeDataGenerator;
    }

    public static FakeDataGenerator buildChina() {
        FakeDataGenerator fakeDataGenerator = new FakeDataGenerator();
        fakeDataGenerator.fakeType = FakeType.CHINA;
        fakeDataGenerator.faker = new Faker(Locale.CHINA);
        return fakeDataGenerator;
    }

    public static FakeDataGenerator build() {
        return buildChina();
    }

    public static FakeDataGenerator build(FakeType fakeType) {
        if (fakeType == FakeType.CHINA) {
            return buildChina();
        }
        if (fakeType == FakeType.ENGLISH) {
            return buildEnglish();
        }
        return build();
    }

    public <T> T generateFakeData(T obj) {
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true); // Allow access to private fields
            try {
                // Check the field type and assign appropriate fake data
                if (field.getType() == String.class) {
                    String fieldName = field.getName();
                    if (fieldName.toLowerCase().contains(NAME)) {
                        field.set(obj, this.faker.name().fullName());
                    } else if (fieldName.toLowerCase().contains(EMAIL)) {
                        if (fakeType == FakeType.CHINA) {
                            field.set(obj, new Faker(Locale.ENGLISH).internet().emailAddress());
                        } else {
                            field.set(obj, this.faker.internet().emailAddress());
                        }
                    } else if (fieldName.toLowerCase().contains(ADDRESS)) {
                        field.set(obj, this.faker.address().fullAddress());
                    } else if (fieldName.toLowerCase().contains(PHONE)) {
                        field.set(obj, this.faker.phoneNumber().phoneNumber());
                    } else {
                        field.set(obj, this.faker.lorem().word());
                    }
                } else if (field.getType() == Integer.class || field.getType() == int.class) {
                    field.set(obj, this.faker.number().numberBetween(1, 100));
                } else if (field.getType() == Double.class || field.getType() == double.class) {
                    field.set(obj, this.faker.number().randomDouble(2, 1, 100));
                } else if (field.getType() == Boolean.class || field.getType() == boolean.class) {
                    field.set(obj, this.faker.bool().bool()); // Random boolean
                } else if (field.getType() == Long.class || field.getType() == long.class) {
                    field.set(obj, this.faker.number().randomNumber(5, true));
                }
                // Add more type checks as needed
            } catch (IllegalAccessException e) {
                log.error("FakeDataGenerator.generateFakeData error:{}", e.getMessage());
            }
        }
        return obj;
    }

}