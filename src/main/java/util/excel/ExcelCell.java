package util.excel;

/**
 * @Description excel单元格model
 * @author linb
 * @date 2017年6月9日 下午4:07:24
 */
public class ExcelCell {

    /**
     * @Description 行号
     */
    private Integer rowNum;

    /**
     * @Description 列号
     */
    private Integer colNum;

    /**
     * @Description 单元格内容
     */
    private String value;
    
    public ExcelCell() {
        
    }
    
    public ExcelCell(Integer rowNum, Integer colNum, String value) {
        this.rowNum = rowNum;
        this.colNum = colNum;
        this.value = value;
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
     * @return 返回 colNum 属性
     */
    public Integer getColNum() {
        return colNum;
    }

    /**
     * @param colNum 设置 colNum 属性
     */
    public void setColNum(Integer colNum) {
        this.colNum = colNum;
    }

    /**
     * @return 返回 value 属性
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value 设置 value 属性
     */
    public void setValue(String value) {
        this.value = value;
    }
}
