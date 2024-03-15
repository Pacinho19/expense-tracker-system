package pl.pacinho.expensetrackersystem.expense.model.mapper;

import pl.pacinho.expensetrackersystem.expense.model.dto.ExpenseDto;
import pl.pacinho.expensetrackersystem.expense.model.entity.Expense;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ExpenseMapper {
    public static Expense convertToEntity(ExpenseDto expenseDto) {
        return Expense.builder()
                .name(expenseDto.name())
                .amount(expenseDto.amount())
                .date(expenseDto.date())
                .category(expenseDto.category())
                .build();
    }

    public static Optional<ExpenseDto> convertToOptionalDto(Optional<Expense> expenseOptional) {
        return expenseOptional.flatMap(expense -> Optional.of(ExpenseDto.of(expense)));
    }

    public static ExpenseDto convertToDto(Expense expense) {
        return ExpenseDto.of(expense);
    }

    public static List<ExpenseDto> convertToDtoList(List<Expense> content) {
        return content.stream()
                .map(ExpenseMapper::convertToDto)
                .collect(Collectors.toList());
    }
}
