package io.github.kuugasky.kuugatool.system;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.collection.MapUtil;
import io.github.kuugasky.kuugatool.core.date.SecondFormatUtil;
import io.github.kuugasky.kuugatool.core.string.StringJoinerUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.junit.jupiter.api.Test;

import javax.management.ObjectName;
import java.util.List;
import java.util.Map;

class RuntimeMxUtilTest {

    @Test
    void getPid() {
        String pid = StringJoinerUtil.join("#", ListUtil.newArrayList("进程ID", RuntimeMxUtil.getPid()));
        System.out.println(pid);

        // Scanner input = new Scanner(System.in);
        // while (input.hasNext())
        //     System.out.println("你输入了:"+input.nextLine());
        // System.out.println("我赌它执行不到我");
    }

    @Test
    void classPath() {
        System.out.println("返回系统类装入器用于搜索类文件的Java类路径");
        System.out.println(RuntimeMxUtil.getClassPath());
    }

    @Test
    void getName() {
        System.out.println("返回表示正在运行的Java虚拟机的名称");
        System.out.println(RuntimeMxUtil.getName());
        System.out.println("返回Java虚拟机实现名称");
        System.out.println(RuntimeMxUtil.getVmName());
        System.out.println("返回Java虚拟机规范名称");
        System.out.println(RuntimeMxUtil.getSpecName());
    }

    @Test
    void getVmName() {

    }

    @Test
    void getSpecName() {

    }

    @Test
    void getUptime() {
        System.out.println("以毫秒为单位返回Java虚拟机的正常运行时间");
        long uptime = RuntimeMxUtil.getUptime();
        System.out.println(uptime);
        System.out.println(SecondFormatUtil.getSecondFormat(uptime / 1000, true, true));
        System.out.println("返回Java虚拟机的开始时间");
        long startTime = RuntimeMxUtil.getStartTime();
        System.out.println(startTime);
        System.out.println(SecondFormatUtil.getSecondFormat(startTime / 1000, true, true));
    }

    @Test
    void getStartTime() {
    }

    @Test
    void getInputArguments() {
        System.out.println("返回传递给Java虚拟机的输入参数，其中不包括{@code main}方法的参数。如果Java虚拟机没有输入参数，这个方法返回一个空列表。");
        List<String> inputArguments = RuntimeMxUtil.getInputArguments();
        inputArguments.forEach(System.out::println);
    }

    @Test
    void getClassPath() {
        System.out.println("返回系统类装入器用于搜索类文件的Java类路径");
        System.out.println(RuntimeMxUtil.getClassPath());
    }

    @Test
    void getBootClassPath() {
        System.out.println("返回引导类装入器用于搜索类文件的引导类路径");
        System.out.println(RuntimeMxUtil.getBootClassPath());
    }

    @Test
    void getLibraryPath() {
        System.out.println("返回Java库路径");
        System.out.println(RuntimeMxUtil.getLibraryPath());
    }

    @Test
    void getManagementSpecVersion() {
        System.out.println("返回正在运行的Java虚拟机实现的管理接口的规范版本");
        System.out.println(RuntimeMxUtil.getManagementSpecVersion());
    }

    @Test
    void getSpecVendor() {
        System.out.println("返回Java虚拟机规格供应商");
        System.out.println(RuntimeMxUtil.getSpecVendor());
    }

    @Test
    void getVmVersion() {
        System.out.println("返回Java虚拟机实现版本");
        System.out.println(RuntimeMxUtil.getVmVersion());
    }

    @Test
    void getSpecVersion() {
        System.out.println("返回Java虚拟机规范版本");
        System.out.println(RuntimeMxUtil.getSpecVersion());
    }

    @Test
    void getObjectName() {
        System.out.println("平台管理对象");
        ObjectName objectName = RuntimeMxUtil.getObjectName();
        System.out.println(StringUtil.formatString(objectName));
    }

    @Test
    void isBootClassPathSupported() {
        System.out.println("测试Java虚拟机是否支持引导类装入器用于搜索类文件的引导类路径机制");
        System.out.println(RuntimeMxUtil.isBootClassPathSupported());
    }

    @Test
    void getSystemProperties() {
        System.out.println("返回所有系统属性的名称和值的映射");
        Map<String, String> systemProperties = RuntimeMxUtil.getSystemProperties();
        System.out.println(MapUtil.toString(systemProperties, true));
    }

}