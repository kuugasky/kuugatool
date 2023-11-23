package io.github.kuugasky.kuugatool.core.number;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.collection.SetUtil;
import io.github.kuugasky.kuugatool.core.numerical.BigDecimalUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.*;

class NumberUtilTest {

    @Test
    void min() {
        List<Integer> integers = ListUtil.newArrayList(1, 2, 3, 5, -11, 0);
        System.out.println(NumberUtil.min(integers));
        List<Float> floats = ListUtil.newArrayList(1F, 2F, 3F, 5F, -11F, 0F);
        System.out.println(NumberUtil.min(floats));
        List<Double> doubles = ListUtil.newArrayList(1D, 2D, 3D, 5D, -11D, 0D);
        System.out.println(NumberUtil.min(doubles));
        List<BigDecimal> bigDecimals = ListUtil.newArrayList(BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.valueOf(-11), BigDecimal.TEN);
        System.out.println(NumberUtil.min(bigDecimals));
    }

    @Test
    void max1() {
        System.out.println(NumberUtil.max(new Integer[]{1, 2, 3, 5, 4}));
        System.out.println(NumberUtil.max(1, 2, 3, 5, 4));
        System.out.println(NumberUtil.max(1L, 2L, 3L, 5L, 4L));
        System.out.println(NumberUtil.max(Short.parseShort("1"), Short.parseShort("2"), Short.parseShort("3"), Short.parseShort("5"), Short.parseShort("4")));
        System.out.println(NumberUtil.max(1D, 2D, 3D, 5D, 4D));
        System.out.println(NumberUtil.max(1F, 2F, 3F, 5F, 4F));
        System.out.println(NumberUtil.max(new BigDecimal("1"), new BigDecimal("2"), new BigDecimal("3"), new BigDecimal("5"), new BigDecimal("4")));

        System.out.println(NumberUtil.max(SetUtil.newHashSet(1, 2, 3, 5, 4)));
        System.out.println(NumberUtil.max(ListUtil.newArrayList(1, 2, 3, 5, 4)));
        System.out.println(NumberUtil.max(ListUtil.newArrayList(1L, 2L, 3L, 5L, 4L)));
        System.out.println(NumberUtil.max(ListUtil.newArrayList(1F, 2F, 3F, 5F, 4F)));
        System.out.println(NumberUtil.max(ListUtil.newArrayList(1D, 2D, 3D, 5D, 4D)));
        System.out.println(NumberUtil.max(ListUtil.newArrayList(Short.parseShort("1"), Short.parseShort("2"), Short.parseShort("3"), Short.parseShort("5"), Short.parseShort("4"))));
        System.out.println(NumberUtil.max(ListUtil.newArrayList(new BigDecimal("1"), new BigDecimal("2"), new BigDecimal("3"), new BigDecimal("5"), new BigDecimal("4"))));
    }

    @Test
    void min1() {
        System.out.println(NumberUtil.min(new Integer[]{9, 1, 2, 3, 5, 4}));
        System.out.println(NumberUtil.min(9, 1, 2, 3, 5, 4));
        System.out.println(NumberUtil.min(9L, 1L, 2L, 3L, 5L, 4L));
        System.out.println(NumberUtil.min(Short.parseShort("9"), Short.parseShort("1"), Short.parseShort("2"), Short.parseShort("3"), Short.parseShort("5"), Short.parseShort("4")));
        System.out.println(NumberUtil.min(9D, 1D, 2D, 3D, 5D, 4D));
        System.out.println(NumberUtil.min(9F, 1F, 2F, 3F, 5F, 4F));
        System.out.println(NumberUtil.min(new BigDecimal("9"), new BigDecimal("1"), new BigDecimal("2"), new BigDecimal("3"), new BigDecimal("5"), new BigDecimal("4")));

        System.out.println(NumberUtil.min(SetUtil.newHashSet(9, 1, 2, 3, 5, 4)));
        System.out.println(NumberUtil.min(ListUtil.newArrayList(9, 1, 2, 3, 5, 4)));
        System.out.println(NumberUtil.min(ListUtil.newArrayList(9L, 1L, 2L, 3L, 5L, 4L)));
        System.out.println(NumberUtil.min(ListUtil.newArrayList(9F, 1F, 2F, 3F, 5F, 4F)));
        System.out.println(NumberUtil.min(ListUtil.newArrayList(9D, 1D, 2D, 3D, 5D, 4D)));
        System.out.println(NumberUtil.min(ListUtil.newArrayList(Short.parseShort("9"), Short.parseShort("1"), Short.parseShort("2"), Short.parseShort("3"), Short.parseShort("5"), Short.parseShort("4"))));
        System.out.println(NumberUtil.min(ListUtil.newArrayList(new BigDecimal("9"), new BigDecimal("1"), new BigDecimal("2"), new BigDecimal("3"), new BigDecimal("5"), new BigDecimal("4"))));
    }

