package io.github.kuugasky.kuugatool.extra.concurrent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

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
public class KuugaDTO implements Serializable {

    private String name;
    private int sex;

}
