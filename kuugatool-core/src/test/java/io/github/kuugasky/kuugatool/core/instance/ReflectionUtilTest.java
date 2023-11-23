package io.github.kuugasky.kuugatool.core.instance;

import io.github.kuugasky.kuugatool.core.clazz.ClassUtil;
import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.entity.KuugaDTO1;
import io.github.kuugasky.kuugatool.core.entity.KuugaModel;
import io.github.kuugasky.kuugatool.core.entity.KuugaStudentModel;
import io.github.kuugasky.kuugatool.core.entity.KuugaXModel;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import lombok.Data;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

public class ReflectionUtilTest {

    @Test
    public void newInstance() {
        // 实例化对象
        KuugaModel kuugaModel = ReflectionUtil.newInstance(KuugaModel.class);
        System.out.println(StringUtil.formatString(kuugaModel));
    }

    @Test
    public void getDeclaredMethod() {
        // 循环向上转型, 获取对象的 DeclaredMethod
        Method method = ReflectionUtil.getDeclaredMethod(KuugaModel.class, "getName");
        System.out.println(StringUtil.formatString(method));
    }

    @Test
    public void invokeMethod() {
        KuugaModel kuugaModel = ReflectionUtil.newInstance(KuugaModel.class);
        kuugaModel.setSex(100);
        // 直接调用对象方法, 而忽略修饰符(private, protected, default)
        Object getAge = ReflectionUtil.invokeMethod(kuugaModel, "getSex");
        System.out.println(getAge);
    }

    @Test
    public void testInvokeMethod() {
        KuugaStudentModel kuugaStudentModel = ReflectionUtil.newInstance(KuugaStudentModel.class);
        // 直接调用对象方法, 而忽略修饰符(private, protected, default)
        Object getSchoolName = ReflectionUtil.invokeMethod(kuugaStudentModel, "getSchoolName", new Class[]{String.class}, new Object[]{"kuuga11"});
        System.out.println(getSchoolName);
    }

    @Test
    public void getDeclaredField() {
        // 循环向上转型, 获取对象的 DeclaredField
        Field name = ReflectionUtil.getDeclaredField(KuugaStudentModel.class, "name");
        System.out.println(name);
    }

    @Test
    public void getDeclaredField2() {
        // 循环向上转型, 获取对象的 DeclaredField
        Field name = ReflectionUtil.getDeclaredField(KuugaStudentModel.class, "name", String.class);
        System.out.println(name);
    }

    @Test
    public void setFieldValue() {
        KuugaStudentModel kuugaStudentModel = ReflectionUtil.newInstance(KuugaStudentModel.class);
        // 直接设置对象属性值, 忽略 private/protected 修饰符, 也不经过 setter
        ReflectionUtil.setFieldValue(kuugaStudentModel, "name", "kuugaTest");
        // 直接读取对象的属性值, 忽略 private/protected 修饰符, 也不经过 getter
        Object name = ReflectionUtil.getFieldValue(kuugaStudentModel, "name");
        System.out.println(name);
    }

    @Test
    public void setFieldValue1() {
        KuugaStudentModel kuugaStudentModel = ReflectionUtil.newInstance(KuugaStudentModel.class);

        Field nameField = ReflectionUtil.getDeclaredField(KuugaStudentModel.class, "name");
        // 直接设置对象属性值, 忽略 private/protected 修饰符, 也不经过 setter
        ReflectionUtil.setFieldValue(kuugaStudentModel, nameField, "kuugaTest");
        // 直接读取对象的属性值, 忽略 private/protected 修饰符, 也不经过 getter
        Object name = ReflectionUtil.getFieldValue(kuugaStudentModel, nameField);
        System.out.println(name);
    }

    @Test
    public void existsField() {
        // 校验clazz对象类类型是否包含fieldName属性
        Demo demo = new Demo();
        Demo.DemoItem demoItem = new Demo.DemoItem();
        demoItem.setName("kuuga");
        demo.setDemoItem(demoItem);
        System.out.println(ReflectionUtil.existsField(Demo.class, "demoItem"));
        System.out.println(ReflectionUtil.existsField(demo, "demoItem"));
    }

