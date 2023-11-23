package io.github.kuugasky.kuugatool.operationlog.core;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.instance.ReflectionUtil;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import io.github.kuugasky.kuugatool.core.optional.KuugaOptional;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import io.github.kuugasky.kuugatool.operationlog.entity.OperationLogBase;
import io.github.kuugasky.kuugatool.operationlog.entity.OperationLogDefinition;
import io.github.kuugasky.kuugatool.operationlog.entity.OperationLogDefinitionWrapper;
import io.github.kuugasky.kuugatool.operationlog.entity.OperationLogEntity;
import io.github.kuugasky.kuugatool.operationlog.enums.FieldTypeEnum;
import io.github.kuugasky.kuugatool.operationlog.enums.OtherFiledLocationEnum;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 默认的操作记录处理器
 *
 * @author pengqinglong
 * @since 2021/3/22
 */
public class OperationDefaultHandle extends OperationHandle {

    @Override
    public String joinContent(OperationLogDefinitionWrapper wrapper) throws Exception {
        List<OperationLogDefinition> definitionList = wrapper.getDefinitionList();

        return this.joinContent(wrapper, definitionList, null);
    }

    /**
     * 重载 逻辑细化
     *
     * @param wrapper        包装总类
     * @param definitionList 类字段定义集合
     * @param entity         数据对象 优先使用此数据对象获取数据 如果此对象为空 则从wrapper对象中的fieldEntityMapping中拿取数据
     */
    protected String joinContent(OperationLogDefinitionWrapper wrapper, List<OperationLogDefinition> definitionList, OperationLogEntity entity) throws Exception {
        StringBuilder content = new StringBuilder();

        // 已经处理过的合并definition
        List<OperationLogDefinition> mergeHandleList = ListUtil.newArrayList();

        Map<Field, OperationLogEntity> fieldEntityMap = wrapper.getFieldEntityMapping();

        // 模块boss字段
        OperationLogDefinition leader = null;
        // 是否递归调用 true 不是 false 是
        for (OperationLogDefinition definition : definitionList) {

            // 当前字段是否需要跳过
            if (definition.isSkip()) {
                continue;
            }

            OperationLogEntity tempEntity = entity;

            // 已经处理的合并字段 不用再次处理
            boolean isMergeField = mergeHandleList.stream().anyMatch(merge -> Objects.equals(definition, merge));
            if (isMergeField) {
                continue;
            }

            Field field = definition.getField();
            if (Objects.isNull(tempEntity)) {
                tempEntity = fieldEntityMap.get(field);
            }

            // 获取leader字段
            if (definition.isJoinModuleName() && definition.isLeader()) {
                leader = definition;
            }

            // 处理合并字段 如果所有字段都相同 会返回""空字符串 所以非合并字段通过返回null进行判断
            String mergeContent = this.handleMerge(wrapper, definition, mergeHandleList, tempEntity);
            if (Objects.nonNull(mergeContent)) {

                // 处理拼接其他字段
                mergeContent = this.handleJoinOtherFiled(definitionList, definition, tempEntity, mergeContent);

                //  boss不为空 并且没有拼接上模块名 在这里重新拼接
                leader = this.handleLeader(content, leader, mergeContent);
                continue;
            }

            // 处理对象集合的字段 如果所有字段都相同 会返回""空字符串 所以非集合通过返回null进行判断
            String listContent = this.handleList(wrapper, definition, tempEntity);
            if (Objects.nonNull(listContent)) {
                content.append(listContent);
                continue;
            }

            // 获取字段值
            Object nowValue = field.get(tempEntity.getNowEntity());
            Object historyValue = field.get(tempEntity.getHistoryEntity());
            // 浮点数处理
            if (field.getType() == BigDecimal.class) {

                if (Objects.nonNull(nowValue)) {
                    BigDecimal decimal = (BigDecimal) nowValue;
                    nowValue = decimal.stripTrailingZeros().toPlainString();
                }
                if (Objects.nonNull(historyValue)) {
                    BigDecimal decimal = (BigDecimal) historyValue;
                    historyValue = decimal.stripTrailingZeros().toPlainString();
                }
            }

            // 拼接操作内容
            String fieldContent = this.joinContent(definition, wrapper.isAddOrEdit(), nowValue, historyValue);

            // 处理拼接其他字段
            fieldContent = this.handleJoinOtherFiled(definitionList, definition, tempEntity, fieldContent);

            // leader不为空 并且没有拼接上模块名 在这里重新拼接
            leader = handleLeader(content, leader, fieldContent);
        }

        return content.toString();
    }

