package io.github.kuugasky.kuugatool.extra.concurrent.completion;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;

import javax.annotation.Resource;

public class SleuthCompletionServicePoolTest {

    @Resource
    private BeanFactory beanFactory;

    @Test
    public void init() {
        new SleuthCompletionServicePool(beanFactory, 1, false).submit(() -> System.out.println("kuuga"));
    }

}