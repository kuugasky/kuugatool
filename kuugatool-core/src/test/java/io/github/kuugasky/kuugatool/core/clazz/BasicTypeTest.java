package io.github.kuugasky.kuugatool.core.clazz;

import io.github.kuugasky.kuugatool.core.lang.Assert;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.junit.jupiter.api.Test;

public class BasicTypeTest {

    @Test
    void wrap() {
        // 原始类转为包装类，非原始类返回原类
        Assert.isTrue(BasicType.wrap(int.class) == Integer.class);
        Assert.isTrue(BasicType.wrap(long.class) == Long.class);
        Assert.isTrue(BasicType.wrap(char.class) == Character.class);
        Assert.isTrue(BasicType.wrap(double.class) == Double.class);
        Assert.isTrue(BasicType.wrap(float.class) == Float.class);
        Assert.isTrue(BasicType.wrap(boolean.class) == Boolean.class);
        Assert.isTrue(BasicType.wrap(short.class) == Short.class);
        Assert.isTrue(BasicType.wrap(void.class) == Void.class);

        Assert.isFalse(BasicType.wrap(int.class) == int.class);
        Assert.isFalse(BasicType.wrap(long.class) == long.class);
        Assert.isFalse(BasicType.wrap(char.class) == char.class);
        Assert.isFalse(BasicType.wrap(double.class) == double.class);
        Assert.isFalse(BasicType.wrap(float.class) == float.class);
        Assert.isFalse(BasicType.wrap(boolean.class) == boolean.class);
        Assert.isFalse(BasicType.wrap(short.class) == short.class);
        Assert.isFalse(BasicType.wrap(void.class) == void.class);
    }

    @Test
    void unWrap() {
        // 包装类转为原始类，非包装类返回原类
        Assert.isTrue(BasicType.unWrap(Integer.class) == int.class);
        Assert.isTrue(BasicType.unWrap(Long.class) == long.class);
        Assert.isTrue(BasicType.unWrap(Character.class) == char.class);
        Assert.isTrue(BasicType.unWrap(Double.class) == double.class);
        Assert.isTrue(BasicType.unWrap(Float.class) == float.class);
        Assert.isTrue(BasicType.unWrap(Boolean.class) == boolean.class);
        Assert.isTrue(BasicType.unWrap(Short.class) == short.class);
        Assert.isTrue(BasicType.unWrap(Void.class) == void.class);

        Assert.isFalse(BasicType.unWrap(Integer.class) == Integer.class);
        Assert.isFalse(BasicType.unWrap(Long.class) == Long.class);
        Assert.isFalse(BasicType.unWrap(Character.class) == Character.class);
        Assert.isFalse(BasicType.unWrap(Double.class) == Double.class);
        Assert.isFalse(BasicType.unWrap(Float.class) == Float.class);
        Assert.isFalse(BasicType.unWrap(Boolean.class) == Boolean.class);
        Assert.isFalse(BasicType.unWrap(Short.class) == Short.class);
        Assert.isFalse(BasicType.unWrap(Void.class) == Void.class);
    }

    @Test
    void values() {
        BasicType[] values = BasicType.values();
        System.out.println(StringUtil.formatString(values));
    }

    @Test
    void valueOf() {
        BasicType aFloat = BasicType.valueOf("FLOAT");
        System.out.println(aFloat);
    }

}