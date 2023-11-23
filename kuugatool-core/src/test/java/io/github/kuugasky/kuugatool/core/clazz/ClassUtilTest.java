package io.github.kuugasky.kuugatool.core.clazz;

import io.github.kuugasky.kuugatool.core.entity.KuugaDTO;
import io.github.kuugasky.kuugatool.core.entity.KuugaModel;
import io.github.kuugasky.kuugatool.core.entity.KuugaStudentModel;
import io.github.kuugasky.kuugatool.core.instance.ReflectionUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

public class ClassUtilTest {

    @Test
    void loadClass() {
        Class<KuugaModel> objectClass = ClassUtil.loadClass("io.github.kuugasky.kuugatool.core.entity.KuugaModel");
        System.out.println(StringUtil.formatString(objectClass));
    }

    @Test
    void testLoadClass1() {
        Class<KuugaModel> objectClass = ClassUtil.loadClass("io.github.kuugasky.kuugatool.core.entity.KuugaModel", true);
        System.out.println(StringUtil.formatString(objectClass));
    }

    @Test
    void getClassLoader() {
        ClassLoader classLoader = ClassUtil.getClassLoader();
        System.out.println(StringUtil.formatString(classLoader));
    }

    @Test
    void getClasses() {
        // 获得对象数组的类数组
        Class<?>[] classes = ClassUtil.getClasses(KuugaModel.class, String.class);
        System.out.println(StringUtil.formatString(classes));
    }

    @Test
    void isAllAssignableFrom() {
        boolean allAssignableFrom = ClassUtil.isAllAssignableFrom(new Class[]{KuugaModel.class}, new Class[]{KuugaModel.class});
        System.out.println(allAssignableFrom);
        boolean allAssignableFrom1 = ClassUtil.isAllAssignableFrom(new Class[]{KuugaModel.class}, new Class[]{KuugaModel.class, KuugaDTO.class});
        System.out.println(allAssignableFrom1);
    }

    @Test
    void getDeclaredMethod() {
        Method getName = ClassUtil.getDeclaredMethod(KuugaModel.class, "getName", new Class[]{});
        System.out.println(StringUtil.formatString(getName));
    }

    @Test
    void isBasicType() {
        // 是否为基本类型（包括包装类和原始类）
        System.out.println(ClassUtil.isBasicType(KuugaModel.class));
        System.out.println(ClassUtil.isBasicType(Float.class));
        System.out.println(ClassUtil.isBasicType(float.class));

        Field sex = ReflectionUtil.getDeclaredField(KuugaModel.class, "sex");
        System.out.println(ClassUtil.isBasicType(sex));
    }

    @Test
    void isPrimitiveWrapper() {
        // 是否为包装类型
        System.out.println(ClassUtil.isPrimitiveWrapper(KuugaModel.class));
        System.out.println(ClassUtil.isPrimitiveWrapper(Float.class));
        System.out.println(ClassUtil.isPrimitiveWrapper(float.class));
    }

    @Test
    void getDefaultValues() {
        Object[] defaultValues = ClassUtil.getDefaultValues(KuugaModel.class, Double.class, double.class, int.class);
        System.out.println(Arrays.toString(defaultValues));
    }

    @Test
    void getDefaultValue() {
        System.out.println(ClassUtil.getDefaultValue(KuugaModel.class));
        System.out.println(ClassUtil.getDefaultValue(Double.class));
        System.out.println(ClassUtil.getDefaultValue(double.class));
        System.out.println(ClassUtil.getDefaultValue(int.class));
    }

    @Test
    void isPrivate() {
        Method getName = ClassUtil.getDeclaredMethod(KuugaModel.class, "getName", new Class[]{});
        System.out.println(ClassUtil.isPrivate(getName));
        Field field = ClassUtil.getDeclaredField(KuugaModel.class, "sex");
        System.out.println(ClassUtil.isPrivate(field));
        System.out.println(ClassUtil.isPrivate(Integer.class));
    }

    @Test
    void is() {
        System.out.println(ClassUtil.isPublic(KuugaModel.class));
    }

