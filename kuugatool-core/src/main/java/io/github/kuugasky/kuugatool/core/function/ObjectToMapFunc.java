package io.github.kuugasky.kuugatool.core.function;

import java.util.Map;

/**
 * 对象转Map函数
 *
 * @author kuuga
 */
@FunctionalInterface
public interface ObjectToMapFunc {

    /**
     * process
     *
     * @param map map
     */
    void process(Map<String, Object> map);

}
