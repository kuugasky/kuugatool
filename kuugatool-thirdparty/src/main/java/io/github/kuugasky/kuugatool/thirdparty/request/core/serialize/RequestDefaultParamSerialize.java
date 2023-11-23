package io.github.kuugasky.kuugatool.thirdparty.request.core.serialize;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.collection.MapUtil;
import io.github.kuugasky.kuugatool.http.NameValuePairUtil;
import io.github.kuugasky.kuugatool.thirdparty.request.context.authentication.RequestForm;
import io.github.kuugasky.kuugatool.thirdparty.request.context.exception.ThirdpartyRequestException;
import org.apache.http.NameValuePair;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * ParamSerialize
 *
 * @author pengqinglong
 * @since 2022/3/24
 */
public class RequestDefaultParamSerialize<T extends RequestForm> implements RequestSerialize<T, List<NameValuePair>> {

    @Override
    public List<NameValuePair> serialize(T t) {

        Map<String, Object> map = MapUtil.newHashMap();

        // 获取子类与父类的字段
        List<Field> list = ListUtil.newArrayList();
        list.addAll(Arrays.asList(t.getClass().getDeclaredFields()));
        list.addAll(Arrays.asList(t.getClass().getSuperclass().getDeclaredFields()));

        for (Field field : list) {
            field.setAccessible(true);

            try {
                map.put(field.getName(), field.get(t));
            } catch (IllegalAccessException e) {
                throw new ThirdpartyRequestException("字段异常");
            }
        }

        return NameValuePairUtil.toList(map);
    }

}