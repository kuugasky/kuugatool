package io.github.kuugasky.kuugatool.aop.proxy;

import io.github.kuugasky.kuugatool.aop.aspects.Aspect;
import io.github.kuugasky.kuugatool.aop.interceptor.CglibInterceptor;
import net.sf.cglib.proxy.Enhancer;

import java.io.Serial;

/**
 * 基于Cglib的切面代理工厂
 *
 * @author looly
 */
public class CglibProxyFactory extends ProxyFactory {

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
    @SuppressWarnings("unchecked")
    public <T> T proxy(T target, Aspect aspect) {
        final Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(new CglibInterceptor(target, aspect));
        return (T) enhancer.create();
    }

}
