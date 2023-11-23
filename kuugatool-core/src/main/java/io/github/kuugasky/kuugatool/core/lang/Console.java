package io.github.kuugasky.kuugatool.core.lang;

import io.github.kuugasky.kuugatool.core.collection.ArrayUtil;
import io.github.kuugasky.kuugatool.core.constants.KuugaCharConstants;
import io.github.kuugasky.kuugatool.core.constants.KuugaConstants;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.fusesource.jansi.Ansi;

import java.util.Scanner;

import static java.lang.System.err;
import static java.lang.System.out;
import static org.fusesource.jansi.Ansi.Color.*;
import static org.fusesource.jansi.Ansi.ansi;

/**
 * 命令行（控制台）工具方法类<br>
 * 此类主要针对{@link System#out} 和 {@link System#err} 做封装。
 *
 * @author Looly
 */
public class Console {

    private static final String TEMPLATE_VAR = "{}";

    // --------------------------------------------------------------------------------- Log

    /**
     * 同 System.out.println()方法，打印控制台日志
     */
    public static void log() {
        out.println();
    }

    /**
     * 同 System.out.println()方法，打印控制台日志<br>
     * 如果传入打印对象为{@link Throwable}对象，那么同时打印堆栈
     *
     * @param obj 要打印的对象
     */
    public static void log(Object obj) {
        if (obj instanceof final Throwable e) {
            log(e, e.getMessage());
        } else {
            log(TEMPLATE_VAR, obj);
        }
    }

    /**
     * 同 System.out.println()方法，打印控制台日志<br>
     * 如果传入打印对象为{@link Throwable}对象，那么同时打印堆栈
     *
     * @param obj1      第一个要打印的对象
     * @param otherObjs 其它要打印的对象
     * @since 5.4.3
     */
    public static void log(Object obj1, Object... otherObjs) {
        if (ArrayUtil.isEmpty(otherObjs)) {
            log(obj1);
        } else {
            log(buildTemplateSplitBySpace(otherObjs.length + 1), ArrayUtil.insert(otherObjs, 0, obj1));
        }
    }

    /**
     * 同 System.out.println()方法，打印控制台日志<br>
     * 当传入template无"{}"时，被认为非模板，直接打印多个参数以空格分隔
     *
     * @param template 文本模板，被替换的部分用 {} 表示
     * @param values   值
     */
    public static void log(String template, Object... values) {
        if (ArrayUtil.isEmpty(values) || StringUtil.contains(template, TEMPLATE_VAR)) {
            logInternal(template, values);
        } else {
            logInternal(buildTemplateSplitBySpace(values.length + 1), ArrayUtil.insert(values, 0, template));
        }
    }

    /**
     * 同 System.out.println()方法，打印控制台日志
     *
     * @param t        异常对象
     * @param template 文本模板，被替换的部分用 {} 表示
     * @param values   值
     */
    public static void log(Throwable t, String template, Object... values) {
        out.println(StringUtil.format(template, values));
        if (null != t) {
            t.printStackTrace();
            out.flush();
        }
    }

    /**
     * 同 System.out.println()方法，打印控制台日志
     *
     * @param template 文本模板，被替换的部分用 {} 表示
     * @param values   值
     * @since 5.4.3
     */
    private static void logInternal(String template, Object... values) {
        log(null, template, values);
    }

    // --------------------------------------------------------------------------------- print

    /**
     * 打印表格到控制台
     *
     * @param consoleTable 控制台表格
     * @since 5.4.5
     */
    public static void table(ConsoleTable consoleTable) {
        print(consoleTable.toString());
    }

    /**
     * 同 System.out.print()方法，打印控制台日志
     *
     * @param obj 要打印的对象
     * @since 3.3.1
     */
    public static void print(Object obj) {
        print(TEMPLATE_VAR, obj);
    }

    /**
     * 同 System.out.println()方法，打印控制台日志<br>
     * 如果传入打印对象为{@link Throwable}对象，那么同时打印堆栈
     *
     * @param obj1      第一个要打印的对象
     * @param otherObjs 其它要打印的对象
     * @since 5.4.3
     */
    public static void print(Object obj1, Object... otherObjs) {
        if (ArrayUtil.isEmpty(otherObjs)) {
            print(obj1);
        } else {
            print(buildTemplateSplitBySpace(otherObjs.length + 1), ArrayUtil.insert(otherObjs, 0, obj1));
        }
    }

