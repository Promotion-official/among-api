package com.promotion.amongapi.domain.converter;

import com.promotion.amongapi.domain.dto.AccountDto;
import com.promotion.amongapi.domain.entity.Account;
import com.thedeanda.lorem.LoremIpsum;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class AccountConverterTest {
    private static AccountConverter converter;
    private static AccountDto dto;
    private static Account entity;


    @BeforeAll
    public static void init() {
        converter = new AccountConverter();
    }

    @BeforeEach
    public void initData() {
        LoremIpsum lorem = LoremIpsum.getInstance();
        Random random = new Random();
        int maxGeneration = Calendar.getInstance().get(Calendar.YEAR) - 2016;

        dto = AccountDto.builder()
                .name(lorem.getName())
                .email(lorem.getEmail())
                .generation(random.nextInt(maxGeneration) + 1)
                .clazz(random.nextInt(4) + 1)
                .build();

        entity = new Account(
                dto.getEmail(),
                dto.getName(),
                dto.getGeneration(),
                dto.getClazz(),
                dto.getNumber()
                );
    }

    @Test
    public void testConvertDtoToEntity() {
        //Prepare test environment
        //---->Convert data
        Account expectedResult = converter.convertDtoToEntity(dto);

        //check convert data
        assertEquals(expectedResult, entity);

        //Logging test
        log.info("entity : " + entity);
        log.info("expected result : " + expectedResult);
    }

    @Test
    public void testConvertEntityToDto() {
        //Prepare test environment
        //---->Convert data
        AccountDto expectedResult = converter.convertEntityToDto(entity);

        //check convert data
        assertEquals(expectedResult, dto);

        //Logging test
        log.info("dto : " + dto);
        log.info("expected result : " + expectedResult);
    }
}
