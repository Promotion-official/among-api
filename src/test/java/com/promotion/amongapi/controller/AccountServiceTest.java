package com.promotion.amongapi.controller;

import com.promotion.amongapi.dto.AccountDto;
import com.promotion.amongapi.service.AccountService;
import com.thedeanda.lorem.LoremIpsum;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@ExtendWith(SpringExtension.class)
public class AccountServiceTest {
    private static AccountService service;
    private static List<AccountDto> testDtos;
    private static LoremIpsum loremIpsum;

    @BeforeAll
    public static void init() {
        Random random = new Random();

        service = new AccountService();
        loremIpsum = LoremIpsum.getInstance();
        testDtos = new ArrayList<>();

        testDtos = Stream.generate(() -> AccountDto.builder()
                .name(loremIpsum.getName())
                .email(loremIpsum.getEmail())
                .generation(random.nextInt(100))
                .clazz(random.nextInt(4))
                .number(random.nextInt(20))
                .build()).limit(10).collect(Collectors.toList());
    }

    @Test
    public void testAddAccount() {
        //Setting test environment
        testDtos.forEach(service::addAccount);

        int idxOfTestDto = new Random().nextInt(10);
        AccountDto result = service.getAccount(testDtos.get(idxOfTestDto).getEmail());
        assertEquals(testDtos.get(idxOfTestDto), result);

        //Logging test
        log.info("AccountServiceTest - testAddAccount");
        log.info("added user : " + testDtos);
        log.info("result : " + result);
    }
}
