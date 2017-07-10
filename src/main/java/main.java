import model.RotateRequireImportExcelModel;
import util.excel.ExcelException;
import util.excel.ExcelIO;
import util.excel.ExcelWrapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author tsy
 * @Description
 * @date 14:30 2017/7/7
 */
public class main {
    public static void main(String[] args)
            throws IllegalAccessException, IOException, InstantiationException {
        // 读取测试
        ExcelWrapper<RotateRequireImportExcelModel> ew = ExcelIO
                .excelToClass("D:\\3.轮转要求模板V0.5.xls", RotateRequireImportExcelModel.class);
        List<RotateRequireImportExcelModel> rrImportModelList = ew.getList();
        List<ExcelException> exceptionList = ew.getExceptionList();

        exceptionList.forEach(u -> System.out.println(u.getDesc()));
        // rrImportModelList.forEach(u-> System.out.println(u.getOrgAlias()));
        System.out.println("读取完成");
    }
}
