package com.github.fttroy.xlsx_compiler.model;

import lombok.Data;

import java.util.List;

@Data
public class FormData {
    private int mese;
    private List<RowData> rowDataList;
}
