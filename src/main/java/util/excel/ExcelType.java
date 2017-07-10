package util.excel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description excel类型枚举
 * @author linb
 * @date 2017年6月14日 下午4:20:05
 */
public enum ExcelType {

    XLS(1, "2003版Excel-HSSF"), XLSX(2, "2007版Excel-XSSF|SXSSF");
    
    private static final Logger LOG = LoggerFactory.getLogger(ExcelType.class);

    private Integer code;
    private String desc;

    ExcelType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * @Description 根据枚举代码获取枚举结果
     * @param code 枚举代码
     * @return 枚举结果
     */
    public static ExcelType parse(Integer code) {
        ExcelType enumeration = null;
        if (null != code) {
            for (ExcelType e : ExcelType.values()) {
                if (e.getCode().equals(code)) {
                    enumeration = e;
                }
            }
        }
        return enumeration;
    }

    /**
     * @Description 根据枚举代码获取枚举结果
     * @param code 枚举代码
     * @return 枚举结果
     */
    public static ExcelType parse(String code) {
        try {
            return parse(Integer.valueOf(code));
        } catch (Exception e) {
            LOG.debug("", e);
            return null;
        }
    }

    /**
     * @return the code
     */
    public Integer getCode() {
        return code;
    }

    /**
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }
}
