package io.github.kuugasky.kuugatool.core.object;

import org.junit.jupiter.api.Test;

import java.util.Objects;

/**
 * ShallowCopyTest
 *
 * @author kuuga
 * @since 2022/7/2 16:01
 */
public class DeepCopyTest {

    public static class Subject {

        private String name;

        public Subject(String s) {
            name = s;
        }

        public String getName() {
            return name;
        }

        public void setName(String s) {
            name = s;
        }
    }

    public static class Student implements Cloneable {

        // 对象引用
        private final Subject subj;
        private String name;

        public Student(String s, String sub) {
            name = s;
            subj = new Subject(sub);
        }

        public Subject getSubj() {
            return subj;
        }

        public String getName() {
            return name;
        }

        public void setName(String s) {
            name = s;
        }

        /**
         * 重写clone()方法
         */
        public Object clone() {
            // 深拷贝，创建拷贝类的一个新对象，这样就和原始对象相互独立
            return new Student(name, subj.getName());
        }
    }

    @Test
    void test() {
        // 原始对象
        Student stud = new Student("杨充", "潇湘剑雨");
        System.out.println("原始对象: " + stud.getName() + " - " + stud.getSubj().getName());

        // 拷贝对象
        Student clonedStud = (Student) stud.clone();
        System.out.println("拷贝对象: " + clonedStud.getName() + " - " + clonedStud.getSubj().getName());

        // 原始对象和拷贝对象是否一样：
        System.out.println("原始对象和拷贝对象是否一样: " + (stud == clonedStud));
        // 原始对象和拷贝对象的name属性是否一样
        System.out.println("原始对象和拷贝对象的name属性是否一样: " + (Objects.equals(stud.getName(), clonedStud.getName())));
        // 原始对象和拷贝对象的subj属性是否一样
        System.out.println("原始对象和拷贝对象的subj属性是否一样: " + (stud.getSubj() == clonedStud.getSubj()));

        stud.setName("小杨逗比");
        stud.getSubj().setName("潇湘剑雨大侠");
        System.out.println("更新后的原始对象: " + stud.getName() + " - " + stud.getSubj().getName());
        System.out.println("更新原始对象后的克隆对象: " + clonedStud.getName() + " - " + clonedStud.getSubj().getName());

        /* 在这个例子中，让要拷贝的类Student实现了Clonable接口并重写Object类的clone()方法，然后在方法内部调用super.clone()方法。从输出结果中我们可以看到，对原始对象stud的"name"属性所做的改变并没有影响到拷贝对象clonedStud，但是对引用对象subj的"name"属性所做的改变影响到了拷贝对象clonedStud。 */
    }

}
