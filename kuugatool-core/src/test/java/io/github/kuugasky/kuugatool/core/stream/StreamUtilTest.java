package io.github.kuugasky.kuugatool.core.stream;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.entity.KuugaDTO;
import io.github.kuugasky.kuugatool.core.entity.KuugaModel;
import io.github.kuugasky.kuugatool.core.number.NumberUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * StreamUtil
 *
 * @author kuuga
 * @since 2021/5/10
 */
public class StreamUtilTest {

    @Test
    public void reduce() {
        /*
            Stream<Integer> stream = ...
            int sum = 0;
            for (n : stream) {
                sum = (sum, n) -> sum + n;
            }
         */
        int sum = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).reduce(0, (acc, n) -> acc + n);
        // 等价于
        // int sum = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).reduce(0, Integer::sum);
        // 所有数值相加
        System.out.println(sum); // 45

        int s = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9).reduce(1, (acc, n) -> acc * n);
        System.out.println(s); // 362880

        // 按行读取配置文件:
        List<String> props = List.of("profile=native", "debug=true", "logging=warn", "interval=500");
        Map<String, String> map = props.stream()
                // 把k=v转换为Map[k]=v:
                .map(kv -> {
                    String[] ss = kv.split("\\=", 2);
                    return Map.of(ss[0], ss[1]);
                })
                // 把所有Map聚合到一个Map:
                .reduce(new HashMap<>(), (m, kv) -> {
                    m.putAll(kv);
                    return m;
                });
        // 打印结果:
        map.forEach((k, v) -> System.out.println(k + " = " + v));
    }

    @Test
    public void min() {
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
    public void max() {
        List<Integer> integersOfMax = ListUtil.newArrayList(1, 2, 3, 5, -11, 0);
        System.out.println(NumberUtil.max(integersOfMax));
        List<Float> floatsOfMax = ListUtil.newArrayList(1F, 2F, 3F, 5F, -11F, 0F);
        System.out.println(NumberUtil.max(floatsOfMax));
        List<Double> doublesOfMax = ListUtil.newArrayList(1D, 2D, 3D, 5D, -11D, 0D);
        System.out.println(NumberUtil.max(doublesOfMax));
        List<BigDecimal> bigDecimalsOfMax = ListUtil.newArrayList(BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.valueOf(-11), BigDecimal.TEN);
        System.out.println(NumberUtil.max(bigDecimalsOfMax));
    }

    @Test
    public void dropWhile() {
        // 有序
        // create a stream of numbers from 1 to 10
        Stream<Integer> stream = Stream.of(4, 4, 4, 8, 5, 6, 7, 8, 9, 10);
        // apply dropWhile to drop all the numbers
        // matches passed predicate
        List<Integer> list = stream.dropWhile(number -> (number / 4 == 1)).collect(Collectors.toList());
        // print list
        System.out.println(list);

        // 无序
        Stream<Integer> integerStream = ListUtil.newArrayList(4, 4, 4, 8, 5, 6, 7, 8, 9, 10).parallelStream();
        List<Integer> list2 = integerStream.dropWhile(number -> (number / 4 == 1)).collect(Collectors.toList());
        System.out.println(list2);
    }

    @Test
    public void skip() {
        // 指定下标跳过，第一位元素下标为1
        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5);
        System.out.println(Arrays.toString(stream.skip(1).toArray()));
    }

    @Test
    public void forEachOrdered() {
        // 如果流具有已定义的遇到顺序，则流forEachOrdered(Consumer action)将对该流的每个元素执行操作，并按流的遇到顺序进行。 Stream forEachOrdered(Consumer action)是一项终端操作，即，它可以遍历该流以产生结果或副作用。
        // 注意：此操作一次处理一个元素，如果存在则按遭遇顺序。对一个元素执行操作happens-before对后续元素执行操作。
        // Creating a list of Integers
        List<Integer> list = Arrays.asList(10, 19, 20, 1, 2);

        // Using forEachOrdered(Consumer action) to
        // print the elements of stream in encounter order
        list.stream().forEachOrdered(System.out::println);

        // Creating a Stream of Strings
        Stream<String> stream = Stream.of("GFG", "Geeks", "for", "GeeksforGeeks");

        // Using forEachOrdered(Consumer action)
        stream.flatMap(str -> Stream.of(str.charAt(2))).forEachOrdered(System.out::println);
    }

    @Test
    public void testAnyMatch2() {
        List<Integer> list = Arrays.asList(10, 19, 20, 1, 2);
        boolean b = list.stream().anyMatch(e -> e == 1);
        System.out.println(b);
    }

    /**
     * Optional<T> findAny()
     * <p>
     * 可以看到findAny()操作，返回的元素是不确定的，对于同一个列表多次调用findAny()有可能会返回不同的值。使用findAny()是为了更高效的性能。如果是数据较少，串行地情况下，一般会返回第一个结果，如果是并行的情况，那就不能确保是第一个。比如下面的例子会随机地返回OptionalInt[50]。
     * <p>
     * System.out.println(IntStream.range(0, 100).parallel().findAny());
     */
    @Test
    public void findAny() {
        List<Integer> list = Arrays.asList(10, 19, 20, 1, 2);
        for (int i = 0; i < 10; i++) {
            System.out.println(list.stream().findAny().orElse(null));
        }

        IntStream range = IntStream.range(0, 100);
        System.out.println(range.parallel().findAny());
    }

    /**
     * Optional<T> findFirst()
     * <p>
     * 返回列表中的第一个元素。
     * <p>
     * 这里的short-circuiting是指：有时候需要在遍历中途停止操作，比如查找第一个满足条件的元素或者limit操作。在Stream中short-circuiting操作有：anyMatch、allMatch、noneMatch、findFirst、findAny、limit，这些操作在Sink中都有一个变量来判断是否短路，比如limit用的是m，match用的是stop，find用的是hasValue。
     * <p>
     * 这里的terminal operation是指：一个终结操作，比如foreach，IntStream.sum
     */
    @Test
    public void findFirst() {
        List<Integer> list = Arrays.asList();
        Integer any = list.stream().findFirst().orElse(99);
        System.out.println(any);
    }

    @Test
    public void generate() {
        // using Stream.generate() method
        // to generate 5 random Integer values
        // 生成随机整数流
        Stream.generate(new Random()::nextInt).limit(5).forEach(System.out::println);
        // 生成随机Double的流
        Stream.generate(new Random()::nextDouble).limit(8).forEach(System.out::println);
        // 生成随机Boolean的流
        Stream.generate(new Random()::nextBoolean).limit(8).forEach(System.out::println);
    }

    /**
     * jdk7和jdk8对比
     *
     * @param args args
     */
    public static void main(String[] args) {
        // ====================================================================================
        System.out.println("使用 Java 7: ");

        // 计算空字符串
        List<String> strings = Arrays.asList("abc", StringUtil.EMPTY, "bc", "efg", "abcd", StringUtil.EMPTY, "jkl");
        System.out.println("列表: " + strings);
        long count = getCountEmptyStringUsingJava7(strings);

        System.out.println("空字符数量为: " + count);
        count = getCountLength3UsingJava7(strings);

        System.out.println("字符串长度为 3 的数量为: " + count);

        // 删除空字符串
        List<String> filtered = deleteEmptyStringsUsingJava7(strings);
        System.out.println("筛选后的列表: " + filtered);

        // 删除空字符串，并使用逗号把它们合并起来
        String mergedString = getMergedStringUsingJava7(strings);
        System.out.println("合并字符串: " + mergedString);
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);

        // 获取列表元素平方数
        List<Integer> squaresList = getSquares(numbers);
        System.out.println("平方数列表: " + squaresList);
        List<Integer> integers = Arrays.asList(1, 2, 13, 4, 15, 6, 17, 8, 19);

        System.out.println("列表: " + integers);
        System.out.println("列表中最大的数 : " + getMax(integers));
        System.out.println("列表中最小的数 : " + getMin(integers));
        System.out.println("所有数之和 : " + getSum(integers));
        System.out.println("平均数 : " + getAverage(integers));
        System.out.println("随机数: ");

        // 输出10个随机数
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            System.out.println(random.nextInt());
        }

        // ====================================================================================

        System.out.println("使用 Java 8: ");
        System.out.println("列表: " + strings);

        count = strings.stream().filter(String::isEmpty).count();
        System.out.println("空字符串数量为: " + count);

        count = strings.stream().filter(string -> string.length() == 3).count();
        System.out.println("字符串长度为 3 的数量为: " + count);

        filtered = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
        System.out.println("筛选后的列表: " + filtered);

        mergedString = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.joining(", "));
        System.out.println("合并字符串: " + mergedString);

        squaresList = numbers.stream().map(i -> i * i).distinct().collect(Collectors.toList());
        System.out.println("Squares List: " + squaresList);
        System.out.println("列表: " + integers);

        // 统计
        IntSummaryStatistics stats = integers.stream().mapToInt((x) -> x).summaryStatistics();

        System.out.println("列表中最大的数 : " + stats.getMax());
        System.out.println("列表中最小的数 : " + stats.getMin());
        System.out.println("所有数之和 : " + stats.getSum());
        System.out.println("平均数 : " + stats.getAverage());
        System.out.println("随机数: ");

        random.ints().limit(10).sorted().forEach(System.out::println);

        // 并行处理
        count = strings.parallelStream().filter(String::isEmpty).count();
        System.out.println("空字符串的数量为: " + count);
    }

    private static int getCountEmptyStringUsingJava7(List<String> strings) {
        int count = 0;

        for (String string : strings) {

            if (string.isEmpty()) {
                count++;
            }
        }
        return count;
    }

    private static int getCountLength3UsingJava7(List<String> strings) {
        int count = 0;

        for (String string : strings) {

            if (string.length() == 3) {
                count++;
            }
        }
        return count;
    }

    private static List<String> deleteEmptyStringsUsingJava7(List<String> strings) {
        List<String> filteredList = new ArrayList<>();

        for (String string : strings) {

            if (!string.isEmpty()) {
                filteredList.add(string);
            }
        }
        return filteredList;
    }

    private static String getMergedStringUsingJava7(List<String> strings) {
        StringBuilder stringBuilder = new StringBuilder();

        for (String string : strings) {

            if (!string.isEmpty()) {
                stringBuilder.append(string);
                stringBuilder.append(", ");
            }
        }
        String mergedString = stringBuilder.toString();
        return mergedString.substring(0, mergedString.length() - 2);
    }

    private static List<Integer> getSquares(List<Integer> numbers) {
        List<Integer> squaresList = new ArrayList<>();

        for (Integer number : numbers) {
            Integer square = number * number;

            if (!squaresList.contains(square)) {
                squaresList.add(square);
            }
        }
        return squaresList;
    }

    private static int getMax(List<Integer> numbers) {
        int max = numbers.get(0);

        for (int i = 1; i < numbers.size(); i++) {

            Integer number = numbers.get(i);

            if (number > max) {
                max = number;
            }
        }
        return max;
    }

    private static int getMin(List<Integer> numbers) {
        int min = numbers.get(0);

        for (int i = 1; i < numbers.size(); i++) {
            Integer number = numbers.get(i);

            if (number < min) {
                min = number;
            }
        }
        return min;
    }

    private static int getSum(List<Integer> numbers) {
        int sum = numbers.get(0);

        for (int i = 1; i < numbers.size(); i++) {
            sum += numbers.get(i);
        }
        return sum;
    }

    private static int getAverage(List<Integer> numbers) {
        return getSum(numbers) / numbers.size();
    }

    @Test
    void allMatch() {
        KuugaModel kuuga1 = KuugaModel.builder().name("kuuga1").sex(1).build();
        KuugaModel kuuga2 = KuugaModel.builder().name("kuuga2").sex(2).build();
        List<KuugaModel> objects = ListUtil.newArrayList();
        // objects.add(kuuga1);
        objects.add(kuuga2);
        boolean allMatch = StreamUtil.allMatch(objects, item -> item.getName().equals("kuuga2"));
        System.out.println("是否全匹配kuuga2:" + allMatch);
    }

    @Test
    void testAllMatch() {
        KuugaModel kuuga1 = KuugaModel.builder().name("kuuga1").sex(1).build();
        KuugaModel kuuga2 = KuugaModel.builder().name("kuuga2").sex(2).build();
        List<KuugaModel> objects = ListUtil.newArrayList();
        // objects.add(kuuga1);
        objects.add(kuuga2);
        boolean allMatch = StreamUtil.allMatch(objects, KuugaModel::getName, str -> str.equals("kuuga2"));
        System.out.println("是否全匹配kuuga2:" + allMatch);
    }

    @Test
    void testAnyMatch() {
        KuugaModel kuuga1 = KuugaModel.builder().name("kuuga1").sex(1).build();
        KuugaModel kuuga2 = KuugaModel.builder().name("kuuga2").sex(2).build();
        List<KuugaModel> objects = ListUtil.newArrayList();
        // objects.add(kuuga1);
        // objects.add(kuuga2);
        boolean allMatch = StreamUtil.anyMatch(objects, item -> item.getName().equals("kuuga2"));
        System.out.println("是否任一匹配kuuga2:" + allMatch);
    }

    @Test
    void anyMatch() {
        KuugaDTO kuuga1 = KuugaDTO.builder().name("kuuga1").sex(1).build();
        KuugaDTO kuuga2 = KuugaDTO.builder().name("kuuga2").sex(2).build();
        List<KuugaDTO> integers = ListUtil.newArrayList(kuuga1, kuuga2);
        boolean kuuga11 = StreamUtil.anyMatch(integers, kuugaDTO -> kuugaDTO.getName().equals("kuuga"));
        System.out.println(kuuga11);
        boolean kuuga12 = StreamUtil.anyMatch(integers, KuugaDTO::getName, name -> name.contains("kuuga"));
        System.out.println(kuuga12);
    }

    @Test
    void noneMatch() {
        KuugaDTO kuuga1 = KuugaDTO.builder().name("kuuga1").sex(1).build();
        KuugaDTO kuuga2 = KuugaDTO.builder().name("kuuga2").sex(2).build();
        List<KuugaDTO> integers = ListUtil.newArrayList(kuuga1, kuuga2);
        boolean kuuga11 = StreamUtil.noneMatch(integers, kuugaDTO -> kuugaDTO.getName().equals("kuuga"));
        System.out.println(kuuga11);
        boolean kuuga12 = StreamUtil.noneMatch(integers, KuugaDTO::getName, name -> name.contains("kuuga"));
        System.out.println(kuuga12);
    }

    @Test
    void test() {
        List<Integer> integers = ListUtil.newArrayList(1, 2, 3);
        // 迭代最大值没意义，还是3
        System.out.println(StreamUtil.reduce(integers, Integer::max));
        System.out.println(NumberUtil.max(integers));
        System.out.println(StreamUtil.reduce(integers, Integer::min));
        System.out.println(NumberUtil.min(integers));
        // 有意义：6
        System.out.println(StreamUtil.reduce(integers, Integer::sum));
        System.out.println(StreamUtil.reduce(integers, 10, Integer::sum));
    }

}
