package io.github.kuugasky.kuugatool.extra.weight;

import java.util.List;

/**
 * WeightValueUtil
 * <p>
 * 小量数据权重记分排序工具
 *
 * @author kuuga
 * @since 2023/3/1-03-01 11:16
 */
public class WeightValueUtil {

    public static <T> List<WeightValueWrapper<T>> calculation(List<T> objects, List<WeightValueAction<T>> weightCalculationRules) {
        return WeightValueCore.init(objects)
                .addRules(weightCalculationRules)
                .calculation();
    }

}