    @Test
    void sum() {
        // System.out.println(NumberUtil.sum(9, 1, 2, 3, 5, 4));
        System.out.println(NumberUtil.sum(9, 1, 2, 3, 5, 4));
        System.out.println(NumberUtil.sum(9L, 1L, 2L, 3L, 5L, 4L));
        System.out.println(NumberUtil.sum(Short.parseShort("9"), Short.parseShort("1"), Short.parseShort("2"), Short.parseShort("3"), Short.parseShort("5"), Short.parseShort("4")));
        System.out.println(NumberUtil.sum(9D, 1D, 2D, 3D, 5D, 4D));
        System.out.println(NumberUtil.sum(9F, 1F, 2F, 3F, 5F, 4F));
        System.out.println(NumberUtil.sum(new BigDecimal("9"), new BigDecimal("1"), new BigDecimal("2"), new BigDecimal("3"), new BigDecimal("5"), new BigDecimal("4")));

        System.out.println(NumberUtil.sum(SetUtil.newHashSet(9, 1, 2, 3, 5, 4)));
        System.out.println(NumberUtil.sum(ListUtil.newArrayList(9, 1, 2, 3, 5, 4)));
        System.out.println(NumberUtil.sum(ListUtil.newArrayList(9L, 1L, 2L, 3L, 5L, 4L)));
        System.out.println(NumberUtil.sum(ListUtil.newArrayList(9F, 1F, 2F, 3F, 5F, 4F)));
        System.out.println(NumberUtil.sum(ListUtil.newArrayList(9D, 1D, 2D, 3D, 5D, 4D)));
        System.out.println(NumberUtil.sum(ListUtil.newArrayList(Short.parseShort("9"), Short.parseShort("1"), Short.parseShort("2"), Short.parseShort("3"), Short.parseShort("5"), Short.parseShort("4"))));
        System.out.println(NumberUtil.sum(ListUtil.newArrayList(new BigDecimal("9"), new BigDecimal("1"), new BigDecimal("2"), new BigDecimal("3"), new BigDecimal("5"), new BigDecimal("4"))));
    }

    @Test
    void max2() {
        List<Integer> ints = ListUtil.newArrayList(44, 55, 1, 4, 100, 2);
        List<Float> floats = ListUtil.newArrayList(44f, 55f, 1f, 4f, 100f, 2f);
        List<Long> longs = ListUtil.newArrayList(44L, 55L, 1L, 4L, 100L, 2L);
        ArrayList<Short> shorts = new ArrayList<>();
        shorts.add(Short.valueOf(44 + ""));
        shorts.add(Short.valueOf(55 + ""));
        shorts.add(Short.valueOf(1 + ""));
        shorts.add(Short.valueOf(100 + ""));
        shorts.add(Short.valueOf(2 + ""));
        List<Double> doubles = ListUtil.newArrayList(44D, 55D, 1D, 4D, 100D, 2D);
        List<BigDecimal> bigDecimals = ListUtil.newArrayList(BigDecimalUtil.of(44), BigDecimalUtil.of(55),
                BigDecimalUtil.of(1), BigDecimalUtil.of(4), BigDecimalUtil.of(100), BigDecimalUtil.of(2));

        System.out.println(NumberUtil.max(ints));
        System.out.println(NumberUtil.max(floats));
        System.out.println(NumberUtil.max(longs));
        System.out.println(NumberUtil.max(shorts));
        System.out.println(NumberUtil.max(doubles));
        System.out.println(NumberUtil.max(bigDecimals));

        System.out.println(StringUtil.repeatNormal());

        System.out.println(NumberUtil.min(ints));
        System.out.println(NumberUtil.min(floats));
        System.out.println(NumberUtil.min(longs));
        System.out.println(NumberUtil.min(shorts));
        System.out.println(NumberUtil.min(doubles));
        System.out.println(NumberUtil.min(bigDecimals));
    }

