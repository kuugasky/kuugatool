package io.github.kuugasky.kuugatool.thirdparty.requested.aop;

import io.github.kuugasky.kuugatool.thirdparty.basic.enums.RequestedAuthenticationWay;
import io.github.kuugasky.kuugatool.thirdparty.requested.authentication.RequestedAuthentication;
import io.github.kuugasky.kuugatool.thirdparty.requested.config.RequestedConfigFactory;
import io.github.kuugasky.kuugatool.thirdparty.requested.config.RequestedConfiguration;
import org.aspectj.lang.JoinPoint;

/**
 * 第三方鉴权切面
 * <p>
 * 鉴权信息传递方式是JSON，鉴权验证采用AOP
 *
 * @author pengqinglong
 * @since 2021/9/8
 */
public interface RequestedJsonAopAuthentication {

    default void requestedAopAuthentication(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        RequestedAuthentication authEntity = null;
        for (Object arg : args) {
            if (arg instanceof RequestedAuthentication argOfAuthentication) {
                authEntity = argOfAuthentication;
            }
        }

        if (authEntity == null) {
            return;
        }

        RequestedAuthenticationWay authenticationWay = authEntity.getAuthenticationWay();
        // 如果鉴权方式不是json 那么代表在其他地方已经完成了鉴权
        if (RequestedAuthenticationWay.JSON != authenticationWay) {
            return;
        }

        RequestedConfiguration<RequestedAuthentication> config = RequestedConfigFactory.getConfig(authEntity);
        config.initForm(authEntity);

        boolean checkout = authEntity.checkout();
        if (!checkout) {
            throw new RuntimeException("鉴权失败");
        }
    }

}