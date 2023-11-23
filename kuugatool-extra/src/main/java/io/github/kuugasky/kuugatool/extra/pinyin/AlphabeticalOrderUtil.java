package io.github.kuugasky.kuugatool.extra.pinyin;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.enums.SortType;
import io.github.kuugasky.kuugatool.core.string.StringUtil;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 文本根据首字母排序工具类
 * <p>
 * 规则：小写字母在大写字母前面，由A-Z
 *
 * @author kuuga
 */
public final class AlphabeticalOrderUtil {

    private static final String[] ARRAY_OF_UPPERCASE_LETTERS = {
            "A", "B", "C", "D", "E", "F", "G",
            "H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"
    };

    /**
     * 字符串集合根据首字母排序【默认升序】
     *
     * @param words 字符串集合
     * @return 排序后的字符串集合
     */
    public static List<String> sort(List<String> words) {
        return sort(words, SortType.ASC);
    }

    /**
     * 字符串集合根据首字母排序
     *
     * @param words    字符串集合
     * @param sortType 排序方式
     * @return 排序后的字符串集合
     */
    public static List<String> sort(List<String> words, SortType sortType) {
        if (ListUtil.isEmpty(words)) {
            return words;
        }
        List<word> list = new ArrayList<>();
        List<String> results = new ArrayList<>();

        words.forEach(word -> {
            String wordPinyin = PinyinUtil.toShortPinYin(word);
            AlphabeticalOrderUtil.word w = indexNum(wordPinyin);
            list.add(new word(word, w.indexNumber));
        });

        return switch (sortType) {
            case ASC -> {
                list.sort(new Forward());
                list.forEach(word -> results.add(word.content));
                yield results;
            }
            case DESC -> {
                list.sort(new Back());
                list.forEach(word -> results.add(word.content));
                yield results;
            }
        };
    }

    private static word indexNum(String word) {
        String res = StringUtil.EMPTY;
        String letter = word.substring(0, 1);
        for (int i = 0; i < ARRAY_OF_UPPERCASE_LETTERS.length; i++) {
            String pattern = ARRAY_OF_UPPERCASE_LETTERS[i];
            if (letter.equalsIgnoreCase(pattern)) {
                res += i;
                break;
            }
        }
        return new word(word, Integer.parseInt(res));
    }

    private static class Forward implements Comparator<word> {
        @Override
        public int compare(word w1, word w2) {
            return w1.indexNumber() - w2.indexNumber();
        }
    }

    private static class Back implements Comparator<word> {
        @Override
        public int compare(word w1, word w2) {
            return w2.indexNumber() - w1.indexNumber();
        }
    }

    /**
     * 单词
     *
     * @param content     文本
     * @param indexNumber 文本首字母在字母数组中的下标位置
     */
    private record word(String content, Integer indexNumber) {

    }

}

