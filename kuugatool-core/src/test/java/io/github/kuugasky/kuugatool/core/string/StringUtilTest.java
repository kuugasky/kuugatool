package io.github.kuugasky.kuugatool.core.string;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.collection.SetUtil;
import io.github.kuugasky.kuugatool.core.constants.KuugaCharConstants;
import io.github.kuugasky.kuugatool.core.entity.KuugaDTO;
import io.github.kuugasky.kuugatool.core.entity.KuugaModel;
import io.github.kuugasky.kuugatool.core.lang.Console;
import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

class StringUtilTest {

    @Test
    void ifNull() {
        String a = null;
        System.out.println(StringUtil.ifNull(a, "b"));
    }

    @Test
    void isEmpty() {
        Object a = null;
        System.out.println(StringUtil.isEmpty(a));
    }

    @Test
    void isEmpty2() {
        Object a = null;
        System.out.println(StringUtil.isEmpty(a, null));
        System.out.println(StringUtil.isEmpty(null, "1"));
    }

    @Test
    void testIsEmpty() {
        Object a = null, b = null;
        System.out.println(StringUtil.containsEmpty(a, b));
        b = "b";
        System.out.println(StringUtil.containsEmpty(a, b));
    }

    @Test
    void hasText() {
        Object a = "null";
        System.out.println(StringUtil.hasText(a));
    }

    @Test
    void hasText2() {
        Object a = "null";
        Object b = "null";
        System.out.println(StringUtil.hasText(a, b));
    }

    @Test
    void testHasText() {
        Object a = "null";
        Object b = null;
        System.out.println(StringUtil.hasText(a, b));
    }

    @Test
    void testToString() {
        System.out.println(StringUtil.toString(KuugaModel.builder().build()));
    }

    @Test
    void formatString() {
        System.out.println(StringUtil.formatString(KuugaModel.builder().build()));
        System.out.println(StringUtil.repeat("-", 100));
        List<KuugaModel> list = ListUtil.newArrayList();
        // List<KuugaModel> list = new LinkedList<>();
        list.add(KuugaModel.builder().name("1").build());
        list.add(KuugaModel.builder().name("2").build());
        list.add(KuugaModel.builder().name("3").build());
        System.out.println(StringUtil.formatString(list));
        System.out.println(StringUtil.repeat("-", 100));
        Set<KuugaModel> set = new HashSet<>();
        // Vector<KuugaModel> set = new Vector<>();
        set.add(KuugaModel.builder().name("1").build());
        set.add(KuugaModel.builder().name("2").build());
        set.add(KuugaModel.builder().name("3").build());
        System.out.println(StringUtil.formatString(set));
        System.out.println(StringUtil.repeat("-", 100));
        Map<String, String> map = new HashMap<>();
        // Map<String, String> map = new TreeMap<>();
        map.put("1", "kuuga1");
        map.put("2", "kuuga2_2");
        map.put("3", "kuuga3_3_3");
        System.out.println(StringUtil.formatString(map));
    }

