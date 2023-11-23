package io.github.kuugasky.kuugatool.core.string;

import org.junit.jupiter.api.Test;

/**
 * StringInternTest
 *
 * @author kuuga
 * @since 2022/6/3 13:52
 */
public class StringInternTest {

    public static void main(String[] args) {
        String s1 = new String("Hol") + new String("lis");
        s1.intern();
        String s2 = new StringBuilder("Holl").append("is").toString();
        System.out.println(s2 == s1);
        System.out.println(s2.intern() == s1);
    }

    @Test
    public void test() {
        String s1 = new String("Hol") + new String("lis");
        String s2 = s1.intern();
        System.out.println(s1 == s2);
    }

    @Test
    public void test1() {
        char s = '\u2001';
        System.out.println("'" + String.valueOf(s).replace(" ", StringUtil.EMPTY) + "'");
    }

}
