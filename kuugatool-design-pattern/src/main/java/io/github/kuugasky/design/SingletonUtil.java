package io.github.kuugasky.design;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SingletonUtil
 *
 * @author kuuga
 * @since 2023/4/10-04-10 09:32
 */
public class SingletonUtil {

    /**
     * 用于存储类的单例实例的Map
     */
    private static final Map<Class<?>, Object> INSTANCES = new ConcurrentHashMap<>();

    /**
     * 返回指定类的单例实例
     *
     * @param clazz 所需单例实例的类
     * @return 指定类的单例实例
     * @throws NoSuchMethodException     没有这样的方法例外
     * @throws InvocationTargetException 调用目标异常
     * @throws InstantiationException    如果此类表示抽象类、接口、数组类、基本类型或void；或者如果类没有无参构造函数；或者如果实例化失败
     * @throws IllegalAccessException    如果类或其无参构造函数不可访问
     */
    public static <T> T getSingletonInstance(Class<T> clazz)
            throws NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException {

        if (!INSTANCES.containsKey(clazz)) {
            synchronized (SingletonUtil.class) {
                if (!INSTANCES.containsKey(clazz)) {
                    T instance = clazz.getDeclaredConstructor().newInstance();
                    INSTANCES.put(clazz, instance);
                }
            }
        }
        return clazz.cast(INSTANCES.get(clazz));
    }

}
