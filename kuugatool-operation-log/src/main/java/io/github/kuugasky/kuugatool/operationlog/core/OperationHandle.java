package io.github.kuugasky.kuugatool.operationlog.core;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.collection.MapUtil;
import io.github.kuugasky.kuugatool.core.date.DateFormat;
import io.github.kuugasky.kuugatool.core.date.DateUtil;
import io.github.kuugasky.kuugatool.core.instance.ReflectionUtil;
import io.github.kuugasky.kuugatool.core.object.MapperUtil;
import io.github.kuugasky.kuugatool.core.string.SensitiveUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import io.github.kuugasky.kuugatool.operationlog.annotations.OperationLog;
import io.github.kuugasky.kuugatool.operationlog.annotations.OperationLogField;
import io.github.kuugasky.kuugatool.operationlog.entity.OperationLogDefinition;
import io.github.kuugasky.kuugatool.operationlog.entity.OperationLogDefinitionWrapper;
import io.github.kuugasky.kuugatool.operationlog.entity.OperationLogEntity;
import io.github.kuugasky.kuugatool.operationlog.entity.OperationLogFieldWrapper;
import io.github.kuugasky.kuugatool.operationlog.enums.FieldTypeEnum;
import io.github.kuugasky.kuugatool.operationlog.enums.OtherFiledLocationEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 操作记录处理器
 *
 * @author pengqinglong
 * @since 2021/3/22
 */
@Slf4j
public abstract class OperationHandle {

    /**
     * stringFormat中的nowValue占位符
     */
    public static final String NOW = "${now}";
    /**
     * stringFormat中的historyValue占位符
     */
    public static final String HISTORY = "${history}";
    /**
     * 拼接字段的默认前缀
     */
    public static final String DEFAULT_PREFIX = ":";

    /**
     * 处理新旧对象之间的字段
     *
     * @param nowEntity 新对象
     * @param history   旧对象
     * @param clazz     类型
     * @return 拼接好的操作记录
     * @throws Exception 异常
     */
    public final String handle(Object nowEntity, Object history, Class<?> clazz) throws Exception {

        // 两个对象不能同时为空
        if (Objects.isNull(nowEntity) && Objects.isNull(history)) {
            throw new NullPointerException();
        }

        // 单个为空时互相生成
        boolean addOrEdit = false;
        if (Objects.isNull(history)) {
            // history为空时判断为新增操作
            addOrEdit = true;
            history = clazz.getDeclaredConstructor().newInstance();
        } else if (Objects.isNull(nowEntity)) {
            nowEntity = clazz.getDeclaredConstructor().newInstance();
        }

        // 开始处理
        OperationLog operationLog = clazz.getAnnotation(OperationLog.class);
        if (Objects.isNull(operationLog)) {
            throw new RuntimeException("clazz对象没有@OperationLog注解");
        }

        // 存储字段与实体对象的映射
        Map<Field, OperationLogEntity> fieldEntityMap = MapUtil.newHashMap();

        // 存储是object或objectList的字段 在下面handleClassDefinitionMapping中进行处理
        List<Field> objFieldList = ListUtil.newArrayList();

        // 封装wrapper
        OperationLogDefinitionWrapper wrapper = new OperationLogDefinitionWrapper();
        wrapper.setAddOrEdit(addOrEdit);
        wrapper.setFieldEntityMapping(fieldEntityMap);

        // 封装新值
        Object nowEntityCopy = MapperUtil.copy(nowEntity, clazz);
        wrapper.setNowEntity(nowEntityCopy);

        // 封装旧对象
        Object historyCopy = MapperUtil.copy(history, clazz);
        wrapper.setHistoryEntity(historyCopy);

        // 过滤非注解字段 同时在内部对字段的数据对象进行映射
        List<OperationLogFieldWrapper> fieldList = this.handleFieldListMapping(clazz, fieldEntityMap, objFieldList, nowEntityCopy, historyCopy);

        // 封装definition
        List<OperationLogDefinition> definitionList = ListUtil.newArrayList();
        for (OperationLogFieldWrapper fieldWrapper : fieldList) {
            Field field = fieldWrapper.getField();
            OperationLogField operationLogField = field.getAnnotation(OperationLogField.class);

            // 生成definition
            OperationLogDefinition definition = this.packageDefinition(fieldWrapper, operationLog, operationLogField);
            definition.setField(field);
            field.setAccessible(true);
            definition.setParentOrder(fieldWrapper.getParentOrder());

            // 不将控制权完全放在使用者那边 如果是默认的普通字段 再校验一遍字段类型
            if (Objects.equals(FieldTypeEnum.NORMAL, definition.getFieldType())) {
                // 对象list
                if (isObjectList(field)) {
                    definition.setFieldType(FieldTypeEnum.OBJECT_LIST);
                } else if (field.getType() == List.class) {
                    // 普通list
                    definition.setFieldType(FieldTypeEnum.NORMAL_LIST);
                }
            }

            definitionList.add(definition);
        }

        // 根据order排序
        // 将字段按照order排序，将 对象字段的字段排序在对象字段的后面
        List<OperationLogDefinition> orderList = definitionList.stream()
                // 第一遍排序将所有的子字段和父字段排在一起
                .sorted(Comparator.comparing(OperationLogDefinition::getDefinitionOrder)
                        // 第二遍排序将父字段排在最前然后子字段按照自身的order排序
                        .thenComparing(definition -> definition.getParentOrder() == 0 ? 0 : definition.getOrder()))
                .collect(Collectors.toList());
        wrapper.setDefinitionList(orderList);

        // 封装合并字段 先根据合并字段名聚合再根据合并下标排序
        List<OperationLogDefinition> mergeList = definitionList.stream()
                .filter(OperationLogDefinition::isMerge)
                .sorted(Comparator.comparing(OperationLogDefinition::getMergeName).thenComparingInt(OperationLogDefinition::getOrder))
                .collect(Collectors.toList());
        wrapper.setMergeList(mergeList);

        // 处理对象字段class与对象字段的字段definition映射
        Map<Class<?>, List<OperationLogDefinition>> classListMapping = this.handleClassDefinitionMapping(objFieldList, orderList);
        wrapper.setClassDefinitionMapping(classListMapping);

        return joinContent(wrapper);
    }

