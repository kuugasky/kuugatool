package io.github.kuugasky.kuugatool.captcha;

import io.github.kuugasky.kuugatool.captcha.generator.CodeGenerator;
import io.github.kuugasky.kuugatool.captcha.generator.RandomGenerator;
import io.github.kuugasky.kuugatool.core.exception.IORuntimeException;
import io.github.kuugasky.kuugatool.core.file.FileUtil;
import io.github.kuugasky.kuugatool.core.img.ImgUtil;
import io.github.kuugasky.kuugatool.core.io.IoUtil;
import io.github.kuugasky.kuugatool.core.string.StringUtil;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.Charset;
import java.util.Base64;

/**
 * 抽象验证码<br>
 * 抽象验证码实现了验证码字符串的生成、验证，验证码图片的写出<br>
 * 实现类通过实现{@link #createImage(String)} 方法生成图片对象
 *
 * @author looly
 */
public abstract class AbstractCaptcha implements ICaptcha {

    @Serial
    private static final long serialVersionUID = 3180820918087507254L;

    /**
     * 图片的宽度
     */
    protected int width;
    /**
     * 图片的高度
     */
    protected int height;
    /**
     * 验证码干扰元素个数
     */
    protected int interfereCount;
    /**
     * 字体
     */
    protected Font font;
    /**
     * 验证码
     */
    protected String code;
    /**
     * 验证码图片
     */
    protected byte[] imageBytes;
    /**
     * 验证码生成器
     */
    @Setter
    @Getter
    protected CodeGenerator generator;
    /**
     * 背景色
     */
    protected Color background;
    /**
     * 文字透明度
     */
    protected AlphaComposite textAlpha;

    /**
     * 构造，使用随机验证码生成器生成验证码
     *
     * @param width          图片宽
     * @param height         图片高
     * @param codeCount      字符个数
     * @param interfereCount 验证码干扰元素个数
     */
    public AbstractCaptcha(int width, int height, int codeCount, int interfereCount) {
        this(width, height, new RandomGenerator(codeCount), interfereCount);
    }

    /**
     * 构造
     *
     * @param width          图片宽
     * @param height         图片高
     * @param generator      验证码生成器
     * @param interfereCount 验证码干扰元素个数
     */
    public AbstractCaptcha(int width, int height, CodeGenerator generator, int interfereCount) {
        this.width = width;
        this.height = height;
        this.generator = generator;
        this.interfereCount = interfereCount;
        // 字体高度设为验证码高度-2，留边距
        this.font = new Font(Font.SANS_SERIF, Font.PLAIN, (int) (this.height * 0.75));
    }

    @Override
    public void createCode() {
        generateCode();

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        ImgUtil.writePng(createImage(this.code), out);
        this.imageBytes = out.toByteArray();
    }

    /**
     * 生成验证码字符串
     *
     * @since 3.3.0
     */
    protected void generateCode() {
        this.code = generator.generate();
    }

    /**
     * 根据生成的code创建验证码图片
     *
     * @param code 验证码
     * @return Image
     */
    protected abstract Image createImage(String code);

    @Override
    public String getCode() {
        if (null == this.code) {
            createCode();
        }
        return this.code;
    }

    @Override
    public boolean verify(String userInputCode) {
        return this.generator.verify(getCode(), userInputCode);
    }

    /**
     * 验证码写出到文件
     *
     * @param path 文件路径
     * @throws IORuntimeException IO异常
     */
    public void write(String path) throws IORuntimeException {
        this.write(new File(path));
    }

    /**
     * 验证码写出到文件
     * PS:该方法输出gif文件时，gifCaptcha.setRepeat(1 );不生效
     *
     * @param file 文件
     * @throws IORuntimeException IO异常
     */
    public void write(File file) throws IORuntimeException {
        try (OutputStream out = FileUtil.getOutputStream(file)) {
            this.write(out);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    @Override
    public void write(OutputStream out) {
        IoUtil.write(out, false, getImageBytes());
    }

    /**
     * 获取图形验证码图片bytes
     *
     * @return 图形验证码图片bytes
     * @since 4.5.17
     */
    public byte[] getImageBytes() {
        if (null == this.imageBytes) {
            createCode();
        }
        return this.imageBytes;
    }

    /**
     * 获取验证码图
     *
     * @return 验证码图
     */
    public BufferedImage getImage() {
        return ImgUtil.read(IoUtil.toInputStream(getImageBytes()));
    }

    /**
     * 获得图片的Base64形式
     *
     * @return 图片的Base64
     * @since 3.3.0
     */
    public String getImageBase64() {
        return Base64.getEncoder().encodeToString(getImageBytes());
    }

    /**
     * 获取图片带文件格式的 Base64
     *
     * @return 图片带文件格式的 Base64
     * @since 5.3.11
     */
    public String getImageBase64Data() {
        return getDataUri("image/png", null, "base64", getImageBase64());
    }

    /**
     * Data URI Scheme封装。data URI scheme 允许我们使用内联（inline-code）的方式在网页中包含数据，<br>
     * 目的是将一些小的数据，直接嵌入到网页中，从而不用再从外部文件载入。常用于将图片嵌入网页。
     *
     * <p>
     * Data URI的格式规范：
     * <pre>
     *     data:[&lt;mime type&gt;][;charset=&lt;charset&gt;][;&lt;encoding&gt;],&lt;encoded data&gt;
     * </pre>
     *
     * @param mimeType 可选项（null表示无），数据类型（image/png、text/plain等）
     * @param charset  可选项（null表示无），源文本的字符集编码方式
     * @param encoding 数据编码方式（US-ASCII，BASE64等）
     * @param data     编码后的数据
     * @return Data URI字符串
     * @since 5.3.6
     */
    public static String getDataUri(String mimeType, Charset charset, String encoding, String data) {
        final StringBuilder builder = new StringBuilder("data:");
        if (StringUtil.hasText(mimeType)) {
            builder.append(mimeType);
        }
        if (null != charset) {
            builder.append(";charset=").append(charset.name());
        }
        if (StringUtil.hasText(encoding)) {
            builder.append(';').append(encoding);
        }
        builder.append(',').append(data);

        return builder.toString();
    }

    /**
     * 自定义字体
     *
     * @param font 字体
     */
    public void setFont(Font font) {
        this.font = font;
    }

    /**
     * 设置背景色
     *
     * @param background 背景色
     * @since 4.1.22
     */
    public void setBackground(Color background) {
        this.background = background;
    }

    /**
     * 设置文字透明度
     *
     * @param textAlpha 文字透明度，取值0~1，1表示不透明
     * @since 4.5.17
     */
    public void setTextAlpha(float textAlpha) {
        this.textAlpha = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, textAlpha);
    }

}
