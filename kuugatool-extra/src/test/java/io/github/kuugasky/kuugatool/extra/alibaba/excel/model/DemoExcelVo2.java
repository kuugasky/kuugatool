package io.github.kuugasky.kuugatool.extra.alibaba.excel.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import io.github.kuugasky.kuugatool.extra.alibaba.excel.MultiData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * DemoExcelVo2
 *
 * @author kuuga
 * @since 2022-01-21 17:10:16
 */
@EqualsAndHashCode(callSuper = true)
@Data
@HeadRowHeight(value = 35)
@NoArgsConstructor
@AllArgsConstructor
public class DemoExcelVo2 extends MultiData {

    /**
     * value ：如果需要表头，则用数组，每个数组下标[0]相同的则合并单元格
     * order ：排序
     * HeadRowHeight ：表头高度
     * ColumnWidth ：列的宽度
     */
    @ExcelProperty(value = {"学生信息", "id"}, order = 1)
    private String id;

    @ExcelProperty(value = {"学生信息", "名称"}, order = 2)
    @ColumnWidth(value = 20)
    private String name;

}