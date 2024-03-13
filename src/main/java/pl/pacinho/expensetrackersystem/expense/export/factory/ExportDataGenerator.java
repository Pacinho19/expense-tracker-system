package pl.pacinho.expensetrackersystem.expense.export.factory;

import pl.pacinho.expensetrackersystem.expense.model.dto.ExpenseDto;

import java.util.List;

public interface ExportDataGenerator {

    String exportData(List<ExpenseDto> expenses);
}