    @Test
    void testIs1() {
        Method getName = ClassUtil.getDeclaredMethod(KuugaModel.class, "getName", new Class[]{});
        System.out.println(ClassUtil.isPublic(getName));
    }

    @Test
    void isNot() {
        System.out.println(ClassUtil.isNotPublic(KuugaModel.class));
    }

    @Test
    void testIsNot1() {
        Method getName = ClassUtil.getDeclaredMethod(KuugaModel.class, "getName", new Class[]{});
        System.out.println(ClassUtil.isNotPublic(getName));
        Field field = ClassUtil.getDeclaredField(KuugaModel.class, "sex");
        System.out.println(ClassUtil.isNotPublic(field));
    }

    @Test
    void isStatic() {
        Method getName = ClassUtil.getDeclaredMethod(KuugaModel.class, "getName", new Class[]{});
        System.out.println(ClassUtil.isStatic(getName));
        System.out.println(ClassUtil.isStatic(Integer.class));
    }

    @Test
    void isFinal() {
        Method getName = ClassUtil.getDeclaredMethod(KuugaModel.class, "getName", new Class[]{});
        System.out.println(ClassUtil.isFinal(getName));
        System.out.println(ClassUtil.isFinal(Integer.class));
        Field field = ClassUtil.getDeclaredField(KuugaModel.class, "sex");
        System.out.println(ClassUtil.isFinal(field));
    }

    @Test
    void setAccessible() {
        // 设置方法为可访问
        Method getName = ClassUtil.getDeclaredMethod(KuugaModel.class, "getName", new Class[]{});
        Method x = ClassUtil.setAccessible(getName);
        System.out.println(x);
    }

    @Test
    void isAbstract() {
        // 是否为抽象类
        System.out.println(ClassUtil.isAbstract(KuugaModel.class));
    }

    @Test
    void isNormalClass() {
        // 是否为标准的类
        System.out.println(ClassUtil.isNormalClass(KuugaModel.class));
        System.out.println(ClassUtil.isNormalClass(ArrayList.class));
    }

    @Test
    void isEnum() {
        // 判断类是否为枚举类型
        System.out.println(ClassUtil.isEnum(KuugaModel.class));
    }

    @Test
    void isEnum2() {
        // 判断类是否为枚举类型
        Field name = ReflectionUtil.getDeclaredField(KuugaModel.class, "name");
        System.out.println(ClassUtil.isEnum(name));
    }

    @Test
    void isCollectionField() {
        Field name = ReflectionUtil.getDeclaredField(KuugaModel.class, "name");
        System.out.println(ClassUtil.isCollectionField(name));
    }

    @Test
    void isTypeVariable() {
        Field name = ReflectionUtil.getDeclaredField(KuugaModel.class, "name");
        System.out.println(ClassUtil.isTypeVariable(name));
    }

    @Test
    void isBasicDataType() {
        System.out.println(ClassUtil.isBasicDataType(String.class));
    }

    @Test
    void getRuntimeClassName() {
        System.out.println(ClassUtil.getRuntimeClassName());
    }

    @Test
    void getRuntimeMethodName() {
        System.out.println(ClassUtil.getRuntimeMethodName());
    }

    @Test
    void getRuntimeLineNumber() {
        System.out.println(ClassUtil.getRuntimeLineNumber());
    }

    @Test
    void getRuntimeTraceInfo() {
        System.out.println(StringUtil.formatString(ClassUtil.getRuntimeTraceInfo()));
        System.out.println(StringUtil.repeatNormal());
        System.out.println(StringUtil.formatString(ClassUtil.getRuntimeTraceInfo(1)));
    }

    @Test
    void testGetRuntimeTraceInfo() {
        RuntimeTraceInfo runtimeTraceInfo = ClassUtil.getRuntimeTraceInfo();
        System.out.println(runtimeTraceInfo);
        System.out.println(runtimeTraceInfo.simpleInfo());
    }

    @Test
    void isAssignable() {
        System.out.println(ClassUtil.isAssignable(KuugaModel.class, KuugaStudentModel.class));
        System.out.println(ClassUtil.isAssignable(KuugaModel.class, String.class));
    }

}