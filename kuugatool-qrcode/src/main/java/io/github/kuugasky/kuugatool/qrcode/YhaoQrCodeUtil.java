package io.github.kuugasky.kuugatool.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import io.github.kuugasky.kuugatool.core.collection.MapUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Map;

/**
 * QrCodeUtil
 *
 * @author yhao
 * @date 2020-12-25 下午5:39
 */
public class YhaoQrCodeUtil {

    /**
     * 二维码颜色
     */
    private static final int BLACK = 0xFF000000;
    /**
     * 二维码颜色
     */
    private static final int WHITE = 0xFFFFFFFF;

    /**
     * 生成包含字符串信息的二维码图片
     *
     * @param outputStream 文件输出流路径
     * @param content      二维码携带信息
     * @param qrCodeSize   二维码图片大小
     * @param imageFormat  二维码的格式
     * @throws Exception Exception
     */
    public static boolean createQrCode(OutputStream outputStream, String content, int qrCodeSize, String imageFormat) throws Exception {
        Map<EncodeHintType, String> his = MapUtil.newHashMap();
        // 设置编码字符集
        his.put(EncodeHintType.CHARACTER_SET, "utf-8");
        // 1、生成二维码
        BitMatrix encode = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, qrCodeSize, qrCodeSize, his);

        // 2、获取二维码宽高
        int codeWidth = encode.getWidth();
        int codeHeight = encode.getHeight();

        // 3、将二维码放入缓冲流
        BufferedImage image = new BufferedImage(codeWidth, codeHeight, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < codeWidth; i++) {
            for (int j = 0; j < codeHeight; j++) {
                // 4、循环将二维码内容定入图片
                image.setRGB(i, j, encode.get(i, j) ? BLACK : WHITE);
            }
        }
        // 4、将二维码写入图片
        return ImageIO.write(image, imageFormat, outputStream);
    }

}
