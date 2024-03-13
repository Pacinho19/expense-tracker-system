package pl.pacinho.expensetrackersystem.expense.export.factory;

import org.junit.jupiter.api.Test;
import pl.pacinho.expensetrackersystem.expense.model.dto.ExpenseDto;
import pl.pacinho.expensetrackersystem.expense.model.enums.Category;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class JsonExportDataGeneratorTest {

    @Test
    void stringInJsonFormatShouldBeReturnedWhenGivenListHasTwoElements() {
        //given
        List<ExpenseDto> expenses = ExpensesStub.getExampleExpenses();

        //when
        String output = new JsonExportDataGenerator().exportData(expenses);

        //then
        String expectedOutput = """
                [{"name":"Name1","category":"ENTERTAINMENT","amount":1,"date":"2024-01-01"},{"name":"Name2","category":"FOOD","amount":10,"date":"2024-01-02"}]""";

        assertThat(output, equalTo(expectedOutput));
    }

    @Test
    void jsonWithEmptyArrayShouldBeReturnedWhenGivenListIsEmpty() {
        //given
        List<ExpenseDto> expenses = Collections.emptyList();

        //when
        String output = new JsonExportDataGenerator().exportData(expenses);

        //then
        assertThat(output, equalTo("[]"));
    }
}