package io.github.kuugasky.kuugatool.core.stream;

import io.github.kuugasky.kuugatool.core.collection.CollectionUtil;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * StreamUtil
 * <br>
 * 常用方法:<br>
 * filter 方法用于通过设置的条件过滤出元素。<br>
 * allMatch 方法用于判断所有元素是否符合判定条件。<br>
 * anyMatch 方法用于判断任一元素是否符合判定条件。<br>
 * allMatch 方法用于判断所有元素是否都不符合判定条件。<br>
 * map 方法用于映射每个元素到对应的结果。<br>
 * limit/skip：<br>
 * limit 返回 Stream 的前面 n个元素；skip 则是扔掉前 n 个元素。<br>
 * sorted 方法用于对流进行排序。<br>
 * distinct 主要用来去重。<br>
 * Stream 提供了方法 forEach' 来迭代流中的每个数据。<br>
 * count用来统计流中的元素个数。<br>
 * collect就是一个归约操作，可以接受各种做法作为参数，将流中的元素累积成一个汇总结果。<br>
 *
 * @author kuuga
 * @since 2022/7/21 14:17
 */
public final class StreamUtil {

    /**
     * list转Stream
     *
     * @param list list集合
     * @param <E>  E
     * @return Stream<E>
     */
    public static <E extends @Nullable Object> Stream<E> of(final List<E> list) {
        return list.stream();
    }

    /**
     * set转Stream
     *
     * @param set set集合
     * @param <E> E
     * @return Stream<E>
     */
    public static <E extends @Nullable Object> Stream<E> of(final Set<E> set) {
        return set.stream();
    }

    /**
     * 通过function函数提取集合对象某个属性转Stream
     *
     * @param collection 集合对象
     * @param mapper     function
     * @param <T>        T
     * @param <R>        R
     * @return Stream<R>
     */
    public static <T, R> Stream<R> map(final Collection<T> collection, Function<T, R> mapper) {
        return collection.stream().map(mapper);
    }

    /**
     * 通过function函数提取集合对象某个Int属性转Stream
     *
     * @param collection 集合对象
     * @param mapper     function
     * @param <T>        T
     * @return Stream<R>
     */
    public static <T> IntStream mapToInt(final Collection<T> collection, ToIntFunction<? super T> mapper) {
        return collection.stream().mapToInt(mapper);
    }

    /**
     * 通过function函数提取集合对象某个Long属性转Stream
     *
     * @param collection 集合对象
     * @param mapper     function
     * @param <T>        T
     * @return Stream<R>
     */
    public static <T> LongStream mapToLong(final Collection<T> collection, ToLongFunction<? super T> mapper) {
        return collection.stream().mapToLong(mapper);
    }

    /**
     * 通过function函数提取集合对象某个Double属性转Stream
     *
     * @param collection 集合对象
     * @param mapper     function
     * @param <T>        T
     * @return Stream<R>
     */
    public static <T> DoubleStream mapToDouble(final Collection<T> collection, ToDoubleFunction<? super T> mapper) {
        return collection.stream().mapToDouble(mapper);
    }

    /**
     * 通过function函数提取集合对象某个属性进行操作转Stream
     *
     * @param collection 集合对象
     * @param mapper     function
     * @param <T>        T
     * @return Stream<R>
     */
    public static <T, R> Stream<R> flatMap(final Collection<T> collection, Function<? super T, ? extends Stream<? extends R>> mapper) {
        return collection.stream().flatMap(mapper);
    }

    /**
     * 通过function函数提取集合对象某个Int属性进行操作转Stream
     *
     * @param collection 集合对象
     * @param mapper     function
     * @param <T>        T
     * @return Stream<R>
     */
    public static <T> IntStream flatMapToInt(final Collection<T> collection, Function<? super T, ? extends IntStream> mapper) {
        return collection.stream().flatMapToInt(mapper);
    }

    /**
     * 通过function函数提取集合对象某个Long属性进行操作转Stream
     *
     * @param collection 集合对象
     * @param mapper     function
     * @param <T>        T
     * @return Stream<R>
     */
    public static <T> LongStream flatMapToLong(final Collection<T> collection, Function<? super T, ? extends LongStream> mapper) {
        return collection.stream().flatMapToLong(mapper);
    }

    /**
     * 通过function函数提取集合对象某个Double属性进行操作转Stream
     *
     * @param collection 集合对象
     * @param mapper     function
     * @param <T>        T
     * @return Stream<R>
     */
    public static <T> DoubleStream flatMapToDouble(final Collection<T> collection, Function<? super T, ? extends DoubleStream> mapper) {
        return collection.stream().flatMapToDouble(mapper);
    }

