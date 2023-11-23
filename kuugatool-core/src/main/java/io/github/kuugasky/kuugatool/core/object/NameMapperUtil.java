package io.github.kuugasky.kuugatool.core.object;

import io.github.kuugasky.kuugatool.core.object.bean.BatchNameMapper;

/**
 * 名称映射工具类
 *
 * @author kuuga
 * @since 2021/4/1
 */
public class NameMapperUtil {

    /**
     * 定义私有构造函数来屏蔽这个隐式公有构造函数
     */
    private NameMapperUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static <T, R> BatchNameMapper<T, R> instance() {
        return new BatchNameMapper<>();
    }

    // DEMO:
    // NameMapperUtil.<PermissionVo, EsGardenVo>instance().batchMapper(items, new String[]{"gardenId", "id"},
    //         (sourceIds) -> dictGardenRemote.selectGardenByIds(new IdsExtendForm(sourceIds)),
    //         (permissionVo, esGardenVo) -> {
    //     permissionVo.setGardenName(esGardenVo.getName());
    //     permissionVo.setBusinessAreaNames(esGardenVo.getBusinessAreaNames());
    // });

}
