package pl.pacinho.expensetrackersystem.expense.report.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import pl.pacinho.expensetrackersystem.expense.model.dto.ExpenseDto;
import pl.pacinho.expensetrackersystem.expense.model.enums.Category;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class ExpensesMonthlyReportDto {

    private DataRangeDto dataRange;
    private BigDecimal totalAmount;
    private int expensesCount;
    private ExpenseDto expenseWithMaxAmount;
    private Category categoryWithMaxTotalAmount;
    private Category categoryWithMaxExpensesCount;


}
