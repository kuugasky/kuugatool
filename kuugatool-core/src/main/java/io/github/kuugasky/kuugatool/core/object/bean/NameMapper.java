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
public final class NameMapper<T, R, E> {

    /**
     * 批量名称映射
     *
     * @param sourceList 源数据集合
     * @param <T>        源数据泛型
     * @param <R>        目标数据泛型(如根据ID查询到的名称目标数据集合)
     * @param <E>        ID泛型
     * @return 批量名称映射结果
     */
    public static <T, R, E> NameMapper<T, R, E> instance(List<T> sourceList) {
        return new NameMapper<>(sourceList);
    }

    public static <T, R, E> NameMapper<T, R, E> instance(T source) {
        return new NameMapper<>(ListUtil.newArrayList(source));
    }

    /**
     * 源数据集合
     */
    private final List<T> sourceList;

    /**
     * 构造方法
     *
     * @param sourceList 源数据集合
     */
    private NameMapper(List<T> sourceList) {
        this.sourceList = sourceList;
    }

    /**
     * 批量映射
     *
     * @param idFields            源数据和目标数据的ID映射【sourceId:targetId】
     * @param queryTargetListFunc 查询目标数据集合函数，根据idFields
     * @param trFieldMapperFunc   字段映射函数
     */
    public void execute(String[] idFields, QueryTargetListFunc<R, E> queryTargetListFunc, FieldMapperFunc<T, R> trFieldMapperFunc) {
        // idFields必须为两位，需包含sourceId和targetId
        if (ArrayUtil.isEmpty(idFields) || idFields.length != KuugaConstants.TWO) {
            return;
        }
        if (ListUtil.isEmpty(sourceList)) {
            return;
        }

        // 源数据对象ID
        String sourceIdField = idFields[0];
        // 目标对象ID（如名称对象ID）
        String targetIdField = idFields[1];

        Set<E> sourceIdsSet = SetUtil.newHashSet();
        sourceList.forEach(source -> {
            // 从源数据对象中提取ID值放入sourceIds，用于根据IDS去查询目标数据集合
            Object fieldValue = ReflectionUtil.getFieldValue(source, sourceIdField);
            sourceIdsSet.add(ObjectUtil.cast(fieldValue));
        });
        // 根据sourceIds的值去查询目标数据集合
        List<R> targetList = queryTargetListFunc.execute(ListUtil.newArrayList(sourceIdsSet));
        if (ListUtil.isEmpty(targetList)) {
            return;
        }

        Map<E, R> targetIdNameMap = MapUtil.newHashMap();
        targetList.forEach(target -> {
            // 遍历查询出来的目标数据集合，将目标数据ID和目标数据对象放入targetIdNameMap
            E targetId = ObjectUtil.cast(ReflectionUtil.getFieldValueByRecursive(target, targetIdField));
            targetIdNameMap.put(targetId, target);
        });

        // 遍历源数据集合，根据sourceId去targetIdNameMap中查询目标数据对象，将目标数据对象赋值给源数据对象
        sourceList.forEach(source -> {
            if (ObjectUtil.isNull(source)) {
                return;
            }
            // 从源数据对象中提取ID值
            E sourceId = ObjectUtil.cast(ReflectionUtil.getFieldValue(source, sourceIdField));
            // 根据sourceId去targetIdNameMap中查询目标数据对象
            R target = targetIdNameMap.get(sourceId);

            if (ObjectUtil.nonNull(source, target)) {
                // 执行字段映射，将目标数据对象赋值给源数据对象
                trFieldMapperFunc.execute(source, target);
            }
        });
    }

    // DEMO:
    // NameMapperUtil.<PermissionVo, EsGardenVo>instance().batchMapper(items, new String[]{"gardenId", "id"},
    //         (sourceIds) -> dictGardenRemote.selectGardenByIds(new IdsExtendForm(sourceIds)),
    //         (permissionVo, esGardenVo) -> {
    //     permissionVo.setGardenName(esGardenVo.getName());
    //     permissionVo.setBusinessAreaNames(esGardenVo.getBusinessAreaNames());
    // });

}
