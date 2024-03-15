package pl.pacinho.expensetrackersystem.expense.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import pl.pacinho.expensetrackersystem.expense.model.entity.Expense;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ExpenseRepository {

    void deleteById(@Param("id") int id);

    List<Expense> findAllByDateBetween(LocalDate startDate, LocalDate endDate);

    Expense save(Expense expense);

    Optional<Expense> findById(int id);

    List<Expense> findAll();
    Page<Expense> findAll(Pageable pageable);
}
