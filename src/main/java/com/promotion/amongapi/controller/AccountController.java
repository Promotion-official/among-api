package com.promotion.amongapi.controller;

import com.promotion.amongapi.domain.dto.AccountDto;
import com.promotion.amongapi.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController()
@RequestMapping("/account")
@Slf4j
public class AccountController {
    private final AccountService service;
    @PostMapping("/get-account")
    public ResponseEntity<AccountDto> getAccount(@RequestBody AccountDto account) {
            AccountDto result = service.get(account.getEmail());
            return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
