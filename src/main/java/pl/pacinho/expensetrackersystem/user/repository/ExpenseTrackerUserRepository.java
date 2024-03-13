package pl.pacinho.expensetrackersystem.user.repository;

import pl.pacinho.expensetrackersystem.user.model.entity.ExpenseTrackerUser;

import java.util.Optional;

public interface ExpenseTrackerUserRepository {
    Optional<ExpenseTrackerUser> findByLogin(String username);
}