    @Test
    void maxByReduce() {
        List<Integer> list = ListUtil.newArrayList(1, 2, 3, 4, 5);
        // reduce求最大值
        System.out.println("reduce求最大值:" + list.stream().reduce(Integer.MIN_VALUE, (result, ele) -> result < ele ? ele : result));
        System.out.println(NumberUtil.max(list));
    }

    @Test
    void minByReduce() {
        List<Integer> list = ListUtil.newArrayList(1, 2, 3, 4, 5);
        // reduce求最大值
        System.out.println("reduce求最小值:" + list.stream().reduce(Integer.MIN_VALUE, (result, ele) -> result > ele ? ele : result));
        System.out.println(NumberUtil.min(list));
    }

    @Test
    void getNumber() {
        BigDecimal number = NumberUtil.getNumber("088.00㎡");
        System.out.println(number);
    }

    @Test
    void getNumberStr() {
        String number = NumberUtil.getNumberStr("088.00㎡");
        System.out.println(number);
    }

    @Test
    void getNumberStr1() {
        String number = NumberUtil.getNumberStr("088.00㎡", true);
        System.out.println(number);
    }

    @Test
    void parseIntList() {
        List<Integer> list = NumberUtil.parseIntList("100:200", ":");
        System.out.println(list);
    }

    @Test
    void parseIntList1() {
        List<Integer> list = NumberUtil.parseIntList("100,200");
        System.out.println(list);
    }

    @Test
    void parseIntArray() {
        Integer[] integers = NumberUtil.parseIntArray("100,200");
        System.out.println(Arrays.toString(integers));
    }

    @Test
    void parseIntArray1() {
        Integer[] integers = NumberUtil.parseIntArray("100,200", ",");
        System.out.println(Arrays.toString(integers));
    }

    @Test
    void parseLongList() {
        List<Long> longs = NumberUtil.parseLongList("100,200", ",");
        System.out.println(longs);
    }

    @Test
    void parseLongList1() {
        List<Long> longs = NumberUtil.parseLongList("100,200");
        System.out.println(longs);
    }

    @Test
    void parseInt() {
        Integer i = NumberUtil.parseInt("100");
        System.out.println(i);
        Integer[] integers = NumberUtil.parseIntArray("100,200");
        System.out.println(Arrays.toString(integers));
        Integer[] integers1 = NumberUtil.parseIntArray("100,200", ",");
        System.out.println(Arrays.toString(integers1));
        List<Integer> integerList = NumberUtil.parseIntList("100,200");
        System.out.println(integerList);
        List<Integer> integerList1 = NumberUtil.parseIntList("100,200", ",");
        System.out.println(integerList1);
    }

    @Test
    void sub() {
        System.out.println(NumberUtil.sub(1, 2));
        System.out.println(NumberUtil.sub(1f, 2f));
        System.out.println(NumberUtil.sub(1d, 2d));
        System.out.println(NumberUtil.sub(1f, 2d));
        System.out.println(NumberUtil.sub(1d, 2f));
        System.out.println(NumberUtil.sub(Double.valueOf(1), Double.valueOf(2)));
        System.out.println(NumberUtil.sub("1", "2"));
        System.out.println(NumberUtil.sub(new BigDecimal(1), new BigDecimal(2)));
        System.out.println(NumberUtil.sub(new BigDecimal(1), new BigDecimal(2), new BigDecimal(3)));
    }

    @Test
    void mul() {
        System.out.println(NumberUtil.mul(1, 2));
        System.out.println(NumberUtil.mul(1f, 2f));
        System.out.println(NumberUtil.mul(1d, 2d));
        System.out.println(NumberUtil.mul(1f, 2d));
        System.out.println(NumberUtil.mul(1d, 2f));
        System.out.println(NumberUtil.mul(Double.valueOf(1), Double.valueOf(2)));
        System.out.println(NumberUtil.mul("1", "2"));
        System.out.println(NumberUtil.mul("1", "2", "3"));
        System.out.println(NumberUtil.mul(new BigDecimal(1), new BigDecimal(2)));
        System.out.println(NumberUtil.mul(new BigDecimal(1), new BigDecimal(2), new BigDecimal(3)));
    }

