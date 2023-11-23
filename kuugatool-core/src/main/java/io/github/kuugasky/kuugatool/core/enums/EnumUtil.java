package io.github.kuugasky.kuugatool.core.enums;

import io.github.kuugasky.kuugatool.core.clazz.ClassUtil;
import io.github.kuugasky.kuugatool.core.collection.ArrayUtil;
import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.collection.MapUtil;
import io.github.kuugasky.kuugatool.core.constants.KuugaCharConstants;
import io.github.kuugasky.kuugatool.core.instance.ReflectionUtil;
import io.github.kuugasky.kuugatool.core.lang.Assert;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 枚举工具类
 *
 * @author kuuga
 * Create on 2021/2/8
 */
@Slf4j
public final class EnumUtil {

    public static final String INDEX = "index";
    private static final String GET = "get";
    private static final String IS = "is";
    private static final String NAME = "name";
    private static final String DESC = "desc";
    private static final String KUUGA = "kuuga";
    public static final String FILE = "file";
    public static final String JAR = "jar";
    public static final String CLASS = "class";
    public static final String ORDINAL = "ordinal";
    public static final String DECLARING_CLASS = "declaringClass";

    /**
     * 定义私有构造函数来屏蔽这个隐式公有构造函数
     */
    private EnumUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * package map cache.
     */
    private static final Map<String, Object> PACKAGE_MAP_CACHE = MapUtil.newHashMap();

    /**
     * 获取某个枚举集合的name作为新的集合返回
     *
     * @param enumList 枚举集合
     * @return nameList
     */
    public static <T extends Enum<T>> List<String> getEnumNames(List<T> enumList) {
        if (ListUtil.isEmpty(enumList)) {
            return ListUtil.emptyList();
        }
        return enumList.stream().map(Enum::name).collect(Collectors.toList());
    }

    /**
     * 扫描指定枚举包下的所有枚举classMap
     * <p>
     * ps：List<Map<String, Object>>内的map如下：
     * - name:B
     * - desc:字节
     *
     * @param list list
     * @return map(className, list)
     */
    public static Map<String, List<Map<String, Object>>> scanAllEnum(List<Class<?>> list) {
        return scanEnum(ListUtil.newArrayList(KUUGA), list, null, null);
    }

    /**
     * 扫描指定枚举包下的某些枚举classMap
     *
     * @param enumNames 要查询的枚举names
     * @param list      指定枚举class包
     * @return map
     */
    public static Map<String, List<Map<String, Object>>> scanEnum(List<String> enumNames, List<Class<?>> list) {
        if (ListUtil.isEmpty(enumNames)) {
            throw new RuntimeException("请输入枚举类名");
        }
        return scanEnum(enumNames, list, null, null);
    }

    /**
     * 扫描指定枚举包下的某些枚举classMap
     *
     * @param enumNames       要查询的枚举names
     * @param list            指定枚举class包
     * @param enumContainsMap 枚举包含
     * @param enumFilterMap   枚举过滤
     * @return map
     */
    public static Map<String, List<Map<String, Object>>> scanEnum(List<String> enumNames, List<Class<?>> list, Map<String, List<Enum<?>>> enumContainsMap, Map<String, List<Enum<?>>> enumFilterMap) {
        Map<String, Object> packClassMap = getPackClassMapByEnumSign(list.toArray(Class[]::new));

        Map<String, List<Map<String, Object>>> result = MapUtil.newHashMap();

        boolean isSpecialParam = KUUGA.equals(enumNames.get(0));

        Stream<Map.Entry<String, Object>> stream = packClassMap.entrySet().stream();
        if (!isSpecialParam) {
            // 非特殊场景需要过滤出用户想要返回的枚举信息
            List<String> optimize = ListUtil.optimize(enumNames);
            stream = stream.filter((e) -> optimize.contains(e.getKey()));
        }

        stream.forEach((e) -> {
            String enumName = String.valueOf(e.getKey());
            Object enumClass = e.getValue();

            try {
                Object[] enumConstants = ((Class<?>) enumClass).getEnumConstants();
                if (ArrayUtil.length(enumConstants) == 0) {
                    return;
                }
                Class<?> class1 = Class.forName(enumConstants[0].getClass().getName());
                if (class1.isEnum()) {
                    List<Map<String, Object>> enumContent = getEnumContent(class1);
                    // 只包含相关枚举值
                    if (MapUtil.optimize(enumContainsMap).containsKey(enumName)) {
                        Set<String> enumItemSet = enumContainsMap.get(enumName).stream().map(Enum::name).collect(Collectors.toSet());
                        List<Map<String, Object>> enumContents = enumContent.stream().filter(map -> {
                            Object name = String.valueOf(map.get(NAME));
                            return enumItemSet.contains(name);
                        }).collect(Collectors.toList());
                        result.put(enumName, enumContents);
                        return;
                    }
                    // 过滤掉相关枚举值
                    if (MapUtil.optimize(enumFilterMap).containsKey(enumName)) {
                        Set<String> enumItemSet = enumFilterMap.get(enumName).stream().map(Enum::name).collect(Collectors.toSet());
                        List<Map<String, Object>> enumContents = enumContent.stream().filter(map -> {
                            Object name = String.valueOf(map.get(NAME));
                            return !enumItemSet.contains(name);
                        }).collect(Collectors.toList());
                        result.put(enumName, enumContents);
                        return;
                    }
                    result.put(enumName, enumContent);
                }
            } catch (Exception var6) {
                var6.printStackTrace();
                log.warn("EnumUtil error:【{}】{}", enumClass.getClass().getName(), var6.getMessage());
            }

        });
        return result;
    }

