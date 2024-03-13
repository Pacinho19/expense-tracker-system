package pl.pacinho.expensetrackersystem.expense.controller;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import pl.pacinho.expensetrackersystem.expense.model.entity.Expense;
import pl.pacinho.expensetrackersystem.expense.repository.ExpenseRepository;
import pl.pacinho.expensetrackersystem.utils.DateUtils;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.*;


@Repository
@Profile("integration")
public class ExpenseIntegrationRepository implements ExpenseRepository {

    private Map<Integer, Expense> expenses = new HashMap<>();

    @Override
    public void deleteById(int id) {
        expenses.remove(id);
    }

    @Override
    public List<Expense> findAllByDateBetween(LocalDate startDate, LocalDate endDate) {
        return expenses.values()
                .stream()
                .filter(expense -> DateUtils.isBetween(expense.getDate(), startDate, endDate))
                .toList();
    }

    @SneakyThrows
    @Override
    public Expense save(Expense expense) {
        int id = expense.getId() != null && expense.getId() > 0 ? expense.getId() : expenses.size() + 1;
        Field field = Expense.class.getDeclaredField("id");
        field.setAccessible(true);
        field.set(expense, id);
        expenses.put(id, expense);
        return expense;
    }

    @Override
    public Optional<Expense> findById(int id) {
        return Optional.ofNullable(expenses.get(id));
    }

    @Override
    public List<Expense> findAll() {
        return new ArrayList<>(expenses.values());
    }
}
