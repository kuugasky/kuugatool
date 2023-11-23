package io.github.kuugasky.kuugatool.extra.pinyin;

import io.github.kuugasky.kuugatool.core.enums.SortType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kuuga
 */
public class AlphabeticalOrderUtilTest {

    public static void main(String[] args) {
        List<String> words = new ArrayList<>();
        words.add("哔哩哔哩");
        words.add("ear");
        words.add("阿里巴巴");
        words.add("choice");
        words.add("name");
        words.add("酷狗");
        words.add("fight");
        words.add("中秋节");
        words.add("joy");
        words.add("Jack");
        words.add("京东");
        words.add("美团");
        words.add("金立");
        words.add("格力");
        words.add("美的");
        List<String> list = AlphabeticalOrderUtil.sort(words);
        for (String str : list) {
            System.out.println(str);
        }
        System.out.println("-------------------------------------");
        List<String> list1 = AlphabeticalOrderUtil.sort(words, SortType.DESC);
        for (String str : list1) {
            System.out.println(str);
        }
    }

}
