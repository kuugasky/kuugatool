package io.github.kuugasky.kuugatool.core.instance;

import io.github.kuugasky.kuugatool.core.clazz.ClassUtil;
import io.github.kuugasky.kuugatool.core.clazz.NullWrapperBean;
import io.github.kuugasky.kuugatool.core.collection.ArrayUtil;
import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.constants.KuugaConstants;
import io.github.kuugasky.kuugatool.core.exception.UtilException;
import io.github.kuugasky.kuugatool.core.lang.Assert;
import io.github.kuugasky.kuugatool.core.lang.single.SimpleCache;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import io.github.kuugasky.kuugatool.core.queue.FifoLinkedQueue;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.*;
import java.util.*;

/**
 * 反射工具类
 *
 * @author kuuga
 */
public final class ReflectionUtil {

    public static final String IS = "is";
    public static final String GET = "get";
    public static final String SET = "set";

    private final static Logger logger = LoggerFactory.getLogger(ReflectionUtil.class);

    /**
     * 定义私有构造函数来屏蔽这个隐式公有构造函数
     */
    private ReflectionUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 构造对象缓存
     */
    private static final SimpleCache<Class<?>, Constructor<?>[]> CONSTRUCTORS_CACHE = new SimpleCache<>();
    /**
     * 字段缓存
     */
    private static final SimpleCache<Class<?>, List<Field>> FIELDS_CACHE = new SimpleCache<>();
    /**
     * 方法缓存
     */
    private static final SimpleCache<Class<?>, List<Method>> METHODS_CACHE = new SimpleCache<>();

    // 对象实例化 ===========================================================================================

    /**
     * 尝试遍历并调用此类的所有构造方法，直到构造成功并返回
     * <p>
     * 对于某些特殊的接口，按照其默认实现实例化，例如：
     * <pre>
     *     Map        -> HashMap
     *     Collection -> ArrayList
     *     List       -> ArrayList
     *     Set        -> HashSet
     * </pre>
     *
     * @param <T>       对象类型
     * @param beanClass 被构造的类
     * @return 构造后的对象
     */
    public static <T> T newInstanceIfPossible(Class<T> beanClass) {
        Assert.notNull(beanClass);

        // 某些特殊接口的实例化按照默认实现进行
        if (beanClass.isAssignableFrom(AbstractMap.class)) {
            beanClass = ObjectUtil.cast(HashMap.class);
        } else if (beanClass.isAssignableFrom(List.class)) {
            beanClass = ObjectUtil.cast(ArrayList.class);
        } else if (beanClass.isAssignableFrom(Set.class)) {
            beanClass = ObjectUtil.cast(HashSet.class);
        }

        try {
            return newInstance(beanClass);
        } catch (Exception e) {
            // ignore
            // 默认构造不存在的情况下查找其它构造
        }

        final Constructor<T>[] constructors = getConstructors(beanClass);
        Class<?>[] parameterTypes;
        for (Constructor<T> constructor : constructors) {
            parameterTypes = constructor.getParameterTypes();
            if (0 == parameterTypes.length) {
                continue;
            }
            setAccessible(constructor);
            try {
                return constructor.newInstance(ClassUtil.getDefaultValues(parameterTypes));
            } catch (Exception ignore) {
                // 构造出错时继续尝试下一种构造方式
            }
        }
        return null;
    }

