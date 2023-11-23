package io.github.kuugasky.kuugatool.core.object;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import io.github.kuugasky.kuugatool.core.date.DateUtil;
import io.github.kuugasky.kuugatool.core.entity.KuugaDTO;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

public class PreconditionsTest {

    @Test
    public void checkArgument() {
        Preconditions.checkArgument(2 > 1);
    }

    @Test
    public void testCheckArgument() {
        Preconditions.checkArgument(1 > 2, "错误");
    }

    @Test
    public void testCheckArgument1() {
        Preconditions.checkArgument(1 > 2, "错误%s", "xxx");
    }

    @Test
    public void testCheckArgument2() {
        Preconditions.checkArgument(1 > 2, "错误%s%s", 'x', '=');
    }

    @Test
    public void testCheckArgument3() {
        Preconditions.checkArgument(1 > 2, "错误%s%s", 'x', 2);
    }

    @Test
    public void testCheckArgument4() {
        Preconditions.checkArgument(1 > 2, "错误%s%s", 'x', 2L);
    }

    @Test
    public void testCheckArgument5() {
        Preconditions.checkArgument(1 > 2, "错误%s%s", 'x', new KuugaDTO());
    }

    @Test
    public void testCheckArgument6() {
        Preconditions.checkArgument(1 > 2, "错误%s%s", 1, '-');
    }

    @Test
    public void testCheckArgument7() {
        Preconditions.checkArgument(1 > 2, "错误%s%s", 1, 2);
    }

    @Test
    public void testCheckArgument8() {
        Preconditions.checkArgument(1 > 2, "错误%s%s", 1, 2L);
    }

    @Test
    public void testCheckArgument9() {
        Preconditions.checkArgument(1 > 2, "错误%s%s", 1, new KuugaDTO());
    }

    @Test
    public void testCheckArgument10() {
    }

    @Test
    public void testCheckArgument11() {
    }

    @Test
    public void testCheckArgument12() {
    }

    @Test
    public void testCheckArgument13() {
    }

    @Test
    public void testCheckArgument14() {
    }

    @Test
    public void testCheckArgument15() {
    }

    @Test
    public void testCheckArgument16() {
    }

    @Test
    public void testCheckArgument17() {
    }

    @Test
    public void testCheckArgument18() {
    }

    @Test
    public void testCheckArgument19() {
    }

    @Test
    public void testCheckArgument20() {
    }

    @Test
    public void testCheckArgument21() {
    }

    @Test
    public void testCheckArgument22() {
    }

    @Test
    public void testCheckArgument23() {
    }

    @Test
    public void checkState() {
    }

    @Test
    public void testCheckState() {
    }

    @Test
    public void testCheckState1() {
    }

    @Test
    public void testCheckState2() {
    }

    @Test
    public void testCheckState3() {
    }

    @Test
    public void testCheckState4() {
    }

    @Test
    public void testCheckState5() {
    }

    @Test
    public void testCheckState6() {
    }

    @Test
    public void testCheckState7() {
    }

    @Test
    public void testCheckState8() {
    }

    @Test
    public void testCheckState9() {
    }

    @Test
    public void testCheckState10() {
    }

    @Test
    public void testCheckState11() {
    }

    @Test
    public void testCheckState12() {
    }

    @Test
    public void testCheckState13() {
    }

    @Test
    public void testCheckState14() {
    }

    @Test
    public void testCheckState15() {
    }

    @Test
    public void testCheckState16() {
    }

    @Test
    public void testCheckState17() {
    }

    @Test
    public void testCheckState18() {
    }

    @Test
    public void testCheckState19() {
    }

    @Test
    public void testCheckState20() {
    }

    @Test
    public void testCheckState21() {
    }

    @Test
    public void testCheckState22() {
    }

    @Test
    public void testCheckState23() {
    }

    @Test
    public void checkNotNull() {
    }

    @Test
    public void testCheckNotNull() {
    }

    @Test
    public void testCheckNotNull1() {
    }

    @Test
    public void testCheckNotNull2() {
    }

    @Test
    public void testCheckNotNull3() {
    }

    @Test
    public void testCheckNotNull4() {
    }

    @Test
    public void testCheckNotNull5() {
    }

    @Test
    public void testCheckNotNull6() {
    }

    @Test
    public void testCheckNotNull7() {
    }

    @Test
    public void testCheckNotNull8() {
    }

    @Test
    public void testCheckNotNull9() {
    }

    @Test
    public void testCheckNotNull10() {
    }

    @Test
    public void testCheckNotNull11() {
    }

    @Test
    public void testCheckNotNull12() {
    }

    @Test
    public void testCheckNotNull13() {
    }

    @Test
    public void testCheckNotNull14() {
    }

    @Test
    public void testCheckNotNull15() {
    }

    @Test
    public void testCheckNotNull16() {
    }

    @Test
    public void testCheckNotNull17() {
    }

    @Test
    public void testCheckNotNull18() {
    }

    @Test
    public void testCheckNotNull19() {
    }

    @Test
    public void testCheckNotNull20() {
    }

    @Test
    public void testCheckNotNull21() {
    }

    @Test
    public void testCheckNotNull22() {
    }

    @Test
    public void testCheckNotNull23() {
    }

    @Test
    public void checkElementIndex() {
    }

    @Test
    public void testCheckElementIndex() {
    }

    @Test
    public void checkPositionIndex() {
    }

    @Test
    public void testCheckPositionIndex() {
    }

    @Test
    public void checkPositionIndexes() {
    }

    @Test
    public void test() {
        Preconditions.checkArgument(1 > 2, new RuntimeException("错误信息"));
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println("开始:" + DateUtil.now());

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        // Future<?> submit = executorService.submit(() -> {
        //     try {
        //         Thread.sleep(10000);
        //     } catch (InterruptedException e) {
        //         e.printStackTrace();
        //     }
        // });
        //
        // System.out.println("----------");
        //
        // submit.get();

        ListeningExecutorService service = MoreExecutors.listeningDecorator(executorService);
        ListenableFuture<Integer> future = service.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("开始耗时计算:" + DateUtil.now());
                Thread.sleep(5000);
                System.out.println("结束耗时计算:" + DateUtil.now());
                return 100;
            }
        });

        future.addListener(new Runnable() {
            @Override
            public void run() {
                System.out.println("调用成功");
            }
        }, executorService);
        System.out.println("结束:" + DateUtil.now());
        new CountDownLatch(1).await();
    }

}
