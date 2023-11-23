package io.github.kuugasky.kuugatool.extra.alibaba.excel;

import lombok.Data;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.util.List;

/**
 * MultiSheet
 *
 * @author kuuga
 * @since 2022-01-21 17:16:48
 */
@Data
public class MultiSheet {

    /**
     * whether set style
     */
    private boolean needStyle;
    /**
     * head background color
     */
    private IndexedColors headColor = IndexedColors.RED;
    /**
     * conte background color
     */
    private IndexedColors contentColor = IndexedColors.GREEN;
    /**
     * sheet name
     */
    private String sheetName;
    /**
     * data
     */
    private List<? extends MultiData> dataList;

}
