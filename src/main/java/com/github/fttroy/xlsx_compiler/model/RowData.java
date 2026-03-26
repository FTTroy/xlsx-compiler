package com.github.fttroy.xlsx_compiler.model;

import lombok.Data;

@Data
public class RowData {
    private Integer day;
    private String localita;
    private String cliente;
    private String attivita;
    private Integer oreOrdinarie;
    private Integer oreStraordinarie;
    private Integer oreAssenza;
    private String motivoAssenza;
    private String trasferta;
    private String indennita;
    private String viaggio;
}
