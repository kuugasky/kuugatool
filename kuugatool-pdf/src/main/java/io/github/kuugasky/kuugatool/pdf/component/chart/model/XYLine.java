package io.github.kuugasky.kuugatool.pdf.component.chart.model;

import lombok.Data;

/**
 * 线性图
 *
 * @author fgm
 * @since 2017/4/7
 */
@Data
public class XYLine {
    private double yValue;
    private String xValue;
    private String groupName;

    public XYLine() {

    }

    /**
     * 创建XYLINE
     *
     * @param yValue    Y轴值
     * @param xValue    X轴值
     * @param groupName 组名
     */
    public XYLine(double yValue, String xValue, String groupName) {
        this.yValue = yValue;
        this.xValue = xValue;
        this.groupName = groupName;
    }


}
