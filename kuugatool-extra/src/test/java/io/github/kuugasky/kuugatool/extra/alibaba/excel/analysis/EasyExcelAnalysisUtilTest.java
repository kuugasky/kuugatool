package io.github.kuugasky.kuugatool.extra.alibaba.excel.analysis;

import com.alibaba.excel.annotation.ExcelProperty;
import io.github.kuugasky.kuugatool.extra.file.MultipartFileUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

class EasyExcelAnalysisUtilTest {

    @Test
    void read() throws IOException {
        MultipartFile multipartFile = MultipartFileUtil.fileToMultipartFile(new File("/Users/kuuga/Downloads/资料盘导入模板【1】无单元（2000）.xlsx"));
        EasyExcelListener<HouseImportDtoEasyExcel> read = EasyExcelAnalysisUtil.read(multipartFile, HouseImportDtoEasyExcel.class, 2000, 200);
        AtomicInteger atomicInteger = new AtomicInteger(0);
        read.foreach(list -> list.forEach(x -> {
            atomicInteger.incrementAndGet();
            System.out.printf("%s-%s-%s-%s-%s%n", x.getGardenName(), x.buildingName, x.unitName, x.floor, x.roomNo);
        }));
        System.out.println(atomicInteger.get());
    }

    @Test
    void testRead() throws IOException {
        File file = new File("/Users/kuuga/Downloads/资料盘导入模板【1】无单元（2000）.xlsx");
        EasyExcelListener<HouseImportDtoEasyExcel> easyExcelEasyExcelListener = EasyExcelAnalysisUtil.read(file, HouseImportDtoEasyExcel.class, 2000, 200);
        AtomicInteger atomicInteger = new AtomicInteger(0);
        easyExcelEasyExcelListener.foreach(list -> list.forEach(x -> {
            atomicInteger.incrementAndGet();
            System.out.printf("%s-%s-%s-%s-%s%n", x.getGardenName(), x.buildingName, x.unitName, x.floor, x.roomNo);
        }));
        System.out.println(atomicInteger.get());
        System.out.println(easyExcelEasyExcelListener.getTotalCount());
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    @NoArgsConstructor
    public static class HouseImportDtoEasyExcel extends EasyExcelAnalysisDto {

        @ExcelProperty(value = "楼盘名称")
        private String gardenName;

        @ExcelProperty(value = "楼栋名称")
        private String buildingName;

        @ExcelProperty(value = "单元名称")
        private String unitName;

        @ExcelProperty(value = "楼层序号")
        private String floor;

        @ExcelProperty(value = "房号")
        private String roomNo;

        @ExcelProperty(value = "联系方式")
        private String ownerInfo;

    }

}