package io.github.kuugasky.kuugatool.aop.aspectj;

import org.aspectj.lang.annotation.Pointcut;

/**
 * 切面切入点声明
 *
 * @author kuuga
 * @since 2022-04-07 09:48:12
 */
interface AspectPointcutStatement {

    /**
     * 定义切入点：对要拦截的方法进行定义与限制，如包、类
     * <p>
     * 1、execution(public * *(..)) 任意的公共方法<br>
     * 2、execution（* set*（..）） 以set开头的所有的方法<br>
     * 3、execution（* com.kuuga.web.annotation.LoggerApply.*（..））com.syzl.web.annotation.LoggerApply这个类里的所有的方法<br>
     * 4、execution（* com.kuuga.web.annotation.*.*（..））com.syzl.web.annotation包下的所有的类的所有的方法<br>
     * 5、execution（* com.kuuga.web.annotation..*.*（..））com.syzl.web.annotation包及子包下所有的类的所有的方法<br>
     * 6、execution(* com.kuuga.web.annotation..*.*(String,?,Long)) com.syzl.web.annotation包及子包下所有的类的有三个参数，第一个参数为String类型，第二个参数为任意类型，第三个参数为Long类型的方法<br>
     * 7、execution(@annotation(com.kuuga.web.annotation.A1HiddenDataAnnotation)) 对该注解切入<br>
     * 8、@Pointcut("execution(* com.kuuga.web.agent..*.controller..*.*(..))") 符合该路径的<br>
     * 9、@Pointcut("@annotation(com.kuuga.service.agent.annotations.AgentServiceLog)") 指定注解<br>
     * 10、@Pointcut("execution(* com.kuuga.service.agent.thirdparty.service..*.*(..))") 执行该service目录下的方法时切入
     */
    @Pointcut()
    void pointcut();

}
