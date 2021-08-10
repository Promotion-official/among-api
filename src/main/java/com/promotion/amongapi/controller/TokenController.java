package com.promotion.amongapi.controller;

import com.promotion.amongapi.advice.exception.TokenAlreadyExistException;
import com.promotion.amongapi.annotation.Verify;
import com.promotion.amongapi.domain.Permission;
import com.promotion.amongapi.domain.entity.AuthorizeKey;
import com.promotion.amongapi.repository.AuthorizeKeyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController()
@RequestMapping("/api/v1/token")
@Slf4j
public class TokenController {
    private final AuthorizeKeyRepository authorizeKeyRepository;

    @PostMapping("/add-auth-token") @Verify(perm = Permission.OPERATOR)
    public void addAuthToken(@RequestHeader(value = "AuthToken") String token) {
        if(authorizeKeyRepository.existsById(token)) throw new TokenAlreadyExistException();
        authorizeKeyRepository.save(new AuthorizeKey(token, Permission.PRODUCT.name()));
    }
}
