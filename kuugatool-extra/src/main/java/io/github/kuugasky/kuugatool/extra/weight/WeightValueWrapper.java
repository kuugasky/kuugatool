package io.github.kuugasky.kuugatool.extra.weight;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * WeightWrapper
 *
 * @author kuuga
 * @since 2023/3/1-03-01 11:07
 */
@Data
@Builder
public class WeightValueWrapper<T> {

    /**
     * 数据对象
     */
    private T object;

    /**
     * 权重值
     */
    private int value;

    /**
     * 标签集
     */
    private List<String> tags;

    public void addWeightValue(int weightValue) {
        this.value = this.value + weightValue;
    }

    public void addTag(String tag) {
        if (ListUtil.isEmpty(this.tags)) {
            this.tags = ListUtil.newArrayList();
        }
        if (this.tags.contains(tag)) {
            return;
        }
        this.tags.add(tag);
    }

    public List<String> getTags() {
        if (ListUtil.isEmpty(tags)) {
            return ListUtil.emptyList();
        }
        return tags;
    }
}
