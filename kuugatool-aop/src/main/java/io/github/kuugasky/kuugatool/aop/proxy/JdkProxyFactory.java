package io.github.kuugasky.kuugatool.aop.proxy;

import io.github.kuugasky.kuugatool.aop.SpiProxyUtil;
import io.github.kuugasky.kuugatool.aop.aspects.Aspect;
import io.github.kuugasky.kuugatool.aop.interceptor.JdkInterceptor;

import java.io.Serial;

/**
 * 基于JDK实现的切面代理工厂
 *
 * @author looly
 */
public class JdkProxyFactory extends ProxyFactory {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 工厂模式-动态创建代理
     *
     * @param <T>    代理对象类型
     * @param target 被代理对象
     * @param aspect 切面实现
     * @return 代理对象
     */
    @Override
    public <T> T proxy(T target, Aspect aspect) {
        ClassLoader classLoader = target.getClass().getClassLoader();
        JdkInterceptor invocationHandler = new JdkInterceptor(target, aspect);
        Class<?>[] interfaces = target.getClass().getInterfaces();

        return SpiProxyUtil.newProxyInstance(classLoader, invocationHandler, interfaces);
    }

}
