// package cn.kuugatool.extra.concurrent;
//
// import cn.kuugatool.extra.concurrent.config.SleuthThreadConfig;
// import org.junit.jupiter.api.Test;
// import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
//
// import java.util.concurrent.Future;
//
// @SpringJUnitConfig(classes = SleuthThreadConfig.class)
// class KuugaThreadPoolTest {
//
//     @Test
//     void call() {
//         Future<String> future = KuugaThreadPool.call(() -> {
//             System.out.println("my name is Kuuga");
//             return "good boy";
//         }, result -> System.out.println("执行成功"), error -> System.out.println("执行失败"));
//         System.out.println(future);
//     }
//
// }