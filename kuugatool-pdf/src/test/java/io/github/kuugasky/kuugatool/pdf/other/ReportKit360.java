package io.github.kuugasky.kuugatool.pdf.other;

import com.google.common.collect.Lists;
import io.github.kuugasky.kuugatool.core.file.FileUtil;
import io.github.kuugasky.kuugatool.core.object.ObjectUtil;
import io.github.kuugasky.kuugatool.pdf.KuugaPdf;
import io.github.kuugasky.kuugatool.pdf.component.PDFHeaderFooter;
import io.github.kuugasky.kuugatool.pdf.component.chart.ScatterPlotChart;
import io.github.kuugasky.kuugatool.pdf.component.chart.impl.DefaultLineChart;
import io.github.kuugasky.kuugatool.pdf.component.chart.model.XYLine;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fgm on 2017/4/17.
 * 360报告
 */
@Slf4j
public class ReportKit360 {

    public static List<XYLine> getTemperatureLineList() {
        List<XYLine> list = Lists.newArrayList();
        for (int i = 1; i <= 7; i++) {
            XYLine line = new XYLine();
            float random = Math.round(Math.random() * 10);
            line.setXValue("星期" + i);
            line.setYValue(20 + random);
            line.setGroupName("下周");
            list.add(line);
        }
        for (int i = 1; i <= 7; i++) {
            XYLine line = new XYLine();
            float random = Math.round(Math.random() * 10);
            line.setXValue("星期" + i);
            line.setYValue(20 + random);
            line.setGroupName("这周");
            list.add(line);
        }
        return list;
    }

    public static void main(String[] args) {
        TemplateBO templateBO = new TemplateBO();
        templateBO.setTemplateName("Hello iText! Hello freemarker! Hello jFreeChart!");
        templateBO.setFreeMarkerUrl("http://www.zheng-hang.com/chm/freemarker2_3_24/ref_directive_if.html");
        templateBO.setITEXTUrl("http://developers.itextpdf.com/examples-itext5");
        templateBO.setJFreeChartUrl("http://www.yiibai.com/jfreechart/jfreechart_referenced_apis.html");
        templateBO.setImageUrl("http://mss.vip.sankuai.com/v1/mss_74e5b6ab17f44f799a524fa86b6faebf/360report/logo_1.png");

        List<String> scores = new ArrayList<>();
        scores.add("90");
        scores.add("95");
        scores.add("98");
        templateBO.setScores(scores);
        // 折线图
        List<XYLine> lineList = getTemperatureLineList();
        DefaultLineChart lineChart = new DefaultLineChart();
        lineChart.setHeight(500);
        lineChart.setWidth(300);
        String picUrl = lineChart.draw(lineList, 0);
        templateBO.setPicUrl(picUrl);

        // 散点图
        String scatterUrl = ScatterPlotChart.draw(ScatterPlotChartTest.getData(), 1, "他评得分(%)", "自评得分(%)");
        templateBO.setScatterUrl(scatterUrl);
        String templateFilePath = "/Users/kuuga/IdeaProjects/kuugatool/kuugatool-pdf/src/test/resources/templates/hello.ftl";

        // pdf保存路径
        try {
            KuugaPdf kuugaPdf = KuugaPdf.builder()
                    .headerFooterBuilder(new PDFHeaderFooter("页眉1", null, "页脚1"))
                    .templateFile(FileUtil.file(templateFilePath))
                    .templateData(ObjectUtil.toMap(templateBO))
                    .outputFile(FileUtil.file("/Users/kuuga/Downloads/pdf/hello.pdf"))
                    .build();

            kuugaPdf.exportToFile();
        } catch (Exception e) {
            log.error("PDF生成失败{}", ExceptionUtils.getFullStackTrace(e));
        }

    }

}
