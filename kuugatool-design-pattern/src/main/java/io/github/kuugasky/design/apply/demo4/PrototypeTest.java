package io.github.kuugasky.design.apply.demo4;

/**
 * PrototypeTest
 *
 * @author kuuga
 * @since 2023/10/13 12:11
 */
public class PrototypeTest {

    public static void main(String[] args) {
        Resume resume = new Resume("kuuga", "男", "33");
        resume.setWork(new WorkExperience("2012-2013", "a company"));
        resume.display();

        Resume clone = resume.clone();
        clone.setName("秦枫");
        clone.setAge("18");
        clone.display();

        Resume clone1 = resume.clone();
        clone1.setName("秦枫2");
        clone1.setAge("20");
        clone1.setWork(new WorkExperience("2022-2023", "BAT"));
        clone1.display();
    }

}
