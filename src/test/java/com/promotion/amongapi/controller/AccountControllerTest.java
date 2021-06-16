package com.promotion.amongapi.controller;

import com.promotion.amongapi.domain.dto.AccountDto;
import com.promotion.amongapi.service.AccountService;
import com.thedeanda.lorem.LoremIpsum;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Calendar;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(SpringExtension.class)
public class AccountControllerTest {
    private static AccountController accountController;
    private static AccountService service;

    private static LoremIpsum lorem;
    private static Random random;

    @BeforeAll
    public static void init() { //init test objects
        service = mock(AccountService.class);
        accountController = new AccountController(service);

        lorem = LoremIpsum.getInstance();
        random = new Random();
    }


    @Test
    public void testGetAccount() {
        //Set request data
        int maxGeneration = Calendar.getInstance().get(Calendar.YEAR);
        AccountDto requestDto = AccountDto.builder()
                .name(lorem.getName())
                .email(lorem.getEmail())
                .generation(random.nextInt(maxGeneration) + 1)
                .clazz(random.nextInt(4) + 1)
                .number(random.nextInt(20) + 1)
                .build();
        when(service.get(requestDto.getEmail())).thenReturn(requestDto);

        //Check response data
        AccountDto responseDto = accountController.getAccount(requestDto).getBody();
        assertEquals(responseDto, requestDto);

        //Logging test
        log.info("AccountControllerTest - testGetAccount");
        log.info("request dto : " + requestDto);
        log.info("response dto : " + responseDto);
    }

    @Test
    public void testGetAccountFailure() {
        //Set request data
        int maxGeneration = Calendar.getInstance().get(Calendar.YEAR);
        AccountDto requestDto = AccountDto.builder()
                .name(lorem.getName())
                .email(lorem.getEmail())
                .generation(random.nextInt(maxGeneration) + 1)
                .clazz(random.nextInt(4) + 1)
                .number(random.nextInt(20) + 1)
                .build();
        when(service.get(requestDto.getEmail())).thenThrow();

        //Check response data
        AccountDto responseDto = accountController.getAccount(requestDto).getBody();
        assertEquals(responseDto, requestDto);

        //Logging test
        log.info("AccountControllerTest - testGetAccount");
        log.info("request dto : " + requestDto);
        log.info("response dto : " + responseDto);
    }
}
