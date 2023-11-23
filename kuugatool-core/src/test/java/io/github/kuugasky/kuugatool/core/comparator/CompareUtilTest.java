package io.github.kuugasky.kuugatool.core.comparator;

import io.github.kuugasky.kuugatool.core.entity.KuugaModel;
import org.junit.jupiter.api.Test;

import java.util.Comparator;

class CompareUtilTest {

    KuugaModel kuugaModel1 = KuugaModel.builder().name("kuuga1").sex(1).build();
    KuugaModel kuugaModel2 = KuugaModel.builder().name("kuuga2").sex(2).build();

    @Test
    void compare() {
        int compare = CompareUtil.compare(kuugaModel1, kuugaModel2, Comparator.comparingInt(KuugaModel::getSex));
        System.out.println(compare);
    }

    @Test
    void testCompare() {
        int compare = CompareUtil.compare(kuugaModel1.toString(), kuugaModel2.toString());
        System.out.println(compare);
    }

    @Test
    void testCompare1() {
        int compare = CompareUtil.compare(kuugaModel1.toString(), kuugaModel2.toString());
        System.out.println(compare);
    }

    @Test
    void testCompare2() {
        int compare = CompareUtil.compare(kuugaModel1.toString(), kuugaModel2.toString(), true);
        System.out.println(compare);
    }

}