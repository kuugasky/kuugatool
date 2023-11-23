package io.github.kuugasky.kuugatool.core.jdk17;

import io.github.kuugasky.kuugatool.core.string.StringUtil;
import org.junit.jupiter.api.Test;

import java.util.random.RandomGenerator;
import java.util.random.RandomGeneratorFactory;

/**
 * RandomGeneratorTest
 * JEP 356：增强的伪随机数生成器
 * 为伪随机数生成器 RPNG（pseudorandom number generator）增加了新的接口类型和实现，让在代码中使用各种 PRNG 算法变得容易许多。
 * <p>
 * 这次增加了 RandomGenerator 接口，为所有的 PRNG 算法提供统一的 API，并且可以获取不同类型的 PRNG 对象流。同时也提供了一个新类 RandomGeneratorFactory 用于构造各种 RandomGenerator 实例，在 RandomGeneratorFactory 中使用 ServiceLoader.provider 来加载各种 PRNG 实现。
 *
 * @author kuuga
 * @since 2022/8/8 12:30
 */
public class RandomGeneratorTest {

    /**
     * 下面是一个使用示例：随便选择一个 PRNG 算法生成 5 个 10 以内的随机数。
     *
     * @param args args
     */
    public static void main(String[] args) {
        RandomGeneratorFactory<RandomGenerator> l128X256MixRandom = RandomGeneratorFactory.of("L128X256MixRandom");
        // 使用时间戳作为随机数种子
        RandomGenerator randomGenerator = l128X256MixRandom.create(System.currentTimeMillis());
        for (int i = 0; i < 5; i++) {
            System.out.println(randomGenerator.nextInt(10));
        }
        System.out.println(StringUtil.repeatNormal());
        for (int i = 0; i < 5; i++) {
            System.out.println(randomGenerator.nextDouble(10));
        }
    }

    @Test
    void foreachAllPrngAlgorithm() {
        // 可以看到 Legacy:Random 也在其中，新的 API 兼容了老的 Random 方式，所以你也可以使用新的 API 调用 Random 类生成随机数。
        RandomGeneratorFactory.all().forEach(factory -> System.out.println(factory.group() + ":" + factory.name()));
    }

    @Test
    void test1() {
        // 使用 Random
        RandomGeneratorFactory<RandomGenerator> l128X256MixRandom = RandomGeneratorFactory.of("Random");
        // 使用时间戳作为随机数种子
        RandomGenerator randomGenerator = l128X256MixRandom.create(System.currentTimeMillis());
        for (int i = 0; i < 5; i++) {
            System.out.println(randomGenerator.nextInt(10));
        }
    }

}
