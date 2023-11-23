package io.github.kuugasky.kuugatool.core.optional;

import io.github.kuugasky.kuugatool.core.lang.Assert;
import org.springframework.lang.NonNull;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * 链式调用 bean 中 value 的方法
 *
 * @author kuuga
 */
public final class KuugaOptional<T> {

    /**
     * Common instance for {@code empty()}.
     */
    private static final KuugaOptional<?> EMPTY = new KuugaOptional<>();

    /**
     * If non-null, the value; if null, indicates no value is present
     */
    private final T value;

    /**
     * Constructs an empty instance.
     *
     * @implNote Generally only one empty instance, {@link KuugaOptional#EMPTY},
     * should exist per VM.
     */
    private KuugaOptional() {
        this.value = null;
    }

    /**
     * Constructs an instance with the described value.
     * 空值会抛出空指针
     *
     * @param value the non-{@code null} value to describe
     * @throws NullPointerException if value is {@code null}
     */
    private KuugaOptional(T value) {
        this.value = Objects.requireNonNull(value);
    }

    /**
     * 返回一个指定非nu值的KuugaOptional.
     * Returns an {@code KuugaOptional} describing the given non-{@code null} value.
     *
     * @param value the value to describe, which must be non-{@code null}
     * @param <T>   the type of the value
     * @return an {@code KuugaOptional} with the value present
     * @throws NullPointerException if value is {@code null}
     */
    public static <T> KuugaOptional<T> of(T value) {
        return new KuugaOptional<>(value);
    }

    /**
     * 如果为非空，返回KuugaOptional描述的指定值，否则返回空的KuugaOptional
     * Returns an {@code KuugaOptional} describing the given value, if
     * non-{@code null}, otherwise returns an empty {@code KuugaOptional}.
     *
     * @param value the possibly-{@code null} value to describe
     * @param <T>   the type of the value
     * @return an {@code KuugaOptional} with a present value if the specified value
     * is non-{@code null}, otherwise an empty {@code KuugaOptional}
     */
    public static <T> KuugaOptional<T> ofNullable(T value) {
        return value == null ? empty() : of(value);
    }

    /**
     * 如果值存在则方法会返回true,否则返回false.
     * If a value is present, returns {@code true}, otherwise {@code false}.
     *
     * @return {@code true} if a value is present, otherwise {@code false}
     */
    public boolean isPresent() {
        return value != null;
    }

    /**
     * 如果值存在则使用该值调用consumer，否则不做任何事情。
     * If a value is present, performs the given action with the value, otherwise does nothing.
     *
     * @param action the action to be performed, if a value is present
     * @throws NullPointerException if value is present and the given action is
     *                              {@code null}
     */
    public void ifPresent(Consumer<? super T> action) {
        if (value != null) {
            action.accept(value);
        }
    }

    /**
     * If a value is present, performs the given action with the value, otherwise performs the given empty-based action.
     * 如果存在一个值，则使用该值执行给定的操作，否则执行给定的基于空的操作
     *
     * @param action      the action to be performed, if a value is present
     * @param emptyAction the empty-based action to be performed, if no value is
     *                    present
     * @throws NullPointerException if a value is present and the given action
     *                              is {@code null}, or no value is present and the given empty-based
     *                              action is {@code null}.
     * @since 9
     */
    public void ifPresentOrElse(Consumer<? super T> action, Runnable emptyAction) {
        if (value != null) {
            action.accept(value);
        } else {
            emptyAction.run();
        }
    }

    /**
     * 如果在这个KuugaOptional中包含这个值，返回值，否则抛出异常: NoSuchElementException
     */
    public T get() {
        return Objects.isNull(value) ? null : value;
    }

    /**
     * If a value is present, returns the value, otherwise throws
     * {@code NoSuchElementException}.
     * 如果存在一个值，返回该值，否则抛出{@code NoSuchElementException}
     *
     * @return the non-{@code null} value described by this {@code KuugaOptional}
     * @throws NoSuchElementException if no value is present
     *                                The preferred alternative to this method is {@link #orElseThrow()}.
     */
    public T getOrThrow() {
        if (value == null) {
            throw new NoSuchElementException("No value present");
        }
        return value;
    }

