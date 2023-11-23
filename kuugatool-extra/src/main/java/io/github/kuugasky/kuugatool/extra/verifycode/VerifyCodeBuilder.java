// package cn.kuugatool.extra.verifycode;
//
// import cn.kuugatool.core.object.ObjectUtil;
// import lombok.extern.slf4j.Slf4j;
//
// import javax.imageio.ImageIO;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
// import java.awt.*;
// import java.awt.image.BufferedImage;
// import java.io.IOException;
// import java.util.Random;
//
// /**
//  * VerifyCodeBuilder
//  *
//  * @author kuuga
//  * @date 2020-12-25 下午5:59
//  */
// @Slf4j
// public class VerifyCodeBuilder {
//
//     protected static final String CONTENT_TYPE = "image/jpeg";
//     protected final static String VERIFY_CODE_SESSION_TEMPORARY = "VERIFY_CODE_SESSION_TEMPORARY";
//
//     private static final int WIDTH = 160;
//     private static final int HEIGHT = 64;
//     /**
//      * 字符最小尺寸
//      */
//     private static final int MIN_FONT_SIZE = 50;
//     /**
//      * 字符最大尺寸
//      */
//     private static final int MAX_FONT_SIZE = 65;
//     /**
//      * 噪点数量
//      */
//     private static final int POINT_NUM = 100;
//     /**
//      * 验证码长度
//      */
//     private static final int CODE_NUM = 4;
//     /**
//      * 干扰线长
//      */
//     @SuppressWarnings("all")
//     private static int lineLength;
//     /**
//      * 干扰线数
//      */
//     private static final int LINE_NUM = 1;
//
//     /**
//      * 随机码来源
//      */
//     private static final char[] CODE = {
//             '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
//             'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
//             'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v',
//             'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F',
//             'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R',
//             'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
//     };
//     /**
//      * 字体来源
//      */
//     private static final String[] FONT_NAME = {"Candara", "Arial", "Times New Roman", "Helvetica", "Tahoma", "Verdana", "Georgia", "Lucida Grande"};
//
//     /**
//      * 颜色来源
//      */
//     private static final Color[] COLORS = {
//             new Color(0, 0, 0),
//             new Color(12, 0, 255),
//             new Color(255, 0, 7),
//             new Color(255, 0, 180),
//             new Color(0, 255, 2),
//             new Color(254, 255, 0),
//             new Color(255, 163, 0),
//             new Color(0, 255, 190),
//             new Color(192, 0, 255),
//             new Color(162, 0, 3),
//             new Color(64, 162, 4),
//             new Color(0, 131, 162),
//             new Color(102, 10, 162),
//             new Color(162, 0, 123),
//             new Color(162, 120, 0),
//             new Color(162, 120, 0)
//     };
//
//     /**
//      * 生成随机字体
//      * Font.PLAIN = 0 普通
//      * Font.BOLD = 1 加粗
//      * Font.ITALIC = 2 斜体
//      * Font.BOLD + Font.ITALIC = 3 粗斜体
//      */
//     @SuppressWarnings("all")
//     private static Font getFont(int minSize, int maxSize) {
//         Random rdm = new Random();
//         return new Font(FONT_NAME[rdm.nextInt(FONT_NAME.length)], rdm.nextInt(4), rdm.nextInt(maxSize - minSize) + minSize);
//     }
//
//     /**
//      * 获取n位随机验证码
//      *
//      * @param n length
//      * @return code
//      */
//     @SuppressWarnings("all")
//     private static String getCode(int n) {
//         Random rdm = new Random();
//         StringBuilder stringBuffer = new StringBuilder();
//         for (int i = 0; i < n; i++) {
//             stringBuffer.append(CODE[rdm.nextInt(CODE.length)]);
//         }
//         return stringBuffer.toString();
//     }
//
//     /**
//      * 生成随机颜色
//      *
//      * @return color
//      */
//     private static Color getColor() {
//         Random rdm = new Random();
//         return COLORS[rdm.nextInt(COLORS.length)];
//     }
//
//     /**
//      * 生成默认大小的验证码
//      *
//      * @param request  request
//      * @param response response
//      */
//     public static void create(HttpServletRequest request, HttpServletResponse response) {
//         create(request, response, null, null);
//     }
//
//     /**
//      * 生成指定大小的验证码
//      *
//      * @param request  request
//      * @param response response
//      */
//     public static void create(HttpServletRequest request, HttpServletResponse response, Integer customCodeImgWidth, Integer customCodeImgHeight) {
//         // 生成四位验证码
//         String capStr = getCode(CODE_NUM);
//         // 将验证码保存到Session中
//         request.getSession().setAttribute(VERIFY_CODE_SESSION_TEMPORARY, capStr);
//
//         int width = ObjectUtil.getOrElse(customCodeImgWidth, WIDTH);
//         int height = ObjectUtil.getOrElse(customCodeImgHeight, HEIGHT);
//         lineLength = width;
//
//         // 随机数
//         Random rdm = new Random();
//         //创建图片
//         BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//         // 创建画笔
//         Graphics g = image.getGraphics();
//         // 背景颜色
//         g.setColor(new Color(0xDCDCDC));
//         // 尺寸
//         g.fillRect(0, 0, width, height);
//         // 边框
//         g.drawRect(0, 0, width - 1, height - 1);
//         // 随机噪点
//         for (int i = 0; i < POINT_NUM; i++) {
//             int x = rdm.nextInt(width);
//             int y = rdm.nextInt(height);
//             // (x,y) 绘制点 0 0 为尺寸
//             g.drawOval(x, y, 2, 1);
//             g.setColor(getColor());
//         }
//
//         //绘制干扰线
//         int[] xPoints = new int[lineLength];
//         int[] yPoints = new int[lineLength];
//         //初始点位置
//         int direction = rdm.nextInt(height / 2);
//         for (int j = 0; j < LINE_NUM; j++) {
//             direction += 10;
//             g.setColor(getColor());
//             for (int i = 0; i < lineLength; i++) {
//                 xPoints[i] = i + 5;
//                 if (i % 2 != 0) {
//                     yPoints[i] = yPoints[i - 1];
//                 } else {
//                     direction += rdm.nextInt(2) == 0 ? 1 : -1;
//                     yPoints[i] = direction;
//                 }
//             }
//             //g是Graphics对象
//             Graphics2D g2 = (Graphics2D) g;
//             //干扰线粗细
//             g2.setStroke(new BasicStroke(3.0f));
//             g2.setColor(getColor());
//             g2.drawPolyline(xPoints, yPoints, lineLength);
//         }
//         // 使用getFont()获取随机字体
//         // void drawString(String str, int x, int y) 绘制一段文本，其中 (x, y) 坐标指的是文本序列的 左上角 的位置
//         //字符的x
//         int x = -width / CODE_NUM;
//         int y = height - height / 4;
//         for (int i = 0; i < CODE_NUM; i++) {
//             x += width / CODE_NUM;
//             g.setFont(getFont(MIN_FONT_SIZE, MAX_FONT_SIZE));
//             g.setColor(getColor());
//             g.drawString(capStr.substring(i, i + 1), x, y + rdm.nextInt(y / 2) - y / 4);
//             g.setFont(getFont(MIN_FONT_SIZE, MAX_FONT_SIZE));
//         }
//         g.dispose();
//         response.setContentType(CONTENT_TYPE);
//         try {
//             ImageIO.write(image, "jpg", response.getOutputStream());
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }
//
// }
