package io.github.kuugasky.kuugatool.core.lang;

import io.github.kuugasky.kuugatool.core.concurrent.ThreadUtil;
import org.junit.jupiter.api.Test;

/**
 * 控制台单元测试
 *
 * @author Looly
 */
public class ConsoleTest {

    @Test
    public void logTest() {
        Console.log();

        String[] a = {"abc", "bcd", "def"};
        Console.log(a);

        Console.log("This is Console log for {}.", "test");
    }

    @Test
    public void logTest2() {
        Console.log("a", "b", "c");
        Console.log((Object) "a", "b", "c");
    }

    @Test
    public void printTest() {
        String[] a = {"abc", "bcd", "def"};
        Console.print(a);

        Console.log("This is Console print for {}.", "test");
    }

    @Test
    public void printTest2() {
        Console.print("a", "b", "c");
        Console.print((Object) "a", "b", "c");
    }

    @Test
    public void errorTest() {
        Console.error();

        String[] a = {"abc", "bcd", "def"};
        Console.error(a);

        Console.error("This is Console error for {}.", "test");
    }

    @Test
    public void errorTest2() {
        Console.error("a", "b", "c");
        Console.error((Object) "a", "b", "c");
    }

    @Test
    public void inputTest() {
        // public static void main(String[] args) {
        Console.log("Please input something: ");
        String input = Console.input();
        Console.log(input);
    }

    @Test
    public void printProgressTest() {
        for (int i = 0; i < 100; i++) {
            Console.printProgress('#', 100, i / 100D);
            ThreadUtil.sleep(200);
        }
    }

    public static void main(String[] args) {
        // Console.ansiLog(Color.GRAY, "test");
        Console.blackLog("黑色文本");
        Console.blueLog("蓝色文本");
        Console.cyanLog("青色文本");
        Console.greenLog("绿色文本");
        Console.magentaLog("品红文本");
        Console.redLog("红色文本");
        Console.whiteLog("白色本文");
        Console.yellowLog("黄色文本");
    }

    @Test
    void lineNumber() {
        Integer integer = Console.lineNumber();
        System.out.println(integer);
    }

}