    /**
     * 判断字段是否是对象集合
     *
     * @param field field
     * @return boolean
     */
    private boolean isObjectList(Field field) {
        return field.getType() == List.class && Objects.nonNull(ReflectionUtil.getFieldClass(field).getAnnotation(OperationLog.class));
    }

    /**
     * 处理字段对象class与definition之间的映射关系
     *
     * @param definitionList 操作记录定义集合
     */
    private Map<Class<?>, List<OperationLogDefinition>> handleClassDefinitionMapping(List<Field> objFieldList, List<OperationLogDefinition> definitionList) {
        Map<Class<?>, List<OperationLogDefinition>> mapping = MapUtil.newHashMap();

        objFieldList.forEach(field -> {
            List<OperationLogDefinition> list = ListUtil.newArrayList();

            // 判断对象是否是List集合
            Class<?> fieldClass = ReflectionUtil.getFieldClass(field);

            definitionList.stream()
                    .filter(definition -> Objects.equals(fieldClass, definition.getField().getDeclaringClass()))
                    .forEach(list::add);

            // 字段如果是对象集合 那么后续会通过mapping单独处理 所以移除出definitionList
            if (isObjectList(field)) {
                definitionList.removeAll(list);
            }
            mapping.put(fieldClass, list);
        });
        return mapping;
    }

    /**
     * 过滤所有非注解字段 深度优先递归获取对象的字段
     *
     * @param clazz          class对象
     * @param fieldEntityMap 字段与实体映射
     * @param objFieldList   对象字段集合
     * @param nowEntity      新实体数据
     * @param historyEntity  旧实体数据
     */
    private List<OperationLogFieldWrapper> handleFieldListMapping(Class<?> clazz, Map<Field, OperationLogEntity> fieldEntityMap, List<Field> objFieldList, Object nowEntity, Object historyEntity) {
        return handleFieldListMapping(clazz, fieldEntityMap, objFieldList, nowEntity, historyEntity, null);
    }

