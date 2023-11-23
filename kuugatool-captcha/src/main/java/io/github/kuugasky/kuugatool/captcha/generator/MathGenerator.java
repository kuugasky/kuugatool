package io.github.kuugasky.kuugatool.captcha.generator;

import io.github.kuugasky.kuugatool.core.constants.KuugaConstants;
import io.github.kuugasky.kuugatool.core.lang.Console;
import io.github.kuugasky.kuugatool.core.math.Calculator;
import io.github.kuugasky.kuugatool.core.random.RandomUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;

import java.io.Serial;

/**
 * 数字计算验证码生成器
 *
 * @author looly
 * @since 4.1.2
 */
public class MathGenerator implements CodeGenerator {

    @Serial
    private static final long serialVersionUID = -5514819971774091076L;

    private static final String OPERATORS = "+-*";

    /**
     * 参与计算数字最大长度
     */
    private final int numberLength;

    /**
     * 构造
     */
    public MathGenerator() {
        this(2);
    }

    /**
     * 构造
     *
     * @param numberLength 参与计算最大数字位数
     */
    public MathGenerator(int numberLength) {
        this.numberLength = numberLength;
    }

    @Override
    public String generate() {
        final int limit = getLimit();
        String number1 = Integer.toString(RandomUtil.randomInt(limit));
        String number2 = Integer.toString(RandomUtil.randomInt(limit));
        number1 = StringUtil.padAfter(number1, this.numberLength, KuugaConstants.SPACE);
        number2 = StringUtil.padAfter(number2, this.numberLength, KuugaConstants.SPACE);

        return StringUtil.removeAllSpace(number1) + RandomUtil.randomChar(OPERATORS) + number2 + '=';
    }

    @Override
    public boolean verify(String code, String userInputCode) {
        int result;
        try {
            result = Integer.parseInt(userInputCode);
        } catch (NumberFormatException e) {
            // 用户输入非数字
            return false;
        }

        final int calculateResult = (int) Calculator.conversion(code);
        Console.log("result:" + calculateResult);
        return result == calculateResult;
    }

    /**
     * 获取验证码长度
     *
     * @return 验证码长度
     */
    public int getLength() {
        return this.numberLength * 2 + 2;
    }

    /**
     * 根据长度获取参与计算数字最大值
     *
     * @return 最大值
     */
    private int getLimit() {
        return Integer.parseInt("1" + StringUtil.repeatAndJoin("0", this.numberLength, StringUtil.EMPTY));
    }

}
