package br.com.fiap.totem_express.infrastructure.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JWTService {

    private final Algorithm ALGORITHM;

    public JWTService(@Value("${authentication.secret}") String secret) {
        ALGORITHM = Algorithm.HMAC256(secret);
    }

    public Optional<String> getUserFromToken(String token){
        try {
            final var jwt = JWT.require(ALGORITHM).build().verify(token);
            final var user = jwt.getClaim("user").asString();

            return Optional.ofNullable(user);
        }
        catch (JWTVerificationException exception){
            return Optional.empty();
        }
    }
}