    @Test
    void ceilDiv() {
        // 3.333333333约等于4
        System.out.println(NumberUtil.ceilDiv(10, 3));
        // 6.666666667约等于7
        System.out.println(NumberUtil.ceilDiv(20, 3));
        // 4.5约等于5
        System.out.println(NumberUtil.ceilDiv(9, 2));
    }

    @Test
    void parseLongArray() {
        Long[] longs = NumberUtil.parseLongArray("100,200", ",");
        System.out.println(Arrays.toString(longs));
    }

    @Test
    void parseLongArray1() {
        Long[] longs = NumberUtil.parseLongArray("100,200");
        System.out.println(Arrays.toString(longs));
    }

    @Test
    void formatVal() {
        // 浮点型四舍五入
        Double aDouble = NumberUtil.formatVal(0.004D);
        // 0.0
        System.out.println(aDouble);
        Double aDouble1 = NumberUtil.formatVal(0.005D);
        // 0.01
        System.out.println(aDouble1);
    }

    @Test
    void judgeErrorRange() {
        // 判断两个值相减的差值的绝对值是否大于0.1
        System.out.println(NumberUtil.judgeErrorRange(0.2, 0.1)); // false
        System.out.println(NumberUtil.judgeErrorRange(0.21, 0.1)); // true
    }

    @Test
    void toBigDecimal() {
        BigDecimal bigDecimal = NumberUtil.toBigDecimal("A0.01D");
        System.out.println(bigDecimal);
    }

    @Test
    void toBigDecimal1() {
        BigDecimal bigDecimal = NumberUtil.toBigDecimal(NumberUtil.parseNumber("0.01D"));
        System.out.println(bigDecimal);
    }

    @Test
    void toBigInteger() {
        BigInteger integer = NumberUtil.toBigInteger(NumberUtil.parseNumber("0.01"));
        System.out.println(integer);
    }

    @Test
    void toBigInteger1() {
        BigInteger integer = NumberUtil.toBigInteger("0.01");
        System.out.println(integer);
    }

    @Test
    void count() {
        // 计算等份个数
        int count = NumberUtil.count(10, 5);
        System.out.println(count);
    }

    @Test
    void zeroPaddingOfFront() {
        String s = NumberUtil.zeroPaddingOfFront(10, 10);
        System.out.println(s);
    }

    @Test
    void zeroPaddingOfFront1() {
        String s = NumberUtil.zeroPaddingOfFront(Long.valueOf(10000), 10);
        System.out.println(s);
    }

    @Test
    void testZeroPaddingOfFront() {
    }


    @Test
    void stringToNumber() throws ParseException {
        System.out.println(NumberUtil.moneyStrToNumber("1,234,5678"));
        System.out.println(NumberUtil.moneyStrToNumber("1,234,5678.0"));
        System.out.println(NumberUtil.moneyStrToNumber("1,234,5678.00"));
        System.out.println(NumberUtil.moneyStrToNumber("111,234,5678.00"));
    }

    @Test
    void generateRandomNumber() {
        int[] ints = NumberUtil.generateRandomNumber(1, 4, 2);
        System.out.println(Arrays.toString(ints));
    }

    @Test
    void testGenerateRandomNumber() {
        int[] ints = NumberUtil.generateRandomNumber(new int[]{15, 18}, 1);
        System.out.println(Arrays.toString(ints));
    }

    @Test
    void rangeOpenInterval1() {
        int[] range = NumberUtil.rangeOpenInterval(5);
        System.out.println(Arrays.toString(range));
    }

    @Test
    void rangeOpenInterval2() {
        int[] range = NumberUtil.rangeOpenInterval(5, 10);
        System.out.println(Arrays.toString(range));
    }

    @Test
    void rangeOpenInterval3() {
        int[] range = NumberUtil.rangeOpenInterval(5, 10, 2);
        System.out.println(Arrays.toString(range));
    }

    @Test
    void rangeClosedInterval() {
        int[] range = NumberUtil.rangeClosedInterval(5);
        System.out.println(Arrays.toString(range));
    }

    @Test
    void rangeClosedInterval1() {
        int[] range = NumberUtil.rangeClosedInterval(5, 10);
        System.out.println(Arrays.toString(range));
    }

    @Test
    void rangeClosedInterval2() {
        int[] range = NumberUtil.rangeClosedInterval(5, 10, 2);
        System.out.println(Arrays.toString(range));
    }

