package io.github.kuugasky.kuugatool.operationlog.annotations;

import io.github.kuugasky.kuugatool.operationlog.core.OperationHandle;
import io.github.kuugasky.kuugatool.operationlog.enums.FieldTypeEnum;
import io.github.kuugasky.kuugatool.operationlog.enums.OtherFiledLocationEnum;

import java.lang.annotation.*;

/**
 * 操作记录字段注解
 * <p>
 * {@link #isSensitive} 为true的时候
 * 在处理字段值时通过{@link #headRetain} 和{@link #tailRetain} 对字段值进行脱敏处理
 * {@link #isSensitive} 为true的时候
 * 在处理需要合并的字段值时
 * 通过相同的{@link #mergeName} 进行获取合并的字段名
 * 使用{@link #mergeSum} 获得需要合并的字段数量
 * 使用{@link #mergeIndex} 对合并字段进行排序
 * 使用{@link #mergePrefix} 和 {@link #mergeSuffix} 对合并字段进行分割拼接
 *
 * @author 彭清龙
 * @since 7:21 下午 2021/3/22
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperationLogField {

    /**
     * NORMAL：普通字段
     * OBJECT：对象字段
     * NORMAL_LIST：普通list
     * OBJECT_LIST：对象list
     */
    FieldTypeEnum fieldType() default FieldTypeEnum.NORMAL;

    /**
     * 操作记录字段的模块名
     * 最优先取当前字段上的模块名
     * 如果为空 判断 如果自己的类是别的对象的字段 获取类作为字段时的字段名为模块名
     * 如果都没有会默认使用{@link OperationLog#moduleName}
     **/
    String moduleName() default "";

    /**
     * {@link #moduleName}是否要拼接模块名
     * <p>
     * 模块名:{@link OperationLog#moduleName()}或者{@link OperationLogField#moduleName()}
     */
    boolean isJoinModuleName() default false;

    /**
     * {@link #moduleName}对象list是否每一个元素都要拼接模块名
     */
    boolean isJoinModuleNameEveryone() default true;

    /**
     * 字段名
     **/
    String fieldName();

    /**
     * {@link #fieldName}是否要拼接字段名
     */
    boolean isJoinFieldName() default true;

    /**
     * 字段下标 用于排序 非必须
     **/
    int order() default 0;

    /**
     * 是否要拼接其他字段
     */
    boolean isJoinOtherFiled() default false;

    /**
     * 其他字段的位置
     */
    OtherFiledLocationEnum otherFiledLocation() default OtherFiledLocationEnum.VALUE_BEFORE;

    /**
     * 连接其他字段前缀
     */
    String otherFiledPrefix() default "";

    /**
     * 连接其他字段
     */
    String otherFiledName() default "";

    /**
     * 连接其他字段后缀
     */
    String otherFiledSuffix() default "";

    /**
     * 模块首领字段，用于模块名拼接
     * true后面所有为false的字段均为该字段的马仔 直到下一个true出现
     * 在更新的一些情况下 包含模块名的字段会没有拼接上
     * 此时通过该字段进行查找，找到模块首领字段获取{@link #moduleName}和{@link #isJoinModuleName}进行模块名拼接
     */
    boolean leader() default false;

    /**
     * 前缀
     */
    String prefix() default OperationHandle.DEFAULT_PREFIX;

    /**
     * 分隔符 在字段是List<普通类型>时使用
     */
    String separator() default "";

    /**
     * 后缀
     */
    String suffix() default "，";

    /**
     * 是否敏感信息需要加密
     **/
    boolean isSensitive() default false;

    /**
     * 基于{@link #isSensitive} 为true的情况
     * 如果是敏感信息 头部保留多少位
     **/
    int headRetain() default 3;

    /**
     * 基于{@link #isSensitive} 为true的情况
     * 如果是敏感信息 尾部保留多少位
     **/
    int tailRetain() default 4;

    /**
     * 字段是否需要合并
     **/
    boolean isMerge() default false;

    /**
     * 基于{@link #isMerge} 为true的情况
     * 合并后的字段名 为空会使用{@link #fieldName}
     **/
    String mergeName() default "";

    /**
     * 基于{@link #isMerge} 为true的情况
     * 一共合并多少个字段 与{@link #mergeIndex}进行联动
     **/
    int mergeSum() default 1;

    /**
     * 基于{@link #isMerge} 为true的情况
     * 合并排序下标 标示哪个在前哪个在后 从小到大
     **/
    int mergeIndex() default -1;

    /**
     * 基于{@link #isMerge} 为true的情况
     * 合并分割符 会拼接在字段值的前面
     **/
    String mergePrefix() default "";

    /**
     * 基于{@link #isMerge} 为true的情况
     * 合并分割符 会拼接在字段值的后面
     **/
    String mergeSuffix() default "";

    /**
     * 跳过本字段 不对比
     */
    boolean skip() default false;
}
