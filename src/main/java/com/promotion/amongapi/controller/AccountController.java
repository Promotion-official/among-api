package com.promotion.amongapi.controller;

import com.promotion.amongapi.advice.exception.AnotherUserTokenException;
import com.promotion.amongapi.annotation.Verify;
import com.promotion.amongapi.domain.Permission;
import com.promotion.amongapi.domain.dto.AccountDto;
import com.promotion.amongapi.domain.dto.DecodeAuthTokenDto;
import com.promotion.amongapi.jwt.JwtProvider;
import com.promotion.amongapi.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController()
@RequestMapping("/api/v1/account")
@Slf4j
public class AccountController {
    private final AccountService service;
    private final JwtProvider jwtProvider;
    @PostMapping("/get-account")
    public ResponseEntity<AccountDto> getAccount(@RequestBody AccountDto account, @RequestParam String auth_token) {
        DecodeAuthTokenDto dto = jwtProvider.encode(auth_token);

        if(dto.getEmail().equals(account.getEmail())) {
            AccountDto result = service.get(account.getEmail());
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } throw new AnotherUserTokenException(dto.getEmail());
    }

    @PostMapping("/add-account") @Verify(perm = {Permission.ADMINISTRATOR, Permission.OPERATOR})
    public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto account) {
        service.add(account);
        return ResponseEntity.ok(account);
    }
}
