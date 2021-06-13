package com.promotion.amongapi.service;

import com.promotion.amongapi.dto.AccountDto;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service @Component
public class AccountService {
    private AccountDto dto;

    public void addAccount(AccountDto testDto) {
        dto = testDto;
    }

    public AccountDto getAccount(String email) {
        return dto;
    }
}
