package util.excel;

import java.io.Serializable;

/**
 * @Description excel导入异常类
 * @author linb
 * @date 2017年6月7日 下午2:22:45
 */
public class ExcelException implements Serializable {

    /**
     * @Description 序列化id
     */
    private static final long serialVersionUID = 1L;

    /**
     * @Description 异常所在行号
     */
    private Integer rowNum;

    /**
     * @Description 异常所在列号
     */
    private Integer colNum;

    /**
     * @Description 异常描述
     */
    private String desc;

    public ExcelException() {

    }
    
    public ExcelException(String desc) {
        this.desc = desc;
    }
    
    public ExcelException(Integer rowNum, String desc) {
        this.rowNum = rowNum;
        this.desc = desc;
    }

    public ExcelException(Integer rowNum, Integer colNum, String desc) {
        this.rowNum = rowNum;
        this.colNum = colNum;
        this.desc = desc;
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
     * @return 返回 desc 属性
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @param desc 设置 desc 属性
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        String str = "";
        if (null != this.rowNum) {
            str += "第" + this.rowNum + "行";
        }
        if (null != this.colNum) {
            str += "第" + this.colNum + "列";
        }
        str += "--" + this.desc;
        return str;
    }
}
