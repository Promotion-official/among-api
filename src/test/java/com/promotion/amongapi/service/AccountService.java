package com.promotion.amongapi.service;

import com.promotion.amongapi.dto.AccountDto;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service @Component
public class AccountService {
    private final List<AccountDto> accountList;

    public AccountService() {
        accountList = new ArrayList<>();
    }

    public void addAccount(AccountDto account) {
        accountList.add(account);
    }

    public AccountDto getAccount(String email) {
        AtomicReference<AccountDto> dto = new AtomicReference<>();
        accountList.forEach(account -> {
            if(account.getEmail().equals(email))
                dto.set(account);
        });

        return dto.get();
    }
}
