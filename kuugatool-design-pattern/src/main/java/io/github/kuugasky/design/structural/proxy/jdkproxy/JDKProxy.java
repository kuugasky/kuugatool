package io.github.kuugasky.design.structural.proxy.jdkproxy;


import java.lang.reflect.Proxy;

/**
 * JDKProxy
 *
 * @author kuuga
 * @since 2023/6/7-06-07 13:03
 */
public class JDKProxy {
    /**
     * 维护一个目标对象
     */
    private final Object target;

    public JDKProxy(Object target) {
        this.target = target;
    }

    /**
     * 给目标对象生成代理对象
     *
     * @return jdk代理生成的对象
     */
    public Object getProxyInstance() {
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                // 这里其实是要实现jdk代理InvocationHandler的接口,然后改成JKD8的写法了而已
                (proxy, method, args) -> {
                    System.out.println("[JDK动态代理]开着法拉利到小区接你");
                    // 执行目标对象方法
                    Object returnValue = method.invoke(target, args);
                    System.out.println("[JDK动态代理]开着法拉利送你回家");
                    return returnValue;
                }
        );
    }

}
