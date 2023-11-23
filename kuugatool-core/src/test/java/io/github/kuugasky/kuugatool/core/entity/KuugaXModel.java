package io.github.kuugasky.kuugatool.core.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 测试实体类
 *
 * @author kuuga
 * @date 2020-12-23 下午2:37
 */
@NoArgsConstructor
@AllArgsConstructor
public class KuugaXModel implements Serializable {

    @Setter
    // @Getter
    private String name;
    @Getter
    @Setter
    private int sex;

    @Getter
    @Setter
    private boolean isMan;

    private String getName() {
        return this.name;
    }

}
