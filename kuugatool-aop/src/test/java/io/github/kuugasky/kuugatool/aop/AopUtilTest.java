package io.github.kuugasky.kuugatool.aop;

import io.github.kuugasky.kuugatool.aop.aspects.SimpleAspect;
import io.github.kuugasky.kuugatool.aop.proxy.JdkProxyFactory;
import io.github.kuugasky.kuugatool.aop.proxy.SpringCglibProxyFactory;
import io.github.kuugasky.kuugatool.aop.test.entity.Dog;
import org.junit.jupiter.api.Test;

class AopUtilTest {

    @Test
    void getTargetClass() {
        Dog dog = new Dog();
        Dog proxy = SpringCglibProxyFactory.createProxy(dog, new SimpleAspect());
        // 获取被代理的目标 class
        Class<?> targetClass = AopUtil.getTargetClass(proxy);
        System.out.println(targetClass);
    }

    @Test
    void isAopProxy() {
        Dog dog = new Dog();
        Dog proxy = SpringCglibProxyFactory.createProxy(dog, new SimpleAspect());
        // 判断是不是 Spring 代理对象
        boolean aopProxy = AopUtil.isAopProxy(proxy);
        System.out.println(aopProxy);
    }

    @Test
    void isJdkDynamicProxy() {
        JdkProxyFactory jdkProxyFactory = new JdkProxyFactory();
        Dog dog = new Dog();
        Dog proxy = jdkProxyFactory.proxy(dog, new SimpleAspect());
        boolean aopProxy = AopUtil.isJdkDynamicProxy(proxy);
        System.out.println(aopProxy);
    }

    @Test
    void isCglibProxy() {
        SpringCglibProxyFactory springCglibProxyFactory = new SpringCglibProxyFactory();
        Dog dog = new Dog();
        Dog proxy = springCglibProxyFactory.proxy(dog, new SimpleAspect());
        boolean aopProxy = AopUtil.isCglibProxy(proxy.getClass());
        System.out.println(aopProxy);
    }

}