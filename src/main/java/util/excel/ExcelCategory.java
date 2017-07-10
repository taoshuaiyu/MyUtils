package util.excel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @Description excel类别枚举
 * @author linb
 * @date 2017年6月8日 下午5:38:11
 */
public enum ExcelCategory {

    SKILL_GRADE_IMPORT(1, "技能考试成绩导入表", null),
    THEORY_GRADE_IMPORT(2, "理论考试成绩导入表", null),
    OUT_FACULTY_GRADE_IMPORT(3, "出科考试成绩导入表", null),
    OUT_FACULTY_GRADE_EXPORT(4, "出科考试成绩导出表", "出科考试成绩模板.xls"),
    STUDENT_ROTATE_EXPORT(5, "学员轮转情况导出表", "学员轮转情况模板.xls"),
    ROTATE_PLAN_IMPORT(6, "轮转计划信息导入表", null),
    ROTATE_PLAN_EXPORT(7, "轮转计划信息导出表", "轮转计划信息模板.xls"),
    TEACHING_IMPORT(8, "教学活动导入表", null),
    TEACHING_EXPORT(9, "教学活动导出表", "教学活动模板.xls"),
    TESTS_IMPORT(10, "考试信息导入表", null),
    TESTS_EXPORT(11, "考试信息导出表", "考试信息模板.xls"),
    ROTATE_REQUIRE_IMPORT(12, "轮转要求导入表", null),
    STUDENT_IMPORT(13, "规培学员导入表", null),
     STUDENT_EXPORT(14, "规培学员导出表", "规培学员模板.xls"),
    TEACHER_IMPORT(15, "规培带教老师导入表", null),
    TEACHER_EXPORT(16, "规培带教老师导出表", "规培带教老师模板.xls"),
    ADMIN_IMPORT(17, "规培管理员导入表", null),
    ADMIN_EXPORT(18, "规培管理员导出表", "规培管理员模板.xls"),
    ROTATE_FACULTY_EXPORT(19, "轮转科室导出表", "轮转科室模板.xls"),
    BASE_IMPORT(20, "规培专业基地导入表", null);
    
    private static final Logger LOG = LoggerFactory.getLogger(ExcelCategory.class);

    private Integer code;
    private String desc;
    private String template;

    ExcelCategory(Integer code, String desc, String template) {
        this.code = code;
        this.desc = desc;
        this.template = template;
    }

    /**
     * @Description 根据枚举代码获取枚举结果
     * @param code 枚举代码
     * @return 枚举结果
     */
    public static ExcelCategory parse(Integer code) {
        ExcelCategory enumeration = null;
        if (null != code) {
            for (ExcelCategory e : ExcelCategory.values()) {
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
    public static ExcelCategory parse(String code) {
        try {
            return parse(Integer.valueOf(code));
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
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

    /**
     * @return 返回 template 属性
     */
    public String getTemplate() {
        return template;
    }

    /**
     * @Description 获取redis键值前缀
     * @author linb 2017年6月8日 下午5:43:19
     * @return 键值前缀
     */
//    public String getPrefix() {
//        return Constants.REDIS_EXCEL_PREFIX + this.code + "_";
//    }
}
