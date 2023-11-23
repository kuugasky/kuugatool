package io.github.kuugasky.kuugatool.extra.weight;

import io.github.kuugasky.kuugatool.core.collection.ListSortUtil;
import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;

import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * WeightCore
 *
 * @author kuuga
 * @since 2023/3/1-03-01 11:20
 */
public class WeightValueCore<T> {

    private final List<T> objects;


    public static <T> WeightValueCore<T> init(List<T> objects) {
        return new WeightValueCore<>(objects);
    }

    private WeightValueCore(List<T> objects) {
        this.objects = objects;
    }

    private final List<WeightValueAction<T>> weightCalculationRules = ListUtil.newArrayList();

    public WeightValueCore<T> addRule(Predicate<T> complianceRule, int weightValue) {
        this.weightCalculationRules.add(new WeightValueAction<>(complianceRule, weightValue));
        return this;
    }

    public WeightValueCore<T> addRule(Predicate<T> complianceRule, int weightValue, String tag) {
        this.weightCalculationRules.add(new WeightValueAction<>(complianceRule, weightValue, tag));
        return this;
    }

    public WeightValueCore<T> addRules(List<WeightValueAction<T>> weightCalculationRules) {
        this.weightCalculationRules.addAll(weightCalculationRules);
        return this;
    }

    public List<WeightValueWrapper<T>> calculation() {
        List<WeightValueWrapper<T>> weightWrappers = ListUtil.newArrayList(objects.size());
        objects.forEach(object -> {
            WeightValueWrapper<T> weightWrapper = ObjectUtil.cast(WeightValueWrapper.builder().object(object).build());

            weightCalculationRules.forEach(rule -> {
                if (rule.getComplianceRule().test(object)) {
                    weightWrapper.addWeightValue(rule.getValue());
                    String tag = rule.getTag();
                    if (StringUtil.hasText(tag)) {
                        weightWrapper.addTag(tag);
                    }
                    Consumer<T> followUpAction = rule.getFollowUpAction();
                    if (ObjectUtil.nonNull(followUpAction)) {
                        followUpAction.accept(object);
                    }
                }
            });
            weightWrappers.add(weightWrapper);
        });
        ListSortUtil.sorted(weightWrappers, Comparator.comparingInt(WeightValueWrapper::getValue));
        ListSortUtil.reverse(weightWrappers);
        return weightWrappers;
    }

}
