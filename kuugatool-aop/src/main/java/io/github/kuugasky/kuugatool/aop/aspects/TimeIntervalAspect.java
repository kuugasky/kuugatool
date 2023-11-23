package io.github.kuugasky.kuugatool.aop.aspects;

import io.github.kuugasky.kuugatool.core.date.interval.TimeInterval;
import io.github.kuugasky.kuugatool.core.lang.Console;

import java.io.Serial;
import java.lang.reflect.Method;

/**
 * 通过日志打印方法的执行时间的切面
 *
 * @author Looly
 */
public class TimeIntervalAspect extends SimpleAspect {

    @Serial
    private static final long serialVersionUID = 1L;

    private final TimeInterval interval = new TimeInterval();

    @Override
    public boolean before(Object target, Method method, Object[] args) {
        interval.start();
        return true;
    }

    @Override
    public boolean after(Object target, Method method, Object[] args, Object returnVal) {
        Console.yellowLog("Method [{}.{}] execute spend [{}]ms return value [{}]",
                target.getClass().getName(),
                method.getName(),
                interval.intervalMs(),
                returnVal);
        return true;
    }

}
