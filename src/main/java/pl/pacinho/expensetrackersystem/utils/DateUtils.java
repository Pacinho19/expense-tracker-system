package pl.pacinho.expensetrackersystem.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class DateUtils {
    public static boolean isBetween(LocalDate date, LocalDate startDate, LocalDate endDate) {
        if (date == null || startDate == null || endDate == null)
            return false;

        return (date.isAfter(startDate) || date.isEqual(startDate))
               && (date.isBefore(endDate) || date.isEqual(endDate));
    }

    public static Date convert(LocalDateTime dateToConvert) {
        if (dateToConvert == null)
            return null;
        return java.sql.Timestamp.valueOf(dateToConvert);
    }
}
