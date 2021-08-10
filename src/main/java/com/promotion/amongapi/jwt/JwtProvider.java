package com.promotion.amongapi.jwt;

import com.promotion.amongapi.advice.exception.WrongTokenException;
import com.promotion.amongapi.domain.dto.EncodedAuthTokenDto;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {
    public EncodedAuthTokenDto encode(String authToken) {
        String[] data = authToken.split(" ");
        if(data.length < 2) throw new WrongTokenException();
        return new EncodedAuthTokenDto(data[0], data[1]);
    }
}
