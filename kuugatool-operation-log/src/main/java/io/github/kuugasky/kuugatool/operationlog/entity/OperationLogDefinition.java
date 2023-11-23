package io.github.kuugasky.kuugatool.operationlog.entity;

import io.github.kuugasky.kuugatool.operationlog.enums.FieldTypeEnum;
import io.github.kuugasky.kuugatool.operationlog.enums.OtherFiledLocationEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;
import java.util.List;

/**
 * OperationLogDefinition
 *
 * @author pengqinglong
 * @since 2021/3/22
 */
@Data
@NoArgsConstructor
public class OperationLogDefinition {

    /**
     * 需要格式化的字符串
     */
    private List<String> stringFormatList;

    /**
     * 字段类型
     */
    private FieldTypeEnum fieldType;

    /**
     * 模块名
     */
    private String moduleName;

    /**
     * 是否要拼接模块名
     */
    private boolean isJoinModuleName;

    /**
     * 对象list是否每一个元素都要拼接模块名
     */
    private boolean isJoinModuleNameEveryone;

    /**
     * 字段名
     */
    private String fieldName;

    /**
     * 是否跳过
     */
    private boolean skip;

    /**
     * 是否要拼接字段名
     */
    private boolean isJoinFieldName;

    /**
     * 父字段下标
     */
    private int parentOrder;

    /**
     * 排序下标
     */
    private int order;

    /**
     * 前缀
     */
    private String prefix;

    /**
     * 分隔符
     */
    private String separator;

    /**
     * 后缀
     */
    private String suffix;

    /**
     * 是否敏感加密
     */
    private boolean isSensitive;

    /**
     * 头部保留位数
     */
    private int headRetain;

    /**
     * 尾部保留位数
     */
    private int tailRetain;

    /**
     * 是否需要合并
     */
    private boolean isMerge;

    /**
     * 合并后的字段名
     */
    private String mergeName;

    /**
     * 需要合并的字段总数
     */
    private int mergeSum;

    /**
     * 合并字段排序下标
     */
    private int mergeIndex;

    /**
     * 合并时字段值的拼接前缀
     */
    private String mergePrefix;

    /**
     * 合并时字段值的拼接后缀
     */
    private String mergeSuffix;

    /**
     * 模块boss字段
     */
    private boolean leader;

    /**
     * 是否要拼接其他字段
     */
    private boolean isJoinOtherFiled;

    /**
     * 连接其他字段前缀
     */
    private String otherFiledPrefix;

    /**
     * 其他字段拼接位子
     */
    private OtherFiledLocationEnum otherFiledLocation;

    /**
     * 连接其他字段
     */
    private String otherFiledName;

    /**
     * 连接其他字段后缀
     */
    private String otherFiledSuffix;

    /**
     * 字段
     */
    private Field field;


    /**
     * 获取排序 优先返回父字段排序 父字段为0时返回自身的order
     */
    public int getDefinitionOrder() {
        if (parentOrder > 0) {
            return parentOrder;
        }
        return order;
    }

    /**
     * 获取字段真实名字
     */
    public String getFieldRealName() {
        return field == null ? "" : field.getName();
    }

}