package com.promotion.amongapi.service;

import com.promotion.amongapi.dto.AccountDto;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service @Component
public class AccountService {

    public void addUser(AccountDto testDto) {

    }

    public AccountDto getUser(String email) {
        return AccountDto.builder()
                .name("지인호")
                .email("xylopeofficial@gmail.com")
                .generation(4)
                .clazz(2)
                .number(18)
                .build();
    }
}
