package io.github.kuugasky.kuugatool.core.concurrent.joinmerge;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * ThreadJoinMerge
 *
 * @author kuuga
 * @since 2021-04-04
 */
public class ThreadJoinMerge {

    public static ThreadJoinMerge init() {
        return new ThreadJoinMerge();
    }

    private final List<Callable<Object>> callables = ListUtil.newArrayList();

    public ThreadJoinMerge submit(Callable<Object> callable) {
        callables.add(callable);
        return this;
    }

    public void invokeAll() {
        ThreadPoolJoinMerge.invokeAllTask(callables);
    }

}
