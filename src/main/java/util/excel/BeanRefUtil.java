package util.excel;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description excel导入导出处理工具类
 * @author tsy
 * @date 2017年6月7日 下午2:52:37
 */
public class BeanRefUtil {

    private static final Logger LOG = LoggerFactory.getLogger(BeanRefUtil.class);

    private static Excel excel;

    /**
     * 取Bean的属性和值对应关系的MAP
     * @param bean 对象
     * @return Map
     */
    public static Map<String, String> getFieldValueMap(Object bean) {
        Class<?> cls = bean.getClass();
        Map<String, String> valueMap = new HashMap<String, String>();
        Method[] methods = cls.getDeclaredMethods();
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            try {
                String fieldType = field.getType().getSimpleName();
                String fieldGetName = parGetName(field.getName());
                if (!checkGetMet(methods, fieldGetName)) {
                    continue;
                }
                Method fieldGetMet = cls.getMethod(fieldGetName, new Class[] {});
                Object fieldVal = fieldGetMet.invoke(bean, new Object[] {});
                String result = null;
                if ("Date".equals(fieldType)) {
                    result = fmtDate((Date) fieldVal);
                } else {
                    if (null != fieldVal) {
                        result = String.valueOf(fieldVal);
                    }
                }
                // String fieldKeyName = parKeyName(field.getName());
                valueMap.put(field.getName(), result);
            } catch (Exception e) {
                LOG.error(e.getMessage(), e);
                continue;
            }
        }
        return valueMap;
    }

    /**
     * set属性的值到Bean
     * @param valMap
     */
    public static List<ExcelException> setFieldValue(int rowNum, Object model,
                                                     Map<String, String> valMap, Map<String, Integer> colMap,
                                                     Map<String, Integer> specialRowMap) {
        Class<?> clazz = model.getClass();
        // 取出bean里的所有方法
        List<Field> fields = ReflectUtils.getFileds(clazz);

        List<ExcelException> exceptionList = new ArrayList<>();

        for (Field field : fields) {
            try {
                String fieldName = field.getName();
                Integer specialRowNum = specialRowMap.get(fieldName);
                String value = valMap.get(fieldName);
                Integer colNum = colMap.get(fieldName);
                List<ExcelException> exceptions = validate(
                        null == specialRowNum ? rowNum : specialRowNum, colNum, field, value);
                if (CollectionUtils.isNotEmpty(exceptions)) {
                    exceptionList.addAll(exceptions);
                    continue;
                }
                if (StringUtils.isNotBlank(value)) {
                    field.setAccessible(true); // 使得可操作private
                    Class<?> fieldType = field.getType();
                    if (String.class.equals(fieldType)) {
                        field.set(model, value);
                    } else if (Date.class.equals(fieldType)) {
                        Date temp = parseDate(value);
                        field.set(model, temp);
                    } else if (Integer.class.equals(fieldType) || int.class.equals(fieldType)) {
                        Integer intval = Integer.valueOf(value);
                        field.set(model, intval);
                    } else if (Long.class.equals(fieldType) || long.class.equals(fieldType)) {
                        Long temp = Long.parseLong(value);
                        field.set(model, temp);
                    } else if (Double.class.equals(fieldType) || double.class.equals(fieldType)) {
                        Double temp = Double.parseDouble(value);
                        field.set(model, temp);
                    } else if (Boolean.class.equals(fieldType) || boolean.class.equals(fieldType)) {
                        Boolean temp = Boolean.parseBoolean(value);
                        field.set(model, temp);
                    } else {
                        LOG.error("------------------ fieldType not exists : " + fieldType);
                    }
                }
            } catch (Exception e) {
                LOG.error("", e);
                exceptionList.add(new ExcelException(rowNum, null, e.getMessage()));
            }
        }
        return exceptionList;
    }

    /**
     * 格式化string为Date
     * @param datestr
     * @return date
     */
    public static Date parseDate(String datestr) {
        if (null == datestr || "".equals(datestr)) {
            return null;
        }
        try {
            String fmtstr = null;
            if (datestr.indexOf(':') > 0) {
                fmtstr = "yyyy.MM.dd HH:mm";
            } else {
                fmtstr = "yyyy.MM.dd";
            }
            SimpleDateFormat sdf = new SimpleDateFormat(fmtstr);
            sdf.setLenient(false);
            return sdf.parse(datestr);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 日期转化为String
     * @param date
     * @return date string
     */
    public static String fmtDate(Date date) {
        if (null == date) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
            return sdf.format(date);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 判断是否存在某属性的 set方法
     * @param methods
     * @param fieldSetMet
     * @return boolean
     */
    public static boolean checkSetMet(Method[] methods, String fieldSetMet) {
        for (Method met : methods) {
            if (fieldSetMet.equals(met.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否存在某属性的 get方法
     * @param methods
     * @param fieldGetMet
     * @return boolean
     */
    public static boolean checkGetMet(Method[] methods, String fieldGetMet) {
        for (Method met : methods) {
            if (fieldGetMet.equals(met.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 拼接某属性的 get方法
     * @param fieldName
     * @return String
     */
    public static String parGetName(String fieldName) {
        if (null == fieldName || "".equals(fieldName)) {
            return null;
        }
        int startIndex = 0;
        if (fieldName.charAt(0) == '_')
            startIndex = 1;
        return "get" + fieldName.substring(startIndex, startIndex + 1).toUpperCase()
                + fieldName.substring(startIndex + 1);
    }

    /**
     * @Description 校验字符是否符合字段的条件
     * @author linb 2017年6月7日 下午3:24:02
     * @param rowNum 字段所在行
     * @param colNum 字段所在列
     * @param field 字段
     * @param value 待导入值
     * @return 检测异常列，正常时为空列表
     */
    public static List<ExcelException> validate(Integer rowNum, Integer colNum, Field field,
                                                Object value) {

        List<ExcelException> exceptionList = new ArrayList<>();
        // 获取对象的成员的注解信息
        excel = field.getAnnotation(Excel.class);
        if (excel == null) {
            return exceptionList;
        }
        String desc = "".equals(excel.description()) ? excel.value() : excel.description();

        /************* 注解解析工作开始 ******************/
        if (value == null || StringUtils.isBlank(value.toString())) {
            if (excel.nullable()) {
                // 可以为空，则直接返回
                return exceptionList;
            } else {
                exceptionList.add(new ExcelException(rowNum, colNum, desc + "不能为空"));
                return exceptionList;
            }
        }

        if (value.toString().length() > excel.maxLength() && excel.maxLength() != 0) {
            exceptionList
                    .add(new ExcelException(rowNum, colNum, desc + "长度不能超过" + excel.maxLength()));
        }
        if (value.toString().length() < excel.minLength() && excel.minLength() != 0) {
            exceptionList
                    .add(new ExcelException(rowNum, colNum, desc + "长度不能小于" + excel.minLength()));
        }

        List<RegexType> regexTypes = new ArrayList<>();
        // 单条正则
        regexTypes.add(excel.regexType());
        // 多条正则
        if (null != excel.regexTypeList() && excel.regexTypeList().length > 0) {
            for (int i = 0; i < excel.regexTypeList().length; i++) {
                regexTypes.add(excel.regexTypeList()[i]);
            }
        }
        // 遍历正则校验
        for (RegexType regexType : regexTypes) {
            switch (regexType) {
                case NONE:
                    break;
                case SPECIALCHAR:
                    if (RegexUtils.hasSpecialChar(value.toString())) {
                        exceptionList.add(new ExcelException(rowNum, colNum, desc + "不能含有特殊字符"));
                    }
                    break;
                case CHINESE:
                    if (RegexUtils.isChinese2(value.toString())) {
                        exceptionList.add(new ExcelException(rowNum, colNum, desc + "不能含有中文字符"));
                    }
                    break;
                case EMAIL:
                    if (!RegexUtils.isEmail(value.toString())) {
                        exceptionList.add(new ExcelException(rowNum, colNum, desc + "地址格式不正确"));
                    }
                    break;
                case IP:
                    if (!RegexUtils.isIp(value.toString())) {
                        exceptionList.add(new ExcelException(rowNum, colNum, desc + "IP地址格式不正确"));
                    }
                    break;
                case NUMBER:
                    if (!RegexUtils.isNumber(value.toString())) {
                        exceptionList.add(new ExcelException(rowNum, colNum, desc + "不是数字"));
                    }
                    break;
                case PHONENUMBER:
                    if (!RegexUtils.isPhoneNumber(value.toString())) {
                        exceptionList.add(new ExcelException(rowNum, colNum, desc + "不是正规手机号"));
                    }
                    break;
                case DATE1:
                    if (!RegexUtils.isValidDate(value.toString(), "yyyy.MM.dd")) {
                        exceptionList.add(new ExcelException(rowNum, colNum, desc + "不是正规日期格式"));
                    }
                    break;
                case DATE2:
                    if (!RegexUtils.isValidDate(value.toString(), "yyyy.MM.dd HH:mm")) {
                        exceptionList.add(new ExcelException(rowNum, colNum, desc + "不是正规日期格式 "));
                    }
                    break;
//                case IDENTITYCARD:
//                    if (!RegexUtils.isIdentifyCard(value.toString())) {
//                        exceptionList.add(new ExcelException(rowNum, colNum, desc + "不是正规身份证格式"));
//                    }
//                    break;
                case DOUBLE:
                    if (!RegexUtils.isDouble(value.toString())) {
                        exceptionList.add(new ExcelException(rowNum, colNum, desc + "不是浮点格式"));
                    }
                    break;
                default:
                    break;
            }
        }

        if (StringUtils.isNotBlank(excel.regexExpression())) {
            if (!value.toString().matches(excel.regexExpression())) {
                exceptionList.add(new ExcelException(rowNum, colNum, desc + "格式不正确"));
            }
        }
        return exceptionList;
        /************* 注解解析工作结束 ******************/
    }

    /**
     * @Description 获取excel导入的记录列表中正常的部分
     * @author linb 2017年6月7日 下午7:00:21
     * @param list 导入的记录列表
     * @param exceptionList excel异常列表
     * @param <T> 泛型
     * @return 正常的记录列表
     */
    public static <T extends ImportModel> List<T> getNormalList(List<T> list,
                                                                List<ExcelException> exceptionList) {
        List<T> normalList = new ArrayList<>();
        if (CollectionUtils.isEmpty(list)) {
            return normalList;
        }
        if (CollectionUtils.isEmpty(exceptionList)) {
            return list;
        }
        // 获取错误行的映射
        Map<Integer, ExcelException> rowMap = new HashMap<>();
        for (ExcelException item : exceptionList) {
            rowMap.put(item.getRowNum(), item);
        }
        // 获取正常的列表
        list.forEach(item -> {
            if (null == rowMap.get(item.getRowNum())) {
                normalList.add(item);
            }
        });
        return normalList;
    }
}