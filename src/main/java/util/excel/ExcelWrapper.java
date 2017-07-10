package util.excel;
import java.util.List;
import java.util.Map;

/**
 * @Description Excel导入数据封装
 * @author tsy 2017年5月27日 上午10:48:23
 * @param <T>
 */
public class ExcelWrapper<T> {

    /**
     * @Description 异常列表
     */
    private List<ExcelException> exceptionList;

    /**
     * @Description 导入的记录列表
     */
    private List<T> list;

    /**
     * @Description 记录类的字段名到excel对应列号的映射
     */
    private Map<String, Integer> colMap;

    /**
     * @Description 记录类的特殊字段名到excel对应列号的映射
     */
    private Map<String, Integer> specialRowMap;

    /**
     * @return 返回 exceptionList 属性
     */
    public List<ExcelException> getExceptionList() {
        return exceptionList;
    }

    /**
     * @param exceptionList 设置 exceptionList 属性
     */
    public void setExceptionList(List<ExcelException> exceptionList) {
        this.exceptionList = exceptionList;
    }

    /**
     * @return 返回 list 属性
     */
    public List<T> getList() {
        return list;
    }

    /**
     * @param list 设置 list 属性
     */
    public void setList(List<T> list) {
        this.list = list;
    }

    /**
     * @return 返回 colMap 属性
     */
    public Map<String, Integer> getColMap() {
        return colMap;
    }

    /**
     * @param colMap 设置 colMap 属性
     */
    public void setColMap(Map<String, Integer> colMap) {
        this.colMap = colMap;
    }

    public Map<String, Integer> getSpecialRowMap() {
        return specialRowMap;
    }

    public void setSpecialRowMap(Map<String, Integer> specialRowMap) {
        this.specialRowMap = specialRowMap;
    }
}
