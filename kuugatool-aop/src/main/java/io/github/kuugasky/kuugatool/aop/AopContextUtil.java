package io.github.kuugasky.kuugatool.aop;

import org.springframework.aop.framework.AopContext;

/**
 * AopContentUtil
 *
 * @author kuuga
 * @since 2022/6/29 10:52
 */
public final class AopContextUtil {

    /**
     * 获取当前对象的代理对象
     *
     * @return proxy
     */
    public static Object currentProxy() {
        return AopContext.currentProxy();
    }

}
