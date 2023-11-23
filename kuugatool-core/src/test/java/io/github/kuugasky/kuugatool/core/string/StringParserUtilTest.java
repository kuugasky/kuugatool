package io.github.kuugasky.kuugatool.core.string;

import io.github.kuugasky.kuugatool.core.enums.ByteType;
import org.junit.jupiter.api.Test;

import java.util.List;

class StringParserUtilTest {

    @Test
    void parse() {
        List<String> parse = StringParserUtil.parse("1,2,3");
        System.out.println(parse);
    }

    @Test
    void testParse() {
        List<String> parse = StringParserUtil.parse(",", "1,2,3,,");
        System.out.println(parse);
    }

    @Test
    void testParse1() {
        List<String> parse = StringParserUtil.parse(",", "1,2,3,,5", false);
        System.out.println(parse);
        List<String> parse1 = StringParserUtil.parse(",", "1,2,3,,5", true);
        System.out.println(parse1);
    }

    @Test
    void testParse2() {
        List<ByteType> parse2 = StringParserUtil.parse("MB,G,KB,", ByteType.class);
        System.out.println(parse2);
        List<ByteType> parse = StringParserUtil.parse("MB,,G,KB,", ByteType.class, true);
        System.out.println(parse);
        List<ByteType> parse1 = StringParserUtil.parse(",", ",", ",", ",MB,G,KB,", ByteType.class);
        System.out.println(parse1);
    }

    @Test
    void testParse3() {
        List<ByteType> parse = StringParserUtil.parse("MB,,G,KB,", ByteType.class, true);
        System.out.println(parse);
    }

    @Test
    void testParse4() {
        List<ByteType> parse = StringParserUtil.parse(",", "MB,,G,KB,", ByteType.class, true);
        System.out.println(parse);
    }

    @Test
    void testParse5() {
        List<ByteType> parse = StringParserUtil.parse(",", "#", "#", "#MB,,G,KB,#", ByteType.class);
        System.out.println(parse);
    }

    @Test
    void testParse6() {
        List<ByteType> parse = StringParserUtil.parse(",", "#", "#", "#MB,,G,KB,#", ByteType.class, true);
        System.out.println(parse);
    }

    @Test
    void testParse7() {
    }
}