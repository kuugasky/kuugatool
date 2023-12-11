package io.github.kuugasky.kuugatool.extra.mockito.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * MockitoTest
 *
 * @author kuuga
 * @since 2023/12/11 08:29
 */
// 注意我们使用了MockitoJUnitRunner
// @RunWith(MockitoJUnitRunner.class)
public class MockitoTest {

    /**
     * foo 对象内部的成员变量会自动被 @Mock 注解的生成的对象注入。
     */
    @InjectMocks
    private Foo foo;

    /**
     * bar 对象会自动的注入到 @InjectMocks 注解的对象的成员变量中去。
     */
    @Mock
    private Bar bar;

    @Test
    public void mockTest() {
        MockitoAnnotations.initMocks(this);
        // 先对mock对象的待测方法进行存根，当真正执行到mock对象的此方法时
        // 会直接返回存根的结果而不会调用mock对象的实际代码
        Mockito.when(bar.add(1, 2)).thenReturn(7);

        int result = foo.sum(1, 2);
        // 验证是否是存根返回的值
        Assertions.assertEquals(7, result);
    }

}