    // ========================================================================================================================

    // /**
    //  * jdk16新特性
    //  *
    //  * @param collection 集合
    //  * @param mapper     BiConsumer
    //  * @param <T>        T
    //  * @return IntStream
    //  */
    // public static <T> IntStream mapMultiToInt(final Collection<T> collection, BiConsumer<? super T, ? super IntConsumer> mapper) {
    //     return collection.stream().mapMultiToInt(mapper);
    // }

    // ========================================================================================================================

    /**
     * 全匹配<br>
     * 注意，如果collection为空集合，直接返回true
     * <p>
     * 返回此流的所有元素是否与提供的谓词匹配。如果没有必要，可能不会评估所有元素的谓词。确定结果。如果流为空，则返回{@code true}并且没有计算谓词。
     *
     * @param collection 集合
     * @param predicate  条件
     * @param <T>        T
     * @return boolean
     */
    public static <T> boolean allMatch(final Collection<T> collection, Predicate<? super T> predicate) {
        return collection.stream().allMatch(predicate);
    }

    /**
     * 全匹配<br>
     * 注意，如果collection为空集合，直接返回true
     *
     * @param collection 集合
     * @param mapper     对象某一属性
     * @param predicate  条件
     * @param <T>        T
     * @return boolean
     */
    public static <T, R> boolean allMatch(final Collection<T> collection, Function<? super T, ? extends R> mapper, Predicate<R> predicate) {
        return collection.stream().map(mapper).allMatch(predicate);
    }

    /**
     * 其中任何一个匹配
     *
     * @param collection 集合
     * @param predicate  条件
     * @param <T>        T
     * @return boolean
     */
    public static <T> boolean anyMatch(final Collection<T> collection, Predicate<? super T> predicate) {
        return collection.stream().anyMatch(predicate);
    }

    /**
     * 其中任何一个匹配
     *
     * @param collection 集合
     * @param mapper     对象某一属性
     * @param predicate  条件
     * @param <T>        T
     * @return boolean
     */
    public static <T, R> boolean anyMatch(final Collection<T> collection, Function<? super T, ? extends R> mapper, Predicate<R> predicate) {
        return collection.stream().map(mapper).anyMatch(predicate);
    }

    /**
     * 没有一个匹配
     *
     * @param collection 集合
     * @param predicate  条件
     * @param <T>        T
     * @return boolean
     */
    public static <T> boolean noneMatch(final Collection<T> collection, Predicate<? super T> predicate) {
        return collection.stream().noneMatch(predicate);
    }

    /**
     * 没有一个匹配
     *
     * @param collection 集合
     * @param mapper     对象某一属性
     * @param predicate  条件
     * @param <T>        T
     * @return boolean
     */
    public static <T, R> boolean noneMatch(final Collection<T> collection, Function<? super T, ? extends R> mapper, Predicate<R> predicate) {
        return collection.stream().map(mapper).noneMatch(predicate);
    }

    public static <E> E findFirst(final Collection<E> collection) {
        return findFirst(collection, null);
    }

    public static <E> E findFirst(final Collection<E> collection, E other) {
        if (CollectionUtil.isEmpty(collection)) {
            return null;
        }
        return collection.stream().findFirst().orElse(other);
    }

    public static <E> E findAny(final Collection<E> collection) {
        return findAny(collection, null);
    }

    public static <E> E findAny(final Collection<E> collection, E other) {
        if (CollectionUtil.isEmpty(collection)) {
            return null;
        }
        return collection.stream().findAny().orElse(other);
    }

    public static <E> long count(final Collection<E> collection) {
        return Optional.of(collection).get().size();
    }

    // MAX MIN 使用 NumberUtil.max/min ==================================================================================================

    // reduce ==================================================================================================

    /**
     * 递归操作
     *
     * @param collection  集合
     * @param accumulator 合并两个值的函数
     * @return 返回递归的结果
     */
    public static <T> T reduce(Collection<T> collection, BinaryOperator<T> accumulator) {
        return collection.stream().reduce(accumulator).orElse(null);
    }

    /**
     * 带恒等值的递归操作
     *
     * @param collection  集合
     * @param identity    累积函数的恒等值
     * @param accumulator 合并两个值的函数
     * @return 返回递归的结果
     */
    public static <T> T reduce(Collection<T> collection, T identity, BinaryOperator<T> accumulator) {
        return collection.stream().reduce(identity, accumulator);
    }


}
