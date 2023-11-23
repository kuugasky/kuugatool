package io.github.kuugasky.kuugatool.operationlog.annotations;

import io.github.kuugasky.kuugatool.operationlog.core.OperationDefaultHandle;
import io.github.kuugasky.kuugatool.operationlog.core.OperationHandle;

import java.lang.annotation.*;
import java.util.List;

/**
 * 操作记录类注解
 *
 * @author 彭清龙
 * @since 7:12 下午 2021/3/22
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperationLog {

    /**
     * 待格式化字符串 必填
     * 使用 {@link OperationHandle#NOW}和{@link OperationHandle#HISTORY}作为nowValue和historyValue的占位符
     * <p>
     * 使用规则
     * <p>
     * 正常操作日志分为2类 新增和修改
     * 新增情况：
     * format：新增${now}值   此时新增的字段值为作为 nowValue 此时nowValue不能为空 且 format中必须存在${now}占位符 否则抛出异常
     * 修改情况：
     * 1. 新增信息：
     * format：新增${now}值            此时新增的字段值为作为 nowValue
     * format：由空更新为{now}值        此时nowValue不能为空 且 format中必须存在${now}占位符 否则抛出异常
     * 2. 修改信息
     * format：由${history}更新为{now}值  此时旧值作为 historyValue 新值 nowValue 都不能为空 且format中必须存在${now}和${history}占位符 否则抛出异常
     * 3. 删除信息
     * format：删除${history}值            此时旧值作为 historyValue 不能为空  且format中必须存在${history}占位符 否则抛出异常
     * format：由${history}值更新为空
     * <p>
     * 新增情况和新增信息情况相同
     * 通过historyEntity对象进行判断
     * 新增的时候historyEntity是为空的
     * 修改的时候historyEntity是有值的
     * <p>
     * 共性抽取
     * 共分为4类情况
     * 所以数组长度最多4个就可以包含所有情况
     * 如果超过四个 会按上面的匹配规则 优先匹配成功就返回 后面的直接无视
     * <p>
     * 有任何疑问 请参考{@link OperationHandle#stringFormat(List, boolean, Object, Object) }方法 源码给你答案
     *
     * @author 彭清龙
     **/
    String[] stringFormats();

    /**
     * 操作记录的模块名
     **/
    String moduleName();

    /**
     * 字段是否拼接模块名 每个字段
     * 与{@link OperationLogField#isJoinModuleName()}的优先级
     * 基于{@link #priorityClass}进行判断
     */
    boolean isJoinModuleName() default false;

    /**
     * 默认字段优先 修改后class优先
     */
    boolean priorityClass() default false;

    /**
     * 使用默认的handle进行实际的日志内容拼接处理
     **/
    Class<? extends OperationHandle> handle() default OperationDefaultHandle.class;
}
