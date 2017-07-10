package util.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Excel导出
 * @author tsy
 */
public final class ExcelUtils {

    /**
     * @Description 微软雅黑字体名称
     */
    private static final String EXCEL_OUTPUT_FONT_YAHEI = "微软雅黑";
    private static final Logger LOG = LoggerFactory.getLogger(ExcelUtils.class);

    private ExcelUtils() {
        // Util class;
    }

    /**
     * 将数据写入到EXCEL文档
     * @param list 数据集合
     * @param cls 实体
     * @param out 输出流
     * @param <T> 泛型对象类型
     * @throws IllegalAccessException
     * @throws IOException
     */
    public static <T> void writeToFile(List<T> list, OutputStream out, Class<T> cls)
            throws IllegalAccessException, IOException {
        // 创建并获取工作簿对象
        Workbook wb = null;
        // 写入到文件
        try {
            wb = getWorkBook(list, cls);
            wb.write(out);
        } catch (IllegalArgumentException | IllegalAccessException | IOException e) {
            LOG.error("文件写入有误", e);
            throw e;
        } finally {
            if (null != wb) {
                wb.close();
            }
        }
    }

    /**
     * @Description 导出excel文件
     * @author linb 2017年6月8日 下午7:45:07
     * @param response 文件流
     * @param fileName 文件名称，无后缀
     * @param list 导出列表
     * @param clazz 列表类型
     * @param <T> 泛型
     * @throws Exception
     */
    public static <T> void writeToFile(HttpServletResponse response, String fileName, List<T> list,
            Class<T> clazz) throws Exception {
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            response.setContentType("octets/stream");
            response.addHeader("Content-Disposition",
                    "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xls");
            ExcelUtils.writeToFile(list, os, clazz);
        } catch (Exception e) {
            LOG.error("", e);
            throw e;
        } finally {
            if (null != os) {
                os.close();
            }
        }
    }

    /**
     * 获得Workbook对象
     * @param list 数据集合
     * @param clazz 实体的类型信息
     * @param <T> 泛型
     * @return Workbook
     * @throws IllegalAccessException
     */
    public static <T> Workbook getWorkBook(List<T> list, Class<T> clazz)
            throws IllegalAccessException {
        // 创建工作簿
        Workbook workbook = new SXSSFWorkbook();

        // 创建一个工作表sheet
        Sheet sheet = workbook.createSheet();
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 15);
        // 行列申明
        Excel excel = null;
        Row row = null;
        Cell cell = null;

        CellStyle titleStyle = createTitleStyle(workbook);
        CellStyle cellStyle = createCellStyle(workbook);

        List<Field> fields = ReflectUtils.getFileds(clazz);
        boolean hasSpecial = false;
        int rowIndex = 0;
        int columnIndex = 0;
        // 处理特殊 字段
        row = sheet.createRow(rowIndex);
        for (Field field : fields) {
            field.setAccessible(true);
            excel = field.getAnnotation(Excel.class);
            if (null == excel || excel.skip() || !excel.isSpecial()) {
                continue;
            }
            // 特殊字段名
            cell = row.createCell(columnIndex);
            setCellValue(cell, excel.name(), titleStyle);
            // 特殊字段内容
            if (null != list && !list.isEmpty()) {
                cell = row.createCell(columnIndex + 1);
                setCellValue(cell, field.get(list.get(0)), cellStyle);
            }
            columnIndex += 2;
            hasSpecial = true;
        }
        // 处理普通标题
        columnIndex = 0;
        if (hasSpecial) {
            rowIndex++;
            row = sheet.createRow(rowIndex);
        }
        for (Field field : fields) {
            field.setAccessible(true);
            excel = field.getAnnotation(Excel.class);
            if (null == excel || excel.skip() || excel.isSpecial()) {
                continue;
            }
            // 列宽注意乘256
            sheet.setColumnWidth(columnIndex, excel.width() * 256);
            // 写入标题
            cell = row.createCell(columnIndex);
            setCellValue(cell, excel.name(), titleStyle);
            columnIndex++;
        }

        // 填充正常数据内容
        if (list == null || list.isEmpty()) {
            return workbook;
        }
        rowIndex++;
        for (Field field : fields) {
            field.setAccessible(true);
            excel = field.getAnnotation(Excel.class);
            if (excel == null || excel.skip() || excel.isSpecial()) {
                continue;
            }
        }

        for (T t : list) {
            row = sheet.createRow(rowIndex);
            columnIndex = 0;
            for (Field field : fields) {
                field.setAccessible(true);
                // 忽略标记skip的字段
                excel = field.getAnnotation(Excel.class);
                if (excel == null || excel.skip() || excel.isSpecial()) {
                    continue;
                }
                cell = row.createCell(columnIndex);
                setCellValue(cell, field.get(t), cellStyle);
                columnIndex++;
            }
            rowIndex++;
        }
        return workbook;
    }

    /**
     * @Description 生成导出excle的标题单元格样式
     * @author linb 2017年6月13日 下午5:40:48
     * @param workbook 导出单元格的工作薄
     * @return 标题单元格样式
     */
    private static CellStyle createTitleStyle(Workbook workbook) {
        CellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        // 设置前景色
        titleStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.index);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);

        // 设置边框
        titleStyle.setBorderBottom(BorderStyle.THIN);
        titleStyle.setBorderLeft(BorderStyle.THIN);
        titleStyle.setBorderRight(BorderStyle.THIN);
        titleStyle.setBorderTop(BorderStyle.THIN);

        Font font = workbook.createFont();
        font.setColor(IndexedColors.BLACK.index);
        font.setFontName(EXCEL_OUTPUT_FONT_YAHEI);
        font.setFontHeightInPoints((short) 13);
        font.setBold(false);
        // 设置字体
        titleStyle.setFont(font);
        return titleStyle;
    }

    /**
     * @Description 生成导出excle的单元格样式
     * @author linb 2017年6月13日 下午5:40:48
     * @param workbook 导出单元格的工作薄
     * @return 单元格样式
     */
    private static CellStyle createCellStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setColor(IndexedColors.BLACK.index);
        font.setFontName(EXCEL_OUTPUT_FONT_YAHEI);
        font.setFontHeightInPoints((short) 10);
        font.setBold(false);
        cellStyle.setFont(font);
        return cellStyle;
    }

    /**
     * @Description 为单元格赋值和样式，时间格式数据默认yyyy-MM-dd HH:mm:ss字符串
     * @author linb 2017年6月13日 下午5:41:46
     * @param cell 待赋值单元格
     * @param o 填充值
     * @param cellStyle 单元格样式
     */
    private static void setCellValue(Cell cell, Object o, CellStyle cellStyle) {
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
