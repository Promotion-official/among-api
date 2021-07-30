package com.promotion.amongapi.jwt;

import com.promotion.amongapi.advice.exception.WrongTokenDataException;
import com.promotion.amongapi.domain.dto.EncodedAuthTokenDto;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {
    public EncodedAuthTokenDto encode(String authToken) {
        String[] data = authToken.split(" ");
        if(data.length < 2) throw new WrongTokenDataException();
        return new EncodedAuthTokenDto(data[0], data[1]);
    }
}