    /**
     * 实例化对象
     *
     * @param <T>   对象类型
     * @param clazz 类名
     * @return 对象
     * @throws UtilException 包装各类异常
     */
    public static <T> T newInstance(Class<T> clazz) throws UtilException {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new UtilException(e, "Instance class [{}] error!", clazz);
        }
    }

    /**
     * 实例化对象
     *
     * @param <T>    对象类型
     * @param clazz  类
     * @param params 构造函数参数
     * @return 对象
     * @throws UtilException 包装各类异常
     */
    public static <T> T newInstance(Class<T> clazz, Object... params) throws UtilException {
        if (ArrayUtil.isEmpty(params)) {
            final Constructor<T> constructor = getConstructor(clazz);
            try {
                return constructor.newInstance();
            } catch (Exception e) {
                throw new UtilException(e, "Instance class [{}] error!", clazz);
            }
        }

        final Class<?>[] paramTypes = ClassUtil.getClasses(params);
        final Constructor<T> constructor = getConstructor(clazz, paramTypes);
        if (null == constructor) {
            throw new UtilException("No Constructor matched for parameter types: [{}]", new Object[]{paramTypes});
        }
        try {
            return constructor.newInstance(params);
        } catch (Exception e) {
            throw new UtilException(e, "Instance class [{}] error!", clazz);
        }
    }

    // 构造器 ===========================================================================================

    /**
     * 获得一个类中所有构造列表
     *
     * @param <T>       构造的对象类型
     * @param beanClass 类
     * @return 字段列表
     * @throws SecurityException 安全检查异常
     */
    public static <T> Constructor<T>[] getConstructors(Class<T> beanClass) throws SecurityException {
        Assert.notNull(beanClass);
        Constructor<?>[] constructors = CONSTRUCTORS_CACHE.get(beanClass);
        if (null != constructors) {
            return ObjectUtil.cast(constructors);
        }

        constructors = getConstructorsDirectly(beanClass);
        return ObjectUtil.cast(CONSTRUCTORS_CACHE.put(beanClass, constructors));
    }

    /**
     * 获得一个类中所有构造列表，直接反射获取，无缓存
     *
     * @param beanClass 类
     * @return 字段列表
     * @throws SecurityException 安全检查异常
     */
    public static Constructor<?>[] getConstructorsDirectly(Class<?> beanClass) throws SecurityException {
        Assert.notNull(beanClass);
        return beanClass.getDeclaredConstructors();
    }

    /**
     * 查找类中的指定参数的构造方法，如果找到构造方法，会自动设置可访问为true
     *
     * @param <T>            对象类型
     * @param clazz          类
     * @param parameterTypes 参数类型，只要任何一个参数是指定参数的父类或接口或相等即可，此参数可以不传
     * @return 构造方法，如果未找到返回null
     * @link Constructor<T> accessibleConstructor(Class<T> clazz, Class<?>... parameterTypes)
     */
    public static <T> Constructor<T> getConstructor(Class<T> clazz, Class<?>... parameterTypes) {
        if (null == clazz) {
            return null;
        }

        final Constructor<?>[] constructors = getConstructors(clazz);
        Class<?>[] pts;
        for (Constructor<?> constructor : constructors) {
            pts = constructor.getParameterTypes();
            if (ClassUtil.isAllAssignableFrom(pts, parameterTypes)) {
                // 构造可访问
                setAccessible(constructor);
                return ObjectUtil.cast(constructor);
            }
        }
        return null;
    }

    // methods ===========================================================================================

    /**
     * 获得一个类中所有方法列表，包括其父类中的方法
     *
     * @param object 类
     * @return 方法列表
     */
    public static List<Method> getMethods(Object object) {
        return getMethods(object.getClass());
    }

    /**
     * 获得一个类中所有方法列表，包括其父类中的方法
     *
     * @param beanClass 类
     * @return 方法列表
     * @throws SecurityException 安全检查异常
     */
    public static List<Method> getMethods(Class<?> beanClass) {
        List<Method> allMethods = METHODS_CACHE.get(beanClass);
        if (null != allMethods) {
            return allMethods;
        }

        allMethods = getMethodsDirectly(beanClass, true);
        return METHODS_CACHE.put(beanClass, allMethods);
    }

    /**
     * 获得一个类中所有方法列表，直接反射获取，无缓存
     *
     * @param beanClass             类
     * @param withSuperClassMethods 是否包括父类的方法列表
     * @return 方法列表
     */
    public static List<Method> getMethodsDirectly(Class<?> beanClass, boolean withSuperClassMethods) {
        Assert.notNull(beanClass);

        Method[] allMethods = null;
        Class<?> searchType = beanClass;
        Method[] declaredMethods;
        while (searchType != null) {
            declaredMethods = searchType.getDeclaredMethods();
            if (null == allMethods) {
                allMethods = declaredMethods;
            } else {
                allMethods = ArrayUtil.append(allMethods, declaredMethods);
            }
            searchType = withSuperClassMethods ? searchType.getSuperclass() : null;
        }

        if (ArrayUtil.isEmpty(allMethods)) {
            return ListUtil.emptyList();
        }
        return ListUtil.newArrayList(allMethods);
    }

    // method ===========================================================================================

    /**
     * 查找指定方法 如果找不到对应的方法则返回{@code null}
     * 此方法为精准获取方法名，即方法名和参数数量和类型必须一致，否则返回{@code null}。
     *
     * @param clazz      类，如果为{@code null}返回{@code null}
     * @param methodName 方法名，如果为空字符串返回{@code null}
     * @param paramTypes 参数类型，指定参数类型如果是方法的子类也算
     * @return 方法
     * @throws SecurityException 无权访问抛出异常
     * @link ReflectionUtils#findMethod(clazz, methodName, paramTypes);
     */
    public static Method getMethod(Class<?> clazz, String methodName, Class<?>... paramTypes) throws SecurityException {
        return getMethod(clazz, false, methodName, paramTypes);
    }

    /**
     * 查找指定方法 如果找不到对应的方法则返回{@code null}
     *
     * <p>
     * 此方法为精准获取方法名，即方法名和参数数量和类型必须一致，否则返回{@code null}。
     * </p>
     *
     * @param clazz      类，如果为{@code null}返回{@code null}
     * @param ignoreCase 是否忽略大小写
     * @param methodName 方法名，如果为空字符串返回{@code null}
     * @param paramTypes 参数类型，指定参数类型如果是方法的子类也算
     * @return 方法
     * @throws SecurityException 无权访问抛出异常
     * @since 3.2.0
     */
    public static Method getMethod(Class<?> clazz, boolean ignoreCase, String methodName, Class<?>... paramTypes) throws SecurityException {
        if (null == clazz || StringUtil.isEmpty(methodName)) {
            return null;
        }

        final List<Method> methods = getMethods(clazz);
        if (ListUtil.hasItem(methods)) {
            for (Method method : methods) {
                if (StringUtil.equals(methodName, method.getName(), ignoreCase)) {
                    if (ClassUtil.isAllAssignableFrom(method.getParameterTypes(), paramTypes)) {
                        return method;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 循环向上转型, 获取对象的 DeclaredMethod
     * PS：该方法和getMethod相同，getMethod实现里面获取methods也默认也包含了父类method
     *
     * @param clazz          类
     * @param methodName     方法名
     * @param parameterTypes 方法参数类型
     * @return 方法对象
     */
    public static Method getDeclaredMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        Method method = null;
        // 以下是先从自身的方法开始查找，如果没有就往父级一层层找
        for (Class<?> type = clazz; type != Object.class; type = type.getSuperclass()) {
            try {
                method = type.getDeclaredMethod(methodName, parameterTypes);
                break;
            } catch (NoSuchMethodException ignored) {
            }
        }
        return method;
    }

    /**
     * 获得类中所有方法，包括继承而来的
     *
     * @param clazz 类
     * @return 方法数组
     */
    public static Method[] getAllDeclaredMethods(Class<?> clazz) {
        return ReflectionUtils.getAllDeclaredMethods(clazz);
    }

    // fields ===========================================================================================

    /**
     * 获得一个类中所有字段列表，包括其父类中的字段
     *
     * @param object 类
     * @return 字段列表
     */
    public static List<Field> getFields(Object object) {
        return getFields(object.getClass());
    }

    /**
     * 获得一个类中所有字段列表，包括其父类中的子弹
     *
     * @param beanClass 类
     * @return 字段列表
     * @throws SecurityException 安全检查异常
     */
    public static List<Field> getFields(Class<?> beanClass) {
        List<Field> allFields = FIELDS_CACHE.get(beanClass);
        if (null != allFields) {
            return allFields;
        }

        allFields = getDeclaredFields(beanClass);
        return FIELDS_CACHE.put(beanClass, allFields);
    }

    /**
     * 获取类自身和父类所有的成员属性
     *
     * @param object 类
     * @return list
     */
    public static List<Field> getDeclaredFields(Object object) {
        return getDeclaredFields(object.getClass());
    }

    /**
     * 获取clazz自身和父类所有的成员属性
     *
     * @param clazz 类类型
     * @return list
     */
    public static List<Field> getDeclaredFields(Class<?> clazz) {
        List<Field> list = ListUtil.newArrayList();
        for (Class<?> type = clazz; type != Object.class; type = type.getSuperclass()) {
            if (!type.getSimpleName().contains("$$EnhancerByCGLIB$$")) {
                Field[] fields = type.getDeclaredFields();
                Collections.addAll(list, fields);
            }
        }
        return list;
    }

    /**
     * 获取对象自身和父类所有的成员属性名
     *
     * @param object 类
     * @return list
     */
    public static List<String> getDeclaredFieldNames(Object object) {
        return getDeclaredFieldNames(object.getClass());
    }

    /**
     * 获取clazz自身和父类所有的成员属性名
     *
     * @param clazz 类类型
     * @return list
     */
    public static List<String> getDeclaredFieldNames(Class<?> clazz) {
        List<String> list = ListUtil.newArrayList();
        String name;
        for (Class<?> type = clazz; type != Object.class; type = type.getSuperclass()) {
            if (!type.getSimpleName().contains("$$EnhancerByCGLIB$$")) {
                Field[] fields = type.getDeclaredFields();
                for (Field field : fields) {
                    name = field.getName();
                    if (!list.contains(name)) {
                        list.add(name);
                    }
                }
            }
        }
        return list;
    }

    // field ===========================================================================================

    /**
     * 循环向上转型, 获取对象的 DeclaredField
     *
     * @param clazz     类
     * @param fieldName 字段名
     * @return 属性对象
     * @link Field findField(Class<?> clazz, String name)
     */
    public static Field getDeclaredField(Class<?> clazz, String fieldName) {
        Field field = null;
        for (Class<?> type = clazz; type != Object.class; type = type.getSuperclass()) {
            try {
                field = type.getDeclaredField(fieldName);
                break;
            } catch (NoSuchFieldException ignored) {
            }
        }
        return field;
    }

    /**
     * 在类中查找指定属性
     *
     * @param clazz 类
     * @param name  字段名
     * @param type  字段类型(类型错误返回null)
     * @return field
     */
    public static Field getDeclaredField(Class<?> clazz, String name, Class<?> type) {
        return ReflectionUtils.findField(clazz, name, type);
    }

    // setFieldValue ===========================================================================================

    /**
     * 直接设置对象属性值, 忽略 private/protected 修饰符, 也不经过 setter
     *
     * @param object 类对象
     * @param field  属性Field
     * @param value  将要设置的值
     * @link ReflectionUtils.setField(Field field, Object target, Object value)
     */
    public static void setFieldValue(Object object, Field field, Object value) {
        setFieldValue(object, field.getName(), value);
    }

    /**
     * 直接设置对象属性值, 忽略 private/protected 修饰符, 也不经过 setter
     *
     * @param object    类对象
     * @param fieldName 属性名
     * @param value     将要设置的值
     */
    public static void setFieldValue(Object object, String fieldName, Object value) {
        List<String> declaredFieldNames = getDeclaredFieldNames(object);
        if (!declaredFieldNames.contains(fieldName)) {
            return;
        }
        // 根据 对象和属性名通过反射 调用上面的方法获取 Field对象
        Field field = getDeclaredField(object.getClass(), fieldName);
        if (field == null) {
            return;
        }
        // 抑制Java对其的检查
        field.setAccessible(true);

        try {
            // 将 object 中 field 所代表的值 设置为 value
            field.set(object, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // getFieldValue ===========================================================================================

    /**
     * 获取属性值
     *
     * @param object 类对象
     * @param field  field属性
     * @return 属性值
     * @link ReflectionUtils.getField(Field field, Object target)
     */
    public static Object getFieldValue(Object object, Field field) {
        return getFieldValue(object, field.getName());
    }

    /**
     * 直接读取对象的属性值, 忽略 private/protected 修饰符, 也不经过 getter
     *
     * @param object    子类对象
     * @param fieldName 父类中的属性名
     * @return : 父类中的属性值
     */
    public static Object getFieldValue(Object object, String fieldName) {
        List<String> declaredFieldNames = getDeclaredFieldNames(object);
        if (!declaredFieldNames.contains(fieldName)) {
            return null;
        }
        // 根据 对象和属性名通过反射 调用上面的方法获取 Field对象
        Field field = getDeclaredField(object.getClass(), fieldName);
        if (field == null) {
            return null;
        }
        // 抑制Java对其的检查
        field.setAccessible(true);

        Object result;

        try {
            // 获取 object 中 field 所代表的属性值
            result = field.get(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 获取字段的class
     * <p>
     * 如果field是List or Set，则返回集合的范型
     *
     * @param field 字段
     */
    public static Class<?> getFieldClass(Field field) {
        return getFieldClass(field, true);
    }

    /**
     * 获取字段的class，如果字段是list，则获取list的泛型
     *
     * @param field                    字段
     * @param getCollectionGenericType 获取集合范型
     */
    public static Class<?> getFieldClass(Field field, boolean getCollectionGenericType) {
        Class<?> clazz = field.getType();

        if (!getCollectionGenericType) {
            return clazz;
        }

        // 判断是否是集合 如果是集合 取集合的泛型
        boolean isCollection = field.getType() == List.class || field.getType() == Set.class;
        if (!isCollection) {
            return clazz;
        }
        // getGenericType 获取字段List的范型
        Type genericType = field.getGenericType();
        // 如果范型是参数化类型
        if (genericType instanceof ParameterizedType pt) {
            // 获取集合的泛型
            Type[] actualTypeArguments = pt.getActualTypeArguments();

            // 没有泛型抛出异常
            if (ArrayUtil.isEmpty(actualTypeArguments)) {
                throw new RuntimeException(field.getName() + "字段作为List没有加泛型");
            }
            if (actualTypeArguments.length == 1 && "?".equals(actualTypeArguments[0].getTypeName())) {
                throw new RuntimeException(field.getName() + "字段作为List没有加明确的泛型");
            }
            clazz = (Class<?>) actualTypeArguments[0];
            return clazz;
        } else {
            throw new RuntimeException(field.getName() + "字段作为List没有加泛型");
        }
    }

    /**
     * 直接读取对象的属性值, 忽略 private/protected 修饰符, 也不经过 getter【递归】
     *
     * @param object    子类对象
     * @param fieldName 父类中的属性名
     * @return : 父类中的属性值
     */
    public static Object getFieldValueByRecursive(Object object, String fieldName) {
        if (fieldName.contains(KuugaConstants.POINT)) {
            String[] fieldSplit = fieldName.split("\\.");
            FifoLinkedQueue<String> fifoQueue = new FifoLinkedQueue<>();
            fifoQueue.putList(Arrays.asList(fieldSplit.clone()));
            Object recursiveObject = object;
            while (fifoQueue.hasItem()) {
                String fieldItemName = fifoQueue.get();
                recursiveObject = getFieldValue(recursiveObject, fieldItemName);
            }
            return recursiveObject;
        } else {
            return getFieldValue(object, fieldName);
        }
    }

    /**
     * 获取对象中的字段值
     *
     * @param object    对象
     * @param fieldPath 字段路径，eg：house#id
     * @return 字段值
     * @throws NoSuchFieldException 字段找不到
     */
    public static Object getFieldValueByPath(Object object, String fieldPath) throws NoSuchFieldException {
        if (StringUtil.isEmpty(fieldPath)) {
            return null;
        }
        if (object instanceof Class<?>) {
            throw new IllegalArgumentException("object is not an instance of declaring class");
        }
        if (fieldPath.contains(KuugaConstants.WELL)) {
            List<String> fieldPathList = ArrayUtil.asList(fieldPath.split(KuugaConstants.WELL));
            fieldPathList = ListUtil.nullFilter(fieldPathList);

            FifoLinkedQueue<String> fifoQueue = new FifoLinkedQueue<>();
            fifoQueue.putList(fieldPathList);

            Object fieldValueOfResult = object;
            while (fifoQueue.hasItem()) {
                String fieldName = fifoQueue.get();
                if (StringUtil.isEmpty(fieldName)) {
                    return fieldValueOfResult;
                }
                fieldValueOfResult = getFieldValueIfExist(fieldValueOfResult, fieldName);
            }
            return fieldValueOfResult;
        } else {
            if (existsField(object, fieldPath)) {
                return getFieldValue(object, fieldPath);
            } else {
                throw new NoSuchFieldException(String.format("[%s]字段不存在", fieldPath));
            }
        }
    }

    /**
     * 从对象中获取字段值，如果存在则返回，不存在则抛异常 {@link  NoSuchFieldException}
     *
     * @param object    对象
     * @param fieldName 字段名
     * @return 字段值
     * @throws NoSuchFieldException 字段找不到
     */
    public static Object getFieldValueIfExist(Object object, String fieldName) throws NoSuchFieldException {
        if (!existsField(object, fieldName)) {
            throw new NoSuchFieldException(String.format("[%s.%s]字段不存在", ((Class<?>) object).getName(), fieldName));
        } else {
            return getFieldValue(object, fieldName);
        }
    }

    /**
     * 查找字段对应的可读方法取值，没有返回null
     *
     * @param object    类对象
     * @param fieldName 字段名
     * @return getMethod.value
     * @since 3.2.0
     */
    public static Object getValueByGetMethod(Object object, String fieldName) {
        Class<?> objectClass = object.getClass();

        try {
            Field declaredField = getDeclaredField(objectClass, fieldName);
            if (ObjectUtil.isNull(declaredField)) {
                logger.error("ReflectionUtil.getValueByGetMethod error:对象属性不存在-{}", fieldName);
            }
            Class<?> type = declaredField.getType();

            String getMethodName;
            if (type == boolean.class || type == Boolean.class) {
                if (fieldName.startsWith(IS)) {
                    fieldName = StringUtil.removeStart(fieldName, IS);
                }
                getMethodName = IS + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            } else {
                getMethodName = GET + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            }
            Method method = objectClass.getMethod(getMethodName);
            return method.invoke(object);
        } catch (Exception e) {
            logger.error("ReflectionUtil.getValueByGetMethod error:{}", e.getMessage(), e);
            return null;
        }
    }

    // setValueBySetMethod ===========================================================================================

    /**
     * 查找字段对应的set方法取值，没有返回null
     *
     * @param object    类对象
     * @param fieldName 字段名
     * @param value     字段值
     */
    public static void setValueBySetMethod(Object object, String fieldName, Object value) {
        try {
            Class<?> objectClass = object.getClass();
            Class<?>[] parameterTypes = new Class[1];
            Field field = objectClass.getDeclaredField(fieldName);
            parameterTypes[0] = field.getType();
            if (fieldName.startsWith(IS)) {
                fieldName = StringUtil.removeStart(fieldName, IS);
            }
            String setMethodName = SET + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            Method method = objectClass.getMethod(setMethodName, parameterTypes);
            method.invoke(object, value);
        } catch (Exception e) {
            logger.error("ReflectionUtil.setValueBySetMethod error:{}", e.getMessage(), e);
        }

    }

    // invoke ===========================================================================================

    /**
     * 执行方法
     *
     * <p>
     * 对于用户传入参数会做必要检查，包括：
     *
     * <pre>
     *     1、忽略多余的参数
     *     2、参数不够补齐默认值
     *     3、传入参数为null，但是目标参数类型为原始类型，做转换
     * </pre>
     *
     * @param <T>    返回对象类型
     * @param obj    对象，如果执行静态方法，此值为{@code null}
     * @param method 方法（对象方法或static方法都可）
     * @param args   参数对象
     * @return 结果
     * @throws UtilException 一些列异常的包装
     */
    public static <T> T invoke(Object obj, Method method, Object... args) throws UtilException {
        setAccessible(method);

        // 检查用户传入参数：
        // 1、忽略多余的参数
        // 2、参数不够补齐默认值
        // 3、通过NullWrapperBean传递的参数,会直接赋值null
        // 4、传入参数为null，但是目标参数类型为原始类型，做转换
        // 5、传入参数类型不对应，尝试转换类型
        final Class<?>[] parameterTypes = method.getParameterTypes();
        final Object[] actualArgs = new Object[parameterTypes.length];
        if (null != args) {
            for (int i = 0; i < actualArgs.length; i++) {
                if (i >= args.length || null == args[i]) {
                    // 越界或者空值
                    actualArgs[i] = ClassUtil.getDefaultValue(parameterTypes[i]);
                } else if (args[i] instanceof NullWrapperBean) {
                    // 如果是通过NullWrapperBean传递的null参数,直接赋值null
                    actualArgs[i] = null;
                } else if (!parameterTypes[i].isAssignableFrom(args[i].getClass())) {
                    // 对于类型不同的字段，尝试转换，转换失败则使用原对象类型
                    final Object targetValue = ObjectUtil.cast(args[i]);
                    if (null != targetValue) {
                        actualArgs[i] = targetValue;
                    }
                } else {
                    actualArgs[i] = args[i];
                }
            }
        }

        try {
            Object invoke = method.invoke(ClassUtil.isStatic(method) ? null : obj, actualArgs);
            return ObjectUtil.cast(invoke);
        } catch (Exception e) {
            throw new UtilException(e);
        }
    }

    /**
     * 直接调用对象方法, 而忽略修饰符(private, protected, default)
     *
     * @param object     对象
     * @param methodName 方法名
     * @return 方法的执行结果
     */
    public static Object invokeMethod(Object object, String methodName) {
        // 根据 对象、方法名和对应的方法参数 通过反射 调用上面的方法获取 Method 对象
        Method method = getDeclaredMethod(object.getClass(), methodName);
        if (method == null) {
            return null;
        }
        // 抑制Java对方法进行检查,主要是针对私有方法而言
        method.setAccessible(true);

        Object result;

        try {
            result = method.invoke(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    /**
     * 直接调用对象方法, 而忽略修饰符(private, protected, default)
     *
     * @param object         对象
     * @param methodName     方法名
     * @param parameterTypes 父类中的方法参数类型
     * @param parameters     父类中的方法参数
     * @return 方法的执行结果
     */
    public static Object invokeMethod(Object object, String methodName, Class<?>[] parameterTypes, Object[] parameters) {
        // 根据 对象、方法名和对应的方法参数 通过反射 调用上面的方法获取 Method 对象
        Method method = getDeclaredMethod(object.getClass(), methodName, parameterTypes);
        if (method == null) {
            return null;
        }
        // 抑制Java对方法进行检查,主要是针对私有方法而言
        method.setAccessible(true);

        Object result;

        try {
            result = method.invoke(object, parameters);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    // exists ================================================================================================================

    /**
     * 校验clazz对象类类型是否包含fieldName属性
     *
     * @param object    类
     * @param fieldName 属性名
     * @return boolean
     */
    public static boolean existsField(Object object, String fieldName) {
        List<String> declaredFieldNames = getDeclaredFieldNames(object.getClass());
        return declaredFieldNames.contains(fieldName);
    }

    /**
     * 校验clazz对象类类型是否包含fieldName属性
     *
     * @param clazz     类类型
     * @param fieldName 属性名
     * @return boolean
     */
    public static boolean existsField(Class<?> clazz, String fieldName) {
        List<String> declaredFieldNames = getDeclaredFieldNames(clazz);
        return declaredFieldNames.contains(fieldName);
    }

    /**
     * 校验clazz对象类类型是否包含methodName方法
     *
     * @param clazz          类类型
     * @param methodName     方法名
     * @param parameterTypes 参数列表(getter、setter时为空)
     * @return boolean
     */
    public static boolean existsMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        return getDeclaredMethod(clazz, methodName, parameterTypes) != null;
    }

    // accessible ================================================================================================================

    /**
     * 取消 Java 权限检查。以便后续执行私有构造方法
     *
     * @param ctor 构造器
     */
    public static void setAccessible(Constructor<?> ctor) {
        ReflectionUtils.makeAccessible(ctor);
    }

    /**
     * 取消 Java 权限检查。以便后续执行该私有方法
     *
     * @param method 方法
     */
    public static void setAccessible(Method method) {
        ReflectionUtils.makeAccessible(method);
    }

    /**
     * 取消 Java 权限检查。以便后续执行私有构造字段
     *
     * @param field 字段
     */
    public static void setAccessible(Field field) {
        ReflectionUtils.makeAccessible(field);
    }

    // copy ================================================================================================================

    /**
     * 对象属性COPY
     *
     * @param source source object
     * @param target target object
     * @param <T>    T
     */
    public static <T> void copyProperties(T source, T target) {
        if (null == source || null == target) {
            return;
        }
        Field[] declaredFields = source.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            String fieldName = declaredField.getName();
            Object value = getFieldValue(source, fieldName);
            if (existsField(target.getClass(), fieldName) && !isStaticFinal(declaredField)) {
                setFieldValue(target, fieldName, value);
            }
        }
    }

    // initializeAllObject ================================================================================================================

    /**
     * 循环创建对象
     * 创建嵌层对象实体，防止内层对象属性调用报空指针异常
     *
     * @param object object
     */
    public static void initializeAllObject(Object object) {
        // 获取对象所有字段
        Field[] declaredFields = object.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            // 获取字段名
            String fieldName = declaredField.getName();
            // 获取字段类型
            Class<?> fieldType = declaredField.getType();
            try {
                // 获取字段值
                Object property = getFieldValue(object, fieldName);

                // 字段是基础数据类型
                if (ClassUtil.isBasicDataType(fieldType)) {
                    if (property == null) {
                        // 实例化字段
                        setFieldValue(object, fieldName, fieldType.getDeclaredConstructor().newInstance());
                    }
                } else {
                    // 字段不是基础数据类型
                    if (property == null) {
                        // 实例化字段
                        property = fieldType.getDeclaredConstructor().newInstance();
                    }
                    // 对象类型，继续循环调用
                    initializeAllObject(property);
                    // 内层循环实例化后赋值
                    setFieldValue(object, fieldName, property);
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("initializeAllObject error:{}", e.toString());
            }
        }
    }

    // write read =============================================================================================================

    /*
        org.apache.commons.beanutils.PropertyUtils
        getProperty：获取对象属性值
        setProperty：设置对象属性值
        getPropertyDiscriptor：获取属性描述器
        isReadable：检查属性是否可访问
        copyProperties：复制属性值，从一个对象到另一个对象
        getPropertyDiscriptors：获取所有属性描述器
        isWriteable：检查属性是否可写
        getPropertyType：获取对象属性类型
     */

    /**
     * 判断对象内字段是否可写
     *
     * @param bean     对象
     * @param fileName 字段名(有setter方法为true)
     * @return 是否可写
     */
    public static boolean canWriteable(Object bean, String fileName) {
        return PropertyUtils.isWriteable(bean, fileName);
    }

    /**
     * 判断对象内字段是否可读
     *
     * @param bean     对象
     * @param fileName 字段名(有getter方法为true)
     * @return 是否可读
     */
    public static boolean canReadable(Object bean, String fileName) {
        return PropertyUtils.isReadable(bean, fileName);
    }

    // 扩展method校验 ========================================================================================================================

    /**
     * 是否是 equals() 方法
     *
     * @param method 方法
     * @return boolean
     */
    public static boolean isEqualsMethod(Method method) {
        return ReflectionUtils.isEqualsMethod(method);
    }

    /**
     * 是否是 hashCode() 方法
     *
     * @param method 方法
     * @return boolean
     */
    public static boolean isHashCodeMethod(Method method) {
        return ReflectionUtils.isHashCodeMethod(method);
    }

    /**
     * 是否是 toString() 方法
     *
     * @param method 方法
     * @return boolean
     */
    public static boolean isToStringMethod(Method method) {
        return ReflectionUtils.isToStringMethod(method);
    }

    /**
     * 是否是从 Object 类继承而来的方法
     *
     * @param method 方法
     * @return boolean
     */
    public static boolean isObjectMethod(Method method) {
        return ReflectionUtils.isObjectMethod(method);
    }

    /**
     * 检查一个方法是否声明抛出指定异常
     *
     * @param method        方法
     * @param exceptionType 异常类型
     * @return boolean
     */
    public static boolean declaresException(Method method, Class<?> exceptionType) {
        return ReflectionUtils.declaresException(method, exceptionType);
    }

    // public static final ==============================================================================================================================

    /**
     * 是否为一个 "public static final" 属性
     * <p>
     * PS:必须三个都有，static和final前后顺序无影响，一个顶三个
     *
     * @param field 字段
     * @return boolean
     * @see ClassUtil#isPublic(Field)
     * @see ClassUtil#isStatic(Field)
     * @see ClassUtil#isFinal(Field)
     */
    public static boolean isPublicStaticFinal(Field field) {
        return ReflectionUtils.isPublicStaticFinal(field);
    }

    /**
     * 是否为一个 "static final" 属性
     * <p>
     * PS:必须两个都有，static和final前后顺序无影响，一个顶两个
     *
     * @param field 字段
     * @return boolean
     * @see ClassUtil#isStatic(Field)
     * @see ClassUtil#isFinal(Field)
     */
    public static boolean isStaticFinal(Field field) {
        int modifiers = field.getModifiers();
        return Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers);
    }

    /**
     * 是否为一个 "public" 属性
     *
     * @param field 字段
     * @return boolean
     * @see ClassUtil#isPublic(Field)
     */
    public static boolean isPublic(Field field) {
        int modifiers = field.getModifiers();
        return Modifier.isPublic(modifiers);
    }

    /**
     * 是否为一个 "static" 属性
     *
     * @param field 字段
     * @return boolean
     * @see ClassUtil#isStatic(Field)
     */
    public static boolean isStatic(Field field) {
        int modifiers = field.getModifiers();
        return Modifier.isStatic(modifiers);
    }

    /**
     * 是否为一个 "final" 属性
     *
     * @param field 字段
     * @return boolean
     * @see ClassUtil#isFinal(Field)
     */
    public static boolean isFinal(Field field) {
        int modifiers = field.getModifiers();
        return Modifier.isFinal(modifiers);
    }

    // 浅拷贝字段状态 ==============================================================================================================================

    /**
     * 同类对象属性对等赋值
     * 支持父子类拷贝 相当于对象拷贝
     *
     * @param src  源对象
     * @param dest 目标对象
     */
    public static void shallowCopyFieldState(Object src, Object dest) {
        ReflectionUtils.shallowCopyFieldState(src, dest);
    }

    // 类字段函数处理 ==============================================================================================================================

    /**
     * 对类的每个属性执行 callback
     *
     * @param clazz 类
     * @param fc    回调函数
     */
    public static void doWithFields(Class<?> clazz, ReflectionUtils.FieldCallback fc) {
        ReflectionUtils.doWithFields(clazz, fc);
    }

    /**
     * 对类的每个属性执行 callback 多了个属性过滤功能
     *
     * @param clazz 类
     * @param fc    回调函数
     * @param ff    属性过滤 过滤出要给回调函数处理的属性
     */
    public static void doWithFields(Class<?> clazz, ReflectionUtils.FieldCallback fc, ReflectionUtils.FieldFilter ff) {
        ReflectionUtils.doWithFields(clazz, fc, ff);
    }

    /**
     * 对类的每个属性执行 callback 但不包括继承而来的属性
     * <p>
     * 注意:不包括继承而来的属性
     *
     * @param clazz 类
     * @param fc    回调函数
     */
    public static void doWithLocalFields(Class<?> clazz, ReflectionUtils.FieldCallback fc) {
        ReflectionUtils.doWithLocalFields(clazz, fc);
    }

}
