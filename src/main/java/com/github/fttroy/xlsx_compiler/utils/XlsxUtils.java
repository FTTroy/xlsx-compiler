package com.github.fttroy.xlsx_compiler.utils;

import com.github.fttroy.xlsx_compiler.model.RowData;
import org.apache.poi.ss.usermodel.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.github.fttroy.xlsx_compiler.utils.DateUtils.*;

public class XlsxUtils {
    private final static Integer TABLE_START = 4;
    private final static Integer TABLE_END = 35;

    public static void setDatesInExcel(Sheet sheet, int month) {
        Cell dateFromCell = sheet.getRow(1).getCell(1);
        Cell dateToCell = sheet.getRow(2).getCell(1);
        dateFromCell.setCellValue(DateUtils.getFirstDayOfMonth(month));
        dateToCell.setCellValue(DateUtils.getLastDayOfMonth(month));
    }

    public static void writeInColumn(String month, Sheet sheet, int cellIndex, List<?> valueList) {
        for (int i = TABLE_START; i < TABLE_END; i++) {
            Row row = sheet.getRow(i);
            LocalDate rowDate = toLocalDate(row.getCell(0).getDateCellValue());
            int rowDay = rowDate.getDayOfMonth();
            String rowMonth = rowDate.getMonth().toString();
            int lengthOfMonth = DateUtils.getNumberOfDayFromYearMonth(month);
            Cell cell = sheet.getRow(i).getCell(cellIndex);
            if (!Objects.equals(rowMonth, month) || rowDay > lengthOfMonth) {
                row.setZeroHeight(true);
            } else {
                if (row.getZeroHeight()) {
                    row.setZeroHeight(false);
                }
                if (!isHoliday(rowDate)) {
                    write(cell, valueList.get(i - 4));
                }
            }
        }
    }

    public static void writeInColumn(String month, Integer day, Object value, Sheet sheet, int cellIndex) {
        Row row = sheet.getRow(day + TABLE_START - 1);
        LocalDate rowDate = toLocalDate(row.getCell(0).getDateCellValue());
        int rowDay = rowDate.getDayOfMonth();
        String rowMonth = rowDate.getMonth().toString();
        int lengthOfMonth = DateUtils.getNumberOfDayFromYearMonth(month);
        Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
        if (!Objects.equals(rowMonth, month) || rowDay > lengthOfMonth) {
            row.setZeroHeight(true);
        } else {
            if (row.getZeroHeight()) {
                row.setZeroHeight(false);
            }
            if (!isHoliday(rowDate)) {
                write(cell, value);
            }
        }
    }

    public static void evaluateCellFormulas(Workbook workbook) {
        FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
        evaluator.evaluateAll();
    }

    private static void write(Cell cell, Object value) {
        if (value == null) return;
        if (value instanceof String) {
            cell.setCellValue((String) value);
        }
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        }
        if (value instanceof Double) {
            cell.setCellValue((Double) value);
        }
        if (value instanceof Date) {
            cell.setCellValue((Date) value);
        }
    }

    public static void writeRowDataInXlsx(String month, Sheet sheet, RowData rowData) {
        Integer day = rowData.getDay();
        if (rowData.getMotivoAssenza() != null && !rowData.getMotivoAssenza().isEmpty()) {
            writeInColumn(month, day, rowData.getOreAssenza(), sheet, 7);
            writeInColumn(month, day, rowData.getMotivoAssenza(), sheet, 8);
        } else {
            writeInColumn(month, day, rowData.getLocalita(), sheet, 2);
            writeInColumn(month, day, rowData.getCliente(), sheet, 3);
            writeInColumn(month, day, rowData.getAttivita(), sheet, 4);
            writeInColumn(month, day, rowData.getOreOrdinarie(), sheet, 5);
            writeInColumn(month, day, rowData.getOreStraordinarie(), sheet, 6);
            writeInColumn(month, day, rowData.getTrasferta(), sheet, 9);
            writeInColumn(month, day, rowData.getIndennita(), sheet, 10);
            writeInColumn(month, day, rowData.getViaggio(), sheet, 11);
        }
    }
}
