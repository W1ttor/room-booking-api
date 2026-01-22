package dev.abraao.roombookingapi.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import dev.abraao.roombookingapi.domain.user.entity.User;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

@Component
public class JwtToken {

    private final String SECRET = "secret";
    private final String ISSUER = "issuer";

    public String generateToken(User user) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(user.getId().toString())
                    .withClaim("role", user.getRole().name())
                    .withExpiresAt(Date.from(Instant.now().plusSeconds(3600)))
                    .sign(algorithm);

        } catch (JWTVerificationException exception) {
            return exception.getMessage();
        }


    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            return JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return exception.getMessage();
        }
    }
}
