package io.github.kuugasky.kuugatool.pdf.component.chart;

import lombok.Getter;
import lombok.Setter;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.XYPlot;
import org.jfree.io.SerialUtilities;
import org.jfree.text.TextUtilities;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.TextAnchor;
import org.jfree.util.ObjectUtilities;
import org.jfree.util.PublicCloneable;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.*;

/**
 * XY指针框架注释
 *
 * @author fgm
 * @since 2017/7/4
 */
@Getter
@Setter
public class XYPointerFrameAnnotation extends XYTextAnnotation implements Cloneable, PublicCloneable, Serializable {

    @Serial
    private static final long serialVersionUID = -4031161445009858499L;
    /**
     * 默认提示半径
     */
    public static final double DEFAULT_TIP_RADIUS = 10.0D;
    /**
     * 默认基准半径
     */
    public static final double DEFAULT_BASE_RADIUS = 40.0D;
    /**
     * 默认标签偏移量
     */
    public static final double DEFAULT_LABEL_OFFSET = 8.0D;
    /**
     * 默认箭头长度
     */
    public static final double DEFAULT_ARROW_LENGTH = 10.0D;
    /**
     * 默认箭头宽度
     */
    public static final double DEFAULT_ARROW_WIDTH = 3.0D;
    /**
     * 角
     */
    private double angle;
    /**
     * 尖端半径
     */
    private double tipRadius;
    /**
     * 基础半径
     */
    private double baseRadius;
    /**
     * 箭头长度
     */
    private double arrowLength;
    /**
     * 箭头宽度
     */
    private double arrowWidth;
    /**
     * 箭头行程
     */
    private transient Stroke arrowStroke;
    /**
     * 箭头油漆
     */
    private transient Paint arrowPaint;
    /**
     * 标签偏移
     */
    private double labelOffset;
    /**
     * 线条油漆
     */
    private transient Paint linePaint;

    /**
     * 创建XY指针框架注释
     *
     * @param label 标签
     * @param x     x值
     * @param y     y值
     * @param angle 角
     */
    public XYPointerFrameAnnotation(String label, double x, double y, double angle) {
        super(label, x, y);
        this.angle = angle;
        this.tipRadius = 1;// 连接线到点的距离
        this.baseRadius = 50;// 连接线的半径
        this.arrowLength = 0;// 箭头长度 不需要
        this.arrowWidth = 0;// 箭头宽度 不需要
        this.labelOffset = 10;// label字体和竖线的距离
        this.arrowStroke = new BasicStroke(1.0F);
        this.arrowPaint = Color.WHITE;
        this.linePaint = Color.WHITE;
    }

    public void setArrowStroke(Stroke stroke) {
        if (stroke == null) {
            throw new IllegalArgumentException("Null 'stroke' not permitted.");
        } else {
            this.arrowStroke = stroke;
        }
    }

    public void draw(Graphics2D g2, XYPlot plot, Rectangle2D dataArea, ValueAxis domainAxis, ValueAxis rangeAxis, int rendererIndex, PlotRenderingInfo info) {
        PlotOrientation orientation = plot.getOrientation();
        RectangleEdge domainEdge = Plot.resolveDomainAxisLocation(plot.getDomainAxisLocation(), orientation);
        RectangleEdge rangeEdge = Plot.resolveRangeAxisLocation(plot.getRangeAxisLocation(), orientation);
        double j2DX = domainAxis.valueToJava2D(this.getX(), dataArea, domainEdge);
        double j2DY = rangeAxis.valueToJava2D(this.getY(), dataArea, rangeEdge);
        double startX;
        if (orientation == PlotOrientation.HORIZONTAL) {
            startX = j2DX;
            j2DX = j2DY;
            j2DY = startX;
        }
        startX = j2DX + Math.cos(this.angle) * this.baseRadius;
        double startY = j2DY + Math.sin(this.angle) * this.baseRadius;
        double endX = j2DX + Math.cos(this.angle) * this.tipRadius;
        double endY = j2DY + Math.sin(this.angle) * this.tipRadius;
        g2.setStroke(this.arrowStroke);
        g2.setPaint(this.arrowPaint);
        Line2D.Double line = new Line2D.Double(startX, startY, endX, endY);

        g2.draw(line);
        g2.setFont(this.getFont());
        g2.setPaint(this.getPaint());
        double labelX = j2DX + Math.cos(this.angle) * (this.baseRadius + this.labelOffset);
        double labelY = j2DY + Math.sin(this.angle) * (this.baseRadius + this.labelOffset);
        Rectangle2D rectangle2D = TextUtilities.drawAlignedString(this.getText(), g2, (float) labelX, (float) labelY, TextAnchor.BASELINE_CENTER);
        // 添加边框
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(1.0F));
        rectangle2D.setFrame(rectangle2D.getX() - 3, rectangle2D.getY() - 2, rectangle2D.getWidth() + 5, rectangle2D.getHeight() + 2);
        g2.draw(rectangle2D);


    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        } else if (obj == this) {
            return true;
        } else if (!(obj instanceof XYPointerFrameAnnotation)) {
            return false;
        } else if (!super.equals(obj)) {
            return false;
        } else {
            XYPointerFrameAnnotation that = (XYPointerFrameAnnotation) obj;
            return this.angle != that.angle ? false : (this.tipRadius != that.tipRadius ? false : (this.baseRadius != that.baseRadius ? false : (this.arrowLength != that.arrowLength ? false : (this.arrowWidth != that.arrowWidth ? false : (!this.arrowPaint.equals(that.arrowPaint) ? false : (!ObjectUtilities.equal(this.arrowStroke, that.arrowStroke) ? false : this.labelOffset == that.labelOffset))))));
        }
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * 写入对象输入流
     *
     * @param stream ObjectInputStream
     * @throws IOException IO异常
     */
    @Serial
    private void writeObject(ObjectOutputStream stream) throws IOException {
        stream.defaultWriteObject();
        SerialUtilities.writePaint(this.arrowPaint, stream);
        SerialUtilities.writeStroke(this.arrowStroke, stream);
    }

    /**
     * 读取对象输入流
     *
     * @param stream ObjectInputStream
     * @throws IOException            IO异常
     * @throws ClassNotFoundException 类找不到异常
     */
    @Serial
    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();
        this.arrowPaint = SerialUtilities.readPaint(stream);
        this.arrowStroke = SerialUtilities.readStroke(stream);
    }


}
