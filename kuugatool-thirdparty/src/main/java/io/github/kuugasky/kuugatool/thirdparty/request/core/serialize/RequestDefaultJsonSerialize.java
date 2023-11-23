package io.github.kuugasky.kuugatool.thirdparty.request.core.serialize;

import io.github.kuugasky.kuugatool.json.JsonUtil;
import io.github.kuugasky.kuugatool.thirdparty.request.context.authentication.RequestForm;

/**
 * JsonSerialize
 *
 * @author pengqinglong
 * @since 2022/3/24
 */
public class RequestDefaultJsonSerialize<T extends RequestForm> implements RequestSerialize<T, String> {

    @Override
    public String serialize(T t) {
        return JsonUtil.toJsonString(t);
    }

}