package io.github.kuugasky.kuugatool.core.object;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.object.bean.NameMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class NameMapperUtilTest {

    public static Map<Integer, String> nameMap = new HashMap<>();

    static {
        nameMap.put(1, "kuuga");
        nameMap.put(2, "kuuga1");
        nameMap.put(3, "kuuga2");
    }

    @Test
    void instance() {
        List<Demo> demos = ListUtil.newArrayList();
        demos.add(new Demo(1));
        demos.add(new Demo(2));
        demos.add(new Demo(3));
        NameMapper.<Demo, NameMapperDemo, Integer>instance(demos)
                .execute(new String[]{"id", "id"},
                        ids -> {
                            List<NameMapperDemo> nameMappers = ListUtil.newArrayList();
                            ids.forEach(id -> {
                                String realName = nameMap.get(id);
                                nameMappers.add(new NameMapperDemo(id, realName));
                            });
                            return nameMappers;
                        },
                        (t, r) -> t.setName(r.getRealName()));

        System.out.println("---------------------");

        demos.forEach(x -> System.out.println(x.getName()));
    }

    @Data
    @AllArgsConstructor
    public static class Demo {
        private Integer id;
        private String name;

        public Demo(Integer id) {
            this.id = id;
        }
    }

    @Data
    @AllArgsConstructor
    public static class NameMapperDemo {
        private Integer id;
        private String realName;
    }

}