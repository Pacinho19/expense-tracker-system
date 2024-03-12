package pl.pacinho.expensetrackersystem.expense.export.factory;

import pl.pacinho.expensetrackersystem.expense.model.dto.ExpenseDto;
import pl.pacinho.expensetrackersystem.expense.model.entity.Expense;

import java.util.List;

public interface ExportDataGenerator {

    String exportData(List<ExpenseDto> expenses);
}
