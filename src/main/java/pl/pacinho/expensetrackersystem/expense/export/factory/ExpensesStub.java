package pl.pacinho.expensetrackersystem.expense.export.factory;

import pl.pacinho.expensetrackersystem.expense.model.dto.ExpenseDto;
import pl.pacinho.expensetrackersystem.expense.model.enums.Category;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ExpensesStub {

    public static List<ExpenseDto> getExampleExpenses() {
        LocalDate date = LocalDate.of(2024, 1, 1);
        return List.of(
                new ExpenseDto("Name1", Category.ENTERTAINMENT, BigDecimal.ONE, date),
                new ExpenseDto("Name2", Category.FOOD, BigDecimal.TEN, date.plusDays(1))
        );
    }
}
