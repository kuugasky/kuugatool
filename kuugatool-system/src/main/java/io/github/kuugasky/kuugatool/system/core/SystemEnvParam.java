package io.github.kuugasky.kuugatool.system.core;

/**
 * @author kuuga
 * @date 2021-01-21 下午3:13
 */
public enum SystemEnvParam {

    /**
     *
     */
    JAVA_VERSION("java.version", "Java 运行时环境版本"),
    JAVA_VENDOR("java.vendor", "Java 运行时环境供应商"),
    JAVA_VENDOR_URL("java.vendor.url", "Java 供应商的 URL"),
    JAVA_HOME("java.home", "Java 安装目录"),
    JAVA_VM_SPECIFICATION_VERSION("java.vm.specification.version", "Java 虚拟机规范版本"),
    JAVA_VM_SPECIFICATION_VENDOR("java.vm.specification.vendor", "Java 虚拟机规范供应商"),
    JAVA_VM_SPECIFICATION_NAME("java.vm.specification.name", "Java 虚拟机规范名称"),
    JAVA_VM_VERSION("java.vm.version", "java 虚拟机实现版本"),
    JAVA_VM_VENDOR("java.vm.vendor", "java 虚拟机实现供应商"),
    JAVA_VM_NAME("java.vm.name", "Java 虚拟机实现名称"),
    JAVA_SPECIFICATION_VERSION("java.specification.version", "Java 运行时环境规范版本"),
    JAVA_SPECIFICATION_VENDOR("java.specification.vendor", "Java 运行时环境规范供应商"),
    JAVA_SPECIFICATION_NAME("java.specification.name", "Java 运行时环境规范名称"),
    JAVA_CLASS_VERSION("java.class.version", "Java 类格式版本号"),
    JAVA_CLASS_PATH("java.class.path", "Java 类路径"),
    JAVA_LIBRARY_PATH("java.library.path", "加载库时搜索的路径列表"),
    JAVA_IO_TMPDIR("java.io.tmpdir", "默认的临时文件路径"),
    JAVA_COMPILER("java.compiler", "要使用的 JIT 编译器的名称"),
    JAVA_EXT_DIRS("java.ext.dirs", "一个或多个扩展目录的路径"),
    OS_NAME("os.name", "操作系统的名称"),
    OS_ARCH("os.arch", "操作系统的架构"),
    OS_VERSION("os.version", "操作系统的版本"),
    FILE_SEPARATOR("file.separator", "文件分隔符（在 UNIX 系统中是“/”）"),
    PATH_SEPARATOR("path.separator", "路径分隔符（在 UNIX 系统中是“:”）"),
    LINE_SEPARATOR("line.separator", "行分隔符（在 UNIX 系统中是“/n”）"),
    USER_NAME("user.name", "用户的账户名称"),
    USER_HOME("user.home", "用户的主目录"),
    USER_DIR("user.dir", "用户的当前工作目录"),
    FILE_ENCODING("file.encoding", "文件编码"),
    GOPHER_PROXY_SET("gopherProxySet", "代理设置"),
    ;

    private String code;
    private String desc;

    SystemEnvParam(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
/*
java.version                    Java 运行时环境版本
java.vendor                     Java 运行时环境供应商
java.vendor.url                 Java 供应商的 URL
java.home                       Java 安装目录
java.vm.specification.version   Java 虚拟机规范版本
java.vm.specification.vendor    Java 虚拟机规范供应商
java.vm.specification.name      Java 虚拟机规范名称
java.vm.version                 Java 虚拟机实现版本
java.vm.vendor                  Java 虚拟机实现供应商
java.vm.name                    Java 虚拟机实现名称
java.specification.version      Java 运行时环境规范版本
java.specification.vendor       Java 运行时环境规范供应商
java.specification.name         Java 运行时环境规范名称
java.class.version              Java 类格式版本号
java.class.path                 Java 类路径
java.library.path               加载库时搜索的路径列表
java.io.tmpdir                  默认的临时文件路径
java.compiler                   要使用的 JIT 编译器的名称
java.ext.dirs                   一个或多个扩展目录的路径
os.name                         操作系统的名称
os.arch                         操作系统的架构
os.version                      操作系统的版本
file.separator                  文件分隔符（在 UNIX 系统中是“/”）
path.separator                  路径分隔符（在 UNIX 系统中是“:”）
line.separator                  行分隔符（在 UNIX 系统中是“/n”）
user.name                       用户的账户名称
user.home                       用户的主目录
user.dir                        用户的当前工作目录
*/