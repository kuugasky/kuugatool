package io.github.kuugasky.kuugatool.operationlog.core;

import io.github.kuugasky.kuugatool.operationlog.annotations.OperationLog;
import io.github.kuugasky.kuugatool.operationlog.annotations.OperationLogField;
import junit.framework.TestCase;
import lombok.*;

public class OperationLogUtilTest extends TestCase {

    @Getter
    @AllArgsConstructor
    enum Sex {
        BOY("男士"), GIRL("女士");
        private final String desc;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @OperationLog(moduleName = "\r\n日志信息",
            stringFormats = {"${now}", "由空更新为\"${now}\"", "\"${history}\"更新为\"${now}\"", "${history}更新为空"})
    static class DiffLog {
        @OperationLogField(moduleName = "【基本信息】", isJoinModuleName = true, fieldName = "姓名", prefix = "(", suffix = "),", order = 1)
        private String name;
        @OperationLogField(fieldName = "年龄", prefix = "(", suffix = ")", order = 2)
        private int age;
        @OperationLogField(moduleName = "\n【性别信息】", fieldName = "性别", prefix = "", suffix = "", order = 3, isJoinModuleName = true)
        private Sex sex;
        @OperationLogField(moduleName = "\n【房厅厨卫】", fieldName = "房间/卧室", isJoinModuleName = true, isMerge = true, mergeName = "户型", mergeSuffix = "*", mergeSum = 4, mergeIndex = 1, order = 4)
        private Integer bedroom;

        @OperationLogField(fieldName = "客厅", isMerge = true, mergeName = "户型", mergeSuffix = "*", mergeSum = 4, mergeIndex = 2, order = 5)
        private Integer hall;

        @OperationLogField(fieldName = "厨房", isMerge = true, mergeName = "户型", mergeSuffix = "*", mergeSum = 4, mergeIndex = 3, order = 6)
        private Integer kitchen;

        @OperationLogField(fieldName = "卫生间/浴室", isMerge = true, mergeName = "户型", mergeSum = 4, mergeIndex = 4, order = 7)
        private Integer bathroom;
    }

    public static void main(String[] args) throws Exception {
        DiffLog history = DiffLog.builder().name("history").age(1).sex(Sex.BOY).build();
        DiffLog now = DiffLog.builder().name("now").age(2).sex(null)
                .bedroom(1)
                .hall(2)
                .kitchen(3)
                .bathroom(4)
                .build();
        String content = OperationLogUtil.getContent(now, history, DiffLog.class);
        System.out.println(content);
    }

}