    static List<Map<String, Object>> getEnumContent(Class<?> class1) {
        List<String> declaredFieldNames = ReflectionUtil.getDeclaredFieldNames(class1);
        List<?> list = Arrays.asList(class1.getEnumConstants());
        if (declaredFieldNames.contains(INDEX)) {
            list.sort((o1, o2) -> {
                int o1Index = Integer.parseInt(Objects.requireNonNull(ReflectionUtil.getFieldValue(o1, INDEX)).toString());
                int o2Index = Integer.parseInt(Objects.requireNonNull(ReflectionUtil.getFieldValue(o2, INDEX)).toString());
                return o1Index - o2Index;
            });
        }
        List<Method> methods = new ArrayList<>();
        for (Method method1 : class1.getDeclaredMethods()) {
            if (method1.getName().contains(GET)) {
                methods.add(method1);
            }
        }
        List<Map<String, Object>> list2 = ListUtil.newArrayList();

        if (methods.size() == 1) {
            list.forEach(enumClasses -> methods.forEach(method -> {
                try {
                    Map<String, Object> enumObjectMap = MapUtil.newHashMap();
                    String field = getFieldNameByMethodName(method.getName());
                    if (INDEX.equalsIgnoreCase(field)) {
                        return;
                    }
                    if (StringUtil.isEmpty(field) || !declaredFieldNames.contains(field)) {
                        return;
                    }
                    Object invoke = method.invoke(enumClasses);
                    enumObjectMap.put(field, ObjectUtil.getOrElse(invoke, StringUtil.EMPTY));
                    enumObjectMap.put(NAME, enumClasses.toString());
                    list2.add(enumObjectMap);
                } catch (Exception var4) {
                    var4.printStackTrace();
                    log.warn("EnumUtil error:【{}】{}", class1.getName(), var4.getMessage());
                }
            }));
        } else {
            list.forEach(enumClasses -> {
                Map<String, Object> enumObjectMap = MapUtil.newHashMap();
                enumObjectMap.put(NAME, enumClasses.toString());
                methods.forEach(method -> {
                    try {
                        String field = getFieldNameByMethodName(method.getName());
                        if (INDEX.equalsIgnoreCase(field)) {
                            return;
                        }
                        if (StringUtil.isEmpty(field) || !declaredFieldNames.contains(field)) {
                            return;
                        }
                        Object invoke = method.invoke(enumClasses);
                        if (ObjectUtil.isNull(invoke)) {
                            enumObjectMap.put(field, StringUtil.EMPTY);
                        } else if (ClassUtil.isEnum(invoke.getClass())) {
                            enumObjectMap.put(field, ObjectUtil.getOrElse(ReflectionUtil.getFieldValue(invoke, DESC), StringUtil.EMPTY));
                        } else {
                            enumObjectMap.put(field, ObjectUtil.getOrElse(invoke, StringUtil.EMPTY));
                        }
                    } catch (Exception var4) {
                        var4.printStackTrace();
                        log.warn("EnumUtil error:【{}】{}", class1.getName(), var4.getMessage());
                    }
                });
                list2.add(enumObjectMap);
            });
        }

        return list2;
    }

