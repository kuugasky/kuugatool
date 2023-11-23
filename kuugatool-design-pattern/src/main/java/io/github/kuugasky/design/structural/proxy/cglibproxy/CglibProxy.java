package io.github.kuugasky.design.structural.proxy.cglibproxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * CglibProxy
 *
 * @author kuuga
 * @since 2023/6/7-06-07 13:32
 */
public class CglibProxy implements MethodInterceptor {
    /**
     * 维护目标对象
     */
    private final Object target;

    public CglibProxy(Object target) {
        this.target = target;
    }

    /**
     * 给目标对象创建一个代理对象
     *
     * @return Object
     */
    public Object getProxyInstance() {
        // 1.工具类
        Enhancer en = new Enhancer();
        // 2.设置父类
        en.setSuperclass(target.getClass());
        // 3.设置回调函数
        en.setCallback(this);
        // 4.创建子类(代理对象)
        return en.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("[cglib动态代理]开着法拉利到小区接你");
        // 执行目标对象的方法
        Object returnValue = method.invoke(target, objects);
        System.out.println("[cglib动态代理]开着法拉利送你回家");
        return returnValue;

    }
}
