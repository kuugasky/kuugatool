package io.github.kuugasky.design.creational.singleton.destroy.reflect;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * SingletonReflect
 * <p>
 * 反射破坏单例
 *
 * @author kuuga
 * @since 2023/6/4-06-04 16:00
 */
public class SingletonDoubleLockReflect {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Singleton singleton1 = Singleton.getSingleton();

        // 通过反射获取到构造函数
        Constructor<Singleton> constructor = Singleton.class.getDeclaredConstructor();
        // 将构造函数设置为可访问类型
        // setAccessible(true)，使得反射对象在使用时应该取消 Java 语言访问检查，使得私有的构造函数能够被访问。
        constructor.setAccessible(true);
        // 调用构造函数的newInstance创建一个对象
        Singleton singleton2 = constructor.newInstance();
        // 判断反射创建的对象和之前的对象是不是同一个对象
        System.out.println(singleton1 == singleton2);
    }

    /**
     * 使用双重校验锁方式实现单例
     */
    static class Singleton implements Serializable {
        private volatile static Singleton singleton;

        private Singleton() {
            if (singleton != null) {
                throw new RuntimeException("单例对象只能创建一次... ");
            }
        }

        public static Singleton getSingleton() {
            if (singleton == null) {
                synchronized (Singleton.class) {
                    if (singleton == null) {
                        singleton = new Singleton();
                    }
                }
            }
            return singleton;
        }
    }

}

