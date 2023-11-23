package io.github.kuugasky.kuugatool.core.string;

import com.google.common.base.Splitter;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class SplitterUtilTest {

    @Test
    public void on() {
        Iterable<String> split = SplitterUtil.COMMA_JOINER.split("a,b,c,d,e,f,g");
        System.out.println(split);
        // limit拆分集合个数，逐个截取，剩的全部给最后一个。如果limit值大于length，则按length拆分
        Iterable<String> split1 = SplitterUtil.COMMA_JOINER.limit(10).split("a,b,c,d,e,f,g");
        split1.forEach(System.out::println);
        System.out.println(StringUtil.repeat('-', 30));

        Map<String, String> split2 = SplitterUtil.COMMA_JOINER.withKeyValueSeparator('-').split("a-b,c-d");
        System.out.println(split2);
    }

    @Test
    void testOn() {
        Iterable<String> split = SplitterUtil.COMMA_JOINER.split("a,b,c,d,e,f,g");
        System.out.println(StringUtil.formatString(split));
        System.out.println(StringUtil.repeat("=", 50));
        Iterable<String> splitPound = SplitterUtil.POUND_SIGN_JOINER.split("a#b#c#d#e#f#g");
        System.out.println(StringUtil.formatString(splitPound));
    }

    @Test
    void toMap() {
        Map<String, String> stringStringMap = SplitterUtil.withKeyValueSeparatorToMap(",", "=", "1=2,3=4");
        System.out.println(StringUtil.formatString(stringStringMap));
        System.out.println(StringUtil.repeat("-", 50));
        Map<String, String> stringStringMap1 = SplitterUtil.withKeyValueSeparatorToMap(',', '=', "1=2,3=4");
        System.out.println(StringUtil.formatString(stringStringMap1));
        System.out.println(StringUtil.repeat("-", 50));
        Map<String, String> stringStringMap2 = SplitterUtil.withKeyValueSeparatorToMap(';', '=', "1=2,3=4");
        System.out.println(StringUtil.formatString(stringStringMap2));
    }

    @Test
    void toList() {
        String str = "1-2-3-4-5-6";
        List<String> list = SplitterUtil.splitToList("-", str);
        System.out.println(list);
        List<String> list2 = SplitterUtil.splitToList('-', str);
        System.out.println(list2);

        // 如果字符串中带有空格，还可以先去掉空格
        List<String> list1 = Splitter.on("-").omitEmptyStrings().trimResults().splitToList(str);
        System.out.println(list1);
    }

}