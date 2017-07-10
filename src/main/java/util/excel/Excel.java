package util.excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author tsy Excel注解，用以导入生成Excel表格文件
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
public @interface Excel {

    /**
     * Excel导出应用注解
     * @return
     */
    // 列名
    String name() default "";

    // 宽度
    int width() default 20;

    // 忽略该字段
    boolean skip() default false;

    /**
     * Excel导出应用注解
     * @return
     */
    // 是否可以为空
    boolean nullable() default false;

    // 最大长度
    int maxLength() default 0;

    // 最小长度
    int minLength() default 0;

    // 提供几种常用的正则验证
    RegexType regexType() default RegexType.NONE;
    
    // 正则校验列表，需要多个正则校验时使用
    RegexType[] regexTypeList() default {};

    // 自定义正则验证
    String regexExpression() default "";

    // 参数或者字段描述,这样能够显示友好的异常信息
    String description() default "";

    // 列名
    String value() default "";
    
    // 是否特殊字段
    boolean isSpecial() default false;

    // 字段是否可以多列
    boolean isExistMore() default false;
}
