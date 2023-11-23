package io.github.kuugasky.kuugatool.core.stream;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * @author kuuga
 * @since 2022-04-09 12:53:53
 */
public class StreamTest {

    /**
     * 1.1 使用Collection下的 stream() 和 parallelStream() 方法
     */
    @Test
    public void demo1() {
        List<String> list = new ArrayList<>();
        Stream<String> stream = list.stream(); // 获取一个顺序流
        Stream<String> parallelStream = list.parallelStream(); // 获取一个并行流
        System.out.println(stream);
        System.out.println(parallelStream);
    }

    /**
     * 1.2 使用Arrays 中的stream()方法，将数组转成流
     */
    @Test
    public void demo2() {
        Integer[] nums = new Integer[10];
        Stream<Integer> stream = Arrays.stream(nums);
        System.out.println(stream);
    }

    /**
     * 1.3 使用Stream中的静态方法：of()、iterate()、generate()
     */
    @Test
    public void demo3() {
        Stream<Integer> stream = Stream.of(1, 2, 3, 4, 5, 6);

        stream.forEach(System.out::println);

        Stream<Integer> stream2 = Stream.iterate(0, (x) -> x + 2).limit(6);
        stream2.forEach(System.out::println); // 0 2 4 6 8 10

        Stream<Double> stream3 = Stream.generate(Math::random).limit(2);
        stream3.forEach(System.out::println);
    }

    /**
     * 1.4 使用 BufferedReader.lines() 方法，将每行内容转成流
     */
    @Test
    public void demo4() throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader("/Users/kuuga/IdeaProjects/kuugatool/kuugatool-core/src/test/java/cn/kuugatool/core/stream/test_stream.txt"));
        Stream<String> lineStream = reader.lines();
        lineStream.forEach(System.out::println);
    }

    /**
     * 1.5 使用 Pattern.splitAsStream() 方法，将字符串分隔成流
     */
    @Test
    public void demo5() {
        java.util.regex.Pattern pattern = Pattern.compile(",");
        Stream<String> stringStream = pattern.splitAsStream("a,b,c,d");
        stringStream.forEach(System.out::println);
    }

}
