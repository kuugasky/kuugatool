package io.github.kuugasky.kuugatool.core.object;

import com.google.common.base.Joiner;
import io.github.kuugasky.kuugatool.core.collection.*;
import io.github.kuugasky.kuugatool.core.constants.KuugaConstants;
import io.github.kuugasky.kuugatool.core.function.ObjectToMapFunc;
import io.github.kuugasky.kuugatool.core.optional.KuugaOptional;
import io.github.kuugasky.kuugatool.core.string.JoinerUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

/**
 * object工具类
 *
 * @author kuuga
 */
public final class ObjectUtil {

    private static final String GET = "get";

    /**
     * 定义私有构造函数来屏蔽这个隐式公有构造函数
     */
    private ObjectUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(ObjectUtil.class);

    /**
     * 两个对象比较是否相同
     *
     * @param a aObject
     * @param b bObject
     * @return boolean
     */
    public static boolean equals(Object a, Object b) {
        return Objects.equals(a, b);
    }

    /**
     * 两个对象比较是否不相同
     *
     * @param a aObject
     * @param b bObject
     * @return boolean
     */
    public static boolean equalsNo(Object a, Object b) {
        return !Objects.equals(a, b);
    }

    /**
     * 两个对象深度比较是否相同
     *
     * @param a aObject
     * @param b bObject
     * @return boolean
     */
    public static boolean deepEquals(Object a, Object b) {
        return Objects.deepEquals(a, b);
    }

    /**
     * 两个对象深度比较是否不相同
     *
     * @param a aObject
     * @param b bObject
     * @return boolean
     */
    public static boolean deepEqualsNo(Object a, Object b) {
        return !Objects.deepEquals(a, b);
    }

    /**
     * 输出对象string
     *
     * @param object object
     * @return string
     */
    public static String toString(Object object) {
        return Objects.toString(object);
    }

    /**
     * 输出对象string
     *
     * @param object         object
     * @param nullDefaultStr 如果对象为空，则输出nullDefaultStr
     * @return string
     */
    public static String toString(Object object, String nullDefaultStr) {
        return Objects.toString(object, nullDefaultStr);
    }

    // require object ============================================================================================================

    /**
     * 必填检测
     */
    public static <T> T requireNonNull(T obj) {
        if (obj == null) {
            throw new NullPointerException();
        }
        return obj;
    }

    /**
     * 必填检测，为null则抛出自定义运行时异常
     */
    public static <T> T requireNonNull(T obj, RuntimeException e) {
        if (obj == null) {
            throw e;
        }
        return obj;
    }

    /**
     * 必填检测，为null则抛出自定义错误信息的空指针异常
     */
    public static <T> T requireNonNull(T obj, String message) {
        if (obj == null) {
            throw new NullPointerException(message);
        }
        return obj;
    }

    /**
     * 必填检测，为null则默认值，默认值为null则抛出`defaultObj`字符串
     */
    public static <T> T requireNonNullElse(T obj, T defaultObj) {
        return (obj != null) ? obj : requireNonNull(defaultObj, "defaultObj");
    }

    /**
     * 必填检测，为null则执行supplier.get()
     */
    public static <T> T requireNonNullElseGet(T obj, Supplier<? extends T> supplier) {
        return (obj != null) ? obj : requireNonNull(requireNonNull(supplier, "supplier").get(), "supplier.get()");
    }

    /**
     * 必填检测，为null则执行supplier.get()给空指针异常赋值错误信息
     */
    public static <T> T requireNonNull(T obj, Supplier<String> messageSupplier) {
        if (obj == null) {
            throw new NullPointerException(messageSupplier == null ? null : messageSupplier.get());
        }
        return obj;
    }

    // require string ============================================================================================================

    /**
     * 必填检测
     */
    public static String requireHasText(String obj) {
        if (StringUtil.isEmpty(obj)) {
            throw new NullPointerException();
        }
        return obj;
    }

    /**
     * 必填检测，为null则抛出自定义运行时异常
     */
    public static String requireHasText(String obj, RuntimeException e) {
        if (StringUtil.isEmpty(obj)) {
            throw e;
        }
        return obj;
    }

    /**
     * 必填检测，为null则抛出自定义错误信息的空指针异常
     */
    public static String requireHasText(String obj, String message) {
        if (StringUtil.isEmpty(obj)) {
            throw new NullPointerException(message);
        }
        return obj;
    }

    /**
     * 必填检测，为null则默认值，默认值为null则抛出`defaultObj`字符串
     */
    public static String requireHasTextElse(String obj, String defaultObj) {
        return StringUtil.hasText(obj) ? obj : requireHasText(defaultObj, "defaultObj");
    }