    /**
     * 取出一个可能为空的对象
     */
    public <R> KuugaOptional<R> getBean(Function<? super T, ? extends R> fn) {
        return Objects.isNull(value) ? KuugaOptional.empty() : KuugaOptional.ofNullable(fn.apply(value));
    }

    /**
     * 如果值存在，并且这个值匹配给定的predicate,返回一个KuugaOptional用以描述这个值，否则返回一个空的KuugaOptional
     * 此方法返回过滤后的Optional实例。如果此KuugaOptional实例中没有值，则此方法返回一个空的Optional实例。
     * PS：只允许value为单对象时使用,如果指定的谓词为null，则此方法引发NullPointerException。
     * If a value is present, and the value matches the given predicate,
     * returns an {@code KuugaOptional} describing the value, otherwise returns an
     * empty {@code KuugaOptional}.
     * 如果存在一个值，并且该值与给定的谓词匹配，将返回一个描述该值的{@code KuugaOptional}，否则将返回一个empty
     *
     * @param predicate the predicate to apply to a value, if present
     * @return an {@code KuugaOptional} describing the value of this
     * {@code KuugaOptional}, if a value is present and the value matches the
     * given predicate, otherwise an empty {@code KuugaOptional}
     * @throws NullPointerException if the predicate is {@code null}
     */
    public KuugaOptional<T> filter(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        if (!isPresent()) {
            return this;
        } else {
            return predicate.test(value) ? this : empty();
        }
    }

    /**
     * 如果有值,则对其执行调用映射函数得到返回值。如果返回值不为null,则创建包含映射返回值的KuugaOptional作为map方法返回值，否则返回空KuugaOptional
     * If a value is present, returns an {@code KuugaOptional} describing (as if by
     * {@link #ofNullable}) the result of applying the given mapping function to
     * the value, otherwise returns an empty {@code KuugaOptional}.
     * 如果存在一个值，返回一个{@code KuugaOptional}来描述(就像通过{@link #ofNullable})应用给定映射函数该值的结果，
     * 否则返回一个空的{@code KuugaOptional}。
     *
     * <p>If the mapping function returns a {@code null} result then this method
     * returns an empty {@code KuugaOptional}.
     *
     * @param mapper the mapping function to apply to a value, if present
     * @param <U>    The type of the value returned from the mapping function
     * @return an {@code KuugaOptional} describing the result of applying a mapping
     * function to the value of this {@code KuugaOptional}, if a value is
     * present, otherwise an empty {@code KuugaOptional}
     * @throws NullPointerException if the mapping function is {@code null}
     *                              This method supports post-processing on {@code KuugaOptional} values, without
     *                              the need to explicitly check for a return status.  For example, the
     *                              following code traverses a stream of URIs, selects one that has not
     *                              yet been processed, and creates a path from that URI, returning
     *                              an {@code KuugaOptional<Path>}:
     */
    public <U> KuugaOptional<U> map(Function<? super T, ? extends U> mapper) {
        Objects.requireNonNull(mapper);
        if (!isPresent()) {
            return empty();
        } else {
            return KuugaOptional.ofNullable(mapper.apply(value));
        }
    }

