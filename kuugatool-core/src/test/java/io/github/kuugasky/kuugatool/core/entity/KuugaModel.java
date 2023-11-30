package io.github.kuugasky.kuugatool.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

/**
 * 测试实体类
 *
 * @author kuuga
 * @since 2020-12-23 下午2:37
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KuugaModel implements Serializable {

    public static final String KUUGA = "kuuga";
    public final static String KUUGA_1 = "kuuga1";
    final static String KUUGA_2 = "kuuga2";

    public KuugaModel(String name) {
        this.name = name;
    }

    private String name;
    private int sex;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KuugaModel that = (KuugaModel) o;
        return sex == that.sex && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, sex);
    }

    @Override
    public String toString() {
        return "KuugaModel{" +
                "name='" + name + '\'' +
                ", sex=" + sex +
                '}';
    }

    public void throwException() throws Exception {

    }

}