    /**
     * 同 System.out.print()方法，打印控制台日志
     *
     * @param template 文本模板，被替换的部分用 {} 表示
     * @param values   值
     * @since 3.3.1
     */
    public static void print(String template, Object... values) {
        if (ArrayUtil.isEmpty(values) || StringUtil.contains(template, TEMPLATE_VAR)) {
            printInternal(template, values);
        } else {
            printInternal(buildTemplateSplitBySpace(values.length + 1), ArrayUtil.insert(values, 0, template));
        }
    }

    public static void println(String template, Object... values) {
        print(template, values);
        out.println();
    }

    /**
     * 打印进度条
     *
     * @param showChar 进度条提示字符，例如“#”
     * @param len      打印长度
     * @since 4.5.6
     */
    public static void printProgress(CharSequence showChar, int len) {
        print("{}{}", KuugaCharConstants.CR, StringUtil.repeatAndJoin(showChar, len, StringUtil.EMPTY));
    }

    /**
     * 打印进度条
     *
     * @param showChar 进度条提示字符，例如“#”
     * @param totalLen 总长度
     * @param rate     总长度所占比取值0~1
     * @since 4.5.6
     */
    public static void printProgress(char showChar, int totalLen, double rate) {
        printProgress(String.valueOf(showChar), totalLen, rate);
    }

    public static void printProgress(CharSequence showChar, int totalLen, double rate) {
        Assert.isTrue(rate >= 0 && rate <= 1, "Rate must between 0 and 1 (both include)");
        printProgress(showChar, (int) (totalLen * rate));
    }

    /**
     * 同 System.out.println()方法，打印控制台日志
     *
     * @param template 文本模板，被替换的部分用 {} 表示
     * @param values   值
     * @since 5.4.3
     */
    private static void printInternal(String template, Object... values) {
        out.print(StringUtil.format(template, values));
    }

    // --------------------------------------------------------------------------------- Error

    /**
     * 同 System.err.println()方法，打印控制台日志
     */
    public static void error() {
        err.println();
    }

    /**
     * 同 System.err.println()方法，打印控制台日志
     *
     * @param obj 要打印的对象
     */
    public static void error(Object obj) {
        if (obj instanceof Throwable e) {
            error(e, e.getMessage());
        } else {
            error(TEMPLATE_VAR, obj);
        }
    }

    /**
     * 同 System.out.println()方法，打印控制台日志<br>
     * 如果传入打印对象为{@link Throwable}对象，那么同时打印堆栈
     *
     * @param obj1      第一个要打印的对象
     * @param otherObjs 其它要打印的对象
     * @since 5.4.3
     */
    public static void error(Object obj1, Object... otherObjs) {
        if (ArrayUtil.isEmpty(otherObjs)) {
            error(obj1);
        } else {
            error(buildTemplateSplitBySpace(otherObjs.length + 1), ArrayUtil.insert(otherObjs, 0, obj1));
        }
    }

    /**
     * 同 System.err.println()方法，打印控制台日志
     *
     * @param template 文本模板，被替换的部分用 {} 表示
     * @param values   值
     */
    public static void error(String template, Object... values) {
        if (ArrayUtil.isEmpty(values) || StringUtil.contains(template, TEMPLATE_VAR)) {
            errorInternal(template, values);
        } else {
            errorInternal(buildTemplateSplitBySpace(values.length + 1), ArrayUtil.insert(values, 0, template));
        }
    }

    /**
     * 同 System.err.println()方法，打印控制台日志
     *
     * @param t        异常对象
     * @param template 文本模板，被替换的部分用 {} 表示
     * @param values   值
     */
    public static void error(Throwable t, String template, Object... values) {
        err.println(StringUtil.format(template, values));
        if (null != t) {
            t.printStackTrace(err);
            err.flush();
        }
    }

    /**
     * 同 System.err.println()方法，打印控制台日志
     *
     * @param template 文本模板，被替换的部分用 {} 表示
     * @param values   值
     */
    private static void errorInternal(String template, Object... values) {
        error(null, template, values);
    }

    // --------------------------------------------------------------------------------- in

