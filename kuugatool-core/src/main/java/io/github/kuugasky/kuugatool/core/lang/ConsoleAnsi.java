package io.github.kuugasky.kuugatool.core.lang;

import org.fusesource.jansi.Ansi;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * ConsoleAnsi
 *
 * @author kuuga
 * @since 2021/3/13
 */
public class ConsoleAnsi {

    private static Ansi ansi;

    public ConsoleAnsi() {
    }

    public static ConsoleAnsi init() {
        ansi = ansi().eraseScreen();
        return new ConsoleAnsi();
    }

    public ConsoleAnsi color(Ansi.Color color) {
        ansi.fg(color);
        return this;
    }

    public ConsoleAnsi append(String text) {
        ansi.a(text);
        return this;
    }

    public void print() {
        ansi.reset();
        Console.log(ansi.reset());
    }

}
