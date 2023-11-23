package io.github.kuugasky.kuugatool.core.clazz;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

/**
 * IO转换工具
 *
 * @author kuuga
 * @since 2022/7/5 18:56
 */
public final class IoConverterUtil {

    /**
     * 字符转字节流（写入外部资源）
     *
     * @param fileOutputStream 文件输出流
     * @return 输出流写入
     */
    public static OutputStreamWriter characterStreamToByteStream(FileOutputStream fileOutputStream) {
        return new OutputStreamWriter(fileOutputStream);
    }

    /**
     * 字节转字符流（读取外部资源）
     *
     * @param fileInputStream 文件输入流
     * @return 输入流读取
     */
    public static InputStreamReader byteStreamToCharacterStream(FileInputStream fileInputStream) {
        return new InputStreamReader(fileInputStream, Charset.defaultCharset());
    }

}
