package io.github.kuugasky.kuugatool.core.concurrent.core;

import java.io.Serializable;

/**
 * 建造者模式-接口定义
 *
 * @param <T> 建造对象类型
 * @author Looly
 * @since 4.2.2
 */
public interface Builder<T> extends Serializable {
    /**
     * 构建
     *
     * @return 被构建的对象
     */
    T build();
}