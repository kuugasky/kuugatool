package io.github.kuugasky.kuugatool.json.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 测试实体类
 *
 * @author kuuga
 * @date 2020-12-23 下午2:37
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KuugaModel {

    private String name;
    private int sex;
    private Boolean enabled;

    private KuugaEnum kuugaEnum = KuugaEnum.GOOD;

}
