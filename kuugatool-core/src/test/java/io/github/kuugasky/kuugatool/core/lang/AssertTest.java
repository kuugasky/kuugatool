package io.github.kuugasky.kuugatool.core.lang;

import io.github.kuugasky.kuugatool.core.entity.KuugaModel;
import io.github.kuugasky.kuugatool.core.entity.KuugaStudentModel;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


public class AssertTest {

    @Test
    void isNullTest() {
        String a = null;
        Assert.isNull(a);
    }

    @Test
    void notNullTest() {
        String a = null;
        Assert.notNull(a);
    }

    @Test
    void notNullTest1() {
        String a = null;
        Assert.notNull(a, "参数不能为空");
    }

    @Test
    void notNullTest2() {
        String a = null;
        Assert.notNull(a, "参数[{}]不能为空", "key");
    }

    // @Test(expected = IllegalArgumentException.class)
    @Test
    void isTrueTest() {
        // given when
        IllegalArgumentException businessException = Assertions.assertThrows(IllegalArgumentException.class, this::isTrueTestDemo);
        // then
        assertNull(businessException.getMessage());
    }

    private void isTrueTestDemo() {
        int i = 0;
        // noinspection ConstantConditions
        Assert.isTrue(i > 0, IllegalArgumentException::new);
    }

    // @Test(expected = IndexOutOfBoundsException.class)
    @Test
    void isTrueTest2() {
        IndexOutOfBoundsException businessException = Assertions.assertThrows(IndexOutOfBoundsException.class,
                () -> {
                    int i = -1;
                    // noinspection ConstantConditions
                    Assert.isTrue(i >= 0, IndexOutOfBoundsException::new);
                });
        System.out.println(businessException.getMessage());
    }

    // @Test(expected = IndexOutOfBoundsException.class)
    @Test
    void isTrueTest3() {
        IndexOutOfBoundsException businessException = Assertions.assertThrows(IndexOutOfBoundsException.class,
                () -> {
                    int i = -1;
                    // noinspection ConstantConditions
                    Assert.isTrue(i > 0, () -> new IndexOutOfBoundsException("relation message to return"));
                });
        assertEquals(businessException.getMessage(), "relation message to return");
    }

    @Test
    void isTrue() {
        Assert.isTrue(true);
    }

    @Test
    void isTrue1() {
        Assert.isTrue(false, "结果不能为false");
    }

    @Test
    void isTrue2() {
        Assert.isTrue(false, "结果[{}]不能为false", "key");
    }

    @Test
    void notEmpty() {
        Assert.notEmpty(StringUtil.EMPTY);
    }

    @Test
    void isInstanceOf() {
        KuugaModel kuugaModel = new KuugaModel();
        kuugaModel.setName("kuuga");
        kuugaModel.setSex(1);
        KuugaStudentModel kuugaStudentModel = new KuugaStudentModel();
        kuugaStudentModel.setSchoolName("school");
        Assert.isInstanceOf(String.class, kuugaStudentModel, "错误信息:{}", kuugaStudentModel.toString());
    }

    @Test
    void isAssignable() {
        Assert.isAssignable(KuugaModel.class, String.class);
    }
}
