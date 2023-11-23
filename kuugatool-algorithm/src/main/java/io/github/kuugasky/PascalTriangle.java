package io.github.kuugasky;

/**
 * PascalTriangle
 * <p>
 * 杨辉三角
 *
 * @author kuuga
 * @since 2023/9/26-09-26 15:02
 */
public class PascalTriangle {

    public static void main(String[] args) {
        int n = 5; // 指定要生成的行数

        generatePascalTriangle(n);
    }

    public static void generatePascalTriangle(int n) {
        for (int i = 0; i < n; i++) {
            int number = 1;
            for (int j = 0; j < n - i; j++) {
                System.out.print("  "); // 添加空格以对齐
            }
            for (int j = 0; j <= i; j++) {
                System.out.print("   "); // 添加空格以对齐
                System.out.print(number);
                number = number * (i - j) / (j + 1);
            }
            System.out.println();
        }
    }

}
