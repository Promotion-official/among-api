package com.promotion.amongapi.controller;

import com.promotion.amongapi.dto.AccountDto;
import com.promotion.amongapi.service.AccountService;
import com.thedeanda.lorem.LoremIpsum;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@ExtendWith(SpringExtension.class)
public class AccountServiceTest {
    private static AccountService service;
    private static AccountDto testDto;
    private static LoremIpsum loremIpsum;

    @BeforeAll
    public static void init() {
        Random random = new Random();

        service = new AccountService();
        loremIpsum = LoremIpsum.getInstance();
        testDto = AccountDto.builder()
                .name(loremIpsum.getName())
                .email(loremIpsum.getEmail())
                .generation(random.nextInt(100))
                .clazz(random.nextInt(4))
                .number(random.nextInt(20))
                .build();
    }

    @Test
    public void testAddAccount() {
        service.addAccount(testDto);
        AccountDto result = service.getAccount(testDto.getEmail());
        assertEquals(testDto, result); //TODO 테스트 데이터를 난수화 하여 테스트 의 신뢰성 증명 | 2021.06.13

        //Logging test
        log.info("AccountServiceTest - testAddAccount");
        log.info("added user : " + testDto);
        log.info("result : " + result);
    }
}
