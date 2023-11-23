package io.github.kuugasky.kuugatool.core.object;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.entity.KuugaModel;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * list对象排序
 */
public class CollectionsSortUtilTest {

    @Test
    public void sort() {
        List<KuugaModel> list = ListUtil.newArrayList();
        list.add(KuugaModel.builder().name("kuuga1").sex(1).build());
        list.add(KuugaModel.builder().name("kuuga2").sex(4).build());
        list.add(KuugaModel.builder().name("kuuga3").sex(3).build());
        list.add(KuugaModel.builder().name("kuuga4").sex(5).build());
        list.add(KuugaModel.builder().name("kuuga5").sex(2).build());

        // public class kuugaModel implements Comparable<kuugaModel> {
        // }

        list.sort((h1, h2) -> h2.getSex() - h1.getSex());
        list.sort(Comparator.comparingInt(KuugaModel::getSex));
        list.sort((h1, h2) -> h2.getSex() - h1.getSex());
        list.sort(Comparator.comparingInt(KuugaModel::getSex));

        list = list.stream().
                sorted((Comparator.comparingInt(KuugaModel::getSex)))
                .collect(Collectors.toList());

        List.copyOf(list).forEach(model -> System.out.println(StringUtil.formatString(model)));
    }

}