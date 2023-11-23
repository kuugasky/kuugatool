package io.github.kuugasky.kuugatool.core.concurrent.synctask;

/**
 * 返回结果接口
 *
 * @author pengqinglong
 */
public interface Result {

    /**
     * 获取主键
     *
     * @return primaryKey
     */
    String getPrimaryKey();

    /**
     * 获取计算结果
     *
     * @return result
     */
    Object getResult();

}