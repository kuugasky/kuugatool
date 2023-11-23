package io.github.kuugasky.kuugatool.qrcode;

import org.junit.jupiter.api.Test;

import java.io.FileOutputStream;
import java.io.OutputStream;

public class YhaoQrCodeUtilTest {

    @Test
    public void createQrCode() throws Exception {
        OutputStream outputStream = new FileOutputStream("/Users/kuuga/Downloads/test-kuuga.jpg");
        boolean jpt = YhaoQrCodeUtil.createQrCode(outputStream, "https://shenzhen.sbwl.com", 200, "jpg");

        System.out.println(jpt);
    }

}