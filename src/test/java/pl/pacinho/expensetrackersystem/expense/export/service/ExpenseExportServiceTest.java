package pl.pacinho.expensetrackersystem.expense.export.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.pacinho.expensetrackersystem.expense.export.factory.ExpensesStub;
import pl.pacinho.expensetrackersystem.expense.export.model.ExportFormat;
import pl.pacinho.expensetrackersystem.expense.model.dto.ExpenseDto;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(MockitoExtension.class)
class ExpenseExportServiceTest {

    private static ExpenseExportService expenseExportService;

    @BeforeAll
    private static void beforeAll() {
        expenseExportService = new ExpenseExportService();
    }

    @Test
    void stringWithJsonFormatShouldBeReturnedWhenGivenExportFormatIsJson() {
        //given
        List<ExpenseDto> expenses = ExpensesStub.getExampleExpenses();
        ExportFormat exportFormat = ExportFormat.JSON;

        //when
        String output = expenseExportService.export(exportFormat, expenses);

        //then
        String expectedOutput = """
                [{"name":"Name1","category":"ENTERTAINMENT","amount":1,"date":"2024-01-01"},{"name":"Name2","category":"FOOD","amount":10,"date":"2024-01-02"}]""";

        assertThat(output, equalTo(expectedOutput));
    }

    @Test
    void jsonWithEmptyArrayShouldBeReturnedWhenGivenListIsEmpty() {
        //given
        List<ExpenseDto> expenses = Collections.emptyList();
        ExportFormat exportFormat = ExportFormat.JSON;

        //when
        String output = expenseExportService.export(exportFormat, expenses);

        //then
        assertThat(output, equalTo("[]"));
    }

    @Test
    void jsonWithEmptyArrayShouldBeReturnedWhenGivenListIsNull() {
        //given
        List<ExpenseDto> expenses = null;
        ExportFormat exportFormat = ExportFormat.JSON;

        //when
        String output = expenseExportService.export(exportFormat, expenses);

        //then
        assertThat(output, equalTo("[]"));
    }

    @Test
    void stringWithCsvFormatShouldBeReturnedWhenGivenExportFormatIsCSV() {
        //given
        List<ExpenseDto> expenses = ExpensesStub.getExampleExpenses();
        ExportFormat exportFormat = ExportFormat.CSV;

        //when
        String output = expenseExportService.export(exportFormat, expenses);

        //then
        String expectedOutput = """
                NAME;CATEGORY;AMOUNT;DATE
                Name1;ENTERTAINMENT;1;2024-01-01
                Name2;FOOD;10;2024-01-02""";

        assertThat(output, equalTo(expectedOutput));
    }

    @Test
    void onlyHeaderRowShouldBeReturnedWhenGivenListIsEmpty(){
        //given
        List<ExpenseDto> expenses = Collections.emptyList();
        ExportFormat exportFormat = ExportFormat.CSV;

        //when
        String output = expenseExportService.export(exportFormat, expenses);

        //then
        assertThat(output.split("\n"), arrayWithSize(1));
    }

    @Test
    void onlyHeaderRowShouldBeReturnedWhenGivenListIsNull(){
        //given
        List<ExpenseDto> expenses = null;
        ExportFormat exportFormat = ExportFormat.CSV;

        //when
        String output = expenseExportService.export(exportFormat, expenses);

        //then
        assertThat(output.split("\n"), arrayWithSize(1));
    }
}