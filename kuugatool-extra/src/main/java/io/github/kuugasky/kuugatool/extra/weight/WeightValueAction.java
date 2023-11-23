package io.github.kuugasky.kuugatool.extra.weight;

import lombok.Data;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * WeightValueAction
 *
 * @author kuuga
 * @since 2023/3/1-03-01 11:12
 */
@Data
public class WeightValueAction<T> {

    /**
     * 是否符合规则
     */
    private Predicate<T> complianceRule;

    /**
     * 符合规则加多少分值
     */
    private int value;

    /**
     * 是否符合规则
     */
    private Consumer<T> followUpAction;

    /**
     * 标签
     */
    private String tag;

    public WeightValueAction(Predicate<T> complianceRule, int value) {
        this.complianceRule = complianceRule;
        this.value = value;
    }

    public WeightValueAction(Predicate<T> complianceRule, int value, String tag) {
        this.complianceRule = complianceRule;
        this.value = value;
        this.tag = tag;
    }

    public WeightValueAction(Predicate<T> complianceRule, int value, Consumer<T> followUpAction) {
        this.complianceRule = complianceRule;
        this.value = value;
        this.followUpAction = followUpAction;
    }

}
