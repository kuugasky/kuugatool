package io.github.kuugasky.kuugatool.core.object;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.collection.SetUtil;
import io.github.kuugasky.kuugatool.core.optional.KuugaOptional;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 对象拷贝
 *
 * @author kuuga
 */
public final class MapperUtil {

    /**
     * 定义私有构造函数来屏蔽这个隐式公有构造函数
     */
    private MapperUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    private static final Mapper MAPPER = DozerBeanMapperBuilder.buildDefault();

    public static <T, R> void copy(T source, R target) {
        if (KuugaOptional.ofNullable(source).isEmpty()) {
            return;
        }
        MAPPER.map(source, target);
    }

    public static <T, R> R copy(T source, Class<R> clazz) {
        Objects.requireNonNull(source);
        return MAPPER.map(source, clazz);
    }

    public static <T, R> List<R> copy(List<T> sourceList, Class<R> clazz) {
        if (ListUtil.isEmpty(sourceList)) {
            return ListUtil.emptyList();
        }
        List<R> targetList = ListUtil.newArrayList();
        sourceList.forEach(sourceObject -> {
            R targetObject = MAPPER.map(sourceObject, clazz);
            targetList.add(targetObject);
        });
        return targetList;
    }

    public static <T, R> Set<R> copy(Set<T> sourceSet, Class<R> clazz) {
        if (SetUtil.isEmpty(sourceSet)) {
            return SetUtil.emptySet();
        }
        Set<R> targetSet = SetUtil.newHashSet();
        sourceSet.forEach(sourceObject -> {
            R targetObject = MAPPER.map(sourceObject, clazz);
            targetSet.add(targetObject);
        });
        return targetSet;
    }

}
