package pl.pacinho.expensetrackersystem.expense.export.factory;

import com.google.gson.GsonBuilder;
import pl.pacinho.expensetrackersystem.expense.model.dto.ExpenseDto;
import pl.pacinho.expensetrackersystem.tools.LocalDateAdapter;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

class JsonExportDataGenerator implements ExportDataGenerator {
    @Override
    public String exportData(List<ExpenseDto> expenses) {
        if (expenses == null)
            expenses = Collections.emptyList();

        return new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create()
                .toJson(expenses);
    }
}
