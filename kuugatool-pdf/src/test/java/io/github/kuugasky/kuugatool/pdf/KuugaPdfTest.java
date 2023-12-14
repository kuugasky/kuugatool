package io.github.kuugasky.kuugatool.pdf;

import io.github.kuugasky.kuugatool.core.collection.ListUtil;
import io.github.kuugasky.kuugatool.core.collection.MapUtil;
import io.github.kuugasky.kuugatool.core.file.FileUtil;
import io.github.kuugasky.kuugatool.core.random.RandomUtil;
import io.github.kuugasky.kuugatool.pdf.component.PDFHeaderFooter;
import io.github.kuugasky.kuugatool.pdf.component.chart.ScatterPlotChart;
import io.github.kuugasky.kuugatool.pdf.component.chart.impl.DefaultLineChart;
import io.github.kuugasky.kuugatool.pdf.component.chart.model.XYLine;
import io.github.kuugasky.kuugatool.pdf.other.ScatterPlotChartTest;
import io.github.kuugasky.kuugatool.pdf.other.TemplateBO;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.github.kuugasky.kuugatool.pdf.other.ReportKit360.getTemperatureLineList;

/**
 * KuugaPdfTest
 *
 * @author kuuga
 * @since 2023/6/12-06-12 16:16
 */
class KuugaPdfTest {

    public static void main(String[] args) {
        String templateFilePath = "/Users/kuuga/IdeaProjects/kuugatool/kuugatool-pdf/src/test/resources/templates/hello.ftl";
        KuugaPdf kuugaPdf = KuugaPdf.builder()
                .outputFile(FileUtil.file("/Users/kuuga/Downloads/pdf/hello.pdf"))
                .templateFile(FileUtil.file(templateFilePath))
                .templateData(packData())
                .build();
        kuugaPdf.exportToFile();
    }

    @Test
    void test() {
        PDFHeaderFooter headerFooter = new PDFHeaderFooter("页眉kuuga", null, "页脚kuuga");

        String outputFile = "/Users/kuuga/Downloads/pdf/kuugaPdf.pdf";
        String templateFilePath = "/Users/kuuga/IdeaProjects/kuugatool/kuugatool-pdf/src/test/resources/templates/hello.ftl";

        File templateFile = FileUtil.file(templateFilePath);
        KuugaPdf build = KuugaPdf.builder()
                .headerFooterBuilder(headerFooter)
                .outputFile(FileUtil.file(outputFile))
                .templateFile(templateFile)
                .templateData(packData())
                .overwriteIfExist(true)
                .build();
        build.exportToFile();
    }

    private static Object packData() {
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
        return templateBO;
    }

    @Test
    void kuuga() {
        PDFHeaderFooter headerFooter = new PDFHeaderFooter("kuuga", null, "Thank you for reading.");

        String outputFile = "/Users/kuuga/Downloads/pdf/kuugaPdf.pdf";
        String templateFilePath = "/Users/kuuga/IdeaProjects/kuugatool/kuugatool-pdf/src/test/resources/templates/kuuga.ftl";

        Map<Object, Object> templateData = MapUtil.newHashMap();
        templateData.put("title", "第一个标题");
        templateData.put("name", "kuuga");
        templateData.put("sex", "男");
        templateData.put("age", "33");
        templateData.put("githubAddress", "https://io.github.kuugasky");
        templateData.put("nextTitle", "第二个标题-爱好列表");

        List<Object> hobbies = ListUtil.newArrayList();
        List<String> hobbiesList = ListUtil.newArrayList("吃", "喝", "玩", "乐");
        for (int i = 0; i < 100; i++) {
            hobbies.add(RandomUtil.randomEle(hobbiesList));
        }
        templateData.put("hobbies", hobbies);

        File templateFile = FileUtil.file(templateFilePath);
        KuugaPdf build = KuugaPdf.builder()
                .headerFooterBuilder(headerFooter)
                .outputFile(FileUtil.file(outputFile))
                .templateFile(templateFile)
                .templateData(templateData)
                // .overwriteIfExist(true)
                .build();
        build.exportToFile();
    }

}
