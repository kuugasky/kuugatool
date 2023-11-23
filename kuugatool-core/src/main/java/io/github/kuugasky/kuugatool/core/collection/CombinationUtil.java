package io.github.kuugasky.kuugatool.core.collection;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * 组合工具类
 *
 * @author kuuga
 * @since 2022-02-22 18:08:57
 */
public final class CombinationUtil {

    public static final int TWO = 2;

    /**
     * 两种元素的所有组合方式
     *
     * <p>
     * eg:1、2、3、4
     * <p>
     * [{1=2}, {1=3}, {1=4}, {2=3}, {2=4}, {3=4}]
     *
     * @param list list
     * @return list.maps
     */
    public static List<Map<String, String>> allCombinationsOfTwoElements(List<String> list) {
        if (ListUtil.isEmpty(list)) {
            return Lists.newArrayList();
        }

        List<Map<String, String>> resultList = Lists.newArrayListWithExpectedSize(list.size() * list.size() / 2);

        if (list.size() >= TWO) {
            for (int j = 0; j < list.size(); j++) {
                resultList.addAll(combinationsOfTwoElements(list.subList(j, list.size())));
            }
        }
        return resultList;
    }

    /**
     * 两种元素的组合方式
     * <p>
     * eg:1、2、3、4
     * <p>
     * [{1=2}, {1=3}, {1=4}]
     *
     * @param list list
     * @return list.maps
     */
    public static List<Map<String, String>> combinationsOfTwoElements(List<String> list) {
        if (ListUtil.isEmpty(list)) {
            return Lists.newArrayList();
        }

        List<Map<String, String>> resultList = Lists.newArrayListWithExpectedSize(list.size() * list.size() / 2);

        for (int j = 1; j < list.size(); j++) {
            Map<String, String> objectObjectHashMap = Maps.newHashMap();
            objectObjectHashMap.put(list.get(0), list.get(j));
            resultList.add(objectObjectHashMap);
        }
        return resultList;
    }

}