    @Test
    public void existsMethod() {
        // 校验clazz对象类类型是否包含methodName方法
        boolean name = ReflectionUtil.existsMethod(KuugaStudentModel.class, "getName");
        System.out.println(name);
    }

    @Test
    public void getDeclaredFieldNames() {
        // 获取clazz自身和父类所有的成员属性名
        List<String> declaredFieldNames = ReflectionUtil.getDeclaredFieldNames(KuugaStudentModel.class);
        System.out.println(declaredFieldNames);
    }

    @Test
    public void copyProperties() {
        KuugaStudentModel kuugaStudentModel = new KuugaStudentModel("kuugaSchool1");
        KuugaStudentModel kuugaStudentModel1 = new KuugaStudentModel();
        // 对象属性COPY
        ReflectionUtil.copyProperties(kuugaStudentModel, kuugaStudentModel1);
        System.out.println(StringUtil.formatString(kuugaStudentModel1));
    }

    @Test
    public void initializeAllObject() {
        // 循环创建对象
        // 创建嵌层对象实体，防止内层对象属性调用报空指针异常
        ReflectionUtil.initializeAllObject(new KuugaModel());
    }

    @Test
    public void canWriteable() {
        KuugaXModel kuugaXModel = new KuugaXModel();
        boolean canWriteable = ReflectionUtil.canWriteable(kuugaXModel, "name");
        System.out.println(canWriteable);
    }

    @Test
    public void canReadable() {
        KuugaXModel kuugaXModel = new KuugaXModel();
        boolean canReadable = ReflectionUtil.canReadable(kuugaXModel, "name");
        System.out.println(canReadable);
    }

    @Test
    public void getValueByGetMethod() {
        KuugaXModel kuugaXModel = new KuugaXModel();
        kuugaXModel.setName("kuuga");
        kuugaXModel.setSex(1);
        kuugaXModel.setMan(true);

        // System.out.println(ReflectionUtil.getFieldValue(kuugaXModel, "name"));
        // System.out.println(ReflectionUtil.getValueByGetMethod(kuugaXModel, "name"));
        // System.out.println(ReflectionUtil.getValueByGetMethod(kuugaXModel, "sex"));
        // System.out.println(ReflectionUtil.getFieldValue(kuugaXModel, "isMan"));
        System.out.println(ReflectionUtil.getValueByGetMethod(kuugaXModel, "man"));
    }

    @Test
    public void setValueByGetMethod() {
        KuugaXModel kuugaXModel = new KuugaXModel();
        // kuugaXModel.setName("kuuga");
        // kuugaXModel.setSex(1);
        kuugaXModel.setMan(false);

        // System.out.println(ReflectionUtil.getFieldValue(kuugaXModel, "name"));
        // System.out.println(ReflectionUtil.getValueByGetMethod(kuugaXModel, "name"));
        // System.out.println(ReflectionUtil.getValueByGetMethod(kuugaXModel, "sex"));
        // System.out.println(ReflectionUtil.getFieldValue(kuugaXModel, "isMan"));
        ReflectionUtil.setValueBySetMethod(kuugaXModel, "isMan", true);
        System.out.println(kuugaXModel.isMan());
    }

    @Test
    public void newInstanceIfPossible() {
        KuugaDTO1 kuugaModel = ReflectionUtil.newInstanceIfPossible(KuugaDTO1.class);
        System.out.println(StringUtil.formatString(kuugaModel));
    }

    @Test
    void getAllDeclaredMethods() {
        Method[] allDeclaredMethods = ReflectionUtil.getAllDeclaredMethods(KuugaModel.class);
        ListUtil.optimize(allDeclaredMethods).forEach(method -> System.out.println(method.getName()));
    }

