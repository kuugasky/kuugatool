package io.github.kuugasky.kuugatool.aop.aspectj;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

/**
 * 切面
 * <p>
 * 1.子类需要注入 {@link Aspect()} 和 {@link @Component}<br>
 * 2.子类重写pointcut(),且方法注入{@link Pointcut()}
 *
 * @author kuuga
 * @since 2022-04-06 17:19:25
 */
@Aspect
public interface AspectInject extends AspectPointcutStatement {

    /**
     * 前置通知：在目标方法执行前调用
     *
     * @param joinPoint 运行时对方法调用过程的一个具体化，是一个运行时动态的概念，内部包含了被调用方法、方法所在的对象及拦截器链等信息。
     * @throws Throwable the throwable
     */
    @Before(value = "pointcut()")
    void before(JoinPoint joinPoint) throws Throwable;

    /**
     * 后置通知：在目标方法执行后调用，若目标方法出现异常，则不执行
     *
     * @param joinPoint 运行时对方法调用过程的一个具体化，是一个运行时动态的概念，内部包含了被调用方法、方法所在的对象及拦截器链等信息。
     * @param result    返回对象
     * @throws Throwable the throwable
     */
    @AfterReturning(value = "pointcut()", returning = "result")
    void afterReturning(JoinPoint joinPoint, Object result) throws Throwable;

    /**
     * 后置/最终通知：无论什么情况下都会执行的方法
     *
     * @param joinPoint 运行时对方法调用过程的一个具体化，是一个运行时动态的概念，内部包含了被调用方法、方法所在的对象及拦截器链等信息。
     * @throws Throwable the throwable
     */
    @After(value = "pointcut()")
    void after(JoinPoint joinPoint) throws Throwable;

    /**
     * 异常通知：目标方法抛出异常时执行
     *
     * @param exception 抛出异常的信息
     * @throws Throwable the throwable
     */
    @AfterThrowing(value = "pointcut()", throwing = "exception")
    void afterThrowing(Throwable exception) throws Throwable;

}
