package io.github.kuugasky.kuugatool.core.stream;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author kuuga
 * @since 2022-04-09 14:18:54
 */
@Data
@AllArgsConstructor
public class Student {

    private String name;
    private int age;
    private int type;

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
