package io.github.kuugasky.kuugatool.core.string;

import com.google.common.primitives.Doubles;
import com.google.common.primitives.Ints;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * GuavaTest
 *
 * @author kuuga
 * @since 2021/7/14
 */
public class GuavaTest {

    @Test
    public void ints() {
        // 快速完成集合转换
        List<Integer> list = Ints.asList(1, 3, 5, 7, 9);
        // 基础类型转string拼接
        System.out.println(Ints.join(",", 1, 3, 1, 4));
        System.out.println(Doubles.join(",", 1D, 3D, 1D, 4D));

        // 原生类型数组快速合并
        int[] newIntArray = Ints.concat(new int[]{1, 2}, new int[]{2, 3, 4});
        System.out.println(newIntArray.length);

        // 最大值/最小值
        System.out.println(Ints.max(newIntArray) + "," + Ints.min(newIntArray));

        // 是否包含
        System.out.println(Ints.contains(newIntArray, 6));

        // 集合到数组的转换
        int[] ints = Ints.toArray(list);
        System.out.println(ints.length);
    }

}