    @Test
    void isEqualsMethod() {
        Method equalMethod = ReflectionUtil.getDeclaredMethod(KuugaModel.class, "equals", Object.class);
        System.out.println(ReflectionUtil.isEqualsMethod(equalMethod));
    }

    @Test
    void isHashCodeMethod() {
        Method equalMethod = ReflectionUtil.getDeclaredMethod(KuugaModel.class, "hashCode");
        System.out.println(ReflectionUtil.isHashCodeMethod(equalMethod));
    }

    @Test
    void isToStringMethod() {
        Method equalMethod = ReflectionUtil.getDeclaredMethod(KuugaModel.class, "toString");
        System.out.println(ReflectionUtil.isToStringMethod(equalMethod));
    }

    @Test
    void isObjectMethod() {
        Method getNameMethod = ReflectionUtil.getDeclaredMethod(KuugaModel.class, "getName");
        Assertions.assertFalse(ReflectionUtil.isObjectMethod(getNameMethod));
        Method toStringMethod = ReflectionUtil.getDeclaredMethod(KuugaModel.class, "toString");
        Assertions.assertTrue(ReflectionUtil.isObjectMethod(toStringMethod));
    }

    @Test
    void declaresException() {
        Method exceptionMethod = ReflectionUtil.getDeclaredMethod(KuugaModel.class, "throwException");
        boolean b = ReflectionUtil.declaresException(exceptionMethod, Exception.class);
        System.out.println(b);
    }

    @Test
    void isPublicStaticFinal() {
        Field Kuuga = ReflectionUtil.getDeclaredField(KuugaModel.class, "kuuga", String.class);
        System.out.println(ReflectionUtil.isPublicStaticFinal(Kuuga));
        Field kuuga1 = ReflectionUtil.getDeclaredField(KuugaModel.class, "kuuga_1");
        System.out.println(ReflectionUtil.isPublicStaticFinal(kuuga1));
        Field kuuga2 = ReflectionUtil.getDeclaredField(KuugaModel.class, "kuuga_2");
        System.out.println(ReflectionUtil.isPublicStaticFinal(kuuga2));
    }

    @Test
    void isPublic() {
        Field Kuuga = ReflectionUtil.getDeclaredField(KuugaModel.class, "kuuga", String.class);
        System.out.println(ReflectionUtil.isPublic(Kuuga));
    }

    @Test
    void isStatic() {
        Field Kuuga = ReflectionUtil.getDeclaredField(KuugaModel.class, "kuuga", String.class);
        System.out.println(ReflectionUtil.isStatic(Kuuga));
    }

    @Test
    void isFinal() {
        Field Kuuga = ReflectionUtil.getDeclaredField(KuugaModel.class, "kuuga", String.class);
        System.out.println(ReflectionUtil.isFinal(Kuuga));
    }

    @Test
    void shallowCopyFieldState() {
        KuugaModel kuugaModel = new KuugaModel();
        kuugaModel.setName("kuuga");
        kuugaModel.setSex(1);
        KuugaStudentModel target = new KuugaStudentModel();
        ReflectionUtil.shallowCopyFieldState(kuugaModel, target);
        System.out.println(StringUtil.formatString(target));
    }

    @Test
    void doWithFields() {
        ReflectionUtil.doWithFields(KuugaModel.class, field -> {
            if (ReflectionUtil.isPublicStaticFinal(field)) {
                System.out.println(field.getName() + "=" + field.getType());
            }
        });
    }

    @Test
    void testDoWithFields() {
        ReflectionUtil.doWithFields(KuugaModel.class, field -> {
            if (ReflectionUtil.isPublicStaticFinal(field)) {
                System.out.println(field.getName() + "=" + field.getType());
            }
        }, field -> ClassUtil.isEnum(field) || ReflectionUtil.isPublicStaticFinal(field));
    }

    @Test
    void doWithLocalFields() {
        ReflectionUtil.doWithLocalFields(KuugaStudentModel.class, field -> {
            if (ReflectionUtil.isPublicStaticFinal(field)) {
                System.out.println(field.getName() + "=" + field.getType());
            }
        });
    }

