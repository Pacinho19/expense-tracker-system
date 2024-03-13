package pl.pacinho.expensetrackersystem.expense.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import pl.pacinho.expensetrackersystem.user.model.entity.ExpenseTrackerUser;
import pl.pacinho.expensetrackersystem.user.repository.ExpenseTrackerUserRepository;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Repository
@Profile("integration")
public class UserIntegrationRepository implements ExpenseTrackerUserRepository {

    private Map<Integer, ExpenseTrackerUser> users = Map.ofEntries(
            Map.entry(1, new ExpenseTrackerUser("super_admin", "super_admin", "ROLE_SUPER_ADMIN", "pass")),
            Map.entry(2, new ExpenseTrackerUser("admin", "admin", "ROLE_ADMIN", "pass")),
            Map.entry(3, new ExpenseTrackerUser("user", "user", "ROLE_USER", "pass"))
            );

    @Override
    public Optional<ExpenseTrackerUser> findByLogin(String username) {
        return users.values()
                .stream()
                .filter(u -> Objects.equals(username, u.getLogin()))
                .findFirst();
    }
}
