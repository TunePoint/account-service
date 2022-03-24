package ua.tunepoint.account.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;
import ua.tunepoint.account.config.properties.JwtProperties;
import ua.tunepoint.account.data.entity.Role;
import ua.tunepoint.account.data.entity.User;
import ua.tunepoint.security.SecurityProperties;

import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider implements TokenProvider {

    private static final String ROLES = "roles";
    private static final String ID = "id";

    private final JwtProperties jwtProperties;
    private final Algorithm algorithm;

    public JwtTokenProvider(JwtProperties jwtProperties, SecurityProperties securityProperties) {
        this.jwtProperties = jwtProperties;
        this.algorithm = Algorithm.HMAC256(securityProperties.getSecret().getBytes());
    }

    @Override
    public String accessToken(User user) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtProperties.getAccessTokenTtl()))
                .withIssuer(jwtProperties.getIssuer())
                .withClaim(ROLES, user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                .withClaim(ID, user.getId())
                .sign(algorithm);
    }

    @Override
    public String refreshToken(User user) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtProperties.getRefreshTokenTtl()))
                .withIssuer(jwtProperties.getIssuer())
                .sign(algorithm);
    }
}
