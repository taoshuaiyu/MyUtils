package util.excel;

/**
 * @Description excel导入model父类
 * @author linb
 * @date 2017年6月8日 上午10:29:53
 */
public class ImportModel {
    /**
     * @Description 对应excel的行号
     */
    @ExcelOrder
    private Integer rowNum;

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

}
