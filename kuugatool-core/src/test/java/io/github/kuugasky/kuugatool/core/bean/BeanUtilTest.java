package io.github.kuugasky.kuugatool.core.bean;

import io.github.kuugasky.kuugatool.core.string.StringUtil;
import lombok.Data;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.beans.PropertyEditor;

class BeanUtilTest {

    @Test
    void isReadableBean() {
        System.out.println(BeanUtil.isReadableBean(BeanTest.class));
    }

    @Test
    void isBean() {
        System.out.println(BeanUtil.isBean(BeanTest.class));
    }

    @Test
    void hasSetter() {
        System.out.println(BeanUtil.hasSetter(BeanTest.class));
    }

    @Test
    void hasGetter() {
        System.out.println(BeanUtil.hasGetter(BeanTest.class));
    }

    @Test
    void hasPublicField() {
        System.out.println(BeanUtil.hasPublicField(BeanTest.class));
    }

    @Test
    @Disabled
    void findEditor() {
        PropertyEditor editor = BeanUtil.findEditor(BeanTest.class);
        System.out.println(StringUtil.formatString(editor));
    }

}

@Data
class BeanTest {

    private BeanTest() {

    }

    private String name;

    public String type;

}