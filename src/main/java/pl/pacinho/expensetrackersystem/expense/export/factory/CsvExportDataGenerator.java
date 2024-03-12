package pl.pacinho.expensetrackersystem.expense.export.factory;

import pl.pacinho.expensetrackersystem.expense.model.dto.ExpenseDto;
import pl.pacinho.expensetrackersystem.expense.model.entity.Expense;

import java.util.List;
import java.util.stream.Collectors;

class CsvExportDataGenerator implements ExportDataGenerator {

    private final String SEPARATOR = ";";
    private final String HEADERS = "NAME;CATEGORY;AMOUNT;DATE";

    @Override
    public String exportData(List<ExpenseDto> expenses) {
        List<String> data = expenses.stream()
                .map(expense -> expense.toStringWithSeparator(SEPARATOR))
                .collect(Collectors.toList());

        data.add(0, HEADERS);
        return String.join("\n", data);
    }
}