    /**
     * 如果值存在，返回甚于KuugaOptional包含的映射方法的值，否则返回一个空的KuugaOptional。
     * 简单点说，从源对象中抽取成员变量做处理，然后封装成一个新的KuugaOptional对象返回。
     * If a value is present, returns the result of applying the given
     * {@code KuugaOptional}-bearing mapping function to the value, otherwise returns
     * an empty {@code KuugaOptional}.
     *
     * <p>This method is similar to {@link #map(Function)}, but the mapping
     * function is one whose result is already an {@code KuugaOptional}, and if
     * invoked, {@code flatMap} does not wrap it within an additional
     * {@code KuugaOptional}.
     *
     * @param <U>    The type of value of the {@code KuugaOptional} returned by the
     *               mapping function
     * @param mapper the mapping function to apply to a value, if present
     * @return the result of applying an {@code KuugaOptional}-bearing mapping
     * function to the value of this {@code KuugaOptional}, if a value is
     * present, otherwise an empty {@code KuugaOptional}
     * @throws NullPointerException if the mapping function is {@code null} or
     *                              returns a {@code null} result
     */
    public <U> KuugaOptional<U> flatMap(Function<? super T, ? extends KuugaOptional<? extends U>> mapper) {
        Objects.requireNonNull(mapper);
        if (!isPresent()) {
            return empty();
        } else {
            @SuppressWarnings("unchecked")
            KuugaOptional<U> r = (KuugaOptional<U>) mapper.apply(value);
            return Objects.requireNonNull(r);
        }
    }

    /**
     * 如果存在一个值，返回一个描述该值的{@code KuugaOptional}， 否则返回一个由提供函数生成的{@code KuugaOptional}。
     * If a value is present, returns an {@code KuugaOptional} describing the value,
     * otherwise returns an {@code KuugaOptional} produced by the supplying function.
     *
     * @param supplier the supplying function that produces an {@code KuugaOptional}
     *                 to be returned
     * @return returns an {@code KuugaOptional} describing the value of this
     * {@code KuugaOptional}, if a value is present, otherwise an
     * {@code KuugaOptional} produced by the supplying function.
     * @throws NullPointerException if the supplying function is {@code null} or
     *                              produces a {@code null} result
     */
    public KuugaOptional<T> or(Supplier<? extends KuugaOptional<? extends T>> supplier) {
        Objects.requireNonNull(supplier);
        if (isPresent()) {
            return this;
        } else {
            @SuppressWarnings("unchecked")
            KuugaOptional<T> r = (KuugaOptional<T>) supplier.get();
            return Objects.requireNonNull(r);
        }
    }

    /**
     * 如果存在一个值，则返回一个只包含该值的stream流，否则返回一个空的流
     * param:此方法不接受任何参数
     * return:此方法返回此KuugaOptional实例中存在的唯一值的顺序流。如果此KuugaOptional实例中没有值，则此方法返回一个空Stream。
     * PS：该方法是将KuugaOptional.value封装到stream，也就是KuugaOptional.of().stream().filter(s -> {})，xx拿到的是value，
     * 假设value是list，filter里面的s是list而不是list的element
     * KuugaOptional.of().stream().forEach(s -> System.out::print)这里的s才是list的element
     * <p>
     * If a value is present, returns a sequential {@link Stream} containing
     * only that value, otherwise returns an empty {@code Stream}.
     *
     * @return the optional value as a {@code Stream}
     * @apiNote This method can be used to transform a {@code Stream} of optional
     * elements to a {@code Stream} of present value elements:
     * {@code
     * Stream<KuugaOptional<T>> os = ..
     * Stream<T> s = os.flatMap(KuugaOptional::stream)
     * }
     */
    public Stream<T> stream() {
        if (!isPresent()) {
            return Stream.empty();
        } else {
            return Stream.of(value);
        }
    }

    /**
     * 如果存在该值，返回值，否则返回other
     * If a value is present, returns the value, otherwise returns
     * {@code other}.
     *
     * @param other the value to be returned, if no value is present. May be {@code null}.
     * @return the value, if present, otherwise {@code other}
     */
    public T orElse(T other) {
        return value != null ? value : other;
    }

    /**
     * 如果存在该值，返回值，否则触发supplier,并返回supplier调用的结果。
     * Ps:
     * 当 KuugaOptional 为空时，orElse 和 orElseGet 区别不大，
     * 但当 KuugaOptional 有值时，orElse 仍然会去调用方法创建对象，而 orElseGet 不会再调用方法；在我们处理的业务数据量大的时候，这两者的性能就有很大的差异。
     * <p>
     * If a value is present, returns the value, otherwise returns the result
     * produced by the supplying function.
     *
     * @param supplier the supplying function that produces a value to be returned
     * @return the value, if present, otherwise the result produced by the
     * supplying function
     * @throws NullPointerException if no value is present and the supplying
     *                              function is {@code null}
     */
    public T orElseGet(@NonNull Supplier<? extends T> supplier) {
        Assert.notNull(supplier, "supplier can not be null.");
        return value != null ? value : supplier.get();
    }

