package pl.pacinho.expensetrackersystem.expense.report.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pacinho.expensetrackersystem.expense.model.dto.ExpenseDto;
import pl.pacinho.expensetrackersystem.expense.model.entity.Expense;
import pl.pacinho.expensetrackersystem.expense.model.enums.Category;
import pl.pacinho.expensetrackersystem.expense.model.mapper.ExpenseMapper;
import pl.pacinho.expensetrackersystem.expense.report.model.DataRangeDto;
import pl.pacinho.expensetrackersystem.expense.report.model.ExpensesMonthlyReportDto;
import pl.pacinho.expensetrackersystem.expense.repository.ExpenseRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.summingDouble;

@RequiredArgsConstructor
@Service
public class ExpensesMonthlyReportGenerator {

    private final ExpenseRepository expenseRepository;

    public ExpensesMonthlyReportDto generate(int year, int month) {
        LocalDate startDate = YearMonth.of(year, month).atDay(1);
        LocalDate endDate = YearMonth.of(year, month).atEndOfMonth();

        List<Expense> expenses = expenseRepository.findAllByDateBetween(startDate, endDate);

        return ExpensesMonthlyReportDto.builder()
                .dataRange(new DataRangeDto(startDate, endDate))
                .expensesCount(expenses.size())
                .totalAmount(calculateTotalAmount(expenses))
                .expenseWithMaxAmount(getExpenseWithMaxAmount(expenses))
                .categoryWithMaxTotalAmount(getCategoryWithMaxTotalAmount(expenses))
                .categoryWithMaxExpensesCount(getCategoryWithMaxExpensesCount(expenses))
                .build();
    }

    private Category getCategoryWithMaxExpensesCount(List<Expense> expenses) {
        return expenses.stream()
                .collect(Collectors.groupingBy(Expense::getCategory, counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    private Category getCategoryWithMaxTotalAmount(List<Expense> expenses) {
        return expenses.stream()
                .collect(Collectors.groupingBy(Expense::getCategory, summingDouble(e -> e.getAmount().doubleValue())))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    private ExpenseDto getExpenseWithMaxAmount(List<Expense> expenses) {
        return expenses.stream()
                .max(Comparator.comparing(Expense::getAmount))
                .map(ExpenseMapper::convertToDto)
                .orElse(null);
    }

    private BigDecimal calculateTotalAmount(List<Expense> expenses) {
        return expenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