    @Test
    void trim() {
        // 去掉\t\r\n和半角空格，但是不能去掉全角空格和中间空格
        System.out.println("1-->" + StringUtil.trim("\t　ab c d \r\n"));
        // 去掉\t\r\n和半角空格和全角空格，但是不能去掉中间空格
        System.out.println("2-->" + "\t　ab c d \r\n".strip());
        System.out.println("2.1-->" + StringUtil.strip("\t　ab c d \r\n"));
        // 去掉前面空格，包括\t\r\n和全角空格和半角空格
        System.out.println("3-->" + "\t\r\n　 ab c d \r\n".stripLeading());
        System.out.println("3.1-->" + StringUtil.stripLeading("\t\r\n　 ab c d \r\n"));
        // 去掉后面空格，包括\t\r\n和全角空格和半角空格
        System.out.println("4-->" + "\t\r\n　 ab c d　 \t\r\n".stripTrailing());
        System.out.println("4.1-->" + StringUtil.stripTrailing("\t\r\n　 ab c d　 \t\r\n"));
        // 去掉所有空格，包括\t\r\n和全角空格和半角空格，但是不包含文本中间空格
        System.out.println("5-->" + StringUtil.removeAllSpace("\t　ab c d \r\n"));
        // 去掉所有空格，包括\t\r\n和全角空格和半角空格，包含文本中间空格
        System.out.println("6-->" + StringUtil.removeAllSpace("\t　ab c d \r\n", true));
        System.out.println("7-->" + StringUtil.removeAll("\t　ab c d \r\n", "a"));
        System.out.println("8-->" + StringUtil.removeAll("\t　ab c d \r\n", "ab c"));
        System.out.println("9-->" + StringUtil.removeStart(StringUtil.removeAllSpace("\t　ab c d \r\n"), "ab"));
        System.out.println("10-->" + StringUtil.removeEnd(StringUtil.removeAllSpace("\t　ab c d \r\n", true), "cd"));
    }

    @Test
    void trimToNull() {
        System.out.println(StringUtil.trimToNull(StringUtil.EMPTY));
        System.out.println(StringUtil.trimToNull(null));
    }

    @Test
    void format() {
        System.out.println(StringUtil.format("My name is {}, i'm {} years old.", "Kuuga {} 0410", 30));
        System.out.printf("My name is %s, i'm %s years old.%n", "Kuuga {} 0410", 30);
    }

    @Test
    void formatPercentSign() {
        System.out.println(StringUtil.formatPercentSign("My name is %s, i'm %s years old.", "kuuga", 30));
    }

    @Test
    void toList() {
        List<String> strings = StringUtil.toList("a,b,c,d,e,f,g");
        System.out.println(StringUtil.toString(strings));
    }

    @Test
    void testToList() {
        List<String> strings = StringUtil.toList("a-b-c-d-e-f-g", "-");
        System.out.println(StringUtil.toString(strings));
    }

    @Test
    void toArray() {
        String[] strings = StringUtil.toArray("a,b,c,d,e,f,g");
        System.out.println(Arrays.toString(strings));
    }

    @Test
    void testToArray() {
        String[] strings = StringUtil.toArray("a-b-c-d-e-f-g", "-");
        System.out.println(Arrays.toString(strings));
    }

    @Test
    void toLowerCase() {
        System.out.println(StringUtil.toLowerCase(null));
        System.out.println(StringUtil.toLowerCase("aBcDeFg"));
    }

    @Test
    void toUpperCase() {
        System.out.println(StringUtil.toUpperCase(null));
        System.out.println(StringUtil.toUpperCase("aBcDeFg"));
    }

    @Test
    void testEquals() {
        System.out.println(StringUtil.equals("a", "A"));
    }

    @Test
    void equalsNo() {
        System.out.println(StringUtil.equalsNo("a", "A"));
    }

    @Test
    void equalsIgnoreCase() {
        System.out.println(StringUtil.equalsIgnoreCase("a", "A"));
    }

    @Test
    void isSubEquals() {
        String Str1 = "www.jb51.net";
        String Str2 = "jb51";
        String Str3 = "JB51";

        System.out.print("返回值 :");
        System.out.println(Str1.regionMatches(4, Str2, 0, 4));

        System.out.print("返回值 :");
        System.out.println(Str1.regionMatches(4, Str3, 0, 4));

        System.out.print("返回值 :");
        System.out.println(Str1.regionMatches(true, 4, Str3, 0, 4));

        System.out.println(StringUtil.regionMatches(Str1, 4, Str3, 0, 4, false));
        System.out.println(StringUtil.regionMatches(Str1, 4, Str3, 0, 4, true));
    }

    @Test
    void simplifySpace() {
        System.out.println(StringUtil.simplifySpace(" a b c "));
    }

    @Test
    void getString() {
        System.out.println(StringUtil.getString("abc".getBytes()));
    }