    protected String handleJoinOtherFiled(List<OperationLogDefinition> definitionList, OperationLogDefinition definition, OperationLogEntity tempEntity, String fieldContent) {
        if (definition.isJoinOtherFiled()) {
            List<OperationLogDefinition> collect = definitionList.stream()
                    .filter(otherFiled -> Objects.equals(otherFiled.getFieldRealName(), definition.getOtherFiledName()))
                    .limit(1)
                    .collect(Collectors.toList());

            for (OperationLogDefinition otherFiled : ListUtil.optimize(collect)) {
                Object fieldValue = ReflectionUtil.getFieldValue(tempEntity.getNowEntity(), otherFiled.getField());
                fieldValue = super.handleSensitive(definition, fieldValue);
                if (fieldValue != null) {
                    fieldContent = fieldContent.replace("#JoinOtherFiled#", fieldValue.toString());
                } else {
                    fieldContent = fieldContent.replace("#JoinOtherFiled#", "");
                }
            }
        }
        return fieldContent;
    }

    /**
     * 处理leader
     * leader不为空 并且没有拼接上模块名 在这里重新拼接
     */
    protected OperationLogDefinition handleLeader(StringBuilder contentBuild, OperationLogDefinition leader, String content) {
        boolean nonNull = Objects.nonNull(leader);
        boolean hasText = StringUtil.hasText(content);
        String moduleName = KuugaOptional.ofNullable(leader).getBean(OperationLogDefinition::getModuleName).orElse(null);
        boolean noContains = !content.contains(moduleName);
        boolean indexOf = contentBuild.indexOf(moduleName) == -1;

        if (nonNull && hasText && noContains && indexOf) {
            content = leader.getModuleName() + content;
            leader = null;
        }
        contentBuild.append(content);
        return leader;
    }

    /**
     * 处理数据对象型集合字段
     *
     * @param wrapper            definition包装类
     * @param definition         字段定义
     * @param operationLogEntity 数据对象
     */
    protected String handleList(OperationLogDefinitionWrapper wrapper, OperationLogDefinition definition, OperationLogEntity operationLogEntity) throws Exception {
        StringBuilder builder = new StringBuilder();
        Field field = definition.getField();
        // 判断字段是否是集合
        if (Objects.equals(FieldTypeEnum.OBJECT_LIST, definition.getFieldType())) {
            Class<?> fieldClass = ReflectionUtil.getFieldClass(field);
            List<OperationLogDefinition> classDefinitionList = wrapper.getClassDefinitionMapping().get(fieldClass);

            // class映射为空 说明是普通类型的List 走普通拼接操作通过分割符进行处理
            if (ListUtil.isEmpty(classDefinitionList)) {
                return null;
            }

            // 不使用MapperUtil.copy 深拷贝把里面list全拷贝了一份 占用内存 使用浅拷贝即可
            OperationLogDefinitionWrapper copy = new OperationLogDefinitionWrapper(wrapper);
            copy.setDefinitionList(classDefinitionList);

            /*
                新旧对象通过id进行匹配 匹配规则如下
                前提条件 wrapper.addOrEdit字段最优先 如果是add 那么下面规则无视 修改才考虑下面的匹配规则
                1.如果新集合中的对象有id 但是在旧集合中找不到相同id的对象  视为修改新增
                2.如果新集合中的对象有id 并且能匹配上旧集合中的对象id     视为修改信息
                3.如果旧集合中的对象有id 但是在新集合中找不到相同id的对象  视为修改删除
             */
            List<OperationLogBase> nowList = ObjectUtil.cast(field.get(operationLogEntity.getNowEntity()));
            List<OperationLogBase> historyList = ObjectUtil.cast(field.get(operationLogEntity.getHistoryEntity()));

            List<OperationLogEntity> logEntityList = ListUtil.newArrayList();

            // 寻找符合条件1和条件2
            ListUtil.optimize(nowList).forEach(nowBase -> {

                // id为空 不符合逻辑 88
                String id = nowBase.getId();
                if (StringUtil.isEmpty(id)) {
                    return;
                }

                OperationLogEntity logEntity = new OperationLogEntity(nowBase.getClass());

                // 默认满足条件1
                logEntity.setNowEntity(nowBase);

                // 条件2
                ListUtil.optimize(historyList).stream()
                        .filter(historyBase -> Objects.equals(id, historyBase.getId()))
                        // 多个id能匹配上其实是有问题的 默认只拿一个
                        .forEach(logEntity::setHistoryEntity);

                logEntityList.add(logEntity);
            });

            // 寻找符合条件3的
            ListUtil.optimize(historyList).forEach(historyBase -> {
                boolean isDelete = ListUtil.optimize(nowList).stream().noneMatch(nowBase -> Objects.equals(historyBase.getId(), nowBase.getId()));
                if (isDelete) {
                    OperationLogEntity logEntity = new OperationLogEntity(historyBase.getClass());
                    logEntity.setHistoryEntity(historyBase);
                    logEntityList.add(logEntity);
                }
            });


            // 获取当前listClass中存在有模块名拼接的字段 且保存快照
            List<OperationLogDefinition> joinModuleNameList = classDefinitionList.stream().filter(OperationLogDefinition::isJoinModuleName).collect(Collectors.toList());
            Map<OperationLogDefinition, Boolean> snapsHoot = ListUtil.optimize(joinModuleNameList).stream().collect(Collectors.toMap(o -> o, OperationLogDefinition::isJoinModuleName));

            // 处理数据
            StringBuilder sb = new StringBuilder();
            for (OperationLogEntity logEntity : logEntityList) {

                // 处理
                copy.setNowEntity(logEntity.getNowEntity());
                copy.setHistoryEntity(logEntity.getHistoryEntity());
                String content = this.joinContent(copy, classDefinitionList, logEntity);
                sb.append(content);

                // 处理完成后使用isJoinModuleNameEveryone更新isJoinModuleName字段
                joinModuleNameList.forEach(o -> o.setJoinModuleName(o.isJoinModuleNameEveryone()));
            }

            // 完成后将快照更新回定义中
            for (Map.Entry<OperationLogDefinition, Boolean> entry : snapsHoot.entrySet()) {
                OperationLogDefinition key = entry.getKey();
                key.setJoinModuleName(entry.getValue());
            }

            // 判断最终结果是否为空 不为空 拼接字段名
            String content = sb.toString();
            if (StringUtil.hasText(content)) {
                builder.append(definition.getFieldName());
                builder.append(content);
            }
            return builder.toString();
        }
        return null;
    }

