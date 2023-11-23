package io.github.kuugasky.kuugatool.core.clazz;

import io.github.kuugasky.kuugatool.core.collection.ArrayUtil;
import io.github.kuugasky.kuugatool.core.instance.ReflectionUtil;
import io.github.kuugasky.kuugatool.core.lang.Assert;
import org.springframework.lang.NonNull;

import java.lang.reflect.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * ClassUtil
 *
 * @author kuuga
 * @since 2021-01-21 下午4:07
 */
public final class ClassUtil {

    /**
     * 定义私有构造函数来屏蔽这个隐式公有构造函数
     */
    private ClassUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    // loadClass ====================================================================================================================================

    /**
     * 加载类
     *
     * @param <T>           对象类型
     * @param className     类名
     * @param isInitialized 是否初始化类（调用static模块内容和初始化static属性）
     * @return 类
     */
    @SuppressWarnings("unchecked")
    public static <T> Class<T> loadClass(String className, boolean isInitialized) {
        return (Class<T>) ClassLoaderUtil.loadClass(className, isInitialized);
    }

    /**
     * 加载类并初始化
     *
     * @param <T>       对象类型
     * @param className 类名
     * @return 类
     */
    public static <T> Class<T> loadClass(String className) {
        return loadClass(className, true);
    }

    // getClassLoader ====================================================================================================================================

    /**
     * 获取{@link ClassLoader}<br>
     * 获取顺序如下：<br>
     *
     * <pre>
     * 1、获取当前线程的ContextClassLoader
     * 2、获取{@link ClassLoaderUtil}类对应的ClassLoader
     * 3、获取系统ClassLoader（{@link ClassLoader#getSystemClassLoader()}）
     * </pre>
     *
     * @return 类加载器
     */
    public static ClassLoader getClassLoader() {
        return ClassLoaderUtil.getClassLoader();
    }

    /**
     * 获得对象数组的类数组
     *
     * @param objects 对象数组，如果数组中存在{@code null}元素，则此元素被认为是Object类型
     * @return 类数组
     */
    public static Class<?>[] getClasses(Object... objects) {
        Class<?>[] classes = new Class<?>[objects.length];
        Object obj;
        for (int i = 0; i < objects.length; i++) {
            obj = objects[i];
            if (obj instanceof NullWrapperBean) {
                // 自定义null值的参数类型
                classes[i] = ((NullWrapperBean<?>) obj).getWrappedClass();
            } else if (null == obj) {
                classes[i] = Object.class;
            } else {
                classes[i] = obj.getClass();
            }
        }
        return classes;
    }

