package com.promotion.amongapi.controller;

import com.promotion.amongapi.domain.dto.AccountDto;
import com.promotion.amongapi.jwt.JwtProvider;
import com.promotion.amongapi.service.AccountService;
import com.thedeanda.lorem.LoremIpsum;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        accountController = new AccountController(service, mock(JwtProvider.class));

        lorem = LoremIpsum.getInstance();
        random = new Random();
    }


    @Test
    public void testGetAccount() {
        //Prepare test environment
        //---->Set request data
        int maxGeneration = Calendar.getInstance().get(Calendar.YEAR);
        AccountDto requestDto = AccountDto.builder()
                .name(lorem.getName())
                .email(lorem.getEmail())
                .password(lorem.getWords(1))
                .generation(random.nextInt(maxGeneration) + 1)
                .clazz(random.nextInt(4) + 1)
                .number(random.nextInt(20) + 1)
                .build();
        when(service.get(requestDto.getEmail())).thenReturn(requestDto);

        //---->Get response data
        AccountDto responseDto = accountController.getAccount(requestDto, "").getBody();

        //Logging test
        log.info("AccountControllerTest - testGetAccount");
        log.info("request dto : " + requestDto);
        log.info("response dto : " + responseDto);

        //Check response data
        assertEquals(responseDto, requestDto);
    }

    @Test
    public void testGetAccountFailure() {
        //Prepare test environment
        //---->Set request data
        int maxGeneration = Calendar.getInstance().get(Calendar.YEAR);
        AccountDto requestDto = AccountDto.builder()
                .name(lorem.getName())
                .email(lorem.getEmail())
                .password(lorem.getWords(1))
                .generation(random.nextInt(maxGeneration) + 1)
                .clazz(random.nextInt(4) + 1)
                .number(random.nextInt(20) + 1)
                .build();
        when(service.get(requestDto.getEmail())).thenThrow(EntityNotFoundException.class);

        //---->Get response data
        AtomicReference<AccountDto> responseDto = new AtomicReference<>();
        assertThrows(EntityNotFoundException.class, () ->responseDto.set(accountController.getAccount(requestDto, "").getBody()));

        //Logging test
        log.info("AccountControllerTest - testGetAccount");
        log.info("request dto : " + requestDto);
        log.info("response dto : " + responseDto);
    }
}
