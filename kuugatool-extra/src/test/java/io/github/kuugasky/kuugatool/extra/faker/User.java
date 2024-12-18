package io.github.kuugasky.kuugatool.extra.faker;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * User
 *
 * @author kuuga
 * @since 2024/12/18
 */
public class User {

    private String name;
    private String email;
    private String address;
    private String phone;
    private String remark;
    private String content;
    private String more;
    private String pictureUrl;
    private Integer age;
    private Double balance;
    private Boolean isActive;
    private Date date;
    private LocalDateTime cTime;

    private List<Item> items;

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
