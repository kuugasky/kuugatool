package io.github.kuugasky.kuugatool.core.serialization;

import io.github.kuugasky.kuugatool.core.exception.UtilException;
import io.github.kuugasky.kuugatool.core.io.IoUtil;
import org.springframework.util.FastByteArrayOutputStream;

import java.io.*;

/**
 * 序列化工具类
 *
 * @author kuuga
 */
public final class SerializationUtil {

    /**
     * 定义私有构造函数来屏蔽这个隐式公有构造函数
     */
    private SerializationUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static final String FILE_NAME = "/Users/kuuga/Documents/serializationObject.bin";

    /**
     * 将对象序列化后写入文件
     *
     * @param object object
     */
    public static void writeObject(Serializable object, File file) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将文件内序列化字符串反序列化为对象
     *
     * @param file 文件
     * @return Object
     */
    public static Object readObject(File file) {
        Object obj = null;
        try (ObjectInput input = new ObjectInputStream(new FileInputStream(file))) {
            obj = input.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }


    /**
     * 序列化后拷贝流的方式克隆「深度克隆」
     * 对象必须实现Serializable接口
     *
     * @param <T> 对象类型
     * @param obj 被克隆对象
     * @return 克隆后的对象
     * @throws UtilException IO异常和ClassNotFoundException封装
     */
    public static <T> T clone(T obj) {
        if (!(obj instanceof Serializable)) {
            throw new UtilException("对象未实现序列化接口");
        }
        return deserialize(serialize(obj));
    }

    /**
     * 序列化
     * 对象必须实现Serializable接口
     *
     * @param <T> 对象类型
     * @param obj 要被序列化的对象
     * @return 序列化后的字节码
     */
    public static <T> byte[] serialize(T obj) {
        if (!(obj instanceof Serializable)) {
            return null;
        }
        final FastByteArrayOutputStream byteOut = new FastByteArrayOutputStream();
        IoUtil.writeObjects(byteOut, false, (Serializable) obj);
        return byteOut.toByteArray();
    }

    /**
     * 反序列化<br>
     * 对象必须实现Serializable接口
     *
     * <p>
     * 注意！！！ 此方法不会检查反序列化安全，可能存在反序列化漏洞风险！！！
     * </p>
     *
     * @param <T>   对象类型
     * @param bytes 反序列化的字节码
     * @return 反序列化后的对象
     */
    public static <T> T deserialize(byte[] bytes) {
        return IoUtil.readObj(new ByteArrayInputStream(bytes));
    }

}