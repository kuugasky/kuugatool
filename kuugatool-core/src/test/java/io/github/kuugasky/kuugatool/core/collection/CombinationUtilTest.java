package io.github.kuugasky.kuugatool.core.collection;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

class CombinationUtilTest {

    @Test
    void allCombinationsOfTwoElements() {
        // 两种元素的所有组合方式
        List<Map<String, String>> pairs = CombinationUtil.allCombinationsOfTwoElements(Lists.newArrayList("1", "2", "3", "4"));
        System.out.println(pairs);
    }

    @Test
    void combinationsOfTwoElements() {
        // 两种元素的组合方式
        List<Map<String, String>> pairs1 = CombinationUtil.combinationsOfTwoElements(Lists.newArrayList("1", "2", "3", "4"));
        System.out.println(pairs1);
    }

}