    @Test
    void testGetString() {
        System.out.println(StringUtil.getString("abc".getBytes(), StandardCharsets.UTF_8));
        System.out.println(StringUtil.getString("abc".getBytes(), StandardCharsets.UTF_8.name()));
        System.out.println(new String("abc".getBytes(Charset.defaultCharset())));
    }

    @Test
    void firstCharToLowerCase() {
        System.out.println(StringUtil.firstCharToLowerCase("AbcdefG"));
        System.out.println(StringUtil.firstCharToLowerCase("abcdefG"));
    }

    @Test
    void firstCharToUpperCase() {
        System.out.println(StringUtil.firstCharToUpperCase("abcdefg"));
        System.out.println(StringUtil.firstCharToUpperCase("Abcdefg"));
    }

    @Test
    void repeat() {
        System.out.println(StringUtil.repeat("-", 2));
        System.out.println(StringUtil.repeat('-', 2));
    }

    @Test
    void repeatAndJoin() {
        // Abcdefg-Abcdefg
        System.out.println(StringUtil.repeatAndJoin('*', 2, "-"));
        System.out.println(StringUtil.repeatAndJoin("Abcdefg", 2, "-"));
        System.out.println(StringUtil.repeatAndJoin('x', 2, '-'));
    }

    @Test
    void contains() {
        System.out.println(StringUtil.contains("abcdefg", "bc"));
        System.out.println(StringUtil.contains("abcdefg", new String[]{"x", "y", "z"}));
        System.out.println(StringUtil.contains("abcdefg", new String[]{"a", "x", "y", "z"}));

        System.out.println(StringUtil.repeat('-', 20));

        System.out.println(StringUtil.containsIgnoreCase("abcdefg", "C"));
        System.out.println(StringUtil.containsIgnoreCase("abcdefg", "C"));
        System.out.println(StringUtil.containsIgnoreCase("abcdefg", "X"));

        System.out.println(StringUtil.repeat('-', 20));

        System.out.println(StringUtil.containsIgnoreCase("abcdefg", new String[]{"C", "G"}));
        System.out.println(StringUtil.containsIgnoreCase("abcdefg", new String[]{"C", "X"}));
        System.out.println(StringUtil.containsIgnoreCase("abcdefg", new String[]{"X", "Y"}));
    }

    @Test
    void maxLength() {
        List<String> list = new ArrayList<>() {{
            add("abc");
            add("abcd");
            add("abcde");
            add("abcdef");
            add("abcdefg");
        }};
        System.out.println(StringUtil.maxLength(list));
    }

    @Test
    void testMaxLength() {
        Map<String, String> map = new HashMap<>() {{
            put("abc", "abc");
            put("abcd", "abcd");
            put("abcde", "abcde");
            put("abcdef", "abcdef");
            put("abcdefg", "abcdefg");
        }};
        System.out.println(StringUtil.maxLength(map));
        System.out.println(StringUtil.maxKeyLength(map));
        System.out.println(StringUtil.maxValueLength(map));
    }

    @Test
    void startWith() {
        System.out.println(StringUtil.startsWith("xyz", "x"));
        System.out.println(StringUtil.startsWith("xyz", "X"));
        System.out.println(StringUtil.startsWithIgnoreCase("xyz", "X"));
    }

    @Test
    void removeStart() {
        System.out.println(StringUtil.removeStart("abcdefg", "abc"));
        System.out.println(StringUtil.removeStart("abcdefg", "Abc"));
        System.out.println(StringUtil.removeStartIgnoreCase("abcdefg", "ABC"));
    }

    @Test
    void removeEnd() {
        System.out.println(StringUtil.removeEnd("abcdefg", "efg"));
        System.out.println(StringUtil.removeEnd("abcdefg", "Efg"));
        System.out.println(StringUtil.removeEndIgnoreCase("abcdefg", "EFG"));
    }