    /**
     * 创建从控制台读取内容的{@link Scanner}
     *
     * @return {@link Scanner}
     * @since 3.3.1
     */
    public static Scanner scanner() {
        return new Scanner(System.in);
    }

    /**
     * 读取用户输入的内容（在控制台敲回车前的内容）
     *
     * @return 用户输入的内容
     * @since 3.3.1
     */
    public static String input() {
        return scanner().next();
    }

    // --------------------------------------------------------------------------------- console lineNumber

    /**
     * 返回当前位置+行号 (不支持Lambda、内部类、递归内使用)
     *
     * @return 返回当前行号
     * @author dahuoyzs
     * @since 5.2.5
     */
    public static String where() {
        final StackTraceElement stackTraceElement = new Throwable().getStackTrace()[1];
        final String className = stackTraceElement.getClassName();
        final String methodName = stackTraceElement.getMethodName();
        final String fileName = stackTraceElement.getFileName();
        final Integer lineNumber = stackTraceElement.getLineNumber();
        return String.format("%s.%s(%s:%s)", className, methodName, fileName, lineNumber);
    }

    /**
     * 返回当前行号 (不支持Lambda、内部类、递归内使用)
     *
     * @return 返回当前行号
     * @since 5.2.5
     */
    public static Integer lineNumber() {
        return new Throwable().getStackTrace()[1].getLineNumber();
    }

    /**
     * 构建空格分隔的模板，类似于"{} {} {} {}"
     *
     * @param count 变量数量
     * @return 模板
     */
    private static String buildTemplateSplitBySpace(int count) {
        return StringUtil.repeatAndJoin(TEMPLATE_VAR, count, KuugaConstants.SPACE);
    }

    // Ansi ==============================================================================================================================

    /**
     * 同 System.out.println()方法，打印控制台日志<br>
     * 如果传入打印对象为{@link Throwable}对象，那么同时打印堆栈
     *
     * @param obj1      第一个要打印的对象
     * @param otherObjs 其它要打印的对象
     * @since 5.4.3
     */
    public static void ansiLog(Ansi.Color color, Object obj1, Object... otherObjs) {
        if (color == null) {
            log(obj1, otherObjs);
        }
        if (ArrayUtil.isEmpty(otherObjs)) {
            log(colorInit(color).a(obj1).reset());
        } else {
            log(colorInit(color).a(StringUtil.format(obj1.toString(), otherObjs)).reset());
        }
    }

    public static void blackLog(Object obj1, Object... otherObjs) {
        ansiLog(BLACK, obj1, otherObjs);
    }

    public static void redLog(Object obj1, Object... otherObjs) {
        ansiLog(RED, obj1, otherObjs);
    }

    public static void greenLog(Object obj1, Object... otherObjs) {
        ansiLog(GREEN, obj1, otherObjs);
    }

    public static void yellowLog(Object obj1, Object... otherObjs) {
        ansiLog(YELLOW, obj1, otherObjs);
    }

    public static void blueLog(Object obj1, Object... otherObjs) {
        ansiLog(BLUE, obj1, otherObjs);
    }

    public static void magentaLog(Object obj1, Object... otherObjs) {
        ansiLog(MAGENTA, obj1, otherObjs);
    }

    public static void cyanLog(Object obj1, Object... otherObjs) {
        ansiLog(CYAN, obj1, otherObjs);
    }

    public static void whiteLog(Object obj1, Object... otherObjs) {
        ansiLog(WHITE, obj1, otherObjs);
    }

    public static Ansi colorInit(Ansi.Color color) {
        if (color == null) {
            return ansi().eraseScreen().fg(WHITE);
        }
        return switch (color) {
            case BLACK -> ansi().eraseScreen().fg(BLACK);
            case RED -> ansi().eraseScreen().fg(RED);
            case GREEN -> ansi().eraseScreen().fg(GREEN);
            case YELLOW -> ansi().eraseScreen().fg(YELLOW);
            case BLUE -> ansi().eraseScreen().fg(BLUE);
            case MAGENTA -> ansi().eraseScreen().fg(MAGENTA);
            case CYAN -> ansi().eraseScreen().fg(CYAN);
            case WHITE -> ansi().eraseScreen().fg(WHITE);
            default -> ansi().eraseScreen().fg(DEFAULT);
        };
    }

}
