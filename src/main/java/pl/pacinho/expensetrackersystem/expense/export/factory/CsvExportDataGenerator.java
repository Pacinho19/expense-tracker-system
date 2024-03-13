package pl.pacinho.expensetrackersystem.expense.export.factory;

import pl.pacinho.expensetrackersystem.expense.model.dto.ExpenseDto;
import pl.pacinho.expensetrackersystem.tools.ReflectionFieldsExtractor;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

class CsvExportDataGenerator implements ExportDataGenerator {

    private final String SEPARATOR = ";";
    @Override
    public String exportData(List<ExpenseDto> expenses) {
        if(expenses==null)
            expenses = Collections.emptyList();

        List<String> data = expenses.stream()
                .map(expense -> expense.toStringWithSeparator(SEPARATOR))
                .collect(Collectors.toList());

        data.add(0, createHeadersFromDto());
        return String.join("\n", data);
    }

    public String createHeadersFromDto() {
        return ReflectionFieldsExtractor.getFieldsStream(ExpenseDto.class)
                .map(f -> f.getName().toUpperCase())
                .collect(Collectors.joining(";"));
    }
}
