package io.github.kuugasky.design.creational.singleton.demo;

/**
 * SingletonDoubleLock
 *
 * @author kuuga
 * @since 2023/6/4-06-04 15:58
 */
public class SingletonDoubleLock {

    private volatile static SingletonDoubleLock singleton;

    private SingletonDoubleLock() {
    }

    public static SingletonDoubleLock getSingleton() {
        if (singleton == null) {
            synchronized (SingletonDoubleLock.class) {
                if (singleton == null) {
                    singleton = new SingletonDoubleLock();
                }
            }
        }
        return singleton;
    }

}
