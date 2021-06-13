package com.promotion.amongapi.controller;

import com.promotion.amongapi.dto.AccountDto;
import com.thedeanda.lorem.LoremIpsum;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@ExtendWith(SpringExtension.class)
public class AccountControllerTest {
    private static AccountController accountController;
    private static LoremIpsum loremIpsum;

    @BeforeAll
    public static void init() { //init test objects
        accountController = new AccountController();
        loremIpsum = LoremIpsum.getInstance();
    }


    @Test
    public void testGetAccount() {
        //Set request data
        String email = loremIpsum.getEmail();
        AccountDto requestDto = AccountDto.builder().email(email).build();

        //Check response data
        AccountDto responseDto = accountController.getAccount(requestDto).getBody();
        assertEquals(responseDto, new AccountDto(email + "email"));

        //Logging test
        log.info("AccountControllerTest - testGetAccount");
        log.info("email : " + email);
        log.info("request dto : " + requestDto);
        log.info("response dto : " + responseDto);
    }
}
