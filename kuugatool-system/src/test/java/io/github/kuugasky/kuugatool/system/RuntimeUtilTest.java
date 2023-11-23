package io.github.kuugasky.kuugatool.system;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.io.IoUtil;
import io.github.kuugasky.kuugatool.core.io.bytes.ByteSizeConvert;
import io.github.kuugasky.kuugatool.core.lang.Console;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import io.github.kuugasky.kuugatool.system.core.JavaRuntimeInfo;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

public class RuntimeUtilTest {

    @Test
    void getJavaRuntimeInfo() {
        JavaRuntimeInfo javaRuntimeInfo = RuntimeUtil.getJavaRuntimeInfo();
        System.out.println(StringUtil.formatString(javaRuntimeInfo));
    }

    /**
     * 获取当前jvm的内存信息,返回的值是 字节为单位
     */
    @Test
    void main() {
        // 获取可用内存
        long value = Runtime.getRuntime().freeMemory();
        System.out.println("可用内存为:" + value / 1024 / 1024 + "mb");
        // 获取jvm的总数量，该值会不断的变化
        long totalMemory = Runtime.getRuntime().totalMemory();
        System.out.println("全部内存为:" + totalMemory / 1024 / 1024 + "mb");
        // 获取jvm 可以最大使用的内存数量，如果没有被限制 返回 Long.MAX_VALUE;
        long maxMemory = Runtime.getRuntime().maxMemory();
        System.out.println("可用最大内存为:" + maxMemory / 1024 / 1024 + "mb");
    }

    @Test
    public void execForStr() throws IOException, InterruptedException {
        String ipconfig = RuntimeUtil.execForStr("ifconfig");
        // String ipconfig = RuntimeUtil.execForStr("open /Users/kuuga/Downloads");
        Console.log(ipconfig);
    }

    @Test
    public void testExecForStr() {
        String ifconfig = RuntimeUtil.execForStr(Charset.forName("GBK"), "ifconfig");
        Console.log(ifconfig);
    }

    @Test
    public void execForLines() {
        // List<String> list = RuntimeUtil.execForLines("ifconfig");
        List<String> list = RuntimeUtil.execForLines("ifconfig", "open /Users/kuuga/Downloads");
        ListUtil.optimize(list).forEach(System.out::println);
    }

    @Test
    public void execForLines2() {
        // List<String> list = RuntimeUtil.execForLines("ifconfig");
        List<String> list = RuntimeUtil.getResultLines(RuntimeUtil.exec("ifconfig"));
        ListUtil.optimize(list).forEach(System.out::println);
    }

    @Test
    public void execForLines3() {
        // List<String> list = RuntimeUtil.execForLines("ifconfig");
        List<String> list = RuntimeUtil.getResultLines(RuntimeUtil.exec("ifconfig"), Charset.defaultCharset());
        ListUtil.optimize(list).forEach(System.out::println);
    }

    @Test
    public void testExecForLines() {

    }

    @Test
    public void exec() {
        // Process ifconfig = RuntimeUtil.exec("ifconfig");
        Process ifconfig = RuntimeUtil.exec("echo $PWD");
        System.out.println(IoUtil.read(ifconfig.getInputStream()));
    }

    @Test
    public void testExec() {
        String[] env = null;
        Process ifconfig = RuntimeUtil.exec(env, "echo $PWD");
        System.out.println(IoUtil.read(ifconfig.getInputStream()));
    }

    @Test
    public void testExec1() {

    }

    @Test
    public void getResultLines() {
    }

    @Test
    public void testGetResultLines() {
    }

    @Test
    public void getResult() {
        // 获取命令执行结果，使用系统默认编码，，获取后销毁进程
        Process ifconfig = RuntimeUtil.exec("ifconfig");
        String result = RuntimeUtil.getResult(ifconfig, Charset.forName("GBK"));
        Console.log(result);
    }

    @Test
    public void testGetResult() {
        // 获取命令执行结果，使用系统默认编码，，获取后销毁进程
        Process ifconfig = RuntimeUtil.exec("ifconfig");
        String result = RuntimeUtil.getResult(ifconfig);
        Console.log(result);
    }

    @Test
    public void getErrorResult() {
        Process ifconfig = RuntimeUtil.exec("ifconfigx");
        Console.log(RuntimeUtil.getErrorResult(ifconfig, Charset.forName("GBK")));
    }

    @Test
    public void testGetErrorResult() {
        Process ifconfig = RuntimeUtil.exec("ifconfigx");
        Console.log(RuntimeUtil.getErrorResult(ifconfig));
    }

    @Test
    public void destroy() {
        Process ifconfig = RuntimeUtil.exec("ifconfig");
        RuntimeUtil.destroy(ifconfig);
    }

    @Test
    public void addShutdownHook() {
        // 增加一个JVM关闭后的钩子，用于在JVM关闭时执行某些操作
        RuntimeUtil.addShutdownHook(() -> System.out.println("JVM shutdown now"));
    }

    @Test
    public void getProcessorCount() {
        // 获得JVM可用的处理器数量（一般为CPU核心数）
        int processorCount = RuntimeUtil.getProcessorCount();
        System.out.println(processorCount);
    }

    @Test
    public void getFreeMemory() {
        // 获得JVM中剩余的内存数，单位byte
        long freeMemory = RuntimeUtil.getFreeMemory();
        System.out.println(ByteSizeConvert.model().format(freeMemory));
    }

    @Test
    public void getTotalMemory() {
        // 获得JVM已经从系统中获取到的总共的内存数，单位byte
        long totalMemory = RuntimeUtil.getTotalMemory();
        System.out.println(ByteSizeConvert.model().format(totalMemory));
    }

    @Test
    public void getMaxMemory() {
        // 获得JVM中可以从系统中获取的最大的内存数，单位byte，以-Xmx参数为准
        long maxMemory = RuntimeUtil.getMaxMemory();
        System.out.println(ByteSizeConvert.model().format(maxMemory));
    }

    @Test
    public void getUsableMemory() {
        // 获得JVM最大可用内存，计算方法为：最大内存-总内存+剩余内存
        long usableMemory = RuntimeUtil.getUsableMemory();
        System.out.println(ByteSizeConvert.model().format(usableMemory));
    }

}