    @Test
    void lastText() {
        System.out.println(StringUtil.lastText("测试,数据,Kuuga"));
        System.out.println(StringUtil.lastText("测试，数据，Kuuga", "，"));
        System.out.println(StringUtil.lastText("测试，数,据，Kuuga-xxx", new String[]{"，", ",", "-"}));
    }

    @Test
    void testLastText() {
        System.out.println(StringUtil.lastText("测试,数据,Kuuga", ","));
    }

    @Test
    void firstText() {
        System.out.println(StringUtil.firstText("测试,数据,Kuuga"));
    }

    @Test
    void testFirstText() {
        System.out.println(StringUtil.firstText("测试,数据,Kuuga", ","));
    }

    @Test
    void testFirstText1() {
        System.out.println(StringUtil.firstText("测试，数据,Kuuga", new String[]{",", "，"}));
        System.out.println(StringUtil.firstText("Kuuga-测试，数据,Kuuga", new String[]{",", "，", "-"}));
    }

    @Test
    void join() {
        System.out.println(StringUtil.join("a", "b", "c"));
        System.out.println(StringUtil.joinWithDelimiter(",", "a", "b", "c"));
        System.out.println(StringUtil.joinWithDelimiter(",", SetUtil.newHashSet("a", "b", "c")));
        System.out.println(StringUtil.joinWithDelimiter(",", ListUtil.newArrayList("a", "b", "c")));
        System.out.println(StringUtil.join(",", "[", "]", ListUtil.newArrayList("a", "b", "c")));
    }

    @Test
    void escapeQueryChars() {
        System.out.println(StringUtil.escapeQueryChars("{a:b}"));
    }

    @Test
    void removeAll() {
        System.out.println(StringUtil.removeAll("abcdefg", "cde"));
    }

    @Test
    void testRemoveAll() {
        System.out.println(StringUtil.removeAll("abcdefg", "ef".toCharArray()));

        System.out.println(StringUtil.removeAll("1\r2\n3\t 4", KuugaCharConstants.CR, KuugaCharConstants.LF, KuugaCharConstants.TAB));
    }

    @Test
    void addPrefixIfNot() {
        System.out.println(StringUtil.addPrefixIfNot("abcdefg", "Kuuga-"));
    }

    @Test
    void addSuffixIfNot() {
        System.out.println(StringUtil.addSuffixIfNot("abcdefg", "-kuuga"));
    }

    @Test
    void padAfter() {
        System.out.println(StringUtil.padAfter("kuuga", 2, "-"));
    }


    @Test
    void endWith() {
        System.out.println(StringUtil.endsWith("Kuuga@", "@"));
    }

    @Test
    void testEndWith() {
        System.out.println(StringUtil.endsWith("Kuuga@", '@'));
    }

    @Test
    void testEndWith1() {
        System.out.println(StringUtil.endsWith("Kuuga@A", "a", true));
    }

    @Test
    void endWithIgnoreCase() {
        System.out.println(StringUtil.endsWithIgnoreCase("Kuuga@A", "a"));
    }

    @Test
    void endWithAny() {
        System.out.println(StringUtil.endsWithAny("Kuuga@A", "A", "b", "c"));
    }

    @Test
    void endWithAnyIgnoreCase() {
        System.out.println(StringUtil.endsWithAnyIgnoreCase("Kuuga@A", "a", "b", "c"));
    }

    @Test
    void removeAllSpace() {
        System.out.println(StringUtil.removeAllSpace(" 12\r3 "));
        System.out.println(StringUtil.removeAllSpace(" 12\t3"));
        System.out.println(StringUtil.removeAllSpace("12\n3 "));
        System.out.println(StringUtil.removeAllSpace("12\n 3 ", false));
    }

    @Test
    void testRemoveAllSpace() {
        System.out.println(StringUtil.removeAllSpace("12\n 3 ", true));
    }

