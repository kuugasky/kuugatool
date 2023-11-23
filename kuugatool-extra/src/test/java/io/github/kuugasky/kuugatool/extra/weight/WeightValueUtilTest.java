package io.github.kuugasky.kuugatool.extra.weight;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.date.DateUtil;
import io.github.kuugasky.kuugatool.core.string.JoinerUtil;
import lombok.Builder;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

class WeightValueUtilTest {

    @Test
    void calculation() {
        List<WeightValueAction<WeightKuuga>> weightCalculationRules = ListUtil.newArrayList();
        weightCalculationRules.add(new WeightValueAction<>(x -> x.getAge() == 10, 1, x -> x.addTag("kuuga1")));
        weightCalculationRules.add(new WeightValueAction<>(x -> x.getAge() == 20, 2, x -> x.addTag("kuuga2")));
        weightCalculationRules.add(new WeightValueAction<>(x -> x.getAge() == 30, 3, x -> x.addTag("kuuga3")));
        weightCalculationRules.add(new WeightValueAction<>(x -> x.getAge() == 40, 4, x -> x.addTag("kuuga4")));
        weightCalculationRules.add(new WeightValueAction<>(x -> x.getAge() == 50, 100, x -> x.addTag("kuuga5")));
        weightCalculationRules.add(new WeightValueAction<>(x -> x.getAge() == 50, 100, x -> x.addTag("kuuga5")));
        weightCalculationRules.add(new WeightValueAction<>(x -> x.getCityCode().equals("SHENZHEN"), 100, x -> x.addTag("kuuga5")));
        List<WeightValueWrapper<WeightKuuga>> calculation = WeightValueUtil.calculation(list, weightCalculationRules);
        calculation.forEach(x ->
                System.out.printf("%s -> %s%n", x.getObject(), x.getValue()));
    }

    @Data
    @Builder
    static class WeightKuuga {
        private int age;
        private Date birthDay;
        private String name;
        private String cityCode;
        private List<String> tags;

        public void addTag(String tag) {
            if (ListUtil.isEmpty(this.tags)) {
                this.tags = ListUtil.newArrayList();
            }
            if (this.tags.contains(tag)) {
                return;
            }
            this.tags.add(tag);
        }
    }

    static List<WeightKuuga> list = ListUtil.newArrayList();

    static {
        list.add(WeightKuuga.builder().age(10).birthDay(DateUtil.of(1990, 1, 1)).name("kuuga1").cityCode("SHENZHEN").build());
        list.add(WeightKuuga.builder().age(20).birthDay(DateUtil.of(2000, 2, 2)).name("kuuga2").cityCode("GUANGZHOU").build());
        list.add(WeightKuuga.builder().age(30).birthDay(DateUtil.of(2010, 3, 3)).name("kuuga3").cityCode("FOSHAN").build());
        list.add(WeightKuuga.builder().age(40).birthDay(DateUtil.of(2020, 4, 4)).name("kuuga4").cityCode("FOSHAN").build());
        list.add(WeightKuuga.builder().age(50).birthDay(DateUtil.of(2030, 5, 5)).name("kuuga5").cityCode("SHENZHEN").build());
        list.add(WeightKuuga.builder().age(10).birthDay(DateUtil.of(2030, 5, 5)).name("kuuga6").cityCode("SHENZHEN").build());
        list.add(WeightKuuga.builder().age(10).birthDay(DateUtil.of(2030, 5, 5)).name("kuuga7").cityCode("GUANGZHOU").build());
    }

    public static void main(String[] args) {
        List<WeightValueWrapper<WeightKuuga>> calculation = WeightValueCore.init(list)
                .addRule(object -> "SHENZHEN".equals(object.getCityCode()), 1, "A")
                .addRule(object -> "FOSHAN".equals(object.getCityCode()), 2, "A")
                .addRule(object -> "GUANGZHOU".equals(object.getCityCode()), 3, "C")
                .addRule(object -> object.getAge() >= 3, 1, "B")
                .calculation();
        calculation.forEach(x ->
                System.out.printf("%s -> %s %s%n", x.getObject(), x.getValue(), JoinerUtil.on(",").join(x.getTags()))
        );
    }

}