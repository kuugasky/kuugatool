package io.github.kuugasky.kuugatool.core.collection.enhance;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * ListForEachEnhance
 * <p>
 * list集合循环处理增强
 *
 * @author pengqinglong
 * @since 2021/4/23
 */
public class ListForEachEnhance<T> {

    /**
     * parallel【是否并行】
     * true:所有遍历规则在一次循环内完成
     * false:所有遍历规则按添加顺序依次循环处理
     * default:false
     */
    private boolean parallel = false;

    /**
     * 条件执行函数
     */
    private final List<ConditionFunction<T>> functionList = ListUtil.newArrayList();

    /**
     * 源数据list
     */
    private List<T> data;

    /**
     * 私有构造方法
     */
    private ListForEachEnhance() {
    }

    /**
     * 创建
     */
    public static <T> ListForEachEnhance<T> init() {
        return new ListForEachEnhance<>();
    }

    /**
     * parallel【是否并行】
     * true:所有遍历规则在一次循环内完成
     * false:所有遍历规则按添加顺序依次循环处理
     * default:false
     */
    public ListForEachEnhance<T> parallel(boolean parallel) {
        this.parallel = parallel;
        return this;
    }

    /**
     * 设置数据源
     */
    public ListForEachEnhance<T> data(List<T> data) {
        this.data = data;
        return this;
    }

    /**
     * 添加条件以及判断函数
     */
    public ListForEachEnhance<T> add(Predicate<? super T> condition, Consumer<T> function) {
        functionList.add(new ConditionFunction<>(condition, function));
        return this;
    }

    /**
     * 执行结果
     */
    public void execute() {
        if (ListUtil.isEmpty(data)) {
            return;
        }
        if (parallel) {
            // 一次循环执行
            foreach();
        } else {
            // 有序多次循环执行
            foreachSort();
        }
    }

    /**
     * 无序执行 执行完后就可以break 最好情况下时间复杂度 o(n) 最坏的情况下 每次匹配最后一个条件 时间复杂度是o(n平方)
     */
    private void foreach() {
        data.forEach(item -> functionList.stream().filter(function -> function.condition.test(item)).forEach(function -> function.function.accept(item)));
    }

    /**
     * 有序执行 必须按照条件判断的顺序执行 效率低 最好情况下的时间复杂度也是 O(n平方) 相对无序的情况来说效率会低很多
     */
    private void foreachSort() {
        functionList.forEach(function -> data.stream().filter(item -> function.condition.test(item)).forEach(item -> function.function.accept(item)));
    }

    @AllArgsConstructor
    private static class ConditionFunction<T> {
        /**
         * 条件判断
         */
        Predicate<? super T> condition;
        /**
         * 满足条件后的执行结果函数
         */
        Consumer<T> function;
    }

}
