package br.com.locahouse.security.authentication;

import br.com.locahouse.security.userdetails.UserDetailsImpl;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class JwtTokenService {

    /**
     * Chave secreta utilizada para gerar e verificar o token.
     **/
    private static final String CHAVE_SECRETA = "4Z^XrroxR@dWxqf$mTTKwW$!@#qGr4P";

    /**
     * Algoritmo para criação e verificação da assinatura do token.
     **/
    private static final Algorithm ALGORITMO = Algorithm.HMAC256(CHAVE_SECRETA);

    /**
     * Emissor do token.
     **/
    private static final String EMISSOR = "pizzurg-api"; //

    public String gerarToken(UserDetailsImpl user) {
        try {
            return JWT.create().withIssuer(EMISSOR).withIssuedAt(definirDataHoraEmissao()).withExpiresAt(definirDataHoraExpiracao()).withSubject(user.getUsername()).sign(ALGORITMO);
        } catch (JWTCreationException exception) {
            throw new JWTCreationException("Erro ao gerar token.", exception);
        }
    }

    public String recuperarSubject(String token) {
        return JWT.require(ALGORITMO).withIssuer(EMISSOR).build().verify(token).getSubject();
    }

    private Instant definirDataHoraEmissao() {
        return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toInstant();
    }

    private Instant definirDataHoraExpiracao() {
        return ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).plusHours(4).toInstant();
    }
}
