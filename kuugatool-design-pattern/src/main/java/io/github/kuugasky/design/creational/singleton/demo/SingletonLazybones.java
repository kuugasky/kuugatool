package io.github.kuugasky.design.creational.singleton.demo;

/**
 * 单例模式-懒汉
 *
 * @author kuuga
 * @since 2023/6/3-06-03 14:52
 */
public class SingletonLazybones {

    private static SingletonLazybones instance;

    private SingletonLazybones() {
    }

    public static synchronized SingletonLazybones getInstance() {
        if (instance == null) {
            instance = new SingletonLazybones();
        }
        return instance;
    }

}
