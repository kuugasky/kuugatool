package io.github.kuugasky.kuugatool.core.instance;

import io.github.kuugasky.kuugatool.core.object.ObjectUtil;

import java.util.List;
import java.util.Map;

/**
 * 创建类实例工具
 *
 * @author junson
 */
public final class InstanceCreator {

    /**
     * 克隆一个全新的对象
     *
     * @param source 来源对象
     * @param <T>    返回泛型与入参class一致
     * @return 返回对象
     */
    public static <T> T clone(Object source) throws Exception {
        // 拷贝一个同类型空属性对象
        Object target = create(source.getClass());

        if (source instanceof List<?> item) {
            List<Object> result = ObjectUtil.cast(target);
            result.addAll(item);
            return ObjectUtil.cast(result);
        }
        if (source instanceof Map<?, ?> map) {
            Map<Object, Object> result = ObjectUtil.cast(target);
            result.putAll(map);
            return ObjectUtil.cast(result);
        }

        ReflectionUtil.copyProperties(source, target);
        return ObjectUtil.cast(target);
    }

    /**
     * 创建类实例
     * <p>
     * 使用无参构造函数创建，相当于new Object();
     *
     * @param clazz 类型
     * @return 传入类型对应实例
     */
    public static <T> T create(Class<T> clazz) throws Exception {
        if (clazz.isInterface()) {
            throw new Exception("Specified class is an interface");
        }
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (InstantiationException ex) {
            throw new Exception("Is it an abstract class?");
        } catch (IllegalAccessException ex) {
            throw new Exception("Is the constructor accessible?");
        } catch (Exception e) {
            throw new Exception("create new instance failed!");
        }
    }

    /**
     * 创建类实例
     * <p>
     * 使用单参构造函数创建初始化
     *
     * @param clazz         类型
     * @param parameterType 参数类型
     * @param initArgs      初始化参数
     * @return 传入类型对应实例
     */
    public static <T> T create(Class<T> clazz, Class<?> parameterType, Object initArgs) {
        T object;
        try {
            object = clazz.getConstructor(parameterType).newInstance(initArgs);
        } catch (Exception e) {
            throw new RuntimeException("create new instance failed!", e);
        }

        return object;
    }

    /**
     * 创建类实例
     * <p>
     * 使用多参构造函数创建初始化
     *
     * @param clazz          类型
     * @param parameterTypes 参数类型列表
     * @param initArgs       初始化参数列表
     * @return 传入类型对应实例
     */
    public static <T> T create(Class<T> clazz, Class<?>[] parameterTypes, Object[] initArgs) {
        T object;
        try {
            object = clazz.getConstructor(parameterTypes).newInstance(initArgs);
        } catch (Exception e) {
            throw new RuntimeException("create new instance failed!", e);
        }

        return object;
    }

    /**
     * 创建类实例
     * <p>
     * 使用包名+类名反射创建
     *
     * @param className 类型全名
     * @return 传入类型对应实例
     */
    @SuppressWarnings("unchecked")
    public static <T> T create(String className) {
        Object object;
        try {
            object = Class.forName(className).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("create new instance failed!", e);
        }

        return (T) object;
    }

    /**
     * 创建类实例
     * <p>
     * 使用class.forName创建后调用单参构造函数初始化
     *
     * @param className     类型全名
     * @param parameterType 参数类型
     * @param initArgs      初始化参数
     * @return 传入类型对应实例
     */
    @SuppressWarnings("unchecked")
    public static <T> T create(String className, Class<?> parameterType, Object initArgs) {
        Object object;
        try {
            object = Class.forName(className).getConstructor(parameterType).newInstance(initArgs);
        } catch (Exception e) {
            throw new RuntimeException("create new instance failed!", e);
        }

        return (T) object;
    }

    /**
     * 创建类实例
     * <p>
     * 使用class.forName创建后调用多参构造函数初始化
     *
     * @param className      类型全名
     * @param parameterTypes 参数类型列表
     * @param initArgs       初始化参数列表
     * @return 传入类型对应实例
     */
    @SuppressWarnings("unchecked")
    public static <T> T create(String className, Class<?>[] parameterTypes, Object[] initArgs) {
        Object object;
        try {
            object = Class.forName(className).getConstructor(parameterTypes).newInstance(initArgs);
        } catch (Exception e) {
            throw new RuntimeException("create new instance failed!", e);
        }

        return (T) object;
    }

}