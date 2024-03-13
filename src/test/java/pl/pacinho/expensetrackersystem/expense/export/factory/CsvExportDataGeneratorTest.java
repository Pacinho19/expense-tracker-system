package pl.pacinho.expensetrackersystem.expense.export.factory;

import org.junit.jupiter.api.Test;
import pl.pacinho.expensetrackersystem.expense.model.dto.ExpenseDto;
import pl.pacinho.expensetrackersystem.expense.model.enums.Category;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.equalTo;

class CsvExportDataGeneratorTest {


    @Test
    void stringWithThreeLinesShouldBeReturnedWhenGivenListHasTwoElements(){
        //given
        List<ExpenseDto> expenses = ExpensesStub.getExampleExpenses();

        //when
        String output = new CsvExportDataGenerator().exportData(expenses);

        //then
        String expectedOutput = """
                NAME;CATEGORY;AMOUNT;DATE
                Name1;ENTERTAINMENT;1;2024-01-01
                Name2;FOOD;10;2024-01-02""";

        assertThat(output.split("\n"), arrayWithSize(3));
        assertThat(output, equalTo(expectedOutput));
    }

    @Test
    void onlyHeaderRowShouldBeReturnedWhenGivenListIsEmpty(){
        //given
        List<ExpenseDto> expenses = Collections.emptyList();

        //when
        String output = new CsvExportDataGenerator().exportData(expenses);
        String headers = new CsvExportDataGenerator().createHeadersFromDto();

        //then
        assertThat(output.split("\n"), arrayWithSize(1));
        assertThat(output, equalTo(headers));
    }
}