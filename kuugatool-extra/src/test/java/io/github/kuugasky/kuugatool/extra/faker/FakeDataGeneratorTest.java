package io.github.kuugasky.kuugatool.extra.faker;

import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.junit.jupiter.api.Test;

import static io.github.kuugasky.kuugatool.extra.faker.FakeType.ENGLISH;

class FakeDataGeneratorTest {

    @Test
    void generateFakeData() {
        User user = new User();
        User user1 = FakeDataGenerator.build().generateFakeData(user);
        System.out.println(StringUtil.formatString(user1));
    }

    @Test
    void generateFakeDataEnglish() {
        User user = new User();
        FakeDataGenerator.buildEnglish().generateFakeData(user);
        System.out.println(StringUtil.formatString(user));
    }

    @Test
    void generateFakeDataByFakerType() {
        User user = new User();
        FakeDataGenerator.build(ENGLISH).generateFakeData(user);
        System.out.println(StringUtil.formatString(user));
    }

}

class User {
    private String name;
    private String email;
    private String address;
    private String phone;
    private Integer age;
    private Double balance;
    private Boolean isActive;

    // Default constructor
    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", age=" + age +
                ", balance=" + balance +
                ", isActive=" + isActive +
                '}';
    }
}