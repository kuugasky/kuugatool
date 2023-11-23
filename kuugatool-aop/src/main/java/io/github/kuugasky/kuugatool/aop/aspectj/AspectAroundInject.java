package io.github.kuugasky.kuugatool.aop.aspectj;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 环绕型切面代理
 * <p>
 * 1.子类需要注入 {@link Aspect()} 和 {@link @Component}<br>
 * 2.子类重写pointcut(),且方法注入{@link Pointcut()}
 * <pre>
 *     环绕通知既可以在目标方法前执行也可在目标方法之后执行，更重要的是环绕通知可以控制目标方法是否指向执行，
 *     但即使如此，我们应该尽量以最简单的方式满足需求，在仅需在目标方法前执行时，应该采用前置通知而非环绕通知。
 *     第一个参数必须是ProceedingJoinPoint，通过该对象的proceed()方法来执行目标函数，proceed()的返回值就是环绕通知的返回值。
 *     同样的，ProceedingJoinPoint对象也是可以获取目标对象的信息,如类名称,方法参数,方法名称等等
 * </pre>
 *
 * @author kuuga
 * @since 2022-04-06 17:19:25
 */
@Aspect
public interface AspectAroundInject extends AspectPointcutStatement {

    /**
     * 环绕通知：灵活自由的在目标方法中切入代码
     * 第一个参数必须是ProceedingJoinPoint，通过该对象的proceed()方法来传递拦截器（通知）链或执行函数，proceed()的返回值就是环绕通知的返回值。
     *
     * @param pjp pjp
     * @return object
     * @throws Throwable Throwable
     */
    @Around("pointcut()")
    Object around(ProceedingJoinPoint pjp) throws Throwable;

}
