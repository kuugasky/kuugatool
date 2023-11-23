package io.github.kuugasky.kuugatool.core.clazz;

import lombok.Builder;

/**
 * RuntimeTraceInfo
 * <p>
 * 运行时跟踪信息
 *
 * @author kuuga
 * @since 2021/6/30
 */
@Builder
public final class RuntimeTraceInfo {

    /**
     * 类加载器名称
     */
    private final String classLoaderName;
    /**
     * 类名
     */
    private final String className;
    /**
     * 方法名
     */
    private final String methodName;
    /**
     * 行数
     */
    private final int lineNumber;
    /**
     * 字段名
     */
    private final String fileName;
    /**
     * 模块名
     */
    private final String moduleName;
    /**
     * 模块版本
     */
    private final String moduleVersion;
    /**
     * 类型
     */
    private final Class<?> clazz;

    public String simpleInfo() {
        return String.format("class:%s,method:%s,lineNumber:%s",
                this.className,
                this.methodName,
                this.lineNumber);
    }

}
