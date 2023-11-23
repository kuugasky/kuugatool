# kuugatool-thirdparty使用说明

## request

> 调用第三方API鉴权委托

### context 上下文模块包

- annotations 注解包
    - RequestAuthParam：第三方请求身份验证参数注解（鉴权顶级form的鉴权字段注入该注解，如：RequestSzHsBaseForm的authorization）
    - RequestEntity：第三方请求实体注解（鉴权具体请求接口form注入该注解，如：RequestSzHsRoomNumberCheckAuthForm）
- authentication 鉴权包
    - RequestAuthentication：第三方请求鉴权器接口（继承该接口实现自定义鉴权逻辑，如：RequestSzHsAuthentication）
    - RequestForm：第三方请求form顶层接口（鉴权顶级form实现该接口，如：RequestSzHsBaseForm）
- config 配置包
    - RequestConfig：第三方配置类（第三方配置相关信息读取，如：RequestSzHsConfig）
- exception 异常包
    - ThirdpartyRequestException：第三方模块请求异常类

#### 使用步骤

- 自定义鉴权类，并实现ThirdpartyForm接口，且鉴权字段加上注解@AuthParam
- 自定义请求类，并加上注解@RequestEntity，且继承自定义鉴权类
- 自定义配置类，并实现ThirdpartyConfig接口

### core 核心包

- delegate 代理包
    - RequestJsonDelegate：JSON POST请求委托器
    - RequestParamDelegate：PARAM POST请求委托器
    - RequestDelegate：请求委托器，默认实现GET请求
    - RequestDelegateExecutor：请求委托执行器
- factory 工厂包
    - ResultHandleParse：第三方结果解析工厂
    - RequestSerializeHandle：第三方请求序列化工厂
    - RequestAuthenticationFactory：第三方请求鉴权工厂
- result 结果解析包
    - RequestDefaultJsonResultHandle：默认的JSON结果处理器
    - RequestResultHandle：第三方结果处理器接口
- serialize 序列化包
    - RequestDefaultJsonSerialize：默认JSON序列化器
    - RequestDefaultParamSerialize：默认PARAM序列化器
    - RequestSerialize：第三方序列化接口
- RequestCoreHandle：核心委托类

#### 使用步骤

- 自定义结果解析类，实现RequestResultHandle接口（如：SzHsResultHandle）

__结合context和core的自定义类，即可使用该鉴权组件__

## requested

> requested鉴权方式分三种：JSON、BODY、HEADER

- 注入拦截器：`registry.addInterceptor(new RequestedInterceptor()).addPathPatterns("/requested/**");`，实现BODY方式鉴权
- 实现AOP切面`RequestedAspect`，实现JSON方式鉴权
- 定义配置类：__注意不同方式的请求需要定义不同的配置类，才能都读取进配置工厂__
  （如：RequestedBodyConfig和RequestedJsonConfig，需要实现RequestedConfiguration接口）
-

定义请求FORM：同样需要区分方式定义不同的请求FORM（如：RequestedKuugaBodyAuthBaseForm和RequestedKuugaJsonAuthBaseForm，需要实现RequestedAuthentication接口）