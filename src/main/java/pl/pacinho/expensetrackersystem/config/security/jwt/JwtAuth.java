package pl.pacinho.expensetrackersystem.config.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.pacinho.expensetrackersystem.user.model.entity.ExpenseTrackerUser;
import pl.pacinho.expensetrackersystem.utils.DateUtils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Service
public class JwtAuth {

    @Value("${token.secret}")
    private String SECRET;

    public String generateToken(String login) {
        String uuid = UUID.randomUUID().toString();
        Map<String, Object> claims = new HashMap<>() {
            {
                put("uuid", uuid);
            }
        };
        return createToken(claims, login);
    }

    private String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setExpiration(DateUtils.convert(LocalDateTime.now().plus(10, ChronoUnit.MINUTES)))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Claims extractClaims(String token) {
        return (Claims) Jwts.parser()
                .setSigningKey(SECRET)
                .parse(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractClaims(token);
        return claimsResolver.apply(claims);
    }
}
