package io.github.kuugasky.kuugatool.core.algorithm.rpn;

import io.github.kuugasky.kuugatool.core.exception.ExceptionUtil;
import io.github.kuugasky.kuugatool.core.number.NumberUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * <pre>
 * 完整版的逆波兰计算器，功能包括
 * 支持 + - * / ( )
 * 多位数，支持小数,
 * 兼容处理, 过滤任何空白字符，包括空格、制表符、换页符
 *
 * 逆波兰计算器完整版考虑的因素较多，下面给出完整版代码供同学们学习，其基本思路和前面一样，也是使用到：中缀表达式转后缀表达式。
 * </pre>
 *
 * @author kuuga
 */
public class ReversePolishMultiCalc {

    /**
     * 匹配 + - * / ( ) 运算符
     */
    static final String SYMBOL = "[+\\-*/()]";

    static final String LEFT = "(";
    static final String RIGHT = ")";
    static final String ADD = "+";
    static final String MINUS = "-";
    static final String TIMES = "*";
    static final String DIVISION = "/";

    /**
     * 加減 + -
     */
    static final int LEVEL_01 = 1;
    /**
     * 乘除 * /
     */
    static final int LEVEL_02 = 2;
    /**
     * 括号
     */
    static final int LEVEL_HIGH = Integer.MAX_VALUE;

    static Stack<String> stack = new Stack<>();
    static List<String> data = Collections.synchronizedList(new ArrayList<>());

    /**
     * 去除所有空白符
     *
     * @param infixExpression 中缀表达式
     * @return 移除所有空格后的中缀表达式
     */
    public static String replaceAllBlank(String infixExpression) {
        // \\s+ 匹配任何空白字符，包括空格、制表符、换页符等等, 等价于[ \f\n\r\t\v]
        return infixExpression.replaceAll("\\s+", "");
    }

    /**
     * 判断是不是数字 int double long float
     *
     * @param s s
     * @return boolean
     */
    public static boolean isNumber(String s) {
        return NumberUtil.isNumber(s);
    }

    /**
     * 判断是不是运算符
     *
     * @param s s
     * @return boolean
     */
    public static boolean isSymbol(String s) {
        return s.matches(SYMBOL);
    }

    /**
     * 匹配运算等级
     *
     * @param s s
     * @return int
     */
    public static int calcLevel(String s) {
        if ("+".equals(s) || "-".equals(s)) {
            return LEVEL_01;
        } else if ("*".equals(s) || "/".equals(s)) {
            return LEVEL_02;
        }
        return LEVEL_HIGH;
    }

    /**
     * 匹配
     *
     * @param infixExpression 中缀表达式
     */
    public static List<String> doMatch(String infixExpression) {
        if (infixExpression == null || infixExpression.trim().isEmpty()) {
            throw new RuntimeException("data is empty");
        }

        if (!isNumber(infixExpression.charAt(0) + "")) {
            throw new RuntimeException("data illegal,start not with a number");
        }

        infixExpression = replaceAllBlank(infixExpression);

        String each;
        int start = 0;

        // 中缀表达式长度
        int length = infixExpression.length();
        for (int i = 0; i < length; i++) {
            char charAt = infixExpression.charAt(i);
            if (isSymbol(charAt + "")) {
                // 运算符逻辑
                each = charAt + "";
                // 栈为空，(操作符，或者 操作符优先级大于栈顶优先级 && 操作符优先级不是( )的优先级 及是 ) 不能直接入栈
                if (stack.isEmpty() || LEFT.equals(each)
                        || ((calcLevel(each) > calcLevel(stack.peek())) && calcLevel(each) < LEVEL_HIGH)) {
                    stack.push(each);
                } else if (!stack.isEmpty() && calcLevel(each) <= calcLevel(stack.peek())) {
                    // 栈非空，操作符优先级小于等于栈顶优先级时出栈入列，直到栈为空，或者遇到了(，最后操作符入栈
                    while (!stack.isEmpty() && calcLevel(each) <= calcLevel(stack.peek())) {
                        if (calcLevel(stack.peek()) == LEVEL_HIGH) {
                            break;
                        }
                        data.add(stack.pop());
                    }
                    stack.push(each);
                } else if (RIGHT.equals(each)) {
                    // ) 操作符，依次出栈入列直到空栈或者遇到了第一个)操作符，此时)出栈
                    while (!stack.isEmpty()) {
                        calcLevel(stack.peek());
                        if (LEVEL_HIGH == calcLevel(stack.peek())) {
                            stack.pop();
                            break;
                        }
                        data.add(stack.pop());
                    }
                }
                start = i;    // 前一个运算符的位置
            } else {
                // 当前char是最后一个
                boolean isLastChar = i == length - 1;

                boolean nextCharIsSymbol = false;
                // 如果当前char不是最后一个，则判断下一个char是否运算符
                if (!isLastChar) {
                    // 当前char的下一个值
                    char nextChar = infixExpression.charAt(i + 1);
                    // 下一个char是运算符
                    nextCharIsSymbol = isSymbol(nextChar + "");
                }

                // 9.0时，9和.下一个不是运算符，所以都跳过，等到0时，下一个时+，这时就从中缀运算符中substring提取0,3，得到9.0
                if (isLastChar || nextCharIsSymbol) {
                    each = start == 0 ? infixExpression.substring(start, i + 1) : infixExpression.substring(start + 1, i + 1);
                    if (isNumber(each)) {
                        data.add(each);
                        continue;
                    }
                    throw new RuntimeException("data not match number");
                }
            }
        }
        // 如果栈里还有元素，此时元素需要依次出栈入列，可以想象栈里剩下栈顶为/，栈底为+，应该依次出栈入列，可以直接翻转整个stack 添加到队列
        Collections.reverse(stack);
        data.addAll(new ArrayList<>(stack));

        System.out.println(data);
        return data;
    }

    /**
     * 算出结果
     *
     * @param list list
     */
    public static void doCalc(List<String> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        if (list.size() == 1) {
            System.out.println(list);
            return;
        }
        ArrayList<String> list1 = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            list1.add(list.get(i));
            if (isSymbol(list.get(i))) {
                Double d1 = doTheMath(list.get(i - 2), list.get(i - 1), list.get(i));
                list1.remove(i);
                list1.remove(i - 1);
                list1.set(i - 2, d1 + "");
                list1.addAll(list.subList(i + 1, list.size()));
                break;
            }
        }
        doCalc(list1);
    }

    /**
     * 运算
     *
     * @param s1     s1
     * @param s2     s2
     * @param symbol symbol
     * @return double
     */
    public static Double doTheMath(String s1, String s2, String symbol) {
        return switch (symbol) {
            case ADD -> Double.parseDouble(s1) + Double.parseDouble(s2);
            case MINUS -> Double.parseDouble(s1) - Double.parseDouble(s2);
            case TIMES -> Double.parseDouble(s1) * Double.parseDouble(s2);
            case DIVISION -> Double.parseDouble(s1) / Double.parseDouble(s2);
            default -> null;
        };

    }

    public static void main(String[] args) {
        String math = "9.02 + (3 - 1) * 3 + 10 / 2";
        // String math = "12.8 + (2 - 3.55)*4+10/5.0";
        try {
            // 中缀表达式转后缀表达式
            List<String> suffix = doMatch(math);

            System.out.println("中缀表达式：" + math);
            System.out.println("后缀表达式：" + String.join(" ", suffix));

            doCalc(suffix);
        } catch (Exception e) {
            System.out.println(ExceptionUtil.getMessage(e));
        }
    }

}

