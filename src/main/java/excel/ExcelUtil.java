package excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.imports.ExcelImportService;
import cn.afterturn.easypoi.exception.excel.ExcelImportException;
import excel.dto.JobDTO;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;

/**
 * 参考：
 * https://opensource.afterturn.cn/doc/easypoi.html#10602
 * 使用文档：
 * https://gitee.com/lemur/easypoi/blob/master/basedemo.md
 * 测试项目：
 * https://gitee.com/lemur/easypoi-test
 *
 * @author 陆昆
 **/
public class ExcelUtil {
    /**
     * 解析excel到指定的对象
     *
     * @param excelPath
     * @param pojoClass
     * @param <T>
     * @return
     */
    public static <T> List<T>  parseExcel(String excelPath, Class<?> pojoClass) {
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(excelPath));
            return new ExcelImportService().importExcelByIs(inputStream, pojoClass, new ImportParams(), false)
                .getList();
        } catch (FileNotFoundException e) {
            throw new ExcelImportException(e.getMessage(), e);
        } catch (ExcelImportException e) {
            throw new ExcelImportException(e.getType(), e);
        } catch (Exception e) {
            throw new ExcelImportException(e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    /**
     * 导出excel文件
     *
     * @param params
     * @param pojoClass
     * @param dataSet
     * @return
     */
    public static Workbook exportExcel(ExportParams params, Class<?> pojoClass,
                                       Collection<?> dataSet) {
        return ExcelExportUtil.exportExcel(params, pojoClass, dataSet);
    }

    public static void main(String[] args) {
        ExportParams params = new ExportParams();
        params.setSheetName("测试");
        params.setTitle("测试");
        List<JobDTO> jobDTOS = new ArrayList<JobDTO>();
        for (int i = 0;i < 100;i++) {
            JobDTO jobDTO = new JobDTO(Integer.toString(i), Integer.toString(i));
            jobDTOS.add(jobDTO);
        }
        Workbook workbook = exportExcel(params, JobDTO.class, jobDTOS);
        if (workbook != null) {
            try {
                workbook.write(new FileOutputStream(new File("/Users/lukun/Downloads/test2.xls")));
            } catch (IOException e) {

            } finally {
                try {
                    workbook.close();
                } catch (IOException e) {

                }
            }
        }
    }
}
