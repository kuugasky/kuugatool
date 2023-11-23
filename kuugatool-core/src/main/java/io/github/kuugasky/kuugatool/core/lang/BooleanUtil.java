package io.github.kuugasky.kuugatool.core.lang;

/**
 * BooleanUtil
 *
 * @author kuuga
 * @since 2021/12/8
 */
public class BooleanUtil {

    public static boolean isTrue(Boolean bool) {
        return Boolean.TRUE.equals(bool);
    }

    public static boolean isNotTrue(Boolean bool) {
        return !isTrue(bool);
    }

    public static boolean isFalse(Boolean bool) {
        return Boolean.FALSE.equals(bool);
    }

    public static boolean isNotFalse(Boolean bool) {
        return !isFalse(bool);
    }

    public static boolean toBoolean(Boolean bool) {
        return bool != null && bool;
    }

    public static boolean toBooleanDefaultIfNull(Boolean bool, boolean valueIfNull) {
        return bool == null ? valueIfNull : bool;
    }


}
