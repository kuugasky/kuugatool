package io.github.kuugasky.kuugatool.core.string;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class TraceIdGeneratorTest {

    @Test
    void generateTraceId() {
        Set<String> result = new HashSet<>(10000);
        int count = 0;
        while (count++ < 10000) {
            String traceId = TraceIdGenerator.generateTraceId();
            System.out.println(count + "--->" + traceId);
            result.add(traceId);
        }
        System.out.println(count - 1 == result.size());
    }

}