    /**
     * 比较判断types1和types2两组类，如果types1中所有的类都与types2对应位置的类相同，或者是其父类或接口，则返回<code>true</code>
     *
     * @param types1 类组1
     * @param types2 类组2
     * @return 是否相同、父类或接口
     */
    public static boolean isAllAssignableFrom(Class<?>[] types1, Class<?>[] types2) {
        if (ArrayUtil.isEmpty(types1) && ArrayUtil.isEmpty(types2)) {
            return true;
        }
        if (null == types1 || null == types2) {
            // 任何一个为null不相等（之前已判断两个都为null的情况）
            return false;
        }
        if (types1.length != types2.length) {
            return false;
        }

        Class<?> type1;
        Class<?> type2;
        for (int i = 0; i < types1.length; i++) {
            type1 = types1[i];
            type2 = types2[i];
            if (isBasicType(type1) && isBasicType(type2)) {
                // 原始类型和包装类型存在不一致情况
                if (BasicType.unWrap(type1) != BasicType.unWrap(type2)) {
                    return false;
                }
            } else if (!type1.isAssignableFrom(type2)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 查找指定类中的名为methodName的方法（包括非public方法），也包括父类和Object类的方法 找不到方法会返回<code>null</code>
     *
     * @param clazz          被查找的类
     * @param methodName     方法名
     * @param parameterTypes 方法入参参数类型
     * @return 方法
     * @throws SecurityException 无访问权限抛出异常
     */
    public static Method getDeclaredMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) throws SecurityException {
        return ReflectionUtil.getMethod(clazz, methodName, parameterTypes);
    }

    /**
     * 查找指定类中的名为fieldName的字段（包括非public方法），也包括父类和Object类的方法 找不到字段会返回<code>null</code>
     *
     * @param clazz     被查找的类
     * @param fieldName 字段名
     * @return 字段
     */
    public static Field getDeclaredField(Class<?> clazz, String fieldName) {
        return ReflectionUtil.getDeclaredField(clazz, fieldName);
    }

    // 类/字段各种类型判断 ====================================================================================================================================

    /**
     * 是否为基本类型（包括包装类和原始类）
     *
     * @param clazz 类
     * @return 是否为基本类型
     */
    public static <T> boolean isBasicType(Class<T> clazz) {
        if (null == clazz) {
            return false;
        }
        return (clazz.isPrimitive() || ClassUtil.isPrimitiveWrapper(clazz));
    }

    /**
     * 是否为基本类型（包括包装类和原始类）
     *
     * @param field 字段
     * @return 是否为基本类型
     */
    public static boolean isBasicType(Field field) {
        return isBasicType(field.getType());
    }

    /**
     * 是否为包装类型
     *
     * @param clazz 类
     * @return 是否为包装类型
     */
    public static <T> boolean isPrimitiveWrapper(Class<T> clazz) {
        if (null == clazz) {
            return false;
        }
        return BasicType.WRAPPER_PRIMITIVE_MAP.containsKey(clazz);
    }

    /**
     * 是否基础数据类型，非自定义Object非集合
     */
    public static <T> boolean isBasicDataType(Class<T> type) {
        return type == int.class ||
                type == Integer.class ||
                type == String.class ||
                type == Date.class ||
                type == Enum.class ||
                type == char.class ||
                type == float.class ||
                type == Float.class ||
                type == double.class ||
                type == Double.class ||
                type == BigDecimal.class ||
                type == BigInteger.class ||
                type == boolean.class ||
                type == Boolean.class ||
                type == byte.class ||
                type == Byte.class ||
                type == short.class ||
                type == Short.class ||
                type == long.class ||
                type == Long.class;
    }

    // 获取Class的默认值 ============================================================================================================================================

    /**
     * 获得默认值列表
     *
     * @param classes 值类型
     * @return 默认值列表
     * @since 3.0.9
     */
    public static Object[] getDefaultValues(Class<?>... classes) {
        final Object[] values = new Object[classes.length];
        for (int i = 0; i < classes.length; i++) {
            values[i] = getDefaultValue(classes[i]);
        }
        return values;
    }

    /**
     * 获取指定类型分的默认值<br>
     * 默认值规则为：
     *
     * <pre>
     * 1、如果为原始类型，返回0
     * 2、非原始类型返回{@code null}
     * </pre>
     *
     * @param clazz 类
     * @return 默认值
     * @since 3.0.8
     */
    public static Object getDefaultValue(Class<?> clazz) {
        if (clazz.isPrimitive()) {
            if (long.class == clazz) {
                return 0L;
            } else if (int.class == clazz) {
                return 0;
            } else if (short.class == clazz) {
                return (short) 0;
            } else if (char.class == clazz) {
                return (char) 0;
            } else if (byte.class == clazz) {
                return (byte) 0;
            } else if (double.class == clazz) {
                return 0D;
            } else if (float.class == clazz) {
                return 0f;
            } else if (boolean.class == clazz) {
                return false;
            }
        }
        return null;
    }

    // isPublic ============================================================================================================================================================

    /**
     * 指定类是否为Public
     *
     * @param clazz 类
     * @return 是否为public
     */
    public static <T> boolean isPublic(Class<T> clazz) {
        Assert.notNull(clazz, "Class to provided is null.");
        return Modifier.isPublic(clazz.getModifiers());
    }

    /**
     * 指定方法是否为Public
     *
     * @param method 方法
     * @return 是否为public
     */
    public static boolean isPublic(Method method) {
        Assert.notNull(method, "Method to provided is null.");
        return Modifier.isPublic(method.getModifiers());
    }

    /**
     * 指定方法是否为Public
     *
     * @param field 字段
     * @return 是否为public
     */
    public static boolean isPublic(Field field) {
        Assert.notNull(field, "Field to provided is null.");
        return Modifier.isPublic(field.getModifiers());
    }

    /**
     * 指定类是否为非public
     *
     * @param clazz 类
     * @return 是否为非public
     */
    public static boolean isNotPublic(Class<?> clazz) {
        return !isPublic(clazz);
    }

    /**
     * 指定方法是否为非public
     *
     * @param method 方法
     * @return 是否为非public
     */
    public static boolean isNotPublic(Method method) {
        return !isPublic(method);
    }

    /**
     * 指定字段是否为非public
     *
     * @param field 字段
     * @return 是否为非public
     */
    public static boolean isNotPublic(Field field) {
        return !isPublic(field);
    }

    // isPrivate ============================================================================================================================================================

    /**
     * 指定类是否为Private
     *
     * @param clazz 类
     * @return 是否为private
     */
    public static <T> boolean isPrivate(Class<T> clazz) {
        Assert.notNull(clazz, "Class to provided is null.");
        return Modifier.isPrivate(clazz.getModifiers());
    }

    /**
     * 指定方法是否为Private
     *
     * @param method 方法
     * @return 是否为private
     */
    public static boolean isPrivate(Method method) {
        Assert.notNull(method, "Method to provided is null.");
        return Modifier.isPrivate(method.getModifiers());
    }

    /**
     * 指定方法是否为Private
     *
     * @param field 字段
     * @return 是否为private
     */
    public static boolean isPrivate(Field field) {
        Assert.notNull(field, "Field to provided is null.");
        return Modifier.isPrivate(field.getModifiers());
    }

    // isStatic ============================================================================================================================================================

    /**
     * 是否为静态方法
     *
     * @param tClass 类
     * @return 是否为静态方法
     */
    public static <T> boolean isStatic(Class<T> tClass) {
        Assert.notNull(tClass, "Class to provided is null.");
        return Modifier.isStatic(tClass.getModifiers());
    }

    /**
     * 是否为静态方法
     *
     * @param method 方法
     * @return 是否为静态方法
     */
    public static boolean isStatic(Method method) {
        Assert.notNull(method, "Method to provided is null.");
        return Modifier.isStatic(method.getModifiers());
    }

    /**
     * 是否为静态字段
     *
     * @param field 字段
     * @return 是否为静态方法
     */
    public static boolean isStatic(Field field) {
        Assert.notNull(field, "Field to provided is null.");
        return Modifier.isStatic(field.getModifiers());
    }

    // isFinal ============================================================================================================================================================

    /**
     * 指定类是否为Private
     *
     * @param clazz 类
     * @return 是否为private
     */
    public static <T> boolean isFinal(Class<T> clazz) {
        Assert.notNull(clazz, "Class to provided is null.");
        return Modifier.isFinal(clazz.getModifiers());
    }

    /**
     * 指定方法是否为Private
     *
     * @param method 方法
     * @return 是否为private
     */
    public static boolean isFinal(Method method) {
        Assert.notNull(method, "Method to provided is null.");
        return Modifier.isFinal(method.getModifiers());
    }

    /**
     * 指定方法是否为Private
     *
     * @param field 字段
     * @return 是否为private
     */
    public static boolean isFinal(Field field) {
        Assert.notNull(field, "Field to provided is null.");
        return Modifier.isFinal(field.getModifiers());
    }

    // isEnum ============================================================================================================================================================

    /**
     * 判断类是否为枚举类型
     *
     * @param clazz 类
     * @return 是否为枚举类型
     * @since 3.2.0
     */
    public static <T> boolean isEnum(Class<T> clazz) {
        return null != clazz && clazz.isEnum();
    }

    /**
     * 是否枚举
     */
    public static boolean isEnum(Field field) {
        if (field != null) {
            Class<?> clz = field.getType();
            return clz.isEnum();
        }
        return false;
    }

    // setAccessible ============================================================================================================================================================

    /**
     * 栏位是否是数组
     */
    public static boolean isCollectionField(Field field) {
        if (field == null) {
            return false;
        }
        Type genericType = field.getGenericType();
        return genericType instanceof ParameterizedType;
    }

    // setAccessible ============================================================================================================================================================

    /**
     * 设置方法为可访问
     *
     * @param method 方法
     * @return 方法
     */
    public static Method setAccessible(Method method) {
        if (null != method && !method.canAccess(method)) {
            // if (null != method && !method.isAccessible()) {
            method.setAccessible(true);
        }
        return method;
    }

    /**
     * 是否为抽象类
     *
     * @param clazz 类
     * @return 是否为抽象类
     */
    public static boolean isAbstract(Class<?> clazz) {
        return Modifier.isAbstract(clazz.getModifiers());
    }

    /**
     * 是否为标准的类<br>
     * 这个类必须：
     *
     * <pre>
     * 1、非接口
     * 2、非抽象类
     * 3、非Enum枚举
     * 4、非数组
     * 5、非注解
     * 6、非原始类型（int, long等）
     * </pre>
     *
     * @param clazz 类
     * @return 是否为标准类
     */
    public static <T> boolean isNormalClass(Class<T> clazz) {
        // 非空
        boolean notNull = null != clazz;

        if (!notNull) {
            return false;
        }

        // 非接口
        boolean notInterface = !clazz.isInterface();
        // 非抽象类
        boolean notAbstract = !isAbstract(clazz);
        // 非Enum枚举
        boolean notEnum = !clazz.isEnum();
        // 非数组
        boolean notArray = !clazz.isArray();
        // 非注解
        boolean notAnnotation = !clazz.isAnnotation();
        // 非合成类
        boolean notSynthetic = !clazz.isSynthetic();
        // 非原始类型（int, long等）
        boolean notPrimitive = !clazz.isPrimitive();
        return notInterface
                && notAbstract
                && notEnum
                && notArray
                && notAnnotation
                && notSynthetic
                && notPrimitive;
    }

    /**
     * 是否是TypeVariable: 是各种类型变量的公共父接口
     */
    public static boolean isTypeVariable(Field field) {
        if (field == null) {
            return false;
        }
        Type genericType = field.getGenericType();
        return genericType instanceof TypeVariable;
    }


    // 获取当前运行的各种名称（类名/方法名/行数/运行信息） ====================================================================================================================================

    /**
     * 获取当前运行的类名
     * ps:调用此方法执行代码块的类名
     */
    public static String getRuntimeClassName() {
        StackTraceElement[] stacks = new Throwable().getStackTrace();
        return stacks[1].getClassName();
    }

    /**
     * 获取当前运行的方法名
     * ps:调用此方法执行代码块的方法名
     */
    public static String getRuntimeMethodName() {
        StackTraceElement[] stacks = new Throwable().getStackTrace();
        return stacks[1].getMethodName();
    }

    /**
     * 获取当前运行行数
     * ps:调用此方法执行代码所在行数
     */
    public static int getRuntimeLineNumber() {
        StackTraceElement[] stacks = new Throwable().getStackTrace();
        return stacks[1].getLineNumber();
    }

    /**
     * 获取当前运行信息
     * ps:调用此方法执行代码运行信息
     */
    public static RuntimeTraceInfo getRuntimeTraceInfo() {
        return getRuntimeTraceInfo(2);
    }

    /**
     * 获取当前指定层级运行信息
     * ps:调用此方法执行代码运行信息
     *
     * @param stackTraceElementIndex 堆栈跟踪元素层级索引
     * @return 运行信息
     */
    public static RuntimeTraceInfo getRuntimeTraceInfo(int stackTraceElementIndex) {
        StackTraceElement[] stacks = new Throwable().getStackTrace();
        StackTraceElement stackTraceElement = stacks[stackTraceElementIndex];
        return RuntimeTraceInfo.builder()
                .className(stackTraceElement.getClassName())
                .methodName(stackTraceElement.getMethodName())
                .lineNumber(stackTraceElement.getLineNumber())
                .classLoaderName(stackTraceElement.getClassLoaderName())
                .fileName(stackTraceElement.getFileName())
                .clazz(stackTraceElement.getClass())
                .moduleName(stackTraceElement.getModuleName())
                .moduleVersion(stackTraceElement.getModuleVersion())
                .build();
    }

    /**
     * 判断subType是否superType的子类或实现类
     *
     * @param superType 父类or接口
     * @param subType   子类or实现类
     * @return boolean
     */
    public static boolean isAssignable(@NonNull Class<?> superType, @NonNull Class<?> subType) {
        return superType.isAssignableFrom(subType);
    }

}
