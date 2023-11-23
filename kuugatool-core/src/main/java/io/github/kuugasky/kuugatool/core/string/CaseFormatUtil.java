package io.github.kuugasky.kuugatool.core.string;

import com.google.common.base.CaseFormat;

/**
 * CaseFormatUtil
 * 不实用
 *
 * @author kuuga
 * @since 2022/8/12 14:27
 */
public class CaseFormatUtil {

    public static void main(String[] args) {
        CaseFormat[] values = CaseFormat.values();
        for (CaseFormat value : values) {
            for (CaseFormat caseFormat : values) {
                String kuugaHuang = value.to(caseFormat, "Kuuga Huang");
                System.out.printf("%s + %s = %s%n", value, caseFormat, kuugaHuang);
            }
        }
    }

}
