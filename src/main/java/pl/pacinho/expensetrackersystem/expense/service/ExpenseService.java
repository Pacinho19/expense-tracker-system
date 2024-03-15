package pl.pacinho.expensetrackersystem.expense.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import pl.pacinho.expensetrackersystem.expense.model.dto.ExpenseDto;
import pl.pacinho.expensetrackersystem.expense.model.dto.ExpensePage;
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
        return ExpenseMapper.convertToDtoList(expenseRepository.findAll());
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

    public List<ExpenseDto> findAllByNameCategoryOrDateRange(String name, Category category, LocalDate startDate, LocalDate endDate) {
        ExpenseFilterDto expenseFilterDto = ExpenseFilterDto.builder()
                .filters(List.of(
                        new FieldFilterDto("category", "category", category, PredicateFunction.EQUAL_TO),
                        new FieldFilterDto("name", "name", name, PredicateFunction.CONTAINS),
                        new FieldFilterDto("date", "startDate", startDate, PredicateFunction.DATE_GREATER_THAN_OR_EQUAL_TO),
                        new FieldFilterDto("date", "endDate", endDate, PredicateFunction.DATE_LESS_THAN_OR_EQUAL_TO)
                ))
                .build();

        return ExpenseMapper.convertToDtoList(
                expenseFilterRepository.findAll(expenseFilterDto)
        );
    }

    public ExpensePage findAllPageable(int pageNumber, int size) {
        Pageable pageable = PageRequest.of(pageNumber - 1, size, Sort.by("id"));
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        Page<Expense> expenses = expenseRepository.findAll(pageable);
        List<ExpenseDto> expensesDtos = ExpenseMapper.convertToDtoList(expenses.getContent());
        return new ExpensePage(expensesDtos, currentPage + 1, expenses.getTotalElements(), expenses.getTotalPages(), pageSize);
    }
}
