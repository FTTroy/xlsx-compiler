package com.github.fttroy.xlsx_compiler.service;

import com.github.fttroy.xlsx_compiler.model.FormData;
import com.github.fttroy.xlsx_compiler.utils.XlsxUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;

import static com.github.fttroy.xlsx_compiler.utils.DateUtils.getMonth;
import static com.github.fttroy.xlsx_compiler.utils.XlsxUtils.*;

@Service
public class XlsxService {

    public byte[] createXlsx(FormData formData) throws IOException {
        String italianMonth = getMonth(formData.getMese(), true);
        ClassPathResource resource = new ClassPathResource("template.xlsx");
        try (InputStream is = resource.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {
            workbook.setSheetName(0, italianMonth);

            Sheet sheet = workbook.getSheetAt(0);
            setDatesInExcel(sheet, formData.getMese());
            evaluateCellFormulas(workbook);

            String month = getMonth(formData.getMese(), false);
            formData.getRowDataList()
                    .forEach(rowData -> {
                                XlsxUtils.writeRowDataInXlsx(month, sheet, rowData);
                            }
                    );
            evaluateCellFormulas(workbook);
            workbook.setForceFormulaRecalculation(true);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            workbook.write(baos);
            return baos.toByteArray();
        }
    }
}