    private static String getFieldNameByMethodName(String methodName) {
        String field = null;
        if (StringUtil.startsWithIgnoreCase(methodName, GET)) {
            field = methodName.replaceFirst(GET, StringUtil.EMPTY);
        } else if (StringUtil.startsWithIgnoreCase(methodName, IS)) {
            field = methodName.replaceFirst(IS, StringUtil.EMPTY);
        }
        field = StringUtil.firstCharToLowerCase(field);
        return field;
    }

    /**
     * 获取指定class包目录下的所有ClassMap
     *
     * @param clazzArray class数组
     * @return map(ClassName, Class)
     */
    public static synchronized Map<String, Object> getPackClassMap(Class<?>... clazzArray) {
        if (PACKAGE_MAP_CACHE.isEmpty()) {
            for (Class<?> clazz : clazzArray) {
                Set<Class<?>> classesFromPackage = getClassSetFromPackage(clazz.getPackage().getName());

                for (Class<?> value : classesFromPackage) {
                    PACKAGE_MAP_CACHE.put(value.getSimpleName(), value);
                }
            }
        }
        return PACKAGE_MAP_CACHE;
    }

    /**
     * 通过枚举符号获取包类映射
     *
     * @param clazzArray clazzArray
     * @return map
     */
    public static synchronized Map<String, Object> getPackClassMapByEnumSign(Class<?>... clazzArray) {
        if (PACKAGE_MAP_CACHE.isEmpty()) {
            for (Class<?> clazz : clazzArray) {
                Set<Class<?>> classesFromPackage = getClassSetFromPackage(clazz.getPackage().getName());

                for (Class<?> value : classesFromPackage) {
                    EnumSign enumSign = value.getAnnotation(EnumSign.class);
                    String simpleName = value.getSimpleName();
                    if (StringUtil.isEmpty(simpleName)) {
                        continue;
                    }

                    simpleName = ObjectUtil.nonNull(enumSign) && StringUtil.hasText(enumSign.value())
                            ? enumSign.value()
                            : simpleName;

                    if (PACKAGE_MAP_CACHE.containsKey(simpleName)) {
                        String message = String.format("存在同名枚举(%s)，请检查.", simpleName);
                        log.warn(message);
                        throw new RuntimeException(message);
                    }

                    PACKAGE_MAP_CACHE.put(simpleName, value);
                }
            }
        }
        return PACKAGE_MAP_CACHE;
    }

    /**
     * 从包中获取类集
     *
     * @param packPath 包路径
     * @return set
     */
    public static Set<Class<?>> getClassSetFromPackage(String packPath) {
        Set<Class<?>> classes = new LinkedHashSet<>();

        String packageDirName = packPath.replace(KuugaCharConstants.DOT, KuugaCharConstants.SLASH);

        try {
            Enumeration<?> dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);

            while (dirs.hasMoreElements()) {
                URL url = (URL) dirs.nextElement();
                // 获取url协议
                String protocol = url.getProtocol();
                if (FILE.equals(protocol)) {
                    String filePath = URLDecoder.decode(url.getFile(), StandardCharsets.UTF_8);
                    findClassInPackageByFile(packPath, filePath, true, classes);
                } else if (JAR.equals(protocol)) {
                    findClassInPackageByJar(url, packageDirName, packPath, true, classes);
                }
            }
        } catch (IOException var9) {
            var9.printStackTrace();
        }

