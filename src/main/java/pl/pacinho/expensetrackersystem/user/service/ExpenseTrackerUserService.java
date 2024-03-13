package pl.pacinho.expensetrackersystem.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.pacinho.expensetrackersystem.user.model.entity.ExpenseTrackerUser;
import pl.pacinho.expensetrackersystem.user.model.ExpenseTrackerUserDetails;
import pl.pacinho.expensetrackersystem.user.repository.ExpenseTrackerUserRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ExpenseTrackerUserService implements UserDetailsService {

    private final ExpenseTrackerUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new ExpenseTrackerUserDetails(getByName(username));
    }

    public ExpenseTrackerUser getByName(String username) {
        Optional<ExpenseTrackerUser> userByName = userRepository.findByLogin(username);
        if (userByName.isEmpty()) {
            throw new UsernameNotFoundException("Cannot find user by username : " + username);
        }
        return userByName.get();
    }
}
