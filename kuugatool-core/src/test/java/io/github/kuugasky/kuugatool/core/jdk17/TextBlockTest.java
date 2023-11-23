package io.github.kuugasky.kuugatool.core.jdk17;

import org.junit.jupiter.api.Test;

/**
 * TextBlockTest
 *
 * @author kuuga
 * @since 2022/8/3 10:15
 */
public class TextBlockTest {

    /**
     * jdk13新特性
     */
    @Test
    void test() {
        // 传统方式
        String html = "<html>\n" +
                "    <body>\n" +
                "        <p>Hello, world</p>\n" +
                "    </body>\n" +
                "</html>\n";

        // 文本块方式
        String html1 = """
                <html>
                    <body>
                        <p>Hello, world</p>
                    </body>
                </html>
                """;

        // 传统方式
        String query = "SELECT `EMP_ID`, `LAST_NAME` FROM `EMPLOYEE_TB`\n" +
                "WHERE `CITY` = 'INDIANAPOLIS'\n" +
                "ORDER BY `EMP_ID`, `LAST_NAME`;\n";

        // 文本块方式
        String query1 = """
                SELECT `EMP_ID`, `LAST_NAME` FROM `EMPLOYEE_TB`
                WHERE `CITY` = 'INDIANAPOLIS'
                ORDER BY `EMP_ID`, `LAST_NAME`;
                """;
    }

}
