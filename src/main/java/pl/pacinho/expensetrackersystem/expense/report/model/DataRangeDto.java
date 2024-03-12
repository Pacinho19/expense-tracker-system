package pl.pacinho.expensetrackersystem.expense.report.model;

import java.time.LocalDate;

public record DataRangeDto(LocalDate startDate, LocalDate endDate) {
}
