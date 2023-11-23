package io.github.kuugasky.kuugatool.core.collection.sort;

import io.github.kuugasky.kuugatool.core.object.ObjectUtil;

/**
 * SortBy
 *
 * @author kuuga
 * @since 2022/7/1
 */
public enum SortBy {

    /**
     *
     */
    ASC,
    DESC,
    ;

    public static boolean isDesc(SortBy sortBy) {
        return ObjectUtil.equals(sortBy, DESC);
    }

    public static boolean isAsc(SortBy sortBy) {
        return ObjectUtil.equals(sortBy, ASC);
    }

}
