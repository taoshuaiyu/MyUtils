package util.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.sl.usermodel.ShapeType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.ClientAnchor.AnchorType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFSimpleShape;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.exception.BizRuntimeException;


/**
 * @Description Excel导入工具类
 * @author tsy
 * @date 2017年5月18日 下午4:31:44
 */
public final class ExcelIO {

    private static final Logger LOG = LoggerFactory.getLogger(ExcelIO.class);

    private ExcelIO() {

    }

    /**
     * @Description Excel转实体+数据校验返回Exception集合
     * @author tsy 2017年5月18日 下午4:48:23
     * @param <T> model
     * @param filePath 文件路径
     * @param clazz model
     * @return Excel封装类
     * @throws IOException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static <T> ExcelWrapper<T> excelToClass(String filePath, Class<T> clazz)
            throws IOException, InstantiationException, IllegalAccessException,
            BizRuntimeException {
        /**
         * 注意点：由于判断是否为字段时，是通过列表（或特殊）标题名称是否包含（而非等值）@Excel中的name值，
         * 若内容也包含name值，也将会被程序判断为标题名称；因而限制列表标题行和特殊行仅有一行， 从而减少内容与标题名称相同（或类似）带来的影响
         **/
        Long startTime = System.currentTimeMillis(); // 耗时计算
        ExcelWrapper<T> excelWrapper = new ExcelWrapper<>();
        List<ExcelException> exceptionList = new ArrayList<>();
        List<T> list = new ArrayList<T>();
        FileInputStream fis = null;
        Workbook workbook = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new BizRuntimeException("文件不存在");
            }
            // 读取excel文件
            fis = new FileInputStream(file);
            try {
                workbook = new HSSFWorkbook(fis);
            } catch (Exception e) {
                LOG.debug("", e);
                // 重新打开文件流
                fis = new FileInputStream(file);
                workbook = new XSSFWorkbook(fis);
            }
            Sheet sheet = workbook.getSheetAt(0);
            // 标题行字段名列表
            List<String> keys = new ArrayList<String>();
            // 字段名到列号的映射
            Map<String, Integer> colMap = new HashMap<>();
            // 特殊字段名对行号的映射
            Map<String, Integer> specialRowMap = new HashMap<>();
            // 获取类的所有字段（含继承字段）
            List<Field> fields = ReflectUtils.getFileds(clazz);

            // 获取记录行号的字段，特殊字段列表，正常字段列表
            List<Field> specialFields = new ArrayList<>();
            List<Field> normalfields = new ArrayList<>();
            Field orderField = null;
            for (Field field : fields) {
                if (field.isAnnotationPresent(ExcelOrder.class)
                        && (Integer.class.equals(field.getType())
                        || int.class.equals(field.getType()))) {
                    orderField = field;
                    continue;
                }
                if (field.isAnnotationPresent(Excel.class)) {
                    if (field.getAnnotation(Excel.class).isSpecial()) {
                        specialFields.add(field);
                    } else {
                        normalfields.add(field);
                    }
                }
            }
            if (null != orderField) {
                orderField.setAccessible(true);
            }
            // 列对应字段名到该行的值的映射
            Map<String, String> valMap = new HashMap<String, String>();

            // 获取特殊化字段数据（横向且值只有一个）
            Iterator<Row> rows = sheet.rowIterator();
            if (!specialFields.isEmpty()) {
                int specialRowNum = -1; // 特殊行号，限定特殊行仅有一行
                while (rows.hasNext()) {
                    Row row = rows.next();
                    // 存在特殊字段，且未找到特殊字段行
                    int colNums = row.getLastCellNum();
                    for (int i = 0; i < colNums; i++) {
                        Cell cell = row.getCell(i);
                        String value = getStringCellValue(cell);
                        for (Field field : specialFields) {
                            if (value.contains(field.getAnnotation(Excel.class).value())) {
                                // 相邻行为其值
                                valMap.put(field.getName(), getStringCellValue(row.getCell(i + 1)));
                                colMap.put(field.getName(), i);
                                specialRowMap.put(field.getName(), row.getRowNum());
                                specialRowNum = row.getRowNum();
                                i++; // 跳过其值单元格
                                break;
                            }
                        }
                    }
                    if (-1 != specialRowNum) {
                        break;
                    }
                }
            }

            // 获取excel表中的列表数据
            rows = sheet.iterator();
            int titleNum = -1; // 定位标题行
            while (rows.hasNext()) {
                Row row = rows.next();
                // 获取标题行
                if (-1 == titleNum) {
                    int colNums = row.getLastCellNum();
                    keys = new ArrayList<>(); // 清空上一行的数据
                    for (int i = 0; i < colNums; i++) {
                        Cell cell = row.getCell(i);
                        String value = getStringCellValue(cell);
                        String fieldName = null;
                        for (Field field : normalfields) {
                            if (field.isAnnotationPresent(Excel.class)
                                    && value.contains(field.getAnnotation(Excel.class).value())) {
                                fieldName = field.getName();
                                // 遍历到标题行
                                titleNum = row.getRowNum();
                                break;
                            }
                        }
                        keys.add(fieldName);
                        colMap.put(fieldName, i);

                    }

                } else {
                    // 正常字段赋值
                    boolean isBlankLine = true;
                    for (int i = 0; i < keys.size(); i++) {
                        Cell cell = row.getCell(i);
                        String value = getStringCellValue(cell);
                        if (null != value && value.trim() != "") {
                            isBlankLine = false;
                        }
                        valMap.put(keys.get(i), value);
                    }
                    if (isBlankLine) {
                        // 空白行，跳过
                        continue;
                    }
                    // 初始化对象
                    T model = clazz.newInstance();
                    // 记录行号
                    if (null != orderField) {
                        orderField.set(model, row.getRowNum());
                    }
                    // 导入数据到model，并校验要求
                    List<ExcelException> exceptions = BeanRefUtil.setFieldValue(row.getRowNum(),
                            model, valMap, colMap, specialRowMap);
                    list.add(model);
                    exceptionList.addAll(exceptions);
                }
            }
            excelWrapper.setExceptionList(exceptionList);
            excelWrapper.setList(list);
            excelWrapper.setColMap(colMap);
            excelWrapper.setSpecialRowMap(specialRowMap);
        } catch (InstantiationException | IllegalAccessException | BizRuntimeException
                | IOException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } finally {
            if (null != fis) {
                fis.close();
            }
            if (null != workbook) {
                workbook.close();
            }
        }
        LOG.warn("------------excelToClass costTime : " + (System.currentTimeMillis() - startTime));
        return excelWrapper;
    }

    /**
     * @Description 获取单元格数据内容为字符串类型的数据
     * @param cell 单元格
     * @return 字符串类型的数据
     */
    public static String getStringCellValue(Cell cell) {
        String strCell = "";
        if (null != cell) {
            CellType cellType = cell.getCellTypeEnum();
            if (CellType.STRING.equals(cellType)) {
                strCell = cell.getStringCellValue();
            } else if (CellType.NUMERIC.equals(cellType)) {
                if (DateUtil.isCellDateFormatted(cell)) {
                    Date date = cell.getDateCellValue();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    strCell = df.format(date);
                } else {
                    strCell = NumberToTextConverter.toText(cell.getNumericCellValue());
                }
            } else if (CellType.BOOLEAN.equals(cellType)) {
                strCell = String.valueOf(cell.getBooleanCellValue());
            } else if (CellType.BLANK.equals(cellType)) {
                strCell = "";
            } else {
                strCell = "";
            }
        }
        if ("".equals(strCell) || strCell == null) {
            return "";
        }
        // 忽略两边空格
        strCell = strCell.trim();
        return strCell;
    }

    /**
     * @Description 将excel格式不正确的单元格标红
     * @author linb 2017年6月8日 下午2:52:39
     * @param filePath excel文件
     * @param exceptionList 异常列表
     * @throws IOException
     * @throws BizRuntimeException
     */
    public static void markException(String filePath, List<ExcelException> exceptionList)
            throws IOException, BizRuntimeException {
        if (null == exceptionList || exceptionList.isEmpty()) {
            return;
        }
        FileInputStream fis = null;
        FileOutputStream fos = null;
        Workbook workbook = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new BizRuntimeException("文件不存在");
            }
            // 读取excel文件
            fis = new FileInputStream(file);
            try {
                workbook = new HSSFWorkbook(fis);
            } catch (Exception e) {
                LOG.debug("", e);
                // 重新打开文件流
                fis = new FileInputStream(file);
                workbook = new XSSFWorkbook(fis);
            }

            // 定义异常单元格前景色
            Map<String, Object> properties = new HashMap<>();
            properties.put(CellUtil.FILL_FOREGROUND_COLOR, IndexedColors.RED.index);
            properties.put(CellUtil.FILL_PATTERN, FillPatternType.SOLID_FOREGROUND);

            // 标识异常单元格
            Sheet sheet = workbook.getSheetAt(0);
            for (ExcelException item : exceptionList) {
                if (null == item.getRowNum() || null == item.getColNum()) {
                    continue;
                }
                Row row = sheet.getRow(item.getRowNum());
                Cell cell = row.getCell(item.getColNum());
                if (null == cell) {
                    cell = row.createCell(item.getColNum());
                }
                // 改变样式，避免影响其他单元格样式，使用CellUtil
                CellUtil.setCellStyleProperties(cell, properties);
            }
            // 输出excel
            fos = new FileOutputStream(file);
            workbook.write(fos);
        } catch (IOException | BizRuntimeException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } finally {
            if (null != fis) {
                fis.close();
            }
            if (null != workbook) {
                workbook.close();
            }
            if (null != fos) {
                fos.close();
            }
        }
    }

    /**
     * @Description 将excel转换成excel行数组
     * @author linb 2017年6月9日 下午4:09:35
     * @param filePath excel文件保存位置
     * @return excel单元格数组
     * @throws BizRuntimeException 异常
     * @throws IOException
     */
    public static List<ExcelRow> excelToList(String filePath)
            throws BizRuntimeException, IOException {
        List<ExcelRow> list = new ArrayList<>();
        FileInputStream fis = null;
        Workbook workbook = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new BizRuntimeException("文件不存在");
            }
            // 读取excel文件
            fis = new FileInputStream(file);
            try {
                workbook = new HSSFWorkbook(fis);
            } catch (Exception e) {
                LOG.debug(e.getMessage(), e);
                // 重新打开文件流
                fis = new FileInputStream(file);
                workbook = new XSSFWorkbook(fis);
            }
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.rowIterator();
            while (rows.hasNext()) {
                Row row = rows.next();
                List<ExcelCell> excelCells = new ArrayList<>();
                for (int i = 0; i < row.getLastCellNum(); i++) {
                    Cell cell = row.getCell(i);
                    if (null == cell) {
                        continue;
                    }
                    String value = getStringCellValue(cell);
                    if (null != value && !"".equals(value.trim())) {
                        excelCells.add(
                                new ExcelCell(cell.getRowIndex(), cell.getColumnIndex(), value));
                    }
                }
                if (!excelCells.isEmpty()) {
                    list.add(new ExcelRow(row.getRowNum(), excelCells));
                }
            }
        } catch (BizRuntimeException | IOException e) {
            LOG.error(e.getMessage(), e);
            throw e;
        } finally {
            if (null != fis) {
                fis.close();
            }
            if (null != workbook) {
                workbook.close();
            }
        }
        return list;
    }

    /**
     * @Description 根据模板导出excel
     * @author linb 2017年6月13日 下午5:57:55
     * @param response 导出excel流
     * @param fileName 导出文件名s
     * @param templatePath 模板文件地址
     * @param list 导出数据
     * @param clazz 导出数据类型
     * @param <T> 泛型
     * @throws IOException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    public static <T> void exportByTemplate(HttpServletResponse response, String fileName,
                                            String templatePath, List<T> list, Class<T> clazz)
            throws IOException, IllegalArgumentException, IllegalAccessException {
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            response.setContentType("octets/stream");
            response.addHeader("Content-Disposition",
                    "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xls");
            exportByTemplate(templatePath, os, list, clazz);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            LOG.error("", e);
            throw e;
        } finally {
            if (null != os) {
                os.close();
            }
        }
    }

    /**
     * @Description 根据模板导出excel
     * @author linb 2017年6月13日 下午5:57:55
     * @param templatePath 模板文件地址
     * @param filePath 导出excel文件地址
     * @param list 导出数据
     * @param clazz 导出数据类型
     * @param <T> 泛型
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws BizRuntimeException
     * @throws IOException
     */
    public static <T> void exportByTemplate(String templatePath, String filePath, List<T> list,
                                            Class<T> clazz) throws BizRuntimeException, IllegalArgumentException,
            IllegalAccessException, IOException {
        FileOutputStream fos = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            exportByTemplate(templatePath, fos, list, clazz);
        } catch (BizRuntimeException | IllegalArgumentException | IllegalAccessException
                | IOException e) {
            LOG.error("", e);
            throw e;
        } finally {
            if (null != fos) {
                fos.close();
            }
        }
    }

    /**
     * @Description 根据模板导出excel
     * @author linb 2017年6月13日 下午5:42:54
     * @param templatePath 模板文件地址
     * @param os 导出excel流
     * @param list 导出数据列表
     * @param clazz 导出数据类型
     * @param <T> 泛型
     * @throws BizRuntimeException
     * @throws IOException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    public static <T> void exportByTemplate(String templatePath, OutputStream os, List<T> list,
                                            Class<T> clazz) throws BizRuntimeException, IOException, IllegalArgumentException,
            IllegalAccessException {
        FileInputStream fis = null;
        Workbook workbook = null;
        try {
            File file = new File(templatePath);
            if (!file.exists()) {
                throw new BizRuntimeException("文件不存在");
            }
            // 读取excel模板文件
            ExcelType excelType = ExcelType.XLS;
            fis = new FileInputStream(file);
            try {
                workbook = new HSSFWorkbook(fis);
            } catch (Exception e) {
                LOG.debug(e.getMessage(), e);
                // 重新打开文件流
                fis = new FileInputStream(file);
                workbook = new XSSFWorkbook(fis);
                excelType = ExcelType.XLSX;
            }
            Sheet sheet = workbook.getSheetAt(0);

            // 删除模板中的图形，文本框
            clearShape(sheet, excelType);

            // 填充数据到模板文件中
            List<Field> fields = ReflectUtils.getFileds(clazz);
            // 填充数据样式
            CellStyle cellStyle = createCellStyle(workbook);
            // 处理特殊字段
            dealSpecialField(sheet, fields, list, cellStyle);
            // 确定标题行以及字段对应列
            Iterator<Row> rows = sheet.rowIterator();
            int rowNum = -1;
            Map<String, Integer> colMap = new HashMap<>();
            while (rows.hasNext()) {
                Row row = rows.next();
                int colNums = row.getLastCellNum();
                for (int i = 0; i < colNums; i++) {
                    Cell cell = row.getCell(i);
                    String value = getStringCellValue(cell);
                    if (null == value || "".equals(value)) {
                        continue;
                    }
                    for (Field field : fields) {
                        field.setAccessible(true);
                        Excel excel = field.getAnnotation(Excel.class);
                        if (null == excel || excel.skip() || excel.isSpecial()) {
                            continue;
                        }
                        if (value.contains(excel.name())) {
                            rowNum = row.getRowNum();
                            colMap.put(field.getName(), cell.getColumnIndex());
                            // 模板的话列宽不需要设计
                            break;
                        }
                    }
                }
            }
            // 在标题行下一列填充数据
            if (null != list && !list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    Row row = sheet.createRow(rowNum + i + 1);
                    for (Field field : fields) {
                        Integer colNum = colMap.get(field.getName());
                        if (null == colNum) {
                            continue;
                        }
                        field.setAccessible(true);
                        Cell cell = row.createCell(colNum);
                        setCellValue(cell, field.get(list.get(i)), cellStyle);
                    }
                }
            }

            // 输出填充数据的excel文件
            workbook.write(os);
        } catch (BizRuntimeException | IOException | IllegalArgumentException
                | IllegalAccessException e) {
            LOG.error("", e);
            throw e;
        } finally {
            if (null != fis) {
                fis.close();
            }
            if (null != workbook) {
                workbook.close();
            }
        }
    }

    /**
     * @Description 清空表单中的文本框和图形
     * @author linb 2017年6月16日 上午11:09:14
     * @param sheet 表单
     * @param excelType excel类型
     */
    public static void clearShape(Sheet sheet, ExcelType excelType) {
        // 删除模板中的图形，文本框
        if (ExcelType.XLS.equals(excelType)) {
            HSSFPatriarch drawing = (HSSFPatriarch) sheet.getDrawingPatriarch();
            if (null != drawing) {
                drawing.clear();
            }
        } else if (ExcelType.XLSX.equals(excelType)) {
            // xssf找不到清除的方法，使用隐藏代替
            Drawing<?> drawing = sheet.getDrawingPatriarch();
            if (null != drawing) {
                Iterator<?> it = drawing.iterator();
                while (it.hasNext()) {
                    Object o = it.next();
                    if (o instanceof XSSFSimpleShape) {
                        XSSFSimpleShape shape = (XSSFSimpleShape) o;
                        XSSFClientAnchor anchor = (XSSFClientAnchor) shape.getAnchor();
                        // 清空文本
                        shape.clearText();
                        // 转换成线形状，可以不转
                        shape.setShapeType(ShapeType.LINE.ooxmlId);
                        // 使得图形可移动和改变大小
                        anchor.setAnchorType(AnchorType.MOVE_AND_RESIZE);
                        // 移动图形到表单左上角，并隐藏大小
                        anchor.setDx1(0);
                        anchor.setDy1(0);
                        anchor.setDx2(0);
                        anchor.setDy2(0);
                        anchor.setRow1(0);
                        anchor.setCol1(0);
                        anchor.setRow2(0);
                        anchor.setCol2(0);
                    }
                }
            }
        }
    }

    /**
     * @Description 处理导出的特殊字段（值唯一且其值在字段右边）
     * @author linb 2017年6月13日 下午5:39:34
     * @param sheet 表单
     * @param fields 导出字段列表
     * @param list 导出数据列表
     * @param cellStyle 单元格样式
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     */
    private static <T> void dealSpecialField(Sheet sheet, List<Field> fields, List<T> list,
                                             CellStyle cellStyle) throws IllegalArgumentException, IllegalAccessException {
        if (null == list || list.isEmpty()) {
            return;
        }
        for (Field field : fields) {
            field.setAccessible(true);
            Excel excel = field.getAnnotation(Excel.class);
            if (null == excel || excel.skip() || !excel.isSpecial()) {
                continue;
            }
            Iterator<Row> rows = sheet.rowIterator();
            while (rows.hasNext()) {
                Row row = rows.next();
                int colNum = row.getLastCellNum();
                int i = 0;
                for (; i < colNum; i++) {
                    Cell cell = row.getCell(i);
                    String value = getStringCellValue(cell);
                    if (null == value || "".equals(value)) {
                        continue;
                    }
                    if (value.contains(excel.name())) {
                        // 特殊字段，唯一，且值位于字段名之右
                        Cell specialCell = row.createCell(i + 1);
                        setCellValue(specialCell, field.get(list.get(0)), cellStyle);
                        break;
                    }
                }
                if (i < colNum) {
                    // 已找到特殊字段
                    break;
                }
            }
        }
    }

    /**
     * @Description 生成导出excle的单元格样式
     * @author linb 2017年6月13日 下午5:40:48
     * @param workbook 导出单元格的工作薄
     * @return 单元格样式
     */
    public static CellStyle createCellStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        // 设置边框
        // cellStyle.setBorderBottom(BorderStyle.THIN);
        // cellStyle.setBorderLeft(BorderStyle.THIN);
        // cellStyle.setBorderRight(BorderStyle.THIN);
        // cellStyle.setBorderTop(BorderStyle.THIN);
        // 设置字体
        Font font = workbook.createFont();
        font.setColor(IndexedColors.BLACK.index);
        font.setFontName("微软雅黑");
        font.setFontHeightInPoints((short) 10);
        font.setBold(false);
        cellStyle.setFont(font);
        // 设置水平居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        return cellStyle;
    }

    /**
     * @Description 为单元格赋值和样式，时间格式数据默认yyyy-MM-dd HH:mm:ss字符串
     * @author linb 2017年6月13日 下午5:41:46
     * @param cell 待赋值单元格
     * @param o 填充值
     * @param cellStyle 单元格样式
     */
    public static void setCellValue(Cell cell, Object o, CellStyle cellStyle) {
        if (null != cellStyle) {
            cell.setCellStyle(cellStyle);
        }
        if (null == o) {
            return;
        }
        if (o instanceof Date) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            cell.setCellValue(format.format(o));
        } else if (o instanceof Double || o instanceof Float) {
            cell.setCellValue((Double) o);
        } else if (o instanceof Boolean) {
            cell.setCellValue((Boolean) o);
        } else if (o instanceof Integer) {
            cell.setCellValue((Integer) o);
        } else {
            cell.setCellValue(o.toString());
        }
    }
}
