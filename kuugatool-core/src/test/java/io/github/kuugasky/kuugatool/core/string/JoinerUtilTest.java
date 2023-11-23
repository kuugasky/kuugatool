package io.github.kuugasky.kuugatool.core.string;

import com.google.common.base.Splitter;
import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.collection.MapUtil;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class JoinerUtilTest {

    @Test
    public void on() {
        List<String> list = ListUtil.newArrayList("1", "2", "3");
        Map<String, String> map = MapUtil.newHashMap("1", "2", "3", "4");

        // 集合#跳空#拼接
        String join = JoinerUtil.on("#").skipNulls().join(list);
        System.out.println(join);

        // null值替代
        String join1 = JoinerUtil.on("#").useForNull("空值").join("旺财", "汤姆", "杰瑞", null, StringUtil.EMPTY);
        System.out.println(join1);

        // 带键值分隔符
        String join2 = JoinerUtil.on("#").withKeyValueSeparator("=").join(map);
        System.out.println(join2);

        // Joiner还提供了appendTo函数,对传入的StringBuilder作处理
        StringBuilder ab = new StringBuilder("start : ");
        StringBuilder join3 = JoinerUtil.on("#").appendTo(ab, list);
        System.out.println(join3.toString());

        // 分割-beta功能
        final Map<String, String> join4 = Splitter.on("&").withKeyValueSeparator("=").split("id=123&name=green&param1=HAN&param2=2");
        System.out.println(" map :" + join4.toString());

        String str = ",a ,,b ,";
        Iterable<String> split = Splitter.on(",")
                .omitEmptyStrings() // 忽略空值
                .trimResults() // 过滤结果中的空白
                .split(str);
        split.forEach(System.out::println);

    }

    @Test
    void testOn() {
        List<String> list = ListUtil.newArrayList("1", "2", "3", null);
        String join = JoinerUtil.COMMA_JOINER.skipNulls().join(list);
        System.out.println(join);
        System.out.println(StringUtil.repeat("-", 40));
        String join1 = JoinerUtil.POUND_SIGN_JOINER.useForNull(StringUtil.EMPTY).join(list);
        System.out.println(join1);
    }

    @Test
    void withKeyValueSeparatorJoin() {
        Map<Integer, String> map = MapUtil.newHashMap(1, "A", 2, "B");
        System.out.println(JoinerUtil.withKeyValueSeparatorJoin("&", "=", map));
    }

    @Test
    void testWithKeyValueSeparatorJoin() {
        Map<Integer, String> map = MapUtil.newHashMap(1, "A", 2, "B");
        System.out.println(JoinerUtil.withKeyValueSeparatorJoin('&', '=', map));
    }

}