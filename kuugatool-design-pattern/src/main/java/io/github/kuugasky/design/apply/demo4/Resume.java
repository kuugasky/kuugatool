package io.github.kuugasky.design.apply.demo4;

import lombok.Data;

/**
 * 个人简历
 *
 * @author kuuga
 * @since 2023/10/13 12:07
 */
@Data
public class Resume implements Cloneable {

    private String name;
    private String sex;
    private String age;

    private WorkExperience work;

    public Resume(String name, String sex, String age) {
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    public void display() {
        String format = String.format("姓名:%s\n性别:%s\n年龄:%s\n工作:%s (%s)",
                name, sex, age, work.getTimeArea(), work.getCompany());
        System.out.println(format);
    }

    @Override
    public Resume clone() {
        try {
            Resume clone = (Resume) super.clone();
            clone.work = this.work.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

}
