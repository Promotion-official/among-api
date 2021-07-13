package com.promotion.amongapi.advice.exception;

import com.promotion.amongapi.domain.dto.AccountDto;

public class AccountAlreadyExistException extends EntityAlreadyExistException {

    public AccountAlreadyExistException(AccountDto account) {
        super(account);
    }
}
