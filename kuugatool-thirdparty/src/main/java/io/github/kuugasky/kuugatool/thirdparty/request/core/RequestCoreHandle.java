package io.github.kuugasky.kuugatool.thirdparty.request.core;

import io.github.kuugasky.kuugatool.aop.AnnotationUtil;
import io.github.kuugasky.kuugatool.thirdparty.request.context.annotations.RequestEntity;
import io.github.kuugasky.kuugatool.thirdparty.request.context.authentication.RequestAuthentication;
import io.github.kuugasky.kuugatool.thirdparty.request.context.authentication.RequestForm;
import io.github.kuugasky.kuugatool.thirdparty.request.context.config.RequestConfig;
import io.github.kuugasky.kuugatool.thirdparty.request.core.delegate.RequestDelegateExecutor;
import io.github.kuugasky.kuugatool.thirdparty.request.core.factory.RequestAuthenticationFactory;
import io.github.kuugasky.kuugatool.thirdparty.request.core.factory.RequestResultHandleParse;

/**
 * 第三方请求核心处理类
 *
 * @author pengqinglong
 * @since 2022/3/15
 */
public class RequestCoreHandle {

    /**
     * 处理方法
     *
     * @param form  请求参数form
     * @param clazz 返回对象Class
     * @param <T>   返回泛形
     */
    public static <T> T request(RequestForm form, Class<T> clazz) {
        // 从第三方鉴权工厂提取符合该请求form的配置信息
        RequestAuthentication<RequestConfig, RequestForm> authentication = RequestAuthenticationFactory.getAuthentication(form);

        RequestConfig config = authentication.getConfig();

        // 鉴权器对form进行鉴权封装，封装每次请求中需要附带的鉴权信息
        form = authentication.initForm(form);

        // 获取url与path拼接出接口请求地址
        RequestEntity annotation = AnnotationUtil.getAnnotation(form.getClass(), RequestEntity.class);
        String url = config.getUrl() + annotation.path();

        // 使用请求代理器 请求
        String result = RequestDelegateExecutor.execute(url, form);

        // 请求返回 通过结果解析器对结果进行解析
        return RequestResultHandleParse.parse(result, form, clazz);
    }

}