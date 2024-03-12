package pl.pacinho.expensetrackersystem.expense.export.factory;

import pl.pacinho.expensetrackersystem.expense.export.model.ExportFormat;

public class ExportDataGeneratorFactory {

    public static ExportDataGenerator build(ExportFormat exportFormat) {
        switch (exportFormat) {
            case CSV -> {
                return new CsvExportDataGenerator();
            }
            default -> {
                return new JsonExportDataGenerator();
            }
        }
    }
}