    @Test
    void isAllCharMatch() {
        System.out.println(StringUtil.isAllCharMatch("ABC", Character::isUpperCase));
        System.out.println(StringUtil.isAllCharMatch("abc", Character::isUpperCase));
        // 判断字符串是否全部为字母组成，包括大写和小写字母和汉字
        System.out.println(StringUtil.isAllCharMatch("abcABC好样的", Character::isLetter));
        System.out.println(StringUtil.isAllCharMatch("abc123", Character::isLetter));
    }

    @Test
    void lines() {
        List<String> collect = StringUtil
                .lines("aaa\nbbb\nccc\n aaa")
                .map(x -> StringUtil.removeAllSpace(x).toUpperCase())
                .distinct().toList();
        collect.forEach(System.out::println);
    }

    @Test
    void sub() {
        // 截取下标从0（a前面）开始，下标后移0位，下标无实际位移
        System.out.println("1 = " + StringUtil.sub("abc", 0, 0));
        Console.greenLog("abc".substring(0, 0));
        // 截取下标0（a前面）开始，下标移动到1，下标往后移动1位
        System.out.println("2 = " + StringUtil.sub("abc", 0, 1));
        Console.greenLog("abc".substring(0, 1));
        // 截取下标1（b前面）开始，下标移动到1，下标无实际位移
        System.out.println("3 = " + StringUtil.sub("abc", 1, 1));
        Console.greenLog("abc".substring(1, 1));
        // 截取下标1（b前面）开始，下标移动到2，下标往后移动1位
        System.out.println("4 = " + StringUtil.sub("abc", 1, 2));
        Console.greenLog("abc".substring(1, 2));
        // 截取下标1（b前面）开始，下标移动到3，下标往后移动2位
        System.out.println("5 = " + StringUtil.sub("abc", 1, 3));
        Console.greenLog("abc".substring(1, 3));
        System.out.println("6 = " + StringUtil.sub("abc", -1, 0));
        Console.greenLog("abc".substring(-1, 0));
        System.out.println("7 = " + StringUtil.sub("abc", 4, 5));
        Console.greenLog("abc".substring(4, 5));
    }

    @Test
    void lastIndexOf() {
        System.out.println(StringUtil.lastIndexOf("a#b#c", '-'));
    }

    @Test
    void codeTypeChange() {
        System.out.println(StringUtil.codeTypeChange("kuuga", StandardCharsets.UTF_8));
        System.out.println(StringUtil.codeTypeChange("kuuga", StandardCharsets.ISO_8859_1));
        System.out.println(StringUtil.codeTypeChange("kuuga", StandardCharsets.UTF_8, StandardCharsets.ISO_8859_1));
    }

    @Test
    void utf8Bytes() {
        System.out.println(Arrays.toString(StringUtil.utf8Bytes("kuuga")));
        System.out.println(Arrays.toString(StringUtil.bytes("kuuga")));
        System.out.println(Arrays.toString(StringUtil.bytes("kuuga", StandardCharsets.UTF_8)));
    }

    @Test
    void byteBuffer() {
        System.out.println(StringUtil.byteBuffer("kuuga", "utf-8"));
    }

    @Test
    void utf8Str() {
        System.out.println(StringUtil.utf8Str(KuugaDTO.builder().name("kuuga").build()));
    }

    @Test
    void containsWhitespace() {
        System.out.println(StringUtil.containsWhitespace("a b"));

        char c1 = '\ufeff';
        char c2 = '\u202a';
        System.out.println(StringUtil.containsWhitespace(String.valueOf(c1)));
        System.out.println(StringUtil.containsWhitespace(String.valueOf(c2)));
    }

    @Test
    void testContainsWhitespace() {
        System.out.println(StringUtil.containsWhitespace(null));
        System.out.println(StringUtil.containsWhitespace((CharSequence) "a b"));
    }

    @Test
    void countOccurrencesOf() {
        System.out.println(StringUtil.countOccurrencesOf("a1a2a3a4a5", "a1"));
    }

}