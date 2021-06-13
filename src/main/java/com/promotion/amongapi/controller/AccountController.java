package com.promotion.amongapi.controller;

import com.promotion.amongapi.dto.AccountDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/account")
public class AccountController {
    @PostMapping("/get-account")
    public ResponseEntity<AccountDto> getAccount(@RequestBody AccountDto account_email) {
        return ResponseEntity.status(HttpStatus.OK).body(new AccountDto(account_email.getEmail() + "email"));
    }
}
