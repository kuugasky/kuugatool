package io.github.kuugasky.kuugatool.aop.proxy;

import io.github.kuugasky.kuugatool.aop.aspects.Aspect;
import io.github.kuugasky.kuugatool.core.instance.ReflectionUtil;
import io.github.kuugasky.kuugatool.core.spi.SpiServiceLoaderUtil;

import java.io.Serial;
import java.io.Serializable;

/**
 * 代理抽象工厂-工厂方法模式
 * <p>
 * 根据用户引入代理库的不同，产生不同的代理对象
 *
 * @author looly
 */
public abstract class ProxyFactory implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 创建代理
     *
     * @param <T>    代理对象类型
     * @param target 被代理对象
     * @param aspect 切面实现
     * @return 代理对象
     */
    public abstract <T> T proxy(T target, Aspect aspect);

    /**
     * 创建代理
     *
     * @param <T>         代理对象类型
     * @param target      被代理对象
     * @param aspectClass 切面实现类，自动实例化
     * @return 代理对象
     * @since 5.3.1
     */
    public <T> T proxy(T target, Class<? extends Aspect> aspectClass) {
        return proxy(target, ReflectionUtil.newInstanceIfPossible(aspectClass));
    }

    /**
     * 根据用户引入Cglib与否自动创建代理对象
     *
     * @param <T>         切面对象类型
     * @param target      目标对象
     * @param aspectClass 切面对象类
     * @return 代理对象
     */
    public static <T> T createProxy(T target, Class<? extends Aspect> aspectClass) {
        return createProxy(target, ReflectionUtil.newInstance(aspectClass));
    }

    /**
     * 根据用户引入Cglib与否自动创建代理对象
     *
     * @param <T>    切面对象类型
     * @param target 被代理对象
     * @param aspect 切面实现
     * @return 代理对象
     */
    public static <T> T createProxy(T target, Aspect aspect) {
        return create().proxy(target, aspect);
    }

    /**
     * 根据用户引入Cglib与否创建代理工厂
     *
     * @return 代理工厂
     */
    public static ProxyFactory create() {
        // 从SPI机制中加载第一个可用的代理工厂
        return SpiServiceLoaderUtil.loadFirstAvailable(ProxyFactory.class);
    }

}
