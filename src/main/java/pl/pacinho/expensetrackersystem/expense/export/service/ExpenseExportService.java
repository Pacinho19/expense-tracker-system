package pl.pacinho.expensetrackersystem.expense.export.service;

import org.springframework.stereotype.Service;
import pl.pacinho.expensetrackersystem.expense.export.factory.ExportDataGenerator;
import pl.pacinho.expensetrackersystem.expense.export.factory.ExportDataGeneratorFactory;
import pl.pacinho.expensetrackersystem.expense.export.model.ExportFormat;
import pl.pacinho.expensetrackersystem.expense.model.dto.ExpenseDto;

import java.util.List;

@Service
public class ExpenseExportService {

    public String export(ExportFormat exportFormat, List<ExpenseDto> expenses) {
        ExportDataGenerator generator = ExportDataGeneratorFactory.build(exportFormat);
        return generator.exportData(expenses);
    }
}
