package io.github.kuugasky.design.creational.singleton.demo;

/**
 * SingleEnum
 *
 * @author kuuga
 * @since 2023/6/4-06-04 15:56
 */
public enum SingletonEnum {

    /**
     * 如果把枚举类进行反编译，你会发现他也是使用了static final来修饰每一个枚举项。
     */
    INSTANCE;

    private static final Singleton SINGLETON;

    static {
        SINGLETON = new Singleton();
    }

    public Singleton getSingleton() {
        return SINGLETON;
    }

    static class Singleton {
        private Singleton() {

        }
    }

}
