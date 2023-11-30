package io.github.kuugasky.kuugatool.core.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 测试实体类
 *
 * @author kuuga
 * @since 2020-12-23 下午2:37
 */
@Data
// @AllArgsConstructor
public class KuugaDTO1 implements Serializable {

    private String name;
    private int sex;

    public KuugaDTO1(String name, int sex) {
        this.name = name;
        this.sex = sex;
    }
}
