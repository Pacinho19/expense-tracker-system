package pl.pacinho.expensetrackersystem.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import pl.pacinho.expensetrackersystem.config.security.jwt.JWTRequestFilter;
import pl.pacinho.expensetrackersystem.config.security.jwt.JwtAuth;
import pl.pacinho.expensetrackersystem.user.service.ExpenseTrackerUserService;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final ExpenseTrackerUserService expenseTrackerUserService;
    private final JwtAuth jwtAuth;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(expenseTrackerUserService);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET).authenticated()
                .antMatchers(HttpMethod.POST).hasAnyRole("ADMIN", "SUPER_ADMIN")
                .antMatchers(HttpMethod.PUT).hasAnyRole("SUPER_ADMIN")
                .antMatchers(HttpMethod.DELETE).hasAnyRole("SUPER_ADMIN")
                .and()
                .addFilter(new JWTRequestFilter(authenticationManager(), expenseTrackerUserService, jwtAuth))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/v3/api-docs/**",
                "/swagger-ui/**",
                "/swagger-ui.html",
                "/authorization");
    }

}
