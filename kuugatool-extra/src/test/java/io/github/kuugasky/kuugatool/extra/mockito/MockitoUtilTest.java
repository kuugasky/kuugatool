package io.github.kuugasky.kuugatool.extra.mockito;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.List;

import static org.mockito.Mockito.times;

/**
 * MockitoUtilTest
 *
 * @author kuuga
 * @since 2023/12/8 23:28
 */
class MockitoUtilTest {

    @Test
    @DisplayName("mock简单使用")
    public void test1() {
        // 创建一个Mock对象
        List mockedList = Mockito.mock(List.class);

        // 存根方法，调用get(0)时，返回first
        Mockito.when(mockedList.get(0)).thenReturn("first");
        // 以下代码实现相同的效果
        // Mockito.doReturn("first").when(mockedList).get(0);
        // 返回first
        System.out.println(mockedList.get(0));
        // 没有存根，则会返回null
        System.out.println(mockedList.get(999));
        // 验证方法被调用次数，指定参数类型匹配器
        Mockito.verify(mockedList, times(2)).get(ArgumentMatchers.anyInt());
        // 存根方法，调用get(1)时，直接抛出异常
        Mockito.when(mockedList.get(1)).thenThrow(new RuntimeException());
        // 以下代码实现相同的效果
        // Mockito.doThrow(new RuntimeException()).when(mockedList).get(1);
        // 抛出异常
        System.out.println(mockedList.get(1));
    }


}
