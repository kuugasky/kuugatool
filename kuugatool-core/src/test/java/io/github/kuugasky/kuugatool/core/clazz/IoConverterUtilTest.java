package io.github.kuugasky.kuugatool.core.clazz;

import org.junit.jupiter.api.Test;

import java.io.*;

class IoConverterUtilTest {

    String file = "/Users/kuuga/Downloads/Kuuga.txt";

    @Test
    void characterStreamToByteStream() throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(file);
             OutputStreamWriter outputStreamWriter = IoConverterUtil.characterStreamToByteStream(fileOutputStream)) {
            outputStreamWriter.write("Kuuga is me.中华");
        }
    }

    @Test
    void byteStreamToCharacterStream() throws IOException {
        InputStreamReader inputStreamReader = IoConverterUtil.byteStreamToCharacterStream(new FileInputStream(file));

        char[] buf = new char[1024];

        int leh = inputStreamReader.read(buf);

        System.out.println(new String(buf, 0, leh));

        inputStreamReader.close();
    }

}