    /**
     * 过滤所有非注解字段 深度优先递归获取对象的字段
     *
     * @param clazz          class对象
     * @param fieldEntityMap 字段与实体映射
     * @param objFieldList   对象字段集合
     * @param nowEntity      新实体数据
     * @param historyEntity  旧实体数据
     * @param parentField    父字段 暂时不使用 后续再考虑用法
     */
    private List<OperationLogFieldWrapper> handleFieldListMapping(Class<?> clazz, Map<Field, OperationLogEntity> fieldEntityMap, List<Field> objFieldList, Object nowEntity, Object historyEntity, Field parentField) {

        Field[] declaredFields = clazz.getDeclaredFields();

        List<OperationLogFieldWrapper> removeList = ListUtil.newArrayList();

        // 最终返回的字段集合
        List<OperationLogFieldWrapper> returnList = ListUtil.newArrayList(declaredFields.length);
        List<OperationLogFieldWrapper> fieldList = ListUtil.newArrayList(declaredFields.length);
        // 过滤所有没有注解的字段
        ListUtil.optimize(declaredFields).stream()
                .filter(field -> !Objects.isNull(field.getAnnotation(OperationLogField.class)))
                .forEach(field -> {

                    // 加入字段
                    OperationLogFieldWrapper operationLogFieldWrapper = new OperationLogFieldWrapper(field);

                    if (Objects.nonNull(parentField)) {
                        // 这里是用来做子字段跟随父字段排序的 目前暂时不使用
                        // OperationLogField annotation = parentField.getAnnotation(OperationLogField.class);
                        // int order = annotation.order();
                        // operationLogFieldWrapper.setParentOrder(order);
                        operationLogFieldWrapper.setParent(parentField);
                    }

                    fieldList.add(operationLogFieldWrapper);
                    returnList.add(operationLogFieldWrapper);

                    // 完成映射字段 数据对象的映射
                    OperationLogEntity operationLogEntity = new OperationLogEntity(clazz);
                    operationLogEntity.setNowEntity(nowEntity);
                    operationLogEntity.setHistoryEntity(historyEntity);
                    fieldEntityMap.put(field, operationLogEntity);
                });

        // 判断字段是否是Object或objectList 如果是 进行递归调用获取字段
        fieldList.stream()
                .filter(fieldWrapper -> {
                    Field field = fieldWrapper.getField();
                    OperationLogField annotation = field.getAnnotation(OperationLogField.class);

                    // 注解校验
                    boolean annotationFlag = Objects.equals(FieldTypeEnum.OBJECT, annotation.fieldType()) || Objects.equals(FieldTypeEnum.OBJECT_LIST, annotation.fieldType());

                    // 不将控制权完全放在使用者那边 自动查找 过滤条件 (字段是List类型 并且 list的泛型class有注解) 或者 非list字段有注解
                    boolean normalFlag = (isObjectList(field)) || Objects.nonNull(ReflectionUtil.getFieldClass(field).getAnnotation(OperationLog.class));
                    return annotationFlag || normalFlag;
                })
                .forEach(fieldWrapper -> {

                    // 处理字段class包含注解的情况
                    try {
                        Field field = fieldWrapper.getField();
                        field.setAccessible(true);
                        returnList.addAll(handleFieldListMapping(ReflectionUtil.getFieldClass(field), fieldEntityMap, objFieldList, field.get(nowEntity), field.get(historyEntity), field));
                        objFieldList.add(field);

                        // 对象字段本身需要从字段集合中remove
                        if (fieldWrapper.getField().getType() != List.class) {
                            removeList.add(fieldWrapper);
                        }
                    } catch (Exception e) {
                        log.error("获取字段值错误", e);
                    }
                });
        returnList.removeAll(removeList);
        return returnList;
    }