    @Test
    void testRange2() {
        int[] range = NumberUtil.range(true, true, 1, 10, 1);
        System.out.println("左开右开区间" + Arrays.toString(range));
        int[] range1 = NumberUtil.range(false, false, 1, 10, 1);
        System.out.println("左开右闭区间" + Arrays.toString(range1));
        int[] range2 = NumberUtil.range(true, false, 1, 10, 1);
        System.out.println("左开右闭区间" + Arrays.toString(range2));
        int[] range3 = NumberUtil.range(false, true, 1, 10, 1);
        System.out.println("左闭右开区间" + Arrays.toString(range3));
    }

    @Test
    void isPositiveInteger() {
        System.out.println(NumberUtil.isPositiveInteger(1));
        System.out.println(NumberUtil.isPositiveInteger("1"));
        System.out.println(NumberUtil.isPositiveInteger("abc"));
        System.out.println(NumberUtil.isPositiveInteger(StringUtil.EMPTY));
        System.out.println(NumberUtil.isPositiveInteger(null));
    }

    @Test
    void null2Zero() {
        System.out.println(NumberUtil.null2Zero(null));
    }

    @Test
    void zero2One() {
        System.out.println(NumberUtil.zero2One(0));
    }

    @Test
    void pow() {
        System.out.println(NumberUtil.pow(2, 2));
    }

    @Test
    void pow1() {
        System.out.println(NumberUtil.pow(new BigDecimal(2), 2));
    }

    @Test
    void isPowerOfTwo() {
        System.out.println(NumberUtil.isPowerOfTwo(32));
    }

    @Test
    void roundStr() {
        System.out.println(NumberUtil.roundStr(32.11, 1));
        System.out.println(NumberUtil.roundStr("100.111", 2));
        System.out.println(NumberUtil.roundStr(100.111D, 2, RoundingMode.HALF_UP));
        System.out.println(NumberUtil.roundStr("100.111", 2, RoundingMode.HALF_UP));
        System.out.println(NumberUtil.roundHalfEven(BigDecimal.valueOf(100.111), 2));

        Number number = new Number() {
            @Override
            public int intValue() {
                return 0;
            }

            @Override
            public long longValue() {
                return 0;
            }

            @Override
            public float floatValue() {
                return 0;
            }

            @Override
            public double doubleValue() {
                return 0;
            }
        };
        System.out.println(NumberUtil.roundHalfEven(number, 2));
    }

    @Test
    void roundDown() {
        System.out.println(NumberUtil.roundDown(32.111, 2));
        System.out.println(NumberUtil.roundDown(32.116, 2));
    }

    @Test
    void decimalFormatMoney() {
        System.out.println(NumberUtil.decimalFormatMoney(123456789));
    }

    @Test
    void decimalFormat() {
        System.out.println(NumberUtil.decimalFormat(",##0.00", 123456789L));
        System.out.println(NumberUtil.decimalFormat(",##0.00", 123456789D));
        System.out.println(NumberUtil.decimalFormat(",##0.00", 123456789));
    }

    @Test
    void formatPercent() {
        System.out.println(NumberUtil.formatPercent(80, 2));
    }

    @Test
    void isInteger() {
        System.out.println(NumberUtil.isInteger("123456789"));
        System.out.println(NumberUtil.isInteger("12345678999999999999"));
    }

    @Test
    void isLong() {
        System.out.println(NumberUtil.isLong("123456789"));
        System.out.println(NumberUtil.isLong("12345678999999999999"));
    }

    @Test
    void generateBySet() {
        System.out.println(Arrays.toString(NumberUtil.generateBySet(1, 10, 5)));
    }

    @Test
    void isGreater() {
        System.out.println(NumberUtil.isGreater(new BigDecimal(2), new BigDecimal(1)));
        System.out.println(NumberUtil.isGreaterOrEqual(new BigDecimal(2), new BigDecimal(2)));
    }

    @Test
    void isLess() {
        System.out.println(NumberUtil.isLess(new BigDecimal(1), new BigDecimal(2)));
        System.out.println(NumberUtil.isLessOrEqual(new BigDecimal(1), new BigDecimal(1)));
    }

    @Test
    void toStr() {
        System.out.println(NumberUtil.toStr(new BigDecimal(1)));
        System.out.println(NumberUtil.toStr(NumberUtil.parseNumber("1"), StringUtil.EMPTY));
    }

