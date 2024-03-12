package pl.pacinho.expensetrackersystem.expense.search.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ExpenseFilterDto {

    List<FieldFilterDto> filters;
}