    /**
     * 封装definition
     */
    private OperationLogDefinition packageDefinition(OperationLogFieldWrapper fieldWrapper, OperationLog operationLog, OperationLogField operationLogField) {
        OperationLogDefinition definition = new OperationLogDefinition();
        // 获取字段所属类的class的注解 优先使用这个
        OperationLog annotation = fieldWrapper.getField().getDeclaringClass().getAnnotation(OperationLog.class);
        if (annotation != null) {
            operationLog = annotation;
        }

        // 格式化字段名
        String[] stringFormats = operationLog.stringFormats();
        List<String> formatList = Arrays.stream(stringFormats).collect(Collectors.toList());
        definition.setStringFormatList(formatList);

        // 字段类型
        FieldTypeEnum fieldType = operationLogField.fieldType();
        definition.setFieldType(fieldType);

        // 模块名称
        String moduleName = operationLogField.moduleName();
        if (StringUtil.isEmpty(moduleName)) {

            if (Objects.nonNull(fieldWrapper.getParent())) {
                moduleName = fieldWrapper.getParent().getAnnotation(OperationLogField.class).fieldName();
            }
            if (StringUtil.isEmpty(moduleName)) {
                moduleName = operationLog.moduleName();
            }
        }
        definition.setModuleName(moduleName);

        // 模块名是否拼接
        boolean joinModuleName = operationLogField.isJoinModuleName();
        definition.setJoinModuleName(joinModuleName);

        // 对象list是否每一个元素都要拼接模块名
        boolean joinModuleNameEveryone = operationLogField.isJoinModuleNameEveryone();
        definition.setJoinModuleNameEveryone(joinModuleNameEveryone);

        // 是否跳过
        boolean skip = operationLogField.skip();
        definition.setSkip(skip);

        // 字段名
        String fieldName = operationLogField.fieldName();
        definition.setFieldName(fieldName);

        // 模块首领字段
        boolean boss = operationLogField.leader();
        definition.setLeader(boss);

        // 字段名是否拼接
        boolean joinFieldName = operationLogField.isJoinFieldName();
        definition.setJoinFieldName(joinFieldName);

        // 排序下标
        int order = operationLogField.order();
        definition.setOrder(order);

        // 是否拼接其他字段
        boolean isJoinOtherFiled = operationLogField.isJoinOtherFiled();
        definition.setJoinOtherFiled(isJoinOtherFiled);

        // 拼接其他字段位置
        OtherFiledLocationEnum otherFiledLocation = operationLogField.otherFiledLocation();
        definition.setOtherFiledLocation(otherFiledLocation);

        // 拼接其他字段前缀
        String otherFiledPrefix = operationLogField.otherFiledPrefix();
        definition.setOtherFiledPrefix(otherFiledPrefix);

        // 拼接其他字段name
        String otherFiledName = operationLogField.otherFiledName();
        definition.setOtherFiledName(otherFiledName);

        // 拼接其他字段后缀
        String otherFiledSuffix = operationLogField.otherFiledSuffix();
        definition.setOtherFiledSuffix(otherFiledSuffix);

        // 前缀
        String prefix = operationLogField.prefix();
        definition.setPrefix(prefix);

        // 分隔符
        String separator = operationLogField.separator();
        definition.setSeparator(separator);

        // 后缀
        String suffix = operationLogField.suffix();
        definition.setSuffix(suffix);

        // 是否敏感
        boolean sensitive = operationLogField.isSensitive();
        definition.setSensitive(sensitive);

        // 头部保留位
        int headRetain = operationLogField.headRetain();
        definition.setHeadRetain(headRetain);

        // 尾部保留位
        int tailRetain = operationLogField.tailRetain();
        definition.setTailRetain(tailRetain);

        // 是否合并
        boolean isMerge = operationLogField.isMerge();
        definition.setMerge(isMerge);

        // 合并后字段名
        String mergeName = operationLogField.mergeName();
        definition.setMergeName(mergeName);

        // 合并字段总数
        int mergeSum = operationLogField.mergeSum();
        definition.setMergeSum(mergeSum);

        // 合并字段排序下标
        int mergeIndex = operationLogField.mergeIndex();
        definition.setMergeIndex(mergeIndex);

        // 合并字段前缀
        String mergePrefix = operationLogField.mergePrefix();
        definition.setMergePrefix(mergePrefix);

        // 合并字段后缀
        String mergeSuffix = operationLogField.mergeSuffix();
        definition.setMergeSuffix(mergeSuffix);
        return definition;
    }

