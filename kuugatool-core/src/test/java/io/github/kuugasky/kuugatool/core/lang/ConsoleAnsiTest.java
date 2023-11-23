package io.github.kuugasky.kuugatool.core.lang;

import junit.framework.TestCase;
import org.fusesource.jansi.Ansi;
import org.junit.jupiter.api.Test;

import static org.fusesource.jansi.Ansi.ansi;

public class ConsoleAnsiTest extends TestCase {

    public static void main(String[] args) {
        ConsoleAnsi.init().color(Ansi.Color.BLACK).append("kuuga").print();
        ConsoleAnsi.init().color(Ansi.Color.RED).append("kuuga").print();
        ConsoleAnsi.init().color(Ansi.Color.GREEN).append("kuuga").print();
        ConsoleAnsi.init().color(Ansi.Color.YELLOW).append("kuuga").print();
        ConsoleAnsi.init().color(Ansi.Color.BLUE).append("kuuga").print();
        ConsoleAnsi.init().color(Ansi.Color.MAGENTA).append("kuuga").print();
        ConsoleAnsi.init().color(Ansi.Color.WHITE).append("kuuga").print();
        ConsoleAnsi.init().color(Ansi.Color.DEFAULT).append("kuuga").print();

        System.out.println(ansi().eraseScreen().bg(Ansi.Color.RED).a("kuuga")
                .bgGreen().a(" is ")
                .bgRgb(100, 100, 100).fg(3).a(" a bog ").reset());

    }

    @Test
    public void testInit() {
    }

    @Test
    public void testColor() {
    }

    @Test
    public void testAppend() {
    }

    @Test
    public void testPrint() {
    }

}