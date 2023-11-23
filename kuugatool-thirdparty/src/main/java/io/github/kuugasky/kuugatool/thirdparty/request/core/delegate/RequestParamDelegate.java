package io.github.kuugasky.kuugatool.thirdparty.request.core.delegate;

import io.github.kuugasky.kuugatool.thirdparty.request.context.authentication.RequestForm;
import io.github.kuugasky.kuugatool.thirdparty.request.core.factory.RequestSerializeHandle;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 表单参数方式请求（暂未支持）
 *
 * @author pengqinglong
 * @since 2022/3/15
 */
public class RequestParamDelegate implements RequestDelegate {

    @Override
    public String post(String url, RequestForm form) {
        // 创建request
        HttpPost httpPost = (HttpPost) this.buildHttpRequest(url, form);
        httpPost.setHeader(CONTENT_TYPE, ContentType.APPLICATION_FORM_URLENCODED.toString());

        // 处理参数
        List<NameValuePair> param = RequestSerializeHandle.serialize(form);
        httpPost.setEntity(new UrlEncodedFormEntity(param, StandardCharsets.UTF_8));

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