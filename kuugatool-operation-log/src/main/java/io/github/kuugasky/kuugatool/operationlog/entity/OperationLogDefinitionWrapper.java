package io.github.kuugasky.kuugatool.operationlog.entity;

import lombok.Data;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * OperationLogDefinition包装类
 *
 * @author pengqinglong
 * @since 2021/3/23
 */
@Data
public class OperationLogDefinitionWrapper {

    /**
     * 操作日志定义集合
     */
    private List<OperationLogDefinition> definitionList;

    /**
     * 需要合并的操作日志定义集合
     */
    private List<OperationLogDefinition> mergeList;

    /**
     * 新增还是修改 true:新增 false:修改
     */
    private boolean addOrEdit;

    /**
     * 字段对应的新数据对象
     */
    private Map<Field, OperationLogEntity> fieldEntityMapping;

    /**
     * 每个对象class对应的OperationLogDefinition
     */
    private Map<Class<?>, List<OperationLogDefinition>> classDefinitionMapping;

    /**
     * 旧数据对象
     */
    private Object historyEntity;

    /**
     * 新数据对象
     */
    private Object nowEntity;

    public OperationLogDefinitionWrapper() {
    }

    public OperationLogDefinitionWrapper(OperationLogDefinitionWrapper wrapper) {
        this.definitionList = wrapper.definitionList;
        this.mergeList = wrapper.mergeList;
        this.addOrEdit = wrapper.isAddOrEdit();
        this.fieldEntityMapping = wrapper.getFieldEntityMapping();
        this.classDefinitionMapping = wrapper.getClassDefinitionMapping();
        this.historyEntity = wrapper.getHistoryEntity();
        this.nowEntity = wrapper.getNowEntity();
    }

}