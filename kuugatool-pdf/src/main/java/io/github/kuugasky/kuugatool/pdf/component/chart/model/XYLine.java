package io.github.kuugasky.kuugatool.pdf.component.chart.model;

import lombok.Data;

/**
 * Created by fgm on 2017/4/7.
 */
@Data
public class XYLine {
    private double yValue;
    private String xValue;
    private String groupName;

    public XYLine() {

    }

    public XYLine(double yValue, String xValue, String groupName) {
        this.yValue = yValue;
        this.xValue = xValue;
        this.groupName = groupName;
    }


}