    /**
     * 必填检测，为null则执行supplier.get()
     */
    public static String requireHasTextElseGet(String obj, Supplier<? extends String> supplier) {
        return StringUtil.hasText(obj) ? obj : requireHasText(requireNonNull(supplier, "supplier").get(), "supplier.get()");
    }

    /**
     * 必填检测，为null则执行supplier.get()给空指针异常赋值错误信息
     */
    public static String requireHasText(String obj, Supplier<String> messageSupplier) {
        if (StringUtil.isEmpty(obj)) {
            throw new NullPointerException(messageSupplier == null ? null : messageSupplier.get());
        }
        return obj;
    }

    // require collection ============================================================================================================

    /**
     * 必填检测
     */
    public static <E> Collection<E> requireHasItem(Collection<E> obj) {
        if (CollectionUtil.isEmpty(obj)) {
            throw new NullPointerException();
        }
        return obj;
    }

    /**
     * 必填检测
     */
    public static <E> Collection<E> requireHasItem(Collection<E> obj, boolean filterNull) {
        if (CollectionUtil.isEmpty(obj)) {
            throw new NullPointerException();
        }
        if (filterNull) {
            obj.removeIf(Objects::isNull);
        }
        if (CollectionUtil.isEmpty(obj)) {
            throw new NullPointerException();
        }
        return obj;
    }

    /**
     * 必填检测，为null则抛出自定义运行时异常
     */
    public static <E> Collection<E> requireHasItem(Collection<E> obj, RuntimeException e) {
        if (CollectionUtil.isEmpty(obj)) {
            throw e;
        }
        return obj;
    }

    /**
     * 必填检测，为null则抛出自定义运行时异常
     */
    public static <E> Collection<E> requireHasItem(Collection<E> obj, boolean filterNull, RuntimeException e) {
        if (CollectionUtil.isEmpty(obj)) {
            throw e;
        }
        if (filterNull) {
            obj.removeIf(Objects::isNull);
        }
        if (CollectionUtil.isEmpty(obj)) {
            throw e;
        }
        return obj;
    }

    /**
     * 必填检测，为null则抛出自定义错误信息的空指针异常
     */
    public static <E> Collection<E> requireHasItem(Collection<E> obj, String message) {
        if (CollectionUtil.isEmpty(obj)) {
            throw new NullPointerException(message);
        }
        return obj;
    }

    /**
     * 必填检测，为null则抛出自定义错误信息的空指针异常
     */
    public static <E> Collection<E> requireHasItem(Collection<E> obj, boolean filterNull, String message) {
        if (CollectionUtil.isEmpty(obj)) {
            throw new NullPointerException(message);
        }
        if (filterNull) {
            obj.removeIf(Objects::isNull);
        }
        if (CollectionUtil.isEmpty(obj)) {
            throw new NullPointerException(message);
        }
        return obj;
    }

    /**
     * 必填检测，为null则默认值，默认值为null则抛出`defaultObj`字符串
     */
    public static <E> Collection<E> requireHasItemElse(Collection<E> obj, Collection<E> defaultObj) {
        return CollectionUtil.hasItem(obj) ? obj : requireHasItem(defaultObj);
    }

    /**
     * 必填检测，为null则执行supplier.get()
     */
    public static <E> Collection<E> requireHasItemElseGet(Collection<E> obj, Supplier<? extends Collection<E>> supplier) {
        return CollectionUtil.hasItem(obj) ? obj : supplier.get();
    }

    /**
     * 必填检测，为null则执行supplier.get()给空指针异常赋值错误信息
     */
    public static <E> Collection<E> requireHasItem(Collection<E> obj, Supplier<String> messageSupplier) {
        if (CollectionUtil.isEmpty(obj)) {
            throw new NullPointerException(messageSupplier == null ? null : messageSupplier.get());
        }
        return obj;
    }

    // require end ============================================================================================================

