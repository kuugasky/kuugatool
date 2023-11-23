package io.github.kuugasky.kuugatool.core.optional;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.entity.KuugaDTO;
import io.github.kuugasky.kuugatool.core.entity.KuugaModel;
import io.github.kuugasky.kuugatool.core.entity.User;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class KuugaOptionalTest {

    @Test
    void test1() {
        User user = new User();
        User.School school1 = new User.School();
        user.setName("hello");
        school1.setAddress("Kuuga's address");
        user.setSchool(school1);
        KuugaOptional.ofNullable(user)
                .getBean(User::getSchool)
                .getBean(User.School::getAddress)
                .ifPresent(address -> System.out.printf("地址存在:%s%n", address));

        List<String> collect = Stream.of(new String[]{"Hello", "World"}).flatMap(str -> Arrays.stream(str.split(StringUtil.EMPTY)))
                .distinct()
                .collect(Collectors.toList());
        System.out.println(collect);

        List<String> collect1 = Stream.of(new String[]{"Hello", "World"}).filter(str -> str.equals("Hello"))
                // .distinct()
                .collect(Collectors.toList());
        System.out.println(collect1);

        List<String> collect2 = Stream.of(new String[]{"Hello", "World"}).map(str -> str + " Kuuga")
                // .distinct()
                .collect(Collectors.toList());
        System.out.println(collect2);

        User user1 = new User();
        User.School school = new User.School();
        user1.setName("hello");
        school.setAddress("Kuuga's address");
        user1.setSchool(school);

        // KuugaOptional.empty
        System.out.println(KuugaOptional.empty());
        // 不允许为空
        // System.out.println(KuugaOptional.of(null));
        // 允许为空
        System.out.println(KuugaOptional.ofNullable(null));
        // 获取user对象
        System.out.println(KuugaOptional.ofNullable(user1).get());
        // 获取user对象，获取不到报错
        // user1 = null;
        System.out.println(KuugaOptional.ofNullable(user1).getOrThrow());
        // 获取user对象，否则返回默认值
        System.out.println(KuugaOptional.ofNullable(user1).orElse(new User()));
        // 判断user是否有值
        System.out.println(KuugaOptional.ofNullable(user1).isPresent());
        // 如果user存在，获取userName
        KuugaOptional.ofNullable(user1).ifPresent(u -> System.out.println(u.getName()));
        // 如果user存在，则获取userName，否则执行后面的逻辑
        // user1 = null;
        KuugaOptional.ofNullable(user1).ifPresentOrElse(u -> System.out.println(u.getName()), () -> System.out.println("kuuga"));
        // 判断user是否为空
        System.out.println(KuugaOptional.ofNullable(user1).isEmpty());
        // 获取user.school.address
        System.out.println(KuugaOptional.ofNullable(user1).getBean(User::getSchool).getBean(User.School::getAddress).get());

        List<User> users = ListUtil.newArrayList();
        users.add(new User("a", "1", null));
        users.add(new User("b", "1", null));
        users.add(new User("c", "1", null));

        User a1 = ListUtil.optimize(users).stream().filter(u -> u.getName().equals("a")).findFirst().orElse(null);
        System.out.println(a1);

        String s = KuugaOptional.ofNullable(user1).getBean(User::getName).orElse(null);
        System.out.println(s);

        // KuugaOptional<String> map = KuugaOptional.ofNullable(user1).flatMap(u-> u.getName().equals("x"));
        // System.out.println(map.get());or

        // 如果不存在就返回null
        System.out.println(KuugaOptional.ofNullable(user1).stream().findFirst().orElse(null));
        // 如果不存在则重新创建一个对象
        System.out.println(KuugaOptional.ofNullable(user1).orElseGet(User::new));
        // 如果对象不存在则报错
        System.out.println(KuugaOptional.ofNullable(user1).orElseThrow());
        // 如果对象为空，返回自定义异常
        System.out.println(KuugaOptional.ofNullable(user1).orElseThrow(RuntimeException::new));
        // System.out.println(KuugaOptional.ofNullable(user1).orElseThrow(() -> new RuntimeException("空指针了")));

        // normalMethod();
        // optionalMethod();
        // optionalMethod2();
    }

    @Test
    void test2() {
        User user = new User();
        User.School school = new User.School();
        user.setName("hello");
        school.setAddress("Kuuga's address");
        user.setSchool(school);

        // System.out.println(OptionalBean.of(user1.getSchool()));
        User user1 = KuugaOptional.ofNullable(user).orElseGet(User::new);

        // 1. 基本调用
        String value1 = KuugaOptional.ofNullable(user1)
                .getBean(User::getSchool)
                .getBean(User.School::getAddress).get();

        System.out.println(value1);

        // 2. 扩展的 isPresent方法 用法与 Optional 一样
        boolean present = KuugaOptional.ofNullable(user1)
                .getBean(User::getSchool)
                .getBean(User.School::getAddress).isPresent();

        System.out.println(present);

        // 3. 扩展的 ifPresent 方法
        KuugaOptional.ofNullable(user1)
                .getBean(User::getSchool)
                .getBean(User.School::getAddress)
                .ifPresent(address -> System.out.printf("地址存在:%s%n", address));

        // 4. 扩展的 orElse
        String value2 = KuugaOptional.ofNullable(user1)
                .getBean(User::getSchool)
                .getBean(User.School::getAddress).orElse("家里蹲");

        System.out.println(value2);

        // 5. 扩展的 orElseThrow
        try {
            String value3 = KuugaOptional.ofNullable(user1)
                    .getBean(User::getSchool)
                    .getBean(User.School::getAddress).orElseThrow(() -> new RuntimeException("空指针了"));
            System.out.println(value3);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void test3() {
        User user = new User();
        user.setName("hello");
        // 1. 基本调用
        String value1 = KuugaOptional.ofNullable(user)
                .getBean(User::getSchool)
                .getBean(User.School::getAddress).orElse("test");
        System.out.println(value1);
    }

    @Test
    void of() {
        KuugaOptional<String> optional = KuugaOptional.of("kuuga");
        // 传入null，抛出NullPointerException
        KuugaOptional<Object> o = KuugaOptional.of(null);
        System.out.println(optional);
        System.out.println(o);
    }

    @Test
    void ofNullable() {
        // 传入null，不抛出NullPointerException
        KuugaOptional<Object> o = KuugaOptional.ofNullable(null);
        System.out.println(o);
    }

    @Test
    void isPresent() {
        KuugaDTO Kuuga = KuugaDTO.builder().name("kuuga").sex(1).build();
        // 对象是否存在
        System.out.println(KuugaOptional.of(Kuuga).isPresent());
    }

    @Test
    void ifPresent() {
        KuugaDTO Kuuga = KuugaDTO.builder().name("kuuga").sex(1).build();
        // 对象是否存在
        KuugaOptional.of(Kuuga).ifPresent(System.out::println);
        KuugaOptional.of(Kuuga).ifPresent(o -> System.out.println(o.getName()));
        KuugaOptional.of(Kuuga).getBean(KuugaDTO::getSex).ifPresent(System.out::println);
    }

    @Test
    void ifPresentOrElse() {
        KuugaDTO Kuuga = KuugaDTO.builder().name(null).sex(1).build();
        // 对象是否存在
        KuugaOptional.of(Kuuga).ifPresentOrElse(o -> System.out.println(o.getName()), () -> System.out.println("对象不存在"));
        KuugaOptional.of(Kuuga).getBean(KuugaDTO::getName).ifPresentOrElse(System.out::println, () -> System.out.println("对象不存在"));
    }

    @Test
    void get() {
        KuugaDTO Kuuga = KuugaDTO.builder().name("kuuga").sex(1).build();
        System.out.println(KuugaOptional.of(Kuuga).get());
    }

    @Test
    void getOrThrow() {
        KuugaDTO Kuuga = KuugaDTO.builder().name(null).sex(1).build();
        // No value present
        System.out.println(KuugaOptional.of(Kuuga).getBean(KuugaDTO::getName).getOrThrow());
    }

    @Test
    void getBean() {
        KuugaDTO Kuuga = KuugaDTO.builder().name("kuuga").sex(1).build();
        // No value present
        System.out.println(KuugaOptional.of(Kuuga).getBean(KuugaDTO::getName));
    }

    @Test
    void filter() {
        List<String> strings = Arrays.asList("rmb", "doller", "ou");
        for (String s : strings) {
            KuugaOptional<String> o = KuugaOptional.of(s).filter(s1 -> !s1.contains("o"));
            System.out.println(o.orElse("没有不包含o的"));
        }
        System.out.println(StringUtil.repeatNormal());
        // 注意：KuugaOptional.filter只能包装单个对象过滤，不能包装数组，下面两行代码的结果完全不同
        KuugaOptional.of(strings).stream().filter(s -> !s.contains("o")).forEach(System.out::println);
        ListUtil.optimize(strings).stream().filter(s -> !s.contains("o")).forEach(System.out::println);

        System.out.println(StringUtil.repeatNormal());

        System.out.println(KuugaOptional.of(strings).stream().filter(v -> v.size() == 3).collect(Collectors.toList()));
        System.out.println(KuugaOptional.of(strings).stream().filter(v -> v.size() == 2).collect(Collectors.toList()));

        KuugaModel kuuga1 = KuugaModel.builder().name("kuuga1").sex(1).build();
        KuugaModel kuuga2 = KuugaModel.builder().name("kuuga2").sex(2).build();
        KuugaOptional.of(kuuga1).filter(v -> v.getName().equals("kuuga1")).ifPresentOrElse(v -> System.out.println(v.getName()), () -> System.out.println("name不等于kuuga1"));
        KuugaOptional.of(kuuga2).filter(v -> v.getName().equals("kuuga1")).ifPresentOrElse(v -> System.out.println(v.getName()), () -> System.out.println("name不等于kuuga1"));
    }

    @Test
    void xx() {
        Stream<Integer> integerStream = Stream.of(1, 2, 3);
        System.out.println(integerStream.filter(x -> x == 1).collect(Collectors.toList()));

        KuugaOptional<List<Integer>> of = KuugaOptional.of(ListUtil.newArrayList(1, 2, 3));
        List<Integer> integers = of.get();
        assert integers != null;
        Stream<Integer> stream1 = integers.stream();
        System.out.println(stream1.filter(x -> x == 1).collect(Collectors.toList()));

        Stream<List<Integer>> stream = of.stream();

    }

    @Test
    @SuppressWarnings("all")
    void map() {
        KuugaOptional<String> optional = KuugaOptional.of("kuuga");
        String s = optional.map(e -> e.toUpperCase()).orElse("kuugasky");
        String s1 = optional.map(String::toLowerCase).orElse("kuugasky");
        System.out.println(s);
        System.out.println(s1);

        KuugaModel Kuuga = KuugaModel.builder().name("kuuga").sex(1).build();
        // 错误用法，如果name为null则报错
        System.out.println(KuugaOptional.of(Kuuga).map(x -> x.getName().toUpperCase()).get());
        // 正确用法，getBean到具体操作的属性再做map处理
        System.out.println(KuugaOptional.of(Kuuga).getBean(KuugaModel::getName).map(String::toUpperCase).get());
    }

    @Test
    void flatMap() {
        KuugaOptional<String> optional = KuugaOptional.of("kuuga");
        KuugaOptional<String> s = optional.flatMap(e -> KuugaOptional.of(e.toUpperCase()));
        System.out.println(s.get());

        KuugaDTO Kuuga = KuugaDTO.builder().name(null).sex(1).build();
        KuugaOptional<String> stringKuugaOptional = KuugaOptional.of(Kuuga).flatMap(o -> KuugaOptional.ofNullable(o.getName()));
        System.out.println(stringKuugaOptional.get());
    }

    @Test
    void or() {
        KuugaDTO Kuuga = KuugaDTO.builder().name("kuuga").sex(1).build();
        KuugaOptional<KuugaDTO> kuuga0410 = KuugaOptional.of(Kuuga).or(() -> KuugaOptional.of(new KuugaDTO("kuugasky", 2)));
        System.out.println(kuuga0410);
    }

    @Test
    void stream() {
        KuugaDTO Kuuga = KuugaDTO.builder().name("kuuga").sex(1).build();
        KuugaOptional.ofNullable(Kuuga).stream().forEach(System.out::println);

        System.out.println(StringUtil.repeatNormal());

        KuugaOptional.of(ListUtil.newArrayList("1", "2", "3"))
                .stream().forEach(System.out::println);

        System.out.println(StringUtil.repeatNormal());

        KuugaOptional<List<String>> kuugaOptional = KuugaOptional.of(ListUtil.newArrayList("1", "2", "3"));
        System.out.println(kuugaOptional.filter(o -> o.size() == 3).get());
    }

    @Test
    void orElse() {
        KuugaDTO Kuuga = null;
        KuugaDTO kuuga0410 = KuugaOptional.ofNullable(Kuuga).orElse(new KuugaDTO("kuugasky", 2));
        System.out.println(kuuga0410);
    }

    @Test
    void orElseGet() {
        KuugaDTO Kuuga = KuugaDTO.builder().name("kuuga").sex(1).build();
        // kuuga存在，一样会执行initOrElse
        KuugaDTO kuuga0410 = KuugaOptional.of(Kuuga).orElse(initOrElse());
        KuugaDTO kuuga1 = KuugaDTO.builder().name("kuuga").sex(1).build();
        // kuuga存在，不会执行initOrElseGet
        KuugaDTO kuuga0513 = KuugaOptional.of(kuuga1).orElseGet(this::initOrElseGet);
        System.out.println(kuuga0410);
        System.out.println(kuuga0513);
    }

    private KuugaDTO initOrElseGet() {
        System.out.println("orElseGet");
        return new KuugaDTO("kuugasky", 2);
    }

    private KuugaDTO initOrElse() {
        System.out.println("orElse");
        return new KuugaDTO("kuugasky", 2);
    }

    @Test
    void orElseThrow() {
        KuugaDTO Kuuga = null;
        KuugaOptional.ofNullable(Kuuga).orElseThrow(() -> new RuntimeException("异常了"));
        KuugaOptional.ofNullable(Kuuga).orElseThrow(RuntimeException::new);
    }

    @Test
    void testOrElseThrow() {
        KuugaDTO Kuuga = null;
        System.out.println(KuugaOptional.ofNullable(Kuuga).orElseThrow());
    }

    @Test
    void testHashCode() {
        KuugaDTO Kuuga = KuugaDTO.builder().name("kuuga").sex(1).build();
        System.out.println(KuugaOptional.ofNullable(Kuuga).hashCode());
    }

    @Test
    void testEquals() {
        KuugaDTO Kuuga = KuugaDTO.builder().name("kuuga").sex(1).build();
        System.out.println(KuugaOptional.ofNullable(Kuuga).equals(KuugaOptional.of(Kuuga)));
    }

    @Test
    void isEmpty() {
        KuugaDTO Kuuga = KuugaDTO.builder().name("kuuga").sex(1).build();
        System.out.println(KuugaOptional.ofNullable(Kuuga).isEmpty());
    }

    @Test
    void empty() {
        System.out.println(KuugaOptional.empty());
        System.out.println(KuugaOptional.empty().get());
        System.out.println(KuugaOptional.empty().isEmpty());
        System.out.println(KuugaOptional.empty().getClass());
        System.out.println(KuugaOptional.empty().getOrThrow());
    }

    @Test
    void testToString() {
        KuugaDTO Kuuga = KuugaDTO.builder().name("kuuga").sex(1).build();
        System.out.println(KuugaOptional.ofNullable(Kuuga).toString());
    }

    @Data
    static class AuditModel {
        private AuditStatusEnum auditStatus;
    }

    @Test
    void test() {
        AuditModel auditModel = null;
        AuditStatusEnum auditStatusEnum = KuugaOptional.ofNullable(auditModel).getBean(AuditModel::getAuditStatus).orElseGet(null);
        System.out.println(auditStatusEnum);
    }

}