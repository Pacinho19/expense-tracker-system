package pl.pacinho.expensetrackersystem.expense.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pacinho.expensetrackersystem.expense.model.dto.ExpenseDto;
import pl.pacinho.expensetrackersystem.expense.search.model.ExpenseFilterDto;
import pl.pacinho.expensetrackersystem.expense.search.model.FieldFilterDto;
import pl.pacinho.expensetrackersystem.expense.search.model.PredicateFunction;
import pl.pacinho.expensetrackersystem.expense.model.entity.Expense;
import pl.pacinho.expensetrackersystem.expense.model.enums.Category;
import pl.pacinho.expensetrackersystem.expense.model.mapper.ExpenseMapper;
import pl.pacinho.expensetrackersystem.expense.search.repository.ExpenseFilterRepository;
import pl.pacinho.expensetrackersystem.expense.repository.ExpenseRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseFilterRepository expenseFilterRepository;

    public Expense save(ExpenseDto expenseDto) {
        return expenseRepository.save(
                ExpenseMapper.convertToEntity(expenseDto)
        );
    }

    public Optional<ExpenseDto> findById(int id) {
        return ExpenseMapper.convertToOptionalDto(
                expenseRepository.findById(id)
        );
    }

    public List<ExpenseDto> findAll() {
        return expenseRepository.findAll()
                .stream()
                .map(ExpenseMapper::convertToDto)
                .toList();
    }

    public void delete(int id) {
        expenseRepository.deleteById(id);
    }

    public ExpenseDto update(int id, ExpenseDto toUpdate) {
        Optional<Expense> expenseOpt = expenseRepository.findById(id);
        if (expenseOpt.isEmpty())
            return null;

        Expense newExpense = expenseOpt.get();
        newExpense.modify(toUpdate);

        return ExpenseMapper.convertToDto(
                expenseRepository.save(newExpense)
        );
    }

    public List<ExpenseDto> findAllByCategoryOrDate(Category category, LocalDate startDate, LocalDate endDate) {
        ExpenseFilterDto expenseFilterDto = ExpenseFilterDto.builder()
                .filters(List.of(
                        new FieldFilterDto("category", "category", category, PredicateFunction.EQUAL_TO),
                        new FieldFilterDto("date", "startDate", startDate, PredicateFunction.GREATER_THAN_OR_EQUAL_TO),
                        new FieldFilterDto("date", "endDate", endDate, PredicateFunction.LESS_THAN_OR_EQUAL_TO)
                ))
                .build();

        return expenseFilterRepository.findAll(expenseFilterDto)
                .stream()
                .map(ExpenseMapper::convertToDto)
                .toList();
    }

}
