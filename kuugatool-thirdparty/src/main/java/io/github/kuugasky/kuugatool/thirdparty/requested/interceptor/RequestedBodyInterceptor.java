package io.github.kuugasky.kuugatool.thirdparty.requested.interceptor;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.instance.ReflectionUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import io.github.kuugasky.kuugatool.thirdparty.basic.enums.RequestedAuthenticationWay;
import io.github.kuugasky.kuugatool.thirdparty.requested.authentication.RequestedAuthentication;
import io.github.kuugasky.kuugatool.thirdparty.requested.config.RequestedConfigFactory;
import io.github.kuugasky.kuugatool.thirdparty.requested.config.RequestedConfiguration;
import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Type;

/**
 * 第三方鉴权拦截器
 * <p>
 * 鉴权信息传递方式是BODY，鉴权验证采用拦截器
 *
 * @author pengqinglong
 * @since 2021/9/16
 */
public class RequestedBodyInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod)) {
            return super.preHandle(request, response, handler);
        }

        RequestedAuthentication authentication = this.getParamAuthentication((HandlerMethod) handler);
        if (authentication == null) {
            return super.preHandle(request, response, handler);
        }
        RequestedAuthenticationWay authenticationWay = authentication.getAuthenticationWay();

        RequestedConfiguration<RequestedAuthentication> config = RequestedConfigFactory.getConfig(authentication);

        // JSON方式走AOP
        if (RequestedAuthenticationWay.BODY == authenticationWay) {
            bodyRequestedSignCheck(request, authentication, config);
        } else if (RequestedAuthenticationWay.HEADER == authenticationWay) {
            headerRequestedSignCheck(request, authentication, config);
        }
        return super.preHandle(request, response, handler);
    }

    private static void headerRequestedSignCheck(HttpServletRequest request, RequestedAuthentication authentication, RequestedConfiguration<RequestedAuthentication> config) {
        String[] requestParamCheckKeys = config.getRequestParamCheckKeys();
        for (String key : ListUtil.optimize(requestParamCheckKeys)) {
            String value = request.getHeader(key);
            if (StringUtil.hasText(value)) {
                ReflectionUtil.setFieldValue(authentication, key, value);
            }
        }
        config.initForm(authentication);

        if (!authentication.checkout()) {
            throw new RuntimeException("鉴权失败");
        }
    }

    private static void bodyRequestedSignCheck(HttpServletRequest request, RequestedAuthentication authentication, RequestedConfiguration<RequestedAuthentication> config) {
        String[] requestParamCheckKeys = config.getRequestParamCheckKeys();
        for (String key : ListUtil.optimize(requestParamCheckKeys)) {
            String[] value = request.getParameterValues(key);
            if (value != null && value.length > 0) {
                ReflectionUtil.setFieldValue(authentication, key, value[0]);
            }
        }
        config.initForm(authentication);

        if (!authentication.checkout()) {
            throw new RuntimeException("鉴权失败");
        }
    }

    /**
     * 找出鉴权参数
     */
    private RequestedAuthentication getParamAuthentication(HandlerMethod handlerMethod) throws Exception {
        MethodParameter[] methodParameters = handlerMethod.getMethodParameters();
        for (MethodParameter methodParameter : ListUtil.optimize(methodParameters)) {
            Type parameterizedType = methodParameter.getParameter().getParameterizedType();
            Class<?> param = Class.forName(parameterizedType.getTypeName());

            if (RequestedAuthentication.class.isAssignableFrom(param)) {
                return (RequestedAuthentication) param.getDeclaredConstructor().newInstance();
            }
        }
        return null;
    }

}