package io.github.kuugasky.design.creational.singleton.destroy.serializable;

import java.io.*;

/**
 * SingletonReflect
 * <p>
 * 反射破坏单例
 *
 * @author kuuga
 * @since 2023/6/4-06-04 16:00
 */
public class SingletonEnumSerializable {

    /**
     * 在序列化的时候Java仅仅是将枚举对象的name属性输出到结果中，反序列化的时候则是通过java.lang.Enum的valueOf方法来根据名字查找枚举对象。
     * 同时，编译器是不允许任何对这种序列化机制的定制的，因此禁用了writeObject、readObject、readObjectNoData、writeReplace和readResolve等方法。
     * <p>
     * 普通的Java类的反序列化过程中，会通过反射调用类的默认构造函数来初始化对象。所以，即使单例中构造函数是私有的，也会被反射给破坏掉。由于反序列化后的对象是重新new出来的，所以这就破坏了单例。
     * <p>
     * 但是，枚举的反序列化并不是通过反射实现的。所以，也就不会发生由于反序列化导致的单例破坏问题。
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // Write Obj to file
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("tempFile"));
        oos.writeObject(SingletonEnum.INSTANCE);
        // Read Obj from file
        File file = new File("tempFile");
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        SingletonEnum newInstance = (SingletonEnum) ois.readObject();
        // 判断是否是同一个对象
        System.out.println(newInstance.getInstance() == SingletonEnum.INSTANCE.getInstance());
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
                // if (INSTANCE != null) {
                //     throw new RuntimeException("单例对象只能创建一次... ");
                // }
            }
        }
    }

}

