package io.github.kuugasky.kuugatool.core.object;

import com.google.common.base.Verify;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import static com.google.common.base.Strings.lenientFormat;

/**
 * 先决条件工具类
 *
 * @author kuuga
 * @since 2021/7/14
 */
public class Preconditions {

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     * 确保涉及调用方法的一个或多个参数的表达式的真实性。
     *
     * @param expression a boolean expression 一个布尔表达式
     * @throws IllegalArgumentException if {@code expression} is false 如果{@code表达式}是假的
     */
    public static void checkArgument(boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     * 确保涉及调用方法的一个或多个参数的表达式的真实性。
     *
     * @param expression   a boolean expression  一个布尔表达式
     * @param errorMessage the exception message to use if the check fails; will be converted to a
     *                     string using {@link String#valueOf(Object)}
     *                     检查失败时使用的异常消息;将使用{@link String#valueOf(Object)}转换为字符串
     * @throws IllegalArgumentException if {@code expression} is false
     */
    public static void checkArgument(boolean expression, @Nullable Object errorMessage) {
        if (!expression) {
            throw new IllegalArgumentException(String.valueOf(errorMessage));
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     * 确保涉及调用方法的一个或多个参数的表达式的真实性。
     *
     * @param expression           a boolean expression 一个布尔表达式
     * @param errorMessageTemplate a template for the exception message should the check fail. The
     *                             message is formed by replacing each {@code %s} placeholder in the template with an
     *                             argument. These are matched by position - the first {@code %s} gets {@code
     *                             errorMessageArgs[0]}, etc. Unmatched arguments will be appended to the formatted message in
     *                             square braces. Unmatched placeholders will be left as-is.
     *                             如果检查失败，异常消息的模板。该消息是通过将模板中的每个{@code %s}占位符替换为一个参数来形成的。
     *                             它们按位置匹配-第一个{@code %s}得到{@code errorMessageArgs[0]}，等等。不匹配的参数将以方括号的形式附加到格式化的消息后面。
     *                             不匹配的占位符将保持原样。
     * @param errorMessageArgs     the arguments to be substituted into the message template. Arguments
     *                             are converted to strings using {@link String#valueOf(Object)}.
     *                             要替换到消息模板中的参数。参数使用{@link String#valueOf(Object)}转换为字符串。
     * @throws IllegalArgumentException if {@code expression} is false
     */
    public static void checkArgument(
            boolean expression,
            @Nullable String errorMessageTemplate,
            Object @Nullable ... errorMessageArgs) {
        if (!expression) {
            throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, errorMessageArgs));
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     * 确保涉及调用方法的一个或多个参数的表达式的真实性。
     *
     * <p>See {@link #checkArgument(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkArgument(boolean b, @Nullable String errorMessageTemplate, char p1) {
        if (!b) {
            throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1));
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     * 确保涉及调用方法的一个或多个参数的表达式的真实性。
     *
     * <p>See {@link #checkArgument(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkArgument(boolean b, @Nullable String errorMessageTemplate, int p1) {
        if (!b) {
            throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1));
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     * 确保涉及调用方法的一个或多个参数的表达式的真实性。
     *
     * <p>See {@link #checkArgument(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkArgument(boolean b, @Nullable String errorMessageTemplate, long p1) {
        if (!b) {
            throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1));
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     * 确保涉及调用方法的一个或多个参数的表达式的真实性。
     *
     * <p>See {@link #checkArgument(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkArgument(
            boolean b, @Nullable String errorMessageTemplate, @Nullable Object p1) {
        if (!b) {
            throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1));
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     * 确保涉及调用方法的一个或多个参数的表达式的真实性。
     *
     * <p>See {@link #checkArgument(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkArgument(
            boolean b, @Nullable String errorMessageTemplate, char p1, char p2) {
        if (!b) {
            throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1, p2));
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     * 确保涉及调用方法的一个或多个参数的表达式的真实性。
     *
     * <p>See {@link #checkArgument(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkArgument(
            boolean b, @Nullable String errorMessageTemplate, char p1, int p2) {
        if (!b) {
            throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1, p2));
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     * 确保涉及调用方法的一个或多个参数的表达式的真实性。
     *
     * <p>See {@link #checkArgument(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkArgument(
            boolean b, @Nullable String errorMessageTemplate, char p1, long p2) {
        if (!b) {
            throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1, p2));
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     * 确保涉及调用方法的一个或多个参数的表达式的真实性。
     *
     * <p>See {@link #checkArgument(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkArgument(
            boolean b, @Nullable String errorMessageTemplate, char p1, @Nullable Object p2) {
        if (!b) {
            throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1, p2));
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     * 确保涉及调用方法的一个或多个参数的表达式的真实性。
     *
     * <p>See {@link #checkArgument(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkArgument(
            boolean b, @Nullable String errorMessageTemplate, int p1, char p2) {
        if (!b) {
            throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1, p2));
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     * 确保涉及调用方法的一个或多个参数的表达式的真实性。
     *
     * <p>See {@link #checkArgument(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkArgument(
            boolean b, @Nullable String errorMessageTemplate, int p1, int p2) {
        if (!b) {
            throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1, p2));
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     * 确保涉及调用方法的一个或多个参数的表达式的真实性。
     *
     * <p>See {@link #checkArgument(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkArgument(
            boolean b, @Nullable String errorMessageTemplate, int p1, long p2) {
        if (!b) {
            throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1, p2));
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     * 确保涉及调用方法的一个或多个参数的表达式的真实性。
     *
     * <p>See {@link #checkArgument(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkArgument(
            boolean b, @Nullable String errorMessageTemplate, int p1, @Nullable Object p2) {
        if (!b) {
            throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1, p2));
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     * 确保涉及调用方法的一个或多个参数的表达式的真实性。
     *
     * <p>See {@link #checkArgument(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkArgument(
            boolean b, @Nullable String errorMessageTemplate, long p1, char p2) {
        if (!b) {
            throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1, p2));
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     * 确保涉及调用方法的一个或多个参数的表达式的真实性。
     *
     * <p>See {@link #checkArgument(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkArgument(
            boolean b, @Nullable String errorMessageTemplate, long p1, int p2) {
        if (!b) {
            throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1, p2));
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     * 确保涉及调用方法的一个或多个参数的表达式的真实性。
     *
     * <p>See {@link #checkArgument(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkArgument(
            boolean b, @Nullable String errorMessageTemplate, long p1, long p2) {
        if (!b) {
            throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1, p2));
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     * 确保涉及调用方法的一个或多个参数的表达式的真实性。
     *
     * <p>See {@link #checkArgument(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkArgument(
            boolean b, @Nullable String errorMessageTemplate, long p1, @Nullable Object p2) {
        if (!b) {
            throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1, p2));
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     * 确保涉及调用方法的一个或多个参数的表达式的真实性。
     *
     * <p>See {@link #checkArgument(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkArgument(
            boolean b, @Nullable String errorMessageTemplate, @Nullable Object p1, char p2) {
        if (!b) {
            throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1, p2));
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     * 确保涉及调用方法的一个或多个参数的表达式的真实性。
     *
     * <p>See {@link #checkArgument(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkArgument(
            boolean b, @Nullable String errorMessageTemplate, @Nullable Object p1, int p2) {
        if (!b) {
            throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1, p2));
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     * 确保涉及调用方法的一个或多个参数的表达式的真实性。
     *
     * <p>See {@link #checkArgument(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkArgument(
            boolean b, @Nullable String errorMessageTemplate, @Nullable Object p1, long p2) {
        if (!b) {
            throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1, p2));
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     * 确保涉及调用方法的一个或多个参数的表达式的真实性。
     *
     * <p>See {@link #checkArgument(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkArgument(
            boolean b, @Nullable String errorMessageTemplate, @Nullable Object p1, @Nullable Object p2) {
        if (!b) {
            throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1, p2));
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     * 确保涉及调用方法的一个或多个参数的表达式的真实性。
     *
     * <p>See {@link #checkArgument(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkArgument(
            boolean b,
            @Nullable String errorMessageTemplate,
            @Nullable Object p1,
            @Nullable Object p2,
            @Nullable Object p3) {
        if (!b) {
            throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1, p2, p3));
        }
    }

    /**
     * Ensures the truth of an expression involving one or more parameters to the calling method.
     * 确保涉及调用方法的一个或多个参数的表达式的真实性。
     *
     * <p>See {@link #checkArgument(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkArgument(
            boolean b,
            @Nullable String errorMessageTemplate,
            @Nullable Object p1,
            @Nullable Object p2,
            @Nullable Object p3,
            @Nullable Object p4) {
        if (!b) {
            throw new IllegalArgumentException(lenientFormat(errorMessageTemplate, p1, p2, p3, p4));
        }
    }

    /**
     * Ensures the truth of an expression involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     * 确保涉及调用实例状态但不涉及调用方法的任何参数的表达式的真实性。
     *
     * @param expression a boolean expression 一个布尔表达式
     * @throws IllegalStateException if {@code expression} is false
     * @see Verify#verify Verify.verify()
     */
    public static void checkState(boolean expression) {
        if (!expression) {
            throw new IllegalStateException();
        }
    }

    /**
     * Ensures the truth of an expression involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     * 确保涉及调用实例状态但不涉及调用方法的任何参数的表达式的真实性。
     *
     * @param expression   a boolean expression
     * @param errorMessage the exception message to use if the check fails; will be converted to a
     *                     string using {@link String#valueOf(Object)}
     * @throws IllegalStateException if {@code expression} is false
     * @see Verify#verify Verify.verify()
     */
    public static void checkState(boolean expression, @Nullable Object errorMessage) {
        if (!expression) {
            throw new IllegalStateException(String.valueOf(errorMessage));
        }
    }

    /**
     * Ensures the truth of an expression involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     * 确保涉及调用实例状态但不涉及调用方法的任何参数的表达式的真实性。
     *
     * @param expression           a boolean expression
     * @param errorMessageTemplate a template for the exception message should the check fail. The
     *                             message is formed by replacing each {@code %s} placeholder in the template with an
     *                             argument. These are matched by position - the first {@code %s} gets {@code
     *                             errorMessageArgs[0]}, etc. Unmatched arguments will be appended to the formatted message in
     *                             square braces. Unmatched placeholders will be left as-is.
     * @param errorMessageArgs     the arguments to be substituted into the message template. Arguments
     *                             are converted to strings using {@link String#valueOf(Object)}.
     * @throws IllegalStateException if {@code expression} is false
     * @see Verify#verify Verify.verify()
     */
    public static void checkState(
            boolean expression,
            @Nullable String errorMessageTemplate,
            @Nullable Object @Nullable ... errorMessageArgs) {
        if (!expression) {
            throw new IllegalStateException(lenientFormat(errorMessageTemplate, errorMessageArgs));
        }
    }

    /**
     * Ensures the truth of an expression involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     * 确保涉及调用实例状态但不涉及调用方法的任何参数的表达式的真实性。
     *
     * <p>See {@link #checkState(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkState(boolean b, @Nullable String errorMessageTemplate, char p1) {
        if (!b) {
            throw new IllegalStateException(lenientFormat(errorMessageTemplate, p1));
        }
    }

    /**
     * Ensures the truth of an expression involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     * 确保涉及调用实例状态但不涉及调用方法的任何参数的表达式的真实性。
     *
     * <p>See {@link #checkState(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkState(boolean b, @Nullable String errorMessageTemplate, int p1) {
        if (!b) {
            throw new IllegalStateException(lenientFormat(errorMessageTemplate, p1));
        }
    }

    /**
     * Ensures the truth of an expression involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     * 确保涉及调用实例状态但不涉及调用方法的任何参数的表达式的真实性。
     *
     * <p>See {@link #checkState(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkState(boolean b, @Nullable String errorMessageTemplate, long p1) {
        if (!b) {
            throw new IllegalStateException(lenientFormat(errorMessageTemplate, p1));
        }
    }

    /**
     * Ensures the truth of an expression involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     * 确保涉及调用实例状态但不涉及调用方法的任何参数的表达式的真实性。
     *
     * <p>See {@link #checkState(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkState(
            boolean b, @Nullable String errorMessageTemplate, @Nullable Object p1) {
        if (!b) {
            throw new IllegalStateException(lenientFormat(errorMessageTemplate, p1));
        }
    }

    /**
     * Ensures the truth of an expression involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     * 确保涉及调用实例状态但不涉及调用方法的任何参数的表达式的真实性。
     *
     * <p>See {@link #checkState(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkState(
            boolean b, @Nullable String errorMessageTemplate, char p1, char p2) {
        if (!b) {
            throw new IllegalStateException(lenientFormat(errorMessageTemplate, p1, p2));
        }
    }

    /**
     * Ensures the truth of an expression involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     * 确保涉及调用实例状态但不涉及调用方法的任何参数的表达式的真实性。
     *
     * <p>See {@link #checkState(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkState(boolean b, @Nullable String errorMessageTemplate, char p1, int p2) {
        if (!b) {
            throw new IllegalStateException(lenientFormat(errorMessageTemplate, p1, p2));
        }
    }

    /**
     * Ensures the truth of an expression involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     * 确保涉及调用实例状态但不涉及调用方法的任何参数的表达式的真实性。
     *
     * <p>See {@link #checkState(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkState(
            boolean b, @Nullable String errorMessageTemplate, char p1, long p2) {
        if (!b) {
            throw new IllegalStateException(lenientFormat(errorMessageTemplate, p1, p2));
        }
    }

    /**
     * Ensures the truth of an expression involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     * 确保涉及调用实例状态但不涉及调用方法的任何参数的表达式的真实性。
     *
     * <p>See {@link #checkState(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkState(
            boolean b, @Nullable String errorMessageTemplate, char p1, @Nullable Object p2) {
        if (!b) {
            throw new IllegalStateException(lenientFormat(errorMessageTemplate, p1, p2));
        }
    }

    /**
     * Ensures the truth of an expression involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     * 确保涉及调用实例状态但不涉及调用方法的任何参数的表达式的真实性。
     *
     * <p>See {@link #checkState(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkState(boolean b, @Nullable String errorMessageTemplate, int p1, char p2) {
        if (!b) {
            throw new IllegalStateException(lenientFormat(errorMessageTemplate, p1, p2));
        }
    }

    /**
     * Ensures the truth of an expression involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     * 确保涉及调用实例状态但不涉及调用方法的任何参数的表达式的真实性。
     *
     * <p>See {@link #checkState(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkState(boolean b, @Nullable String errorMessageTemplate, int p1, int p2) {
        if (!b) {
            throw new IllegalStateException(lenientFormat(errorMessageTemplate, p1, p2));
        }
    }

    /**
     * Ensures the truth of an expression involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     * 确保涉及调用实例状态但不涉及调用方法的任何参数的表达式的真实性。
     *
     * <p>See {@link #checkState(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkState(boolean b, @Nullable String errorMessageTemplate, int p1, long p2) {
        if (!b) {
            throw new IllegalStateException(lenientFormat(errorMessageTemplate, p1, p2));
        }
    }

    /**
     * Ensures the truth of an expression involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     * 确保涉及调用实例状态但不涉及调用方法的任何参数的表达式的真实性。
     *
     * <p>See {@link #checkState(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkState(
            boolean b, @Nullable String errorMessageTemplate, int p1, @Nullable Object p2) {
        if (!b) {
            throw new IllegalStateException(lenientFormat(errorMessageTemplate, p1, p2));
        }
    }

    /**
     * Ensures the truth of an expression involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     * 确保涉及调用实例状态但不涉及调用方法的任何参数的表达式的真实性。
     *
     * <p>See {@link #checkState(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkState(
            boolean b, @Nullable String errorMessageTemplate, long p1, char p2) {
        if (!b) {
            throw new IllegalStateException(lenientFormat(errorMessageTemplate, p1, p2));
        }
    }

    /**
     * Ensures the truth of an expression involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     * 确保涉及调用实例状态但不涉及调用方法的任何参数的表达式的真实性。
     * <p>See {@link #checkState(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkState(boolean b, @Nullable String errorMessageTemplate, long p1, int p2) {
        if (!b) {
            throw new IllegalStateException(lenientFormat(errorMessageTemplate, p1, p2));
        }
    }

    /**
     * Ensures the truth of an expression involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     * 确保涉及调用实例状态但不涉及调用方法的任何参数的表达式的真实性。
     * <p>See {@link #checkState(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkState(
            boolean b, @Nullable String errorMessageTemplate, long p1, long p2) {
        if (!b) {
            throw new IllegalStateException(lenientFormat(errorMessageTemplate, p1, p2));
        }
    }

    /**
     * Ensures the truth of an expression involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     * 确保涉及调用实例状态但不涉及调用方法的任何参数的表达式的真实性。
     * <p>See {@link #checkState(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkState(
            boolean b, @Nullable String errorMessageTemplate, long p1, @Nullable Object p2) {
        if (!b) {
            throw new IllegalStateException(lenientFormat(errorMessageTemplate, p1, p2));
        }
    }

    /**
     * Ensures the truth of an expression involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     * 确保涉及调用实例状态但不涉及调用方法的任何参数的表达式的真实性。
     * <p>See {@link #checkState(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkState(
            boolean b, @Nullable String errorMessageTemplate, @Nullable Object p1, char p2) {
        if (!b) {
            throw new IllegalStateException(lenientFormat(errorMessageTemplate, p1, p2));
        }
    }

    /**
     * Ensures the truth of an expression involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     * 确保涉及调用实例状态但不涉及调用方法的任何参数的表达式的真实性。
     * <p>See {@link #checkState(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkState(
            boolean b, @Nullable String errorMessageTemplate, @Nullable Object p1, int p2) {
        if (!b) {
            throw new IllegalStateException(lenientFormat(errorMessageTemplate, p1, p2));
        }
    }

    /**
     * Ensures the truth of an expression involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     * 确保涉及调用实例状态但不涉及调用方法的任何参数的表达式的真实性。
     * <p>See {@link #checkState(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkState(
            boolean b, @Nullable String errorMessageTemplate, @Nullable Object p1, long p2) {
        if (!b) {
            throw new IllegalStateException(lenientFormat(errorMessageTemplate, p1, p2));
        }
    }

    /**
     * Ensures the truth of an expression involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     * 确保涉及调用实例状态但不涉及调用方法的任何参数的表达式的真实性。
     * <p>See {@link #checkState(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkState(
            boolean b, @Nullable String errorMessageTemplate, @Nullable Object p1, @Nullable Object p2) {
        if (!b) {
            throw new IllegalStateException(lenientFormat(errorMessageTemplate, p1, p2));
        }
    }

    /**
     * Ensures the truth of an expression involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     * 确保涉及调用实例状态但不涉及调用方法的任何参数的表达式的真实性。
     * <p>See {@link #checkState(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkState(
            boolean b,
            @Nullable String errorMessageTemplate,
            @Nullable Object p1,
            @Nullable Object p2,
            @Nullable Object p3) {
        if (!b) {
            throw new IllegalStateException(lenientFormat(errorMessageTemplate, p1, p2, p3));
        }
    }

    /**
     * Ensures the truth of an expression involving the state of the calling instance, but not
     * involving any parameters to the calling method.
     * 确保涉及调用实例状态但不涉及调用方法的任何参数的表达式的真实性。
     * <p>See {@link #checkState(boolean, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    public static void checkState(
            boolean b,
            @Nullable String errorMessageTemplate,
            @Nullable Object p1,
            @Nullable Object p2,
            @Nullable Object p3,
            @Nullable Object p4) {
        if (!b) {
            throw new IllegalStateException(lenientFormat(errorMessageTemplate, p1, p2, p3, p4));
        }
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     * 确保作为参数传递给调用方法的对象引用不为空。
     *
     * @param reference an object reference
     * @return the non-null reference that was validated
     * @throws NullPointerException if {@code reference} is null
     * @see Verify#verifyNotNull Verify.verifyNotNull()
     */
    @CanIgnoreReturnValue
    public static <T extends @NonNull Object> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     * 确保作为参数传递给调用方法的对象引用不为空。
     *
     * @param reference    an object reference
     * @param errorMessage the exception message to use if the check fails; will be converted to a
     *                     string using {@link String#valueOf(Object)}
     * @return the non-null reference that was validated
     * @throws NullPointerException if {@code reference} is null
     * @see Verify#verifyNotNull Verify.verifyNotNull()
     */
    @CanIgnoreReturnValue
    public static <T extends @NonNull Object> T checkNotNull(
            T reference, @Nullable Object errorMessage) {
        if (reference == null) {
            throw new NullPointerException(String.valueOf(errorMessage));
        }
        return reference;
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     * 确保作为参数传递给调用方法的对象引用不为空。
     *
     * @param reference            an object reference
     * @param errorMessageTemplate a template for the exception message should the check fail. The
     *                             message is formed by replacing each {@code %s} placeholder in the template with an
     *                             argument. These are matched by position - the first {@code %s} gets {@code
     *                             errorMessageArgs[0]}, etc. Unmatched arguments will be appended to the formatted message in
     *                             square braces. Unmatched placeholders will be left as-is.
     * @param errorMessageArgs     the arguments to be substituted into the message template. Arguments
     *                             are converted to strings using {@link String#valueOf(Object)}.
     * @return the non-null reference that was validated
     * @throws NullPointerException if {@code reference} is null
     * @see Verify#verifyNotNull Verify.verifyNotNull()
     */
    @CanIgnoreReturnValue
    public static <T extends @NonNull Object> T checkNotNull(
            T reference, @Nullable String errorMessageTemplate, Object @Nullable ... errorMessageArgs) {
        if (reference == null) {
            throw new NullPointerException(lenientFormat(errorMessageTemplate, errorMessageArgs));
        }
        return reference;
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     * 确保作为参数传递给调用方法的对象引用不为空。
     * <p>See {@link #checkNotNull(Object, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    @CanIgnoreReturnValue
    public static <T extends @NonNull Object> T checkNotNull(
            T obj, @Nullable String errorMessageTemplate, char p1) {
        if (obj == null) {
            throw new NullPointerException(lenientFormat(errorMessageTemplate, p1));
        }
        return obj;
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     * 确保作为参数传递给调用方法的对象引用不为空。
     * <p>See {@link #checkNotNull(Object, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    @CanIgnoreReturnValue
    public static <T extends @NonNull Object> T checkNotNull(
            T obj, @Nullable String errorMessageTemplate, int p1) {
        if (obj == null) {
            throw new NullPointerException(lenientFormat(errorMessageTemplate, p1));
        }
        return obj;
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     * 确保作为参数传递给调用方法的对象引用不为空。
     * <p>See {@link #checkNotNull(Object, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    @CanIgnoreReturnValue
    public static <T extends @NonNull Object> T checkNotNull(
            T obj, @Nullable String errorMessageTemplate, long p1) {
        if (obj == null) {
            throw new NullPointerException(lenientFormat(errorMessageTemplate, p1));
        }
        return obj;
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     * 确保作为参数传递给调用方法的对象引用不为空。
     * <p>See {@link #checkNotNull(Object, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    @CanIgnoreReturnValue
    public static <T extends @NonNull Object> T checkNotNull(
            T obj, @Nullable String errorMessageTemplate, @Nullable Object p1) {
        if (obj == null) {
            throw new NullPointerException(lenientFormat(errorMessageTemplate, p1));
        }
        return obj;
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     * 确保作为参数传递给调用方法的对象引用不为空。
     * <p>See {@link #checkNotNull(Object, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    @CanIgnoreReturnValue
    public static <T extends @NonNull Object> T checkNotNull(
            T obj, @Nullable String errorMessageTemplate, char p1, char p2) {
        if (obj == null) {
            throw new NullPointerException(lenientFormat(errorMessageTemplate, p1, p2));
        }
        return obj;
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     * 确保作为参数传递给调用方法的对象引用不为空。
     * <p>See {@link #checkNotNull(Object, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    @CanIgnoreReturnValue
    public static <T extends @NonNull Object> T checkNotNull(
            T obj, @Nullable String errorMessageTemplate, char p1, int p2) {
        if (obj == null) {
            throw new NullPointerException(lenientFormat(errorMessageTemplate, p1, p2));
        }
        return obj;
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     * 确保作为参数传递给调用方法的对象引用不为空。
     * <p>See {@link #checkNotNull(Object, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    @CanIgnoreReturnValue
    public static <T extends @NonNull Object> T checkNotNull(
            T obj, @Nullable String errorMessageTemplate, char p1, long p2) {
        if (obj == null) {
            throw new NullPointerException(lenientFormat(errorMessageTemplate, p1, p2));
        }
        return obj;
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     * 确保作为参数传递给调用方法的对象引用不为空。
     * <p>See {@link #checkNotNull(Object, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    @CanIgnoreReturnValue
    public static <T extends @NonNull Object> T checkNotNull(
            T obj, @Nullable String errorMessageTemplate, char p1, @Nullable Object p2) {
        if (obj == null) {
            throw new NullPointerException(lenientFormat(errorMessageTemplate, p1, p2));
        }
        return obj;
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     * 确保作为参数传递给调用方法的对象引用不为空。
     * <p>See {@link #checkNotNull(Object, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    @CanIgnoreReturnValue
    public static <T extends @NonNull Object> T checkNotNull(
            T obj, @Nullable String errorMessageTemplate, int p1, char p2) {
        if (obj == null) {
            throw new NullPointerException(lenientFormat(errorMessageTemplate, p1, p2));
        }
        return obj;
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     * 确保作为参数传递给调用方法的对象引用不为空。
     * <p>See {@link #checkNotNull(Object, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    @CanIgnoreReturnValue
    public static <T extends @NonNull Object> T checkNotNull(
            T obj, @Nullable String errorMessageTemplate, int p1, int p2) {
        if (obj == null) {
            throw new NullPointerException(lenientFormat(errorMessageTemplate, p1, p2));
        }
        return obj;
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     * 确保作为参数传递给调用方法的对象引用不为空。
     * <p>See {@link #checkNotNull(Object, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    @CanIgnoreReturnValue
    public static <T extends @NonNull Object> T checkNotNull(
            T obj, @Nullable String errorMessageTemplate, int p1, long p2) {
        if (obj == null) {
            throw new NullPointerException(lenientFormat(errorMessageTemplate, p1, p2));
        }
        return obj;
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     * 确保作为参数传递给调用方法的对象引用不为空。
     * <p>See {@link #checkNotNull(Object, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    @CanIgnoreReturnValue
    public static <T extends @NonNull Object> T checkNotNull(
            T obj, @Nullable String errorMessageTemplate, int p1, @Nullable Object p2) {
        if (obj == null) {
            throw new NullPointerException(lenientFormat(errorMessageTemplate, p1, p2));
        }
        return obj;
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     * 确保作为参数传递给调用方法的对象引用不为空。
     * <p>See {@link #checkNotNull(Object, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    @CanIgnoreReturnValue
    public static <T extends @NonNull Object> T checkNotNull(
            T obj, @Nullable String errorMessageTemplate, long p1, char p2) {
        if (obj == null) {
            throw new NullPointerException(lenientFormat(errorMessageTemplate, p1, p2));
        }
        return obj;
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     * 确保作为参数传递给调用方法的对象引用不为空。
     * <p>See {@link #checkNotNull(Object, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    @CanIgnoreReturnValue
    public static <T extends @NonNull Object> T checkNotNull(
            T obj, @Nullable String errorMessageTemplate, long p1, int p2) {
        if (obj == null) {
            throw new NullPointerException(lenientFormat(errorMessageTemplate, p1, p2));
        }
        return obj;
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     * 确保作为参数传递给调用方法的对象引用不为空。
     * <p>See {@link #checkNotNull(Object, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    @CanIgnoreReturnValue
    public static <T extends @NonNull Object> T checkNotNull(
            T obj, @Nullable String errorMessageTemplate, long p1, long p2) {
        if (obj == null) {
            throw new NullPointerException(lenientFormat(errorMessageTemplate, p1, p2));
        }
        return obj;
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     * 确保作为参数传递给调用方法的对象引用不为空。
     * <p>See {@link #checkNotNull(Object, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    @CanIgnoreReturnValue
    public static <T extends @NonNull Object> T checkNotNull(
            T obj, @Nullable String errorMessageTemplate, long p1, @Nullable Object p2) {
        if (obj == null) {
            throw new NullPointerException(lenientFormat(errorMessageTemplate, p1, p2));
        }
        return obj;
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     * 确保作为参数传递给调用方法的对象引用不为空。
     * <p>See {@link #checkNotNull(Object, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    @CanIgnoreReturnValue
    public static <T extends @NonNull Object> T checkNotNull(
            T obj, @Nullable String errorMessageTemplate, @Nullable Object p1, char p2) {
        if (obj == null) {
            throw new NullPointerException(lenientFormat(errorMessageTemplate, p1, p2));
        }
        return obj;
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     * 确保作为参数传递给调用方法的对象引用不为空。
     * <p>See {@link #checkNotNull(Object, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    @CanIgnoreReturnValue
    public static <T extends @NonNull Object> T checkNotNull(
            T obj, @Nullable String errorMessageTemplate, @Nullable Object p1, int p2) {
        if (obj == null) {
            throw new NullPointerException(lenientFormat(errorMessageTemplate, p1, p2));
        }
        return obj;
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     * 确保作为参数传递给调用方法的对象引用不为空。
     * <p>See {@link #checkNotNull(Object, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    @CanIgnoreReturnValue
    public static <T extends @NonNull Object> T checkNotNull(
            T obj, @Nullable String errorMessageTemplate, @Nullable Object p1, long p2) {
        if (obj == null) {
            throw new NullPointerException(lenientFormat(errorMessageTemplate, p1, p2));
        }
        return obj;
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     * 确保作为参数传递给调用方法的对象引用不为空。
     * <p>See {@link #checkNotNull(Object, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    @CanIgnoreReturnValue
    public static <T extends @NonNull Object> T checkNotNull(
            T obj, @Nullable String errorMessageTemplate, @Nullable Object p1, @Nullable Object p2) {
        if (obj == null) {
            throw new NullPointerException(lenientFormat(errorMessageTemplate, p1, p2));
        }
        return obj;
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     * 确保作为参数传递给调用方法的对象引用不为空。
     * <p>See {@link #checkNotNull(Object, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    @CanIgnoreReturnValue
    public static <T extends @NonNull Object> T checkNotNull(
            T obj,
            @Nullable String errorMessageTemplate,
            @Nullable Object p1,
            @Nullable Object p2,
            @Nullable Object p3) {
        if (obj == null) {
            throw new NullPointerException(lenientFormat(errorMessageTemplate, p1, p2, p3));
        }
        return obj;
    }

    /**
     * Ensures that an object reference passed as a parameter to the calling method is not null.
     * 确保作为参数传递给调用方法的对象引用不为空。
     * <p>See {@link #checkNotNull(Object, String, Object...)} for details.
     *
     * @since 20.0 (varargs overload since 2.0)
     */
    @CanIgnoreReturnValue
    public static <T extends @NonNull Object> T checkNotNull(
            T obj,
            @Nullable String errorMessageTemplate,
            @Nullable Object p1,
            @Nullable Object p2,
            @Nullable Object p3,
            @Nullable Object p4) {
        if (obj == null) {
            throw new NullPointerException(lenientFormat(errorMessageTemplate, p1, p2, p3, p4));
        }
        return obj;
    }

    /*
     * All recent hotspots (as of 2009) *really* like to have the natural code
     *
     * if (guardExpression) {
     *    throw new BadException(messageExpression);
     * }
     *
     * refactored so that messageExpression is moved to a separate String-returning method.
     *
     * if (guardExpression) {
     *    throw new BadException(badMsg(...));
     * }
     *
     * The alternative natural refactorings into void or Exception-returning methods are much slower.
     * This is a big deal - we're talking factors of 2-8 in microbenchmarks, not just 10-20%. (This is
     * a hotspot optimizer bug, which should be fixed, but that's a separate, big project).
     *
     * The coding pattern above is heavily used in java.util, e.g. in ArrayList. There is a
     * RangeCheckMicroBenchmark in the JDK that was used to test this.
     *
     * But the methods in this class want to throw different exceptions, depending on the args, so it
     * appears that this pattern is not directly applicable. But we can use the ridiculous, devious
     * trick of throwing an exception in the middle of the construction of another exception. Hotspot
     * is fine with that.
     */

    /**
     * Ensures that {@code index} specifies a valid <i>element</i> in an array, list or string of size
     * {@code size}. An element index may range from zero, inclusive, to {@code size}, exclusive.
     * 确保{@code index}在大小为{@code size}的数组、列表或字符串中指定一个有效的<i>元素<i>。元素索引的范围可以从0(包含)到{@code size}(不包含)。
     *
     * @param index a user-supplied index identifying an element of an array, list or string
     * @param size  the size of that array, list or string
     * @return the value of {@code index}
     * @throws IndexOutOfBoundsException if {@code index} is negative or is not less than {@code size}
     * @throws IllegalArgumentException  if {@code size} is negative
     */
    @CanIgnoreReturnValue
    public static int checkElementIndex(int index, int size) {
        return checkElementIndex(index, size, "index");
    }

    /**
     * Ensures that {@code index} specifies a valid <i>element</i> in an array, list or string of size
     * {@code size}. An element index may range from zero, inclusive, to {@code size}, exclusive.
     * 确保{@code index}在大小为{@code size}的数组、列表或字符串中指定一个有效的<i>元素<i>。元素索引的范围可以从0(包含)到{@code size}(不包含)。
     *
     * @param index a user-supplied index identifying an element of an array, list or string
     * @param size  the size of that array, list or string
     * @param desc  the text to use to describe this index in an error message
     * @return the value of {@code index}
     * @throws IndexOutOfBoundsException if {@code index} is negative or is not less than {@code size}
     * @throws IllegalArgumentException  if {@code size} is negative
     */
    @CanIgnoreReturnValue
    public static int checkElementIndex(int index, int size, @Nullable String desc) {
        // Carefully optimized for execution by hotspot (explanatory comment above)
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(badElementIndex(index, size, desc));
        }
        return index;
    }

    private static String badElementIndex(int index, int size, @Nullable String desc) {
        if (index < 0) {
            return lenientFormat("%s (%s) must not be negative", desc, index);
        } else if (size < 0) {
            throw new IllegalArgumentException("negative size: " + size);
        } else { // index >= size
            return lenientFormat("%s (%s) must be less than size (%s)", desc, index, size);
        }
    }

    /**
     * Ensures that {@code index} specifies a valid <i>position</i> in an array, list or string of
     * size {@code size}. A position index may range from zero to {@code size}, inclusive.
     * 确保{@code index}在大小为{@code size}的数组、列表或字符串中指定一个有效的<i>位置<i>。位置索引可以从0到{@code size}，包括在内。
     *
     * @param index a user-supplied index identifying a position in an array, list or string
     * @param size  the size of that array, list or string
     * @return the value of {@code index}
     * @throws IndexOutOfBoundsException if {@code index} is negative or is greater than {@code size}
     * @throws IllegalArgumentException  if {@code size} is negative
     */
    @CanIgnoreReturnValue
    public static int checkPositionIndex(int index, int size) {
        return checkPositionIndex(index, size, "index");
    }

    /**
     * Ensures that {@code index} specifies a valid <i>position</i> in an array, list or string of
     * size {@code size}. A position index may range from zero to {@code size}, inclusive.
     * 确保{@code index}在大小为{@code size}的数组、列表或字符串中指定一个有效的<i>位置<i>。位置索引可以从0到{@code size}，包括在内。
     *
     * @param index a user-supplied index identifying a position in an array, list or string
     * @param size  the size of that array, list or string
     * @param desc  the text to use to describe this index in an error message
     * @return the value of {@code index}
     * @throws IndexOutOfBoundsException if {@code index} is negative or is greater than {@code size}
     * @throws IllegalArgumentException  if {@code size} is negative
     */
    @CanIgnoreReturnValue
    public static int checkPositionIndex(int index, int size, @Nullable String desc) {
        // Carefully optimized for execution by hotspot (explanatory comment above)
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(badPositionIndex(index, size, desc));
        }
        return index;
    }

    private static String badPositionIndex(int index, int size, @Nullable String desc) {
        if (index < 0) {
            return lenientFormat("%s (%s) must not be negative", desc, index);
        } else if (size < 0) {
            throw new IllegalArgumentException("negative size: " + size);
        } else { // index > size
            return lenientFormat("%s (%s) must not be greater than size (%s)", desc, index, size);
        }
    }

    /**
     * Ensures that {@code start} and {@code end} specify a valid <i>positions</i> in an array, list
     * or string of size {@code size}, and are in order. A position index may range from zero to
     * {@code size}, inclusive.
     * 确保{@code start}和{@code end}在大小为{@code size}的数组、列表或字符串中指定一个有效的<i>位置<i>，并且是有序的。位置索引可以从0到{@code size}，包括在内。
     *
     * @param start a user-supplied index identifying a starting position in an array, list or string
     * @param end   a user-supplied index identifying a ending position in an array, list or string
     * @param size  the size of that array, list or string
     * @throws IndexOutOfBoundsException if either index is negative or is greater than {@code size},
     *                                   or if {@code end} is less than {@code start}
     * @throws IllegalArgumentException  if {@code size} is negative
     */
    public static void checkPositionIndexes(int start, int end, int size) {
        // Carefully optimized for execution by hotspot (explanatory comment above)
        if (start < 0 || end < start || end > size) {
            throw new IndexOutOfBoundsException(badPositionIndexes(start, end, size));
        }
    }

    private static String badPositionIndexes(int start, int end, int size) {
        if (start < 0 || start > size) {
            return badPositionIndex(start, size, "start index");
        }
        if (end < 0 || end > size) {
            return badPositionIndex(end, size, "end index");
        }
        // end < start
        return lenientFormat("end index (%s) must not be less than start index (%s)", end, start);
    }

}