    public static <T> void checkElementIndex(Collection<T> collection, int size) {
        if (collection.size() != size) {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * 如果参数相同则返回0，否则返回{@code c.compare(a, b)}
     * 因此，如果两个参数都是{@code null}则返回0。
     */
    public static <T> int compare(T a, T b, Comparator<? super T> c) {
        return (a == b) ? 0 : c.compare(a, b);
    }

    public static boolean nonNull(Object object) {
        return Objects.nonNull(object);
    }

    public static boolean nonNull(@Nullable Object... objects) {
        return !containsNull(objects);
    }

    public static boolean isNull(@Nullable Object object) {
        return Objects.isNull(object);
    }

    public static boolean isNull(@Nullable Object... objects) {
        if (ArrayUtil.isEmpty(objects)) {
            return true;
        }
        return Arrays.stream(objects).allMatch(ObjectUtil::isNull);
    }

    public static boolean containsNull(@Nullable Object... objects) {
        long count = ListUtil.optimize(objects).stream().filter(Objects::isNull).count();
        return count > 0;
    }

    public static boolean containsNotNull(@Nullable Object... objects) {
        long count = ListUtil.optimize(objects).stream().filter(Objects::nonNull).count();
        return count > 0;
    }

    public static <T> T defaultIfNull(final T object, final T defaultObject) {
        return isNull(object) ? defaultObject : object;
    }

    /**
     * java对象转换成Map对象
     *
     * @param javaBean javaBean
     * @return map
     */
    public static Map<String, Object> toMap(Object javaBean) {
        return toMap(javaBean, Object.class, false);
    }

    /**
     * java对象转换成Map对象，然后执行自定义函数方法
     *
     * @param javaBean javaBean
     * @param function 自定义函数方法
     * @return map
     */
    public static Map<String, Object> toMap(Object javaBean, ObjectToMapFunc function) {
        Map<String, Object> objectMap = toMap(javaBean, Object.class, false);
        function.process(objectMap);
        return objectMap;
    }

    /**
     * java对象转换成Map对象
     *
     * @param javaBean javaBean
     * @return map
     */
    public static Map<String, Object> toMap(Object javaBean, boolean includeParent) {
        return toMap(javaBean, Object.class, includeParent);
    }

    /**
     * java对象转换成Map对象，然后执行自定义函数方法
     *
     * @param javaBean      javaBean
     * @param includeParent 是否包含父类
     * @param function      自定义函数方法
     * @return map
     */
    public static Map<String, Object> toMap(Object javaBean, boolean includeParent, ObjectToMapFunc function) {
        Map<String, Object> objectMap = toMap(javaBean, Object.class, includeParent);
        function.process(objectMap);
        return objectMap;
    }

    /**
     * java对象转换成Map对象
     *
     * @param javaBean      javaBean
     * @param clazz         对象类型
     * @param includeParent 是否包含父类
     * @return map
     */
    public static <T> Map<String, Object> toMap(Object javaBean, Class<T> clazz, boolean includeParent) {
        return toMap(javaBean, clazz, includeParent, null);
    }

    /**
     * java对象转换成Map对象，然后执行自定义函数方法
     *
     * @param javaBean      javaBean
     * @param clazz         对象类型
     * @param includeParent 是否包含父类
     * @param function      自定义函数方法
     * @return map
     */
    public static <T> Map<String, Object> toMap(Object javaBean, Class<T> clazz, boolean includeParent, ObjectToMapFunc function) {
        if (isNull(javaBean)) {
            return MapUtil.emptyMap();
        }
        Map<String, Object> objectMap = MapUtil.newHashMap();

        Method[] methods;
        if (includeParent) {
            methods = javaBean.getClass().getMethods();
        } else {
            methods = javaBean.getClass().getDeclaredMethods();
        }

        ListUtil.optimize(Arrays.asList(methods)).stream()
                .filter(method -> method.getName().startsWith(GET))
                .filter(method -> method.getDeclaringClass() != Object.class)
                .forEach(method -> {
                    try {
                        String field = method.getName();
                        field = field.substring(field.indexOf(GET) + 3);

                        field = field.toLowerCase().charAt(0) + field.substring(1);
                        Object value = method.invoke(javaBean, (Object[]) null);
                        if (clazz == String.class) {
                            objectMap.put(field, (null == value ? StringUtil.EMPTY : value.toString()));
                        } else {
                            objectMap.put(field, (null == value ? StringUtil.EMPTY : value));
                        }
                    } catch (Exception e) {
                        LOGGER.warn("bean转map异常:", e);
                    }
                });
        if (function != null) {
            function.process(objectMap);
        }
        return objectMap;
    }

    // toUrlParams ================================================================================

    /**
     * 对象转url拼接请求参数
     *
     * @param source 对象
     * @return url请求参数
     */
    public static String toUrlParams(Object source) {
        return toUrlParams(source, KuugaConstants.SINGLE_WITH);
    }

    /**
     * 对象转url拼接请求参数，可自定义拼接符
     *
     * @param source 对象
     * @return url请求参数
     */
    public static String toUrlParams(Object source, String separator) {
        return toUrlParams(source, separator, false, false);
    }

    /**
     * 对象转url拼接请求参数，并移除null值
     *
     * @param source 对象
     * @return url请求参数
     */
    public static String toUrlParamsOfRemoveNullValue(Object source) {
        return toUrlParams(source, KuugaConstants.SINGLE_WITH, false, true);
    }

    /**
     * 对象转url拼接请求参数，可包含父类属性
     *
     * @param source        对象
     * @param includeParent 是否包含父类
     * @return url请求参数
     */
    public static String toUrlParams(Object source, boolean includeParent) {
        return toUrlParams(source, KuugaConstants.SINGLE_WITH, includeParent, false);
    }

    /**
     * 对象转url拼接请求参数，可包含父类对象属性
     *
     * @param source 对象
     * @return url请求参数
     */
    public static String toUrlParamsOfRemoveNullValue(Object source, boolean includeParent) {
        return toUrlParams(source, KuugaConstants.SINGLE_WITH, includeParent, true);
    }

    /**
     * 对象转url拼接请求参数，可自定义拼接符，可包含父类对象属性，可移除null值
     *
     * @param source 对象
     * @return url请求参数
     */
    public static String toUrlParams(Object source, String separator, boolean includeParent, boolean removeNullParam) {
        if (ObjectUtil.isNullObject(source)) {
            return StringUtil.EMPTY;
        }
        Map<String, Object> map = toMap(source, includeParent);

        separator = StringUtil.isEmpty(separator) ? KuugaConstants.SINGLE_WITH : separator;

        Joiner joiner = JoinerUtil.on(separator);
        if (removeNullParam) {
            return joiner.skipNulls().withKeyValueSeparator(KuugaConstants.EQUAL_SIGN).join(map);
        }
        return joiner.withKeyValueSeparator(KuugaConstants.EQUAL_SIGN).useForNull(StringUtil.EMPTY).join(map);
    }

    /**
     * 判断object是否为null值
     *
     * @param value object
     * @return boolean
     */
    private static boolean isNullObject(Object value) {
        if (value == null) {
            return true;
        }
        if (value instanceof List) {
            return ListUtil.isEmpty((Collection<?>) value);
        }
        if (value instanceof Set) {
            return SetUtil.isEmpty((Collection<?>) value);
        }
        if (value instanceof Map) {
            return MapUtil.isEmpty((Map<?, ?>) value);
        }
        if (value instanceof Array) {
            return (ArrayUtil.isEmpty(cast(value)));
        }
        if (value instanceof String) {
            return StringUtil.isEmpty(value);
        }
        return false;
    }

    // cast ================================================================================

    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj) throws ClassCastException {
        return (T) obj;
    }

    public static <T> T cast(Object obj, Class<T> tClass) throws ClassCastException {
        return MapperUtil.copy(obj, tClass);
    }

    // getOrElse ================================================================================

    @SuppressWarnings("unchecked")
    public static <T> T getOrElse(Object object, T value) {
        if (ObjectUtil.isNull(object)) {
            return value;
        } else {
            return (T) object;
        }
    }

    // instanceOf ================================================================================

    /**
     * 判断对象类型是否实现类序列化接口
     *
     * @param object 对象
     * @return boolean
     */
    public static boolean classTypeIsSerializable(Object object) {
        return object instanceof Serializable;
    }

    /**
     * 判断对象类型是否为Map
     *
     * @param object 对象
     * @return boolean
     */
    public static boolean classTypeIsMap(Object object) {
        return object instanceof Map;
    }

    /**
     * 判断对象类型是否为数组
     *
     * @param object 对象
     * @return boolean
     */
    public static boolean classTypeIsList(Object object) {
        return object instanceof List;
    }

    /**
     * 判断对象类型是否为Set
     *
     * @param object 对象
     * @return boolean
     */
    public static boolean classTypeIsSet(Object object) {
        return object instanceof Set;
    }

    /**
     * 判断对象类型是否为数组
     *
     * @param object 对象
     * @return boolean
     */
    public static boolean classTypeIsArray(Object object) {
        return object instanceof Array;
    }

    // anewSetValueToStr ========================================================================================================================

    /**
     * anewSetValueToStr
     * 设置“追加模板后的字段值”
     *
     * @param object         对象
     * @param consumer       consumer
     * @param kuugaOptional  kuugaOptional
     * @param appendTemplate 拼接文本模版
     */
    public static <T, U> void anewSetValueToStr(T object, BiConsumer<T, String> consumer, KuugaOptional<U> kuugaOptional, String appendTemplate) {
        U u = kuugaOptional.get();
        anewSetValueToStr(object, consumer, u, appendTemplate);
    }

    /**
     * setFieldValueAfterAppendTemplate
     * 设置“追加模板后的字段值”
     *
     * @param object         对象
     * @param consumer       consumer
     * @param fieldObject    属性对象
     * @param appendTemplate 拼接文本模版
     */
    public static <T> void anewSetValueToStr(T object, BiConsumer<T, String> consumer, Object fieldObject, String appendTemplate) {
        if (StringUtil.isEmpty(appendTemplate) || ObjectUtil.isNull(fieldObject)) {
            consumer.accept(object, String.valueOf(fieldObject));
            return;
        }
        consumer.accept(object, ObjectUtil.cast(String.format(appendTemplate, fieldObject)));
    }

}