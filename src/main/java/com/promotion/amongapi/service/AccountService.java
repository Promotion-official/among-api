package com.promotion.amongapi.service;

import com.promotion.amongapi.dto.AccountDto;
import com.promotion.amongapi.repository.AccountRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service @Component
public class AccountService {
    private final List<AccountDto> accountList;
    private final AccountRepository repository;

    public AccountService(AccountRepository repository) {
        accountList = new ArrayList<>();
        this.repository = repository;
    }

    public void add(AccountDto account) {
        repository.save(account);
    }

    public AccountDto get(String email) {
        return repository.getAccountDtoByEmail(email);
    }
}