    /**
     * 如果存在该值，返回包含的值，否则抛出{@code NoSuchElementException}异常
     * If a value is present, returns the value, otherwise throws
     * {@code NoSuchElementException}.
     *
     * @return the non-{@code null} value described by this {@code KuugaOptional}
     * @throws NoSuchElementException if no value is present
     * @since 10
     */
    public T orElseThrow() {
        if (value == null) {
            throw new NoSuchElementException("No value present");
        }
        return value;
    }

    /**
     * 如果存在该值，返回包含的值，否则抛出由Supplier继承的异常。
     * If a value is present, returns the value, otherwise throws an exception
     * produced by the exception supplying function.
     *
     * @param <X>               Type of the exception to be thrown
     * @param exceptionSupplier the supplying function that produces an
     *                          exception to be thrown
     * @return the value, if present
     * @throws X                    if no value is present
     * @throws NullPointerException if no value is present and the exception
     *                              supplying function is {@code null}
     * @apiNote A method reference to the exception constructor with an empty argument
     * list can be used as the supplier. For example,
     * {@code IllegalStateException::new}
     */
    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (value != null) {
            return value;
        } else {
            throw exceptionSupplier.get();
        }
    }

    /**
     * 返回存在值的哈希码，如果值不存在返回0。
     * Returns the hash code of the value, if present, otherwise {@code 0}
     * (zero) if no value is present.
     *
     * @return hash code value of the present value or {@code 0} if no value is present
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    /**
     * 判断其他对象是否等于KuugaOptional。
     * Indicates whether some other object is "equal to" this {@code KuugaOptional}.
     * The other object is considered equal if:
     * <ul>
     * <li>it is also an {@code KuugaOptional} and;
     * <li>both instances have no value present or;
     * <li>the present values are "equal to" each other via {@code equals()}.
     * </ul>
     *
     * @param obj an object to be tested for equality
     * @return {@code true} if the other object is "equal to" this object otherwise {@code false}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof KuugaOptional<?> other)) {
            return false;
        }

        return Objects.equals(value, other.value);
    }

    /**
     * 如果值不存在，则返回{@code true}，否则返回。
     * If a value is  not present, returns {@code true}, otherwise
     * {@code false}.
     *
     * @return {@code true} if a value is not present, otherwise {@code false}
     */
    public boolean isEmpty() {
        return value == null;
    }

    /**
     * 返回空的KuugaOptional实例。
     * Returns an empty {@code KuugaOptional} instance.  No value is present for this
     * {@code KuugaOptional}.
     *
     * @param <T> The type of the non-existent value
     * @return an empty {@code KuugaOptional}
     * @apiNote Though it may be tempting to do so, avoid testing if an object is empty
     * by comparing with {@code ==} against instances returned by
     * {@code KuugaOptional.empty()}.  There is no guarantee that it is a singleton.
     * Instead, use {@link #isPresent()}.
     */
    public static <T> KuugaOptional<T> empty() {
        @SuppressWarnings("unchecked")
        KuugaOptional<T> none = (KuugaOptional<T>) EMPTY;
        return none;
    }

    /**
     * 返回一个KuugaOptional的非空字符串，用来调试
     * Returns a non-empty string representation of this {@code KuugaOptional}
     * suitable for debugging.  The exact presentation format is unspecified and
     * may vary between implementations and versions.
     *
     * @return the string representation of this instance
     * @implSpec If a value is present the result must include its string representation
     * in the result.  Empty and present {@code KuugaOptional}s must be unambiguously
     * differentiable.
     */
    @Override
    public String toString() {
        return value != null
                ? String.format("KuugaOptional[%s]", value)
                : "KuugaOptional.empty";
    }

}
