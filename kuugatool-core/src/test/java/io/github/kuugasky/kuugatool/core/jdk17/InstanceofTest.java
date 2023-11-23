package io.github.kuugasky.kuugatool.core.jdk17;

import org.junit.jupiter.api.Test;

/**
 * InstanceofTest
 *
 * @author kuuga
 * @since 2022/8/3 10:19
 */
public class InstanceofTest {

    // 在方法的入口接收一个对象
    @Test
    void beforeWay(Object obj) {
        // 通过instanceof判断obj对象的真实数据类型是否是String类型
        if (obj instanceof String) {
            // 如果进来了，说明obj的类型是String类型，直接进行强制类型转换。
            String str = (String) obj;
            // 输出字符串的长度
            System.out.println(str.length());
        }
    }

    @Test
    void afterWay(Object obj) {
        // 通过instanceof判断obj对象的真实数据类型是否是String类型
        if (obj instanceof String str) {
            // 如果进来了，说明obj的类型是String类型，直接进行强制类型转换。
            // 输出字符串的长度
            System.out.println(str.length());
        }
    }

    @Test
    void x(Object obj) {
        if (obj instanceof String s) {
            // 使用s
        } else {
            // 不能使用s
        }
    }

}
