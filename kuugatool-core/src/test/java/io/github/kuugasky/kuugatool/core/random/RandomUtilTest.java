package io.github.kuugasky.kuugatool.core.random;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

public class RandomUtilTest {


    @Test
    public void getRandom() {
        System.out.println(RandomUtil.getRandom());
    }

    @Test
    public void createSecureRandom() {
        System.out.println(RandomUtil.createSecureRandom("kuuga".getBytes()));
    }

    @Test
    public void getSecureRandom() {
        System.out.println(RandomUtil.getSecureRandom());
    }

    @Test
    public void testGetSecureRandom() {
        System.out.println(RandomUtil.getSecureRandom("kuuga".getBytes()));
    }

    @Test
    public void testGetRandom() {
        System.out.println(RandomUtil.getRandom(true));
    }

    @Test
    public void randomBoolean() {
        System.out.println(RandomUtil.randomBoolean());
    }

    @Test
    public void randomInt() {
        System.out.println(RandomUtil.randomInt());
    }

    @Test
    public void testRandomInt() {
        System.out.println(RandomUtil.randomInt(10, 20));
    }

    @Test
    public void testRandomInt1() {
        System.out.println(RandomUtil.randomInt(10));
    }

    @Test
    public void randomLong() {
        System.out.println(RandomUtil.randomLong());
    }

    @Test
    public void testRandomLong() {
        System.out.println(RandomUtil.randomLong(10, 20));
    }

    @Test
    public void testRandomLong1() {
        System.out.println(RandomUtil.randomLong(10));
    }

    @Test
    public void randomDouble() {
        System.out.println(RandomUtil.randomDouble());
    }

    @Test
    public void testRandomDouble() {
        System.out.println(RandomUtil.randomDouble(10, 20));
    }

    @Test
    public void testRandomDouble1() {
        System.out.println(RandomUtil.randomDouble(10));
    }

    @Test
    public void testRandomDouble2() {
        System.out.println(RandomUtil.randomDouble(10, 2, RoundingMode.CEILING));
    }

    @Test
    public void testRandomDouble3() {
        System.out.println(RandomUtil.randomDouble(10, RoundingMode.CEILING));
    }

    @Test
    public void testRandomDouble4() {
        System.out.println(RandomUtil.randomDouble(10, 20, 2, RoundingMode.CEILING));
    }

    @Test
    public void randomBigDecimal() {
        System.out.println(RandomUtil.randomBigDecimal());
    }

    @Test
    public void testRandomBigDecimal() {
        System.out.println(RandomUtil.randomBigDecimal(new BigDecimal(10)));
    }

    @Test
    public void testRandomBigDecimal1() {
        System.out.println(RandomUtil.randomBigDecimal(new BigDecimal(10), new BigDecimal(20)));
    }

    @Test
    public void randomBytes() {
        System.out.println(Arrays.toString(RandomUtil.randomBytes(10)));
    }

    @Test
    public void randomEle() {
        List<Integer> list = ListUtil.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Integer integer = RandomUtil.randomEle(list);
        System.out.println(integer);
    }

    @Test
    public void testRandomEle() {
        List<Integer> list = ListUtil.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Integer integer = RandomUtil.randomEle(list, 5);
        System.out.println(integer);
    }

    @Test
    public void testRandomEle1() {
        Integer[] array = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        Integer integer = RandomUtil.randomEle(array);
        System.out.println(integer);
    }

    @Test
    public void randomEles() {
        List<Integer> list = ListUtil.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        List<Integer> integer = RandomUtil.randomEles(list, 3);
        System.out.println(integer);
    }

    @Test
    public void randomString() {
        System.out.println(RandomUtil.randomString(4));
    }

    @Test
    public void randomStringUpper() {
        System.out.println(RandomUtil.randomStringUpper(4));
    }

    @Test
    public void randomStringWithoutStr() {
        System.out.println(RandomUtil.randomStringWithoutStr(4, "kuuga"));
    }

    @Test
    public void testRandomString() {
    }

    @Test
    public void randomNumber() {
        System.out.println(RandomUtil.randomNumber());
    }

    @Test
    public void randomNumbers() {
        System.out.println(RandomUtil.randomNumbers(4));
    }

    @Test
    public void randomChar() {
        System.out.println(RandomUtil.randomChar());
    }

    @Test
    public void testRandomChar() {
        System.out.println(RandomUtil.randomChar(RandomUtil.BASE_CHAR_NUMBER));
    }

    @Test
    public void randomColor() {
        System.out.println(RandomUtil.randomColor());
    }

    @Test
    public void randomUUID() {
        System.out.println(RandomUtil.randomUUID());
    }

    @Test
    public void simpleUUID() {
        System.out.println(RandomUtil.simpleUUID());
    }
}