    @Test
    void getFieldValueIfExist() throws NoSuchFieldException {
        Object houseId = ReflectionUtil.getFieldValueIfExist(KuugaStudentModel.class, "houseId");
        System.out.println(houseId);
    }

    @Test
    void getFieldValueByPath() throws NoSuchFieldException {
        Demo demo = new Demo();
        Demo.DemoItem demoItem = new Demo.DemoItem();
        demoItem.setName("kuuga");
        demo.setDemoItem(demoItem);
        Object houseId = ReflectionUtil.getFieldValueByPath(demo, "demoItem#name");
        System.out.println(houseId);
    }

    @Data
    static class Demo {

        private DemoItem demoItem;

        @Data
        static class DemoItem {
            private String name;
        }
    }

    static class Demo2 {

        private Demo2() {
        }
    }

    @Test
    void setAccessibleMethod() throws InvocationTargetException, IllegalAccessException {
        KuugaXModel kuugaModel = new KuugaXModel();
        kuugaModel.setName("kuuga");

        Method method = ReflectionUtil.getMethod(KuugaXModel.class, "getName");
        try {
            Object x = method.invoke(kuugaModel);
            System.out.println(x);
        } catch (RuntimeException | InvocationTargetException | IllegalAccessException e) {
            System.err.println(e.getMessage());
        }
        ReflectionUtil.setAccessible(method);
        // 取消 Java 权限检查。以便后续执行私有构造方法
        Object x = method.invoke(kuugaModel);
        System.out.println(x);
    }

    @Test
    void setAccessible() throws IllegalAccessException, RuntimeException {
        KuugaModel kuugaModel = new KuugaModel();
        kuugaModel.setName("kuuga");

        Field name = ReflectionUtil.getDeclaredField(KuugaModel.class, "name");
        try {
            Object x = name.get(kuugaModel);
            System.out.println(x);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            System.err.println(e.getMessage());
        }
        ReflectionUtil.setAccessible(name);
        // 取消 Java 权限检查。以便后续执行私有构造字段
        Object x = name.get(kuugaModel);
        System.out.println(x);
    }

    @Test
    void invoke() {
        KuugaModel kuugaModel = new KuugaModel();
        kuugaModel.setName("kuuga");

        Method getNameMethod = ReflectionUtil.getMethod(KuugaModel.class, "getName");

        Object invoke = ReflectionUtil.invoke(kuugaModel, getNameMethod);
        System.out.println(StringUtil.formatString(invoke));
    }

    @Test
    void getMethods() {
        System.out.println(ReflectionUtil.getMethods(KuugaModel.class));
        System.out.println(ReflectionUtil.getMethods(new KuugaModel()));
    }

    @Test
    void getDeclaredFields() {
        System.out.println(ReflectionUtil.getDeclaredFields(KuugaModel.class));
        System.out.println(ReflectionUtil.getDeclaredFields(new KuugaModel()));
    }

    @Test
    void getFieldClass() {
        Field name = ReflectionUtil.getDeclaredField(Demo3.class, "demo2s");
        System.out.println(ReflectionUtil.getFieldClass(name, true));
        Field name2 = ReflectionUtil.getDeclaredField(Demo3.class, "demo1s");
        System.out.println(ReflectionUtil.getFieldClass(name2, false));
        Field name1 = ReflectionUtil.getDeclaredField(Demo3.class, "demo");
        System.out.println(ReflectionUtil.getFieldClass(name1));
    }

    @Test
    void getFields() {
        List<Field> fields = ReflectionUtil.getFields(KuugaModel.class);
        System.out.println(fields);
        List<Field> fields1 = ReflectionUtil.getFields(new KuugaModel());
        System.out.println(fields1);
    }

    @SuppressWarnings("unused")
    static class Demo3 {
        private List<Demo2> demo2s;
        private Set<Demo3> demo1s;
        private Demo demo;
    }

}