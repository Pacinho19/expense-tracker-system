package pl.pacinho.expensetrackersystem.config.security.jwt;

import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import pl.pacinho.expensetrackersystem.user.service.ExpenseTrackerUserService;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JWTRequestFilter extends BasicAuthenticationFilter {

    public static final String TOKEN_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    private final ExpenseTrackerUserService expenseTrackerUserService;
    private final JwtAuth jwtAuth;

    public JWTRequestFilter(AuthenticationManager authenticationManager, ExpenseTrackerUserService expenseTrackerUserService, JwtAuth jwtAuth) {
        super(authenticationManager);
        this.expenseTrackerUserService = expenseTrackerUserService;
        this.jwtAuth = jwtAuth;
    }

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        if (authentication == null) {
            filterChain.doFilter(request, response);
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(TOKEN_HEADER);
        if (token != null && token.startsWith(TOKEN_PREFIX)) {
            token = token.replace(TOKEN_PREFIX, "");
            String username = jwtAuth.extractUserName(token);
            if (username != null) {
                UserDetails userDetails = expenseTrackerUserService.loadUserByUsername(username);
                return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
            }
        }
        return null;
    }
}
