package io.github.kuugasky.design.creational.singleton.destroy.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

import static io.github.kuugasky.design.creational.singleton.destroy.reflect.SingletonEnumReflect.SingletonEnum.INSTANCE;
import static io.github.kuugasky.design.creational.singleton.destroy.reflect.SingletonEnumReflect.SingletonEnum.Singleton;

/**
 * SingletonReflect
 * <p>
 * 反射破坏单例
 *
 * @author kuuga
 * @since 2023/6/4-06-04 16:00
 */
public class SingletonEnumReflect {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Singleton singleton1 = INSTANCE.getInstance();
        Singleton singleton = new Singleton();
        System.out.println(Objects.equals(singleton1, singleton));

        // 通过反射获取到构造函数
        Constructor<Singleton> constructor = Singleton.class.getDeclaredConstructor();
        // 将构造函数设置为可访问类型
        constructor.setAccessible(true);
        // 调用构造函数的newInstance创建一个对象
        Singleton singleton2 = constructor.newInstance();
        // 判断反射创建的对象和之前的对象是不是同一个对象
        System.out.println(singleton1 == singleton2);
    }


    /**
     * 使用静态内部类实现单例
     */
    enum SingletonEnum {
        /**
         *
         */
        INSTANCE;

        private final Singleton singleton;

        SingletonEnum() {
            singleton = new Singleton();
        }

        public Singleton getInstance() {
            return singleton;
        }

        static class Singleton {
            private Singleton() {
                if (INSTANCE != null) {
                    throw new RuntimeException("单例对象只能创建一次... ");
                }
            }
        }
    }

}

