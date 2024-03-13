package pl.pacinho.expensetrackersystem.utils;

import java.time.LocalDate;

public class DateUtils {
    public static boolean isBetween(LocalDate date, LocalDate startDate, LocalDate endDate) {
        if (date == null || startDate == null || endDate == null)
            return false;

        return (date.isAfter(startDate) || date.isEqual(startDate))
               && (date.isBefore(endDate) || date.isEqual(endDate));
    }
}
