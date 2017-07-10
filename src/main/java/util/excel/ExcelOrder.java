package util.excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description 标识记录excel导入列表行号的字段，要求字段为Integer/int型
 * @author linb
 * @date 2017年6月7日 下午7:29:22
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
public @interface ExcelOrder {

}
