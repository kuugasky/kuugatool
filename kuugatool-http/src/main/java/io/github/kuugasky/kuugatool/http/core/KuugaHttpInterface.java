package io.github.kuugasky.kuugatool.http.core;

import io.github.kuugasky.kuugatool.http.KuugaHttp;
import org.apache.http.NameValuePair;

import java.util.List;
import java.util.Map;

/**
 * Http客户端接口
 *
 * @author kuuga
 */
public interface KuugaHttpInterface {

    /**
     * 发起HTTP GET请求(UTF-8)
     *
     * @param url URL
     * @return 响应结果
     */
    KuugaHttp.HttpResult get(String url);

    /**
     * 发起HTTP GET请求(@param=charset)
     *
     * @param uri    URI
     * @param params 名称值对集合请求参数
     * @return 响应结果
     */
    KuugaHttp.HttpResult get(String uri, List<NameValuePair> params);

    /**
     * 发起HTTP GET请求(@param=charset)
     *
     * @param uri    URI
     * @param params 名称值对数组请求参数
     * @return 响应结果
     */
    KuugaHttp.HttpResult get(String uri, NameValuePair... params);

    /**
     * 发起HTTP GET请求(UTF-8)
     *
     * @param uri    URI
     * @param params Map请求参数
     * @return 响应结果
     */
    KuugaHttp.HttpResult get(String uri, Map<String, Object> params);

    /**
     * 发起HTTP POST请求(@param=charset)
     *
     * @param url URL
     * @return 响应结果
     */
    KuugaHttp.HttpResult post(String url);

    /**
     * 发起HTTP POST请求(UTF-8)
     *
     * @param uri    URI
     * @param params 名称值对集合请求参数
     * @return 响应结果
     */
    KuugaHttp.HttpResult post(String uri, List<NameValuePair> params);

    /**
     * 发起HTTP POST请求(UTF-8)
     *
     * @param uri    URI
     * @param params 名称值对数组请求参数
     * @return 响应结果
     */
    KuugaHttp.HttpResult post(String uri, NameValuePair... params);

    /**
     * 发起HTTP POST请求(UTF-8)
     *
     * @param uri    URI
     * @param params Map请求参数
     * @return 响应结果
     */
    KuugaHttp.HttpResult post(String uri, Map<String, Object> params);

    /**
     * 发起HTTP POST请求(UTF-8)
     *
     * @param uri  URI
     * @param json JSON请求参数
     * @return 响应结果
     */
    KuugaHttp.HttpResult post(String uri, String json);

}