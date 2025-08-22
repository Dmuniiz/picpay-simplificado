package com.picpaysimplificado.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.picpaysimplificado.domain.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("{api.security.token.secret}")
    private String secret;

    public static final String ISSUER = "API Pic Pay";

    public String generateJWT(User user){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(user.getEmail())
                    .withClaim("id", user.getId())
                    .withExpiresAt(dateExpires())
                    .sign(algorithm);
            return token;
        }catch (JWTCreationException ex){
            throw new JWTCreationException("Não foi possivel criar token",ex);
        }
    }

    public String getSubjectUser(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();
            return verifier;
        }catch (JWTCreationException ex){
            throw new JWTCreationException("Não foi verificar usuário",ex);
        }
    }

    public Instant dateExpires(){
        return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-03:00"));
    }

}
