package pl.pacinho.expensetrackersystem.expense.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.pacinho.expensetrackersystem.expense.model.entity.Expense;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {
    @Modifying
    @Query(value = "DELETE FROM Expense WHERE ID = :id")
    void deleteById(@Param("id") int id);

    List<Expense> findAllByDateBetween(LocalDate startDate, LocalDate endDate);
}
