package com.promotion.amongapi.domain.converter;

import com.promotion.amongapi.domain.Permission;
import com.promotion.amongapi.domain.dto.AuthorizeKeyDto;
import com.promotion.amongapi.domain.entity.AuthorizeKey;
import com.thedeanda.lorem.LoremIpsum;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class AuthorizeKeyConverterTest {
    private static AuthorizeKeyConverter converter;
    private static AuthorizeKeyDto dto;
    private static AuthorizeKey entity;

    @BeforeAll
    public static void init() {
        converter = new AuthorizeKeyConverter();
    }

    @BeforeEach
    public void initData() {
        String key = Base64.getEncoder().encodeToString(LoremIpsum.getInstance().getName().getBytes(StandardCharsets.UTF_8));

        Permission[] allPerm = Permission.values();
        int numOfPerm = allPerm.length;
        Permission randomPerm = allPerm[new Random().nextInt(numOfPerm)];

        dto = AuthorizeKeyDto.builder()
                .authorizeKey(key)
                .perm(randomPerm)
                .build();

        entity = new AuthorizeKey(key, randomPerm.name());
    }

    @Test
    public void testConvertDtoToEntity() {
        //Prepare test environment
        //---->Convert data
        AuthorizeKey expectedResult = converter.convertDtoToEntity(dto);

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
        AuthorizeKeyDto expectedResult = converter.convertEntityToDto(entity);

        //check convert data
        assertEquals(expectedResult, dto);

        //Logging test
        log.info("dto : " + dto);
        log.info("expected result : " + expectedResult);
    }
}
