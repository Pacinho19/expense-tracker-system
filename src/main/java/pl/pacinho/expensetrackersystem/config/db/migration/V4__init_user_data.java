package pl.pacinho.expensetrackersystem.config.db.migration;

import lombok.RequiredArgsConstructor;
import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.stereotype.Component;
import pl.pacinho.expensetrackersystem.config.security.CryptoConfig;


@RequiredArgsConstructor
@Component
public class V4__init_user_data extends BaseJavaMigration {

    private final CryptoConfig cryptoConfig;

    @Override
    public void migrate(Context context) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(new SingleConnectionDataSource(context.getConnection(), true));
        jdbcTemplate.execute("insert into expense_tracker_user(name, login, role, password) values ('mother', 'mother', 'ROLE_ADMIN', '" + cryptoConfig.passwordEncoder().encode("mother") + "')");
        jdbcTemplate.execute("insert into expense_tracker_user(name, login, role, password) values ('father', 'father', 'ROLE_SUPER_ADMIN', '" + cryptoConfig.passwordEncoder().encode("father") + "')");
        jdbcTemplate.execute("insert into expense_tracker_user(name, login, role, password) values ('kid', 'kid', 'ROLE_USER', '" + cryptoConfig.passwordEncoder().encode("kid") + "')");
    }
}