        return classes;
    }

    /**
     * 通过Jar找到Package中的Class
     *
     * @param url            url
     * @param packageDirName 包目录名
     * @param packageName    包名
     * @param recursive      是否递归
     * @param classes        classes
     */
    public static void findClassInPackageByJar(URL url, String packageDirName, String packageName, boolean recursive, Set<Class<?>> classes) {
        try {
            JarFile jar = ((JarURLConnection) url.openConnection()).getJarFile();
            Enumeration<?> entries = jar.entries();

            while (entries.hasMoreElements()) {
                JarEntry entry = (JarEntry) entries.nextElement();
                String name = entry.getName();
                if (name.charAt(0) == '/') {
                    name = name.substring(1);
                }

                if (name.startsWith(packageDirName)) {
                    int idx = name.lastIndexOf(47);
                    if (idx != -1) {
                        packageName = name.substring(0, idx).replace('/', '.');
                    }

                    boolean b = (idx != -1 || recursive) && name.endsWith(".class") && !entry.isDirectory();
                    if (b) {
                        String className = name.substring(packageName.length() + 1, name.length() - 6);

                        try {
                            classes.add(Class.forName(packageName + "." + className));
                        } catch (ClassNotFoundException var12) {
                            var12.printStackTrace();
                        }
                    }
                }
            }
        } catch (IOException var13) {
            var13.printStackTrace();
        }
    }

    /**
     * @param packageName 包名
     * @param filePath    文件路径
     * @param recursive   是否递归
     * @param clazzArray  class数组
     */
    public static void findClassInPackageByFile(String packageName, String filePath, final boolean recursive, Set<Class<?>> clazzArray) {
        File dir = new File(filePath);
        if (dir.exists() && dir.isDirectory()) {
            File[] dirFiles = dir.listFiles((file) -> {
                boolean acceptDir = recursive && file.isDirectory();
                boolean acceptClass = file.getName().endsWith(CLASS);
                return acceptDir || acceptClass;
            });
            assert dirFiles != null;

            for (File file : dirFiles) {
                if (file.isDirectory()) {
                    findClassInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, clazzArray);
                } else {
                    String className = file.getName().substring(0, file.getName().length() - 6);

                    try {
                        clazzArray.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + "." + className));
                    } catch (Exception var12) {
                        var12.printStackTrace();
                    }
                }
            }
        }
    }

    // =================================================================================

    /**
     * 指定类是否为Enum类
     *
     * @param clazz 类
     * @return 是否为Enum类
     */
    public static boolean isEnum(Class<?> clazz) {
        Assert.notNull(clazz);
        return clazz.isEnum();
    }

    /**
     * 指定类是否为Enum类
     *
     * @param obj 类
     * @return 是否为Enum类
     */
    public static boolean isEnum(Object obj) {
        Assert.notNull(obj);
        return obj.getClass().isEnum();
    }

    /**
     * Enum对象转String，调用{@link Enum#name()} 方法
     *
     * @param e Enum
     * @return name值
     * @since 4.1.13
     */
    public static String toString(Enum<?> e) {
        return null != e ? e.name() : null;
    }

    /**
     * 枚举类中所有枚举对象的name列表
     *
     * @param clazz 枚举类
     * @return name列表
     */
    public static List<String> getNames(Class<? extends Enum<?>> clazz) {
        final Enum<?>[] enums = clazz.getEnumConstants();
        if (null == enums) {
            return null;
        }
        final List<String> list = new ArrayList<>(enums.length);
        for (Enum<?> e : enums) {
            list.add(e.name());
        }
        return list;
    }

    /**
     * 将name字符串解析成枚举项
     *
     * @param enumClass 枚举class
     * @param name      枚举项name
     * @param <T>       泛型
     * @return 枚举
     */
    public static <T extends Enum<T>> T parseEnumOfName(Class<T> enumClass, String name) {
        if (StringUtil.hasText(name)) {
            try {
                return Enum.valueOf(enumClass, name);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("枚举转换异常:{}", e.getMessage(), e);
            }
        }
        return null;
    }

    /**
     * 获得枚举类中各枚举对象下指定字段的值
     *
     * @param clazz     枚举类
     * @param fieldName 字段名，最终调用getXXX方法
     * @return 字段值列表
     */
    public static List<Object> getFieldValues(Class<? extends Enum<?>> clazz, String fieldName) {
        final Enum<?>[] enums = clazz.getEnumConstants();
        if (null == enums) {
            return null;
        }
        final List<Object> list = new ArrayList<>(enums.length);
        for (Enum<?> e : enums) {
            list.add(ReflectionUtil.getFieldValue(e, fieldName));
        }
        return list;
    }

    /**
     * 获得枚举类中所有的字段名<br>
     * 除用户自定义的字段名，也包括“name”字段，例如：
     *
     * <pre>
     *   EnumUtil.getFieldNames(Color.class) == ["name", "index"]
     * </pre>
     *
     * @param clazz 枚举类
     * @return 字段名列表
     * @since 4.1.20
     */
    public static List<String> getFieldNames(Class<? extends Enum<?>> clazz) {
        final List<String> names = new ArrayList<>();
        final List<Field> fields = ReflectionUtil.getDeclaredFields(clazz);
        String name;
        for (Field field : fields) {
            name = field.getName();
            if (field.getType().isEnum() || name.contains("$VALUES") || ORDINAL.equals(name)) {
                continue;
            }
            if (!names.contains(name)) {
                names.add(name);
            }
        }
        return names;
    }

    /**
     * 获取枚举字符串值和枚举对象的Map对应，使用LinkedHashMap保证有序<br>
     * 结果中键为枚举名，值为枚举对象
     *
     * @param <E>       枚举类型
     * @param enumClass 枚举类
     * @return 枚举字符串值和枚举对象的Map对应，使用LinkedHashMap保证有序
     * @since 4.0.2
     */
    public static <E extends Enum<E>> LinkedHashMap<String, E> getEnumMap(final Class<E> enumClass) {
        final LinkedHashMap<String, E> map = new LinkedHashMap<>();
        for (final E e : enumClass.getEnumConstants()) {
            map.put(e.name(), e);
        }
        return map;
    }

    /**
     * 获得枚举名对应指定字段值的Map<br>
     * 键为枚举名，值为字段值
     *
     * @param clazz     枚举类
     * @param fieldName 字段名，最终调用getXXX方法
     * @return 枚举名对应指定字段值的Map
     */
    public static Map<String, Object> getNameFieldMap(Class<? extends Enum<?>> clazz, String fieldName) {
        final Enum<?>[] enums = clazz.getEnumConstants();
        if (null == enums) {
            return null;
        }
        final Map<String, Object> map = MapUtil.newHashMap(enums.length);
        for (Enum<?> e : enums) {
            map.put(e.name(), ReflectionUtil.getFieldValue(e, fieldName));
        }
        return map;
    }

    /**
     * 判断某个值是存在枚举中
     *
     * @param <E>       枚举类型
     * @param enumClass 枚举类
     * @param val       需要查找的值
     * @return 是否存在
     */
    public static <E extends Enum<E>> boolean contains(final Class<E> enumClass, String val) {
        return EnumUtil.getEnumMap(enumClass).containsKey(val);
    }

    /**
     * 判断某个枚举项是存在enumArray中
     *
     * @param <E>       枚举类型
     * @param source    需要比对枚举项
     * @param enumArray 枚举项数组
     * @return 是否存在
     */
    public static <E extends Enum<E>> boolean contains(Enum<E> source, Enum<E>[] enumArray) {
        if (ArrayUtil.isEmpty(enumArray)) {
            return false;
        }
        if (null == source) {
            return false;
        }
        return Arrays.stream(enumArray).anyMatch(item -> item == source);
    }

    /**
     * 判断某个值是不存在枚举中
     *
     * @param <E>       枚举类型
     * @param enumClass 枚举类
     * @param val       需要查找的值
     * @return 是否不存在
     */
    public static <E extends Enum<E>> boolean notContains(final Class<E> enumClass, String val) {
        return !contains(enumClass, val);
    }

    /**
     * 忽略大小检查某个枚举值是否匹配指定值
     *
     * @param e   枚举值
     * @param val 需要判断的值
     * @return 是非匹配
     */
    public static <T extends Enum<T>> boolean equalsIgnoreCase(final Enum<T> e, String val) {
        return StringUtil.equalsIgnoreCase(toString(e), val);
    }

    /**
     * 检查某个枚举值是否匹配指定值
     *
     * @param e   枚举值
     * @param val 需要判断的值
     * @return 是非匹配
     */
    public static <T extends Enum<T>> boolean equals(final Enum<T> e, String val) {
        return StringUtil.equals(e.name(), val);
    }

    /**
     * 获取枚举项的某个方法值
     *
     * @param enumItem  枚举项
     * @param attribute 属性名
     * @param <T>       泛型
     * @return 文本值
     */
    public static <T extends Enum<T>> String getAttributeValue(final Enum<T> enumItem, String attribute) {
        Object fieldValue = ReflectionUtil.getFieldValue(enumItem, attribute);
        return ObjectUtil.getOrElse(fieldValue, StringUtil.EMPTY);
    }

    /**
     * 枚举项names转枚举项集合
     *
     * @param enumItemNames 枚举项names
     * @param enumClass     枚举class
     * @param <T>           泛型
     * @return 枚举项集合
     */
    public static <T extends Enum<T>> List<T> toList(List<String> enumItemNames, Class<T> enumClass) {
        if (ListUtil.isEmpty(enumItemNames)) {
            return ListUtil.emptyList();
        }
        List<T> result = ListUtil.newArrayList();
        enumItemNames.forEach(enumItemName -> result.add(parseEnumOfName(enumClass, enumItemName)));
        return result;
    }

    // =================================================================================

    /**
     * 解析enum的fieldName对应集合值，判断是否包含fieldValue，如果匹配，则返回对应枚举项，可能返回null
     *
     * @param enumClass  枚举class
     * @param fieldName  枚举项fieldName
     * @param fieldValue 枚举项filedName对应值
     * @param <T>        T
     * @return 枚举
     */
    public static <T extends Enum<T>> Enum<T> getEnumItemIfContain(Class<T> enumClass, String fieldName, Object fieldValue) {
        return getEnumItemIfContain(enumClass, fieldName, fieldValue, null);
    }

    /**
     * 解析enum的fieldName对应集合值，判断是否包含fieldValue，如果匹配，则返回对应枚举项
     *
     * @param enumClass   枚举class
     * @param fieldName   枚举项fieldName
     * @param fieldValue  枚举项filedName对应值
     * @param defaultEnum 默认枚举值
     * @param <T>         T
     * @return 枚举
     */
    public static <T extends Enum<T>> Enum<T> getEnumItemIfContain(Class<T> enumClass, String fieldName, Object fieldValue, Enum<T> defaultEnum) {
        final Enum<T>[] enums = enumClass.getEnumConstants();
        for (Enum<T> e : enums) {
            Object fieldValueObject = ReflectionUtil.getFieldValue(e, fieldName);
            if (ObjectUtil.equals(fieldValueObject, fieldValue)) {
                return e;
            }
        }
        return defaultEnum;
    }

    // toMap ================================================================================================================

    /**
     * 枚举值转Map<br>
     * - key:name<br>
     * - value:enum.name<br>
     *
     * @param enumValue 枚举值
     * @param <T>       T
     * @return map
     */
    public static <T extends Enum<T>> Map<String, String> toMap(Enum<T> enumValue) {
        return convert(enumValue);
    }

    @SafeVarargs
    public static <T extends Enum<T>> List<Map<String, String>> toMap(Enum<T>... enumValues) {
        List<Map<String, String>> result = ListUtil.newArrayList();
        ListUtil.optimize(enumValues).forEach(enumValue -> result.add(convert(enumValue)));
        return result;
    }

    /**
     * 枚举值转List<Map<String, Object>><br>
     * - key:name<br>
     * - value:enum.name<br>
     *
     * @param enumClass 枚举class
     * @param <T>       T
     * @return map
     */
    public static <T extends Enum<T>> List<Map<String, String>> toMap(Class<T> enumClass, List<String> containsItems) {
        List<T> values = Arrays.asList(enumClass.getEnumConstants());
        List<Map<String, String>> list = new ArrayList<>();
        values.forEach(value -> {
            try {
                if (ListUtil.hasItem(containsItems)) {
                    if (!containsItems.contains(value.name())) {
                        return;
                    }
                }
                // fixme 考虑containsItems移除
                list.add(toMap(value));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        return list;
    }

    /**
     * 枚举类转Map<String, String><br>
     * - key:name<br>
     * - key:enum.name<br>
     *
     * @param <T>       T
     * @param enumClass 枚举类
     * @return map
     */
    private static <T extends Enum<T>> Map<String, String> convert(Enum<T> enumClass) {
        if (enumClass == null) {
            return Collections.emptyMap();
        }

        List<String> excludeFields = Arrays.asList(CLASS, DECLARING_CLASS);
        Map<String, String> params = new HashMap<>(10);

        params.put(NAME, enumClass.name());
        Class<?> clazz = enumClass.getClass();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            String methodName = method.getName();
            if (methodName.startsWith(GET) && method.getParameterTypes().length == 0) {
                try {
                    String methodNameStr = methodName.split(GET)[1];
                    String fieldName = methodNameStr.substring(0, 1).toLowerCase() + methodNameStr.substring(1);

                    if (excludeFields.contains(fieldName)) {
                        continue;
                    }
                    params.put(fieldName, (String) method.invoke(enumClass));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return params;
    }

}