    @Test
    void newBigInteger() {
        System.out.println(NumberUtil.newBigInteger("111"));
    }

    @Test
    void factorial() {
        System.out.println(NumberUtil.factorial(3)); // 3*(3-1)*(2-1) = 6
        System.out.println(NumberUtil.factorial(4)); // 4*(4-1)*(3-1)*(2-1) = 24
        System.out.println(NumberUtil.factorial(5)); // 5*(5-1)*(4-1)*(3-1)*(2-1) = 120
        System.out.println(NumberUtil.factorial(5, 1)); // 5*(5-1)*(4-1)*(3-1)*(2-1) = 120
        System.out.println(NumberUtil.factorial(5, 2)); // 5*(5-1)*(4-1) = 60
        System.out.println(NumberUtil.factorial(5, 3)); // 5*(5-1)*(4-1) = 20
        System.out.println(NumberUtil.factorial(5, 4)); // 5*(5-1) = 5
        System.out.println(NumberUtil.factorial(5, 5)); // 1
    }

    @Test
    void toUnsignedByteArray() {
        System.out.println(Arrays.toString(NumberUtil.toUnsignedByteArray(new BigInteger("111"))));
        System.out.println(Arrays.toString(NumberUtil.toUnsignedByteArray(10, new BigInteger("222"))));
        System.out.println(NumberUtil.fromUnsignedByteArray("111".getBytes()));
        System.out.println(NumberUtil.fromUnsignedByteArray("111".getBytes(), 1, 2));
    }

    @Test
    void multiple() {
        // 最小公倍数
        System.out.println(NumberUtil.multiple(120, 60));
    }

    @Test
    void divisor() {
        // 最大公约数 60
        System.out.println(NumberUtil.divisor(120, 60));
    }

    @Test
    void getBinaryStr() {
        System.out.println(NumberUtil.getBinaryStr(100));
    }

    @Test
    void getInt() {
        System.out.println(NumberUtil.getInt(null, 1));
    }

    @Test
    void getFloat() {
        System.out.println(NumberUtil.getFloat(null, 1f));
    }

    @Test
    void getDouble() {
        System.out.println(NumberUtil.getDouble(null, 1D));
    }

    @Test
    void getBigDecimal() {
        System.out.println(NumberUtil.getBigDecimal(null, BigDecimal.ONE));
    }

    @Test
    void toBigDecimal2() {
        BigDecimal bigDecimal = NumberUtil.toBigDecimal("1234567");
        System.out.println(bigDecimal);
    }

    @Test
    void div() {
        System.out.println(NumberUtil.div(9f, 2f));
        System.out.println(NumberUtil.div(9d, 2d));
        System.out.println(NumberUtil.div(Double.valueOf("9"), Double.valueOf("2")));
        System.out.println(NumberUtil.div(9, 2));
        System.out.println(NumberUtil.div("9", "2"));
        System.out.println(NumberUtil.div(9d, 2f));
        System.out.println(NumberUtil.div(9f, 2D));
        Number number = new Number() {
            @Override
            public int intValue() {
                return 0;
            }

            @Override
            public long longValue() {
                return 0;
            }

            @Override
            public float floatValue() {
                return 0;
            }

            @Override
            public double doubleValue() {
                return 0;
            }
        };
        System.out.println(NumberUtil.div(number, number));
    }

    @Test
    void isDouble() {
        System.out.println(NumberUtil.isDouble("1.1"));
    }

    @Test
    void isPrimes() {
        System.out.println(NumberUtil.isPrimes(3));
    }

    @Test
    void appendRange() {
        List<Integer> list1 = ListUtil.newArrayList();
        System.out.println(NumberUtil.appendRange(1, 5, list1));
        List<Integer> list2 = ListUtil.newArrayList();
        System.out.println(NumberUtil.appendRange(1, 5, 2, list2));
    }

    @Test
    void sqrt() {
        System.out.println(NumberUtil.sqrt(9));
        System.out.println(NumberUtil.sqrt(10));
    }

    @Test
    void processMultiple() {
        System.out.println(NumberUtil.processMultiple(7, 5));
    }

    @Test
    void binaryToInt() {
        System.out.println(NumberUtil.binaryToInt("1001"));
    }

    @Test
    void binaryToLong() {
        System.out.println(NumberUtil.binaryToLong("1001"));
    }

