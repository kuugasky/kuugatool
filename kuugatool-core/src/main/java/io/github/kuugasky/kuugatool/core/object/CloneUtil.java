package io.github.kuugasky.kuugatool.core.object;

import org.apache.commons.lang.SerializationUtils;

import java.io.*;

/**
 * CloneUtil
 *
 * @author kuuga
 */
public final class CloneUtil {

    /*
        浅拷贝是按位拷贝对象，它会创建一个新对象，这个对象有着原始对象属性值的一份精确拷贝。
        如果属性是基本类型，拷贝的就是基本类型的值；如果属性是内存地址（引用类型），拷贝的就是内存地址 ，因此如果其中一个对象改变了这个地址，就会影响到另一个对象。

        深拷贝会拷贝所有的属性,并拷贝属性指向的动态分配的内存。当对象和它所引用的对象一起拷贝时即发生深拷贝。
        深拷贝相比于浅拷贝速度较慢并且花销较大。
     */

    /**
     * 定义私有构造函数来屏蔽这个隐式公有构造函数
     */
    private CloneUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 深层拷贝 - 需要类继承序列化接口
     *
     * @param <T> 对象类型
     * @param obj 原对象
     * @return 深度拷贝的对象
     * @throws IOException            IOException
     * @throws ClassNotFoundException 类找不到异常
     * @see java.io.Closeable
     * @see AutoCloseable 不用进行关闭
     */
    @SuppressWarnings("unchecked")
    public static <T> T cloneByStream(T obj) throws IOException, ClassNotFoundException {
        // 如果子类没有继承该接口，这一步会报错
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(obj);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

        Object object = objectInputStream.readObject();
        return (T) object;
    }

    /**
     * Apache Commons Lang提供的SerializationUtils工具实现深拷贝
     *
     * @param object 序列化对象
     * @return object
     */
    public static <T> T cloneByApache(Serializable object) {
        return ObjectUtil.cast(SerializationUtils.clone(object));
    }

    /*
    还可以通过json把对象序列化成json字符串，再将字符串反序列化成对象，如fastjson
    User newUser = JSON.parseObject(JSON.toJSONString(user), User.class);
     */

}
