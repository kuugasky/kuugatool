package io.github.kuugasky.kuugatool.thirdparty.request.core.result;

import io.github.kuugasky.kuugatool.json.JsonUtil;
import io.github.kuugasky.kuugatool.thirdparty.request.context.authentication.RequestForm;

/**
 * 默认的json结果处理器
 *
 * @author pengqinglong
 * @since 2022/3/15
 */
public class RequestDefaultJsonResultHandle implements RequestResultHandle<RequestForm> {

    @Override
    public <K> K handle(String result, Class<K> resultClass) {
        return JsonUtil.toJavaObject(result, resultClass);
    }

}