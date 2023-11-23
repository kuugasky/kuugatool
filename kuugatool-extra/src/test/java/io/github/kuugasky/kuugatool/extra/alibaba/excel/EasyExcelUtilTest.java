package io.github.kuugasky.kuugatool.extra.alibaba.excel;

import io.github.kuugasky.kuugatool.extra.alibaba.excel.model.DemoExcelVo;
import io.github.kuugasky.kuugatool.extra.alibaba.excel.model.DemoExcelVo2;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

class EasyExcelUtilTest {

    /**
     * 写入本地文件
     *
     * @throws Exception Exception
     */
    @Test
    void write() throws Exception {
        List<DemoExcelVo2> demoExcelVos2 = new ArrayList<>();
        Long startTime = System.currentTimeMillis();

        int size = 2000;
        List<DemoExcelVo> demoExcelVos = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            DemoExcelVo demoExcelVo = new DemoExcelVo();
            demoExcelVo.setId(String.valueOf(i));
            demoExcelVo.setName("name:" + i);
            demoExcelVo.setAge(i);
            demoExcelVo.setGarde("garde:" + i);
            demoExcelVo.setGardeName("gardeName:" + i);
            demoExcelVo.setDoubleData(0.6369D);
            demoExcelVo.setDate(new Date());
            demoExcelVos.add(demoExcelVo);
        }

        MultiSheet multiSheet = new MultiSheet();
        multiSheet.setNeedStyle(true);
        multiSheet.setHeadColor(IndexedColors.LIGHT_BLUE);
        multiSheet.setContentColor(IndexedColors.WHITE1);
        multiSheet.setSheetName("sheet1");
        multiSheet.setDataList(demoExcelVos);

        for (int i = 0; i < size; i++) {
            DemoExcelVo2 demoExcelVo2 = new DemoExcelVo2();
            demoExcelVo2.setId(String.valueOf(i));
            demoExcelVo2.setName("name:" + i);
            demoExcelVos2.add(demoExcelVo2);
        }

        MultiSheet multiSheet2 = new MultiSheet();
        multiSheet2.setNeedStyle(true);
        multiSheet2.setHeadColor(IndexedColors.RED);
        multiSheet2.setContentColor(IndexedColors.GREEN);
        multiSheet2.setSheetName("sheet2");
        multiSheet2.setDataList(demoExcelVos2);

        List<MultiSheet> multiSheetList = new ArrayList<>();
        multiSheetList.add(multiSheet);
        multiSheetList.add(multiSheet2);
        EasyExcelUtil.writeExcelMultiSheetByFilePath("/Users/kuuga/Downloads/demo1.xlsx", multiSheetList);
        Long endTime = System.currentTimeMillis();
        System.out.println("100万数据写Excel花费时间毫秒：" + (endTime - startTime));
    }

    /**
     * 读取本地文件
     *
     * @throws Exception Exception
     */
    @Test
    void read() throws Exception {
        Long startTime1 = System.currentTimeMillis();
        List<DemoExcelVo> ts = EasyExcelUtil.readExcelByPath("/Users/kuuga/Downloads/demo1.xlsx", DemoExcelVo.class);
        System.out.println(Objects.requireNonNull(ts).size());
        Long endTime1 = System.currentTimeMillis();
        System.out.println("100万数据读Excel花费时间毫秒：" + (endTime1 - startTime1));
    }

    @Test
    void writeExcelByFilePath() throws Exception {
        int size = 2000;
        List<DemoExcelVo> demoExcelVos = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            DemoExcelVo demoExcelVo = new DemoExcelVo();
            demoExcelVo.setId(String.valueOf(i));
            demoExcelVo.setName("name:" + i);
            demoExcelVo.setAge(i);
            demoExcelVo.setGarde("garde:" + i);
            demoExcelVo.setGardeName("gardeName:" + i);
            demoExcelVo.setDoubleData(0.6369D);
            demoExcelVo.setDate(new Date());
            demoExcelVos.add(demoExcelVo);
        }
        EasyExcelUtil.writeExcelByFilePath("/Users/kuuga/Downloads/demo2.xlsx", demoExcelVos);
    }

}