    /**
     * 枚举数据处理
     *
     * @param value 值
     * @return Object
     * @throws Exception Exception
     */
    protected Object handleEnum(Object value) throws Exception {
        if (Objects.isNull(value)) {
            return null;
        }
        Class<?> type = value.getClass();
        if (type.isEnum()) {
            Method getDesc = type.getMethod("getDesc");
            value = getDesc.invoke(value);
        }
        return value;
    }

    /**
     * 敏感数据处理
     *
     * @param definition 操作日志定义
     * @param value      value
     * @return String
     */
    protected Object handleSensitive(OperationLogDefinition definition, Object value) {

        if (definition.isSensitive() && !Objects.isNull(value)) {
            value = SensitiveUtil.handleSensitive((String) value, definition.getHeadRetain(), definition.getTailRetain());
        }
        return value;
    }

    /**
     * date数据处理
     *
     * @param definition 操作日志定义
     * @param value      value
     * @return String
     */
    protected Object handleDate(OperationLogDefinition definition, Object value) {
        if (!(value instanceof Date)) {
            return value;
        }

        DateTimeFormat annotation = definition.getField().getAnnotation(DateTimeFormat.class);
        if (annotation == null) {
            return DateUtil.format(DateFormat.yyyy_MM_dd, (Date) value);
        }


        return DateFormatUtils.format((Date) value, annotation.pattern());
    }

    /**
     * 根据新值旧值进行格式化
     *
     * @param formatList   格式列表
     * @param nowValue     新的值
     * @param historyValue 历史值
     * @return String
     */
    public String stringFormat(List<String> formatList, boolean addOrEdit, Object nowValue, Object historyValue) {

        boolean nowFlag = StringUtil.hasText(nowValue);
        boolean historyFlag = StringUtil.hasText(historyValue);

        String result = null;

        for (String format : formatList) {

            // 新增并且now值不为空并且now占位符存在的情况
            if (addOrEdit && (nowFlag && format.contains(NOW))) {
                result = format.replace(NOW, nowValue.toString());
                break;
            } else if ((nowFlag && historyFlag) && (format.contains(NOW) && format.contains(HISTORY))) {
                // 两个值都不为空 那么需要找到两个占位符都存在的情况
                result = format.replace(NOW, nowValue.toString()).replace(HISTORY, historyValue.toString());
                break;
            } else if ((!addOrEdit && nowFlag && !historyFlag) && (format.contains(NOW) && !format.contains(HISTORY))) {
                // 第一个条件不满足 找修改的情况 并且now值不为空 historyFlag为空 并且now占位符存在history占位符不为空的情况
                result = format.replace(NOW, nowValue.toString());
                break;
            } else if ((!addOrEdit && !nowFlag && historyFlag) && (!format.contains(NOW) && format.contains(HISTORY))) {
                // 前两个条件不满足 找history值不为空并且history占位符存在的情况
                result = format.replace(HISTORY, historyValue.toString());
                break;
            }
        }

        // 如果format中没有找到匹配的条件 说明format存在问题 或者说nowValue和historyValue存在问题 没有匹配上 抛出异常
        // if (StringUtil.isEmpty(result)) {
        //
        //     if(!Objects.equals("",nowValue) && !Objects.equals("", historyValue)){
        //         throw new RuntimeException("@OperationLog注解的stringFormat定义与实际nowValue和historyValue匹配存在问题 请检查");
        //     }
        // }
        return result;
    }

    /**
     * 最终拼接数据
     *
     * @param wrapper OperationLogDefinition包装类
     * @return String
     * @throws Exception Exception
     */
    public abstract String joinContent(OperationLogDefinitionWrapper wrapper) throws Exception;
}