package io.github.kuugasky.kuugatool.aop.interceptor;

import io.github.kuugasky.kuugatool.aop.aspects.Aspect;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.io.Serial;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Cglib实现的动态代理切面
 *
 * @author looly, ted.L
 */
public class CglibInterceptor implements MethodInterceptor, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 原对象
     */
    private final Object target;
    /**
     * 自定义切面
     */
    private final Aspect aspect;

    /**
     * 构造
     *
     * @param target 被代理对象
     * @param aspect 切面实现
     */
    public CglibInterceptor(Object target, Aspect aspect) {
        this.target = target;
        this.aspect = aspect;
    }

    public Object getTarget() {
        return this.target;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        // 开始前回调
        if (aspect.before(target, method, args)) {
            try {
                Object result = proxy.invoke(target, args);
                // 结束执行回调
                if (aspect.after(target, method, args, result)) {
                    return result;
                }
            } catch (InvocationTargetException e) {
                // 异常回调（只捕获业务代码导致的异常，而非反射导致的异常）
                if (aspect.afterException(target, method, args, e.getTargetException())) {
                    throw e;
                }
            }
        }
        return null;
    }

}