    @Test
    void isBeside() {
        System.out.println(NumberUtil.isBeside(1, 2));
        System.out.println(NumberUtil.isBeside(1L, 2L));
        System.out.println(NumberUtil.isBeside(1, 3));
        System.out.println(NumberUtil.isBeside(-1, 0));
        System.out.println(NumberUtil.isBeside(-1, -3));
    }

    @Test
    void partValue() {
        System.out.println(NumberUtil.partValue(10, 3));
    }

    @Test
    void parseLong() {
        System.out.println(NumberUtil.parseLong("10"));
    }

    @Test
    void parseFloat() {
        System.out.println(NumberUtil.parseFloat("10"));
    }

    @Test
    void parseDouble() {
        System.out.println(NumberUtil.parseDouble("10"));
    }

    @Test
    void toBytes() {
        byte[] bytes = NumberUtil.toBytes(10);
        System.out.println(Arrays.toString(bytes));
        System.out.println(NumberUtil.toInt(bytes));
    }

    @Test
    void parseScienceNumber() {
        System.out.println(NumberUtil.parseScienceNumber("1"));
    }

    @Test
    void sum1() {
        System.out.println(NumberUtil.sum(ListUtil.newArrayList(1, 2, 3, 4, 5)));
        System.out.println(NumberUtil.sum(ListUtil.newArrayList(1L, 2L, 3L, 4L, 5L)));
        System.out.println(NumberUtil.sum(ListUtil.newArrayList(1D, 2D, 3D, 4D, 5D)));
    }

    @Test
    void sum2() {
        List<Integer> ints = ListUtil.newArrayList(44, 55, 1, 4, 100, 2);
        List<Float> floats = ListUtil.newArrayList(44f, 55f, 1f, 4f, 100f, 2f);
        List<Long> longs = ListUtil.newArrayList(44L, 55L, 1L, 4L, 100L, 2L);
        ArrayList<Short> shorts = new ArrayList<>();
        shorts.add(Short.valueOf(44 + ""));
        shorts.add(Short.valueOf(55 + ""));
        shorts.add(Short.valueOf(1 + ""));
        shorts.add(Short.valueOf(4 + ""));
        shorts.add(Short.valueOf(100 + ""));
        shorts.add(Short.valueOf(2 + ""));
        List<Double> doubles = ListUtil.newArrayList(44D, 55D, 1D, 4D, 100D, 2D);
        List<BigDecimal> bigDecimals = ListUtil.newArrayList(BigDecimalUtil.of(44), BigDecimalUtil.of(55),
                BigDecimalUtil.of(1), BigDecimalUtil.of(4), BigDecimalUtil.of(100), BigDecimalUtil.of(2));

        System.out.println(NumberUtil.sum(ints));
        System.out.println(NumberUtil.sum(floats));
        System.out.println(NumberUtil.sum(longs));
        System.out.println(NumberUtil.sum(shorts));
        System.out.println(NumberUtil.sum(doubles));
        System.out.println(NumberUtil.sum(bigDecimals));
    }

    @Test
    void averagingInt() {
        System.out.println(NumberUtil.averagingInt(ListUtil.newArrayList(1, 2, 3, 4, 5, 6)));
    }

    @Test
    void averagingLong() {
        System.out.println(NumberUtil.averagingLong(ListUtil.newArrayList(1L, 2L, 3L, 4L, 5L, 6L)));
    }

    @Test
    void averagingDouble() {
        System.out.println(NumberUtil.averagingDouble(ListUtil.newArrayList(1D, 2D, 3D, 4D, 5D, 6D)));
    }

    @Test
    void summarizingInt() {
        IntSummaryStatistics x = NumberUtil.summarizingInt(ListUtil.newArrayList(1, 2, 3, 4, 5, 6));
        System.out.println(StringUtil.formatString(x));
    }

    @Test
    void summarizingLong() {
        LongSummaryStatistics x = NumberUtil.summarizingLong(ListUtil.newArrayList(1L, 2L, 3L, 4L, 5L, 6L));
        System.out.println(StringUtil.formatString(x));
    }

    @Test
    void summarizingDouble() {
        DoubleSummaryStatistics x = NumberUtil.summarizingDouble(ListUtil.newArrayList(1D, 2D, 3D, 4D, 5D, 6D));
        System.out.println(StringUtil.formatString(x));
    }

}