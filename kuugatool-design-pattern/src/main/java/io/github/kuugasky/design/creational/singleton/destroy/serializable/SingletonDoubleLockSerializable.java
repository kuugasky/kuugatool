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
public class SingletonDoubleLockSerializable {

    /**
     * 为了便于理解，忽略关闭流操作及删除文件操作。真正编码时千万不要忘记
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // Write Obj to file
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("tempFile"));
        oos.writeObject(Singleton.getSingleton());
        // Read Obj from file
        File file = new File("tempFile");
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        Singleton newInstance = (Singleton) ois.readObject();
        // 判断是否是同一个对象
        System.out.println(newInstance == Singleton.getSingleton());
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