    /**
     * 处理合并字段
     *
     * @param wrapper         OperationLogDefinition包装类
     * @param definition      操作日志定义
     * @param mergeHandleList 合并处理列表
     * @return String
     * @throws Exception Exception
     */
    protected String handleMerge(OperationLogDefinitionWrapper wrapper, OperationLogDefinition definition, List<OperationLogDefinition> mergeHandleList, OperationLogEntity operationLogEntity) throws Exception {
        StringBuilder builder = new StringBuilder();
        // 判断是否需要合并
        boolean isMerge = definition.isMerge();
        if (isMerge) {
            // 获取合并字段 并用mergeIndex排序
            int mergeSum = definition.getMergeSum();
            List<OperationLogDefinition> mergeList = wrapper.getMergeList().stream()
                    .filter(merge -> Objects.equals(merge.getMergeName(), definition.getMergeName()))
                    .sorted(Comparator.comparingInt(OperationLogDefinition::getMergeIndex)).toList();

            // 字段数量与mergeSum校验
            if (mergeList.size() != mergeSum) {
                builder.append(wrapper.getNowEntity().getClass().getName()).append("@OperationLogField注解的mergeSum定义有误");
                throw new RuntimeException(builder.toString());
            }

            // 封装数据
            StringBuilder nowBuilder = new StringBuilder();
            StringBuilder historyBuilder = new StringBuilder();
            for (OperationLogDefinition merge : mergeList) {
                // 获取字段值
                Field field = merge.getField();
                Object nowValue = field.get(operationLogEntity.getNowEntity());
                Object historyValue = field.get(operationLogEntity.getHistoryEntity());

                // 枚举处理
                nowValue = super.handleEnum(nowValue);
                historyValue = super.handleEnum(historyValue);

                // 敏感数据处理
                nowValue = super.handleSensitive(merge, nowValue);
                historyValue = super.handleSensitive(merge, historyValue);

                // 前后缀
                String mergePrefix = merge.getMergePrefix();
                String mergeSuffix = merge.getMergeSuffix();

                // 拼接字段
                if (StringUtil.hasText(nowValue)) {
                    nowBuilder.append(mergePrefix);
                    nowBuilder.append(nowValue);
                    nowBuilder.append(mergeSuffix);
                }

                if (StringUtil.hasText(historyValue)) {
                    historyBuilder.append(mergePrefix);
                    historyBuilder.append(historyValue);
                    historyBuilder.append(mergeSuffix);
                }

                // 标示表示处理过了
                mergeHandleList.add(merge);
            }

            // 上面已经处理过敏感信息了 里面就不用再处理一次了
            boolean sensitive = definition.isSensitive();
            definition.setSensitive(false);

            // 拼接内容
            String content = this.joinContent(definition, wrapper.isAddOrEdit(), nowBuilder.toString(), historyBuilder.toString());
            // 敏感信息还原
            definition.setSensitive(sensitive);
            builder.append(content);


            return builder.toString();
        }
        return null;
    }

