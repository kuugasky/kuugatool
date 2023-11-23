package io.github.kuugasky.kuugatool.core.clazz;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;

public class ClassLoaderUtilTest {

    @Test
    public void getContextClassLoader() {
        // 获取当前线程的类加载器
        ClassLoader contextClassLoader = ClassLoaderUtil.getContextClassLoader();
        System.out.println(contextClassLoader.toString());
    }

    @Test
    public void getClassLoader() {
        // 获取当前的类加载器
        ClassLoader classLoader = ClassLoaderUtil.getClassLoader();
        System.out.println(classLoader.toString());
    }

    @Test
    public void loadClass() {
        // 加载类，通过传入类的字符串，返回其对应的类名，使用默认ClassLoader并初始化类（调用static模块内容和初始化static属性）
        Class<?> classOfIntArray = ClassLoaderUtil.loadClass("int[]");
        System.out.println(classOfIntArray.toString());
        System.out.println(classOfIntArray == Array.class);
        Class<?> classOfInt = ClassLoaderUtil.loadClass("int");
        System.out.println(classOfInt.toString());
        System.out.println(classOfInt == Integer.class);
    }

    @Test
    public void testLoadClass() {
        Class<?> classOfIntArray = ClassLoaderUtil.loadClass("int[]", true);
        System.out.println(classOfIntArray.toString());
    }

    @Test
    public void testLoadClass1() {
    }

    @Test
    public void loadPrimitiveClass() {
    }

    @Test
    public void isPresent() {
        boolean classLoaderUtil = ClassLoaderUtil.isPresent("ClassLoaderUtil");
        System.out.println(classLoaderUtil);
        boolean str = ClassLoaderUtil.isPresent("String");
        System.out.println(str);
    }

    @Test
    public void testIsPresent() {
        boolean str = ClassLoaderUtil.isPresent("String", ClassLoaderUtil.getClassLoader());
        System.out.println(str);
    }
}