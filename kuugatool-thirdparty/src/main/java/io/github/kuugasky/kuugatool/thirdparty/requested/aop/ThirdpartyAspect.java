// package cn.kuugatool.thirdparty.callme.aop;
//
// import cn.kuugatool.aop.aspectj.AspectInject;
// import cn.kuugatool.thirdparty.callme.config.ThirdpartyConfigFactory;
// import cn.kuugatool.thirdparty.callme.config.ThirdpartyConfiguration;
// import cn.kuugatool.thirdparty.basic.enums.AuthenticationWay;
// import cn.kuugatool.thirdparty.basic.form.Authentication;
// import lombok.extern.slf4j.Slf4j;
// import org.aspectj.lang.JoinPoint;
// import org.aspectj.lang.annotation.Aspect;
// import org.aspectj.lang.annotation.Pointcut;
// import org.springframework.cloud.context.config.annotation.RefreshScope;
// import org.springframework.core.annotation.Order;
// import org.springframework.stereotype.Component;
//
// /**
//  * 第三方鉴权切面
//  *
//  * @author pengqinglong
//  * @since 2021/9/8
//  */
// @Slf4j
// @RefreshScope
// @Aspect
// @Component
// @Order(999)
// public class ThirdpartyAspect implements AspectInject {
//
//     @Override
//     @Pointcut("execution(* com.kfang.web.agent.thirdparty.controller.openapi..*.*(..))")
//     public void pointcut() {
//
//     }
//
//     @Override
//     public void before(JoinPoint joinPoint) {
//         Object[] args = joinPoint.getArgs();
//         Authentication authEntity = null;
//         for (Object arg : args) {
//             if (arg instanceof Authentication argOfAuthentication) {
//                 authEntity = argOfAuthentication;
//             }
//         }
//
//         if (authEntity == null) {
//             return;
//         }
//
//         AuthenticationWay authenticationWay = authEntity.getAuthenticationWay();
//         // 如果鉴权方式不是json 那么代表在其他地方已经完成了鉴权
//         if (AuthenticationWay.JSON != authenticationWay) {
//             return;
//         }
//
//         ThirdpartyConfiguration<Authentication> config = ThirdpartyConfigFactory.getConfig(authEntity);
//         config.initForm(authEntity);
//
//         boolean checkout = authEntity.checkout();
//         if (!checkout) {
//             throw new RuntimeException("鉴权失败");
//         }
//     }
//
//     @Override
//     public void afterReturning(JoinPoint joinPoint, Object result) {
//
//     }
//
//     @Override
//     public void after(JoinPoint joinPoint) {
//
//     }
//
//     @Override
//     public void afterThrowing(Throwable exception) {
//
//     }
//
// }