# HttpServletRequest增强类

> 在Controller接到请求参数前，假如在拦截器或者过滤器中读取出request中post请求的参数，则会导致controller方法读取不到请求参数而报错

- 拦截器中读取，即便采用request增强包装类依旧无法在controller中读取参数
- 过滤器中读取，采用BodyReaderHttpServletRequestWrapper类进行封装，再将request的增强类放入filterChain中

```java
// BodyReaderHttpServletRequestWrapper wrapperRequest = new BodyReaderHttpServletRequestWrapper(request);
// initSleuthInfo(wrapperRequest);
// filterChain.doFilter(wrapperRequest, response);
```

# web层使用方式

> 定义一个类

```java
package com.kfang.web.agent.utils;

import object.io.github.kuugasky.kuugatool.core.ObjectUtil;
import web.io.github.kuugasky.kuugatool.extra.IpAddressUtil;
import kfang.infra.common.model.LoginDto;
import kfang.infra.web.constants.WebCommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import jakarta.servlet.http.HttpServletRequest;

import static com.kfang.common.agent.utils.OperatorInfoUtil.*;

/**
 * Web端全局拦截器工具类
 *
 * @author kuuga
 */
@Slf4j
public class WebSleuthLogUtil {

    public static void initSleuthInfo(HttpServletRequest request) {
        LoginDto loginDto = ObjectUtil.cast(request.getSession().getAttribute(WebCommonConstants.CURRENT_LOGIN_PERSON));
        if (ObjectUtil.nonNull(loginDto)) {
            MDC.put(OPERATOR_ID, loginDto.getId());
            MDC.put(OPERATOR_USERNAME, loginDto.getName());
            MDC.put(OPERATOR_ORG_ID, loginDto.getOrgId());
            MDC.put(OPERATOR_ORG_NAME, loginDto.getOrgName());
            MDC.put(OPERATOR_ORG_POSITION_ID, loginDto.getPositionId());
            MDC.put(OPERATOR_ORG_POSITION_NAME, loginDto.getPositionName());
            MDC.put(OPERATOR_COMPANY_ORG_ID, loginDto.getCompanyOrgId());
            MDC.put(OPERATOR_COMPANY_ORG_NAME, loginDto.getCompanyOrgName());
            MDC.put(OPERATOR_IS_MAJOR, String.valueOf(loginDto.IsMajor()));
            MDC.put(OPERATOR_IP, IpAddressUtil.getIpAddr(request)[0]);
            MDC.put(OPERATOR_TERMINAL_TYPE, loginDto.getTerminalType());
        }
    }

}

```

> 全局拦截器嵌入

```java
package com.kfang.web.agent.business.pc.interceptor;

import com.kfang.web.agent.utils.WebSleuthLogUtil;
import kfang.infra.common.ContextHolder;
import kfang.infra.web.interceptor.ZBaseInterceptorAdapter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 上下文拦截器，用于往上下文中放入必要的信息
 *
 * @author kuuga
 */
@Slf4j
public class ContextInterceptor extends ZBaseInterceptorAdapter {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        try {
            ContextHolder.setDataSource(DataSource.WEB_KUUGA_BUSINESS_PC);

            WebSleuthLogUtil.initSleuthInfo(request);
            return super.preHandle(request, response, handler);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
    }

    public enum DataSource implements ContextHolder.DataSourceContextKey {
        /**
         *
         */
        WEB_KUUGA_BUSINESS_PC
    }

}
```

# service层使用方式

> 创建一个过滤器注入

```java
package com.kfang.service.agent.filter;

import io.github.kuugasky.kuugatool.bean.servlet.BodyReaderHttpServletRequestWrapper;
import object.io.github.kuugasky.kuugatool.core.ObjectUtil;
import string.io.github.kuugasky.kuugatool.core.StringUtil;
import web.io.github.kuugasky.kuugatool.extra.CommonWebUtil;
import web.io.github.kuugasky.kuugatool.extra.IpAddressUtil;
import io.github.kuugasky.kuugatool.json.JsonUtil;
import com.alibaba.fastjson2.JSONObject;
import com.kfang.common.agent.constants.SysConstants;
import kfang.infra.common.model.LoginDto;
import org.slf4j.MDC;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.kfang.common.agent.utils.OperatorInfoUtil.*;

/**
 * ServiceRequestLogFilter
 *
 * @author kuuga
 * @since 2021/10/28
 */
@Component
public class ServiceSleuthLogFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        BodyReaderHttpServletRequestWrapper wrapperRequest = new BodyReaderHttpServletRequestWrapper(request);
        initSleuthInfo(wrapperRequest);
        filterChain.doFilter(wrapperRequest, response);
    }

    public static void initSleuthInfo(BodyReaderHttpServletRequestWrapper wrapperRequest) {
        if (ObjectUtil.isNull(wrapperRequest)) {
            return;
        }

        String jsonParams = CommonWebUtil.getJsonParams(wrapperRequest);
        if (StringUtil.isEmpty(jsonParams)) {
            return;
        }
        JSONObject jsonObject = JsonUtil.parseObject(jsonParams);
        String loginExtendDtoStr = jsonObject.getString(SysConstants.LOGIN_EXTEND_DTO);
        if (StringUtil.isEmpty(loginExtendDtoStr)) {
            return;
        }

        LoginDto loginDto = JsonUtil.toJavaObject(JsonUtil.toJsonString(loginExtendDtoStr), LoginDto.class);

        MDC.put(OPERATOR_ID, loginDto.getId());
        MDC.put(OPERATOR_USERNAME, loginDto.getName());
        MDC.put(OPERATOR_ORG_ID, loginDto.getOrgId());
        MDC.put(OPERATOR_ORG_NAME, loginDto.getOrgName());
        MDC.put(OPERATOR_ORG_POSITION_ID, loginDto.getPositionId());
        MDC.put(OPERATOR_ORG_POSITION_NAME, loginDto.getPositionName());
        MDC.put(OPERATOR_COMPANY_ORG_ID, loginDto.getCompanyOrgId());
        MDC.put(OPERATOR_COMPANY_ORG_NAME, loginDto.getCompanyOrgName());
        MDC.put(OPERATOR_IS_MAJOR, String.valueOf(loginDto.IsMajor()));
        MDC.put(OPERATOR_IP, IpAddressUtil.getIpAddr(wrapperRequest)[0]);
        MDC.put(OPERATOR_TERMINAL_TYPE, loginDto.getTerminalType());
    }

}
```

# logback.xml

```xml

<pattern>#AGENT# [%d{yyyy-MM-dd HH:mm:ss.SSS}][%thread] %highlight(%-5level) %cyan(%logger{50}-%line) %green(%X{X-B3-TraceId:-}-%X{X-B3-SpanId:-}) %red(%X{operator-ip})#%red(%X{operator-terminal-type})#%red(%X{operator-id})#%red(%X{operator-org-id})#%red(%X{operator-org-position-id}) %msg #e_logger#%n</pattern>
```

# 程序中打印操作人信息

```java
// OperatorInfoUtil.logOperatorInfo();
```
