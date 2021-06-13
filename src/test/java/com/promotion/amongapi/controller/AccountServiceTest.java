package com.promotion.amongapi.controller;

import com.promotion.amongapi.dto.AccountDto;
import com.promotion.amongapi.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@ExtendWith(SpringExtension.class)
public class AccountServiceTest {
    AccountService service;
    AccountDto testDto;

    @BeforeEach
    public void init() {
        service = new AccountService();

        testDto = AccountDto.builder()
                .name("지인호")
                .email("xylopeofficial@gmail.com")
                .generation(4)
                .clazz(2)
                .number(18)
                .build();
    }

    @Test
    public void testAddAccount() {
        service.addUser(testDto);
        AccountDto result = service.getUser(testDto.getEmail());
        assertEquals(testDto, result); //TODO 테스트 데이터를 난수화 하여 테스트 의 신뢰성 증명 | 2021.06.13
    }
}
