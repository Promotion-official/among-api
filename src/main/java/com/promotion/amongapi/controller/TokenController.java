package com.promotion.amongapi.controller;

import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.promotion.amongapi.advice.exception.TokenAlreadyExistException;
import com.promotion.amongapi.annotation.Verify;
import com.promotion.amongapi.domain.Permission;
import com.promotion.amongapi.domain.dto.AccountDto;
import com.promotion.amongapi.domain.dto.EncodedAuthTokenDto;
import com.promotion.amongapi.domain.entity.AuthorizeKey;
import com.promotion.amongapi.repository.AuthorizeKeyRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController()
@RequestMapping("/token")
@Slf4j
public class TokenController {
    private final AuthorizeKeyRepository authorizeKeyRepository;

    @PostMapping("/add-auth-token") @Verify(perm = Permission.OPERATOR)
    public void addAuthToken(@RequestBody TargetToken targetToken) {
        if(authorizeKeyRepository.existsById(targetToken.targetToken)) throw new TokenAlreadyExistException();
        authorizeKeyRepository.save(new AuthorizeKey(targetToken.getTargetToken(), Permission.PRODUCT.name()));
    }

    @AllArgsConstructor
    @Getter
    static class TargetToken {
        String targetToken;
    }
}
