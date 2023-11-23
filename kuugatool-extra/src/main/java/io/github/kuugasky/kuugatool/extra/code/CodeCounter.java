package io.github.kuugasky.kuugatool.extra.code;

import io.github.kuugasky.kuugatool.core.collection.ArrayUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 代码行数统计
 *
 * @author kuuga
 * @since 2021/4/22
 */
public class CodeCounter {

    /**
     * 代码行数统计
     */
    public static void main(String[] args) {
        String file = Objects.requireNonNull(CodeCounter.class.getResource("/")).getFile().replace("target/classes/", "src");

        List<File> al = getFile(new File(file));
        for (File f : al) {
            // 匹配java格式的文件
            if (f.getName().matches(".*\\.java$")) {
                count(f);
                System.out.println(f);
            }
        }
        System.out.println("统计文件：" + files);
        System.out.println("代码行数：" + codeLines);
        System.out.println("注释行数：" + commentLines);
        System.out.println("空白行数：" + blankLines);
    }

    static long files = 0;
    static long codeLines = 0;
    static long commentLines = 0;
    static long blankLines = 0;
    static List<File> fileArray = new ArrayList<>();

    /**
     * 获得目录下的文件和子目录下的文件
     */
    public static List<File> getFile(File file) {
        File[] files = file.listFiles();
        if (ArrayUtil.isEmpty(files)) {
            return fileArray;
        }
        for (File child : files) {
            if (child.isDirectory()) {
                getFile(child);
            } else {
                fileArray.add(child);
            }
        }
        return fileArray;

    }

    /**
     * 统计方法
     */
    public static void count(File file) {
        BufferedReader br = null;
        boolean flag = false;
        try {
            br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                // 除去注释前的空格
                line = line.trim();
                // 匹配空行
                if (line.matches("^ *$")) {
                    blankLines++;
                } else if (line.startsWith("//")) {
                    commentLines++;
                } else if (line.startsWith("/*") && !line.endsWith("*/")) {
                    commentLines++;
                    flag = true;
                } else if (line.startsWith("/*") && line.endsWith("*/")) {
                    commentLines++;
                } else if (flag) {
                    commentLines++;
                    if (line.endsWith("*/")) {
                        flag = false;
                    }
                } else {
                    codeLines++;
                }
            }
            files++;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