    /**
     * 拼接日志内容
     *
     * @param definition   操作日志定义
     * @param addOrEdit    新增还是修改
     * @param nowValue     新的值
     * @param historyValue 历史值
     * @return String
     */
    protected String joinContent(OperationLogDefinition definition, boolean addOrEdit, Object nowValue, Object historyValue) throws Exception {

        StringBuilder builder = new StringBuilder();
        // 上面过滤掉了对象集合的情况 所以这里的集合全是普通字段/枚举 直接循环处理
        if (Objects.equals(FieldTypeEnum.NORMAL_LIST, definition.getFieldType())) {

            // 分隔符
            String separator = definition.getSeparator();

            // 处理新值
            List<Object> nowValueList = ObjectUtil.cast(nowValue);
            List<Object> nowList = ListUtil.optimize(nowValueList).stream().sorted().toList();
            StringBuilder nowBuilder = new StringBuilder();
            for (Object now : nowList) {
                now = super.handleEnum(now);
                nowBuilder.append(now);
                nowBuilder.append(separator);
            }

            // 处理旧值
            List<Object> historyValueList = ObjectUtil.cast(historyValue);
            List<Object> historyList = ListUtil.optimize(historyValueList).stream().sorted().toList();

            StringBuilder historyBuilder = new StringBuilder();
            for (Object history : historyList) {
                history = super.handleEnum(history);
                historyBuilder.append(history);
                historyBuilder.append(separator);
            }

            nowValue = StringUtil.removeEnd(nowBuilder.toString(), separator);
            historyValue = StringUtil.removeEnd(historyBuilder.toString(), separator);
        }

        // 如果两个值一模一样 不用处理了
        if (Objects.equals(nowValue, historyValue)) {
            return builder.toString();
        }

        // 日期处理
        nowValue = super.handleDate(definition, nowValue);
        historyValue = super.handleDate(definition, historyValue);

        // 枚举处理
        nowValue = super.handleEnum(nowValue);
        historyValue = super.handleEnum(historyValue);

        // 敏感数据处理
        nowValue = super.handleSensitive(definition, nowValue);
        historyValue = super.handleSensitive(definition, historyValue);

        // 数据格式化
        String result = this.stringFormat(definition.getStringFormatList(), addOrEdit, nowValue, historyValue);

        // 格式化不出数据并且没报错 不用处理了 字段没填数据
        if (StringUtil.isEmpty(result)) {
            return builder.toString();
        }

        // 拼接其他字段 字段值前面
        if (definition.isJoinOtherFiled() && definition.getOtherFiledLocation() == OtherFiledLocationEnum.VALUE_BEFORE) {
            result = String.format("%s#JoinOtherFiled#%s%s", definition.getOtherFiledPrefix(), result, definition.getOtherFiledSuffix());
        } else if (definition.isJoinOtherFiled() && definition.getOtherFiledLocation() == OtherFiledLocationEnum.VALUE_AFTER) {
            result = String.format("%s%s#JoinOtherFiled#%s", definition.getOtherFiledPrefix(), result, definition.getOtherFiledSuffix());
        }

        // 拼接模块名
        spliceModuleName(definition, builder);

        // 拼接字段名 如果需要合并就拼接合并字段名
        spliceFieldName(definition, builder);

        // 拼接前缀 如果是合并字段就不拼接默认的:
        builder.append(definition.getPrefix());

        // 拼接格式化后的字符串
        builder.append(result);

        // 拼接后缀
        builder.append(definition.getSuffix());

        return builder.toString();
    }

    private static void spliceFieldName(OperationLogDefinition definition, StringBuilder builder) {
        if (definition.isJoinFieldName()) {
            // 拼接其他字段 字段前面
            if (definition.isJoinOtherFiled() && definition.getOtherFiledLocation() == OtherFiledLocationEnum.FIELD) {
                builder.append(String.format("%s#JoinOtherFiled#%s", definition.getOtherFiledPrefix(), definition.getOtherFiledSuffix()));
            }
            if (definition.isMerge()) {
                builder.append(definition.getMergeName());
            } else {
                builder.append(definition.getFieldName());
            }
        }
    }

    private static void spliceModuleName(OperationLogDefinition definition, StringBuilder builder) {
        if (definition.isJoinModuleName()) {
            // 拼接其他字段 模块前面
            if (definition.isJoinOtherFiled() && definition.getOtherFiledLocation() == OtherFiledLocationEnum.MODEL) {
                builder.append(String.format("%s#JoinOtherFiled#%s", definition.getOtherFiledPrefix(), definition.getOtherFiledSuffix()));
            }
            builder.append(definition.getModuleName());
        }
    }

}