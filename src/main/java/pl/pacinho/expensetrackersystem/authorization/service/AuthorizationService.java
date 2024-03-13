package pl.pacinho.expensetrackersystem.authorization.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pacinho.expensetrackersystem.config.security.jwt.JwtAuth;
import pl.pacinho.expensetrackersystem.user.model.entity.ExpenseTrackerUser;
import pl.pacinho.expensetrackersystem.user.service.ExpenseTrackerUserService;

@RequiredArgsConstructor
@Service
public class AuthorizationService {

    private final ExpenseTrackerUserService expenseTrackerUserService;
    private final JwtAuth jwtAuth;

    public String generateToken(String username) {
        ExpenseTrackerUser expenseTrackerUser = expenseTrackerUserService.getByName(username);
        return jwtAuth.generateToken(expenseTrackerUser.getLogin());
    }
}
