package io.github.kuugasky.kuugatool.captcha;

import io.github.kuugasky.kuugatool.captcha.generator.MathGenerator;
import io.github.kuugasky.kuugatool.core.img.ImgUtil;
import io.github.kuugasky.kuugatool.core.random.RandomUtil;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.File;

public class CaptchaUtilTest {

    @Test
    public void createTest() {
        for (int i = 0; i < 1; i++) {
            // 创建扭曲干扰的验证码，默认5位验证码
            CaptchaUtil.createShearCaptcha(600, 200).write("/Users/kuuga/Downloads/Kuuga" + i + ".jpg");
        }
    }

    @Test
    public void createLineCaptcha() {
        /*
         * 创建线干扰的验证码，默认5位验证码，150条干扰线
         *
         * @param width  图片宽
         * @param height 图片高
         * @return {@link LineCaptcha}
         */
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
        Image xxx = lineCaptcha.createImage(RandomUtil.randomNumbers(4));
        // final ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImgUtil.write(xxx, new File("/Users/kuuga/Downloads/captcha/1/createLineCaptcha1 .jpg"));
    }

    @Test
    public void testCreateLineCaptcha() {
        /*
         * 创建线干扰的验证码
         *
         * @param width     图片宽
         * @param height    图片高
         * @param codeCount 字符个数
         * @param lineCount 干扰线条数
         * @return {@link LineCaptcha}
         */
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100, 5, 1000);
        Image xxx = lineCaptcha.createImage(RandomUtil.randomNumbers(4));
        ImgUtil.write(xxx, new File("/Users/kuuga/Downloads/captcha/1/createLineCaptcha2.jpg"));
    }

    @Test
    public void createCircleCaptcha() {
        /*
         * 创建圆圈干扰的验证码，默认5位验证码，15个干扰圈
         *
         * @param width  图片宽
         * @param height 图片高
         * @return {@link CircleCaptcha}
         * @since 3.2.3
         */
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(200, 100);
        Image xxx = circleCaptcha.createImage(RandomUtil.randomNumbers(4));
        ImgUtil.write(xxx, new File("/Users/kuuga/Downloads/captcha/1/createLineCaptcha3.jpg"));
    }

    @Test
    public void testCreateCircleCaptcha() {
        /*
         * 创建圆圈干扰的验证码
         *
         * @param width       图片宽
         * @param height      图片高
         * @param codeCount   字符个数
         * @param circleCount 干扰圆圈条数
         * @return {@link CircleCaptcha}
         * @since 3.2.3
         */
        CircleCaptcha circleCaptcha = CaptchaUtil.createCircleCaptcha(200, 100, 4, 1000);
        Image xxx = circleCaptcha.createImage(RandomUtil.randomNumbers(4));
        ImgUtil.write(xxx, new File("/Users/kuuga/Downloads/captcha/1/createLineCaptcha4.jpg"));
    }

    @Test
    public void createShearCaptcha() {
        /*
         * 创建扭曲干扰的验证码，默认5位验证码
         *
         * @param width  图片宽
         * @param height 图片高
         * @return {@link ShearCaptcha}
         * @since 3.2.3
         */
        ShearCaptcha shearCaptcha = CaptchaUtil.createShearCaptcha(300, 100);
        ImgUtil.write(shearCaptcha.getImage(), new File("/Users/kuuga/Downloads/captcha/1/createLineCaptcha5.jpg"));
    }

    @Test
    public void testCreateShearCaptcha() {
        /*
         * 创建扭曲干扰的验证码，默认5位验证码
         *
         * @param width     图片宽
         * @param height    图片高
         * @param codeCount 字符个数
         * @param thickness 干扰线宽度
         * @return {@link ShearCaptcha}
         * @since 3.3.0
         */
        ShearCaptcha shearCaptcha = CaptchaUtil.createShearCaptcha(300, 100, 4, 3);
        File targetFile = new File("/Users/kuuga/Downloads/captcha/1/createLineCaptcha6.jpg");
        if (targetFile.exists()) {
            System.out.println(targetFile.delete());
        }
        ImgUtil.write(shearCaptcha.getImage(), targetFile);
    }

    @Test
    public void createGifCaptcha() {
        /*
         * 创建GIF验证码
         *
         * @param width 宽
         * @param height 高
         * @return {@link GifCaptcha}
         */
        GifCaptcha gifCaptcha = CaptchaUtil.createGifCaptcha(300, 100);
        /*
         * 设置GIF帧应该播放的次数。
         * 默认是 0; 0意味着无限循环。
         * 必须在添加的第一个图像之前被调用。
         */
        gifCaptcha.setRepeat(5);
        // 随机字符验证码生成器
        // gifCaptcha.setGenerator(new RandomGenerator(RandomUtil.BASE_CHAR_NUMBER, 5));
        // 数字计算验证码生成器【53+41=】
        gifCaptcha.setGenerator(new MathGenerator(2));

        System.out.println(gifCaptcha.getCode());
        /*
         * 设置图像的颜色量化(转换质量 由GIF规范允许的最大256种颜色)。
         * 低的值(最小值= 1)产生更好的颜色,但处理显著缓慢。
         * 10是默认,并产生良好的颜色而且有以合理的速度。
         * 值更大(大于20)不产生显著的改善速度
         */
        gifCaptcha.setQuality(10);
        // 设置文字透明度
        gifCaptcha.setTextAlpha(0.5f);
        gifCaptcha.setMaxColor(255);
        gifCaptcha.setMinColor(255);
        gifCaptcha.setBackground(Color.blue);
        gifCaptcha.setFont(Font.getFont(Font.MONOSPACED));
        File targetFile = new File("/Users/kuuga/Downloads/captcha/1/createLineCaptcha7-1.gif");
        if (targetFile.exists()) {
            System.out.println(targetFile.delete());
        }
        gifCaptcha.write(targetFile);
        // ImgUtil.write(gifCaptcha.getImage(), targetFile);
    }

    @Test
    public void testCreateGifCaptcha() {
        /*
         * 创建GIF验证码
         *
         * @param width 宽
         * @param height 高
         * @param codeCount 字符个数
         * @return {@link GifCaptcha}
         */
        GifCaptcha gifCaptcha = CaptchaUtil.createGifCaptcha(300, 100, 6);
        System.out.println(gifCaptcha.getCode());
        File targetFile = new File("/Users/kuuga/Downloads/captcha/1/createLineCaptcha7-2.gif");
        if (targetFile.exists()) {
            System.out.println(targetFile.delete());
        }
        gifCaptcha.write(targetFile);
        // 输出到流
        // final ByteArrayOutputStream out = new ByteArrayOutputStream();
        // gifCaptcha.write(out);
    }

}
