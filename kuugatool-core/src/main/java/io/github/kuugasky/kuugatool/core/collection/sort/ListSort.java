package io.github.kuugasky.kuugatool.core.collection.sort;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import java.util.stream.Collectors;

/**
 * ListSort
 *
 * @author kuuga
 * @since 2022/7/1
 */
public final class ListSort<T> {

    private final List<T> list;

    private Comparator<T> comparator;

    private boolean isFirst = true;

    public ListSort(List<T> list) {
        this.list = list;
    }

    public <U extends Comparable<? super U>> ListSort<T> rule(Function<T, ? extends U> keyExtractor) {
        return rule(keyExtractor, null);
    }

    public <U extends Comparable<? super U>> ListSort<T> rule(Function<T, ? extends U> keyExtractor, SortBy sortBy) {
        if (isFirst) {
            comparator = SortBy.isDesc(sortBy) ?
                    Comparator.comparing(keyExtractor).reversed() :
                    Comparator.comparing(keyExtractor);
            isFirst = false;
        } else {
            comparator = SortBy.isDesc(sortBy) ? comparator.thenComparing(keyExtractor, Comparator.reverseOrder()) : comparator.thenComparing(keyExtractor);
        }
        return this;
    }

    public ListSort<T> rule(ToIntFunction<T> keyExtractor) {
        return rule(keyExtractor, null);
    }

    public ListSort<T> rule(ToIntFunction<T> keyExtractor, SortBy sortBy) {
        if (isFirst) {
            comparator = SortBy.isDesc(sortBy) ?
                    Comparator.comparingInt(keyExtractor).reversed() :
                    Comparator.comparingInt(keyExtractor);
            isFirst = false;
        } else {
            comparator = SortBy.isDesc(sortBy) ? comparator.thenComparingInt(keyExtractor).reversed() : comparator.thenComparingInt(keyExtractor);
        }
        return this;
    }

    public ListSort<T> rule(ToLongFunction<T> keyExtractor) {
        return rule(keyExtractor, null);
    }

    public ListSort<T> rule(ToLongFunction<T> keyExtractor, SortBy sortBy) {
        if (isFirst) {
            comparator = SortBy.isDesc(sortBy) ?
                    Comparator.comparingLong(keyExtractor).reversed() :
                    Comparator.comparingLong(keyExtractor);
            isFirst = false;
        } else {
            comparator = SortBy.isDesc(sortBy) ? comparator.thenComparingLong(keyExtractor).reversed() : comparator.thenComparingLong(keyExtractor);
        }
        return this;
    }

    public ListSort<T> rule(ToDoubleFunction<T> keyExtractor) {
        return rule(keyExtractor, null);
    }

    public ListSort<T> rule(ToDoubleFunction<T> keyExtractor, SortBy sortBy) {
        if (isFirst) {
            comparator = SortBy.isDesc(sortBy) ?
                    Comparator.comparingDouble(keyExtractor).reversed() :
                    Comparator.comparingDouble(keyExtractor);
            isFirst = false;
        } else {
            comparator = SortBy.isDesc(sortBy) ? comparator.thenComparingDouble(keyExtractor).reversed() : comparator.thenComparingDouble(keyExtractor);
        }
        return this;
    }

    public List<T> sorted() {
        return list.stream().sorted(comparator).collect(Collectors.toList());
    }

}
