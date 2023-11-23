package io.github.kuugasky.kuugatool.core.jdk17;

import lombok.Data;

import java.io.*;

/**
 * JEP415
 *
 * @author kuuga
 * @since 2022/8/8 14:24
 */
public class JEP415 {

    /**
     * 在 Java 17 中可以自定义反序列化过滤器，拦截不允许的类。
     * 假设 Dog 类中的 Poc 是恶意构造的类，但是正常反序列化是可以成功的。
     * 但是jdk17，通过ObjectInputFilter可以拦截
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Dog dog = new Dog("哈士奇");
        dog.setPoc(new Poc());
        // 序列化 - 对象转字节数组
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);) {
            objectOutputStream.writeObject(dog);
        }
        byte[] bytes = byteArrayOutputStream.toByteArray();
        // 反序列化 - 字节数组转对象
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        // 允许 com.wdbyte.java17.Dog 类，允许 java.base 中的所有类，拒绝其他任何类
        ObjectInputFilter filter = ObjectInputFilter.Config.createFilter(
                "com.wdbyte.java17.Dog;java.base/*;!*");
        objectInputStream.setObjectInputFilter(filter);
        Object object = objectInputStream.readObject();
        System.out.println(object.toString());
    }

}

@Data
class Dog implements Serializable {
    private String name;
    private Poc poc;

    public Dog(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Dog{" + "name='" + name + '\'' + '}';
    }
    // get...set...
}

class Poc implements Serializable {
}

