package com.github.fttroy.xlsx_compiler.utils;

import java.sql.Date;
import java.time.*;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.Locale;

public class DateUtils {

    public static Date getFirstDayOfMonth(int month) {
        LocalDate firstDayOfMonth = YearMonth.of(Year.now().getValue(), month).atDay(1);
        return Date.valueOf(LocalDate.from(firstDayOfMonth));
    }

    public static Date getLastDayOfMonth(int month) {
        LocalDate lastDayOfMonth = YearMonth.of(Year.now().getValue(), month).atEndOfMonth();
        return Date.valueOf(LocalDate.from(lastDayOfMonth)
                .with(TemporalAdjusters.lastDayOfMonth()));
    }

    public static String getMonth(int month, boolean italian) {
        return italian
                ? Month.of(month).getDisplayName(TextStyle.FULL, Locale.ITALIAN)
                : Month.of(month).toString();
    }

    public static boolean isHoliday(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }

    public static LocalDate toLocalDate(java.util.Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public static int getNumberOfDayFromYearMonth(String month) {
        return YearMonth.of(Year.now().getValue(), Month.valueOf(month))
                .lengthOfMonth();

    }
}
