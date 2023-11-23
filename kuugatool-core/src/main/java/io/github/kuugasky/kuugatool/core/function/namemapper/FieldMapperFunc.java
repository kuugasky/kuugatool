package io.github.kuugasky.kuugatool.core.function.namemapper;

/**
 * 字段映射函数
 *
 * @author kuuga
 * @since 2021/2/8
 */
@FunctionalInterface
public interface FieldMapperFunc<T, R> {

    /**
     * 执行器
     *
     * @param t 源数据对象
     * @param r 目标数据对象
     */
    void execute(T t, R r);

}
