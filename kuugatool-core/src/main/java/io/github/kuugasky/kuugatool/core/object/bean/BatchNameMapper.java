package io.github.kuugasky.kuugatool.core.object.bean;

import io.github.kuugasky.kuugatool.core.collection.ArrayUtil;
import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.collection.MapUtil;
import io.github.kuugasky.kuugatool.core.collection.SetUtil;
import io.github.kuugasky.kuugatool.core.constants.KuugaConstants;
import io.github.kuugasky.kuugatool.core.function.namemapper.FieldMapperFunc;
import io.github.kuugasky.kuugatool.core.function.namemapper.QueryTargetListFunc;
import io.github.kuugasky.kuugatool.core.instance.ReflectionUtil;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 批量名称映射器
 *
 * @author kuuga
 * @since 2021/4/1
 */
public final class BatchNameMapper<T, R> {

    /**
     * 批量映射
     *
     * @param sourceList          源数据集合
     * @param idFields            源数据和目标数据的ID映射【sourceId:targetId】
     * @param queryTargetListFunc 查询目标数据集合函数
     * @param trFieldMapperFunc   字段映射函数
     */
    public void batchMapper(List<T> sourceList, String[] idFields, QueryTargetListFunc<R> queryTargetListFunc, FieldMapperFunc<T, R> trFieldMapperFunc) {
        if (ArrayUtil.isEmpty(idFields) || idFields.length != KuugaConstants.TWO) {
            return;
        }
        if (ListUtil.isEmpty(sourceList)) {
            return;
        }

        String sourceIdField = idFields[0];
        String targetIdField = idFields[1];

        Set<String> sourceIdsSet = SetUtil.newHashSet();
        sourceList.forEach(source -> sourceIdsSet.add(String.valueOf(ReflectionUtil.getFieldValue(source, sourceIdField))));
        List<R> targetList = queryTargetListFunc.execute(ListUtil.newArrayList(sourceIdsSet));
        if (ListUtil.isEmpty(targetList)) {
            return;
        }

        Map<String, R> targetIdNameMap = MapUtil.newHashMap();
        targetList.forEach(target -> {
            String targetId = String.valueOf(ReflectionUtil.getFieldValueByRecursive(target, targetIdField));
            targetIdNameMap.put(targetId, target);
        });

        sourceList.forEach(source -> {
            if (ObjectUtil.isNull(source)) {
                return;
            }
            String sourceId = String.valueOf(ReflectionUtil.getFieldValue(source, sourceIdField));
            R target = targetIdNameMap.get(sourceId);

            if (ObjectUtil.nonNull(source, target)) {
                trFieldMapperFunc.execute(source, target);
            }
        });
    }

}
