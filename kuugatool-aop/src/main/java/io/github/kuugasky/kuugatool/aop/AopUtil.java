package io.github.kuugasky.kuugatool.aop;

import org.springframework.aop.support.AopUtils;
import org.springframework.lang.Nullable;

/**
 * AopUtil
 *
 * @author kuuga
 * @since 2022/6/29 10:56
 */
public final class AopUtil {

    /**
     * 获取被代理的目标 class
     *
     * @param object object
     * @return Class
     */
    public static Class<?> getTargetClass(Object object) {
        return AopUtils.getTargetClass(object);
    }

    /**
     * 判断是不是 Spring 代理对象
     *
     * @param object object
     * @return boolean
     */
    public static boolean isAopProxy(@Nullable Object object) {
        return AopUtils.isAopProxy(object);
    }

    /**
     * 判断是不是 jdk 动态代理对象
     *
     * @param object object
     * @return boolean
     */
    public static boolean isJdkDynamicProxy(@Nullable Object object) {
        return AopUtils.isJdkDynamicProxy(object);
    }

    /**
     * 判断是不是 CGLIB 代理对象
     *
     * @param object object
     * @return boolean
     */
    public static boolean isCglibProxy(@Nullable Object object) {
        return AopUtils.isCglibProxy(object);
    }

}
