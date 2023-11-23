package io.github.kuugasky.design.creational.singleton.demo;

/**
 * SingleStaticInner
 *
 * @author kuuga
 * @since 2023/6/4-06-04 15:55
 */
public class SingletonStaticInner {

    /**
     * 这种方式和饿汉方式只有细微差别，只是做法上稍微优雅一点。这种方式是Singleton类被装载了，instance不一定被初始化。<br>
     * 因为SingletonHolder类没有被主动使用，只有显示通过调用getInstance方法时，才会显示装载SingletonHolder类，从而实例化instance。。。<br>
     * 但是，原理和饿汉一样。
     */
    private static class SingletonHolder {
        private static final SingletonStaticInner INSTANCE = new SingletonStaticInner();
    }

    private SingletonStaticInner() {
    }

    public static SingletonStaticInner getInstance() {
        return SingletonHolder.INSTANCE;
    }

}
