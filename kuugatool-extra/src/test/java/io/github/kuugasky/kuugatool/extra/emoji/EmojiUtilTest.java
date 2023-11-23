package io.github.kuugasky.kuugatool.extra.emoji;

import com.vdurmont.emoji.Emoji;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class EmojiUtilTest {

    @Test
    void isEmoji() {
        System.out.println(EmojiUtil.isEmoji("😄"));
        System.out.println(EmojiUtil.isEmoji("yes"));
    }

    @Test
    void toUnicodeTest() {
        String emoji = EmojiUtil.toUnicode(":smile:");
        System.out.println(emoji);
        assertEquals("😄", emoji);
    }

    @Test
    void toAliasTest() {
        String alias = EmojiUtil.toAlias("😄");
        System.out.println(alias);
        assertEquals(":smile:", alias);
    }

    @Test
    void get() {
        String alias = EmojiUtil.toAlias("😄");
        System.out.println(alias);
        Emoji emoji = EmojiUtil.get(alias);
        System.out.println(StringUtil.formatString(emoji));
    }

    @Test
    void toHtmlHex() {
        String alias = EmojiUtil.toAlias("😄");
        System.out.println(alias);
        String s = EmojiUtil.toHtmlHex(alias);
        System.out.println(s);

        System.out.println(EmojiUtil.toHtmlHex("<code>\uD83D\uDC66\uD83C\uDFFF</code>"));
        System.out.println(EmojiUtil.toHtmlHex("\uD83D\uDC66\uD83C\uDFFF"));
    }

    @Test
    void toHtml() {
        String alias = EmojiUtil.toAlias("😄");
        System.out.println(alias);
        String s = EmojiUtil.toHtml(alias);
        System.out.println(s);

        System.out.println(EmojiUtil.toHtml("<code>\uD83D\uDC66\uD83C\uDFFF</code>"));
        System.out.println(EmojiUtil.toHtml("\uD83D\uDC66\uD83C\uDFFF"));
    }

    @Test
    void removeAllEmojis() {
        String alias = EmojiUtil.toAlias("😄");
        System.out.println(alias);
        String s = EmojiUtil.removeAllEmojis(alias);
        System.out.println(s);

        System.out.println(EmojiUtil.removeAllEmojis("<code>\uD83D\uDC66\uD83C\uDFFF</code>"));
        System.out.println(EmojiUtil.removeAllEmojis("\uD83D\uDC66\uD83C\uDFFF"));
    }

    @Test
    void extractEmojis() {
        List<String> emojis = EmojiUtil.extractEmojis("😄");
        System.out.println(emojis);

        System.out.println(EmojiUtil.extractEmojis("<code>\uD83D\uDC66\uD83C\uDFFF</code>"));
        System.out.println(EmojiUtil.extractEmojis("\uD83D\uDC66\uD83C\uDFFF"));
    }

    @Test
    void containsEmojiTest() {
        boolean containsEmoji = EmojiUtil.containsEmoji("测试一下是否包含EMOJ:😄");
        assertTrue(containsEmoji);
        boolean notContainsEmoji = EmojiUtil.containsEmoji("不包含EMOJ:^_^");
        assertFalse(notContainsEmoji);
    }

    @Test
    void getByTag() {
        Set<Emoji> happy = EmojiUtil.getByTag("happy");
        happy.forEach(x -> System.out.println(StringUtil.formatString(x)));
        System.out.println(StringUtil.repeatNormal());
        Set<Emoji> yes = EmojiUtil.getByTag("yes");
        yes.forEach(x -> System.out.println(StringUtil.formatString(x)));
        System.out.println(StringUtil.repeatNormal());
        System.out.println(EmojiUtil.getByTag("😄"));
        System.out.println(EmojiUtil.getByTag(EmojiUtil.toAlias("😄")));
        System.out.println(EmojiUtil.getByTag("\uD83D\uDE04"));
    }

}
