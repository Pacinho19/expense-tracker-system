package pl.pacinho.expensetrackersystem.user.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.pacinho.expensetrackersystem.user.model.entity.ExpenseTrackerUser;

import java.util.Optional;

@Profile("!integration")
@Repository
interface UserRepository extends ExpenseTrackerUserRepository, JpaRepository<ExpenseTrackerUser, Integer> {
    Optional<ExpenseTrackerUser> findByLogin(String username);
}
