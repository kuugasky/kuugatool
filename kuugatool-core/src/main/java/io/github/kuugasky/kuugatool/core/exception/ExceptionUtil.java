package io.github.kuugasky.kuugatool.core.exception;

import io.github.kuugasky.kuugatool.core.collection.ArrayUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

/**
 * 获取异常的堆栈信息
 *
 * @author kuuga
 * @date 2019-10-10
 */
public final class ExceptionUtil {

    /**
     * 定义私有构造函数来屏蔽这个隐式公有构造函数
     */
    private ExceptionUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 获得完整消息，包括异常名，消息格式为：{SimpleClassName}: {ThrowableMessage}
     *
     * @param e 异常
     * @return 完整消息
     */
    public static String getMessage(Throwable e) {
        if (null == e) {
            return null;
        }
        return StringUtil.format("{}: {}", e.getClass().getSimpleName(), e.getMessage());
    }

    public static String stackTrace(Exception ex) {
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            ex.printStackTrace(pw);
            return sw.toString();
        }
    }

    /**
     * 获取异常的堆栈信息
     *
     * @param t t
     * @return string
     */
    public static String stackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw)) {
            t.printStackTrace(pw);
            return sw.toString();
        }
    }

    /**
     * 打印异常的堆栈信息
     *
     * @param ex ex
     * @return string
     */
    public static String stackTracePrint(Exception ex) {
        StackTraceElement[] stackElements = ex.getStackTrace();
        if (ArrayUtil.isEmpty(stackElements)) {
            return StringUtil.EMPTY;
        }
        StringBuilder stringBuilder = new StringBuilder();
        List<StackTraceElement> stackTraceElements = ArrayUtil.toList(stackElements);
        for (StackTraceElement stackElement : stackTraceElements) {
            String message = stackElement.toString();
            stringBuilder.append(message).append("\n");
        }
        return stringBuilder.toString();
    }

}
