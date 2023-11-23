package io.github.kuugasky.kuugatool.captcha;

import io.github.kuugasky.kuugatool.captcha.generator.MathGenerator;
import org.junit.jupiter.api.Test;

public class GeneratorTest {
    @Test
    public void mathGeneratorTest() {
        final MathGenerator mathGenerator = new MathGenerator();
        for (int i = 0; i < 10; i++) {
            String generate = mathGenerator.generate();
            System.out.println(generate);
            System.out.println(mathGenerator.verify(generate, "20"));
        }
    }
}
