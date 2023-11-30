package io.github.kuugasky.kuugatool.core.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 测试实体类
 *
 * @author kuuga
 * @since 2020-12-23 下午2:37
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class KuugaStudentModel extends KuugaModel {

    private String schoolName;

    public String getSchoolName(String prefix) {
        return prefix + "." + schoolName;
    }

}
