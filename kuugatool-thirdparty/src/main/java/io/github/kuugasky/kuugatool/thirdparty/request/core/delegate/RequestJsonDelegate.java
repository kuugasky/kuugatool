package io.github.kuugasky.kuugatool.thirdparty.request.core.delegate;

import io.github.kuugasky.kuugatool.thirdparty.request.context.authentication.RequestForm;
import io.github.kuugasky.kuugatool.thirdparty.request.core.factory.RequestSerializeHandle;
import org.apache.http.Consts;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * json序列化参数进行请求
 *
 * @author pengqinglong
 * @since 2022/3/15
 */
public class RequestJsonDelegate implements RequestDelegate {

    @Override
    public String post(String url, RequestForm form) {
        // 创建request
        HttpPost httpPost = (HttpPost) this.buildHttpRequest(url, form);
        httpPost.setHeader(CONTENT_TYPE, ContentType.APPLICATION_JSON.toString());

        // 封装请求参数
        String json = RequestSerializeHandle.serialize(form);
        httpPost.setEntity(new StringEntity(json, Consts.UTF_8));

        try (CloseableHttpClient httpClient = createDefault()) {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            byte[] bytes = EntityUtils.toByteArray(response.getEntity());
            return new String(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}