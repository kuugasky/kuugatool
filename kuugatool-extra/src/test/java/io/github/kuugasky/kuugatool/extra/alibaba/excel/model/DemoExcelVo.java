package io.github.kuugasky.kuugatool.extra.alibaba.excel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import io.github.kuugasky.kuugatool.extra.alibaba.excel.MultiData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author kuuga
 * @since 2022-01-21 17:07:23
 */
@EqualsAndHashCode(callSuper = true)
@Data
@HeadRowHeight(value = 35)
@NoArgsConstructor
@AllArgsConstructor
public class DemoExcelVo extends MultiData {

    /**
     * value ：如果需要表头，则用数组，每个数组下标[0]相同的则合并单元格
     * order ：排序
     * HeadRowHeight ：表头高度
     * ColumnWidth ：列的宽度
     */
    @ExcelProperty(value = {"学生信息", "id"}, order = 1)
    private String id;

    @ExcelProperty(value = {"学生信息", "名称"}, order = 2)
    private String name;

    @ExcelProperty(value = {"学生信息", "年龄"}, order = 3)
    private int age;

    @ExcelProperty(value = {"年级信息", "年级"}, order = 4)
    private String garde;

    @ExcelProperty(value = {"年级信息", "年级名称"}, order = 5)
    @ColumnWidth(value = 30)
    private String gardeName;

    @NumberFormat("#.##%")
    @ExcelProperty(value = {"分数", "平均分"})
    @ColumnWidth(value = 20)
    private Double doubleData;

    @DateTimeFormat("yyyy年MM月dd日HH时mm分ss秒")
    @ExcelProperty(value = {"日期", "日期"})
    @ColumnWidth(value = 50)
    private Date date;

}