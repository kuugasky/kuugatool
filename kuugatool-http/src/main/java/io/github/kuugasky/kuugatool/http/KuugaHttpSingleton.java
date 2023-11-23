package io.github.kuugasky.kuugatool.http;

import io.github.kuugasky.kuugatool.http.core.KuugaHttpInterface;
import org.apache.http.NameValuePair;

import java.util.List;
import java.util.Map;

/**
 * HTTP客户端,实现最基本的GET,POST请求,返回值类型均为JSON字符串
 *
 * @author 卢俊生
 */
public final class KuugaHttpSingleton implements KuugaHttpInterface {

    private static KuugaHttpCustom kuugaHttp = null;

    public static KuugaHttpCustom init() {
        kuugaHttp = KuugaHttpSingletonEnum.INSTANCE.getSingleton();
        return kuugaHttp;
    }

    /**
     * 枚举实现单例
     */
    private enum KuugaHttpSingletonEnum {
        /**
         * 线程池单例枚举
         */
        INSTANCE;

        KuugaHttpSingletonEnum() {
            kuugaHttp = KuugaHttpCustom.init();
        }

        public KuugaHttpCustom getSingleton() {
            return kuugaHttp;
        }
    }

    @Override
    public KuugaHttpCustom.HttpResult get(String url) {
        return kuugaHttp.get(url);
    }

    @Override
    public KuugaHttpCustom.HttpResult get(String uri, List<NameValuePair> params) {
        return kuugaHttp.get(uri, params);
    }

    @Override
    public KuugaHttpCustom.HttpResult get(String uri, NameValuePair... params) {
        return kuugaHttp.get(uri, params);
    }

    @Override
    public KuugaHttpCustom.HttpResult get(String uri, Map<String, Object> params) {
        return kuugaHttp.get(uri, params);
    }

    @Override
    public KuugaHttpCustom.HttpResult post(String url) {
        return kuugaHttp.post(url);
    }

    @Override
    public KuugaHttpCustom.HttpResult post(String uri, List<NameValuePair> params) {
        return kuugaHttp.post(uri, params);
    }

    @Override
    public KuugaHttpCustom.HttpResult post(String uri, NameValuePair... params) {
        return kuugaHttp.post(uri, params);
    }

    @Override
    public KuugaHttpCustom.HttpResult post(String uri, Map<String, Object> params) {
        return kuugaHttp.post(uri, params);
    }

    @Override
    public KuugaHttpCustom.HttpResult post(String uri, String json) {
        return kuugaHttp.post(uri, json);
    }

}
