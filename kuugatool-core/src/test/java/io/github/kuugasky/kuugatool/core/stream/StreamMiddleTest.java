package io.github.kuugasky.kuugatool.core.stream;

import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author kuuga
 * @since 2022-04-09 13:44:14
 */
public class StreamMiddleTest {

    /**
     * 2.1 筛选与切片
     * filter：过滤流中的某些元素
     * limit(n)：获取n个元素
     * skip(n)：跳过n元素，配合limit(n)可实现分页
     * distinct：通过流中元素的 hashCode() 和 equals() 去除重复元素
     */
    @Test
    public void demo1() {
        Stream<Integer> stream = Stream.of(6, 4, 6, 7, 3, 9, 8, 10, 12, 14, 14);

        Stream<Integer> newStream = stream.filter(s -> s > 5) // 6 6 7 9 8 10 12 14 14
                .distinct() // 6 7 9 8 10 12 14
                .skip(2) // 9 8 10 12 14
                .limit(2); // 9 8
        newStream.forEach(System.out::println);
    }

    /**
     * 2.2 映射
     * map： 接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。
     * flatMap： 接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流。
     */
    @Test
    public void demo2() {
        List<String> list = Arrays.asList("a,b,c", "1,2,3");
        // 将每个元素转成一个新的且不带逗号的元素
        Stream<String> s1 = list.stream().map(s -> s.replaceAll(",", StringUtil.EMPTY));
        s1.forEach(System.out::println); // abc  123

        Stream<String> s3 = list.stream().flatMap(s -> {
            // 将每个元素转换成一个stream
            String[] split = s.split(",");
            return Arrays.stream(split);
        });
        s3.forEach(System.out::println); // a b c 1 2 3
    }

    /**
     * 2.3 排序
     * sorted()：自然排序，流中元素需实现Comparable接口
     * sorted(Comparator com)：定制排序，自定义Comparator排序器
     */
    @Test
    public void demo3() {
        List<String> list = Arrays.asList("aa", "ff", "dd");
        // String 类自身已实现Comparator接口
        list.stream().sorted().forEach(System.out::println);// aa dd ff

        Student s1 = new Student("aa", 10);
        Student s2 = new Student("bb", 20);
        Student s3 = new Student("aa", 30);
        Student s4 = new Student("dd", 40);
        List<Student> studentList = Arrays.asList(s1, s2, s3, s4);

        // 自定义排序：先按姓名升序，姓名相同则按年龄升序
        studentList.stream().sorted(
                (o1, o2) -> {
                    if (o1.getName().equals(o2.getName())) {
                        return o1.getAge() - o2.getAge();
                    } else {
                        return o1.getName().compareTo(o2.getName());
                    }
                }
        ).forEach(System.out::println);
    }

    /**
     * 2.4消费
     * peek：如同于map，能得到流中的每一个元素。但map接收的是一个Function表达式，有返回值；而peek接收的是Consumer表达式，没有返回值。
     */
    @Test
    public void demo4() {
        Student s1 = new Student("aa", 10);
        Student s2 = new Student("bb", 20);
        List<Student> studentList = Arrays.asList(s1, s2);
        studentList.stream()
                .peek(o -> o.setAge(100))
                .forEach(System.out::println);
        // 结果：
        // Student {
        //     name = 'aa', age = 100
        // }
        //
        // Student {
        //     name = 'bb', age = 100
        // }
    }

}