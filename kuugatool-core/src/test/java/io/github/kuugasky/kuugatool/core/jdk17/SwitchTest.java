package io.github.kuugasky.kuugatool.core.jdk17;

import org.junit.jupiter.api.Test;

/**
 * SwitchTest
 *
 * @author kuuga
 * @since 2022/8/2 17:37
 */
public class SwitchTest {

    String day = "MONDAY";

    @Test
    void switchJdk11() {
        switch (day) {
            case "MONDAY":
                System.out.println(1);
                break;
            case "TUESDAY":
                System.out.println(2);
                break;
            case "WEDNESDAY":
                System.out.println(3);
                break;
            case "THURSDAY":
                System.out.println(4);
                break;
            case "FRIDAY":
                System.out.println(5);
                break;
            case "SATURDAY":
                System.out.println(6);
                break;
            case "SUNDAY":
                System.out.println(7);
                break;
            default:
                System.out.println(0);
                break;
        }
    }

    /**
     * JDK12简化了写法
     */
    @Test
    void switchJdk12() {
        switch (day) {
            case "MONDAY" -> System.out.println(1);
            case "TUESDAY" -> System.out.println(2);
            case "WEDNESDAY" -> System.out.println(3);
            case "THURSDAY" -> System.out.println(4);
            case "FRIDAY" -> System.out.println(5);
            case "SATURDAY" -> System.out.println(6);
            case "SUNDAY" -> System.out.println(7);
            default -> System.out.println(0);
        }
    }

    /**
     * JDK13增加了返回值
     */
    @Test
    void switchJdk13() {
        int i = switch (day) {
            case "MONDAY" -> 1;
            case "TUESDAY" -> 2;
            case "WEDNESDAY" -> 3;
            case "THURSDAY" -> 4;
            case "FRIDAY" -> 5;
            case "SATURDAY" -> 6;
            case "SUNDAY", "kuuga" -> 7;
            default -> 0;
        };
        System.out.println(i);
    }

    @Test
    void switchJdk17BeforeInstanceof() {
        // o instanceof Integer i 为JDK16新特性
        // if (o instanceof Integer i) {
        //     System.out.println(i);
        // } else if (o instanceof Long l) {
        //     System.out.println(l);
        // } else if (o instanceof Double d) {
        //     System.out.println(d);
        // } else if (o instanceof String s) {
        //     System.out.println(s);
        // } else {
        //     System.out.println("UNKNOWN");
        // }
    }

    @Test
    void switchJdk17AfterInstanceof() {
        // switch (o) {
        //     case Integer i -> System.out.println(i);
        //     case Long l -> System.out.println(l);
        //     case Double d -> System.out.println(d);
        //     case String s -> System.out.println(s);
        //     default -> System.out.println("UNKNOWN");
        // }
    }

    /**
     * JDK 14的Yielding a value
     * 当使用箭头标签时，箭头标签右边可以是表达式、throw语句或是代码块。
     * 如果是代码块，需要使用yield语句来返回值。
     * 下面代码中的print方法中的default语句的右边是一个代码块。在代码块中使用yield来返回值。
     * JDK 14引入了一个新的yield语句来产生一个值，该值成为封闭的switch表达式的值。
     */
    @Test
    void x() {
        // 声明变量score，并为其赋值为'C'
        var score = 'C';
        String result = switch (score) {
            case 'A', 'B' -> "上等";
            case 'C' -> "中等";
            case 'D', 'E' -> "下等";
            default -> {
                if (score > 100) {
                    yield "数据不能超过100";
                } else {
                    yield score + "此分数低于0分";
                }
            }
        };
        System.out.println(result);
    }

    // Java 17 之前
    @Test
    static void testFooBar(String s) {
        if (s == null) {
            System.out.println("oops!");
            return;
        }
        switch (s) {
            case "Foo", "Bar" -> System.out.println("Great");
            default -> System.out.println("Ok");
        }
    }

    // Java 17
    @Test
    void testFooBar1(String s) {
        switch (s) {
            // case null -> System.out.println("Oops");
            case "Foo", "Bar" -> System.out.println("Great");
            default -> System.out.println("Ok");
        }
    }
}

class People {
    Address address;
}

class Address {
    public void go() {
        System.out.println("我们出去旅游吧");
    }
}

