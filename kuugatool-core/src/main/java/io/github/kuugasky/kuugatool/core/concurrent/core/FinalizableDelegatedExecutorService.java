package io.github.kuugasky.kuugatool.core.concurrent.core;

import java.util.concurrent.ExecutorService;

/**
 * 保证ExecutorService在对象回收时正常结束
 * <p>
 * 可终结的委托执行者服务
 *
 * @author loolly
 */
public class FinalizableDelegatedExecutorService extends DelegatedExecutorService {

    /**
     * 构造
     *
     * @param executor {@link ExecutorService}
     */
    FinalizableDelegatedExecutorService(ExecutorService executor) {
        super(executor);
    }

    // @Override
    // @Deprecated(since = "9")
    // protected void finalize() {
    //     super.shutdown();
    // }

}