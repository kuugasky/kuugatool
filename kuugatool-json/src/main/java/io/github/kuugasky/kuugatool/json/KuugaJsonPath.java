package io.github.kuugasky.kuugatool.json;

import io.github.kuugasky.kuugatool.core.queue.FifoLinkedQueue;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * KuugaJsonPath
 *
 * @author kuuga
 * @since 2022/12/23-12-23 14:47
 */
@Slf4j
public final class KuugaJsonPath {

    public static KuugaJsonParser of(String json, String jsonPath) {
        if (StringUtil.isEmpty(jsonPath)) {
            return KuugaJsonParser.of("{}");
        }
        KuugaJsonParser kuugaJsonParser = KuugaJsonParser.of(json);

        try {
            FifoLinkedQueue<String> charQueue = parsePath(jsonPath);
            boolean nextStepIsObject = true;

            while (charQueue.hasItem()) {
                String signOrField = charQueue.get();
                if (StringUtil.contains(signOrField, new String[]{".", "#"})) {
                    if ("\\.".equals(signOrField)) {
                        nextStepIsObject = true;
                    }
                    if ("#".equals(signOrField)) {
                        nextStepIsObject = false;
                    }
                } else {
                    if (nextStepIsObject) {
                        kuugaJsonParser.parsingObject(signOrField);
                    } else {
                        kuugaJsonParser.parsingArray(signOrField);
                    }
                }
            }
        } catch (Exception e) {
            log.error("Json path parsing error, please check your path. \n error : {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return kuugaJsonParser;
    }

    private static FifoLinkedQueue<String> parsePath(String path) {
        FifoLinkedQueue<String> charQueue = new FifoLinkedQueue<>();
        char[] chars = path.toCharArray();

        StringBuilder whileFieldValue = StringUtil.builder();
        for (int i = 0; i < chars.length; i++) {
            char aChar = chars[i];
            if (aChar == '.' || aChar == '#') {
                if (StringUtil.hasText(whileFieldValue.toString())) {
                    charQueue.put(whileFieldValue.toString());
                    whileFieldValue = StringUtil.builder();
                }
                charQueue.put(String.valueOf(aChar));
            } else {
                whileFieldValue.append(aChar);
                if ((i + 1) == chars.length) {
                    charQueue.put(whileFieldValue.toString());
                }
            }
        }
        return charQueue;
    }

}
