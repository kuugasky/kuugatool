package io.github.kuugasky.kuugatool.operationlog.entity;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * OperationLogEntity
 *
 * @author pengqinglong
 * @since 2021/3/23
 */
@Data
@RequiredArgsConstructor
public class OperationLogEntity {

    /**
     * 旧数据对象
     */
    private Object historyEntity;

    /**
     * 新数据对象
     */
    private Object nowEntity;

    /**
     * 对象的class
     */
    @NonNull
    private Class<?> clazz;

    public Object getHistoryEntity() {
        try {
            return historyEntity == null ? clazz.getDeclaredConstructor().newInstance() : historyEntity;
        } catch (Exception e) {
            return null;
        }
    }

    public Object getNowEntity() {
        try {
            return nowEntity == null ? clazz.getDeclaredConstructor().newInstance() : nowEntity;
        } catch (Exception e) {
            return null;
        }
    }

}