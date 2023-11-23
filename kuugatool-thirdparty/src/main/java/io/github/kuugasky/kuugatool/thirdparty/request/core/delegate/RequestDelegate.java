package io.github.kuugasky.kuugatool.thirdparty.request.core.delegate;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.constants.KuugaConstants;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import io.github.kuugasky.kuugatool.thirdparty.basic.enums.RequestAuthenticationMode;
import io.github.kuugasky.kuugatool.thirdparty.basic.enums.RequestMode;
import io.github.kuugasky.kuugatool.thirdparty.request.context.annotations.RequestAuthParam;
import io.github.kuugasky.kuugatool.thirdparty.request.context.annotations.RequestEntity;
import io.github.kuugasky.kuugatool.thirdparty.request.context.authentication.RequestForm;
import io.github.kuugasky.kuugatool.thirdparty.request.context.exception.ThirdpartyRequestException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 请求委托器
 *
 * @author pengqinglong
 * @since 2022 /3/15
 */
public interface RequestDelegate {

    String CONTENT_TYPE = "Content-Type";

    /**
     * Post string.
     *
     * @param url  the url
     * @param form the form
     * @return the string
     */
    String post(String url, RequestForm form);

    /**
     * 创建CloseableHttpClient
     *
     * @return CloseableHttpClient
     */
    default CloseableHttpClient createDefault() {
        return HttpClients.createDefault();
    }

    /**
     * Get string.
     *
     * @param url  the url
     * @param form the form
     * @return the string
     */
    default String get(String url, RequestForm form) {

        // 封装请求参数
        String param = ObjectUtil.toUrlParams(form);

        // 创建request
        HttpGet httpGet = (HttpGet) this.buildHttpRequest(url + "?" + param, form);

        try {
            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                CloseableHttpResponse response = httpClient.execute(httpGet);
                byte[] bytes = EntityUtils.toByteArray(response.getEntity());
                return new String(bytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Build http request http request base.
     *
     * @param url  the url
     * @param form the form
     * @return the http request base
     */
    default HttpRequestBase buildHttpRequest(String url, RequestForm form) {

        Class<? extends RequestForm> clazz = form.getClass();
        RequestEntity annotation = clazz.getAnnotation(RequestEntity.class);

        try {
            if (url.contains(KuugaConstants.SPACE)) {
                url = url.replaceAll(KuugaConstants.SPACE, "%20");
            }
            URIBuilder builder = new URIBuilder(url);
            HttpRequestBase httpRequest;
            if (annotation.mode() == RequestMode.GET) {
                httpRequest = new HttpGet(builder.build());
            } else {
                httpRequest = new HttpPost(builder.build());
            }

            // 设置请求超时时间
            int timeout = annotation.timeout();
            httpRequest.setConfig(RequestConfig.custom()
                    .setConnectionRequestTimeout(timeout)
                    .setConnectTimeout(timeout)
                    .setSocketTimeout(timeout)
                    .build());

            // body跟随序列化直接进入body了 不用处理
            RequestAuthenticationMode authenticationMode = annotation.authMode();
            if (authenticationMode == RequestAuthenticationMode.BODY) {
                return httpRequest;
            }

            // 获取自己+父类的字段
            List<Field> fieldList = ListUtil.newArrayList(clazz.getDeclaredFields());
            fieldList.addAll(Arrays.asList(clazz.getSuperclass().getDeclaredFields()));

            // 找出鉴权参数
            fieldList = fieldList.stream()
                    .filter(f -> f.isAnnotationPresent(RequestAuthParam.class))
                    .collect(Collectors.toList());

            // 没有返回
            if (ListUtil.isEmpty(fieldList)) {
                return httpRequest;
            }

            // 封装请求头
            this.packHeader(form, httpRequest, fieldList);

            return httpRequest;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ThirdpartyRequestException(e.fillInStackTrace().toString() + Arrays.toString(e.getStackTrace()));
        }
    }

    /**
     * page header info.
     *
     * @param form        the form
     * @param httpRequest the httpRequest
     * @param fieldList   the fieldList
     */
    private void packHeader(RequestForm form, HttpRequestBase httpRequest, List<Field> fieldList) {
        // 存在鉴权参数 开始封装
        for (Field field : fieldList) {
            field.setAccessible(true);
            // 获取鉴权字段名
            RequestAuthParam authParam = field.getAnnotation(RequestAuthParam.class);
            String key = StringUtil.hasText(authParam.value()) ? authParam.value() : field.getName();
            try {
                // 为空校验
                Object o = field.get(form);
                if (o == null) {
                    throw new ThirdpartyRequestException(String.format("entity:[%s] 鉴权参数:[%s]为空", form.getClass().getName(), key));
                }

                httpRequest.setHeader(key, o.toString());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

}