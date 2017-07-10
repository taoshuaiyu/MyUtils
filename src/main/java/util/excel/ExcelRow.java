package util.excel;

import java.util.List;

/**
 * @Description excel导入行model
 * @author linb
 * @date 2017年6月12日 上午9:30:46
 */
public class ExcelRow {

    /**
     * @Description 行号
     */
    private Integer rowNum;

    /**
     * @Description 该行单元格列表
     */
    private List<ExcelCell> excelCells;
    
    public ExcelRow() {
        
    }
    
    public ExcelRow(Integer rowNum, List<ExcelCell> excelCells) {
        this.rowNum = rowNum;
        this.excelCells = excelCells;
    }

    /**
     * @return 返回 rowNum 属性
     */
    public Integer getRowNum() {
        return rowNum;
    }

    /**
     * @param rowNum 设置 rowNum 属性
     */
    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    /**
     * @return 返回 excelCells 属性
     */
    public List<ExcelCell> getExcelCells() {
        return excelCells;
    }

    /**
     * @param excelCells 设置 excelCells 属性
     */
    public void setExcelCells(List<ExcelCell> excelCells) {
        this.excelCells = excelCells;
    }

}
