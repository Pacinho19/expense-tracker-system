package pl.pacinho.expensetrackersystem.expense.export.factory;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import pl.pacinho.expensetrackersystem.expense.export.model.ExportFormat;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.*;

class ExportDataGeneratorFactoryTest {

    @Test
    void instanceOfCsvExportDataGeneratorShouldBeReturnedWhenGivenExportFormatIsCSV() {
        //given
        ExportFormat exportFormat = ExportFormat.CSV;

        //when
        ExportDataGenerator exportDataGenerator = ExportDataGeneratorFactory.build(exportFormat);

        //then
        assertThat(exportDataGenerator, instanceOf(CsvExportDataGenerator.class));
    }

    @Test
    void instanceOfJsonExportDataGeneratorShouldBeReturnedWhenGivenExportFormatIsJson() {
        //given
        ExportFormat exportFormat = ExportFormat.JSON;

        //when
        ExportDataGenerator exportDataGenerator = ExportDataGeneratorFactory.build(exportFormat);

        //then
        assertThat(exportDataGenerator, instanceOf(JsonExportDataGenerator.class));
    }
}