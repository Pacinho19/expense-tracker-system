package pl.pacinho.expensetrackersystem.expense.model.dto;

import pl.pacinho.expensetrackersystem.expense.model.entity.Expense;
import pl.pacinho.expensetrackersystem.expense.model.enums.Category;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExpenseDto(String name, Category category, BigDecimal amount, LocalDate date) {
    public static ExpenseDto of(Expense expense) {
        return new ExpenseDto(
                expense.getName(),
                expense.getCategory(),
                expense.getAmount(),
                expense.getDate()
        );
    }

    public String toStringWithSeparator(String separator) {
        return name + separator
               + category + separator
               + amount + separator
               + date;
    }
}
