package com.promotion.amongapi.jwt;

import com.promotion.amongapi.advice.exception.WrongTokenException;
import com.promotion.amongapi.domain.dto.DecodedAuthTokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
@RequiredArgsConstructor
@Slf4j

public class JwtProvider {

    private final JwtProperties jwtProperties;

    private SecretKey secretKey;


    @PostConstruct
    public void init() {
        var secret = Base64.getEncoder().encodeToString(this.jwtProperties.getSecretKey().getBytes());
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public DecodeAuthTokenDto encode(String authToken) {
        if (validateToken(authToken)) {
            Claims claims = Jwts.parserBuilder().setSigningKey(this.secretKey).build().parseClaimsJws(authToken).getBody();
            String[] data = authToken.split(" ");
            data[0] = claims.get("email",String.class);
            data[1] = claims.get("clientId",String.class);
            return new DecodedAuthTokenDto(data[0],data[1]);
        } else throw new WrongTokenException();

    }

    public boolean validateToken(String authToken) {
        try {
            Jws<Claims> claims = Jwts
                    .parserBuilder()
                    .setSigningKey(this.secretKey).build()
                    .parseClaimsJws(authToken);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new WrongTokenException();
        }
    }
}
